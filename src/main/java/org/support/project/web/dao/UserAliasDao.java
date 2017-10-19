package org.support.project.web.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.dao.gen.GenUserAliasDao;
import org.support.project.web.entity.UserAliasEntity;

/**
 * ユーザのエイリアス
 */
@DI(instance = Instance.Singleton)
public class UserAliasDao extends GenUserAliasDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static UserAliasDao get() {
        return Container.getComp(UserAliasDao.class);
    }
    
    public UserAliasEntity selectOnAliasKey(String authKey, String aliasKey) {
        String sql = "SELECT * FROM USER_ALIAS WHERE AUTH_KEY = ? AND ALIAS_KEY = ?";
        return executeQuerySingle(sql, UserAliasEntity.class, authKey, aliasKey.toLowerCase());
    }



}
