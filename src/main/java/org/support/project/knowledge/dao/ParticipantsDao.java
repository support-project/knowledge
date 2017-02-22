package org.support.project.knowledge.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.knowledge.dao.gen.GenParticipantsDao;

/**
 * 参加者
 */
@DI(instance = Instance.Singleton)
public class ParticipantsDao extends GenParticipantsDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static ParticipantsDao get() {
        return Container.getComp(ParticipantsDao.class);
    }



}
