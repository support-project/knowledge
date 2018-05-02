package org.support.project.web.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.web.entity.RoleFunctionsEntity;
import org.support.project.ormapping.dao.AbstractDao;
import org.support.project.ormapping.exception.ORMappingException;
import org.support.project.ormapping.common.SQLManager;
import org.support.project.ormapping.common.DBUserPool;
import org.support.project.ormapping.common.IDGen;
import org.support.project.ormapping.config.ORMappingParameter;
import org.support.project.ormapping.config.Order;
import org.support.project.ormapping.connection.ConnectionManager;
import org.support.project.common.util.PropertyUtil;
import org.support.project.common.util.DateUtils;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.aop.Aspect;

/**
 * 機能にアクセスできる権限
 * this class is auto generate and not edit.
 * if modify dao method, you can edit RoleFunctionsDao.
 */
@DI(instance = Instance.Singleton)
public class GenRoleFunctionsDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenRoleFunctionsDao get() {
        return Container.getComp(GenRoleFunctionsDao.class);
    }

    /**
     * Select all data.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<RoleFunctionsEntity> physicalSelectAll() { 
        return physicalSelectAll(Order.DESC);
    }
    /**
     * Select all data.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<RoleFunctionsEntity> physicalSelectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/RoleFunctionsDao/RoleFunctionsDao_physical_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, RoleFunctionsEntity.class);
    }
    /**
     * Select all data with pager.
     * @param limit limit
     * @param offset offset
     * @return all data on limit and offset
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<RoleFunctionsEntity> physicalSelectAllWithPager(int limit, int offset) { 
        return physicalSelectAllWithPager(limit, offset, Order.DESC);
    }
    /**
     * Select all data with pager.
     * @param limit limit
     * @param offset offset
     * @param order order
     * @return all data on limit and offset
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<RoleFunctionsEntity> physicalSelectAllWithPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/RoleFunctionsDao/RoleFunctionsDao_physical_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, RoleFunctionsEntity.class, limit, offset);
    }
    /**
     * Select data on key.
     * @param  functionKey functionKey
     * @param  roleId roleId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public RoleFunctionsEntity physicalSelectOnKey(String functionKey, Integer roleId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/RoleFunctionsDao/RoleFunctionsDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, RoleFunctionsEntity.class, functionKey, roleId);
    }
    /**
     * Select all data that not deleted.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<RoleFunctionsEntity> selectAll() { 
        return selectAll(Order.DESC);
    }
    /**
     * Select all data that not deleted.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<RoleFunctionsEntity> selectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/RoleFunctionsDao/RoleFunctionsDao_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, RoleFunctionsEntity.class);
    }
    /**
     * Select all data that not deleted with pager.
     * @param limit limit
     * @param offset offset
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<RoleFunctionsEntity> selectAllWidthPager(int limit, int offset) { 
        return selectAllWidthPager(limit, offset, Order.DESC);
    }
    /**
     * Select all data that not deleted with pager.
     * @param limit limit
     * @param offset offset
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<RoleFunctionsEntity> selectAllWidthPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/RoleFunctionsDao/RoleFunctionsDao_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, RoleFunctionsEntity.class, limit, offset);
    }
    /**
     * Select count that not deleted.
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer selectCountAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/RoleFunctionsDao/RoleFunctionsDao_select_count_all.sql");
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Select data that not deleted on key.
     * @param  functionKey functionKey
     * @param  roleId roleId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public RoleFunctionsEntity selectOnKey(String functionKey, Integer roleId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/RoleFunctionsDao/RoleFunctionsDao_select_on_key.sql");
        return executeQuerySingle(sql, RoleFunctionsEntity.class, functionKey, roleId);
    }
    /**
     * Select data that not deleted on FUNCTION_KEY column.
     * @param functionKey functionKey
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<RoleFunctionsEntity> selectOnFunctionKey(String functionKey) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/RoleFunctionsDao/RoleFunctionsDao_select_on_function_key.sql");
        return executeQueryList(sql, RoleFunctionsEntity.class, functionKey);
    }
    /**
     * Select data that not deleted on ROLE_ID column.
     * @param roleId roleId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<RoleFunctionsEntity> selectOnRoleId(Integer roleId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/RoleFunctionsDao/RoleFunctionsDao_select_on_role_id.sql");
        return executeQueryList(sql, RoleFunctionsEntity.class, roleId);
    }
    /**
     * Select data on FUNCTION_KEY column.
     * @param functionKey functionKey
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<RoleFunctionsEntity> physicalSelectOnFunctionKey(String functionKey) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/RoleFunctionsDao/RoleFunctionsDao_physical_select_on_function_key.sql");
        return executeQueryList(sql, RoleFunctionsEntity.class, functionKey);
    }
    /**
     * Select data on ROLE_ID column.
     * @param roleId roleId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<RoleFunctionsEntity> physicalSelectOnRoleId(Integer roleId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/RoleFunctionsDao/RoleFunctionsDao_physical_select_on_role_id.sql");
        return executeQueryList(sql, RoleFunctionsEntity.class, roleId);
    }
    /**
     * Count all data
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int physicalCountAll() {
        String sql = "SELECT COUNT(*) FROM ROLE_FUNCTIONS";
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Create row id.
     * @return row id
     */
    protected String createRowId() {
        return IDGen.get().gen("ROLE_FUNCTIONS");
    }
    /**
     * Physical Insert.
     * it is not create key on database sequence.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public RoleFunctionsEntity rawPhysicalInsert(RoleFunctionsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/RoleFunctionsDao/RoleFunctionsDao_raw_insert.sql");
        executeUpdate(sql, 
            entity.getFunctionKey(), 
            entity.getRoleId(), 
            entity.getRowId(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag());
        return entity;
    }
    /**
     * Physical Insert.
     * if key column have sequence, key value create by database.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public RoleFunctionsEntity physicalInsert(RoleFunctionsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/RoleFunctionsDao/RoleFunctionsDao_insert.sql");
        executeUpdate(sql, 
            entity.getFunctionKey(), 
            entity.getRoleId(), 
            entity.getRowId(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag());
        return entity;
    }
    /**
     * Insert.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public RoleFunctionsEntity insert(Integer user, RoleFunctionsEntity entity) {
        entity.setInsertUser(user);
        entity.setInsertDatetime(new Timestamp(DateUtils.now().getTime()));
        entity.setUpdateUser(user);
        entity.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        entity.setDeleteFlag(0);
        entity.setRowId(createRowId());
        return physicalInsert(entity);
    }
    /**
     * Insert.
     * saved user id is auto set.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public RoleFunctionsEntity insert(RoleFunctionsEntity entity) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer userId = (Integer) pool.getUser();
        return insert(userId, entity);
    }
    /**
     * Physical Update.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public RoleFunctionsEntity physicalUpdate(RoleFunctionsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/RoleFunctionsDao/RoleFunctionsDao_update.sql");
        executeUpdate(sql, 
            entity.getRowId(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag(), 
            entity.getFunctionKey(), 
            entity.getRoleId());
        return entity;
    }
    /**
     * Update.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public RoleFunctionsEntity update(Integer user, RoleFunctionsEntity entity) {
        RoleFunctionsEntity db = selectOnKey(entity.getFunctionKey(), entity.getRoleId());
        entity.setInsertUser(db.getInsertUser());
        entity.setInsertDatetime(db.getInsertDatetime());
        entity.setDeleteFlag(db.getDeleteFlag());
        entity.setUpdateUser(user);
        entity.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        return physicalUpdate(entity);
    }
    /**
     * Update.
     * saved user id is auto set.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public RoleFunctionsEntity update(RoleFunctionsEntity entity) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer userId = (Integer) pool.getUser();
        return update(userId, entity);
    }
    /**
     * Save. 
     * if same key data is exists, the data is update. otherwise the data is insert.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public RoleFunctionsEntity save(Integer user, RoleFunctionsEntity entity) {
        RoleFunctionsEntity db = selectOnKey(entity.getFunctionKey(), entity.getRoleId());
        if (db == null) {
            return insert(user, entity);
        } else {
            return update(user, entity);
        }
    }
    /**
     * Save. 
     * if same key data is exists, the data is update. otherwise the data is insert.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public RoleFunctionsEntity save(RoleFunctionsEntity entity) {
        RoleFunctionsEntity db = selectOnKey(entity.getFunctionKey(), entity.getRoleId());
        if (db == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }
    /**
     * Physical Delete.
     * @param  functionKey functionKey
     * @param  roleId roleId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(String functionKey, Integer roleId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/RoleFunctionsDao/RoleFunctionsDao_delete.sql");
        executeUpdate(sql, functionKey, roleId);
    }
    /**
     * Physical Delete.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(RoleFunctionsEntity entity) {
        physicalDelete(entity.getFunctionKey(), entity.getRoleId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param  functionKey functionKey
     * @param  roleId roleId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, String functionKey, Integer roleId) {
        RoleFunctionsEntity db = selectOnKey(functionKey, roleId);
        db.setDeleteFlag(1);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * @param  functionKey functionKey
     * @param  roleId roleId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String functionKey, Integer roleId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        delete(user, functionKey, roleId);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, RoleFunctionsEntity entity) {
        delete(user, entity.getFunctionKey(), entity.getRoleId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(RoleFunctionsEntity entity) {
        delete(entity.getFunctionKey(), entity.getRoleId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param  functionKey functionKey
     * @param  roleId roleId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, String functionKey, Integer roleId) {
        RoleFunctionsEntity db = physicalSelectOnKey(functionKey, roleId);
        db.setDeleteFlag(0);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param  functionKey functionKey
     * @param  roleId roleId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String functionKey, Integer roleId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        activation(user, functionKey, roleId);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, RoleFunctionsEntity entity) {
        activation(user, entity.getFunctionKey(), entity.getRoleId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(RoleFunctionsEntity entity) {
        activation(entity.getFunctionKey(), entity.getRoleId());

    }

}
