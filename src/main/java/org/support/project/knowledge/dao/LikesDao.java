package org.support.project.knowledge.dao;

import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenLikesDao;
import org.support.project.knowledge.entity.LikesEntity;
import org.support.project.ormapping.common.SQLManager;

/**
 * いいね
 */
@DI(instance = Instance.Singleton)
public class LikesDao extends GenLikesDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static LikesDao get() {
        return Container.getComp(LikesDao.class);
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public Long countOnKnowledgeId(Long knowledgeId) {
        String sql = "SELECT COUNT(*) FROM LIKES WHERE KNOWLEDGE_ID = ?";
        return super.executeQuerySingle(sql, Long.class, knowledgeId);
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public long selectUniqueUserCountOnKnowledgeId(Long knowledgeId) {
        String sql = "SELECT COUNT(*) FROM ("
                + "SELECT KNOWLEDGE_ID, INSERT_USER FROM LIKES WHERE KNOWLEDGE_ID = ? GROUP BY KNOWLEDGE_ID, INSERT_USER) AS SUBQ";
        return super.executeQuerySingle(sql, Long.class, knowledgeId);
    }

    

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<LikesEntity> selectOnKnowledge(Long knowledgeId, int offset, int limit) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/LikesDao/LikesDao_selectOnKnowledge.sql");
        return executeQueryList(sql, LikesEntity.class, knowledgeId, limit, offset);
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public LikesEntity selectExistsOnUser(Long knowledgeId, Integer userId) {
        String sql = "SELECT * FROM LIKES WHERE KNOWLEDGE_ID = ? AND INSERT_USER = ? LIMIT 1 OFFSET 0";
        return super.executeQuerySingle(sql, LikesEntity.class, knowledgeId, userId);
    }


}
