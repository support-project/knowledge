package org.support.project.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.ormapping.dao.AbstractDao;
import org.support.project.ormapping.transaction.Transaction;

import javassist.util.proxy.MethodHandler;

public class Intercepter implements InvocationHandler, MethodHandler {
    /** ログ */
    private static Log logger = LogFactory.getLog(Intercepter.class);

    private Object target;
    private Class<?> intf;

    public Intercepter(Class<?> intf, final Object target) {
        this.target = target;
        this.intf = intf;
    }

    /**
     * インタフェースの実行
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            Aspect aspect = intf.getAnnotation(Aspect.class);
            if (aspect != null) {
                // クラスにAspectが登録されていればそれを実行(そしてリターン)
                Class<? extends Advice> adviceClass = aspect.advice();
                Advice advice = adviceClass.newInstance();
                Object ret = advice.invoke(target, method, args);
                return ret;
            }

            aspect = method.getAnnotation(Aspect.class);
            if (aspect != null) {
                // メソッドにAspectが登録されていればそれを実行(そしてリターン)
                Class<? extends Advice> adviceClass = aspect.advice();
                Advice advice = adviceClass.newInstance();
                Object ret = advice.invoke(target, method, args);
                return ret;
            }

            Method intfMethod = getAnnotationMethod(method.getName(), method.getParameterTypes());
            if (intfMethod != null) {
                aspect = intfMethod.getAnnotation(Aspect.class);
                if (aspect != null) {
                    Class<? extends Advice> adviceClass = aspect.advice();
                    Advice advice = adviceClass.newInstance();
                    Object ret = advice.invoke(target, method, args);
                    return ret;
                }
            }

            // 何も登録されていない場合、通常処理
            if (logger.isTraceEnabled()) {
                logger.trace("[BEGIN] " + target.getClass() + " : " + method.getName());
            }

            // 実際のコードを呼び出し
            Object ret = method.invoke(target, args);

            if (logger.isTraceEnabled()) {
                logger.trace("[END] " + target.getClass() + " : " + method.getName());
            }
            return ret;
        } catch (Throwable e) {
            if (logger.isDebugEnabled()) {
                logger.debug("[ERROR] " + target.getClass() + " : " + method.getName() + " : " + e.getMessage());
            }
            throw e;
        }
    }

    private Method getAnnotationMethod(String name, Class<?>[] parameterTypes) {
        Method m;
        m = getMethod(intf, name, parameterTypes);
        if (m != null) {
            Aspect aspect = m.getAnnotation(Aspect.class);
            if (aspect != null) {
                return m;
            }
        }
        m = getMethod(target.getClass(), name, parameterTypes);
        if (m != null) {
            Aspect aspect = m.getAnnotation(Aspect.class);
            if (aspect != null) {
                return m;
            }
        }
        return null;
    }

    /**
     * 実行するメソッドの検索
     * 
     * @param clazz
     *            メソッドを検索するクラス
     * @param methodName
     *            メソッド名
     * @param parameterTypes
     *            メソッドの引数の型
     * @return メソッド
     */
    private Method getMethod(Class<?> clazz, String methodName, Class<?>[] parameterTypes) {
        Method[] methods = clazz.getDeclaredMethods();
        // boolean exist = false;
        List<Method> list = new ArrayList<Method>();

        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                list.add(method);
            }
        }
        if (!list.isEmpty()) {
            if (list.size() == 1) {
                return list.get(0);
            } else {
                // Privateメソッドを取得する場合、getDeclaredMethodメソッドを使用します。
                Method method;
                try {
                    method = clazz.getDeclaredMethod(methodName, parameterTypes);
                } catch (NoSuchMethodException | SecurityException e) {
                    // throw new SystemException(e);
                    return null;
                }
                return method;
            }
        } else {
            // 親クラスに定義されていないか再帰的に検索
            if (clazz.getSuperclass() != null) {
                return getMethod(clazz.getSuperclass(), methodName, parameterTypes);
            } else {
                // throw new SystemException(new NoSuchMethodException("methodName"));
                return null;
            }
        }
    }

    /**
     * クラスの実行
     */
    @Override
    public Object invoke(Object proxy, Method method, Method proceed, Object[] args) throws Throwable {
        try {
            Aspect aspect = intf.getAnnotation(Aspect.class);
            if (aspect != null) {
                // クラスにAspectが登録されていればそれを実行(そしてリターン)
                Class<? extends Advice> adviceClass = aspect.advice();
                Advice advice = adviceClass.newInstance();
                Object ret = advice.invoke(target, proceed, args);
                return ret;
            }
            // proceedはContainerで取得してProxyでくるんだMethodなので、アノテーションが設定されることは無い
            // aspect = proceed.getAnnotation(Aspect.class);
            // if (aspect != null) {
            // Class<? extends Advice> adviceClass = aspect.advice();
            // Advice advice = adviceClass.newInstance();
            // Object ret = advice.invoke(target, proceed, args);
            // return ret;
            // }

            aspect = method.getAnnotation(Aspect.class);
            if (aspect != null) {
                Class<? extends Advice> adviceClass = aspect.advice();
                Advice advice = adviceClass.newInstance();
                Object ret = advice.invoke(target, proceed, args);
                return ret;
            }

            // 何も登録されていないが、クラスがAbstractDaoを継承している場合
            if (target instanceof AbstractDao) {
                // NewTransactionが設定されていない場合、デフォルトでトランザクションを引き継ぐ
                Advice advice = new Transaction();
                Object ret = advice.invoke(target, proceed, args);
                return ret;
            }

            // 何も登録されていない場合、通常処理
            if (logger.isTraceEnabled()) {
                logger.trace("[BEGIN] " + proxy.getClass() + " : " + method.getName());
            }

            // 実際のコードを呼び出し
            Object ret = proceed.invoke(proxy, args);
            // Object ret = method.invoke(proxy, args);

            if (logger.isTraceEnabled()) {
                logger.trace("[END] " + proxy.getClass() + " : " + method.getName());
            }
            return ret;
        } catch (Throwable e) {
            if (e instanceof InvocationTargetException) {
                InvocationTargetException invocationTargetException = (InvocationTargetException) e;
                Throwable throwable = invocationTargetException.getTargetException();
                if (logger.isInfoEnabled()) {
                    StringBuilder builder = new StringBuilder();
                    builder.append("error. [object]").append(proxy.getClass().toString()).append("[proceed]").append(proceed.getName());
                    if (args != null) {
                        int count = 0;
                        for (Object param : args) {
                            builder.append("[param").append(count++).append("]").append(param);
                        }
                    }
                    builder.append("\n[throwable.getMessage()] ").append(throwable.getMessage());
                    logger.info(builder.toString());
                }
                throw throwable;
            } else {
                if (logger.isInfoEnabled()) {
                    logger.info("[ERROR] " + proxy.getClass() + " : " + method.getName() + " : " + e.getMessage());
                    if (e.getCause() != null) {
                        logger.info("  <Cause>", e.getCause());
                    }
                }
            }
            throw e;
        }
    }
}
