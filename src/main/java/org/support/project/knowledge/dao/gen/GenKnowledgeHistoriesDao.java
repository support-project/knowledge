package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.KnowledgeHistoriesEntity;
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
 * ナレッジ更新履歴
 */
@DI(instance=Instance.Singleton)
public class GenKnowledgeHistoriesDao extends AbstractDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenKnowledgeHistoriesDao get() {
		return Container.getComp(GenKnowledgeHistoriesDao.class);
	}

	/**
	 * 全て取得(削除フラグを無視して取得) 
	 */
	public List<KnowledgeHistoriesEntity> physicalSelectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeHistoriesDao/KnowledgeHistoriesDao_physical_select_all.sql");
		return executeQueryList(sql, KnowledgeHistoriesEntity.class);
	}
	/**
	 * キーで1件取得(削除フラグを無視して取得) 
	 */
	public KnowledgeHistoriesEntity physicalSelectOnKey(Integer historyNo, Long knowledgeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeHistoriesDao/KnowledgeHistoriesDao_physical_select_on_key.sql");
		return executeQuerySingle(sql, KnowledgeHistoriesEntity.class, historyNo, knowledgeId);
	}
	/**
	 * 全て取得 
	 */
	public List<KnowledgeHistoriesEntity> selectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeHistoriesDao/KnowledgeHistoriesDao_select_all.sql");
		return executeQueryList(sql, KnowledgeHistoriesEntity.class);
	}
	/**
	 * キーで1件取得 
	 */
	public KnowledgeHistoriesEntity selectOnKey(Integer historyNo, Long knowledgeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeHistoriesDao/KnowledgeHistoriesDao_select_on_key.sql");
		return executeQuerySingle(sql, KnowledgeHistoriesEntity.class, historyNo, knowledgeId);
	}
	/**
	 * HISTORY_NO でリストを取得
	 */
	public List<KnowledgeHistoriesEntity> selectOnHistoryNo(Integer historyNo) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeHistoriesDao/KnowledgeHistoriesDao_select_on_history_no.sql");
		return executeQueryList(sql, KnowledgeHistoriesEntity.class, historyNo);
	}
	/**
	 * KNOWLEDGE_ID でリストを取得
	 */
	public List<KnowledgeHistoriesEntity> selectOnKnowledgeId(Long knowledgeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeHistoriesDao/KnowledgeHistoriesDao_select_on_knowledge_id.sql");
		return executeQueryList(sql, KnowledgeHistoriesEntity.class, knowledgeId);
	}
	/**
	 * HISTORY_NO でリストを取得
	 */
	public List<KnowledgeHistoriesEntity> physicalSelectOnHistoryNo(Integer historyNo) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeHistoriesDao/KnowledgeHistoriesDao_physical_select_on_history_no.sql");
		return executeQueryList(sql, KnowledgeHistoriesEntity.class, historyNo);
	}
	/**
	 * KNOWLEDGE_ID でリストを取得
	 */
	public List<KnowledgeHistoriesEntity> physicalSelectOnKnowledgeId(Long knowledgeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeHistoriesDao/KnowledgeHistoriesDao_physical_select_on_knowledge_id.sql");
		return executeQueryList(sql, KnowledgeHistoriesEntity.class, knowledgeId);
	}
	/**
	 * 登録(データを生で操作/DBの採番機能のカラムも自分でセット) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public KnowledgeHistoriesEntity rawPhysicalInsert(KnowledgeHistoriesEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeHistoriesDao/KnowledgeHistoriesDao_raw_insert.sql");
		executeUpdate(sql, 
			entity.getHistoryNo()
			, entity.getKnowledgeId()
			, entity.getTitle()
			, entity.getContent()
			, entity.getPublicFlag()
			, entity.getTagIds()
			, entity.getTagNames()
			, entity.getLikeCount()
			, entity.getCommentCount()
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
	public KnowledgeHistoriesEntity physicalInsert(KnowledgeHistoriesEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeHistoriesDao/KnowledgeHistoriesDao_insert.sql");
		executeUpdate(sql, 
			entity.getHistoryNo()
			, entity.getKnowledgeId()
			, entity.getTitle()
			, entity.getContent()
			, entity.getPublicFlag()
			, entity.getTagIds()
			, entity.getTagNames()
			, entity.getLikeCount()
			, entity.getCommentCount()
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
	public KnowledgeHistoriesEntity insert(Integer user, KnowledgeHistoriesEntity entity) {
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
	public KnowledgeHistoriesEntity insert(KnowledgeHistoriesEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return insert(userId, entity);
	}
	/**
	 * 更新(データを生で操作) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public KnowledgeHistoriesEntity physicalUpdate(KnowledgeHistoriesEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeHistoriesDao/KnowledgeHistoriesDao_update.sql");
		executeUpdate(sql, 
			entity.getTitle()
			, entity.getContent()
			, entity.getPublicFlag()
			, entity.getTagIds()
			, entity.getTagNames()
			, entity.getLikeCount()
			, entity.getCommentCount()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
			, entity.getHistoryNo()
			, entity.getKnowledgeId()
		);
		return entity;
	}
	/**
	 * 更新(更新ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public KnowledgeHistoriesEntity update(Integer user, KnowledgeHistoriesEntity entity) {
		KnowledgeHistoriesEntity db = selectOnKey(entity.getHistoryNo(), entity.getKnowledgeId());
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
	public KnowledgeHistoriesEntity update(KnowledgeHistoriesEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return update(userId, entity);
	}
	/**
	 * 保存(ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public KnowledgeHistoriesEntity save(Integer user, KnowledgeHistoriesEntity entity) {
		KnowledgeHistoriesEntity db = selectOnKey(entity.getHistoryNo(), entity.getKnowledgeId());
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
	public KnowledgeHistoriesEntity save(KnowledgeHistoriesEntity entity) {
		KnowledgeHistoriesEntity db = selectOnKey(entity.getHistoryNo(), entity.getKnowledgeId());
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
	public void physicalDelete(Integer historyNo, Long knowledgeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeHistoriesDao/KnowledgeHistoriesDao_delete.sql");
		executeUpdate(sql, historyNo, knowledgeId);
	}
	/**
	 * 削除(データを生で操作/物理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void physicalDelete(KnowledgeHistoriesEntity entity) {
		physicalDelete(entity.getHistoryNo(), entity.getKnowledgeId());

	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, Integer historyNo, Long knowledgeId) {
		KnowledgeHistoriesEntity db = selectOnKey(historyNo, knowledgeId);
		db.setDeleteFlag(1);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer historyNo, Long knowledgeId) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		delete(user, historyNo, knowledgeId);
	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, KnowledgeHistoriesEntity entity) {
		delete(user, entity.getHistoryNo(), entity.getKnowledgeId());

	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(KnowledgeHistoriesEntity entity) {
		delete(entity.getHistoryNo(), entity.getKnowledgeId());

	}
	/**
	 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, Integer historyNo, Long knowledgeId) {
		KnowledgeHistoriesEntity db = physicalSelectOnKey(historyNo, knowledgeId);
		db.setDeleteFlag(0);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer historyNo, Long knowledgeId) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		activation(user, historyNo, knowledgeId);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, KnowledgeHistoriesEntity entity) {
		activation(user, entity.getHistoryNo(), entity.getKnowledgeId());

	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(KnowledgeHistoriesEntity entity) {
		activation(entity.getHistoryNo(), entity.getKnowledgeId());

	}

}
