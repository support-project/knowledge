package org.support.project.web.dao;

import java.util.Date;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.dao.gen.GenTokensDao;
import org.support.project.web.entity.TokensEntity;

/**
 * 認証トークン
 */
@DI(instance = Instance.Singleton)
public class TokensDao extends GenTokensDao {
    /** PrivateToken発行のUIで作成したToken */
    public static final int TOKEN_TYPE_PRIVATE_TOKEN = 1;
    /** APIを呼び出すようにID/Passで裏で作成したToken */
    public static final int TOKEN_TYPE_API_TOKEN = 2;

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
     * (UIで発行したもの）
     * 
     * @param userId
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public TokensEntity selectOnUserId(Integer userId) {
        String sql = "SELECT * FROM TOKENS WHERE USER_ID = ? AND DELETE_FLAG = 0 AND TOKEN_TYPE = ?";
        return executeQuerySingle(sql, TokensEntity.class, userId, TOKEN_TYPE_PRIVATE_TOKEN);
    }
    
    /**
     * 有効期限が切れてしばらく経過したTokenを削除
     */
    public int removeExpiredToken(int tokenType, long term) {
        Date t = new Date(new Date().getTime() - term);
        String sql = "DELETE FROM TOKENS WHERE expires < ?";
        return executeUpdate(sql, TokensEntity.class, t);
    }

}
