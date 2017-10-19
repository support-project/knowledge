package org.support.project.di;

import org.support.project.aop.Aspect;
import org.support.project.di.DI;
import org.support.project.di.Instance;

@DI(impl = TestInterfaceImpl.class, instance = Instance.Singleton)
public interface TestInterface {

    @Aspect(advice = TestAdvice.class)
    void test();

}
