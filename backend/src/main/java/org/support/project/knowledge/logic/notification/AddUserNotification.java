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
import org.support.project.web.entity.SystemConfigsEntity;
import org.support.project.web.entity.UsersEntity;

import net.arnx.jsonic.JSON;

/**
 * 管理者へユーザが追加されたことを通知
 * @author koda
 */
@DI(instance = Instance.Singleton)
public class AddUserNotification extends AbstractNotification {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(AddUserNotification.class);
    /** インスタンス取得 */
    public static AddUserNotification get() {
        return Container.getComp(AddUserNotification.class);
    }
    
    private static final String TEMPLATE = MailLogic.NOTIFY_ADD_USER;
    
    private NotificationsEntity insertNotification(UsersEntity user) {
        NotificationsEntity notification = new NotificationsEntity();
        notification.setTitle(TEMPLATE);
        user.setPassword("");
        notification.setContent(JSON.encode(user));
        notification = NotificationsDao.get().insert(notification);
        return notification;
    }
    
    /**
     * 管理者へユーザが登録されたことを通知
     * 
     * @param user
     */
    public void sendNotifyAddUser(UsersEntity user) {
        LOG.trace("sendNotifyAddUser");
        SystemConfigsDao configsDao = SystemConfigsDao.get();
        SystemConfigsEntity configsEntity = configsDao.selectOnKey(SystemConfig.USER_ADD_NOTIFY, AppConfig.get().getSystemName());
        if (configsEntity != null && SystemConfig.USER_ADD_NOTIFY_ON.equals(configsEntity.getConfigValue())) {
            // 通知の登録
            NotificationsEntity notification = insertNotification(user);
            
            // メール送信の設定のロード
            MailConfigsDao mailConfigsDao = MailConfigsDao.get();
            MailConfigsEntity mailConfigsEntity = mailConfigsDao.selectOnKey(org.support.project.knowledge.config.AppConfig.get().getSystemName());
            // 管理者へのメール通知がONなので、メールを送信する
            UsersDao usersDao = UsersDao.get();
            List<UsersEntity> users = usersDao.selectOnRoleKey(WebConfig.ROLE_ADMIN);
            for (UsersEntity entity : users) {
                // 通知を管理者ユーザに紐付け
                insertUserNotification(notification, entity.getUserId());
                
                // メール送信するかチェック
                if (mailConfigsEntity == null) {
                    // メールの設定が登録されていなければ、送信処理は終了
                    continue;
                }
                if (!StringUtils.isEmailAddress(entity.getMailAddress())) {
                    // 送信先のメールアドレスが不正なので、送信処理は終了
                    LOG.warn("mail targget [" + entity.getMailAddress() + "] is wrong.");
                    continue;
                }
                Locale locale = entity.getLocale();
                MailLocaleTemplatesEntity mailConfig = MailLogic.get().load(locale, TEMPLATE);

                String contents = mailConfig.getContent();
                contents = contents.replace("{UserId}", String.valueOf(user.getUserId()));
                contents = contents.replace("{UserName}", user.getUserName());
                contents = contents.replace("{UserMail}", user.getMailAddress());
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
        UsersEntity user = JSON.decode(notificationsEntity.getContent(), UsersEntity.class);
        MailLocaleTemplatesEntity template = MailLogic.get().load(loginedUser.getLocale(), notificationsEntity.getTitle());
        
        String title = template.getTitle();
        notificationsEntity.setTitle(title);
        
        if (target == TARGET.detail) {
            String contents = template.getContent();
            contents = contents.replace("{UserId}", String.valueOf(user.getUserId()));
            contents = contents.replace("{UserName}", user.getUserName());
            contents = contents.replace("{UserMail}", user.getMailAddress());
            notificationsEntity.setContent(contents);
        }
    }

}
