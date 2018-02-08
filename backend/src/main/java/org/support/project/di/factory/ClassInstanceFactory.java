package org.support.project.di.factory;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

import org.support.project.aop.Intercepter;
import org.support.project.common.classanalysis.ClassAnalysisForDI;
import org.support.project.common.classanalysis.ClassAnalysisForDIFactory;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.ormapping.dao.AbstractDao;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.Proxy;
import javassist.util.proxy.ProxyFactory;

public class ClassInstanceFactory {
    /** ログ */
    private static Log logger = LogFactory.getLog(MethodHandles.lookup());

    public static <T> T newInstance(String key, final Class<? extends T> type) throws InstantiationException, IllegalAccessException {
        T object;
        // クラスであった場合、javassistのライブラリのプロクシを利用する
        // object = (T) type.newInstance();

        ClassAnalysisForDI analysis = ClassAnalysisForDIFactory.getClassAnalysis(type);
        if (!analysis.isAspectAnnotationExists() && !AbstractDao.class.isAssignableFrom(type)) {
            // AOPが存在しない → プロクシでくるむ必要無し
            if (logger.isTraceEnabled()) {
                logger.trace("The instance of " + type.getName() + " was generated.");
            }
            return type.newInstance();
        }

        // プロクシ生成
        ProxyFactory factory = new ProxyFactory();
        factory.setSuperclass(type);
        factory.setFilter(new MethodFilter() {
            @Override
            public boolean isHandled(Method method) {
                // プロキシの捕捉の対象にしたくないメソッドはfalseを返すようにする
                // この例ではequalsメソッドを対象外としている
                return !(method != null && method.getName().equals("equals") && method.getReturnType() == boolean.class
                        && method.getParameterTypes().length == 1 && method.getParameterTypes()[0] == Object.class);
            }
        });
        // factory.setHandler(new MethodHandler() {
        // @Override
        // public Object invoke(Object self, Method method, Method proceed, Object[] args) throws Throwable {
        // // MethodHandlerの無名インナークラスで捕捉対象の前後に処理を挿入できる
        // System.out.println("*** before " + method.getName() + " ***");
        // Object ret = proceed.invoke(self, args);
        // System.out.println("*** after " + method.getName() + " ***");
        // return ret;
        // }
        // });

        // MethodHandler mi = new MethodHandler() {
        // public Object invoke(Object self, Method m, Method proceed, Object[] args) throws Throwable {
        // System.out.println("Name: " + m.getName());
        // return proceed.invoke(self, args); // execute the original method.
        // }
        // };
        // object = (T) factory.createClass().newInstance();

        Class<?> c = factory.createClass();
        object = (T) c.newInstance();

        MethodHandler methodHandler = new Intercepter(type, object);
        ((Proxy) object).setHandler(methodHandler);

        if (logger.isTraceEnabled()) {
            logger.trace("The instance of " + type.getName() + " was generated with proxy.");
        }

        return object;
    }

}
