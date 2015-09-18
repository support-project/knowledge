package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.StockKnowledgesEntity;
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
 * ストックしたナレッジ
 */
@DI(instance=Instance.Singleton)
public class GenStockKnowledgesDao extends AbstractDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenStockKnowledgesDao get() {
		return Container.getComp(GenStockKnowledgesDao.class);
	}

	/**
	 * 全て取得(削除フラグを無視して取得) 
	 */
	public List<StockKnowledgesEntity> physicalSelectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/StockKnowledgesDao/StockKnowledgesDao_physical_select_all.sql");
		return executeQueryList(sql, StockKnowledgesEntity.class);
	}
	/**
	 * キーで1件取得(削除フラグを無視して取得) 
	 */
	public StockKnowledgesEntity physicalSelectOnKey(Long knowledgeId, Long stockId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/StockKnowledgesDao/StockKnowledgesDao_physical_select_on_key.sql");
		return executeQuerySingle(sql, StockKnowledgesEntity.class, knowledgeId, stockId);
	}
	/**
	 * 全て取得 
	 */
	public List<StockKnowledgesEntity> selectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/StockKnowledgesDao/StockKnowledgesDao_select_all.sql");
		return executeQueryList(sql, StockKnowledgesEntity.class);
	}
	/**
	 * キーで1件取得 
	 */
	public StockKnowledgesEntity selectOnKey(Long knowledgeId, Long stockId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/StockKnowledgesDao/StockKnowledgesDao_select_on_key.sql");
		return executeQuerySingle(sql, StockKnowledgesEntity.class, knowledgeId, stockId);
	}
	/**
	 * KNOWLEDGE_ID でリストを取得
	 */
	public List<StockKnowledgesEntity> selectOnKnowledgeId(Long knowledgeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/StockKnowledgesDao/StockKnowledgesDao_select_on_knowledge_id.sql");
		return executeQueryList(sql, StockKnowledgesEntity.class, knowledgeId);
	}
	/**
	 * STOCK_ID でリストを取得
	 */
	public List<StockKnowledgesEntity> selectOnStockId(Long stockId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/StockKnowledgesDao/StockKnowledgesDao_select_on_stock_id.sql");
		return executeQueryList(sql, StockKnowledgesEntity.class, stockId);
	}
	/**
	 * KNOWLEDGE_ID でリストを取得
	 */
	public List<StockKnowledgesEntity> physicalSelectOnKnowledgeId(Long knowledgeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/StockKnowledgesDao/StockKnowledgesDao_physical_select_on_knowledge_id.sql");
		return executeQueryList(sql, StockKnowledgesEntity.class, knowledgeId);
	}
	/**
	 * STOCK_ID でリストを取得
	 */
	public List<StockKnowledgesEntity> physicalSelectOnStockId(Long stockId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/StockKnowledgesDao/StockKnowledgesDao_physical_select_on_stock_id.sql");
		return executeQueryList(sql, StockKnowledgesEntity.class, stockId);
	}
	/**
	 * 登録(データを生で操作/DBの採番機能のカラムも自分でセット) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public StockKnowledgesEntity rawPhysicalInsert(StockKnowledgesEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/StockKnowledgesDao/StockKnowledgesDao_raw_insert.sql");
		executeUpdate(sql, 
			entity.getKnowledgeId()
			, entity.getStockId()
			, entity.getComment()
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
	public StockKnowledgesEntity physicalInsert(StockKnowledgesEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/StockKnowledgesDao/StockKnowledgesDao_insert.sql");
		executeUpdate(sql, 
			entity.getKnowledgeId()
			, entity.getStockId()
			, entity.getComment()
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
	public StockKnowledgesEntity insert(Integer user, StockKnowledgesEntity entity) {
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
	public StockKnowledgesEntity insert(StockKnowledgesEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return insert(userId, entity);
	}
	/**
	 * 更新(データを生で操作) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public StockKnowledgesEntity physicalUpdate(StockKnowledgesEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/StockKnowledgesDao/StockKnowledgesDao_update.sql");
		executeUpdate(sql, 
			entity.getComment()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
			, entity.getKnowledgeId()
			, entity.getStockId()
		);
		return entity;
	}
	/**
	 * 更新(更新ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public StockKnowledgesEntity update(Integer user, StockKnowledgesEntity entity) {
		StockKnowledgesEntity db = selectOnKey(entity.getKnowledgeId(), entity.getStockId());
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
	public StockKnowledgesEntity update(StockKnowledgesEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return update(userId, entity);
	}
	/**
	 * 保存(ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public StockKnowledgesEntity save(Integer user, StockKnowledgesEntity entity) {
		StockKnowledgesEntity db = selectOnKey(entity.getKnowledgeId(), entity.getStockId());
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
	public StockKnowledgesEntity save(StockKnowledgesEntity entity) {
		StockKnowledgesEntity db = selectOnKey(entity.getKnowledgeId(), entity.getStockId());
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
	public void physicalDelete(Long knowledgeId, Long stockId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/StockKnowledgesDao/StockKnowledgesDao_delete.sql");
		executeUpdate(sql, knowledgeId, stockId);
	}
	/**
	 * 削除(データを生で操作/物理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void physicalDelete(StockKnowledgesEntity entity) {
		physicalDelete(entity.getKnowledgeId(), entity.getStockId());

	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, Long knowledgeId, Long stockId) {
		StockKnowledgesEntity db = selectOnKey(knowledgeId, stockId);
		db.setDeleteFlag(1);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Long knowledgeId, Long stockId) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		delete(user, knowledgeId, stockId);
	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, StockKnowledgesEntity entity) {
		delete(user, entity.getKnowledgeId(), entity.getStockId());

	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(StockKnowledgesEntity entity) {
		delete(entity.getKnowledgeId(), entity.getStockId());

	}
	/**
	 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, Long knowledgeId, Long stockId) {
		StockKnowledgesEntity db = physicalSelectOnKey(knowledgeId, stockId);
		db.setDeleteFlag(0);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Long knowledgeId, Long stockId) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		activation(user, knowledgeId, stockId);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, StockKnowledgesEntity entity) {
		activation(user, entity.getKnowledgeId(), entity.getStockId());

	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(StockKnowledgesEntity entity) {
		activation(entity.getKnowledgeId(), entity.getStockId());

	}

}
