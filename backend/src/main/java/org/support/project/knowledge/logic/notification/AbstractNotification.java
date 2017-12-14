package org.support.project.knowledge.logic.notification;

import org.support.project.knowledge.logic.MailLogic;
import org.support.project.knowledge.logic.NotificationLogic;
import org.support.project.web.dao.UserNotificationsDao;
import org.support.project.web.entity.NotificationsEntity;
import org.support.project.web.entity.UserNotificationsEntity;

public abstract class AbstractNotification implements Notification {
    /**
     * メール送信のIDを生成
     * @param string
     * @return
     */
    protected String idGen(String label) {
        return MailLogic.get().idGen(label);
    }

    /**
     * 指定の通知をユーザに紐付け
     * @param notification
     * @param userId
     */
    protected void insertUserNotification(NotificationsEntity notification, int userId) {
        UserNotificationsEntity userNotification = new UserNotificationsEntity(notification.getNo(), userId);
        userNotification.setStatus(NotificationLogic.STATUS_UNREAD);
        UserNotificationsDao.get().insert(userNotification);
    }
    
}
