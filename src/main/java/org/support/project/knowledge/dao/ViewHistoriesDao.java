package org.support.project.knowledge.dao;

import java.util.ArrayList;
import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenViewHistoriesDao;
import org.support.project.knowledge.entity.ViewHistoriesEntity;
import org.support.project.knowledge.vo.StockKnowledge;

/**
 * ナレッジの参照履歴
 */
@DI(instance = Instance.Singleton)
public class ViewHistoriesDao extends GenViewHistoriesDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static ViewHistoriesDao get() {
        return Container.getComp(ViewHistoriesDao.class);
    }
    
    /**
     * 指定のKnowledgeを参照したことがあるか検索して取得
     * @param stocks
     * @param userId
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<Long> selectViewdKnowledgeIds(List<StockKnowledge> stocks, Integer userId) {
        if (stocks == null || stocks.isEmpty()) {
            return new ArrayList<>();
        }
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT KNOWLEDGE_ID FROM VIEW_HISTORIES WHERE KNOWLEDGE_ID IN (");
        int cnt = 0;
        List<Object> params = new ArrayList<>();
        for (StockKnowledge knowledge : stocks) {
            if (cnt > 0) {
                sql.append(", ");
            }
            params.add(knowledge.getKnowledgeId());
            sql.append("?");
            cnt++;
        }
        sql.append(") AND INSERT_USER = ?");
        params.add(userId);
        return executeQueryList(sql.toString(), Long.class, params.toArray(new Object[0]));
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public long selectCountOnKnowledgeId(Long knowledgeId) {
        String sql = "SELECT COUNT(*) FROM VIEW_HISTORIES WHERE KNOWLEDGE_ID = ?";
        return executeQuerySingle(sql, Long.class, knowledgeId);
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public long selectUniqueUserCountOnKnowledgeId(Long knowledgeId) {
        String sql = "SELECT COUNT(*) FROM ("
                + "SELECT KNOWLEDGE_ID, INSERT_USER FROM VIEW_HISTORIES WHERE KNOWLEDGE_ID = ? GROUP BY KNOWLEDGE_ID, INSERT_USER) AS SUBQ";
        return executeQuerySingle(sql, Long.class, knowledgeId);
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<ViewHistoriesEntity> selectDistinctAllWidthPager(int limit, int offset) {
        String sql = "SELECT * FROM VIEW_HISTORIES AS M WHERE NOT EXISTS ("
                + "SELECT 1 FROM VIEW_HISTORIES AS S WHERE M.KNOWLEDGE_ID = S.KNOWLEDGE_ID "
                + "AND M.INSERT_USER = S.INSERT_USER "
                + "AND M.INSERT_DATETIME > S.INSERT_DATETIME"
                + ") AND M.INSERT_USER IS NOT NULL "
                + "ORDER BY M.KNOWLEDGE_ID ASC LIMIT ? OFFSET ?;";
        return executeQueryList(sql.toString(), ViewHistoriesEntity.class, limit, offset);
    }

}
