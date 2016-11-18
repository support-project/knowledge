package org.support.project.knowledge.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.knowledge.dao.gen.GenDraftKnowledgesDao;

/**
 * ナレッジの下書き
 */
@DI(instance = Instance.Singleton)
public class DraftKnowledgesDao extends GenDraftKnowledgesDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static DraftKnowledgesDao get() {
        return Container.getComp(DraftKnowledgesDao.class);
    }



}
