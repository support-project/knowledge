package org.support.project.aop;

import org.support.project.di.DI;
import org.support.project.di.Instance;

@DI(instance = Instance.Singleton)
public class NormalClass {
	
	
    @Aspect(advice = InterfaceAnnotationImpl.class)
	public String test0() {
		return "RESULT";
	}
	
	public String test() {
		return "RESULT";
	}
	
	
	public String testException() throws Exception {
		throw new Exception("RESULT");
	}
	
	
}
