package org.support.project.knowledge.logic;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.support.project.common.config.Resources;
import org.support.project.common.exception.ParseException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.PropertyUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.DraftItemValuesDao;
import org.support.project.knowledge.dao.DraftKnowledgesDao;
import org.support.project.knowledge.dao.KnowledgeFilesDao;
import org.support.project.knowledge.dao.TemplateItemsDao;
import org.support.project.knowledge.entity.DraftItemValuesEntity;
import org.support.project.knowledge.entity.DraftKnowledgesEntity;
import org.support.project.knowledge.entity.KnowledgeFilesEntity;
import org.support.project.knowledge.entity.TemplateItemsEntity;
import org.support.project.knowledge.entity.TemplateMastersEntity;
import org.support.project.knowledge.vo.MarkDown;
import org.support.project.knowledge.vo.api.AttachedFile;
import org.support.project.knowledge.vo.api.Knowledge;
import org.support.project.knowledge.vo.api.Target;
import org.support.project.knowledge.vo.api.Targets;
import org.support.project.web.bean.AccessUser;
import org.support.project.web.bean.LabelValue;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.config.MessageStatus;
import org.support.project.web.exception.InvalidParamException;

@DI(instance = Instance.Singleton)
public class DraftDataLogic extends KnowledgeDataSelectLogic {
    /** LOG */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    /** Get instance */
    public static DraftDataLogic get() {
        return Container.getComp(DraftDataLogic.class);
    }
    
    /**
     * 指定の記事に下書きの情報が登録されていれば、下書きの情報を取得する
     * @param knowledgeId
     * @param loginedUser
     * @param parseMarkdown
     * @param sanitize
     * @return
     * @throws ParseException 
     */
    public Knowledge selectOnKnowledgeId(long knowledgeId, AccessUser loginedUser, boolean parseMarkdown, boolean sanitize) throws ParseException {
        DraftKnowledgesEntity draft = DraftKnowledgesDao.get().selectOnKnowledgeAndUser(knowledgeId, loginedUser.getUserId());
        return convDraft(draft, parseMarkdown, sanitize);
    }

    public Knowledge convDraft(DraftKnowledgesEntity draft, boolean parseMarkdown, boolean sanitize) throws ParseException {
        if (draft == null) {
            return null;
        }
        Map<Integer, TemplateMastersEntity> typeMap = getTypeMap();
        Knowledge result = convDraft(draft, SINGLE, typeMap);
        
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

    private Knowledge convDraft(DraftKnowledgesEntity entity, int single, Map<Integer, TemplateMastersEntity> typeMap) {
        Knowledge result = new Knowledge();
        PropertyUtil.copyPropertyValue(entity, result, true);
        
        // テンプレートの情報を追加
        TemplateMastersEntity template;
        if (typeMap.containsKey(entity.getTypeId())) {
            template = typeMap.get(entity.getTypeId());
        } else {
            template = typeMap.get(TemplateLogic.TYPE_ID_KNOWLEDGE);
        }
        result.setType(convType(template));
        if (single != SINGLE) {
            // リストで取得する場合は、Typeのみ
            return result;
        }
        
        // タグ
        if (StringUtils.isNotEmpty(entity.getTagNames())) {
            String[] tags = entity.getTagNames().split(",");
            result.setTags(Arrays.asList(tags));
        } else {
            result.setTags(new ArrayList<>());
        }
        
        // 参照権限
        Targets viewers = getTarget(entity.getAccesses());
        result.setViewers(viewers);
        
        // 編集権限
        Targets editors = getTarget(entity.getEditors());
        result.setEditors(editors);
        
        // テンプレートで拡張した項目の値を取得
        List<LabelValue> templateItems = getDraftTemplateItems(entity.getTypeId(), entity.getDraftId(), template);
        result.setItems(templateItems);

        // 添付ファイル
        List<AttachedFile> attachedFiles = getAttachedFiles(entity.getKnowledgeId(), entity.getDraftId());
        result.setAttachments(attachedFiles);
        
        return result;
    }
    
    private List<AttachedFile> getAttachedFiles(long knowledgeId, long draftId) {
        List<AttachedFile> attachedFiles = new ArrayList<>();
        // 下書きにのみ紐づくファイルがあれば取得
        List<KnowledgeFilesEntity> filesEntities = KnowledgeFilesDao.get().selectOnDraftId(draftId);
        for (KnowledgeFilesEntity entity : filesEntities) {
            if (entity.getCommentNo() == null || entity.getCommentNo() == 0) {
                AttachedFile file = new AttachedFile();
                PropertyUtil.copyPropertyValue(entity, file);
                attachedFiles.add(file);
            }
        }
        // ナレッジに紐づく添付ファイルを取得
        filesEntities = KnowledgeFilesDao.get().selectOnKnowledgeId(knowledgeId);
        for (KnowledgeFilesEntity entity : filesEntities) {
            if (entity.getCommentNo() == null || entity.getCommentNo() == 0) {
                AttachedFile file = new AttachedFile();
                PropertyUtil.copyPropertyValue(entity, file);
                attachedFiles.add(file);
            }
        }
        return attachedFiles;
    }

    /**
     * ターゲット文字列からTargetオブジェクトを生成
     * @param accesses
     * @return
     */
    private Targets getTarget(String accesses) {
        Targets target = new Targets();
        String[] targets = accesses.split(",");
        List<LabelValue> viewers = TargetLogic.get().selectTargets(targets);
        List<Target> groupViewers = new ArrayList<>();
        List<Target> userViewers = new ArrayList<>();
        target.setGroups(groupViewers);
        target.setUsers(userViewers);
        for (LabelValue labelValue : viewers) {
            if (TargetLogic.get().isGroupLabel(labelValue.getLabel())) {
                Target group = new Target(labelValue.getLabel(), labelValue.getValue());
                group.setType("group");
                groupViewers.add(group);
            } else if (TargetLogic.get().isGroupLabel(labelValue.getLabel())) {
                Target user = new Target(labelValue.getLabel(), labelValue.getValue());
                user.setType("user");
                userViewers.add(user);
            }
        }
        return null;
    }
    
    public List<LabelValue> getDraftTemplateItems(int typeId, long draftId, TemplateMastersEntity template) {
        List<TemplateItemsEntity> items = TemplateItemsDao.get().selectOnTypeId(typeId);
        List<DraftItemValuesEntity> values = DraftItemValuesDao.get().selectOnDraftId(draftId);
        List<LabelValue> templateItems = new ArrayList<>();
        for (DraftItemValuesEntity val : values) {
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

    public void deleteDraft(long draftId, AccessUser user) throws InvalidParamException {
        Resources resources = Resources.getInstance(user.getLocale());
        DraftKnowledgesEntity draft = DraftKnowledgesDao.get().selectOnKey(draftId);
        // アクセス可能かチェック
        if (draft == null) {
            throw new InvalidParamException(new MessageResult(
                    MessageStatus.Warning, HttpStatus.SC_404_NOT_FOUND, "NOT_FOUND", ""));
        }
        if (draft.getInsertUser().intValue() != user.getUserId().intValue()) {
            throw new InvalidParamException(new MessageResult(
                    MessageStatus.Warning, HttpStatus.SC_403_FORBIDDEN, resources.getResource("knowledge.edit.noaccess"), ""));
        }
        DraftKnowledgesDao.get().physicalDelete(draft);
        DraftItemValuesDao.get().deleteOnDraftId(draftId);
    }
    
}
