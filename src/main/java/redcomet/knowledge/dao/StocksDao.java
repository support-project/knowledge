package redcomet.knowledge.dao;

import redcomet.di.Container;
import redcomet.di.DI;
import redcomet.di.Instance;

import redcomet.knowledge.dao.gen.GenStocksDao;

/**
 * ストックしたナレッジ
 */
@DI(instance=Instance.Singleton)
public class StocksDao extends GenStocksDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;
	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static StocksDao get() {
		return Container.getComp(StocksDao.class);
	}



}
