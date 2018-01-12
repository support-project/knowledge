package org.support.project.knowledge.control.api.internal.articles;

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
import org.support.project.web.control.ApiControl;
import org.support.project.web.control.service.Get;
import org.support.project.web.logic.invoke.Open;

@DI(instance = Instance.Prototype)
public class GetArticleOneApiControl extends ApiControl {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(GetArticleOneApiControl.class);
    /**
     * 記事の一覧を取得
     * @throws Exception 
     */
    @Get(path="_api/articles/:id")
    @Open
    public Boundary article() throws Exception {
        LOG.trace("access user: " + getLoginUserId());
        String id = super.getParam("id");
        LOG.debug(id);
        if (!StringUtils.isLong(id)) {
            return sendError(HttpStatus.SC_400_BAD_REQUEST);
        }
        boolean parseMarkdown = false;
        String parse = getParam("parse");
        if (parse != null && parse.toLowerCase().equals("true")) {
            parseMarkdown = true;
        }
        boolean sanitize = true;
        String s = getParam("sanitize");
        if (s != null && s.toLowerCase().equals("false")) {
            sanitize = false;
        }
        
        long knowledgeId = Long.parseLong(id);
        Knowledge result = KnowledgeDataSelectLogic.get().select(knowledgeId, getLoginedUser(), parseMarkdown, sanitize);
        if (result == null) {
            return sendError(HttpStatus.SC_404_NOT_FOUND);
        }
        
        List<StocksEntity> stocks = StocksDao.get().selectStockOnKnowledge(knowledgeId, getLoginedUser());
        result.setStocks(stocks);
        
        super.setSendEscapeHtml(false);
        return send(HttpStatus.SC_200_OK, result);
    }

}
