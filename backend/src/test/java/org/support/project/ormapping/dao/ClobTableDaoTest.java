package org.support.project.ormapping.dao;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.ormapping.gen.dao.ClobTableDao;
import org.support.project.ormapping.gen.entity.ClobTableEntity;

/**
 * test for ClobTableDao
 * @author Koda
 *
 */
public class ClobTableDaoTest {

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
        ClobTableDao dao = ClobTableDao.get();

        ClobTableEntity entity = new ClobTableEntity();
        // entity.setContents("1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        entity.setContents(StringUtils.randamGen(1024 * 1024));
        log.debug(entity);

        entity = dao.insert(entity);
        log.debug(entity);

        Assert.assertNotNull(entity.getNo());

        ClobTableEntity find = dao.selectOnKey(entity.getNo());
        org.junit.Assert.assertEquals(entity.getContents(), find.getContents());

    }
    /**
     * test
     */
    @Test
    public void testSearch() {
        ClobTableDao dao = ClobTableDao.get();

        ClobTableEntity entity = new ClobTableEntity();
        entity.setContents("1234567890ABCDEFGH hoge  IJKLMNOPQRSTUVWXYZ");
        log.debug(entity);
        entity = dao.insert(entity);
        log.debug(entity);
        Assert.assertNotNull(entity.getNo());

        List<ClobTableEntity> finds = dao.searchContent("%hoge%");
        log.debug(finds);
        org.junit.Assert.assertFalse(finds.isEmpty());

        // 少なくともH2 Databaseではtextのカラムを検索出来るようだ

    }

}
