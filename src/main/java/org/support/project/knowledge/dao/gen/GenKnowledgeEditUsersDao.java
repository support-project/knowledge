package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.KnowledgeEditUsersEntity;
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
 * 編集可能なユーザ
 */
@DI(instance=Instance.Singleton)
public class GenKnowledgeEditUsersDao extends AbstractDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenKnowledgeEditUsersDao get() {
		return Container.getComp(GenKnowledgeEditUsersDao.class);
	}

	/**
	 * 全て取得(削除フラグを無視して取得) 
	 */
	public List<KnowledgeEditUsersEntity> physicalSelectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditUsersDao/KnowledgeEditUsersDao_physical_select_all.sql");
		return executeQueryList(sql, KnowledgeEditUsersEntity.class);
	}
	/**
	 * キーで1件取得(削除フラグを無視して取得) 
	 */
	public KnowledgeEditUsersEntity physicalSelectOnKey(Long knowledgeId, Integer userId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditUsersDao/KnowledgeEditUsersDao_physical_select_on_key.sql");
		return executeQuerySingle(sql, KnowledgeEditUsersEntity.class, knowledgeId, userId);
	}
	/**
	 * 全て取得 
	 */
	public List<KnowledgeEditUsersEntity> selectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditUsersDao/KnowledgeEditUsersDao_select_all.sql");
		return executeQueryList(sql, KnowledgeEditUsersEntity.class);
	}
	/**
	 * キーで1件取得 
	 */
	public KnowledgeEditUsersEntity selectOnKey(Long knowledgeId, Integer userId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditUsersDao/KnowledgeEditUsersDao_select_on_key.sql");
		return executeQuerySingle(sql, KnowledgeEditUsersEntity.class, knowledgeId, userId);
	}
	/**
	 * KNOWLEDGE_ID でリストを取得
	 */
	public List<KnowledgeEditUsersEntity> selectOnKnowledgeId(Long knowledgeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditUsersDao/KnowledgeEditUsersDao_select_on_knowledge_id.sql");
		return executeQueryList(sql, KnowledgeEditUsersEntity.class, knowledgeId);
	}
	/**
	 * USER_ID でリストを取得
	 */
	public List<KnowledgeEditUsersEntity> selectOnUserId(Integer userId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditUsersDao/KnowledgeEditUsersDao_select_on_user_id.sql");
		return executeQueryList(sql, KnowledgeEditUsersEntity.class, userId);
	}
	/**
	 * KNOWLEDGE_ID でリストを取得
	 */
	public List<KnowledgeEditUsersEntity> physicalSelectOnKnowledgeId(Long knowledgeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditUsersDao/KnowledgeEditUsersDao_physical_select_on_knowledge_id.sql");
		return executeQueryList(sql, KnowledgeEditUsersEntity.class, knowledgeId);
	}
	/**
	 * USER_ID でリストを取得
	 */
	public List<KnowledgeEditUsersEntity> physicalSelectOnUserId(Integer userId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditUsersDao/KnowledgeEditUsersDao_physical_select_on_user_id.sql");
		return executeQueryList(sql, KnowledgeEditUsersEntity.class, userId);
	}
	/**
	 * 登録(データを生で操作/DBの採番機能のカラムも自分でセット) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public KnowledgeEditUsersEntity rawPhysicalInsert(KnowledgeEditUsersEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditUsersDao/KnowledgeEditUsersDao_raw_insert.sql");
		executeUpdate(sql, 
			entity.getKnowledgeId()
			, entity.getUserId()
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
	public KnowledgeEditUsersEntity physicalInsert(KnowledgeEditUsersEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditUsersDao/KnowledgeEditUsersDao_insert.sql");
		executeUpdate(sql, 
			entity.getKnowledgeId()
			, entity.getUserId()
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
	public KnowledgeEditUsersEntity insert(Integer user, KnowledgeEditUsersEntity entity) {
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
	public KnowledgeEditUsersEntity insert(KnowledgeEditUsersEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return insert(userId, entity);
	}
	/**
	 * 更新(データを生で操作) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public KnowledgeEditUsersEntity physicalUpdate(KnowledgeEditUsersEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditUsersDao/KnowledgeEditUsersDao_update.sql");
		executeUpdate(sql, 
			entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
			, entity.getKnowledgeId()
			, entity.getUserId()
		);
		return entity;
	}
	/**
	 * 更新(更新ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public KnowledgeEditUsersEntity update(Integer user, KnowledgeEditUsersEntity entity) {
		KnowledgeEditUsersEntity db = selectOnKey(entity.getKnowledgeId(), entity.getUserId());
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
	public KnowledgeEditUsersEntity update(KnowledgeEditUsersEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return update(userId, entity);
	}
	/**
	 * 保存(ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public KnowledgeEditUsersEntity save(Integer user, KnowledgeEditUsersEntity entity) {
		KnowledgeEditUsersEntity db = selectOnKey(entity.getKnowledgeId(), entity.getUserId());
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
	public KnowledgeEditUsersEntity save(KnowledgeEditUsersEntity entity) {
		KnowledgeEditUsersEntity db = selectOnKey(entity.getKnowledgeId(), entity.getUserId());
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
	public void physicalDelete(Long knowledgeId, Integer userId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditUsersDao/KnowledgeEditUsersDao_delete.sql");
		executeUpdate(sql, knowledgeId, userId);
	}
	/**
	 * 削除(データを生で操作/物理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void physicalDelete(KnowledgeEditUsersEntity entity) {
		physicalDelete(entity.getKnowledgeId(), entity.getUserId());

	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, Long knowledgeId, Integer userId) {
		KnowledgeEditUsersEntity db = selectOnKey(knowledgeId, userId);
		db.setDeleteFlag(1);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Long knowledgeId, Integer userId) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		delete(user, knowledgeId, userId);
	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, KnowledgeEditUsersEntity entity) {
		delete(user, entity.getKnowledgeId(), entity.getUserId());

	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(KnowledgeEditUsersEntity entity) {
		delete(entity.getKnowledgeId(), entity.getUserId());

	}
	/**
	 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, Long knowledgeId, Integer userId) {
		KnowledgeEditUsersEntity db = physicalSelectOnKey(knowledgeId, userId);
		db.setDeleteFlag(0);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Long knowledgeId, Integer userId) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		activation(user, knowledgeId, userId);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, KnowledgeEditUsersEntity entity) {
		activation(user, entity.getKnowledgeId(), entity.getUserId());

	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(KnowledgeEditUsersEntity entity) {
		activation(entity.getKnowledgeId(), entity.getUserId());

	}

}
