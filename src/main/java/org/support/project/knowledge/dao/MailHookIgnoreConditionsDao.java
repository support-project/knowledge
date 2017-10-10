package org.support.project.knowledge.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.knowledge.dao.gen.GenMailHookIgnoreConditionsDao;

/**
 * メールから投稿の際の除外条件
 */
@DI(instance = Instance.Singleton)
public class MailHookIgnoreConditionsDao extends GenMailHookIgnoreConditionsDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static MailHookIgnoreConditionsDao get() {
        return Container.getComp(MailHookIgnoreConditionsDao.class);
    }



}
