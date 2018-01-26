package org.support.project.common.classanalysis;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.support.project.common.exception.SystemException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;

/**
 * リフレクションを使ってオブジェクトを解析した結果を保持するVo
 * 
 * リフレクション処理は少し重いので、一度解析を行ったものは 保持して使いまわす
 * 
 * @author Koda
 *
 */
public class ClassAnalysis {
    /** ログ */
    private static Log log = LogFactory.getLog(MethodHandles.lookup());

    /** プロパティにフィールドとしてアクセスする */
    public static final int PROPERTY_ACCESS_FIELD = 0;
    /** プロパティにメソッドとしてアクセスする */
    public static final int PROPERTY_ACCESS_METHOD = 1;
    /** プロパティにアクセスできない */
    public static final int PROPERTY_ACCESS_NONE = -1;

    /**
     * プロパティとして認識するものの名前の一覧。
     * 
     * プロパティとして認識するものは、以下とする。
     * 
     * publicのフィールド publicなgetter/setterがあるもの
     * 
     * private等の隠ぺいされたものは、いったんプロパティとしない
     */
    private List<String> propertyNames;
    /**
     * プロパティ名で保持されたFieldオブジェクト
     */
    private Map<String, Field> propertyFeild;
    /**
     * プロパティ名に値を取得するgetterメソッド
     */
    private Map<String, Method> getPropertyMethod;
    /**
     * プロパティ名の値を設定するsetterメソッド
     */
    private Map<String, Method> setPropertyMethod;

    /** プロパティのタイプ */
    private Map<String, Class<?>> propertyType;

    /**
     * 解析したクラス
     */
    private Class<?> clazz;

    /** 解析したクラスが持つ、publicなメソッド */
    private Map<String, Method> methodMap = new HashMap<>();

    /**
     * クラスの解析を生成
     * 
     * @param clazz
     *            解析するクラス
     */
    ClassAnalysis(Class<?> clazz) {
        super();
        this.clazz = clazz;
        this.propertyNames = new ArrayList<>();
        this.propertyFeild = new HashMap<String, Field>();
        this.getPropertyMethod = new HashMap<>();
        this.setPropertyMethod = new HashMap<>();
        this.propertyType = new HashMap<>();
        sartAnalysis();
    }

    /**
     * クラスを解析する
     */
    private void sartAnalysis() {
        log.trace("start class analysis : " + this.clazz.getName());
        // クラスに存在する全てのメソッドを取得する
        addPublicMethod(methodMap, this.clazz);

        // publicメソッドのうち、getXxx / setXxxのペアが存在するものとプロパティとする
        // PropertyDescriptorを利用するのもありだが、いったん自前で実装している
        List<String> getter = new ArrayList<>();
        List<String> setter = new ArrayList<>();
        Iterator<String> iterator = methodMap.keySet().iterator();
        while (iterator.hasNext()) {
            String methodName = (String) iterator.next();
            if (methodName.startsWith("get")) {
                Method getterMethod = methodMap.get(methodName);
                if (getterMethod.getParameterTypes().length == 0) {
                    if (!getterMethod.getReturnType().equals(Void.TYPE)) {
                        if (methodName.length() > "get".length()) {
                            StringBuilder builder = new StringBuilder();
                            builder.append(methodName.substring("get".length(), "get".length() + 1).toLowerCase());
                            if (methodName.length() > "get".length() + 1) {
                                builder.append(methodName.substring("get".length() + 1));
                            }
                            getter.add(builder.toString());
                        }
                    }
                }
            } else if (methodName.startsWith("set")) {
                Method setterMethod = methodMap.get(methodName);
                if (setterMethod.getParameterTypes().length == 1) {
                    if (setterMethod.getReturnType().equals(Void.TYPE) || setterMethod.getReturnType().isAssignableFrom(this.clazz)) {
                        if (methodName.length() > "set".length()) {
                            if (setterMethod.getParameterTypes().length == 1) {
                                StringBuilder builder = new StringBuilder();
                                builder.append(methodName.substring("set".length(), "get".length() + 1).toLowerCase());
                                if (methodName.length() > "set".length() + 1) {
                                    builder.append(methodName.substring("get".length() + 1));
                                }
                                setter.add(builder.toString());
                            }
                        }
                    }
                }
            } else if (methodName.startsWith("is")) {
                Method getterMethod = methodMap.get(methodName);
                if (getterMethod.getParameterTypes().length == 0) {
                    if (!getterMethod.getReturnType().equals(Void.TYPE)) {
                        if (methodName.length() > "is".length()) {
                            StringBuilder builder = new StringBuilder();
                            builder.append(methodName.substring("is".length(), "is".length() + 1).toLowerCase());
                            if (methodName.length() > "is".length() + 1) {
                                builder.append(methodName.substring("is".length() + 1));
                            }
                            getter.add(builder.toString());
                        }
                    }
                }
            }
        }

        for (String propName : setter) {
            if (getter.contains(propName)) {
                Method getterMethod = methodMap.get("get" + propName.substring(0, 1).toUpperCase() + propName.substring(1));
                if (getterMethod == null) {
                    getterMethod = methodMap.get("is" + propName.substring(0, 1).toUpperCase() + propName.substring(1));
                }
                Method setterMethod = methodMap.get("set" + propName.substring(0, 1).toUpperCase() + propName.substring(1));
                this.getPropertyMethod.put(propName, getterMethod);
                this.setPropertyMethod.put(propName, setterMethod);
                this.propertyType.put(propName, getterMethod.getReturnType());

                this.propertyNames.add(propName);
                log.trace("\t put public getter/setter method. property name is " + propName);
            }
        }

        // クラスに存在する全てのフィールドを取得する
        addPublicFeilds(this.clazz);
    }

    /**
     * クラスに存在する全てのpublicメソッドを取得する
     * 
     * @param methodMap
     * @param clazz2
     */
    private void addPublicMethod(Map<String, Method> methodMap, Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (Modifier.isPublic(method.getModifiers())) {
                if (!methodMap.containsKey(method.getName())) {
                    methodMap.put(method.getName(), method);
                }
            }
        }
        if (clazz.getSuperclass() != null) {
            addPublicMethod(methodMap, clazz.getSuperclass());
        }
    }

    /**
     * 指定オブジェクトのフィールドを再帰的に全て取得し、Listに追加する
     * 
     * @param feilds
     * @param clazz
     */
    private void addPublicFeilds(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isPublic(field.getModifiers())) {
                if (!propertyFeild.containsKey(field.getName())) {
                    if (!this.propertyNames.contains(field.getName())) {
                        // 追加していないフィールド名のみ追加
                        // スーパークラスに同じフィールドがある場合、追加されないので注意すること
                        // これにより、スーパークラスに同じフィールド名が定義されている場合、
                        // スーパークラスの値はセットしない
                        // また、すでにgetter/setterがあり、プロパティとして認識されている場合、
                        // そちらを優先する
                        this.propertyFeild.put(field.getName(), field);
                        this.propertyNames.add(field.getName());
                        this.propertyType.put(field.getName(), field.getType());
                        log.trace("\t put public field. property name is " + field.getName());
                    }
                }
            }
        }
        if (clazz.getSuperclass() != null) {
            // 親クラスがある場合、再帰的に処理する
            addPublicFeilds(clazz.getSuperclass());
        }
    }

    /**
     * プロパティの一覧を取得
     * 
     * @return プロパティ名の一覧
     */
    public List<String> getPropertyNames() {
        // 変更されると困るので、コピーインスタンスを返す
        List<String> list = new ArrayList<>();
        list.addAll(propertyNames);
        return list;
    }

    /**
     * プロパティ名でプロパティへのアクセス方法を取得する
     * 
     * @param propertyName property name
     * @return access method
     */
    public int getPropertyAccessType(String propertyName) {
        if (this.getPropertyMethod.containsKey(propertyName)) {
            return PROPERTY_ACCESS_METHOD;
        } else if (this.propertyFeild.containsKey(propertyName)) {
            return PROPERTY_ACCESS_FIELD;
        }
        return PROPERTY_ACCESS_NONE;
    }

    /**
     * プロパティ名でフィールドを取得
     * 
     * @param propertyName
     *            プロパティ名
     * @return Field
     */
    public Field getPropertyFeild(String propertyName) {
        if (this.propertyFeild.containsKey(propertyName)) {
            return this.propertyFeild.get(propertyName);
        }
        throw new SystemException(new NoSuchFieldException(propertyName));
    }

    /**
     * プロパティ名でGetterメソッドを取得
     * 
     * @param propertyName
     *            プロパティ名
     * @return Getterメソッド
     */
    public Method getGetterPropertyMethod(String propertyName) {
        if (this.getPropertyMethod.containsKey(propertyName)) {
            return this.getPropertyMethod.get(propertyName);
        }
        throw new SystemException(new NoSuchMethodException(propertyName));
    }

    /**
     * プロパティ名でSetterメソッドを取得
     * 
     * @param propertyName
     *            プロパティ名
     * @return Setterメソッド
     */
    public Method getSetterPropertyMethod(String propertyName) {
        if (this.setPropertyMethod.containsKey(propertyName)) {
            return this.setPropertyMethod.get(propertyName);
        }
        throw new SystemException(new NoSuchMethodException(propertyName));
    }

    /**
     * get property type
     * 
     * @param propertyName
     *            property name
     * @return property type
     */
    public Class<?> getPropertyType(String propertyName) {
        if (this.propertyType.containsKey(propertyName)) {
            return this.propertyType.get(propertyName);
        }
        throw new SystemException(new NoSuchMethodException(propertyName));
    }
    
    /**
     * check method is exists.
     * @param method method
     * @return result
     */
    public boolean haveMethod(String method) {
        if (methodMap.containsKey(method)) {
            return true;
        }
        return false;
    }
    
    /**
     * get method
     * @param method method name
     * @return method
     */
    public Method getMethod(String method) {
        if (methodMap.containsKey(method)) {
            return methodMap.get(method);
        }
        return null;
    }

}
