package org.support.project.knowledge.dao;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.knowledge.dao.gen.GenTokensDao;
import org.support.project.knowledge.entity.TokensEntity;
import org.support.project.ormapping.common.SQLManager;

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
    /**
     * ユーザのTokenを取得
     * @param userId
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public TokensEntity selectOnUserId(Integer userId) {
        String sql = "SELECT * FROM TOKENS WHERE USER_ID = ? AND DELETE_FLAG = 0";
        return executeQuerySingle(sql, TokensEntity.class, userId);
        
    }



}
