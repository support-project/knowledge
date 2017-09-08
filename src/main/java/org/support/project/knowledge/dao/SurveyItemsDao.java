package org.support.project.knowledge.dao;

import org.support.project.aop.Aspect;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenSurveyItemsDao;

/**
 * アンケート項目
 */
@DI(instance = Instance.Singleton)
public class SurveyItemsDao extends GenSurveyItemsDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;
    /**
     * Get instance from DI container.
     * @return instance
     */
    public static SurveyItemsDao get() {
        return Container.getComp(SurveyItemsDao.class);
    }
    
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void deleteOnKnowledgeId(Long knowledgeId) {
        String sql = "DELETE FROM SURVEY_ITEMS WHERE KNOWLEDGE_ID = ?";
        executeUpdate(sql, knowledgeId);
    }



}
