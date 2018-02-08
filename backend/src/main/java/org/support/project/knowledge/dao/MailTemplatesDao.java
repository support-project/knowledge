package org.support.project.knowledge.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.knowledge.dao.gen.GenMailTemplatesDao;

/**
 * メールテンプレート
 */
@DI(instance = Instance.Singleton)
public class MailTemplatesDao extends GenMailTemplatesDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static MailTemplatesDao get() {
        return Container.getComp(MailTemplatesDao.class);
    }



}
