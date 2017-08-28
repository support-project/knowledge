package org.support.project.knowledge.logic;

import java.util.List;

import org.support.project.common.config.INT_FLAG;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.SystemConfig;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.LikeCommentsEntity;
import org.support.project.knowledge.entity.LikesEntity;
import org.support.project.knowledge.logic.notification.CommentInsertNotification;
import org.support.project.knowledge.logic.notification.CommentLikedNotification;
import org.support.project.knowledge.logic.notification.DesktopNotification;
import org.support.project.knowledge.logic.notification.KnowledgeUpdateNotification;
import org.support.project.knowledge.logic.notification.LikeInsertNotification;
import org.support.project.knowledge.logic.notification.QueueNotification;
import org.support.project.knowledge.websocket.NotifyAction;
import org.support.project.web.dao.SystemConfigsDao;
import org.support.project.web.entity.SystemConfigsEntity;
import org.support.project.web.entity.UsersEntity;
/**
 * 通知を処理するロジック
 * @author Koda
 *
 */
@DI(instance = Instance.Singleton)
public class NotifyLogic {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(NotifyLogic.class);
    /**
     * インスタンスを取得
     * @return
     */
    public static NotifyLogic get() {
        return Container.getComp(NotifyLogic.class);
    }
    /**
     * 指定のナレッジにアクセスするURLを作成
     * 
     * @param knowledge
     * @return
     */
    public String makeURL(Long knowledgeId) {
        SystemConfigsDao dao = SystemConfigsDao.get();
        SystemConfigsEntity config = dao.selectOnKey(SystemConfig.SYSTEM_URL, AppConfig.get().getSystemName());
        if (config == null) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        builder.append(config.getConfigValue());
        if (!config.getConfigValue().endsWith("/")) {
            builder.append("/");
        }
        builder.append("open.knowledge/view/");
        builder.append(knowledgeId);
        return builder.toString();
    }
    /**
     * デスクトップ通知を処理（別スレッドで実行）
     * @param notify
     */
    private void notifyDeskTop(DesktopNotification notify) {
        // Desktop通知は、多数のユーザを処理するので、別スレッドで処理する
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                LOG.debug("start notify");
                NotifyAction notifyAction = Container.getComp(NotifyAction.class);
                notifyAction.notifyObservers(notify);
                LOG.debug("end notify");
            }
        });
        t.setDaemon(true);
        t.start();
    }
    /**
     * Integer型のフラグをチェック
     * 
     * @param check
     * @return
     */
    public boolean flagCheck(Integer check) {
        if (check == null) {
            return false;
        }
        if (check.intValue() == INT_FLAG.ON.getValue()) {
            return true;
        }
        return false;
    }
    /**
     * 既に指定のユーザが追加されているのか確認
     * @param users
     * @param groupUser
     * @return
     */
    protected boolean contains(List<UsersEntity> users, UsersEntity groupUser) {
        for (UsersEntity usersEntity : users) {
            if (usersEntity.equalsOnKey(groupUser)) {
                return true;
            }
        }
        return false;
    }
    /**
     * ナレッジが登録された際の通知
     * 
     * @param knowledgesEntity
     */
    public void notifyOnKnowledgeInsert(KnowledgesEntity knowledgesEntity) {
        KnowledgeUpdateNotification notify = KnowledgeUpdateNotification.get();
        notify.setKnowledge(knowledgesEntity);
        notify.setType(QueueNotification.TYPE_KNOWLEDGE_INSERT);
        notify.insertNotifyQueue();
        notifyDeskTop(notify);
    }

    /**
     * ナレッジが更新された際の通知
     * 
     * @param knowledgesEntity
     */
    public void notifyOnKnowledgeUpdate(KnowledgesEntity knowledgesEntity) {
        KnowledgeUpdateNotification notify = KnowledgeUpdateNotification.get();
        notify.setKnowledge(knowledgesEntity);
        notify.setType(QueueNotification.TYPE_KNOWLEDGE_UPDATE);
        notify.insertNotifyQueue();
        notifyDeskTop(notify);
    }

    /**
     * ナレッジに「イイネ」が登録された際の通知
     * 
     * @param knowledgeId
     */
    public void notifyOnKnowledgeLiked(Long knowledgeId, LikesEntity likesEntity) {
        LikeInsertNotification notify = LikeInsertNotification.get();
        notify.setLike(likesEntity);
        notify.setType(QueueNotification.TYPE_KNOWLEDGE_LIKE);
        notify.insertNotifyQueue();
        notifyDeskTop(notify);
    }

    /**
     * ナレッジにコメントが追加された
     * 
     * @param knowledgeId
     */
    public void notifyOnKnowledgeComment(Long knowledgeId, CommentsEntity commentsEntity) {
        CommentInsertNotification notify = CommentInsertNotification.get();
        notify.setComment(commentsEntity);
        notify.setType(QueueNotification.TYPE_KNOWLEDGE_COMMENT);
        notify.insertNotifyQueue();
        notifyDeskTop(notify);
    }
    
    public void notifyOnCommentLiked(LikeCommentsEntity like) {
        CommentLikedNotification notify = CommentLikedNotification.get();
        notify.setLike(like);
        notify.setType(QueueNotification.TYPE_COMMENT_LIKE);
        notify.insertNotifyQueue();
        notifyDeskTop(notify);
    }


}
