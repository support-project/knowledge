package redcomet.knowledge.dao;

import redcomet.di.Container;
import redcomet.di.DI;
import redcomet.di.Instance;
import redcomet.knowledge.dao.gen.GenKnowledgeTagsDao;

/**
 * ナレッジが持つタグ
 */
@DI(instance=Instance.Singleton)
public class KnowledgeTagsDao extends GenKnowledgeTagsDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;
	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static KnowledgeTagsDao get() {
		return Container.getComp(KnowledgeTagsDao.class);
	}
	
	/**
	 * ナレッジが持つタグを削除
	 * @param knowledgeId
	 */
	public void deleteOnKnowledgeId(Long knowledgeId) {
		String sql = "DELETE FROM KNOWLEDGE_TAGS WHERE KNOWLEDGE_ID = ?";
		super.executeUpdate(sql, knowledgeId);
	}



}
