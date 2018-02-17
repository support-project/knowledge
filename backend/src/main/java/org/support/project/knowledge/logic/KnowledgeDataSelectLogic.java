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
import org.support.project.knowledge.dao.DraftKnowledgesDao;
import org.support.project.knowledge.dao.KnowledgeFilesDao;
import org.support.project.knowledge.dao.KnowledgeItemValuesDao;
import org.support.project.knowledge.dao.StocksDao;
import org.support.project.knowledge.dao.TargetsDao;
import org.support.project.knowledge.dao.TemplateItemsDao;
import org.support.project.knowledge.dao.TemplateMastersDao;
import org.support.project.knowledge.dao.ViewHistoriesDao;
import org.support.project.knowledge.entity.DraftKnowledgesEntity;
import org.support.project.knowledge.entity.ItemChoicesEntity;
import org.support.project.knowledge.entity.KnowledgeFilesEntity;
import org.support.project.knowledge.entity.KnowledgeItemValuesEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.StocksEntity;
import org.support.project.knowledge.entity.TemplateItemsEntity;
import org.support.project.knowledge.entity.TemplateMastersEntity;
import org.support.project.knowledge.vo.ArticleDataInterface;
import org.support.project.knowledge.vo.ArticleKeyInterface;
import org.support.project.knowledge.vo.MarkDown;
import org.support.project.knowledge.vo.SearchKnowledgeParam;
import org.support.project.knowledge.vo.SearchResultArticle;
import org.support.project.knowledge.vo.SearchResultKnowledge;
import org.support.project.knowledge.vo.api.AttachedFile;
import org.support.project.knowledge.vo.api.Choice;
import org.support.project.knowledge.vo.api.Comment;
import org.support.project.knowledge.vo.api.Item;
import org.support.project.knowledge.vo.api.Knowledge;
import org.support.project.knowledge.vo.api.KnowledgeDetail;
import org.support.project.knowledge.vo.api.KnowledgeDetailDraft;
import org.support.project.knowledge.vo.api.Target;
import org.support.project.knowledge.vo.api.Targets;
import org.support.project.knowledge.vo.api.Type;
import org.support.project.knowledge.vo.api.internal.KnowledgeList;
import org.support.project.web.bean.AccessUser;
import org.support.project.web.bean.LabelValue;
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
    
    protected static final int SINGLE = 0;
    protected static final int LIST = 1;
    
    
    /**
     * KnowledgesEntity を WebAPIで送る情報に変換する
     * @param entity
     * @param loginedUser 
     * @param checkDraft 
     * @return
     * @throws ParseException 
     */
    private Knowledge conv(ArticleDataInterface entity, int type, Map<Integer, TemplateMastersEntity> typeMap, AccessUser loginedUser) throws ParseException {
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
        Targets viewers = getViewers(entity);
        result.setViewers(viewers);
        
        // 1件取得の場合は詳細な情報を取得する
        if (SINGLE == type) {
            KnowledgeDetail detail = new KnowledgeDetail();
            PropertyUtil.copyPropertyValue(result, detail);
            
            // テンプレートで拡張した項目の値を取得
            List<LabelValue> templateItems = getTemplateItems(entity, template);
            detail.setItems(templateItems);
            
            // コメント
            List<Comment> comments = CommentDataSelectLogic.get().getComments(entity.getKnowledgeId());
            detail.setComments(comments);
            
            // 添付ファイル
            List<AttachedFile> attachedFiles = getAttachedFiles(entity);
            detail.setAttachments(attachedFiles);
            
            // 共同編集者
            Targets editors = getEditors(entity);
            detail.setEditors(editors);
            
            List<LabelValue> editorsCheck = TargetLogic.get().selectEditorsViewOnKnowledgeId(entity.getKnowledgeId(), loginedUser);
            boolean editable = KnowledgeLogic.get().isEditor(loginedUser, entity, editorsCheck);
            detail.setEditable(editable);
            
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
    private List<AttachedFile> getAttachedFiles(ArticleDataInterface entity) {
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
     * テンプレートの項目とその値を取得
     * @param entity
     * @param template
     * @return
     */
    private List<LabelValue> getTemplateItems(ArticleDataInterface entity, TemplateMastersEntity template) {
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
    private Targets getEditors(ArticleDataInterface entity) {
        Targets editors = new Targets();
        List<Target> listGroups = new ArrayList<>();
        List<Target> listUsers = new ArrayList<>();
        editors.setGroups(listGroups);
        editors.setUsers(listUsers);
        TargetsDao targetsDao = TargetsDao.get();
        List<GroupsEntity> groups = targetsDao.selectEditorGroupsOnKnowledgeId(entity.getKnowledgeId());
        for (GroupsEntity groupsEntity : groups) {
            Target group = new Target(groupsEntity.getGroupName(), String.valueOf(groupsEntity.getGroupId()));
            group.setType("group");
            listGroups.add(group);
        }
        List<UsersEntity> users = targetsDao.selectEditorUsersOnKnowledgeId(entity.getKnowledgeId());
        for (UsersEntity usersEntity : users) {
            Target user = new Target(usersEntity.getUserName(), String.valueOf(usersEntity.getUserId()));
            user.setType("user");
            listUsers.add(user);
        }
        return editors;
    }
    /**
     * 公開対象を取得
     * @param entity
     * @return
     */
    private Targets getViewers(ArticleDataInterface entity) {
        Targets viewers = new Targets();
        if (entity.getPublicFlag().intValue() == KnowledgeLogic.PUBLIC_FLAG_PROTECT) {
            List<Target> groupViewers = new ArrayList<>();
            List<Target> userViewers = new ArrayList<>();
            viewers.setGroups(groupViewers);
            viewers.setUsers(userViewers);
            TargetsDao targetsDao = TargetsDao.get();
            List<GroupsEntity> groups = targetsDao.selectGroupsOnKnowledgeId(entity.getKnowledgeId());
            for (GroupsEntity groupsEntity : groups) {
                Target group = new Target(groupsEntity.getGroupName(), String.valueOf(groupsEntity.getGroupId()));
                group.setType("group");
                groupViewers.add(group);
            }
            List<UsersEntity> users = targetsDao.selectUsersOnKnowledgeId(entity.getKnowledgeId());
            for (UsersEntity usersEntity : users) {
                Target user = new Target(usersEntity.getUserName(), String.valueOf(usersEntity.getUserId()));
                user.setType("user");
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
     * @param includeDraft 
     * @param checkDraft 
     * @return
     * @throws ParseException 
     */
    public Knowledge select(long knowledgeId, AccessUser loginedUser, boolean parseMarkdown, boolean sanitize, boolean includeDraft, boolean checkDraft) throws ParseException {
        KnowledgesEntity entity = KnowledgeLogic.get().selectWithTags(knowledgeId, loginedUser);
        if (entity == null) {
            return null;
        }
        
        Knowledge knowledge = conv(entity, loginedUser, parseMarkdown, sanitize);
        if (includeDraft) {
            KnowledgeDetailDraft draft = DraftDataLogic.get().selectOnKnowledgeId(knowledgeId, loginedUser, parseMarkdown, sanitize);
            if (draft != null) {
                draft.setEditable(true);
                return draft;
            } else {
                draft = new KnowledgeDetailDraft();
                PropertyUtil.copyPropertyValue(knowledge, draft, true);
                return draft;
            }
        }
        if (checkDraft) {
            KnowledgeDetailDraft draft = new KnowledgeDetailDraft();
            PropertyUtil.copyPropertyValue(knowledge, draft, true);
            DraftKnowledgesEntity d = DraftKnowledgesDao.get().selectOnKnowledgeAndUser(knowledgeId, loginedUser.getUserId());
            if (d != null) {
                draft.setDraftId(d.getDraftId());
                return draft;
            }
        }
        return knowledge;
    }
    
    /**
     * Knowledgeのデータを1件取得（WebAPIで返す形で）
     * @param knowledgeId
     * @param loginedUser
     * @param parseMarkdown 
     * @param sanitize 
     * @param checkDraft 
     * @return
     * @throws ParseException 
     */
    public Knowledge conv(ArticleDataInterface entity, AccessUser loginedUser, boolean parseMarkdown, boolean sanitize) throws ParseException {
        if (entity == null) {
            return null;
        }
        // 記事のマスタを読み込み
        Map<Integer, TemplateMastersEntity> typeMap = getTypeMap();
        Knowledge result = conv(entity, SINGLE, typeMap, loginedUser);
        
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
    public SearchResultArticle selectList(SearchKnowledgeParam param) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("get knowledge list. [params] " + PropertyUtil.reflectionToString(param));
        }
        // 記事のマスタを読み込み
        Map<Integer, TemplateMastersEntity> typeMap = getTypeMap();
        
        // 記事検索
        List<Knowledge> results = new ArrayList<>();
        SearchResultKnowledge searchResults = KnowledgeLogic.get().searchKnowledge(
                param.getKeyword(),
                param.getTags(),
                param.getGroups(),
                param.getCreators(),
                param.getTemplates(),
                param.getLoginedUser(),
                param.getOffset(),
                param.getLimit());
        List<String> ids = new ArrayList<>();
        for (KnowledgesEntity entity : searchResults.getItems()) {
            Knowledge result = conv(entity, LIST, typeMap, param.getLoginedUser());
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
        
        SearchResultArticle result = new SearchResultArticle();
        result.setItems(results);
        result.setTotal(searchResults.getTotal());
        return result;
    }
    protected Map<Integer, TemplateMastersEntity> getTypeMap() {
        // 記事の種類の情報取得
        List<TemplateMastersEntity> template = TemplateMastersDao.get().selectAll();
        Map<Integer, TemplateMastersEntity> typeMap = new HashMap<>();
        for (TemplateMastersEntity templateMastersEntity : template) {
            typeMap.put(templateMastersEntity.getTypeId(), templateMastersEntity);
        }
        return typeMap;
    }
    
    public List<KnowledgeList> convInternalList(List<? extends ArticleKeyInterface> results, AccessUser loginedUser) {
        List<KnowledgeList> list = new ArrayList<>();
        List<Long> knowledgeIds = null;
        if (loginedUser != null) {
            knowledgeIds = ViewHistoriesDao.get().selectViewdKnowledgeIds(results, loginedUser.getUserId());
        }
        //N+1問題になるので、もっと良い方法は無いか検討
        for (ArticleKeyInterface knowledge : results) {
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
