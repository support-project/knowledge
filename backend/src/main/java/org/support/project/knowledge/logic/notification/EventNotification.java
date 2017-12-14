package org.support.project.knowledge.logic.notification;

import java.util.Calendar;
import java.util.List;

import org.support.project.knowledge.entity.EventsEntity;
import org.support.project.knowledge.entity.KnowledgeItemValuesEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;

public interface EventNotification extends Notification {
    
    /**
     * イベントの開催通知
     * @param event
     * @param knowledge
     * @param values
     * @param now
     */
    void notify(EventsEntity event, KnowledgesEntity knowledge, List<KnowledgeItemValuesEntity> values,
            Calendar now);
    
}
