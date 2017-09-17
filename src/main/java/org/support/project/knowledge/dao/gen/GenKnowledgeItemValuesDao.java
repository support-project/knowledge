package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.KnowledgeItemValuesEntity;
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
 * ナレッジの項目値
 * this class is auto generate and not edit.
 * if modify dao method, you can edit KnowledgeItemValuesDao.
 */
@DI(instance = Instance.Singleton)
public class GenKnowledgeItemValuesDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenKnowledgeItemValuesDao get() {
        return Container.getComp(GenKnowledgeItemValuesDao.class);
    }

    /**
     * Select all data.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeItemValuesEntity> physicalSelectAll() { 
        return physicalSelectAll(Order.DESC);
    }
    /**
     * Select all data.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeItemValuesEntity> physicalSelectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeItemValuesDao/KnowledgeItemValuesDao_physical_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, KnowledgeItemValuesEntity.class);
    }
    /**
     * Select all data with pager.
     * @param limit limit
     * @param offset offset
     * @return all data on limit and offset
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeItemValuesEntity> physicalSelectAllWithPager(int limit, int offset) { 
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
    public List<KnowledgeItemValuesEntity> physicalSelectAllWithPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeItemValuesDao/KnowledgeItemValuesDao_physical_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, KnowledgeItemValuesEntity.class, limit, offset);
    }
    /**
     * Select data on key.
     * @param  itemNo itemNo
     * @param  knowledgeId knowledgeId
     * @param  typeId typeId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public KnowledgeItemValuesEntity physicalSelectOnKey(Integer itemNo, Long knowledgeId, Integer typeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeItemValuesDao/KnowledgeItemValuesDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, KnowledgeItemValuesEntity.class, itemNo, knowledgeId, typeId);
    }
    /**
     * Select all data that not deleted.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeItemValuesEntity> selectAll() { 
        return selectAll(Order.DESC);
    }
    /**
     * Select all data that not deleted.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeItemValuesEntity> selectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeItemValuesDao/KnowledgeItemValuesDao_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, KnowledgeItemValuesEntity.class);
    }
    /**
     * Select all data that not deleted with pager.
     * @param limit limit
     * @param offset offset
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeItemValuesEntity> selectAllWidthPager(int limit, int offset) { 
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
    public List<KnowledgeItemValuesEntity> selectAllWidthPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeItemValuesDao/KnowledgeItemValuesDao_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, KnowledgeItemValuesEntity.class, limit, offset);
    }
    /**
     * Select count that not deleted.
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer selectCountAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeItemValuesDao/KnowledgeItemValuesDao_select_count_all.sql");
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Select data that not deleted on key.
     * @param  itemNo itemNo
     * @param  knowledgeId knowledgeId
     * @param  typeId typeId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public KnowledgeItemValuesEntity selectOnKey(Integer itemNo, Long knowledgeId, Integer typeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeItemValuesDao/KnowledgeItemValuesDao_select_on_key.sql");
        return executeQuerySingle(sql, KnowledgeItemValuesEntity.class, itemNo, knowledgeId, typeId);
    }
    /**
     * Select data that not deleted on ITEM_NO column.
     * @param itemNo itemNo
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeItemValuesEntity> selectOnItemNo(Integer itemNo) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeItemValuesDao/KnowledgeItemValuesDao_select_on_item_no.sql");
        return executeQueryList(sql, KnowledgeItemValuesEntity.class, itemNo);
    }
    /**
     * Select data that not deleted on KNOWLEDGE_ID column.
     * @param knowledgeId knowledgeId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeItemValuesEntity> selectOnKnowledgeId(Long knowledgeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeItemValuesDao/KnowledgeItemValuesDao_select_on_knowledge_id.sql");
        return executeQueryList(sql, KnowledgeItemValuesEntity.class, knowledgeId);
    }
    /**
     * Select data that not deleted on TYPE_ID column.
     * @param typeId typeId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeItemValuesEntity> selectOnTypeId(Integer typeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeItemValuesDao/KnowledgeItemValuesDao_select_on_type_id.sql");
        return executeQueryList(sql, KnowledgeItemValuesEntity.class, typeId);
    }
    /**
     * Select data on ITEM_NO column.
     * @param itemNo itemNo
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeItemValuesEntity> physicalSelectOnItemNo(Integer itemNo) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeItemValuesDao/KnowledgeItemValuesDao_physical_select_on_item_no.sql");
        return executeQueryList(sql, KnowledgeItemValuesEntity.class, itemNo);
    }
    /**
     * Select data on KNOWLEDGE_ID column.
     * @param knowledgeId knowledgeId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeItemValuesEntity> physicalSelectOnKnowledgeId(Long knowledgeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeItemValuesDao/KnowledgeItemValuesDao_physical_select_on_knowledge_id.sql");
        return executeQueryList(sql, KnowledgeItemValuesEntity.class, knowledgeId);
    }
    /**
     * Select data on TYPE_ID column.
     * @param typeId typeId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeItemValuesEntity> physicalSelectOnTypeId(Integer typeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeItemValuesDao/KnowledgeItemValuesDao_physical_select_on_type_id.sql");
        return executeQueryList(sql, KnowledgeItemValuesEntity.class, typeId);
    }
    /**
     * Count all data
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int physicalCountAll() {
        String sql = "SELECT COUNT(*) FROM KNOWLEDGE_ITEM_VALUES";
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Physical Insert.
     * it is not create key on database sequence.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public KnowledgeItemValuesEntity rawPhysicalInsert(KnowledgeItemValuesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeItemValuesDao/KnowledgeItemValuesDao_raw_insert.sql");
        executeUpdate(sql, 
            entity.getItemNo(), 
            entity.getKnowledgeId(), 
            entity.getTypeId(), 
            entity.getItemValue(), 
            entity.getItemStatus(), 
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
    public KnowledgeItemValuesEntity physicalInsert(KnowledgeItemValuesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeItemValuesDao/KnowledgeItemValuesDao_insert.sql");
        executeUpdate(sql, 
            entity.getItemNo(), 
            entity.getKnowledgeId(), 
            entity.getTypeId(), 
            entity.getItemValue(), 
            entity.getItemStatus(), 
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
    public KnowledgeItemValuesEntity insert(Integer user, KnowledgeItemValuesEntity entity) {
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
    public KnowledgeItemValuesEntity insert(KnowledgeItemValuesEntity entity) {
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
    public KnowledgeItemValuesEntity physicalUpdate(KnowledgeItemValuesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeItemValuesDao/KnowledgeItemValuesDao_update.sql");
        executeUpdate(sql, 
            entity.getItemValue(), 
            entity.getItemStatus(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag(), 
            entity.getItemNo(), 
            entity.getKnowledgeId(), 
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
    public KnowledgeItemValuesEntity update(Integer user, KnowledgeItemValuesEntity entity) {
        KnowledgeItemValuesEntity db = selectOnKey(entity.getItemNo(), entity.getKnowledgeId(), entity.getTypeId());
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
    public KnowledgeItemValuesEntity update(KnowledgeItemValuesEntity entity) {
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
    public KnowledgeItemValuesEntity save(Integer user, KnowledgeItemValuesEntity entity) {
        KnowledgeItemValuesEntity db = selectOnKey(entity.getItemNo(), entity.getKnowledgeId(), entity.getTypeId());
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
    public KnowledgeItemValuesEntity save(KnowledgeItemValuesEntity entity) {
        KnowledgeItemValuesEntity db = selectOnKey(entity.getItemNo(), entity.getKnowledgeId(), entity.getTypeId());
        if (db == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }
    /**
     * Physical Delete.
     * @param  itemNo itemNo
     * @param  knowledgeId knowledgeId
     * @param  typeId typeId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(Integer itemNo, Long knowledgeId, Integer typeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeItemValuesDao/KnowledgeItemValuesDao_delete.sql");
        executeUpdate(sql, itemNo, knowledgeId, typeId);
    }
    /**
     * Physical Delete.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(KnowledgeItemValuesEntity entity) {
        physicalDelete(entity.getItemNo(), entity.getKnowledgeId(), entity.getTypeId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param  itemNo itemNo
     * @param  knowledgeId knowledgeId
     * @param  typeId typeId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, Integer itemNo, Long knowledgeId, Integer typeId) {
        KnowledgeItemValuesEntity db = selectOnKey(itemNo, knowledgeId, typeId);
        db.setDeleteFlag(1);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * @param  itemNo itemNo
     * @param  knowledgeId knowledgeId
     * @param  typeId typeId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer itemNo, Long knowledgeId, Integer typeId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        delete(user, itemNo, knowledgeId, typeId);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, KnowledgeItemValuesEntity entity) {
        delete(user, entity.getItemNo(), entity.getKnowledgeId(), entity.getTypeId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(KnowledgeItemValuesEntity entity) {
        delete(entity.getItemNo(), entity.getKnowledgeId(), entity.getTypeId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param  itemNo itemNo
     * @param  knowledgeId knowledgeId
     * @param  typeId typeId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, Integer itemNo, Long knowledgeId, Integer typeId) {
        KnowledgeItemValuesEntity db = physicalSelectOnKey(itemNo, knowledgeId, typeId);
        db.setDeleteFlag(0);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param  itemNo itemNo
     * @param  knowledgeId knowledgeId
     * @param  typeId typeId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer itemNo, Long knowledgeId, Integer typeId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        activation(user, itemNo, knowledgeId, typeId);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, KnowledgeItemValuesEntity entity) {
        activation(user, entity.getItemNo(), entity.getKnowledgeId(), entity.getTypeId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(KnowledgeItemValuesEntity entity) {
        activation(entity.getItemNo(), entity.getKnowledgeId(), entity.getTypeId());

    }

}
