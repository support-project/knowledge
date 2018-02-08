package org.support.project.web.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.web.entity.SystemAttributesEntity;
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
 * システム付加情報
 * this class is auto generate and not edit.
 * if modify dao method, you can edit SystemAttributesDao.
 */
@DI(instance = Instance.Singleton)
public class GenSystemAttributesDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenSystemAttributesDao get() {
        return Container.getComp(GenSystemAttributesDao.class);
    }

    /**
     * Select all data.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<SystemAttributesEntity> physicalSelectAll() { 
        return physicalSelectAll(Order.DESC);
    }
    /**
     * Select all data.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<SystemAttributesEntity> physicalSelectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/SystemAttributesDao/SystemAttributesDao_physical_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, SystemAttributesEntity.class);
    }
    /**
     * Select all data with pager.
     * @param limit limit
     * @param offset offset
     * @return all data on limit and offset
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<SystemAttributesEntity> physicalSelectAllWithPager(int limit, int offset) { 
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
    public List<SystemAttributesEntity> physicalSelectAllWithPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/SystemAttributesDao/SystemAttributesDao_physical_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, SystemAttributesEntity.class, limit, offset);
    }
    /**
     * Select data on key.
     * @param  configName configName
     * @param  systemName systemName
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public SystemAttributesEntity physicalSelectOnKey(String configName, String systemName) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/SystemAttributesDao/SystemAttributesDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, SystemAttributesEntity.class, configName, systemName);
    }
    /**
     * Select all data that not deleted.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<SystemAttributesEntity> selectAll() { 
        return selectAll(Order.DESC);
    }
    /**
     * Select all data that not deleted.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<SystemAttributesEntity> selectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/SystemAttributesDao/SystemAttributesDao_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, SystemAttributesEntity.class);
    }
    /**
     * Select all data that not deleted with pager.
     * @param limit limit
     * @param offset offset
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<SystemAttributesEntity> selectAllWidthPager(int limit, int offset) { 
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
    public List<SystemAttributesEntity> selectAllWidthPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/SystemAttributesDao/SystemAttributesDao_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, SystemAttributesEntity.class, limit, offset);
    }
    /**
     * Select count that not deleted.
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer selectCountAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/SystemAttributesDao/SystemAttributesDao_select_count_all.sql");
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Select data that not deleted on key.
     * @param  configName configName
     * @param  systemName systemName
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public SystemAttributesEntity selectOnKey(String configName, String systemName) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/SystemAttributesDao/SystemAttributesDao_select_on_key.sql");
        return executeQuerySingle(sql, SystemAttributesEntity.class, configName, systemName);
    }
    /**
     * Select data that not deleted on CONFIG_NAME column.
     * @param configName configName
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<SystemAttributesEntity> selectOnConfigName(String configName) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/SystemAttributesDao/SystemAttributesDao_select_on_config_name.sql");
        return executeQueryList(sql, SystemAttributesEntity.class, configName);
    }
    /**
     * Select data that not deleted on SYSTEM_NAME column.
     * @param systemName systemName
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<SystemAttributesEntity> selectOnSystemName(String systemName) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/SystemAttributesDao/SystemAttributesDao_select_on_system_name.sql");
        return executeQueryList(sql, SystemAttributesEntity.class, systemName);
    }
    /**
     * Select data on CONFIG_NAME column.
     * @param configName configName
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<SystemAttributesEntity> physicalSelectOnConfigName(String configName) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/SystemAttributesDao/SystemAttributesDao_physical_select_on_config_name.sql");
        return executeQueryList(sql, SystemAttributesEntity.class, configName);
    }
    /**
     * Select data on SYSTEM_NAME column.
     * @param systemName systemName
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<SystemAttributesEntity> physicalSelectOnSystemName(String systemName) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/SystemAttributesDao/SystemAttributesDao_physical_select_on_system_name.sql");
        return executeQueryList(sql, SystemAttributesEntity.class, systemName);
    }
    /**
     * Count all data
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int physicalCountAll() {
        String sql = "SELECT COUNT(*) FROM SYSTEM_ATTRIBUTES";
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Create row id.
     * @return row id
     */
    protected String createRowId() {
        return IDGen.get().gen("SYSTEM_ATTRIBUTES");
    }
    /**
     * Physical Insert.
     * it is not create key on database sequence.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public SystemAttributesEntity rawPhysicalInsert(SystemAttributesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/SystemAttributesDao/SystemAttributesDao_raw_insert.sql");
        executeUpdate(sql, 
            entity.getConfigName(), 
            entity.getSystemName(), 
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
    public SystemAttributesEntity physicalInsert(SystemAttributesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/SystemAttributesDao/SystemAttributesDao_insert.sql");
        executeUpdate(sql, 
            entity.getConfigName(), 
            entity.getSystemName(), 
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
    public SystemAttributesEntity insert(Integer user, SystemAttributesEntity entity) {
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
    public SystemAttributesEntity insert(SystemAttributesEntity entity) {
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
    public SystemAttributesEntity physicalUpdate(SystemAttributesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/SystemAttributesDao/SystemAttributesDao_update.sql");
        executeUpdate(sql, 
            entity.getConfigValue(), 
            entity.getRowId(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag(), 
            entity.getConfigName(), 
            entity.getSystemName());
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
    public SystemAttributesEntity update(Integer user, SystemAttributesEntity entity) {
        SystemAttributesEntity db = selectOnKey(entity.getConfigName(), entity.getSystemName());
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
    public SystemAttributesEntity update(SystemAttributesEntity entity) {
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
    public SystemAttributesEntity save(Integer user, SystemAttributesEntity entity) {
        SystemAttributesEntity db = selectOnKey(entity.getConfigName(), entity.getSystemName());
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
    public SystemAttributesEntity save(SystemAttributesEntity entity) {
        SystemAttributesEntity db = selectOnKey(entity.getConfigName(), entity.getSystemName());
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
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(String configName, String systemName) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/SystemAttributesDao/SystemAttributesDao_delete.sql");
        executeUpdate(sql, configName, systemName);
    }
    /**
     * Physical Delete.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(SystemAttributesEntity entity) {
        physicalDelete(entity.getConfigName(), entity.getSystemName());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param  configName configName
     * @param  systemName systemName
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, String configName, String systemName) {
        SystemAttributesEntity db = selectOnKey(configName, systemName);
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
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String configName, String systemName) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        delete(user, configName, systemName);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, SystemAttributesEntity entity) {
        delete(user, entity.getConfigName(), entity.getSystemName());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(SystemAttributesEntity entity) {
        delete(entity.getConfigName(), entity.getSystemName());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param  configName configName
     * @param  systemName systemName
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, String configName, String systemName) {
        SystemAttributesEntity db = physicalSelectOnKey(configName, systemName);
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
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String configName, String systemName) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        activation(user, configName, systemName);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, SystemAttributesEntity entity) {
        activation(user, entity.getConfigName(), entity.getSystemName());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(SystemAttributesEntity entity) {
        activation(entity.getConfigName(), entity.getSystemName());

    }

}
