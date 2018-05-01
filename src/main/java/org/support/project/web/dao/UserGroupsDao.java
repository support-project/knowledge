package org.support.project.web.dao;

import java.util.ArrayList;
import java.util.List;

import org.support.project.common.util.StringJoinBuilder;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.common.SQLManager;
import org.support.project.web.dao.gen.GenUserGroupsDao;
import org.support.project.web.entity.UserGroupsEntity;

/**
 * ユーザが所属するグループ
 */
@DI(instance = Instance.Singleton)
public class UserGroupsDao extends GenUserGroupsDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static UserGroupsDao get() {
        return Container.getComp(UserGroupsDao.class);
    }

    /**
     * グループのID配列からエンティティを取得する
     * 
     * @param groupIds Integer[] Group Ids
     * @return list
     */
    public List<UserGroupsEntity> selectOnGroupIds(List<Integer> groupIds) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserGroupsDao/UserGroupsDao_select_on_group_ids.sql");
        StringJoinBuilder builder = new StringJoinBuilder();
        List<Integer> params = new ArrayList<Integer>();

        for (Integer groupId : groupIds) {
            builder.append("?");
            params.add(groupId);
        }

        sql = sql.replace("%GROUPS%", builder.join(", "));

        return executeQueryList(sql, UserGroupsEntity.class, params.toArray(new Integer[0]));
    }
}
