package org.support.project.knowledge.logic.notification;

import org.support.project.knowledge.entity.NotifyQueuesEntity;

public interface QueueNotification extends Notification {

    /**
     * 通知キューに入っている、通知を処理する
     * @param notifyQueue
     */
    void notify(NotifyQueuesEntity notifyQueue) throws Exception;
    
    

}
