package org.support.project.knowledge.logic.integration;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.test.Order;
import org.support.project.common.test.OrderedRunner;
import org.support.project.knowledge.TestCommon;
import org.support.project.knowledge.bat.NotifyMailBat;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.AuthType;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.dao.NotifyConfigsDao;
import org.support.project.knowledge.dao.NotifyQueuesDao;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.NotifyConfigsEntity;
import org.support.project.knowledge.entity.NotifyQueuesEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.TemplateLogic;
import org.support.project.knowledge.logic.notification.QueueNotification;
import org.support.project.ormapping.common.DBUserPool;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.dao.MailConfigsDao;
import org.support.project.web.dao.NotificationsDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.MailConfigsEntity;
import org.support.project.web.entity.NotificationsEntity;
import org.support.project.web.entity.UsersEntity;

/**
 * ユーザを2つ追加し、片方のユーザで非公開のKnowledgeを登録し、通知や記事が参照できることを確認する
 * @author koda
 *
 */
@RunWith(OrderedRunner.class)
public class PostPrivateLogicIntegrationTest extends TestCommon {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(PostPrivateLogicIntegrationTest.class);
    
    private static long knowledgeId; // テストメソッド単位にインスタンスが歳生成されるようなので、staticで保持する
    
    /**
     * ユーザを2つ登録
     * @throws Exception
     */
    @Test
    @Order(order = 1)
    public void testUserInsert() throws Exception {
        LOG.info("ユーザ登録");
        UsersEntity user = addUser("integration-test-user-01");
        UsersEntity check = UsersDao.get().selectOnUserKey(user.getUserKey());
        Assert.assertNotNull(check);
        Assert.assertEquals(user.getUserKey(), check.getUserKey());
        Assert.assertEquals(user.getLocaleKey(), check.getLocaleKey());
        Assert.assertEquals(user.getMailAddress(), check.getMailAddress());
        Assert.assertEquals(user.getUserName(), check.getUserName());
        
        UsersEntity user2 = addUser("integration-test-user-02");
        check = UsersDao.get().selectOnUserKey(user2.getUserKey());
        Assert.assertNotNull(check);
        Assert.assertEquals(user2.getUserKey(), check.getUserKey());
        Assert.assertEquals(user2.getLocaleKey(), check.getLocaleKey());
        Assert.assertEquals(user2.getMailAddress(), check.getMailAddress());
        Assert.assertEquals(user2.getUserName(), check.getUserName());
    }
    
    /**
     * 登録されたユーザの通知設定を参照
     * @throws Exception
     */
    @Test
    @Order(order = 2)
    public void testCheckNotify() throws Exception {
        LOG.info("通知設定確認");
        testCheckNotify("integration-test-user-01");
        testCheckNotify("integration-test-user-02");
    }
    private void testCheckNotify(String key) {
        UsersEntity user = UsersDao.get().selectOnUserKey(key);
        NotifyConfigsEntity notify = NotifyConfigsDao.get().selectOnKey(user.getUserId());
        //LOG.info(notify.toString());
        Assert.assertNotNull(notify);
        Assert.assertEquals(1, notify.getNotifyMail().intValue());
        Assert.assertEquals(0, notify.getNotifyDesktop().intValue());
        Assert.assertEquals(1, notify.getMyItemComment().intValue());
        Assert.assertEquals(1, notify.getMyItemLike().intValue());
        Assert.assertEquals(1, notify.getMyItemStock().intValue());
        Assert.assertEquals(1, notify.getToItemSave().intValue());
        Assert.assertEquals(1, notify.getToItemComment().intValue());
        Assert.assertEquals(0, notify.getToItemIgnorePublic().intValue());
        Assert.assertEquals(0, notify.getStockItemSave().intValue());
        Assert.assertEquals(0, notify.getStokeItemComment().intValue());
    }

    /**
     * 記事を登録
     * @throws Exception
     */
    @Test
    @Order(order = 3)
    public void testInsertKnowledge() throws Exception {
        LOG.info("記事投稿");
        LoginedUser loginUser = getLoginUser("integration-test-user-01");
        DBUserPool.get().setUser(loginUser.getUserId()); // 操作ユーザのIDを指定
        
        KnowledgesEntity knowledge = super.insertKnowledge("integration-test-knowledge-01", loginUser,
                TemplateLogic.TYPE_ID_KNOWLEDGE, KnowledgeLogic.PUBLIC_FLAG_PRIVATE);
        
        KnowledgesEntity check = KnowledgesDao.get().selectOnKey(knowledge.getKnowledgeId());
        Assert.assertNotNull(check);
        Assert.assertEquals(knowledge.getKnowledgeId(), check.getKnowledgeId());
        Assert.assertEquals("integration-test-knowledge-01", check.getTitle());
        Assert.assertEquals(loginUser.getUserId(), check.getInsertUser());
        knowledgeId = knowledge.getKnowledgeId();
    }
    
    /**
     * 通知情報が登録されていることを確認
     * @throws Exception
     */
    @Test
    @Order(order = 4)
    public void testCheckNotifyQueue() throws Exception {
        LOG.info("通知キュー確認");
        NotifyQueuesEntity notify = NotifyQueuesDao.get().selectOnTypeAndId(QueueNotification.TYPE_KNOWLEDGE_INSERT, knowledgeId);
        Assert.assertNotNull(notify);
    }
    
    /**
     * 通知を処理
     * @throws Exception
     */
    @Test
    @Order(order = 5)
    public void testExecNotifyQueue() throws Exception {
        LOG.info("通知キューを処理");
        MailConfigsEntity mailConfig = new MailConfigsEntity(AppConfig.get().getSystemName());
        mailConfig.setHost("example.com");
        mailConfig.setPort(25);
        mailConfig.setAuthType(AuthType.None.getValue());
        MailConfigsDao.get().insert(mailConfig); // メール送信設定
        
        NotifyMailBat.main(null);
        NotifyQueuesEntity notify = NotifyQueuesDao.get().selectOnTypeAndId(QueueNotification.TYPE_KNOWLEDGE_INSERT, knowledgeId);
        Assert.assertNull(notify);
    }
    
    /**
     * ユーザに通知が届いていることを確認
     * @throws Exception
     */
    @Test
    @Order(order = 6)
    public void testCheckUserNotification() throws Exception {
        LOG.info("通知確認");
        NotificationsEntity notification = NotificationsDao.get().selectOnKey(new Long(1)); // 1件も通知がないはず
        Assert.assertNull(notification);
    }
    
    /**
     * 記事を参照できる
     * @throws Exception
     */
    @Test
    @Order(order = 7)
    public void testKnowledgeView() throws Exception {
        LOG.info("記事を参照");
        LoginedUser user = getLoginUser("integration-test-user-01");
        List<KnowledgesEntity> knowledges = KnowledgeLogic.get().searchKnowledge(null, user, 0, 100);
        Assert.assertEquals(1, knowledges.size());
        
        KnowledgesEntity knowledge = KnowledgeLogic.get().select(knowledges.get(0).getKnowledgeId(), user);
        Assert.assertNotNull(knowledge);
        Assert.assertEquals(knowledgeId, knowledge.getKnowledgeId().intValue());
        
        user = getLoginUser("integration-test-user-02");
        knowledges = KnowledgeLogic.get().searchKnowledge(null, user, 0, 100);
        Assert.assertEquals(0, knowledges.size()); // 登録者以外は参照できない
    }
    
    
}
