package org.support.project.web.dao;

import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.common.SQLManager;
import org.support.project.web.dao.gen.GenRolesDao;
import org.support.project.web.entity.RolesEntity;

/**
 * 権限
 */
@DI(instance = Instance.Singleton)
public class RolesDao extends GenRolesDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static RolesDao get() {
        return Container.getComp(RolesDao.class);
    }

    /**
     * ID
     */
    private int currentId = 0;

    /**
     * IDを採番 ※コミットしなくても次のIDを採番する為、保存しなければ欠番になる
     * @return next id
     */
    public Integer getNextId() {
        String sql = "SELECT MAX(ROLE_ID) FROM ROLES;";
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
     * ユーザのキーでロールの一覧を取得
     * 
     * @param userKey userKey
     * @return role list
     */
    public List<RolesEntity> selectOnUserKey(String userKey) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/RolesDao/RolesDao_select_on_userkey.sql");
        return executeQueryList(sql, RolesEntity.class, userKey);
    }

    /**
     * データをtruncateする
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void truncate() {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/RolesDao/RolesDao_truncate.sql");
        executeUpdate(sql);
    }
    
    /**
     * ロールのキー文字列で取得
     * @param roleKey ロールのキー文字列
     * @return ロール情報
     */
    public RolesEntity selectOnRoleKey(String roleKey) {
        String sql = "SELECT * FROM ROLES WHERE ROLE_KEY = ? AND DELETE_FLAG = 0";
        return executeQuerySingle(sql, RolesEntity.class, roleKey);
    }
}
