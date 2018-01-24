package org.support.project.web.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.dao.gen.GenFunctionsDao;

/**
 * 機能
 */
@DI(instance = Instance.Singleton)
public class FunctionsDao extends GenFunctionsDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static FunctionsDao get() {
        return Container.getComp(FunctionsDao.class);
    }

}
