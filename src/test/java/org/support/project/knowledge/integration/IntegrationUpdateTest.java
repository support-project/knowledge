package org.support.project.knowledge.integration;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.test.Order;
import org.support.project.common.util.PropertyUtil;
import org.support.project.knowledge.dao.NotifyQueuesDao;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.NotifyQueuesEntity;
import org.support.project.knowledge.logic.AggregateLogic;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.TemplateLogic;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.boundary.ForwardBoundary;
import org.support.project.web.boundary.JsonBoundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.logic.HttpRequestCheckLogic;
import org.support.project.web.logic.impl.DefaultAuthenticationLogicImpl;
import org.support.project.web.test.stub.StubHttpServletRequest;
import org.support.project.web.test.stub.StubHttpServletResponse;

/**
 * 記事をいろいろ変えてみた場合のテスト
 * @author koda
 */
public class IntegrationUpdateTest extends IntegrationCommon {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(IntegrationProtectTest.class);
    
    private static final String POST_USER = "integration-test-user-01";
    private static final String USER1 = "integration-test-user-02";
    private static long knowledgeId;
    
    /**
     * 更新を実行
     * @throws Exception
     */
    private void doUpdate(int publicflag, boolean updateContent) throws Exception {
        // 編集画面を開く
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        request.setServletPath("protect.knowledge/view_edit/" + knowledgeId);
        request.setMethod("get");
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(POST_USER, request, response);
        ForwardBoundary boundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/knowledge/edit.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, boundary, "path"));
        String csrfToken = (String) request.getAttribute(HttpRequestCheckLogic.REQ_ID_KEY);
        Assert.assertNotNull(csrfToken);

        // 保存
        request = new StubHttpServletRequest(request);
        response = new StubHttpServletResponse(request);
        request.setServletPath("protect.knowledge/save");
        request.setMethod("post");
        request.addParameter("knowledgeId", String.valueOf(1));
        request.addParameter(HttpRequestCheckLogic.REQ_ID_KEY, csrfToken);
        request.addParameter("title", "タイトル");
        request.addParameter("content", "内容");
        request.addParameter("typeId", String.valueOf(TemplateLogic.TYPE_ID_KNOWLEDGE));
        request.addParameter("publicFlag", String.valueOf(publicflag));
        request.addParameter("updateContent", String.valueOf(updateContent));
        
        JsonBoundary jsonBoundary = invoke(request, response, JsonBoundary.class);
        MessageResult sendObject = (MessageResult) jsonBoundary.getObj();
        LOG.info(sendObject);
        Assert.assertEquals(200, sendObject.getCode().intValue());
    }
        
    
    /**
     * ユーザを登録
     * @throws Exception
     */
    @Test
    @Order(order = 1)
    public void testUserInsert() throws Exception {
        LOG.info("ユーザ登録");
        addUser(POST_USER);
        addUser(USER1);
    }
    /**
     * 記事を登録(非公開）
     * @throws Exception
     */
    @Test
    @Order(order = 100)
    public void testPostPrivate() throws Exception {
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
        request.addParameter("typeId", String.valueOf(TemplateLogic.TYPE_ID_KNOWLEDGE));
        request.addParameter("publicFlag", String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PRIVATE));
        
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
        assertCP(POST_USER, 0);
        assertCP(USER1, 0);
        assertKnowledgeCP(POST_USER, knowledgeId, 0); // POST_USERではアクセスできる（CPを確認できる）
        assertNotAccessAble(USER1, knowledgeId, HttpStatus.SC_404_NOT_FOUND); // アクセス権が無い場合、403では無く404にしている

        execNotificationQueue();
        assertNotificationCount(POST_USER, 0);
        assertNotificationCount(USER1, 0);
    }
    
    
    

    /**
     * 記事を更新
     * @throws Exception
     */
    @Test
    @Order(order = 200)
    public void testAssertAfterUpdatePrivateToProtect() throws Exception {
        doUpdate(KnowledgeLogic.PUBLIC_FLAG_PROTECT, true);
        assertCP(POST_USER, 10);
        assertCP(USER1, 0);
        assertKnowledgeCP(POST_USER, knowledgeId, 10); // POST_USERではアクセスできる（CPを確認できる）
        assertNotAccessAble(USER1, knowledgeId, HttpStatus.SC_404_NOT_FOUND); // アクセス権が無い場合、403では無く404にしている

        execNotificationQueue();
        assertNotificationCount(POST_USER, 1);
        assertNotificationCount(USER1, 0);
    }

    /**
     * 記事を更新
     * @throws Exception
     */
    @Test
    @Order(order = 300)
    public void testAssertAfterUpdateProtectToPublic() throws Exception {
        doUpdate(KnowledgeLogic.PUBLIC_FLAG_PUBLIC, true);
        
        assertKnowledgeCP(POST_USER, knowledgeId, 10); // POST_USERではアクセスできる（CPを確認できる）
        assertKnowledgeCP(USER1, knowledgeId, 1); //参照したので１ポイント追加
        
        assertCP(POST_USER, 11);
        assertCP(USER1, 1);

        execNotificationQueue();
        assertNotificationCount(POST_USER, 1);
        assertNotificationCount(USER1, 1);
    }
    
    /**
     * 記事を更新(公開→公開）
     * @throws Exception
     */
    @Test
    @Order(order = 400)
    public void testAssertAfterUpdatePublicToPublic() throws Exception {
        doUpdate(KnowledgeLogic.PUBLIC_FLAG_PUBLIC, true);
        
        assertKnowledgeCP(POST_USER, knowledgeId, 0);
        assertKnowledgeCP(USER1, knowledgeId, 0);
        
        assertCP(POST_USER, 0);
        assertCP(USER1, 0);

        execNotificationQueue();
        assertNotificationCount(POST_USER, 1);
        assertNotificationCount(USER1, 1);
    }
    
    /**
     * 記事を更新(公開→保護）
     * @throws Exception
     */
    @Test
    @Order(order = 500)
    public void testAssertAfterUpdatePublicToProtect() throws Exception {
        doUpdate(KnowledgeLogic.PUBLIC_FLAG_PROTECT, true);
        
        assertKnowledgeCP(POST_USER, knowledgeId, -10);
        assertNotAccessAble(USER1, knowledgeId, HttpStatus.SC_404_NOT_FOUND);
        
        assertCP(POST_USER, -10);
        assertCP(USER1, 0);

        execNotificationQueue();
        assertNotificationCount(POST_USER, 1);
        assertNotificationCount(USER1, 0);
    }
    
    /**
     * 記事を更新(保護→非公開）
     * @throws Exception
     */
    @Test
    @Order(order = 600)
    public void testAssertAfterUpdateProtectToPrivate() throws Exception {
        doUpdate(KnowledgeLogic.PUBLIC_FLAG_PRIVATE, true);
        
        assertKnowledgeCP(POST_USER, knowledgeId, -10);
        assertNotAccessAble(USER1, knowledgeId, HttpStatus.SC_404_NOT_FOUND);
        
        assertCP(POST_USER, -10);
        assertCP(USER1, 0);

        execNotificationQueue();
        assertNotificationCount(POST_USER, 0);
        assertNotificationCount(USER1, 0);
    }

    /**
     * 記事を更新(非公開→公開）
     * @throws Exception
     */
    @Test
    @Order(order = 700)
    public void testAssertAfterUpdatePrivateToPublic() throws Exception {
        doUpdate(KnowledgeLogic.PUBLIC_FLAG_PUBLIC, true);
        
        assertKnowledgeCP(POST_USER, knowledgeId, 20);
        assertKnowledgeCP(USER1, knowledgeId, 0);
        
        assertCP(POST_USER, 20);
        assertCP(USER1, 0);

        execNotificationQueue();
        assertNotificationCount(POST_USER, 1);
        assertNotificationCount(USER1, 1);
    }

    /**
     * 記事を更新(非公開→公開）
     * @throws Exception
     */
    @Test
    @Order(order = 800)
    public void testAssertAfterUpdateNotNotify() throws Exception {
        doUpdate(KnowledgeLogic.PUBLIC_FLAG_PUBLIC, false);
        
        assertKnowledgeCP(POST_USER, knowledgeId, 0);
        assertKnowledgeCP(USER1, knowledgeId, 0);
        
        assertCP(POST_USER, 0);
        assertCP(USER1, 0);

        List<NotifyQueuesEntity> list = NotifyQueuesDao.get().selectAll();
        Assert.assertEquals(0, list.size());

        assertNotificationCount(POST_USER, 0);
        assertNotificationCount(USER1, 0);
    }

    
    /**
     * 記事を削除
     * @throws Exception
     */
    @Test
    @Order(order = 900)
    public void testDelete() throws Exception {
        // 編集画面を開く
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        request.setServletPath("protect.knowledge/view_edit/" + knowledgeId);
        request.setMethod("get");
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(POST_USER, request, response);
        ForwardBoundary boundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/knowledge/edit.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, boundary, "path"));
        String csrfToken = (String) request.getAttribute(HttpRequestCheckLogic.REQ_ID_KEY);
        Assert.assertNotNull(csrfToken);

        // 削除
        request = new StubHttpServletRequest(request);
        response = new StubHttpServletResponse(request);
        request.setServletPath("protect.knowledge/delete");
        request.setMethod("post");
        request.addParameter("knowledgeId", String.valueOf(1));
        request.addParameter(HttpRequestCheckLogic.REQ_ID_KEY, csrfToken);
        boundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/open/knowledge/list.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, boundary, "path"));
        @SuppressWarnings("unchecked")
        List<KnowledgesEntity> knowledges = (List<KnowledgesEntity>) request.getAttribute("knowledges");
        Assert.assertNotNull(knowledges);
        Assert.assertEquals(0, knowledges.size());
    }
    
    
    /**
     * CP獲得履歴
     * @throws Exception
     */
    @Test
    @Order(order = 1000)
    public void testActivityHistory() throws Exception {
        assertPointHistoryCount(POST_USER, 6);
        assertPointHistoryCount(USER1, 1);
    }





}
