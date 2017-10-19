package org.support.project.ormapping.gen.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.gen.dao.gen.GenUserRoleRelationshipDao;

/**
 * 組織以外の役割の紐付
 */
@DI(instance = Instance.Singleton)
public class UserRoleRelationshipDao extends GenUserRoleRelationshipDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static UserRoleRelationshipDao get() {
        return Container.getComp(UserRoleRelationshipDao.class);
    }

}
