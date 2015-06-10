package org.support.project.knowledge.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenKnowledgesDao;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.ormapping.common.SQLManager;

/**
 * ナレッジ
 */
@DI(instance=Instance.Singleton)
public class KnowledgesDao extends GenKnowledgesDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;
	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static KnowledgesDao get() {
		return Container.getComp(KnowledgesDao.class);
	}


	/**
	 * ID 
	 */
	private int currentId = 0;

	/**
	 * IDを採番 
	 * ※コミットしなくても次のIDを採番する為、保存しなければ欠番になる 
	 */
	public Integer getNextId() {
		String sql = "SELECT MAX(KNOWLEDGE_ID) FROM KNOWLEDGES;";
		Integer integer = executeQuerySingle(sql, Integer.class);
		if (integer != null) {
			if (currentId < integer) {
				currentId = integer;
			}
		}
		currentId++;
		return currentId;
	}

	
	
	public List<KnowledgesEntity> selectKnowledge(int offset, int limit, Integer userId) {
		// String sql = "SELECT * FROM KNOWLEDGES WHERE DELETE_FLAG = 0 ORDER BY UPDATE_DATETIME DESC Limit ? offset ?;";
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgesDao/KnowledgesDao_selectKnowledgeWithUserName.sql");
		return executeQueryList(sql, KnowledgesEntity.class, limit, offset);
	}


	/**
	 * ナレッジIDを複数渡して、その情報を取得
	 * @param knowledgeIds
	 * @return
	 */
	public List<KnowledgesEntity> selectKnowledges(List<Long> knowledgeIds) {
		if (knowledgeIds == null || knowledgeIds.isEmpty()) {
			return new ArrayList<KnowledgesEntity>();
		}
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append(" KNOWLEDGES.*");
        sql.append(" ,USERS.USER_NAME AS INSERT_USER_NAME");
        sql.append(" ,UP_USER.USER_NAME AS UPDATE_USER_NAME");
        sql.append(" FROM");
        sql.append(" KNOWLEDGES");
        sql.append(" LEFT OUTER JOIN USERS");
        sql.append(" ON USERS.USER_ID = KNOWLEDGES.INSERT_USER");
        sql.append(" LEFT OUTER JOIN USERS AS UP_USER");
        sql.append(" ON UP_USER.USER_ID = KNOWLEDGES.UPDATE_USER");
		sql.append(" WHERE KNOWLEDGE_ID IN (");
		int count = 0;
		for (Long integer : knowledgeIds) {
			if (count > 0) {
				sql.append(", ");
			}
			sql.append("?");
			count++;
		}
		sql.append(")");
		return executeQueryList(sql.toString(), KnowledgesEntity.class, knowledgeIds.toArray(new Long[0]));
	}


	/**
	 * ナレッジを取得
	 * 取得する際に、登録者名も取得
	 * @param knowledgeId
	 * @return
	 */
	public KnowledgesEntity selectOnKeyWithUserName(Long knowledgeId) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/KnowledgesDao/KnowledgesDao_selectOnKeyWithUserName.sql");
		return executeQuerySingle(sql, KnowledgesEntity.class, knowledgeId);
	}


	/**
	 * ユーザが登録したナレッジのIDを全て取得
	 * @param userId
	 * @return
	 */
	public List<Long> selectOnUser(Integer userId) {
		String sql = "SELECT KNOWLEDGE_ID FROM KNOWLEDGES WHERE INSERT_USER = ? ORDER BY KNOWLEDGE_ID DESC";
		return executeQueryList(sql, Long.class, userId);
	}


	/**
	 * ユーザが登録したナレッジを全て削除
	 * （論理削除）
	 * @param loginUserId
	 */
	public void deleteOnUser(Integer loginUserId) {
		String sql = "UPDATE KNOWLEDGES SET DELETE_FLAG = 1 , UPDATE_USER = ? , UPDATE_DATETIME = ? WHERE INSERT_USER = ?";
		super.executeUpdate(sql, loginUserId, new Timestamp(new Date().getTime()), loginUserId);
	}


	
	/**
	 * 登録されているナレッジを、番号指定で取得
	 * @param start
	 * @param end
	 * @return
	 */
	public List<KnowledgesEntity> selectBetween(Long start, Long end) {
		String sql = "SELECT * FROM knowledges WHERE knowledge_id BETWEEN ? AND ? AND DELETE_FLAG = 0 ORDER BY knowledge_id";
		return executeQueryList(sql, KnowledgesEntity.class, start, end);
	}


}
