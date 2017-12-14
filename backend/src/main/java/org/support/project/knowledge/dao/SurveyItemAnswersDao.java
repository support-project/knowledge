package org.support.project.knowledge.dao;

import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import org.support.project.knowledge.dao.gen.GenSurveyItemAnswersDao;
import org.support.project.knowledge.entity.SurveyItemAnswersEntity;

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
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void deleteOnKnowledgeId(Long knowledgeId) {
        String sql = "DELETE FROM SURVEY_ITEM_ANSWERS WHERE KNOWLEDGE_ID = ?";
        executeUpdate(sql, knowledgeId);
    }
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public List<SurveyItemAnswersEntity> selectOnKnowledgeIdAndAnswerId(Long knowledgeId, Integer userId) {
        String sql = "SELECT * FROM SURVEY_ITEM_ANSWERS WHERE KNOWLEDGE_ID = ? AND ANSWER_ID = ?";
        return executeQueryList(sql, SurveyItemAnswersEntity.class, knowledgeId, userId);
    }



}
