package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.KnowledgeUsersEntity;
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
 * アクセス可能なユーザ
 */
@DI(instance=Instance.Singleton)
public class GenKnowledgeUsersDao extends AbstractDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenKnowledgeUsersDao get() {
		return Container.getComp(GenKnowledgeUsersDao.class);
	}

	/**
	 * 全て取得(削除フラグを無視して取得) 
	 */
	public List<KnowledgeUsersEntity> physicalSelectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeUsersDao/KnowledgeUsersDao_physical_select_all.sql");
		return executeQuery(sql, KnowledgeUsersEntity.class);
	}
	/**
	 * キーで1件取得(削除フラグを無視して取得) 
	 */
	public KnowledgeUsersEntity physicalSelectOnKey(Long knowledgeId, Integer userId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeUsersDao/KnowledgeUsersDao_physical_select_on_key.sql");
		return executeQueryOnKey(sql, KnowledgeUsersEntity.class, knowledgeId, userId);
	}
	/**
	 * 全て取得 
	 */
	public List<KnowledgeUsersEntity> selectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeUsersDao/KnowledgeUsersDao_select_all.sql");
		return executeQuery(sql, KnowledgeUsersEntity.class);
	}
	/**
	 * キーで1件取得 
	 */
	public KnowledgeUsersEntity selectOnKey(Long knowledgeId, Integer userId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeUsersDao/KnowledgeUsersDao_select_on_key.sql");
		return executeQueryOnKey(sql, KnowledgeUsersEntity.class, knowledgeId, userId);
	}
	/**
	 * KNOWLEDGE_ID でリストを取得
	 */
	public List<KnowledgeUsersEntity> selectOnKnowledgeId(Long knowledgeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeUsersDao/KnowledgeUsersDao_select_on_knowledge_id.sql");
		return executeQuery(sql, KnowledgeUsersEntity.class, knowledgeId);
	}
	/**
	 * USER_ID でリストを取得
	 */
	public List<KnowledgeUsersEntity> selectOnUserId(Integer userId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeUsersDao/KnowledgeUsersDao_select_on_user_id.sql");
		return executeQuery(sql, KnowledgeUsersEntity.class, userId);
	}
	/**
	 * KNOWLEDGE_ID でリストを取得
	 */
	public List<KnowledgeUsersEntity> physicalSelectOnKnowledgeId(Long knowledgeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeUsersDao/KnowledgeUsersDao_physical_select_on_knowledge_id.sql");
		return executeQuery(sql, KnowledgeUsersEntity.class, knowledgeId);
	}
	/**
	 * USER_ID でリストを取得
	 */
	public List<KnowledgeUsersEntity> physicalSelectOnUserId(Integer userId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeUsersDao/KnowledgeUsersDao_physical_select_on_user_id.sql");
		return executeQuery(sql, KnowledgeUsersEntity.class, userId);
	}
	/**
	 * 登録(データを生で操作) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public KnowledgeUsersEntity physicalInsert(KnowledgeUsersEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeUsersDao/KnowledgeUsersDao_insert.sql");
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
	public KnowledgeUsersEntity insert(Integer user, KnowledgeUsersEntity entity) {
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
	public KnowledgeUsersEntity insert(KnowledgeUsersEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return insert(userId, entity);
	}
	/**
	 * 更新(データを生で操作) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public KnowledgeUsersEntity physicalUpdate(KnowledgeUsersEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeUsersDao/KnowledgeUsersDao_update.sql");
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
	public KnowledgeUsersEntity update(Integer user, KnowledgeUsersEntity entity) {
		KnowledgeUsersEntity db = selectOnKey(entity.getKnowledgeId(), entity.getUserId());
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
	public KnowledgeUsersEntity update(KnowledgeUsersEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return update(userId, entity);
	}
	/**
	 * 保存(ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public KnowledgeUsersEntity save(Integer user, KnowledgeUsersEntity entity) {
		KnowledgeUsersEntity db = selectOnKey(entity.getKnowledgeId(), entity.getUserId());
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
	public KnowledgeUsersEntity save(KnowledgeUsersEntity entity) {
		KnowledgeUsersEntity db = selectOnKey(entity.getKnowledgeId(), entity.getUserId());
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
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeUsersDao/KnowledgeUsersDao_delete.sql");
		executeUpdate(sql, knowledgeId, userId);
	}
	/**
	 * 削除(データを生で操作/物理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void physicalDelete(KnowledgeUsersEntity entity) {
		physicalDelete(entity.getKnowledgeId(), entity.getUserId());

	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, Long knowledgeId, Integer userId) {
		KnowledgeUsersEntity db = selectOnKey(knowledgeId, userId);
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
	public void delete(Integer user, KnowledgeUsersEntity entity) {
		delete(user, entity.getKnowledgeId(), entity.getUserId());

	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(KnowledgeUsersEntity entity) {
		delete(entity.getKnowledgeId(), entity.getUserId());

	}
	/**
	 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, Long knowledgeId, Integer userId) {
		KnowledgeUsersEntity db = physicalSelectOnKey(knowledgeId, userId);
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
	public void activation(Integer user, KnowledgeUsersEntity entity) {
		activation(user, entity.getKnowledgeId(), entity.getUserId());

	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(KnowledgeUsersEntity entity) {
		activation(entity.getKnowledgeId(), entity.getUserId());

	}

}
