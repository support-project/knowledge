package org.support.project.web.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.web.entity.LdapConfigsEntity;
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
 * LDAP認証設定
 * this class is auto generate and not edit.
 * if modify dao method, you can edit LdapConfigsDao.
 */
@DI(instance = Instance.Singleton)
public class GenLdapConfigsDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenLdapConfigsDao get() {
        return Container.getComp(GenLdapConfigsDao.class);
    }

    /**
     * Select all data.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<LdapConfigsEntity> physicalSelectAll() { 
        return physicalSelectAll(Order.DESC);
    }
    /**
     * Select all data.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<LdapConfigsEntity> physicalSelectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/LdapConfigsDao/LdapConfigsDao_physical_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, LdapConfigsEntity.class);
    }
    /**
     * Select all data with pager.
     * @param limit limit
     * @param offset offset
     * @return all data on limit and offset
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<LdapConfigsEntity> physicalSelectAllWithPager(int limit, int offset) { 
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
    public List<LdapConfigsEntity> physicalSelectAllWithPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/LdapConfigsDao/LdapConfigsDao_physical_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, LdapConfigsEntity.class, limit, offset);
    }
    /**
     * Select data on key.
     * @param  systemName systemName
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public LdapConfigsEntity physicalSelectOnKey(String systemName) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/LdapConfigsDao/LdapConfigsDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, LdapConfigsEntity.class, systemName);
    }
    /**
     * Select all data that not deleted.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<LdapConfigsEntity> selectAll() { 
        return selectAll(Order.DESC);
    }
    /**
     * Select all data that not deleted.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<LdapConfigsEntity> selectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/LdapConfigsDao/LdapConfigsDao_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, LdapConfigsEntity.class);
    }
    /**
     * Select all data that not deleted with pager.
     * @param limit limit
     * @param offset offset
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<LdapConfigsEntity> selectAllWidthPager(int limit, int offset) { 
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
    public List<LdapConfigsEntity> selectAllWidthPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/LdapConfigsDao/LdapConfigsDao_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, LdapConfigsEntity.class, limit, offset);
    }
    /**
     * Select count that not deleted.
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer selectCountAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/LdapConfigsDao/LdapConfigsDao_select_count_all.sql");
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Select data that not deleted on key.
     * @param  systemName systemName
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public LdapConfigsEntity selectOnKey(String systemName) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/LdapConfigsDao/LdapConfigsDao_select_on_key.sql");
        return executeQuerySingle(sql, LdapConfigsEntity.class, systemName);
    }
    /**
     * Count all data
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int physicalCountAll() {
        String sql = "SELECT COUNT(*) FROM LDAP_CONFIGS";
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Create row id.
     * @return row id
     */
    protected String createRowId() {
        return IDGen.get().gen("LDAP_CONFIGS");
    }
    /**
     * Physical Insert.
     * it is not create key on database sequence.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public LdapConfigsEntity rawPhysicalInsert(LdapConfigsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/LdapConfigsDao/LdapConfigsDao_raw_insert.sql");
        executeUpdate(sql, 
            entity.getSystemName(), 
            entity.getDescription(), 
            entity.getHost(), 
            entity.getPort(), 
            entity.getUseSsl(), 
            entity.getUseTls(), 
            entity.getBindDn(), 
            entity.getBindPassword(), 
            entity.getSalt(), 
            entity.getBaseDn(), 
            entity.getFilter(), 
            entity.getIdAttr(), 
            entity.getNameAttr(), 
            entity.getMailAttr(), 
            entity.getAdminCheckFilter(), 
            entity.getAuthType(), 
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
    public LdapConfigsEntity physicalInsert(LdapConfigsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/LdapConfigsDao/LdapConfigsDao_insert.sql");
        executeUpdate(sql, 
            entity.getSystemName(), 
            entity.getDescription(), 
            entity.getHost(), 
            entity.getPort(), 
            entity.getUseSsl(), 
            entity.getUseTls(), 
            entity.getBindDn(), 
            entity.getBindPassword(), 
            entity.getSalt(), 
            entity.getBaseDn(), 
            entity.getFilter(), 
            entity.getIdAttr(), 
            entity.getNameAttr(), 
            entity.getMailAttr(), 
            entity.getAdminCheckFilter(), 
            entity.getAuthType(), 
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
    public LdapConfigsEntity insert(Integer user, LdapConfigsEntity entity) {
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
    public LdapConfigsEntity insert(LdapConfigsEntity entity) {
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
    public LdapConfigsEntity physicalUpdate(LdapConfigsEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/LdapConfigsDao/LdapConfigsDao_update.sql");
        executeUpdate(sql, 
            entity.getDescription(), 
            entity.getHost(), 
            entity.getPort(), 
            entity.getUseSsl(), 
            entity.getUseTls(), 
            entity.getBindDn(), 
            entity.getBindPassword(), 
            entity.getSalt(), 
            entity.getBaseDn(), 
            entity.getFilter(), 
            entity.getIdAttr(), 
            entity.getNameAttr(), 
            entity.getMailAttr(), 
            entity.getAdminCheckFilter(), 
            entity.getAuthType(), 
            entity.getRowId(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag(), 
            entity.getSystemName());
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
    public LdapConfigsEntity update(Integer user, LdapConfigsEntity entity) {
        LdapConfigsEntity db = selectOnKey(entity.getSystemName());
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
    public LdapConfigsEntity update(LdapConfigsEntity entity) {
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
    public LdapConfigsEntity save(Integer user, LdapConfigsEntity entity) {
        LdapConfigsEntity db = selectOnKey(entity.getSystemName());
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
    public LdapConfigsEntity save(LdapConfigsEntity entity) {
        LdapConfigsEntity db = selectOnKey(entity.getSystemName());
        if (db == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }
    /**
     * Physical Delete.
     * @param  systemName systemName
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(String systemName) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/web/dao/sql/LdapConfigsDao/LdapConfigsDao_delete.sql");
        executeUpdate(sql, systemName);
    }
    /**
     * Physical Delete.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(LdapConfigsEntity entity) {
        physicalDelete(entity.getSystemName());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param  systemName systemName
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, String systemName) {
        LdapConfigsEntity db = selectOnKey(systemName);
        db.setDeleteFlag(1);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * @param  systemName systemName
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String systemName) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        delete(user, systemName);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, LdapConfigsEntity entity) {
        delete(user, entity.getSystemName());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(LdapConfigsEntity entity) {
        delete(entity.getSystemName());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param  systemName systemName
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, String systemName) {
        LdapConfigsEntity db = physicalSelectOnKey(systemName);
        db.setDeleteFlag(0);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param  systemName systemName
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String systemName) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        activation(user, systemName);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, LdapConfigsEntity entity) {
        activation(user, entity.getSystemName());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(LdapConfigsEntity entity) {
        activation(entity.getSystemName());

    }

}
