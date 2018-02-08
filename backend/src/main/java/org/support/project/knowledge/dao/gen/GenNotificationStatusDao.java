package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.NotificationStatusEntity;
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
 * いいねの通知状態
 * this class is auto generate and not edit.
 * if modify dao method, you can edit NotificationStatusDao.
 */
@DI(instance = Instance.Singleton)
public class GenNotificationStatusDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenNotificationStatusDao get() {
        return Container.getComp(GenNotificationStatusDao.class);
    }

    /**
     * Select all data.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<NotificationStatusEntity> physicalSelectAll() { 
        return physicalSelectAll(Order.DESC);
    }
    /**
     * Select all data.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<NotificationStatusEntity> physicalSelectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotificationStatusDao/NotificationStatusDao_physical_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, NotificationStatusEntity.class);
    }
    /**
     * Select all data with pager.
     * @param limit limit
     * @param offset offset
     * @return all data on limit and offset
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<NotificationStatusEntity> physicalSelectAllWithPager(int limit, int offset) { 
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
    public List<NotificationStatusEntity> physicalSelectAllWithPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotificationStatusDao/NotificationStatusDao_physical_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, NotificationStatusEntity.class, limit, offset);
    }
    /**
     * Select data on key.
     * @param  targetId targetId
     * @param  type type
     * @param  userId userId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public NotificationStatusEntity physicalSelectOnKey(Long targetId, Integer type, Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotificationStatusDao/NotificationStatusDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, NotificationStatusEntity.class, targetId, type, userId);
    }
    /**
     * Select all data that not deleted.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<NotificationStatusEntity> selectAll() { 
        return selectAll(Order.DESC);
    }
    /**
     * Select all data that not deleted.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<NotificationStatusEntity> selectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotificationStatusDao/NotificationStatusDao_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, NotificationStatusEntity.class);
    }
    /**
     * Select all data that not deleted with pager.
     * @param limit limit
     * @param offset offset
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<NotificationStatusEntity> selectAllWidthPager(int limit, int offset) { 
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
    public List<NotificationStatusEntity> selectAllWidthPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotificationStatusDao/NotificationStatusDao_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, NotificationStatusEntity.class, limit, offset);
    }
    /**
     * Select count that not deleted.
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer selectCountAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotificationStatusDao/NotificationStatusDao_select_count_all.sql");
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Select data that not deleted on key.
     * @param  targetId targetId
     * @param  type type
     * @param  userId userId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public NotificationStatusEntity selectOnKey(Long targetId, Integer type, Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotificationStatusDao/NotificationStatusDao_select_on_key.sql");
        return executeQuerySingle(sql, NotificationStatusEntity.class, targetId, type, userId);
    }
    /**
     * Select data that not deleted on TARGET_ID column.
     * @param targetId targetId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<NotificationStatusEntity> selectOnTargetId(Long targetId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotificationStatusDao/NotificationStatusDao_select_on_target_id.sql");
        return executeQueryList(sql, NotificationStatusEntity.class, targetId);
    }
    /**
     * Select data that not deleted on TYPE column.
     * @param type type
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<NotificationStatusEntity> selectOnType(Integer type) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotificationStatusDao/NotificationStatusDao_select_on_type.sql");
        return executeQueryList(sql, NotificationStatusEntity.class, type);
    }
    /**
     * Select data that not deleted on USER_ID column.
     * @param userId userId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<NotificationStatusEntity> selectOnUserId(Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotificationStatusDao/NotificationStatusDao_select_on_user_id.sql");
        return executeQueryList(sql, NotificationStatusEntity.class, userId);
    }
    /**
     * Select data on TARGET_ID column.
     * @param targetId targetId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<NotificationStatusEntity> physicalSelectOnTargetId(Long targetId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotificationStatusDao/NotificationStatusDao_physical_select_on_target_id.sql");
        return executeQueryList(sql, NotificationStatusEntity.class, targetId);
    }
    /**
     * Select data on TYPE column.
     * @param type type
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<NotificationStatusEntity> physicalSelectOnType(Integer type) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotificationStatusDao/NotificationStatusDao_physical_select_on_type.sql");
        return executeQueryList(sql, NotificationStatusEntity.class, type);
    }
    /**
     * Select data on USER_ID column.
     * @param userId userId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<NotificationStatusEntity> physicalSelectOnUserId(Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotificationStatusDao/NotificationStatusDao_physical_select_on_user_id.sql");
        return executeQueryList(sql, NotificationStatusEntity.class, userId);
    }
    /**
     * Count all data
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int physicalCountAll() {
        String sql = "SELECT COUNT(*) FROM NOTIFICATION_STATUS";
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Physical Insert.
     * it is not create key on database sequence.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public NotificationStatusEntity rawPhysicalInsert(NotificationStatusEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotificationStatusDao/NotificationStatusDao_raw_insert.sql");
        executeUpdate(sql, 
            entity.getTargetId(), 
            entity.getType(), 
            entity.getUserId(), 
            entity.getStatus(), 
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
    public NotificationStatusEntity physicalInsert(NotificationStatusEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotificationStatusDao/NotificationStatusDao_insert.sql");
        executeUpdate(sql, 
            entity.getTargetId(), 
            entity.getType(), 
            entity.getUserId(), 
            entity.getStatus(), 
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
    public NotificationStatusEntity insert(Integer user, NotificationStatusEntity entity) {
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
    public NotificationStatusEntity insert(NotificationStatusEntity entity) {
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
    public NotificationStatusEntity physicalUpdate(NotificationStatusEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotificationStatusDao/NotificationStatusDao_update.sql");
        executeUpdate(sql, 
            entity.getStatus(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag(), 
            entity.getTargetId(), 
            entity.getType(), 
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
    public NotificationStatusEntity update(Integer user, NotificationStatusEntity entity) {
        NotificationStatusEntity db = selectOnKey(entity.getTargetId(), entity.getType(), entity.getUserId());
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
    public NotificationStatusEntity update(NotificationStatusEntity entity) {
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
    public NotificationStatusEntity save(Integer user, NotificationStatusEntity entity) {
        NotificationStatusEntity db = selectOnKey(entity.getTargetId(), entity.getType(), entity.getUserId());
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
    public NotificationStatusEntity save(NotificationStatusEntity entity) {
        NotificationStatusEntity db = selectOnKey(entity.getTargetId(), entity.getType(), entity.getUserId());
        if (db == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }
    /**
     * Physical Delete.
     * @param  targetId targetId
     * @param  type type
     * @param  userId userId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(Long targetId, Integer type, Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotificationStatusDao/NotificationStatusDao_delete.sql");
        executeUpdate(sql, targetId, type, userId);
    }
    /**
     * Physical Delete.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(NotificationStatusEntity entity) {
        physicalDelete(entity.getTargetId(), entity.getType(), entity.getUserId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param  targetId targetId
     * @param  type type
     * @param  userId userId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, Long targetId, Integer type, Integer userId) {
        NotificationStatusEntity db = selectOnKey(targetId, type, userId);
        db.setDeleteFlag(1);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * @param  targetId targetId
     * @param  type type
     * @param  userId userId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Long targetId, Integer type, Integer userId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        delete(user, targetId, type, userId);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, NotificationStatusEntity entity) {
        delete(user, entity.getTargetId(), entity.getType(), entity.getUserId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(NotificationStatusEntity entity) {
        delete(entity.getTargetId(), entity.getType(), entity.getUserId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param  targetId targetId
     * @param  type type
     * @param  userId userId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, Long targetId, Integer type, Integer userId) {
        NotificationStatusEntity db = physicalSelectOnKey(targetId, type, userId);
        db.setDeleteFlag(0);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param  targetId targetId
     * @param  type type
     * @param  userId userId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Long targetId, Integer type, Integer userId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        activation(user, targetId, type, userId);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, NotificationStatusEntity entity) {
        activation(user, entity.getTargetId(), entity.getType(), entity.getUserId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(NotificationStatusEntity entity) {
        activation(entity.getTargetId(), entity.getType(), entity.getUserId());

    }

}
