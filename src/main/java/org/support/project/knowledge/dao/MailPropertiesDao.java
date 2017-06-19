package org.support.project.knowledge.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.knowledge.dao.gen.GenMailPropertiesDao;

/**
 * メール受信設定
 */
@DI(instance = Instance.Singleton)
public class MailPropertiesDao extends GenMailPropertiesDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static MailPropertiesDao get() {
        return Container.getComp(MailPropertiesDao.class);
    }



}
