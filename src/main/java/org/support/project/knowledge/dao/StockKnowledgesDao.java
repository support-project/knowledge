package org.support.project.knowledge.dao;

import java.util.List;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenStockKnowledgesDao;
import org.support.project.knowledge.entity.StockKnowledgesEntity;
import org.support.project.ormapping.common.SQLManager;

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
	
	
	/**
	 * ストックに登録されたナレッジを取得
	 * @param stockId
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<StockKnowledgesEntity> selectOnStockIdWithKnowledgeInfo(Long stockId, int offset, int limit) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/StockKnowledgesDao/StockKnowledgesDao_selectOnStockIdWithKnowledgeInfo.sql");
		return executeQueryList(sql, StockKnowledgesEntity.class, stockId, limit, offset);
	}



}
