package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.TemplateMastersEntity;
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
 * テンプレートのマスタ
 */
@DI(instance=Instance.Singleton)
public class GenTemplateMastersDao extends AbstractDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenTemplateMastersDao get() {
		return Container.getComp(GenTemplateMastersDao.class);
	}

	/**
	 * 全て取得(削除フラグを無視して取得) 
	 */
	public List<TemplateMastersEntity> physicalSelectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateMastersDao/TemplateMastersDao_physical_select_all.sql");
		return executeQueryList(sql, TemplateMastersEntity.class);
	}
	/**
	 * キーで1件取得(削除フラグを無視して取得) 
	 */
	public TemplateMastersEntity physicalSelectOnKey(Integer typeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateMastersDao/TemplateMastersDao_physical_select_on_key.sql");
		return executeQuerySingle(sql, TemplateMastersEntity.class, typeId);
	}
	/**
	 * 全て取得 
	 */
	public List<TemplateMastersEntity> selectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateMastersDao/TemplateMastersDao_select_all.sql");
		return executeQueryList(sql, TemplateMastersEntity.class);
	}
	/**
	 * キーで1件取得 
	 */
	public TemplateMastersEntity selectOnKey(Integer typeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateMastersDao/TemplateMastersDao_select_on_key.sql");
		return executeQuerySingle(sql, TemplateMastersEntity.class, typeId);
	}
	/**
	 * 登録(データを生で操作/DBの採番機能のカラムも自分でセット) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public TemplateMastersEntity rawPhysicalInsert(TemplateMastersEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateMastersDao/TemplateMastersDao_raw_insert.sql");
		executeUpdate(sql, 
			entity.getTypeId()
			, entity.getTypeName()
			, entity.getTypeIcon()
			, entity.getDescription()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
		);
		String driverClass = ConnectionManager.getInstance().getDriverClass(getConnectionName());
		if (ORMappingParameter.DRIVER_NAME_POSTGRESQL.equals(driverClass)) {
			String setValSql = "select setval('TEMPLATE_MASTERS_TYPE_ID_seq', (select max(TYPE_ID) from TEMPLATE_MASTERS));";
			executeQuerySingle(setValSql, Long.class);
		}
		return entity;
	}
	/**
	 * 登録(データを生で操作) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public TemplateMastersEntity physicalInsert(TemplateMastersEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateMastersDao/TemplateMastersDao_insert.sql");
		Class<?> type = PropertyUtil.getPropertyType(entity, "typeId");
		Object key = executeInsert(sql, type, 
			entity.getTypeName()
			, entity.getTypeIcon()
			, entity.getDescription()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
		);
		PropertyUtil.setPropertyValue(entity, "typeId", key);
		return entity;
	}
	/**
	 * 登録(登録ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public TemplateMastersEntity insert(Integer user, TemplateMastersEntity entity) {
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
	public TemplateMastersEntity insert(TemplateMastersEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return insert(userId, entity);
	}
	/**
	 * 更新(データを生で操作) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public TemplateMastersEntity physicalUpdate(TemplateMastersEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateMastersDao/TemplateMastersDao_update.sql");
		executeUpdate(sql, 
			entity.getTypeName()
			, entity.getTypeIcon()
			, entity.getDescription()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
			, entity.getTypeId()
		);
		return entity;
	}
	/**
	 * 更新(更新ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public TemplateMastersEntity update(Integer user, TemplateMastersEntity entity) {
		TemplateMastersEntity db = selectOnKey(entity.getTypeId());
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
	public TemplateMastersEntity update(TemplateMastersEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return update(userId, entity);
	}
	/**
	 * 保存(ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public TemplateMastersEntity save(Integer user, TemplateMastersEntity entity) {
		TemplateMastersEntity db = selectOnKey(entity.getTypeId());
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
	public TemplateMastersEntity save(TemplateMastersEntity entity) {
		TemplateMastersEntity db = selectOnKey(entity.getTypeId());
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
	public void physicalDelete(Integer typeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateMastersDao/TemplateMastersDao_delete.sql");
		executeUpdate(sql, typeId);
	}
	/**
	 * 削除(データを生で操作/物理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void physicalDelete(TemplateMastersEntity entity) {
		physicalDelete(entity.getTypeId());

	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, Integer typeId) {
		TemplateMastersEntity db = selectOnKey(typeId);
		db.setDeleteFlag(1);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer typeId) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		delete(user, typeId);
	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, TemplateMastersEntity entity) {
		delete(user, entity.getTypeId());

	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(TemplateMastersEntity entity) {
		delete(entity.getTypeId());

	}
	/**
	 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, Integer typeId) {
		TemplateMastersEntity db = physicalSelectOnKey(typeId);
		db.setDeleteFlag(0);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer typeId) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		activation(user, typeId);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, TemplateMastersEntity entity) {
		activation(user, entity.getTypeId());

	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(TemplateMastersEntity entity) {
		activation(entity.getTypeId());

	}

}
