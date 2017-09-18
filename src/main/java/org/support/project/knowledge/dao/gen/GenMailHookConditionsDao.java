package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.MailHookConditionsEntity;
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
 * メールから投稿する条件
 * this class is auto generate and not edit.
 * if modify dao method, you can edit MailHookConditionsDao.
 */
@DI(instance = Instance.Singleton)
public class GenMailHookConditionsDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenMailHookConditionsDao get() {
        return Container.getComp(GenMailHookConditionsDao.class);
    }

    /**
     * Select all data.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailHookConditionsEntity> physicalSelectAll() { 
        return physicalSelectAll(Order.DESC);
    }
    /**
     * Select all data.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailHookConditionsEntity> physicalSelectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHookConditionsDao/MailHookConditionsDao_physical_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, MailHookConditionsEntity.class);
    }
    /**
     * Select all data with pager.
     * @param limit limit
     * @param offset offset
     * @return all data on limit and offset
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailHookConditionsEntity> physicalSelectAllWithPager(int limit, int offset) { 
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
    public List<MailHookConditionsEntity> physicalSelectAllWithPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHookConditionsDao/MailHookConditionsDao_physical_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, MailHookConditionsEntity.class, limit, offset);
    }
    /**
     * Select data on key.
     * @param  conditionNo conditionNo
     * @param  hookId hookId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public MailHookConditionsEntity physicalSelectOnKey(Integer conditionNo, Integer hookId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHookConditionsDao/MailHookConditionsDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, MailHookConditionsEntity.class, conditionNo, hookId);
    }
    /**
     * Select all data that not deleted.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailHookConditionsEntity> selectAll() { 
        return selectAll(Order.DESC);
    }
    /**
     * Select all data that not deleted.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailHookConditionsEntity> selectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHookConditionsDao/MailHookConditionsDao_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, MailHookConditionsEntity.class);
    }
    /**
     * Select all data that not deleted with pager.
     * @param limit limit
     * @param offset offset
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailHookConditionsEntity> selectAllWidthPager(int limit, int offset) { 
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
    public List<MailHookConditionsEntity> selectAllWidthPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHookConditionsDao/MailHookConditionsDao_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, MailHookConditionsEntity.class, limit, offset);
    }
    /**
     * Select count that not deleted.
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer selectCountAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHookConditionsDao/MailHookConditionsDao_select_count_all.sql");
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Select data that not deleted on key.
     * @param  conditionNo conditionNo
     * @param  hookId hookId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public MailHookConditionsEntity selectOnKey(Integer conditionNo, Integer hookId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHookConditionsDao/MailHookConditionsDao_select_on_key.sql");
        return executeQuerySingle(sql, MailHookConditionsEntity.class, conditionNo, hookId);
    }
    /**
     * Select data that not deleted on CONDITION_NO column.
     * @param conditionNo conditionNo
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailHookConditionsEntity> selectOnConditionNo(Integer conditionNo) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHookConditionsDao/MailHookConditionsDao_select_on_condition_no.sql");
        return executeQueryList(sql, MailHookConditionsEntity.class, conditionNo);
    }
    /**
     * Select data that not deleted on HOOK_ID column.
     * @param hookId hookId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailHookConditionsEntity> selectOnHookId(Integer hookId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHookConditionsDao/MailHookConditionsDao_select_on_hook_id.sql");
        return executeQueryList(sql, MailHookConditionsEntity.class, hookId);
    }
    /**
     * Select data on CONDITION_NO column.
     * @param conditionNo conditionNo
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailHookConditionsEntity> physicalSelectOnConditionNo(Integer conditionNo) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHookConditionsDao/MailHookConditionsDao_physical_select_on_condition_no.sql");
        return executeQueryList(sql, MailHookConditionsEntity.class, conditionNo);
    }
    /**
     * Select data on HOOK_ID column.
     * @param hookId hookId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailHookConditionsEntity> physicalSelectOnHookId(Integer hookId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHookConditionsDao/MailHookConditionsDao_physical_select_on_hook_id.sql");
        return executeQueryList(sql, MailHookConditionsEntity.class, hookId);
    }
    /**
     * Count all data
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int physicalCountAll() {
        String sql = "SELECT COUNT(*) FROM MAIL_HOOK_CONDITIONS";
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Physical Insert.
     * it is not create key on database sequence.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public MailHookConditionsEntity rawPhysicalInsert(MailHookConditionsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHookConditionsDao/MailHookConditionsDao_raw_insert.sql");
        executeUpdate(sql, 
            entity.getConditionNo(), 
            entity.getHookId(), 
            entity.getConditionKind(), 
            entity.getCondition(), 
            entity.getProcessUser(), 
            entity.getProcessUserKind(), 
            entity.getPublicFlag(), 
            entity.getTags(), 
            entity.getViewers(), 
            entity.getEditors(), 
            entity.getPostLimit(), 
            entity.getLimitParam(), 
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
    public MailHookConditionsEntity physicalInsert(MailHookConditionsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHookConditionsDao/MailHookConditionsDao_insert.sql");
        executeUpdate(sql, 
            entity.getConditionNo(), 
            entity.getHookId(), 
            entity.getConditionKind(), 
            entity.getCondition(), 
            entity.getProcessUser(), 
            entity.getProcessUserKind(), 
            entity.getPublicFlag(), 
            entity.getTags(), 
            entity.getViewers(), 
            entity.getEditors(), 
            entity.getPostLimit(), 
            entity.getLimitParam(), 
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
    public MailHookConditionsEntity insert(Integer user, MailHookConditionsEntity entity) {
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
    public MailHookConditionsEntity insert(MailHookConditionsEntity entity) {
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
    public MailHookConditionsEntity physicalUpdate(MailHookConditionsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHookConditionsDao/MailHookConditionsDao_update.sql");
        executeUpdate(sql, 
            entity.getConditionKind(), 
            entity.getCondition(), 
            entity.getProcessUser(), 
            entity.getProcessUserKind(), 
            entity.getPublicFlag(), 
            entity.getTags(), 
            entity.getViewers(), 
            entity.getEditors(), 
            entity.getPostLimit(), 
            entity.getLimitParam(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag(), 
            entity.getConditionNo(), 
            entity.getHookId());
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
    public MailHookConditionsEntity update(Integer user, MailHookConditionsEntity entity) {
        MailHookConditionsEntity db = selectOnKey(entity.getConditionNo(), entity.getHookId());
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
    public MailHookConditionsEntity update(MailHookConditionsEntity entity) {
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
    public MailHookConditionsEntity save(Integer user, MailHookConditionsEntity entity) {
        MailHookConditionsEntity db = selectOnKey(entity.getConditionNo(), entity.getHookId());
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
    public MailHookConditionsEntity save(MailHookConditionsEntity entity) {
        MailHookConditionsEntity db = selectOnKey(entity.getConditionNo(), entity.getHookId());
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
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(Integer conditionNo, Integer hookId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailHookConditionsDao/MailHookConditionsDao_delete.sql");
        executeUpdate(sql, conditionNo, hookId);
    }
    /**
     * Physical Delete.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(MailHookConditionsEntity entity) {
        physicalDelete(entity.getConditionNo(), entity.getHookId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param  conditionNo conditionNo
     * @param  hookId hookId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, Integer conditionNo, Integer hookId) {
        MailHookConditionsEntity db = selectOnKey(conditionNo, hookId);
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
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer conditionNo, Integer hookId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        delete(user, conditionNo, hookId);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, MailHookConditionsEntity entity) {
        delete(user, entity.getConditionNo(), entity.getHookId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(MailHookConditionsEntity entity) {
        delete(entity.getConditionNo(), entity.getHookId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param  conditionNo conditionNo
     * @param  hookId hookId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, Integer conditionNo, Integer hookId) {
        MailHookConditionsEntity db = physicalSelectOnKey(conditionNo, hookId);
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
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer conditionNo, Integer hookId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        activation(user, conditionNo, hookId);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, MailHookConditionsEntity entity) {
        activation(user, entity.getConditionNo(), entity.getHookId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(MailHookConditionsEntity entity) {
        activation(entity.getConditionNo(), entity.getHookId());

    }

}
