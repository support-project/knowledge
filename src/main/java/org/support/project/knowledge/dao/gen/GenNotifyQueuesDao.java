package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.NotifyQueuesEntity;
import org.support.project.ormapping.dao.AbstractDao;
import org.support.project.ormapping.exception.ORMappingException;
import org.support.project.ormapping.common.SQLManager;
import org.support.project.ormapping.common.DBUserPool;
import org.support.project.ormapping.common.IDGen;
import org.support.project.common.util.PropertyUtil;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.aop.Aspect;

/**
 * 通知待ちキュー
 */
@DI(instance=Instance.Singleton)
public class GenNotifyQueuesDao extends AbstractDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenNotifyQueuesDao get() {
		return Container.getComp(GenNotifyQueuesDao.class);
	}

	/**
	 * 全て取得(削除フラグを無視して取得) 
	 */
	public List<NotifyQueuesEntity> physicalSelectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotifyQueuesDao/NotifyQueuesDao_physical_select_all.sql");
		return executeQuery(sql, NotifyQueuesEntity.class);
	}
	/**
	 * キーで1件取得(削除フラグを無視して取得) 
	 */
	public NotifyQueuesEntity physicalSelectOnKey(String hash) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotifyQueuesDao/NotifyQueuesDao_physical_select_on_key.sql");
		return executeQueryOnKey(sql, NotifyQueuesEntity.class, hash);
	}
	/**
	 * 全て取得 
	 */
	public List<NotifyQueuesEntity> selectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotifyQueuesDao/NotifyQueuesDao_select_all.sql");
		return executeQuery(sql, NotifyQueuesEntity.class);
	}
	/**
	 * キーで1件取得 
	 */
	public NotifyQueuesEntity selectOnKey(String hash) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotifyQueuesDao/NotifyQueuesDao_select_on_key.sql");
		return executeQueryOnKey(sql, NotifyQueuesEntity.class, hash);
	}
	/**
	 * 登録(データを生で操作) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public NotifyQueuesEntity physicalInsert(NotifyQueuesEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotifyQueuesDao/NotifyQueuesDao_insert.sql");
		executeUpdate(sql, 
			entity.getHash()
			, entity.getType()
			, entity.getId()
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
	public NotifyQueuesEntity insert(Integer user, NotifyQueuesEntity entity) {
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
	public NotifyQueuesEntity insert(NotifyQueuesEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return insert(userId, entity);
	}
	/**
	 * 更新(データを生で操作) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public NotifyQueuesEntity physicalUpdate(NotifyQueuesEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotifyQueuesDao/NotifyQueuesDao_update.sql");
		executeUpdate(sql, 
			entity.getType()
			, entity.getId()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
			, entity.getHash()
		);
		return entity;
	}
	/**
	 * 更新(更新ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public NotifyQueuesEntity update(Integer user, NotifyQueuesEntity entity) {
		NotifyQueuesEntity db = selectOnKey(entity.getHash());
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
	public NotifyQueuesEntity update(NotifyQueuesEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return update(userId, entity);
	}
	/**
	 * 保存(ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public NotifyQueuesEntity save(Integer user, NotifyQueuesEntity entity) {
		NotifyQueuesEntity db = selectOnKey(entity.getHash());
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
	public NotifyQueuesEntity save(NotifyQueuesEntity entity) {
		NotifyQueuesEntity db = selectOnKey(entity.getHash());
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
	public void physicalDelete(String hash) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/NotifyQueuesDao/NotifyQueuesDao_delete.sql");
		executeUpdate(sql, hash);
	}
	/**
	 * 削除(データを生で操作/物理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void physicalDelete(NotifyQueuesEntity entity) {
		physicalDelete(entity.getHash());

	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, String hash) {
		NotifyQueuesEntity db = selectOnKey(hash);
		db.setDeleteFlag(1);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(String hash) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		delete(user, hash);
	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, NotifyQueuesEntity entity) {
		delete(user, entity.getHash());

	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(NotifyQueuesEntity entity) {
		delete(entity.getHash());

	}
	/**
	 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, String hash) {
		NotifyQueuesEntity db = physicalSelectOnKey(hash);
		db.setDeleteFlag(0);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(String hash) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		activation(user, hash);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, NotifyQueuesEntity entity) {
		activation(user, entity.getHash());

	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(NotifyQueuesEntity entity) {
		activation(entity.getHash());

	}

}
