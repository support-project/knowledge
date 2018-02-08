package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.ServiceLocaleConfigsEntity;
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
 * サービスの表示言語毎の設定
 * this class is auto generate and not edit.
 * if modify dao method, you can edit ServiceLocaleConfigsDao.
 */
@DI(instance = Instance.Singleton)
public class GenServiceLocaleConfigsDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenServiceLocaleConfigsDao get() {
        return Container.getComp(GenServiceLocaleConfigsDao.class);
    }

    /**
     * Select all data.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ServiceLocaleConfigsEntity> physicalSelectAll() { 
        return physicalSelectAll(Order.DESC);
    }
    /**
     * Select all data.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ServiceLocaleConfigsEntity> physicalSelectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ServiceLocaleConfigsDao/ServiceLocaleConfigsDao_physical_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, ServiceLocaleConfigsEntity.class);
    }
    /**
     * Select all data with pager.
     * @param limit limit
     * @param offset offset
     * @return all data on limit and offset
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ServiceLocaleConfigsEntity> physicalSelectAllWithPager(int limit, int offset) { 
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
    public List<ServiceLocaleConfigsEntity> physicalSelectAllWithPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ServiceLocaleConfigsDao/ServiceLocaleConfigsDao_physical_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, ServiceLocaleConfigsEntity.class, limit, offset);
    }
    /**
     * Select data on key.
     * @param  localeKey localeKey
     * @param  serviceName serviceName
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public ServiceLocaleConfigsEntity physicalSelectOnKey(String localeKey, String serviceName) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ServiceLocaleConfigsDao/ServiceLocaleConfigsDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, ServiceLocaleConfigsEntity.class, localeKey, serviceName);
    }
    /**
     * Select all data that not deleted.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ServiceLocaleConfigsEntity> selectAll() { 
        return selectAll(Order.DESC);
    }
    /**
     * Select all data that not deleted.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ServiceLocaleConfigsEntity> selectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ServiceLocaleConfigsDao/ServiceLocaleConfigsDao_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, ServiceLocaleConfigsEntity.class);
    }
    /**
     * Select all data that not deleted with pager.
     * @param limit limit
     * @param offset offset
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ServiceLocaleConfigsEntity> selectAllWidthPager(int limit, int offset) { 
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
    public List<ServiceLocaleConfigsEntity> selectAllWidthPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ServiceLocaleConfigsDao/ServiceLocaleConfigsDao_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, ServiceLocaleConfigsEntity.class, limit, offset);
    }
    /**
     * Select count that not deleted.
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer selectCountAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ServiceLocaleConfigsDao/ServiceLocaleConfigsDao_select_count_all.sql");
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Select data that not deleted on key.
     * @param  localeKey localeKey
     * @param  serviceName serviceName
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public ServiceLocaleConfigsEntity selectOnKey(String localeKey, String serviceName) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ServiceLocaleConfigsDao/ServiceLocaleConfigsDao_select_on_key.sql");
        return executeQuerySingle(sql, ServiceLocaleConfigsEntity.class, localeKey, serviceName);
    }
    /**
     * Select data that not deleted on LOCALE_KEY column.
     * @param localeKey localeKey
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ServiceLocaleConfigsEntity> selectOnLocaleKey(String localeKey) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ServiceLocaleConfigsDao/ServiceLocaleConfigsDao_select_on_locale_key.sql");
        return executeQueryList(sql, ServiceLocaleConfigsEntity.class, localeKey);
    }
    /**
     * Select data that not deleted on SERVICE_NAME column.
     * @param serviceName serviceName
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ServiceLocaleConfigsEntity> selectOnServiceName(String serviceName) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ServiceLocaleConfigsDao/ServiceLocaleConfigsDao_select_on_service_name.sql");
        return executeQueryList(sql, ServiceLocaleConfigsEntity.class, serviceName);
    }
    /**
     * Select data on LOCALE_KEY column.
     * @param localeKey localeKey
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ServiceLocaleConfigsEntity> physicalSelectOnLocaleKey(String localeKey) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ServiceLocaleConfigsDao/ServiceLocaleConfigsDao_physical_select_on_locale_key.sql");
        return executeQueryList(sql, ServiceLocaleConfigsEntity.class, localeKey);
    }
    /**
     * Select data on SERVICE_NAME column.
     * @param serviceName serviceName
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ServiceLocaleConfigsEntity> physicalSelectOnServiceName(String serviceName) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ServiceLocaleConfigsDao/ServiceLocaleConfigsDao_physical_select_on_service_name.sql");
        return executeQueryList(sql, ServiceLocaleConfigsEntity.class, serviceName);
    }
    /**
     * Count all data
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int physicalCountAll() {
        String sql = "SELECT COUNT(*) FROM SERVICE_LOCALE_CONFIGS";
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Physical Insert.
     * it is not create key on database sequence.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public ServiceLocaleConfigsEntity rawPhysicalInsert(ServiceLocaleConfigsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ServiceLocaleConfigsDao/ServiceLocaleConfigsDao_raw_insert.sql");
        executeUpdate(sql, 
            entity.getLocaleKey(), 
            entity.getServiceName(), 
            entity.getPageHtml(), 
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
    public ServiceLocaleConfigsEntity physicalInsert(ServiceLocaleConfigsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ServiceLocaleConfigsDao/ServiceLocaleConfigsDao_insert.sql");
        executeUpdate(sql, 
            entity.getLocaleKey(), 
            entity.getServiceName(), 
            entity.getPageHtml(), 
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
    public ServiceLocaleConfigsEntity insert(Integer user, ServiceLocaleConfigsEntity entity) {
        entity.setInsertUser(user);
        entity.setInsertDatetime(new Timestamp(DateUtils.now().getTime()));
        entity.setUpdateUser(user);
        entity.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        entity.setDeleteFlag(0);
        return physicalInsert(entity);
    }
    /**
     * Insert.
     * saved user id is auto set.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public ServiceLocaleConfigsEntity insert(ServiceLocaleConfigsEntity entity) {
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
    public ServiceLocaleConfigsEntity physicalUpdate(ServiceLocaleConfigsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ServiceLocaleConfigsDao/ServiceLocaleConfigsDao_update.sql");
        executeUpdate(sql, 
            entity.getPageHtml(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag(), 
            entity.getLocaleKey(), 
            entity.getServiceName());
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
    public ServiceLocaleConfigsEntity update(Integer user, ServiceLocaleConfigsEntity entity) {
        ServiceLocaleConfigsEntity db = selectOnKey(entity.getLocaleKey(), entity.getServiceName());
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
    public ServiceLocaleConfigsEntity update(ServiceLocaleConfigsEntity entity) {
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
    public ServiceLocaleConfigsEntity save(Integer user, ServiceLocaleConfigsEntity entity) {
        ServiceLocaleConfigsEntity db = selectOnKey(entity.getLocaleKey(), entity.getServiceName());
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
    public ServiceLocaleConfigsEntity save(ServiceLocaleConfigsEntity entity) {
        ServiceLocaleConfigsEntity db = selectOnKey(entity.getLocaleKey(), entity.getServiceName());
        if (db == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }
    /**
     * Physical Delete.
     * @param  localeKey localeKey
     * @param  serviceName serviceName
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(String localeKey, String serviceName) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ServiceLocaleConfigsDao/ServiceLocaleConfigsDao_delete.sql");
        executeUpdate(sql, localeKey, serviceName);
    }
    /**
     * Physical Delete.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(ServiceLocaleConfigsEntity entity) {
        physicalDelete(entity.getLocaleKey(), entity.getServiceName());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param  localeKey localeKey
     * @param  serviceName serviceName
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, String localeKey, String serviceName) {
        ServiceLocaleConfigsEntity db = selectOnKey(localeKey, serviceName);
        db.setDeleteFlag(1);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * @param  localeKey localeKey
     * @param  serviceName serviceName
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String localeKey, String serviceName) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        delete(user, localeKey, serviceName);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, ServiceLocaleConfigsEntity entity) {
        delete(user, entity.getLocaleKey(), entity.getServiceName());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(ServiceLocaleConfigsEntity entity) {
        delete(entity.getLocaleKey(), entity.getServiceName());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param  localeKey localeKey
     * @param  serviceName serviceName
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, String localeKey, String serviceName) {
        ServiceLocaleConfigsEntity db = physicalSelectOnKey(localeKey, serviceName);
        db.setDeleteFlag(0);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param  localeKey localeKey
     * @param  serviceName serviceName
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String localeKey, String serviceName) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        activation(user, localeKey, serviceName);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, ServiceLocaleConfigsEntity entity) {
        activation(user, entity.getLocaleKey(), entity.getServiceName());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(ServiceLocaleConfigsEntity entity) {
        activation(entity.getLocaleKey(), entity.getServiceName());

    }

}
