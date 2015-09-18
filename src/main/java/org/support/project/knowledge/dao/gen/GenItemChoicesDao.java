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
import org.support.project.ormapping.connection.ConnectionManager;
import org.support.project.common.util.PropertyUtil;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.aop.Aspect;

/**
 * 選択肢の値
 */
@DI(instance=Instance.Singleton)
public class GenItemChoicesDao extends AbstractDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenItemChoicesDao get() {
		return Container.getComp(GenItemChoicesDao.class);
	}

	/**
	 * 全て取得(削除フラグを無視して取得) 
	 */
	public List<ItemChoicesEntity> physicalSelectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ItemChoicesDao/ItemChoicesDao_physical_select_all.sql");
		return executeQueryList(sql, ItemChoicesEntity.class);
	}
	/**
	 * キーで1件取得(削除フラグを無視して取得) 
	 */
	public ItemChoicesEntity physicalSelectOnKey(Integer choiceNo, Integer itemNo, Integer typeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ItemChoicesDao/ItemChoicesDao_physical_select_on_key.sql");
		return executeQuerySingle(sql, ItemChoicesEntity.class, choiceNo, itemNo, typeId);
	}
	/**
	 * 全て取得 
	 */
	public List<ItemChoicesEntity> selectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ItemChoicesDao/ItemChoicesDao_select_all.sql");
		return executeQueryList(sql, ItemChoicesEntity.class);
	}
	/**
	 * キーで1件取得 
	 */
	public ItemChoicesEntity selectOnKey(Integer choiceNo, Integer itemNo, Integer typeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ItemChoicesDao/ItemChoicesDao_select_on_key.sql");
		return executeQuerySingle(sql, ItemChoicesEntity.class, choiceNo, itemNo, typeId);
	}
	/**
	 * CHOICE_NO でリストを取得
	 */
	public List<ItemChoicesEntity> selectOnChoiceNo(Integer choiceNo) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ItemChoicesDao/ItemChoicesDao_select_on_choice_no.sql");
		return executeQueryList(sql, ItemChoicesEntity.class, choiceNo);
	}
	/**
	 * ITEM_NO でリストを取得
	 */
	public List<ItemChoicesEntity> selectOnItemNo(Integer itemNo) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ItemChoicesDao/ItemChoicesDao_select_on_item_no.sql");
		return executeQueryList(sql, ItemChoicesEntity.class, itemNo);
	}
	/**
	 * TYPE_ID でリストを取得
	 */
	public List<ItemChoicesEntity> selectOnTypeId(Integer typeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ItemChoicesDao/ItemChoicesDao_select_on_type_id.sql");
		return executeQueryList(sql, ItemChoicesEntity.class, typeId);
	}
	/**
	 * CHOICE_NO でリストを取得
	 */
	public List<ItemChoicesEntity> physicalSelectOnChoiceNo(Integer choiceNo) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ItemChoicesDao/ItemChoicesDao_physical_select_on_choice_no.sql");
		return executeQueryList(sql, ItemChoicesEntity.class, choiceNo);
	}
	/**
	 * ITEM_NO でリストを取得
	 */
	public List<ItemChoicesEntity> physicalSelectOnItemNo(Integer itemNo) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ItemChoicesDao/ItemChoicesDao_physical_select_on_item_no.sql");
		return executeQueryList(sql, ItemChoicesEntity.class, itemNo);
	}
	/**
	 * TYPE_ID でリストを取得
	 */
	public List<ItemChoicesEntity> physicalSelectOnTypeId(Integer typeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ItemChoicesDao/ItemChoicesDao_physical_select_on_type_id.sql");
		return executeQueryList(sql, ItemChoicesEntity.class, typeId);
	}
	/**
	 * 登録(データを生で操作/DBの採番機能のカラムも自分でセット) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public ItemChoicesEntity rawPhysicalInsert(ItemChoicesEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ItemChoicesDao/ItemChoicesDao_raw_insert.sql");
		executeUpdate(sql, 
			entity.getChoiceNo()
			, entity.getItemNo()
			, entity.getTypeId()
			, entity.getChoiceValue()
			, entity.getChoiceLabel()
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
	public ItemChoicesEntity physicalInsert(ItemChoicesEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ItemChoicesDao/ItemChoicesDao_insert.sql");
		executeUpdate(sql, 
			entity.getChoiceNo()
			, entity.getItemNo()
			, entity.getTypeId()
			, entity.getChoiceValue()
			, entity.getChoiceLabel()
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
	public ItemChoicesEntity insert(Integer user, ItemChoicesEntity entity) {
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
	public ItemChoicesEntity insert(ItemChoicesEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return insert(userId, entity);
	}
	/**
	 * 更新(データを生で操作) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public ItemChoicesEntity physicalUpdate(ItemChoicesEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ItemChoicesDao/ItemChoicesDao_update.sql");
		executeUpdate(sql, 
			entity.getChoiceValue()
			, entity.getChoiceLabel()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
			, entity.getChoiceNo()
			, entity.getItemNo()
			, entity.getTypeId()
		);
		return entity;
	}
	/**
	 * 更新(更新ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public ItemChoicesEntity update(Integer user, ItemChoicesEntity entity) {
		ItemChoicesEntity db = selectOnKey(entity.getChoiceNo(), entity.getItemNo(), entity.getTypeId());
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
	public ItemChoicesEntity update(ItemChoicesEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return update(userId, entity);
	}
	/**
	 * 保存(ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public ItemChoicesEntity save(Integer user, ItemChoicesEntity entity) {
		ItemChoicesEntity db = selectOnKey(entity.getChoiceNo(), entity.getItemNo(), entity.getTypeId());
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
	public ItemChoicesEntity save(ItemChoicesEntity entity) {
		ItemChoicesEntity db = selectOnKey(entity.getChoiceNo(), entity.getItemNo(), entity.getTypeId());
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
	public void physicalDelete(Integer choiceNo, Integer itemNo, Integer typeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ItemChoicesDao/ItemChoicesDao_delete.sql");
		executeUpdate(sql, choiceNo, itemNo, typeId);
	}
	/**
	 * 削除(データを生で操作/物理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void physicalDelete(ItemChoicesEntity entity) {
		physicalDelete(entity.getChoiceNo(), entity.getItemNo(), entity.getTypeId());

	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, Integer choiceNo, Integer itemNo, Integer typeId) {
		ItemChoicesEntity db = selectOnKey(choiceNo, itemNo, typeId);
		db.setDeleteFlag(1);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer choiceNo, Integer itemNo, Integer typeId) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		delete(user, choiceNo, itemNo, typeId);
	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, ItemChoicesEntity entity) {
		delete(user, entity.getChoiceNo(), entity.getItemNo(), entity.getTypeId());

	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(ItemChoicesEntity entity) {
		delete(entity.getChoiceNo(), entity.getItemNo(), entity.getTypeId());

	}
	/**
	 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, Integer choiceNo, Integer itemNo, Integer typeId) {
		ItemChoicesEntity db = physicalSelectOnKey(choiceNo, itemNo, typeId);
		db.setDeleteFlag(0);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer choiceNo, Integer itemNo, Integer typeId) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		activation(user, choiceNo, itemNo, typeId);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, ItemChoicesEntity entity) {
		activation(user, entity.getChoiceNo(), entity.getItemNo(), entity.getTypeId());

	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(ItemChoicesEntity entity) {
		activation(entity.getChoiceNo(), entity.getItemNo(), entity.getTypeId());

	}

}
