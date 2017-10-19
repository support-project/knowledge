package org.support.project.ormapping.gen.dao.gen;

import java.util.List;

import java.sql.Timestamp;

import org.support.project.ormapping.gen.entity.CampanyEntity;
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
 * 会社
 */
@DI(instance = Instance.Singleton)
public class GenCampanyDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static GenCampanyDao get() {
        return Container.getComp(GenCampanyDao.class);
    }

    /**
     * 全て取得(削除フラグを無視して取得)
     */
    public List<CampanyEntity> physicalSelectAll() {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/CampanyDao/CampanyDao_physical_select_all.sql");
        return executeQueryList(sql, CampanyEntity.class);
    }

    /**
     * キーで1件取得(削除フラグを無視して取得)
     */
    public CampanyEntity physicalSelectOnKey(String campanyCode) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/CampanyDao/CampanyDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, CampanyEntity.class, campanyCode);
    }

    /**
     * 全て取得
     */
    public List<CampanyEntity> selectAll() {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/CampanyDao/CampanyDao_select_all.sql");
        return executeQueryList(sql, CampanyEntity.class);
    }

    /**
     * キーで1件取得
     */
    public CampanyEntity selectOnKey(String campanyCode) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/CampanyDao/CampanyDao_select_on_key.sql");
        return executeQuerySingle(sql, CampanyEntity.class, campanyCode);
    }

    /**
     * 登録(データを生で操作/DBの採番機能のカラムも自分でセット)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public CampanyEntity rawPhysicalInsert(CampanyEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/CampanyDao/CampanyDao_raw_insert.sql");
        executeUpdate(sql, entity.getCampanyCode(), entity.getCampanyName(), entity.getAdress(), entity.getInsertUser(), entity.getInsertDatetime(),
                entity.getUpdateUser(), entity.getUpdateDatetime());
        return entity;
    }

    /**
     * 登録(データを生で操作)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public CampanyEntity physicalInsert(CampanyEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/CampanyDao/CampanyDao_insert.sql");
        executeUpdate(sql, entity.getCampanyCode(), entity.getCampanyName(), entity.getAdress(), entity.getInsertUser(), entity.getInsertDatetime(),
                entity.getUpdateUser(), entity.getUpdateDatetime());
        return entity;
    }

    /**
     * 登録(登録ユーザを指定)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public CampanyEntity insert(String user, CampanyEntity entity) {
        return physicalInsert(entity);
    }

    /**
     * 登録
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public CampanyEntity insert(CampanyEntity entity) {
        return physicalInsert(entity);
    }

    /**
     * 更新(データを生で操作)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public CampanyEntity physicalUpdate(CampanyEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/CampanyDao/CampanyDao_update.sql");
        executeUpdate(sql, entity.getCampanyName(), entity.getAdress(), entity.getInsertUser(), entity.getInsertDatetime(), entity.getUpdateUser(),
                entity.getUpdateDatetime(), entity.getCampanyCode());
        return entity;
    }

    /**
     * 更新(更新ユーザを指定)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public CampanyEntity update(String user, CampanyEntity entity) {
        return physicalUpdate(entity);
    }

    /**
     * 更新
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public CampanyEntity update(CampanyEntity entity) {
        return physicalUpdate(entity);
    }

    /**
     * 保存(ユーザを指定)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public CampanyEntity save(String user, CampanyEntity entity) {
        CampanyEntity db = selectOnKey(entity.getCampanyCode());
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
    public CampanyEntity save(CampanyEntity entity) {
        CampanyEntity db = selectOnKey(entity.getCampanyCode());
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
    public void physicalDelete(String campanyCode) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/CampanyDao/CampanyDao_delete.sql");
        executeUpdate(sql, campanyCode);
    }

    /**
     * 削除(データを生で操作/物理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(CampanyEntity entity) {
        physicalDelete(entity.getCampanyCode());

    }

    /**
     * 削除(削除ユーザを指定／論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String user, String campanyCode) {
        physicalDelete(campanyCode);
    }

    /**
     * 削除(論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String campanyCode) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        String user = (String) pool.getUser();
        delete(user, campanyCode);
    }

    /**
     * 削除(削除ユーザを指定／論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String user, CampanyEntity entity) {
        delete(user, entity.getCampanyCode());

    }

    /**
     * 削除(論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(CampanyEntity entity) {
        delete(entity.getCampanyCode());

    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String user, String campanyCode) {
        throw new ORMappingException("delete flag is not exists.");
    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String campanyCode) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        String user = (String) pool.getUser();
        activation(user, campanyCode);
    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String user, CampanyEntity entity) {
        activation(user, entity.getCampanyCode());

    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(CampanyEntity entity) {
        activation(entity.getCampanyCode());

    }

}
