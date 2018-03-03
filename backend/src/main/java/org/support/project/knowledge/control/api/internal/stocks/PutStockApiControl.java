package org.support.project.knowledge.control.api.internal.stocks;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.support.project.common.bean.ValidateError;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.StocksDao;
import org.support.project.knowledge.entity.StocksEntity;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.ApiControl;
import org.support.project.web.control.service.Put;

import net.arnx.jsonic.JSONException;

@DI(instance = Instance.Prototype)
public class PutStockApiControl extends ApiControl {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    /**
     * ストックを取得
     * @throws Exception 
     */
    @Put(path="_api/stocks/:id", checkCookieToken=false, checkHeaderToken=true)
    public Boundary execute() throws Exception {
        try {
            String id = super.getRouteParam("id");
            LOG.debug(id);
            if (!StringUtils.isLong(id)) {
                return sendError(HttpStatus.SC_400_BAD_REQUEST);
            }
            Long stockId = new Long(id);
            StocksEntity stocksEntity = StocksDao.get().selectOnKey(stockId);
            if (stocksEntity == null) {
                return sendError(HttpStatus.SC_404_NOT_FOUND);
            }
            if (stocksEntity.getInsertUser().intValue() != getLoginUserId()) {
                return sendError(HttpStatus.SC_403_FORBIDDEN);
            }
            StocksEntity stock = getJsonObject(StocksEntity.class);
            List<ValidateError> errors = stock.validate();
            if (!errors.isEmpty()) {
                return send(HttpStatus.SC_400_BAD_REQUEST, errors);
            }
            stocksEntity.setStockName(stock.getStockName());
            stocksEntity.setDescription(stock.getDescription());
            stocksEntity = StocksDao.get().update(stocksEntity);
            return send(HttpStatus.SC_200_OK, stocksEntity);
        } catch (JSONException e) {
            LOG.debug("json parse error", e);
            return sendError(HttpStatus.SC_400_BAD_REQUEST);
        } catch (Exception e) {
            LOG.error("error", e);
            return sendError(HttpStatus.SC_500_INTERNAL_SERVER_ERROR);
        }
    }
}
