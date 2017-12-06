package org.support.project.knowledge.integration;

import java.util.List;

import javax.mail.Address;

import org.junit.Assert;
import org.junit.Test;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.test.Order;
import org.support.project.knowledge.config.MailHookCondition;
import org.support.project.knowledge.dao.MailHookConditionsDao;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.MailHookConditionsEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.MailhookLogic;
import org.support.project.knowledge.stub.mail.StubAddress;
import org.support.project.knowledge.stub.mail.StubMessage;
import org.support.project.knowledge.vo.api.Knowledge;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.test.stub.StubHttpServletRequest;

public class IntegrationMailReadTest extends IntegrationCommon {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(IntegrationPostTest.class);
    
    private static final String CONFIG_USER = "CONFIG-USER";
    private static final String POST_USER = "POST-USER";
    private static final String COMMENT_USER = "COMMENT-USER";
    
    /**
     * テスト用初期データが登録できること
     * @throws Exception
     */
    @Test
    @Order(order = 1)
    public void testInitDataInsert() throws Exception {
        LOG.info("テストのためのデータ登録");
        addUser(CONFIG_USER);
        addUser(POST_USER);
        addUser(COMMENT_USER);
        LoginedUser user = super.getLoginUser(CONFIG_USER);
        
        // メールから投稿設定
        MailHookConditionsEntity condition = new MailHookConditionsEntity();
        condition.setHookId(MailhookLogic.MAIL_HOOK_ID);
        condition.setConditionNo(-1);
        condition.setConditionKind(MailHookCondition.Title.getValue());
        condition.setCondition("[メールからの投稿]");
        condition.setProcessUser(user.getUserId());
        condition.setProcessUserKind(1);
        condition.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PUBLIC);
        condition.setTags("メールから投稿");
        MailhookLogic.get().saveCondition(condition);
    }
    
    /**
     * メールから投稿できること
     * @throws Exception
     */
    @Test
    @Order(order = 100)
    public void testPostFromMail() throws Exception {
        LoginedUser user = super.getLoginUser(POST_USER);
        List<MailHookConditionsEntity> conditions = MailHookConditionsDao.get().selectOnHookId(MailhookLogic.MAIL_HOOK_ID);
        StubMessage msg = new StubMessage();
        Address[] addresses = {new StubAddress(user.getLoginUser().getMailAddress())};
        msg.addFrom(addresses);
        Address[] recipients = {new StubAddress("to@example.com")};
        msg.setAllRecipients(recipients);
        msg.setContent("投稿の本文になる");
        msg.setSubject("[メールからの投稿] タイトル");
        msg.addHeader("Message-ID", "MailHookTest-01");
        MailhookLogic.get().checkConditionsAndPost(msg, conditions);
    }
    /**
     * Knowledgeを参照
     * @throws Exception
     */
    @Test
    @Order(order = 101)
    public void testViewAfterPost() throws Exception {
        LoginedUser user = super.getLoginUser(POST_USER);
        Knowledge result = knowledgeGetOnAPI(POST_USER, 1);
        Assert.assertEquals((long) 1, result.getKnowledgeId().longValue());
        Assert.assertEquals("[メールからの投稿] タイトル", result.getTitle());
        Assert.assertEquals("投稿の本文になる", result.getContent());
        Assert.assertEquals(user.getUserId(), result.getInsertUser());
        
        StubHttpServletRequest request = openKnowledge(POST_USER, 1);
        @SuppressWarnings("unchecked")
        List<CommentsEntity> comments = (List<CommentsEntity>) request.getAttribute("comments");
        Assert.assertEquals(0, comments.size());
    }
    
    /**
     * 登録後の状態を確認
     * @throws Exception
     */
    @Test
    @Order(order = 102)
    public void testCheckDataAfterPost() throws Exception {
        assertCP(POST_USER, 20);
        assertCP(COMMENT_USER, 0);
        assertKnowledgeCP(POST_USER, 1, 20);

        execNotificationQueue();
        assertNotificationCount(POST_USER, 1);
        assertNotificationCount(COMMENT_USER, 1);
    }
    
    
    /**
     * メールからコメント登録できること
     * @throws Exception
     */
    @Test
    @Order(order = 200)
    public void testCommentFromMail() throws Exception {
        LoginedUser user = super.getLoginUser(COMMENT_USER);
        List<MailHookConditionsEntity> conditions = MailHookConditionsDao.get().selectOnHookId(MailhookLogic.MAIL_HOOK_ID);
        StubMessage msg = new StubMessage();
        Address[] addresses = {new StubAddress(user.getLoginUser().getMailAddress())};
        msg.addFrom(addresses);
        Address[] recipients = {new StubAddress("to@example.com")};
        msg.setAllRecipients(recipients);
        msg.setContent("コメント");
        msg.setSubject("Re: [メールからの投稿] タイトル");
        msg.addHeader("Message-ID", "MailHookTest-02");
        msg.addHeader("References", "MailHookTest-01");
        MailhookLogic.get().checkConditionsAndPost(msg, conditions);
    }
    /**
     * Knowledgeを参照
     * @throws Exception
     */
    @Test
    @Order(order = 201)
    public void testViewAfterComment() throws Exception {
        Knowledge result = knowledgeGetOnAPI(COMMENT_USER, 1);
        Assert.assertEquals((long) 1, result.getKnowledgeId().longValue());
        Assert.assertEquals("[メールからの投稿] タイトル", result.getTitle());
        Assert.assertEquals("投稿の本文になる", result.getContent());
        
        LoginedUser user = super.getLoginUser(POST_USER); // 作成者チェック
        Assert.assertEquals(user.getUserId(), result.getInsertUser());
        
        StubHttpServletRequest request = openKnowledge(POST_USER, 1);
        @SuppressWarnings("unchecked")
        List<CommentsEntity> comments = (List<CommentsEntity>) request.getAttribute("comments");
        Assert.assertEquals(1, comments.size());
    }
    /**
     * 登録後の状態を確認
     * @throws Exception
     */
    @Test
    @Order(order = 202)
    public void testCheckDataAfterComment() throws Exception {
        assertCP(POST_USER, 0);
        assertCP(COMMENT_USER, 10);
        assertKnowledgeCP(POST_USER, 1, 10);

        execNotificationQueue();
        assertNotificationCount(POST_USER, 1);
        assertNotificationCount(COMMENT_USER, 1);
    }
    
    
    
    /**
     * メールアドレスで投稿
     * @throws Exception
     */
    @Test
    @Order(order = 300)
    public void testPostOnRecipient1() throws Exception {
        // データ完全初期化
        setUpBeforeClass();
        
        // テストユーザ追加
        addUser(CONFIG_USER);
        addUser(POST_USER);
        LoginedUser user = super.getLoginUser(CONFIG_USER);
        
        // メールから投稿設定（受信メールアドレスの一部で）
        MailHookConditionsEntity condition = new MailHookConditionsEntity();
        condition.setHookId(MailhookLogic.MAIL_HOOK_ID);
        condition.setConditionNo(-1);
        condition.setConditionKind(MailHookCondition.Recipient.getValue());
        condition.setCondition("hoge@example.com");
        condition.setProcessUser(user.getUserId());
        condition.setProcessUserKind(1);
        condition.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PUBLIC);
        condition.setTags("メールの宛先の一部で投稿");
        MailhookLogic.get().saveCondition(condition);
        
        // メールから投稿
        user = super.getLoginUser(POST_USER);
        List<MailHookConditionsEntity> conditions = MailHookConditionsDao.get().selectOnHookId(MailhookLogic.MAIL_HOOK_ID);
        StubMessage msg = new StubMessage();
        Address[] addresses = {new StubAddress(user.getLoginUser().getMailAddress())};
        msg.addFrom(addresses);
        Address[] recipients = {new StubAddress("hoge@example.com")};
        msg.setAllRecipients(recipients);
        msg.setContent("投稿の本文になる");
        msg.setSubject("メールアドレスの部分一致で投稿");
        msg.addHeader("Message-ID", "MailHookTest-02");
        
        MailhookLogic.get().checkConditionsAndPost(msg, conditions);
        Knowledge result = knowledgeGetOnAPI(POST_USER, 1);
        Assert.assertEquals((long) 1, result.getKnowledgeId().longValue());
        Assert.assertEquals("メールアドレスの部分一致で投稿", result.getTitle());

        // メールから投稿
        user = super.getLoginUser(POST_USER);
        conditions = MailHookConditionsDao.get().selectOnHookId(MailhookLogic.MAIL_HOOK_ID);
        msg = new StubMessage();
        addresses[0] = new StubAddress(user.getLoginUser().getMailAddress());
        msg.addFrom(addresses);
        recipients[0] = new StubAddress("hoge_info@example.com");
        msg.setAllRecipients(recipients);
        msg.setContent("投稿の本文になる");
        msg.setSubject("これは対象外になる");
        msg.addHeader("Message-ID", "MailHookTest-03");
        
        MailhookLogic.get().checkConditionsAndPost(msg, conditions);
        result = knowledgeGetOnAPI(POST_USER, 1);
        Assert.assertEquals((long) 1, result.getKnowledgeId().longValue());
        Assert.assertEquals("メールアドレスの部分一致で投稿", result.getTitle());
    }
    
    /**
     * メールアドレスの一部で投稿
     * @throws Exception
     */
    @Test
    @Order(order = 301)
    public void testPostOnRecipient2() throws Exception {
        // データ完全初期化
        setUpBeforeClass();
        
        // テストユーザ追加
        addUser(CONFIG_USER);
        addUser(POST_USER);
        LoginedUser user = super.getLoginUser(CONFIG_USER);
        
        // メールから投稿設定（受信メールアドレスの一部で）
        MailHookConditionsEntity condition = new MailHookConditionsEntity();
        condition.setHookId(MailhookLogic.MAIL_HOOK_ID);
        condition.setConditionNo(-1);
        condition.setConditionKind(MailHookCondition.Recipient.getValue());
        condition.setCondition("hoge");
        condition.setProcessUser(user.getUserId());
        condition.setProcessUserKind(1);
        condition.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PUBLIC);
        condition.setTags("メールの宛先の一部で投稿");
        MailhookLogic.get().saveCondition(condition);
        
        // メールから投稿
        user = super.getLoginUser(POST_USER);
        List<MailHookConditionsEntity> conditions = MailHookConditionsDao.get().selectOnHookId(MailhookLogic.MAIL_HOOK_ID);
        StubMessage msg = new StubMessage();
        Address[] addresses = {new StubAddress(user.getLoginUser().getMailAddress())};
        msg.addFrom(addresses);
        Address[] recipients = {new StubAddress("hoge@example.com")};
        msg.setAllRecipients(recipients);
        msg.setContent("投稿の本文になる");
        msg.setSubject("メールアドレスの部分一致で投稿");
        msg.addHeader("Message-ID", "MailHookTest-02");
        
        MailhookLogic.get().checkConditionsAndPost(msg, conditions);
        Knowledge result = knowledgeGetOnAPI(POST_USER, 1);
        Assert.assertEquals((long) 1, result.getKnowledgeId().longValue());
        Assert.assertEquals("メールアドレスの部分一致で投稿", result.getTitle());
        
        // メールから投稿
        user = super.getLoginUser(POST_USER);
        conditions = MailHookConditionsDao.get().selectOnHookId(MailhookLogic.MAIL_HOOK_ID);
        msg = new StubMessage();
        addresses[0] = new StubAddress(user.getLoginUser().getMailAddress());
        msg.addFrom(addresses);
        recipients[0] = new StubAddress("hoge_info@example.com");
        msg.setAllRecipients(recipients);
        msg.setContent("投稿の本文になる");
        msg.setSubject("こんどは投稿する");
        msg.addHeader("Message-ID", "MailHookTest-03");
        
        MailhookLogic.get().checkConditionsAndPost(msg, conditions);
        result = knowledgeGetOnAPI(POST_USER, 2);
        Assert.assertEquals((long) 2, result.getKnowledgeId().longValue());
        Assert.assertEquals("こんどは投稿する", result.getTitle());
    }
    
    
    
    
}
