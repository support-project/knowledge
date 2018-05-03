package org.support.project.web.dao;

import java.util.List;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.common.SQLManager;
import org.support.project.web.dao.gen.GenNoticesDao;
import org.support.project.web.entity.NoticesEntity;

/**
 * 告知
 */
@DI(instance = Instance.Singleton)
public class NoticesDao extends GenNoticesDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static NoticesDao get() {
        return Container.getComp(NoticesDao.class);
    }


    /**
     * ID 
     */
    private int currentId = 0;

    /**
     * Get Next id 
     * @return next id
     */
    public Integer getNextId() {
        String sql = "SELECT MAX(NO) FROM NOTICES;";
        Integer integer = executeQuerySingle(sql, Integer.class);
        if (integer != null) {
            if (currentId < integer) {
                currentId = integer;
            }
        }
        currentId++;
        return currentId;
    }
    
    /**
     * Select notices on now
     * @return notices
     */
    public List<NoticesEntity> selectNowNotices() {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/NoticesDao/NoticesDao_select_now_notices.sql");
        return executeQueryList(sql, NoticesEntity.class);
    }
    
    /**
     * Select notices on now and not read
     * @param userId user id
     * @return notices
     */
    public List<NoticesEntity> selectMyNotices(Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/NoticesDao/NoticesDao_select_my_notices.sql");
        return executeQueryList(sql, NoticesEntity.class, userId);
    }


}
