package org.support.project.knowledge.dao.gen;

import java.util.List;

import java.sql.Timestamp;


import org.support.project.knowledge.entity.VotesEntity;
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
 * 投票
 */
@DI(instance=Instance.Singleton)
public class GenVotesDao extends AbstractDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static GenVotesDao get() {
		return Container.getComp(GenVotesDao.class);
	}

	/**
	 * 全て取得(削除フラグを無視して取得) 
	 */
	public List<VotesEntity> physicalSelectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/VotesDao/VotesDao_physical_select_all.sql");
		return executeQueryList(sql, VotesEntity.class);
	}
	/**
	 * キーで1件取得(削除フラグを無視して取得) 
	 */
	public VotesEntity physicalSelectOnKey(Long voteNo) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/VotesDao/VotesDao_physical_select_on_key.sql");
		return executeQuerySingle(sql, VotesEntity.class, voteNo);
	}
	/**
	 * 全て取得 
	 */
	public List<VotesEntity> selectAll() { 
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/VotesDao/VotesDao_select_all.sql");
		return executeQueryList(sql, VotesEntity.class);
	}
	/**
	 * キーで1件取得 
	 */
	public VotesEntity selectOnKey(Long voteNo) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/VotesDao/VotesDao_select_on_key.sql");
		return executeQuerySingle(sql, VotesEntity.class, voteNo);
	}
	/**
	 * 登録(データを生で操作/DBの採番機能のカラムも自分でセット) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public VotesEntity rawPhysicalInsert(VotesEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/VotesDao/VotesDao_raw_insert.sql");
		executeUpdate(sql, 
			entity.getVoteNo()
			, entity.getKnowledgeId()
			, entity.getVoteKind()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
		);
		String driverClass = ConnectionManager.getInstance().getDriverClass(getConnectionName());
		if (ORMappingParameter.DRIVER_NAME_POSTGRESQL.equals(driverClass)) {
			String setValSql = "select setval('VOTES_VOTE_NO_seq', (select max(VOTE_NO) from VOTES));";
			executeQuerySingle(setValSql, Long.class);
		}
		return entity;
	}
	/**
	 * 登録(データを生で操作) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public VotesEntity physicalInsert(VotesEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/VotesDao/VotesDao_insert.sql");
		Class<?> type = PropertyUtil.getPropertyType(entity, "voteNo");
		Object key = executeInsert(sql, type, 
			entity.getKnowledgeId()
			, entity.getVoteKind()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
		);
		PropertyUtil.setPropertyValue(entity, "voteNo", key);
		return entity;
	}
	/**
	 * 登録(登録ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public VotesEntity insert(Integer user, VotesEntity entity) {
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
	public VotesEntity insert(VotesEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return insert(userId, entity);
	}
	/**
	 * 更新(データを生で操作) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public VotesEntity physicalUpdate(VotesEntity entity) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/VotesDao/VotesDao_update.sql");
		executeUpdate(sql, 
			entity.getKnowledgeId()
			, entity.getVoteKind()
			, entity.getInsertUser()
			, entity.getInsertDatetime()
			, entity.getUpdateUser()
			, entity.getUpdateDatetime()
			, entity.getDeleteFlag()
			, entity.getVoteNo()
		);
		return entity;
	}
	/**
	 * 更新(更新ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public VotesEntity update(Integer user, VotesEntity entity) {
		VotesEntity db = selectOnKey(entity.getVoteNo());
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
	public VotesEntity update(VotesEntity entity) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer userId = (Integer) pool.getUser();
		return update(userId, entity);
	}
	/**
	 * 保存(ユーザを指定) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public VotesEntity save(Integer user, VotesEntity entity) {
		VotesEntity db = selectOnKey(entity.getVoteNo());
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
	public VotesEntity save(VotesEntity entity) {
		VotesEntity db = selectOnKey(entity.getVoteNo());
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
	public void physicalDelete(Long voteNo) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/VotesDao/VotesDao_delete.sql");
		executeUpdate(sql, voteNo);
	}
	/**
	 * 削除(データを生で操作/物理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void physicalDelete(VotesEntity entity) {
		physicalDelete(entity.getVoteNo());

	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, Long voteNo) {
		VotesEntity db = selectOnKey(voteNo);
		db.setDeleteFlag(1);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Long voteNo) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		delete(user, voteNo);
	}
	/**
	 * 削除(削除ユーザを指定／論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(Integer user, VotesEntity entity) {
		delete(user, entity.getVoteNo());

	}
	/**
	 * 削除(論理削除があれば論理削除) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void delete(VotesEntity entity) {
		delete(entity.getVoteNo());

	}
	/**
	 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, Long voteNo) {
		VotesEntity db = physicalSelectOnKey(voteNo);
		db.setDeleteFlag(0);
		db.setUpdateUser(user);
		db.setUpdateDatetime(new Timestamp(new java.util.Date().getTime()));
		physicalUpdate(db);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Long voteNo) {
		DBUserPool pool = Container.getComp(DBUserPool.class);
		Integer user = (Integer) pool.getUser();
		activation(user, voteNo);
	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(Integer user, VotesEntity entity) {
		activation(user, entity.getVoteNo());

	}
	/**
	 * 復元(論理削除されていたものを有効化) 
	 */
	@Aspect(advice=org.support.project.ormapping.transaction.Transaction.class)
	public void activation(VotesEntity entity) {
		activation(entity.getVoteNo());

	}

}
