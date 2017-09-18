package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.MailPropertiesEntity;
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
 * メール受信設定
 * this class is auto generate and not edit.
 * if modify dao method, you can edit MailPropertiesDao.
 */
@DI(instance = Instance.Singleton)
public class GenMailPropertiesDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenMailPropertiesDao get() {
        return Container.getComp(GenMailPropertiesDao.class);
    }

    /**
     * Select all data.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailPropertiesEntity> physicalSelectAll() { 
        return physicalSelectAll(Order.DESC);
    }
    /**
     * Select all data.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailPropertiesEntity> physicalSelectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailPropertiesDao/MailPropertiesDao_physical_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, MailPropertiesEntity.class);
    }
    /**
     * Select all data with pager.
     * @param limit limit
     * @param offset offset
     * @return all data on limit and offset
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailPropertiesEntity> physicalSelectAllWithPager(int limit, int offset) { 
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
    public List<MailPropertiesEntity> physicalSelectAllWithPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailPropertiesDao/MailPropertiesDao_physical_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, MailPropertiesEntity.class, limit, offset);
    }
    /**
     * Select data on key.
     * @param  hookId hookId
     * @param  propertyKey propertyKey
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public MailPropertiesEntity physicalSelectOnKey(Integer hookId, String propertyKey) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailPropertiesDao/MailPropertiesDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, MailPropertiesEntity.class, hookId, propertyKey);
    }
    /**
     * Select all data that not deleted.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailPropertiesEntity> selectAll() { 
        return selectAll(Order.DESC);
    }
    /**
     * Select all data that not deleted.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailPropertiesEntity> selectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailPropertiesDao/MailPropertiesDao_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, MailPropertiesEntity.class);
    }
    /**
     * Select all data that not deleted with pager.
     * @param limit limit
     * @param offset offset
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailPropertiesEntity> selectAllWidthPager(int limit, int offset) { 
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
    public List<MailPropertiesEntity> selectAllWidthPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailPropertiesDao/MailPropertiesDao_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, MailPropertiesEntity.class, limit, offset);
    }
    /**
     * Select count that not deleted.
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer selectCountAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailPropertiesDao/MailPropertiesDao_select_count_all.sql");
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Select data that not deleted on key.
     * @param  hookId hookId
     * @param  propertyKey propertyKey
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public MailPropertiesEntity selectOnKey(Integer hookId, String propertyKey) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailPropertiesDao/MailPropertiesDao_select_on_key.sql");
        return executeQuerySingle(sql, MailPropertiesEntity.class, hookId, propertyKey);
    }
    /**
     * Select data that not deleted on HOOK_ID column.
     * @param hookId hookId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailPropertiesEntity> selectOnHookId(Integer hookId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailPropertiesDao/MailPropertiesDao_select_on_hook_id.sql");
        return executeQueryList(sql, MailPropertiesEntity.class, hookId);
    }
    /**
     * Select data that not deleted on PROPERTY_KEY column.
     * @param propertyKey propertyKey
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailPropertiesEntity> selectOnPropertyKey(String propertyKey) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailPropertiesDao/MailPropertiesDao_select_on_property_key.sql");
        return executeQueryList(sql, MailPropertiesEntity.class, propertyKey);
    }
    /**
     * Select data on HOOK_ID column.
     * @param hookId hookId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailPropertiesEntity> physicalSelectOnHookId(Integer hookId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailPropertiesDao/MailPropertiesDao_physical_select_on_hook_id.sql");
        return executeQueryList(sql, MailPropertiesEntity.class, hookId);
    }
    /**
     * Select data on PROPERTY_KEY column.
     * @param propertyKey propertyKey
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailPropertiesEntity> physicalSelectOnPropertyKey(String propertyKey) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailPropertiesDao/MailPropertiesDao_physical_select_on_property_key.sql");
        return executeQueryList(sql, MailPropertiesEntity.class, propertyKey);
    }
    /**
     * Count all data
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int physicalCountAll() {
        String sql = "SELECT COUNT(*) FROM MAIL_PROPERTIES";
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Physical Insert.
     * it is not create key on database sequence.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public MailPropertiesEntity rawPhysicalInsert(MailPropertiesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailPropertiesDao/MailPropertiesDao_raw_insert.sql");
        executeUpdate(sql, 
            entity.getHookId(), 
            entity.getPropertyKey(), 
            entity.getPropertyValue(), 
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
    public MailPropertiesEntity physicalInsert(MailPropertiesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailPropertiesDao/MailPropertiesDao_insert.sql");
        executeUpdate(sql, 
            entity.getHookId(), 
            entity.getPropertyKey(), 
            entity.getPropertyValue(), 
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
    public MailPropertiesEntity insert(Integer user, MailPropertiesEntity entity) {
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
    public MailPropertiesEntity insert(MailPropertiesEntity entity) {
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
    public MailPropertiesEntity physicalUpdate(MailPropertiesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailPropertiesDao/MailPropertiesDao_update.sql");
        executeUpdate(sql, 
            entity.getPropertyValue(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag(), 
            entity.getHookId(), 
            entity.getPropertyKey());
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
    public MailPropertiesEntity update(Integer user, MailPropertiesEntity entity) {
        MailPropertiesEntity db = selectOnKey(entity.getHookId(), entity.getPropertyKey());
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
    public MailPropertiesEntity update(MailPropertiesEntity entity) {
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
    public MailPropertiesEntity save(Integer user, MailPropertiesEntity entity) {
        MailPropertiesEntity db = selectOnKey(entity.getHookId(), entity.getPropertyKey());
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
    public MailPropertiesEntity save(MailPropertiesEntity entity) {
        MailPropertiesEntity db = selectOnKey(entity.getHookId(), entity.getPropertyKey());
        if (db == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }
    /**
     * Physical Delete.
     * @param  hookId hookId
     * @param  propertyKey propertyKey
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(Integer hookId, String propertyKey) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailPropertiesDao/MailPropertiesDao_delete.sql");
        executeUpdate(sql, hookId, propertyKey);
    }
    /**
     * Physical Delete.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(MailPropertiesEntity entity) {
        physicalDelete(entity.getHookId(), entity.getPropertyKey());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param  hookId hookId
     * @param  propertyKey propertyKey
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, Integer hookId, String propertyKey) {
        MailPropertiesEntity db = selectOnKey(hookId, propertyKey);
        db.setDeleteFlag(1);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * @param  hookId hookId
     * @param  propertyKey propertyKey
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer hookId, String propertyKey) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        delete(user, hookId, propertyKey);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, MailPropertiesEntity entity) {
        delete(user, entity.getHookId(), entity.getPropertyKey());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(MailPropertiesEntity entity) {
        delete(entity.getHookId(), entity.getPropertyKey());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param  hookId hookId
     * @param  propertyKey propertyKey
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, Integer hookId, String propertyKey) {
        MailPropertiesEntity db = physicalSelectOnKey(hookId, propertyKey);
        db.setDeleteFlag(0);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param  hookId hookId
     * @param  propertyKey propertyKey
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer hookId, String propertyKey) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        activation(user, hookId, propertyKey);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, MailPropertiesEntity entity) {
        activation(user, entity.getHookId(), entity.getPropertyKey());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(MailPropertiesEntity entity) {
        activation(entity.getHookId(), entity.getPropertyKey());

    }

}
