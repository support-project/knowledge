package org.support.project.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.support.project.common.exception.SystemException;

/**
 * オブジェクト操作
 * 
 * @author Koda
 *
 */
public abstract class ObjectUtils extends org.apache.commons.lang.ObjectUtils {

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
    public static Method getMethod(Class<?> clazz, String methodName, Class<?>[] parameterTypes) {
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
                    throw new SystemException(e);
                }
                return method;
            }
        } else {
            // 親クラスに定義されていないか再帰的に検索
            if (clazz.getSuperclass() != null) {
                return getMethod(clazz.getSuperclass(), methodName, parameterTypes);
            } else {
                throw new SystemException(new NoSuchMethodException("methodName"));
            }
        }
    }

    /**
     * メソッドの実行。
     * 
     * テストの際に、privateやprotectedのメソッドを呼び出したい事がある。 その際には本メソッドで実行し結果を取得する。
     * 
     * @param obj
     *            メソッドを実行するオブジェクト
     * @param methodName
     *            実行するメソッド名
     * @param parameterTypes
     *            メソッドのパラメータのタイプ
     * @param params
     *            メソッドに渡すパラメータ
     * @return メソッドのリターン値
     */
    private static Object invoke(Object obj, String methodName, Class<?>[] parameterTypes, Object... params) {
        try {
            Method method = getMethod(obj.getClass(), methodName, parameterTypes);
            method.setAccessible(true);
            return invoke(obj, method, params);
        } catch (IllegalArgumentException e) {
            throw new SystemException(e);
        }
    }

    /**
     * メソッドの実行。
     * 
     * テストの際に、privateやprotectedのメソッドを呼び出したい事がある。 その際には本メソッドで実行し結果を取得する。
     * 
     * @param obj
     *            メソッドを実行するオブジェクト
     * @param methodName
     *            実行するメソッド名
     * @param params
     *            メソッドに渡すパラメータ
     * @return メソッドのリターン値
     */
    public static Object invoke(Object obj, String methodName, Object... params) {
        Class<?>[] parameterTypes = null;
        if (params != null) {
            parameterTypes = new Class<?>[params.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                Object param = params[i];
                if (param != null) {
                    parameterTypes[i] = params[i].getClass();
                }
            }
        }
        return invoke(obj, methodName, parameterTypes, params);
    }

    /**
     * メソッド実行
     * 
     * @param obj
     *            オブジェクト
     * @param method
     *            メソッド
     * @param params
     *            パラメータ
     * @return メソッドの戻り値
     */
    public static Object invoke(Object obj, Method method, Object... params) {
        try {
            if (method.getReturnType().equals(Void.class)) {
                method.invoke(obj, params);
                return null;
            } else {
                Object ret = method.invoke(obj, params);
                return ret;
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new SystemException(e);
        }
    }

    /**
     * フィールドの検索
     * 
     * @param clazz
     *            クラス
     * @param feildname
     *            フィールド名
     * @return フィールドにセットされたオブジェクト
     * @throws NoSuchFieldException
     *             NoSuchFieldException
     */
    private static Field getField(Class<?> clazz, String feildname) throws NoSuchFieldException {
        Field[] fields = clazz.getDeclaredFields();
        boolean exist = false;
        for (Field field : fields) {
            if (field.getName().equals(feildname)) {
                exist = true;
            }
        }
        if (exist) {
            Field field = clazz.getDeclaredField(feildname);
            return field;
        } else {
            // 親クラスに定義されていないか再帰的に検索
            if (clazz.getSuperclass() != null) {
                return getField(clazz.getSuperclass(), feildname);
            } else {
                clazz.getDeclaredField(feildname); // Exceptionが発生する
                return null;
            }
        }
    }

    /**
     * フィールドのオブジェクトを取得
     * 
     * テストの際に、privateやprotectedのフィールドの状態を確認したい事がある。 その際には本メソッドでフィールドのオブジェクトを取得する。
     * 
     * @param obj
     *            オブジェクト
     * @param feildname
     *            フィールド名
     * @return フィールド
     */
    public static Object getFieldObject(Object obj, String feildname) {
        try {
            Field field;
            field = getField(obj.getClass(), feildname);
            field.setAccessible(true);
            return field.get(obj);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            throw new SystemException(e);
        }
    }

}
