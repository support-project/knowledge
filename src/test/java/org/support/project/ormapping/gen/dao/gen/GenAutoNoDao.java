package org.support.project.ormapping.gen.dao.gen;

import java.util.List;

import java.sql.Timestamp;

import org.support.project.ormapping.gen.entity.AutoNoEntity;
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
 * 採番のテストテーブル
 */
@DI(instance = Instance.Singleton)
public class GenAutoNoDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static GenAutoNoDao get() {
        return Container.getComp(GenAutoNoDao.class);
    }

    /**
     * 全て取得(削除フラグを無視して取得)
     */
    public List<AutoNoEntity> physicalSelectAll() {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/AutoNoDao/AutoNoDao_physical_select_all.sql");
        return executeQueryList(sql, AutoNoEntity.class);
    }

    /**
     * キーで1件取得(削除フラグを無視して取得)
     */
    public AutoNoEntity physicalSelectOnKey(Long no) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/AutoNoDao/AutoNoDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, AutoNoEntity.class, no);
    }

    /**
     * 全て取得
     */
    public List<AutoNoEntity> selectAll() {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/AutoNoDao/AutoNoDao_select_all.sql");
        return executeQueryList(sql, AutoNoEntity.class);
    }

    /**
     * キーで1件取得
     */
    public AutoNoEntity selectOnKey(Long no) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/AutoNoDao/AutoNoDao_select_on_key.sql");
        return executeQuerySingle(sql, AutoNoEntity.class, no);
    }

    /**
     * 登録(データを生で操作/DBの採番機能のカラムも自分でセット)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public AutoNoEntity rawPhysicalInsert(AutoNoEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/AutoNoDao/AutoNoDao_raw_insert.sql");
        executeUpdate(sql, entity.getNo(), entity.getStr(), entity.getInsertUser(), entity.getInsertDatetime(), entity.getUpdateUser(),
                entity.getUpdateDatetime());
        String driverClass = ConnectionManager.getInstance().getDriverClass(getConnectionName());
        if (ORMappingParameter.DRIVER_NAME_POSTGRESQL.equals(driverClass)) {
            String setValSql = "select setval('AUTO_NO_NO_seq', (select max(NO) from AUTO_NO));";
            executeQuerySingle(setValSql, Long.class);
        }
        return entity;
    }

    /**
     * 登録(データを生で操作)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public AutoNoEntity physicalInsert(AutoNoEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/AutoNoDao/AutoNoDao_insert.sql");
        Class<?> type = PropertyUtil.getPropertyType(entity, "no");
        Object key = executeInsert(sql, type, entity.getStr(), entity.getInsertUser(), entity.getInsertDatetime(), entity.getUpdateUser(),
                entity.getUpdateDatetime());
        PropertyUtil.setPropertyValue(entity, "no", key);
        return entity;
    }

    /**
     * 登録(登録ユーザを指定)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public AutoNoEntity insert(String user, AutoNoEntity entity) {
        return physicalInsert(entity);
    }

    /**
     * 登録
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public AutoNoEntity insert(AutoNoEntity entity) {
        return physicalInsert(entity);
    }

    /**
     * 更新(データを生で操作)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public AutoNoEntity physicalUpdate(AutoNoEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/AutoNoDao/AutoNoDao_update.sql");
        executeUpdate(sql, entity.getStr(), entity.getInsertUser(), entity.getInsertDatetime(), entity.getUpdateUser(), entity.getUpdateDatetime(),
                entity.getNo());
        return entity;
    }

    /**
     * 更新(更新ユーザを指定)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public AutoNoEntity update(String user, AutoNoEntity entity) {
        return physicalUpdate(entity);
    }

    /**
     * 更新
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public AutoNoEntity update(AutoNoEntity entity) {
        return physicalUpdate(entity);
    }

    /**
     * 保存(ユーザを指定)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public AutoNoEntity save(String user, AutoNoEntity entity) {
        AutoNoEntity db = selectOnKey(entity.getNo());
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
    public AutoNoEntity save(AutoNoEntity entity) {
        AutoNoEntity db = selectOnKey(entity.getNo());
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
    public void physicalDelete(Long no) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/AutoNoDao/AutoNoDao_delete.sql");
        executeUpdate(sql, no);
    }

    /**
     * 削除(データを生で操作/物理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(AutoNoEntity entity) {
        physicalDelete(entity.getNo());

    }

    /**
     * 削除(削除ユーザを指定／論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String user, Long no) {
        physicalDelete(no);
    }

    /**
     * 削除(論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Long no) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        String user = (String) pool.getUser();
        delete(user, no);
    }

    /**
     * 削除(削除ユーザを指定／論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String user, AutoNoEntity entity) {
        delete(user, entity.getNo());

    }

    /**
     * 削除(論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(AutoNoEntity entity) {
        delete(entity.getNo());

    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String user, Long no) {
        throw new ORMappingException("delete flag is not exists.");
    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Long no) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        String user = (String) pool.getUser();
        activation(user, no);
    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String user, AutoNoEntity entity) {
        activation(user, entity.getNo());

    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(AutoNoEntity entity) {
        activation(entity.getNo());

    }

}
