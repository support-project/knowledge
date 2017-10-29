package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.ItemChoicesEntity;
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
 * 選択肢の値
 * this class is auto generate and not edit.
 * if modify dao method, you can edit ItemChoicesDao.
 */
@DI(instance = Instance.Singleton)
public class GenItemChoicesDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenItemChoicesDao get() {
        return Container.getComp(GenItemChoicesDao.class);
    }

    /**
     * Select all data.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ItemChoicesEntity> physicalSelectAll() { 
        return physicalSelectAll(Order.DESC);
    }
    /**
     * Select all data.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ItemChoicesEntity> physicalSelectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ItemChoicesDao/ItemChoicesDao_physical_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, ItemChoicesEntity.class);
    }
    /**
     * Select all data with pager.
     * @param limit limit
     * @param offset offset
     * @return all data on limit and offset
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ItemChoicesEntity> physicalSelectAllWithPager(int limit, int offset) { 
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
    public List<ItemChoicesEntity> physicalSelectAllWithPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ItemChoicesDao/ItemChoicesDao_physical_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, ItemChoicesEntity.class, limit, offset);
    }
    /**
     * Select data on key.
     * @param  choiceNo choiceNo
     * @param  itemNo itemNo
     * @param  typeId typeId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public ItemChoicesEntity physicalSelectOnKey(Integer choiceNo, Integer itemNo, Integer typeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ItemChoicesDao/ItemChoicesDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, ItemChoicesEntity.class, choiceNo, itemNo, typeId);
    }
    /**
     * Select all data that not deleted.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ItemChoicesEntity> selectAll() { 
        return selectAll(Order.DESC);
    }
    /**
     * Select all data that not deleted.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ItemChoicesEntity> selectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ItemChoicesDao/ItemChoicesDao_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, ItemChoicesEntity.class);
    }
    /**
     * Select all data that not deleted with pager.
     * @param limit limit
     * @param offset offset
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ItemChoicesEntity> selectAllWidthPager(int limit, int offset) { 
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
    public List<ItemChoicesEntity> selectAllWidthPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ItemChoicesDao/ItemChoicesDao_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, ItemChoicesEntity.class, limit, offset);
    }
    /**
     * Select count that not deleted.
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer selectCountAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ItemChoicesDao/ItemChoicesDao_select_count_all.sql");
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Select data that not deleted on key.
     * @param  choiceNo choiceNo
     * @param  itemNo itemNo
     * @param  typeId typeId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public ItemChoicesEntity selectOnKey(Integer choiceNo, Integer itemNo, Integer typeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ItemChoicesDao/ItemChoicesDao_select_on_key.sql");
        return executeQuerySingle(sql, ItemChoicesEntity.class, choiceNo, itemNo, typeId);
    }
    /**
     * Select data that not deleted on CHOICE_NO column.
     * @param choiceNo choiceNo
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ItemChoicesEntity> selectOnChoiceNo(Integer choiceNo) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ItemChoicesDao/ItemChoicesDao_select_on_choice_no.sql");
        return executeQueryList(sql, ItemChoicesEntity.class, choiceNo);
    }
    /**
     * Select data that not deleted on ITEM_NO column.
     * @param itemNo itemNo
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ItemChoicesEntity> selectOnItemNo(Integer itemNo) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ItemChoicesDao/ItemChoicesDao_select_on_item_no.sql");
        return executeQueryList(sql, ItemChoicesEntity.class, itemNo);
    }
    /**
     * Select data that not deleted on TYPE_ID column.
     * @param typeId typeId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ItemChoicesEntity> selectOnTypeId(Integer typeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ItemChoicesDao/ItemChoicesDao_select_on_type_id.sql");
        return executeQueryList(sql, ItemChoicesEntity.class, typeId);
    }
    /**
     * Select data on CHOICE_NO column.
     * @param choiceNo choiceNo
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ItemChoicesEntity> physicalSelectOnChoiceNo(Integer choiceNo) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ItemChoicesDao/ItemChoicesDao_physical_select_on_choice_no.sql");
        return executeQueryList(sql, ItemChoicesEntity.class, choiceNo);
    }
    /**
     * Select data on ITEM_NO column.
     * @param itemNo itemNo
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ItemChoicesEntity> physicalSelectOnItemNo(Integer itemNo) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ItemChoicesDao/ItemChoicesDao_physical_select_on_item_no.sql");
        return executeQueryList(sql, ItemChoicesEntity.class, itemNo);
    }
    /**
     * Select data on TYPE_ID column.
     * @param typeId typeId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ItemChoicesEntity> physicalSelectOnTypeId(Integer typeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ItemChoicesDao/ItemChoicesDao_physical_select_on_type_id.sql");
        return executeQueryList(sql, ItemChoicesEntity.class, typeId);
    }
    /**
     * Count all data
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int physicalCountAll() {
        String sql = "SELECT COUNT(*) FROM ITEM_CHOICES";
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Physical Insert.
     * it is not create key on database sequence.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public ItemChoicesEntity rawPhysicalInsert(ItemChoicesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ItemChoicesDao/ItemChoicesDao_raw_insert.sql");
        executeUpdate(sql, 
            entity.getChoiceNo(), 
            entity.getItemNo(), 
            entity.getTypeId(), 
            entity.getChoiceValue(), 
            entity.getChoiceLabel(), 
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
    public ItemChoicesEntity physicalInsert(ItemChoicesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ItemChoicesDao/ItemChoicesDao_insert.sql");
        executeUpdate(sql, 
            entity.getChoiceNo(), 
            entity.getItemNo(), 
            entity.getTypeId(), 
            entity.getChoiceValue(), 
            entity.getChoiceLabel(), 
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
    public ItemChoicesEntity insert(Integer user, ItemChoicesEntity entity) {
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
    public ItemChoicesEntity insert(ItemChoicesEntity entity) {
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
    public ItemChoicesEntity physicalUpdate(ItemChoicesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ItemChoicesDao/ItemChoicesDao_update.sql");
        executeUpdate(sql, 
            entity.getChoiceValue(), 
            entity.getChoiceLabel(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag(), 
            entity.getChoiceNo(), 
            entity.getItemNo(), 
            entity.getTypeId());
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
    public ItemChoicesEntity update(Integer user, ItemChoicesEntity entity) {
        ItemChoicesEntity db = selectOnKey(entity.getChoiceNo(), entity.getItemNo(), entity.getTypeId());
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
    public ItemChoicesEntity update(ItemChoicesEntity entity) {
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
    public ItemChoicesEntity save(Integer user, ItemChoicesEntity entity) {
        ItemChoicesEntity db = selectOnKey(entity.getChoiceNo(), entity.getItemNo(), entity.getTypeId());
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
    public ItemChoicesEntity save(ItemChoicesEntity entity) {
        ItemChoicesEntity db = selectOnKey(entity.getChoiceNo(), entity.getItemNo(), entity.getTypeId());
        if (db == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }
    /**
     * Physical Delete.
     * @param  choiceNo choiceNo
     * @param  itemNo itemNo
     * @param  typeId typeId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(Integer choiceNo, Integer itemNo, Integer typeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ItemChoicesDao/ItemChoicesDao_delete.sql");
        executeUpdate(sql, choiceNo, itemNo, typeId);
    }
    /**
     * Physical Delete.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(ItemChoicesEntity entity) {
        physicalDelete(entity.getChoiceNo(), entity.getItemNo(), entity.getTypeId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param  choiceNo choiceNo
     * @param  itemNo itemNo
     * @param  typeId typeId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, Integer choiceNo, Integer itemNo, Integer typeId) {
        ItemChoicesEntity db = selectOnKey(choiceNo, itemNo, typeId);
        db.setDeleteFlag(1);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * @param  choiceNo choiceNo
     * @param  itemNo itemNo
     * @param  typeId typeId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer choiceNo, Integer itemNo, Integer typeId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        delete(user, choiceNo, itemNo, typeId);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, ItemChoicesEntity entity) {
        delete(user, entity.getChoiceNo(), entity.getItemNo(), entity.getTypeId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(ItemChoicesEntity entity) {
        delete(entity.getChoiceNo(), entity.getItemNo(), entity.getTypeId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param  choiceNo choiceNo
     * @param  itemNo itemNo
     * @param  typeId typeId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, Integer choiceNo, Integer itemNo, Integer typeId) {
        ItemChoicesEntity db = physicalSelectOnKey(choiceNo, itemNo, typeId);
        db.setDeleteFlag(0);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param  choiceNo choiceNo
     * @param  itemNo itemNo
     * @param  typeId typeId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer choiceNo, Integer itemNo, Integer typeId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        activation(user, choiceNo, itemNo, typeId);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, ItemChoicesEntity entity) {
        activation(user, entity.getChoiceNo(), entity.getItemNo(), entity.getTypeId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(ItemChoicesEntity entity) {
        activation(entity.getChoiceNo(), entity.getItemNo(), entity.getTypeId());

    }

}
