package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.NotifyConfigsEntity;
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
 * 通知設定
 */
@DI(instance=Instance.Singleton)
public class GenNotifyConfigsDao extends AbstractDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenNotifyConfigsDao get() {
		return Container.getComp(GenNotifyConfigsDao.class);
	}

	/**
	 * 全て取得(削除フラグを無視して取得) 
	 */
	public List<NotifyConfigsEntity> physicalSelectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotifyConfigsDao/NotifyConfigsDao_physical_select_all.sql");
		return executeQueryList(sql, NotifyConfigsEntity.class);
	}
	/**
	 * キーで1件取得(削除フラグを無視して取得) 
	 */
	public NotifyConfigsEntity physicalSelectOnKey(Integer userId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotifyConfigsDao/NotifyConfigsDao_physical_select_on_key.sql");
		return executeQuerySingle(sql, NotifyConfigsEntity.class, userId);
	}
	/**
	 * 全て取得 
	 */
	public List<NotifyConfigsEntity> selectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotifyConfigsDao/NotifyConfigsDao_select_all.sql");
		return executeQueryList(sql, NotifyConfigsEntity.class);
	}
	/**
	 * キーで1件取得 
	 */
	public NotifyConfigsEntity selectOnKey(Integer userId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotifyConfigsDao/NotifyConfigsDao_select_on_key.sql");
		return executeQuerySingle(sql, NotifyConfigsEntity.class, userId);
	}
	/**
	 * 登録(データを生で操作/DBの採番機能のカラムも自分でセット) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public NotifyConfigsEntity rawPhysicalInsert(NotifyConfigsEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotifyConfigsDao/NotifyConfigsDao_raw_insert.sql");
		executeUpdate(sql, 
			entity.getUserId()
			, entity.getNotifyMail()
			, entity.getNotifyDesktop()
			, entity.getMyItemComment()
			, entity.getMyItemLike()
			, entity.getMyItemStock()
			, entity.getToItemSave()
			, entity.getToItemComment()
			, entity.getToItemIgnorePublic()
			, entity.getStockItemSave()
			, entity.getStokeItemComment()
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
	public NotifyConfigsEntity physicalInsert(NotifyConfigsEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotifyConfigsDao/NotifyConfigsDao_insert.sql");
		executeUpdate(sql, 
			entity.getUserId()
			, entity.getNotifyMail()
			, entity.getNotifyDesktop()
			, entity.getMyItemComment()
			, entity.getMyItemLike()
			, entity.getMyItemStock()
			, entity.getToItemSave()
			, entity.getToItemComment()
			, entity.getToItemIgnorePublic()
			, entity.getStockItemSave()
			, entity.getStokeItemComment()
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
	public NotifyConfigsEntity insert(Integer user, NotifyConfigsEntity entity) {
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
	public NotifyConfigsEntity insert(NotifyConfigsEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return insert(userId, entity);
	}
	/**
	 * 更新(データを生で操作) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public NotifyConfigsEntity physicalUpdate(NotifyConfigsEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotifyConfigsDao/NotifyConfigsDao_update.sql");
		executeUpdate(sql, 
			entity.getNotifyMail()
			, entity.getNotifyDesktop()
			, entity.getMyItemComment()
			, entity.getMyItemLike()
			, entity.getMyItemStock()
			, entity.getToItemSave()
			, entity.getToItemComment()
			, entity.getToItemIgnorePublic()
			, entity.getStockItemSave()
			, entity.getStokeItemComment()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
			, entity.getUserId()
		);
		return entity;
	}
	/**
	 * 更新(更新ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public NotifyConfigsEntity update(Integer user, NotifyConfigsEntity entity) {
		NotifyConfigsEntity db = selectOnKey(entity.getUserId());
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
	public NotifyConfigsEntity update(NotifyConfigsEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return update(userId, entity);
	}
	/**
	 * 保存(ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public NotifyConfigsEntity save(Integer user, NotifyConfigsEntity entity) {
		NotifyConfigsEntity db = selectOnKey(entity.getUserId());
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
	public NotifyConfigsEntity save(NotifyConfigsEntity entity) {
		NotifyConfigsEntity db = selectOnKey(entity.getUserId());
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
	public void physicalDelete(Integer userId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotifyConfigsDao/NotifyConfigsDao_delete.sql");
		executeUpdate(sql, userId);
	}
	/**
	 * 削除(データを生で操作/物理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void physicalDelete(NotifyConfigsEntity entity) {
		physicalDelete(entity.getUserId());

	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, Integer userId) {
		NotifyConfigsEntity db = selectOnKey(userId);
		db.setDeleteFlag(1);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer userId) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		delete(user, userId);
	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, NotifyConfigsEntity entity) {
		delete(user, entity.getUserId());

	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(NotifyConfigsEntity entity) {
		delete(entity.getUserId());

	}
	/**
	 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, Integer userId) {
		NotifyConfigsEntity db = physicalSelectOnKey(userId);
		db.setDeleteFlag(0);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer userId) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		activation(user, userId);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, NotifyConfigsEntity entity) {
		activation(user, entity.getUserId());

	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(NotifyConfigsEntity entity) {
		activation(entity.getUserId());

	}

}
