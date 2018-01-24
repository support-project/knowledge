package org.support.project.web.dao;

import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.common.SQLManager;
import org.support.project.web.bean.AccessUser;
import org.support.project.web.dao.gen.GenGroupsDao;
import org.support.project.web.entity.GroupsEntity;

/**
 * グループ
 */
@DI(instance = Instance.Singleton)
public class GroupsDao extends GenGroupsDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static GroupsDao get() {
        return Container.getComp(GroupsDao.class);
    }

    /**
     * ID
     */
    private int currentId = 0;

    /**
     * IDを採番 ※コミットしなくても次のIDを採番する為、保存しなければ欠番になる
     * @return next id
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer getNextId() {
        String sql = "SELECT MAX(GROUP_ID) FROM GROUPS;";
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
     * 自分が所属しているグループを取得
     * 
     * @param loginedUser loginedUser
     * @param offset offset
     * @param limit limit
     * @return groups
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<GroupsEntity> selectMyGroup(AccessUser loginedUser, int offset, int limit) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/GroupsDao/GroupsDao_selectMyGroup.sql");
        return executeQueryList(sql, GroupsEntity.class, loginedUser.getUserId(), limit, offset);
    }

    /**
     * アクセスできるグループを取得
     * @param loginedUser loginedUser
     * @param offset offset
     * @param limit limit
     * @return groups
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<GroupsEntity> selectAccessAbleGroups(AccessUser loginedUser, int offset, int limit) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/GroupsDao/GroupsDao_selectAccessAbleGroups.sql");
        return executeQueryList(sql, GroupsEntity.class, loginedUser.getUserId(), limit, offset);
    }

    /**
     * キーワードでグループを取得
     * 
     * @param keyword keyword
     * @param loginedUser loginedUser
     * @param offset offset
     * @param limit limit
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<GroupsEntity> selectOnKeyword(String keyword, AccessUser loginedUser, int offset, int limit) {
        if (loginedUser != null && loginedUser.isAdmin()) {
            String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/GroupsDao/GroupsDao_selectAdminOnKeyword.sql");
            return executeQueryList(sql, GroupsEntity.class, keyword, limit, offset);
        } else {
            String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/GroupsDao/GroupsDao_selectOnKeyword.sql");
            int userId = -1;
            if (loginedUser != null) {
                userId = loginedUser.getUserId();
            }
            return executeQueryList(sql, GroupsEntity.class, keyword, userId, limit, offset);
        }
    }

    /**
     * キーワードで所属グループを取得
     * 
     * @param keyword keyword
     * @param loginedUser loginedUser
     * @param offset offset
     * @param limit limit
     * @return group list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<GroupsEntity> selectMyGroupOnKeyword(String keyword, AccessUser loginedUser, int offset, int limit) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/GroupsDao/GroupsDao_selectMyGroupOnKeyword.sql");
        return executeQueryList(sql, GroupsEntity.class, keyword, loginedUser.getUserId(), limit, offset);
    }

    /**
     * アクセス可能なグループを取得 ※アクセス可能というのはUSER_GROUPSに登録されている、もしくは、「公開」か「保護」のもの
     * 
     * @param groupId groupid
     * @param loginedUser loginedUser
     * @return group
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public GroupsEntity selectAccessAbleGroup(Integer groupId, AccessUser loginedUser) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/GroupsDao/GroupsDao_selectAccessAbleGroup.sql");
        return executeQuerySingle(sql, GroupsEntity.class, groupId, loginedUser.getUserId());
    }

    /**
     * 編集可能なグループを取得 ※編集可能というのはUSER_GROUPSに存在し、かつそのロールが「1:管理者」であるもの
     * 
     * @param groupId groupId
     * @param loginedUser loginedUser
     * @return group
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public GroupsEntity selectEditAbleGroup(Integer groupId, AccessUser loginedUser) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/GroupsDao/GroupsDao_selectEditAbleGroup.sql");
        return executeQuerySingle(sql, GroupsEntity.class, groupId, loginedUser.getUserId());
    }

    /**
     * 指定のグループを取得
     * 
     * @param groupids groupids
     * @return group list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<GroupsEntity> selectOnGroupIds(List<Integer> groupids) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT * FROM GROUPS WHERE GROUP_ID IN (");
        int cnt = 0;
        for (Integer integer : groupids) {
            if (cnt > 0) {
                builder.append(",");
            }
            builder.append("?");
            cnt++;
        }
        builder.append(") ORDER BY GROUP_ID");
        return executeQueryList(builder.toString(), GroupsEntity.class, groupids.toArray(new Integer[0]));
    }

    /**
     * グループ名からグループを取得
     * 
     * @param groupName group name
     * @return group
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public GroupsEntity selectOnGroupName(String groupName) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/GroupsDao/GroupsDao_selectOnGroupName.sql");
        return executeQuerySingle(sql, GroupsEntity.class, groupName);
    }

    /**
     * データをtruncateする
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void truncate() {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/GroupsDao/GroupsDao_truncate.sql");
        executeUpdate(sql);
    }

    /**
     * sequenceをリセットする
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void resetSequence() {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/GroupsDao/GroupsDao_alter_sequence.sql");
        executeUpdate(sql);
    }
}
