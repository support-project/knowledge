package org.support.project.web.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.web.entity.UserGroupsEntity;
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
 * ユーザが所属するグループ
 * this class is auto generate and not edit.
 * if modify dao method, you can edit UserGroupsDao.
 */
@DI(instance = Instance.Singleton)
public class GenUserGroupsDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenUserGroupsDao get() {
        return Container.getComp(GenUserGroupsDao.class);
    }

    /**
     * Select all data.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<UserGroupsEntity> physicalSelectAll() { 
        return physicalSelectAll(Order.DESC);
    }
    /**
     * Select all data.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<UserGroupsEntity> physicalSelectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserGroupsDao/UserGroupsDao_physical_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, UserGroupsEntity.class);
    }
    /**
     * Select all data with pager.
     * @param limit limit
     * @param offset offset
     * @return all data on limit and offset
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<UserGroupsEntity> physicalSelectAllWithPager(int limit, int offset) { 
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
    public List<UserGroupsEntity> physicalSelectAllWithPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserGroupsDao/UserGroupsDao_physical_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, UserGroupsEntity.class, limit, offset);
    }
    /**
     * Select data on key.
     * @param  groupId groupId
     * @param  userId userId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public UserGroupsEntity physicalSelectOnKey(Integer groupId, Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserGroupsDao/UserGroupsDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, UserGroupsEntity.class, groupId, userId);
    }
    /**
     * Select all data that not deleted.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<UserGroupsEntity> selectAll() { 
        return selectAll(Order.DESC);
    }
    /**
     * Select all data that not deleted.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<UserGroupsEntity> selectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserGroupsDao/UserGroupsDao_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, UserGroupsEntity.class);
    }
    /**
     * Select all data that not deleted with pager.
     * @param limit limit
     * @param offset offset
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<UserGroupsEntity> selectAllWidthPager(int limit, int offset) { 
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
    public List<UserGroupsEntity> selectAllWidthPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserGroupsDao/UserGroupsDao_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, UserGroupsEntity.class, limit, offset);
    }
    /**
     * Select count that not deleted.
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer selectCountAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserGroupsDao/UserGroupsDao_select_count_all.sql");
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Select data that not deleted on key.
     * @param  groupId groupId
     * @param  userId userId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public UserGroupsEntity selectOnKey(Integer groupId, Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserGroupsDao/UserGroupsDao_select_on_key.sql");
        return executeQuerySingle(sql, UserGroupsEntity.class, groupId, userId);
    }
    /**
     * Select data that not deleted on GROUP_ID column.
     * @param groupId groupId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<UserGroupsEntity> selectOnGroupId(Integer groupId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserGroupsDao/UserGroupsDao_select_on_group_id.sql");
        return executeQueryList(sql, UserGroupsEntity.class, groupId);
    }
    /**
     * Select data that not deleted on USER_ID column.
     * @param userId userId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<UserGroupsEntity> selectOnUserId(Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserGroupsDao/UserGroupsDao_select_on_user_id.sql");
        return executeQueryList(sql, UserGroupsEntity.class, userId);
    }
    /**
     * Select data on GROUP_ID column.
     * @param groupId groupId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<UserGroupsEntity> physicalSelectOnGroupId(Integer groupId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserGroupsDao/UserGroupsDao_physical_select_on_group_id.sql");
        return executeQueryList(sql, UserGroupsEntity.class, groupId);
    }
    /**
     * Select data on USER_ID column.
     * @param userId userId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<UserGroupsEntity> physicalSelectOnUserId(Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserGroupsDao/UserGroupsDao_physical_select_on_user_id.sql");
        return executeQueryList(sql, UserGroupsEntity.class, userId);
    }
    /**
     * Count all data
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int physicalCountAll() {
        String sql = "SELECT COUNT(*) FROM USER_GROUPS";
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Create row id.
     * @return row id
     */
    protected String createRowId() {
        return IDGen.get().gen("USER_GROUPS");
    }
    /**
     * Physical Insert.
     * it is not create key on database sequence.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public UserGroupsEntity rawPhysicalInsert(UserGroupsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserGroupsDao/UserGroupsDao_raw_insert.sql");
        executeUpdate(sql, 
            entity.getGroupId(), 
            entity.getUserId(), 
            entity.getGroupRole(), 
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
    public UserGroupsEntity physicalInsert(UserGroupsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserGroupsDao/UserGroupsDao_insert.sql");
        executeUpdate(sql, 
            entity.getGroupId(), 
            entity.getUserId(), 
            entity.getGroupRole(), 
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
    public UserGroupsEntity insert(Integer user, UserGroupsEntity entity) {
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
    public UserGroupsEntity insert(UserGroupsEntity entity) {
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
    public UserGroupsEntity physicalUpdate(UserGroupsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserGroupsDao/UserGroupsDao_update.sql");
        executeUpdate(sql, 
            entity.getGroupRole(), 
            entity.getRowId(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag(), 
            entity.getGroupId(), 
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
    public UserGroupsEntity update(Integer user, UserGroupsEntity entity) {
        UserGroupsEntity db = selectOnKey(entity.getGroupId(), entity.getUserId());
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
    public UserGroupsEntity update(UserGroupsEntity entity) {
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
    public UserGroupsEntity save(Integer user, UserGroupsEntity entity) {
        UserGroupsEntity db = selectOnKey(entity.getGroupId(), entity.getUserId());
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
    public UserGroupsEntity save(UserGroupsEntity entity) {
        UserGroupsEntity db = selectOnKey(entity.getGroupId(), entity.getUserId());
        if (db == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }
    /**
     * Physical Delete.
     * @param  groupId groupId
     * @param  userId userId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(Integer groupId, Integer userId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/UserGroupsDao/UserGroupsDao_delete.sql");
        executeUpdate(sql, groupId, userId);
    }
    /**
     * Physical Delete.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(UserGroupsEntity entity) {
        physicalDelete(entity.getGroupId(), entity.getUserId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param  groupId groupId
     * @param  userId userId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, Integer groupId, Integer userId) {
        UserGroupsEntity db = selectOnKey(groupId, userId);
        db.setDeleteFlag(1);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * @param  groupId groupId
     * @param  userId userId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer groupId, Integer userId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        delete(user, groupId, userId);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, UserGroupsEntity entity) {
        delete(user, entity.getGroupId(), entity.getUserId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(UserGroupsEntity entity) {
        delete(entity.getGroupId(), entity.getUserId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param  groupId groupId
     * @param  userId userId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, Integer groupId, Integer userId) {
        UserGroupsEntity db = physicalSelectOnKey(groupId, userId);
        db.setDeleteFlag(0);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param  groupId groupId
     * @param  userId userId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer groupId, Integer userId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        activation(user, groupId, userId);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, UserGroupsEntity entity) {
        activation(user, entity.getGroupId(), entity.getUserId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(UserGroupsEntity entity) {
        activation(entity.getGroupId(), entity.getUserId());

    }

}
