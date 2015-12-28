package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.WebhookConfigsEntity;
import org.support.project.ormapping.dao.AbstractDao;
import org.support.project.ormapping.exception.ORMappingException;
import org.support.project.ormapping.common.SQLManager;
import org.support.project.ormapping.common.DBUserPool;
import org.support.project.ormapping.common.IDGen;
import org.support.project.ormapping.config.ORMappingParameter;
import org.support.project.ormapping.connection.ConnectionManager;
import org.support.project.common.util.PropertyUtil;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.aop.Aspect;

/**
 * Webhooks 設定
 */
@DI(instance=Instance.Singleton)
public class GenWebhookConfigsDao extends AbstractDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenWebhookConfigsDao get() {
		return Container.getComp(GenWebhookConfigsDao.class);
	}

	/**
	 * 全て取得(削除フラグを無視して取得) 
	 */
	public List<WebhookConfigsEntity> physicalSelectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/WebhookConfigsDao/WebhookConfigsDao_physical_select_all.sql");
		return executeQueryList(sql, WebhookConfigsEntity.class);
	}
	/**
	 * キーで1件取得(削除フラグを無視して取得) 
	 */
	public WebhookConfigsEntity physicalSelectOnKey(Integer hookId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/WebhookConfigsDao/WebhookConfigsDao_physical_select_on_key.sql");
		return executeQuerySingle(sql, WebhookConfigsEntity.class, hookId);
	}
	/**
	 * 全て取得 
	 */
	public List<WebhookConfigsEntity> selectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/WebhookConfigsDao/WebhookConfigsDao_select_all.sql");
		return executeQueryList(sql, WebhookConfigsEntity.class);
	}
	/**
	 * キーで1件取得 
	 */
	public WebhookConfigsEntity selectOnKey(Integer hookId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/WebhookConfigsDao/WebhookConfigsDao_select_on_key.sql");
		return executeQuerySingle(sql, WebhookConfigsEntity.class, hookId);
	}
	/**
	 * 登録(データを生で操作/DBの採番機能のカラムも自分でセット) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public WebhookConfigsEntity rawPhysicalInsert(WebhookConfigsEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/WebhookConfigsDao/WebhookConfigsDao_raw_insert.sql");
		executeUpdate(sql, 
			entity.getHookId()
			, entity.getHook()
			, entity.getUrl()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
		);
		String driverClass = ConnectionManager.getInstance().getDriverClass(getConnectionName());
		if (ORMappingParameter.DRIVER_NAME_POSTGRESQL.equals(driverClass)) {
			String setValSql = "select setval('WEBHOOK_CONFIGS_HOOK_ID_seq', (select max(HOOK_ID) from WEBHOOK_CONFIGS));";
			executeQuerySingle(setValSql, Long.class);
		}
		return entity;
	}
	/**
	 * 登録(データを生で操作) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public WebhookConfigsEntity physicalInsert(WebhookConfigsEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/WebhookConfigsDao/WebhookConfigsDao_insert.sql");
		Class<?> type = PropertyUtil.getPropertyType(entity, "hookId");
		Object key = executeInsert(sql, type, 
			entity.getHook()
			, entity.getUrl()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
		);
		PropertyUtil.setPropertyValue(entity, "hookId", key);
		return entity;
	}
	/**
	 * 登録(登録ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public WebhookConfigsEntity insert(Integer user, WebhookConfigsEntity entity) {
		entity.setInsertUser(user);
		entity.setInsertDatetime(new Timestamp(new java.util.Date().getTime()));
		entity.setUpdateUser(user);
		entity.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		entity.setDeleteFlag(0);
		return physicalInsert(entity);
	}
	/**
	 * 登録
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public WebhookConfigsEntity insert(WebhookConfigsEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return insert(userId, entity);
	}
	/**
	 * 更新(データを生で操作) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public WebhookConfigsEntity physicalUpdate(WebhookConfigsEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/WebhookConfigsDao/WebhookConfigsDao_update.sql");
		executeUpdate(sql, 
			entity.getHook()
			, entity.getUrl()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
			, entity.getHookId()
		);
		return entity;
	}
	/**
	 * 更新(更新ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public WebhookConfigsEntity update(Integer user, WebhookConfigsEntity entity) {
		WebhookConfigsEntity db = selectOnKey(entity.getHookId());
		entity.setInsertUser(db.getInsertUser());
		entity.setInsertDatetime(db.getInsertDatetime());
		entity.setDeleteFlag(db.getDeleteFlag());
		entity.setUpdateUser(user);
		entity.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		return physicalUpdate(entity);
	}
	/**
	 * 更新
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public WebhookConfigsEntity update(WebhookConfigsEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return update(userId, entity);
	}
	/**
	 * 保存(ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public WebhookConfigsEntity save(Integer user, WebhookConfigsEntity entity) {
		WebhookConfigsEntity db = selectOnKey(entity.getHookId());
		if (db == null) {
			return insert(user, entity);
		} else {
			return update(user, entity);
		}
	}
	/**
	 * 保存(存在しなければ登録、存在すれば更新) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public WebhookConfigsEntity save(WebhookConfigsEntity entity) {
		WebhookConfigsEntity db = selectOnKey(entity.getHookId());
		if (db == null) {
			return insert(entity);
		} else {
			return update(entity);
		}
	}
	/**
	 * 削除(データを生で操作/物理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void physicalDelete(Integer hookId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/WebhookConfigsDao/WebhookConfigsDao_delete.sql");
		executeUpdate(sql, hookId);
	}
	/**
	 * 削除(データを生で操作/物理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void physicalDelete(WebhookConfigsEntity entity) {
		physicalDelete(entity.getHookId());

	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, Integer hookId) {
		WebhookConfigsEntity db = selectOnKey(hookId);
		db.setDeleteFlag(1);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer hookId) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		delete(user, hookId);
	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, WebhookConfigsEntity entity) {
		delete(user, entity.getHookId());

	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(WebhookConfigsEntity entity) {
		delete(entity.getHookId());

	}
	/**
	 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, Integer hookId) {
		WebhookConfigsEntity db = physicalSelectOnKey(hookId);
		db.setDeleteFlag(0);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer hookId) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		activation(user, hookId);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, WebhookConfigsEntity entity) {
		activation(user, entity.getHookId());

	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(WebhookConfigsEntity entity) {
		activation(entity.getHookId());

	}

}
