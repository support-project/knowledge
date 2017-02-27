package org.support.project.knowledge.entity;

import org.support.project.knowledge.entity.gen.GenEventsEntity;

import java.util.List;
import java.util.Map;

import org.support.project.common.bean.ValidateError;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import java.sql.Timestamp;


/**
 * イベント
 */
@DI(instance = Instance.Prototype)
public class EventsEntity extends GenEventsEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static EventsEntity get() {
        return Container.getComp(EventsEntity.class);
    }

    /**
     * Constructor.
     */
    public EventsEntity() {
        super();
    }

    /**
     * Constructor
     * @param knowledgeId ナレッジID
     */

    public EventsEntity(Long knowledgeId) {
        super( knowledgeId);
    }

}
