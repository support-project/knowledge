package org.support.project.knowledge.logic.notification;

import java.util.List;
import java.util.Locale;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.SystemConfig;
import org.support.project.knowledge.entity.MailLocaleTemplatesEntity;
import org.support.project.knowledge.logic.MailLogic;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.config.WebConfig;
import org.support.project.web.dao.MailConfigsDao;
import org.support.project.web.dao.MailsDao;
import org.support.project.web.dao.NotificationsDao;
import org.support.project.web.dao.SystemConfigsDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.MailConfigsEntity;
import org.support.project.web.entity.MailsEntity;
import org.support.project.web.entity.NotificationsEntity;
import org.support.project.web.entity.ProvisionalRegistrationsEntity;
import org.support.project.web.entity.SystemConfigsEntity;
import org.support.project.web.entity.UsersEntity;

import net.arnx.jsonic.JSON;


/**
 * 管理者へユーザの仮登録を通知（承認する必要があるため）
 * @author koda
 */
@DI(instance = Instance.Singleton)
public class AcceptCheckUserNotification extends AbstractNotification {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(AcceptCheckUserNotification.class);
    /** インスタンス取得 */
    public static AcceptCheckUserNotification get() {
        return Container.getComp(AcceptCheckUserNotification.class);
    }

    private static final String TEMPLATE = MailLogic.NOTIFY_ACCEPT_USER;
    
    private NotificationsEntity insertNotification(ProvisionalRegistrationsEntity registrationsEntity) {
        NotificationsEntity notification = new NotificationsEntity();
        notification.setTitle(TEMPLATE);
        notification.setContent(JSON.encode(registrationsEntity));
        notification = NotificationsDao.get().insert(notification);
        return notification;
    }
    
    /**
     * 管理者へユーザの仮登録を通知（承認する必要があるため）
     * 
     * @param entity
     */
    public void sendNotifyAcceptUser(ProvisionalRegistrationsEntity registrationsEntity) {
        LOG.trace("sendNotifyAcceptUser");
        SystemConfigsDao configsDao = SystemConfigsDao.get();
        SystemConfigsEntity configsEntity = configsDao.selectOnKey(SystemConfig.USER_ADD_NOTIFY, AppConfig.get().getSystemName());
        if (configsEntity != null && SystemConfig.USER_ADD_NOTIFY_ON.equals(configsEntity.getConfigValue())) {
            MailConfigsDao mailConfigsDao = MailConfigsDao.get();
            MailConfigsEntity mailConfigsEntity = mailConfigsDao.selectOnKey(org.support.project.knowledge.config.AppConfig.get().getSystemName());
            
            // 通知の登録
            NotificationsEntity notification = insertNotification(registrationsEntity);
            
            // 管理者へのメール通知がONなので、メールを送信する
            UsersDao usersDao = UsersDao.get();
            List<UsersEntity> users = usersDao.selectOnRoleKey(WebConfig.ROLE_ADMIN);
            for (UsersEntity entity : users) {
                // 通知を管理者ユーザに紐付け
                insertUserNotification(notification, entity.getUserId());
                
                if (mailConfigsEntity == null) {
                    // メールの設定が登録されていなければ、送信処理は終了
                    return;
                }
                if (!StringUtils.isEmailAddress(entity.getMailAddress())) {
                    // 送信先のメールアドレスが不正なので、送信処理は終了
                    LOG.warn("mail targget [" + entity.getMailAddress() + "] is wrong.");
                    continue;
                }
                Locale locale = entity.getLocale();
                MailLocaleTemplatesEntity mailConfig = MailLogic.get().load(locale, TEMPLATE);

                String contents = mailConfig.getContent();
                contents = contents.replace("{UserKey}", registrationsEntity.getUserKey());
                contents = contents.replace("{UserName}", registrationsEntity.getUserName());
                
                String title = mailConfig.getTitle();

                MailsDao mailsDao = MailsDao.get();
                MailsEntity mailsEntity = new MailsEntity();
                String mailId = idGen("Notify");
                mailsEntity.setMailId(mailId);
                mailsEntity.setStatus(MailLogic.MAIL_STATUS_UNSENT);
                mailsEntity.setToAddress(entity.getMailAddress());
                mailsEntity.setToName(entity.getUserName());
                mailsEntity.setTitle(title);
                mailsEntity.setContent(contents);
                mailsDao.insert(mailsEntity);
            }
        }
    }
    
    @Override
    public void convNotification(NotificationsEntity notificationsEntity, LoginedUser loginedUser, TARGET target) {
        ProvisionalRegistrationsEntity user = JSON.decode(notificationsEntity.getContent(), ProvisionalRegistrationsEntity.class);
        MailLocaleTemplatesEntity template = MailLogic.get().load(loginedUser.getLocale(), notificationsEntity.getTitle());
        
        String title = template.getTitle();
        notificationsEntity.setTitle(title);
        
        if (target == TARGET.detail) {
            String contents = template.getContent();
            contents = contents.replace("{UserKey}", user.getUserKey());
            contents = contents.replace("{UserName}", user.getUserName());
            notificationsEntity.setContent(contents);
        }
    }
}
