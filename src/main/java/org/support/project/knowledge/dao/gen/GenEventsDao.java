package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.EventsEntity;
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
 * イベント
 * this class is auto generate and not edit.
 * if modify dao method, you can edit EventsDao.
 */
@DI(instance = Instance.Singleton)
public class GenEventsDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenEventsDao get() {
        return Container.getComp(GenEventsDao.class);
    }

    /**
     * Select all data.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<EventsEntity> physicalSelectAll() { 
        return physicalSelectAll(Order.DESC);
    }
    /**
     * Select all data.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<EventsEntity> physicalSelectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/EventsDao/EventsDao_physical_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, EventsEntity.class);
    }
    /**
     * Select all data with pager.
     * @param limit limit
     * @param offset offset
     * @return all data on limit and offset
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<EventsEntity> physicalSelectAllWithPager(int limit, int offset) { 
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
    public List<EventsEntity> physicalSelectAllWithPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/EventsDao/EventsDao_physical_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, EventsEntity.class, limit, offset);
    }
    /**
     * Select data on key.
     * @param  knowledgeId knowledgeId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public EventsEntity physicalSelectOnKey(Long knowledgeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/EventsDao/EventsDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, EventsEntity.class, knowledgeId);
    }
    /**
     * Select all data that not deleted.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<EventsEntity> selectAll() { 
        return selectAll(Order.DESC);
    }
    /**
     * Select all data that not deleted.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<EventsEntity> selectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/EventsDao/EventsDao_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, EventsEntity.class);
    }
    /**
     * Select all data that not deleted with pager.
     * @param limit limit
     * @param offset offset
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<EventsEntity> selectAllWidthPager(int limit, int offset) { 
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
    public List<EventsEntity> selectAllWidthPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/EventsDao/EventsDao_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, EventsEntity.class, limit, offset);
    }
    /**
     * Select count that not deleted.
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer selectCountAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/EventsDao/EventsDao_select_count_all.sql");
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Select data that not deleted on key.
     * @param  knowledgeId knowledgeId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public EventsEntity selectOnKey(Long knowledgeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/EventsDao/EventsDao_select_on_key.sql");
        return executeQuerySingle(sql, EventsEntity.class, knowledgeId);
    }
    /**
     * Count all data
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int physicalCountAll() {
        String sql = "SELECT COUNT(*) FROM EVENTS";
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Physical Insert.
     * it is not create key on database sequence.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public EventsEntity rawPhysicalInsert(EventsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/EventsDao/EventsDao_raw_insert.sql");
        executeUpdate(sql, 
            entity.getKnowledgeId(), 
            entity.getStartDateTime(), 
            entity.getTimeZone(), 
            entity.getNotifyStatus(), 
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
    public EventsEntity physicalInsert(EventsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/EventsDao/EventsDao_insert.sql");
        executeUpdate(sql, 
            entity.getKnowledgeId(), 
            entity.getStartDateTime(), 
            entity.getTimeZone(), 
            entity.getNotifyStatus(), 
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
    public EventsEntity insert(Integer user, EventsEntity entity) {
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
    public EventsEntity insert(EventsEntity entity) {
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
    public EventsEntity physicalUpdate(EventsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/EventsDao/EventsDao_update.sql");
        executeUpdate(sql, 
            entity.getStartDateTime(), 
            entity.getTimeZone(), 
            entity.getNotifyStatus(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag(), 
            entity.getKnowledgeId());
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
    public EventsEntity update(Integer user, EventsEntity entity) {
        EventsEntity db = selectOnKey(entity.getKnowledgeId());
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
    public EventsEntity update(EventsEntity entity) {
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
    public EventsEntity save(Integer user, EventsEntity entity) {
        EventsEntity db = selectOnKey(entity.getKnowledgeId());
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
    public EventsEntity save(EventsEntity entity) {
        EventsEntity db = selectOnKey(entity.getKnowledgeId());
        if (db == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }
    /**
     * Physical Delete.
     * @param  knowledgeId knowledgeId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(Long knowledgeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/EventsDao/EventsDao_delete.sql");
        executeUpdate(sql, knowledgeId);
    }
    /**
     * Physical Delete.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(EventsEntity entity) {
        physicalDelete(entity.getKnowledgeId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param  knowledgeId knowledgeId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, Long knowledgeId) {
        EventsEntity db = selectOnKey(knowledgeId);
        db.setDeleteFlag(1);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * @param  knowledgeId knowledgeId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Long knowledgeId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        delete(user, knowledgeId);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, EventsEntity entity) {
        delete(user, entity.getKnowledgeId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(EventsEntity entity) {
        delete(entity.getKnowledgeId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param  knowledgeId knowledgeId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, Long knowledgeId) {
        EventsEntity db = physicalSelectOnKey(knowledgeId);
        db.setDeleteFlag(0);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param  knowledgeId knowledgeId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Long knowledgeId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        activation(user, knowledgeId);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, EventsEntity entity) {
        activation(user, entity.getKnowledgeId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(EventsEntity entity) {
        activation(entity.getKnowledgeId());

    }

}
