package org.support.project.web.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.web.entity.GroupsEntity;
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
 * グループ
 * this class is auto generate and not edit.
 * if modify dao method, you can edit GroupsDao.
 */
@DI(instance = Instance.Singleton)
public class GenGroupsDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenGroupsDao get() {
        return Container.getComp(GenGroupsDao.class);
    }

    /**
     * Select all data.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<GroupsEntity> physicalSelectAll() { 
        return physicalSelectAll(Order.DESC);
    }
    /**
     * Select all data.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<GroupsEntity> physicalSelectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/GroupsDao/GroupsDao_physical_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, GroupsEntity.class);
    }
    /**
     * Select all data with pager.
     * @param limit limit
     * @param offset offset
     * @return all data on limit and offset
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<GroupsEntity> physicalSelectAllWithPager(int limit, int offset) { 
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
    public List<GroupsEntity> physicalSelectAllWithPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/GroupsDao/GroupsDao_physical_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, GroupsEntity.class, limit, offset);
    }
    /**
     * Select data on key.
     * @param  groupId groupId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public GroupsEntity physicalSelectOnKey(Integer groupId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/GroupsDao/GroupsDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, GroupsEntity.class, groupId);
    }
    /**
     * Select all data that not deleted.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<GroupsEntity> selectAll() { 
        return selectAll(Order.DESC);
    }
    /**
     * Select all data that not deleted.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<GroupsEntity> selectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/GroupsDao/GroupsDao_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, GroupsEntity.class);
    }
    /**
     * Select all data that not deleted with pager.
     * @param limit limit
     * @param offset offset
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<GroupsEntity> selectAllWidthPager(int limit, int offset) { 
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
    public List<GroupsEntity> selectAllWidthPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/GroupsDao/GroupsDao_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, GroupsEntity.class, limit, offset);
    }
    /**
     * Select count that not deleted.
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer selectCountAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/GroupsDao/GroupsDao_select_count_all.sql");
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Select data that not deleted on key.
     * @param  groupId groupId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public GroupsEntity selectOnKey(Integer groupId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/GroupsDao/GroupsDao_select_on_key.sql");
        return executeQuerySingle(sql, GroupsEntity.class, groupId);
    }
    /**
     * Count all data
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int physicalCountAll() {
        String sql = "SELECT COUNT(*) FROM GROUPS";
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Create row id.
     * @return row id
     */
    protected String createRowId() {
        return IDGen.get().gen("GROUPS");
    }
    /**
     * Physical Insert.
     * it is not create key on database sequence.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public GroupsEntity rawPhysicalInsert(GroupsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/GroupsDao/GroupsDao_raw_insert.sql");
        executeUpdate(sql, 
            entity.getGroupId(), 
            entity.getGroupKey(), 
            entity.getGroupName(), 
            entity.getDescription(), 
            entity.getParentGroupKey(), 
            entity.getGroupClass(), 
            entity.getRowId(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag());
        String driverClass = ConnectionManager.getInstance().getDriverClass(getConnectionName());
        if (ORMappingParameter.DRIVER_NAME_POSTGRESQL.equals(driverClass)) {
            String maxSql = "SELECT MAX(GROUP_ID) from GROUPS;";
            long max = executeQuerySingle(maxSql, Long.class);
            if (max < 1) {
                max = 1;
            }
            String setValSql = "SELECT SETVAL('GROUPS_GROUP_ID_seq', ?);";
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
    public GroupsEntity physicalInsert(GroupsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/GroupsDao/GroupsDao_insert.sql");
        Class<?> type = PropertyUtil.getPropertyType(entity, "groupId");
        Object key = executeInsert(sql, type, 
            entity.getGroupKey(), 
            entity.getGroupName(), 
            entity.getDescription(), 
            entity.getParentGroupKey(), 
            entity.getGroupClass(), 
            entity.getRowId(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag());
        PropertyUtil.setPropertyValue(entity, "groupId", key);
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
    public GroupsEntity insert(Integer user, GroupsEntity entity) {
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
    public GroupsEntity insert(GroupsEntity entity) {
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
    public GroupsEntity physicalUpdate(GroupsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/GroupsDao/GroupsDao_update.sql");
        executeUpdate(sql, 
            entity.getGroupKey(), 
            entity.getGroupName(), 
            entity.getDescription(), 
            entity.getParentGroupKey(), 
            entity.getGroupClass(), 
            entity.getRowId(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag(), 
            entity.getGroupId());
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
    public GroupsEntity update(Integer user, GroupsEntity entity) {
        GroupsEntity db = selectOnKey(entity.getGroupId());
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
    public GroupsEntity update(GroupsEntity entity) {
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
    public GroupsEntity save(Integer user, GroupsEntity entity) {
        GroupsEntity db = selectOnKey(entity.getGroupId());
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
    public GroupsEntity save(GroupsEntity entity) {
        GroupsEntity db = selectOnKey(entity.getGroupId());
        if (db == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }
    /**
     * Physical Delete.
     * @param  groupId groupId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(Integer groupId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/GroupsDao/GroupsDao_delete.sql");
        executeUpdate(sql, groupId);
    }
    /**
     * Physical Delete.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(GroupsEntity entity) {
        physicalDelete(entity.getGroupId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param  groupId groupId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, Integer groupId) {
        GroupsEntity db = selectOnKey(groupId);
        db.setDeleteFlag(1);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * @param  groupId groupId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer groupId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        delete(user, groupId);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, GroupsEntity entity) {
        delete(user, entity.getGroupId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(GroupsEntity entity) {
        delete(entity.getGroupId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param  groupId groupId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, Integer groupId) {
        GroupsEntity db = physicalSelectOnKey(groupId);
        db.setDeleteFlag(0);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param  groupId groupId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer groupId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        activation(user, groupId);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, GroupsEntity entity) {
        activation(user, entity.getGroupId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(GroupsEntity entity) {
        activation(entity.getGroupId());

    }

}
