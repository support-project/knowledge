package redcomet.knowledge.dao;

import redcomet.di.Container;
import redcomet.di.DI;
import redcomet.di.Instance;

import redcomet.knowledge.dao.gen.GenVotesDao;

/**
 * 投票
 */
@DI(instance=Instance.Singleton)
public class VotesDao extends GenVotesDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;
	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static VotesDao get() {
		return Container.getComp(VotesDao.class);
	}



}
