package org.support.project.knowledge.control.api.internal;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.TemplateMastersEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.KnowledgeTemplateItemSelectLogic;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.ApiControl;
import org.support.project.web.control.service.Get;
import org.support.project.web.logic.invoke.Open;

@DI(instance = Instance.Prototype)
public class ArticleDetailTemplateItemsGetApiControl extends ApiControl {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(ArticleDetailTemplateItemsGetApiControl.class);
    /**
     * 記事の一覧を取得
     * @throws Exception 
     */
    @Get(path="_api/articles/:id/items")
    @Open
    public Boundary comments() throws Exception {
        LOG.trace("access user: " + getLoginUserId());
        String id = super.getParam("id");
        LOG.debug(id);
        if (!StringUtils.isLong(id)) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST);
        }
        long knowledgeId = Long.parseLong(id);
        KnowledgesEntity knowledge = KnowledgeLogic.get().select(knowledgeId, getLoginedUser());
        if (knowledge == null) {
            // 存在しない or アクセス権無し
            return sendError(HttpStatus.SC_404_NOT_FOUND);
        }
        TemplateMastersEntity template = KnowledgeTemplateItemSelectLogic.get().getItems(knowledge);
        return send(HttpStatus.SC_200_OK, template);
    }
    
}
