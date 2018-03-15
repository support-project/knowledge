package org.support.project.knowledge.control.api.internal.articles;

import java.lang.invoke.MethodHandles;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.DraftKnowledgesDao;
import org.support.project.knowledge.entity.DraftKnowledgesEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.TemplateMastersEntity;
import org.support.project.knowledge.logic.KnowledgeDataSelectLogic;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.KnowledgeTemplateItemSelectLogic;
import org.support.project.knowledge.vo.api.Type;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.service.Get;
import org.support.project.web.logic.invoke.Open;

@DI(instance = Instance.Prototype)
public class GetArticleTemplateItemsApiControl extends AbstractArticleApi {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    /**
     * 記事の一覧を取得
     * @throws Exception 
     */
    @Get(path="_api/articles/:id/items")
    @Open
    public Boundary execute() throws Exception {
        LOG.trace("access user: " + getLoginUserId());
        long knowledgeId = getRouteArticleId();
        boolean includeDraft = false; // 下書きがあれば下書きの情報を取得
        String d = getParam("include_draft");
        if (d != null && d.toLowerCase().equals("true")) {
            includeDraft = true;
        }
        KnowledgesEntity knowledge = KnowledgeLogic.get().select(knowledgeId, getLoginedUser());
        if (knowledge == null) {
            // 存在しない or アクセス権無し
            return sendError(HttpStatus.SC_404_NOT_FOUND);
        }
        int typeId = knowledge.getTypeId();
        long draftId = -1;
        if (includeDraft) {
            DraftKnowledgesEntity draft = DraftKnowledgesDao.get().selectOnKnowledgeAndUser(knowledgeId, getLoginUserId());
            if (draft != null) {
                typeId = draft.getTypeId();
                draftId = draft.getDraftId();
            }
        }
        TemplateMastersEntity template = KnowledgeTemplateItemSelectLogic.get().getItems(knowledgeId, typeId, includeDraft, draftId);
        Type type = KnowledgeDataSelectLogic.get().convType(template);
        return send(HttpStatus.SC_200_OK, type);
    }
    
}
