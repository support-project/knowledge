package org.support.project.knowledge.dao;

import java.util.List;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenCommentsDao;
import org.support.project.knowledge.entity.CommentsEntity;

/**
 * コメント
 */
@DI(instance=Instance.Singleton)
public class CommentsDao extends GenCommentsDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;
	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static CommentsDao get() {
		return Container.getComp(CommentsDao.class);
	}
	
	public List<CommentsEntity> selectOnKnowledgeId(Long knowledgeId) {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT *, USERS.USER_NAME AS UPDATE_USER_NAME FROM COMMENTS ");
		builder.append("LEFT OUTER JOIN USERS ON USERS.USER_ID = COMMENTS.UPDATE_USER ");
		builder.append("WHERE KNOWLEDGE_ID = ? ");
		return executeQueryList(builder.toString(), CommentsEntity.class, knowledgeId);
	}
	
	/**
	 * ナレッジのコメントの件数を取得
	 * @param knowledgeId
	 * @return
	 */
	public Integer countOnKnowledgeId(Long knowledgeId) {
		String sql = "SELECT COUNT(*) FROM COMMENTS WHERE KNOWLEDGE_ID = ?";
		return super.executeQuerySingle(sql, Integer.class, knowledgeId);
	}



}
