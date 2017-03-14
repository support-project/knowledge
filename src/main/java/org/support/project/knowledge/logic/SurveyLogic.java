package org.support.project.knowledge.logic;

import java.util.List;

import org.support.project.aop.Aspect;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.SurveyChoicesDao;
import org.support.project.knowledge.dao.SurveyItemsDao;
import org.support.project.knowledge.dao.SurveysDao;
import org.support.project.knowledge.entity.SurveyChoicesEntity;
import org.support.project.knowledge.entity.SurveyItemsEntity;
import org.support.project.knowledge.entity.SurveysEntity;
import org.support.project.web.bean.LoginedUser;

@DI(instance = Instance.Singleton)
public class SurveyLogic extends TemplateLogic {
    private static final Log LOG = LogFactory.getLog(SurveyLogic.class);

    public static SurveyLogic get() {
        return Container.getComp(SurveyLogic.class);
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public SurveysEntity saveSurvey(SurveysEntity survey, LoginedUser loginedUser) {
        SurveyItemsDao.get().deleteOnKnowledgeId(survey.getKnowledgeId());
        SurveyChoicesDao.get().deleteOnKnowledgeId(survey.getKnowledgeId());
        
        survey = SurveysDao.get().save(survey);
        List<SurveyItemsEntity> items = survey.getItems();
        int itemNo = 0;
        for (SurveyItemsEntity item : items) {
            item.setKnowledgeId(survey.getKnowledgeId());
            item.setItemNo(itemNo);
            SurveyItemsDao.get().save(item);
            
            int choiceNo = 0;
            List<SurveyChoicesEntity> choices = item.getChoices();
            for (SurveyChoicesEntity choice : choices) {
                choice.setKnowledgeId(survey.getKnowledgeId());
                choice.setItemNo(itemNo);
                choice.setChoiceNo(choiceNo);
                SurveyChoicesDao.get().save(choice);
                choiceNo++;
            }
            itemNo++;
        }
        
        
        
        return new SurveysEntity();
    }

}
