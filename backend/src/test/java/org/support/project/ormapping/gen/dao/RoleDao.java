package org.support.project.ormapping.gen.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.gen.dao.gen.GenRoleDao;

/**
 * 役割
 */
@DI(instance = Instance.Singleton)
public class RoleDao extends GenRoleDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static RoleDao get() {
        return Container.getComp(RoleDao.class);
    }

}
