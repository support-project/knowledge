package org.support.project.knowledge.bat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.ClassUtils;
import org.support.project.common.config.INT_FLAG;
import org.support.project.common.config.LocaleConfigLoader;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.MailConfig;
import org.support.project.knowledge.config.NotifyType;
import org.support.project.knowledge.dao.CommentsDao;
import org.support.project.knowledge.dao.ExUsersDao;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.dao.LikesDao;
import org.support.project.knowledge.dao.NotifyConfigsDao;
import org.support.project.knowledge.dao.NotifyQueuesDao;
import org.support.project.knowledge.dao.TargetsDao;
import org.support.project.knowledge.dao.WebhookConfigsDao;
import org.support.project.knowledge.dao.WebhooksDao;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.LikesEntity;
import org.support.project.knowledge.entity.NotifyConfigsEntity;
import org.support.project.knowledge.entity.NotifyQueuesEntity;
import org.support.project.knowledge.entity.WebhookConfigsEntity;
import org.support.project.knowledge.entity.WebhooksEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.NotifyCommentLogic;
import org.support.project.knowledge.logic.NotifyLogic;
import org.support.project.knowledge.logic.WebhookLogic;
import org.support.project.knowledge.vo.GroupUser;
import org.support.project.knowledge.vo.Notify;
import org.support.project.web.dao.MailConfigsDao;
import org.support.project.web.dao.MailsDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.GroupsEntity;
import org.support.project.web.entity.MailConfigsEntity;
import org.support.project.web.entity.MailsEntity;
import org.support.project.web.entity.UsersEntity;

import net.arnx.jsonic.JSON;

/**
 * メッセージ処理を処理する定期的なバッチプログラム
 * @author Koda
 */
public class NotifyMailBat extends AbstractBat {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(NotifyMailBat.class);
    /** config dir for mail */
    private static final String MAIL_CONFIG_DIR = "/org/support/project/knowledge/mail/";
    /** date format */
    private static DateFormat getDayFormat() {
        return new SimpleDateFormat("yyyyMMddHHmmss");
    }
    
    /** knowledge id what is sended */
    private List<Long> sendedCommentKnowledgeIds = new ArrayList<>();
    /** like id what is sended */
    private List<Long> sendedLikeKnowledgeIds = new ArrayList<>();
    
    /**
     * バッチ処理の開始
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        initLogName("NotifyMailBat.log");
        configInit(ClassUtils.getShortClassName(NotifyMailBat.class));
        
        NotifyMailBat bat = new NotifyMailBat();
        bat.dbInit();
        bat.start();
        
        finishInfo();
    }

    /**
     * 通知キューを処理して、メール送信テーブルにメール通知を登録する
     */
    private void start() {
        LOG.info("Notify process started.");
        NotifyQueuesDao notifyQueuesDao = NotifyQueuesDao.get();
        List<NotifyQueuesEntity> notifyQueuesEntities = notifyQueuesDao.selectAll();
        for (NotifyQueuesEntity notifyQueuesEntity : notifyQueuesEntities) {
            if (notifyQueuesEntity.getType() == Notify.TYPE_KNOWLEDGE_INSERT 
                    || notifyQueuesEntity.getType() == Notify.TYPE_KNOWLEDGE_UPDATE) {
                notifyKnowledgeUpdate(notifyQueuesEntity);
            } else if (notifyQueuesEntity.getType() == Notify.TYPE_KNOWLEDGE_COMMENT) {
                notifyCommentInsert(notifyQueuesEntity);
            } else if (notifyQueuesEntity.getType() == Notify.TYPE_KNOWLEDGE_LIKE) {
                notifyLikeInsert(notifyQueuesEntity);
            }
            // 通知のキューから削除
            //notifyQueuesDao.delete(notifyQueuesEntity);
            notifyQueuesDao.physicalDelete(notifyQueuesEntity); // とっておいてもしょうがないので物理削除
        }
        LOG.info("Notify process finished. count: " + notifyQueuesEntities.size());
    }
    
    
    
    /**
     * イイネが押された
     * @param notifyQueuesEntity
     */
    private void notifyLikeInsert(NotifyQueuesEntity notifyQueuesEntity) {
        LikesDao likesDao = LikesDao.get();
        LikesEntity like = likesDao.selectOnKey(notifyQueuesEntity.getId());
        if (null == like) {
            LOG.warn("Like record not found. id: " + notifyQueuesEntity.getId());
            return;
        }

        KnowledgesDao knowledgesDao = KnowledgesDao.get();
        KnowledgesEntity knowledge = knowledgesDao.selectOnKey(like.getKnowledgeId());
        if (null == knowledge) {
            LOG.warn("Knowledge record not found. id: " + notifyQueuesEntity.getId());
            return;
        }
        
        if (sendedLikeKnowledgeIds.contains(knowledge.getKnowledgeId())) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Knowledge [" + knowledge.getKnowledgeId() + "] ");
            }
            return;
        } else {
            sendedLikeKnowledgeIds.add(knowledge.getKnowledgeId());
        }

        UsersDao usersDao = UsersDao.get();
        UsersEntity likeUser = usersDao.selectOnKey(like.getInsertUser());
        
        // 登録者に通知
        UsersEntity user = usersDao.selectOnKey(knowledge.getInsertUser());
        if (user != null) {
            NotifyConfigsDao notifyConfigsDao = NotifyConfigsDao.get();
            NotifyConfigsEntity notifyConfigsEntity = notifyConfigsDao.selectOnKey(user.getUserId());
            if (notifyConfigsEntity != null && INT_FLAG.flagCheck(notifyConfigsEntity.getMyItemLike())) {
                // 登録者でかつイイネが登録した場合に通知が欲しい
                Locale locale = user.getLocale();
                MailConfig config = LocaleConfigLoader.load(MAIL_CONFIG_DIR, "notify_insert_like_myitem", locale, MailConfig.class);
                sendLikeMail(like, knowledge, likeUser, user, config);
            }
        }
    }
    
    /**
     * イイネが押されたメールを送る
     * @param like
     * @param knowledge
     * @param likeUser
     * @param user
     * @param config
     */
    private void sendLikeMail(LikesEntity like, KnowledgesEntity knowledge, UsersEntity likeUser, UsersEntity user, MailConfig config) {
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
        String mailId = idGenu("Notify");
        mailsEntity.setMailId(mailId);
        mailsEntity.setStatus(MailSendBat.MAIL_STATUS_UNSENT);
        mailsEntity.setToAddress(user.getMailAddress());
        mailsEntity.setToName(user.getUserName());
        
        String title = config.getTitle();
        title = title.replace("{KnowledgeId}", knowledge.getKnowledgeId().toString());
        title = title.replace("{KnowledgeTitle}", StringUtils.abbreviate(knowledge.getTitle(), 80));
        mailsEntity.setTitle(title);
        String contents = config.getContents();
        contents = contents.replace("{KnowledgeId}", knowledge.getKnowledgeId().toString());
        contents = contents.replace("{KnowledgeTitle}", knowledge.getTitle());
        contents = contents.replace("{Contents}", getContent(knowledge.getContent()));
        if (likeUser != null) {
            contents = contents.replace("{LikeInsertUser}", likeUser.getUserName());
        } else {
            contents = contents.replace("{LikeInsertUser}", "");
        }
        contents = contents.replace("{URL}", NotifyLogic.get().makeURL(knowledge.getKnowledgeId()));
        
        mailsEntity.setContent(contents);
        if (LOG.isDebugEnabled()) {
            LOG.debug("News email has been registered. [type] Like added. [knowledge]" + knowledge.getKnowledgeId().toString()
                    + " [target] " + user.getMailAddress());
        }
        MailsDao.get().insert(mailsEntity);
    }

    /**
     * ナレッジにコメントが登録された場合の通知
     * @param notifyQueuesEntity
     */
    private void notifyCommentInsert(NotifyQueuesEntity notifyQueuesEntity) {
        CommentsDao commentsDao = CommentsDao.get();
        CommentsEntity comment = commentsDao.selectOnKey(notifyQueuesEntity.getId());
        if (null == comment) {
            LOG.warn("Comment record not found. id: " + notifyQueuesEntity.getId());
            return;
        }

        KnowledgesDao knowledgesDao = KnowledgesDao.get();
        KnowledgesEntity knowledge = knowledgesDao.selectOnKey(comment.getKnowledgeId());
        if (null == knowledge) {
            LOG.warn("Knowledge record not found. id: " + notifyQueuesEntity.getId());
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
            Locale locale = user.getLocale();
            MailConfig config = LocaleConfigLoader.load(MAIL_CONFIG_DIR, "notify_insert_comment_myitem", locale, MailConfig.class);
            sendCommentMail(comment, knowledge, commentUser, user, config);
        }
        // 宛先のナレッジにコメント追加で通知が欲しいユーザに通知
        List<UsersEntity> users = NotifyCommentLogic.get().getTargetUsersOnComment(NotifyType.Mail, comment, knowledge);
        for (UsersEntity target : users) {
            // 宛先にメール送信
            Locale locale = target.getLocale();
            MailConfig config = LocaleConfigLoader.load(MAIL_CONFIG_DIR, "notify_insert_comment", locale, MailConfig.class);
            sendCommentMail(comment, knowledge, commentUser, target, config);
        }
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
        String webhookId = idGenu("Notify");
        webhooksEntity.setWebhookId(webhookId);
        webhooksEntity.setStatus(WebhookBat.WEBHOOK_STATUS_UNSENT);
        webhooksEntity.setHook(WebhookConfigsEntity.HOOK_COMMENTS);
        webhooksEntity.setContent(JSON.encode(commentData));

        WebhooksDao.get().insert(webhooksEntity);
    }
    
    /**
     * コメントが追加されたメールを通知する
     * @param comment コメントの情報
     * @param knowledge ナレッジの情報
     * @param commentUser コメントを登録したユーザの情報
     * @param user メールの送信先
     */
    private void sendCommentMail(CommentsEntity comment, KnowledgesEntity knowledge, UsersEntity commentUser, UsersEntity user, MailConfig config) {
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
        String mailId = idGenu("Notify");
        mailsEntity.setMailId(mailId);
        mailsEntity.setStatus(MailSendBat.MAIL_STATUS_UNSENT);
        mailsEntity.setToAddress(user.getMailAddress());
        mailsEntity.setToName(user.getUserName());
        
        String title = config.getTitle();
        title = title.replace("{KnowledgeId}", knowledge.getKnowledgeId().toString());
        title = title.replace("{KnowledgeTitle}", StringUtils.abbreviate(knowledge.getTitle(), 80));
        mailsEntity.setTitle(title);
        String contents = config.getContents();
        contents = contents.replace("{KnowledgeId}", knowledge.getKnowledgeId().toString());
        contents = contents.replace("{KnowledgeTitle}", knowledge.getTitle());
        contents = contents.replace("{Contents}", getContent(knowledge.getContent()));
        if (commentUser != null) {
            contents = contents.replace("{CommentInsertUser}", commentUser.getUserName());
        } else {
            contents = contents.replace("{CommentInsertUser}", "");
        }
        contents = contents.replace("{CommentContents}", getContent(comment.getComment()));
        contents = contents.replace("{URL}", NotifyLogic.get().makeURL(knowledge.getKnowledgeId()));

        mailsEntity.setContent(contents);
        if (LOG.isDebugEnabled()) {
            LOG.debug("News email has been registered. [type] comment added. [knowledge]" + knowledge.getKnowledgeId().toString()
                    + " [target] " + user.getMailAddress());
        }
        MailsDao.get().insert(mailsEntity);
    }

    /**
     * ナレッジを登録・更新した際にメール通知を送信する
     * @param notifyQueuesEntity
     */
    private void notifyKnowledgeUpdate(NotifyQueuesEntity notifyQueuesEntity) {
        // ナレッジが登録/更新された
        KnowledgesDao knowledgesDao = KnowledgesDao.get();
        KnowledgesEntity knowledge = knowledgesDao.selectOnKeyWithUserName(notifyQueuesEntity.getId());
        if (null == knowledge) {
            LOG.warn("Knowledge record not found. id: " + notifyQueuesEntity.getId());
            return;
        }

        sendKnowledgeWebhook(knowledge, notifyQueuesEntity.getType());

        if (knowledge.getPublicFlag() == KnowledgeLogic.PUBLIC_FLAG_PUBLIC) {
            notifyPublicKnowledgeUpdate(notifyQueuesEntity, knowledge);
        } else if (knowledge.getPublicFlag() == KnowledgeLogic.PUBLIC_FLAG_PROTECT) {
            notifyProtectKnowledgeUpdate(notifyQueuesEntity, knowledge);
        }
        // 「非公開」のナレッジは、通知対象外
    }
    
    /**
     * 「保護」のナレッジを登録・更新した際にメール通知を送信する
     * @param notifyQueuesEntity
     * @param knowledge
     */
    private void notifyProtectKnowledgeUpdate(NotifyQueuesEntity notifyQueuesEntity, KnowledgesEntity knowledge) {
        List<UsersEntity> users = new ArrayList<>();
        // 宛先の一覧取得
        TargetsDao targetsDao = TargetsDao.get();
        List<UsersEntity> targetUsers = targetsDao.selectUsersOnKnowledgeId(knowledge.getKnowledgeId());
        users.addAll(targetUsers);
        
        //グループの一覧
        List<GroupsEntity> targetGroups = targetsDao.selectGroupsOnKnowledgeId(knowledge.getKnowledgeId());
        for (GroupsEntity groupsEntity : targetGroups) {
            List<GroupUser> groupUsers = ExUsersDao.get().selectGroupUser(groupsEntity.getGroupId(), 0, Integer.MAX_VALUE);
            for (GroupUser groupUser : groupUsers) {
                if (!contains(users, groupUser)) {
                    users.add(groupUser);
                }
            }
        }

        Iterator<UsersEntity> iterator = users.iterator();
        while (iterator.hasNext()) {
            UsersEntity usersEntity = (UsersEntity) iterator.next();
            // 自分宛てのナレッジ登録／更新で通知するかどうかの判定
            NotifyConfigsDao notifyConfigsDao = NotifyConfigsDao.get();
            NotifyConfigsEntity notifyConfigsEntity = notifyConfigsDao.selectOnKey(usersEntity.getUserId());
            if (notifyConfigsEntity == null) {
                iterator.remove();
            } else if (!INT_FLAG.flagCheck(notifyConfigsEntity.getToItemSave())) {
                iterator.remove();
            }
        }
        notifyKnowledgeUpdateToUsers(notifyQueuesEntity, knowledge, users);
    }

    /**
     * 既に指定のユーザが追加されているのか確認
     * @param users
     * @param groupUser
     * @return
     */
    private boolean contains(List<UsersEntity> users, UsersEntity groupUser) {
        for (UsersEntity usersEntity : users) {
            if (usersEntity.equalsOnKey(groupUser)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 「公開」のナレッジを登録・更新した際にメール通知を送信する
     * @param notifyQueuesEntity
     * @param knowledge
     */
    private void notifyPublicKnowledgeUpdate(NotifyQueuesEntity notifyQueuesEntity, KnowledgesEntity knowledge) {
        //ナレッジ登録ONでかつ、公開区分「公開」を除外しないユーザに通知
        List<UsersEntity> users = ExUsersDao.get().selectNotifyPublicUsers();
        notifyKnowledgeUpdateToUsers(notifyQueuesEntity, knowledge, users);
    }
    
    /**
     * 指定のユーザ一覧に、ナレッジを登録・更新した際にメール通知を送信する
     * @param notifyQueuesEntity
     * @param knowledge
     * @param users
     */
    private void notifyKnowledgeUpdateToUsers(NotifyQueuesEntity notifyQueuesEntity, KnowledgesEntity knowledge, List<UsersEntity> users) {
        MailConfigsDao mailConfigsDao = MailConfigsDao.get();
        MailConfigsEntity mailConfigsEntity = mailConfigsDao.selectOnKey(AppConfig.get().getSystemName());
        if (mailConfigsEntity == null) {
            // メールの設定が登録されていなければ、送信処理は終了
            LOG.info("mail config is not exists.");
            return;
        }

        for (UsersEntity usersEntity : users) {
            if (!StringUtils.isEmailAddress(usersEntity.getMailAddress())) {
                // 送信先のメールアドレスが不正なのでこのユーザにはメール送信しない
                LOG.warn("mail targget [" + usersEntity.getMailAddress() + "] is wrong.");
                continue;
            }
            if (LOG.isTraceEnabled()) {
                LOG.trace("[Notify] " + usersEntity.getMailAddress());
            }
            Locale locale = usersEntity.getLocale();
            MailConfig config;
            if (notifyQueuesEntity.getType() == Notify.TYPE_KNOWLEDGE_INSERT) {
                config = LocaleConfigLoader.load(MAIL_CONFIG_DIR, "notify_insert_knowledge", locale, MailConfig.class);
            } else {
                config = LocaleConfigLoader.load(MAIL_CONFIG_DIR, "notify_update_knowledge", locale, MailConfig.class);
            }
            
            insertNotifyKnowledgeUpdateMailQue(knowledge, usersEntity, config);
        }
    }

    /**
     * 記事の追加・更新のWebhookの登録を行う
     * @param comment
     * @param knowledge
     */
    private void sendKnowledgeWebhook(KnowledgesEntity knowledge, int type) {
        WebhookConfigsDao webhookConfigsDao = WebhookConfigsDao.get();
        List<WebhookConfigsEntity> webhookConfigsEntities = webhookConfigsDao.selectOnHook(WebhookConfigsEntity.HOOK_KNOWLEDGES);

        if (0 == webhookConfigsEntities.size()) {
            return;
        }

        WebhookLogic webhookLogic = WebhookLogic.get();
        Map<String, Object> knowledgeData = webhookLogic.getKnowledgeData(knowledge, type);

        WebhooksEntity webhooksEntity = new WebhooksEntity();
        String webhookId = idGenu("Notify");
        webhooksEntity.setWebhookId(webhookId);
        webhooksEntity.setStatus(WebhookBat.WEBHOOK_STATUS_UNSENT);
        webhooksEntity.setHook(WebhookConfigsEntity.HOOK_KNOWLEDGES);
        webhooksEntity.setContent(JSON.encode(knowledgeData));

        WebhooksDao.get().insert(webhooksEntity);
    }
    
    /**
     * メール送信のキュー情報を登録する
     * @param knowledge
     * @param usersEntity
     * @param config
     */
    private void insertNotifyKnowledgeUpdateMailQue(KnowledgesEntity knowledge, UsersEntity usersEntity, MailConfig config) {
        MailConfigsDao mailConfigsDao = MailConfigsDao.get();
        MailConfigsEntity mailConfigsEntity = mailConfigsDao.selectOnKey(AppConfig.get().getSystemName());
        if (mailConfigsEntity == null) {
            // メールの設定が登録されていなければ、送信処理は終了
            LOG.info("mail config is not exists.");
            return;
        }
        if (!StringUtils.isEmailAddress(usersEntity.getMailAddress())) {
            // 送信先のメールアドレスが不正なので、送信処理は終了
            LOG.warn("mail targget [" + usersEntity.getMailAddress() + "] is wrong.");
            return;
        }

        MailsEntity mailsEntity = new MailsEntity();
        String mailId = idGenu("Notify");
        mailsEntity.setMailId(mailId);
        mailsEntity.setStatus(MailSendBat.MAIL_STATUS_UNSENT);
        mailsEntity.setToAddress(usersEntity.getMailAddress());
        mailsEntity.setToName(usersEntity.getUserName());
        String title = config.getTitle();
        title = title.replace("{KnowledgeId}", knowledge.getKnowledgeId().toString());
        title = title.replace("{KnowledgeTitle}", StringUtils.abbreviate(knowledge.getTitle(), 80));
        mailsEntity.setTitle(title);
        String contents = config.getContents();
        contents = contents.replace("{KnowledgeId}", knowledge.getKnowledgeId().toString());
        contents = contents.replace("{KnowledgeTitle}", knowledge.getTitle());
        contents = contents.replace("{User}", knowledge.getUpdateUserName());
        
        // コンテンツがHTMLであった場合、テキストを取得する
        contents = contents.replace("{Contents}", getContent(knowledge.getContent()));
        contents = contents.replace("{URL}", NotifyLogic.get().makeURL(knowledge.getKnowledgeId()));

        mailsEntity.setContent(contents);
        
        if (LOG.isDebugEnabled()) {
            LOG.debug("News email has been registered. [type] knowledge update. [knowledge]" + knowledge.getKnowledgeId().toString()
                    + " [target] " + usersEntity.getMailAddress());
        }
        MailsDao.get().insert(mailsEntity);
    }
    
    /**
     * メールにセットする本文の取得
     * @param content
     * @return
     */
    private String getContent(String content) {
        return null;
    }

    /**
     * メール送信のIDを生成
     * @param string
     * @return
     */
    private String idGenu(String label) {
        StringBuilder builder = new StringBuilder();
        builder.append(label);
        builder.append("-");
        builder.append(getDayFormat().format(new Date()));
        builder.append("-");
        builder.append(UUID.randomUUID().toString());
        return builder.toString();
    }


}
