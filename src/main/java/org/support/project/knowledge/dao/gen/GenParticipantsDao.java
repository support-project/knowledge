package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.ParticipantsEntity;
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
 * 参加者
 * this class is auto generate and not edit.
 * if modify dao method, you can edit ParticipantsDao.
 */
@DI(instance = Instance.Singleton)
public class GenParticipantsDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenParticipantsDao get() {
        return Container.getComp(GenParticipantsDao.class);
    }

    /**
     * Select all data.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ParticipantsEntity> physicalSelectAll() { 
        return physicalSelectAll(Order.DESC);
    }
    /**
     * Select all data.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ParticipantsEntity> physicalSelectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ParticipantsDao/ParticipantsDao_physical_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, ParticipantsEntity.class);
    }
    /**
     * Select all data with pager.
     * @param limit limit
     * @param offset offset
     * @return all data on limit and offset
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ParticipantsEntity> physicalSelectAllWithPager(int limit, int offset) { 
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
    public List<ParticipantsEntity> physicalSelectAllWithPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ParticipantsDao/ParticipantsDao_physical_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, ParticipantsEntity.class, limit, offset);
    }
    /**
     * Select data on key.
     * @param  knowledgeId knowledgeId
     * @param  userId userId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public ParticipantsEntity physicalSelectOnKey(Long knowledgeId, Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ParticipantsDao/ParticipantsDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, ParticipantsEntity.class, knowledgeId, userId);
    }
    /**
     * Select all data that not deleted.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ParticipantsEntity> selectAll() { 
        return selectAll(Order.DESC);
    }
    /**
     * Select all data that not deleted.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ParticipantsEntity> selectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ParticipantsDao/ParticipantsDao_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, ParticipantsEntity.class);
    }
    /**
     * Select all data that not deleted with pager.
     * @param limit limit
     * @param offset offset
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ParticipantsEntity> selectAllWidthPager(int limit, int offset) { 
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
    public List<ParticipantsEntity> selectAllWidthPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ParticipantsDao/ParticipantsDao_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, ParticipantsEntity.class, limit, offset);
    }
    /**
     * Select count that not deleted.
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer selectCountAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ParticipantsDao/ParticipantsDao_select_count_all.sql");
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Select data that not deleted on key.
     * @param  knowledgeId knowledgeId
     * @param  userId userId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public ParticipantsEntity selectOnKey(Long knowledgeId, Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ParticipantsDao/ParticipantsDao_select_on_key.sql");
        return executeQuerySingle(sql, ParticipantsEntity.class, knowledgeId, userId);
    }
    /**
     * Select data that not deleted on KNOWLEDGE_ID column.
     * @param knowledgeId knowledgeId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ParticipantsEntity> selectOnKnowledgeId(Long knowledgeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ParticipantsDao/ParticipantsDao_select_on_knowledge_id.sql");
        return executeQueryList(sql, ParticipantsEntity.class, knowledgeId);
    }
    /**
     * Select data that not deleted on USER_ID column.
     * @param userId userId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ParticipantsEntity> selectOnUserId(Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ParticipantsDao/ParticipantsDao_select_on_user_id.sql");
        return executeQueryList(sql, ParticipantsEntity.class, userId);
    }
    /**
     * Select data on KNOWLEDGE_ID column.
     * @param knowledgeId knowledgeId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ParticipantsEntity> physicalSelectOnKnowledgeId(Long knowledgeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ParticipantsDao/ParticipantsDao_physical_select_on_knowledge_id.sql");
        return executeQueryList(sql, ParticipantsEntity.class, knowledgeId);
    }
    /**
     * Select data on USER_ID column.
     * @param userId userId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ParticipantsEntity> physicalSelectOnUserId(Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ParticipantsDao/ParticipantsDao_physical_select_on_user_id.sql");
        return executeQueryList(sql, ParticipantsEntity.class, userId);
    }
    /**
     * Count all data
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int physicalCountAll() {
        String sql = "SELECT COUNT(*) FROM PARTICIPANTS";
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Physical Insert.
     * it is not create key on database sequence.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public ParticipantsEntity rawPhysicalInsert(ParticipantsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ParticipantsDao/ParticipantsDao_raw_insert.sql");
        executeUpdate(sql, 
            entity.getKnowledgeId(), 
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
    public ParticipantsEntity physicalInsert(ParticipantsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ParticipantsDao/ParticipantsDao_insert.sql");
        executeUpdate(sql, 
            entity.getKnowledgeId(), 
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
    public ParticipantsEntity insert(Integer user, ParticipantsEntity entity) {
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
    public ParticipantsEntity insert(ParticipantsEntity entity) {
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
    public ParticipantsEntity physicalUpdate(ParticipantsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ParticipantsDao/ParticipantsDao_update.sql");
        executeUpdate(sql, 
            entity.getStatus(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag(), 
            entity.getKnowledgeId(), 
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
    public ParticipantsEntity update(Integer user, ParticipantsEntity entity) {
        ParticipantsEntity db = selectOnKey(entity.getKnowledgeId(), entity.getUserId());
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
    public ParticipantsEntity update(ParticipantsEntity entity) {
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
    public ParticipantsEntity save(Integer user, ParticipantsEntity entity) {
        ParticipantsEntity db = selectOnKey(entity.getKnowledgeId(), entity.getUserId());
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
    public ParticipantsEntity save(ParticipantsEntity entity) {
        ParticipantsEntity db = selectOnKey(entity.getKnowledgeId(), entity.getUserId());
        if (db == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }
    /**
     * Physical Delete.
     * @param  knowledgeId knowledgeId
     * @param  userId userId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(Long knowledgeId, Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ParticipantsDao/ParticipantsDao_delete.sql");
        executeUpdate(sql, knowledgeId, userId);
    }
    /**
     * Physical Delete.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(ParticipantsEntity entity) {
        physicalDelete(entity.getKnowledgeId(), entity.getUserId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param  knowledgeId knowledgeId
     * @param  userId userId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, Long knowledgeId, Integer userId) {
        ParticipantsEntity db = selectOnKey(knowledgeId, userId);
        db.setDeleteFlag(1);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * @param  knowledgeId knowledgeId
     * @param  userId userId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Long knowledgeId, Integer userId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        delete(user, knowledgeId, userId);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, ParticipantsEntity entity) {
        delete(user, entity.getKnowledgeId(), entity.getUserId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(ParticipantsEntity entity) {
        delete(entity.getKnowledgeId(), entity.getUserId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param  knowledgeId knowledgeId
     * @param  userId userId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, Long knowledgeId, Integer userId) {
        ParticipantsEntity db = physicalSelectOnKey(knowledgeId, userId);
        db.setDeleteFlag(0);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param  knowledgeId knowledgeId
     * @param  userId userId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Long knowledgeId, Integer userId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        activation(user, knowledgeId, userId);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, ParticipantsEntity entity) {
        activation(user, entity.getKnowledgeId(), entity.getUserId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(ParticipantsEntity entity) {
        activation(entity.getKnowledgeId(), entity.getUserId());

    }

}
