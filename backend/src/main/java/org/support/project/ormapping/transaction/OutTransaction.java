package org.support.project.ormapping.transaction;

import java.lang.reflect.Method;

import org.support.project.aop.Advice;

/**
 * AbstractDaoを継承したメソッドは、デフォルトでTracsactionを継承する。
 * どうしても実行中のTransaction以外のTransactionで実行したい場合に、
 * このアノテーションを設定する
 * 
 * @author Koda
 *
 */
public class OutTransaction implements Advice<Object> {
    @Override
    public Object invoke(Object object, Method method, Object[] args) throws Throwable {
        Object r = method.invoke(object, args);
        return r;
    }
}
