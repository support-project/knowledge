package org.support.project.knowledge.integration;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.test.Order;
import org.support.project.common.util.PropertyUtil;
import org.support.project.knowledge.dao.NotifyQueuesDao;
import org.support.project.knowledge.entity.NotifyQueuesEntity;
import org.support.project.knowledge.entity.StockKnowledgesEntity;
import org.support.project.knowledge.entity.StocksEntity;
import org.support.project.knowledge.logic.AggregateLogic;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.TemplateLogic;
import org.support.project.knowledge.vo.Stock;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.boundary.ForwardBoundary;
import org.support.project.web.boundary.JsonBoundary;
import org.support.project.web.logic.impl.DefaultAuthenticationLogicImpl;
import org.support.project.web.test.stub.StubHttpServletRequest;
import org.support.project.web.test.stub.StubHttpServletResponse;


/**
 * 
 * @author koda
 */
public class IntegrationStockTest extends IntegrationCommon {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(IntegrationStockTest.class);
    
    private static final String POST_USER = "integration-test-user-01";
    private static final String STOCK_USER = "integration-test-user-02";
    
    private static long knowledgeId;
    private static long stockId;
    
    /**
     * ユーザを登録
     * 
     * @throws Exception
     */
    @Test
    @Order(order = 1)
    public void testUserInsert() throws Exception {
        LOG.info("ユーザ登録");
        addUser(POST_USER);
        addUser(STOCK_USER);
    }
    
    /**
     * 記事登録
     * @throws Exception
     */
    @Test
    @Order(order = 100)
    public void testPost() throws Exception {
        MessageResult msg = postKnowledge(
                POST_USER,
                KnowledgeLogic.PUBLIC_FLAG_PUBLIC,
                TemplateLogic.TYPE_ID_KNOWLEDGE,
                null);

        knowledgeId = new Long(msg.getResult());
        
        assertKnowledgeCP(POST_USER, knowledgeId, 20);
        assertKnowledgeCP(STOCK_USER, knowledgeId, 1);
        assertCP(POST_USER, 21);
        assertCP(STOCK_USER, 1);
        execNotificationQueue();
        assertNotificationCount(POST_USER, 1);
        assertNotificationCount(STOCK_USER, 1);
    }
    
    /**
     * ストックが無いことを確認
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Test
    @Order(order = 101)
    public void testCheckListEmpty() throws Exception {
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(STOCK_USER, request, response);
        
        // ストック一覧は無い
        request.setServletPath("protect.stock/mylist");
        request.setMethod("get");
        ForwardBoundary forwardBoundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/stock/list.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, forwardBoundary, "path"));
        List<StocksEntity> stocks = (List<StocksEntity>) request.getAttribute("stocks");
        Assert.assertEquals(0, stocks.size());
    }
    
    
    /**
     * ストックを作成
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Test
    @Order(order = 200)
    public void testCreateStock() throws Exception {
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(STOCK_USER, request, response);
        
        // Stock一覧を取得
        request.setServletPath("protect.stock/chooselist");
        request.setMethod("get");
        JsonBoundary jsonBoundary = invoke(request, response, JsonBoundary.class);
        List<Stock> stocks = (List<Stock>) jsonBoundary.getObj();
        Assert.assertEquals(0, stocks.size());
        
        // Stock登録画面を表示
        request.setServletPath("protect.stock/add");
        request.setMethod("get");
        ForwardBoundary forwardBoundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/stock/add.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, forwardBoundary, "path"));
        
        // Stock登録
        request = new StubHttpServletRequest(request);
        response = new StubHttpServletResponse(request);
        request.setServletPath("protect.stock/insert");
        request.setMethod("post");
        request.addParameter("stockName", "Stock");
        forwardBoundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/stock/edit.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, forwardBoundary, "path"));
        
        // 一覧再取得
        request.setServletPath("protect.stock/chooselist");
        request.setMethod("get");
        jsonBoundary = invoke(request, response, JsonBoundary.class);
        stocks = (List<Stock>) jsonBoundary.getObj();
        Assert.assertEquals(1, stocks.size());
        stockId = stocks.get(0).getStockId();
    }
    
    /**
     * ストックに何も登録していないことを確認
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Test
    @Order(order = 201)
    public void testCheckStockEmpty() throws Exception {
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(STOCK_USER, request, response);
        
        // ストック一覧は無い
        request.setServletPath("protect.stock/knowledge");
        request.setMethod("get");
        request.setAttribute("stockId", String.valueOf(stockId));
        request.setAttribute("offset", "0");
        ForwardBoundary forwardBoundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/stock/knowledge.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, forwardBoundary, "path"));
        List<StockKnowledgesEntity> knowledges = (List<StockKnowledgesEntity>) request.getAttribute("knowledges");
        Assert.assertEquals(0, knowledges.size());
    }
    
    
    /**
     * ストックに入れる
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Test
    @Order(order = 300)
    public void testStock() throws Exception {
        // 記事を開く
        StubHttpServletRequest request = openKnowledge(STOCK_USER, knowledgeId);
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        
        // Stockの一覧を取得
        request = new StubHttpServletRequest(request);
        response = new StubHttpServletResponse(request);
        request.setServletPath("protect.stock/chooselist");
        request.setMethod("get");
        JsonBoundary jsonBoundary = invoke(request, response, JsonBoundary.class);
        List<Stock> stocks = (List<Stock>) jsonBoundary.getObj();
        Assert.assertEquals(1, stocks.size());
        
        // 記事をStock
        request.setServletPath("protect.knowledge/stock/" + knowledgeId);
        request.setMethod("Post");
        String json = "[{\"description\":\"\",\"stockId\":"
                + stocks.get(0).getStockId()
                + ",\"stockName\":\"Stock\",\"stockType\":0,\"stocked\":true}]";
        request.setInputstream(new ByteArrayInputStream(json.getBytes()));
        jsonBoundary = invoke(request, response, JsonBoundary.class);
        LOG.info(jsonBoundary.getObj());
    }
    
    /**
     * ストックに入ったことを確認
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Test
    @Order(order = 301)
    public void testCheckStockStored() throws Exception {
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(STOCK_USER, request, response);
        
        request.setServletPath("protect.stock/knowledge");
        request.setMethod("get");
        request.setAttribute("stockId", String.valueOf(stockId));
        request.setAttribute("offset", "0");
        ForwardBoundary forwardBoundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/stock/knowledge.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, forwardBoundary, "path"));
        List<StockKnowledgesEntity> knowledges = (List<StockKnowledgesEntity>) request.getAttribute("knowledges");
        Assert.assertEquals(1, knowledges.size());
    }
    
    
    /**
     * ストック後の状態確認
     * @throws Exception
     */
    @Test
    @Order(order = 302)
    public void testAssertAfterStock() throws Exception {
        assertKnowledgeCP(POST_USER, knowledgeId, 2);
        assertCP(POST_USER, 2);
        assertCP(STOCK_USER, 0);
        List<NotifyQueuesEntity> list = NotifyQueuesDao.get().selectAll();
        Assert.assertEquals(0, list.size());
        assertNotificationCount(POST_USER, 0);
        assertNotificationCount(STOCK_USER, 0);
    }


    /**
     * 再集計を実行
     * @throws Exception
     */
    @Test
    @Order(order = 302)
    public void testAggregate() throws Exception {
        AggregateLogic.get().startAggregate();
        // CPは変化しない
        assertCP(POST_USER, 0);
        assertCP(STOCK_USER, 0);
        assertKnowledgeCP(POST_USER, knowledgeId, 0);
    }



    /**
     * ストックから外す
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Test
    @Order(order = 400)
    public void testRemoveStock() throws Exception {
        // 記事を開く
        StubHttpServletRequest request = openKnowledge(STOCK_USER, knowledgeId);
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        
        // Stockの一覧を取得
        request = new StubHttpServletRequest(request);
        response = new StubHttpServletResponse(request);
        request.setServletPath("protect.stock/chooselist");
        request.setMethod("get");
        JsonBoundary jsonBoundary = invoke(request, response, JsonBoundary.class);
        List<Stock> stocks = (List<Stock>) jsonBoundary.getObj();
        Assert.assertEquals(1, stocks.size());
        
        // 記事をStock
        request.setServletPath("protect.knowledge/stock/" + knowledgeId);
        request.setMethod("Post");
        String json = "[{\"description\":\"\",\"stockId\":"
                + stocks.get(0).getStockId()
                + ",\"stockName\":\"Stock\",\"stockType\":0,\"stocked\":false}]";
        request.setInputstream(new ByteArrayInputStream(json.getBytes()));
        jsonBoundary = invoke(request, response, JsonBoundary.class);
        LOG.info(jsonBoundary.getObj());
    }   
    
    /**
     * ストックから解除されたことを確認
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Test
    @Order(order = 401)
    public void testCheckStockRemoved() throws Exception {
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(STOCK_USER, request, response);
        
        // ストック一覧は無い
        request.setServletPath("protect.stock/knowledge");
        request.setMethod("get");
        request.setAttribute("stockId", String.valueOf(stockId));
        request.setAttribute("offset", "0");
        ForwardBoundary forwardBoundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/stock/knowledge.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, forwardBoundary, "path"));
        List<StockKnowledgesEntity> knowledges = (List<StockKnowledgesEntity>) request.getAttribute("knowledges");
        Assert.assertEquals(0, knowledges.size());
    }
    
    /**
     * ストックから削除後の状態確認
     * （ストックから削除しても原点していないため、CPはそのまま）
     * @throws Exception
     */
    @Test
    @Order(order = 402)
    public void testAssertAfterRemove() throws Exception {
        assertKnowledgeCP(POST_USER, knowledgeId, 0);
        assertCP(POST_USER, 0);
        assertCP(STOCK_USER, 0);
        List<NotifyQueuesEntity> list = NotifyQueuesDao.get().selectAll();
        Assert.assertEquals(0, list.size());
        assertNotificationCount(POST_USER, 0);
        assertNotificationCount(STOCK_USER, 0);
    }
    
    /**
     * ストックを更新
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Test
    @Order(order = 500)
    public void testUpdateStock() throws Exception {
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(STOCK_USER, request, response);
        
        // Stock登録画面を表示
        request.setServletPath("protect.stock/edit/" + stockId);
        request.setMethod("get");
        ForwardBoundary forwardBoundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/stock/edit.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, forwardBoundary, "path"));
        
        // Stock更新
        request = new StubHttpServletRequest(request);
        response = new StubHttpServletResponse(request);
        request.setServletPath("protect.stock/update");
        request.setMethod("post");
        request.addParameter("stockId", String.valueOf(stockId));
        request.addParameter("stockName", "StockUpdate");
        forwardBoundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/stock/edit.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, forwardBoundary, "path"));
        
        // 一覧再取得
        request.setServletPath("protect.stock/chooselist");
        request.setMethod("get");
        JsonBoundary jsonBoundary = invoke(request, response, JsonBoundary.class);
        List<Stock> stocks = (List<Stock>) jsonBoundary.getObj();
        Assert.assertEquals(1, stocks.size());
    }
    
    /**
     * ストックを削除
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Test
    @Order(order = 600)
    public void testDeleteStock() throws Exception {
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(STOCK_USER, request, response);
        
        // Stock登録画面を表示
        request.setServletPath("protect.stock/edit/" + stockId);
        request.setMethod("get");
        ForwardBoundary forwardBoundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/stock/edit.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, forwardBoundary, "path"));
        
        // Stock更新
        request = new StubHttpServletRequest(request);
        response = new StubHttpServletResponse(request);
        request.setServletPath("protect.stock/delete");
        request.setMethod("post");
        request.addParameter("stockId", String.valueOf(stockId));
        request.addParameter("stockName", "StockUpdate");
        forwardBoundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/stock/list.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, forwardBoundary, "path"));
        List<StocksEntity> stocks = (List<StocksEntity>) request.getAttribute("stocks");
        Assert.assertEquals(0, stocks.size());
    }

    
    /**
     * CP獲得履歴
     * @throws Exception
     */
    @Test
    @Order(order = 700)
    public void testActivityHistory() throws Exception {
        assertPointHistoryCount(POST_USER, 3);
        assertPointHistoryCount(STOCK_USER, 1);
    }


    /**
     * 再集計を実行
     * ストックを解除後に再実行しており、削除したポイント分、ポイントが下がる
     * @throws Exception
     */
    @Test
    @Order(order = 1000)
    public void testAggregate2() throws Exception {
        AggregateLogic.get().startAggregate();
        assertCP(POST_USER, -2);
        assertCP(STOCK_USER, 0);
        assertKnowledgeCP(POST_USER, knowledgeId, -2);
    }


}
