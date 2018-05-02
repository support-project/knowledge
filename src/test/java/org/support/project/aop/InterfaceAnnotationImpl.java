package org.support.project.aop;

import java.lang.reflect.Method;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;

public class InterfaceAnnotationImpl implements Advice<Object>, InterfaceAnnotation {
	/** ログ */
	private static final Log LOG = LogFactory.getLog(InterfaceAnnotationImpl.class);

	
	@Override
	public Object invoke(Object object, Method method, Object[] args) throws Throwable {
		LOG.debug("invoke!!!");
		LOG.debug(object.getClass().getName() + "#" + method.getName() + "()");
		return "RESULT";
	}


	@Override
	public String test() {
		return null;
	}

}
