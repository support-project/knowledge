package org.support.project.web.common;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.support.project.common.util.ObjectUtils;
import org.support.project.di.Container;

/**
 * 実行するターゲットのオブジェクト
 * 
 * @author Koda
 *
 */
public class InvokeTarget {
    /** 実行するクラス */
    private Class<?> targetClass;
    /** 実行するメソッド */
    private Method targetMethod;
    /** 実行ターゲット */
    private Object target;

    /** ターゲット検索で起点になったパッケージ名 */
    private String targetPackageName;
    /** ターゲットのパッケージ名からの、サブパッケージの名称 */
    private String subPackageName;
    /** ターゲットの検索の際に使ったサフィックス */
    private String classSuffix;
    /** Authアノテーションでセットするロール情報(copyするとアノテーション情報が取得できなくなるため実体を保持する） */
    private List<String> roles = new ArrayList<>();
    
    /** パスからパラメータ値を取得できるようにセットした場合の値 */
    private Map<String, String> pathValue;
    /** 実行時の呼び出しパラメータ */
    private Object[] params;
    
    /**
     * コンストラクタ
     * 
     * @param targetClass targetClass
     * @param targetMethod targetMethod
     * @param targetPackageName targetPackageName
     * @param classSuffix classSuffix
     */
    public InvokeTarget(Class<?> targetClass, Method targetMethod, String targetPackageName, String classSuffix, Map<String, String> pathValue) {
        super();
        this.targetClass = targetClass;
        this.targetMethod = targetMethod;
        // 実行するオブジェクトのインスタンスをDIコンテナから取得
        // DIで、シングルトンなどの管理を行う
        this.target = Container.getComp(targetClass);
        this.targetPackageName = targetPackageName;
        String packageName = targetClass.getPackage().getName();
        if (!packageName.equals(targetPackageName) && packageName.length() > targetPackageName.length()) {
            subPackageName = packageName.substring(targetPackageName.length() + 1);
        }
        this.classSuffix = classSuffix;
        this.pathValue = pathValue;
    }
    /**
     * マルチスレッド時に同じInvokeTargetを実行するとスレッドセーフで無いので、コピーインスタンスを生成する
     * 
     * @return copy instance
     */
    public InvokeTarget copy() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>(pathValue);
        InvokeTarget copy = new InvokeTarget(targetClass, targetMethod, targetPackageName, classSuffix, map);
        for (String role : roles) {
            copy.addRole(role);
        }
        return copy;
    }
    
    /**
     * Get Target Class
     * @return targetClass
     */
    public Class<?> getTargetClass() {
        return targetClass;
    }

    /**
     * Get Target Method
     * @return targetMethod
     */
    public Method getTargetMethod() {
        return targetMethod;
    }
    /**
     * Get Target
     * @return target
     */
    public Object getTarget() {
        return target;
    }
    /**
     * Get Target Package Name
     * @return targetPackageName
     */
    public String getTargetPackageName() {
        return targetPackageName;
    }
    /**
     * Get Sub Package Name
     * @return subPackageName
     */
    public String getSubPackageName() {
        return subPackageName;
    }
    /**
     * Get Class Suffix
     * @return classSuffix
     */
    public String getClassSuffix() {
        return classSuffix;
    }

    /**
     * 処理を実行
     * @return 実行結果
     */
    public Object invoke() {
        // 処理を実施
        return ObjectUtils.invoke(target, targetMethod, params);
    }

    /**
     * @return the pathValue
     */
    public Map<String, String> getPathValue() {
        return pathValue;
    }
    /**
     * @param params the params to set
     */
    public void setParams(Object[] params) {
        this.params = params;
    }
    public List<String> getRoles() {
        return roles;
    }
    public void addRole(String role) {
        this.roles.add(role);
    }

}
