package org.support.project.knowledge.dao;

import java.util.List;

import org.support.project.aop.Aspect;
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
        sql.append("SELECT *, USERS.USER_NAME FROM ACTIVITIES INNER JOIN USERS ON (ACTIVITIES.user_id = users.user_id) ");
        sql.append("WHERE activity_no IN (");
        boolean appended = false;
        for (Long no : activityNos) {
            if (appended) {
                sql.append(", ");
                
            }
            sql.append("?");
            appended = true;
        }
        sql.append(");");
        return executeQueryList(sql.toString(), ActivitiesEntity.class, activityNos.toArray(new Long[0]));
    }



}
