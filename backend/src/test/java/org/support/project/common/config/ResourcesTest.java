package org.support.project.common.config;

import static org.junit.Assert.*;

import java.lang.invoke.MethodHandles;
import java.util.Locale;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;

public class ResourcesTest {

    /** ログ */
    private static Log logger = LogFactory.getLog(MethodHandles.lookup());
    
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

    @Test
    public void testGetString() {
        Locale locale = Locale.JAPANESE;
        String str = Resources.getInstance(locale).getResource("errors.invalid", "1");
        logger.info(str);
        assertEquals("1が不正です。", str);
        
        str = Resources.getInstance(CommonBaseParameter.COMMON_RESOURCE, locale).getResource("errors.common.notimpl");
        logger.info(str);
        assertEquals("未実装です。", str);
        
        logger.info(Resources.getInstance().getResource("errors.invalid"));
        logger.info(Resources.getInstance().getResource("errors.common.notimpl"));
        
    }
    
    @Test
    public void testGetStringUS() {
        Locale locale = Locale.ENGLISH;
        String str = Resources.getInstance(locale).getResource("errors.invalid", "aaa");
        logger.info(str);
        assertEquals("aaa is invalid.", str);
        assertNotEquals("aaaが不正です。", str);
        
        //str = Resources.getInstance().getResource("errors.common.notimpl");
        //logger.info(str);
        //assertEquals("errors.common.notimpl", str); // 指定のキーにない場合、そのキーをそのまま取得
        
        str = Resources.getInstance(locale).getResource("errors.common.notimpl");
        logger.info(str);
        assertEquals("errors.common.notimpl", str); // 指定のキーにない場合、そのキーをそのまま取得
        
        str = Resources.getInstance(CommonBaseParameter.COMMON_RESOURCE, locale).getResource("errors.common.notimpl");
        logger.info(str);
        assertEquals("Not implement.", str);
        
        str = Resources.getInstance().getResource("errors.common.notimpl");
        logger.info(str);
        assertEquals("errors.common.notimpl", str); // 指定のキーにない場合、そのキーをそのまま取得
    }
    
}
