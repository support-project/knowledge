package org.support.project.knowledge.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenPointUserHistoriesDao;
import org.support.project.knowledge.entity.PointUserHistoriesEntity;
import org.support.project.knowledge.vo.ContributionPointHistory;
import org.support.project.knowledge.vo.UserConfigs;
import org.support.project.ormapping.common.SQLManager;
import org.support.project.ormapping.config.Order;
import org.support.project.ormapping.connection.ConnectionManager;

/**
 * ユーザのポイント獲得履歴
 */
@DI(instance = Instance.Singleton)
public class PointUserHistoriesDao extends GenPointUserHistoriesDao {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(PointUserHistoriesDao.class);

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static PointUserHistoriesDao get() {
        return Container.getComp(PointUserHistoriesDao.class);
    }
    
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public long selectNumOnUser(int user) {
        String sql = "SELECT MAX(HISTORY_NO) FROM POINT_USER_HISTORIES WHERE USER_ID = ?";
        return executeQuerySingle(sql, Long.class, user);
    }
    
    private String convTimeToString(int min) throws ParseException {
        boolean minus = false;
        if (min < 0) {
            minus = true;
            min = min * -1;
        }
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date d = format.parse("2000/01/01 00:00:00");
        d.setTime(d.getTime() + (min * 60 * 1000));
        StringBuilder builder = new StringBuilder();
        if (minus) {
            builder.append("-");
        }
        builder.append(time.format(d));
        return builder.toString();
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ContributionPointHistory> selectPointHistoriesByDate(Integer userId, UserConfigs userConfigs) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/PointUserHistoriesDao/select_point_history_by_date.sql");
        if (ConnectionManager.getInstance().getDriverClass().indexOf("postgresql") != -1) {
            String min;
            try {
                min = convTimeToString(userConfigs.getTimezoneOffset());
            } catch (ParseException e) {
                min = "00:00";
            }
            return executeQueryList(sql, ContributionPointHistory.class, min, userId);
        } else {
            return executeQueryList(sql, ContributionPointHistory.class, userConfigs.getTimezoneOffset(), userId);
        }
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<PointUserHistoriesEntity> selectOnUser(Integer userId, int limit, int offset, Order order) {
        String sql = "SELECT * FROM POINT_USER_HISTORIES WHERE USER_ID = ? ORDER BY INSERT_DATETIME %S LIMIT ? OFFSET ?;";
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, PointUserHistoriesEntity.class, userId, limit, offset);
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void removeAll() {
        LOG.warn("Remove all point user histories");
        String sql = "DELETE FROM POINT_USER_HISTORIES";
        executeUpdate(sql);
    }



}
