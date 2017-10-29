package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.SurveyItemsEntity;
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
 * アンケート項目
 * this class is auto generate and not edit.
 * if modify dao method, you can edit SurveyItemsDao.
 */
@DI(instance = Instance.Singleton)
public class GenSurveyItemsDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenSurveyItemsDao get() {
        return Container.getComp(GenSurveyItemsDao.class);
    }

    /**
     * Select all data.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<SurveyItemsEntity> physicalSelectAll() { 
        return physicalSelectAll(Order.DESC);
    }
    /**
     * Select all data.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<SurveyItemsEntity> physicalSelectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/SurveyItemsDao/SurveyItemsDao_physical_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, SurveyItemsEntity.class);
    }
    /**
     * Select all data with pager.
     * @param limit limit
     * @param offset offset
     * @return all data on limit and offset
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<SurveyItemsEntity> physicalSelectAllWithPager(int limit, int offset) { 
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
    public List<SurveyItemsEntity> physicalSelectAllWithPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/SurveyItemsDao/SurveyItemsDao_physical_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, SurveyItemsEntity.class, limit, offset);
    }
    /**
     * Select data on key.
     * @param  itemNo itemNo
     * @param  knowledgeId knowledgeId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public SurveyItemsEntity physicalSelectOnKey(Integer itemNo, Long knowledgeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/SurveyItemsDao/SurveyItemsDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, SurveyItemsEntity.class, itemNo, knowledgeId);
    }
    /**
     * Select all data that not deleted.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<SurveyItemsEntity> selectAll() { 
        return selectAll(Order.DESC);
    }
    /**
     * Select all data that not deleted.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<SurveyItemsEntity> selectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/SurveyItemsDao/SurveyItemsDao_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, SurveyItemsEntity.class);
    }
    /**
     * Select all data that not deleted with pager.
     * @param limit limit
     * @param offset offset
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<SurveyItemsEntity> selectAllWidthPager(int limit, int offset) { 
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
    public List<SurveyItemsEntity> selectAllWidthPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/SurveyItemsDao/SurveyItemsDao_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, SurveyItemsEntity.class, limit, offset);
    }
    /**
     * Select count that not deleted.
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer selectCountAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/SurveyItemsDao/SurveyItemsDao_select_count_all.sql");
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Select data that not deleted on key.
     * @param  itemNo itemNo
     * @param  knowledgeId knowledgeId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public SurveyItemsEntity selectOnKey(Integer itemNo, Long knowledgeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/SurveyItemsDao/SurveyItemsDao_select_on_key.sql");
        return executeQuerySingle(sql, SurveyItemsEntity.class, itemNo, knowledgeId);
    }
    /**
     * Select data that not deleted on ITEM_NO column.
     * @param itemNo itemNo
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<SurveyItemsEntity> selectOnItemNo(Integer itemNo) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/SurveyItemsDao/SurveyItemsDao_select_on_item_no.sql");
        return executeQueryList(sql, SurveyItemsEntity.class, itemNo);
    }
    /**
     * Select data that not deleted on KNOWLEDGE_ID column.
     * @param knowledgeId knowledgeId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<SurveyItemsEntity> selectOnKnowledgeId(Long knowledgeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/SurveyItemsDao/SurveyItemsDao_select_on_knowledge_id.sql");
        return executeQueryList(sql, SurveyItemsEntity.class, knowledgeId);
    }
    /**
     * Select data on ITEM_NO column.
     * @param itemNo itemNo
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<SurveyItemsEntity> physicalSelectOnItemNo(Integer itemNo) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/SurveyItemsDao/SurveyItemsDao_physical_select_on_item_no.sql");
        return executeQueryList(sql, SurveyItemsEntity.class, itemNo);
    }
    /**
     * Select data on KNOWLEDGE_ID column.
     * @param knowledgeId knowledgeId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<SurveyItemsEntity> physicalSelectOnKnowledgeId(Long knowledgeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/SurveyItemsDao/SurveyItemsDao_physical_select_on_knowledge_id.sql");
        return executeQueryList(sql, SurveyItemsEntity.class, knowledgeId);
    }
    /**
     * Count all data
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int physicalCountAll() {
        String sql = "SELECT COUNT(*) FROM SURVEY_ITEMS";
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Physical Insert.
     * it is not create key on database sequence.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public SurveyItemsEntity rawPhysicalInsert(SurveyItemsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/SurveyItemsDao/SurveyItemsDao_raw_insert.sql");
        executeUpdate(sql, 
            entity.getItemNo(), 
            entity.getKnowledgeId(), 
            entity.getItemName(), 
            entity.getItemType(), 
            entity.getDescription(), 
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
    public SurveyItemsEntity physicalInsert(SurveyItemsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/SurveyItemsDao/SurveyItemsDao_insert.sql");
        executeUpdate(sql, 
            entity.getItemNo(), 
            entity.getKnowledgeId(), 
            entity.getItemName(), 
            entity.getItemType(), 
            entity.getDescription(), 
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
    public SurveyItemsEntity insert(Integer user, SurveyItemsEntity entity) {
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
    public SurveyItemsEntity insert(SurveyItemsEntity entity) {
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
    public SurveyItemsEntity physicalUpdate(SurveyItemsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/SurveyItemsDao/SurveyItemsDao_update.sql");
        executeUpdate(sql, 
            entity.getItemName(), 
            entity.getItemType(), 
            entity.getDescription(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag(), 
            entity.getItemNo(), 
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
    public SurveyItemsEntity update(Integer user, SurveyItemsEntity entity) {
        SurveyItemsEntity db = selectOnKey(entity.getItemNo(), entity.getKnowledgeId());
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
    public SurveyItemsEntity update(SurveyItemsEntity entity) {
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
    public SurveyItemsEntity save(Integer user, SurveyItemsEntity entity) {
        SurveyItemsEntity db = selectOnKey(entity.getItemNo(), entity.getKnowledgeId());
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
    public SurveyItemsEntity save(SurveyItemsEntity entity) {
        SurveyItemsEntity db = selectOnKey(entity.getItemNo(), entity.getKnowledgeId());
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
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(Integer itemNo, Long knowledgeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/SurveyItemsDao/SurveyItemsDao_delete.sql");
        executeUpdate(sql, itemNo, knowledgeId);
    }
    /**
     * Physical Delete.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(SurveyItemsEntity entity) {
        physicalDelete(entity.getItemNo(), entity.getKnowledgeId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param  itemNo itemNo
     * @param  knowledgeId knowledgeId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, Integer itemNo, Long knowledgeId) {
        SurveyItemsEntity db = selectOnKey(itemNo, knowledgeId);
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
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer itemNo, Long knowledgeId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        delete(user, itemNo, knowledgeId);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, SurveyItemsEntity entity) {
        delete(user, entity.getItemNo(), entity.getKnowledgeId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(SurveyItemsEntity entity) {
        delete(entity.getItemNo(), entity.getKnowledgeId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param  itemNo itemNo
     * @param  knowledgeId knowledgeId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, Integer itemNo, Long knowledgeId) {
        SurveyItemsEntity db = physicalSelectOnKey(itemNo, knowledgeId);
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
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer itemNo, Long knowledgeId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        activation(user, itemNo, knowledgeId);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, SurveyItemsEntity entity) {
        activation(user, entity.getItemNo(), entity.getKnowledgeId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(SurveyItemsEntity entity) {
        activation(entity.getItemNo(), entity.getKnowledgeId());

    }

}
