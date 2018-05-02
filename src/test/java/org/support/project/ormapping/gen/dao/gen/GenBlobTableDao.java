package org.support.project.ormapping.gen.dao.gen;

import java.util.List;

import java.io.InputStream;
import java.sql.Timestamp;

import org.support.project.ormapping.gen.entity.BlobTableEntity;
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
 * BLOBのテストテーブル
 */
@DI(instance = Instance.Singleton)
public class GenBlobTableDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static GenBlobTableDao get() {
        return Container.getComp(GenBlobTableDao.class);
    }

    /**
     * 全て取得(削除フラグを無視して取得)
     */
    public List<BlobTableEntity> physicalSelectAll() {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/BlobTableDao/BlobTableDao_physical_select_all.sql");
        return executeQueryList(sql, BlobTableEntity.class);
    }

    /**
     * キーで1件取得(削除フラグを無視して取得)
     */
    public BlobTableEntity physicalSelectOnKey(Long no) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/BlobTableDao/BlobTableDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, BlobTableEntity.class, no);
    }

    /**
     * 全て取得
     */
    public List<BlobTableEntity> selectAll() {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/BlobTableDao/BlobTableDao_select_all.sql");
        return executeQueryList(sql, BlobTableEntity.class);
    }

    /**
     * キーで1件取得
     */
    public BlobTableEntity selectOnKey(Long no) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/BlobTableDao/BlobTableDao_select_on_key.sql");
        return executeQuerySingle(sql, BlobTableEntity.class, no);
    }

    /**
     * 登録(データを生で操作/DBの採番機能のカラムも自分でセット)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public BlobTableEntity rawPhysicalInsert(BlobTableEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/BlobTableDao/BlobTableDao_raw_insert.sql");
        executeUpdate(sql, entity.getNo(), entity.getBlob(), entity.getInsertUser(), entity.getInsertDatetime(), entity.getUpdateUser(),
                entity.getUpdateDatetime());
        String driverClass = ConnectionManager.getInstance().getDriverClass(getConnectionName());
        if (ORMappingParameter.DRIVER_NAME_POSTGRESQL.equals(driverClass)) {
            String setValSql = "select setval('BLOB_TABLE_NO_seq', (select max(NO) from BLOB_TABLE));";
            executeQuerySingle(setValSql, Long.class);
        }
        return entity;
    }

    /**
     * 登録(データを生で操作)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public BlobTableEntity physicalInsert(BlobTableEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/BlobTableDao/BlobTableDao_insert.sql");
        Class<?> type = PropertyUtil.getPropertyType(entity, "no");
        Object key = executeInsert(sql, type, entity.getBlob(), entity.getInsertUser(), entity.getInsertDatetime(), entity.getUpdateUser(),
                entity.getUpdateDatetime());
        PropertyUtil.setPropertyValue(entity, "no", key);
        return entity;
    }

    /**
     * 登録(登録ユーザを指定)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public BlobTableEntity insert(String user, BlobTableEntity entity) {
        return physicalInsert(entity);
    }

    /**
     * 登録
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public BlobTableEntity insert(BlobTableEntity entity) {
        return physicalInsert(entity);
    }

    /**
     * 更新(データを生で操作)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public BlobTableEntity physicalUpdate(BlobTableEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/BlobTableDao/BlobTableDao_update.sql");
        executeUpdate(sql, entity.getBlob(), entity.getInsertUser(), entity.getInsertDatetime(), entity.getUpdateUser(), entity.getUpdateDatetime(),
                entity.getNo());
        return entity;
    }

    /**
     * 更新(更新ユーザを指定)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public BlobTableEntity update(String user, BlobTableEntity entity) {
        return physicalUpdate(entity);
    }

    /**
     * 更新
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public BlobTableEntity update(BlobTableEntity entity) {
        return physicalUpdate(entity);
    }

    /**
     * 保存(ユーザを指定)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public BlobTableEntity save(String user, BlobTableEntity entity) {
        BlobTableEntity db = selectOnKey(entity.getNo());
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
    public BlobTableEntity save(BlobTableEntity entity) {
        BlobTableEntity db = selectOnKey(entity.getNo());
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
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/BlobTableDao/BlobTableDao_delete.sql");
        executeUpdate(sql, no);
    }

    /**
     * 削除(データを生で操作/物理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(BlobTableEntity entity) {
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
    public void delete(String user, BlobTableEntity entity) {
        delete(user, entity.getNo());

    }

    /**
     * 削除(論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(BlobTableEntity entity) {
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
    public void activation(String user, BlobTableEntity entity) {
        activation(user, entity.getNo());

    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(BlobTableEntity entity) {
        activation(entity.getNo());

    }

}
