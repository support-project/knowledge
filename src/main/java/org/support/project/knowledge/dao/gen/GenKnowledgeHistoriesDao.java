package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.KnowledgeHistoriesEntity;
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
 * ナレッジ更新履歴
 * this class is auto generate and not edit.
 * if modify dao method, you can edit KnowledgeHistoriesDao.
 */
@DI(instance = Instance.Singleton)
public class GenKnowledgeHistoriesDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenKnowledgeHistoriesDao get() {
        return Container.getComp(GenKnowledgeHistoriesDao.class);
    }

    /**
     * Select all data.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeHistoriesEntity> physicalSelectAll() { 
        return physicalSelectAll(Order.DESC);
    }
    /**
     * Select all data.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeHistoriesEntity> physicalSelectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeHistoriesDao/KnowledgeHistoriesDao_physical_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, KnowledgeHistoriesEntity.class);
    }
    /**
     * Select all data with pager.
     * @param limit limit
     * @param offset offset
     * @return all data on limit and offset
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeHistoriesEntity> physicalSelectAllWithPager(int limit, int offset) { 
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
    public List<KnowledgeHistoriesEntity> physicalSelectAllWithPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeHistoriesDao/KnowledgeHistoriesDao_physical_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, KnowledgeHistoriesEntity.class, limit, offset);
    }
    /**
     * Select data on key.
     * @param  historyNo historyNo
     * @param  knowledgeId knowledgeId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public KnowledgeHistoriesEntity physicalSelectOnKey(Integer historyNo, Long knowledgeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeHistoriesDao/KnowledgeHistoriesDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, KnowledgeHistoriesEntity.class, historyNo, knowledgeId);
    }
    /**
     * Select all data that not deleted.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeHistoriesEntity> selectAll() { 
        return selectAll(Order.DESC);
    }
    /**
     * Select all data that not deleted.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeHistoriesEntity> selectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeHistoriesDao/KnowledgeHistoriesDao_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, KnowledgeHistoriesEntity.class);
    }
    /**
     * Select all data that not deleted with pager.
     * @param limit limit
     * @param offset offset
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeHistoriesEntity> selectAllWidthPager(int limit, int offset) { 
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
    public List<KnowledgeHistoriesEntity> selectAllWidthPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeHistoriesDao/KnowledgeHistoriesDao_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, KnowledgeHistoriesEntity.class, limit, offset);
    }
    /**
     * Select count that not deleted.
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer selectCountAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeHistoriesDao/KnowledgeHistoriesDao_select_count_all.sql");
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Select data that not deleted on key.
     * @param  historyNo historyNo
     * @param  knowledgeId knowledgeId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public KnowledgeHistoriesEntity selectOnKey(Integer historyNo, Long knowledgeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeHistoriesDao/KnowledgeHistoriesDao_select_on_key.sql");
        return executeQuerySingle(sql, KnowledgeHistoriesEntity.class, historyNo, knowledgeId);
    }
    /**
     * Select data that not deleted on HISTORY_NO column.
     * @param historyNo historyNo
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeHistoriesEntity> selectOnHistoryNo(Integer historyNo) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeHistoriesDao/KnowledgeHistoriesDao_select_on_history_no.sql");
        return executeQueryList(sql, KnowledgeHistoriesEntity.class, historyNo);
    }
    /**
     * Select data that not deleted on KNOWLEDGE_ID column.
     * @param knowledgeId knowledgeId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeHistoriesEntity> selectOnKnowledgeId(Long knowledgeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeHistoriesDao/KnowledgeHistoriesDao_select_on_knowledge_id.sql");
        return executeQueryList(sql, KnowledgeHistoriesEntity.class, knowledgeId);
    }
    /**
     * Select data on HISTORY_NO column.
     * @param historyNo historyNo
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeHistoriesEntity> physicalSelectOnHistoryNo(Integer historyNo) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeHistoriesDao/KnowledgeHistoriesDao_physical_select_on_history_no.sql");
        return executeQueryList(sql, KnowledgeHistoriesEntity.class, historyNo);
    }
    /**
     * Select data on KNOWLEDGE_ID column.
     * @param knowledgeId knowledgeId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeHistoriesEntity> physicalSelectOnKnowledgeId(Long knowledgeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeHistoriesDao/KnowledgeHistoriesDao_physical_select_on_knowledge_id.sql");
        return executeQueryList(sql, KnowledgeHistoriesEntity.class, knowledgeId);
    }
    /**
     * Count all data
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int physicalCountAll() {
        String sql = "SELECT COUNT(*) FROM KNOWLEDGE_HISTORIES";
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Physical Insert.
     * it is not create key on database sequence.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public KnowledgeHistoriesEntity rawPhysicalInsert(KnowledgeHistoriesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeHistoriesDao/KnowledgeHistoriesDao_raw_insert.sql");
        executeUpdate(sql, 
            entity.getHistoryNo(), 
            entity.getKnowledgeId(), 
            entity.getTitle(), 
            entity.getContent(), 
            entity.getPublicFlag(), 
            entity.getTagIds(), 
            entity.getTagNames(), 
            entity.getLikeCount(), 
            entity.getCommentCount(), 
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
    public KnowledgeHistoriesEntity physicalInsert(KnowledgeHistoriesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeHistoriesDao/KnowledgeHistoriesDao_insert.sql");
        executeUpdate(sql, 
            entity.getHistoryNo(), 
            entity.getKnowledgeId(), 
            entity.getTitle(), 
            entity.getContent(), 
            entity.getPublicFlag(), 
            entity.getTagIds(), 
            entity.getTagNames(), 
            entity.getLikeCount(), 
            entity.getCommentCount(), 
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
    public KnowledgeHistoriesEntity insert(Integer user, KnowledgeHistoriesEntity entity) {
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
    public KnowledgeHistoriesEntity insert(KnowledgeHistoriesEntity entity) {
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
    public KnowledgeHistoriesEntity physicalUpdate(KnowledgeHistoriesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeHistoriesDao/KnowledgeHistoriesDao_update.sql");
        executeUpdate(sql, 
            entity.getTitle(), 
            entity.getContent(), 
            entity.getPublicFlag(), 
            entity.getTagIds(), 
            entity.getTagNames(), 
            entity.getLikeCount(), 
            entity.getCommentCount(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag(), 
            entity.getHistoryNo(), 
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
    public KnowledgeHistoriesEntity update(Integer user, KnowledgeHistoriesEntity entity) {
        KnowledgeHistoriesEntity db = selectOnKey(entity.getHistoryNo(), entity.getKnowledgeId());
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
    public KnowledgeHistoriesEntity update(KnowledgeHistoriesEntity entity) {
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
    public KnowledgeHistoriesEntity save(Integer user, KnowledgeHistoriesEntity entity) {
        KnowledgeHistoriesEntity db = selectOnKey(entity.getHistoryNo(), entity.getKnowledgeId());
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
    public KnowledgeHistoriesEntity save(KnowledgeHistoriesEntity entity) {
        KnowledgeHistoriesEntity db = selectOnKey(entity.getHistoryNo(), entity.getKnowledgeId());
        if (db == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }
    /**
     * Physical Delete.
     * @param  historyNo historyNo
     * @param  knowledgeId knowledgeId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(Integer historyNo, Long knowledgeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeHistoriesDao/KnowledgeHistoriesDao_delete.sql");
        executeUpdate(sql, historyNo, knowledgeId);
    }
    /**
     * Physical Delete.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(KnowledgeHistoriesEntity entity) {
        physicalDelete(entity.getHistoryNo(), entity.getKnowledgeId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param  historyNo historyNo
     * @param  knowledgeId knowledgeId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, Integer historyNo, Long knowledgeId) {
        KnowledgeHistoriesEntity db = selectOnKey(historyNo, knowledgeId);
        db.setDeleteFlag(1);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * @param  historyNo historyNo
     * @param  knowledgeId knowledgeId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer historyNo, Long knowledgeId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        delete(user, historyNo, knowledgeId);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, KnowledgeHistoriesEntity entity) {
        delete(user, entity.getHistoryNo(), entity.getKnowledgeId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(KnowledgeHistoriesEntity entity) {
        delete(entity.getHistoryNo(), entity.getKnowledgeId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param  historyNo historyNo
     * @param  knowledgeId knowledgeId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, Integer historyNo, Long knowledgeId) {
        KnowledgeHistoriesEntity db = physicalSelectOnKey(historyNo, knowledgeId);
        db.setDeleteFlag(0);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param  historyNo historyNo
     * @param  knowledgeId knowledgeId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer historyNo, Long knowledgeId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        activation(user, historyNo, knowledgeId);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, KnowledgeHistoriesEntity entity) {
        activation(user, entity.getHistoryNo(), entity.getKnowledgeId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(KnowledgeHistoriesEntity entity) {
        activation(entity.getHistoryNo(), entity.getKnowledgeId());

    }

}
