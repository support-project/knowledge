package org.support.project.aop;

import org.support.project.di.DI;

@DI(impl = InterfaceMethodAnnotationImpl.class)
public interface InterfaceMethodAnnotation {

    @Aspect(advice = InterfaceAnnotationImpl.class)
    String test();

    String test2();

    String test3();

    String test4() throws Exception;

}
