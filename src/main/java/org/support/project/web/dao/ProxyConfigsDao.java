package org.support.project.web.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.web.dao.gen.GenProxyConfigsDao;

/**
 * プロキシ設定
 */
@DI(instance = Instance.Singleton)
public class ProxyConfigsDao extends GenProxyConfigsDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static ProxyConfigsDao get() {
        return Container.getComp(ProxyConfigsDao.class);
    }

}
