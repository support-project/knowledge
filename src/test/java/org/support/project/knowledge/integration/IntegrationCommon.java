package org.support.project.knowledge.integration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.PropertyUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.knowledge.TestCommon;
import org.support.project.knowledge.bat.NotifyMailBat;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.AuthType;
import org.support.project.knowledge.dao.NotifyQueuesDao;
import org.support.project.knowledge.entity.NotifyQueuesEntity;
import org.support.project.knowledge.vo.LikeCount;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.boundary.ForwardBoundary;
import org.support.project.web.boundary.JsonBoundary;
import org.support.project.web.boundary.RedirectBoundary;
import org.support.project.web.common.InvokeTarget;
import org.support.project.web.dao.MailConfigsDao;
import org.support.project.web.entity.MailConfigsEntity;
import org.support.project.web.entity.NotificationsEntity;
import org.support.project.web.logic.CallControlLogic;
import org.support.project.web.logic.HttpRequestCheckLogic;
import org.support.project.web.logic.impl.DefaultAuthenticationLogicImpl;
import org.support.project.web.test.stub.StubHttpServletRequest;
import org.support.project.web.test.stub.StubHttpServletResponse;

public abstract class IntegrationCommon extends TestCommon {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(IntegrationCommon.class);
    
    private static Map<String, Long> userPointMap = new HashMap<>();
    private static Map<Long, Long> knowledgePointMap = new HashMap<>();
    private static Map<String, Integer> userNotificationCountMap = new HashMap<>();

    
    /**
     * @BeforeClass
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        TestCommon.setUpBeforeClass();
        String controlPackage = "org.support.project.knowledge.control,org.support.project.web.control";
        String classSuffix = "Control";
        String ignoreRegularExpression = "^/ws|^/template|^/bower|css$|js$|ico$|html$";
        CallControlLogic.get().init(controlPackage, classSuffix, true, ignoreRegularExpression);
        
        MailConfigsEntity mailConfig = new MailConfigsEntity(AppConfig.get().getSystemName());
        mailConfig.setHost("example.com");
        mailConfig.setPort(25);
        mailConfig.setAuthType(AuthType.None.getValue());
        MailConfigsDao.get().insert(mailConfig); // メール送信設定
    }
    /**
     * コントローラーを呼び出し
     * @param request
     * @param response
     * @param clazz
     * @return
     * @throws Exception
     */
    protected <T> T invoke(HttpServletRequest request, HttpServletResponse response, Class<T> clazz) throws Exception {
        InvokeTarget invoke = CallControlLogic.get().searchInvokeTarget(request, response);
        Assert.assertNotNull(invoke);
        Object result = invoke.invoke();
        LOG.info(result);
        Assert.assertNotNull(result);
        if (!result.getClass().isAssignableFrom(clazz)) {
            Assert.fail("Result is not " + clazz.getSimpleName() + ". actual: " + result.getClass().getSimpleName());
        }
        return (T) result;
    }
    /**
     * CP確認
     * @param userKey
     * @param cp
     * @throws Exception
     */
    protected void assertCP(String userKey, long cp) throws Exception {
        LoginedUser user = super.getLoginUser(userKey);
        
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        request.setServletPath("open.account/info/" + user.getUserId());
        request.setMethod("get");
        ForwardBoundary boundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/open/account/account.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, boundary, "path"));
        if (!userPointMap.containsKey(userKey)) {
            userPointMap.put(userKey, new Long(0));
        }
        long now = userPointMap.get(userKey);
        now = now + cp;
        userPointMap.put(userKey, now);
        Assert.assertEquals(now, request.getAttribute("point"));
    }
    /**
     * 記事のCPを確認
     * （記事にアクセス可能なユーザを指定すること）
     * @param userKey
     * @param knowledgeId
     * @param cp
     * @throws Exception
     */
    protected void assertKnowledgeCP(String userKey, long knowledgeId, long cp) throws Exception {
        StubHttpServletRequest request = openKnowledge(userKey, knowledgeId);
        if (!knowledgePointMap.containsKey(knowledgeId)) {
            knowledgePointMap.put(knowledgeId, new Long(0));
        }
        long now = knowledgePointMap.get(knowledgeId);
        now = now + cp;
        knowledgePointMap.put(knowledgeId, now);
        Assert.assertEquals(now, request.getAttribute("point"));
    }
    /**
     * ユーザに届いている通知の件数を確認
     * @param userKey
     * @param add
     * @throws Exception
     */
    protected void assertNotificationCount(String userKey, int add) throws Exception {
        List<NotificationsEntity> notifications = getNotification(userKey);
        
        if (!userNotificationCountMap.containsKey(userKey)) {
            userNotificationCountMap.put(userKey, 0);
        }
        int now = userNotificationCountMap.get(userKey);
        now = now + add;
        userNotificationCountMap.put(userKey, now);
        Assert.assertEquals(now, notifications.size());
    }
    
    
    /**
     * 通知を取得
     * @param userKey
     * @return
     * @throws Exception
     */
    protected List<NotificationsEntity> getNotification(String userKey) throws Exception {
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        request.setServletPath("protect.notification/list");
        request.setMethod("get");
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(userKey, request, response);
        
        ForwardBoundary boundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/notification/list.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, boundary, "path"));
        
        List<NotificationsEntity> notifications = (List<NotificationsEntity>) request.getAttribute("notifications");
        Assert.assertNotNull(notifications);
        return notifications;
    }
    /**
     * 記事の一覧を取得
     * @param userKey
     * @return
     * @throws Exception 
     */
    protected StubHttpServletRequest openKnowledges(String userKey) throws Exception {
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        request.setServletPath("open.knowledge/list");
        request.setMethod("get");
        if (StringUtils.isNotEmpty(userKey)) {
            DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
            auth.setSession(userKey, request, response);
        }
        invoke(request, response, ForwardBoundary.class);
        return request;
    }
    
    /**
     * 記事の参照ページを開く
     * @param userKey
     * @return
     * @throws Exception
     */
    protected StubHttpServletRequest openKnowledge(String userKey, long knowledgeId) throws Exception {
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        request.setServletPath("open.knowledge/view/" + knowledgeId);
        request.setMethod("get");
        if (StringUtils.isNotEmpty(userKey)) {
            DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
            auth.setSession(userKey, request, response);
        }
        ForwardBoundary boundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/open/knowledge/view.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, boundary, "path"));
        return request;
    }
    /**
     * いいねを押下
     * @param userKey
     * @param knowledgeId
     * @return
     * @throws Exception
     */
    protected LikeCount like(String userKey, long knowledgeId) throws Exception {
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        request.setServletPath("open.knowledge/like/" + knowledgeId);
        request.setMethod("post");
        if (StringUtils.isNotEmpty(userKey)) {
            DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
            auth.setSession(userKey, request, response);
        }
        JsonBoundary boundary = invoke(request, response, JsonBoundary.class);
        return (LikeCount) boundary.getObj();
    }
    /**
     * コメントにイイネを押す
     * @param integrationTestUser02
     * @param knowledgeId
     * @param commentNo
     * @return
     */
    protected LikeCount likeComment(String userKey, long knowledgeId, Long commentNo) throws Exception {
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        request.setServletPath("open.knowledge/likecomment/" + commentNo);
        request.setMethod("post");
        if (StringUtils.isNotEmpty(userKey)) {
            DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
            auth.setSession(userKey, request, response);
        }
        JsonBoundary boundary = invoke(request, response, JsonBoundary.class);
        return (LikeCount) boundary.getObj();
    }
    
    
    /**
     * コメント登録
     * @param userKey
     * @param knowledgeId
     * @param comment
     * @throws Exception
     */
    protected void comment(String userKey, long knowledgeId, String comment) throws Exception {
        if (userKey == null) {
            Assert.fail("post comment must be logined");
        }
        StubHttpServletRequest request = openKnowledge(userKey, knowledgeId); //CSRF
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        request.setServletPath("protect.knowledge/comment/" + knowledgeId);
        request.setMethod("post");
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(userKey, request, response);
        request.addParameter("addcomment", "コメント");
        
        String csrfToken = (String) request.getAttribute(HttpRequestCheckLogic.REQ_ID_KEY);
        request.addParameter(HttpRequestCheckLogic.REQ_ID_KEY, csrfToken);
        
        RedirectBoundary boundary = invoke(request, response, RedirectBoundary.class);
        Assert.assertNotNull(boundary);
    }
    
    /**
     * キューにある通知を処理する
     * @throws Exception
     */
    protected void execNotificationQueue() throws Exception {
        List<NotifyQueuesEntity> list = NotifyQueuesDao.get().selectAll();
        Assert.assertEquals(1, list.size());
        NotifyMailBat.main(null);
        list = NotifyQueuesDao.get().selectAll();
        Assert.assertEquals(0, list.size());
    }

}
