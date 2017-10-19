package org.support.project.common.config;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AppConfigTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.setProperty("APP_HOME", "/data/temp");
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
    public void testGetTime_zone() {
        AppConfig config = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
        assertEquals("Asia/Tokyo", config.getTime_zone());
    }

    @Test
    public void testBasePath() {
        AppConfig appConfig = AppConfig.get();
//        assertEquals("/data/temp", appConfig.getBasePath());
//        assertEquals("/data/temp/logs/", appConfig.getLogsPath());
        System.out.println(appConfig.getBasePath());
    }
    
    
    @Test
    public void testSetTime_zone() {
        AppConfig config = new AppConfig();
        config.setTime_zone("America");
        assertEquals("America", config.getTime_zone());
    }

    @Test
    public void testGetConfigOnLocale() {
        String dir = "/org/support/project/common/config/";
        String file = "appconfig";
        AppConfig config = LocaleConfigLoader.load(dir, file, Locale.CANADA, AppConfig.class);
        assertEquals("CANADA", config.getTime_zone());

        config = LocaleConfigLoader.load(dir, file, Locale.CANADA, AppConfig.class);
        assertEquals("CANADA", config.getTime_zone());

        config = LocaleConfigLoader.load(dir, file, Locale.JAPAN, AppConfig.class);
        assertEquals("東京", config.getTime_zone());

        config = LocaleConfigLoader.load(dir, file, Locale.FRANCE, AppConfig.class);
        assertEquals("FRANCE", config.getTime_zone());

    }

}
