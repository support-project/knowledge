package org.support.project.knowledge.logic.notification;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.support.project.common.config.INT_FLAG;
import org.support.project.common.test.Order;
import org.support.project.common.test.OrderedRunner;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.dao.NotifyQueuesDao;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.NotifyQueuesEntity;
import org.support.project.knowledge.logic.NotificationLogic;
import org.support.project.knowledge.logic.notification.Notification.TARGET;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.entity.NotificationsEntity;

@RunWith(OrderedRunner.class)
public class KnowledgeUpdateNotificationTest extends NotificationTestCommon {
    @Test
    @Order(order = 1)
    public void testInsertNotifyQueue() throws Exception {
        KnowledgesEntity entity = new KnowledgesEntity();
        entity.setKnowledgeId(new Long(-1));
        KnowledgeUpdateNotification notify = KnowledgeUpdateNotification.get();
        notify.setKnowledge(entity);
        notify.setType(QueueNotification.TYPE_KNOWLEDGE_INSERT);
        notify.insertNotifyQueue();
        
        List<NotifyQueuesEntity> list = NotifyQueuesDao.get().selectAll();
        LOG.info(list);
        
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(new Long(-1), list.get(0).getId());
        Assert.assertEquals(QueueNotification.TYPE_KNOWLEDGE_INSERT, list.get(0).getType().intValue());
        
        NotifyQueuesDao.get().delete(list.get(0));
    }
    
    @Test
    @Order(order = 2)
    public void testNotify() throws Exception {
        KnowledgesEntity entity = insertKnowledge("テスト", loginedUser);
        entity = KnowledgesDao.get().selectOnKey(entity.getKnowledgeId());
        Assert.assertEquals(INT_FLAG.OFF.getValue(), entity.getNotifyStatus().intValue());
        
        List<NotifyQueuesEntity> list = NotifyQueuesDao.get().selectAll();
        LOG.info(list);
        
        Assert.assertEquals(1, list.size());
        
        KnowledgeUpdateNotification.get().notify(list.get(0));
        
        entity = KnowledgesDao.get().selectOnKey(entity.getKnowledgeId());
        Assert.assertEquals(INT_FLAG.ON.getValue(), entity.getNotifyStatus().intValue());
        
        List<NotificationsEntity> notifications = NotificationLogic.get().getNotification(loginedUser.getUserId(), 0, false);
        LOG.info(notifications.get(0));
        Assert.assertEquals(1, notifications.size()); // 登録した通知が届いている
        
        // 変換処理がエラーにならないこと
        KnowledgeUpdateNotification.get().convNotification(notifications.get(0), loginedUser, TARGET.detail);
        
        // Desktop notification
        KnowledgeUpdateNotification notify = KnowledgeUpdateNotification.get();
        notify.setKnowledge(entity);
        notify.setType(QueueNotification.TYPE_KNOWLEDGE_INSERT);

        MessageResult result = notify.getMessage(loginedUser, loginedUser.getLocale());
        LOG.info(result);
    }

}
