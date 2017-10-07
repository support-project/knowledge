package org.support.project.knowledge.integration;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.FileUtil;
import org.support.project.common.util.PropertyUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.common.util.SystemUtils;
import org.support.project.knowledge.TestCommon;
import org.support.project.knowledge.bat.MailSendBat;
import org.support.project.knowledge.bat.NotifyMailBat;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.AuthType;
import org.support.project.knowledge.dao.NotifyQueuesDao;
import org.support.project.knowledge.dao.TemplateItemsDao;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.NotifyQueuesEntity;
import org.support.project.knowledge.logic.EventsLogic;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.TemplateLogic;
import org.support.project.knowledge.vo.ActivityHistory;
import org.support.project.knowledge.vo.LikeCount;
import org.support.project.knowledge.vo.api.Knowledge;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.bean.Msg;
import org.support.project.web.bean.NameId;
import org.support.project.web.boundary.ForwardBoundary;
import org.support.project.web.boundary.JsonBoundary;
import org.support.project.web.boundary.RedirectBoundary;
import org.support.project.web.boundary.SendMessageBoundary;
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
    
    private static boolean isInitCallControlLogic = false;
    private static boolean isMailSend = false;
    
    private static Map<String, Long> userPointMap = new HashMap<>();
    private static Map<Long, Long> knowledgePointMap = new HashMap<>();
    private static Map<String, Integer> userNotificationCountMap = new HashMap<>();
    private static Map<Long, Integer> knowledgeLikeMap = new HashMap<>();
    private static Map<Long, Integer> commentLikeMap = new HashMap<>();
    
    /**
     * @BeforeClass
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        TestCommon.setUpBeforeClass();
        
        userPointMap = new HashMap<>();
        knowledgePointMap = new HashMap<>();
        userNotificationCountMap = new HashMap<>();
        knowledgeLikeMap = new HashMap<>();
        commentLikeMap = new HashMap<>();
        

        if (!isInitCallControlLogic) {
            String controlPackage = "org.support.project.knowledge.control,org.support.project.web.control";
            String classSuffix = "Control";
            String ignoreRegularExpression = "^/ws|^/template|^/bower|css$|js$|ico$|html$";
            CallControlLogic.get().init(controlPackage, classSuffix, true, ignoreRegularExpression);
            isInitCallControlLogic = true;
        }
        
        MailConfigsEntity mailConfig = new MailConfigsEntity(AppConfig.get().getSystemName());
        mailConfig.setHost("localhost");
        mailConfig.setPort(1025);
        mailConfig.setAuthType(AuthType.None.getValue());
        MailConfigsDao.get().insert(mailConfig); // メール送信設定
        String testMailSend = SystemUtils.getenv("KNOWLEDGE_TEST_MAIL");
        if (StringUtils.isNotEmpty(testMailSend) && testMailSend.toLowerCase().equals("true")) {
            // テスト用のメールサーバーが存在するので送信まで実施する
            isMailSend = true;
        }
    }
    /**
     * コントローラーを呼び出し
     * @param request
     * @param response
     * @param clazz
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    protected <T> T invoke(HttpServletRequest request, HttpServletResponse response, Class<T> clazz) throws Exception {
        InvokeTarget invoke = CallControlLogic.get().searchInvokeTarget(request, response);
        if (invoke == null) {
            LOG.error("InvokeTarget is not find. [Method]" + request.getMethod() + " [Path]" + request.getServletPath());
        }
        Assert.assertNotNull(invoke);
        Object result = invoke.invoke();
        LOG.trace(result);
        Assert.assertNotNull(result);
        if (!result.getClass().isAssignableFrom(clazz)) {
            Assert.fail("Result is not " + clazz.getSimpleName() + ". actual: " + result.getClass().getSimpleName());
        }
        return (T) result;
    }
    
    
    /**
     * 投稿
     * @return
     * @throws Exception
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    protected MessageResult postKnowledge(String userKey, int publicFlag, int typeId, String viewers) throws Exception, NoSuchFieldException, IllegalAccessException {
        // 登録画面へアクセスできること(パスのルーティングのみ確認）
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        request.setServletPath("protect.knowledge/view_add");
        request.setMethod("get");
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(userKey, request, response);
        
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
        request.addParameter("publicFlag", String.valueOf(publicFlag));
        request.addParameter("typeId", String.valueOf(typeId));
        
        if (typeId == TemplateLogic.TYPE_ID_BOOKMARK) {
            request.addParameter("item_" + TemplateItemsDao.ITEM_ID_BOOKMARK_URL, "https://information-supportproject.org/");
        } else if (typeId == TemplateLogic.TYPE_ID_EVENT) {
            request.addParameter("item_" + EventsLogic.ITEM_NO_DATE, "2017-10-01");
            request.addParameter("item_" + EventsLogic.ITEM_NO_START, "10:00");
            request.addParameter("item_" + EventsLogic.ITEM_NO_END, "12:00");
            request.addParameter("item_" + EventsLogic.ITEM_NO_TIMEZONE, "Asia/Tokyo");
            request.addParameter("item_" + EventsLogic.ITEM_NO_THE_NUMBER_TO_BE_ACCEPTED, "10");
        }
        
        if (publicFlag == KnowledgeLogic.PUBLIC_FLAG_PROTECT) {
            if (StringUtils.isNotEmpty(viewers)) {
                request.addParameter("groups", viewers);
            }
        }
        
        JsonBoundary jsonBoundary = invoke(request, response, JsonBoundary.class);
        MessageResult sendObject = (MessageResult) jsonBoundary.getObj();
        LOG.info(sendObject);
        Assert.assertEquals(200, sendObject.getCode().intValue());
        return sendObject;
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
    @SuppressWarnings("unchecked")
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
     * 指定の記事にアクセスできないことを確認する
     * @param userKey
     * @param knowledgeId
     * @throws Exception
     */
    protected void assertNotAccessAble(String userKey, long knowledgeId, int httpStatus) throws Exception {
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        request.setServletPath("open.knowledge/view/" + knowledgeId);
        request.setMethod("get");
        if (StringUtils.isNotEmpty(userKey)) {
            DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
            auth.setSession(userKey, request, response);
        }
        SendMessageBoundary boundary = invoke(request, response, SendMessageBoundary.class);
        Assert.assertEquals(httpStatus, boundary.getStatus());
    }
    
    /**
     * いいねを押下
     * @param userKey
     * @param knowledgeId
     * @return
     * @throws Exception
     */
    private LikeCount like(String userKey, long knowledgeId) throws Exception {
        StubHttpServletRequest request = openKnowledge(userKey, knowledgeId); //CSRF
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
     * いいねを押下し、いいねの件数が+1されていることを確認
     * @param userKey
     * @param knowledgeId
     * @throws Exception
     */
    protected void addLike(String userKey, long knowledgeId) throws Exception {
        if (!knowledgeLikeMap.containsKey(knowledgeId)) {
            knowledgeLikeMap.put(knowledgeId, 0);
        }
        int now = knowledgeLikeMap.get(knowledgeId);
        now = now + 1;
        knowledgeLikeMap.put(knowledgeId, now);
        LikeCount like = like(userKey, knowledgeId);
        Assert.assertEquals(now, like.getCount().longValue());
    }
    
    /**
     * コメントにイイネを押す
     * @param integrationTestUser02
     * @param knowledgeId
     * @param commentNo
     * @return
     */
    private LikeCount likeComment(String userKey, long knowledgeId, Long commentNo) throws Exception {
        StubHttpServletRequest request = openKnowledge(userKey, knowledgeId); // CSRF
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
     * コメントにイイネを押し、いいねの件数が+1されていることを確認
     * @param userKey
     * @param knowledgeId
     * @throws Exception
     */
    protected void addLikeComment(String userKey, long knowledgeId, long commentNo) throws Exception {
        if (!commentLikeMap.containsKey(commentNo)) {
            commentLikeMap.put(commentNo, 0);
        }
        int now = commentLikeMap.get(commentNo);
        now = now + 1;
        commentLikeMap.put(commentNo, now);
        
        LikeCount like = likeComment(userKey, knowledgeId, commentNo);
        Assert.assertEquals(now, like.getCount().longValue());
    }
    /**
     * コメントにイイネを押し、いいねの件数が+1されていることを確認
     * @param userKey
     * @param knowledgeId
     * @throws Exception
     */
    protected void addLatestLikeComment(String userKey, long knowledgeId) throws Exception {
        CommentsEntity comment = getLatestComment(userKey, knowledgeId);
        if (comment == null) {
            Assert.fail("comment not exists");
        }
        addLikeComment(userKey, knowledgeId, comment.getCommentNo());
    }
    
    
    
    /**
     * コメント登録
     * @param userKey
     * @param knowledgeId
     * @param comment
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    protected void comment(String userKey, long knowledgeId, String comment) throws Exception {
        if (userKey == null) {
            Assert.fail("post comment must be logined");
        }
        StubHttpServletRequest request = openKnowledge(userKey, knowledgeId); //CSRF
        List<CommentsEntity> comments = (List<CommentsEntity>) request.getAttribute("comments");
        int commentCount = comments.size();
        
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
        
        // 登録確認（1件追加されている）
        request = openKnowledge(userKey, knowledgeId);
        comments = (List<CommentsEntity>) request.getAttribute("comments");
        Assert.assertEquals(commentCount +1, comments.size());
    }
    /**
     * 最新のコメントを取得
     * @param userKey
     * @param knowledgeId
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    protected CommentsEntity getLatestComment(String userKey, long knowledgeId) throws Exception {
        StubHttpServletRequest request = openKnowledge(userKey, knowledgeId);
        List<CommentsEntity> comments = (List<CommentsEntity>) request.getAttribute("comments");
        CommentsEntity comment = comments.get(comments.size() - 1);
        return comment;
    }
    
    
    /**
     * キューにある通知を処理する
     * @throws Exception
     */
    protected void execNotificationQueue() throws Exception {
        List<NotifyQueuesEntity> list = NotifyQueuesDao.get().selectAll();
        Assert.assertTrue(0 < list.size());
        NotifyMailBat.main(null);
        list = NotifyQueuesDao.get().selectAll();
        Assert.assertEquals(0, list.size());
        if (isMailSend) {
            MailSendBat.main(null);
        }
    }
    
    /**
     * WebAPIでKnowledgeをPOST（新規登録）
     * @param userKey
     * @param jsonResource
     * @return
     * @throws Exception
     */
    protected long knowledgePostOnAPI(String userKey, String jsonResource) throws Exception {
        String json = FileUtil.read(this.getClass().getResourceAsStream(jsonResource));
        LOG.info(json);
        
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(userKey, request, response);
        request.setServletPath("api/knowledges");
        request.setMethod("post");
        request.setInputstream(new ByteArrayInputStream(json.getBytes("utf-8")));
        JsonBoundary boundary = invoke(request, response, JsonBoundary.class);
        Object result = boundary.getObj();
        LOG.info(result);
        if (result instanceof NameId) {
            NameId nameId = (NameId) boundary.getObj();
            return new Long(nameId.getId());
        }
        Assert.fail("result is not NameId object.");
        return -1;
    }
    /**
     * WebAPIでKnowledgeをPUT（更新）
     * @param userKey
     * @param jsonResource
     * @return
     * @throws Exception
     */
    protected void knowledgePutOnAPI(String userKey, String jsonResource, long knowledgeId) throws Exception {
        String json = FileUtil.read(this.getClass().getResourceAsStream(jsonResource));
        LOG.info(json);
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(userKey, request, response);
        request.setServletPath("api/knowledges/" + knowledgeId);
        request.setMethod("put");
        request.setInputstream(new ByteArrayInputStream(json.getBytes("utf-8")));
        JsonBoundary boundary = invoke(request, response, JsonBoundary.class);
        Object result = boundary.getObj();
        LOG.info(result);
        if (result instanceof Msg) {
            Msg msg = (Msg) boundary.getObj();
            Assert.assertEquals("updated", msg.getMsg());
        } else {
            Assert.fail("result is not Msg object.");
        }
    }
    /**
     * WebAPIでKnowledgeをDelete
     * @param userKey
     * @param knowledgeId
     * @return
     * @throws Exception
     */
    protected void knowledgeDeleteOnAPI(String userKey, long knowledgeId) throws Exception {
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(userKey, request, response);
        request.setServletPath("api/knowledges/" + knowledgeId);
        request.setMethod("delete");
        JsonBoundary boundary = invoke(request, response, JsonBoundary.class);
        Object result = boundary.getObj();
        LOG.info(result);
        if (result instanceof Msg) {
            Msg msg = (Msg) boundary.getObj();
            Assert.assertEquals("deleted", msg.getMsg());
        } else {
            Assert.fail("result is not Msg object.");
        }
    }
    /**
     * Knowledgeの一覧を取得し、一覧の最初のものを取得（リストの先頭は、最後に登録したもの）
     * 同時に一覧で取得できる件数はチェックする
     * 0件が期待値であった場合、Nullを返す
     * @param userKey
     * @param count
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    protected Knowledge knowledgeGetOnAPI(String userKey, int count) throws Exception {
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(userKey, request, response);
        // 一覧取得
        request.setServletPath("api/knowledges");
        request.setMethod("get");
        JsonBoundary boundary = invoke(request, response, JsonBoundary.class);
        Object result = boundary.getObj();
        long lastestKnowledgeId = -1;
        if (result instanceof List) {
            List<Knowledge> results = (List<Knowledge>) boundary.getObj();
            Assert.assertEquals(count, results.size());
            if (count > 0) {
                lastestKnowledgeId = results.get(0).getKnowledgeId();
            }
        } else {
            Assert.fail("result is not List object.");
        }
        if (count > 0) {
            request.setServletPath("api/knowledges/" + lastestKnowledgeId);
            request.setMethod("get");
            boundary = invoke(request, response, JsonBoundary.class);
            result = boundary.getObj();
            if (result instanceof Knowledge) {
                Knowledge knowledge = (Knowledge) boundary.getObj();
                return knowledge;
            } else {
                Assert.fail("result is not Knowledge object.");
            }
        }
        return null;
    }
    
    /**
     * CPの獲得履歴の件数確認
     * @param userKey
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    protected void assertPointHistoryCount(String userKey, int count) throws Exception {
        StubHttpServletRequest request = new StubHttpServletRequest();
        StubHttpServletResponse response = new StubHttpServletResponse(request);
        DefaultAuthenticationLogicImpl auth = org.support.project.di.Container.getComp(DefaultAuthenticationLogicImpl.class);
        auth.setSession(userKey, request, response);
        
        LoginedUser user = getLoginUser(userKey);
        request.setServletPath("open.account/activity/" + user.getUserId());
        request.setMethod("get");
        JsonBoundary jsonBoundary = invoke(request, response, JsonBoundary.class);
        List<ActivityHistory> list = (List<ActivityHistory>) jsonBoundary.getObj();
        Assert.assertEquals(count, list.size());
    }

}
