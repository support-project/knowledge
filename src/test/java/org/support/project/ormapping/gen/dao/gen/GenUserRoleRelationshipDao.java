package org.support.project.ormapping.gen.dao.gen;

import java.util.List;

import java.sql.Timestamp;

import org.support.project.ormapping.gen.entity.UserRoleRelationshipEntity;
import org.support.project.ormapping.dao.AbstractDao;
import org.support.project.ormapping.exception.ORMappingException;
import org.support.project.ormapping.common.SQLManager;
import org.support.project.ormapping.common.DBUserPool;
import org.support.project.ormapping.common.IDGen;
import org.support.project.ormapping.config.ORMappingParameter;
import org.support.project.ormapping.connection.ConnectionManager;
import org.support.project.common.util.PropertyUtil;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.aop.Aspect;

/**
 * 組織以外の役割の紐付
 */
@DI(instance = Instance.Singleton)
public class GenUserRoleRelationshipDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static GenUserRoleRelationshipDao get() {
        return Container.getComp(GenUserRoleRelationshipDao.class);
    }

    /**
     * 全て取得(削除フラグを無視して取得)
     */
    public List<UserRoleRelationshipEntity> physicalSelectAll() {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/UserRoleRelationshipDao/UserRoleRelationshipDao_physical_select_all.sql");
        return executeQueryList(sql, UserRoleRelationshipEntity.class);
    }

    /**
     * キーで1件取得(削除フラグを無視して取得)
     */
    public UserRoleRelationshipEntity physicalSelectOnKey(String employeeId, String roleId) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/UserRoleRelationshipDao/UserRoleRelationshipDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, UserRoleRelationshipEntity.class, employeeId, roleId);
    }

    /**
     * 全て取得
     */
    public List<UserRoleRelationshipEntity> selectAll() {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/UserRoleRelationshipDao/UserRoleRelationshipDao_select_all.sql");
        return executeQueryList(sql, UserRoleRelationshipEntity.class);
    }

    /**
     * キーで1件取得
     */
    public UserRoleRelationshipEntity selectOnKey(String employeeId, String roleId) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/UserRoleRelationshipDao/UserRoleRelationshipDao_select_on_key.sql");
        return executeQuerySingle(sql, UserRoleRelationshipEntity.class, employeeId, roleId);
    }

    /**
     * EMPLOYEE_ID でリストを取得
     */
    public List<UserRoleRelationshipEntity> selectOnEmployeeId(String employeeId) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/UserRoleRelationshipDao/UserRoleRelationshipDao_select_on_employee_id.sql");
        return executeQueryList(sql, UserRoleRelationshipEntity.class, employeeId);
    }

    /**
     * ROLE_ID でリストを取得
     */
    public List<UserRoleRelationshipEntity> selectOnRoleId(String roleId) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/UserRoleRelationshipDao/UserRoleRelationshipDao_select_on_role_id.sql");
        return executeQueryList(sql, UserRoleRelationshipEntity.class, roleId);
    }

    /**
     * EMPLOYEE_ID でリストを取得
     */
    public List<UserRoleRelationshipEntity> physicalSelectOnEmployeeId(String employeeId) {
        String sql = SQLManager.getInstance().getSql(
                "/org/support/project/ormapping/gen/dao/sql/UserRoleRelationshipDao/UserRoleRelationshipDao_physical_select_on_employee_id.sql");
        return executeQueryList(sql, UserRoleRelationshipEntity.class, employeeId);
    }

    /**
     * ROLE_ID でリストを取得
     */
    public List<UserRoleRelationshipEntity> physicalSelectOnRoleId(String roleId) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/UserRoleRelationshipDao/UserRoleRelationshipDao_physical_select_on_role_id.sql");
        return executeQueryList(sql, UserRoleRelationshipEntity.class, roleId);
    }

    /**
     * 登録(データを生で操作/DBの採番機能のカラムも自分でセット)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public UserRoleRelationshipEntity rawPhysicalInsert(UserRoleRelationshipEntity entity) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/UserRoleRelationshipDao/UserRoleRelationshipDao_raw_insert.sql");
        executeUpdate(sql, entity.getEmployeeId(), entity.getRoleId(), entity.getInsertUser(), entity.getInsertDatetime(), entity.getUpdateUser(),
                entity.getUpdateDatetime());
        return entity;
    }

    /**
     * 登録(データを生で操作)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public UserRoleRelationshipEntity physicalInsert(UserRoleRelationshipEntity entity) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/UserRoleRelationshipDao/UserRoleRelationshipDao_insert.sql");
        executeUpdate(sql, entity.getEmployeeId(), entity.getRoleId(), entity.getInsertUser(), entity.getInsertDatetime(), entity.getUpdateUser(),
                entity.getUpdateDatetime());
        return entity;
    }

    /**
     * 登録(登録ユーザを指定)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public UserRoleRelationshipEntity insert(String user, UserRoleRelationshipEntity entity) {
        return physicalInsert(entity);
    }

    /**
     * 登録
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public UserRoleRelationshipEntity insert(UserRoleRelationshipEntity entity) {
        return physicalInsert(entity);
    }

    /**
     * 更新(データを生で操作)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public UserRoleRelationshipEntity physicalUpdate(UserRoleRelationshipEntity entity) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/UserRoleRelationshipDao/UserRoleRelationshipDao_update.sql");
        executeUpdate(sql, entity.getInsertUser(), entity.getInsertDatetime(), entity.getUpdateUser(), entity.getUpdateDatetime(),
                entity.getEmployeeId(), entity.getRoleId());
        return entity;
    }

    /**
     * 更新(更新ユーザを指定)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public UserRoleRelationshipEntity update(String user, UserRoleRelationshipEntity entity) {
        return physicalUpdate(entity);
    }

    /**
     * 更新
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public UserRoleRelationshipEntity update(UserRoleRelationshipEntity entity) {
        return physicalUpdate(entity);
    }

    /**
     * 保存(ユーザを指定)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public UserRoleRelationshipEntity save(String user, UserRoleRelationshipEntity entity) {
        UserRoleRelationshipEntity db = selectOnKey(entity.getEmployeeId(), entity.getRoleId());
        if (db == null) {
            return insert(user, entity);
        } else {
            return update(user, entity);
        }
    }

    /**
     * 保存(存在しなければ登録、存在すれば更新)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public UserRoleRelationshipEntity save(UserRoleRelationshipEntity entity) {
        UserRoleRelationshipEntity db = selectOnKey(entity.getEmployeeId(), entity.getRoleId());
        if (db == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }

    /**
     * 削除(データを生で操作/物理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(String employeeId, String roleId) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/UserRoleRelationshipDao/UserRoleRelationshipDao_delete.sql");
        executeUpdate(sql, employeeId, roleId);
    }

    /**
     * 削除(データを生で操作/物理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(UserRoleRelationshipEntity entity) {
        physicalDelete(entity.getEmployeeId(), entity.getRoleId());

    }

    /**
     * 削除(削除ユーザを指定／論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String user, String employeeId, String roleId) {
        physicalDelete(employeeId, roleId);
    }

    /**
     * 削除(論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String employeeId, String roleId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        String user = (String) pool.getUser();
        delete(user, employeeId, roleId);
    }

    /**
     * 削除(削除ユーザを指定／論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String user, UserRoleRelationshipEntity entity) {
        delete(user, entity.getEmployeeId(), entity.getRoleId());

    }

    /**
     * 削除(論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(UserRoleRelationshipEntity entity) {
        delete(entity.getEmployeeId(), entity.getRoleId());

    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String user, String employeeId, String roleId) {
        throw new ORMappingException("delete flag is not exists.");
    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String employeeId, String roleId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        String user = (String) pool.getUser();
        activation(user, employeeId, roleId);
    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String user, UserRoleRelationshipEntity entity) {
        activation(user, entity.getEmployeeId(), entity.getRoleId());

    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(UserRoleRelationshipEntity entity) {
        activation(entity.getEmployeeId(), entity.getRoleId());

    }

}
