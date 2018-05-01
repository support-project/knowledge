package org.support.project.ormapping.gen.dao.gen;

import java.util.List;

import java.sql.Timestamp;

import org.support.project.ormapping.gen.entity.ClobTableEntity;
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
 * CLOBのテストテーブル
 */
@DI(instance = Instance.Singleton)
public class GenClobTableDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static GenClobTableDao get() {
        return Container.getComp(GenClobTableDao.class);
    }

    /**
     * 全て取得(削除フラグを無視して取得)
     */
    public List<ClobTableEntity> physicalSelectAll() {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/ClobTableDao/ClobTableDao_physical_select_all.sql");
        return executeQueryList(sql, ClobTableEntity.class);
    }

    /**
     * キーで1件取得(削除フラグを無視して取得)
     */
    public ClobTableEntity physicalSelectOnKey(Integer no) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/ClobTableDao/ClobTableDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, ClobTableEntity.class, no);
    }

    /**
     * 全て取得
     */
    public List<ClobTableEntity> selectAll() {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/ClobTableDao/ClobTableDao_select_all.sql");
        return executeQueryList(sql, ClobTableEntity.class);
    }

    /**
     * キーで1件取得
     */
    public ClobTableEntity selectOnKey(Integer no) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/ClobTableDao/ClobTableDao_select_on_key.sql");
        return executeQuerySingle(sql, ClobTableEntity.class, no);
    }

    /**
     * 登録(データを生で操作/DBの採番機能のカラムも自分でセット)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public ClobTableEntity rawPhysicalInsert(ClobTableEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/ClobTableDao/ClobTableDao_raw_insert.sql");
        executeUpdate(sql, entity.getNo(), entity.getContents(), entity.getInsertUser(), entity.getInsertDatetime(), entity.getUpdateUser(),
                entity.getUpdateDatetime());
        String driverClass = ConnectionManager.getInstance().getDriverClass(getConnectionName());
        if (ORMappingParameter.DRIVER_NAME_POSTGRESQL.equals(driverClass)) {
            String setValSql = "select setval('CLOB_TABLE_NO_seq', (select max(NO) from CLOB_TABLE));";
            executeQuerySingle(setValSql, Long.class);
        }
        return entity;
    }

    /**
     * 登録(データを生で操作)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public ClobTableEntity physicalInsert(ClobTableEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/ClobTableDao/ClobTableDao_insert.sql");
        Class<?> type = PropertyUtil.getPropertyType(entity, "no");
        Object key = executeInsert(sql, type, entity.getContents(), entity.getInsertUser(), entity.getInsertDatetime(), entity.getUpdateUser(),
                entity.getUpdateDatetime());
        PropertyUtil.setPropertyValue(entity, "no", key);
        return entity;
    }

    /**
     * 登録(登録ユーザを指定)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public ClobTableEntity insert(String user, ClobTableEntity entity) {
        return physicalInsert(entity);
    }

    /**
     * 登録
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public ClobTableEntity insert(ClobTableEntity entity) {
        return physicalInsert(entity);
    }

    /**
     * 更新(データを生で操作)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public ClobTableEntity physicalUpdate(ClobTableEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/ClobTableDao/ClobTableDao_update.sql");
        executeUpdate(sql, entity.getContents(), entity.getInsertUser(), entity.getInsertDatetime(), entity.getUpdateUser(),
                entity.getUpdateDatetime(), entity.getNo());
        return entity;
    }

    /**
     * 更新(更新ユーザを指定)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public ClobTableEntity update(String user, ClobTableEntity entity) {
        return physicalUpdate(entity);
    }

    /**
     * 更新
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public ClobTableEntity update(ClobTableEntity entity) {
        return physicalUpdate(entity);
    }

    /**
     * 保存(ユーザを指定)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public ClobTableEntity save(String user, ClobTableEntity entity) {
        ClobTableEntity db = selectOnKey(entity.getNo());
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
    public ClobTableEntity save(ClobTableEntity entity) {
        ClobTableEntity db = selectOnKey(entity.getNo());
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
    public void physicalDelete(Integer no) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/ClobTableDao/ClobTableDao_delete.sql");
        executeUpdate(sql, no);
    }

    /**
     * 削除(データを生で操作/物理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(ClobTableEntity entity) {
        physicalDelete(entity.getNo());

    }

    /**
     * 削除(削除ユーザを指定／論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String user, Integer no) {
        physicalDelete(no);
    }

    /**
     * 削除(論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer no) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        String user = (String) pool.getUser();
        delete(user, no);
    }

    /**
     * 削除(削除ユーザを指定／論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String user, ClobTableEntity entity) {
        delete(user, entity.getNo());

    }

    /**
     * 削除(論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(ClobTableEntity entity) {
        delete(entity.getNo());

    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String user, Integer no) {
        throw new ORMappingException("delete flag is not exists.");
    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer no) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        String user = (String) pool.getUser();
        activation(user, no);
    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String user, ClobTableEntity entity) {
        activation(user, entity.getNo());

    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(ClobTableEntity entity) {
        activation(entity.getNo());

    }

}
