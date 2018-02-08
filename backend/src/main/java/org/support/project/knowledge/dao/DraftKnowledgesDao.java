package org.support.project.knowledge.dao;

import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.knowledge.dao.gen.GenDraftKnowledgesDao;
import org.support.project.knowledge.entity.DraftKnowledgesEntity;

/**
 * ナレッジの下書き
 */
@DI(instance = Instance.Singleton)
public class DraftKnowledgesDao extends GenDraftKnowledgesDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static DraftKnowledgesDao get() {
        return Container.getComp(DraftKnowledgesDao.class);
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<DraftKnowledgesEntity> selectOnUser(Integer loginUserId, int limit, int offset) {
        String sql = "SELECT * FROM DRAFT_KNOWLEDGES WHERE INSERT_USER = ? limit ? offset ?";
        return super.executeQueryList(sql, DraftKnowledgesEntity.class, loginUserId, limit, offset);
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public DraftKnowledgesEntity selectOnKnowledgeAndUser(Long knowledgeId, Integer loginUserId) {
        String sql = "SELECT * FROM DRAFT_KNOWLEDGES WHERE INSERT_USER = ? AND KNOWLEDGE_ID = ?";
        return super.executeQuerySingle(sql, DraftKnowledgesEntity.class, loginUserId, knowledgeId);
    }


}
