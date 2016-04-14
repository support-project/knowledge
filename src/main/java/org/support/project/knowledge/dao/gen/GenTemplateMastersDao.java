package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.TemplateMastersEntity;
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
 * テンプレートのマスタ
 * this class is auto generate and not edit.
 * if modify dao method, you can edit TemplateMastersDao.
 */
@DI(instance = Instance.Singleton)
public class GenTemplateMastersDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenTemplateMastersDao get() {
        return Container.getComp(GenTemplateMastersDao.class);
    }

    /**
     * Select all data.
     * @return all data
     */
    public List<TemplateMastersEntity> physicalSelectAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateMastersDao/TemplateMastersDao_physical_select_all.sql");
        return executeQueryList(sql, TemplateMastersEntity.class);
    }
    /**
     * Select all data with pager.
     * @param limit limit
     * @param offset offset
     * @return all data on limit and offset
     */
    public List<TemplateMastersEntity> physicalSelectAllWithPager(int limit, int offset) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateMastersDao/TemplateMastersDao_physical_select_all_with_pager.sql");
        return executeQueryList(sql, TemplateMastersEntity.class, limit, offset);
    }
    /**
     * Select data on key.
     * @param  typeId typeId
     * @return data
     */
    public TemplateMastersEntity physicalSelectOnKey(Integer typeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateMastersDao/TemplateMastersDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, TemplateMastersEntity.class, typeId);
    }
    /**
     * Select all data that not deleted.
     * @return all data
     */
    public List<TemplateMastersEntity> selectAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateMastersDao/TemplateMastersDao_select_all.sql");
        return executeQueryList(sql, TemplateMastersEntity.class);
    }
    /**
     * Select all data that not deleted with pager.
     * @param limit limit
     * @param offset offset
     * @return all data
     */
    public List<TemplateMastersEntity> selectAllWidthPager(int limit, int offset) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateMastersDao/TemplateMastersDao_select_all_with_pager.sql");
        return executeQueryList(sql, TemplateMastersEntity.class, limit, offset);
    }
    /**
     * Select count that not deleted.
     * @return count
     */
    public Integer selectCountAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateMastersDao/TemplateMastersDao_select_count_all.sql");
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Select data that not deleted on key.
     * @param  typeId typeId
     * @return data
     */
    public TemplateMastersEntity selectOnKey(Integer typeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateMastersDao/TemplateMastersDao_select_on_key.sql");
        return executeQuerySingle(sql, TemplateMastersEntity.class, typeId);
    }
    /**
     * Physical Insert.
     * it is not create key on database sequence.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public TemplateMastersEntity rawPhysicalInsert(TemplateMastersEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateMastersDao/TemplateMastersDao_raw_insert.sql");
        executeUpdate(sql, 
            entity.getTypeId(), 
            entity.getTypeName(), 
            entity.getTypeIcon(), 
            entity.getDescription(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag());
        String driverClass = ConnectionManager.getInstance().getDriverClass(getConnectionName());
        if (ORMappingParameter.DRIVER_NAME_POSTGRESQL.equals(driverClass)) {
            String setValSql = "select setval('TEMPLATE_MASTERS_TYPE_ID_seq', (select max(TYPE_ID) from TEMPLATE_MASTERS));";
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
    public TemplateMastersEntity physicalInsert(TemplateMastersEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateMastersDao/TemplateMastersDao_insert.sql");
        Class<?> type = PropertyUtil.getPropertyType(entity, "typeId");
        Object key = executeInsert(sql, type, 
            entity.getTypeName(), 
            entity.getTypeIcon(), 
            entity.getDescription(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag());
        PropertyUtil.setPropertyValue(entity, "typeId", key);
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
    public TemplateMastersEntity insert(Integer user, TemplateMastersEntity entity) {
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
    public TemplateMastersEntity insert(TemplateMastersEntity entity) {
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
    public TemplateMastersEntity physicalUpdate(TemplateMastersEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateMastersDao/TemplateMastersDao_update.sql");
        executeUpdate(sql, 
            entity.getTypeName(), 
            entity.getTypeIcon(), 
            entity.getDescription(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag(), 
            entity.getTypeId());
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
    public TemplateMastersEntity update(Integer user, TemplateMastersEntity entity) {
        TemplateMastersEntity db = selectOnKey(entity.getTypeId());
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
    public TemplateMastersEntity update(TemplateMastersEntity entity) {
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
    public TemplateMastersEntity save(Integer user, TemplateMastersEntity entity) {
        TemplateMastersEntity db = selectOnKey(entity.getTypeId());
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
    public TemplateMastersEntity save(TemplateMastersEntity entity) {
        TemplateMastersEntity db = selectOnKey(entity.getTypeId());
        if (db == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }
    /**
     * Physical Delete.
     * @param  typeId typeId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(Integer typeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateMastersDao/TemplateMastersDao_delete.sql");
        executeUpdate(sql, typeId);
    }
    /**
     * Physical Delete.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(TemplateMastersEntity entity) {
        physicalDelete(entity.getTypeId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param  typeId typeId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, Integer typeId) {
        TemplateMastersEntity db = selectOnKey(typeId);
        db.setDeleteFlag(1);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
        physicalUpdate(db);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * @param  typeId typeId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer typeId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        delete(user, typeId);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, TemplateMastersEntity entity) {
        delete(user, entity.getTypeId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(TemplateMastersEntity entity) {
        delete(entity.getTypeId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param  typeId typeId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, Integer typeId) {
        TemplateMastersEntity db = physicalSelectOnKey(typeId);
        db.setDeleteFlag(0);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
        physicalUpdate(db);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param  typeId typeId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer typeId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        activation(user, typeId);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, TemplateMastersEntity entity) {
        activation(user, entity.getTypeId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(TemplateMastersEntity entity) {
        activation(entity.getTypeId());

    }

}
