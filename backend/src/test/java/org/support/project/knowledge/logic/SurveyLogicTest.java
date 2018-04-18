package org.support.project.knowledge.logic;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.support.project.knowledge.TestCommon;
import org.support.project.knowledge.dao.SurveysDaoTest;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.SurveyChoicesEntity;
import org.support.project.knowledge.entity.SurveyItemsEntity;
import org.support.project.knowledge.entity.SurveysEntity;
import org.support.project.knowledge.vo.KnowledgeData;
import org.support.project.ormapping.common.DBUserPool;
import org.support.project.web.bean.AccessUser;

public class SurveyLogicTest extends TestCommon {

    @Test
    public void testSaveSurvey() {
        Long knowledgeId = new Long(1);
        SurveysEntity survey = new SurveysEntity(knowledgeId);
        survey.setTitle("テストアンケート");
        survey.setDescription("テスト");
        
        SurveyItemsEntity item = new SurveyItemsEntity();
        survey.getItems().add(item);
        item.setKnowledgeId(knowledgeId);
        item.setItemName("test");
        item.setItemType(SurveyLogic.ITEM_TYPE_TEXT);
        
        SurveyLogic.get().saveSurvey(survey, loginedUser2);
        
        SurveysEntity check = SurveyLogic.get().loadSurvey(knowledgeId, loginedUser2.getUserId());
        assertEquals(check.getKnowledgeId(), survey.getKnowledgeId());
        assertEquals(1, survey.getItems().size());
        assertEquals(0, survey.getItems().get(0).getChoices().size());
    }
    
    @Test
    public void testLoadSurvey() {
        Long knowledgeId = new Long(2);
        SurveysEntity survey = new SurveysEntity(knowledgeId);
        survey.setTitle("テストアンケート");
        survey.setDescription("テスト");
        
        SurveyItemsEntity item = new SurveyItemsEntity();
        survey.getItems().add(item);
        item.setKnowledgeId(knowledgeId);
        item.setItemName("item1");
        item.setItemType(SurveyLogic.ITEM_TYPE_RADIO);
        
        SurveyChoicesEntity choice = new SurveyChoicesEntity();
        item.getChoices().add(choice);
        choice.setKnowledgeId(knowledgeId);
        choice.setChoiceLabel("choice1");
        choice.setChoiceValue("1");
        
        SurveyLogic.get().saveSurvey(survey, loginedUser2);
        
        SurveysEntity check = SurveyLogic.get().loadSurvey(knowledgeId, loginedUser2.getUserId());
        assertEquals(check.getKnowledgeId(), survey.getKnowledgeId());
        assertEquals(1, survey.getItems().size());
        assertEquals(1, survey.getItems().get(0).getChoices().size());
    }
    
    
    @Test
    public void testListSurveys() throws Exception {
        AccessUser admin = SurveysDaoTest.loginedAdmin; //AdminUser
        AccessUser user = SurveysDaoTest.loginedUser2;
        AccessUser user2 = SurveysDaoTest.loginedUser3;
        KnowledgeLogic logic = KnowledgeLogic.get();
        
        DBUserPool.get().setUser(user.getUserId());
        
        // Knowledge登録（非公開）
        KnowledgesEntity entity = new KnowledgesEntity();
        entity.setTitle("Test1");
        entity.setContent("テストだよ");
        entity.setTypeId(-100);
        KnowledgeData data = new KnowledgeData();
        data.setKnowledge(entity);
        entity.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PRIVATE);
        entity = logic.insert(data, user, false);

        // Survey登録
        Long knowledgeId = entity.getKnowledgeId();
        SurveysEntity survey = new SurveysEntity(knowledgeId);
        survey.setTitle("テストアンケート");
        survey.setDescription("テスト");
        SurveyLogic.get().saveSurvey(survey, user);
        
        // Adminなら取得できる
        List<SurveysEntity> surveysEntities = SurveyLogic.get().listSurveys(admin, "", 0);
        assertEquals(1, surveysEntities.size());
        // Userも登録者なので取得できる（非公開）
        surveysEntities = SurveyLogic.get().listSurveys(user, "", 0);
        assertEquals(1, surveysEntities.size());
        // User2は取得できない
        surveysEntities = SurveyLogic.get().listSurveys(user2, "", 0);
        assertEquals(0, surveysEntities.size());
        
        // Knowledge登録(公開）
        entity = new KnowledgesEntity();
        entity.setTitle("Test2");
        entity.setContent("テストだよ");
        entity.setTypeId(-100);
        data = new KnowledgeData();
        data.setKnowledge(entity);
        entity.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PUBLIC);
        entity = logic.insert(data, admin, false);
        
        // Survey登録
        knowledgeId = entity.getKnowledgeId();
        survey = new SurveysEntity(knowledgeId);
        survey.setTitle("テストアンケート2");
        survey.setDescription("テスト2");
        SurveyLogic.get().saveSurvey(survey, user);

        // Admin
        surveysEntities = SurveyLogic.get().listSurveys(admin, "", 0);
        assertEquals(2, surveysEntities.size());
        // User
        surveysEntities = SurveyLogic.get().listSurveys(user, "", 0);
        assertEquals(2, surveysEntities.size());
        // User2
        surveysEntities = SurveyLogic.get().listSurveys(user2, "", 0);
        assertEquals(1, surveysEntities.size());
    }


    @Test
    public void testDeleteSurvey() {
        Long knowledgeId = new Long(3);
        SurveysEntity survey = new SurveysEntity(knowledgeId);
        survey.setTitle("テストアンケート");
        survey.setDescription("テスト");
        
        SurveyItemsEntity item = new SurveyItemsEntity();
        survey.getItems().add(item);
        item.setKnowledgeId(knowledgeId);
        item.setItemName("item1");
        item.setItemType(SurveyLogic.ITEM_TYPE_RADIO);
        
        SurveyChoicesEntity choice = new SurveyChoicesEntity();
        item.getChoices().add(choice);
        choice.setKnowledgeId(knowledgeId);
        choice.setChoiceLabel("choice1");
        choice.setChoiceValue("1");
        
        SurveyLogic.get().saveSurvey(survey, loginedUser2);
        
        SurveysEntity check = SurveyLogic.get().loadSurvey(knowledgeId, loginedUser2.getUserId());
        assertEquals(check.getKnowledgeId(), survey.getKnowledgeId());
        assertEquals(1, survey.getItems().size());
        assertEquals(1, survey.getItems().get(0).getChoices().size());
        
        SurveyLogic.get().deleteSurvey(knowledgeId);
        check = SurveyLogic.get().loadSurvey(knowledgeId, loginedUser2.getUserId());
        assertEquals(null, check);
    }

    
    /*
    @Test
    public void testSaveAnswer() {
        fail("Not yet implemented");
    }

    @Test
    public void testLoadAnswers() {
        fail("Not yet implemented");
    }
    
    */
}
