package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.StockKnowledgesEntity;
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
 * ストックしたナレッジ
 * this class is auto generate and not edit.
 * if modify dao method, you can edit StockKnowledgesDao.
 */
@DI(instance = Instance.Singleton)
public class GenStockKnowledgesDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenStockKnowledgesDao get() {
        return Container.getComp(GenStockKnowledgesDao.class);
    }

    /**
     * Select all data.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<StockKnowledgesEntity> physicalSelectAll() { 
        return physicalSelectAll(Order.DESC);
    }
    /**
     * Select all data.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<StockKnowledgesEntity> physicalSelectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/StockKnowledgesDao/StockKnowledgesDao_physical_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, StockKnowledgesEntity.class);
    }
    /**
     * Select all data with pager.
     * @param limit limit
     * @param offset offset
     * @return all data on limit and offset
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<StockKnowledgesEntity> physicalSelectAllWithPager(int limit, int offset) { 
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
    public List<StockKnowledgesEntity> physicalSelectAllWithPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/StockKnowledgesDao/StockKnowledgesDao_physical_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, StockKnowledgesEntity.class, limit, offset);
    }
    /**
     * Select data on key.
     * @param  knowledgeId knowledgeId
     * @param  stockId stockId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public StockKnowledgesEntity physicalSelectOnKey(Long knowledgeId, Long stockId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/StockKnowledgesDao/StockKnowledgesDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, StockKnowledgesEntity.class, knowledgeId, stockId);
    }
    /**
     * Select all data that not deleted.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<StockKnowledgesEntity> selectAll() { 
        return selectAll(Order.DESC);
    }
    /**
     * Select all data that not deleted.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<StockKnowledgesEntity> selectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/StockKnowledgesDao/StockKnowledgesDao_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, StockKnowledgesEntity.class);
    }
    /**
     * Select all data that not deleted with pager.
     * @param limit limit
     * @param offset offset
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<StockKnowledgesEntity> selectAllWidthPager(int limit, int offset) { 
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
    public List<StockKnowledgesEntity> selectAllWidthPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/StockKnowledgesDao/StockKnowledgesDao_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, StockKnowledgesEntity.class, limit, offset);
    }
    /**
     * Select count that not deleted.
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer selectCountAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/StockKnowledgesDao/StockKnowledgesDao_select_count_all.sql");
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Select data that not deleted on key.
     * @param  knowledgeId knowledgeId
     * @param  stockId stockId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public StockKnowledgesEntity selectOnKey(Long knowledgeId, Long stockId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/StockKnowledgesDao/StockKnowledgesDao_select_on_key.sql");
        return executeQuerySingle(sql, StockKnowledgesEntity.class, knowledgeId, stockId);
    }
    /**
     * Select data that not deleted on KNOWLEDGE_ID column.
     * @param knowledgeId knowledgeId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<StockKnowledgesEntity> selectOnKnowledgeId(Long knowledgeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/StockKnowledgesDao/StockKnowledgesDao_select_on_knowledge_id.sql");
        return executeQueryList(sql, StockKnowledgesEntity.class, knowledgeId);
    }
    /**
     * Select data that not deleted on STOCK_ID column.
     * @param stockId stockId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<StockKnowledgesEntity> selectOnStockId(Long stockId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/StockKnowledgesDao/StockKnowledgesDao_select_on_stock_id.sql");
        return executeQueryList(sql, StockKnowledgesEntity.class, stockId);
    }
    /**
     * Select data on KNOWLEDGE_ID column.
     * @param knowledgeId knowledgeId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<StockKnowledgesEntity> physicalSelectOnKnowledgeId(Long knowledgeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/StockKnowledgesDao/StockKnowledgesDao_physical_select_on_knowledge_id.sql");
        return executeQueryList(sql, StockKnowledgesEntity.class, knowledgeId);
    }
    /**
     * Select data on STOCK_ID column.
     * @param stockId stockId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<StockKnowledgesEntity> physicalSelectOnStockId(Long stockId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/StockKnowledgesDao/StockKnowledgesDao_physical_select_on_stock_id.sql");
        return executeQueryList(sql, StockKnowledgesEntity.class, stockId);
    }
    /**
     * Count all data
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int physicalCountAll() {
        String sql = "SELECT COUNT(*) FROM STOCK_KNOWLEDGES";
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Physical Insert.
     * it is not create key on database sequence.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public StockKnowledgesEntity rawPhysicalInsert(StockKnowledgesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/StockKnowledgesDao/StockKnowledgesDao_raw_insert.sql");
        executeUpdate(sql, 
            entity.getKnowledgeId(), 
            entity.getStockId(), 
            entity.getComment(), 
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
    public StockKnowledgesEntity physicalInsert(StockKnowledgesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/StockKnowledgesDao/StockKnowledgesDao_insert.sql");
        executeUpdate(sql, 
            entity.getKnowledgeId(), 
            entity.getStockId(), 
            entity.getComment(), 
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
    public StockKnowledgesEntity insert(Integer user, StockKnowledgesEntity entity) {
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
    public StockKnowledgesEntity insert(StockKnowledgesEntity entity) {
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
    public StockKnowledgesEntity physicalUpdate(StockKnowledgesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/StockKnowledgesDao/StockKnowledgesDao_update.sql");
        executeUpdate(sql, 
            entity.getComment(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag(), 
            entity.getKnowledgeId(), 
            entity.getStockId());
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
    public StockKnowledgesEntity update(Integer user, StockKnowledgesEntity entity) {
        StockKnowledgesEntity db = selectOnKey(entity.getKnowledgeId(), entity.getStockId());
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
    public StockKnowledgesEntity update(StockKnowledgesEntity entity) {
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
    public StockKnowledgesEntity save(Integer user, StockKnowledgesEntity entity) {
        StockKnowledgesEntity db = selectOnKey(entity.getKnowledgeId(), entity.getStockId());
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
    public StockKnowledgesEntity save(StockKnowledgesEntity entity) {
        StockKnowledgesEntity db = selectOnKey(entity.getKnowledgeId(), entity.getStockId());
        if (db == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }
    /**
     * Physical Delete.
     * @param  knowledgeId knowledgeId
     * @param  stockId stockId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(Long knowledgeId, Long stockId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/StockKnowledgesDao/StockKnowledgesDao_delete.sql");
        executeUpdate(sql, knowledgeId, stockId);
    }
    /**
     * Physical Delete.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(StockKnowledgesEntity entity) {
        physicalDelete(entity.getKnowledgeId(), entity.getStockId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param  knowledgeId knowledgeId
     * @param  stockId stockId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, Long knowledgeId, Long stockId) {
        StockKnowledgesEntity db = selectOnKey(knowledgeId, stockId);
        db.setDeleteFlag(1);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * @param  knowledgeId knowledgeId
     * @param  stockId stockId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Long knowledgeId, Long stockId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        delete(user, knowledgeId, stockId);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, StockKnowledgesEntity entity) {
        delete(user, entity.getKnowledgeId(), entity.getStockId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(StockKnowledgesEntity entity) {
        delete(entity.getKnowledgeId(), entity.getStockId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param  knowledgeId knowledgeId
     * @param  stockId stockId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, Long knowledgeId, Long stockId) {
        StockKnowledgesEntity db = physicalSelectOnKey(knowledgeId, stockId);
        db.setDeleteFlag(0);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param  knowledgeId knowledgeId
     * @param  stockId stockId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Long knowledgeId, Long stockId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        activation(user, knowledgeId, stockId);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, StockKnowledgesEntity entity) {
        activation(user, entity.getKnowledgeId(), entity.getStockId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(StockKnowledgesEntity entity) {
        activation(entity.getKnowledgeId(), entity.getStockId());

    }

}
