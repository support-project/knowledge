package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.KnowledgeItemValuesEntity;
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
 * ナレッジの項目値
 */
@DI(instance=Instance.Singleton)
public class GenKnowledgeItemValuesDao extends AbstractDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenKnowledgeItemValuesDao get() {
		return Container.getComp(GenKnowledgeItemValuesDao.class);
	}

	/**
	 * 全て取得(削除フラグを無視して取得) 
	 */
	public List<KnowledgeItemValuesEntity> physicalSelectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeItemValuesDao/KnowledgeItemValuesDao_physical_select_all.sql");
		return executeQueryList(sql, KnowledgeItemValuesEntity.class);
	}
	/**
	 * キーで1件取得(削除フラグを無視して取得) 
	 */
	public KnowledgeItemValuesEntity physicalSelectOnKey(Integer itemNo, Long knowledgeId, Integer typeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeItemValuesDao/KnowledgeItemValuesDao_physical_select_on_key.sql");
		return executeQuerySingle(sql, KnowledgeItemValuesEntity.class, itemNo, knowledgeId, typeId);
	}
	/**
	 * 全て取得 
	 */
	public List<KnowledgeItemValuesEntity> selectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeItemValuesDao/KnowledgeItemValuesDao_select_all.sql");
		return executeQueryList(sql, KnowledgeItemValuesEntity.class);
	}
	/**
	 * キーで1件取得 
	 */
	public KnowledgeItemValuesEntity selectOnKey(Integer itemNo, Long knowledgeId, Integer typeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeItemValuesDao/KnowledgeItemValuesDao_select_on_key.sql");
		return executeQuerySingle(sql, KnowledgeItemValuesEntity.class, itemNo, knowledgeId, typeId);
	}
	/**
	 * ITEM_NO でリストを取得
	 */
	public List<KnowledgeItemValuesEntity> selectOnItemNo(Integer itemNo) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeItemValuesDao/KnowledgeItemValuesDao_select_on_item_no.sql");
		return executeQueryList(sql, KnowledgeItemValuesEntity.class, itemNo);
	}
	/**
	 * KNOWLEDGE_ID でリストを取得
	 */
	public List<KnowledgeItemValuesEntity> selectOnKnowledgeId(Long knowledgeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeItemValuesDao/KnowledgeItemValuesDao_select_on_knowledge_id.sql");
		return executeQueryList(sql, KnowledgeItemValuesEntity.class, knowledgeId);
	}
	/**
	 * TYPE_ID でリストを取得
	 */
	public List<KnowledgeItemValuesEntity> selectOnTypeId(Integer typeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeItemValuesDao/KnowledgeItemValuesDao_select_on_type_id.sql");
		return executeQueryList(sql, KnowledgeItemValuesEntity.class, typeId);
	}
	/**
	 * ITEM_NO でリストを取得
	 */
	public List<KnowledgeItemValuesEntity> physicalSelectOnItemNo(Integer itemNo) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeItemValuesDao/KnowledgeItemValuesDao_physical_select_on_item_no.sql");
		return executeQueryList(sql, KnowledgeItemValuesEntity.class, itemNo);
	}
	/**
	 * KNOWLEDGE_ID でリストを取得
	 */
	public List<KnowledgeItemValuesEntity> physicalSelectOnKnowledgeId(Long knowledgeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeItemValuesDao/KnowledgeItemValuesDao_physical_select_on_knowledge_id.sql");
		return executeQueryList(sql, KnowledgeItemValuesEntity.class, knowledgeId);
	}
	/**
	 * TYPE_ID でリストを取得
	 */
	public List<KnowledgeItemValuesEntity> physicalSelectOnTypeId(Integer typeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeItemValuesDao/KnowledgeItemValuesDao_physical_select_on_type_id.sql");
		return executeQueryList(sql, KnowledgeItemValuesEntity.class, typeId);
	}
	/**
	 * 登録(データを生で操作/DBの採番機能のカラムも自分でセット) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public KnowledgeItemValuesEntity rawPhysicalInsert(KnowledgeItemValuesEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeItemValuesDao/KnowledgeItemValuesDao_raw_insert.sql");
		executeUpdate(sql, 
			entity.getItemNo()
			, entity.getKnowledgeId()
			, entity.getTypeId()
			, entity.getItemValue()
			, entity.getItemStatus()
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
	public KnowledgeItemValuesEntity physicalInsert(KnowledgeItemValuesEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeItemValuesDao/KnowledgeItemValuesDao_insert.sql");
		executeUpdate(sql, 
			entity.getItemNo()
			, entity.getKnowledgeId()
			, entity.getTypeId()
			, entity.getItemValue()
			, entity.getItemStatus()
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
	public KnowledgeItemValuesEntity insert(Integer user, KnowledgeItemValuesEntity entity) {
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
	public KnowledgeItemValuesEntity insert(KnowledgeItemValuesEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return insert(userId, entity);
	}
	/**
	 * 更新(データを生で操作) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public KnowledgeItemValuesEntity physicalUpdate(KnowledgeItemValuesEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeItemValuesDao/KnowledgeItemValuesDao_update.sql");
		executeUpdate(sql, 
			entity.getItemValue()
			, entity.getItemStatus()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
			, entity.getItemNo()
			, entity.getKnowledgeId()
			, entity.getTypeId()
		);
		return entity;
	}
	/**
	 * 更新(更新ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public KnowledgeItemValuesEntity update(Integer user, KnowledgeItemValuesEntity entity) {
		KnowledgeItemValuesEntity db = selectOnKey(entity.getItemNo(), entity.getKnowledgeId(), entity.getTypeId());
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
	public KnowledgeItemValuesEntity update(KnowledgeItemValuesEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return update(userId, entity);
	}
	/**
	 * 保存(ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public KnowledgeItemValuesEntity save(Integer user, KnowledgeItemValuesEntity entity) {
		KnowledgeItemValuesEntity db = selectOnKey(entity.getItemNo(), entity.getKnowledgeId(), entity.getTypeId());
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
	public KnowledgeItemValuesEntity save(KnowledgeItemValuesEntity entity) {
		KnowledgeItemValuesEntity db = selectOnKey(entity.getItemNo(), entity.getKnowledgeId(), entity.getTypeId());
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
	public void physicalDelete(Integer itemNo, Long knowledgeId, Integer typeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeItemValuesDao/KnowledgeItemValuesDao_delete.sql");
		executeUpdate(sql, itemNo, knowledgeId, typeId);
	}
	/**
	 * 削除(データを生で操作/物理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void physicalDelete(KnowledgeItemValuesEntity entity) {
		physicalDelete(entity.getItemNo(), entity.getKnowledgeId(), entity.getTypeId());

	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, Integer itemNo, Long knowledgeId, Integer typeId) {
		KnowledgeItemValuesEntity db = selectOnKey(itemNo, knowledgeId, typeId);
		db.setDeleteFlag(1);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer itemNo, Long knowledgeId, Integer typeId) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		delete(user, itemNo, knowledgeId, typeId);
	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, KnowledgeItemValuesEntity entity) {
		delete(user, entity.getItemNo(), entity.getKnowledgeId(), entity.getTypeId());

	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(KnowledgeItemValuesEntity entity) {
		delete(entity.getItemNo(), entity.getKnowledgeId(), entity.getTypeId());

	}
	/**
	 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, Integer itemNo, Long knowledgeId, Integer typeId) {
		KnowledgeItemValuesEntity db = physicalSelectOnKey(itemNo, knowledgeId, typeId);
		db.setDeleteFlag(0);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer itemNo, Long knowledgeId, Integer typeId) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		activation(user, itemNo, knowledgeId, typeId);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, KnowledgeItemValuesEntity entity) {
		activation(user, entity.getItemNo(), entity.getKnowledgeId(), entity.getTypeId());

	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(KnowledgeItemValuesEntity entity) {
		activation(entity.getItemNo(), entity.getKnowledgeId(), entity.getTypeId());

	}

}
