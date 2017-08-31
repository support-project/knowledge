package org.support.project.knowledge.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenPointKnowledgeHistoriesDao;

/**
 * ナレッジのポイント獲得履歴
 */
@DI(instance = Instance.Singleton)
public class PointKnowledgeHistoriesDao extends GenPointKnowledgeHistoriesDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static PointKnowledgeHistoriesDao get() {
        return Container.getComp(PointKnowledgeHistoriesDao.class);
    }
    public long selectNumOnKnowledge(long knowledgeId) {
        String sql = "SELECT MAX(HISTORY_NO) FROM POINT_KNOWLEDGE_HISTORIES WHERE KNOWLEDGE_ID = ?";
        return executeQuerySingle(sql, Long.class, knowledgeId);
    }



}
