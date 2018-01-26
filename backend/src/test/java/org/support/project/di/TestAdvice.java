package org.support.project.di;

import java.lang.reflect.Method;

import org.support.project.aop.Advice;

public class TestAdvice implements Advice<TestInterface> {

	@Override
	public Object invoke(TestInterface object, Method method, Object[] args) throws Throwable {
		System.out.println("TestAdvice point cut");
		
		// 実際のコードを呼び出し
		Object ret = method.invoke(object, args);
		
		return ret;
	}

}
