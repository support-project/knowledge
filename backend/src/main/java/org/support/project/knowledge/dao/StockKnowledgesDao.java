package org.support.project.knowledge.dao;

import java.util.Date;
import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.common.util.DateUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenStockKnowledgesDao;
import org.support.project.knowledge.entity.StockKnowledgesEntity;
import org.support.project.ormapping.common.DBUserPool;
import org.support.project.ormapping.common.SQLManager;

/**
 * ストックしたナレッジ
 */
@DI(instance = Instance.Singleton)
public class StockKnowledgesDao extends GenStockKnowledgesDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static StockKnowledgesDao get() {
        return Container.getComp(StockKnowledgesDao.class);
    }

    /**
     * ストックに登録されたナレッジを取得
     * 
     * @param stockId
     * @param offset
     * @param limit
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<StockKnowledgesEntity> selectOnStockIdWithKnowledgeInfo(Long stockId, int offset, int limit) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/knowledge/dao/sql/StockKnowledgesDao/StockKnowledgesDao_selectOnStockIdWithKnowledgeInfo.sql");
        return executeQueryList(sql, StockKnowledgesEntity.class, stockId, limit, offset);
    }
    /**
     * ストック内のナレッジを取得（有効なもののみ）
     * @param stockId
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<StockKnowledgesEntity> selectActiveOnStockId(Long stockId) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/knowledge/dao/sql/StockKnowledgesDao/StockKnowledgesDao_select_active_on_stock_id.sql");
        return executeQueryList(sql, StockKnowledgesEntity.class, stockId);
    }
    
    /**
     * 指定のナレッジが削除されたときに、ストックからも消す
     * @param knowledgeId
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void deleteOnKnowledgeId(Long knowledgeId) {
        DBUserPool pool = Container.getComp(DBUserPool.class);
        Integer user = (Integer) pool.getUser();
        Date now = DateUtils.now();
        String sql = "UPDATE stock_knowledges SET delete_flag = 1, update_user = ?, update_datetime = ? WHERE knowledge_id = ?";
        executeUpdate(sql, user, now, knowledgeId);
    }

}
