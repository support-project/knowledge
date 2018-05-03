package org.support.project.aop;

import org.support.project.di.DI;

@Aspect(advice = InterfaceAnnotationImpl.class)
@DI(impl = InterfaceAnnotationImpl.class)
public interface InterfaceAnnotation {
    String test();
}
