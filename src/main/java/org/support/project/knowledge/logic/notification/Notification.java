package org.support.project.knowledge.logic.notification;

import org.support.project.knowledge.entity.NotifyQueuesEntity;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.entity.NotificationsEntity;

public interface Notification {
    enum TARGET {
        list, detail
    }
    
    /**
     * 通知キューに入っている、通知を処理する
     * @param notifyQueue
     */
    void notify(NotifyQueuesEntity notifyQueue) throws Exception;
    
    
    /**
     * 画面での通知情報を、表示する形式に変換する
     * @param notificationsEntity
     * @param loginedUser
     * @param target
     */
    void convNotification(NotificationsEntity notificationsEntity, LoginedUser loginedUser, TARGET target);
}
