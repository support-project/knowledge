package org.support.project.knowledge.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.knowledge.dao.gen.GenMailLocaleTemplatesDao;

/**
 * ロケール毎のメールテンプレート
 */
@DI(instance = Instance.Singleton)
public class MailLocaleTemplatesDao extends GenMailLocaleTemplatesDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static MailLocaleTemplatesDao get() {
        return Container.getComp(MailLocaleTemplatesDao.class);
    }



}
