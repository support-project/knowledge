package org.support.project.web.batch;

import java.lang.invoke.MethodHandles;

import org.apache.commons.lang.ClassUtils;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;

public class TestBatch extends AbstractBat {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    public static void main(String[] args) {
        initLogName("TestBatch.log");
        configInit(ClassUtils.getShortClassName(TestBatch.class));
        
        TestBatch bat = new TestBatch();
        bat.dbInit();
        bat.start();
        
        finishInfo();
    }

    private void start() {
        LOG.info("Hello World");
    }

}
