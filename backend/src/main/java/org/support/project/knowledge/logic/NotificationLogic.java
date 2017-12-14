package org.support.project.knowledge.logic;

import java.util.Iterator;
import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.knowledge.logic.notification.AcceptCheckUserNotification;
import org.support.project.knowledge.logic.notification.AddUserNotification;
import org.support.project.knowledge.logic.notification.CommentInsertNotification;
import org.support.project.knowledge.logic.notification.CommentLikedNotification;
import org.support.project.knowledge.logic.notification.EventNotificationByWeek;
import org.support.project.knowledge.logic.notification.KnowledgeUpdateNotification;
import org.support.project.knowledge.logic.notification.LikeInsertNotification;
import org.support.project.knowledge.logic.notification.Notification;
import org.support.project.knowledge.logic.notification.Notification.TARGET;
import org.support.project.knowledge.logic.notification.ParticipateChangeStatusForParticipantNotification;
import org.support.project.knowledge.logic.notification.ParticipateForParticipantNotification;
import org.support.project.knowledge.logic.notification.ParticipateForSponsorNotification;
import org.support.project.knowledge.logic.notification.ParticipateRemoveForSponsorNotification;
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
        } else if (MailLogic.NOTIFY_INSERT_LIKE_COMMENT_MYITEM.equals(category)) {
            return CommentLikedNotification.get();
        } else if (MailLogic.NOTIFY_EVENT.equals(category)) {
            return EventNotificationByWeek.get();
        } else if (MailLogic.NOTIFY_REGISTRATION_EVENT.equals(category)) {
            return ParticipateForParticipantNotification.get();
        } else if (MailLogic.NOTIFY_ADD_PARTICIPATE.equals(category)) {
            return ParticipateForSponsorNotification.get();
        } else if (MailLogic.NOTIFY_CHANGE_EVENT_STATUS.equals(category)) {
            return ParticipateChangeStatusForParticipantNotification.get();
        } else if (MailLogic.NOTIFY_REMOVE_PARTICIPATE.equals(category)) {
            return ParticipateRemoveForSponsorNotification.get();
        } else if (MailLogic.NOTIFY_ADD_USER.equals(category)) {
            return AddUserNotification.get();
        } else if (MailLogic.NOTIFY_ACCEPT_USER.equals(category)) {
            return AcceptCheckUserNotification.get();
        }
        return null;
    }
    
    
    public List<NotificationsEntity> getNotification(LoginedUser loginedUser, int offset, boolean all) {
        List<NotificationsEntity> notifications = super.getNotification(loginedUser.getUserId(), offset, all);
        for (Iterator<NotificationsEntity> iterator = notifications.iterator(); iterator.hasNext();) {
            NotificationsEntity notificationsEntity = (NotificationsEntity) iterator.next();
            Notification notification = getNotification(notificationsEntity.getTitle());
            if (notification == null) {
                iterator.remove();
            } else {
                notification.convNotification(notificationsEntity, loginedUser, Notification.TARGET.list);
            }
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
        if (notificationsEntity == null) {
            return null;
        }
        Notification notification = getNotification(notificationsEntity.getTitle());
        notification.convNotification(notificationsEntity, loginedUser, TARGET.detail);
        return notificationsEntity;
    }
    
    public NotificationsEntity previous(long no, LoginedUser loginedUser, boolean all) {
        if (loginedUser == null) {
            return null;
        }
        NotificationsEntity notificationsEntity = super.previous(no, loginedUser.getUserId(), all);
        if (notificationsEntity == null) {
            return null;
        }
        Notification notification = getNotification(notificationsEntity.getTitle());
        notification.convNotification(notificationsEntity, loginedUser, TARGET.detail);
        return notificationsEntity;
    }
    public NotificationsEntity next(long no, LoginedUser loginedUser, boolean all) {
        if (loginedUser == null) {
            return null;
        }
        NotificationsEntity notificationsEntity = super.next(no, loginedUser.getUserId(), all);
        if (notificationsEntity == null) {
            return null;
        }
        Notification notification = getNotification(notificationsEntity.getTitle());
        notification.convNotification(notificationsEntity, loginedUser, TARGET.detail);
        return notificationsEntity;
    }
    
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void markAllAsRead(String no, Integer userID) {
        String[] nos = no.split(",");
        for (String n : nos) {
            if (StringUtils.isInteger(n)) {
                int num = Integer.parseInt(n);
                super.setStatus(userID, num, NotificationLogic.STATUS_READED);
            }
        }
    }
}
