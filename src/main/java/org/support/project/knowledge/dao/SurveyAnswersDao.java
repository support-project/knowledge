package org.support.project.knowledge.dao;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.knowledge.dao.gen.GenSurveyAnswersDao;

/**
 * アンケートの回答
 */
@DI(instance = Instance.Singleton)
public class SurveyAnswersDao extends GenSurveyAnswersDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static SurveyAnswersDao get() {
        return Container.getComp(SurveyAnswersDao.class);
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void deleteOnKnowledgeId(Long knowledgeId) {
        String sql = "DELETE FROM SURVEY_ANSWERS WHERE KNOWLEDGE_ID = ?";
        executeUpdate(sql, knowledgeId);
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public int selectCountOnKnowledgeId(Long knowledgeId) {
        String sql = "SELECT COUNT(*) FROM SURVEY_ANSWERS WHERE KNOWLEDGE_ID = ?";
        return executeQuerySingle(sql, Integer.class, knowledgeId);
    }



}
