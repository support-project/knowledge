package org.support.project.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.support.project.common.classanalysis.ClassAnalysis;
import org.support.project.common.classanalysis.ClassAnalysisFactory;
import org.support.project.common.exception.SystemException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;

import net.arnx.jsonic.JSON;
import net.arnx.jsonic.JSONException;

/**
 * Utility for object property.
 * @author Koda
 */
public class PropertyUtil {
    /** LOG */
    private static final Log LOG = LogFactory.getLog(PropertyUtil.class);

    /**
     * 直接値を持つクラス (equalsPropertyで利用)
     */
    public static final Class<?>[] VALUE_CLASSES = { byte.class, Byte.class, short.class, Short.class, int.class, Integer.class, long.class,
        Long.class, char.class, Character.class, float.class, Float.class, double.class, Double.class, boolean.class, Boolean.class,
        String.class };

    /**
     * 本クラスで値を設定／取得ができるプロパティ名の一覧を取得
     * 
     * @param obj
     *            Objject
     * @return 本クラスで値を設定／取得ができるプロパティ名の一覧
     */
    public static List<String> getPropertyNames(Object obj) {
        return getPropertyNames(obj.getClass());
    }

    /**
     * 本クラスで値を設定／取得ができるプロパティ名の一覧を取得
     * 
     * @param clazz
     *            クラス
     * @return 本クラスで値を設定／取得ができるプロパティ名の一覧
     */
    public static List<String> getPropertyNames(Class<?> clazz) {
        ClassAnalysis analysis = ClassAnalysisFactory.getClassAnalysis(clazz);
        List<String> props = new ArrayList<String>();
        props.addAll(analysis.getPropertyNames());
        return props;
    }

    /**
     * 指定のプロパティを持つかチェック
     * @param clazz クラス
     * @param prop プロパティ
     * @return 結果
     */
    public static boolean hasProperty(Class<?> clazz, String prop) {
        List<String> props = getPropertyNames(clazz);
        return props.contains(prop);
    }
    
    /**
     * オブジェクトのプロパティ値を取得
     * 
     * @param object
     *            オブジェクト
     * @param propertyName
     *            プロパティ名
     * @return プロパティ値
     */
    public static Object getPropertyValue(Object object, String propertyName) {
        try {
            ClassAnalysis analysis = ClassAnalysisFactory.getClassAnalysis(object.getClass());
            int accessType = analysis.getPropertyAccessType(propertyName);
            if (accessType == ClassAnalysis.PROPERTY_ACCESS_FIELD) {
                Field field = analysis.getPropertyFeild(propertyName);
                return field.get(object);
            } else if (accessType == ClassAnalysis.PROPERTY_ACCESS_METHOD) {
                Method getter = analysis.getGetterPropertyMethod(propertyName);
                return getter.invoke(object, null);
            } else {
                throw new SystemException(new NoSuchMethodException(propertyName));
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new SystemException(e);
        }
    }

    /**
     * オブジェクトのプロパティ値に値をセット
     * 
     * @param object
     *            オブジェクト
     * @param propertyName
     *            プロパティ名
     * @param value
     *            セットする値
     */
    public static void setPropertyValue(Object object, String propertyName, Object value) {
        try {
            ClassAnalysis analysis = ClassAnalysisFactory.getClassAnalysis(object.getClass());
            int accessType = analysis.getPropertyAccessType(propertyName);
            if (accessType == ClassAnalysis.PROPERTY_ACCESS_FIELD) {
                Field field = analysis.getPropertyFeild(propertyName);
                try {
                    field.set(object, value);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    LOG.debug("Feild value set error." + object.getClass().getName() + "#" + field.getName(), e);
                }
            } else if (accessType == ClassAnalysis.PROPERTY_ACCESS_METHOD) {
                Method setter = analysis.getSetterPropertyMethod(propertyName);
                setter.invoke(object, value);
            } else {
                throw new SystemException(new NoSuchMethodException(propertyName));
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            StringBuilder error = new StringBuilder();
            error.append("Error on setPropertyValue.\n");
            error.append("\tClass=").append(object.getClass().getName()).append("\n");
            error.append("\tProperty=").append(propertyName).append("\n");
            error.append("\tValue=").append(value).append("\n");
            if (value != null) {
                error.append("\tValueClass=").append(value.getClass().getName()).append("\n");
            }
            LOG.error(error.toString(), e);
            SystemException exception = new SystemException(e.getMessage());
            exception.setStackTrace(e.getStackTrace());
            throw exception;
        }
    }

    /**
     * プロパティの値を同一のプロパティにコピーする (同一名のプロパティで、型が異なる場合はエラー) 渡すプロパティのインスタンスがNULLの場合もNullPointerが発生する
     * 
     * @param base
     *            コピー元
     * @param target
     *            コピー先
     */
    public static void copyPropertyValue(Object base, Object target) {
        try {
            List<String> baseProps = getPropertyNames(base);
            List<String> targetProps = getPropertyNames(target);
            for (String prop : baseProps) {
                if (targetProps.contains(prop)) {
                    // 同一のプロパティが存在する
                    ClassAnalysis analysis1 = ClassAnalysisFactory.getClassAnalysis(base.getClass());
                    ClassAnalysis analysis2 = ClassAnalysisFactory.getClassAnalysis(target.getClass());

                    Class<?> getType = null;
                    if (analysis1.getPropertyAccessType(prop) == ClassAnalysis.PROPERTY_ACCESS_METHOD) {
                        Method method = analysis1.getGetterPropertyMethod(prop);
                        getType = method.getReturnType();
                    } else if (analysis1.getPropertyAccessType(prop) == ClassAnalysis.PROPERTY_ACCESS_FIELD) {
                        Field field = analysis1.getPropertyFeild(prop);
                        getType = field.getType();
                    }

                    Class<?> setType = null;
                    if (analysis2.getPropertyAccessType(prop) == ClassAnalysis.PROPERTY_ACCESS_METHOD) {
                        Method method = analysis1.getSetterPropertyMethod(prop);
                        setType = method.getParameterTypes()[0];
                    } else if (analysis2.getPropertyAccessType(prop) == ClassAnalysis.PROPERTY_ACCESS_FIELD) {
                        Field field = analysis1.getPropertyFeild(prop);
                        setType = field.getType();
                    }

                    if (getType != null && setType != null && getType.equals(setType)) {
                        setPropertyValue(target, prop, getPropertyValue(base, prop));
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            throw new SystemException(e);
        }
    }

    /**
     * プロパティの型を取得
     * 
     * @param object
     *            オブジェクト
     * @param propertyName
     *            プロパティ名
     * @return プロパティの型
     */
    public static Class<?> getPropertyType(Object object, String propertyName) {
        return getPropertyType(object.getClass(), propertyName);
    }

    /**
     * プロパティの型を取得
     * 
     * @param clazz
     *            クラス
     * @param propertyName
     *            プロパティ名
     * @return プロパティの型
     */
    public static Class<?> getPropertyType(Class<?> clazz, String propertyName) {
        try {
            ClassAnalysis analysis = ClassAnalysisFactory.getClassAnalysis(clazz);
            int accessType = analysis.getPropertyAccessType(propertyName);
            if (accessType == ClassAnalysis.PROPERTY_ACCESS_FIELD) {
                Field field = analysis.getPropertyFeild(propertyName);
                return field.getType();
            } else if (accessType == ClassAnalysis.PROPERTY_ACCESS_METHOD) {
                Method getter = analysis.getGetterPropertyMethod(propertyName);
                return getter.getReturnType();
            } else {
                throw new SystemException(new NoSuchMethodException(propertyName));
            }
        } catch (IllegalArgumentException e) {
            throw new SystemException(e);
        }
    }

    /**
     * オブジェクトのプロパティの値を全て取得
     * 
     * @param object object
     * @return property values on map
     */
    public static Map<String, Object> getValues(Object object) {
        Map<String, Object> map = new HashMap<>();

        List<String> props = getPropertyNames(object.getClass());
        for (String prop : props) {
            map.put(prop, getPropertyValue(object, prop));
        }

        return map;
    }

    /**
     * オブジェクトの比較
     * 
     * @param obj1 object1
     * @param obj2 object2
     * @return result
     */
    public static boolean equalsProperty(Object obj1, Object obj2) {
        if (obj1 == null) {
            if (obj2 == null) {
                return true;
            } else {
                return false;
            }
        } else {
            if (obj2 == null) {
                return false;
            }
            if (obj1.getClass().isPrimitive() && obj2.getClass().isPrimitive()) {
                // 比較の対象はプリミティブ型
                return equalsPrimitive(obj1, obj2);
            } else {
                for (Class clazz : VALUE_CLASSES) {
                    if (clazz.isAssignableFrom(obj1.getClass()) || clazz.isAssignableFrom(obj2.getClass())) {
                        return obj1.equals(obj2);
                    }
                }
            }

            // 値を直接持たないオブジェクトの場合
            List<String> props1 = PropertyUtil.getPropertyNames(obj1.getClass());
            List<String> props2 = PropertyUtil.getPropertyNames(obj2.getClass());

            if (props1.size() == 0 && props2.size() == 0) {
                // プロパティを持たないオブジェクト
                return obj1.equals(obj2);
            }

            for (String prop : props1) {
                if (props2.contains(prop)) {
                    // 同一のプロパティが存在する
                    Object value1 = PropertyUtil.getPropertyValue(obj1, prop);
                    Object value2 = PropertyUtil.getPropertyValue(obj2, prop);

                    if (value1 == null) {
                        if (value2 != null) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("property [" + prop + "] is wrong. 1's value is " + value1 + ". 2's value is " + value1 + ".");
                            }
                            return false;
                        }
                    } else if (!equalsProperty(value1, value2)) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("property [" + prop + "] is wrong. 1's value is " + value1 + ". 2's value is " + value1 + ".");
                        }
                        return false;
                    }
                    props2.remove(prop);
                }
            }

            for (String prop : props2) {
                if (props1.contains(prop)) {
                    Object value1 = PropertyUtil.getPropertyValue(obj1, prop);
                    Object value2 = PropertyUtil.getPropertyValue(obj2, prop);
                    if (value1 == null) {
                        if (value2 != null) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("property [" + prop + "] is wrong. 1's value is " + value1 + ". 2's value is " + value1 + ".");
                            }
                            return false;
                        }
                    } else if (!equalsProperty(value1, value2)) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("property [" + prop + "] is wrong. 1's value is " + value1 + ". 2's value is " + value1 + ".");
                        }
                        return false;
                    }
                }
            }

            return true;
        }
    }

    /**
     * プリミティブ型の比較
     * 
     * @param obj1
     * @param obj2
     * @return
     */
    private static boolean equalsPrimitive(Object obj1, Object obj2) {
        if (obj1.getClass().isPrimitive() && obj2.getClass().isPrimitive()) {
            return obj1.equals(obj2);
        }
        throw new SystemException("The comparison is not a primitive type");
    }

    /**
     * オブジェクトから、表示用の文字列生成
     * 
     * @param obj object
     * @return string
     */
    public static String reflectionToString(Object obj) {
        if (obj == null) {
            return "null";
        }
        if (isValueClass(obj.getClass())) {
            return String.valueOf(obj);
        }
        try {
            return JSON.encode(obj);
        } catch (JSONException e) {
            return obj.toString();
        }
    }

    // public static String reflectionToString(Object obj, int level) {
    // StringBuilder builder = new StringBuilder();
    // for (int i = 0; i < level; i++) {
    // builder.append("\t");
    // }
    // if (obj == null) {
    // builder.append("null");
    // return builder.toString();
    // }
    //
    // List<String> props = PropertyUtil.getPropertyNames(obj.getClass());
    // for (String prop : props) {
    // Object value = getPropertyValue(obj, prop);
    // if (value == null) {
    // builder.append("[").append(prop).append("] ").append("null");
    // } else {
    // Class<?> type = PropertyUtil.getPropertyType(obj.getClass(), prop);
    // if (isValueClass(type)) {
    // builder.append("[").append(prop).append("] ").append(value);
    // } else {
    // builder.append("[").append(prop).append("] ").append(type.getName());
    // builder.append("\n");
    // builder.append(reflectionToString(value, level + 1));
    // }
    // }
    // }
    // return builder.toString();
    // }
    //
    
    /**
     * 指定のオブジェクトの private フィールドの値を取得する
     * テスト用
     * 
     * @param class1 取得するフィールドの型
     * @param obj オブジェクト
     * @param property プロパティ名
     * @return プロパティの値
     * @throws NoSuchFieldException NoSuchFieldException
     * @throws SecurityException SecurityException
     * @throws IllegalArgumentException IllegalArgumentException
     * @throws IllegalAccessException IllegalAccessException
     */
    public static <T> T getPrivateFeildOnReflection(Class<T> class1, Object obj, String property)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(property);
        field.setAccessible(true);
        return (T) field.get(obj);
    }
    
    /**
     * 指定のクラスが値を持つクラスかどうか
     * @param c class type
     * @return is value class
     */
    public static boolean isValueClass(Class<?> c) {
        for (Class<?> clazz : VALUE_CLASSES) {
            if (clazz.isAssignableFrom(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 文字列の値を指定したクラスの値に変換する
     * 
     * @param value string
     * @param class1 class
     * @param <E> result type
     * @return convert result
     */
    public static <E> E convValue(String value, Class<? extends E> class1) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        if (class1.equals(Integer.class)) {
            Integer result = new Integer(value);
            return (E) result;
        } else if (class1.equals(Double.class)) {
            Double result = new Double(value);
            return (E) result;
        } else if (class1.equals(Float.class)) {
            Float result = new Float(value);
            return (E) result;
        } else if (class1.equals(Long.class)) {
            Long result = new Long(value);
            return (E) result;
        } else if (class1.equals(Short.class)) {
            Short result = new Short(value);
            return (E) result;
        } else if (class1.equals(String.class)) {
            String result = value;
            return (E) result;
        } else if (class1.equals(BigDecimal.class)) {
            BigDecimal result = new BigDecimal(value);
            return (E) result;
        } else if (class1.equals(Blob.class)) {
            throw new SystemException("This process does not support Blob");
        } else if (class1.equals(Boolean.class)) {
            Boolean result = new Boolean(value);
            return (E) result;
        } else if (class1.equals(Byte.class)) {
            throw new SystemException("This process does not support Byte");
        } else if (class1.equals(Timestamp.class)) {
            throw new SystemException("This process does not support Timestamp");
        } else if (class1.equals(Time.class)) {
            throw new SystemException("This process does not support Time");
        } else if (class1.equals(Date.class)) {
            throw new SystemException("This process does not support Date");
        } else if (class1.equals(java.util.Date.class)) {
            throw new SystemException("This process does not support Date");
        } else {
            throw new SystemException("This process does not support " + class1.getName());
        }
    }

}
