package org.support.project.web.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.web.entity.LoginHistoriesEntity;
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
 * ログイン履歴
 * this class is auto generate and not edit.
 * if modify dao method, you can edit LoginHistoriesDao.
 */
@DI(instance = Instance.Singleton)
public class GenLoginHistoriesDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenLoginHistoriesDao get() {
        return Container.getComp(GenLoginHistoriesDao.class);
    }

    /**
     * Select all data.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<LoginHistoriesEntity> physicalSelectAll() { 
        return physicalSelectAll(Order.DESC);
    }
    /**
     * Select all data.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<LoginHistoriesEntity> physicalSelectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/LoginHistoriesDao/LoginHistoriesDao_physical_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, LoginHistoriesEntity.class);
    }
    /**
     * Select all data with pager.
     * @param limit limit
     * @param offset offset
     * @return all data on limit and offset
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<LoginHistoriesEntity> physicalSelectAllWithPager(int limit, int offset) { 
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
    public List<LoginHistoriesEntity> physicalSelectAllWithPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/LoginHistoriesDao/LoginHistoriesDao_physical_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, LoginHistoriesEntity.class, limit, offset);
    }
    /**
     * Select data on key.
     * @param  loginCount loginCount
     * @param  userId userId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public LoginHistoriesEntity physicalSelectOnKey(Double loginCount, Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/LoginHistoriesDao/LoginHistoriesDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, LoginHistoriesEntity.class, loginCount, userId);
    }
    /**
     * Select all data that not deleted.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<LoginHistoriesEntity> selectAll() { 
        return selectAll(Order.DESC);
    }
    /**
     * Select all data that not deleted.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<LoginHistoriesEntity> selectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/LoginHistoriesDao/LoginHistoriesDao_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, LoginHistoriesEntity.class);
    }
    /**
     * Select all data that not deleted with pager.
     * @param limit limit
     * @param offset offset
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<LoginHistoriesEntity> selectAllWidthPager(int limit, int offset) { 
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
    public List<LoginHistoriesEntity> selectAllWidthPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/LoginHistoriesDao/LoginHistoriesDao_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, LoginHistoriesEntity.class, limit, offset);
    }
    /**
     * Select count that not deleted.
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer selectCountAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/LoginHistoriesDao/LoginHistoriesDao_select_count_all.sql");
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Select data that not deleted on key.
     * @param  loginCount loginCount
     * @param  userId userId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public LoginHistoriesEntity selectOnKey(Double loginCount, Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/LoginHistoriesDao/LoginHistoriesDao_select_on_key.sql");
        return executeQuerySingle(sql, LoginHistoriesEntity.class, loginCount, userId);
    }
    /**
     * Select data that not deleted on LOGIN_COUNT column.
     * @param loginCount loginCount
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<LoginHistoriesEntity> selectOnLoginCount(Double loginCount) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/LoginHistoriesDao/LoginHistoriesDao_select_on_login_count.sql");
        return executeQueryList(sql, LoginHistoriesEntity.class, loginCount);
    }
    /**
     * Select data that not deleted on USER_ID column.
     * @param userId userId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<LoginHistoriesEntity> selectOnUserId(Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/LoginHistoriesDao/LoginHistoriesDao_select_on_user_id.sql");
        return executeQueryList(sql, LoginHistoriesEntity.class, userId);
    }
    /**
     * Select data on LOGIN_COUNT column.
     * @param loginCount loginCount
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<LoginHistoriesEntity> physicalSelectOnLoginCount(Double loginCount) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/LoginHistoriesDao/LoginHistoriesDao_physical_select_on_login_count.sql");
        return executeQueryList(sql, LoginHistoriesEntity.class, loginCount);
    }
    /**
     * Select data on USER_ID column.
     * @param userId userId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<LoginHistoriesEntity> physicalSelectOnUserId(Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/LoginHistoriesDao/LoginHistoriesDao_physical_select_on_user_id.sql");
        return executeQueryList(sql, LoginHistoriesEntity.class, userId);
    }
    /**
     * Count all data
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int physicalCountAll() {
        String sql = "SELECT COUNT(*) FROM LOGIN_HISTORIES";
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Create row id.
     * @return row id
     */
    protected String createRowId() {
        return IDGen.get().gen("LOGIN_HISTORIES");
    }
    /**
     * Physical Insert.
     * it is not create key on database sequence.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public LoginHistoriesEntity rawPhysicalInsert(LoginHistoriesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/LoginHistoriesDao/LoginHistoriesDao_raw_insert.sql");
        executeUpdate(sql, 
            entity.getLoginCount(), 
            entity.getUserId(), 
            entity.getLodinDateTime(), 
            entity.getIpAddress(), 
            entity.getUserAgent(), 
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
    public LoginHistoriesEntity physicalInsert(LoginHistoriesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/LoginHistoriesDao/LoginHistoriesDao_insert.sql");
        executeUpdate(sql, 
            entity.getLoginCount(), 
            entity.getUserId(), 
            entity.getLodinDateTime(), 
            entity.getIpAddress(), 
            entity.getUserAgent(), 
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
    public LoginHistoriesEntity insert(Integer user, LoginHistoriesEntity entity) {
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
    public LoginHistoriesEntity insert(LoginHistoriesEntity entity) {
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
    public LoginHistoriesEntity physicalUpdate(LoginHistoriesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/LoginHistoriesDao/LoginHistoriesDao_update.sql");
        executeUpdate(sql, 
            entity.getLodinDateTime(), 
            entity.getIpAddress(), 
            entity.getUserAgent(), 
            entity.getRowId(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag(), 
            entity.getLoginCount(), 
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
    public LoginHistoriesEntity update(Integer user, LoginHistoriesEntity entity) {
        LoginHistoriesEntity db = selectOnKey(entity.getLoginCount(), entity.getUserId());
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
    public LoginHistoriesEntity update(LoginHistoriesEntity entity) {
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
    public LoginHistoriesEntity save(Integer user, LoginHistoriesEntity entity) {
        LoginHistoriesEntity db = selectOnKey(entity.getLoginCount(), entity.getUserId());
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
    public LoginHistoriesEntity save(LoginHistoriesEntity entity) {
        LoginHistoriesEntity db = selectOnKey(entity.getLoginCount(), entity.getUserId());
        if (db == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }
    /**
     * Physical Delete.
     * @param  loginCount loginCount
     * @param  userId userId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(Double loginCount, Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/LoginHistoriesDao/LoginHistoriesDao_delete.sql");
        executeUpdate(sql, loginCount, userId);
    }
    /**
     * Physical Delete.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(LoginHistoriesEntity entity) {
        physicalDelete(entity.getLoginCount(), entity.getUserId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param  loginCount loginCount
     * @param  userId userId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, Double loginCount, Integer userId) {
        LoginHistoriesEntity db = selectOnKey(loginCount, userId);
        db.setDeleteFlag(1);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * @param  loginCount loginCount
     * @param  userId userId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Double loginCount, Integer userId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        delete(user, loginCount, userId);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, LoginHistoriesEntity entity) {
        delete(user, entity.getLoginCount(), entity.getUserId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(LoginHistoriesEntity entity) {
        delete(entity.getLoginCount(), entity.getUserId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param  loginCount loginCount
     * @param  userId userId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, Double loginCount, Integer userId) {
        LoginHistoriesEntity db = physicalSelectOnKey(loginCount, userId);
        db.setDeleteFlag(0);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param  loginCount loginCount
     * @param  userId userId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Double loginCount, Integer userId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        activation(user, loginCount, userId);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, LoginHistoriesEntity entity) {
        activation(user, entity.getLoginCount(), entity.getUserId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(LoginHistoriesEntity entity) {
        activation(entity.getLoginCount(), entity.getUserId());

    }

}
