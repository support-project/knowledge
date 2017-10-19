package org.support.project.common.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.support.project.common.test.Order;
import org.support.project.common.test.OrderedRunner;

@RunWith(OrderedRunner.class)
public class FileUtilTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    // @Test
    @Order(order = 5)
    public void testDelete() {
        File outdir = new File("/data/temp/test");
        FileUtil.delete(outdir);
        assertFalse(outdir.exists());
    }

    @Test
    @Order(order = 2)
    public void testGetExtension() {
        String extention = FileUtil.getExtension(new File("/data/temp/hoge.txt"));
        assertEquals(".txt", extention);
    }

    @Test
    @Order(order = 3)
    public void testGetFileName() {
        String fileName = FileUtil.getFileName(new File("/data/temp/hoge.txt"));
        assertEquals("hoge.txt", fileName);
    }

    // @Test
    @Order(order = 4)
    public void testCopy() throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            File outdir = new File("/data/temp/test");
            outdir.mkdirs();

            inputStream = getClass().getResourceAsStream("/appconfig.xml");
            outputStream = new FileOutputStream(new File(outdir, "test.xml"));
            FileUtil.copy(inputStream, outputStream);
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

    }

}
