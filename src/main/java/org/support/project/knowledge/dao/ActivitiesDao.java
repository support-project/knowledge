package org.support.project.knowledge.dao;

import java.util.ArrayList;
import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenActivitiesDao;
import org.support.project.knowledge.entity.ActivitiesEntity;

/**
 * アクティビティ
 */
@DI(instance = Instance.Singleton)
public class ActivitiesDao extends GenActivitiesDao {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(ActivitiesDao.class);

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static ActivitiesDao get() {
        return Container.getComp(ActivitiesDao.class);
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public ActivitiesEntity select(int userId, int type, String target) {
        String sql = "SELECT * FROM ACTIVITIES WHERE USER_ID = ? AND KIND = ? AND TARGET = ?";
        return executeQuerySingle(sql, ActivitiesEntity.class, userId, type, target);
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ActivitiesEntity> selectOnNos(List<Long> activityNos) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT *, USERS.USER_NAME FROM ACTIVITIES INNER JOIN USERS ON (ACTIVITIES.USER_ID = USERS.USER_ID) ");
        sql.append("WHERE ACTIVITY_NO IN (");
        boolean appended = false;
        for (@SuppressWarnings("unused") Long no : activityNos) {
            if (appended) {
                sql.append(", ");
            }
            sql.append("?");
            appended = true;
        }
        sql.append(");");
        return executeQueryList(sql.toString(), ActivitiesEntity.class, activityNos.toArray(new Object[0]));
    }
    
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public ActivitiesEntity selectBefore(Integer userId, String target, int... kinds) {
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<>();
        params.add(userId);
        params.add(target);
        sql.append("SELECT * FROM ACTIVITIES ");
        sql.append("WHERE USER_ID = ? AND TARGET = ? AND ");
        sql.append("KIND IN (");
        boolean appended = false;
        for (int i : kinds) {
            if (appended) {
                sql.append(", ");
                
            }
            sql.append("?");
            appended = true;
            params.add(i);
        }
        sql.append(") ORDER BY INSERT_DATETIME DESC LIMIT 1 OFFSET 0");
        return executeQuerySingle(sql.toString(), ActivitiesEntity.class, params.toArray(new Object[0]));
    }
    /**
     * 指定のアクティビティの件数を取得
     * @param kind
     * @param targetId
     * @return
     */
    public long selectCountByTarget(int kind, Long targetId) {
        String target = String.valueOf(targetId);
        String sql = "SELECT COUNT(*) FROM ACTIVITIES WHERE KIND = ? AND TARGET = ?";
        return executeQuerySingle(sql, Long.class, kind, target);
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void removeAll() {
        LOG.warn("Remove all activities");
        String sql = "DELETE FROM ACTIVITIES";
        executeUpdate(sql);
    }



}
