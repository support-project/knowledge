package org.support.project.knowledge.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        LOG.trace("saveSurvey");
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

    public SurveysEntity loadSurvey(Long id) {
        LOG.trace("loadSurvey");
        SurveysEntity entity = SurveysDao.get().selectOnKey(id);
        if (entity == null) {
            return null;
        }
        List<SurveyItemsEntity> itemsEntities = SurveyItemsDao.get().selectOnKnowledgeId(id);
        entity.setItems(itemsEntities);
        Map<Integer, SurveyItemsEntity> itemMap = new HashMap<Integer, SurveyItemsEntity>();
        for (SurveyItemsEntity itemsEntity : itemsEntities) {
            itemsEntity.setChoices(new ArrayList<SurveyChoicesEntity>());
            itemMap.put(itemsEntity.getItemNo(), itemsEntity);
        }
        
        List<SurveyChoicesEntity> choicesEntities = SurveyChoicesDao.get().selectOnKnowledgeId(id);
        // 念のためソート
        Collections.sort(choicesEntities, new Comparator<SurveyChoicesEntity>() {
            @Override
            public int compare(SurveyChoicesEntity o1, SurveyChoicesEntity o2) {
                if (!o1.getKnowledgeId().equals(o2.getKnowledgeId())) {
                    return o1.getKnowledgeId().compareTo(o2.getKnowledgeId());
                }
                if (!o1.getItemNo().equals(o2.getItemNo())) {
                    return o1.getItemNo().compareTo(o2.getItemNo());
                }
                if (!o1.getChoiceNo().equals(o2.getChoiceNo())) {
                    return o1.getChoiceNo().compareTo(o2.getChoiceNo());
                }
                return 0;
            }
        });
        for (SurveyChoicesEntity itemChoicesEntity : choicesEntities) {
            if (itemMap.containsKey(itemChoicesEntity.getItemNo())) {
                SurveyItemsEntity templateItemsEntity = itemMap.get(itemChoicesEntity.getItemNo());
                templateItemsEntity.getChoices().add(itemChoicesEntity);
            }
        }
        entity.setEditable(true);
        return entity;
    }

}
