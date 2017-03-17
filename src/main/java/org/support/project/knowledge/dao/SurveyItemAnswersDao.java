package org.support.project.knowledge.dao;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.knowledge.dao.gen.GenSurveyItemAnswersDao;

/**
 * アンケートの回答
 */
@DI(instance = Instance.Singleton)
public class SurveyItemAnswersDao extends GenSurveyItemAnswersDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static SurveyItemAnswersDao get() {
        return Container.getComp(SurveyItemAnswersDao.class);
    }
    public void deleteOnKnowledgeId(Long knowledgeId) {
        String sql = "DELETE FROM SURVEY_ITEM_ANSWERS WHERE KNOWLEDGE_ID = ?";
        executeUpdate(sql, knowledgeId);
    }



}
