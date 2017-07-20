package org.support.project.knowledge.logic;

import java.util.List;

import org.support.project.di.Container;
import org.support.project.knowledge.logic.notification.CommentInsertNotification;
import org.support.project.knowledge.logic.notification.KnowledgeUpdateNotification;
import org.support.project.knowledge.logic.notification.LikeInsertNotification;
import org.support.project.knowledge.logic.notification.Notification;
import org.support.project.knowledge.logic.notification.Notification.TARGET;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.dao.NotificationsDao;
import org.support.project.web.dao.UserNotificationsDao;
import org.support.project.web.entity.NotificationsEntity;
import org.support.project.web.entity.UserNotificationsEntity;
import org.support.project.web.entity.UsersEntity;

public class NotificationLogic extends org.support.project.web.logic.NotificationLogic {
    /**
     * Get instance
     * @return instance
     */
    public static NotificationLogic get() {
        return Container.getComp(NotificationLogic.class);
    }
    
    /**
     * ユーザに通知をセット
     * @param notification
     * @param usersEntity
     */
    public void insertUserNotification(NotificationsEntity notification, UsersEntity usersEntity) {
        UserNotificationsEntity userNotification = new UserNotificationsEntity(notification.getNo(), usersEntity.getUserId());
        userNotification.setStatus(NotificationLogic.STATUS_UNREAD);
        UserNotificationsDao.get().insert(userNotification);
    }

    public Notification getNotification(String category) {
        if (MailLogic.NOTIFY_INSERT_KNOWLEDGE.equals(category) || MailLogic.NOTIFY_UPDATE_KNOWLEDGE.equals(category)) {
            return KnowledgeUpdateNotification.get();
        } else if (MailLogic.NOTIFY_INSERT_COMMENT_MYITEM.equals(category) || MailLogic.NOTIFY_INSERT_COMMENT.equals(category)) {
            return CommentInsertNotification.get();
        } else if (MailLogic.NOTIFY_INSERT_LIKE_MYITEM.equals(category)) {
            return LikeInsertNotification.get();
        }
        return null;
    }
    
    
    public List<NotificationsEntity> getNotification(LoginedUser loginedUser, int offset) {
        List<NotificationsEntity> notifications = super.getNotification(loginedUser.getUserId(), offset);
        for (NotificationsEntity notificationsEntity : notifications) {
            Notification notification = getNotification(notificationsEntity.getTitle());
            notification.convNotification(notificationsEntity, loginedUser, Notification.TARGET.list);
        }
        return notifications;
    }
    
    
    /**
     * 通知の読み込み
     * @param no
     * @param loginedUser
     * @return
     */
    public NotificationsEntity load(long no, LoginedUser loginedUser) {
        UserNotificationsEntity userNotification = UserNotificationsDao.get().selectOnKey(no, loginedUser.getUserId());
        if (userNotification == null) {
            return null;
        }
        NotificationsEntity notificationsEntity = NotificationsDao.get().selectOnKey(no);
        Notification notification = getNotification(notificationsEntity.getTitle());
        notification.convNotification(notificationsEntity, loginedUser, TARGET.detail);
        return notificationsEntity;
    }
    
}
