package org.support.project.ormapping.dao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.support.project.common.logic.H2DBServerLogic;
import org.support.project.common.util.FileUtil;
import org.support.project.ormapping.gen.dao.BlobTableDao;
import org.support.project.ormapping.gen.entity.BlobTableEntity;
/**
 * test for BlobTableDao
 * @author Koda
 */
public class BlobTableDaoTest {
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

    /*
     * @Test public void testGet() throws IOException { //テストの前に、番号１にバイナリを登録しておくこと BlobTableDao dao = BlobTableDao.get(); BlobTableEntity find =
     * dao.selectOnKey((long) 1); FileOutputStream out2 = new FileOutputStream(new File("/data/test.txt")); FileUtil.copy(find.getBlob(), out2); }
     */
    /**
     * test
     * @throws IOException
     */
    @Test
    public void testDao() throws IOException {
        BlobTableDao dao = BlobTableDao.get();

        BlobTableEntity entity = new BlobTableEntity();
        // entity.setContents("1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        entity.setBlob(this.getClass().getResourceAsStream("/ddl.sql"));

        // FileOutputStream file = new FileOutputStream(new File("/data/test.txt"));
        // FileUtil.copy(entity.getBlob(), file);

        entity = dao.insert(entity);
        // logger.debug(entity);
        Assert.assertNotNull(entity.getNo());

        BlobTableEntity find = dao.selectOnKey(entity.getNo());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        FileUtil.copy(find.getBlob(), out);

        ByteArrayOutputStream check = new ByteArrayOutputStream();
        FileUtil.copy(this.getClass().getResourceAsStream("/ddl.sql"), check);

        org.junit.Assert.assertEquals(out.toByteArray().length, check.toByteArray().length);
        org.junit.Assert.assertArrayEquals(out.toByteArray(), check.toByteArray());
    }
}
