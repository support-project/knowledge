package redcomet.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import redcomet.knowledge.entity.CommentsEntity;
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
 * コメント
 */
@DI(instance=Instance.Singleton)
public class GenCommentsDao extends AbstractDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenCommentsDao get() {
		return Container.getComp(GenCommentsDao.class);
	}

	/**
	 * 全て取得(削除フラグを無視して取得) 
	 */
	public List<CommentsEntity> physicalSelectAll() { 
		String sql = SQLManager.getInstance().getSql("/redcomet/knowledge/dao/sql/CommentsDao/CommentsDao_physical_select_all.sql");
		return executeQuery(sql, CommentsEntity.class);
	}
	/**
	 * キーで1件取得(削除フラグを無視して取得) 
	 */
	public CommentsEntity physicalSelectOnKey(Long commentNo) {
		String sql = SQLManager.getInstance().getSql("/redcomet/knowledge/dao/sql/CommentsDao/CommentsDao_physical_select_on_key.sql");
		return executeQueryOnKey(sql, CommentsEntity.class, commentNo);
	}
	/**
	 * 全て取得 
	 */
	public List<CommentsEntity> selectAll() { 
		String sql = SQLManager.getInstance().getSql("/redcomet/knowledge/dao/sql/CommentsDao/CommentsDao_select_all.sql");
		return executeQuery(sql, CommentsEntity.class);
	}
	/**
	 * キーで1件取得 
	 */
	public CommentsEntity selectOnKey(Long commentNo) {
		String sql = SQLManager.getInstance().getSql("/redcomet/knowledge/dao/sql/CommentsDao/CommentsDao_select_on_key.sql");
		return executeQueryOnKey(sql, CommentsEntity.class, commentNo);
	}
	/**
	 * 登録(データを生で操作) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public CommentsEntity physicalInsert(CommentsEntity entity) {
		String sql = SQLManager.getInstance().getSql("/redcomet/knowledge/dao/sql/CommentsDao/CommentsDao_insert.sql");
		Class<?> type = PropertyUtil.getPropertyType(entity, "commentNo");
		Object key = executeInsert(sql, type, 
			entity.getCommentNo()
			, entity.getKnowledgeId()
			, entity.getComment()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
		);
		PropertyUtil.setPropertyValue(entity, "commentNo", key);
		return entity;
	}
	/**
	 * 登録(登録ユーザを指定) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public CommentsEntity insert(Integer user, CommentsEntity entity) {
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
	public CommentsEntity insert(CommentsEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return insert(userId, entity);
	}
	/**
	 * 更新(データを生で操作) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public CommentsEntity physicalUpdate(CommentsEntity entity) {
		String sql = SQLManager.getInstance().getSql("/redcomet/knowledge/dao/sql/CommentsDao/CommentsDao_update.sql");
		executeUpdate(sql, 
			entity.getKnowledgeId()
			, entity.getComment()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
			, entity.getCommentNo()
		);
		return entity;
	}
	/**
	 * 更新(更新ユーザを指定) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public CommentsEntity update(Integer user, CommentsEntity entity) {
		CommentsEntity db = selectOnKey(entity.getCommentNo());
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
	public CommentsEntity update(CommentsEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return update(userId, entity);
	}
	/**
	 * 保存(ユーザを指定) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public CommentsEntity save(Integer user, CommentsEntity entity) {
		CommentsEntity db = selectOnKey(entity.getCommentNo());
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
	public CommentsEntity save(CommentsEntity entity) {
		CommentsEntity db = selectOnKey(entity.getCommentNo());
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
	public void physicalDelete(Long commentNo) {
		String sql = SQLManager.getInstance().getSql("/redcomet/knowledge/dao/sql/CommentsDao/CommentsDao_delete.sql");
		executeUpdate(sql, commentNo);
	}
	/**
	 * 削除(データを生で操作/物理削除) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public void physicalDelete(CommentsEntity entity) {
		physicalDelete(entity.getCommentNo());

	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public void delete(Integer user, Long commentNo) {
		CommentsEntity db = selectOnKey(commentNo);
		db.setDeleteFlag(1);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public void delete(Long commentNo) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		delete(user, commentNo);
	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public void delete(Integer user, CommentsEntity entity) {
		delete(user, entity.getCommentNo());

	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public void delete(CommentsEntity entity) {
		delete(entity.getCommentNo());

	}
	/**
	 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public void activation(Integer user, Long commentNo) {
		CommentsEntity db = physicalSelectOnKey(commentNo);
		db.setDeleteFlag(0);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public void activation(Long commentNo) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		activation(user, commentNo);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public void activation(Integer user, CommentsEntity entity) {
		activation(user, entity.getCommentNo());

	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=redcomet.ormapping.transaction.Transaction.class)
	public void activation(CommentsEntity entity) {
		activation(entity.getCommentNo());

	}

}
