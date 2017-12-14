package org.support.project.knowledge.logic;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.support.project.aop.Aspect;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.DateUtils;
import org.support.project.common.util.HtmlUtils;
import org.support.project.common.util.PropertyUtil;
import org.support.project.common.util.StringJoinBuilder;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.bat.FileParseBat;
import org.support.project.knowledge.config.IndexType;
import org.support.project.knowledge.config.SystemConfig;
import org.support.project.knowledge.dao.CommentsDao;
import org.support.project.knowledge.dao.DraftItemValuesDao;
import org.support.project.knowledge.dao.DraftKnowledgesDao;
import org.support.project.knowledge.dao.ExGroupsDao;
import org.support.project.knowledge.dao.KnowledgeEditGroupsDao;
import org.support.project.knowledge.dao.KnowledgeEditUsersDao;
import org.support.project.knowledge.dao.KnowledgeFilesDao;
import org.support.project.knowledge.dao.KnowledgeGroupsDao;
import org.support.project.knowledge.dao.KnowledgeHistoriesDao;
import org.support.project.knowledge.dao.KnowledgeItemValuesDao;
import org.support.project.knowledge.dao.KnowledgeTagsDao;
import org.support.project.knowledge.dao.KnowledgeUsersDao;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.dao.LikesDao;
import org.support.project.knowledge.dao.StockKnowledgesDao;
import org.support.project.knowledge.dao.StocksDao;
import org.support.project.knowledge.dao.TagsDao;
import org.support.project.knowledge.dao.TemplateMastersDao;
import org.support.project.knowledge.dao.ViewHistoriesDao;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.DraftItemValuesEntity;
import org.support.project.knowledge.entity.DraftKnowledgesEntity;
import org.support.project.knowledge.entity.KnowledgeEditGroupsEntity;
import org.support.project.knowledge.entity.KnowledgeEditUsersEntity;
import org.support.project.knowledge.entity.KnowledgeFilesEntity;
import org.support.project.knowledge.entity.KnowledgeGroupsEntity;
import org.support.project.knowledge.entity.KnowledgeHistoriesEntity;
import org.support.project.knowledge.entity.KnowledgeItemValuesEntity;
import org.support.project.knowledge.entity.KnowledgeTagsEntity;
import org.support.project.knowledge.entity.KnowledgeUsersEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.StocksEntity;
import org.support.project.knowledge.entity.TagsEntity;
import org.support.project.knowledge.entity.TemplateItemsEntity;
import org.support.project.knowledge.entity.TemplateMastersEntity;
import org.support.project.knowledge.entity.ViewHistoriesEntity;
import org.support.project.knowledge.indexer.IndexingValue;
import org.support.project.knowledge.logic.activity.Activity;
import org.support.project.knowledge.logic.activity.ActivityLogic;
import org.support.project.knowledge.logic.hook.AfterSaveHook;
import org.support.project.knowledge.logic.hook.BeforeSaveHook;
import org.support.project.knowledge.logic.hook.HookFactory;
import org.support.project.knowledge.searcher.SearchResultValue;
import org.support.project.knowledge.searcher.SearchingValue;
import org.support.project.knowledge.vo.KnowledgeData;
import org.support.project.knowledge.vo.StockKnowledge;
import org.support.project.web.bean.LabelValue;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.entity.GroupsEntity;
import org.support.project.web.entity.UsersEntity;
import org.support.project.web.exception.AuthenticateException;

/**
 * Logic class for knowledge data
 * @author Koda
 */
@DI(instance = Instance.Singleton)
public class KnowledgeLogic {
    /** LOG */
    private static final Log LOG = LogFactory.getLog(KnowledgeLogic.class);

    /** Group id for ALL_USER */
    public static final int ALL_USER = 0;
    /** Open range: PUBLIC */
    public static final int PUBLIC_FLAG_PUBLIC = 0;
    /** Open range: PRIVATE */
    public static final int PUBLIC_FLAG_PRIVATE = 1;
    /** Open range: PROTECT */
    public static final int PUBLIC_FLAG_PROTECT = 2;

    /** Index type: Bookmark */
    public static final int TYPE_KNOWLEDGE = IndexType.knowledge.getValue();
    /** Index type: Attached File */
    public static final int TYPE_FILE = IndexType.KnowledgeFile.getValue();
    /** Index type: Comment */
    public static final int TYPE_COMMENT = IndexType.KnowledgeComment.getValue();

    /** Prefix for Comment */
    public static final String COMMENT_ID_PREFIX = "COMMENT-";
    
    /** Get instance */
    public static KnowledgeLogic get() {
        return Container.getComp(KnowledgeLogic.class);
    }

    /** KnowledgesDao */
    private KnowledgesDao knowledgesDao = Container.getComp(KnowledgesDao.class);
    /** KnowledgeUsersDao */
    private KnowledgeUsersDao knowledgeUsersDao = KnowledgeUsersDao.get();
    /** TagsDao */
    private TagsDao tagsDao = TagsDao.get();
    /** KnowledgeTagsDao */
    private KnowledgeTagsDao knowledgeTagsDao = KnowledgeTagsDao.get();
    /** UploadedFileLogic */
    private UploadedFileLogic fileLogic = UploadedFileLogic.get();

    /** キーワード検索時のソート順 : スコア順 */
    public static final int KEYWORD_SORT_TYPE_SCORE = 1;

    /** キーワード検索時のソート順 : 日付順 */
    public static final int KEYWORD_SORT_TYPE_TIME = 2;

    /** キーワード検索時のソート順 */
    private int keywordSortType = KEYWORD_SORT_TYPE_SCORE;

    /**
     * キーワード検索時のソート順をセットする
     *
     * @param keywordSortType
     */
    public void setKeywordSortType(int keywordSortType) {
        this.keywordSortType = keywordSortType;
    }

    /**
     * タグの文字列（カンマ区切り）から、登録済のタグであれば、それを取得し、 存在しないものであれば、新たにタグを生成してタグの情報を取得
     * 
     * @param tags
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<TagsEntity> manegeTags(String tags) {
        List<TagsEntity> tagList = new ArrayList<>();
        if (StringUtils.isEmpty(tags)) {
            return tagList;
        }
        String[] splits;
        if (tags.indexOf(",") != -1) {
            splits = tags.split(",");
        } else {
            splits = new String[1];
            splits[0] = tags;
        }

        for (String tag : splits) {
            tag = tag.trim();
            if (tag.startsWith(" ")) {
                tag = tag.substring(" ".length());
            }
            if (tag.startsWith("　")) {
                tag = tag.substring("　".length());
            }

            TagsEntity tagsEntity = tagsDao.selectOnTagName(tag);
            if (tagsEntity == null) {
                tagsEntity = new TagsEntity();
                tagsEntity.setTagName(tag);
                tagsEntity = tagsDao.insert(tagsEntity);
                LOG.debug("Tag added." + PropertyUtil.reflectionToString(tagsEntity));
            }
            tagList.add(tagsEntity);
        }
        return tagList;
    }

    /**
     * ナレッジを登録
     * 
     * @param data KnowledgeData
     * @param loginedUser LoginedUser
     * @return KnowledgesEntity
     * @throws Exception Exception
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public KnowledgesEntity insert(KnowledgeData data, LoginedUser loginedUser) throws Exception {
        List<BeforeSaveHook> beforeSaveHooks = HookFactory.getBeforeSaveHookInstance(data, null);
        for (BeforeSaveHook beforeSaveHook : beforeSaveHooks) {
            beforeSaveHook.beforeSave(data, null, loginedUser);
        }
        // ナレッジを登録
        KnowledgesEntity insertedEntity = knowledgesDao.insert(data.getKnowledge());
        data.getKnowledge().setKnowledgeId(insertedEntity.getKnowledgeId());
        // アクセス権を登録
        saveAccessUser(insertedEntity, loginedUser, data.getViewers());
        // 編集権を登録
        saveEditorsUser(insertedEntity, loginedUser, data.getEditors());
        // タグを登録
        setTags(insertedEntity, data.getTags());
        // 添付ファイルを更新（紐付けをセット）
        fileLogic.setKnowledgeFiles(insertedEntity.getKnowledgeId(), data.getFileNos(), loginedUser);
        // 拡張項目の保存
        saveTemplateItemValue(insertedEntity.getKnowledgeId(), data.getTemplate(), loginedUser);

        // 全文検索エンジンへ登録
        saveIndex(insertedEntity, data.getTags(), data.getViewers(), data.getTemplate(), loginedUser.getUserId());
        // 一覧表示用の情報を更新
        updateKnowledgeExInfo(insertedEntity);
        // 履歴登録
        insertHistory(insertedEntity);
        // 通知
        NotifyLogic.get().notifyOnKnowledgeInsert(insertedEntity);
        
        // 下書きがあったら消す
        removeDraft(data.getDraftId(), loginedUser);

        List<AfterSaveHook> afterSaveHooks = HookFactory.getAfterSaveHookInstance(data);
        for (AfterSaveHook afterSaveHook : afterSaveHooks) {
            afterSaveHook.afterSave(data, loginedUser);
        }
        
        // CPの処理
        ActivityLogic.get().processKnowledgeSaveActivity(loginedUser, DateUtils.now(), insertedEntity);
        
        return insertedEntity;
    }

    /**
     * ナレッジを更新
     * 
     * @param data KnowledgeData
     * @param loginedUser LoginedUser
     * @return KnowledgesEntity
     * @throws Exception Exception
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public KnowledgesEntity update(KnowledgeData data, LoginedUser loginedUser) throws Exception {
        // 更新前の情報を保持
        KnowledgesEntity db = knowledgesDao.selectOnKey(data.getKnowledge().getKnowledgeId());
        List<BeforeSaveHook> beforeSaveHooks = HookFactory.getBeforeSaveHookInstance(data, db);
        for (BeforeSaveHook beforeSaveHook : beforeSaveHooks) {
            beforeSaveHook.beforeSave(data, db, loginedUser);
        }

        // ナレッッジを更新
        data.getKnowledge().setNotifyStatus(db.getNotifyStatus()); // 通知フラグはDBの値を引き継ぐ
        data.getKnowledge().setPoint(db.getPoint()); // ポイントもDBの値を引き継ぐ
        KnowledgesEntity updatedEntity = knowledgesDao.update(data.getKnowledge());
        // ユーザのアクセス権を解除
        knowledgeUsersDao.deleteOnKnowledgeId(updatedEntity.getKnowledgeId());
        // グループとナレッジのヒモ付を解除
        GroupLogic groupLogic = GroupLogic.get();
        groupLogic.removeKnowledgeGroup(updatedEntity.getKnowledgeId());
        // 編集権限を削除
        KnowledgeEditUsersDao editUsersDao = KnowledgeEditUsersDao.get();
        KnowledgeEditGroupsDao editGroupsDao = KnowledgeEditGroupsDao.get();
        editUsersDao.deleteOnKnowledgeId(updatedEntity.getKnowledgeId());
        editGroupsDao.deleteOnKnowledgeId(updatedEntity.getKnowledgeId());

        // アクセス権を登録
        saveAccessUser(updatedEntity, loginedUser, data.getViewers());
        // 編集権を登録
        saveEditorsUser(updatedEntity, loginedUser, data.getEditors());

        // タグを登録
        knowledgeTagsDao.deleteOnKnowledgeId(updatedEntity.getKnowledgeId());
        setTags(updatedEntity, data.getTags());

        // 拡張項目の保存
        saveTemplateItemValue(updatedEntity.getKnowledgeId(), data.getTemplate(), loginedUser);

        // 添付ファイルを更新（紐付けをセット）
        fileLogic.setKnowledgeFiles(updatedEntity.getKnowledgeId(), data.getFileNos(), loginedUser);

        // 一覧表示用の情報を更新
        updateKnowledgeExInfo(updatedEntity);

        if (data.isUpdateContent()) {
            // 履歴登録
            insertHistory(updatedEntity);
        } else {
            // 更新ユーザ＆更新日付を戻す
            updatedEntity.setUpdateUser(db.getUpdateUser());
            updatedEntity.setUpdateDatetime(db.getUpdateDatetime());
            knowledgesDao.physicalUpdate(updatedEntity);
        }
        if (data.isNotifyUpdate()) {
            // 通知
            NotifyLogic.get().notifyOnKnowledgeUpdate(updatedEntity);
        }
        if (data.isDonotUpdateTimeline()) {
            // タイムラインの上に表示しないと明示的に指定があれば、全文検索エンジン上の更新日は更新しない（検索エンジンのインデックスでタイムラインを作っているため）
            updatedEntity.setUpdateDatetime(db.getUpdateDatetime());
        }
        
        // 全文検索エンジンへ登録
        saveIndex(updatedEntity, data.getTags(), data.getViewers(), data.getTemplate(), updatedEntity.getInsertUser());

        // 下書きがあったら消す
        removeDraft(data.getDraftId(), loginedUser);
        
        List<AfterSaveHook> afterSaveHooks = HookFactory.getAfterSaveHookInstance(data);
        for (AfterSaveHook afterSaveHook : afterSaveHooks) {
            afterSaveHook.afterSave(data, loginedUser);
        }
        
        // CP
        ActivityLogic.get().processKnowledgeSaveActivity(loginedUser, DateUtils.now(), updatedEntity);
        
        return updatedEntity;
    }


    /**
     * テンプレートにある拡張項目値を保存
     * 
     * @param knowledgeId
     * @param template
     * @param loginedUser
     */
    private void saveTemplateItemValue(Long knowledgeId, TemplateMastersEntity template, LoginedUser loginedUser) {
        if (template == null) {
            return;
        }
        List<TemplateItemsEntity> items = template.getItems();
        if (items == null) {
            return;
        }
        for (TemplateItemsEntity item : items) {
            KnowledgeItemValuesEntity val = new KnowledgeItemValuesEntity();
            val.setKnowledgeId(knowledgeId);
            val.setTypeId(template.getTypeId());
            val.setItemNo(item.getItemNo());
            val.setItemValue(item.getItemValue());
            val.setItemStatus(KnowledgeItemValuesEntity.STATUS_SAVED);
            KnowledgeItemValuesDao.get().save(val);
        }
    }

    /**
     * ナレッジの更新履歴を登録
     * 
     * @param entity
     */
    public void insertHistory(KnowledgesEntity entity) {
        // 既存のナレッジ情報を履歴へコピー
        KnowledgesEntity origin = knowledgesDao.selectOnKey(entity.getKnowledgeId());
        KnowledgeHistoriesEntity history = new KnowledgeHistoriesEntity();
        PropertyUtil.copyPropertyValue(origin, history);
        KnowledgeHistoriesDao historiesDao = KnowledgeHistoriesDao.get();
        int max = historiesDao.selectMaxHistoryNo(entity.getKnowledgeId());
        max++;
        history.setHistoryNo(max);
        historiesDao.physicalInsert(history);
    }

    /**
     * タグを登録
     * 
     * @param entity
     * @param tags
     */
    private void setTags(KnowledgesEntity entity, List<TagsEntity> tags) {
        if (tags != null) {
            for (TagsEntity tagsEntity : tags) {
                KnowledgeTagsEntity knowledgeTagsEntity = new KnowledgeTagsEntity(entity.getKnowledgeId(), tagsEntity.getTagId());
                knowledgeTagsDao.save(knowledgeTagsEntity);
            }
        }
    }

    /**
     * 編集権限を登録
     * 
     * @param entity
     * @param loginedUser
     * @param editors
     */
    private void saveEditorsUser(KnowledgesEntity entity, LoginedUser loginedUser, List<LabelValue> editors) {
        KnowledgeEditUsersDao editUsersDao = KnowledgeEditUsersDao.get();
        KnowledgeEditGroupsDao editGroupsDao = KnowledgeEditGroupsDao.get();

        // 編集権限を設定
        if (editors != null && !editors.isEmpty()) {
            for (int i = 0; i < editors.size(); i++) {
                LabelValue labelValue = editors.get(i);

                Integer id = TargetLogic.get().getGroupId(labelValue.getValue());
                if (id != Integer.MIN_VALUE) {
                    KnowledgeEditGroupsEntity editGroupsEntity = new KnowledgeEditGroupsEntity();
                    editGroupsEntity.setKnowledgeId(entity.getKnowledgeId());
                    editGroupsEntity.setGroupId(id);
                    editGroupsDao.save(editGroupsEntity);
                } else {
                    id = TargetLogic.get().getUserId(labelValue.getValue());
                    if (id != Integer.MIN_VALUE && ALL_USER != id.intValue()) {
                        KnowledgeEditUsersEntity editUsersEntity = new KnowledgeEditUsersEntity();
                        editUsersEntity.setKnowledgeId(entity.getKnowledgeId());
                        editUsersEntity.setUserId(id);
                        editUsersDao.save(editUsersEntity);
                    }
                }
            }
        }
    }

    /**
     * アクセス権を登録
     * 
     * @param entity
     * @param loginedUser
     * @param targets
     */
    private void saveAccessUser(KnowledgesEntity entity, LoginedUser loginedUser, List<LabelValue> targets) {
        // ナレッジにアクセス可能なユーザに、自分自身をセット
        KnowledgeUsersEntity knowledgeUsersEntity = new KnowledgeUsersEntity();
        knowledgeUsersEntity.setKnowledgeId(entity.getKnowledgeId());
        knowledgeUsersEntity.setUserId(loginedUser.getLoginUser().getUserId());
        knowledgeUsersDao.insert(knowledgeUsersEntity);
        if (entity.getPublicFlag() == null || PUBLIC_FLAG_PUBLIC == entity.getPublicFlag()) {
            // 全て公開する情報
            knowledgeUsersEntity = new KnowledgeUsersEntity();
            knowledgeUsersEntity.setKnowledgeId(entity.getKnowledgeId());
            knowledgeUsersEntity.setUserId(ALL_USER);
            knowledgeUsersDao.insert(knowledgeUsersEntity);
        }
        if (entity.getPublicFlag() != null && entity.getPublicFlag().intValue() == PUBLIC_FLAG_PROTECT) {
            // ナレッジとグループを紐付け
            GroupLogic groupLogic = GroupLogic.get();
            groupLogic.saveKnowledgeGroup(entity.getKnowledgeId(), targets);

            // アクセスできるユーザを指定
            if (targets != null && !targets.isEmpty()) {
                for (int i = 0; i < targets.size(); i++) {
                    LabelValue labelValue = targets.get(i);
                    Integer id = TargetLogic.get().getUserId(labelValue.getValue());
                    if (id != Integer.MIN_VALUE && loginedUser.getUserId().intValue() != id.intValue() && ALL_USER != id.intValue()) {
                        knowledgeUsersEntity = new KnowledgeUsersEntity();
                        knowledgeUsersEntity.setKnowledgeId(entity.getKnowledgeId());
                        knowledgeUsersEntity.setUserId(id);
                        knowledgeUsersDao.insert(knowledgeUsersEntity);
                    }
                }
            }
        }
    }

    /**
     * 全文検索エンジンへ保存
     * 
     * @param entity
     * @param tags
     * @param template
     * @param groups
     * @param loginedUser
     * @throws Exception
     */
    private void saveIndex(KnowledgesEntity entity, List<TagsEntity> tags, List<LabelValue> targets, TemplateMastersEntity template, Integer creator)
            throws Exception {
        IndexingValue indexingValue = new IndexingValue();
        indexingValue.setType(TYPE_KNOWLEDGE);
        indexingValue.setTemplate(entity.getTypeId());
        indexingValue.setId(String.valueOf(entity.getKnowledgeId()));
        indexingValue.setTitle(entity.getTitle());

        StringBuilder content = new StringBuilder(entity.getContent());
        if (template != null) {
            List<TemplateItemsEntity> items = template.getItems();
            for (TemplateItemsEntity item : items) {
                content.append(item.getItemName()).append(':').append(item.getItemValue());
            }
        }
        indexingValue.setContents(content.toString());
        indexingValue.addUser(creator);
        if (entity.getPublicFlag() == null || PUBLIC_FLAG_PUBLIC == entity.getPublicFlag()) {
            indexingValue.addUser(ALL_USER);
        }
        if (tags != null) {
            for (TagsEntity tagsEntity : tags) {
                indexingValue.addTag(tagsEntity.getTagId());
            }
        }
        if (entity.getPublicFlag() != null && entity.getPublicFlag().intValue() == PUBLIC_FLAG_PROTECT) {
            if (targets != null) {
                for (LabelValue target : targets) {
                    Integer id = TargetLogic.get().getGroupId(target.getValue());
                    if (id != Integer.MIN_VALUE) {
                        indexingValue.addGroup(id);
                    }
                    id = TargetLogic.get().getUserId(target.getValue());
                    if (id != Integer.MIN_VALUE) {
                        indexingValue.addUser(id);
                    }
                }
            }
        }

        indexingValue.setCreator(creator);
        indexingValue.setTime(entity.getUpdateDatetime().getTime()); // 更新日時をセットするので、更新日時でソート

        IndexLogic.get().save(indexingValue); // 全文検索のエンジンにも保存（DBに保存する意味ないかも）
    }

    /**
     * 全文検索エンジンからナレッジを取得し、そこにさらに付加情報をつけて返す
     * 
     * @param searchingValue
     * @return
     * @throws Exception
     */
    private List<KnowledgesEntity> searchKnowledge(SearchingValue searchingValue) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("search params：" + PropertyUtil.reflectionToString(searchingValue));
        }
        LOG.trace("検索開始");
        List<SearchResultValue> list = IndexLogic.get().search(searchingValue, this.keywordSortType);
        LOG.trace("検索終了");
        LOG.trace("付加情報をセット開始");
        List<KnowledgesEntity> result = getKnowledgeDatas(list);
        LOG.trace("付加情報をセット終了");
        return result;
    }

    /**
     * ナレッジ検索
     * 
     * @param keyword
     * @param tags
     * @param groups
     * @param creators 
     * @param loginedUser
     * @param offset
     * @param limit
     * @return
     * @throws Exception
     */
    public List<KnowledgesEntity> searchKnowledge(String keyword, List<TagsEntity> tags, List<GroupsEntity> groups, 
            List<UsersEntity> creators, String[] templates, LoginedUser loginedUser,
            Integer offset, Integer limit) throws Exception {
        SearchingValue searchingValue = new SearchingValue();
        searchingValue.setKeyword(keyword);
        searchingValue.setOffset(offset);
        searchingValue.setLimit(limit);

        // タグが指定されてる場合はユーザに関係なく条件に追加する
        if (tags != null && !tags.isEmpty()) {
            for (TagsEntity tagsEntity : tags) {
                searchingValue.addTag(tagsEntity.getTagId());
            }
        }
        // テンプレート指定もユーザに関係なく条件追加
        if (templates != null) {
            for (String template: templates) {
                if (StringUtils.isNotEmpty(template) && StringUtils.isInteger(template)) {
                    searchingValue.addTemplate(new Integer(template));
                }
            }
        }
        
        if (creators != null) {
            for (UsersEntity creator : creators) {
                searchingValue.addCreator(creator.getUserId());
            }
        }
        
        // ログインしてない場合はグループ検索ができないので公開記事のみを対象にして検索する
        if (loginedUser == null) {
            searchingValue.addUser(ALL_USER);
            return searchKnowledge(searchingValue);
        }

        // グループが指定されてる場合はグループのみ対象にして検索する
        if (groups != null && !groups.isEmpty()) {
            boolean exit = false;
            for (GroupsEntity groupsEntity : groups) {
                // 指定のグループは、自分が所属しているグループかどうか
                List<GroupsEntity> logiedUserGroups = loginedUser.getGroups();
                if (logiedUserGroups != null && !logiedUserGroups.isEmpty()) {
                    for (GroupsEntity userGroup : logiedUserGroups) {
                        if (userGroup.getGroupId().intValue() == groupsEntity.getGroupId().intValue()) {
                            searchingValue.addGroup(groupsEntity.getGroupId());
                            exit = true;
                        }
                    }
                }
            }
            if (!exit) {
                // 権限の無いグループの情報を参照しようとしているので、検索できないようにする
                LOG.warn("Bad request group param");
                searchingValue.addTag(-123); //ありえないタグで検索するので、絶対に何もヒットしない
            }
            return searchKnowledge(searchingValue);
        }

        // 管理者じゃなければ自身が参加してる公開記事、自身の記事、グループの記事を条件に追加する
        if (!loginedUser.isAdmin()) {
            searchingValue.addUser(ALL_USER);
            searchingValue.addUser(loginedUser.getLoginUser().getUserId());

            List<GroupsEntity> logiedUserGroups = loginedUser.getGroups();
            if (logiedUserGroups != null && !logiedUserGroups.isEmpty()) {
                for (GroupsEntity groupsEntity : logiedUserGroups) {
                    searchingValue.addGroup(groupsEntity.getGroupId());
                }
            }
        }

        return searchKnowledge(searchingValue);
    }

    /**
     * ナレッジの検索
     * 
     * @param keyword
     * @param loginedUser
     * @param offset
     * @param limit
     * @return
     * @throws Exception
     */
    public List<KnowledgesEntity> searchKnowledge(String keyword, LoginedUser loginedUser, Integer offset, Integer limit) throws Exception {
        return searchKnowledge(keyword, null, null, null, null, loginedUser, offset, limit);
    }

    /**
     * ナレッジをタグ指定で表示
     * 
     * @param tag
     * @param loginedUser
     * @param offset
     * @param limit
     * @return
     * @throws Exception
     */
    public List<KnowledgesEntity> showKnowledgeOnTag(String tag, LoginedUser loginedUser, Integer offset, Integer limit) throws Exception {
        SearchingValue searchingValue = new SearchingValue();
        searchingValue.setOffset(offset);
        searchingValue.setLimit(limit);

        if (loginedUser != null && loginedUser.isAdmin()) {
            // 管理者の場合、ユーザのアクセス権を考慮しない
            LOG.trace("管理者の場合はアクセス権は考慮しません");
        } else {
            searchingValue.addUser(ALL_USER);
            if (loginedUser != null) {
                Integer userId = loginedUser.getLoginUser().getUserId();
                searchingValue.addUser(userId);

                List<GroupsEntity> groups = loginedUser.getGroups();
                if (groups != null && !groups.isEmpty()) {
                    for (GroupsEntity groupsEntity : groups) {
                        searchingValue.addGroup(groupsEntity.getGroupId());
                    }
                }
            }
        }

        if (StringUtils.isInteger(tag)) {
            searchingValue.addTag(new Integer(tag));
        }

        return searchKnowledge(searchingValue);
    }

    /**
     * ナレッジをグループ指定で表示
     *
     * @param group
     * @param loginedUser
     * @param offset
     * @param limit
     * @return
     * @throws Exception
     */
    public List<KnowledgesEntity> showKnowledgeOnGroup(String group, LoginedUser loginedUser, Integer offset, Integer limit) throws Exception {
        List<KnowledgesEntity> knowledges = new ArrayList<KnowledgesEntity>();
        if (loginedUser == null) {
            return knowledges;
        }

        SearchingValue searchingValue = new SearchingValue();
        searchingValue.setOffset(offset);
        searchingValue.setLimit(limit);

        GroupsEntity targetGroup = ExGroupsDao.get().selectOnKey(new Integer(group));

        if (loginedUser.isAdmin()) {
            searchingValue.addGroup(targetGroup.getGroupId());
            return searchKnowledge(searchingValue);
        }

        List<GroupsEntity> groups = loginedUser.getGroups();
        for (GroupsEntity groupsEntity : groups) {
            if (groupsEntity.getGroupId().intValue() == targetGroup.getGroupId().intValue()) {
                searchingValue.addGroup(targetGroup.getGroupId());
                return searchKnowledge(searchingValue);
            }
        }

        return knowledges;
    }

    /**
     * 指定ユーザのナレッジを取得
     * @param targetUser
     * @param loginedUser
     * @param offset
     * @param limit
     * @return
     * @throws Exception
     */
    public List<KnowledgesEntity> showKnowledgeOnUser(int targetUser, LoginedUser loginedUser, Integer offset, Integer limit) throws Exception {
        SearchingValue searchingValue = new SearchingValue();
        searchingValue.setOffset(offset);
        searchingValue.setLimit(limit);

        if (loginedUser != null && loginedUser.isAdmin()) {
            // 管理者の場合、ユーザのアクセス権を考慮しない
            LOG.trace("管理者の場合はアクセス権は考慮しません");
        } else {
            searchingValue.addUser(ALL_USER);
            if (loginedUser != null) {
                Integer userId = loginedUser.getLoginUser().getUserId();
                searchingValue.addUser(userId);

                List<GroupsEntity> groups = loginedUser.getGroups();
                if (groups != null && !groups.isEmpty()) {
                    for (GroupsEntity groupsEntity : groups) {
                        searchingValue.addGroup(groupsEntity.getGroupId());
                    }
                }
            }
        }
        searchingValue.addCreator(targetUser);

        return searchKnowledge(searchingValue);
    }

    /**
     * 全文検索エンジンの結果を元に、DBからデータを取得し、 さらにアクセス権のチェックなどを行う
     * 
     * @param list
     * @return
     */
    private List<KnowledgesEntity> getKnowledgeDatas(List<SearchResultValue> list) {
        KnowledgeFilesDao filesDao = KnowledgeFilesDao.get();
        List<Long> knowledgeIds = new ArrayList<Long>();
        // List<Long> fileIds = new ArrayList<>();
        for (SearchResultValue searchResultValue : list) {
            if (searchResultValue.getType() == TYPE_KNOWLEDGE) {
                knowledgeIds.add(new Long(searchResultValue.getId()));
                // } else if (searchResultValue.getType() == TYPE_FILE) {
                // LOG.trace("FILE!!! " + searchResultValue.getId());
                // String id = searchResultValue.getId().substring(FileParseBat.ID_PREFIX.length());
                // fileIds.add(new Long(id));
            }
        }
        LOG.trace("添付ファイル情報取得完了");

        List<KnowledgesEntity> dbs = knowledgesDao.selectKnowledges(knowledgeIds);
        Map<Long, KnowledgesEntity> map = new HashMap<Long, KnowledgesEntity>();
        for (KnowledgesEntity knowledgesEntity : dbs) {
            map.put(knowledgesEntity.getKnowledgeId(), knowledgesEntity);
        }
        LOG.trace("ナレッジ情報取得完了");

        List<KnowledgesEntity> knowledges = new ArrayList<>();
        for (SearchResultValue searchResultValue : list) {
            if (searchResultValue.getType() == TYPE_KNOWLEDGE) {
                Long key = new Long(searchResultValue.getId());
                if (map.containsKey(key)) {
                    KnowledgesEntity entity = map.get(key);
                    if (StringUtils.isNotEmpty(searchResultValue.getHighlightedTitle())) {
                        entity.setTitle(searchResultValue.getHighlightedTitle());
                    }
                    if (StringUtils.isNotEmpty(searchResultValue.getHighlightedContents())) {
                        entity.setContent(searchResultValue.getHighlightedContents());
                    } else {
                        String content = HtmlUtils.escapeHTML(entity.getContent());
                        entity.setContent(content);
                        // entity.setContent(org.apache.commons.lang.StringUtils.abbreviate(entity.getContent(),
                        // LuceneSearcher.CONTENTS_LIMIT_LENGTH));
                    }

                    entity.setScore(searchResultValue.getScore());
                    knowledges.add(entity);
                }
            } else if (searchResultValue.getType() == TYPE_FILE) {
                // TODO 1件づつ処理しているので、パフォーマンスが悪いので後で処理を検討
                String id = searchResultValue.getId().substring(FileParseBat.ID_PREFIX.length());
                Long fileNo = new Long(id);
                KnowledgeFilesEntity filesEntity = filesDao.selectOnKeyWithoutBinary(fileNo);
                if (filesEntity != null && filesEntity.getKnowledgeId() != null) {
                    KnowledgesEntity entity = knowledgesDao.selectOnKeyWithUserName(filesEntity.getKnowledgeId());
                    if (entity == null) {
                        // 添付ファイルの情報が検索エンジン内にあり、検索にHitしたが、それに紐づくナレッジデータは削除されていた
                        break;
                    }
                    entity.setTitle(entity.getTitle());

                    StringBuilder builder = new StringBuilder();
                    builder.append("[FILE] ");

                    if (StringUtils.isNotEmpty(searchResultValue.getHighlightedTitle())) {
                        builder.append(searchResultValue.getHighlightedTitle());
                    } else {
                        builder.append(filesEntity.getFileName());
                    }
                    builder.append("<br/>");
                    if (StringUtils.isNotEmpty(searchResultValue.getHighlightedContents())) {
                        builder.append(searchResultValue.getHighlightedContents());
                    }
                    entity.setContent(builder.toString());
                    entity.setScore(searchResultValue.getScore());
                    knowledges.add(entity);
                }
            } else if (searchResultValue.getType() == TYPE_COMMENT) {
                // TODO 1件づつ処理しているので、パフォーマンスが悪いので後で処理を検討
                String id = searchResultValue.getId().substring(COMMENT_ID_PREFIX.length());
                Long commentNo = new Long(id);
                CommentsEntity commentsEntity = CommentsDao.get().selectOnKey(commentNo);
                if (commentsEntity != null && commentsEntity.getKnowledgeId() != null) {
                    KnowledgesEntity entity = knowledgesDao.selectOnKeyWithUserName(commentsEntity.getKnowledgeId());
                    if (entity == null) {
                        // コメントの情報が検索エンジン内にあり、検索にHitしたが、それに紐づくナレッジデータは削除されていた
                        break;
                    }
                    entity.setTitle(entity.getTitle());

                    StringBuilder builder = new StringBuilder();
                    builder.append("[COMMENT] ");
                    if (StringUtils.isNotEmpty(searchResultValue.getHighlightedContents())) {
                        builder.append(searchResultValue.getHighlightedContents());
                    }
                    entity.setContent(builder.toString());
                    entity.setScore(searchResultValue.getScore());
                    knowledges.add(entity);
                }
            } else if (searchResultValue.getType() == IndexType.bookmarkContent.getValue()) {
                // TODO 1件づつ処理しているので、パフォーマンスが悪いので後で処理を検討
                String id = searchResultValue.getId().substring(FileParseBat.WEB_ID_PREFIX.length());
                Long knowledgeId = new Long(id);
                KnowledgesEntity entity = knowledgesDao.selectOnKeyWithUserName(knowledgeId);
                if (entity != null && entity.getKnowledgeId() != null) {
                    StringBuilder builder = new StringBuilder();
                    builder.append("[Bookmark Content] ");
                    if (StringUtils.isNotEmpty(searchResultValue.getHighlightedTitle())) {
                        builder.append(searchResultValue.getHighlightedTitle());
                    }
                    builder.append("<br/>");
                    if (StringUtils.isNotEmpty(searchResultValue.getHighlightedContents())) {
                        builder.append(searchResultValue.getHighlightedContents());
                    }
                    entity.setContent(builder.toString());
                    entity.setScore(searchResultValue.getScore());
                    knowledges.add(entity);
                }
            }
        }

        // 以下の付加情報は、ナレッジテーブルに持ち各テーブルに再取得しない
        // for (KnowledgesEntity entity : knowledges) {
        // // タグを取得（1件づつ処理するのでパフォーマンス悪いかも？）
        // setTags(entity);
        // // いいねの回数
        // setLikeCount(entity);
        // // コメント件数
        // setCommentsCount(entity);
        // }

        LOG.trace("ナレッジ１件づつに、付加情報をセット完了");

        return knowledges;
    }

    // /**
    // * コメントの件数を取得
    // * 再度SQLを実行するのでは無く、ナレッジ取得時にカウントもjoinして取得したほうが早い
    // *
    // * @param entity
    // */
    // private void setCommentsCount(KnowledgesEntity entity) {
    // CommentsDao commentsDao = CommentsDao.get();
    // Integer count = commentsDao.countOnKnowledgeId(entity.getKnowledgeId());
    // entity.setCommentCount(count);
    // }
    //
    // /**
    // * いいねの件数を取得
    // * 再度SQLを実行するのでは無く、ナレッジ取得時にカウントもjoinして取得したほうが早い
    // *
    // * @param entity
    // */
    // private void setLikeCount(KnowledgesEntity entity) {
    // LikesDao likesDao = LikesDao.get();
    // Long count = likesDao.countOnKnowledgeId(entity.getKnowledgeId());
    // entity.setLikeCount(count);
    // }

    /**
     * ナレッジの情報を取得 取得する際にタグ情報も取得
     */
    public KnowledgesEntity selectWithTags(Long knowledgeId, LoginedUser loginedUser) {
        KnowledgesEntity entity = select(knowledgeId, loginedUser);
        if (entity != null) {
            // タグをセット
            setTags(entity);
        }
        return entity;
    }

    /**
     * ナレッジを取得（アクセス権のあるもののみ）
     * 
     * @param knowledgeId
     * @param loginedUser
     * @return
     */
    public KnowledgesEntity select(Long knowledgeId, LoginedUser loginedUser) {
        KnowledgesDao dao = Container.getComp(KnowledgesDao.class);
        KnowledgesEntity entity = dao.selectOnKeyWithUserName(knowledgeId);
        if (entity == null) {
            return entity;
        }

        if (entity.getPublicFlag() == null || entity.getPublicFlag().intValue() == PUBLIC_FLAG_PUBLIC) {
            return entity;
        }
        Integer userId = Integer.MIN_VALUE;
        if (loginedUser != null) {
            userId = loginedUser.getLoginUser().getUserId();
        }
        if (entity.getInsertUser().intValue() == userId.intValue()) {
            // 作成者ならば、アクセス可能
            return entity;
        }
        if (loginedUser != null && loginedUser.isAdmin()) {
            // 管理者は全ての情報にアクセス可能
            return entity;
        }

        List<KnowledgeUsersEntity> usersEntities = knowledgeUsersDao.selectOnKnowledgeId(entity.getKnowledgeId());
        for (KnowledgeUsersEntity knowledgeUsersEntity : usersEntities) {
            if (knowledgeUsersEntity.getUserId().intValue() == userId.intValue()) {
                // アクセス権限が登録されていれば、取得
                return entity;
            }
        }
        if (loginedUser != null) {
            List<GroupsEntity> groups = loginedUser.getGroups();
            if (groups != null) {
                List<KnowledgeGroupsEntity> knowledgeGroups = KnowledgeGroupsDao.get().selectOnKnowledgeId(knowledgeId);
                for (KnowledgeGroupsEntity knowledgeGroupsEntity : knowledgeGroups) {
                    for (GroupsEntity groupsEntity : groups) {
                        if (groupsEntity.getGroupId().intValue() == knowledgeGroupsEntity.getGroupId().intValue()) {
                            // グループに登録があればアクセス可能
                            return entity;
                        }
                    }
                }
            }
        }
        // アクセス権がなかった
        return null;
    }

    /**
     * ナレッジのタグをセット
     * 
     * @param entity
     */
    private void setTags(KnowledgesEntity entity) {
        // タグを取得
        List<TagsEntity> tagsEntities = tagsDao.selectOnKnowledgeId(entity.getKnowledgeId());
        int count = 0;
        StringBuilder builder = new StringBuilder();
        for (TagsEntity tagsEntity : tagsEntities) {
            if (count > 0) {
                builder.append(",");
            }
            builder.append(tagsEntity.getTagName());
            count++;
        }
        entity.setTagNames(builder.toString());
    }

    /**
     * ナレッジを取得
     * 
     * @param ids
     * @param loginedUser
     * @return
     */
    public List<KnowledgesEntity> getKnowledges(List<String> ids, LoginedUser loginedUser) {
        List<Long> knowledgeIds = new ArrayList<>();
        for (String string : ids) {
            if (StringUtils.isLong(string)) {
                knowledgeIds.add(new Long(string));
            }
        }

        // List<KnowledgesEntity> knowledgesEntities = knowledgesDao.selectKnowledges(knowledgeIds);
        // アクセス権を考慮して取得
        List<KnowledgesEntity> knowledgesEntities = new ArrayList<>();
        List<String> addSuccess = new ArrayList<String>();
        List<String> addFail = new ArrayList<String>();
        for (Long integer : knowledgeIds) {
            KnowledgesEntity entity = select(integer, loginedUser);
            if (entity != null) {
                addSuccess.add(integer.toString());
                knowledgesEntities.add(entity);
            } else {
                addFail.add(integer.toString());
            }
        }
        if (addSuccess.isEmpty()) {
            LOG.debug("History: add success. [Empty]");
        } else {
            LOG.debug("History: add success. " + String.join(",", addSuccess.toArray(new String[0])));
        }

        if (addFail.isEmpty()) {
            LOG.debug("History: add fail. [Empty]");
        } else {
            LOG.debug("History: add fail. " + String.join(",", addFail.toArray(new String[0])));
        }

        return knowledgesEntities;
    }

    /**
     * ナレッジを削除
     * 
     * @param knowledgeId
     * @param loginedUser
     * @throws Exception
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Long knowledgeId) throws Exception {
        LOG.info("delete Knowledge: " + knowledgeId);
        // ナレッジ削除(通常のdeleteは、論理削除になる)
        knowledgesDao.delete(knowledgeId);

        // アクセス権削除
        knowledgeUsersDao.deleteOnKnowledgeId(knowledgeId);

        // タグを削除
        knowledgeTagsDao.deleteOnKnowledgeId(knowledgeId);

        // 添付ファイルを削除
        fileLogic.deleteOnKnowledgeId(knowledgeId);

        // コメント削除
        this.deleteCommentsOnKnowledgeId(knowledgeId);

        // ナレッジにアクセス可能なグループ削除
        GroupLogic.get().removeKnowledgeGroup(knowledgeId);

        // 編集権限を削除
        KnowledgeEditUsersDao editUsersDao = KnowledgeEditUsersDao.get();
        KnowledgeEditGroupsDao editGroupsDao = KnowledgeEditGroupsDao.get();
        editUsersDao.deleteOnKnowledgeId(knowledgeId);
        editGroupsDao.deleteOnKnowledgeId(knowledgeId);

        // 全文検索エンジンから削除
        IndexLogic indexLogic = IndexLogic.get();
        indexLogic.delete(knowledgeId);
        indexLogic.delete("WEB-" + knowledgeId);
        
        // ストック一覧から削除
        StockKnowledgesDao.get().deleteOnKnowledgeId(knowledgeId);
    }

    /**
     * ナレッジに紐づくコメントを削除
     * 
     * @param knowledgeId
     * @throws Exception
     */
    private void deleteCommentsOnKnowledgeId(Long knowledgeId) throws Exception {
        CommentsDao commentsDao = CommentsDao.get();
        List<CommentsEntity> comments = commentsDao.selectOnKnowledgeId(knowledgeId);
        if (comments != null) {
            for (CommentsEntity commentsEntity : comments) {
                deleteComment(commentsEntity);
            }
        }
    }

    /**
     * ユーザのナレッジを削除 TODO ものすごく多くのナレッジを登録したユーザの場合、それを全て削除するのは時間がかかるかも？ ただ、非同期で実施して、「そのうち消えます」と表示するのも気持ち悪いと感じられるので、 いったん同期処理で1件づつ消す（効率的な消し方を検討する）
     * 
     * @param loginUserId
     * @throws Exception
     */
    public void deleteOnUser(Integer loginUserId) throws Exception {
        // ユーザのナレッジを取得
        List<Long> knowledgeIds = knowledgesDao.selectOnUser(loginUserId);
        for (Long knowledgeId : knowledgeIds) {
            delete(knowledgeId);
        }
    }

    /**
     * 閲覧履歴を保持
     * 
     * @param knowledgeId
     * @param loginedUser
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void addViewHistory(Long knowledgeId, LoginedUser loginedUser) {
        ViewHistoriesDao historiesDao = ViewHistoriesDao.get();
        ViewHistoriesEntity historiesEntity = new ViewHistoriesEntity();
        historiesEntity.setKnowledgeId(knowledgeId);
        historiesEntity.setViewDateTime(new Timestamp(DateUtils.now().getTime()));
        if (loginedUser != null) {
            historiesEntity.setInsertUser(loginedUser.getUserId());
        } else {
            historiesEntity.setInsertUser(SystemConfig.SYSTEM_USER_ID);
        }
        historiesDao.insert(historiesEntity);
        
        KnowledgesEntity entity = KnowledgesDao.get().selectOnKey(knowledgeId);
        if (entity != null) {
            Long count = entity.getViewCount();
            if (count == null) {
                count = new Long(0);
            }
            count = count + 1;
            KnowledgesDao.get().updateViewCount(count, knowledgeId);
        }
    }

    /**
     * ナレッジテーブルの タグやイイネ件数、コメント件数などの付加情報を 更新する（一覧表示用）
     * 
     * @param knowledgeId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void updateKnowledgeExInfo(Long knowledgeId) {
        // 一覧表示用の情報を更新
        KnowledgesDao knowledgesDao = KnowledgesDao.get();
        KnowledgesEntity entity = knowledgesDao.selectOnKey(knowledgeId);
        updateKnowledgeExInfo(entity);
    }

    /**
     * ナレッジテーブルの タグやイイネ件数、コメント件数などの付加情報を 更新する（一覧表示用）
     * 
     * @param entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void updateKnowledgeExInfo(KnowledgesEntity entity) {
        // タグ情報
        TagsDao tagsDao = TagsDao.get();
        List<TagsEntity> tags = tagsDao.selectOnKnowledgeId(entity.getKnowledgeId());
        StringJoinBuilder ids = new StringJoinBuilder();
        StringJoinBuilder names = new StringJoinBuilder();
        for (TagsEntity tagsEntity : tags) {
            ids.append(tagsEntity.getTagId());
            names.append(tagsEntity.getTagName());
        }
        entity.setTagIds(ids.join(","));
        entity.setTagNames(names.join(","));
        // いいね件数
        LikesDao likesDao = LikesDao.get();
        Long likeCount = likesDao.countOnKnowledgeId(entity.getKnowledgeId());
        entity.setLikeCount(likeCount);
        // コメント件数
        CommentsDao commentsDao = CommentsDao.get();
        Integer commentCount = commentsDao.countOnKnowledgeId(entity.getKnowledgeId());
        entity.setCommentCount(commentCount);

        // 更新
        KnowledgesDao knowledgesDao = KnowledgesDao.get();
        knowledgesDao.physicalUpdate(entity);
    }

    /**
     * コメント保存
     * 
     * @param knowledgeId
     * @param comment
     * @param fileNos
     * @throws Exception
     */
    public CommentsEntity saveComment(Long knowledgeId, String comment, List<Long> fileNos, LoginedUser loginedUser) throws Exception {
        CommentsDao commentsDao = CommentsDao.get();
        CommentsEntity commentsEntity = new CommentsEntity();
        commentsEntity.setKnowledgeId(knowledgeId);
        commentsEntity.setComment(comment);
        commentsEntity = commentsDao.insert(commentsEntity);
        // 一覧表示用の情報を更新
        KnowledgeLogic.get().updateKnowledgeExInfo(knowledgeId);

        // 検索エンジンに追加
        addIndexOnComment(commentsEntity);

        // 添付ファイルを更新（紐付けをセット）
        fileLogic.setKnowledgeFiles(knowledgeId, fileNos, loginedUser, commentsEntity.getCommentNo());

        // 通知
        NotifyLogic.get().notifyOnKnowledgeComment(knowledgeId, commentsEntity);
        
        //ポイント
        ActivityLogic.get().processActivity(Activity.COMMENT_INSERT, loginedUser, DateUtils.now(), commentsEntity);
        
        return commentsEntity;
    }

    /**
     * コメント更新
     * 
     * @param commentsEntity
     * @param fileNos
     * @param loginedUser
     * @throws Exception
     */
    public void updateComment(CommentsEntity commentsEntity, List<Long> fileNos, LoginedUser loginedUser) throws Exception {
        CommentsDao commentsDao = CommentsDao.get();
        CommentsEntity updatedCommentsEntity = commentsDao.update(commentsEntity);
        // 一覧表示用の情報を更新
        KnowledgeLogic.get().updateKnowledgeExInfo(updatedCommentsEntity.getKnowledgeId());

        // 検索エンジンに追加
        addIndexOnComment(updatedCommentsEntity);

        // 添付ファイルを更新（紐付けをセット）
        fileLogic.setKnowledgeFiles(updatedCommentsEntity.getKnowledgeId(), fileNos, loginedUser, updatedCommentsEntity.getCommentNo());
    }

    /**
     * コメント削除
     * 
     * @param commentsEntity
     * @param loginedUser
     * @throws Exception
     */
    public void deleteComment(CommentsEntity commentsEntity) throws Exception {
        CommentsDao commentsDao = CommentsDao.get();
        commentsDao.delete(commentsEntity);
        // 検索エンジンから削除
        IndexLogic indexLogic = IndexLogic.get();
        indexLogic.delete(COMMENT_ID_PREFIX + String.valueOf(commentsEntity.getCommentNo()));
    }

    /**
     * コメント削除
     * 
     * @param commentsEntity
     * @param loginedUser
     * @throws Exception
     */
    public void deleteComment(CommentsEntity commentsEntity, LoginedUser loginedUser) throws Exception {
        deleteComment(commentsEntity);
        // 一覧表示用の情報を更新
        KnowledgeLogic.get().updateKnowledgeExInfo(commentsEntity.getKnowledgeId());
        // 添付ファイルを更新（紐付けをセット）
        fileLogic.setKnowledgeFiles(commentsEntity.getKnowledgeId(), new ArrayList<Long>(), loginedUser, commentsEntity.getCommentNo());
    }

    /**
     * コメントを全文検索エンジンへ登録
     * 
     * @param commentsEntity
     * @throws Exception
     */
    public void addIndexOnComment(CommentsEntity commentsEntity) throws Exception {
        KnowledgesDao knowledgesDao = KnowledgesDao.get();
        KnowledgesEntity entity = knowledgesDao.selectOnKey(commentsEntity.getKnowledgeId());

        IndexingValue indexingValue = new IndexingValue();
        indexingValue.setType(TYPE_COMMENT);
        indexingValue.setTemplate(entity.getTypeId());
        indexingValue.setId(COMMENT_ID_PREFIX + String.valueOf(commentsEntity.getCommentNo()));
        indexingValue.setTitle("");
        indexingValue.setContents(commentsEntity.getComment());
        indexingValue.addUser(entity.getInsertUser());
        if (entity.getPublicFlag() == null || PUBLIC_FLAG_PUBLIC == entity.getPublicFlag()) {
            indexingValue.addUser(ALL_USER);
        }

        List<TagsEntity> tags = TagsDao.get().selectOnKnowledgeId(commentsEntity.getKnowledgeId());
        if (tags != null) {
            for (TagsEntity tagsEntity : tags) {
                indexingValue.addTag(tagsEntity.getTagId());
            }
        }
        if (entity.getPublicFlag() != null && entity.getPublicFlag().intValue() == PUBLIC_FLAG_PROTECT) {
            TargetLogic targetLogic = TargetLogic.get();
            List<LabelValue> targets = targetLogic.selectTargetsOnKnowledgeId(commentsEntity.getKnowledgeId());
            if (targets != null) {
                for (LabelValue target : targets) {
                    Integer id = TargetLogic.get().getGroupId(target.getValue());
                    if (id != Integer.MIN_VALUE) {
                        indexingValue.addGroup(id);
                    }
                    id = TargetLogic.get().getUserId(target.getValue());
                    if (id != Integer.MIN_VALUE) {
                        indexingValue.addUser(id);
                    }
                }
            }
        }
        indexingValue.setCreator(commentsEntity.getInsertUser());
        indexingValue.setTime(commentsEntity.getUpdateDatetime().getTime()); // 更新日時をセットするので、更新日時でソート

        IndexLogic.get().save(indexingValue); // 全文検索のエンジンにも保存（DBに保存する意味ないかも）
    }
    
    /**
     * Re indexing
     * @param knowledgesEntity
     * @throws Exception
     */
    public void reindexing(KnowledgesEntity knowledgesEntity) throws Exception {
        // ナレッジの情報を検索エンジンへ更新
        List<TagsEntity> tags = tagsDao.selectOnKnowledgeId(knowledgesEntity.getKnowledgeId());
        List<LabelValue> targets = TargetLogic.get().selectTargetsOnKnowledgeId(knowledgesEntity.getKnowledgeId());

        // 拡張値を取得
        TemplateMastersEntity template = TemplateMastersDao.get().selectWithItems(knowledgesEntity.getTypeId());
        if (template != null) {
            List<TemplateItemsEntity> items = template.getItems();
            List<KnowledgeItemValuesEntity> values = KnowledgeItemValuesDao.get().selectOnKnowledgeId(knowledgesEntity.getKnowledgeId());
            for (TemplateItemsEntity item : items) {
                for (KnowledgeItemValuesEntity val : values) {
                    if (item.getItemNo().equals(val.getItemNo())) {
                        item.setItemValue(val.getItemValue());
                    }
                }
            }
        }
        // インデックス更新
        saveIndex(knowledgesEntity, tags, targets, template, knowledgesEntity.getInsertUser());

        // コメントを検索エンジンへ
        List<CommentsEntity> comments = CommentsDao.get().selectOnKnowledgeId(knowledgesEntity.getKnowledgeId());
        for (CommentsEntity commentsEntity : comments) {
            addIndexOnComment(commentsEntity);
        }

        // 添付ファイルを検索エンジンへ
        KnowledgeFilesDao filesDao = KnowledgeFilesDao.get();
        List<KnowledgeFilesEntity> filesEntities = filesDao.selectOnKnowledgeId(knowledgesEntity.getKnowledgeId());
        for (KnowledgeFilesEntity knowledgeFilesEntity : filesEntities) {
            // 添付ファイルのパースは、パースバッチに任せる（ステータスをパース待ちにしておけばバッチが処理する）
            filesDao.changeStatus(knowledgeFilesEntity.getFileNo(), FileParseBat.PARSE_STATUS_WAIT, FileParseBat.UPDATE_USER_ID);
        }
    }
    
    /**
     * ナレッジに対し編集権限があるかチェック
     * @param loginedUser
     * @param knowledgeId
     * @return
     */
    public boolean isEditor(LoginedUser loginedUser, Long knowledgeId) {
        if (loginedUser.isAdmin()) {
            return true;
        }
        KnowledgesEntity check = KnowledgesDao.get().selectOnKey(knowledgeId);
        if (check == null) {
            return false;
        }
        List<LabelValue> editors = TargetLogic.get().selectEditorsOnKnowledgeId(knowledgeId);
        return isEditor(loginedUser, check, editors);
    }
    
    
    /**
     * ナレッジに対し編集権限があるかチェック
     * 
     * @param loginedUser
     * @param entity
     * @param editors
     * @return
     */
    public boolean isEditor(LoginedUser loginedUser, KnowledgesEntity entity, List<LabelValue> editors) {
        if (loginedUser == null) {
            // ログインしていないユーザに編集権限は無し
            return false;
        }
        if (loginedUser.isAdmin()) {
            return true;
        } else {
            if (entity != null) {
                if (entity.getInsertUser().intValue() == loginedUser.getUserId().intValue()) {
                    return true;
                }
            }
            if (editors != null) {
                for (LabelValue labelValue : editors) {
                    Integer id = TargetLogic.get().getGroupId(labelValue.getValue());
                    if (id != Integer.MIN_VALUE) {
                        List<GroupsEntity> groups = loginedUser.getGroups();
                        if (groups != null) {
                            for (GroupsEntity groupsEntity : groups) {
                                if (groupsEntity.getGroupId().intValue() == id.intValue()) {
                                    return true;
                                }
                            }
                        }
                    } else {
                        id = TargetLogic.get().getUserId(labelValue.getValue());
                        if (id != Integer.MIN_VALUE) {
                            if (id.intValue() == loginedUser.getUserId().intValue()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 一定期間で人気の高い記事を並べる
     * 
     * 
     * @param loginedUser
     * @return
     */
    public List<KnowledgesEntity> getPopularityKnowledges(LoginedUser loginedUser, int offset, int limit) {
        long now = DateUtils.now().getTime();
        LOG.trace(now);

        long term = 1000L * 60L * 60L * 24L * 5L; // 5日間の中でCPの合計が高いものにする
        LOG.trace(term);
        long s = now - term;
        LOG.trace(s);
        Timestamp start = new Timestamp(s);
        LOG.trace(start.getTime());
        LOG.trace(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(start));

        long e = now + (1000L * 60L * 60L * 24L * 1L);
        LOG.trace(e);
        Timestamp end = new Timestamp(e);
        LOG.trace(end.getTime());
        LOG.trace(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(end));

        if (loginedUser != null && loginedUser.isAdmin()) {
            return KnowledgesDao.get().selectPopularity(start, end, offset, limit);
        }

        return KnowledgesDao.get().selectPopularityWithAccessControl(loginedUser, start, end, offset, limit);
    }

    /**
     * ユーザがストックしたナレッジを取得
     * @param loginedUser
     * @param offset
     * @param limit
     * @param stockid
     * @return
     */
    public List<KnowledgesEntity> getStocks(LoginedUser loginedUser, int offset, int limit, Long stockid) {
        return KnowledgesDao.get().selectStocks(loginedUser, offset, limit, stockid);
    }
    /**
     * ナレッジ一覧に格納しているストックの情報を付与する
     * @param knowledges
     * @param loginedUser
     * @return
     */
    public List<StockKnowledge> setStockInfo(List<KnowledgesEntity> knowledges, LoginedUser loginedUser) {
        List<StockKnowledge> list = new ArrayList<>();
        //N+1問題になるので、もっと良い方法は無いか検討
        for (KnowledgesEntity knowledgesEntity : knowledges) {
            StockKnowledge stock = new StockKnowledge();
            PropertyUtil.copyPropertyValue(knowledgesEntity, stock);
            list.add(stock);
            //ストック情報を取得
            List<StocksEntity> stocks = StocksDao.get().selectStockOnKnowledge(knowledgesEntity, loginedUser);
            stock.setStocks(stocks);
        }
        return list;
    }

    /**
     * 下書き保存
     * @param draft
     * @param template
     * @param files 
     * @param loginedUser
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public DraftKnowledgesEntity draft(DraftKnowledgesEntity draft, TemplateMastersEntity template, String[] files, LoginedUser loginedUser) {
        if (draft.getKnowledgeId() != null && draft.getKnowledgeId() > 0) {
            // 権限チェック
            KnowledgesEntity knowledge = KnowledgesDao.get().selectOnKey(draft.getKnowledgeId());
            String editorsstr = draft.getEditors();
            String[] editordids = editorsstr.split(",");
            List<LabelValue> editors = TargetLogic.get().selectTargets(editordids);
            if (!isEditor(loginedUser, knowledge, editors)) {
                throw new AuthenticateException("errors.noauthority");
            }
        }
        if (draft.getDraftId() == null || draft.getDraftId() <= 0) {
            DraftKnowledgesDao.get().insert(draft);
        } else {
            DraftKnowledgesEntity saved = DraftKnowledgesDao.get().selectOnKey(draft.getDraftId());
            if (saved == null) {
                // 既に別の画面で削除済
                DraftKnowledgesDao.get().insert(draft);
            } else {
                draft.setInsertUser(saved.getInsertUser());
                draft.setInsertDatetime(saved.getInsertDatetime());
                DraftKnowledgesDao.get().update(draft);
                DraftItemValuesDao.get().deleteOnDraftId(draft.getDraftId());
            }
        }
        if (template != null && template.getItems() != null) {
            for (TemplateItemsEntity item : template.getItems()) {
                DraftItemValuesEntity val = new DraftItemValuesEntity();
                val.setDraftId(draft.getDraftId());
                val.setTypeId(template.getTypeId());
                val.setItemNo(item.getItemNo());
                val.setItemValue(item.getItemValue());
                val.setItemStatus(KnowledgeItemValuesEntity.STATUS_SAVED);
                DraftItemValuesDao.get().save(val);
            }
        }
        // 添付ファイルと下書きを紐付ける(紐付けを作るのみ、既存のKnowledgeの添付ファイルを削除はしない）
        // 削除する場合は、「投稿する」を実施すること（制御を簡単にするため）
        if (files != null) {
            for (String string : files) {
                if (StringUtils.isLong(string)) {
                    Long fileNo = new Long(string);
                    KnowledgeFilesEntity entity = KnowledgeFilesDao.get().selectOnKeyWithoutBinary(fileNo);
                    if (entity != null) {
                        if (entity.getKnowledgeId() == null || entity.getKnowledgeId().longValue() == 0) {
                            // 既にKnowledgeに紐付いているものは対象外
                            entity.setDraftId(draft.getDraftId());
                            entity.setKnowledgeId(null);
                            KnowledgeFilesDao.get().updateDraftId(entity, loginedUser.getUserId());
                        }
                    }
                }
            }
        }
        return draft;
    }
    
    
    /**
     * 下書きを削除
     * @param draftId
     * @param loginedUser
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void removeDraft(Long draftId, LoginedUser loginedUser) {
        DraftKnowledgesEntity draft = DraftKnowledgesDao.get().selectOnKey(draftId);
        if (draft == null) {
            // 既に削除済
            return;
        }
        if (draft.getInsertUser().intValue() != loginedUser.getUserId().intValue()) {
            // 登録していない下書きを消そうとしているんで、何もしない
            // TODO エラーにするべき？
            return;
        }
        DraftKnowledgesDao.get().physicalDelete(draft);
        DraftItemValuesDao.get().deleteOnDraftId(draftId);
    }

    
    /**
     * アクセス可能な記事を、IDの前方一致で取得
     * @param q
     * @param loginedUser
     * @return
     */
    public List<KnowledgesEntity> selectAccessAbleKnowledge(String q, LoginedUser loginedUser) {
        int limit = 5;
        int offset = 0;
        if (loginedUser.isAdmin()) {
            return KnowledgesDao.get().selectKnowledgeOnIdPrefix(q, limit, offset);
        }
        return KnowledgesDao.get().selectAccessAbleKnowledgeOnIdPrefix(q, loginedUser, limit, offset);
    }
    
    /**
     * 参照済かどうかをセットする
     * @param stocks
     * @param loginedUser
     */
    public void setViewed(List<StockKnowledge> stocks, LoginedUser loginedUser) {
        if (stocks == null) {
            return;
        }
        if (loginedUser == null) {
            // 未ログインユーザは、未読かんりしないので、全て既読にする
            for (StockKnowledge knowledge : stocks) {
                knowledge.setViewed(true);
            }
            return;
        }
        List<Long> knowledgeIds = ViewHistoriesDao.get().selectViewdKnowledgeIds(stocks, loginedUser.getUserId());
        for (StockKnowledge knowledge : stocks) {
            if (knowledgeIds.contains(knowledge.getKnowledgeId())) {
                knowledge.setViewed(true);
            }
        }
    }

}
