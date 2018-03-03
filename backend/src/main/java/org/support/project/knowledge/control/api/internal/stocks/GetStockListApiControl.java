package org.support.project.knowledge.control.api.internal.stocks;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.StockKnowledgesDao;
import org.support.project.knowledge.dao.StocksDao;
import org.support.project.knowledge.entity.StocksEntity;
import org.support.project.web.bean.ApiParams;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.ApiControl;
import org.support.project.web.control.service.Get;

@DI(instance = Instance.Prototype)
public class GetStockListApiControl extends ApiControl {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    /**
     * ストックの一覧を取得
     * @throws Exception 
     */
    @Get(path="_api/stocks")
    public Boundary execute() throws Exception {
        LOG.trace("_api/stocks");
        
        ApiParams apiParams = super.getCommonApiParams();

        List<StocksEntity> stocks = StocksDao.get().selectMyStocks(super.getAccessUser(), apiParams.getOffset(), apiParams.getLimit());
        for (StocksEntity stocksEntity : stocks) {
            int count = StockKnowledgesDao.get().selectActiveOnStockId(stocksEntity.getStockId()).size();
            stocksEntity.setKnowledgeCount(count);
        }
        int count = StocksDao.get().selectMyStocksCount(super.getAccessUser());
        setPaginationHeaders(count, apiParams.getOffset(), apiParams.getLimit());
        return send(HttpStatus.SC_200_OK, stocks);
    }
}
