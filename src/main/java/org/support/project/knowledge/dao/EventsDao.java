package org.support.project.knowledge.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.knowledge.dao.gen.GenEventsDao;

/**
 * イベント
 */
@DI(instance = Instance.Singleton)
public class EventsDao extends GenEventsDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static EventsDao get() {
        return Container.getComp(EventsDao.class);
    }



}
