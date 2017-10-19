package org.support.project.common.classanalysis;

import org.junit.Test;
import org.support.project.common.classanalysis.impl.ClassSearchImpl;

public class ClassSearchTest {

    @Test
    public void testClassSearchImpl() {
        System.out.println("*************************************************\n" + "testClassSearchImpl\n"
                + "*************************************************\n");
        ClassSearchImpl classSearch = new ClassSearchImpl();
        try {
            Class<?>[] classes = classSearch.classSearch("org.support.project.common.bat", false);
            for (Class<?> class1 : classes) {
                System.out.println(class1.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testClassSearchImpl2() {
        System.out.println("*************************************************\n" + "testClassSearchImpl2\n"
                + "*************************************************\n");
        ClassSearchImpl classSearch = new ClassSearchImpl();
        try {
            Class<?>[] classes = classSearch.classSearch("org.support.project.common.classanalysis", false);
            for (Class<?> class1 : classes) {
                System.out.println(class1.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testClassSearchJar() {
        System.out.println("*************************************************\n" + "testClassSearchJar\n"
                + "*************************************************\n");
        ClassSearchImpl classSearch = new ClassSearchImpl();
        try {
            Class<?>[] classes = classSearch.classSearch("net.arnx.jsonic.io", false);
            for (Class<?> class1 : classes) {
                System.out.println(class1.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
