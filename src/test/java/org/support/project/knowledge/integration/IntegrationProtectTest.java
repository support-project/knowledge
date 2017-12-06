package org.support.project.knowledge.integration;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.test.Order;
import org.support.project.common.util.PropertyUtil;
import org.support.project.knowledge.dao.NotifyQueuesDao;
import org.support.project.knowledge.dao.TemplateItemsDao;
import org.support.project.knowledge.entity.NotifyQueuesEntity;
import org.support.project.knowledge.logic.AggregateLogic;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.TargetLogic;
import org.support.project.knowledge.logic.TemplateLogic;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.boundary.ForwardBoundary;
import org.support.project.web.boundary.JsonBoundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.logic.HttpRequestCheckLogic;
import org.support.project.web.logic.impl.DefaultAuthenticationLogicImpl;
import org.support.project.web.test.stub.StubHttpServletRequest;
import org.support.project.web.test.stub.StubHttpServletResponse;

/**
 * 保護の記事の操作のテスト
 * @author koda
 */
public class IntegrationProtectTest extends IntegrationCommon {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(IntegrationProtectTest.class);
    
    private static final String POST_USER = "integration-test-user-01";
    private static final String USER1 = "integration-test-user-02";
    
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
        addUser(USER1);
    }
    
    /**
     * 保護の記事を登録
     * @throws Exception
     */
    @Test
    @Order(order = 100)
    public void testPost() throws Exception {
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
        request.addParameter("typeId", String.valueOf(TemplateLogic.TYPE_ID_BOOKMARK));
        request.addParameter("item_" + TemplateItemsDao.ITEM_ID_BOOKMARK_URL, "https://information-supportproject.org/");
        request.addParameter("publicFlag", String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PROTECT));
        
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
        assertCP(POST_USER, 10);
        assertCP(USER1, 0);
        assertKnowledgeCP(POST_USER, knowledgeId, 10);
        assertNotAccessAble(USER1, knowledgeId, HttpStatus.SC_404_NOT_FOUND); // アクセス権が無い場合、403では無く404にしている

        execNotificationQueue();
        assertNotificationCount(POST_USER, 1);
        assertNotificationCount(USER1, 0);
    }
    
    /**
     * 保護の記事を更新
     * @throws Exception
     */
    @Test
    @Order(order = 200)
    public void testUpdate() throws Exception {
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
        request.addParameter("knowledgeId", String.valueOf(1));
        request.addParameter(HttpRequestCheckLogic.REQ_ID_KEY, csrfToken);
        request.addParameter("title", "タイトル");
        request.addParameter("content", "内容");
        request.addParameter("typeId", String.valueOf(TemplateLogic.TYPE_ID_BOOKMARK));
        request.addParameter("item_" + TemplateItemsDao.ITEM_ID_BOOKMARK_URL, "https://information-supportproject.org/");
        request.addParameter("publicFlag", String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PROTECT));
        request.addParameter("updateContent", "true");
        
        LoginedUser user = super.getLoginUser(USER1);
        request.addParameter("groups", TargetLogic.ID_PREFIX_USER + user.getUserId());
        
        JsonBoundary jsonBoundary = invoke(request, response, JsonBoundary.class);
        MessageResult sendObject = (MessageResult) jsonBoundary.getObj();
        LOG.info(sendObject);
        Assert.assertEquals(200, sendObject.getCode().intValue());
    }
    
    /**
     * 更新後の確認
     * @throws Exception
     */
    @Test
    @Order(order = 201)
    public void testAssertAfterUpdate() throws Exception {
        super.openKnowledge(USER1, 1); // USER1でアクセスできるようになっている
        assertCP(POST_USER, 1);
        assertCP(USER1, 1); // 参照でポイントアップ
        assertKnowledgeCP(POST_USER, knowledgeId, 1);

        execNotificationQueue();
        assertNotificationCount(POST_USER, 1);
        assertNotificationCount(USER1, 1);
    }
    
    
    /**
     * 保護の記事を更新
     * @throws Exception
     */
    @Test
    @Order(order = 300)
    public void testUpdateOnlyMeta() throws Exception {
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
        request.addParameter("knowledgeId", String.valueOf(1));
        request.addParameter(HttpRequestCheckLogic.REQ_ID_KEY, csrfToken);
        request.addParameter("title", "タイトル");
        request.addParameter("content", "内容");
        request.addParameter("typeId", String.valueOf(TemplateLogic.TYPE_ID_BOOKMARK));
        request.addParameter("item_" + TemplateItemsDao.ITEM_ID_BOOKMARK_URL, "https://information-supportproject.org/");
        request.addParameter("publicFlag", String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PROTECT));
        request.addParameter("updateContent", "false");
        
        LoginedUser user = super.getLoginUser(USER1);
        request.addParameter("groups", TargetLogic.ID_PREFIX_USER + user.getUserId());
        
        request.addParameter("tagNames", "タグのみを追加したパターン");
        
        JsonBoundary jsonBoundary = invoke(request, response, JsonBoundary.class);
        MessageResult sendObject = (MessageResult) jsonBoundary.getObj();
        LOG.info(sendObject);
        Assert.assertEquals(200, sendObject.getCode().intValue());
    }
    
    /**
     * 更新後の確認
     * @throws Exception
     */
    @Test
    @Order(order = 301)
    public void testAssertAfterUpdateOnlyMeta() throws Exception {
        StubHttpServletRequest request = super.openKnowledge(USER1, 1);
        Assert.assertEquals("タグのみを追加したパターン", request.getAttribute("tagNames"));
        
        assertCP(POST_USER, 0);
        assertCP(USER1, 0);
        assertKnowledgeCP(POST_USER, knowledgeId, 0);

        // 通知無し
        List<NotifyQueuesEntity> list = NotifyQueuesDao.get().selectAll();
        Assert.assertEquals(0, list.size());
        
        assertNotificationCount(POST_USER, 0);
        assertNotificationCount(USER1, 0);
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
        assertCP(USER1, 0);
        assertKnowledgeCP(POST_USER, knowledgeId, 0);
    }



}
