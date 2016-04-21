package org.support.project.knowledge.dao.gen;

import java.util.List;
import java.sql.Timestamp;
import org.support.project.knowledge.entity.PinsEntity;
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
 * ピン
 */
@DI(instance = Instance.Singleton)
public class GenPinsDao extends AbstractDao {
	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenPinsDao get() {
		return Container.getComp(GenPinsDao.class);
	}

	/**
	 * 全て取得(削除フラグを無視して取得) 
	 */
	public List<PinsEntity> physicalSelectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/PinsDao/PinsDao_physical_select_all.sql");
		return executeQueryList(sql, PinsEntity.class);
	}

	/**
	 * キーで1件取得(削除フラグを無視して取得) 
	 */
	public PinsEntity physicalSelectOnKey(Integer no) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/PinsDao/PinsDao_physical_select_on_key.sql");
		return executeQuerySingle(sql, PinsEntity.class, no);
	}

	/**
	 * 全て取得 
	 */
	public List<PinsEntity> selectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/PinsDao/PinsDao_select_all.sql");
		return executeQueryList(sql, PinsEntity.class);
	}

	/**
	 * キーで1件取得 
	 */
	public PinsEntity selectOnKey(Integer no) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/PinsDao/PinsDao_select_on_key.sql");
		return executeQuerySingle(sql, PinsEntity.class, no);
	}

	/**
	 * 登録(データを生で操作/DBの採番機能のカラムも自分でセット) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public PinsEntity rawPhysicalInsert(PinsEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/PinsDao/PinsDao_raw_insert.sql");
		executeUpdate(sql, 
			entity.getNo()
			, entity.getKnowledgeId()
			, entity.getRowId()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
		);
		String driverClass = ConnectionManager.getInstance().getDriverClass(getConnectionName());
		if (ORMappingParameter.DRIVER_NAME_POSTGRESQL.equals(driverClass)) {
			String setValSql = "select setval('pins_no_seq', (select max(no) from pins));";
			executeQuerySingle(setValSql, Long.class);
		}
		return entity;
	}

	/**
	 * 登録(データを生で操作) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public PinsEntity physicalInsert(PinsEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/PinsDao/PinsDao_insert.sql");
		Class<?> type = PropertyUtil.getPropertyType(entity, "no");
		Object key = executeInsert(sql, type, 
			entity.getKnowledgeId()
			, entity.getRowId()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
		);
		PropertyUtil.setPropertyValue(entity, "no", key);
		return entity;
	}

	/**
	 * 登録(登録ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public PinsEntity insert(Integer user, PinsEntity entity) {
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
	public PinsEntity insert(PinsEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return insert(userId, entity);
	}

	/**
	 * 更新(データを生で操作) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public PinsEntity physicalUpdate(PinsEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/PinsDao/PinsDao_update.sql");
		executeUpdate(sql, 
			entity.getKnowledgeId()
			, entity.getRowId()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
			, entity.getNo()
		);
		return entity;
	}

	/**
	 * 更新(更新ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public PinsEntity update(Integer user, PinsEntity entity) {
		PinsEntity db = selectOnKey(entity.getNo());
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
	public PinsEntity update(PinsEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return update(userId, entity);
	}

	/**
	 * 保存(ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public PinsEntity save(Integer user, PinsEntity entity) {
		PinsEntity db = selectOnKey(entity.getNo());
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
	public PinsEntity save(PinsEntity entity) {
		PinsEntity db = selectOnKey(entity.getNo());
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
	public void physicalDelete(Integer no) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/PinsDao/PinsDao_delete.sql");
		executeUpdate(sql, no);
	}

	/**
	 * 削除(データを生で操作/物理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void physicalDelete(PinsEntity entity) {
		physicalDelete(entity.getNo());
	}
	
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, Integer no) {
		PinsEntity db = selectOnKey(no);
		db.setDeleteFlag(1);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}

	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer no) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		delete(user, no);
	}

	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, PinsEntity entity) {
		delete(user, entity.getNo());
	}
	
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(PinsEntity entity) {
		delete(entity.getNo());
	}
	
	/**
	 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, Integer no) {
		PinsEntity db = physicalSelectOnKey(no);
		db.setDeleteFlag(0);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer no) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		activation(user, no);
	}
	
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, PinsEntity entity) {
		activation(user, entity.getNo());
	}
	
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(PinsEntity entity) {
		activation(entity.getNo());
	}
}