package org.support.project.ormapping.gen.dao.gen;

import java.util.List;

import java.sql.Timestamp;

import org.support.project.ormapping.gen.entity.RoleEntity;
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
 * 役割
 */
@DI(instance = Instance.Singleton)
public class GenRoleDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static GenRoleDao get() {
        return Container.getComp(GenRoleDao.class);
    }

    /**
     * 全て取得(削除フラグを無視して取得)
     */
    public List<RoleEntity> physicalSelectAll() {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/RoleDao/RoleDao_physical_select_all.sql");
        return executeQueryList(sql, RoleEntity.class);
    }

    /**
     * キーで1件取得(削除フラグを無視して取得)
     */
    public RoleEntity physicalSelectOnKey(String roleId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/RoleDao/RoleDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, RoleEntity.class, roleId);
    }

    /**
     * 全て取得
     */
    public List<RoleEntity> selectAll() {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/RoleDao/RoleDao_select_all.sql");
        return executeQueryList(sql, RoleEntity.class);
    }

    /**
     * キーで1件取得
     */
    public RoleEntity selectOnKey(String roleId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/RoleDao/RoleDao_select_on_key.sql");
        return executeQuerySingle(sql, RoleEntity.class, roleId);
    }

    /**
     * 登録(データを生で操作/DBの採番機能のカラムも自分でセット)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public RoleEntity rawPhysicalInsert(RoleEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/RoleDao/RoleDao_raw_insert.sql");
        executeUpdate(sql, entity.getRoleId(), entity.getRoleName(), entity.getInsertUser(), entity.getInsertDatetime(), entity.getUpdateUser(),
                entity.getUpdateDatetime());
        return entity;
    }

    /**
     * 登録(データを生で操作)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public RoleEntity physicalInsert(RoleEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/RoleDao/RoleDao_insert.sql");
        executeUpdate(sql, entity.getRoleId(), entity.getRoleName(), entity.getInsertUser(), entity.getInsertDatetime(), entity.getUpdateUser(),
                entity.getUpdateDatetime());
        return entity;
    }

    /**
     * 登録(登録ユーザを指定)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public RoleEntity insert(String user, RoleEntity entity) {
        return physicalInsert(entity);
    }

    /**
     * 登録
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public RoleEntity insert(RoleEntity entity) {
        return physicalInsert(entity);
    }

    /**
     * 更新(データを生で操作)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public RoleEntity physicalUpdate(RoleEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/RoleDao/RoleDao_update.sql");
        executeUpdate(sql, entity.getRoleName(), entity.getInsertUser(), entity.getInsertDatetime(), entity.getUpdateUser(),
                entity.getUpdateDatetime(), entity.getRoleId());
        return entity;
    }

    /**
     * 更新(更新ユーザを指定)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public RoleEntity update(String user, RoleEntity entity) {
        return physicalUpdate(entity);
    }

    /**
     * 更新
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public RoleEntity update(RoleEntity entity) {
        return physicalUpdate(entity);
    }

    /**
     * 保存(ユーザを指定)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public RoleEntity save(String user, RoleEntity entity) {
        RoleEntity db = selectOnKey(entity.getRoleId());
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
    public RoleEntity save(RoleEntity entity) {
        RoleEntity db = selectOnKey(entity.getRoleId());
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
    public void physicalDelete(String roleId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/RoleDao/RoleDao_delete.sql");
        executeUpdate(sql, roleId);
    }

    /**
     * 削除(データを生で操作/物理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(RoleEntity entity) {
        physicalDelete(entity.getRoleId());

    }

    /**
     * 削除(削除ユーザを指定／論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String user, String roleId) {
        physicalDelete(roleId);
    }

    /**
     * 削除(論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String roleId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        String user = (String) pool.getUser();
        delete(user, roleId);
    }

    /**
     * 削除(削除ユーザを指定／論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String user, RoleEntity entity) {
        delete(user, entity.getRoleId());

    }

    /**
     * 削除(論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(RoleEntity entity) {
        delete(entity.getRoleId());

    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String user, String roleId) {
        throw new ORMappingException("delete flag is not exists.");
    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String roleId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        String user = (String) pool.getUser();
        activation(user, roleId);
    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String user, RoleEntity entity) {
        activation(user, entity.getRoleId());

    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(RoleEntity entity) {
        activation(entity.getRoleId());

    }

}
