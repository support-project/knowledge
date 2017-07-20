package org.support.project.knowledge.logic.notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.support.project.aop.Aspect;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.bat.WebhookBat;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.NotifyType;
import org.support.project.knowledge.dao.CommentsDao;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.dao.WebhookConfigsDao;
import org.support.project.knowledge.dao.WebhooksDao;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.MailLocaleTemplatesEntity;
import org.support.project.knowledge.entity.NotifyQueuesEntity;
import org.support.project.knowledge.entity.WebhookConfigsEntity;
import org.support.project.knowledge.entity.WebhooksEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.MailLogic;
import org.support.project.knowledge.logic.NotificationLogic;
import org.support.project.knowledge.logic.NotifyCommentLogic;
import org.support.project.knowledge.logic.NotifyLogic;
import org.support.project.knowledge.logic.WebhookLogic;
import org.support.project.knowledge.vo.notification.CommentInsert;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.dao.MailConfigsDao;
import org.support.project.web.dao.MailsDao;
import org.support.project.web.dao.NotificationsDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.MailConfigsEntity;
import org.support.project.web.entity.MailsEntity;
import org.support.project.web.entity.NotificationsEntity;
import org.support.project.web.entity.UsersEntity;

import net.arnx.jsonic.JSON;

/**
 * ナレッジにコメントが登録された場合の通知
 * @author koda
 */
@DI(instance = Instance.Singleton)
public class CommentInsertNotification extends AbstractNotification {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(CommentInsertNotification.class);
    /** インスタンス取得 */
    public static CommentInsertNotification get() {
        return Container.getComp(CommentInsertNotification.class);
    }

    /** knowledge id what is sended */
    private List<Long> sendedCommentKnowledgeIds = new ArrayList<>();
    
    @Override
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void notify(NotifyQueuesEntity notifyQueue) throws Exception {
        CommentsDao commentsDao = CommentsDao.get();
        CommentsEntity comment = commentsDao.selectOnKey(notifyQueue.getId());
        if (null == comment) {
            LOG.warn("Comment record not found. id: " + notifyQueue.getId());
            return;
        }

        KnowledgesDao knowledgesDao = KnowledgesDao.get();
        KnowledgesEntity knowledge = knowledgesDao.selectOnKey(comment.getKnowledgeId());
        if (null == knowledge) {
            LOG.warn("Knowledge record not found. id: " + notifyQueue.getId());
            return;
        }

        if (sendedCommentKnowledgeIds.contains(knowledge.getKnowledgeId())) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Knowledge [" + knowledge.getKnowledgeId() + "] ");
            }
            return;
        } else {
            sendedCommentKnowledgeIds.add(knowledge.getKnowledgeId());
        }
        
        sendCommentWebhook(comment, knowledge);

        UsersDao usersDao = UsersDao.get();
        UsersEntity commentUser = usersDao.selectOnKey(comment.getInsertUser());
        
        // 登録者に通知
        UsersEntity user = NotifyCommentLogic.get().getInsertUserOnComment(NotifyType.Mail, comment, knowledge);
        if (user != null) {
            // 画面での通知の情報を登録
            NotificationsEntity notification = insertNotificationOnComentInsert(knowledge, comment, commentUser,
                    MailLogic.NOTIFY_INSERT_COMMENT_MYITEM);
            // 通知とユーザの紐付け
            NotificationLogic.get().insertUserNotification(notification, user);
            
            Locale locale = user.getLocale();
            MailLocaleTemplatesEntity template = MailLogic.get().load(locale, MailLogic.NOTIFY_INSERT_COMMENT_MYITEM);
            sendCommentMail(comment, knowledge, commentUser, user, template);
        }
        // 宛先のナレッジにコメント追加で通知が欲しいユーザに通知
        NotificationsEntity notification = insertNotificationOnComentInsert(knowledge, comment, commentUser,
                MailLogic.NOTIFY_INSERT_COMMENT);
        List<UsersEntity> users = NotifyCommentLogic.get().getTargetUsersOnComment(NotifyType.Mail, comment, knowledge);
        for (UsersEntity target : users) {
            // 通知とユーザの紐付け
            NotificationLogic.get().insertUserNotification(notification, target);
            
            // 宛先にメール送信
            Locale locale = target.getLocale();
            MailLocaleTemplatesEntity template = MailLogic.get().load(locale, MailLogic.NOTIFY_INSERT_COMMENT);
            sendCommentMail(comment, knowledge, commentUser, target, template);
        }
    }
    
    /**
     * 画面での通知の情報を登録
     * @param knowledge
     * @param comment
     * @param commentUser
     * @param notifyInsertCommentMyitem 
     * @return
     */
    private NotificationsEntity insertNotificationOnComentInsert(KnowledgesEntity knowledge, CommentsEntity comment, UsersEntity commentUser,
            String template) {
        // 通知情報を作成
        NotificationsEntity notification = new NotificationsEntity();
        notification.setTitle(template);
        
        CommentInsert info = new CommentInsert();
        info.setKnowledgeId(knowledge.getKnowledgeId());
        info.setKnowledgeTitle(knowledge.getTitle());
        info.setUpdateUser(knowledge.getUpdateUserName());
        
        info.setCommentContents(comment.getComment());
        info.setCommentInsertUser(commentUser.getUserName());
        
        notification.setContent(JSON.encode(info));
        notification = NotificationsDao.get().insert(notification);
        return notification;
    }


    /**
     * コメントが追加されたメールを通知する
     * @param comment コメントの情報
     * @param knowledge ナレッジの情報
     * @param commentUser コメントを登録したユーザの情報
     * @param user メールの送信先
     * @throws Exception 
     */
    private void sendCommentMail(CommentsEntity comment, KnowledgesEntity knowledge, UsersEntity commentUser, UsersEntity user, MailLocaleTemplatesEntity template)
            throws Exception {
        MailConfigsDao mailConfigsDao = MailConfigsDao.get();
        MailConfigsEntity mailConfigsEntity = mailConfigsDao.selectOnKey(AppConfig.get().getSystemName());
        if (mailConfigsEntity == null) {
            // メールの設定が登録されていなければ、送信処理は終了
            LOG.info("mail config is not exists.");
            return;
        }
        if (!StringUtils.isEmailAddress(user.getMailAddress())) {
            // 送信先のメールアドレスが不正なので、送信処理は終了
            LOG.warn("mail targget [" + user.getMailAddress() + "] is wrong.");
            return;
        }

        MailsEntity mailsEntity = new MailsEntity();
        String mailId = idGen("Notify");
        mailsEntity.setMailId(mailId);
        mailsEntity.setStatus(MailLogic.MAIL_STATUS_UNSENT);
        mailsEntity.setToAddress(user.getMailAddress());
        mailsEntity.setToName(user.getUserName());
        
        String title = template.getTitle();
        title = title.replace("{KnowledgeId}", knowledge.getKnowledgeId().toString());
        title = title.replace("{KnowledgeTitle}", StringUtils.abbreviate(knowledge.getTitle(), 80));
        mailsEntity.setTitle(title);
        String contents = template.getContent();
        contents = contents.replace("{KnowledgeId}", knowledge.getKnowledgeId().toString());
        contents = contents.replace("{KnowledgeTitle}", knowledge.getTitle());
        contents = contents.replace("{Contents}", MailLogic.get().getMailContent(knowledge.getContent()));
        if (commentUser != null) {
            contents = contents.replace("{CommentInsertUser}", commentUser.getUserName());
        } else {
            contents = contents.replace("{CommentInsertUser}", "");
        }
        contents = contents.replace("{CommentContents}", MailLogic.get().getMailContent(comment.getComment()));
        contents = contents.replace("{URL}", NotifyLogic.get().makeURL(knowledge.getKnowledgeId()));

        mailsEntity.setContent(contents);
        if (LOG.isDebugEnabled()) {
            LOG.debug("News email has been registered. [type] comment added. [knowledge]" + knowledge.getKnowledgeId().toString()
                    + " [target] " + user.getMailAddress());
        }
        MailsDao.get().insert(mailsEntity);
    }
    
    /**
     * コメント追加のWebhookの登録を行う
     * @param comment
     * @param knowledge
     */
    private void sendCommentWebhook(CommentsEntity comment, KnowledgesEntity knowledge) {
        WebhookConfigsDao webhookConfigsDao = WebhookConfigsDao.get();
        List<WebhookConfigsEntity> webhookConfigsEntities = webhookConfigsDao.selectOnHook(WebhookConfigsEntity.HOOK_COMMENTS);

        if (0 == webhookConfigsEntities.size()) {
            return;
        }

        WebhookLogic webhookLogic = WebhookLogic.get();
        Map<String, Object> commentData = webhookLogic.getCommentData(comment, knowledge);

        WebhooksEntity webhooksEntity = new WebhooksEntity();
        String webhookId = idGen("Notify");
        webhooksEntity.setWebhookId(webhookId);
        webhooksEntity.setStatus(WebhookBat.WEBHOOK_STATUS_UNSENT);
        webhooksEntity.setHook(WebhookConfigsEntity.HOOK_COMMENTS);
        webhooksEntity.setContent(JSON.encode(commentData));

        WebhooksDao.get().insert(webhooksEntity);
    }
    

    @Override
    public void convNotification(NotificationsEntity notificationsEntity, LoginedUser loginedUser, TARGET target) {
        CommentInsert update = JSON.decode(notificationsEntity.getContent(), CommentInsert.class);
        MailLocaleTemplatesEntity template = MailLogic.get().load(loginedUser.getLocale(), notificationsEntity.getTitle());
        
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
            contents = contents.replace("{CommentInsertUser}", update.getCommentInsertUser());
            contents = contents.replace("{CommentContents}", update.getCommentContents());
            
            notificationsEntity.setContent(contents);
        }
    }

}
