package org.support.project.web.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.dao.gen.GenPasswordResetsDao;

/**
 * パスワードリセット
 */
@DI(instance = Instance.Singleton)
public class PasswordResetsDao extends GenPasswordResetsDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static PasswordResetsDao get() {
        return Container.getComp(PasswordResetsDao.class);
    }

}
