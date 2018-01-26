package org.support.project.di;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.lang.invoke.MethodHandles;

import org.junit.Test;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.exception.DIException;

public class TestDI {
    /** ログ */
    private static Log logger = LogFactory.getLog(MethodHandles.lookup());

    @Test
    public void test() {
        logger.info("test");
        TestObject testObject1 = Container.getComp(TestObject.class);
        System.out.println(testObject1.getClass().getName());

        logger.info("testObject1 : " + testObject1.hashCode());

        TestObject testObject2 = Container.getComp(TestObject.class);
        System.out.println(testObject2.getClass().getName());

        logger.info("testObject2 : " + testObject2.hashCode());

        testObject1.test();
    }

    @Test
    public void test2() {
        logger.info("test2");
        TestInterface interface1 = Container.getComp(TestInterface.class);
        System.out.println(interface1.getClass().getName());

        logger.info("interface1 : " + interface1.hashCode());

        TestInterface interface2 = Container.getComp(TestInterface.class);
        System.out.println(interface2.getClass().getName());

        logger.info("interface2 : " + interface2.hashCode());

        interface2.test();
    }

    @Test
    public void test3() {
        logger.info("test3");
        try {
            NoDiInterface interface1 = Container.getComp(NoDiInterface.class);
            fail("エラーになるはずがなってない");
        } catch (DIException e) {
            logger.info(e.getMessage());
        }

        try {
            WrongDiInterface interface1 = Container.getComp(WrongDiInterface.class);
            fail("エラーになるはずがなってない");
        } catch (DIException e) {
            logger.info(e.getMessage());
        }
    }

    @Test
    public void test4() {
        ManyImplInterface interface1 = Container.getComp("1", ManyImplInterface.class);
        System.out.println(interface1.getClass().getName());

        ManyImplInterfaceImpl1 impl1 = new ManyImplInterfaceImpl1();
        assertEquals(interface1.value(), impl1.value());

        ManyImplInterface interface2 = Container.getComp("2", ManyImplInterface.class);
        System.out.println(interface2.getClass().getName());

        ManyImplInterfaceImpl2 impl2 = new ManyImplInterfaceImpl2();
        assertEquals(interface2.value(), impl2.value());
    }

    @Test
    public void test5() {
        TestNoAspectObject object = Container.getComp(TestNoAspectObject.class);
        object.setHoge("aaaaa");
        System.out.println(object.getClass().getName());
    }

}
