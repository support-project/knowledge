package org.support.project.knowledge.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.knowledge.dao.gen.GenActivitiesDao;

/**
 * アクティビティ
 */
@DI(instance = Instance.Singleton)
public class ActivitiesDao extends GenActivitiesDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static ActivitiesDao get() {
        return Container.getComp(ActivitiesDao.class);
    }



}
