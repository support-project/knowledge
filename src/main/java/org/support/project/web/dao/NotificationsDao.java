package org.support.project.web.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.web.dao.gen.GenNotificationsDao;

/**
 * 通知
 */
@DI(instance = Instance.Singleton)
public class NotificationsDao extends GenNotificationsDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static NotificationsDao get() {
        return Container.getComp(NotificationsDao.class);
    }



}
