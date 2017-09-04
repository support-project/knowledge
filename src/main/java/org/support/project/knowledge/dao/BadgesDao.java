package org.support.project.knowledge.dao;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.knowledge.dao.gen.GenBadgesDao;

/**
 * 称号
 */
@DI(instance = Instance.Singleton)
public class BadgesDao extends GenBadgesDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static BadgesDao get() {
        return Container.getComp(BadgesDao.class);
    }


    /**
     * ID 
     */
    private int currentId = 0;

    /**
     * Get Next id
     * @return next id
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer getNextId() {
        String sql = "SELECT MAX(NO) FROM BADGES;";
        Integer integer = executeQuerySingle(sql, Integer.class);
        if (integer != null) {
            if (currentId < integer) {
                currentId = integer;
            }
        }
        currentId++;
        return currentId;
    }


}
