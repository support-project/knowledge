package org.support.project.knowledge.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.knowledge.dao.gen.GenNotificationStatusDao;

/**
 * いいねの通知状態
 */
@DI(instance = Instance.Singleton)
public class NotificationStatusDao extends GenNotificationStatusDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static NotificationStatusDao get() {
        return Container.getComp(NotificationStatusDao.class);
    }



}
