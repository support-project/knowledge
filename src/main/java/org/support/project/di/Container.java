package org.support.project.di;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.support.project.common.classanalysis.ClassAnalysis;
import org.support.project.common.classanalysis.ClassAnalysisFactory;
import org.support.project.common.config.Resources;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.config.DIParameter;
import org.support.project.di.exception.DIException;
import org.support.project.di.factory.ClassInstanceFactory;
import org.support.project.di.factory.InterfaceInstanceFactory;

/**
 * DIコンテナ
 */
public class Container {
    /** ログ */
    private static Log logger = LogFactory.getLog(Container.class);
    /** シングルトンのオブジェクトを管理するマップ */
    private static Map<String, Object> objectMap = null;

    static {
        init();
    }

    /**
     * 初期化
     */
    private static synchronized void init() {
        if (objectMap == null) {
            objectMap = new HashMap<>();
            if (logger.isTraceEnabled()) {
                logger.trace("The objectMap holding Singleton's instance was generated.");
            }
        }
        InterfaceInstanceFactory.init();
        Resources.getInstance(DIParameter.DI_RESOURCE);
    }

    /**
     * シングルトンのインスタンスを管理するマップを取得
     * 
     * @return map
     */
    private static synchronized Map<String, Object> getObjectMap() {
        return objectMap;
    }

    /**
     * コンポーネントを取得する
     * 
     * @param type
     *            type
     * @param <T>
     *            return type
     * @return instance
     */
    public static <T> T getComp(final Class<? extends T> type) throws DIException {
        String key = type.getName();
        return getComp(key, type);
    }

    /**
     * コンポーネントを取得する
     * 
     * @param key
     *            key
     * @param type
     *            type
     * @param <T>
     *            return type
     * @return instance
     */
    @SuppressWarnings("unchecked")
    public static <T> T getComp(final String key, final Class<? extends T> type) throws DIException {
        DI di = type.getAnnotation(DI.class);
        if (di == null) {
            // DI設定が無ければ、ただのnewで返す(Prototypeで毎回インスタンスを生成)
            return newInstance(key, type);
        }

        if (di.instance() == Instance.Singleton) {
            if (getObjectMap().containsKey(key)) {
                return (T) getObjectMap().get(key);
            } else {
                T object = newInstance(key, type);
                getObjectMap().put(key, object);
                return object;

            }
        }
        // プロトタイプ
        return newInstance(key, type);
    }

    /**
     * 新しいインスタンスを生成
     * 
     * @param key
     *            key
     * @param type
     *            type
     * @param <T>
     *            return type
     * @return instance
     */
    private static <T> T newInstance(String key, final Class<? extends T> type) throws DIException {
        try {
            if (type.isInterface()) {
                return InterfaceInstanceFactory.newInstance(key, type);
            } else {
                return ClassInstanceFactory.newInstance(key, type);
            }
        } catch (InstantiationException e) {
            throw new DIException("errors.di.instantiation", e);
        } catch (IllegalAccessException e) {
            throw new DIException("errors.di.illegalAccess", e);
        }
    }
    
    /**
     * clear component
     * @param type type
     */
    public static void clearComp(final Class<?> type) {
        String key = type.getName();
        clearComp(key, type);
    }
    /**
     * clear component
     * @param key key
     * @param type type
     * @throws DIException DIException
     */
    public static void clearComp(final String key, final Class<?> type) throws DIException {
        DI di = type.getAnnotation(DI.class);
        if (di == null) {
            // DI設定が無ければ、ただのnewで返すので、特に何もする必要無し
            return;
        }
        if (di.instance() == Instance.Singleton) {
            // Singletonの場合に、情報を削除する
            if (getObjectMap().containsKey(key)) {
                Object obj = getComp(key, type);

                ClassAnalysis classAnalysis = ClassAnalysisFactory.getClassAnalysis(type);
                if (classAnalysis.haveMethod("clear")) {
                    Method clearMethod = classAnalysis.getMethod(key);
                    if (clearMethod != null) {
                        try {
                            clearMethod.invoke(obj);
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                            throw new DIException(e);
                        }
                    }
                }

                obj = null;
                getObjectMap().remove(key);
            }
        }
    }

}
