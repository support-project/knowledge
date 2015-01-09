package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.ViewHistoriesEntity;
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
 * ナレッジの参照履歴
 */
@DI(instance=Instance.Singleton)
public class GenViewHistoriesDao extends AbstractDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenViewHistoriesDao get() {
		return Container.getComp(GenViewHistoriesDao.class);
	}

	/**
	 * 全て取得(削除フラグを無視して取得) 
	 */
	public List<ViewHistoriesEntity> physicalSelectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ViewHistoriesDao/ViewHistoriesDao_physical_select_all.sql");
		return executeQuery(sql, ViewHistoriesEntity.class);
	}
	/**
	 * キーで1件取得(削除フラグを無視して取得) 
	 */
	public ViewHistoriesEntity physicalSelectOnKey(Long historyNo) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ViewHistoriesDao/ViewHistoriesDao_physical_select_on_key.sql");
		return executeQueryOnKey(sql, ViewHistoriesEntity.class, historyNo);
	}
	/**
	 * 全て取得 
	 */
	public List<ViewHistoriesEntity> selectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ViewHistoriesDao/ViewHistoriesDao_select_all.sql");
		return executeQuery(sql, ViewHistoriesEntity.class);
	}
	/**
	 * キーで1件取得 
	 */
	public ViewHistoriesEntity selectOnKey(Long historyNo) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ViewHistoriesDao/ViewHistoriesDao_select_on_key.sql");
		return executeQueryOnKey(sql, ViewHistoriesEntity.class, historyNo);
	}
	/**
	 * 登録(データを生で操作) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public ViewHistoriesEntity physicalInsert(ViewHistoriesEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ViewHistoriesDao/ViewHistoriesDao_insert.sql");
		Class<?> type = PropertyUtil.getPropertyType(entity, "historyNo");
		Object key = executeInsert(sql, type, 
			entity.getHistoryNo()
			, entity.getKnowledgeId()
			, entity.getViewDateTime()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
		);
		PropertyUtil.setPropertyValue(entity, "historyNo", key);
		return entity;
	}
	/**
	 * 登録(登録ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public ViewHistoriesEntity insert(Integer user, ViewHistoriesEntity entity) {
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
	public ViewHistoriesEntity insert(ViewHistoriesEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return insert(userId, entity);
	}
	/**
	 * 更新(データを生で操作) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public ViewHistoriesEntity physicalUpdate(ViewHistoriesEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ViewHistoriesDao/ViewHistoriesDao_update.sql");
		executeUpdate(sql, 
			entity.getKnowledgeId()
			, entity.getViewDateTime()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
			, entity.getHistoryNo()
		);
		return entity;
	}
	/**
	 * 更新(更新ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public ViewHistoriesEntity update(Integer user, ViewHistoriesEntity entity) {
		ViewHistoriesEntity db = selectOnKey(entity.getHistoryNo());
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
	public ViewHistoriesEntity update(ViewHistoriesEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return update(userId, entity);
	}
	/**
	 * 保存(ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public ViewHistoriesEntity save(Integer user, ViewHistoriesEntity entity) {
		ViewHistoriesEntity db = selectOnKey(entity.getHistoryNo());
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
	public ViewHistoriesEntity save(ViewHistoriesEntity entity) {
		ViewHistoriesEntity db = selectOnKey(entity.getHistoryNo());
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
	public void physicalDelete(Long historyNo) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ViewHistoriesDao/ViewHistoriesDao_delete.sql");
		executeUpdate(sql, historyNo);
	}
	/**
	 * 削除(データを生で操作/物理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void physicalDelete(ViewHistoriesEntity entity) {
		physicalDelete(entity.getHistoryNo());

	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, Long historyNo) {
		ViewHistoriesEntity db = selectOnKey(historyNo);
		db.setDeleteFlag(1);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Long historyNo) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		delete(user, historyNo);
	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, ViewHistoriesEntity entity) {
		delete(user, entity.getHistoryNo());

	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(ViewHistoriesEntity entity) {
		delete(entity.getHistoryNo());

	}
	/**
	 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, Long historyNo) {
		ViewHistoriesEntity db = physicalSelectOnKey(historyNo);
		db.setDeleteFlag(0);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Long historyNo) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		activation(user, historyNo);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, ViewHistoriesEntity entity) {
		activation(user, entity.getHistoryNo());

	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(ViewHistoriesEntity entity) {
		activation(entity.getHistoryNo());

	}

}
