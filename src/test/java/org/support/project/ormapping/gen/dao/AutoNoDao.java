package org.support.project.ormapping.gen.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.gen.dao.gen.GenAutoNoDao;

/**
 * 採番のテストテーブル
 */
@DI(instance = Instance.Singleton)
public class AutoNoDao extends GenAutoNoDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static AutoNoDao get() {
        return Container.getComp(AutoNoDao.class);
    }

}
