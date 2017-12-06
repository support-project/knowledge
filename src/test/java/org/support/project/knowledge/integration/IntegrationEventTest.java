package org.support.project.knowledge.integration;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.support.project.common.config.Resources;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.test.Order;
import org.support.project.common.util.PropertyUtil;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.logic.AggregateLogic;
import org.support.project.knowledge.logic.EventsLogic;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.TemplateLogic;
import org.support.project.web.bean.LabelValue;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.boundary.ForwardBoundary;
import org.support.project.web.boundary.JsonBoundary;
import org.support.project.web.common.HttpUtil;
import org.support.project.web.logic.HttpRequestCheckLogic;
import org.support.project.web.logic.impl.DefaultAuthenticationLogicImpl;
import org.support.project.web.test.stub.StubHttpServletRequest;
import org.support.project.web.test.stub.StubHttpServletResponse;

/**
 * 
 * @author koda
 */
public class IntegrationEventTest extends IntegrationCommon {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(IntegrationEventTest.class);
    
    private static final String POST_USER = "integration-test-user-01";
    private static final String JOIN_USER = "integration-test-user-02";
    
    private static long knowledgeId;
    
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
        addUser(JOIN_USER);
    }
    
    /**
     * イベント登録
     * @throws Exception
     */
    @Test
    @Order(order = 100)
    public void testPostEventPublic() throws Exception {
        // 登録画面へアクセスできること(パスのルーティングのみ確認）
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        request.setServletPath("protect.knowledge/view_add");
        request.setMethod("get");
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(POST_USER, request, response);
        
        ForwardBoundary boundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/knowledge/edit.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, boundary, "path"));

        String csrfToken = (String) request.getAttribute(HttpRequestCheckLogic.REQ_ID_KEY);
        Assert.assertNotNull(csrfToken);

        // 保存
        request.setServletPath("protect.knowledge/save");
        request.setMethod("post");
        request.addParameter(HttpRequestCheckLogic.REQ_ID_KEY, csrfToken);
        request.addParameter("title", "タイトル");
        request.addParameter("content", "内容");
        request.addParameter("publicFlag", String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PUBLIC));
        request.addParameter("typeId", String.valueOf(TemplateLogic.TYPE_ID_EVENT));
        request.addParameter("item_" + EventsLogic.ITEM_NO_DATE, "2017-10-01");
        request.addParameter("item_" + EventsLogic.ITEM_NO_START, "10:00");
        request.addParameter("item_" + EventsLogic.ITEM_NO_END, "12:00");
        request.addParameter("item_" + EventsLogic.ITEM_NO_TIMEZONE, "Asia/Tokyo");
        request.addParameter("item_" + EventsLogic.ITEM_NO_THE_NUMBER_TO_BE_ACCEPTED, "10");
        
        JsonBoundary jsonBoundary = invoke(request, response, JsonBoundary.class);
        MessageResult sendObject = (MessageResult) jsonBoundary.getObj();
        LOG.info(sendObject);
        Assert.assertEquals(200, sendObject.getCode().intValue());
        
        knowledgeId = new Long(sendObject.getResult());
    }
    
    /**
     * 投稿後の確認
     * @throws Exception
     */
    @Test
    @Order(order = 101)
    public void testAssertAfterPost() throws Exception {
        assertCP(POST_USER, 20);
        assertCP(JOIN_USER, 0);
        assertKnowledgeCP(null, knowledgeId, 20);
        
        execNotificationQueue();
        assertNotificationCount(POST_USER, 1);
        assertNotificationCount(JOIN_USER, 1);
    }
    
    /**
     * 参加者が参加登録
     * @throws Exception
     */
    @Test
    @Order(order = 200)
    public void testJoinByJoinUser() throws Exception {
        StubHttpServletRequest request = openKnowledges(JOIN_USER);
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        @SuppressWarnings("unchecked")
        List<KnowledgesEntity> knowledges = (List<KnowledgesEntity>) request.getAttribute("knowledges");
        long knowledgeId = knowledges.get(0).getKnowledgeId();
        request = openKnowledge(JOIN_USER, knowledgeId);
        
        request.setServletPath("protect.event/participation/" + knowledgeId);
        request.setMethod("put");
        JsonBoundary boundary = invoke(request, response, JsonBoundary.class);
        LabelValue labelValue = (LabelValue) boundary.getObj();
        Assert.assertEquals("true", labelValue.getValue());
    }
    
    /**
     * 参加登録後の確認
     * @throws Exception
     */
    @Test
    @Order(order = 201)
    public void testAssertAfterJoinbyJoinUser() throws Exception {
        assertCP(POST_USER, 6); // 参照 +1 / 参加 +5
        assertCP(JOIN_USER, 6);
        assertKnowledgeCP(null, knowledgeId, 6);
        
        // execNotificationQueue(); 参加登録はキューでメール通知しないで、リアルタイムで処理している
        assertNotificationCount(POST_USER, 1);
        assertNotificationCount(JOIN_USER, 1);
    }
    
    /**
     * 参加者が参加取りやめ
     * @throws Exception
     */
    @Test
    @Order(order = 202)
    public void testReaveByJoinUser() throws Exception {
        StubHttpServletRequest request = openKnowledges(JOIN_USER);
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        @SuppressWarnings("unchecked")
        List<KnowledgesEntity> knowledges = (List<KnowledgesEntity>) request.getAttribute("knowledges");
        long knowledgeId = knowledges.get(0).getKnowledgeId();
        request = openKnowledge(JOIN_USER, knowledgeId);
        
        request.setServletPath("protect.event/nonparticipation/" + knowledgeId);
        request.setMethod("delete");
        JsonBoundary boundary = invoke(request, response, JsonBoundary.class);
        String msg = (String) boundary.getObj();
        
        Resources resources = Resources.getInstance(HttpUtil.getLocale(request));
        Assert.assertEquals(resources.getResource("knowledge.view.msg.participate.delete"), msg);
    }
    
    /**
     * 参加取りやめ後の確認
     * @throws Exception
     */
    @Test
    @Order(order = 203)
    public void testAssertAfterReavebyJoinUser() throws Exception {
        assertCP(POST_USER, 0);
        assertCP(JOIN_USER, 0);
        assertKnowledgeCP(null, knowledgeId, 0);
        
        assertNotificationCount(POST_USER, 1);
        assertNotificationCount(JOIN_USER, 0);
    }
    
    /**
     * 再度、参加者が参加登録
     * @throws Exception
     */
    @Test
    @Order(order = 204)
    public void testReJoinByJoinUser() throws Exception {
        StubHttpServletRequest request = openKnowledges(JOIN_USER);
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        @SuppressWarnings("unchecked")
        List<KnowledgesEntity> knowledges = (List<KnowledgesEntity>) request.getAttribute("knowledges");
        long knowledgeId = knowledges.get(0).getKnowledgeId();
        request = openKnowledge(JOIN_USER, knowledgeId);
        
        request.setServletPath("protect.event/participation/" + knowledgeId);
        request.setMethod("put");
        JsonBoundary boundary = invoke(request, response, JsonBoundary.class);
        LabelValue labelValue = (LabelValue) boundary.getObj();
        Assert.assertEquals("true", labelValue.getValue());
    }
    
    /**
     * 参加取りやめ後、再度参加登録の確認
     * @throws Exception
     */
    @Test
    @Order(order = 205)
    public void testAssertAfterReJoinbyJoinUser() throws Exception {
        assertCP(POST_USER, 0);
        assertCP(JOIN_USER, 0);
        assertKnowledgeCP(null, knowledgeId, 0);
        
        assertNotificationCount(POST_USER, 1);
        assertNotificationCount(JOIN_USER, 1);
    }
    

    /**
     * 参加者が参加登録
     * @throws Exception
     */
    @Test
    @Order(order = 300)
    public void testJoinByPostUser() throws Exception {
        StubHttpServletRequest request = openKnowledges(POST_USER);
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        @SuppressWarnings("unchecked")
        List<KnowledgesEntity> knowledges = (List<KnowledgesEntity>) request.getAttribute("knowledges");
        long knowledgeId = knowledges.get(0).getKnowledgeId();
        request = openKnowledge(POST_USER, knowledgeId);
        
        request.setServletPath("protect.event/participation/" + knowledgeId);
        request.setMethod("put");
        JsonBoundary boundary = invoke(request, response, JsonBoundary.class);
        LabelValue labelValue = (LabelValue) boundary.getObj();
        Assert.assertEquals("true", labelValue.getValue());
    }
    
    /**
     * 参加登録後の確認
     * @throws Exception
     */
    @Test
    @Order(order = 301)
    public void testAssertAfterJoinbyPostUser() throws Exception {
        assertCP(POST_USER, 5);
        assertCP(JOIN_USER, 0);
        assertKnowledgeCP(null, knowledgeId, 5);
        
        // execNotificationQueue(); 参加登録はキューでメール通知しないで、リアルタイムで処理している
        assertNotificationCount(POST_USER, 2);
        assertNotificationCount(JOIN_USER, 0);
    }
    
    
    
    /**
     * CP獲得履歴
     * @throws Exception
     */
    @Test
    @Order(order = 600)
    public void testActivityHistory() throws Exception {
        assertPointHistoryCount(POST_USER, 4);
        assertPointHistoryCount(JOIN_USER, 2);
    }


    /**
     * 再集計を実行
     * @throws Exception
     */
    @Test
    @Order(order = 1000)
    public void testAggregate() throws Exception {
        AggregateLogic.get().startAggregate();
        // CPは変化しない
        assertCP(POST_USER, 0);
        assertCP(JOIN_USER, 0);
        assertKnowledgeCP(POST_USER, knowledgeId, 0);
    }


}
