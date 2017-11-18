package org.support.project.knowledge.logic.notification;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.support.project.common.test.Order;
import org.support.project.common.test.OrderedRunner;
import org.support.project.knowledge.dao.LikeCommentsDao;
import org.support.project.knowledge.dao.NotifyQueuesDao;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.LikeCommentsEntity;
import org.support.project.knowledge.entity.NotifyQueuesEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.LikeLogic;
import org.support.project.knowledge.logic.NotificationLogic;
import org.support.project.knowledge.logic.notification.Notification.TARGET;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.entity.NotificationsEntity;

@RunWith(OrderedRunner.class)
public class CommentLikedNotificationTest extends NotificationTestCommon {
    @Test
    @Order(order = 1)
    public void testNotification() throws Exception {
        // Knowledgeを登録し、そこにコメント登録し、それにいいねを押した
        KnowledgesEntity knowledge = super.insertKnowledge("テスト", loginedUser2);
        CommentsEntity comment = KnowledgeLogic.get().saveComment(knowledge.getKnowledgeId(), "コメント", new ArrayList<>(), loginedUser);
        LikeLogic.get().addLikeComment(comment.getCommentNo(), loginedUser2, loginedUser.getLocale());

        List<NotifyQueuesEntity> list = NotifyQueuesDao.get().selectAll();
        LOG.info(list);

        Assert.assertEquals(3, list.size()); // Knowledge登録と、コメントの登録、イイネの通知が登録されている
        Assert.assertEquals(knowledge.getKnowledgeId(), list.get(0).getId());
        Assert.assertEquals(QueueNotification.TYPE_KNOWLEDGE_INSERT, list.get(0).getType().intValue());
        Assert.assertEquals(QueueNotification.TYPE_KNOWLEDGE_COMMENT, list.get(1).getType().intValue());
        Assert.assertEquals(QueueNotification.TYPE_COMMENT_LIKE, list.get(2).getType().intValue());

        CommentLikedNotification.get().notify(list.get(2)); // Likeの通知キューを処理

        List<NotificationsEntity> notifications = NotificationLogic.get().getNotification(loginedUser.getUserId(), 0, false);
        Assert.assertEquals(1, notifications.size()); // 登録した通知が届いている
        LOG.info(notifications.get(0));

        // 変換処理がエラーにならないこと
        CommentLikedNotification.get().convNotification(notifications.get(0), loginedUser, TARGET.detail);

        // Desktop notification
        LikeCommentsEntity like = LikeCommentsDao.get().selectOnCommentNo(comment.getCommentNo(), 0, 100).get(0);
        CommentLikedNotification notify = CommentLikedNotification.get();
        notify.setLike(like);
        notify.setType(QueueNotification.TYPE_COMMENT_LIKE);

        MessageResult result = notify.getMessage(loginedUser, loginedUser.getLocale());
        LOG.info(result.getMessage());
    }
}
