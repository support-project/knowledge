package org.support.project.ormapping.gen.dao.gen;

import java.util.List;

import java.sql.Timestamp;

import org.support.project.ormapping.gen.entity.GroupUserRelationshipEntity;
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
 * 所属
 */
@DI(instance = Instance.Singleton)
public class GenGroupUserRelationshipDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static GenGroupUserRelationshipDao get() {
        return Container.getComp(GenGroupUserRelationshipDao.class);
    }

    /**
     * 全て取得(削除フラグを無視して取得)
     */
    public List<GroupUserRelationshipEntity> physicalSelectAll() {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/GroupUserRelationshipDao/GroupUserRelationshipDao_physical_select_all.sql");
        return executeQueryList(sql, GroupUserRelationshipEntity.class);
    }

    /**
     * キーで1件取得(削除フラグを無視して取得)
     */
    public GroupUserRelationshipEntity physicalSelectOnKey(String employeeId, String sectionCode) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/GroupUserRelationshipDao/GroupUserRelationshipDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, GroupUserRelationshipEntity.class, employeeId, sectionCode);
    }

    /**
     * 全て取得
     */
    public List<GroupUserRelationshipEntity> selectAll() {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/GroupUserRelationshipDao/GroupUserRelationshipDao_select_all.sql");
        return executeQueryList(sql, GroupUserRelationshipEntity.class);
    }

    /**
     * キーで1件取得
     */
    public GroupUserRelationshipEntity selectOnKey(String employeeId, String sectionCode) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/GroupUserRelationshipDao/GroupUserRelationshipDao_select_on_key.sql");
        return executeQuerySingle(sql, GroupUserRelationshipEntity.class, employeeId, sectionCode);
    }

    /**
     * EMPLOYEE_ID でリストを取得
     */
    public List<GroupUserRelationshipEntity> selectOnEmployeeId(String employeeId) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/GroupUserRelationshipDao/GroupUserRelationshipDao_select_on_employee_id.sql");
        return executeQueryList(sql, GroupUserRelationshipEntity.class, employeeId);
    }

    /**
     * SECTION_CODE でリストを取得
     */
    public List<GroupUserRelationshipEntity> selectOnSectionCode(String sectionCode) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/GroupUserRelationshipDao/GroupUserRelationshipDao_select_on_section_code.sql");
        return executeQueryList(sql, GroupUserRelationshipEntity.class, sectionCode);
    }

    /**
     * EMPLOYEE_ID でリストを取得
     */
    public List<GroupUserRelationshipEntity> physicalSelectOnEmployeeId(String employeeId) {
        String sql = SQLManager.getInstance().getSql(
                "/org/support/project/ormapping/gen/dao/sql/GroupUserRelationshipDao/GroupUserRelationshipDao_physical_select_on_employee_id.sql");
        return executeQueryList(sql, GroupUserRelationshipEntity.class, employeeId);
    }

    /**
     * SECTION_CODE でリストを取得
     */
    public List<GroupUserRelationshipEntity> physicalSelectOnSectionCode(String sectionCode) {
        String sql = SQLManager.getInstance().getSql(
                "/org/support/project/ormapping/gen/dao/sql/GroupUserRelationshipDao/GroupUserRelationshipDao_physical_select_on_section_code.sql");
        return executeQueryList(sql, GroupUserRelationshipEntity.class, sectionCode);
    }

    /**
     * 登録(データを生で操作/DBの採番機能のカラムも自分でセット)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public GroupUserRelationshipEntity rawPhysicalInsert(GroupUserRelationshipEntity entity) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/GroupUserRelationshipDao/GroupUserRelationshipDao_raw_insert.sql");
        executeUpdate(sql, entity.getEmployeeId(), entity.getSectionCode(), entity.getRoleId(), entity.getInsertUser(), entity.getInsertDatetime(),
                entity.getUpdateUser(), entity.getUpdateDatetime());
        return entity;
    }

    /**
     * 登録(データを生で操作)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public GroupUserRelationshipEntity physicalInsert(GroupUserRelationshipEntity entity) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/GroupUserRelationshipDao/GroupUserRelationshipDao_insert.sql");
        executeUpdate(sql, entity.getEmployeeId(), entity.getSectionCode(), entity.getRoleId(), entity.getInsertUser(), entity.getInsertDatetime(),
                entity.getUpdateUser(), entity.getUpdateDatetime());
        return entity;
    }

    /**
     * 登録(登録ユーザを指定)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public GroupUserRelationshipEntity insert(String user, GroupUserRelationshipEntity entity) {
        return physicalInsert(entity);
    }

    /**
     * 登録
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public GroupUserRelationshipEntity insert(GroupUserRelationshipEntity entity) {
        return physicalInsert(entity);
    }

    /**
     * 更新(データを生で操作)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public GroupUserRelationshipEntity physicalUpdate(GroupUserRelationshipEntity entity) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/GroupUserRelationshipDao/GroupUserRelationshipDao_update.sql");
        executeUpdate(sql, entity.getRoleId(), entity.getInsertUser(), entity.getInsertDatetime(), entity.getUpdateUser(), entity.getUpdateDatetime(),
                entity.getEmployeeId(), entity.getSectionCode());
        return entity;
    }

    /**
     * 更新(更新ユーザを指定)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public GroupUserRelationshipEntity update(String user, GroupUserRelationshipEntity entity) {
        return physicalUpdate(entity);
    }

    /**
     * 更新
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public GroupUserRelationshipEntity update(GroupUserRelationshipEntity entity) {
        return physicalUpdate(entity);
    }

    /**
     * 保存(ユーザを指定)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public GroupUserRelationshipEntity save(String user, GroupUserRelationshipEntity entity) {
        GroupUserRelationshipEntity db = selectOnKey(entity.getEmployeeId(), entity.getSectionCode());
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
    public GroupUserRelationshipEntity save(GroupUserRelationshipEntity entity) {
        GroupUserRelationshipEntity db = selectOnKey(entity.getEmployeeId(), entity.getSectionCode());
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
    public void physicalDelete(String employeeId, String sectionCode) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/ormapping/gen/dao/sql/GroupUserRelationshipDao/GroupUserRelationshipDao_delete.sql");
        executeUpdate(sql, employeeId, sectionCode);
    }

    /**
     * 削除(データを生で操作/物理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(GroupUserRelationshipEntity entity) {
        physicalDelete(entity.getEmployeeId(), entity.getSectionCode());

    }

    /**
     * 削除(削除ユーザを指定／論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String user, String employeeId, String sectionCode) {
        physicalDelete(employeeId, sectionCode);
    }

    /**
     * 削除(論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String employeeId, String sectionCode) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        String user = (String) pool.getUser();
        delete(user, employeeId, sectionCode);
    }

    /**
     * 削除(削除ユーザを指定／論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String user, GroupUserRelationshipEntity entity) {
        delete(user, entity.getEmployeeId(), entity.getSectionCode());

    }

    /**
     * 削除(論理削除があれば論理削除)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(GroupUserRelationshipEntity entity) {
        delete(entity.getEmployeeId(), entity.getSectionCode());

    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String user, String employeeId, String sectionCode) {
        throw new ORMappingException("delete flag is not exists.");
    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String employeeId, String sectionCode) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        String user = (String) pool.getUser();
        activation(user, employeeId, sectionCode);
    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String user, GroupUserRelationshipEntity entity) {
        activation(user, entity.getEmployeeId(), entity.getSectionCode());

    }

    /**
     * 復元(論理削除されていたものを有効化)
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(GroupUserRelationshipEntity entity) {
        activation(entity.getEmployeeId(), entity.getSectionCode());

    }

}
