package org.support.project.knowledge.dao;

import java.util.ArrayList;
import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenSurveysDao;
import org.support.project.knowledge.entity.SurveysEntity;
import org.support.project.ormapping.common.SQLManager;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.entity.GroupsEntity;

/**
 * アンケート
 */
@DI(instance = Instance.Singleton)
public class SurveysDao extends GenSurveysDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static SurveysDao get() {
        return Container.getComp(SurveysDao.class);
    }
    
    /**
     * IDのプレフィックスで絞り込んだアンケートの一覧を取得
     * @param idPrefix
     * @param limit
     * @param offset
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<SurveysEntity> selectWithKnowledgeTitle(String idPrefix, int limit, int offset) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/SurveysDao/SurveysDao_select_with_knowledge_title.sql");
        return executeQueryList(sql, SurveysEntity.class, idPrefix, limit, offset);
    }

    /**
     * IDのプレフィックスで絞り込んだアンケートの一覧を取得
     * アクセス権のあるKnowledgeに紐付いたもののみ
     * @param loginedUser 
     * @param idPrefix
     * @param limit
     * @param offset
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<SurveysEntity> selectWithKnowledgeTitleOnlyAccessAble(LoginedUser loginedUser, String idPrefix, int limit, int offset) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/SurveysDao/SurveysDao_select_on_accessable.sql");
        List<Object> params = new ArrayList<>();
        params.add(idPrefix);
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
        params.add(limit);
        params.add(offset);
        
        return executeQueryList(sql, SurveysEntity.class, params.toArray(new Object[0]));
    }


}
