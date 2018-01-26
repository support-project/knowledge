package org.support.project.common.log;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.support.project.common.config.AppConfig;
import org.support.project.common.log.impl.log4j.Log4jLogInitializerImpl;

public class Log4jLogTest {

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
    public void testLog4jLogImpl() {
        // 目視確認
        Log4jLogInitializerImpl initializer = new Log4jLogInitializerImpl();
        Log log = initializer.createLog(Log4jLogTest.class);
        log.trace("trace");
        log.debug("debug");
        log.info("info");
        log.warn("warn");
        log.error("error");
        log.fatal("fatal");

        /*
        AppConfig config = new AppConfig();
        config.setTime_zone("aaa");
        log.trace(config);
        log.debug(config);
        log.info(config);
        log.warn(config);
        log.error(config);
        log.fatal(config);

        log.trace(config, new Exception("hoge"));
        log.debug(config, new Exception("hoge"));
        log.info(config, new Exception("hoge"));
        log.warn(config, new Exception("hoge"));
        log.error(config, new Exception("hoge"));
        log.fatal(config, new Exception("hoge"));

        if (log.isTraceEnabled()) {
            log.trace(config);
        }
        if (log.isDebugEnabled()) {
            log.debug(config);
        }
        if (log.isInfoEnabled()) {
            log.info(config);
        }
        if (log.isWarnEnabled()) {
            log.warn(config);
        }
        if (log.isErrorEnabled()) {
            log.error(config);
        }
        if (log.isFatalEnabled()) {
            log.fatal(config);
        }
        */
    }

}
