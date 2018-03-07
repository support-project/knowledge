package org.support.project.knowledge.control.api.internal.articles.stocks;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.api.internal.articles.AbstractArticleApi;
import org.support.project.knowledge.dao.StocksDao;
import org.support.project.knowledge.vo.Stock;
import org.support.project.web.bean.ApiParams;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.control.service.Get;

@DI(instance = Instance.Prototype)
public class GetArticleStocksApiControl extends AbstractArticleApi {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    /**
     * ストックの一覧を取得
     * @throws Exception 
     */
    @Get(path="_api/articles/:id/stocks")
    public Boundary execute() throws Exception {
        LOG.trace("GET _api/articles/:id/likes");
        long knowledgeId = super.getRouteArticleId();
        
        ApiParams apiParams = super.getCommonApiParams();
        int limit = apiParams.getLimit();
        int offset = apiParams.getOffset();
        
        int total = StocksDao.get().selectMyStocksCount(super.getAccessUser());
        super.setPaginationHeaders(total, offset, limit);
        
        List<Stock> stocks = StocksDao.get().selectMyStocksWithStocked(
                super.getAccessUser(), knowledgeId, offset, limit);
        return send(stocks);
    }
}
