package org.support.project.web.common;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.support.project.common.exception.ArgumentException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;

public class ValueConverter {
    /** ログ */
    private static Log log = LogFactory.getLog(MethodHandles.lookup());

    public static <T> T conv(String[] value, Class<? extends T> type) {
        if (value == null) {
            return null;
        }
        if (value.length == 0) {
            return null;
        }
        if (type.isPrimitive()) {
            return convPrimitive(value, type);
        } else if (type.isArray()) {
            return convArray(value, type);
        } else if (List.class.isAssignableFrom(type)) {
            if (type.isInterface()) {
                return (T) convList(value, ArrayList.class);
            } else {
                return convList(value, type);
            }
        } else {
            return convObject(value, type);
        }
    }

    /**
     * 配列の型にコンバート(多次元配列は対象外)
     * 
     * @param value 値
     * @param type 型
     * @param <T> type
     * @return コンバートされた値
     */
    public static <T> T convList(String[] value, Class<? extends T> type) {
        log.warn("listへの値変換はString型のみになるので、非推奨です");
        List list;
        try {
            list = (List) type.newInstance();
            for (String string : value) {
                list.add(string);
            }
            return (T) list;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ArgumentException(e);
        }
    }

    /**
     * 配列の型にコンバート(多次元配列は対象外)
     * 
     * @param value 値
     * @param type 型
     * @param <T> type
     * @return コンバートされた値
     */
    public static <T> T convArray(String[] value, Class<? extends T> type) {
        Class<?> compType = type.getComponentType();

        Object arr = Array.newInstance(compType, value.length);

        int index = 0;
        for (String string : value) {
            if (compType.isPrimitive()) {
                Object obj = convPrimitive(string, compType);
                // Array.set(arr, index, obj);
                if (int.class.isAssignableFrom(compType)) {
                    Array.setInt(arr, index, (int) obj);
                } else if (double.class.isAssignableFrom(compType)) {
                    Array.setDouble(arr, index, (double) obj);
                } else if (short.class.isAssignableFrom(compType)) {
                    Array.setShort(arr, index, (short) obj);
                } else if (long.class.isAssignableFrom(compType)) {
                    Array.setLong(arr, index, (long) obj);
                } else if (float.class.isAssignableFrom(compType)) {
                    Array.setFloat(arr, index, (float) obj);
                } else if (boolean.class.isAssignableFrom(compType)) {
                    Array.setBoolean(arr, index, (boolean) obj);
                }
            } else {
                Object obj = convObject(string, compType);
                Array.set(arr, index, obj);
            }

            index++;
        }

        return (T) arr;
    }

    /**
     * プリミティブ型にコンバート
     * 
     * @param value 値
     * @param type 型
     * @param <T> type
     * @return コンバートされた値
     */
    public static <T> T convPrimitive(String[] value, Class<? extends T> type) {
        String v = value[0];
        return convPrimitive(v, type);
    }

    /**
     * プリミティブ型にコンバート
     * 
     * @param v 値
     * @param type 型
     * @param <T> type
     * @return コンバートされた値
     */
    public static <T> T convPrimitive(String v, Class<? extends T> type) {
        if (int.class.isAssignableFrom(type)) {
            return (T) new Integer(v);
        } else if (double.class.isAssignableFrom(type)) {
            return (T) new Double(v);
        } else if (short.class.isAssignableFrom(type)) {
            return (T) new Short(v);
        } else if (long.class.isAssignableFrom(type)) {
            return (T) new Long(v);
        } else if (float.class.isAssignableFrom(type)) {
            return (T) new Float(v);
        } else if (boolean.class.isAssignableFrom(type)) {
            return (T) new Boolean(v);
        }
        return null;
    }

    /**
     * オブジェクトの型にコンバート
     * 
     * @param value 値
     * @param type 型
     * @param <T> type
     * @return コンバートされた値
     */
    public static <T> T convObject(String[] value, Class<? extends T> type) {
        String v = value[0];
        return convObject(v, type);
    }

    /**
     * オブジェクトの型にコンバート
     * 
     * @param v 値
     * @param type 型
     * @param <T> type
     * @return コンバートされた値
     */
    public static <T> T convObject(String v, Class<? extends T> type) {
        if (String.class.isAssignableFrom(type)) {
            return (T) v;
        } else if (Integer.class.isAssignableFrom(type)) {
            if (StringUtils.isEmpty(v)) {
                return null;
            }
            return (T) new Integer(v);
        } else if (Double.class.isAssignableFrom(type)) {
            if (StringUtils.isEmpty(v)) {
                return null;
            }
            return (T) new Double(v);
        } else if (Short.class.isAssignableFrom(type)) {
            if (StringUtils.isEmpty(v)) {
                return null;
            }
            return (T) new Short(v);
        } else if (Long.class.isAssignableFrom(type)) {
            if (StringUtils.isEmpty(v)) {
                return null;
            }
            return (T) new Long(v);
        } else if (Float.class.isAssignableFrom(type)) {
            if (StringUtils.isEmpty(v)) {
                return null;
            }
            return (T) new Float(v);
        } else if (Boolean.class.isAssignableFrom(type)) {
            if (StringUtils.isEmpty(v)) {
                return null;
            }
            return (T) new Boolean(v);
        }
        return null;

    }

}
