package org.support.project.knowledge.dao;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.knowledge.dao.gen.GenActivitiesDao;
import org.support.project.knowledge.entity.ActivitiesEntity;

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
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public ActivitiesEntity select(int userId, int type, String target) {
        String sql = "SELECT * FROM ACTIVITIES WHERE USER_ID = ? AND KIND = ? AND TARGET = ?";
        return executeQuerySingle(sql, ActivitiesEntity.class, userId, type, target);
    }



}
