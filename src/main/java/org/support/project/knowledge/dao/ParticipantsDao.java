package org.support.project.knowledge.dao;

import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenParticipantsDao;
import org.support.project.knowledge.vo.Participation;
import org.support.project.ormapping.common.SQLManager;

/**
 * 参加者
 */
@DI(instance = Instance.Singleton)
public class ParticipantsDao extends GenParticipantsDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static ParticipantsDao get() {
        return Container.getComp(ParticipantsDao.class);
    }
    
    /**
     * 指定のイベントの参加者を取得
     * @param knowledgeId
     * @return
     */
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<Participation> selectParticipations(Long knowledgeId) {
        String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/ParticipantsDao/ParticipantsDao_selectParticipations.sql");
        return executeQueryList(sql, Participation.class, knowledgeId);
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public long selectUniqueUserCountOnKnowledgeId(Long knowledgeId) {
        String sql = "SELECT COUNT(*) FROM ("
                + "SELECT KNOWLEDGE_ID, USER_ID FROM PARTICIPANTS WHERE KNOWLEDGE_ID = ? GROUP BY KNOWLEDGE_ID, USER_ID) AS SUBQ";
        return super.executeQuerySingle(sql, Long.class, knowledgeId);
    }



}
