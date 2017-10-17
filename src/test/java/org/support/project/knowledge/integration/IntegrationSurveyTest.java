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
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.TargetLogic;
import org.support.project.knowledge.logic.TemplateLogic;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.bean.Msg;
import org.support.project.web.boundary.ForwardBoundary;
import org.support.project.web.boundary.JsonBoundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.logic.HttpRequestCheckLogic;
import org.support.project.web.logic.impl.DefaultAuthenticationLogicImpl;
import org.support.project.web.test.stub.StubHttpServletRequest;
import org.support.project.web.test.stub.StubHttpServletResponse;

/**
 * 
 * @author koda
 */
public class IntegrationSurveyTest extends IntegrationCommon {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(IntegrationSurveyTest.class);
    
    private static final String POST_USER = "integration-test-user-01";
    private static final String ANSWER_USER = "integration-test-user-02";
    
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
        addUser(ANSWER_USER);
    }
    
    /**
     * 「公開」の記事登録
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
        request.addParameter("typeId", String.valueOf(TemplateLogic.TYPE_ID_KNOWLEDGE));
        
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
        assertKnowledgeCP(POST_USER, knowledgeId, 50);
        assertCP(POST_USER, 50);
        assertKnowledgeCP(ANSWER_USER, knowledgeId, 1);
        assertCP(ANSWER_USER, 1);
        assertCP(POST_USER, 1);
        
        execNotificationQueue();
        assertNotificationCount(POST_USER, 1);
        assertNotificationCount(ANSWER_USER, 1);
    }
    
    @Test
    @Order(order = 200)
    public void testCreateSurvey() throws Exception {
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        
        request.setServletPath("protect.survey/load/" + knowledgeId);
        request.setMethod("get");
        JsonBoundary msg = invoke(request, response, JsonBoundary.class);
        Msg result = (Msg) msg.getObj();
        Assert.assertEquals("survey data is not exists.", result.getMsg());
        
        request.setServletPath("protect.survey/save");
        request.setMethod("post");
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(POST_USER, request, response);
        
        String csrfToken = (String) request.getAttribute(HttpRequestCheckLogic.REQ_ID_KEY);
        request.addParameter(HttpRequestCheckLogic.REQ_ID_KEY, csrfToken);
        
        request.addParameter("knowledgeId", String.valueOf(knowledgeId));
        request.addParameter("typeName", "アンケート");
        request.addParameter("itemType", "text_item0");
        request.addParameter("title_item0", "入力項目1");
        
        JsonBoundary boundary = invoke(request, response, JsonBoundary.class);
        MessageResult messageResult = (MessageResult) boundary.getObj();
        LOG.info(messageResult);
        Assert.assertEquals(HttpStatus.SC_200_OK, messageResult.getCode().intValue());
    }

    
    /**
     * アンケート作成後の確認
     * @throws Exception
     */
    @Test
    @Order(order = 201)
    public void testAssertAfterCreateSurvey() throws Exception {
        assertKnowledgeCP(POST_USER, knowledgeId, 0);
        assertCP(POST_USER, 0);
        assertKnowledgeCP(ANSWER_USER, knowledgeId, 0);
        assertCP(ANSWER_USER, 0);
        
        List<NotifyQueuesEntity> list = NotifyQueuesDao.get().selectAll();
        Assert.assertEquals(0, list.size());
        assertNotificationCount(POST_USER, 0);
        assertNotificationCount(ANSWER_USER, 0);
    }

    /**
     * アンケートに回答
     * @throws Exception
     */
    @Test
    @Order(order = 300)
    public void testAnswerSurvey() throws Exception {
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        
        request.setServletPath("protect.survey/load/" + knowledgeId);
        request.setMethod("get");
        JsonBoundary survey = invoke(request, response, JsonBoundary.class);
        LOG.info(survey);
        Assert.assertNotNull(survey);
        
        request.setServletPath("protect.survey/answer");
        request.setMethod("post");
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(ANSWER_USER, request, response);
        
        String csrfToken = (String) request.getAttribute(HttpRequestCheckLogic.REQ_ID_KEY);
        request.addParameter(HttpRequestCheckLogic.REQ_ID_KEY, csrfToken);
        
        request.addParameter("knowledgeId", String.valueOf(knowledgeId));
        request.addParameter("item_0", "回答");
        
        JsonBoundary boundary = invoke(request, response, JsonBoundary.class);
        MessageResult messageResult = (MessageResult) boundary.getObj();
        LOG.info(messageResult);
        Assert.assertEquals(HttpStatus.SC_200_OK, messageResult.getCode().intValue());
    }

    /**
     * アンケート作成後の確認
     * @throws Exception
     */
    @Test
    @Order(order = 301)
    public void testAssertAfterAnswer() throws Exception {
        assertKnowledgeCP(POST_USER, knowledgeId, 3);
        assertCP(POST_USER, 3);
        assertCP(ANSWER_USER, 3);
        
        List<NotifyQueuesEntity> list = NotifyQueuesDao.get().selectAll();
        Assert.assertEquals(0, list.size());
        assertNotificationCount(POST_USER, 0);
        assertNotificationCount(ANSWER_USER, 0);
    }

    /**
     * CP獲得履歴
     * @throws Exception
     */
    @Test
    @Order(order = 600)
    public void testActivityHistory() throws Exception {
        assertPointHistoryCount(POST_USER, 3);
        assertPointHistoryCount(ANSWER_USER, 2);
    }
    
    /**
     * アンケートの編集権限の確認
     * @throws Exception
     */
    @Test
    @Order(order = 700)
    public void testEditAble() throws Exception {
        // 登録
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
        request.setServletPath("protect.knowledge/save");
        request.setMethod("post");
        request.addParameter(HttpRequestCheckLogic.REQ_ID_KEY, csrfToken);
        request.addParameter("title", "タイトル");
        request.addParameter("content", "内容");
        request.addParameter("publicFlag", String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PROTECT));
        request.addParameter("typeId", String.valueOf(TemplateLogic.TYPE_ID_KNOWLEDGE));
        LoginedUser answerUser = super.getLoginUser(ANSWER_USER);
        request.addParameter("groups", TargetLogic.ID_PREFIX_USER + answerUser.getUserId());
        request.addParameter("editors", TargetLogic.ID_PREFIX_USER + answerUser.getUserId());
        JsonBoundary jsonBoundary = invoke(request, response, JsonBoundary.class);
        MessageResult sendObject = (MessageResult) jsonBoundary.getObj();
        LOG.info(sendObject);
        Assert.assertEquals(200, sendObject.getCode().intValue());
        
        Long knowledgeId = new Long(sendObject.getResult());
        
        // アンケート登録
        request = new StubHttpServletRequest();
        response = new StubHttpServletResponse(request);
        request.setServletPath("protect.survey/load/" + knowledgeId);
        request.setMethod("get");
        JsonBoundary msg = invoke(request, response, JsonBoundary.class);
        Msg result = (Msg) msg.getObj();
        Assert.assertEquals("survey data is not exists.", result.getMsg());
        request.setServletPath("protect.survey/save");
        request.setMethod("post");
        auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(ANSWER_USER, request, response);
        csrfToken = (String) request.getAttribute(HttpRequestCheckLogic.REQ_ID_KEY);
        request.addParameter(HttpRequestCheckLogic.REQ_ID_KEY, csrfToken);
        request.addParameter("knowledgeId", String.valueOf(knowledgeId));
        request.addParameter("typeName", "アンケート");
        request.addParameter("itemType", "text_item0");
        request.addParameter("title_item0", "入力項目1");
        jsonBoundary = invoke(request, response, JsonBoundary.class);
        MessageResult messageResult = (MessageResult) jsonBoundary.getObj();
        LOG.info(messageResult);
        Assert.assertEquals(HttpStatus.SC_200_OK, messageResult.getCode().intValue());
    }
    
    
    
}
