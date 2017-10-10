package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.MailHookIgnoreConditionsEntity;
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
 * メールから投稿の際の除外条件
 * this class is auto generate and not edit.
 * if modify dao method, you can edit MailHookIgnoreConditionsDao.
 */
@DI(instance = Instance.Singleton)
public class GenMailHookIgnoreConditionsDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenMailHookIgnoreConditionsDao get() {
        return Container.getComp(GenMailHookIgnoreConditionsDao.class);
    }

    /**
     * Select all data.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailHookIgnoreConditionsEntity> physicalSelectAll() { 
        return physicalSelectAll(Order.DESC);
    }
    /**
     * Select all data.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailHookIgnoreConditionsEntity> physicalSelectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHookIgnoreConditionsDao/MailHookIgnoreConditionsDao_physical_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, MailHookIgnoreConditionsEntity.class);
    }
    /**
     * Select all data with pager.
     * @param limit limit
     * @param offset offset
     * @return all data on limit and offset
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailHookIgnoreConditionsEntity> physicalSelectAllWithPager(int limit, int offset) { 
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
    public List<MailHookIgnoreConditionsEntity> physicalSelectAllWithPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHookIgnoreConditionsDao/MailHookIgnoreConditionsDao_physical_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, MailHookIgnoreConditionsEntity.class, limit, offset);
    }
    /**
     * Select data on key.
     * @param  conditionNo conditionNo
     * @param  hookId hookId
     * @param  ignoreConditionNo ignoreConditionNo
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public MailHookIgnoreConditionsEntity physicalSelectOnKey(Integer conditionNo, Integer hookId, Integer ignoreConditionNo) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHookIgnoreConditionsDao/MailHookIgnoreConditionsDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, MailHookIgnoreConditionsEntity.class, conditionNo, hookId, ignoreConditionNo);
    }
    /**
     * Select all data that not deleted.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailHookIgnoreConditionsEntity> selectAll() { 
        return selectAll(Order.DESC);
    }
    /**
     * Select all data that not deleted.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailHookIgnoreConditionsEntity> selectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHookIgnoreConditionsDao/MailHookIgnoreConditionsDao_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, MailHookIgnoreConditionsEntity.class);
    }
    /**
     * Select all data that not deleted with pager.
     * @param limit limit
     * @param offset offset
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailHookIgnoreConditionsEntity> selectAllWidthPager(int limit, int offset) { 
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
    public List<MailHookIgnoreConditionsEntity> selectAllWidthPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHookIgnoreConditionsDao/MailHookIgnoreConditionsDao_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, MailHookIgnoreConditionsEntity.class, limit, offset);
    }
    /**
     * Select count that not deleted.
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer selectCountAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHookIgnoreConditionsDao/MailHookIgnoreConditionsDao_select_count_all.sql");
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Select data that not deleted on key.
     * @param  conditionNo conditionNo
     * @param  hookId hookId
     * @param  ignoreConditionNo ignoreConditionNo
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public MailHookIgnoreConditionsEntity selectOnKey(Integer conditionNo, Integer hookId, Integer ignoreConditionNo) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHookIgnoreConditionsDao/MailHookIgnoreConditionsDao_select_on_key.sql");
        return executeQuerySingle(sql, MailHookIgnoreConditionsEntity.class, conditionNo, hookId, ignoreConditionNo);
    }
    /**
     * Select data that not deleted on CONDITION_NO column.
     * @param conditionNo conditionNo
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailHookIgnoreConditionsEntity> selectOnConditionNo(Integer conditionNo) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHookIgnoreConditionsDao/MailHookIgnoreConditionsDao_select_on_condition_no.sql");
        return executeQueryList(sql, MailHookIgnoreConditionsEntity.class, conditionNo);
    }
    /**
     * Select data that not deleted on HOOK_ID column.
     * @param hookId hookId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailHookIgnoreConditionsEntity> selectOnHookId(Integer hookId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHookIgnoreConditionsDao/MailHookIgnoreConditionsDao_select_on_hook_id.sql");
        return executeQueryList(sql, MailHookIgnoreConditionsEntity.class, hookId);
    }
    /**
     * Select data that not deleted on IGNORE_CONDITION_NO column.
     * @param ignoreConditionNo ignoreConditionNo
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailHookIgnoreConditionsEntity> selectOnIgnoreConditionNo(Integer ignoreConditionNo) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHookIgnoreConditionsDao/MailHookIgnoreConditionsDao_select_on_ignore_condition_no.sql");
        return executeQueryList(sql, MailHookIgnoreConditionsEntity.class, ignoreConditionNo);
    }
    /**
     * Select data on CONDITION_NO column.
     * @param conditionNo conditionNo
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailHookIgnoreConditionsEntity> physicalSelectOnConditionNo(Integer conditionNo) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHookIgnoreConditionsDao/MailHookIgnoreConditionsDao_physical_select_on_condition_no.sql");
        return executeQueryList(sql, MailHookIgnoreConditionsEntity.class, conditionNo);
    }
    /**
     * Select data on HOOK_ID column.
     * @param hookId hookId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailHookIgnoreConditionsEntity> physicalSelectOnHookId(Integer hookId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHookIgnoreConditionsDao/MailHookIgnoreConditionsDao_physical_select_on_hook_id.sql");
        return executeQueryList(sql, MailHookIgnoreConditionsEntity.class, hookId);
    }
    /**
     * Select data on IGNORE_CONDITION_NO column.
     * @param ignoreConditionNo ignoreConditionNo
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailHookIgnoreConditionsEntity> physicalSelectOnIgnoreConditionNo(Integer ignoreConditionNo) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHookIgnoreConditionsDao/MailHookIgnoreConditionsDao_physical_select_on_ignore_condition_no.sql");
        return executeQueryList(sql, MailHookIgnoreConditionsEntity.class, ignoreConditionNo);
    }
    /**
     * Count all data
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int physicalCountAll() {
        String sql = "SELECT COUNT(*) FROM MAIL_HOOK_IGNORE_CONDITIONS";
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Physical Insert.
     * it is not create key on database sequence.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public MailHookIgnoreConditionsEntity rawPhysicalInsert(MailHookIgnoreConditionsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHookIgnoreConditionsDao/MailHookIgnoreConditionsDao_raw_insert.sql");
        executeUpdate(sql, 
            entity.getConditionNo(), 
            entity.getHookId(), 
            entity.getIgnoreConditionNo(), 
            entity.getConditionKind(), 
            entity.getCondition(), 
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
    public MailHookIgnoreConditionsEntity physicalInsert(MailHookIgnoreConditionsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHookIgnoreConditionsDao/MailHookIgnoreConditionsDao_insert.sql");
        executeUpdate(sql, 
            entity.getConditionNo(), 
            entity.getHookId(), 
            entity.getIgnoreConditionNo(), 
            entity.getConditionKind(), 
            entity.getCondition(), 
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
    public MailHookIgnoreConditionsEntity insert(Integer user, MailHookIgnoreConditionsEntity entity) {
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
    public MailHookIgnoreConditionsEntity insert(MailHookIgnoreConditionsEntity entity) {
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
    public MailHookIgnoreConditionsEntity physicalUpdate(MailHookIgnoreConditionsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHookIgnoreConditionsDao/MailHookIgnoreConditionsDao_update.sql");
        executeUpdate(sql, 
            entity.getConditionKind(), 
            entity.getCondition(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag(), 
            entity.getConditionNo(), 
            entity.getHookId(), 
            entity.getIgnoreConditionNo());
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
    public MailHookIgnoreConditionsEntity update(Integer user, MailHookIgnoreConditionsEntity entity) {
        MailHookIgnoreConditionsEntity db = selectOnKey(entity.getConditionNo(), entity.getHookId(), entity.getIgnoreConditionNo());
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
    public MailHookIgnoreConditionsEntity update(MailHookIgnoreConditionsEntity entity) {
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
    public MailHookIgnoreConditionsEntity save(Integer user, MailHookIgnoreConditionsEntity entity) {
        MailHookIgnoreConditionsEntity db = selectOnKey(entity.getConditionNo(), entity.getHookId(), entity.getIgnoreConditionNo());
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
    public MailHookIgnoreConditionsEntity save(MailHookIgnoreConditionsEntity entity) {
        MailHookIgnoreConditionsEntity db = selectOnKey(entity.getConditionNo(), entity.getHookId(), entity.getIgnoreConditionNo());
        if (db == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }
    /**
     * Physical Delete.
     * @param  conditionNo conditionNo
     * @param  hookId hookId
     * @param  ignoreConditionNo ignoreConditionNo
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(Integer conditionNo, Integer hookId, Integer ignoreConditionNo) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHookIgnoreConditionsDao/MailHookIgnoreConditionsDao_delete.sql");
        executeUpdate(sql, conditionNo, hookId, ignoreConditionNo);
    }
    /**
     * Physical Delete.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(MailHookIgnoreConditionsEntity entity) {
        physicalDelete(entity.getConditionNo(), entity.getHookId(), entity.getIgnoreConditionNo());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param  conditionNo conditionNo
     * @param  hookId hookId
     * @param  ignoreConditionNo ignoreConditionNo
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, Integer conditionNo, Integer hookId, Integer ignoreConditionNo) {
        MailHookIgnoreConditionsEntity db = selectOnKey(conditionNo, hookId, ignoreConditionNo);
        db.setDeleteFlag(1);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * @param  conditionNo conditionNo
     * @param  hookId hookId
     * @param  ignoreConditionNo ignoreConditionNo
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer conditionNo, Integer hookId, Integer ignoreConditionNo) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        delete(user, conditionNo, hookId, ignoreConditionNo);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, MailHookIgnoreConditionsEntity entity) {
        delete(user, entity.getConditionNo(), entity.getHookId(), entity.getIgnoreConditionNo());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(MailHookIgnoreConditionsEntity entity) {
        delete(entity.getConditionNo(), entity.getHookId(), entity.getIgnoreConditionNo());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param  conditionNo conditionNo
     * @param  hookId hookId
     * @param  ignoreConditionNo ignoreConditionNo
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, Integer conditionNo, Integer hookId, Integer ignoreConditionNo) {
        MailHookIgnoreConditionsEntity db = physicalSelectOnKey(conditionNo, hookId, ignoreConditionNo);
        db.setDeleteFlag(0);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param  conditionNo conditionNo
     * @param  hookId hookId
     * @param  ignoreConditionNo ignoreConditionNo
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer conditionNo, Integer hookId, Integer ignoreConditionNo) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        activation(user, conditionNo, hookId, ignoreConditionNo);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, MailHookIgnoreConditionsEntity entity) {
        activation(user, entity.getConditionNo(), entity.getHookId(), entity.getIgnoreConditionNo());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(MailHookIgnoreConditionsEntity entity) {
        activation(entity.getConditionNo(), entity.getHookId(), entity.getIgnoreConditionNo());

    }

}
