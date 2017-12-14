package org.support.project.knowledge.control.protect;

import java.io.IOException;
import java.util.List;

import org.support.project.common.bean.ValidateError;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.dao.StockKnowledgesDao;
import org.support.project.knowledge.dao.StocksDao;
import org.support.project.knowledge.entity.StockKnowledgesEntity;
import org.support.project.knowledge.entity.StocksEntity;
import org.support.project.knowledge.vo.Stock;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;
import org.support.project.web.exception.InvalidParamException;

import net.arnx.jsonic.JSONException;

@DI(instance = Instance.Prototype)
public class StockControl extends Control {

    private static final int LIST_LIMIT = 20;

    /**
     * 自分の登録したストックの一覧を表示
     * 
     * ストックは、 - Javascriptの参考になるナレッジをストックする - Newsのストック など、ストックするフォルダのような形で一覧を分けられるようにしようかと考えている
     * 
     * 記事には「タグ」がついているが、「タグ」はナレッジの登録者が設定する。 ストックは、ナレッジの参照者が行うが、参照側でも自由に分類分けが出来たほうが良いかと思い、 この形にした。
     * 
     * @return
     * @throws InvalidParamException
     */
    @Get(publishToken = "knowledge")
    public Boundary mylist() throws InvalidParamException {
        Integer offset = super.getPathInteger(0);
        StocksDao stocksDao = StocksDao.get();

        List<StocksEntity> stocks = stocksDao.selectMyStocks(super.getLoginedUser(), offset * LIST_LIMIT, LIST_LIMIT);
        for (StocksEntity stocksEntity : stocks) {
            int count = StockKnowledgesDao.get().selectActiveOnStockId(stocksEntity.getStockId()).size();
            stocksEntity.setKnowledgeCount(count);
        }
        setAttribute("stocks", stocks);

        int previous = offset - 1;
        if (previous < 0) {
            previous = 0;
        }
        setAttribute("offset", offset);
        setAttribute("previous", previous);
        setAttribute("next", offset + 1);

        return forward("list.jsp");
    }

    /**
     * ストック登録画面を表示
     * 
     * @return
     * @throws InvalidParamException
     */
    @Get(publishToken = "knowledge")
    public Boundary add() throws InvalidParamException {
        return forward("add.jsp");
    }

    /**
     * ストック登録
     * 
     * @return
     * @throws InvalidParamException
     * @throws IOException
     * @throws JSONException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Post(subscribeToken = "knowledge")
    public Boundary insert() throws InvalidParamException, InstantiationException, IllegalAccessException, JSONException, IOException {
        StocksEntity stocksEntity = super.getParamOnProperty(StocksEntity.class);
        stocksEntity.setStockType(StocksEntity.STOCKTYPE_PRIVATE);
        List<ValidateError> errors = stocksEntity.validate();
        if (!errors.isEmpty()) {
            setResult(null, errors);
            return forward("add.jsp");
        }

        stocksEntity = StocksDao.get().insert(stocksEntity);
        String successMsg = "message.success.save";
        setResult(successMsg, errors);

        super.setPathInfo(stocksEntity.getStockId().toString());
        return edit();
    }

    /**
     * ストック更新画面を表示
     * 
     * @return
     * @throws InvalidParamException
     */
    @Get(publishToken = "knowledge")
    public Boundary edit() throws InvalidParamException {
        Long stockId = super.getPathLong(new Long(0));
        StocksDao stocksDao = StocksDao.get();

        StocksEntity stocksEntity = stocksDao.selectOnKey(stockId);
        setAttributeOnProperty(stocksEntity);

        return forward("edit.jsp");
    }

    /**
     * ストック更新
     * 
     * @return
     * @throws InvalidParamException
     * @throws IOException
     * @throws JSONException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Post(subscribeToken = "knowledge")
    public Boundary update() throws InvalidParamException, InstantiationException, IllegalAccessException, JSONException, IOException {
        StocksEntity stocksEntity = super.getParamOnProperty(StocksEntity.class);
        stocksEntity.setStockType(StocksEntity.STOCKTYPE_PRIVATE);
        List<ValidateError> errors = stocksEntity.validate();
        if (!errors.isEmpty()) {
            setResult(null, errors);
            return forward("edit.jsp");
        }

        stocksEntity = StocksDao.get().update(stocksEntity);
        String successMsg = "message.success.update";
        setResult(successMsg, errors);

        super.setPathInfo(stocksEntity.getStockId().toString());
        return edit();
    }

    /**
     * ストック削除
     * 
     * @return
     * @throws InvalidParamException
     * @throws IOException
     * @throws JSONException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Post(subscribeToken = "knowledge")
    public Boundary delete() throws InvalidParamException, InstantiationException, IllegalAccessException, JSONException, IOException {
        StocksDao dao = StocksDao.get();
        Long stockId = getParam("stockId", Long.class);

        dao.delete(stockId);
        String successMsg = "message.success.delete";
        setResult(successMsg, null);
        return mylist();
    }

    /**
     * ストックの選択肢を一覧取得（JSON）
     * 
     * @return
     * @throws InvalidParamException
     */
    @Get(publishToken = "knowledge")
    public Boundary chooselist() throws InvalidParamException {
        Integer offset = super.getPathInteger(0);
        StocksDao stocksDao = StocksDao.get();

        Long knowledgeId = getParam("knowledge_id", Long.class);
        List<Stock> stocks = stocksDao.selectMyStocksWithStocked(super.getLoginedUser(), knowledgeId, offset * LIST_LIMIT, LIST_LIMIT);
        return send(stocks);
    }

    /**
     * 選択したストックに登録されたナレッジを取得
     * 
     * @return
     * @throws InvalidParamException
     */
    @Get(publishToken = "knowledge")
    public Boundary knowledge() throws InvalidParamException {
        Long stockId = getParam("stockId", Long.class);
        Integer offset = getParam("offset", Integer.class);

        StocksDao stocksDao = StocksDao.get();
        StocksEntity entity = stocksDao.selectOnKey(stockId);

        if (entity == null) {
            return sendError(HttpStatus.SC_404_NOT_FOUND, "Not Found");
        }
        if (entity.getInsertUser().intValue() != getLoginUserId().intValue() && entity.getStockType().intValue() != StocksEntity.STOCKTYPE_PUBLIC) {
            return sendError(HttpStatus.SC_403_FORBIDDEN, "Forbidden");
        }
        setAttributeOnProperty(entity);

        StockKnowledgesDao knowledgesDao = StockKnowledgesDao.get();
        List<StockKnowledgesEntity> knowledges = knowledgesDao.selectOnStockIdWithKnowledgeInfo(stockId, offset * LIST_LIMIT, LIST_LIMIT);
        setAttribute("knowledges", knowledges);

        int previous = offset - 1;
        if (previous < 0) {
            previous = 0;
        }
        setAttribute("offset", offset);
        setAttribute("previous", previous);
        setAttribute("next", offset + 1);

        return forward("knowledge.jsp");
    }

}
