package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.io.InputStream;
import java.sql.Timestamp;


import org.support.project.knowledge.entity.AccountImagesEntity;
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
 * アカウントの画像
 * this class is auto generate and not edit.
 * if modify dao method, you can edit AccountImagesDao.
 */
@DI(instance = Instance.Singleton)
public class GenAccountImagesDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenAccountImagesDao get() {
        return Container.getComp(GenAccountImagesDao.class);
    }

    /**
     * Select all data.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<AccountImagesEntity> physicalSelectAll() { 
        return physicalSelectAll(Order.DESC);
    }
    /**
     * Select all data.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<AccountImagesEntity> physicalSelectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/AccountImagesDao/AccountImagesDao_physical_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, AccountImagesEntity.class);
    }
    /**
     * Select all data with pager.
     * @param limit limit
     * @param offset offset
     * @return all data on limit and offset
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<AccountImagesEntity> physicalSelectAllWithPager(int limit, int offset) { 
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
    public List<AccountImagesEntity> physicalSelectAllWithPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/AccountImagesDao/AccountImagesDao_physical_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, AccountImagesEntity.class, limit, offset);
    }
    /**
     * Select data on key.
     * @param  imageId imageId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public AccountImagesEntity physicalSelectOnKey(Long imageId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/AccountImagesDao/AccountImagesDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, AccountImagesEntity.class, imageId);
    }
    /**
     * Select all data that not deleted.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<AccountImagesEntity> selectAll() { 
        return selectAll(Order.DESC);
    }
    /**
     * Select all data that not deleted.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<AccountImagesEntity> selectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/AccountImagesDao/AccountImagesDao_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, AccountImagesEntity.class);
    }
    /**
     * Select all data that not deleted with pager.
     * @param limit limit
     * @param offset offset
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<AccountImagesEntity> selectAllWidthPager(int limit, int offset) { 
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
    public List<AccountImagesEntity> selectAllWidthPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/AccountImagesDao/AccountImagesDao_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, AccountImagesEntity.class, limit, offset);
    }
    /**
     * Select count that not deleted.
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer selectCountAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/AccountImagesDao/AccountImagesDao_select_count_all.sql");
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Select data that not deleted on key.
     * @param  imageId imageId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public AccountImagesEntity selectOnKey(Long imageId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/AccountImagesDao/AccountImagesDao_select_on_key.sql");
        return executeQuerySingle(sql, AccountImagesEntity.class, imageId);
    }
    /**
     * Count all data
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int physicalCountAll() {
        String sql = "SELECT COUNT(*) FROM ACCOUNT_IMAGES";
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Physical Insert.
     * it is not create key on database sequence.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public AccountImagesEntity rawPhysicalInsert(AccountImagesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/AccountImagesDao/AccountImagesDao_raw_insert.sql");
        executeUpdate(sql, 
            entity.getImageId(), 
            entity.getUserId(), 
            entity.getFileName(), 
            entity.getFileSize(), 
            entity.getFileBinary(), 
            entity.getExtension(), 
            entity.getContentType(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag());
        String driverClass = ConnectionManager.getInstance().getDriverClass(getConnectionName());
        if (ORMappingParameter.DRIVER_NAME_POSTGRESQL.equals(driverClass)) {
            String maxSql = "SELECT MAX(IMAGE_ID) from ACCOUNT_IMAGES;";
            long max = executeQuerySingle(maxSql, Long.class);
            if (max < 1) {
                max = 1;
            }
            String setValSql = "SELECT SETVAL('ACCOUNT_IMAGES_IMAGE_ID_seq', ?);";
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
    public AccountImagesEntity physicalInsert(AccountImagesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/AccountImagesDao/AccountImagesDao_insert.sql");
        Class<?> type = PropertyUtil.getPropertyType(entity, "imageId");
        Object key = executeInsert(sql, type, 
            entity.getUserId(), 
            entity.getFileName(), 
            entity.getFileSize(), 
            entity.getFileBinary(), 
            entity.getExtension(), 
            entity.getContentType(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag());
        PropertyUtil.setPropertyValue(entity, "imageId", key);
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
    public AccountImagesEntity insert(Integer user, AccountImagesEntity entity) {
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
    public AccountImagesEntity insert(AccountImagesEntity entity) {
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
    public AccountImagesEntity physicalUpdate(AccountImagesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/AccountImagesDao/AccountImagesDao_update.sql");
        executeUpdate(sql, 
            entity.getUserId(), 
            entity.getFileName(), 
            entity.getFileSize(), 
            entity.getFileBinary(), 
            entity.getExtension(), 
            entity.getContentType(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag(), 
            entity.getImageId());
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
    public AccountImagesEntity update(Integer user, AccountImagesEntity entity) {
        AccountImagesEntity db = selectOnKey(entity.getImageId());
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
    public AccountImagesEntity update(AccountImagesEntity entity) {
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
    public AccountImagesEntity save(Integer user, AccountImagesEntity entity) {
        AccountImagesEntity db = selectOnKey(entity.getImageId());
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
    public AccountImagesEntity save(AccountImagesEntity entity) {
        AccountImagesEntity db = selectOnKey(entity.getImageId());
        if (db == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }
    /**
     * Physical Delete.
     * @param  imageId imageId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(Long imageId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/AccountImagesDao/AccountImagesDao_delete.sql");
        executeUpdate(sql, imageId);
    }
    /**
     * Physical Delete.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(AccountImagesEntity entity) {
        physicalDelete(entity.getImageId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param  imageId imageId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, Long imageId) {
        AccountImagesEntity db = selectOnKey(imageId);
        db.setDeleteFlag(1);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * @param  imageId imageId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Long imageId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        delete(user, imageId);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, AccountImagesEntity entity) {
        delete(user, entity.getImageId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(AccountImagesEntity entity) {
        delete(entity.getImageId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param  imageId imageId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, Long imageId) {
        AccountImagesEntity db = physicalSelectOnKey(imageId);
        db.setDeleteFlag(0);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param  imageId imageId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Long imageId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        activation(user, imageId);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, AccountImagesEntity entity) {
        activation(user, entity.getImageId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(AccountImagesEntity entity) {
        activation(entity.getImageId());

    }

}
