package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.ViewHistoriesEntity;
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
 * ナレッジの参照履歴
 * this class is auto generate and not edit.
 * if modify dao method, you can edit ViewHistoriesDao.
 */
@DI(instance = Instance.Singleton)
public class GenViewHistoriesDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenViewHistoriesDao get() {
        return Container.getComp(GenViewHistoriesDao.class);
    }

    /**
     * Select all data.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ViewHistoriesEntity> physicalSelectAll() { 
        return physicalSelectAll(Order.DESC);
    }
    /**
     * Select all data.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ViewHistoriesEntity> physicalSelectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ViewHistoriesDao/ViewHistoriesDao_physical_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, ViewHistoriesEntity.class);
    }
    /**
     * Select all data with pager.
     * @param limit limit
     * @param offset offset
     * @return all data on limit and offset
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ViewHistoriesEntity> physicalSelectAllWithPager(int limit, int offset) { 
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
    public List<ViewHistoriesEntity> physicalSelectAllWithPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ViewHistoriesDao/ViewHistoriesDao_physical_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, ViewHistoriesEntity.class, limit, offset);
    }
    /**
     * Select data on key.
     * @param  historyNo historyNo
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public ViewHistoriesEntity physicalSelectOnKey(Long historyNo) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ViewHistoriesDao/ViewHistoriesDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, ViewHistoriesEntity.class, historyNo);
    }
    /**
     * Select all data that not deleted.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ViewHistoriesEntity> selectAll() { 
        return selectAll(Order.DESC);
    }
    /**
     * Select all data that not deleted.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ViewHistoriesEntity> selectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ViewHistoriesDao/ViewHistoriesDao_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, ViewHistoriesEntity.class);
    }
    /**
     * Select all data that not deleted with pager.
     * @param limit limit
     * @param offset offset
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ViewHistoriesEntity> selectAllWidthPager(int limit, int offset) { 
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
    public List<ViewHistoriesEntity> selectAllWidthPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ViewHistoriesDao/ViewHistoriesDao_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, ViewHistoriesEntity.class, limit, offset);
    }
    /**
     * Select count that not deleted.
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer selectCountAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ViewHistoriesDao/ViewHistoriesDao_select_count_all.sql");
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Select data that not deleted on key.
     * @param  historyNo historyNo
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public ViewHistoriesEntity selectOnKey(Long historyNo) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ViewHistoriesDao/ViewHistoriesDao_select_on_key.sql");
        return executeQuerySingle(sql, ViewHistoriesEntity.class, historyNo);
    }
    /**
     * Count all data
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int physicalCountAll() {
        String sql = "SELECT COUNT(*) FROM VIEW_HISTORIES";
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Physical Insert.
     * it is not create key on database sequence.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public ViewHistoriesEntity rawPhysicalInsert(ViewHistoriesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ViewHistoriesDao/ViewHistoriesDao_raw_insert.sql");
        executeUpdate(sql, 
            entity.getHistoryNo(), 
            entity.getKnowledgeId(), 
            entity.getViewDateTime(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag());
        String driverClass = ConnectionManager.getInstance().getDriverClass(getConnectionName());
        if (ORMappingParameter.DRIVER_NAME_POSTGRESQL.equals(driverClass)) {
            String maxSql = "SELECT MAX(HISTORY_NO) from VIEW_HISTORIES;";
            long max = executeQuerySingle(maxSql, Long.class);
            if (max < 1) {
                max = 1;
            }
            String setValSql = "SELECT SETVAL('VIEW_HISTORIES_HISTORY_NO_seq', ?);";
            executeQuerySingle(setValSql, Long.class, max);
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
    public ViewHistoriesEntity physicalInsert(ViewHistoriesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ViewHistoriesDao/ViewHistoriesDao_insert.sql");
        Class<?> type = PropertyUtil.getPropertyType(entity, "historyNo");
        Object key = executeInsert(sql, type, 
            entity.getKnowledgeId(), 
            entity.getViewDateTime(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag());
        PropertyUtil.setPropertyValue(entity, "historyNo", key);
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
    public ViewHistoriesEntity insert(Integer user, ViewHistoriesEntity entity) {
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
    public ViewHistoriesEntity insert(ViewHistoriesEntity entity) {
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
    public ViewHistoriesEntity physicalUpdate(ViewHistoriesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ViewHistoriesDao/ViewHistoriesDao_update.sql");
        executeUpdate(sql, 
            entity.getKnowledgeId(), 
            entity.getViewDateTime(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag(), 
            entity.getHistoryNo());
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
    public ViewHistoriesEntity update(Integer user, ViewHistoriesEntity entity) {
        ViewHistoriesEntity db = selectOnKey(entity.getHistoryNo());
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
    public ViewHistoriesEntity update(ViewHistoriesEntity entity) {
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
    public ViewHistoriesEntity save(Integer user, ViewHistoriesEntity entity) {
        ViewHistoriesEntity db = selectOnKey(entity.getHistoryNo());
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
    public ViewHistoriesEntity save(ViewHistoriesEntity entity) {
        ViewHistoriesEntity db = selectOnKey(entity.getHistoryNo());
        if (db == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }
    /**
     * Physical Delete.
     * @param  historyNo historyNo
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(Long historyNo) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ViewHistoriesDao/ViewHistoriesDao_delete.sql");
        executeUpdate(sql, historyNo);
    }
    /**
     * Physical Delete.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(ViewHistoriesEntity entity) {
        physicalDelete(entity.getHistoryNo());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param  historyNo historyNo
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, Long historyNo) {
        ViewHistoriesEntity db = selectOnKey(historyNo);
        db.setDeleteFlag(1);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * @param  historyNo historyNo
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Long historyNo) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        delete(user, historyNo);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, ViewHistoriesEntity entity) {
        delete(user, entity.getHistoryNo());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(ViewHistoriesEntity entity) {
        delete(entity.getHistoryNo());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param  historyNo historyNo
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, Long historyNo) {
        ViewHistoriesEntity db = physicalSelectOnKey(historyNo);
        db.setDeleteFlag(0);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param  historyNo historyNo
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Long historyNo) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        activation(user, historyNo);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, ViewHistoriesEntity entity) {
        activation(user, entity.getHistoryNo());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(ViewHistoriesEntity entity) {
        activation(entity.getHistoryNo());

    }

}
