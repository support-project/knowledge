package org.support.project.knowledge.dao;

import java.util.ArrayList;
import java.util.List;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenViewHistoriesDao;
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

}
