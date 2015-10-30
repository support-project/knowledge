package org.support.project.knowledge.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.knowledge.dao.gen.GenStockKnowledgesDao;

/**
 * ストックしたナレッジ
 */
@DI(instance=Instance.Singleton)
public class StockKnowledgesDao extends GenStockKnowledgesDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;
	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static StockKnowledgesDao get() {
		return Container.getComp(StockKnowledgesDao.class);
	}



}
