package org.support.project.knowledge.control.api.internal.stocks;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.support.project.common.bean.ValidateError;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.StocksDao;
import org.support.project.knowledge.entity.StocksEntity;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.ApiControl;
import org.support.project.web.control.service.Post;

import net.arnx.jsonic.JSONException;

@DI(instance = Instance.Prototype)
public class PostStockApiControl extends ApiControl {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    /**
     * ストックを登録
     * @throws Exception 
     */
    @Post(path="_api/stocks", checkCookieToken=false, checkHeaderToken=true)
    public Boundary execute() throws Exception {
        try {
            StocksEntity stocksEntity = getJsonObject(StocksEntity.class);
            stocksEntity.setStockType(StocksEntity.STOCKTYPE_PRIVATE);
            
            List<ValidateError> errors = stocksEntity.validate();
            if (!errors.isEmpty()) {
                return send(HttpStatus.SC_400_BAD_REQUEST, errors);
            }
            stocksEntity = StocksDao.get().insert(stocksEntity);
            return send(HttpStatus.SC_201_CREATED, stocksEntity);
        } catch (JSONException e) {
            LOG.debug("json parse error", e);
            return sendError(HttpStatus.SC_400_BAD_REQUEST);
        } catch (Exception e) {
            LOG.error("error", e);
            return sendError(HttpStatus.SC_500_INTERNAL_SERVER_ERROR);
        }
    }
}
