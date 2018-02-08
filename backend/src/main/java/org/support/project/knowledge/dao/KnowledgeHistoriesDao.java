package org.support.project.knowledge.dao;

import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenKnowledgeHistoriesDao;
import org.support.project.knowledge.entity.KnowledgeHistoriesEntity;
import org.support.project.ormapping.common.SQLManager;

/**
 * ナレッジ更新履歴
 */
@DI(instance = Instance.Singleton)
public class KnowledgeHistoriesDao extends GenKnowledgeHistoriesDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static KnowledgeHistoriesDao get() {
        return Container.getComp(KnowledgeHistoriesDao.class);
    }

    /**
     * 指定ナレッジの最大の履歴番号を取得
     * 
     * @param knowledgeId
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int selectMaxHistoryNo(Long knowledgeId) {
        String sql = "SELECT MAX(HISTORY_NO) FROM KNOWLEDGE_HISTORIES WHERE KNOWLEDGE_ID = ? ";
        return executeQuerySingle(sql, Integer.class, knowledgeId);
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<KnowledgeHistoriesEntity> selectOnKnowledge(Long knowledgeId, int offset, int limit) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/knowledge/dao/sql/KnowledgeHistoriesDao/KnowledgeHistoriesDao_selectOnKnowledge.sql");
        return executeQueryList(sql, KnowledgeHistoriesEntity.class, knowledgeId, limit, offset);
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public KnowledgeHistoriesEntity selectOnKeyWithName(Integer historyNo, Long knowledgeId) {
        String sql = SQLManager.getInstance()
                .getSql("/org/support/project/knowledge/dao/sql/KnowledgeHistoriesDao/KnowledgeHistoriesDao_selectOnKeyWithName.sql");
        return executeQuerySingle(sql, KnowledgeHistoriesEntity.class, knowledgeId, historyNo);
    }

}
