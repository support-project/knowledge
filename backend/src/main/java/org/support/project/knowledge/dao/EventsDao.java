package org.support.project.knowledge.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenEventsDao;
import org.support.project.knowledge.entity.EventsEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.ormapping.common.SQLManager;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.entity.GroupsEntity;

/**
 * イベント
 */
@DI(instance = Instance.Singleton)
public class EventsDao extends GenEventsDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static EventsDao get() {
        return Container.getComp(EventsDao.class);
    }
    
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<EventsEntity> selectAccessAbleEvents(Calendar start, Calendar end, LoginedUser loginedUser) {
        String sql;
        if (loginedUser != null && loginedUser.isAdmin()) {
            sql = SQLManager.getInstance()
                    .getSql("/org/support/project/knowledge/dao/sql/EventsDao/EventsDao_selectAdminEvents.sql");
        } else {
            sql = SQLManager.getInstance()
                    .getSql("/org/support/project/knowledge/dao/sql/EventsDao/EventsDao_selectAccessAbleEvents.sql");
        }
        List<Object> params = new ArrayList<>();
        params.add(new Timestamp(start.getTimeInMillis()));
        params.add(new Timestamp(end.getTimeInMillis()));
        if (loginedUser == null || !loginedUser.isAdmin()) {
            sql = addAccessCondition(loginedUser, sql, params);
        }
        return executeQueryList(sql, EventsEntity.class, params.toArray(new Object[0]));
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    private String addAccessCondition(LoginedUser loginedUser, String sql, List<Object> params) {
        Integer loginuserId = Integer.MIN_VALUE;
        if (loginedUser != null) {
            loginuserId = loginedUser.getUserId();
        }
        params.add(loginuserId);
        params.add(loginuserId);

        List<Integer> groups = new ArrayList<>();
        groups.add(0); // ALL Groups
        if (loginedUser != null && loginedUser.getGroups() != null) {
            List<GroupsEntity> userGroups = loginedUser.getGroups();
            for (GroupsEntity groupsEntity : userGroups) {
                groups.add(groupsEntity.getGroupId());
            }
        }
        StringBuilder groupParams = new StringBuilder();
        int cnt = 0;
        for (Integer integer : groups) {
            if (cnt > 0) {
                groupParams.append(", ");
            }
            cnt++;
            params.add(integer);
            groupParams.append("?");
        }
        sql = sql.replace("%GROUPS%", groupParams.toString());
        return sql;
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgesEntity> selectAccessAbleEvents(Calendar start, LoginedUser loginedUser, int limit, int offset) {
        String sql;
        if (loginedUser != null && loginedUser.isAdmin()) {
            sql = SQLManager.getInstance()
                    .getSql("/org/support/project/knowledge/dao/sql/EventsDao/EventsDao_selectAdminKnowledgeEvents.sql");
        } else {
            sql = SQLManager.getInstance()
                    .getSql("/org/support/project/knowledge/dao/sql/EventsDao/EventsDao_selectAccessAbleKnowledgeEvents.sql");
        }
        List<Object> params = new ArrayList<>();
        params.add(new Timestamp(start.getTimeInMillis()));
        if (loginedUser == null || !loginedUser.isAdmin()) {
            sql = addAccessCondition(loginedUser, sql, params);
        }
        params.add(limit);
        params.add(offset);
        return executeQueryList(sql, KnowledgesEntity.class, params.toArray(new Object[0]));
    }



}
