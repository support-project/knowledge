package org.support.project.ormapping.gen.dao.gen;

import java.util.List;

import java.sql.Timestamp;

import org.support.project.ormapping.gen.entity.GroupRelationshipEntity;
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
 * グループの親子関係
 */
@DI(instance = Instance.Singleton)
public class GenGroupRelationshipDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static GenGroupRelationshipDao get() {
        return Container.getComp(GenGroupRelationshipDao.class);
    }

    /**
     * 全て取得(削除フラグを無視して取得)
     */
    public List<GroupRelationshipEntity> physicalSelectAll() {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/GroupRelationshipDao/GroupRelationshipDao_physical_select_all.sql");
        return executeQueryList(sql, GroupRelationshipEntity.class);
    }

    /**
     * キーで1件取得(削除フラグを無視して取得)
     */
    public GroupRelationshipEntity physicalSelectOnKey(String childSectionCode, String parentSectionCode) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/GroupRelationshipDao/GroupRelationshipDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, GroupRelationshipEntity.class, childSectionCode, parentSectionCode);
    }

    /**
     * 全て取得
     */
    public List<GroupRelationshipEntity> selectAll() {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/GroupRelationshipDao/GroupRelationshipDao_select_all.sql");
        return executeQueryList(sql, GroupRelationshipEntity.class);
    }

    /**
     * キーで1件取得
     */
    public GroupRelationshipEntity selectOnKey(String childSectionCode, String parentSectionCode) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/GroupRelationshipDao/GroupRelationshipDao_select_on_key.sql");
        return executeQuerySingle(sql, GroupRelationshipEntity.class, childSectionCode, parentSectionCode);
    }

    /**
     * CHILD_SECTION_CODE でリストを取得
     */
    public List<GroupRelationshipEntity> selectOnChildSectionCode(String childSectionCode) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/GroupRelationshipDao/GroupRelationshipDao_select_on_child_section_code.sql");
        return executeQueryList(sql, GroupRelationshipEntity.class, childSectionCode);
    }

    /**
     * PARENT_SECTION_CODE でリストを取得
     */
    public List<GroupRelationshipEntity> selectOnParentSectionCode(String parentSectionCode) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/GroupRelationshipDao/GroupRelationshipDao_select_on_parent_section_code.sql");
        return executeQueryList(sql, GroupRelationshipEntity.class, parentSectionCode);
    }

    /**
     * CHILD_SECTION_CODE でリストを取得
     */
    public List<GroupRelationshipEntity> physicalSelectOnChildSectionCode(String childSectionCode) {
        String sql = SQLManager.getInstance().getSql(
                "/org/support/project/ormapping/gen/dao/sql/GroupRelationshipDao/GroupRelationshipDao_physical_select_on_child_section_code.sql");
        return executeQueryList(sql, GroupRelationshipEntity.class, childSectionCode);
    }

    /**
     * PARENT_SECTION_CODE でリストを取得
     */
    public List<GroupRelationshipEntity> physicalSelectOnParentSectionCode(String parentSectionCode) {
        String sql = SQLManager.getInstance().getSql(
                "/org/support/project/ormapping/gen/dao/sql/GroupRelationshipDao/GroupRelationshipDao_physical_select_on_parent_section_code.sql");
        return executeQueryList(sql, GroupRelationshipEntity.class, parentSectionCode);
    }

    /**
     * 登録(データを生で操作/DBの採番機能のカラムも自分でセット)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public GroupRelationshipEntity rawPhysicalInsert(GroupRelationshipEntity entity) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/GroupRelationshipDao/GroupRelationshipDao_raw_insert.sql");
        executeUpdate(sql, entity.getChildSectionCode(), entity.getParentSectionCode(), entity.getInsertUser(), entity.getInsertDatetime(),
                entity.getUpdateUser(), entity.getUpdateDatetime());
        return entity;
    }

    /**
     * 登録(データを生で操作)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public GroupRelationshipEntity physicalInsert(GroupRelationshipEntity entity) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/GroupRelationshipDao/GroupRelationshipDao_insert.sql");
        executeUpdate(sql, entity.getChildSectionCode(), entity.getParentSectionCode(), entity.getInsertUser(), entity.getInsertDatetime(),
                entity.getUpdateUser(), entity.getUpdateDatetime());
        return entity;
    }

    /**
     * 登録(登録ユーザを指定)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public GroupRelationshipEntity insert(String user, GroupRelationshipEntity entity) {
        return physicalInsert(entity);
    }

    /**
     * 登録
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public GroupRelationshipEntity insert(GroupRelationshipEntity entity) {
        return physicalInsert(entity);
    }

    /**
     * 更新(データを生で操作)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public GroupRelationshipEntity physicalUpdate(GroupRelationshipEntity entity) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/GroupRelationshipDao/GroupRelationshipDao_update.sql");
        executeUpdate(sql, entity.getInsertUser(), entity.getInsertDatetime(), entity.getUpdateUser(), entity.getUpdateDatetime(),
                entity.getChildSectionCode(), entity.getParentSectionCode());
        return entity;
    }

    /**
     * 更新(更新ユーザを指定)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public GroupRelationshipEntity update(String user, GroupRelationshipEntity entity) {
        return physicalUpdate(entity);
    }

    /**
     * 更新
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public GroupRelationshipEntity update(GroupRelationshipEntity entity) {
        return physicalUpdate(entity);
    }

    /**
     * 保存(ユーザを指定)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public GroupRelationshipEntity save(String user, GroupRelationshipEntity entity) {
        GroupRelationshipEntity db = selectOnKey(entity.getChildSectionCode(), entity.getParentSectionCode());
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
    public GroupRelationshipEntity save(GroupRelationshipEntity entity) {
        GroupRelationshipEntity db = selectOnKey(entity.getChildSectionCode(), entity.getParentSectionCode());
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
    public void physicalDelete(String childSectionCode, String parentSectionCode) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/GroupRelationshipDao/GroupRelationshipDao_delete.sql");
        executeUpdate(sql, childSectionCode, parentSectionCode);
    }

    /**
     * 削除(データを生で操作/物理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(GroupRelationshipEntity entity) {
        physicalDelete(entity.getChildSectionCode(), entity.getParentSectionCode());

    }

    /**
     * 削除(削除ユーザを指定／論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String user, String childSectionCode, String parentSectionCode) {
        physicalDelete(childSectionCode, parentSectionCode);
    }

    /**
     * 削除(論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String childSectionCode, String parentSectionCode) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        String user = (String) pool.getUser();
        delete(user, childSectionCode, parentSectionCode);
    }

    /**
     * 削除(削除ユーザを指定／論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String user, GroupRelationshipEntity entity) {
        delete(user, entity.getChildSectionCode(), entity.getParentSectionCode());

    }

    /**
     * 削除(論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(GroupRelationshipEntity entity) {
        delete(entity.getChildSectionCode(), entity.getParentSectionCode());

    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String user, String childSectionCode, String parentSectionCode) {
        throw new ORMappingException("delete flag is not exists.");
    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String childSectionCode, String parentSectionCode) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        String user = (String) pool.getUser();
        activation(user, childSectionCode, parentSectionCode);
    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String user, GroupRelationshipEntity entity) {
        activation(user, entity.getChildSectionCode(), entity.getParentSectionCode());

    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(GroupRelationshipEntity entity) {
        activation(entity.getChildSectionCode(), entity.getParentSectionCode());

    }

}
