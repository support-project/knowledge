package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.KnowledgeEditUsersEntity;
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
 * 編集可能なユーザ
 * this class is auto generate and not edit.
 * if modify dao method, you can edit KnowledgeEditUsersDao.
 */
@DI(instance = Instance.Singleton)
public class GenKnowledgeEditUsersDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenKnowledgeEditUsersDao get() {
        return Container.getComp(GenKnowledgeEditUsersDao.class);
    }

    /**
     * Select all data.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeEditUsersEntity> physicalSelectAll() { 
        return physicalSelectAll(Order.DESC);
    }
    /**
     * Select all data.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeEditUsersEntity> physicalSelectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditUsersDao/KnowledgeEditUsersDao_physical_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, KnowledgeEditUsersEntity.class);
    }
    /**
     * Select all data with pager.
     * @param limit limit
     * @param offset offset
     * @return all data on limit and offset
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeEditUsersEntity> physicalSelectAllWithPager(int limit, int offset) { 
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
    public List<KnowledgeEditUsersEntity> physicalSelectAllWithPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditUsersDao/KnowledgeEditUsersDao_physical_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, KnowledgeEditUsersEntity.class, limit, offset);
    }
    /**
     * Select data on key.
     * @param  knowledgeId knowledgeId
     * @param  userId userId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public KnowledgeEditUsersEntity physicalSelectOnKey(Long knowledgeId, Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditUsersDao/KnowledgeEditUsersDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, KnowledgeEditUsersEntity.class, knowledgeId, userId);
    }
    /**
     * Select all data that not deleted.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeEditUsersEntity> selectAll() { 
        return selectAll(Order.DESC);
    }
    /**
     * Select all data that not deleted.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeEditUsersEntity> selectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditUsersDao/KnowledgeEditUsersDao_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, KnowledgeEditUsersEntity.class);
    }
    /**
     * Select all data that not deleted with pager.
     * @param limit limit
     * @param offset offset
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeEditUsersEntity> selectAllWidthPager(int limit, int offset) { 
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
    public List<KnowledgeEditUsersEntity> selectAllWidthPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditUsersDao/KnowledgeEditUsersDao_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, KnowledgeEditUsersEntity.class, limit, offset);
    }
    /**
     * Select count that not deleted.
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer selectCountAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditUsersDao/KnowledgeEditUsersDao_select_count_all.sql");
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Select data that not deleted on key.
     * @param  knowledgeId knowledgeId
     * @param  userId userId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public KnowledgeEditUsersEntity selectOnKey(Long knowledgeId, Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditUsersDao/KnowledgeEditUsersDao_select_on_key.sql");
        return executeQuerySingle(sql, KnowledgeEditUsersEntity.class, knowledgeId, userId);
    }
    /**
     * Select data that not deleted on KNOWLEDGE_ID column.
     * @param knowledgeId knowledgeId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeEditUsersEntity> selectOnKnowledgeId(Long knowledgeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditUsersDao/KnowledgeEditUsersDao_select_on_knowledge_id.sql");
        return executeQueryList(sql, KnowledgeEditUsersEntity.class, knowledgeId);
    }
    /**
     * Select data that not deleted on USER_ID column.
     * @param userId userId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeEditUsersEntity> selectOnUserId(Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditUsersDao/KnowledgeEditUsersDao_select_on_user_id.sql");
        return executeQueryList(sql, KnowledgeEditUsersEntity.class, userId);
    }
    /**
     * Select data on KNOWLEDGE_ID column.
     * @param knowledgeId knowledgeId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeEditUsersEntity> physicalSelectOnKnowledgeId(Long knowledgeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditUsersDao/KnowledgeEditUsersDao_physical_select_on_knowledge_id.sql");
        return executeQueryList(sql, KnowledgeEditUsersEntity.class, knowledgeId);
    }
    /**
     * Select data on USER_ID column.
     * @param userId userId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeEditUsersEntity> physicalSelectOnUserId(Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditUsersDao/KnowledgeEditUsersDao_physical_select_on_user_id.sql");
        return executeQueryList(sql, KnowledgeEditUsersEntity.class, userId);
    }
    /**
     * Count all data
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int physicalCountAll() {
        String sql = "SELECT COUNT(*) FROM KNOWLEDGE_EDIT_USERS";
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Physical Insert.
     * it is not create key on database sequence.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public KnowledgeEditUsersEntity rawPhysicalInsert(KnowledgeEditUsersEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditUsersDao/KnowledgeEditUsersDao_raw_insert.sql");
        executeUpdate(sql, 
            entity.getKnowledgeId(), 
            entity.getUserId(), 
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
    public KnowledgeEditUsersEntity physicalInsert(KnowledgeEditUsersEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditUsersDao/KnowledgeEditUsersDao_insert.sql");
        executeUpdate(sql, 
            entity.getKnowledgeId(), 
            entity.getUserId(), 
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
    public KnowledgeEditUsersEntity insert(Integer user, KnowledgeEditUsersEntity entity) {
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
    public KnowledgeEditUsersEntity insert(KnowledgeEditUsersEntity entity) {
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
    public KnowledgeEditUsersEntity physicalUpdate(KnowledgeEditUsersEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditUsersDao/KnowledgeEditUsersDao_update.sql");
        executeUpdate(sql, 
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
    public KnowledgeEditUsersEntity update(Integer user, KnowledgeEditUsersEntity entity) {
        KnowledgeEditUsersEntity db = selectOnKey(entity.getKnowledgeId(), entity.getUserId());
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
    public KnowledgeEditUsersEntity update(KnowledgeEditUsersEntity entity) {
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
    public KnowledgeEditUsersEntity save(Integer user, KnowledgeEditUsersEntity entity) {
        KnowledgeEditUsersEntity db = selectOnKey(entity.getKnowledgeId(), entity.getUserId());
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
    public KnowledgeEditUsersEntity save(KnowledgeEditUsersEntity entity) {
        KnowledgeEditUsersEntity db = selectOnKey(entity.getKnowledgeId(), entity.getUserId());
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
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditUsersDao/KnowledgeEditUsersDao_delete.sql");
        executeUpdate(sql, knowledgeId, userId);
    }
    /**
     * Physical Delete.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(KnowledgeEditUsersEntity entity) {
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
        KnowledgeEditUsersEntity db = selectOnKey(knowledgeId, userId);
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
    public void delete(Integer user, KnowledgeEditUsersEntity entity) {
        delete(user, entity.getKnowledgeId(), entity.getUserId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(KnowledgeEditUsersEntity entity) {
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
        KnowledgeEditUsersEntity db = physicalSelectOnKey(knowledgeId, userId);
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
    public void activation(Integer user, KnowledgeEditUsersEntity entity) {
        activation(user, entity.getKnowledgeId(), entity.getUserId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(KnowledgeEditUsersEntity entity) {
        activation(entity.getKnowledgeId(), entity.getUserId());

    }

}
