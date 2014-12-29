package redcomet.knowledge.dao;

import redcomet.di.Container;
import redcomet.di.DI;
import redcomet.di.Instance;

import redcomet.knowledge.dao.gen.GenKnowledgeGroupsDao;

/**
 * アクセス可能なグループ
 */
@DI(instance=Instance.Singleton)
public class KnowledgeGroupsDao extends GenKnowledgeGroupsDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;
	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static KnowledgeGroupsDao get() {
		return Container.getComp(KnowledgeGroupsDao.class);
	}



}
