package org.support.project.knowledge.logic.notification;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.support.project.common.test.Order;
import org.support.project.common.test.OrderedRunner;
import org.support.project.knowledge.dao.LikesDao;
import org.support.project.knowledge.dao.NotifyQueuesDao;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.LikesEntity;
import org.support.project.knowledge.entity.NotifyQueuesEntity;
import org.support.project.knowledge.logic.LikeLogic;
import org.support.project.knowledge.logic.NotificationLogic;
import org.support.project.knowledge.logic.notification.Notification.TARGET;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.entity.NotificationsEntity;

@RunWith(OrderedRunner.class)
public class LikeInsertNotificationTest extends NotificationTestCommon {
    @Test
    @Order(order = 1)
    public void testNotification() throws Exception {
        // Knowledgeを登録し、それにいいねを押した
        KnowledgesEntity knowledge = super.insertKnowledge("テスト", loginedUser);
        LikeLogic.get().addLike(knowledge.getKnowledgeId(), loginedUser2, loginedUser.getLocale());
        
        List<NotifyQueuesEntity> list = NotifyQueuesDao.get().selectAll();
        LOG.info(list);
        
        Assert.assertEquals(2, list.size()); // Knowledge登録と、イイネの通知が登録されている
        Assert.assertEquals(knowledge.getKnowledgeId(), list.get(0).getId());
        Assert.assertEquals(QueueNotification.TYPE_KNOWLEDGE_INSERT, list.get(0).getType().intValue());
        Assert.assertEquals(QueueNotification.TYPE_KNOWLEDGE_LIKE, list.get(1).getType().intValue());
        
        LikeInsertNotification.get().notify(list.get(1)); // Likeの通知キューを処理
        
        List<NotificationsEntity> notifications = NotificationLogic.get().getNotification(loginedUser.getUserId(), 0, false);
        LOG.info(notifications.get(0));
        Assert.assertEquals(1, notifications.size()); // 登録した通知が届いている
        
        // 変換処理がエラーにならないこと
        LikeInsertNotification.get().convNotification(notifications.get(0), loginedUser, TARGET.detail);
        
        // Desktop notification
        LikesEntity like = LikesDao.get().selectOnKnowledge(knowledge.getKnowledgeId(), 0, 100).get(0);
        LikeInsertNotification notify = LikeInsertNotification.get();
        notify.setLike(like);
        notify.setType(QueueNotification.TYPE_KNOWLEDGE_LIKE);

        MessageResult result = notify.getMessage(loginedUser, loginedUser.getLocale());
        LOG.info(result.getMessage());
    }
}
