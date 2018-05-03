package org.support.project.web.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.web.entity.ConfirmMailChangesEntity;
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
 * メールアドレス変更確認
 * this class is auto generate and not edit.
 * if modify dao method, you can edit ConfirmMailChangesDao.
 */
@DI(instance = Instance.Singleton)
public class GenConfirmMailChangesDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenConfirmMailChangesDao get() {
        return Container.getComp(GenConfirmMailChangesDao.class);
    }

    /**
     * Select all data.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ConfirmMailChangesEntity> physicalSelectAll() { 
        return physicalSelectAll(Order.DESC);
    }
    /**
     * Select all data.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ConfirmMailChangesEntity> physicalSelectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/ConfirmMailChangesDao/ConfirmMailChangesDao_physical_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, ConfirmMailChangesEntity.class);
    }
    /**
     * Select all data with pager.
     * @param limit limit
     * @param offset offset
     * @return all data on limit and offset
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ConfirmMailChangesEntity> physicalSelectAllWithPager(int limit, int offset) { 
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
    public List<ConfirmMailChangesEntity> physicalSelectAllWithPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/ConfirmMailChangesDao/ConfirmMailChangesDao_physical_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, ConfirmMailChangesEntity.class, limit, offset);
    }
    /**
     * Select data on key.
     * @param  id id
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public ConfirmMailChangesEntity physicalSelectOnKey(String id) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/ConfirmMailChangesDao/ConfirmMailChangesDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, ConfirmMailChangesEntity.class, id);
    }
    /**
     * Select all data that not deleted.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ConfirmMailChangesEntity> selectAll() { 
        return selectAll(Order.DESC);
    }
    /**
     * Select all data that not deleted.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ConfirmMailChangesEntity> selectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/ConfirmMailChangesDao/ConfirmMailChangesDao_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, ConfirmMailChangesEntity.class);
    }
    /**
     * Select all data that not deleted with pager.
     * @param limit limit
     * @param offset offset
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ConfirmMailChangesEntity> selectAllWidthPager(int limit, int offset) { 
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
    public List<ConfirmMailChangesEntity> selectAllWidthPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/ConfirmMailChangesDao/ConfirmMailChangesDao_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, ConfirmMailChangesEntity.class, limit, offset);
    }
    /**
     * Select count that not deleted.
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer selectCountAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/ConfirmMailChangesDao/ConfirmMailChangesDao_select_count_all.sql");
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Select data that not deleted on key.
     * @param  id id
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public ConfirmMailChangesEntity selectOnKey(String id) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/ConfirmMailChangesDao/ConfirmMailChangesDao_select_on_key.sql");
        return executeQuerySingle(sql, ConfirmMailChangesEntity.class, id);
    }
    /**
     * Count all data
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int physicalCountAll() {
        String sql = "SELECT COUNT(*) FROM CONFIRM_MAIL_CHANGES";
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Create row id.
     * @return row id
     */
    protected String createRowId() {
        return IDGen.get().gen("CONFIRM_MAIL_CHANGES");
    }
    /**
     * Physical Insert.
     * it is not create key on database sequence.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public ConfirmMailChangesEntity rawPhysicalInsert(ConfirmMailChangesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/ConfirmMailChangesDao/ConfirmMailChangesDao_raw_insert.sql");
        executeUpdate(sql, 
            entity.getId(), 
            entity.getUserId(), 
            entity.getMailAddress(), 
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
    public ConfirmMailChangesEntity physicalInsert(ConfirmMailChangesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/ConfirmMailChangesDao/ConfirmMailChangesDao_insert.sql");
        executeUpdate(sql, 
            entity.getId(), 
            entity.getUserId(), 
            entity.getMailAddress(), 
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
    public ConfirmMailChangesEntity insert(Integer user, ConfirmMailChangesEntity entity) {
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
    public ConfirmMailChangesEntity insert(ConfirmMailChangesEntity entity) {
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
    public ConfirmMailChangesEntity physicalUpdate(ConfirmMailChangesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/ConfirmMailChangesDao/ConfirmMailChangesDao_update.sql");
        executeUpdate(sql, 
            entity.getUserId(), 
            entity.getMailAddress(), 
            entity.getRowId(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag(), 
            entity.getId());
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
    public ConfirmMailChangesEntity update(Integer user, ConfirmMailChangesEntity entity) {
        ConfirmMailChangesEntity db = selectOnKey(entity.getId());
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
    public ConfirmMailChangesEntity update(ConfirmMailChangesEntity entity) {
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
    public ConfirmMailChangesEntity save(Integer user, ConfirmMailChangesEntity entity) {
        ConfirmMailChangesEntity db = selectOnKey(entity.getId());
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
    public ConfirmMailChangesEntity save(ConfirmMailChangesEntity entity) {
        ConfirmMailChangesEntity db = selectOnKey(entity.getId());
        if (db == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }
    /**
     * Physical Delete.
     * @param  id id
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(String id) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/ConfirmMailChangesDao/ConfirmMailChangesDao_delete.sql");
        executeUpdate(sql, id);
    }
    /**
     * Physical Delete.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(ConfirmMailChangesEntity entity) {
        physicalDelete(entity.getId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param  id id
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, String id) {
        ConfirmMailChangesEntity db = selectOnKey(id);
        db.setDeleteFlag(1);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * @param  id id
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String id) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        delete(user, id);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, ConfirmMailChangesEntity entity) {
        delete(user, entity.getId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(ConfirmMailChangesEntity entity) {
        delete(entity.getId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param  id id
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, String id) {
        ConfirmMailChangesEntity db = physicalSelectOnKey(id);
        db.setDeleteFlag(0);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param  id id
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String id) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        activation(user, id);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, ConfirmMailChangesEntity entity) {
        activation(user, entity.getId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(ConfirmMailChangesEntity entity) {
        activation(entity.getId());

    }

}
