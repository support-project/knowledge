package org.support.project.common.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.support.project.common.classanalysis.ClassAnalysisTestTarget;
import org.support.project.common.util.PropertyUtil;

public class PropertyUtilTest {

    @Test
    public void testGetPropertyValue() {
        ClassAnalysisTestTarget target = new ClassAnalysisTestTarget();
        // target.string1 = "hoge";

        PropertyUtil.setPropertyValue(target, "string1", "hoge");

        // target.setNum1(9);

        PropertyUtil.setPropertyValue(target, "num1", 9);

        assertEquals(target.string1, PropertyUtil.getPropertyValue(target, "string1"));
        assertEquals(target.getNum1(), PropertyUtil.getPropertyValue(target, "num1"));

        List<String> strings = new ArrayList<>();
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String string = "test" + i;
            strings.add(string);
            integers.add(i);
        }
        // target.strings = strings;

        PropertyUtil.setPropertyValue(target, "strings", strings);

        target.setIntegers(integers);

        PropertyUtil.setPropertyValue(target, "integers", integers);

        List<String> strings2 = (List<String>) PropertyUtil.getPropertyValue(target, "strings");
        assertArrayEquals(strings.toArray(new String[0]), strings2.toArray(new String[0]));

        List<Integer> integers2 = (List<Integer>) PropertyUtil.getPropertyValue(target, "integers");
        assertArrayEquals(integers.toArray(new Integer[0]), integers2.toArray(new Integer[0]));

        ClassAnalysisTestTarget target2 = new ClassAnalysisTestTarget();
        target2.string1 = "aaaaa";

        // target.setTarget(target2);

        PropertyUtil.setPropertyValue(target, "target", target2);

        ClassAnalysisTestTarget target3 = (ClassAnalysisTestTarget) PropertyUtil.getPropertyValue(target, "target");
        assertEquals(target2, target3);

    }

    @Test
    public void testCopyPropertyValue() {
        ClassAnalysisTestTarget target = new ClassAnalysisTestTarget();
        PropertyUtil.setPropertyValue(target, "string1", "hoge");
        PropertyUtil.setPropertyValue(target, "num1", 9);
        List<String> strings = new ArrayList<>();
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String string = "test" + i;
            strings.add(string);
            integers.add(i);
        }
        PropertyUtil.setPropertyValue(target, "strings", strings);
        PropertyUtil.setPropertyValue(target, "integers", integers);
        ClassAnalysisTestTarget target2 = new ClassAnalysisTestTarget();
        target2.string1 = "aaaaa";
        PropertyUtil.setPropertyValue(target, "target", target2);

        ClassAnalysisTestTarget copy = new ClassAnalysisTestTarget();

        PropertyUtil.copyPropertyValue(target, copy);

        assertEquals(target.string1, PropertyUtil.getPropertyValue(copy, "string1"));
        assertEquals(target.getNum1(), PropertyUtil.getPropertyValue(copy, "num1"));
        List<String> strings2 = (List<String>) PropertyUtil.getPropertyValue(copy, "strings");
        assertArrayEquals(strings.toArray(new String[0]), strings2.toArray(new String[0]));
        List<Integer> integers2 = (List<Integer>) PropertyUtil.getPropertyValue(copy, "integers");
        assertArrayEquals(integers.toArray(new Integer[0]), integers2.toArray(new Integer[0]));
        ClassAnalysisTestTarget target3 = (ClassAnalysisTestTarget) PropertyUtil.getPropertyValue(copy, "target");
        assertEquals(target2, target3);

        assertEquals(true, target.hashCode() != copy.hashCode());

    }

    @Test
    public void testGetPropertyType() {
        ClassAnalysisTestTarget target = new ClassAnalysisTestTarget();

        assertEquals(String.class, PropertyUtil.getPropertyType(target, "string1"));
        assertEquals(int.class, PropertyUtil.getPropertyType(target, "num1"));
        // assertEquals(Integer.class, PropertyUtil.getPropertyType(target, "num1"));

        assertEquals(List.class, PropertyUtil.getPropertyType(target, "strings"));

        assertEquals(ClassAnalysisTestTarget.class, PropertyUtil.getPropertyType(target, "target"));

        assertTrue(PropertyUtil.getPropertyType(target, "strings2").isArray());
        assertTrue(PropertyUtil.getPropertyType(target, "is").isArray());

    }

    @Test
    public void testGetValues() {
        TestClass1 class1 = new TestClass1();
        class1.setNum(1);
        class1.setStr("aaa");

        Map<String, Object> values = PropertyUtil.getValues(class1);

        assertEquals(1, values.get("num"));
        assertEquals("aaa", values.get("str"));
    }

    @Test
    public void testEqualsProperty() {
        TestClass1 class1 = new TestClass1();
        class1.setNum(1);
        class1.setStr("aaa");
        TestClass2 class2 = new TestClass2();
        class2.setNum(1);
        class2.setStr("aaa");

        assertTrue(PropertyUtil.equalsProperty(class1, class2));

        class1.setNum(2);
        assertFalse(PropertyUtil.equalsProperty(class1, class2));

    }

    @Test
    public void testToString() {
        TestClass1 class1 = new TestClass1();
        class1.setNum(1);
        class1.setStr("aaa");
        String str = PropertyUtil.reflectionToString(class1);
        assertEquals("{\"bool\":false,\"num\":1,\"str\":\"aaa\"}", str);

        str = PropertyUtil.reflectionToString(null);
        assertEquals("null", str);

        str = PropertyUtil.reflectionToString(1);
        assertEquals("1", str);
    }

    @Test
    public void testError() {
        TestClass1 class1 = new TestClass1();
        try {
            PropertyUtil.setPropertyValue(class1, "aaaaa", 123);
            fail("Errorになるはず");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            PropertyUtil.setPropertyValue(class1, "num", "123");
            fail("Errorになるはず");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
