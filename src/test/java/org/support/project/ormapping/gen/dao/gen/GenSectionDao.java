package org.support.project.ormapping.gen.dao.gen;

import java.util.List;

import java.sql.Timestamp;

import org.support.project.ormapping.gen.entity.SectionEntity;
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
 * 組織
 */
@DI(instance = Instance.Singleton)
public class GenSectionDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static GenSectionDao get() {
        return Container.getComp(GenSectionDao.class);
    }

    /**
     * 全て取得(削除フラグを無視して取得)
     */
    public List<SectionEntity> physicalSelectAll() {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/SectionDao/SectionDao_physical_select_all.sql");
        return executeQueryList(sql, SectionEntity.class);
    }

    /**
     * キーで1件取得(削除フラグを無視して取得)
     */
    public SectionEntity physicalSelectOnKey(String sectionCode) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/SectionDao/SectionDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, SectionEntity.class, sectionCode);
    }

    /**
     * 全て取得
     */
    public List<SectionEntity> selectAll() {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/SectionDao/SectionDao_select_all.sql");
        return executeQueryList(sql, SectionEntity.class);
    }

    /**
     * キーで1件取得
     */
    public SectionEntity selectOnKey(String sectionCode) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/SectionDao/SectionDao_select_on_key.sql");
        return executeQuerySingle(sql, SectionEntity.class, sectionCode);
    }

    /**
     * 登録(データを生で操作/DBの採番機能のカラムも自分でセット)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public SectionEntity rawPhysicalInsert(SectionEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/SectionDao/SectionDao_raw_insert.sql");
        executeUpdate(sql, entity.getSectionCode(), entity.getSectionName(), entity.getSectionSynonym(), entity.getCampanyCode(),
                entity.getInsertUser(), entity.getInsertDatetime(), entity.getUpdateUser(), entity.getUpdateDatetime());
        return entity;
    }

    /**
     * 登録(データを生で操作)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public SectionEntity physicalInsert(SectionEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/SectionDao/SectionDao_insert.sql");
        executeUpdate(sql, entity.getSectionCode(), entity.getSectionName(), entity.getSectionSynonym(), entity.getCampanyCode(),
                entity.getInsertUser(), entity.getInsertDatetime(), entity.getUpdateUser(), entity.getUpdateDatetime());
        return entity;
    }

    /**
     * 登録(登録ユーザを指定)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public SectionEntity insert(String user, SectionEntity entity) {
        return physicalInsert(entity);
    }

    /**
     * 登録
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public SectionEntity insert(SectionEntity entity) {
        return physicalInsert(entity);
    }

    /**
     * 更新(データを生で操作)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public SectionEntity physicalUpdate(SectionEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/SectionDao/SectionDao_update.sql");
        executeUpdate(sql, entity.getSectionName(), entity.getSectionSynonym(), entity.getCampanyCode(), entity.getInsertUser(),
                entity.getInsertDatetime(), entity.getUpdateUser(), entity.getUpdateDatetime(), entity.getSectionCode());
        return entity;
    }

    /**
     * 更新(更新ユーザを指定)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public SectionEntity update(String user, SectionEntity entity) {
        return physicalUpdate(entity);
    }

    /**
     * 更新
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public SectionEntity update(SectionEntity entity) {
        return physicalUpdate(entity);
    }

    /**
     * 保存(ユーザを指定)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public SectionEntity save(String user, SectionEntity entity) {
        SectionEntity db = selectOnKey(entity.getSectionCode());
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
    public SectionEntity save(SectionEntity entity) {
        SectionEntity db = selectOnKey(entity.getSectionCode());
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
    public void physicalDelete(String sectionCode) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/ormapping/gen/dao/sql/SectionDao/SectionDao_delete.sql");
        executeUpdate(sql, sectionCode);
    }

    /**
     * 削除(データを生で操作/物理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(SectionEntity entity) {
        physicalDelete(entity.getSectionCode());

    }

    /**
     * 削除(削除ユーザを指定／論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String user, String sectionCode) {
        physicalDelete(sectionCode);
    }

    /**
     * 削除(論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String sectionCode) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        String user = (String) pool.getUser();
        delete(user, sectionCode);
    }

    /**
     * 削除(削除ユーザを指定／論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String user, SectionEntity entity) {
        delete(user, entity.getSectionCode());

    }

    /**
     * 削除(論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(SectionEntity entity) {
        delete(entity.getSectionCode());

    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String user, String sectionCode) {
        throw new ORMappingException("delete flag is not exists.");
    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String sectionCode) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        String user = (String) pool.getUser();
        activation(user, sectionCode);
    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String user, SectionEntity entity) {
        activation(user, entity.getSectionCode());

    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(SectionEntity entity) {
        activation(entity.getSectionCode());

    }

}
