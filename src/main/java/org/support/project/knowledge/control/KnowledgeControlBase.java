package org.support.project.knowledge.control;

import java.util.ArrayList;
import java.util.List;

import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.KnowledgeFilesDao;
import org.support.project.knowledge.dao.TagsDao;
import org.support.project.knowledge.dao.TemplateMastersDao;
import org.support.project.knowledge.entity.DraftKnowledgesEntity;
import org.support.project.knowledge.entity.KnowledgeFilesEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.TagsEntity;
import org.support.project.knowledge.entity.TemplateMastersEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.TargetLogic;
import org.support.project.knowledge.logic.UploadedFileLogic;
import org.support.project.knowledge.vo.UploadFile;
import org.support.project.web.bean.LabelValue;

@DI(instance = Instance.Prototype)
public class KnowledgeControlBase extends Control {

    protected String setViewParam() {
        StringBuilder params = new StringBuilder();
        params.append("?keyword=").append(getParamWithDefault("keyword", ""));
        params.append("&tag=").append(getParamWithDefault("tag", ""));
        params.append("&tagNames=").append(getParamWithDefault("tagNames", ""));
        params.append("&user=").append(getParamWithDefault("user", ""));
        params.append("&offset=").append(getParamWithDefault("offset", ""));

        if (super.getLoginedUser() != null) {
            params.append("&group=").append(getParamWithDefault("group", ""));
            params.append("&groupNames=").append(getParamWithDefault("groupNames", ""));
        }

        setAttribute("params", params.toString());
        return params.toString();
    }
    
    protected void setAttributeForEditPage() {
        List<TagsEntity> tagitems = TagsDao.get().selectAll();
        setAttribute("tagitems", tagitems);

        List<TemplateMastersEntity> templates = TemplateMastersDao.get().selectAll();
        setAttribute("templates", templates);
    }
    
    /**
     * 下書きの情報をセットする
     * 下書き一覧と、Knowledgeの編集画面の2つから呼び出されるため共通化
     * @param draft
     */
    protected void setDraftInfo(DraftKnowledgesEntity draft) {
        List<UploadFile> files = new ArrayList<UploadFile>();
        // 下書きにのみ紐づくファイルがあれば取得
        List<UploadFile> draftFiles = new ArrayList<UploadFile>();
        List<KnowledgeFilesEntity> filesEntities = KnowledgeFilesDao.get().selectOnDraftId(draft.getDraftId());
        for (KnowledgeFilesEntity entity : filesEntities) {
            if (entity.getCommentNo() == null || entity.getCommentNo() == 0) {
                draftFiles.add(UploadedFileLogic.get().convUploadFile(getRequest().getContextPath(), entity));
            }
        }
        if (draft.getKnowledgeId() != null && draft.getKnowledgeId() > 0) {
            // ナレッジに紐付いた下書きであれば、Knowledgeの編集権限をチェックする
            KnowledgesEntity knowledge = KnowledgeLogic.get().select(draft.getKnowledgeId(), getLoginedUser());
            if (knowledge == null) {
                addMsgWarn("knowledge.draft.view.msg.not.editor");
            }
            
            // ナレッジに紐づく添付ファイルを取得
            List<UploadFile> knowledgeFiles = UploadedFileLogic.get().selectOnKnowledgeIdWithoutCommentFiles(
                  draft.getKnowledgeId(), getRequest().getContextPath());
            files.addAll(knowledgeFiles);
        } else {
            draft.setKnowledgeId(null);
        }
        setAttributeOnProperty(draft);
        files.addAll(draftFiles);
        setAttribute("files", files);

        // 表示するグループを取得
        String[] targets = draft.getAccesses().split(",");
        List<LabelValue> viewers = TargetLogic.get().selectTargets(targets);
        setAttribute("groups", viewers);
        // 共同編集者
        String[] editordids = draft.getEditors().split(",");
        List<LabelValue> editors = TargetLogic.get().selectTargets(editordids);
        setAttribute("editors", editors);
        
    }
    
}
