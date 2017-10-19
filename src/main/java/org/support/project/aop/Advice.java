package org.support.project.aop;

import java.lang.reflect.Method;


/**
 * メソッドが呼ばれた時に呼ばれる
 * 
 */
public interface Advice<T> {
	Object invoke(T object, Method method, Object[] args) throws Throwable;
}
