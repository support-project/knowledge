package org.support.project.knowledge.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenVotesDao;

/**
 * 投票
 */
@DI(instance = Instance.Singleton)
public class VotesDao extends GenVotesDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static VotesDao get() {
        return Container.getComp(VotesDao.class);
    }

}
