package org.support.project.knowledge.integration;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.test.Order;
import org.support.project.common.util.PropertyUtil;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.vo.LikeCount;
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
    
    private static final String INTEGRATION_TEST_USER_01 = "integration-test-user-01";
    private static final String INTEGRATION_TEST_USER_02 = "integration-test-user-02";
    private static final String INTEGRATION_TEST_USER_03 = "integration-test-user-03";
    
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
        addUser(INTEGRATION_TEST_USER_03);
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
        StubHttpServletRequest request = openKnowledges(INTEGRATION_TEST_USER_01);
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
        assertCP(INTEGRATION_TEST_USER_01, 0);
        assertCP(INTEGRATION_TEST_USER_02, 0);
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
     * CPの確認（ユーザ1と2）
     * @throws Exception
     */
    @Test
    @Order(order = 101)
    public void testCPAfterPost() throws Exception {
        assertCP(INTEGRATION_TEST_USER_01, 0);
        assertCP(INTEGRATION_TEST_USER_02, 50);
        assertKnowledgeCP(null, knowledgeId, 50);
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
        StubHttpServletRequest request = openKnowledges(INTEGRATION_TEST_USER_01);
        List<KnowledgesEntity> knowledges = (List<KnowledgesEntity>) request.getAttribute("knowledges");
        Assert.assertNotNull(knowledges);
        Assert.assertEquals(1, knowledges.size());
        KnowledgesEntity knowledge = knowledges.get(0);
        Assert.assertEquals("タイトル", knowledge.getTitle());
        Assert.assertEquals("内容", knowledge.getContent());
        Assert.assertEquals(KnowledgeLogic.PUBLIC_FLAG_PUBLIC, knowledge.getPublicFlag().intValue());
        
        // 詳細表示
        request = openKnowledge(INTEGRATION_TEST_USER_01, knowledgeId);
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
        assertCP(INTEGRATION_TEST_USER_01, 1);
        assertCP(INTEGRATION_TEST_USER_02, 1);
        assertKnowledgeCP(INTEGRATION_TEST_USER_01, knowledgeId, 1);
    }
    
    /**
     * ログインユーザでKnowledgeを再度参照
     * @throws Exception
     */
    @Test
    @Order(order = 105)
    public void testReAccessListByLoginedUser2() throws Exception {
        // 一覧表示画面を開き、
        StubHttpServletRequest request = openKnowledges(INTEGRATION_TEST_USER_01);
        List<KnowledgesEntity> knowledges = (List<KnowledgesEntity>) request.getAttribute("knowledges");
        Assert.assertNotNull(knowledges);
        Assert.assertEquals(1, knowledges.size());
        KnowledgesEntity knowledge = knowledges.get(0);
        Assert.assertEquals("タイトル", knowledge.getTitle());
        Assert.assertEquals("内容", knowledge.getContent());
        Assert.assertEquals(KnowledgeLogic.PUBLIC_FLAG_PUBLIC, knowledge.getPublicFlag().intValue());
        
        // 詳細表示
        request = openKnowledge(INTEGRATION_TEST_USER_01, knowledgeId);
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
        assertCP(INTEGRATION_TEST_USER_01, 0); // 変化無し
        assertCP(INTEGRATION_TEST_USER_02, 0); // 変化無し
        assertKnowledgeCP(INTEGRATION_TEST_USER_01, knowledgeId, 0);
    }
    
    
    /**
     * ログインユーザで、通知の確認(未だ何も届いていない）
     * @throws Exception
     */
    @Test
    @Order(order = 200)
    public void testNothingNotificationByLoginedUser1() throws Exception {
        assertNotificationCount(INTEGRATION_TEST_USER_01, 0);
    }
    /**
     * ログインユーザ2で、通知の確認(未だ何も届いていない）
     * @throws Exception
     */
    @Test
    @Order(order = 201)
    public void testNothingNotificationByLoginedUser2() throws Exception {
        assertNotificationCount(INTEGRATION_TEST_USER_02, 0);
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
        assertNotificationCount(INTEGRATION_TEST_USER_01, 1);
    }

    /**
     * ログインユーザ2で、通知の確認
     * TODO 登録者本人には、通知を出さなくても良いかも（今のところ出している）
     * @throws Exception
     */
    @Test
    @Order(order = 204)
    public void testNotificationByLoginedUser2() throws Exception {
        assertNotificationCount(INTEGRATION_TEST_USER_02, 1);
    }
    
    /**
     * 未ログインユーザにて、イイネを押下
     * @throws Exception
     */
    @Test
    @Order(order = 300)
    public void testLikeByNoLoginUser() throws Exception {
        LikeCount like = like(null, knowledgeId);
        Assert.assertEquals((long) 1, like.getCount().longValue());
    }
    /**
     * CPの確認（ユーザ1と2）
     * @throws Exception
     */
    @Test
    @Order(order = 301)
    public void testCPAfterLikeNotLogin() throws Exception {
        assertCP(INTEGRATION_TEST_USER_01, 0); // 変化無し
        assertCP(INTEGRATION_TEST_USER_02, 0); // 変化無し
        assertKnowledgeCP(INTEGRATION_TEST_USER_01, knowledgeId, 0);
    }
    
    /**
     * 通知を処理
     * @throws Exception
     */
    @Test
    @Order(order = 302)
    public void testNotificationAfterLikeByAnonymous() throws Exception {
        execNotificationQueue();
        assertNotificationCount(INTEGRATION_TEST_USER_01, 0);
        assertNotificationCount(INTEGRATION_TEST_USER_02, 1);
    }
    
    
    /**
     * ログインユーザ1にて、イイネを押下
     * @throws Exception
     */
    @Test
    @Order(order = 303)
    public void testLikeByLoginUser1() throws Exception {
        LikeCount like = like(INTEGRATION_TEST_USER_01, knowledgeId);
        Assert.assertEquals((long) 2, like.getCount().longValue());
    }
    
    /**
     * CPの確認（ユーザ1と2）
     * @throws Exception
     */
    @Test
    @Order(order = 304)
    public void testCPAfterLikeLogined() throws Exception {
        assertCP(INTEGRATION_TEST_USER_01, 2); // +2
        assertCP(INTEGRATION_TEST_USER_02, 10); // +10
        assertKnowledgeCP(INTEGRATION_TEST_USER_01, knowledgeId, 10);
    }
    
    
    /**
     * 通知を処理
     * @throws Exception
     */
    @Test
    @Order(order = 305)
    public void testNotificationAfterLikeByUser1() throws Exception {
        execNotificationQueue();
        assertNotificationCount(INTEGRATION_TEST_USER_01, 0);
        assertNotificationCount(INTEGRATION_TEST_USER_02, 1);
    }
    
    
    /**
     * ログインユーザ2にて、イイネを押下
     * @throws Exception
     */
    @Test
    @Order(order = 306)
    public void testLikeByLoginUser2() throws Exception {
        LikeCount like = like(INTEGRATION_TEST_USER_02, knowledgeId);
        Assert.assertEquals((long) 3, like.getCount().longValue());
    }
    
    /**
     * CPの確認（ユーザ1と2）
     * @throws Exception
     */
    @Test
    @Order(order = 307)
    public void testCPAfterLikeLogined2() throws Exception {
        assertCP(INTEGRATION_TEST_USER_01, 0); // 変化無し
        assertCP(INTEGRATION_TEST_USER_02, 2); // 自分で押しても +10 はつかない / アクション起こした +2 はつく
        assertKnowledgeCP(INTEGRATION_TEST_USER_01, knowledgeId, 0);
    }
    
    /**
     * 通知を処理
     * @throws Exception
     */
    @Test
    @Order(order = 308)
    public void testNotificationAfterLikeByUser2() throws Exception {
        execNotificationQueue();
        assertNotificationCount(INTEGRATION_TEST_USER_01, 0);
        assertNotificationCount(INTEGRATION_TEST_USER_02, 1);
    }
    
    /**
     * ログインユーザ1にて、イイネを押下（再実行)
     * 
     * TODO 再度実行してもポイントなどはつかないが、通知は押した回数分送っている
     * 一度送った場合は、そのユーザが何回いいねを押しても通知しない方がよさげ
     * 
     * @throws Exception
     */
    @Test
    @Order(order = 309)
    public void testReLikeByLoginUser1() throws Exception {
        LikeCount like = like(INTEGRATION_TEST_USER_01, knowledgeId);
        Assert.assertEquals((long) 4, like.getCount().longValue());
        
        assertCP(INTEGRATION_TEST_USER_01, 0); // 変化無し
        assertCP(INTEGRATION_TEST_USER_02, 0); // 変化無し
        assertKnowledgeCP(INTEGRATION_TEST_USER_01, knowledgeId, 0); // 変化無し

        execNotificationQueue();
        assertNotificationCount(INTEGRATION_TEST_USER_01, 0);
        assertNotificationCount(INTEGRATION_TEST_USER_02, 1);
    }
    
    
    /**
     * ログインユーザ1にて、コメント追加
     * @throws Exception
     */
    @Test
    @Order(order = 400)
    public void testPostCommentByLoginUser1() throws Exception {
        // コメントが登録されていないこと
        StubHttpServletRequest request = openKnowledge(INTEGRATION_TEST_USER_01, knowledgeId);
        List<CommentsEntity> comments = (List<CommentsEntity>) request.getAttribute("comments");
        Assert.assertEquals(0, comments.size()); // 閲覧者にもデフォルト通知（初期設定）
        
        // コメントが登録できること
        comment(INTEGRATION_TEST_USER_01, knowledgeId, "コメント");
        
        // CP確認
        assertCP(INTEGRATION_TEST_USER_01, 20); // コメント追加者 +20
        assertCP(INTEGRATION_TEST_USER_02, 0); // コメントを追加した記事の作者は変化無し
        assertKnowledgeCP(INTEGRATION_TEST_USER_01, knowledgeId, 20); // +20
        
        // 通知の確認
        execNotificationQueue();
        assertNotificationCount(INTEGRATION_TEST_USER_01, 1);
        assertNotificationCount(INTEGRATION_TEST_USER_02, 1);
        
        // コメントが登録されていないこと
        request = openKnowledge(INTEGRATION_TEST_USER_01, knowledgeId);
        comments = (List<CommentsEntity>) request.getAttribute("comments");
        Assert.assertEquals(1, comments.size()); // 閲覧者にもデフォルト通知（初期設定）
    }

    
    /**
     * ログインユーザ2でコメントにいいね
     * @throws Exception
     */
    @Test
    @Order(order = 401)
    public void testLikeCommentByLoginUser2() throws Exception {
        StubHttpServletRequest request = openKnowledge(INTEGRATION_TEST_USER_02, knowledgeId);
        List<CommentsEntity> comments = (List<CommentsEntity>) request.getAttribute("comments");
        CommentsEntity comment = comments.get(0);
        
        LikeCount like = likeComment(INTEGRATION_TEST_USER_02, knowledgeId, comment.getCommentNo());
        Assert.assertEquals((long) 1, like.getCount().longValue());
        
        assertCP(INTEGRATION_TEST_USER_01, 10); // +10
        assertCP(INTEGRATION_TEST_USER_02, 2); // +2(押した)
        assertKnowledgeCP(INTEGRATION_TEST_USER_01, knowledgeId, 0); // ユーザ2は記事の作者なので、作者が押してもポイントは増えない（コメントであっても）

        execNotificationQueue();
        assertNotificationCount(INTEGRATION_TEST_USER_01, 1); // コメント登録者に通知
        assertNotificationCount(INTEGRATION_TEST_USER_02, 0);
    }


    /**
     * ログインユーザ3でコメントにいいね
     * @throws Exception
     */
    @Test
    @Order(order = 402)
    public void testLikeCommentByLoginUser3() throws Exception {
        StubHttpServletRequest request = openKnowledge(INTEGRATION_TEST_USER_03, knowledgeId);
        List<CommentsEntity> comments = (List<CommentsEntity>) request.getAttribute("comments");
        CommentsEntity comment = comments.get(0);
        
        LikeCount like = likeComment(INTEGRATION_TEST_USER_03, knowledgeId, comment.getCommentNo());
        Assert.assertEquals((long) 2, like.getCount().longValue());
        
        assertCP(INTEGRATION_TEST_USER_01, 10); // +10
        assertCP(INTEGRATION_TEST_USER_02, 1); // 参照
        assertKnowledgeCP(INTEGRATION_TEST_USER_01, knowledgeId, 11); // イイネ + ユーザ3での参照

        execNotificationQueue();
        assertNotificationCount(INTEGRATION_TEST_USER_01, 1); // コメント登録者に通知
        assertNotificationCount(INTEGRATION_TEST_USER_02, 0);
    }


    /**
     * ログインユーザ1でコメントにいいね(登録者)
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
        StubHttpServletRequest request = openKnowledge(INTEGRATION_TEST_USER_01, knowledgeId);
        List<CommentsEntity> comments = (List<CommentsEntity>) request.getAttribute("comments");
        CommentsEntity comment = comments.get(0);
        
        LikeCount like = likeComment(INTEGRATION_TEST_USER_01, knowledgeId, comment.getCommentNo());
        Assert.assertEquals((long) 3, like.getCount().longValue());
        
        assertCP(INTEGRATION_TEST_USER_01, 0); // ポイントつかない？
        assertCP(INTEGRATION_TEST_USER_02, 0); 
        assertKnowledgeCP(INTEGRATION_TEST_USER_01, knowledgeId, 10);

        execNotificationQueue();
        assertNotificationCount(INTEGRATION_TEST_USER_01, 1); // コメント登録者に通知
        assertNotificationCount(INTEGRATION_TEST_USER_02, 0);
    }
    

    /**
     * 未ログインユーザでコメントにいいね
     * 
     * 未ログインユーザの場合ポイントつかない
     * 
     * @throws Exception
     */
    @Test
    @Order(order = 404)
    public void testLikeCommentByAnonymous() throws Exception {
        StubHttpServletRequest request = openKnowledge(null, knowledgeId);
        List<CommentsEntity> comments = (List<CommentsEntity>) request.getAttribute("comments");
        CommentsEntity comment = comments.get(0);
        LikeCount like = likeComment(null, knowledgeId, comment.getCommentNo());
        Assert.assertEquals((long) 4, like.getCount().longValue());
        
        assertCP(INTEGRATION_TEST_USER_01, 0);
        assertCP(INTEGRATION_TEST_USER_02, 0); 
        assertKnowledgeCP(INTEGRATION_TEST_USER_01, knowledgeId, 0);

        execNotificationQueue();
        assertNotificationCount(INTEGRATION_TEST_USER_01, 1); // コメント登録者に通知
        assertNotificationCount(INTEGRATION_TEST_USER_02, 0);
    }
    
    

}