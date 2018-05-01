package org.support.project.ormapping.gen.dao.gen;

import java.util.List;

import java.sql.Timestamp;

import org.support.project.ormapping.gen.entity.EmployeeEntity;
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
 * 従業員
 */
@DI(instance = Instance.Singleton)
public class GenEmployeeDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static GenEmployeeDao get() {
        return Container.getComp(GenEmployeeDao.class);
    }

    /**
     * 全て取得(削除フラグを無視して取得)
     */
    public List<EmployeeEntity> physicalSelectAll() {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/EmployeeDao/EmployeeDao_physical_select_all.sql");
        return executeQueryList(sql, EmployeeEntity.class);
    }

    /**
     * キーで1件取得(削除フラグを無視して取得)
     */
    public EmployeeEntity physicalSelectOnKey(String employeeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/EmployeeDao/EmployeeDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, EmployeeEntity.class, employeeId);
    }

    /**
     * 全て取得
     */
    public List<EmployeeEntity> selectAll() {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/EmployeeDao/EmployeeDao_select_all.sql");
        return executeQueryList(sql, EmployeeEntity.class);
    }

    /**
     * キーで1件取得
     */
    public EmployeeEntity selectOnKey(String employeeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/EmployeeDao/EmployeeDao_select_on_key.sql");
        return executeQuerySingle(sql, EmployeeEntity.class, employeeId);
    }

    /**
     * 登録(データを生で操作/DBの採番機能のカラムも自分でセット)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public EmployeeEntity rawPhysicalInsert(EmployeeEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/EmployeeDao/EmployeeDao_raw_insert.sql");
        executeUpdate(sql, entity.getEmployeeId(), entity.getEmployeeName(), entity.getPassword(), entity.getMailAdress(), entity.getInsertUser(),
                entity.getInsertDatetime(), entity.getUpdateUser(), entity.getUpdateDatetime());
        return entity;
    }

    /**
     * 登録(データを生で操作)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public EmployeeEntity physicalInsert(EmployeeEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/EmployeeDao/EmployeeDao_insert.sql");
        executeUpdate(sql, entity.getEmployeeId(), entity.getEmployeeName(), entity.getPassword(), entity.getMailAdress(), entity.getInsertUser(),
                entity.getInsertDatetime(), entity.getUpdateUser(), entity.getUpdateDatetime());
        return entity;
    }

    /**
     * 登録(登録ユーザを指定)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public EmployeeEntity insert(String user, EmployeeEntity entity) {
        return physicalInsert(entity);
    }

    /**
     * 登録
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public EmployeeEntity insert(EmployeeEntity entity) {
        return physicalInsert(entity);
    }

    /**
     * 更新(データを生で操作)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public EmployeeEntity physicalUpdate(EmployeeEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/EmployeeDao/EmployeeDao_update.sql");
        executeUpdate(sql, entity.getEmployeeName(), entity.getPassword(), entity.getMailAdress(), entity.getInsertUser(), entity.getInsertDatetime(),
                entity.getUpdateUser(), entity.getUpdateDatetime(), entity.getEmployeeId());
        return entity;
    }

    /**
     * 更新(更新ユーザを指定)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public EmployeeEntity update(String user, EmployeeEntity entity) {
        return physicalUpdate(entity);
    }

    /**
     * 更新
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public EmployeeEntity update(EmployeeEntity entity) {
        return physicalUpdate(entity);
    }

    /**
     * 保存(ユーザを指定)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public EmployeeEntity save(String user, EmployeeEntity entity) {
        EmployeeEntity db = selectOnKey(entity.getEmployeeId());
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
    public EmployeeEntity save(EmployeeEntity entity) {
        EmployeeEntity db = selectOnKey(entity.getEmployeeId());
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
    public void physicalDelete(String employeeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/EmployeeDao/EmployeeDao_delete.sql");
        executeUpdate(sql, employeeId);
    }

    /**
     * 削除(データを生で操作/物理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(EmployeeEntity entity) {
        physicalDelete(entity.getEmployeeId());

    }

    /**
     * 削除(削除ユーザを指定／論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String user, String employeeId) {
        physicalDelete(employeeId);
    }

    /**
     * 削除(論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String employeeId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        String user = (String) pool.getUser();
        delete(user, employeeId);
    }

    /**
     * 削除(削除ユーザを指定／論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String user, EmployeeEntity entity) {
        delete(user, entity.getEmployeeId());

    }

    /**
     * 削除(論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(EmployeeEntity entity) {
        delete(entity.getEmployeeId());

    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String user, String employeeId) {
        throw new ORMappingException("delete flag is not exists.");
    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String employeeId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        String user = (String) pool.getUser();
        activation(user, employeeId);
    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String user, EmployeeEntity entity) {
        activation(user, entity.getEmployeeId());

    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(EmployeeEntity entity) {
        activation(entity.getEmployeeId());

    }

}
