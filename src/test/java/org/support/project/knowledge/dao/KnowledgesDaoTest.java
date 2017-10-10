package org.support.project.knowledge.dao;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.knowledge.TestCommon;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.LikeLogic;
import org.support.project.knowledge.vo.KnowledgeData;
import org.support.project.ormapping.common.DBUserPool;

public class KnowledgesDaoTest extends TestCommon {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(KnowledgesDaoTest.class);

    @Test
    public void testSelectKnowledges() throws Exception {
        java.util.List<Long> knowledgeIds = new ArrayList<Long>();
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                DBUserPool.get().setUser(loginedUser.getUserId());
            } else {
                DBUserPool.get().setUser(loginedUser.getUserId());
            }
            KnowledgesEntity entity = new KnowledgesEntity();
            entity.setTitle("Test-" + i);
            entity.setContent("テスト");
            KnowledgeLogic logic = KnowledgeLogic.get();
            
            KnowledgeData data = new KnowledgeData();
            data.setKnowledge(entity);

            entity = logic.insert(data, loginedUser);
            knowledgeIds.add(entity.getKnowledgeId());
        }
        KnowledgesDao dao = KnowledgesDao.get();
        List<KnowledgesEntity> knowledgesEntities = dao.selectKnowledges(knowledgeIds);
        for (KnowledgesEntity knowledgesEntity : knowledgesEntities) {
            LOG.info(knowledgesEntity);
            org.junit.Assert.assertNotNull(knowledgesEntity.getInsertUserName());
        }

    }

    @Test
    public void testSelectPopularity() throws Exception {
        // テストデータ登録
        List<KnowledgesEntity> knowledge = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            KnowledgesEntity entity = new KnowledgesEntity();
            entity.setTitle("Test-" + i);
            entity.setContent("テスト");
            entity.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PUBLIC);
            KnowledgeLogic logic = KnowledgeLogic.get();
            
            KnowledgeData data = new KnowledgeData();
            data.setKnowledge(entity);

            entity = logic.insert(data, loginedUser);
            knowledge.add(entity);

            if (i == 29) {
                LikeLogic.get().addLike(entity.getKnowledgeId(), loginedUser2, Locale.JAPAN);
            }
            entity = KnowledgesDao.get().selectOnKey(entity.getKnowledgeId());
            LOG.info("[" + i + "]" + entity.getPoint());
        }

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        Timestamp start = new Timestamp(c.getTimeInMillis());
        LOG.info(new SimpleDateFormat("YYYY-MM-dd hh:mm").format(start));

        c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        Timestamp end = new Timestamp(c.getTimeInMillis());
        LOG.info(new SimpleDateFormat("YYYY-MM-dd hh:mm").format(end));

        List<KnowledgesEntity> list = KnowledgesDao.get().selectPopularity(start, end, 0, 20);

        for (KnowledgesEntity knowledgesEntity : list) {
            LOG.info(knowledgesEntity.getTitle() + ":" + knowledgesEntity.getPointOnTerm() + " : " + knowledgesEntity.getPoint());
        }
        Assert.assertEquals("Test-29", list.get(0).getTitle());
    }

    @Test
    public void testSelectPopularityWithAccessControl() throws Exception {
        // テストデータ登録
        List<KnowledgesEntity> knowledge = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            KnowledgesEntity entity = new KnowledgesEntity();
            entity.setTitle("Test-" + i);
            entity.setContent("テスト");
            entity.setPublicFlag(KnowledgeLogic.PUBLIC_FLAG_PUBLIC);
            KnowledgeLogic logic = KnowledgeLogic.get();
            
            KnowledgeData data = new KnowledgeData();
            data.setKnowledge(entity);
            
            entity = logic.insert(data, loginedUser);
            knowledge.add(entity);
            if (i == 29) {
                LikeLogic.get().addLike(entity.getKnowledgeId(), loginedUser2, Locale.JAPAN);
            }
        }

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        Timestamp start = new Timestamp(c.getTimeInMillis());
        LOG.info(new SimpleDateFormat("YYYY-MM-dd hh:mm").format(start));

        c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        Timestamp end = new Timestamp(c.getTimeInMillis());
        LOG.info(new SimpleDateFormat("YYYY-MM-dd hh:mm").format(end));

        List<KnowledgesEntity> list = KnowledgesDao.get().selectPopularityWithAccessControl(loginedUser, start, end, 0, 20);

        for (KnowledgesEntity knowledgesEntity : list) {
            LOG.info(knowledgesEntity.getTitle() + ":" + knowledgesEntity.getPointOnTerm() + " : " + knowledgesEntity.getPoint());
        }
        Assert.assertEquals("Test-29", list.get(0).getTitle());
    }

}
