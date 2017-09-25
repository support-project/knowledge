package org.support.project.knowledge.integration;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.test.Order;
import org.support.project.common.util.PropertyUtil;
import org.support.project.knowledge.bat.NotifyMailBat;
import org.support.project.knowledge.dao.NotifyQueuesDao;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.NotifyQueuesEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.notification.QueueNotification;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.boundary.ForwardBoundary;
import org.support.project.web.boundary.JsonBoundary;
import org.support.project.web.entity.NotificationsEntity;
import org.support.project.web.logic.HttpRequestCheckLogic;
import org.support.project.web.logic.impl.DefaultAuthenticationLogicImpl;
import org.support.project.web.test.stub.StubHttpServletRequest;
import org.support.project.web.test.stub.StubHttpServletResponse;

/**
 * 
 * @author koda
 */
public class IntegrationPostTest extends IntegrationCommon {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(IntegrationCommon.class);
    
    private static final String INTEGRATION_TEST_USER_02 = "integration-test-user-02";
    private static final String INTEGRATION_TEST_USER_01 = "integration-test-user-01";
    
    private static long knowledgeId; // テストメソッド単位にインスタンスが歳生成されるようなので、staticで保持する
    
    /**
     * ユーザを登録
     * 
     * @throws Exception
     */
    @Test
    @Order(order = 1)
    public void testUserInsert() throws Exception {
        LOG.info("ユーザ登録");
        addUser(INTEGRATION_TEST_USER_01);
        addUser(INTEGRATION_TEST_USER_02);
    }

    /**
     * 未ログインユーザで、Knowledge一覧を開く(1件も無し）
     * 
     * @throws Exception
     */
    @Test
    @Order(order = 2)
    public void testAccessListByNoLoginUser() throws Exception {
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        request.setServletPath("open.knowledge/list");
        request.setMethod("get");
        
        invoke(request, response, ForwardBoundary.class);
        
        List<KnowledgesEntity> knowledges = (List<KnowledgesEntity>) request.getAttribute("knowledges");
        Assert.assertNotNull(knowledges);
        Assert.assertEquals(0, knowledges.size());
    }

    /**
     * ログインユーザでKnowledge一覧を開く(1件も無し）
     * 
     * @throws Exception
     */
    @Test
    @Order(order = 3)
    public void testAccessListByLoginedUser() throws Exception {
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        request.setServletPath("open.knowledge/list");
        request.setMethod("get");
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(INTEGRATION_TEST_USER_01, request, response);
        
        invoke(request, response, ForwardBoundary.class);
        
        List<KnowledgesEntity> knowledges = (List<KnowledgesEntity>) request.getAttribute("knowledges");
        Assert.assertNotNull(knowledges);
        Assert.assertEquals(0, knowledges.size());
    }
    
    /**
     * ログインユーザで、通知の確認（1件なし）
     * @throws Exception
     */
    @Test
    @Order(order = 4)
    public void testInitNotificationByLoginedUser() throws Exception {
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        request.setServletPath("protect.notification/list");
        request.setMethod("get");
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(INTEGRATION_TEST_USER_01, request, response);
        
        ForwardBoundary boundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/notification/list.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, boundary, "path"));
        
        List<NotificationsEntity> notifications = (List<NotificationsEntity>) request.getAttribute("notifications");
        Assert.assertNotNull(notifications);
        Assert.assertEquals(0, notifications.size());
    }

    /**
     * CPの確認（ユーザ1と2）
     * @throws Exception
     */
    @Test
    @Order(order = 5)
    public void testInitCPByLoginedUser() throws Exception {
        LoginedUser user = super.getLoginUser(INTEGRATION_TEST_USER_01);
        
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        request.setServletPath("open.account/info/" + user.getUserId());
        request.setMethod("get");
        
        ForwardBoundary boundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/open/account/account.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, boundary, "path"));
        Assert.assertEquals((long) 0, request.getAttribute("point"));
        
        LoginedUser user2 = super.getLoginUser(INTEGRATION_TEST_USER_02);
        request.setServletPath("open.account/info/" + user2.getUserId());
        
        boundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/open/account/account.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, boundary, "path"));
        Assert.assertEquals((long) 0, request.getAttribute("point"));
    }

    
    
    
    /**
     * ユーザ2で「公開」の記事を1件追加
     * 
     * @throws Exception
     */
    @Test
    @Order(order = 100)
    public void testPostPublic() throws Exception {
        // 登録画面へアクセスできること(パスのルーティングのみ確認）
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        request.setServletPath("protect.knowledge/view_add");
        request.setMethod("get");
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(INTEGRATION_TEST_USER_02, request, response);
        
        ForwardBoundary boundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/knowledge/edit.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, boundary, "path"));

        String csrfToken = (String) request.getAttribute(HttpRequestCheckLogic.REQ_ID_KEY);
        Assert.assertNotNull(csrfToken);

        // 保存失敗（登録する値を何もセットしていない）
        request.setServletPath("protect.knowledge/save");
        request.setMethod("post");
        request.addParameter(HttpRequestCheckLogic.REQ_ID_KEY, csrfToken);
        
        JsonBoundary jsonBoundary = invoke(request, response, JsonBoundary.class);
        MessageResult sendObject = (MessageResult) jsonBoundary.getObj();
        LOG.info(sendObject);
        Assert.assertEquals(400, sendObject.getCode().intValue());

        // 保存
        request.addParameter("title", "タイトル");
        request.addParameter("content", "内容");
        request.addParameter("publicFlag", String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PUBLIC));
        
        jsonBoundary = invoke(request, response, JsonBoundary.class);
        sendObject = (MessageResult) jsonBoundary.getObj();
        LOG.info(sendObject);
        Assert.assertEquals(200, sendObject.getCode().intValue());
        
        knowledgeId = new Long(sendObject.getResult());
    }

    /**
     * 未ログインユーザで、Knowledgeを参照
     * 
     * @throws Exception
     */
    @Test
    @Order(order = 101)
    public void testAccessListByNoLoginUser2() throws Exception {
        // 一覧を表示
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        request.setServletPath("open.knowledge/list");
        request.setMethod("get");
        
        invoke(request, response, ForwardBoundary.class);
        
        List<KnowledgesEntity> knowledges = (List<KnowledgesEntity>) request.getAttribute("knowledges");
        Assert.assertNotNull(knowledges);
        Assert.assertEquals(1, knowledges.size());

        KnowledgesEntity knowledge = knowledges.get(0);
        Assert.assertEquals("タイトル", knowledge.getTitle());
        Assert.assertEquals("内容", knowledge.getContent());
        Assert.assertEquals(KnowledgeLogic.PUBLIC_FLAG_PUBLIC, knowledge.getPublicFlag().intValue());

        // 詳細表示
        request.setServletPath("open.knowledge/view/" + knowledge.getKnowledgeId());
        
        invoke(request, response, ForwardBoundary.class);
        
        Assert.assertEquals("タイトル", request.getAttribute("title"));
        Assert.assertEquals("<p>内容</p>\n", request.getAttribute("content")); // Markdownが処理されている
        Assert.assertEquals(KnowledgeLogic.PUBLIC_FLAG_PUBLIC, request.getAttribute("publicFlag"));
    }

    /**
     * ログインユーザでKnowledgeを参照
     * 
     * @throws Exception
     */
    @Test
    @Order(order = 102)
    public void testAccessListByLoginedUser2() throws Exception {
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        request.setServletPath("open.knowledge/list");
        request.setMethod("get");
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(INTEGRATION_TEST_USER_01, request, response);
        
        invoke(request, response, ForwardBoundary.class);
        
        List<KnowledgesEntity> knowledges = (List<KnowledgesEntity>) request.getAttribute("knowledges");
        Assert.assertNotNull(knowledges);
        Assert.assertEquals(1, knowledges.size());

        KnowledgesEntity knowledge = knowledges.get(0);
        Assert.assertEquals("タイトル", knowledge.getTitle());
        Assert.assertEquals("内容", knowledge.getContent());
        Assert.assertEquals(KnowledgeLogic.PUBLIC_FLAG_PUBLIC, knowledge.getPublicFlag().intValue());

        // 詳細表示
        request.setServletPath("open.knowledge/view/" + knowledge.getKnowledgeId());
        invoke(request, response, ForwardBoundary.class);
        
        Assert.assertEquals("タイトル", request.getAttribute("title"));
        Assert.assertEquals("<p>内容</p>\n", request.getAttribute("content")); // Markdownが処理されている
        Assert.assertEquals(KnowledgeLogic.PUBLIC_FLAG_PUBLIC, request.getAttribute("publicFlag"));
    }
    
    /**
     * ログインユーザで、通知の確認(未だ何も届いていない）
     * @throws Exception
     */
    @Test
    @Order(order = 200)
    public void testNothingNotificationByLoginedUser1() throws Exception {
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        request.setServletPath("protect.notification/list");
        request.setMethod("get");
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(INTEGRATION_TEST_USER_01, request, response);
        
        ForwardBoundary boundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/notification/list.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, boundary, "path"));
        
        List<NotificationsEntity> notifications = (List<NotificationsEntity>) request.getAttribute("notifications");
        Assert.assertNotNull(notifications);
        Assert.assertEquals(0, notifications.size());
    }

    /**
     * ログインユーザ2で、通知の確認(未だ何も届いていない）
     * @throws Exception
     */
    @Test
    @Order(order = 201)
    public void testNothingNotificationByLoginedUser2() throws Exception {
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        request.setServletPath("protect.notification/list");
        request.setMethod("get");
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(INTEGRATION_TEST_USER_02, request, response);
        
        ForwardBoundary boundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/notification/list.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, boundary, "path"));
        
        List<NotificationsEntity> notifications = (List<NotificationsEntity>) request.getAttribute("notifications");
        Assert.assertNotNull(notifications);
        Assert.assertEquals(0, notifications.size());
    }
    
    /**
     * 通知を処理
     * @throws Exception
     */
    @Test
    @Order(order = 202)
    public void testExecNotification() throws Exception {
        NotifyQueuesEntity notify = NotifyQueuesDao.get().selectOnTypeAndId(QueueNotification.TYPE_KNOWLEDGE_INSERT, knowledgeId);
        Assert.assertNotNull(notify);
        NotifyMailBat.main(null);
        notify = NotifyQueuesDao.get().selectOnTypeAndId(QueueNotification.TYPE_KNOWLEDGE_INSERT, knowledgeId);
        Assert.assertNull(notify);
    }

    /**
     * ログインユーザで、通知の確認
     * @throws Exception
     */
    @Test
    @Order(order = 203)
    public void testNotificationByLoginedUser1() throws Exception {
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        request.setServletPath("protect.notification/list");
        request.setMethod("get");
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(INTEGRATION_TEST_USER_01, request, response);
        
        ForwardBoundary boundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/notification/list.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, boundary, "path"));
        
        List<NotificationsEntity> notifications = (List<NotificationsEntity>) request.getAttribute("notifications");
        Assert.assertNotNull(notifications);
        Assert.assertEquals(1, notifications.size()); // 登録通知が届いている
    }

    /**
     * ログインユーザ2で、通知の確認
     * TODO 登録者本人には、通知を出さなくても良いかも（今のところ出している）
     * @throws Exception
     */
    @Test
    @Order(order = 204)
    public void testNotificationByLoginedUser2() throws Exception {
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        request.setServletPath("protect.notification/list");
        request.setMethod("get");
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(INTEGRATION_TEST_USER_02, request, response);
        
        ForwardBoundary boundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/notification/list.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, boundary, "path"));
        
        List<NotificationsEntity> notifications = (List<NotificationsEntity>) request.getAttribute("notifications");
        Assert.assertNotNull(notifications);
        Assert.assertEquals(1, notifications.size()); // 登録通知が届いている
    }
    
    
}
