package org.support.project.knowledge.control.protect;

import java.util.List;

import org.support.project.knowledge.control.KnowledgeControlBase;
import org.support.project.knowledge.dao.DraftKnowledgesDao;
import org.support.project.knowledge.entity.DraftKnowledgesEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.service.Get;
import org.support.project.web.exception.InvalidParamException;

public class DraftControl extends KnowledgeControlBase {
    /** 一覧の表示件数 */
    public static final int PAGE_LIMIT = 20;

    /**
     * 下書きの一覧
     * @return
     * @throws InvalidParamException 
     */
    @Get
    public Boundary list() throws InvalidParamException {
        Integer page = super.getPathInteger(0);
        int previous = page - 1;
        if (previous < 0) {
            previous = 0;
        }
        setAttribute("offset", page);
        setAttribute("previous", previous);
        setAttribute("next", page + 1);
        int offset = page * PAGE_LIMIT;
        
        List<DraftKnowledgesEntity> drafts = DraftKnowledgesDao.get().selectOnUser(super.getLoginUserId(), PAGE_LIMIT, offset);
        setAttribute("drafts", drafts);

        return forward("drafts.jsp");
    }

    
    
    /**
     * 下書きの表示
     * @return
     * @throws InvalidParamException 
     */
    @Get
    public Boundary view() throws InvalidParamException {
        Long draftId = super.getPathLong(new Long(0));
        DraftKnowledgesEntity draft = DraftKnowledgesDao.get().selectOnKey(draftId);
        // アクセス可能かチェック
        if (draft == null) {
            sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
        }
        if (draft.getInsertUser().intValue() != getLoginUserId().intValue()) {
            // 下書きは自分が登録したもののみ（他人と下書きは共有しない） TODO 共有したいことがあるかも？制御が複雑になるのでいったんはできない
            addMsgWarn("message.authorize.error");
            return list();
        }
        if (draft.getKnowledgeId() == null || draft.getKnowledgeId() > 0) {
            // ナレッジに紐付いた下書きであれば、Knowledgeの編集権限をチェックする
            KnowledgesEntity knowledge = KnowledgeLogic.get().select(draft.getKnowledgeId(), getLoginedUser());
            if (knowledge == null) {
                addMsgWarn("knowledge.draft.view.msg.not.editor");
            }
        } else {
            draft.setKnowledgeId(null);
        }
        
        // ナレッジ編集画面を表示するために、必要な情報をそろえる
        setAttributeForEditPage();
        
        setAttributeOnProperty(draft);
        return forward("/protect/knowledge/edit.jsp");
    }
    
}
