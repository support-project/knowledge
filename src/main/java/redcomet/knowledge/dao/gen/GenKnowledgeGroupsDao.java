package redcomet.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import redcomet.knowledge.entity.KnowledgeGroupsEntity;
import redcomet.ormapping.dao.AbstractDao;
import redcomet.ormapping.exception.ORMappingException;
import redcomet.ormapping.common.SQLManager;
import redcomet.ormapping.common.DBUserPool;
import redcomet.ormapping.common.IDGen;
import redcomet.common.util.PropertyUtil;

import redcomet.di.Container;
import redcomet.di.DI;
import redcomet.di.Instance;
import redcomet.aop.Aspect;

/**
 * アクセス可能なグループ
 */
@DI(instance=Instance.Singleton)
public class GenKnowledgeGroupsDao extends AbstractDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenKnowledgeGroupsDao get() {
		return Container.getComp(GenKnowledgeGroupsDao.class);
	}

	/**
	 * 全て取得(削除フラグを無視して取得) 
	 */
	public List<KnowledgeGroupsEntity> physicalSelectAll() { 
		String sql = SQLManager.getInstance().getSql("/redcomet/knowledge/dao/sql/KnowledgeGroupsDao/KnowledgeGroupsDao_physical_select_all.sql");
		return executeQuery(sql, KnowledgeGroupsEntity.class);
	}
	/**
	 * キーで1件取得(削除フラグを無視して取得) 
	 */
	public KnowledgeGroupsEntity physicalSelectOnKey(Integer groupId, Long knowledgeId) {
		String sql = SQLManager.getInstance().getSql("/redcomet/knowledge/dao/sql/KnowledgeGroupsDao/KnowledgeGroupsDao_physical_select_on_key.sql");
		return executeQueryOnKey(sql, KnowledgeGroupsEntity.class, groupId, knowledgeId);
	}
	/**
	 * 全て取得 
	 */
	public List<KnowledgeGroupsEntity> selectAll() { 
		String sql = SQLManager.getInstance().getSql("/redcomet/knowledge/dao/sql/KnowledgeGroupsDao/KnowledgeGroupsDao_select_all.sql");
		return executeQuery(sql, KnowledgeGroupsEntity.class);
	}
	/**
	 * キーで1件取得 
	 */
	public KnowledgeGroupsEntity selectOnKey(Integer groupId, Long knowledgeId) {
		String sql = SQLManager.getInstance().getSql("/redcomet/knowledge/dao/sql/KnowledgeGroupsDao/KnowledgeGroupsDao_select_on_key.sql");
		return executeQueryOnKey(sql, KnowledgeGroupsEntity.class, groupId, knowledgeId);
	}
	/**
	 * GROUP_ID でリストを取得
	 */
	public List<KnowledgeGroupsEntity> selectOnGroupId(Integer groupId) {
		String sql = SQLManager.getInstance().getSql("/redcomet/knowledge/dao/sql/KnowledgeGroupsDao/KnowledgeGroupsDao_select_on_group_id.sql");
		return executeQuery(sql, KnowledgeGroupsEntity.class, groupId);
	}
	/**
	 * KNOWLEDGE_ID でリストを取得
	 */
	public List<KnowledgeGroupsEntity> selectOnKnowledgeId(Long knowledgeId) {
		String sql = SQLManager.getInstance().getSql("/redcomet/knowledge/dao/sql/KnowledgeGroupsDao/KnowledgeGroupsDao_select_on_knowledge_id.sql");
		return executeQuery(sql, KnowledgeGroupsEntity.class, knowledgeId);
	}
	/**
	 * GROUP_ID でリストを取得
	 */
	public List<KnowledgeGroupsEntity> physicalSelectOnGroupId(Integer groupId) {
		String sql = SQLManager.getInstance().getSql("/redcomet/knowledge/dao/sql/KnowledgeGroupsDao/KnowledgeGroupsDao_physical_select_on_group_id.sql");
		return executeQuery(sql, KnowledgeGroupsEntity.class, groupId);
	}
	/**
	 * KNOWLEDGE_ID でリストを取得
	 */
	public List<KnowledgeGroupsEntity> physicalSelectOnKnowledgeId(Long knowledgeId) {
		String sql = SQLManager.getInstance().getSql("/redcomet/knowledge/dao/sql/KnowledgeGroupsDao/KnowledgeGroupsDao_physical_select_on_knowledge_id.sql");
		return executeQuery(sql, KnowledgeGroupsEntity.class, knowledgeId);
	}
	/**
	 * 登録(データを生で操作) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public KnowledgeGroupsEntity physicalInsert(KnowledgeGroupsEntity entity) {
		String sql = SQLManager.getInstance().getSql("/redcomet/knowledge/dao/sql/KnowledgeGroupsDao/KnowledgeGroupsDao_insert.sql");
		executeUpdate(sql, 
			entity.getKnowledgeId()
			, entity.getGroupId()
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
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public KnowledgeGroupsEntity insert(Integer user, KnowledgeGroupsEntity entity) {
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
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public KnowledgeGroupsEntity insert(KnowledgeGroupsEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return insert(userId, entity);
	}
	/**
	 * 更新(データを生で操作) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public KnowledgeGroupsEntity physicalUpdate(KnowledgeGroupsEntity entity) {
		String sql = SQLManager.getInstance().getSql("/redcomet/knowledge/dao/sql/KnowledgeGroupsDao/KnowledgeGroupsDao_update.sql");
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
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public KnowledgeGroupsEntity update(Integer user, KnowledgeGroupsEntity entity) {
		KnowledgeGroupsEntity db = selectOnKey(entity.getGroupId(), entity.getKnowledgeId());
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
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public KnowledgeGroupsEntity update(KnowledgeGroupsEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return update(userId, entity);
	}
	/**
	 * 保存(ユーザを指定) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public KnowledgeGroupsEntity save(Integer user, KnowledgeGroupsEntity entity) {
		KnowledgeGroupsEntity db = selectOnKey(entity.getGroupId(), entity.getKnowledgeId());
		if (db == null) {
			return insert(user, entity);
		} else {
			return update(user, entity);
		}
	}
	/**
	 * 保存(存在しなければ登録、存在すれば更新) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public KnowledgeGroupsEntity save(KnowledgeGroupsEntity entity) {
		KnowledgeGroupsEntity db = selectOnKey(entity.getGroupId(), entity.getKnowledgeId());
		if (db == null) {
			return insert(entity);
		} else {
			return update(entity);
		}
	}
	/**
	 * 削除(データを生で操作/物理削除) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public void physicalDelete(Integer groupId, Long knowledgeId) {
		String sql = SQLManager.getInstance().getSql("/redcomet/knowledge/dao/sql/KnowledgeGroupsDao/KnowledgeGroupsDao_delete.sql");
		executeUpdate(sql, groupId, knowledgeId);
	}
	/**
	 * 削除(データを生で操作/物理削除) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public void physicalDelete(KnowledgeGroupsEntity entity) {
		physicalDelete(entity.getGroupId(), entity.getKnowledgeId());

	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public void delete(Integer user, Integer groupId, Long knowledgeId) {
		KnowledgeGroupsEntity db = selectOnKey(groupId, knowledgeId);
		db.setDeleteFlag(1);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public void delete(Integer groupId, Long knowledgeId) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		delete(user, groupId, knowledgeId);
	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public void delete(Integer user, KnowledgeGroupsEntity entity) {
		delete(user, entity.getGroupId(), entity.getKnowledgeId());

	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public void delete(KnowledgeGroupsEntity entity) {
		delete(entity.getGroupId(), entity.getKnowledgeId());

	}
	/**
	 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public void activation(Integer user, Integer groupId, Long knowledgeId) {
		KnowledgeGroupsEntity db = physicalSelectOnKey(groupId, knowledgeId);
		db.setDeleteFlag(0);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public void activation(Integer groupId, Long knowledgeId) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		activation(user, groupId, knowledgeId);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public void activation(Integer user, KnowledgeGroupsEntity entity) {
		activation(user, entity.getGroupId(), entity.getKnowledgeId());

	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public void activation(KnowledgeGroupsEntity entity) {
		activation(entity.getGroupId(), entity.getKnowledgeId());

	}

}
