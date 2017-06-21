package org.support.project.knowledge.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.knowledge.dao.gen.GenTokensDao;

/**
 * 認証トークン
 */
@DI(instance = Instance.Singleton)
public class TokensDao extends GenTokensDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static TokensDao get() {
        return Container.getComp(TokensDao.class);
    }



}
