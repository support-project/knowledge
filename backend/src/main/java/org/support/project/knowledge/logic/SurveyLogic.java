package org.support.project.knowledge.logic;

import java.lang.invoke.MethodHandles;
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
import org.support.project.knowledge.dao.SurveyAnswersDao;
import org.support.project.knowledge.dao.SurveyChoicesDao;
import org.support.project.knowledge.dao.SurveyItemAnswersDao;
import org.support.project.knowledge.dao.SurveyItemsDao;
import org.support.project.knowledge.dao.SurveysDao;
import org.support.project.knowledge.entity.SurveyAnswersEntity;
import org.support.project.knowledge.entity.SurveyChoicesEntity;
import org.support.project.knowledge.entity.SurveyItemAnswersEntity;
import org.support.project.knowledge.entity.SurveyItemsEntity;
import org.support.project.knowledge.entity.SurveysEntity;
import org.support.project.knowledge.vo.SurveyReport;
import org.support.project.web.bean.AccessUser;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.UsersEntity;

@DI(instance = Instance.Singleton)
public class SurveyLogic extends TemplateLogic {
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    public static SurveyLogic get() {
        return Container.getComp(SurveyLogic.class);
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public SurveysEntity saveSurvey(SurveysEntity survey, AccessUser loginedUser) {
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
    
    /**
     * 登録されているアンケートの一覧を取得（ページ制御あり）
     * @param user ログインユーザ
     * @param idPrefix IDのプレフィックス（絞込条件） 
     * @param page ページ番号
     * @return 
     */
    public List<SurveysEntity> listSurveys(AccessUser user, String idPrefix, int page) {
        int limit = 10;
        int offset = page * limit;
        if (user == null) {
            // アンケート一覧はコピーする対象を選択する機能なので、Knowledge編集者のはずで、userがnullはありえない
            return new ArrayList<>();
        } else if (user.isAdmin()) {
            // アクセス権関係なく、全てのアンケート情報を取得
            return SurveysDao.get().selectWithKnowledgeTitle(idPrefix, limit, offset);
        } else {
            // アクセス可能なアンケートの情報を取得
            return SurveysDao.get().selectWithKnowledgeTitleOnlyAccessAble(user, idPrefix, limit, offset);
        }
    }

    
    /**
     * アンケート情報の取得
     * @param knowledgeId
     * @param userId
     * @return
     */
    public SurveysEntity loadSurvey(Long knowledgeId, Integer userId) {
        LOG.trace("loadSurvey");
        SurveysEntity entity = SurveysDao.get().selectOnKey(knowledgeId);
        if (entity == null) {
            return null;
        }
        List<SurveyItemsEntity> itemsEntities = SurveyItemsDao.get().selectOnKnowledgeId(knowledgeId);
        // 念のためソート
        Collections.sort(itemsEntities, new Comparator<SurveyItemsEntity>() {
            @Override
            public int compare(SurveyItemsEntity o1, SurveyItemsEntity o2) {
                if (!o1.getItemNo().equals(o2.getItemNo())) {
                    return o1.getItemNo().compareTo(o2.getItemNo());
                }
                return 0;
            }
        });
        entity.setItems(itemsEntities);
        Map<Integer, SurveyItemsEntity> itemMap = new HashMap<Integer, SurveyItemsEntity>();
        for (SurveyItemsEntity itemsEntity : itemsEntities) {
            itemsEntity.setChoices(new ArrayList<SurveyChoicesEntity>());
            itemMap.put(itemsEntity.getItemNo(), itemsEntity);
        }
        
        List<SurveyChoicesEntity> choicesEntities = SurveyChoicesDao.get().selectOnKnowledgeId(knowledgeId);
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
        
        if (userId > 0) {
            List<SurveyItemAnswersEntity> annswers = SurveyItemAnswersDao.get().selectOnKnowledgeIdAndAnswerId(knowledgeId, userId);
            for (SurveyItemAnswersEntity answer : annswers) {
                if (itemMap.containsKey(answer.getItemNo())) {
                    SurveyItemsEntity templateItemsEntity = itemMap.get(answer.getItemNo());
                    templateItemsEntity.setItemValue(answer.getItemValue());
                }
            }
        }
        entity.setEditable(true);
        return entity;
    }

    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void deleteSurvey(Long knowledgeId) {
        SurveysDao.get().physicalDelete(knowledgeId);
        SurveyItemsDao.get().deleteOnKnowledgeId(knowledgeId);
        SurveyChoicesDao.get().deleteOnKnowledgeId(knowledgeId);
        
        SurveyAnswersDao.get().deleteOnKnowledgeId(knowledgeId);
        SurveyItemAnswersDao.get().deleteOnKnowledgeId(knowledgeId);
    }
    
    
    @Aspect(advice = org.support.project.ormapping.transaction.Transaction.class)
    public void saveAnswer(SurveyAnswersEntity answer, Integer userId) {
        SurveyAnswersDao.get().save(answer);
        List<SurveyItemAnswersEntity> items = answer.getItems();
        for (SurveyItemAnswersEntity item : items) {
            item.setAnswerId(answer.getAnswerId());
            SurveyItemAnswersDao.get().save(item);
        }
    }

    public SurveyReport loadAnswers(Long knowledgeId, Integer userId) {
        SurveyReport report = new SurveyReport();
        List<SurveyAnswersEntity> answers = SurveyAnswersDao.get().selectOnKnowledgeId(knowledgeId);
        for (SurveyAnswersEntity answer : answers) {
            List<SurveyItemAnswersEntity> items = SurveyItemAnswersDao.get().selectOnKnowledgeIdAndAnswerId(knowledgeId, answer.getAnswerId());
            // 念のためソート
            Collections.sort(items, new Comparator<SurveyItemAnswersEntity>() {
                @Override
                public int compare(SurveyItemAnswersEntity o1, SurveyItemAnswersEntity o2) {
                    if (!o1.getItemNo().equals(o2.getItemNo())) {
                        return o1.getItemNo().compareTo(o2.getItemNo());
                    }
                    return 0;
                }
            });
            answer.setItems(items);
            
            UsersEntity user = UsersDao.get().physicalSelectOnKey(answer.getAnswerId());
            if (user != null) {
                answer.setUserName(user.getUserName());
            }
        }
        report.setAnswers(answers);
        
        SurveysEntity survey = this.loadSurvey(knowledgeId, userId);
        report.setSurvey(survey);
        return report;
    }

}
