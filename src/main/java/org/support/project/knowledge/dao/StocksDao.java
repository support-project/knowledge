package org.support.project.knowledge.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenStocksDao;

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
