package org.support.project.knowledge.control.api.internal.articles;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.StocksDao;
import org.support.project.knowledge.entity.StocksEntity;
import org.support.project.knowledge.logic.KnowledgeDataSelectLogic;
import org.support.project.knowledge.vo.api.Knowledge;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.service.Get;
import org.support.project.web.logic.invoke.Open;

@DI(instance = Instance.Prototype)
public class GetArticleOneApiControl extends AbstractArticleApi {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    /**
     * 記事を取得
     * @throws Exception 
     */
    @Get(path="_api/articles/:id")
    @Open
    public Boundary execute() throws Exception {
        LOG.trace("access user: " + getLoginUserId());
        long knowledgeId = getRouteArticleId();
        
        String s = getParam("sanitize");
        boolean sanitize = !StringUtils.isFalse(s);
        
        String parse = getParam("parse");
        boolean parseMarkdown = StringUtils.isTrue(parse);
        
        String d = getParam("include_draft");
        boolean includeDraft = StringUtils.isTrue(d);
        
        String c = getParam("check_draft");
        boolean checkDraft = StringUtils.isTrue(c);
        
        Knowledge result = KnowledgeDataSelectLogic.get().select(knowledgeId, getAccessUser(), parseMarkdown, sanitize, includeDraft, checkDraft);
        if (result == null) {
            return sendError(HttpStatus.SC_404_NOT_FOUND);
        }
        
        List<StocksEntity> stocks = StocksDao.get().selectStockOnKnowledge(knowledgeId, getLoginedUser());
        result.setStocks(stocks);
        
        super.setSendEscapeHtml(false);
        return send(HttpStatus.SC_200_OK, result);
    }

}
