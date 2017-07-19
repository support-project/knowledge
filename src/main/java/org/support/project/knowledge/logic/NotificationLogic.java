package org.support.project.knowledge.logic;

import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.MailLocaleTemplatesEntity;
import org.support.project.knowledge.vo.notification.KnowledgeUpdate;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.dao.NotificationsDao;
import org.support.project.web.dao.UserNotificationsDao;
import org.support.project.web.entity.NotificationsEntity;
import org.support.project.web.entity.UserNotificationsEntity;
import org.support.project.web.entity.UsersEntity;

import net.arnx.jsonic.JSON;

public class NotificationLogic extends org.support.project.web.logic.NotificationLogic {
    public static enum TARGET {
        list, detail
    }
    
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
    
    /**
     * Knowledgeの登録/更新時の通知情報を作成
     * @param knowledge
     * @return
     */
    public NotificationsEntity insertNotificationOnKnowledgeUpdate(KnowledgesEntity knowledge) {
        // 通知情報を作成
        NotificationsEntity notification = new NotificationsEntity();
        if (knowledge.getNotifyStatus() == null || knowledge.getNotifyStatus().intValue() == 0) {
            notification.setTitle(MailLogic.NOTIFY_INSERT_KNOWLEDGE);
        } else {
            notification.setTitle(MailLogic.NOTIFY_UPDATE_KNOWLEDGE);
        }
        KnowledgeUpdate update = new KnowledgeUpdate();
        update.setKnowledgeId(knowledge.getKnowledgeId());
        update.setKnowledgeTitle(knowledge.getTitle());
        update.setUpdateUser(knowledge.getUpdateUserName());
        notification.setContent(JSON.encode(update));
        notification = NotificationsDao.get().insert(notification);
        return notification;
    }
    

    /**
     * ユーザへの通知を意味のある形へ変換
     * @param notificationsEntity
     * @param loginedUser 
     */
    public void convNotification(NotificationsEntity notificationsEntity, LoginedUser loginedUser, TARGET target) {
        String category = notificationsEntity.getTitle();
        if (MailLogic.NOTIFY_INSERT_KNOWLEDGE.equals(category) || MailLogic.NOTIFY_UPDATE_KNOWLEDGE.equals(category)) {
            KnowledgeUpdate update = JSON.decode(notificationsEntity.getContent(), KnowledgeUpdate.class);
            MailLocaleTemplatesEntity template = MailLogic.get().load(loginedUser.getLocale(), MailLogic.NOTIFY_INSERT_KNOWLEDGE);
            
            String title = template.getTitle();
            title = title.replace("{KnowledgeId}", String.valueOf(update.getKnowledgeId()));
            title = title.replace("{KnowledgeTitle}", StringUtils.abbreviate(update.getKnowledgeTitle(), 80));
            notificationsEntity.setTitle(title);
            
            if (target == TARGET.detail) {
                String contents = template.getContent();
                contents = contents.replace("{KnowledgeId}", String.valueOf(update.getKnowledgeId()));
                contents = contents.replace("{KnowledgeTitle}", update.getKnowledgeTitle());
                contents = contents.replace("{User}", update.getUpdateUser());
                contents = contents.replace("{URL}", NotifyLogic.get().makeURL(update.getKnowledgeId()));
                KnowledgesEntity entity = KnowledgeLogic.get().select(update.getKnowledgeId(), loginedUser);
                if (entity != null) {
                    contents = contents.replace("{Contents}", entity.getContent());
                } else {
                    contents = contents.replace("{Contents}", "");
                }
                notificationsEntity.setContent(contents);
            }
        }
        
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
        NotificationsEntity notification = NotificationsDao.get().selectOnKey(no);
        convNotification(notification, loginedUser, TARGET.detail);
        return notification;
    }
    

    
    
}
