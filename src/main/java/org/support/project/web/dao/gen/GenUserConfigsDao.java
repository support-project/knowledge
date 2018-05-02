package org.support.project.web.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.web.entity.UserConfigsEntity;
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
 * ユーザ設定
 * this class is auto generate and not edit.
 * if modify dao method, you can edit UserConfigsDao.
 */
@DI(instance = Instance.Singleton)
public class GenUserConfigsDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenUserConfigsDao get() {
        return Container.getComp(GenUserConfigsDao.class);
    }

    /**
     * Select all data.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<UserConfigsEntity> physicalSelectAll() { 
        return physicalSelectAll(Order.DESC);
    }
    /**
     * Select all data.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<UserConfigsEntity> physicalSelectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserConfigsDao/UserConfigsDao_physical_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, UserConfigsEntity.class);
    }
    /**
     * Select all data with pager.
     * @param limit limit
     * @param offset offset
     * @return all data on limit and offset
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<UserConfigsEntity> physicalSelectAllWithPager(int limit, int offset) { 
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
    public List<UserConfigsEntity> physicalSelectAllWithPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserConfigsDao/UserConfigsDao_physical_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, UserConfigsEntity.class, limit, offset);
    }
    /**
     * Select data on key.
     * @param  configName configName
     * @param  systemName systemName
     * @param  userId userId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public UserConfigsEntity physicalSelectOnKey(String configName, String systemName, Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserConfigsDao/UserConfigsDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, UserConfigsEntity.class, configName, systemName, userId);
    }
    /**
     * Select all data that not deleted.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<UserConfigsEntity> selectAll() { 
        return selectAll(Order.DESC);
    }
    /**
     * Select all data that not deleted.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<UserConfigsEntity> selectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserConfigsDao/UserConfigsDao_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, UserConfigsEntity.class);
    }
    /**
     * Select all data that not deleted with pager.
     * @param limit limit
     * @param offset offset
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<UserConfigsEntity> selectAllWidthPager(int limit, int offset) { 
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
    public List<UserConfigsEntity> selectAllWidthPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserConfigsDao/UserConfigsDao_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, UserConfigsEntity.class, limit, offset);
    }
    /**
     * Select count that not deleted.
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer selectCountAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserConfigsDao/UserConfigsDao_select_count_all.sql");
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Select data that not deleted on key.
     * @param  configName configName
     * @param  systemName systemName
     * @param  userId userId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public UserConfigsEntity selectOnKey(String configName, String systemName, Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserConfigsDao/UserConfigsDao_select_on_key.sql");
        return executeQuerySingle(sql, UserConfigsEntity.class, configName, systemName, userId);
    }
    /**
     * Select data that not deleted on CONFIG_NAME column.
     * @param configName configName
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<UserConfigsEntity> selectOnConfigName(String configName) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserConfigsDao/UserConfigsDao_select_on_config_name.sql");
        return executeQueryList(sql, UserConfigsEntity.class, configName);
    }
    /**
     * Select data that not deleted on SYSTEM_NAME column.
     * @param systemName systemName
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<UserConfigsEntity> selectOnSystemName(String systemName) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserConfigsDao/UserConfigsDao_select_on_system_name.sql");
        return executeQueryList(sql, UserConfigsEntity.class, systemName);
    }
    /**
     * Select data that not deleted on USER_ID column.
     * @param userId userId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<UserConfigsEntity> selectOnUserId(Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserConfigsDao/UserConfigsDao_select_on_user_id.sql");
        return executeQueryList(sql, UserConfigsEntity.class, userId);
    }
    /**
     * Select data on CONFIG_NAME column.
     * @param configName configName
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<UserConfigsEntity> physicalSelectOnConfigName(String configName) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserConfigsDao/UserConfigsDao_physical_select_on_config_name.sql");
        return executeQueryList(sql, UserConfigsEntity.class, configName);
    }
    /**
     * Select data on SYSTEM_NAME column.
     * @param systemName systemName
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<UserConfigsEntity> physicalSelectOnSystemName(String systemName) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserConfigsDao/UserConfigsDao_physical_select_on_system_name.sql");
        return executeQueryList(sql, UserConfigsEntity.class, systemName);
    }
    /**
     * Select data on USER_ID column.
     * @param userId userId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<UserConfigsEntity> physicalSelectOnUserId(Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserConfigsDao/UserConfigsDao_physical_select_on_user_id.sql");
        return executeQueryList(sql, UserConfigsEntity.class, userId);
    }
    /**
     * Count all data
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int physicalCountAll() {
        String sql = "SELECT COUNT(*) FROM USER_CONFIGS";
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Create row id.
     * @return row id
     */
    protected String createRowId() {
        return IDGen.get().gen("USER_CONFIGS");
    }
    /**
     * Physical Insert.
     * it is not create key on database sequence.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public UserConfigsEntity rawPhysicalInsert(UserConfigsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserConfigsDao/UserConfigsDao_raw_insert.sql");
        executeUpdate(sql, 
            entity.getConfigName(), 
            entity.getSystemName(), 
            entity.getUserId(), 
            entity.getConfigValue(), 
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
    public UserConfigsEntity physicalInsert(UserConfigsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserConfigsDao/UserConfigsDao_insert.sql");
        executeUpdate(sql, 
            entity.getConfigName(), 
            entity.getSystemName(), 
            entity.getUserId(), 
            entity.getConfigValue(), 
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
    public UserConfigsEntity insert(Integer user, UserConfigsEntity entity) {
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
    public UserConfigsEntity insert(UserConfigsEntity entity) {
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
    public UserConfigsEntity physicalUpdate(UserConfigsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserConfigsDao/UserConfigsDao_update.sql");
        executeUpdate(sql, 
            entity.getConfigValue(), 
            entity.getRowId(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag(), 
            entity.getConfigName(), 
            entity.getSystemName(), 
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
    public UserConfigsEntity update(Integer user, UserConfigsEntity entity) {
        UserConfigsEntity db = selectOnKey(entity.getConfigName(), entity.getSystemName(), entity.getUserId());
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
    public UserConfigsEntity update(UserConfigsEntity entity) {
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
    public UserConfigsEntity save(Integer user, UserConfigsEntity entity) {
        UserConfigsEntity db = selectOnKey(entity.getConfigName(), entity.getSystemName(), entity.getUserId());
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
    public UserConfigsEntity save(UserConfigsEntity entity) {
        UserConfigsEntity db = selectOnKey(entity.getConfigName(), entity.getSystemName(), entity.getUserId());
        if (db == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }
    /**
     * Physical Delete.
     * @param  configName configName
     * @param  systemName systemName
     * @param  userId userId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(String configName, String systemName, Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserConfigsDao/UserConfigsDao_delete.sql");
        executeUpdate(sql, configName, systemName, userId);
    }
    /**
     * Physical Delete.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(UserConfigsEntity entity) {
        physicalDelete(entity.getConfigName(), entity.getSystemName(), entity.getUserId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param  configName configName
     * @param  systemName systemName
     * @param  userId userId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, String configName, String systemName, Integer userId) {
        UserConfigsEntity db = selectOnKey(configName, systemName, userId);
        db.setDeleteFlag(1);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * @param  configName configName
     * @param  systemName systemName
     * @param  userId userId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String configName, String systemName, Integer userId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        delete(user, configName, systemName, userId);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, UserConfigsEntity entity) {
        delete(user, entity.getConfigName(), entity.getSystemName(), entity.getUserId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(UserConfigsEntity entity) {
        delete(entity.getConfigName(), entity.getSystemName(), entity.getUserId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param  configName configName
     * @param  systemName systemName
     * @param  userId userId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, String configName, String systemName, Integer userId) {
        UserConfigsEntity db = physicalSelectOnKey(configName, systemName, userId);
        db.setDeleteFlag(0);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param  configName configName
     * @param  systemName systemName
     * @param  userId userId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String configName, String systemName, Integer userId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        activation(user, configName, systemName, userId);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, UserConfigsEntity entity) {
        activation(user, entity.getConfigName(), entity.getSystemName(), entity.getUserId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(UserConfigsEntity entity) {
        activation(entity.getConfigName(), entity.getSystemName(), entity.getUserId());

    }

}
