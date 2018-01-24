package org.support.project.web.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.dao.gen.GenHashConfigsDao;

/**
 * ハッシュ生成の設定
 */
@DI(instance = Instance.Singleton)
public class HashConfigsDao extends GenHashConfigsDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static HashConfigsDao get() {
        return Container.getComp(HashConfigsDao.class);
    }

}
