package org.support.project.knowledge.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.knowledge.dao.gen.GenUserBadgesDao;

/**
 * ユーザの称号
 */
@DI(instance = Instance.Singleton)
public class UserBadgesDao extends GenUserBadgesDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static UserBadgesDao get() {
        return Container.getComp(UserBadgesDao.class);
    }



}
