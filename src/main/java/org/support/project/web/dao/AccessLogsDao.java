package org.support.project.web.dao;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.dao.gen.GenAccessLogsDao;

/**
 * ACCESS_LOGS
 */
@DI(instance = Instance.Singleton)
public class AccessLogsDao extends GenAccessLogsDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static AccessLogsDao get() {
        return Container.getComp(AccessLogsDao.class);
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Long countOnPath(String path) {
        String sql = "SELECT COUNT(*) FROM ACCESS_LOGS WHERE PATH = ?";
        return executeQuerySingle(sql, Long.class, path);
    }

}
