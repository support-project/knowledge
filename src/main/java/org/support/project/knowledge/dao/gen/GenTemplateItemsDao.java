package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.TemplateItemsEntity;
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
 * テンプレートの項目
 */
@DI(instance=Instance.Singleton)
public class GenTemplateItemsDao extends AbstractDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenTemplateItemsDao get() {
		return Container.getComp(GenTemplateItemsDao.class);
	}

	/**
	 * 全て取得(削除フラグを無視して取得) 
	 */
	public List<TemplateItemsEntity> physicalSelectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateItemsDao/TemplateItemsDao_physical_select_all.sql");
		return executeQueryList(sql, TemplateItemsEntity.class);
	}
	/**
	 * キーで1件取得(削除フラグを無視して取得) 
	 */
	public TemplateItemsEntity physicalSelectOnKey(Integer itemNo, Integer typeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateItemsDao/TemplateItemsDao_physical_select_on_key.sql");
		return executeQuerySingle(sql, TemplateItemsEntity.class, itemNo, typeId);
	}
	/**
	 * 全て取得 
	 */
	public List<TemplateItemsEntity> selectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateItemsDao/TemplateItemsDao_select_all.sql");
		return executeQueryList(sql, TemplateItemsEntity.class);
	}
	/**
	 * キーで1件取得 
	 */
	public TemplateItemsEntity selectOnKey(Integer itemNo, Integer typeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateItemsDao/TemplateItemsDao_select_on_key.sql");
		return executeQuerySingle(sql, TemplateItemsEntity.class, itemNo, typeId);
	}
	/**
	 * ITEM_NO でリストを取得
	 */
	public List<TemplateItemsEntity> selectOnItemNo(Integer itemNo) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateItemsDao/TemplateItemsDao_select_on_item_no.sql");
		return executeQueryList(sql, TemplateItemsEntity.class, itemNo);
	}
	/**
	 * TYPE_ID でリストを取得
	 */
	public List<TemplateItemsEntity> selectOnTypeId(Integer typeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateItemsDao/TemplateItemsDao_select_on_type_id.sql");
		return executeQueryList(sql, TemplateItemsEntity.class, typeId);
	}
	/**
	 * ITEM_NO でリストを取得
	 */
	public List<TemplateItemsEntity> physicalSelectOnItemNo(Integer itemNo) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateItemsDao/TemplateItemsDao_physical_select_on_item_no.sql");
		return executeQueryList(sql, TemplateItemsEntity.class, itemNo);
	}
	/**
	 * TYPE_ID でリストを取得
	 */
	public List<TemplateItemsEntity> physicalSelectOnTypeId(Integer typeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateItemsDao/TemplateItemsDao_physical_select_on_type_id.sql");
		return executeQueryList(sql, TemplateItemsEntity.class, typeId);
	}
	/**
	 * 登録(データを生で操作/DBの採番機能のカラムも自分でセット) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public TemplateItemsEntity rawPhysicalInsert(TemplateItemsEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateItemsDao/TemplateItemsDao_raw_insert.sql");
		executeUpdate(sql, 
			entity.getItemNo()
			, entity.getTypeId()
			, entity.getItemName()
			, entity.getItemType()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
		);
		return entity;
	}
	/**
	 * 登録(データを生で操作) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public TemplateItemsEntity physicalInsert(TemplateItemsEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateItemsDao/TemplateItemsDao_insert.sql");
		executeUpdate(sql, 
			entity.getItemNo()
			, entity.getTypeId()
			, entity.getItemName()
			, entity.getItemType()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
		);
		return entity;
	}
	/**
	 * 登録(登録ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public TemplateItemsEntity insert(Integer user, TemplateItemsEntity entity) {
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
	public TemplateItemsEntity insert(TemplateItemsEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return insert(userId, entity);
	}
	/**
	 * 更新(データを生で操作) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public TemplateItemsEntity physicalUpdate(TemplateItemsEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateItemsDao/TemplateItemsDao_update.sql");
		executeUpdate(sql, 
			entity.getItemName()
			, entity.getItemType()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
			, entity.getItemNo()
			, entity.getTypeId()
		);
		return entity;
	}
	/**
	 * 更新(更新ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public TemplateItemsEntity update(Integer user, TemplateItemsEntity entity) {
		TemplateItemsEntity db = selectOnKey(entity.getItemNo(), entity.getTypeId());
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
	public TemplateItemsEntity update(TemplateItemsEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return update(userId, entity);
	}
	/**
	 * 保存(ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public TemplateItemsEntity save(Integer user, TemplateItemsEntity entity) {
		TemplateItemsEntity db = selectOnKey(entity.getItemNo(), entity.getTypeId());
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
	public TemplateItemsEntity save(TemplateItemsEntity entity) {
		TemplateItemsEntity db = selectOnKey(entity.getItemNo(), entity.getTypeId());
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
	public void physicalDelete(Integer itemNo, Integer typeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TemplateItemsDao/TemplateItemsDao_delete.sql");
		executeUpdate(sql, itemNo, typeId);
	}
	/**
	 * 削除(データを生で操作/物理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void physicalDelete(TemplateItemsEntity entity) {
		physicalDelete(entity.getItemNo(), entity.getTypeId());

	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, Integer itemNo, Integer typeId) {
		TemplateItemsEntity db = selectOnKey(itemNo, typeId);
		db.setDeleteFlag(1);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer itemNo, Integer typeId) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		delete(user, itemNo, typeId);
	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, TemplateItemsEntity entity) {
		delete(user, entity.getItemNo(), entity.getTypeId());

	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(TemplateItemsEntity entity) {
		delete(entity.getItemNo(), entity.getTypeId());

	}
	/**
	 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, Integer itemNo, Integer typeId) {
		TemplateItemsEntity db = physicalSelectOnKey(itemNo, typeId);
		db.setDeleteFlag(0);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer itemNo, Integer typeId) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		activation(user, itemNo, typeId);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, TemplateItemsEntity entity) {
		activation(user, entity.getItemNo(), entity.getTypeId());

	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(TemplateItemsEntity entity) {
		activation(entity.getItemNo(), entity.getTypeId());

	}
}
