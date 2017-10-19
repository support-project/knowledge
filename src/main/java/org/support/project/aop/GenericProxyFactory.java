package org.support.project.aop;

import java.lang.reflect.Proxy;

/**
 * インスタンスのプロクシー
 */
public class GenericProxyFactory {
    /**
     * プロクシでラップしたオブジェクトを取得
     * 
     * @param intf
     *            type
     * @param obj
     *            object
     * @param <T>
     *            type
     * @return proxy object
     */
    public static <T> T getProxy(Class<? extends T> intf, final T obj) {
        return (T) Proxy.newProxyInstance(obj.getClass().getClassLoader(), new Class[] { intf }, new Intercepter(intf, obj));
    }
}
