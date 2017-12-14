package org.support.project.knowledge.integration;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.test.Order;
import org.support.project.common.util.PropertyUtil;
import org.support.project.knowledge.dao.NotifyQueuesDao;
import org.support.project.knowledge.entity.NotifyQueuesEntity;
import org.support.project.knowledge.vo.api.Knowledge;
import org.support.project.web.boundary.ForwardBoundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.logic.impl.DefaultAuthenticationLogicImpl;
import org.support.project.web.test.stub.StubHttpServletRequest;
import org.support.project.web.test.stub.StubHttpServletResponse;

public class IntegrationAPITest extends IntegrationCommon {
    
    /** ログ */
    private static final Log LOG = LogFactory.getLog(IntegrationAPITest.class);
    
    private static final String USER_01 = "integration-test-user-01";

    /**
     * ユーザを登録
     * @throws Exception
     */
    @Test
    @Order(order = 1)
    public void testUserInsert() throws Exception {
        LOG.info("ユーザ登録");
        addUser(USER_01);
        
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(USER_01, request, response);
        // 参照画面を開く(CSRF Token)
        request.setServletPath("protect.token/index");
        request.setMethod("get");
        ForwardBoundary boundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/token/index.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, boundary, "path"));
        
        // アクセスTokenを発行
        request.setServletPath("protect.token/save");
        request.setMethod("post");
        boundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/token/index.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, boundary, "path"));

        String token = (String) request.getAttribute("token");
        Assert.assertNotNull(token);
    }
    
    /**
     * KnowledgeをPost
     * @throws Exception
     */
    @Test
    @Order(order = 10)
    public void testPost() throws Exception {
        long knowledgeId = knowledgePostOnAPI(USER_01, "post_knowledge.json");
        Assert.assertEquals(1, knowledgeId);
    }
    
    /**
     * Knowledgeを参照
     * @throws Exception
     */
    @Test
    @Order(order = 11)
    public void testViewAfterPost() throws Exception {
        Knowledge result = knowledgeGetOnAPI(USER_01, 1);
        Assert.assertEquals((long) 1, result.getKnowledgeId().longValue());
        Assert.assertEquals("タイトル", result.getTitle());
        Assert.assertEquals("コンテンツ", result.getContent());
    }
    
    /**
     * 登録後の状態を確認
     * @throws Exception
     */
    @Test
    @Order(order = 12)
    public void testCheckDataAfterPost() throws Exception {
        assertCP(USER_01, 20);
        assertKnowledgeCP(USER_01, 1, 20);

        execNotificationQueue();
        assertNotificationCount(USER_01, 1);
    }
    
    /**
     * KnowledgeをPut
     * @throws Exception
     */
    @Test
    @Order(order = 20)
    public void testPut() throws Exception {
        knowledgePutOnAPI(USER_01, "put_knowledge.json", 1);
    }
    
    /**
     * Knowledgeを参照
     * @throws Exception
     */
    @Test
    @Order(order = 21)
    public void testViewAfterPut() throws Exception {
        Knowledge result = knowledgeGetOnAPI(USER_01, 1);
        Assert.assertEquals((long) 1, result.getKnowledgeId().longValue());
        Assert.assertEquals("タイトル更新", result.getTitle());
        Assert.assertEquals("コンテンツ更新", result.getContent());
    }

    /**
     * 更新後の状態を確認
     * @throws Exception
     */
    @Test
    @Order(order = 22)
    public void testCheckDataAfterPut() throws Exception {
        assertCP(USER_01, 0);
        assertKnowledgeCP(USER_01, 1, 0);

        execNotificationQueue();
        assertNotificationCount(USER_01, 1);
    }
    
    /**
     * KnowledgeをPut
     * @throws Exception
     */
    @Test
    @Order(order = 30)
    public void testDelete() throws Exception {
        knowledgeDeleteOnAPI(USER_01, 1);
    }
    
    /**
     * Knowledgeを参照
     * @throws Exception
     */
    @Test
    @Order(order = 31)
    public void testViewAfterDelete() throws Exception {
        knowledgeGetOnAPI(USER_01, 0);
    }
    
    /**
     * 削除後の状態を確認
     * @throws Exception
     */
    @Test
    @Order(order = 32)
    public void testCheckDataAfterDelete() throws Exception {
        assertCP(USER_01, 0);
        assertNotAccessAble(USER_01, 1, HttpStatus.SC_404_NOT_FOUND);
        
        // 削除後には通知はでない
        List<NotifyQueuesEntity> list = NotifyQueuesDao.get().selectAll();
        Assert.assertEquals(0, list.size());
        assertNotificationCount(USER_01, 0);
    }
    
}
