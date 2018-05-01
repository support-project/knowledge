package org.support.project.ormapping.gen.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.gen.dao.gen.GenEmployeeDao;

/**
 * 従業員
 */
@DI(instance = Instance.Singleton)
public class EmployeeDao extends GenEmployeeDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static EmployeeDao get() {
        return Container.getComp(EmployeeDao.class);
    }

}
