package org.support.project.knowledge.dao;

import java.util.ArrayList;
import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenPointKnowledgeHistoriesDao;

/**
 * ナレッジのポイント獲得履歴
 */
@DI(instance = Instance.Singleton)
public class PointKnowledgeHistoriesDao extends GenPointKnowledgeHistoriesDao {
    /** LOG */
    private static final Log LOG = LogFactory.getLog(PointKnowledgeHistoriesDao.class);

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static PointKnowledgeHistoriesDao get() {
        return Container.getComp(PointKnowledgeHistoriesDao.class);
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public long selectNumOnKnowledge(long knowledgeId) {
        String sql = "SELECT MAX(HISTORY_NO) FROM POINT_KNOWLEDGE_HISTORIES WHERE KNOWLEDGE_ID = ?";
        return executeQuerySingle(sql, Long.class, knowledgeId);
    }
    public int selectBeforePoint(Long knowledgeId, int... types) {
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<>();
        sql.append("SELECT SUM(POINT) FROM POINT_KNOWLEDGE_HISTORIES ");
        sql.append("WHERE KNOWLEDGE_ID = ? ");
        params.add(knowledgeId);
        sql.append("AND TYPE IN (");
        boolean appended = false;
        for (int i : types) {
            if (appended) {
                sql.append(", ");
                
            }
            sql.append("?");
            appended = true;
            params.add(i);
        }
        sql.append(");");
        Integer point =  executeQuerySingle(sql.toString(), Integer.class, params.toArray(new Object[0]));
        if (point == null) {
            point = 0;
        }
        return point;
    }
    
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void removeAll() {
        LOG.warn("Remove all point knowledge histories");
        String sql = "DELETE FROM POINT_KNOWLEDGE_HISTORIES";
        executeUpdate(sql);
    }



}
