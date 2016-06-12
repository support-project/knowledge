package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.MailHooksEntity;
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
 * 受信したメールからの処理
 * this class is auto generate and not edit.
 * if modify dao method, you can edit MailHooksDao.
 */
@DI(instance = Instance.Singleton)
public class GenMailHooksDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenMailHooksDao get() {
        return Container.getComp(GenMailHooksDao.class);
    }

    /**
     * Select all data.
     * @return all data
     */
    public List<MailHooksEntity> physicalSelectAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHooksDao/MailHooksDao_physical_select_all.sql");
        return executeQueryList(sql, MailHooksEntity.class);
    }
    /**
     * Select all data with pager.
     * @param limit limit
     * @param offset offset
     * @return all data on limit and offset
     */
    public List<MailHooksEntity> physicalSelectAllWithPager(int limit, int offset) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHooksDao/MailHooksDao_physical_select_all_with_pager.sql");
        return executeQueryList(sql, MailHooksEntity.class, limit, offset);
    }
    /**
     * Select data on key.
     * @param  hookId hookId
     * @return data
     */
    public MailHooksEntity physicalSelectOnKey(Integer hookId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHooksDao/MailHooksDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, MailHooksEntity.class, hookId);
    }
    /**
     * Select all data that not deleted.
     * @return all data
     */
    public List<MailHooksEntity> selectAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHooksDao/MailHooksDao_select_all.sql");
        return executeQueryList(sql, MailHooksEntity.class);
    }
    /**
     * Select all data that not deleted with pager.
     * @param limit limit
     * @param offset offset
     * @return all data
     */
    public List<MailHooksEntity> selectAllWidthPager(int limit, int offset) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHooksDao/MailHooksDao_select_all_with_pager.sql");
        return executeQueryList(sql, MailHooksEntity.class, limit, offset);
    }
    /**
     * Select count that not deleted.
     * @return count
     */
    public Integer selectCountAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHooksDao/MailHooksDao_select_count_all.sql");
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Select data that not deleted on key.
     * @param  hookId hookId
     * @return data
     */
    public MailHooksEntity selectOnKey(Integer hookId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHooksDao/MailHooksDao_select_on_key.sql");
        return executeQuerySingle(sql, MailHooksEntity.class, hookId);
    }
    /**
     * Physical Insert.
     * it is not create key on database sequence.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public MailHooksEntity rawPhysicalInsert(MailHooksEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHooksDao/MailHooksDao_raw_insert.sql");
        executeUpdate(sql, 
            entity.getHookId(), 
            entity.getMailProtocol(), 
            entity.getMailHost(), 
            entity.getMailPort(), 
            entity.getMailUser(), 
            entity.getMailPass(), 
            entity.getMailPassSalt(), 
            entity.getMailFolder(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag());
        String driverClass = ConnectionManager.getInstance().getDriverClass(getConnectionName());
        if (ORMappingParameter.DRIVER_NAME_POSTGRESQL.equals(driverClass)) {
            String setValSql = "select setval('MAIL_HOOKS_HOOK_ID_seq', (select max(HOOK_ID) from MAIL_HOOKS));";
            executeQuerySingle(setValSql, Long.class);
        }
        return entity;
    }
    /**
     * Physical Insert.
     * if key column have sequence, key value create by database.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public MailHooksEntity physicalInsert(MailHooksEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHooksDao/MailHooksDao_insert.sql");
        Class<?> type = PropertyUtil.getPropertyType(entity, "hookId");
        Object key = executeInsert(sql, type, 
            entity.getMailProtocol(), 
            entity.getMailHost(), 
            entity.getMailPort(), 
            entity.getMailUser(), 
            entity.getMailPass(), 
            entity.getMailPassSalt(), 
            entity.getMailFolder(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag());
        PropertyUtil.setPropertyValue(entity, "hookId", key);
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
    public MailHooksEntity insert(Integer user, MailHooksEntity entity) {
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
    public MailHooksEntity insert(MailHooksEntity entity) {
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
    public MailHooksEntity physicalUpdate(MailHooksEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHooksDao/MailHooksDao_update.sql");
        executeUpdate(sql, 
            entity.getMailProtocol(), 
            entity.getMailHost(), 
            entity.getMailPort(), 
            entity.getMailUser(), 
            entity.getMailPass(), 
            entity.getMailPassSalt(), 
            entity.getMailFolder(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag(), 
            entity.getHookId());
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
    public MailHooksEntity update(Integer user, MailHooksEntity entity) {
        MailHooksEntity db = selectOnKey(entity.getHookId());
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
    public MailHooksEntity update(MailHooksEntity entity) {
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
    public MailHooksEntity save(Integer user, MailHooksEntity entity) {
        MailHooksEntity db = selectOnKey(entity.getHookId());
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
    public MailHooksEntity save(MailHooksEntity entity) {
        MailHooksEntity db = selectOnKey(entity.getHookId());
        if (db == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }
    /**
     * Physical Delete.
     * @param  hookId hookId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(Integer hookId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHooksDao/MailHooksDao_delete.sql");
        executeUpdate(sql, hookId);
    }
    /**
     * Physical Delete.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(MailHooksEntity entity) {
        physicalDelete(entity.getHookId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param  hookId hookId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, Integer hookId) {
        MailHooksEntity db = selectOnKey(hookId);
        db.setDeleteFlag(1);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
        physicalUpdate(db);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * @param  hookId hookId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer hookId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        delete(user, hookId);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, MailHooksEntity entity) {
        delete(user, entity.getHookId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(MailHooksEntity entity) {
        delete(entity.getHookId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param  hookId hookId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, Integer hookId) {
        MailHooksEntity db = physicalSelectOnKey(hookId);
        db.setDeleteFlag(0);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
        physicalUpdate(db);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param  hookId hookId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer hookId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        activation(user, hookId);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, MailHooksEntity entity) {
        activation(user, entity.getHookId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(MailHooksEntity entity) {
        activation(entity.getHookId());

    }

}
