package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.TagsEntity;
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
 * タグ
 */
@DI(instance=Instance.Singleton)
public class GenTagsDao extends AbstractDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenTagsDao get() {
		return Container.getComp(GenTagsDao.class);
	}

	/**
	 * 全て取得(削除フラグを無視して取得) 
	 */
	public List<TagsEntity> physicalSelectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TagsDao/TagsDao_physical_select_all.sql");
		return executeQuery(sql, TagsEntity.class);
	}
	/**
	 * キーで1件取得(削除フラグを無視して取得) 
	 */
	public TagsEntity physicalSelectOnKey(Integer tagId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TagsDao/TagsDao_physical_select_on_key.sql");
		return executeQueryOnKey(sql, TagsEntity.class, tagId);
	}
	/**
	 * 全て取得 
	 */
	public List<TagsEntity> selectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TagsDao/TagsDao_select_all.sql");
		return executeQuery(sql, TagsEntity.class);
	}
	/**
	 * キーで1件取得 
	 */
	public TagsEntity selectOnKey(Integer tagId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TagsDao/TagsDao_select_on_key.sql");
		return executeQueryOnKey(sql, TagsEntity.class, tagId);
	}
	/**
	 * 登録(データを生で操作) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public TagsEntity physicalInsert(TagsEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TagsDao/TagsDao_insert.sql");
		Class<?> type = PropertyUtil.getPropertyType(entity, "tagId");
		Object key = executeInsert(sql, type, 
			entity.getTagId()
			, entity.getTagName()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
		);
		PropertyUtil.setPropertyValue(entity, "tagId", key);
		return entity;
	}
	/**
	 * 登録(登録ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public TagsEntity insert(Integer user, TagsEntity entity) {
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
	public TagsEntity insert(TagsEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return insert(userId, entity);
	}
	/**
	 * 更新(データを生で操作) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public TagsEntity physicalUpdate(TagsEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TagsDao/TagsDao_update.sql");
		executeUpdate(sql, 
			entity.getTagName()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
			, entity.getTagId()
		);
		return entity;
	}
	/**
	 * 更新(更新ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public TagsEntity update(Integer user, TagsEntity entity) {
		TagsEntity db = selectOnKey(entity.getTagId());
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
	public TagsEntity update(TagsEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return update(userId, entity);
	}
	/**
	 * 保存(ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public TagsEntity save(Integer user, TagsEntity entity) {
		TagsEntity db = selectOnKey(entity.getTagId());
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
	public TagsEntity save(TagsEntity entity) {
		TagsEntity db = selectOnKey(entity.getTagId());
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
	public void physicalDelete(Integer tagId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/TagsDao/TagsDao_delete.sql");
		executeUpdate(sql, tagId);
	}
	/**
	 * 削除(データを生で操作/物理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void physicalDelete(TagsEntity entity) {
		physicalDelete(entity.getTagId());

	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, Integer tagId) {
		TagsEntity db = selectOnKey(tagId);
		db.setDeleteFlag(1);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer tagId) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		delete(user, tagId);
	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, TagsEntity entity) {
		delete(user, entity.getTagId());

	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(TagsEntity entity) {
		delete(entity.getTagId());

	}
	/**
	 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, Integer tagId) {
		TagsEntity db = physicalSelectOnKey(tagId);
		db.setDeleteFlag(0);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer tagId) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		activation(user, tagId);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, TagsEntity entity) {
		activation(user, entity.getTagId());

	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(TagsEntity entity) {
		activation(entity.getTagId());

	}

}
