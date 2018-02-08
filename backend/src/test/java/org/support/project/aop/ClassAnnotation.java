package org.support.project.aop;

@Aspect(advice = InterfaceAnnotationImpl.class)
public class ClassAnnotation {

    public String test() {
        return null;
    }

}
