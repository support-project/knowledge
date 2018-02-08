package org.support.project.knowledge.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.knowledge.dao.gen.GenMailPostsDao;

/**
 * メールから投稿
 */
@DI(instance = Instance.Singleton)
public class MailPostsDao extends GenMailPostsDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static MailPostsDao get() {
        return Container.getComp(MailPostsDao.class);
    }



}
