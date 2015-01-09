package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.KnowledgeTagsEntity;
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
 * ナレッジが持つタグ
 */
@DI(instance=Instance.Singleton)
public class GenKnowledgeTagsDao extends AbstractDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenKnowledgeTagsDao get() {
		return Container.getComp(GenKnowledgeTagsDao.class);
	}

	/**
	 * 全て取得(削除フラグを無視して取得) 
	 */
	public List<KnowledgeTagsEntity> physicalSelectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeTagsDao/KnowledgeTagsDao_physical_select_all.sql");
		return executeQuery(sql, KnowledgeTagsEntity.class);
	}
	/**
	 * キーで1件取得(削除フラグを無視して取得) 
	 */
	public KnowledgeTagsEntity physicalSelectOnKey(Long knowledgeId, Integer tagId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeTagsDao/KnowledgeTagsDao_physical_select_on_key.sql");
		return executeQueryOnKey(sql, KnowledgeTagsEntity.class, knowledgeId, tagId);
	}
	/**
	 * 全て取得 
	 */
	public List<KnowledgeTagsEntity> selectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeTagsDao/KnowledgeTagsDao_select_all.sql");
		return executeQuery(sql, KnowledgeTagsEntity.class);
	}
	/**
	 * キーで1件取得 
	 */
	public KnowledgeTagsEntity selectOnKey(Long knowledgeId, Integer tagId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeTagsDao/KnowledgeTagsDao_select_on_key.sql");
		return executeQueryOnKey(sql, KnowledgeTagsEntity.class, knowledgeId, tagId);
	}
	/**
	 * KNOWLEDGE_ID でリストを取得
	 */
	public List<KnowledgeTagsEntity> selectOnKnowledgeId(Long knowledgeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeTagsDao/KnowledgeTagsDao_select_on_knowledge_id.sql");
		return executeQuery(sql, KnowledgeTagsEntity.class, knowledgeId);
	}
	/**
	 * TAG_ID でリストを取得
	 */
	public List<KnowledgeTagsEntity> selectOnTagId(Integer tagId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeTagsDao/KnowledgeTagsDao_select_on_tag_id.sql");
		return executeQuery(sql, KnowledgeTagsEntity.class, tagId);
	}
	/**
	 * KNOWLEDGE_ID でリストを取得
	 */
	public List<KnowledgeTagsEntity> physicalSelectOnKnowledgeId(Long knowledgeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeTagsDao/KnowledgeTagsDao_physical_select_on_knowledge_id.sql");
		return executeQuery(sql, KnowledgeTagsEntity.class, knowledgeId);
	}
	/**
	 * TAG_ID でリストを取得
	 */
	public List<KnowledgeTagsEntity> physicalSelectOnTagId(Integer tagId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeTagsDao/KnowledgeTagsDao_physical_select_on_tag_id.sql");
		return executeQuery(sql, KnowledgeTagsEntity.class, tagId);
	}
	/**
	 * 登録(データを生で操作) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public KnowledgeTagsEntity physicalInsert(KnowledgeTagsEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeTagsDao/KnowledgeTagsDao_insert.sql");
		executeUpdate(sql, 
			entity.getKnowledgeId()
			, entity.getTagId()
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
	public KnowledgeTagsEntity insert(Integer user, KnowledgeTagsEntity entity) {
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
	public KnowledgeTagsEntity insert(KnowledgeTagsEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return insert(userId, entity);
	}
	/**
	 * 更新(データを生で操作) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public KnowledgeTagsEntity physicalUpdate(KnowledgeTagsEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeTagsDao/KnowledgeTagsDao_update.sql");
		executeUpdate(sql, 
			entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
			, entity.getKnowledgeId()
			, entity.getTagId()
		);
		return entity;
	}
	/**
	 * 更新(更新ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public KnowledgeTagsEntity update(Integer user, KnowledgeTagsEntity entity) {
		KnowledgeTagsEntity db = selectOnKey(entity.getKnowledgeId(), entity.getTagId());
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
	public KnowledgeTagsEntity update(KnowledgeTagsEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return update(userId, entity);
	}
	/**
	 * 保存(ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public KnowledgeTagsEntity save(Integer user, KnowledgeTagsEntity entity) {
		KnowledgeTagsEntity db = selectOnKey(entity.getKnowledgeId(), entity.getTagId());
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
	public KnowledgeTagsEntity save(KnowledgeTagsEntity entity) {
		KnowledgeTagsEntity db = selectOnKey(entity.getKnowledgeId(), entity.getTagId());
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
	public void physicalDelete(Long knowledgeId, Integer tagId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeTagsDao/KnowledgeTagsDao_delete.sql");
		executeUpdate(sql, knowledgeId, tagId);
	}
	/**
	 * 削除(データを生で操作/物理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void physicalDelete(KnowledgeTagsEntity entity) {
		physicalDelete(entity.getKnowledgeId(), entity.getTagId());

	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, Long knowledgeId, Integer tagId) {
		KnowledgeTagsEntity db = selectOnKey(knowledgeId, tagId);
		db.setDeleteFlag(1);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Long knowledgeId, Integer tagId) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		delete(user, knowledgeId, tagId);
	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, KnowledgeTagsEntity entity) {
		delete(user, entity.getKnowledgeId(), entity.getTagId());

	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(KnowledgeTagsEntity entity) {
		delete(entity.getKnowledgeId(), entity.getTagId());

	}
	/**
	 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, Long knowledgeId, Integer tagId) {
		KnowledgeTagsEntity db = physicalSelectOnKey(knowledgeId, tagId);
		db.setDeleteFlag(0);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Long knowledgeId, Integer tagId) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		activation(user, knowledgeId, tagId);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, KnowledgeTagsEntity entity) {
		activation(user, entity.getKnowledgeId(), entity.getTagId());

	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(KnowledgeTagsEntity entity) {
		activation(entity.getKnowledgeId(), entity.getTagId());

	}

}
