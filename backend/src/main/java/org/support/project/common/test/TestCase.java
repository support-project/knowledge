package org.support.project.common.test;

import java.lang.invoke.MethodHandles;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.logic.H2DBServerLogic;

@RunWith(OrderedRunner.class)
public class TestCase {
    /** ログ */
    protected Log LOG = LogFactory.getLog(MethodHandles.lookup());
    
    public TestCase() {
        super();
//        LOG = LogFactory.getLog(this.getClass());
    }
    
    @Rule
    public TestWatcher watchman = new TestWatcher();
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        if (!H2DBServerLogic.get().isActive()) {
            H2DBServerLogic.get().start();
        }
    }
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }
    
    @Test
    public void test() {
        //何もしない
    }

}
