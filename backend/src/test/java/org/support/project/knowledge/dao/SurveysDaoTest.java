package org.support.project.knowledge.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.support.project.common.test.Order;
import org.support.project.knowledge.TestCommon;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.SurveysEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.vo.KnowledgeData;
import org.support.project.ormapping.common.DBUserPool;
import org.support.project.web.bean.AccessUser;

public class SurveysDaoTest extends TestCommon {

    @Test
    @Order(order = 1)
    public void testSelectWithKnowledgeTitle() throws Exception {
        AccessUser admin = SurveysDaoTest.loginedAdmin; //AdminUser
        
        DBUserPool.get().setUser(admin.getUserId());
        
        // Knowledge登録（非公開）
        KnowledgesEntity entity = new KnowledgesEntity();
        entity.setTitle("Test1");
        entity.setContent("テストだよ");
        entity.setTypeId(-100);
        KnowledgeLogic logic = KnowledgeLogic.get();
        KnowledgeData data = new KnowledgeData();
        data.setKnowledge(entity);
        entity.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PRIVATE);
        entity = logic.insert(data, admin, false);

        // Survey登録
        SurveysEntity survey = new SurveysEntity(entity.getKnowledgeId());
        survey.setTitle("テストアンケート");
        SurveysDao.get().save(survey);
        
        // 検索
        List<SurveysEntity> surveysEntities = SurveysDao.get().selectWithKnowledgeTitle("", 10, 0);
        
        // 結果確認（1件取得)
        assertEquals(1, surveysEntities.size());
    }
    
    @Test
    @Order(order = 2)
    public void testSelectWithKnowledgeTitleOnlyAccessAble() throws Exception {
        AccessUser admin = SurveysDaoTest.loginedAdmin; //AdminUser
        AccessUser user = SurveysDaoTest.loginedUser2;
        KnowledgeLogic logic = KnowledgeLogic.get();
        
        DBUserPool.get().setUser(admin.getUserId());

        // 検索
        List<SurveysEntity> surveysEntities = SurveysDao.get().selectWithKnowledgeTitleOnlyAccessAble(user, "", 10, 0);
        // 結果確認（Privateは取得されない/test1で登録したものが取得される)
        assertEquals(0, surveysEntities.size());
        
        // Knowledge登録(公開）
        KnowledgesEntity entity = new KnowledgesEntity();
        entity.setTitle("Test2");
        entity.setContent("テストだよ");
        entity.setTypeId(-100);
        KnowledgeData data = new KnowledgeData();
        data.setKnowledge(entity);
        entity.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PUBLIC);
        entity = logic.insert(data, admin, false);

        // Survey登録
        SurveysEntity survey = new SurveysEntity(entity.getKnowledgeId());
        survey.setTitle("テストアンケート");
        SurveysDao.get().save(survey);
        
        // 検索
        surveysEntities = SurveysDao.get().selectWithKnowledgeTitleOnlyAccessAble(user, "", 10, 0);
        // 結果確認（1件取得)
        assertEquals(1, surveysEntities.size());
    }
    
    
    

}
