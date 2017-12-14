package org.support.project.knowledge.logic.notification;

import org.support.project.common.config.Resources;
import org.support.project.common.util.StringUtils;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.MailLocaleTemplatesEntity;
import org.support.project.knowledge.logic.EventsLogic;
import org.support.project.knowledge.logic.MailLogic;
import org.support.project.knowledge.logic.NotificationLogic;
import org.support.project.knowledge.logic.NotifyLogic;
import org.support.project.knowledge.vo.notification.Participate;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.dao.NotificationsDao;
import org.support.project.web.dao.UserNotificationsDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.NotificationsEntity;
import org.support.project.web.entity.UserNotificationsEntity;
import org.support.project.web.entity.UsersEntity;

import net.arnx.jsonic.JSON;

public abstract class AbstractParticipateNotification extends AbstractNotification implements ParticipateNotification {

    
    protected void insertNotification(KnowledgesEntity knowledge, Integer participant, Integer status, String template) {
        NotificationsEntity notification = new NotificationsEntity();
        notification.setTitle(template);
        
        Participate info = new Participate();
        info.setKnowledgeId(knowledge.getKnowledgeId());
        info.setKnowledgeTitle(knowledge.getTitle());
        info.setUpdateUser(knowledge.getUpdateUserName());
        
        info.setParticipant(participant);
        info.setStatus(status);
        
        notification.setContent(JSON.encode(info));
        notification = NotificationsDao.get().insert(notification);
        
        if (template.equals(MailLogic.NOTIFY_ADD_PARTICIPATE) || template.equals(MailLogic.NOTIFY_REMOVE_PARTICIPATE)) {
            // 参加登録があったことを、開催者へ通知 / 参加キャンセルを開催者へ通知
            UserNotificationsEntity userNotification = new UserNotificationsEntity(notification.getNo(), knowledge.getInsertUser());
            userNotification.setStatus(NotificationLogic.STATUS_UNREAD);
            UserNotificationsDao.get().insert(userNotification);
        } else {
            // 参加登録したことを、参加者へ通知 / キャンセル待ちのユーザに、参加登録になったことを通知
            UserNotificationsEntity userNotification = new UserNotificationsEntity(notification.getNo(), participant);
            userNotification.setStatus(NotificationLogic.STATUS_UNREAD);
            UserNotificationsDao.get().insert(userNotification);
        }
    }

    
    @Override
    public void convNotification(NotificationsEntity notificationsEntity, LoginedUser loginedUser, TARGET target) {
        Participate info = JSON.decode(notificationsEntity.getContent(), Participate.class);
        String templateString = notificationsEntity.getTitle();
        MailLocaleTemplatesEntity template = MailLogic.get().load(loginedUser.getLocale(), notificationsEntity.getTitle());
        
        String title = template.getTitle();
        title = title.replace("{KnowledgeId}", String.valueOf(info.getKnowledgeId()));
        title = title.replace("{KnowledgeTitle}", StringUtils.abbreviate(info.getKnowledgeTitle(), 80));
        notificationsEntity.setTitle(title);
        
        if (target == TARGET.detail) {
            String contents = template.getContent();
            contents = contents.replace("{KnowledgeId}", String.valueOf(info.getKnowledgeId()));
            contents = contents.replace("{KnowledgeTitle}", info.getKnowledgeTitle());
            contents = contents.replace("{URL}", NotifyLogic.get().makeURL(info.getKnowledgeId()));
            UsersEntity participant = UsersDao.get().selectOnKey(info.getParticipant());
            if (participant != null) {
                Resources resources = Resources.getInstance(loginedUser.getLocale());
                StringBuilder builder = new StringBuilder();
                int status = info.getStatus();
                if (MailLogic.NOTIFY_ADD_PARTICIPATE.equals(templateString) || MailLogic.NOTIFY_REMOVE_PARTICIPATE.equals(templateString)) {
                    // 参加登録があったことを、開催者へ通知 || 参加キャンセルを開催者へ通知
                    builder.append(participant.getUserName());
                    if (MailLogic.NOTIFY_ADD_PARTICIPATE.equals(templateString)) {
                        // 参加登録の場合、ステータスも表示
                        if (status == EventsLogic.STATUS_PARTICIPATION) {
                            builder.append("[").append(resources.getResource("knowledge.view.label.status.participation")).append("]");
                        } else {
                            builder.append("[").append(resources.getResource("knowledge.view.label.status.wait.cansel")).append("]");
                        }
                    }
                    contents = contents.replace("{Participant}", builder.toString());
                } else {
                    // 参加登録したことを参加者へ通知 // キャンセル待ちの本登録の通知
                    if (status == EventsLogic.STATUS_PARTICIPATION) {
                        builder.append(resources.getResource("knowledge.view.label.status.participation"));
                    } else {
                        builder.append(resources.getResource("knowledge.view.label.status.wait.cansel"));
                    }
                    contents = contents.replace("{Status}", builder.toString());
                }
            }
            notificationsEntity.setContent(contents);
        }
    }


}
