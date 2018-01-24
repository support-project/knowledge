package org.support.project.web.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.dao.gen.GenLoginHistoriesDao;

/**
 * ログイン履歴
 */
@DI(instance = Instance.Singleton)
public class LoginHistoriesDao extends GenLoginHistoriesDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static LoginHistoriesDao get() {
        return Container.getComp(LoginHistoriesDao.class);
    }

}
