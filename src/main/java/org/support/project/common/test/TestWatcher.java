package org.support.project.common.test;

import java.util.Date;

import org.junit.runner.Description;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.DateUtils;

/**
 * TestWatcher
 * @author Koda
 */
public class TestWatcher extends org.junit.rules.TestWatcher {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(TestWatcher.class);
    /** Date */
    private Date start;
    
    @Override
    protected void starting(Description description) {
        super.starting(description);
        start = DateUtils.now();
        LOG.info("@@@@@@ TEST START - " + description.getClassName() + "#" + description.getMethodName());
    }
    
    @Override
    protected void finished(Description description) {
        super.finished(description);
        Date end = DateUtils.now();
        long time = end.getTime() - start.getTime();
        LOG.info("@@@@@@ TEST FINISHED - "  + time + " [ms]" + "  -  " + description.getClassName() + "#" + description.getMethodName());
    }
    
    @Override
    protected void failed(Throwable e, Description description) {
        LOG.error(description, e);
    }

    @Override
    protected void succeeded(Description description) {
    }
}
