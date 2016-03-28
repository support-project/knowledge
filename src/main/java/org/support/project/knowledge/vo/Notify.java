package org.support.project.knowledge.vo;

import java.util.List;
import java.util.Locale;

import org.support.project.common.config.Resources;
import org.support.project.common.util.RandomUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.knowledge.config.NotifyType;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.dao.NotifyConfigsDao;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.LikesEntity;
import org.support.project.knowledge.entity.NotifyConfigsEntity;
import org.support.project.knowledge.entity.NotifyQueuesEntity;
import org.support.project.knowledge.logic.NotifyCommentLogic;
import org.support.project.knowledge.logic.NotifyLogic;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.entity.UsersEntity;

/**
 * Class for notify
 * @author Koda
 */
public class Notify {
    /** notify type: insert knowledge */
    public static final int TYPE_KNOWLEDGE_INSERT = 1;
    /** notify type: update knowledge */
    public static final int TYPE_KNOWLEDGE_UPDATE = 2;
    /** notify type: add comment */
    public static final int TYPE_KNOWLEDGE_COMMENT = 11;
    /** notify type: add like */
    public static final int TYPE_KNOWLEDGE_LIKE = 21;

    /** notify type */
    private int type;

    /** Updated KnowledgesEntity */
    private KnowledgesEntity knowledge;
    /** Added CommentsEntity */
    private CommentsEntity comment;
    /** Added LikesEntity */
    private LikesEntity like;
    
    /**
     * Knowledge inserted.
     * @param entity
     */
    public void inserted(KnowledgesEntity entity) {
        type = TYPE_KNOWLEDGE_INSERT;
        knowledge = entity;
    }
    /**
     * Knowledge updated.
     * @param entity
     */
    public void updated(KnowledgesEntity entity) {
        type = TYPE_KNOWLEDGE_UPDATE;
        knowledge = entity;
    }
    /**
     * Comment added.
     * @param commentsEntity
     */
    public void commented(CommentsEntity commentsEntity) {
        type = TYPE_KNOWLEDGE_COMMENT;
        comment = commentsEntity;
    }
    /**
     * Like added.
     * @param entity
     */
    public void liked(LikesEntity entity) {
        type = TYPE_KNOWLEDGE_LIKE;
        like = entity;
    }
    /**
     * メール通知のキューを取得
     * 
     * @return
     */
    public NotifyQueuesEntity getQueue() {
        NotifyQueuesEntity entity = new NotifyQueuesEntity();
        entity.setHash(RandomUtil.randamGen(30));
        entity.setType(type);

        if (type == TYPE_KNOWLEDGE_INSERT || type == TYPE_KNOWLEDGE_UPDATE) {
            entity.setId(knowledge.getKnowledgeId());
        } else if (type == TYPE_KNOWLEDGE_COMMENT) {
            entity.setId(comment.getCommentNo());
        } else if (type == TYPE_KNOWLEDGE_LIKE) {
            entity.setId(like.getNo());
        }
        return entity;
    }

    /**
     * デスクトップ通知するメッセージを取得
     * 
     * @param loginuser
     * @param locale
     * @return
     */
    public MessageResult getMessage(LoginedUser loginuser, Locale locale) {
        if (type == TYPE_KNOWLEDGE_INSERT) {
            return NotifyLogic.get().getInsertKnowledgeMessage(loginuser, locale, knowledge);
        } else if (type == TYPE_KNOWLEDGE_UPDATE) {
            return NotifyLogic.get().getUpdateKnowledgeMessage(loginuser, locale, knowledge);
        } else if (type == TYPE_KNOWLEDGE_COMMENT) {
            return this.getSaveCommentMessage(loginuser, locale, comment);
        } else if (type == TYPE_KNOWLEDGE_LIKE) {
            return NotifyLogic.get().getSaveLikeMessage(loginuser, locale, like);
        }
        return null;
    }
    /**
     * コメント更新時のデスクトップメッセージを取得
     * @param loginuser
     * @param locale
     * @param comment
     * @return
     */
    private MessageResult getSaveCommentMessage(LoginedUser loginuser, Locale locale, CommentsEntity comment) {
        NotifyConfigsDao dao = NotifyConfigsDao.get();
        NotifyConfigsEntity entity = dao.selectOnKey(loginuser.getUserId()); // ログインユーザのデスクトップ通知設定
        if (!NotifyLogic.get().flagCheck(entity.getNotifyDesktop())) {
            // デスクトップ通知対象外
            return null;
        }
        
        KnowledgesDao knowledgesDao = KnowledgesDao.get();
        KnowledgesEntity knowledge = knowledgesDao.selectOnKey(comment.getKnowledgeId());
        // 登録者に通知
        UsersEntity user = NotifyCommentLogic.get().getInsertUserOnComment(NotifyType.Desktop, comment, knowledge);
        if (user != null) {
            if (user.getUserId().intValue() == loginuser.getUserId().intValue()) {
                // ログインユーザはナレッジ登録者で、自分の登録したナレッジにコメントがついたら通知を希望
                MessageResult messageResult = new MessageResult();
                messageResult.setMessage(Resources.getInstance(locale).getResource("knowledge.notify.msg.desktop.myitem.comment",
                        StringUtils.abbreviate(knowledge.getTitle(), 80)));
                messageResult.setResult(NotifyLogic.get().makeURL(knowledge.getKnowledgeId())); // Knowledgeへのリンク
                return messageResult;
            }
        }
        // 宛先のナレッジにコメント追加で通知が欲しいユーザに通知
        List<UsersEntity> users = NotifyCommentLogic.get().getTargetUsersOnComment(NotifyType.Desktop, comment, knowledge);
        for (UsersEntity target : users) {
            if (target.getUserId().intValue() == loginuser.getUserId().intValue()) {
                // 自分宛てのナレッジにコメントがついたので通知
                MessageResult messageResult = new MessageResult();
                messageResult.setMessage(
                        Resources.getInstance(locale).getResource("knowledge.notify.msg.desktop.to.comment",
                                StringUtils.abbreviate(knowledge.getTitle(), 80)));
                messageResult.setResult(NotifyLogic.get().makeURL(knowledge.getKnowledgeId())); // Knowledgeへのリンク
                return messageResult;
            }
        }
        return null;
    }
}
