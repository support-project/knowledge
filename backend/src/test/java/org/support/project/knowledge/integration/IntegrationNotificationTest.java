package org.support.project.knowledge.integration;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.test.Order;
import org.support.project.common.util.PropertyUtil;
import org.support.project.common.util.StringJoinBuilder;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.TemplateLogic;
import org.support.project.web.boundary.ForwardBoundary;
import org.support.project.web.boundary.JsonBoundary;
import org.support.project.web.entity.NotificationsEntity;
import org.support.project.web.logic.impl.DefaultAuthenticationLogicImpl;
import org.support.project.web.test.stub.StubHttpServletRequest;
import org.support.project.web.test.stub.StubHttpServletResponse;

/**
 * 
 * @author koda
 */
public class IntegrationNotificationTest extends IntegrationCommon {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(IntegrationNotificationTest.class);
    
    private static final String POST_USER = "integration-test-user-01";
    private static final String READ_USER = "integration-test-user-02";
    
    private List<NotificationsEntity> getList(String userKey, StubHttpServletRequest request, StubHttpServletResponse response, boolean all) throws Exception {
        request.setServletPath("protect.notification/list");
        request.setMethod("get");
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(userKey, request, response);
        if (all) {
            request.addParameter("all", "true");
        }
        
        ForwardBoundary boundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/notification/list.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, boundary, "path"));
        
        @SuppressWarnings("unchecked")
        List<NotificationsEntity> notifications = (List<NotificationsEntity>) request.getAttribute("notifications");
        return notifications;
    }
    
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
        addUser(READ_USER);
    }
    
    /**
     * 「公開」の記事登録（通知の登録）
     * @throws Exception
     */
    @Test
    @Order(order = 100)
    public void testNotification() throws Exception {
        // 投稿
        postKnowledge(
                POST_USER,
                KnowledgeLogic.PUBLIC_FLAG_PUBLIC,
                TemplateLogic.TYPE_ID_KNOWLEDGE,
                null);
        // 通知
        execNotificationQueue();
        
        // 通知にアクセス（List）
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        List<NotificationsEntity> notifications = getList(READ_USER, request, response, false);
        Assert.assertEquals(1, notifications.size());
        
        NotificationsEntity notification = notifications.get(0);
        Assert.assertEquals(0, notification.getStatus());
        
        // 通知を表示
        request.setServletPath("protect.notification/view/" + notification.getNo());
        ForwardBoundary boundary2 = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/notification/view.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, boundary2, "path"));
        
        Assert.assertEquals(1, request.getAttribute("status"));
    }
    

    /**
     * 「公開」の記事登録（通知の登録）
     * @throws Exception
     */
    @Test
    @Order(order = 101)
    public void testReadAll() throws Exception {
        // 投稿
        for (int i = 0; i < 10; i++) {
            postKnowledge(
                    POST_USER,
                    KnowledgeLogic.PUBLIC_FLAG_PUBLIC,
                    TemplateLogic.TYPE_ID_KNOWLEDGE,
                    null);
        }
        // 通知
        execNotificationQueue();
        
        // 通知にアクセス（List）
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        List<NotificationsEntity> notifications = getList(READ_USER, request, response, false);
        Assert.assertEquals(10, notifications.size());
        
        // 全て既読へ
        StringJoinBuilder<Long> builder = new StringJoinBuilder<>();
        for (NotificationsEntity notificationsEntity : notifications) {
            builder.append(notificationsEntity.getNo());
        }
        
        request.setServletPath("protect.notification/markread");
        request.setMethod("post");
        request.addParameter("no", builder.join(","));
        JsonBoundary jsonBoundary = invoke(request, response, JsonBoundary.class);
        Assert.assertEquals("OK", jsonBoundary.getObj());
        
        // 通知にアクセス（List）
        notifications = getList(READ_USER, request, response, false);
        Assert.assertEquals(0, notifications.size());
        
        // 通知にアクセス（List）
        notifications = getList(READ_USER, request, response, true);
        Assert.assertEquals(11, notifications.size());
    }

    
    /**
     * 次へのボタンの操作
     * @throws Exception
     */
    @Test
    @Order(order = 102)
    public void testNext() throws Exception {
        // 投稿
        for (int i = 0; i < 3; i++) {
            postKnowledge(
                    POST_USER,
                    KnowledgeLogic.PUBLIC_FLAG_PUBLIC,
                    TemplateLogic.TYPE_ID_KNOWLEDGE,
                    null);
        }
        // 通知
        execNotificationQueue();
        
        // 通知にアクセス（List）
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        List<NotificationsEntity> notifications = getList(READ_USER, request, response, false);
        Assert.assertEquals(3, notifications.size());
        
        // 通知を表示
        NotificationsEntity notification = notifications.get(2);
        request.setServletPath("protect.notification/view/" + notification.getNo());
        ForwardBoundary boundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/notification/view.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, boundary, "path"));
        Assert.assertEquals(1, request.getAttribute("status"));
        Assert.assertEquals(notification.getNo(), request.getAttribute("no"));
        
        // Nextをクリック
        request.setServletPath("protect.notification/next/" + notification.getNo());
        boundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/notification/view.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, boundary, "path"));
        Assert.assertEquals(1, request.getAttribute("status"));
        notification = notifications.get(1);
        Assert.assertEquals(notification.getNo(), request.getAttribute("no"));
        
        // Nextをクリック
        request.setServletPath("protect.notification/next/" + notification.getNo());
        boundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/notification/view.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, boundary, "path"));
        Assert.assertEquals(1, request.getAttribute("status"));
        notification = notifications.get(0);
        Assert.assertEquals(notification.getNo(), request.getAttribute("no"));
        
        // Nextをクリック
        request.setServletPath("protect.notification/next/" + notification.getNo());
        boundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/notification/not_found.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, boundary, "path"));
        
        // リストの先頭に戻ってNextをクリック
        notification = notifications.get(2);
        request.setServletPath("protect.notification/next/" + notification.getNo());
        boundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/notification/not_found.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, boundary, "path"));
        
        // 全てを表示のモードでNextをクリック
        request.setServletPath("protect.notification/next/" + notification.getNo());
        request.addParameter("all", "true");
        boundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/notification/view.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, boundary, "path"));
        Assert.assertEquals(1, request.getAttribute("status"));
        notification = notifications.get(1);
        Assert.assertEquals(notification.getNo(), request.getAttribute("no"));
    }
    

    /**
     * 前へのボタンの操作
     * @throws Exception
     */
    @Test
    @Order(order = 103)
    public void testPrevious() throws Exception {
        // 投稿
        for (int i = 0; i < 3; i++) {
            postKnowledge(
                    POST_USER,
                    KnowledgeLogic.PUBLIC_FLAG_PUBLIC,
                    TemplateLogic.TYPE_ID_KNOWLEDGE,
                    null);
        }
        // 通知
        execNotificationQueue();
        
        // 通知にアクセス（List）
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        List<NotificationsEntity> notifications = getList(READ_USER, request, response, false);
        Assert.assertEquals(3, notifications.size());
        
        // 通知を表示
        NotificationsEntity notification = notifications.get(0);
        request.setServletPath("protect.notification/view/" + notification.getNo());
        ForwardBoundary boundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/notification/view.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, boundary, "path"));
        Assert.assertEquals(1, request.getAttribute("status"));
        Assert.assertEquals(notification.getNo(), request.getAttribute("no"));
        
        // previousをクリック
        request.setServletPath("protect.notification/previous/" + notification.getNo());
        boundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/notification/view.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, boundary, "path"));
        Assert.assertEquals(1, request.getAttribute("status"));
        notification = notifications.get(1);
        Assert.assertEquals(notification.getNo(), request.getAttribute("no"));
        
        // previousをクリック
        request.setServletPath("protect.notification/previous/" + notification.getNo());
        boundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/notification/view.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, boundary, "path"));
        Assert.assertEquals(1, request.getAttribute("status"));
        notification = notifications.get(2);
        Assert.assertEquals(notification.getNo(), request.getAttribute("no"));
        
        // previousをクリック
        request.setServletPath("protect.notification/previous/" + notification.getNo());
        boundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/notification/not_found.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, boundary, "path"));
        
        // リストの先頭に戻ってpreviousをクリック
        notification = notifications.get(0);
        request.setServletPath("protect.notification/previous/" + notification.getNo());
        boundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/notification/not_found.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, boundary, "path"));
        
        // 全てを表示のモードでpreviousをクリック
        request.setServletPath("protect.notification/previous/" + notification.getNo());
        request.addParameter("all", "true");
        boundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/notification/view.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, boundary, "path"));
        Assert.assertEquals(1, request.getAttribute("status"));
        notification = notifications.get(1);
        Assert.assertEquals(notification.getNo(), request.getAttribute("no"));
    }


}
