package org.support.project.web.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.dao.gen.GenMailConfigsDao;

/**
 * メール設定
 */
@DI(instance = Instance.Singleton)
public class MailConfigsDao extends GenMailConfigsDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static MailConfigsDao get() {
        return Container.getComp(MailConfigsDao.class);
    }

}
