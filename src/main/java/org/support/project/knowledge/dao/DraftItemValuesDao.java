package org.support.project.knowledge.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.knowledge.dao.gen.GenDraftItemValuesDao;

/**
 * ナレッジの項目値
 */
@DI(instance = Instance.Singleton)
public class DraftItemValuesDao extends GenDraftItemValuesDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static DraftItemValuesDao get() {
        return Container.getComp(DraftItemValuesDao.class);
    }



}
