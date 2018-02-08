package org.support.project.aop;

public class InterfaceMethodAnnotationImpl implements InterfaceMethodAnnotation {

    @Override
    public String test() {
        return null;
    }

    @Override
    @Aspect(advice = InterfaceAnnotationImpl.class)
    public String test2() {
        return null;
    }

    @Override
    public String test3() {
        return "test3";
    }

    @Override
    public String test4() throws Exception {
        throw new Exception();
    }

}
