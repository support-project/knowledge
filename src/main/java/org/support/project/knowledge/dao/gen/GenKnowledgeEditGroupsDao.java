package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.KnowledgeEditGroupsEntity;
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
 * 編集可能なグループ
 */
@DI(instance=Instance.Singleton)
public class GenKnowledgeEditGroupsDao extends AbstractDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenKnowledgeEditGroupsDao get() {
		return Container.getComp(GenKnowledgeEditGroupsDao.class);
	}

	/**
	 * 全て取得(削除フラグを無視して取得) 
	 */
	public List<KnowledgeEditGroupsEntity> physicalSelectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditGroupsDao/KnowledgeEditGroupsDao_physical_select_all.sql");
		return executeQueryList(sql, KnowledgeEditGroupsEntity.class);
	}
	/**
	 * キーで1件取得(削除フラグを無視して取得) 
	 */
	public KnowledgeEditGroupsEntity physicalSelectOnKey(Integer groupId, Long knowledgeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditGroupsDao/KnowledgeEditGroupsDao_physical_select_on_key.sql");
		return executeQuerySingle(sql, KnowledgeEditGroupsEntity.class, groupId, knowledgeId);
	}
	/**
	 * 全て取得 
	 */
	public List<KnowledgeEditGroupsEntity> selectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditGroupsDao/KnowledgeEditGroupsDao_select_all.sql");
		return executeQueryList(sql, KnowledgeEditGroupsEntity.class);
	}
	/**
	 * キーで1件取得 
	 */
	public KnowledgeEditGroupsEntity selectOnKey(Integer groupId, Long knowledgeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditGroupsDao/KnowledgeEditGroupsDao_select_on_key.sql");
		return executeQuerySingle(sql, KnowledgeEditGroupsEntity.class, groupId, knowledgeId);
	}
	/**
	 * GROUP_ID でリストを取得
	 */
	public List<KnowledgeEditGroupsEntity> selectOnGroupId(Integer groupId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditGroupsDao/KnowledgeEditGroupsDao_select_on_group_id.sql");
		return executeQueryList(sql, KnowledgeEditGroupsEntity.class, groupId);
	}
	/**
	 * KNOWLEDGE_ID でリストを取得
	 */
	public List<KnowledgeEditGroupsEntity> selectOnKnowledgeId(Long knowledgeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditGroupsDao/KnowledgeEditGroupsDao_select_on_knowledge_id.sql");
		return executeQueryList(sql, KnowledgeEditGroupsEntity.class, knowledgeId);
	}
	/**
	 * GROUP_ID でリストを取得
	 */
	public List<KnowledgeEditGroupsEntity> physicalSelectOnGroupId(Integer groupId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditGroupsDao/KnowledgeEditGroupsDao_physical_select_on_group_id.sql");
		return executeQueryList(sql, KnowledgeEditGroupsEntity.class, groupId);
	}
	/**
	 * KNOWLEDGE_ID でリストを取得
	 */
	public List<KnowledgeEditGroupsEntity> physicalSelectOnKnowledgeId(Long knowledgeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditGroupsDao/KnowledgeEditGroupsDao_physical_select_on_knowledge_id.sql");
		return executeQueryList(sql, KnowledgeEditGroupsEntity.class, knowledgeId);
	}
	/**
	 * 登録(データを生で操作/DBの採番機能のカラムも自分でセット) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public KnowledgeEditGroupsEntity rawPhysicalInsert(KnowledgeEditGroupsEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditGroupsDao/KnowledgeEditGroupsDao_raw_insert.sql");
		executeUpdate(sql, 
			entity.getGroupId()
			, entity.getKnowledgeId()
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
	public KnowledgeEditGroupsEntity physicalInsert(KnowledgeEditGroupsEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditGroupsDao/KnowledgeEditGroupsDao_insert.sql");
		executeUpdate(sql, 
			entity.getGroupId()
			, entity.getKnowledgeId()
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
	public KnowledgeEditGroupsEntity insert(Integer user, KnowledgeEditGroupsEntity entity) {
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
	public KnowledgeEditGroupsEntity insert(KnowledgeEditGroupsEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return insert(userId, entity);
	}
	/**
	 * 更新(データを生で操作) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public KnowledgeEditGroupsEntity physicalUpdate(KnowledgeEditGroupsEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditGroupsDao/KnowledgeEditGroupsDao_update.sql");
		executeUpdate(sql, 
			entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
			, entity.getGroupId()
			, entity.getKnowledgeId()
		);
		return entity;
	}
	/**
	 * 更新(更新ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public KnowledgeEditGroupsEntity update(Integer user, KnowledgeEditGroupsEntity entity) {
		KnowledgeEditGroupsEntity db = selectOnKey(entity.getGroupId(), entity.getKnowledgeId());
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
	public KnowledgeEditGroupsEntity update(KnowledgeEditGroupsEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return update(userId, entity);
	}
	/**
	 * 保存(ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public KnowledgeEditGroupsEntity save(Integer user, KnowledgeEditGroupsEntity entity) {
		KnowledgeEditGroupsEntity db = selectOnKey(entity.getGroupId(), entity.getKnowledgeId());
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
	public KnowledgeEditGroupsEntity save(KnowledgeEditGroupsEntity entity) {
		KnowledgeEditGroupsEntity db = selectOnKey(entity.getGroupId(), entity.getKnowledgeId());
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
	public void physicalDelete(Integer groupId, Long knowledgeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeEditGroupsDao/KnowledgeEditGroupsDao_delete.sql");
		executeUpdate(sql, groupId, knowledgeId);
	}
	/**
	 * 削除(データを生で操作/物理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void physicalDelete(KnowledgeEditGroupsEntity entity) {
		physicalDelete(entity.getGroupId(), entity.getKnowledgeId());

	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, Integer groupId, Long knowledgeId) {
		KnowledgeEditGroupsEntity db = selectOnKey(groupId, knowledgeId);
		db.setDeleteFlag(1);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer groupId, Long knowledgeId) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		delete(user, groupId, knowledgeId);
	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, KnowledgeEditGroupsEntity entity) {
		delete(user, entity.getGroupId(), entity.getKnowledgeId());

	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(KnowledgeEditGroupsEntity entity) {
		delete(entity.getGroupId(), entity.getKnowledgeId());

	}
	/**
	 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, Integer groupId, Long knowledgeId) {
		KnowledgeEditGroupsEntity db = physicalSelectOnKey(groupId, knowledgeId);
		db.setDeleteFlag(0);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer groupId, Long knowledgeId) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		activation(user, groupId, knowledgeId);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, KnowledgeEditGroupsEntity entity) {
		activation(user, entity.getGroupId(), entity.getKnowledgeId());

	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(KnowledgeEditGroupsEntity entity) {
		activation(entity.getGroupId(), entity.getKnowledgeId());

	}

}
