package org.support.project.knowledge.logic.notification;

public interface ParticipateNotification extends Notification {

    /**
     * 参加登録通知
     * @param knowledgeId
     * @param userId
     * @param status
     */
    void notify(Long knowledgeId, Integer userId, Integer status);
}
