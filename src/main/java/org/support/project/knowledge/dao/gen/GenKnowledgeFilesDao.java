package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.io.InputStream;
import java.sql.Timestamp;


import org.support.project.knowledge.entity.KnowledgeFilesEntity;
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
 * 添付ファイル
 */
@DI(instance=Instance.Singleton)
public class GenKnowledgeFilesDao extends AbstractDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenKnowledgeFilesDao get() {
		return Container.getComp(GenKnowledgeFilesDao.class);
	}

	/**
	 * 全て取得(削除フラグを無視して取得) 
	 */
	public List<KnowledgeFilesEntity> physicalSelectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeFilesDao/KnowledgeFilesDao_physical_select_all.sql");
		return executeQueryList(sql, KnowledgeFilesEntity.class);
	}
	/**
	 * キーで1件取得(削除フラグを無視して取得) 
	 */
	public KnowledgeFilesEntity physicalSelectOnKey(Long fileNo) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeFilesDao/KnowledgeFilesDao_physical_select_on_key.sql");
		return executeQuerySingle(sql, KnowledgeFilesEntity.class, fileNo);
	}
	/**
	 * 全て取得 
	 */
	public List<KnowledgeFilesEntity> selectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeFilesDao/KnowledgeFilesDao_select_all.sql");
		return executeQueryList(sql, KnowledgeFilesEntity.class);
	}
	/**
	 * キーで1件取得 
	 */
	public KnowledgeFilesEntity selectOnKey(Long fileNo) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeFilesDao/KnowledgeFilesDao_select_on_key.sql");
		return executeQuerySingle(sql, KnowledgeFilesEntity.class, fileNo);
	}
	/**
	 * 登録(データを生で操作/DBの採番機能のカラムも自分でセット) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public KnowledgeFilesEntity rawPhysicalInsert(KnowledgeFilesEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeFilesDao/KnowledgeFilesDao_raw_insert.sql");
		executeUpdate(sql, 
			entity.getFileNo()
			, entity.getKnowledgeId()
			, entity.getCommentNo()
			, entity.getFileName()
			, entity.getFileSize()
			, entity.getFileBinary()
			, entity.getParseStatus()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
		);
		String driverClass = ConnectionManager.getInstance().getDriverClass(getConnectionName());
		if (ORMappingParameter.DRIVER_NAME_POSTGRESQL.equals(driverClass)) {
			String setValSql = "select setval('KNOWLEDGE_FILES_FILE_NO_seq', (select max(FILE_NO) from KNOWLEDGE_FILES));";
			executeQuerySingle(setValSql, Long.class);
		}
		return entity;
	}
	/**
	 * 登録(データを生で操作) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public KnowledgeFilesEntity physicalInsert(KnowledgeFilesEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeFilesDao/KnowledgeFilesDao_insert.sql");
		Class<?> type = PropertyUtil.getPropertyType(entity, "fileNo");
		Object key = executeInsert(sql, type, 
			entity.getKnowledgeId()
			, entity.getCommentNo()
			, entity.getFileName()
			, entity.getFileSize()
			, entity.getFileBinary()
			, entity.getParseStatus()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
		);
		PropertyUtil.setPropertyValue(entity, "fileNo", key);
		return entity;
	}
	/**
	 * 登録(登録ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public KnowledgeFilesEntity insert(Integer user, KnowledgeFilesEntity entity) {
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
	public KnowledgeFilesEntity insert(KnowledgeFilesEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return insert(userId, entity);
	}
	/**
	 * 更新(データを生で操作) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public KnowledgeFilesEntity physicalUpdate(KnowledgeFilesEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeFilesDao/KnowledgeFilesDao_update.sql");
		executeUpdate(sql, 
			entity.getKnowledgeId()
			, entity.getCommentNo()
			, entity.getFileName()
			, entity.getFileSize()
			, entity.getFileBinary()
			, entity.getParseStatus()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
			, entity.getFileNo()
		);
		return entity;
	}
	/**
	 * 更新(更新ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public KnowledgeFilesEntity update(Integer user, KnowledgeFilesEntity entity) {
		KnowledgeFilesEntity db = selectOnKey(entity.getFileNo());
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
	public KnowledgeFilesEntity update(KnowledgeFilesEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return update(userId, entity);
	}
	/**
	 * 保存(ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public KnowledgeFilesEntity save(Integer user, KnowledgeFilesEntity entity) {
		KnowledgeFilesEntity db = selectOnKey(entity.getFileNo());
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
	public KnowledgeFilesEntity save(KnowledgeFilesEntity entity) {
		KnowledgeFilesEntity db = selectOnKey(entity.getFileNo());
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
	public void physicalDelete(Long fileNo) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgeFilesDao/KnowledgeFilesDao_delete.sql");
		executeUpdate(sql, fileNo);
	}
	/**
	 * 削除(データを生で操作/物理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void physicalDelete(KnowledgeFilesEntity entity) {
		physicalDelete(entity.getFileNo());

	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, Long fileNo) {
		KnowledgeFilesEntity db = selectOnKey(fileNo);
		db.setDeleteFlag(1);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Long fileNo) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		delete(user, fileNo);
	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, KnowledgeFilesEntity entity) {
		delete(user, entity.getFileNo());

	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(KnowledgeFilesEntity entity) {
		delete(entity.getFileNo());

	}
	/**
	 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, Long fileNo) {
		KnowledgeFilesEntity db = physicalSelectOnKey(fileNo);
		db.setDeleteFlag(0);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Long fileNo) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		activation(user, fileNo);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, KnowledgeFilesEntity entity) {
		activation(user, entity.getFileNo());

	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(KnowledgeFilesEntity entity) {
		activation(entity.getFileNo());

	}

}
