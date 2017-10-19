package org.support.project.di;

import org.support.project.aop.Aspect;
import org.support.project.di.DI;
import org.support.project.di.Instance;

@DI(instance = Instance.Singleton)
public class TestObject {

    @Aspect(advice = TestAdvice2.class)
    public void test() {
        System.out.println("test");
    }

}
