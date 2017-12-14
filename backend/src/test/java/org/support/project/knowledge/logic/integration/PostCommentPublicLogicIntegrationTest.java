package org.support.project.knowledge.logic.integration;

import java.util.ArrayList;
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
import org.support.project.knowledge.dao.NotifyQueuesDao;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.NotifyQueuesEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.MailLogic;
import org.support.project.knowledge.logic.notification.QueueNotification;
import org.support.project.ormapping.common.DBUserPool;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.dao.MailConfigsDao;
import org.support.project.web.dao.MailsDao;
import org.support.project.web.dao.NotificationsDao;
import org.support.project.web.dao.UserNotificationsDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.MailConfigsEntity;
import org.support.project.web.entity.NotificationsEntity;
import org.support.project.web.entity.UserNotificationsEntity;
import org.support.project.web.entity.UsersEntity;


/**
 * 記事登録後に、コメントを登録
 * @author koda
 */
@RunWith(OrderedRunner.class)
public class PostCommentPublicLogicIntegrationTest extends TestCommon {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(PostCommentPublicLogicIntegrationTest.class);
    
    private static long knowledgeId; // テストメソッド単位にインスタンスが歳生成されるようなので、staticで保持する
    
    /**
     * ユーザを2つ登録
     * @throws Exception
     */
    @Test
    @Order(order = 1)
    public void testUserInsert() throws Exception {
        LOG.info("ユーザ登録");
        MailConfigsEntity mailConfig = new MailConfigsEntity(AppConfig.get().getSystemName());
        mailConfig.setHost("example.com");
        mailConfig.setPort(25);
        mailConfig.setAuthType(AuthType.None.getValue());
        MailConfigsDao.get().insert(mailConfig); // メール送信設定

        addUser("integration-test-user-01");
        addUser("integration-test-user-02");
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
        
        KnowledgesEntity knowledge = super.insertKnowledge("integration-test-knowledge-01", loginUser);
        knowledgeId = knowledge.getKnowledgeId();
        
        NotifyQueuesEntity notify = NotifyQueuesDao.get().selectOnTypeAndId(QueueNotification.TYPE_KNOWLEDGE_INSERT, knowledgeId);
        Assert.assertNotNull(notify);
        NotifyMailBat.main(null);
        notify = NotifyQueuesDao.get().selectOnTypeAndId(QueueNotification.TYPE_KNOWLEDGE_INSERT, knowledgeId);
        Assert.assertNull(notify);

        NotificationsEntity notification = NotificationsDao.get().selectOnKey(new Long(1)); // 1件だけ通知が登録されているはず
        Assert.assertNotNull(notification);
        
        UsersEntity user = UsersDao.get().selectOnUserKey("integration-test-user-01");
        UserNotificationsEntity userNotification = UserNotificationsDao.get().selectOnKey(notification.getNo(), user.getUserId());
        Assert.assertNotNull(userNotification);
        user = UsersDao.get().selectOnUserKey("integration-test-user-02");
        userNotification = UserNotificationsDao.get().selectOnKey(notification.getNo(), user.getUserId());
        Assert.assertNotNull(userNotification);
        
        int count = MailsDao.get().selectCountAll();
        Assert.assertEquals(2, count);
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
        Assert.assertEquals(1, knowledges.size());
        
        knowledge = KnowledgeLogic.get().select(knowledges.get(0).getKnowledgeId(), user);
        Assert.assertNotNull(knowledge);
        Assert.assertEquals(knowledgeId, knowledge.getKnowledgeId().intValue());
    }
    
    /**
     * コメント追加
     * @throws Exception
     */
    @Test
    @Order(order = 8)
    public void testInsertComment() throws Exception {
        LoginedUser user = getLoginUser("integration-test-user-02");
        CommentsEntity comment = KnowledgeLogic.get().saveComment(knowledgeId, "コメント", new ArrayList<>(), user);
        
        NotifyQueuesEntity notify = NotifyQueuesDao.get().selectOnTypeAndId(QueueNotification.TYPE_KNOWLEDGE_COMMENT, comment.getCommentNo());
        Assert.assertNotNull(notify);
        NotifyMailBat.main(null);
        notify = NotifyQueuesDao.get().selectOnTypeAndId(QueueNotification.TYPE_KNOWLEDGE_COMMENT, comment.getCommentNo());
        Assert.assertNull(notify);
        
        NotificationsEntity notification = NotificationsDao.get().selectOnKey(new Long(2));
        Assert.assertNotNull(notification);
        Assert.assertEquals(MailLogic.NOTIFY_INSERT_COMMENT_MYITEM, notification.getTitle());
        
        user = getLoginUser("integration-test-user-01");
        UserNotificationsEntity userNotification = UserNotificationsDao.get().selectOnKey(notification.getNo(), user.getUserId());
        Assert.assertNotNull(userNotification);

        notification = NotificationsDao.get().selectOnKey(new Long(3));
        Assert.assertNotNull(notification);
        Assert.assertEquals(MailLogic.NOTIFY_INSERT_COMMENT, notification.getTitle());
        
        user = getLoginUser("integration-test-user-02");
        userNotification = UserNotificationsDao.get().selectOnKey(notification.getNo(), user.getUserId());
        Assert.assertNotNull(userNotification);
    }
    
    

    
    
}
