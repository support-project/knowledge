package org.support.project.knowledge.logic;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.support.project.common.exception.ParseException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.PropertyUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.CommentsDao;
import org.support.project.knowledge.dao.KnowledgeFilesDao;
import org.support.project.knowledge.dao.KnowledgeItemValuesDao;
import org.support.project.knowledge.dao.StocksDao;
import org.support.project.knowledge.dao.TargetsDao;
import org.support.project.knowledge.dao.TemplateItemsDao;
import org.support.project.knowledge.dao.TemplateMastersDao;
import org.support.project.knowledge.dao.ViewHistoriesDao;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.ItemChoicesEntity;
import org.support.project.knowledge.entity.KnowledgeFilesEntity;
import org.support.project.knowledge.entity.KnowledgeItemValuesEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.StocksEntity;
import org.support.project.knowledge.entity.TemplateItemsEntity;
import org.support.project.knowledge.entity.TemplateMastersEntity;
import org.support.project.knowledge.vo.KnowledgeDataInterface;
import org.support.project.knowledge.vo.KnowledgeKeyInterface;
import org.support.project.knowledge.vo.MarkDown;
import org.support.project.knowledge.vo.SearchKnowledgeParam;
import org.support.project.knowledge.vo.api.AttachedFile;
import org.support.project.knowledge.vo.api.Choice;
import org.support.project.knowledge.vo.api.Comment;
import org.support.project.knowledge.vo.api.Item;
import org.support.project.knowledge.vo.api.Knowledge;
import org.support.project.knowledge.vo.api.KnowledgeDetail;
import org.support.project.knowledge.vo.api.Target;
import org.support.project.knowledge.vo.api.Type;
import org.support.project.knowledge.vo.api.internal.KnowledgeList;
import org.support.project.web.bean.LabelValue;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.bean.NameId;
import org.support.project.web.entity.GroupsEntity;
import org.support.project.web.entity.UsersEntity;

@DI(instance = Instance.Singleton)
public class KnowledgeDataSelectLogic {
    /** LOG */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    /** Get instance */
    public static KnowledgeDataSelectLogic get() {
        return Container.getComp(KnowledgeDataSelectLogic.class);
    }
    
    private static final int SINGLE = 0;
    private static final int LIST = 1;
    
    
    /**
     * KnowledgesEntity を WebAPIで送る情報に変換する
     * @param entity
     * @return
     */
    private Knowledge conv(KnowledgeDataInterface entity, int type, Map<Integer, TemplateMastersEntity> typeMap) {
        if (entity == null) {
            return null;
        }
        Knowledge result = new Knowledge();
        PropertyUtil.copyPropertyValue(entity, result);
        
        // テンプレートの情報を追加
        TemplateMastersEntity template;
        if (typeMap.containsKey(entity.getTypeId())) {
            template = typeMap.get(entity.getTypeId());
        } else {
            template = typeMap.get(TemplateLogic.TYPE_ID_KNOWLEDGE);
        }
        result.setType(convType(template));
        
        // タグ
        if (StringUtils.isNotEmpty(entity.getTagNames())) {
            String[] tags = entity.getTagNames().split(",");
            result.setTags(Arrays.asList(tags));
        } else {
            result.setTags(new ArrayList<>());
        }
        
        // 公開範囲
        Target viewers = getViewers(entity);
        result.setViewers(viewers);
        
        // 1件取得の場合は詳細な情報を取得する
        if (SINGLE == type) {
            KnowledgeDetail detail = new KnowledgeDetail();
            PropertyUtil.copyPropertyValue(result, detail);
            
            // テンプレートで拡張した項目の値を取得
            List<LabelValue> templateItems = getTemplateItems(entity, template);
            detail.setItems(templateItems);
            
            // コメント
            List<Comment> comments = getComments(entity);
            detail.setComments(comments);
            
            // 添付ファイル
            List<AttachedFile> attachedFiles = getAttachedFiles(entity);
            detail.setAttachments(attachedFiles);
            
            // 共同編集者
            Target editors = getEditors(entity);
            detail.setEditors(editors);
            
            
            return detail;
        }
        return result;
    }
    /**
     * 記事の種類の情報をAPIで返す形に変換
     * @param template
     * @return
     */
    public Type convType(TemplateMastersEntity template) {
        Type type = new Type();
        type.setId(template.getTypeId());
        type.setName(template.getTypeName());
        type.setIcon(template.getTypeIcon());
        type.setDescription(template.getDescription());
        
        if (template.getItems() != null) {
            for (TemplateItemsEntity i : template.getItems()) {
                Item item = new Item();
                PropertyUtil.copyPropertyValue(i, item);
                type.getItems().add(item);
                if (i.getChoices() != null) {
                    List<Choice> choices = new ArrayList<>();
                    for (ItemChoicesEntity c : i.getChoices()) {
                        Choice choice = new Choice();
                        PropertyUtil.copyPropertyValue(c, choice);
                        choices.add(choice);
                    }
                    item.setChoices(choices);
                }
            }
        }
        return type;
    }
    /**
     * 添付ファイルを取得
     * @param entity
     * @return
     */
    private List<AttachedFile> getAttachedFiles(KnowledgeDataInterface entity) {
        List<AttachedFile> attachedFiles = new ArrayList<>();
        List<KnowledgeFilesEntity> filesEntities = KnowledgeFilesDao.get().selectOnKnowledgeId(entity.getKnowledgeId());
        for (KnowledgeFilesEntity knowledgeFilesEntity : filesEntities) {
            AttachedFile file = new AttachedFile();
            PropertyUtil.copyPropertyValue(knowledgeFilesEntity, file);
            attachedFiles.add(file);
        }
        return attachedFiles;
    }
    /**
     * コメント取得
     * @param entity
     * @return
     */
    private List<Comment> getComments(KnowledgeDataInterface entity) {
        List<Comment> comments = new ArrayList<>();
        List<CommentsEntity> commentsEntities = CommentsDao.get().selectOnKnowledgeId(entity.getKnowledgeId());
        for (CommentsEntity commentsEntity : commentsEntities) {
            Comment comment = new Comment();
            PropertyUtil.copyPropertyValue(commentsEntity, comment);
            comments.add(comment);
        }
        return comments;
    }
    /**
     * テンプレートの項目とその値を取得
     * @param entity
     * @param template
     * @return
     */
    private List<LabelValue> getTemplateItems(KnowledgeDataInterface entity, TemplateMastersEntity template) {
        List<TemplateItemsEntity> items = TemplateItemsDao.get().selectOnTypeId(template.getTypeId());
        List<KnowledgeItemValuesEntity> values = KnowledgeItemValuesDao.get().selectOnKnowledgeId(entity.getKnowledgeId());
        List<LabelValue> templateItems = new ArrayList<>();
        for (KnowledgeItemValuesEntity val : values) {
            for (TemplateItemsEntity item : items) {
                if (val.getItemNo().equals(item.getItemNo())) {
                    item.setItemValue(val.getItemValue());
                    templateItems.add(new LabelValue(
                            item.getItemName(),
                            val.getItemValue()));
                    break;
                }
            }
        }
        return templateItems;
    }
    /**
     * 共同編集者を取得
     * @param entity
     * @return
     */
    private Target getEditors(KnowledgeDataInterface entity) {
        Target editors = new Target();
        List<NameId> listGroups = new ArrayList<>();
        List<NameId> listUsers = new ArrayList<>();
        editors.setGroups(listGroups);
        editors.setUsers(listUsers);
        TargetsDao targetsDao = TargetsDao.get();
        List<GroupsEntity> groups = targetsDao.selectEditorGroupsOnKnowledgeId(entity.getKnowledgeId());
        for (GroupsEntity groupsEntity : groups) {
            NameId group = new NameId(groupsEntity.getGroupName(), String.valueOf(groupsEntity.getGroupId()));
            listGroups.add(group);
        }
        List<UsersEntity> users = targetsDao.selectEditorUsersOnKnowledgeId(entity.getKnowledgeId());
        for (UsersEntity usersEntity : users) {
            NameId user = new NameId(usersEntity.getUserName(), String.valueOf(usersEntity.getUserId()));
            listUsers.add(user);
        }
        return editors;
    }
    /**
     * 公開対象を取得
     * @param entity
     * @return
     */
    private Target getViewers(KnowledgeDataInterface entity) {
        Target viewers = new Target();
        if (entity.getPublicFlag().intValue() == KnowledgeLogic.PUBLIC_FLAG_PROTECT) {
            List<NameId> groupViewers = new ArrayList<>();
            List<NameId> userViewers = new ArrayList<>();
            viewers.setGroups(groupViewers);
            viewers.setUsers(userViewers);
            TargetsDao targetsDao = TargetsDao.get();
            List<GroupsEntity> groups = targetsDao.selectGroupsOnKnowledgeId(entity.getKnowledgeId());
            for (GroupsEntity groupsEntity : groups) {
                NameId group = new NameId(groupsEntity.getGroupName(), String.valueOf(groupsEntity.getGroupId()));
                groupViewers.add(group);
            }
            List<UsersEntity> users = targetsDao.selectUsersOnKnowledgeId(entity.getKnowledgeId());
            for (UsersEntity usersEntity : users) {
                NameId user = new NameId(usersEntity.getUserName(), String.valueOf(usersEntity.getUserId()));
                userViewers.add(user);
            }
        }
        return viewers;
    }
    
    /**
     * Knowledgeのデータを1件取得（WebAPIで返す形で）
     * @param knowledgeId
     * @param loginedUser
     * @param parseMarkdown 
     * @param sanitize 
     * @return
     * @throws ParseException 
     */
    public Knowledge select(long knowledgeId, LoginedUser loginedUser, boolean parseMarkdown, boolean sanitize) throws ParseException {
        KnowledgesEntity entity = KnowledgeLogic.get().selectWithTags(knowledgeId, loginedUser);
        return conv(entity, loginedUser, parseMarkdown, sanitize);
    }
    
    /**
     * Knowledgeのデータを1件取得（WebAPIで返す形で）
     * @param knowledgeId
     * @param loginedUser
     * @param parseMarkdown 
     * @param sanitize 
     * @return
     * @throws ParseException 
     */
    public Knowledge conv(KnowledgeDataInterface entity, LoginedUser loginedUser, boolean parseMarkdown, boolean sanitize) throws ParseException {
        if (entity == null) {
            return null;
        }
        // 記事のマスタを読み込み
        Map<Integer, TemplateMastersEntity> typeMap = getTypeMap();
        Knowledge result = conv(entity, SINGLE, typeMap);
        
        if (parseMarkdown) {
            LOG.warn("Parse Markdown on server side is deprecated.");
            MarkDown markdown = MarkdownLogic.get().markdownToHtml(result.getContent()); // Markdownのパースの中でサニタイズも実施
            result.setDisplaySafeHtml(markdown.getHtml());
        }
        if (sanitize) {
            // content(生データ)のサニタイズ
            // コードブロックのタグは全て置換し、Markdown本文内のタグは、危険なものを消す
            result.setContent(SanitizeMarkdownTextLogic.get().sanitize(result.getContent()));
        }
        return result;
    }    
    
    /**
     * Knowledgeのデータを取得（WebAPIで返す形で）
     * @param param
     * @return
     * @throws Exception
     */
    public List<Knowledge> selectList(SearchKnowledgeParam param) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("get knowledge list. [params] " + PropertyUtil.reflectionToString(param));
        }
        // 記事のマスタを読み込み
        Map<Integer, TemplateMastersEntity> typeMap = getTypeMap();
        
        // 記事検索
        List<Knowledge> results = new ArrayList<>();
        List<KnowledgesEntity> entities = KnowledgeLogic.get().searchKnowledge(
                param.getKeyword(),
                param.getTags(),
                param.getGroups(),
                param.getCreators(),
                param.getTemplates(),
                param.getLoginedUser(),
                param.getOffset(),
                param.getLimit());
        List<String> ids = new ArrayList<>();
        for (KnowledgesEntity entity : entities) {
            Knowledge result = conv(entity, LIST, typeMap);
            results.add(result);
            ids.add(String.valueOf(result.getKnowledgeId()));
        }
        List<KnowledgesEntity> dbs = KnowledgeLogic.get().getKnowledges(ids, param.getLoginedUser());
        Map<Long, KnowledgesEntity> idMap = new HashedMap<>();
        for (KnowledgesEntity knowledgesEntity : dbs) {
            idMap.put(knowledgesEntity.getKnowledgeId(), knowledgesEntity);
        }
        
        // TODO 以下は、パブリックAPIのみで実施するように後で変更すること(このメソッドは共通処理）
        for (Knowledge knowledge : results) {
            if (idMap.containsKey(knowledge.getKnowledgeId())) {
                // Titleのハイライトを消す
                knowledge.setTitle(idMap.get(knowledge.getKnowledgeId()).getTitle());
            }
        }
        
        return results;
    }
    private Map<Integer, TemplateMastersEntity> getTypeMap() {
        // 記事の種類の情報取得
        List<TemplateMastersEntity> template = TemplateMastersDao.get().selectAll();
        Map<Integer, TemplateMastersEntity> typeMap = new HashMap<>();
        for (TemplateMastersEntity templateMastersEntity : template) {
            typeMap.put(templateMastersEntity.getTypeId(), templateMastersEntity);
        }
        return typeMap;
    }
    
    public List<KnowledgeList> convInternalList(List<Knowledge> results, LoginedUser loginedUser) {
        List<KnowledgeList> list = new ArrayList<>();
        List<Long> knowledgeIds = null;
        if (loginedUser != null) {
            knowledgeIds = ViewHistoriesDao.get().selectViewdKnowledgeIds(results, loginedUser.getUserId());
        }
        //N+1問題になるので、もっと良い方法は無いか検討
        for (KnowledgeKeyInterface knowledge : results) {
            KnowledgeList v = new KnowledgeList();
            PropertyUtil.copyPropertyValue(knowledge, v);
            list.add(v);
            //ストック情報を取得
            List<StocksEntity> stocks = StocksDao.get().selectStockOnKnowledge(knowledge.getKnowledgeId(), loginedUser);
            v.setStocks(stocks);
            // 参照の状態
            if (loginedUser == null) {
                // 未ログインユーザは、未読かんりしないので、全て既読にする
                v.setViewed(true);
            } else if (knowledgeIds.contains(knowledge.getKnowledgeId())) {
                v.setViewed(true);
            }
        }
        return list;
    }

}
