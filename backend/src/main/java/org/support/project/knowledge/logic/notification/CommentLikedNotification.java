package org.support.project.knowledge.logic.notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.support.project.common.config.INT_FLAG;
import org.support.project.common.config.Resources;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.RandomUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.CommentsDao;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.dao.LikeCommentsDao;
import org.support.project.knowledge.dao.NotifyConfigsDao;
import org.support.project.knowledge.dao.NotifyQueuesDao;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.LikeCommentsEntity;
import org.support.project.knowledge.entity.MailLocaleTemplatesEntity;
import org.support.project.knowledge.entity.NotifyConfigsEntity;
import org.support.project.knowledge.entity.NotifyQueuesEntity;
import org.support.project.knowledge.logic.MailLogic;
import org.support.project.knowledge.logic.NotificationLogic;
import org.support.project.knowledge.logic.NotifyLogic;
import org.support.project.knowledge.logic.notification.webhook.CommentLikedWebhookNotification;
import org.support.project.knowledge.vo.notification.LikeInsert;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.dao.NotificationsDao;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.NotificationsEntity;
import org.support.project.web.entity.UsersEntity;

import net.arnx.jsonic.JSON;

@DI(instance = Instance.Prototype)
public class CommentLikedNotification extends AbstractQueueNotification implements DesktopNotification {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(CommentLikedNotification.class);
    /** インスタンス取得 */
    public static CommentLikedNotification get() {
        return Container.getComp(CommentLikedNotification.class);
    }
    
    private LikeCommentsEntity like;
    /**
     * @return the like
     */
    public LikeCommentsEntity getLike() {
        return like;
    }
    /**
     * @param like the like to set
     */
    public void setLike(LikeCommentsEntity like) {
        this.like = like;
    }
    
    /** like id what is sended */
    private List<Long> sended = new ArrayList<>();
    

    @Override
    public void insertNotifyQueue() {
        NotifyQueuesEntity entity = new NotifyQueuesEntity();
        entity.setHash(RandomUtil.randamGen(30));
        entity.setType(TYPE_COMMENT_LIKE);
        entity.setId(like.getNo());
        
        NotifyQueuesDao notifyQueuesDao = NotifyQueuesDao.get();
        NotifyQueuesEntity exist = notifyQueuesDao.selectOnTypeAndId(entity.getType(), entity.getId());
        if (exist == null) {
            notifyQueuesDao.insert(entity);
        }
    }
    @Override
    public void notify(NotifyQueuesEntity notifyQueue) throws Exception {
        LikeCommentsEntity like = LikeCommentsDao.get().selectOnKey(notifyQueue.getId());
        if (null == like) {
            LOG.warn("LikeComment record not found. id: " + notifyQueue.getId());
            return;
        }
        CommentsEntity comment = CommentsDao.get().selectOnKey(like.getCommentNo());
        if (null == comment) {
            LOG.warn("Comment record not found.");
            return;
        }
        KnowledgesEntity knowledge = KnowledgesDao.get().selectOnKey(comment.getKnowledgeId());
        if (null == knowledge) {
            LOG.warn("Knowledge record not found.");
            return;
        }
        
        // Webhook通知
        CommentLikedWebhookNotification webhook = CommentLikedWebhookNotification.get();
        webhook.init(like);
        webhook.saveWebhookData();
        
        if (sended.contains(comment.getCommentNo())) {
            // この通知キューでは、コメント対し何件のイイネがあっても1回送るだけになる
            if (LOG.isDebugEnabled()) {
                LOG.debug("comment [" + comment.getCommentNo() + "] ");
            }
            return;
        } else {
            sended.add(comment.getCommentNo());
        }
        
        // イイネを押したユーザ
        UsersEntity likeUser = UsersDao.get().selectOnKey(like.getInsertUser());
        
        // 宛先のユーザ（コメントを登録したユーザ）
        UsersEntity user = UsersDao.get().selectOnKey(comment.getInsertUser());
        if (user != null) {
            NotifyConfigsDao notifyConfigsDao = NotifyConfigsDao.get();
            NotifyConfigsEntity notifyConfigsEntity = notifyConfigsDao.selectOnKey(user.getUserId());
            // 登録者でかつイイネが登録した場合に通知が欲しい
            if (notifyConfigsEntity != null && INT_FLAG.flagCheck(notifyConfigsEntity.getMyItemLike())) {
                // 通知情報生成
                NotificationsEntity notification = new NotificationsEntity();
                notification.setTitle(MailLogic.NOTIFY_INSERT_LIKE_COMMENT_MYITEM);
                
                LikeInsert info = new LikeInsert();
                info.setKnowledgeId(knowledge.getKnowledgeId());
                info.setKnowledgeTitle(knowledge.getTitle());
                info.setCommentNo(comment.getCommentNo());
                
                info.setUpdateUser(user.getUserName());
                info.setLikeInsertUser(like.getUserName());
                
                notification.setContent(JSON.encode(info));
                notification = NotificationsDao.get().insert(notification);
                
                // 通知とユーザの紐付け
                NotificationLogic.get().insertUserNotification(notification, user);
                
                // メール送信
                Locale locale = user.getLocale();
                MailLocaleTemplatesEntity template = MailLogic.get().load(locale, MailLogic.NOTIFY_INSERT_LIKE_MYITEM);
                LikeInsertNotification.get().sendLikeMail(knowledge, likeUser, user, template);
            }
        }
    }
    
    @Override
    public void convNotification(NotificationsEntity notificationsEntity, LoginedUser loginedUser, TARGET target) {
        LikeInsert info = JSON.decode(notificationsEntity.getContent(), LikeInsert.class);
        MailLocaleTemplatesEntity template = MailLogic.get().load(loginedUser.getLocale(), MailLogic.NOTIFY_INSERT_LIKE_MYITEM);
        
        String title = template.getTitle();
        title = title.replace("{KnowledgeId}", String.valueOf(info.getKnowledgeId()));
        title = title.replace("{KnowledgeTitle}", StringUtils.abbreviate(info.getKnowledgeTitle(), 80));
        notificationsEntity.setTitle(title);
        
        if (target == TARGET.detail) {
            String contents = template.getContent();
            contents = contents.replace("{KnowledgeId}", String.valueOf(info.getKnowledgeId()));
            contents = contents.replace("{KnowledgeTitle}", info.getKnowledgeTitle());
            contents = contents.replace("{User}", info.getUpdateUser());
            contents = contents.replace("{URL}", NotifyLogic.get().makeURL(info.getKnowledgeId()));
            CommentsEntity entity = CommentsDao.get().selectOnKey(info.getCommentNo());
            if (entity != null) {
                contents = contents.replace("{Contents}", entity.getComment());
            } else {
                contents = contents.replace("{Contents}", "");
            }
            contents = contents.replace("{LikeInsertUser}", info.getLikeInsertUser());
            notificationsEntity.setContent(contents);
        }
    }
    @Override
    public MessageResult getMessage(LoginedUser loginuser, Locale locale) {
        NotifyConfigsDao dao = NotifyConfigsDao.get();
        NotifyConfigsEntity entity = dao.selectOnKey(loginuser.getUserId());
        if (!NotifyLogic.get().flagCheck(entity.getNotifyDesktop())) {
            // デスクトップ通知対象外
            return null;
        }
        CommentsEntity comment = CommentsDao.get().selectOnKey(like.getCommentNo());

        if (NotifyLogic.get().flagCheck(entity.getMyItemLike()) && comment.getInsertUser().intValue() == loginuser.getUserId().intValue()) {
            // 自分で投稿したナレッジにイイネが押されたので通知
            MessageResult messageResult = new MessageResult();
            messageResult.setMessage(Resources.getInstance(locale).getResource("knowledge.notify.msg.desktop.myitem.like",
                    String.valueOf(comment.getKnowledgeId())));
            messageResult.setResult(NotifyLogic.get().makeURL(comment.getKnowledgeId())); // Knowledgeへのリンク
            return messageResult;
        }
        return null;
    }

}
