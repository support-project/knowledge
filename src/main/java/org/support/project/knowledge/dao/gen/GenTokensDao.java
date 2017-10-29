package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.TokensEntity;
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
 * 認証トークン
 * this class is auto generate and not edit.
 * if modify dao method, you can edit TokensDao.
 */
@DI(instance = Instance.Singleton)
public class GenTokensDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenTokensDao get() {
        return Container.getComp(GenTokensDao.class);
    }

    /**
     * Select all data.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<TokensEntity> physicalSelectAll() { 
        return physicalSelectAll(Order.DESC);
    }
    /**
     * Select all data.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<TokensEntity> physicalSelectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TokensDao/TokensDao_physical_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, TokensEntity.class);
    }
    /**
     * Select all data with pager.
     * @param limit limit
     * @param offset offset
     * @return all data on limit and offset
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<TokensEntity> physicalSelectAllWithPager(int limit, int offset) { 
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
    public List<TokensEntity> physicalSelectAllWithPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TokensDao/TokensDao_physical_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, TokensEntity.class, limit, offset);
    }
    /**
     * Select data on key.
     * @param  token token
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public TokensEntity physicalSelectOnKey(String token) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TokensDao/TokensDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, TokensEntity.class, token);
    }
    /**
     * Select all data that not deleted.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<TokensEntity> selectAll() { 
        return selectAll(Order.DESC);
    }
    /**
     * Select all data that not deleted.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<TokensEntity> selectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TokensDao/TokensDao_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, TokensEntity.class);
    }
    /**
     * Select all data that not deleted with pager.
     * @param limit limit
     * @param offset offset
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<TokensEntity> selectAllWidthPager(int limit, int offset) { 
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
    public List<TokensEntity> selectAllWidthPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TokensDao/TokensDao_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, TokensEntity.class, limit, offset);
    }
    /**
     * Select count that not deleted.
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer selectCountAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TokensDao/TokensDao_select_count_all.sql");
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Select data that not deleted on key.
     * @param  token token
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public TokensEntity selectOnKey(String token) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TokensDao/TokensDao_select_on_key.sql");
        return executeQuerySingle(sql, TokensEntity.class, token);
    }
    /**
     * Count all data
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int physicalCountAll() {
        String sql = "SELECT COUNT(*) FROM TOKENS";
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Physical Insert.
     * it is not create key on database sequence.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public TokensEntity rawPhysicalInsert(TokensEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TokensDao/TokensDao_raw_insert.sql");
        executeUpdate(sql, 
            entity.getToken(), 
            entity.getUserId(), 
            entity.getExpires(), 
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
    public TokensEntity physicalInsert(TokensEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TokensDao/TokensDao_insert.sql");
        executeUpdate(sql, 
            entity.getToken(), 
            entity.getUserId(), 
            entity.getExpires(), 
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
    public TokensEntity insert(Integer user, TokensEntity entity) {
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
    public TokensEntity insert(TokensEntity entity) {
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
    public TokensEntity physicalUpdate(TokensEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TokensDao/TokensDao_update.sql");
        executeUpdate(sql, 
            entity.getUserId(), 
            entity.getExpires(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag(), 
            entity.getToken());
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
    public TokensEntity update(Integer user, TokensEntity entity) {
        TokensEntity db = selectOnKey(entity.getToken());
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
    public TokensEntity update(TokensEntity entity) {
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
    public TokensEntity save(Integer user, TokensEntity entity) {
        TokensEntity db = selectOnKey(entity.getToken());
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
    public TokensEntity save(TokensEntity entity) {
        TokensEntity db = selectOnKey(entity.getToken());
        if (db == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }
    /**
     * Physical Delete.
     * @param  token token
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(String token) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TokensDao/TokensDao_delete.sql");
        executeUpdate(sql, token);
    }
    /**
     * Physical Delete.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(TokensEntity entity) {
        physicalDelete(entity.getToken());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param  token token
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, String token) {
        TokensEntity db = selectOnKey(token);
        db.setDeleteFlag(1);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * @param  token token
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String token) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        delete(user, token);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, TokensEntity entity) {
        delete(user, entity.getToken());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(TokensEntity entity) {
        delete(entity.getToken());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param  token token
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, String token) {
        TokensEntity db = physicalSelectOnKey(token);
        db.setDeleteFlag(0);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param  token token
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String token) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        activation(user, token);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, TokensEntity entity) {
        activation(user, entity.getToken());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(TokensEntity entity) {
        activation(entity.getToken());

    }

}
