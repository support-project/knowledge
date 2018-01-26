package org.support.project.ormapping.dao;

import java.lang.invoke.MethodHandles;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.logic.H2DBServerLogic;
import org.support.project.ormapping.gen.dao.AutoNoDao;
import org.support.project.ormapping.gen.entity.AutoNoEntity;


/**
 * Test for AutoNoDao.
 * @author Koda
 *
 */
public class AutoNoDaoTest {
    /** ログ */
    private static Log log = LogFactory.getLog(MethodHandles.lookup());
    /**
     * setUpBeforeClass
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        DatabaseInitialization.setUp();
    }
    /**
     * tearDownAfterClass
     * @throws Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        // H2DBServerLogic.get().stop();
    }
    
    /**
     * test
     */
    @Test
    public void testDao() {
        AutoNoDao dao = AutoNoDao.get();

        // 自動採番
        AutoNoEntity entity = new AutoNoEntity();
        entity.setStr("hoge");
        // log.debug(entity);

        entity = dao.insert(entity);
        log.debug(entity);
        Assert.assertNotNull(entity.getNo());

        Long max = entity.getNo();

        // 手入力
        entity = new AutoNoEntity();
        entity.setStr("hoge");
        entity.setNo(max + 1);

        entity = dao.rawPhysicalInsert(entity);
        log.debug(entity);
        Assert.assertEquals(Long.valueOf(max + 1), entity.getNo());

        // 再度自動採番

        entity = new AutoNoEntity();
        entity.setStr("hoge");

        entity = dao.insert(entity);
        log.debug(entity);
        Assert.assertEquals(Long.valueOf(max + 2), entity.getNo());

    }

}
