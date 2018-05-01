package org.support.project.aop;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.logic.H2DBServerLogic;
import org.support.project.di.Container;

public class IntercepterTest {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(IntercepterTest.class);

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        if (!H2DBServerLogic.get().isActive()) {
            H2DBServerLogic.get().start();
        }
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
    public void testInvokeInterface() {
        InterfaceAnnotation method = Container.getComp(InterfaceAnnotation.class);
        String result = method.test();
        LOG.info(result);
        assertEquals("RESULT", result);
    }
    @Test
    public void testInvokeInterfaceMethod() {
        InterfaceMethodAnnotation method = Container.getComp(InterfaceMethodAnnotation.class);
        String result = method.test();
        LOG.info(result);
        assertEquals("RESULT", result);
        
        result = method.test2();
        LOG.info(result);
        assertEquals("RESULT", result);
        
        result = method.test3();
        LOG.info(result);
        assertEquals("test3", result);
        
        try {
            method.test4();
            fail("Exceptionが発生するはず");
        } catch (Exception e) {
        }
        
    }

    
    
    @Test
    public void testInvokeClass() {
        ClassAnnotation method = Container.getComp(ClassAnnotation.class);
        String result = method.test();
        LOG.info(result);
        assertEquals("RESULT", result);
    }
    
    
    @Test
    public void testInvokeMethod() {
        ClassMethodAnnotation method = Container.getComp(ClassMethodAnnotation.class);
        String result = method.test();
        LOG.info(result);
        assertEquals("RESULT", result);
    }
    
    @Test
    public void testInvokeDao() {
        TestDao method = Container.getComp(TestDao.class);
        //トランザクションがかかる
        String result = method.test();
        LOG.info(result);
        assertEquals("test", result);
    }
    
    @Test
    public void testInvokeNormal() {
        NormalClass method = Container.getComp(NormalClass.class);
        String result = method.test();
        LOG.info(result);
        assertEquals("RESULT", result);
    }
    
    @Test
    public void testInvokeException() {
        NormalClass method = Container.getComp(NormalClass.class);
        try {
            method.testException();
            fail("Exceptionが発生するはず");
        } catch (Exception e) {
        }
    }
    
}
