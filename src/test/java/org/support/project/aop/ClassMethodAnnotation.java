package org.support.project.aop;

public class ClassMethodAnnotation {

    @Aspect(advice = InterfaceAnnotationImpl.class)
    public String test() {
        return null;
    }

}
