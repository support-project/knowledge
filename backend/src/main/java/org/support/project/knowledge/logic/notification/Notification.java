package org.support.project.knowledge.logic.notification;

import org.support.project.web.bean.AccessUser;
import org.support.project.web.entity.NotificationsEntity;

public interface Notification {
    enum TARGET {
        list, detail
    }
    
    /**
     * 画面での通知情報を、表示する形式に変換する
     * @param notificationsEntity
     * @param loginedUser
     * @param target
     */
    void convNotification(NotificationsEntity notificationsEntity, AccessUser loginedUser, TARGET target);
}
