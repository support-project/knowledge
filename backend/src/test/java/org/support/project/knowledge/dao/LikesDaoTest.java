package org.support.project.knowledge.dao;

import java.lang.invoke.MethodHandles;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.logic.H2DBServerLogic;
import org.support.project.knowledge.TestCommon;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.entity.LikesEntity;

public class LikesDaoTest {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AppConfig.initEnvKey(TestCommon.KNOWLEDGE_TEST_HOME);
        if (!H2DBServerLogic.get().isActive()) {
            H2DBServerLogic.get().start();
        }
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
//        H2DBServerLogic.get().stop();
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testInsert() {
        LikesDao dao = LikesDao.get();

        // 自動採番
        LikesEntity entity = new LikesEntity();
        entity.setKnowledgeId(new Long(1));
        // log.debug(entity);

        entity = dao.insert(entity);
        LOG.debug(entity);
        Assert.assertNotNull(entity.getNo());

        Long max = entity.getNo();

        // 手入力
        entity = new LikesEntity();
        entity.setKnowledgeId(new Long(1));
        entity.setNo(max + 1);

        entity = dao.rawPhysicalInsert(entity);
        LOG.debug(entity);
        Assert.assertEquals(Long.valueOf(max + 1), entity.getNo());

        // 再度自動採番
        entity = new LikesEntity();
        entity.setKnowledgeId(new Long(1));

        entity = dao.insert(entity);
        LOG.debug(entity);
        Assert.assertEquals(Long.valueOf(max + 2), entity.getNo());
    }

}
