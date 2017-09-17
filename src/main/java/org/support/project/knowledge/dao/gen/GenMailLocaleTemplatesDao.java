package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.MailLocaleTemplatesEntity;
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
 * ロケール毎のメールテンプレート
 * this class is auto generate and not edit.
 * if modify dao method, you can edit MailLocaleTemplatesDao.
 */
@DI(instance = Instance.Singleton)
public class GenMailLocaleTemplatesDao extends AbstractDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static GenMailLocaleTemplatesDao get() {
        return Container.getComp(GenMailLocaleTemplatesDao.class);
    }

    /**
     * Select all data.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailLocaleTemplatesEntity> physicalSelectAll() { 
        return physicalSelectAll(Order.DESC);
    }
    /**
     * Select all data.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailLocaleTemplatesEntity> physicalSelectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailLocaleTemplatesDao/MailLocaleTemplatesDao_physical_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, MailLocaleTemplatesEntity.class);
    }
    /**
     * Select all data with pager.
     * @param limit limit
     * @param offset offset
     * @return all data on limit and offset
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailLocaleTemplatesEntity> physicalSelectAllWithPager(int limit, int offset) { 
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
    public List<MailLocaleTemplatesEntity> physicalSelectAllWithPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailLocaleTemplatesDao/MailLocaleTemplatesDao_physical_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, MailLocaleTemplatesEntity.class, limit, offset);
    }
    /**
     * Select data on key.
     * @param  key key
     * @param  templateId templateId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public MailLocaleTemplatesEntity physicalSelectOnKey(String key, String templateId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailLocaleTemplatesDao/MailLocaleTemplatesDao_physical_select_on_key.sql");
        return executeQuerySingle(sql, MailLocaleTemplatesEntity.class, key, templateId);
    }
    /**
     * Select all data that not deleted.
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailLocaleTemplatesEntity> selectAll() { 
        return selectAll(Order.DESC);
    }
    /**
     * Select all data that not deleted.
     * @param order order
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailLocaleTemplatesEntity> selectAll(Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailLocaleTemplatesDao/MailLocaleTemplatesDao_select_all.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, MailLocaleTemplatesEntity.class);
    }
    /**
     * Select all data that not deleted with pager.
     * @param limit limit
     * @param offset offset
     * @return all data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailLocaleTemplatesEntity> selectAllWidthPager(int limit, int offset) { 
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
    public List<MailLocaleTemplatesEntity> selectAllWidthPager(int limit, int offset, Order order) { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailLocaleTemplatesDao/MailLocaleTemplatesDao_select_all_with_pager.sql");
        sql = String.format(sql, order.toString());
        return executeQueryList(sql, MailLocaleTemplatesEntity.class, limit, offset);
    }
    /**
     * Select count that not deleted.
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Integer selectCountAll() { 
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailLocaleTemplatesDao/MailLocaleTemplatesDao_select_count_all.sql");
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Select data that not deleted on key.
     * @param  key key
     * @param  templateId templateId
     * @return data
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public MailLocaleTemplatesEntity selectOnKey(String key, String templateId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailLocaleTemplatesDao/MailLocaleTemplatesDao_select_on_key.sql");
        return executeQuerySingle(sql, MailLocaleTemplatesEntity.class, key, templateId);
    }
    /**
     * Select data that not deleted on KEY column.
     * @param key key
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailLocaleTemplatesEntity> selectOnKey(String key) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailLocaleTemplatesDao/MailLocaleTemplatesDao_select_on_col_key.sql");
        return executeQueryList(sql, MailLocaleTemplatesEntity.class, key);
    }
    /**
     * Select data that not deleted on TEMPLATE_ID column.
     * @param templateId templateId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailLocaleTemplatesEntity> selectOnTemplateId(String templateId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailLocaleTemplatesDao/MailLocaleTemplatesDao_select_on_template_id.sql");
        return executeQueryList(sql, MailLocaleTemplatesEntity.class, templateId);
    }
    /**
     * Select data on KEY column.
     * @param key key
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailLocaleTemplatesEntity> physicalSelectOnKey(String key) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailLocaleTemplatesDao/MailLocaleTemplatesDao_physical_select_on_col_key.sql");
        return executeQueryList(sql, MailLocaleTemplatesEntity.class, key);
    }
    /**
     * Select data on TEMPLATE_ID column.
     * @param templateId templateId
     * @return list
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<MailLocaleTemplatesEntity> physicalSelectOnTemplateId(String templateId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailLocaleTemplatesDao/MailLocaleTemplatesDao_physical_select_on_template_id.sql");
        return executeQueryList(sql, MailLocaleTemplatesEntity.class, templateId);
    }
    /**
     * Count all data
     * @return count
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int physicalCountAll() {
        String sql = "SELECT COUNT(*) FROM MAIL_LOCALE_TEMPLATES";
        return executeQuerySingle(sql, Integer.class);
    }
    /**
     * Physical Insert.
     * it is not create key on database sequence.
     * @param entity entity
     * @return saved entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public MailLocaleTemplatesEntity rawPhysicalInsert(MailLocaleTemplatesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailLocaleTemplatesDao/MailLocaleTemplatesDao_raw_insert.sql");
        executeUpdate(sql, 
            entity.getKey(), 
            entity.getTemplateId(), 
            entity.getTitle(), 
            entity.getContent(), 
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
    public MailLocaleTemplatesEntity physicalInsert(MailLocaleTemplatesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailLocaleTemplatesDao/MailLocaleTemplatesDao_insert.sql");
        executeUpdate(sql, 
            entity.getKey(), 
            entity.getTemplateId(), 
            entity.getTitle(), 
            entity.getContent(), 
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
    public MailLocaleTemplatesEntity insert(Integer user, MailLocaleTemplatesEntity entity) {
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
    public MailLocaleTemplatesEntity insert(MailLocaleTemplatesEntity entity) {
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
    public MailLocaleTemplatesEntity physicalUpdate(MailLocaleTemplatesEntity entity) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailLocaleTemplatesDao/MailLocaleTemplatesDao_update.sql");
        executeUpdate(sql, 
            entity.getTitle(), 
            entity.getContent(), 
            entity.getInsertUser(), 
            entity.getInsertDatetime(), 
            entity.getUpdateUser(), 
            entity.getUpdateDatetime(), 
            entity.getDeleteFlag(), 
            entity.getKey(), 
            entity.getTemplateId());
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
    public MailLocaleTemplatesEntity update(Integer user, MailLocaleTemplatesEntity entity) {
        MailLocaleTemplatesEntity db = selectOnKey(entity.getKey(), entity.getTemplateId());
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
    public MailLocaleTemplatesEntity update(MailLocaleTemplatesEntity entity) {
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
    public MailLocaleTemplatesEntity save(Integer user, MailLocaleTemplatesEntity entity) {
        MailLocaleTemplatesEntity db = selectOnKey(entity.getKey(), entity.getTemplateId());
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
    public MailLocaleTemplatesEntity save(MailLocaleTemplatesEntity entity) {
        MailLocaleTemplatesEntity db = selectOnKey(entity.getKey(), entity.getTemplateId());
        if (db == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }
    /**
     * Physical Delete.
     * @param  key key
     * @param  templateId templateId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(String key, String templateId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/MailLocaleTemplatesDao/MailLocaleTemplatesDao_delete.sql");
        executeUpdate(sql, key, templateId);
    }
    /**
     * Physical Delete.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void physicalDelete(MailLocaleTemplatesEntity entity) {
        physicalDelete(entity.getKey(), entity.getTemplateId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param  key key
     * @param  templateId templateId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, String key, String templateId) {
        MailLocaleTemplatesEntity db = selectOnKey(key, templateId);
        db.setDeleteFlag(1);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * @param  key key
     * @param  templateId templateId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(String key, String templateId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        delete(user, key, templateId);
    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(Integer user, MailLocaleTemplatesEntity entity) {
        delete(user, entity.getKey(), entity.getTemplateId());

    }
    /**
     * Delete.
     * if delete flag is exists, the data is logical delete.
     * set saved user id.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void delete(MailLocaleTemplatesEntity entity) {
        delete(entity.getKey(), entity.getTemplateId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param  key key
     * @param  templateId templateId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, String key, String templateId) {
        MailLocaleTemplatesEntity db = physicalSelectOnKey(key, templateId);
        db.setDeleteFlag(0);
        db.setUpdateUser(user);
        db.setUpdateDatetime(new Timestamp(DateUtils.now().getTime()));
        physicalUpdate(db);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param  key key
     * @param  templateId templateId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(String key, String templateId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        activation(user, key, templateId);
    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * set saved user id.
     * @param user saved userid
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(Integer user, MailLocaleTemplatesEntity entity) {
        activation(user, entity.getKey(), entity.getTemplateId());

    }
    /**
     * Ativation.
     * if delete flag is exists and delete flag is true, delete flug is false to activate.
     * @param entity entity
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void activation(MailLocaleTemplatesEntity entity) {
        activation(entity.getKey(), entity.getTemplateId());

    }

}
