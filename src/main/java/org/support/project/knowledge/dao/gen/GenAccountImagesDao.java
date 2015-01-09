package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.io.InputStream;
import java.sql.Timestamp;


import org.support.project.knowledge.entity.AccountImagesEntity;
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
 * アカウントの画像
 */
@DI(instance=Instance.Singleton)
public class GenAccountImagesDao extends AbstractDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenAccountImagesDao get() {
		return Container.getComp(GenAccountImagesDao.class);
	}

	/**
	 * 全て取得(削除フラグを無視して取得) 
	 */
	public List<AccountImagesEntity> physicalSelectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/AccountImagesDao/AccountImagesDao_physical_select_all.sql");
		return executeQuery(sql, AccountImagesEntity.class);
	}
	/**
	 * キーで1件取得(削除フラグを無視して取得) 
	 */
	public AccountImagesEntity physicalSelectOnKey(Long imageId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/AccountImagesDao/AccountImagesDao_physical_select_on_key.sql");
		return executeQueryOnKey(sql, AccountImagesEntity.class, imageId);
	}
	/**
	 * 全て取得 
	 */
	public List<AccountImagesEntity> selectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/AccountImagesDao/AccountImagesDao_select_all.sql");
		return executeQuery(sql, AccountImagesEntity.class);
	}
	/**
	 * キーで1件取得 
	 */
	public AccountImagesEntity selectOnKey(Long imageId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/AccountImagesDao/AccountImagesDao_select_on_key.sql");
		return executeQueryOnKey(sql, AccountImagesEntity.class, imageId);
	}
	/**
	 * 登録(データを生で操作) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public AccountImagesEntity physicalInsert(AccountImagesEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/AccountImagesDao/AccountImagesDao_insert.sql");
		Class<?> type = PropertyUtil.getPropertyType(entity, "imageId");
		Object key = executeInsert(sql, type, 
			entity.getImageId()
			, entity.getUserId()
			, entity.getFileName()
			, entity.getFileSize()
			, entity.getFileBinary()
			, entity.getExtension()
			, entity.getContentType()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
		);
		PropertyUtil.setPropertyValue(entity, "imageId", key);
		return entity;
	}
	/**
	 * 登録(登録ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public AccountImagesEntity insert(Integer user, AccountImagesEntity entity) {
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
	public AccountImagesEntity insert(AccountImagesEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return insert(userId, entity);
	}
	/**
	 * 更新(データを生で操作) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public AccountImagesEntity physicalUpdate(AccountImagesEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/AccountImagesDao/AccountImagesDao_update.sql");
		executeUpdate(sql, 
			entity.getUserId()
			, entity.getFileName()
			, entity.getFileSize()
			, entity.getFileBinary()
			, entity.getExtension()
			, entity.getContentType()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
			, entity.getImageId()
		);
		return entity;
	}
	/**
	 * 更新(更新ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public AccountImagesEntity update(Integer user, AccountImagesEntity entity) {
		AccountImagesEntity db = selectOnKey(entity.getImageId());
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
	public AccountImagesEntity update(AccountImagesEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return update(userId, entity);
	}
	/**
	 * 保存(ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public AccountImagesEntity save(Integer user, AccountImagesEntity entity) {
		AccountImagesEntity db = selectOnKey(entity.getImageId());
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
	public AccountImagesEntity save(AccountImagesEntity entity) {
		AccountImagesEntity db = selectOnKey(entity.getImageId());
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
	public void physicalDelete(Long imageId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/AccountImagesDao/AccountImagesDao_delete.sql");
		executeUpdate(sql, imageId);
	}
	/**
	 * 削除(データを生で操作/物理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void physicalDelete(AccountImagesEntity entity) {
		physicalDelete(entity.getImageId());

	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, Long imageId) {
		AccountImagesEntity db = selectOnKey(imageId);
		db.setDeleteFlag(1);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Long imageId) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		delete(user, imageId);
	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, AccountImagesEntity entity) {
		delete(user, entity.getImageId());

	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(AccountImagesEntity entity) {
		delete(entity.getImageId());

	}
	/**
	 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, Long imageId) {
		AccountImagesEntity db = physicalSelectOnKey(imageId);
		db.setDeleteFlag(0);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Long imageId) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		activation(user, imageId);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, AccountImagesEntity entity) {
		activation(user, entity.getImageId());

	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(AccountImagesEntity entity) {
		activation(entity.getImageId());

	}

}
