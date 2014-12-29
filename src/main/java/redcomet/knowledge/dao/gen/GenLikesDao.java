package redcomet.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import redcomet.knowledge.entity.LikesEntity;
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
 * いいね
 */
@DI(instance=Instance.Singleton)
public class GenLikesDao extends AbstractDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenLikesDao get() {
		return Container.getComp(GenLikesDao.class);
	}

	/**
	 * 全て取得(削除フラグを無視して取得) 
	 */
	public List<LikesEntity> physicalSelectAll() { 
		String sql = SQLManager.getInstance().getSql("/redcomet/knowledge/dao/sql/LikesDao/LikesDao_physical_select_all.sql");
		return executeQuery(sql, LikesEntity.class);
	}
	/**
	 * キーで1件取得(削除フラグを無視して取得) 
	 */
	public LikesEntity physicalSelectOnKey(Long no) {
		String sql = SQLManager.getInstance().getSql("/redcomet/knowledge/dao/sql/LikesDao/LikesDao_physical_select_on_key.sql");
		return executeQueryOnKey(sql, LikesEntity.class, no);
	}
	/**
	 * 全て取得 
	 */
	public List<LikesEntity> selectAll() { 
		String sql = SQLManager.getInstance().getSql("/redcomet/knowledge/dao/sql/LikesDao/LikesDao_select_all.sql");
		return executeQuery(sql, LikesEntity.class);
	}
	/**
	 * キーで1件取得 
	 */
	public LikesEntity selectOnKey(Long no) {
		String sql = SQLManager.getInstance().getSql("/redcomet/knowledge/dao/sql/LikesDao/LikesDao_select_on_key.sql");
		return executeQueryOnKey(sql, LikesEntity.class, no);
	}
	/**
	 * 登録(データを生で操作) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public LikesEntity physicalInsert(LikesEntity entity) {
		String sql = SQLManager.getInstance().getSql("/redcomet/knowledge/dao/sql/LikesDao/LikesDao_insert.sql");
		Class<?> type = PropertyUtil.getPropertyType(entity, "no");
		Object key = executeInsert(sql, type, 
			entity.getNo()
			, entity.getKnowledgeId()
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
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public LikesEntity insert(Integer user, LikesEntity entity) {
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
	public LikesEntity insert(LikesEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return insert(userId, entity);
	}
	/**
	 * 更新(データを生で操作) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public LikesEntity physicalUpdate(LikesEntity entity) {
		String sql = SQLManager.getInstance().getSql("/redcomet/knowledge/dao/sql/LikesDao/LikesDao_update.sql");
		executeUpdate(sql, 
			entity.getKnowledgeId()
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
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public LikesEntity update(Integer user, LikesEntity entity) {
		LikesEntity db = selectOnKey(entity.getNo());
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
	public LikesEntity update(LikesEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return update(userId, entity);
	}
	/**
	 * 保存(ユーザを指定) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public LikesEntity save(Integer user, LikesEntity entity) {
		LikesEntity db = selectOnKey(entity.getNo());
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
	public LikesEntity save(LikesEntity entity) {
		LikesEntity db = selectOnKey(entity.getNo());
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
	public void physicalDelete(Long no) {
		String sql = SQLManager.getInstance().getSql("/redcomet/knowledge/dao/sql/LikesDao/LikesDao_delete.sql");
		executeUpdate(sql, no);
	}
	/**
	 * 削除(データを生で操作/物理削除) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public void physicalDelete(LikesEntity entity) {
		physicalDelete(entity.getNo());

	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public void delete(Integer user, Long no) {
		LikesEntity db = selectOnKey(no);
		db.setDeleteFlag(1);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public void delete(Long no) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		delete(user, no);
	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public void delete(Integer user, LikesEntity entity) {
		delete(user, entity.getNo());

	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public void delete(LikesEntity entity) {
		delete(entity.getNo());

	}
	/**
	 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public void activation(Integer user, Long no) {
		LikesEntity db = physicalSelectOnKey(no);
		db.setDeleteFlag(0);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public void activation(Long no) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		activation(user, no);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public void activation(Integer user, LikesEntity entity) {
		activation(user, entity.getNo());

	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public void activation(LikesEntity entity) {
		activation(entity.getNo());

	}

}
