package org.support.project.web.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.web.entity.ProvisionalRegistrationsEntity;
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
 * 仮登録ユーザ
 * this class is auto generate and not edit.
 * if modify dao method, you can edit ProvisionalRegistrationsDao.
 */
@DI(instance = Instance.Singleton)
public class GenProvisionalRegistrationsDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenProvisionalRegistrationsDao get() {
        return Container.getComp(GenProvisionalRegistrationsDao.class);
    }

    /**
     * Select all data.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ProvisionalRegistrationsEntity> physicalSelectAll() { 
        return physicalSelectAll(Order.DESC);
    }
    /**
     * Select all data.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ProvisionalRegistrationsEntity> physicalSelectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/ProvisionalRegistrationsDao/ProvisionalRegistrationsDao_physical_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, ProvisionalRegistrationsEntity.class);
    }
    /**
     * Select all data with pager.
     * @param limit limit
     * @param offset offset
     * @return all data on limit and offset
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ProvisionalRegistrationsEntity> physicalSelectAllWithPager(int limit, int offset) { 
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
    public List<ProvisionalRegistrationsEntity> physicalSelectAllWithPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/ProvisionalRegistrationsDao/ProvisionalRegistrationsDao_physical_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, ProvisionalRegistrationsEntity.class, limit, offset);
    }
    /**
     * Select data on key.
     * @param  id id
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public ProvisionalRegistrationsEntity physicalSelectOnKey(String id) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/ProvisionalRegistrationsDao/ProvisionalRegistrationsDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, ProvisionalRegistrationsEntity.class, id);
    }
    /**
     * Select all data that not deleted.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ProvisionalRegistrationsEntity> selectAll() { 
        return selectAll(Order.DESC);
    }
    /**
     * Select all data that not deleted.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ProvisionalRegistrationsEntity> selectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/ProvisionalRegistrationsDao/ProvisionalRegistrationsDao_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, ProvisionalRegistrationsEntity.class);
    }
    /**
     * Select all data that not deleted with pager.
     * @param limit limit
     * @param offset offset
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ProvisionalRegistrationsEntity> selectAllWidthPager(int limit, int offset) { 
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
    public List<ProvisionalRegistrationsEntity> selectAllWidthPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/ProvisionalRegistrationsDao/ProvisionalRegistrationsDao_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, ProvisionalRegistrationsEntity.class, limit, offset);
    }
    /**
     * Select count that not deleted.
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer selectCountAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/ProvisionalRegistrationsDao/ProvisionalRegistrationsDao_select_count_all.sql");
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Select data that not deleted on key.
     * @param  id id
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public ProvisionalRegistrationsEntity selectOnKey(String id) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/ProvisionalRegistrationsDao/ProvisionalRegistrationsDao_select_on_key.sql");
        return executeQuerySingle(sql, ProvisionalRegistrationsEntity.class, id);
    }
    /**
     * Count all data
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int physicalCountAll() {
        String sql = "SELECT COUNT(*) FROM PROVISIONAL_REGISTRATIONS";
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Create row id.
     * @return row id
     */
    protected String createRowId() {
        return IDGen.get().gen("PROVISIONAL_REGISTRATIONS");
    }
    /**
     * Physical Insert.
     * it is not create key on database sequence.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public ProvisionalRegistrationsEntity rawPhysicalInsert(ProvisionalRegistrationsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/ProvisionalRegistrationsDao/ProvisionalRegistrationsDao_raw_insert.sql");
        executeUpdate(sql, 
            entity.getId(), 
            entity.getUserKey(), 
            entity.getUserName(), 
            entity.getPassword(), 
            entity.getSalt(), 
            entity.getLocaleKey(), 
            entity.getMailAddress(), 
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
    public ProvisionalRegistrationsEntity physicalInsert(ProvisionalRegistrationsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/ProvisionalRegistrationsDao/ProvisionalRegistrationsDao_insert.sql");
        executeUpdate(sql, 
            entity.getId(), 
            entity.getUserKey(), 
            entity.getUserName(), 
            entity.getPassword(), 
            entity.getSalt(), 
            entity.getLocaleKey(), 
            entity.getMailAddress(), 
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
    public ProvisionalRegistrationsEntity insert(Integer user, ProvisionalRegistrationsEntity entity) {
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
    public ProvisionalRegistrationsEntity insert(ProvisionalRegistrationsEntity entity) {
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
    public ProvisionalRegistrationsEntity physicalUpdate(ProvisionalRegistrationsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/ProvisionalRegistrationsDao/ProvisionalRegistrationsDao_update.sql");
        executeUpdate(sql, 
            entity.getUserKey(), 
            entity.getUserName(), 
            entity.getPassword(), 
            entity.getSalt(), 
            entity.getLocaleKey(), 
            entity.getMailAddress(), 
            entity.getRowId(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag(), 
            entity.getId());
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
    public ProvisionalRegistrationsEntity update(Integer user, ProvisionalRegistrationsEntity entity) {
        ProvisionalRegistrationsEntity db = selectOnKey(entity.getId());
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
    public ProvisionalRegistrationsEntity update(ProvisionalRegistrationsEntity entity) {
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
    public ProvisionalRegistrationsEntity save(Integer user, ProvisionalRegistrationsEntity entity) {
        ProvisionalRegistrationsEntity db = selectOnKey(entity.getId());
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
    public ProvisionalRegistrationsEntity save(ProvisionalRegistrationsEntity entity) {
        ProvisionalRegistrationsEntity db = selectOnKey(entity.getId());
        if (db == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }
    /**
     * Physical Delete.
     * @param  id id
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(String id) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/ProvisionalRegistrationsDao/ProvisionalRegistrationsDao_delete.sql");
        executeUpdate(sql, id);
    }
    /**
     * Physical Delete.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(ProvisionalRegistrationsEntity entity) {
        physicalDelete(entity.getId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param  id id
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, String id) {
        ProvisionalRegistrationsEntity db = selectOnKey(id);
        db.setDeleteFlag(1);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * @param  id id
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String id) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        delete(user, id);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, ProvisionalRegistrationsEntity entity) {
        delete(user, entity.getId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(ProvisionalRegistrationsEntity entity) {
        delete(entity.getId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param  id id
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, String id) {
        ProvisionalRegistrationsEntity db = physicalSelectOnKey(id);
        db.setDeleteFlag(0);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param  id id
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String id) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        activation(user, id);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, ProvisionalRegistrationsEntity entity) {
        activation(user, entity.getId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(ProvisionalRegistrationsEntity entity) {
        activation(entity.getId());

    }

}
