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
import org.support.project.ormapping.connection.ConnectionManager;
import org.support.project.common.util.PropertyUtil;

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
    public List<ServiceLocaleConfigsEntity> physicalSelectAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ServiceLocaleConfigsDao/ServiceLocaleConfigsDao_physical_select_all.sql");
        return executeQueryList(sql, ServiceLocaleConfigsEntity.class);
    }
    /**
     * Select all data with pager.
     * @param limit limit
     * @param offset offset
     * @return all data on limit and offset
     */
    public List<ServiceLocaleConfigsEntity> physicalSelectAllWithPager(int limit, int offset) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ServiceLocaleConfigsDao/ServiceLocaleConfigsDao_physical_select_all_with_pager.sql");
        return executeQueryList(sql, ServiceLocaleConfigsEntity.class, limit, offset);
    }
    /**
     * Select data on key.
     * @param  serviceName serviceName
     * @return data
     */
    public ServiceLocaleConfigsEntity physicalSelectOnKey(String serviceName) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ServiceLocaleConfigsDao/ServiceLocaleConfigsDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, ServiceLocaleConfigsEntity.class, serviceName);
    }
    /**
     * Select all data that not deleted.
     * @return all data
     */
    public List<ServiceLocaleConfigsEntity> selectAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ServiceLocaleConfigsDao/ServiceLocaleConfigsDao_select_all.sql");
        return executeQueryList(sql, ServiceLocaleConfigsEntity.class);
    }
    /**
     * Select all data that not deleted with pager.
     * @param limit limit
     * @param offset offset
     * @return all data
     */
    public List<ServiceLocaleConfigsEntity> selectAllWidthPager(int limit, int offset) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ServiceLocaleConfigsDao/ServiceLocaleConfigsDao_select_all_with_pager.sql");
        return executeQueryList(sql, ServiceLocaleConfigsEntity.class, limit, offset);
    }
    /**
     * Select count that not deleted.
     * @return count
     */
    public Integer selectCountAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ServiceLocaleConfigsDao/ServiceLocaleConfigsDao_select_count_all.sql");
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Select data that not deleted on key.
     * @param  serviceName serviceName
     * @return data
     */
    public ServiceLocaleConfigsEntity selectOnKey(String serviceName) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ServiceLocaleConfigsDao/ServiceLocaleConfigsDao_select_on_key.sql");
        return executeQuerySingle(sql, ServiceLocaleConfigsEntity.class, serviceName);
    }
    /**
     * Count all data
     * @return count
     */
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
            entity.getServiceName(), 
            entity.getLocaleKey(), 
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
            entity.getServiceName(), 
            entity.getLocaleKey(), 
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
        entity.setInsertDatetime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateUser(user);
        entity.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
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
            entity.getLocaleKey(), 
            entity.getPageHtml(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag(), 
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
        ServiceLocaleConfigsEntity db = selectOnKey(entity.getServiceName());
        entity.setInsertUser(db.getInsertUser());
        entity.setInsertDatetime(db.getInsertDatetime());
        entity.setDeleteFlag(db.getDeleteFlag());
        entity.setUpdateUser(user);
        entity.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
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
        ServiceLocaleConfigsEntity db = selectOnKey(entity.getServiceName());
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
        ServiceLocaleConfigsEntity db = selectOnKey(entity.getServiceName());
        if (db == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }
    /**
     * Physical Delete.
     * @param  serviceName serviceName
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(String serviceName) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ServiceLocaleConfigsDao/ServiceLocaleConfigsDao_delete.sql");
        executeUpdate(sql, serviceName);
    }
    /**
     * Physical Delete.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(ServiceLocaleConfigsEntity entity) {
        physicalDelete(entity.getServiceName());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param  serviceName serviceName
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, String serviceName) {
        ServiceLocaleConfigsEntity db = selectOnKey(serviceName);
        db.setDeleteFlag(1);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
        physicalUpdate(db);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * @param  serviceName serviceName
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String serviceName) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        delete(user, serviceName);
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
        delete(user, entity.getServiceName());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(ServiceLocaleConfigsEntity entity) {
        delete(entity.getServiceName());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param  serviceName serviceName
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, String serviceName) {
        ServiceLocaleConfigsEntity db = physicalSelectOnKey(serviceName);
        db.setDeleteFlag(0);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
        physicalUpdate(db);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param  serviceName serviceName
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String serviceName) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        activation(user, serviceName);
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
        activation(user, entity.getServiceName());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(ServiceLocaleConfigsEntity entity) {
        activation(entity.getServiceName());

    }

}
