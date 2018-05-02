package org.support.project.common.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ListUtilsTest {

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
    public void testConvertList() throws InstantiationException, IllegalAccessException {
        List<TestClass1> list = new ArrayList<TestClass1>();
        for (int num = 0; num < 5; num++) {
            TestClass1 class1 = new TestClass1();
            class1.setNum(num);
            class1.setStr("hoge-" + num);
            list.add(class1);
        }

        List<TestClass2> list2 = ListUtils.convertList(list, TestClass2.class);

        assertEquals(list.size(), list2.size());
        // assertArrayEquals(list.toArray(new TestClass1[0]), list2.toArray(new TestClass2[0]));
        for (int i = 0; i < list.size(); i++) {
            TestClass1 class1 = list.get(i);
            TestClass2 class2 = list2.get(i);
            assertTrue(PropertyUtil.equalsProperty(class1, class2));
        }
    }

    @Test
    public void testToStringObjectArray() {
        List<TestClass1> list = new ArrayList<TestClass1>();
        for (int num = 0; num < 3; num++) {
            TestClass1 class1 = new TestClass1();
            class1.setNum(num);
            class1.setStr("hoge-" + num);
            list.add(class1);
        }

        String str = ListUtils.toString(list);
        assertEquals(
                "[0]{\"bool\":false,\"num\":0,\"str\":\"hoge-0\"}\n[1]{\"bool\":false,\"num\":1,\"str\":\"hoge-1\"}\n[2]{\"bool\":false,\"num\":2,\"str\":\"hoge-2\"}",
                str);
    }

    @Test
    public void testToStringListOfObject() {
        List<TestClass1> list = new ArrayList<TestClass1>();
        for (int num = 0; num < 3; num++) {
            TestClass1 class1 = new TestClass1();
            class1.setNum(num);
            class1.setStr("hoge-" + num);
            list.add(class1);
        }

        String str = ListUtils.toString(list.toArray(new TestClass1[0]));
        assertEquals(
                "[0]{\"bool\":false,\"num\":0,\"str\":\"hoge-0\"}\n[1]{\"bool\":false,\"num\":1,\"str\":\"hoge-1\"}\n[2]{\"bool\":false,\"num\":2,\"str\":\"hoge-2\"}",
                str);
    }

}
