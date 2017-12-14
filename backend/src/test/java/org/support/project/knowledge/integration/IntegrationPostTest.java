package org.support.project.knowledge.integration;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.test.Order;
import org.support.project.common.util.PropertyUtil;
import org.support.project.knowledge.TestCommon;
import org.support.project.knowledge.dao.NotifyQueuesDao;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.NotifyQueuesEntity;
import org.support.project.knowledge.logic.AggregateLogic;
import org.support.project.knowledge.logic.KnowledgeLogic;
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
    private static final Log LOG = LogFactory.getLog(IntegrationPostTest.class);
    
    private static final String COMMENT_POST_USER = "integration-test-user-01";
    private static final String KNOWLEDGE_POST_USER = "integration-test-user-02";
    private static final String READ_USER = "integration-test-user-03";
    
    private static long knowledgeId; // テストメソッド単位にインスタンスが歳生成されるようなので、staticで保持する
    
    @Before
    public void setUp() throws Exception {
        Thread.sleep(TestCommon.WAIT_MILLSEC);
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
        addUser(COMMENT_POST_USER);
        addUser(KNOWLEDGE_POST_USER);
        addUser(READ_USER);
    }

    /**
     * 未ログインユーザで、Knowledge一覧を開く(1件も無し）
     * 
     * @throws Exception
     */
    @Test
    @Order(order = 2)
    public void testAccessListByNoLoginUser() throws Exception {
        StubHttpServletRequest request = openKnowledges(null);
        @SuppressWarnings("unchecked")
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
        StubHttpServletRequest request = openKnowledges(COMMENT_POST_USER);
        @SuppressWarnings("unchecked")
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
        auth.setSession(COMMENT_POST_USER, request, response);
        
        ForwardBoundary boundary = invoke(request, response, ForwardBoundary.class);
        Assert.assertEquals("/WEB-INF/views/protect/notification/list.jsp", PropertyUtil.getPrivateFeildOnReflection(String.class, boundary, "path"));
        
        @SuppressWarnings("unchecked")
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
        assertCP(COMMENT_POST_USER, 0);
        assertCP(KNOWLEDGE_POST_USER, 0);
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
        auth.setSession(KNOWLEDGE_POST_USER, request, response);
        
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
     * CPの確認（ユーザ1と2）
     * @throws Exception
     */
    @Test
    @Order(order = 101)
    public void testCPAfterPost() throws Exception {
        assertCP(COMMENT_POST_USER, 0);
        assertCP(KNOWLEDGE_POST_USER, 20);
        assertKnowledgeCP(null, knowledgeId, 20);
    }
    
    

    /**
     * 未ログインユーザで、Knowledgeを参照
     * @throws Exception
     */
    @Test
    @Order(order = 102)
    public void testAccessListByNoLoginUser2() throws Exception {
        // 一覧表示画面を開き、
        StubHttpServletRequest request = openKnowledges(null);
        @SuppressWarnings("unchecked")
        List<KnowledgesEntity> knowledges = (List<KnowledgesEntity>) request.getAttribute("knowledges");
        Assert.assertNotNull(knowledges);
        Assert.assertEquals(1, knowledges.size());
        KnowledgesEntity knowledge = knowledges.get(0);
        Assert.assertEquals("タイトル", knowledge.getTitle());
        Assert.assertEquals("内容", knowledge.getContent());
        Assert.assertEquals(KnowledgeLogic.PUBLIC_FLAG_PUBLIC, knowledge.getPublicFlag().intValue());
        
        // 詳細表示
        request = openKnowledge(null, knowledgeId);
        Assert.assertEquals("タイトル", request.getAttribute("title"));
        Assert.assertEquals("<p>内容</p>\n", request.getAttribute("content")); // Markdownが処理されている
        Assert.assertEquals(KnowledgeLogic.PUBLIC_FLAG_PUBLIC, request.getAttribute("publicFlag"));
    }

    /**
     * ログインユーザでKnowledgeを参照
     * @throws Exception
     */
    @Test
    @Order(order = 103)
    public void testAccessListByLoginedUser2() throws Exception {
        // 一覧表示画面を開き、
        StubHttpServletRequest request = openKnowledges(COMMENT_POST_USER);
        @SuppressWarnings("unchecked")
        List<KnowledgesEntity> knowledges = (List<KnowledgesEntity>) request.getAttribute("knowledges");
        Assert.assertNotNull(knowledges);
        Assert.assertEquals(1, knowledges.size());
        KnowledgesEntity knowledge = knowledges.get(0);
        Assert.assertEquals("タイトル", knowledge.getTitle());
        Assert.assertEquals("内容", knowledge.getContent());
        Assert.assertEquals(KnowledgeLogic.PUBLIC_FLAG_PUBLIC, knowledge.getPublicFlag().intValue());
        
        // 詳細表示
        request = openKnowledge(COMMENT_POST_USER, knowledgeId);
        Assert.assertEquals("タイトル", request.getAttribute("title"));
        Assert.assertEquals("<p>内容</p>\n", request.getAttribute("content")); // Markdownが処理されている
        Assert.assertEquals(KnowledgeLogic.PUBLIC_FLAG_PUBLIC, request.getAttribute("publicFlag"));
    }
    
    /**
     * CPの確認（ユーザ1と2）
     * @throws Exception
     */
    @Test
    @Order(order = 104)
    public void testCPAfterRead() throws Exception {
        assertCP(COMMENT_POST_USER, 1);
        assertCP(KNOWLEDGE_POST_USER, 1);
        assertKnowledgeCP(COMMENT_POST_USER, knowledgeId, 1);
    }
    
    /**
     * ログインユーザでKnowledgeを再度参照
     * @throws Exception
     */
    @Test
    @Order(order = 105)
    public void testReAccessListByLoginedUser2() throws Exception {
        // 一覧表示画面を開き、
        StubHttpServletRequest request = openKnowledges(COMMENT_POST_USER);
        @SuppressWarnings("unchecked")
        List<KnowledgesEntity> knowledges = (List<KnowledgesEntity>) request.getAttribute("knowledges");
        Assert.assertNotNull(knowledges);
        Assert.assertEquals(1, knowledges.size());
        KnowledgesEntity knowledge = knowledges.get(0);
        Assert.assertEquals("タイトル", knowledge.getTitle());
        Assert.assertEquals("内容", knowledge.getContent());
        Assert.assertEquals(KnowledgeLogic.PUBLIC_FLAG_PUBLIC, knowledge.getPublicFlag().intValue());
        
        // 詳細表示
        request = openKnowledge(COMMENT_POST_USER, knowledgeId);
        Assert.assertEquals("タイトル", request.getAttribute("title"));
        Assert.assertEquals("<p>内容</p>\n", request.getAttribute("content")); // Markdownが処理されている
        Assert.assertEquals(KnowledgeLogic.PUBLIC_FLAG_PUBLIC, request.getAttribute("publicFlag"));
    }
    
    /**
     * CPの確認（ユーザ1と2）
     * 同じユーザで再度開いてもポイントはつかない
     * @throws Exception
     */
    @Test
    @Order(order = 106)
    public void testCPAfterReRead() throws Exception {
        assertCP(COMMENT_POST_USER, 0); // 変化無し
        assertCP(KNOWLEDGE_POST_USER, 0); // 変化無し
        assertKnowledgeCP(COMMENT_POST_USER, knowledgeId, 0);
    }
    
    
    /**
     * ログインユーザで、通知の確認(未だ何も届いていない）
     * @throws Exception
     */
    @Test
    @Order(order = 200)
    public void testNothingNotificationByLoginedUser1() throws Exception {
        assertNotificationCount(COMMENT_POST_USER, 0);
    }
    /**
     * ログインユーザ2で、通知の確認(未だ何も届いていない）
     * @throws Exception
     */
    @Test
    @Order(order = 201)
    public void testNothingNotificationByLoginedUser2() throws Exception {
        assertNotificationCount(KNOWLEDGE_POST_USER, 0);
    }
    
    /**
     * 通知を処理
     * @throws Exception
     */
    @Test
    @Order(order = 202)
    public void testExecNotification() throws Exception {
        execNotificationQueue();
    }

    /**
     * ログインユーザで、通知の確認
     * @throws Exception
     */
    @Test
    @Order(order = 203)
    public void testNotificationByLoginedUser1() throws Exception {
        assertNotificationCount(COMMENT_POST_USER, 1);
    }

    /**
     * ログインユーザ2で、通知の確認
     * TODO 登録者本人には、通知を出さなくても良いかも（今のところ出している）
     * @throws Exception
     */
    @Test
    @Order(order = 204)
    public void testNotificationByLoginedUser2() throws Exception {
        assertNotificationCount(KNOWLEDGE_POST_USER, 1);
    }
    
    /**
     * 未ログインユーザにて、イイネを押下
     * @throws Exception
     */
    @Test
    @Order(order = 300)
    public void testLikeByNoLoginUser() throws Exception {
        addLike(null, knowledgeId);
    }
    /**
     * CPの確認（ユーザ1と2）
     * @throws Exception
     */
    @Test
    @Order(order = 301)
    public void testCPAfterLikeNotLogin() throws Exception {
        assertCP(COMMENT_POST_USER, 0); // 変化無し
        assertCP(KNOWLEDGE_POST_USER, 0); // 変化無し
        assertKnowledgeCP(COMMENT_POST_USER, knowledgeId, 0);
    }
    
    /**
     * 通知を処理
     * @throws Exception
     */
    @Test
    @Order(order = 302)
    public void testNotificationAfterLikeByAnonymous() throws Exception {
        execNotificationQueue();
        assertNotificationCount(COMMENT_POST_USER, 0);
        assertNotificationCount(KNOWLEDGE_POST_USER, 1);
    }
    
    
    /**
     * ログインユーザ1にて、イイネを押下
     * @throws Exception
     */
    @Test
    @Order(order = 303)
    public void testLikeByLoginUser1() throws Exception {
        addLike(COMMENT_POST_USER, knowledgeId);
    }
    
    /**
     * CPの確認（ユーザ1と2）
     * @throws Exception
     */
    @Test
    @Order(order = 304)
    public void testCPAfterLikeLogined() throws Exception {
        assertCP(COMMENT_POST_USER, 2); // +2
        assertCP(KNOWLEDGE_POST_USER, 10); // +10
        assertKnowledgeCP(COMMENT_POST_USER, knowledgeId, 10);
    }
    
    
    /**
     * 通知を処理
     * @throws Exception
     */
    @Test
    @Order(order = 305)
    public void testNotificationAfterLikeByUser1() throws Exception {
        execNotificationQueue();
        assertNotificationCount(COMMENT_POST_USER, 0);
        assertNotificationCount(KNOWLEDGE_POST_USER, 1);
    }
    
    
    /**
     * ログインユーザ2にて、イイネを押下
     * @throws Exception
     */
    @Test
    @Order(order = 306)
    public void testLikeByLoginUser2() throws Exception {
        addLike(KNOWLEDGE_POST_USER, knowledgeId);
    }
    
    /**
     * CPの確認（ユーザ1と2）
     * @throws Exception
     */
    @Test
    @Order(order = 307)
    public void testCPAfterLikeLogined2() throws Exception {
        assertCP(COMMENT_POST_USER, 0); // 変化無し
        assertCP(KNOWLEDGE_POST_USER, 2); // 自分で押しても +10 はつかない / アクション起こした +2 はつく
        assertKnowledgeCP(COMMENT_POST_USER, knowledgeId, 0);
    }
    
    /**
     * 通知を処理
     * @throws Exception
     */
    @Test
    @Order(order = 308)
    public void testNotificationAfterLikeByUser2() throws Exception {
        execNotificationQueue();
        assertNotificationCount(COMMENT_POST_USER, 0);
        assertNotificationCount(KNOWLEDGE_POST_USER, 1);
    }
    
    /**
     * ログインユーザ1にて、イイネを押下（再実行)
     * 再度実行してもポイントなどはつかず、通知も送らないないように
     * @throws Exception
     */
    @Test
    @Order(order = 309)
    public void testReLikeByLoginUser1() throws Exception {
        addLike(COMMENT_POST_USER, knowledgeId);
        
        assertCP(COMMENT_POST_USER, 0); // 変化無し
        assertCP(KNOWLEDGE_POST_USER, 0); // 変化無し
        assertKnowledgeCP(COMMENT_POST_USER, knowledgeId, 0); // 変化無し

        List<NotifyQueuesEntity> list = NotifyQueuesDao.get().selectAll();
        Assert.assertEquals(0, list.size());
        assertNotificationCount(COMMENT_POST_USER, 0);
        assertNotificationCount(KNOWLEDGE_POST_USER, 0);
    }
    
    
    /**
     * コメント登録者でコメント登録
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Test
    @Order(order = 400)
    public void testPostCommentByLoginUser1() throws Exception {
        // コメントが登録されていないこと
        StubHttpServletRequest request = openKnowledge(COMMENT_POST_USER, knowledgeId);
        List<CommentsEntity> comments = (List<CommentsEntity>) request.getAttribute("comments");
        Assert.assertEquals(0, comments.size());
        
        // コメントが登録できること
        comment(COMMENT_POST_USER, knowledgeId, "コメント");
        
        // CP確認
        assertCP(COMMENT_POST_USER, 10); // コメント追加者 +10
        assertCP(KNOWLEDGE_POST_USER, 0); // コメントを追加した記事の作者は変化無し
        assertKnowledgeCP(COMMENT_POST_USER, knowledgeId, 10);
        // 通知の確認
        execNotificationQueue();
        assertNotificationCount(COMMENT_POST_USER, 1);
        assertNotificationCount(KNOWLEDGE_POST_USER, 1);
        
        // コメントが登録されていないこと
        request = openKnowledge(COMMENT_POST_USER, knowledgeId);
        comments = (List<CommentsEntity>) request.getAttribute("comments");
        Assert.assertEquals(1, comments.size());
    }

    
    /**
     * コメント登録者のコメントに、記事登録者がイイネを押す
     * @throws Exception
     */
    @Test
    @Order(order = 401)
    public void testLikeCommentByLoginUser2() throws Exception {
        addLatestLikeComment(KNOWLEDGE_POST_USER, knowledgeId);
        
        assertCP(COMMENT_POST_USER, 10); // +10
        assertCP(KNOWLEDGE_POST_USER, 2); // +2(押した)
        assertKnowledgeCP(COMMENT_POST_USER, knowledgeId, 0); // ユーザ2は記事の作者なので、作者が押してもポイントは増えない（コメントであっても）

        execNotificationQueue();
        assertNotificationCount(COMMENT_POST_USER, 1); // コメント登録者に通知
        assertNotificationCount(KNOWLEDGE_POST_USER, 0);
    }


    /**
     * コメント登録者のコメントに、参照ユーザがイイネを押す
     * @throws Exception
     */
    @Test
    @Order(order = 402)
    public void testLikeCommentByLoginUser3() throws Exception {
        addLatestLikeComment(READ_USER, knowledgeId);
        
        assertCP(COMMENT_POST_USER, 10); // +10
        assertCP(KNOWLEDGE_POST_USER, 1); // 参照
        assertKnowledgeCP(COMMENT_POST_USER, knowledgeId, 11); // イイネ + ユーザ3での参照

        execNotificationQueue();
        assertNotificationCount(COMMENT_POST_USER, 1); // コメント登録者に通知
        assertNotificationCount(KNOWLEDGE_POST_USER, 0);
    }


    /**
     * コメント登録者のコメントに、自分自身でイイネを押す
     * 
     * TODO コメントの場合、いいねを作者が押すと、アクションポイント(+2)がつかない？
     * TODO 自分のコメントであっても、記事の作者と違うので、記事にポイント(+10)がつくがおかしくない？
     * TODO 自分でいいねを押しても通知が届いてしまう
     * 
     * @throws Exception
     */
    @Test
    @Order(order = 403)
    public void testLikeCommentByLoginUser1() throws Exception {
        addLatestLikeComment(COMMENT_POST_USER, knowledgeId);
        
        assertCP(COMMENT_POST_USER, 0); // ポイントつかない？
        assertCP(KNOWLEDGE_POST_USER, 0); 
        assertKnowledgeCP(COMMENT_POST_USER, knowledgeId, 10);

        execNotificationQueue();
        assertNotificationCount(COMMENT_POST_USER, 1); // コメント登録者に通知
        assertNotificationCount(KNOWLEDGE_POST_USER, 0);
    }
    

    /**
     * コメント登録者のコメントに、未ログインユーザがイイネを押す
     * 未ログインユーザの場合ポイントつかない
     * @throws Exception
     */
    @Test
    @Order(order = 404)
    public void testLikeCommentByAnonymous() throws Exception {
        addLatestLikeComment(null, knowledgeId);
        
        assertCP(COMMENT_POST_USER, 0);
        assertCP(KNOWLEDGE_POST_USER, 0); 
        assertKnowledgeCP(COMMENT_POST_USER, knowledgeId, 0);

        execNotificationQueue();
        assertNotificationCount(COMMENT_POST_USER, 1); // コメント登録者に通知
        assertNotificationCount(KNOWLEDGE_POST_USER, 0);
    }
    
    /**
     * 記事登録者が自分自身の記事にコメント追加
     * @throws Exception
     */
    @Test
    @Order(order = 500)
    public void testPostCommentByLoginUser2() throws Exception {
        // コメントが登録できること
        comment(KNOWLEDGE_POST_USER, knowledgeId, "コメント");
        
        // CP確認
        assertCP(COMMENT_POST_USER, 0);
        assertCP(KNOWLEDGE_POST_USER, 10);
        assertKnowledgeCP(COMMENT_POST_USER, knowledgeId, 10);
        
        // 通知の確認
        execNotificationQueue();
        assertNotificationCount(COMMENT_POST_USER, 1);
        assertNotificationCount(KNOWLEDGE_POST_USER, 1);
    }
    
    /**
     * 記事登録者が登録したコメントに、コメント登録者がイイネを押す
     * @throws Exception
     */
    @Test
    @Order(order = 501)
    public void testLikeCommentByCommentUserToKnowledgeUser() throws Exception {
        addLatestLikeComment(COMMENT_POST_USER, knowledgeId);
        
        assertCP(COMMENT_POST_USER, 2);
        assertCP(KNOWLEDGE_POST_USER, 10); 
        assertKnowledgeCP(COMMENT_POST_USER, knowledgeId, 10);

        execNotificationQueue();
        assertNotificationCount(COMMENT_POST_USER, 0);
        assertNotificationCount(KNOWLEDGE_POST_USER, 1);
    }
    /**
     * 記事登録者が登録したコメントに、自分自身でイイネを押す
     * @throws Exception
     */
    @Test
    @Order(order = 502)
    public void testLikeCommentByKnowledgeUserToKnowledgeUser() throws Exception {
        addLatestLikeComment(KNOWLEDGE_POST_USER, knowledgeId);
        
        assertCP(COMMENT_POST_USER, 0);
        assertCP(KNOWLEDGE_POST_USER, 0); // アクションポイントもつかない？
        assertKnowledgeCP(COMMENT_POST_USER, knowledgeId, 0);

        execNotificationQueue();
        assertNotificationCount(COMMENT_POST_USER, 0);
        assertNotificationCount(KNOWLEDGE_POST_USER, 1);
    }

    /**
     * 記事登録者が登録したコメントに、参照ユーザでイイネを押す
     * @throws Exception
     */
    @Test
    @Order(order = 503)
    public void testLikeCommentByReadUserToKnowledgeUser() throws Exception {
        addLatestLikeComment(READ_USER, knowledgeId);
        
        assertCP(COMMENT_POST_USER, 0);
        assertCP(KNOWLEDGE_POST_USER, 10);
        assertKnowledgeCP(COMMENT_POST_USER, knowledgeId, 10);

        execNotificationQueue();
        assertNotificationCount(COMMENT_POST_USER, 0);
        assertNotificationCount(KNOWLEDGE_POST_USER, 1);
    }
    
    /**
     * 記事登録者が登録したコメントに、未ログインユーザでイイネを押す
     * @throws Exception
     */
    @Test
    @Order(order = 504)
    public void testLikeCommentByNotLoginToKnowledgeUser() throws Exception {
        addLatestLikeComment(null, knowledgeId);
        
        assertCP(COMMENT_POST_USER, 0);
        assertCP(KNOWLEDGE_POST_USER, 0);
        assertKnowledgeCP(COMMENT_POST_USER, knowledgeId, 0);

        execNotificationQueue();
        assertNotificationCount(COMMENT_POST_USER, 0);
        assertNotificationCount(KNOWLEDGE_POST_USER, 1);
    }
    
    /**
     * CP獲得履歴
     * @throws Exception
     */
    @Test
    @Order(order = 600)
    public void testActivityHistory() throws Exception {
        assertPointHistoryCount(KNOWLEDGE_POST_USER, 9);
        assertPointHistoryCount(COMMENT_POST_USER, 6);
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
        assertCP(COMMENT_POST_USER, 0);
        assertCP(KNOWLEDGE_POST_USER, 0);
        assertKnowledgeCP(COMMENT_POST_USER, knowledgeId, 0);
    }


}
