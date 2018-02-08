package org.support.project.web.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.web.dao.gen.GenSystemAttributesDao;

/**
 * システム付加情報
 */
@DI(instance = Instance.Singleton)
public class SystemAttributesDao extends GenSystemAttributesDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static SystemAttributesDao get() {
        return Container.getComp(SystemAttributesDao.class);
    }

}
