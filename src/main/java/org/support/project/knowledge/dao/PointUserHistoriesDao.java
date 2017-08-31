package org.support.project.knowledge.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenPointUserHistoriesDao;

/**
 * ユーザのポイント獲得履歴
 */
@DI(instance = Instance.Singleton)
public class PointUserHistoriesDao extends GenPointUserHistoriesDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static PointUserHistoriesDao get() {
        return Container.getComp(PointUserHistoriesDao.class);
    }
    
    public long selectNumOnUser(int user) {
        String sql = "SELECT MAX(HISTORY_NO) FROM POINT_USER_HISTORIES WHERE USER_ID = ?";
        return executeQuerySingle(sql, Long.class, user);
    }



}
