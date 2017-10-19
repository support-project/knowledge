package org.support.project.di;

import java.lang.reflect.Method;

public class TestAdvice2 implements org.support.project.aop.Advice<TestObject> {

	@Override
	public Object invoke(TestObject object, Method method, Object[] args) throws Throwable {
		System.out.println("TestAdvice2 point cut");
		
		// 実際のコードを呼び出し
		Object ret = method.invoke(object, args);
		
		return ret;

	}


}
