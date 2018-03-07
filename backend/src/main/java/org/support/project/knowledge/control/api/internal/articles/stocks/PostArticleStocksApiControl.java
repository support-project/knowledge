package org.support.project.knowledge.control.api.internal.articles.stocks;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.api.internal.articles.AbstractArticleApi;
import org.support.project.knowledge.logic.KnowledgeStockLogic;
import org.support.project.knowledge.vo.Stock;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.service.Post;

import net.arnx.jsonic.JSONException;

@DI(instance = Instance.Prototype)
public class PostArticleStocksApiControl extends AbstractArticleApi {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    /**
     * 記事のイイネの一覧を取得
     * @throws Exception 
     */
    @Post(path="_api/articles/:id/stocks", checkCookieToken=false, checkHeaderToken=true)
    public Boundary execute() throws Exception {
        LOG.trace("POST _api/articles/:id/stocks");
        try {
            List<Map<String, Object>> stockSelect = parseJson(List.class);
            long knowledgeId = super.getRouteArticleId();
            List<Stock> stocks = conv(stockSelect);
            KnowledgeStockLogic.get().setStocks(knowledgeId, stocks, getAccessUser());
            return send(HttpStatus.SC_200_OK);
        } catch (JSONException e) {
            LOG.debug("json parse error", e);
            return sendError(HttpStatus.SC_400_BAD_REQUEST);
        } catch (Exception e) {
            LOG.error("error", e);
            return sendError(HttpStatus.SC_500_INTERNAL_SERVER_ERROR);
        }
    }
    
    private List<Stock> conv(List<Map<String, Object>> stockSelect) {
        List<Stock> list = new ArrayList<Stock>();
        for (Map<String, Object> map : stockSelect) {
            Stock stock = new Stock();
            BigDecimal stockId = (BigDecimal) map.get("stockId");
            stock.setStockId(stockId.longValue());
            stock.setStockName((String) map.get("stockName"));
            stock.setStocked((Boolean) map.get("stocked"));
            stock.setComment((String) map.get("comment"));
            list.add(stock);
        }
        return list;
    }
}
