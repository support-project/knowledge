package org.support.project.knowledge.dao;

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
    public void deleteOnKnowledgeId(Long knowledgeId) {
        String sql = "DELETE FROM SURVEY_ANSWERS WHERE KNOWLEDGE_ID = ?";
        executeUpdate(sql, knowledgeId);
    }



}
