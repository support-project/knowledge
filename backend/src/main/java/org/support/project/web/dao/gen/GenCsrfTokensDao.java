package org.support.project.web.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.web.entity.CsrfTokensEntity;
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
 * CSRF_TOKENS
 * this class is auto generate and not edit.
 * if modify dao method, you can edit CsrfTokensDao.
 */
@DI(instance = Instance.Singleton)
public class GenCsrfTokensDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenCsrfTokensDao get() {
        return Container.getComp(GenCsrfTokensDao.class);
    }

    /**
     * Select all data.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<CsrfTokensEntity> physicalSelectAll() { 
        return physicalSelectAll(Order.DESC);
    }
    /**
     * Select all data.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<CsrfTokensEntity> physicalSelectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/CsrfTokensDao/CsrfTokensDao_physical_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, CsrfTokensEntity.class);
    }
    /**
     * Select all data with pager.
     * @param limit limit
     * @param offset offset
     * @return all data on limit and offset
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<CsrfTokensEntity> physicalSelectAllWithPager(int limit, int offset) { 
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
    public List<CsrfTokensEntity> physicalSelectAllWithPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/CsrfTokensDao/CsrfTokensDao_physical_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, CsrfTokensEntity.class, limit, offset);
    }
    /**
     * Select data on key.
     * @param  processName processName
     * @param  userId userId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public CsrfTokensEntity physicalSelectOnKey(String processName, Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/CsrfTokensDao/CsrfTokensDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, CsrfTokensEntity.class, processName, userId);
    }
    /**
     * Select all data that not deleted.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<CsrfTokensEntity> selectAll() { 
        return selectAll(Order.DESC);
    }
    /**
     * Select all data that not deleted.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<CsrfTokensEntity> selectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/CsrfTokensDao/CsrfTokensDao_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, CsrfTokensEntity.class);
    }
    /**
     * Select all data that not deleted with pager.
     * @param limit limit
     * @param offset offset
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<CsrfTokensEntity> selectAllWidthPager(int limit, int offset) { 
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
    public List<CsrfTokensEntity> selectAllWidthPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/CsrfTokensDao/CsrfTokensDao_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, CsrfTokensEntity.class, limit, offset);
    }
    /**
     * Select count that not deleted.
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer selectCountAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/CsrfTokensDao/CsrfTokensDao_select_count_all.sql");
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Select data that not deleted on key.
     * @param  processName processName
     * @param  userId userId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public CsrfTokensEntity selectOnKey(String processName, Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/CsrfTokensDao/CsrfTokensDao_select_on_key.sql");
        return executeQuerySingle(sql, CsrfTokensEntity.class, processName, userId);
    }
    /**
     * Select data that not deleted on PROCESS_NAME column.
     * @param processName processName
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<CsrfTokensEntity> selectOnProcessName(String processName) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/CsrfTokensDao/CsrfTokensDao_select_on_process_name.sql");
        return executeQueryList(sql, CsrfTokensEntity.class, processName);
    }
    /**
     * Select data that not deleted on USER_ID column.
     * @param userId userId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<CsrfTokensEntity> selectOnUserId(Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/CsrfTokensDao/CsrfTokensDao_select_on_user_id.sql");
        return executeQueryList(sql, CsrfTokensEntity.class, userId);
    }
    /**
     * Select data on PROCESS_NAME column.
     * @param processName processName
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<CsrfTokensEntity> physicalSelectOnProcessName(String processName) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/CsrfTokensDao/CsrfTokensDao_physical_select_on_process_name.sql");
        return executeQueryList(sql, CsrfTokensEntity.class, processName);
    }
    /**
     * Select data on USER_ID column.
     * @param userId userId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<CsrfTokensEntity> physicalSelectOnUserId(Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/CsrfTokensDao/CsrfTokensDao_physical_select_on_user_id.sql");
        return executeQueryList(sql, CsrfTokensEntity.class, userId);
    }
    /**
     * Count all data
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int physicalCountAll() {
        String sql = "SELECT COUNT(*) FROM CSRF_TOKENS";
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Create row id.
     * @return row id
     */
    protected String createRowId() {
        return IDGen.get().gen("CSRF_TOKENS");
    }
    /**
     * Physical Insert.
     * it is not create key on database sequence.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public CsrfTokensEntity rawPhysicalInsert(CsrfTokensEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/CsrfTokensDao/CsrfTokensDao_raw_insert.sql");
        executeUpdate(sql, 
            entity.getProcessName(), 
            entity.getUserId(), 
            entity.getToken(), 
            entity.getExpires(), 
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
    public CsrfTokensEntity physicalInsert(CsrfTokensEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/CsrfTokensDao/CsrfTokensDao_insert.sql");
        executeUpdate(sql, 
            entity.getProcessName(), 
            entity.getUserId(), 
            entity.getToken(), 
            entity.getExpires(), 
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
    public CsrfTokensEntity insert(Integer user, CsrfTokensEntity entity) {
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
    public CsrfTokensEntity insert(CsrfTokensEntity entity) {
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
    public CsrfTokensEntity physicalUpdate(CsrfTokensEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/CsrfTokensDao/CsrfTokensDao_update.sql");
        executeUpdate(sql, 
            entity.getToken(), 
            entity.getExpires(), 
            entity.getRowId(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag(), 
            entity.getProcessName(), 
            entity.getUserId());
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
    public CsrfTokensEntity update(Integer user, CsrfTokensEntity entity) {
        CsrfTokensEntity db = selectOnKey(entity.getProcessName(), entity.getUserId());
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
    public CsrfTokensEntity update(CsrfTokensEntity entity) {
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
    public CsrfTokensEntity save(Integer user, CsrfTokensEntity entity) {
        CsrfTokensEntity db = selectOnKey(entity.getProcessName(), entity.getUserId());
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
    public CsrfTokensEntity save(CsrfTokensEntity entity) {
        CsrfTokensEntity db = selectOnKey(entity.getProcessName(), entity.getUserId());
        if (db == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }
    /**
     * Physical Delete.
     * @param  processName processName
     * @param  userId userId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(String processName, Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/CsrfTokensDao/CsrfTokensDao_delete.sql");
        executeUpdate(sql, processName, userId);
    }
    /**
     * Physical Delete.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(CsrfTokensEntity entity) {
        physicalDelete(entity.getProcessName(), entity.getUserId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param  processName processName
     * @param  userId userId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, String processName, Integer userId) {
        CsrfTokensEntity db = selectOnKey(processName, userId);
        db.setDeleteFlag(1);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * @param  processName processName
     * @param  userId userId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String processName, Integer userId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        delete(user, processName, userId);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, CsrfTokensEntity entity) {
        delete(user, entity.getProcessName(), entity.getUserId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(CsrfTokensEntity entity) {
        delete(entity.getProcessName(), entity.getUserId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param  processName processName
     * @param  userId userId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, String processName, Integer userId) {
        CsrfTokensEntity db = physicalSelectOnKey(processName, userId);
        db.setDeleteFlag(0);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param  processName processName
     * @param  userId userId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String processName, Integer userId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        activation(user, processName, userId);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, CsrfTokensEntity entity) {
        activation(user, entity.getProcessName(), entity.getUserId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(CsrfTokensEntity entity) {
        activation(entity.getProcessName(), entity.getUserId());

    }

}
