package org.support.project.web.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.web.dao.gen.GenReadMarksDao;

/**
 * 既読
 */
@DI(instance = Instance.Singleton)
public class ReadMarksDao extends GenReadMarksDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static ReadMarksDao get() {
        return Container.getComp(ReadMarksDao.class);
    }



}
