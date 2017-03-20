package org.support.project.knowledge.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.knowledge.dao.gen.GenSurveysDao;

/**
 * アンケート
 */
@DI(instance = Instance.Singleton)
public class SurveysDao extends GenSurveysDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static SurveysDao get() {
        return Container.getComp(SurveysDao.class);
    }



}
