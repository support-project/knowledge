package org.support.project.knowledge.dao;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenAccountImagesDao;
import org.support.project.knowledge.entity.AccountImagesEntity;

/**
 * アカウントの画像
 */
@DI(instance = Instance.Singleton)
public class AccountImagesDao extends GenAccountImagesDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static AccountImagesDao get() {
        return Container.getComp(AccountImagesDao.class);
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public AccountImagesEntity selectOnUserId(Integer userId) {
        String sql = "SELECT * FROM ACCOUNT_IMAGES WHERE USER_ID = ?";
        return executeQuerySingle(sql, AccountImagesEntity.class, userId);
    }

}
