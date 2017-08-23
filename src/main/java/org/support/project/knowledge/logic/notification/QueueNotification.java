package org.support.project.knowledge.logic.notification;

import org.support.project.knowledge.entity.NotifyQueuesEntity;

public interface QueueNotification extends Notification {
    /** notify type: insert knowledge */
    int TYPE_KNOWLEDGE_INSERT = 1;
    /** notify type: update knowledge */
    int TYPE_KNOWLEDGE_UPDATE = 2;
    /** notify type: add comment */
    int TYPE_KNOWLEDGE_COMMENT = 11;
    /** notify type: add like */
    int TYPE_KNOWLEDGE_LIKE = 21;
    
    /**
     * メール通知のキューを取得
     * 
     * @return
     */
    NotifyQueuesEntity getQueue();
    /**
     * 通知キューに入っている、通知を処理する
     * @param notifyQueue
     */
    void notify(NotifyQueuesEntity notifyQueue) throws Exception;

}
