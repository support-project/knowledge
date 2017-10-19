package org.support.project.common.classanalysis;

import java.lang.reflect.Method;

import org.support.project.aop.Aspect;
import org.support.project.di.DI;

/**
 * DIを行う為に実施するClassAnalysisの拡張
 * 
 * @author koda
 *
 */
public class ClassAnalysisForDI extends ClassAnalysis {
    /** インターフェースかどうか */
    private boolean interfaceType = false;
    /** "DI"のアノテーションが存在するか */
    private boolean diAnnotationExists = false;
    /** "DI"のアノテーション */
    private DI diAnnotation = null;
    /** "Aspect"のアノテーションが一つでも存在するか */
    private boolean aspectAnnotationExists = false;

    /**
     * Constractor
     * 
     * @param clazz
     *            type
     */
    ClassAnalysisForDI(Class<?> clazz) {
        super(clazz);

        if (clazz.isInterface()) {
            interfaceType = true;
        }

        DI di = clazz.getAnnotation(DI.class);
        if (di == null) {
            diAnnotationExists = true;
            diAnnotation = di;
        }

        Aspect aspect = clazz.getAnnotation(Aspect.class);
        if (aspect != null) {
            aspectAnnotationExists = true;
        }

        if (!aspectAnnotationExists) {
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                aspect = method.getAnnotation(Aspect.class);
                if (aspect != null) {
                    aspectAnnotationExists = true;
                    break;
                }
            }
            if (!aspectAnnotationExists) {
                methods = clazz.getDeclaredMethods();
                for (Method method : methods) {
                    aspect = method.getAnnotation(Aspect.class);
                    if (aspect != null) {
                        aspectAnnotationExists = true;
                        break;
                    }
                }
            }
        }
    }

    /**
     * インターフェースかどうかを取得します。
     * 
     * @return インターフェースかどうか
     */
    public boolean isInterfaceType() {
        return interfaceType;
    }

    /**
     * DIのアノテーションが存在するかを取得します。
     * 
     * @return DIのアノテーションが存在するか
     */
    public boolean isDiAnnotationExists() {
        return diAnnotationExists;
    }

    /**
     * DIのアノテーションを取得します。
     * 
     * @return DIのアノテーション
     */
    public DI getDiAnnotation() {
        return diAnnotation;
    }

    /**
     * Aspectのアノテーションが一つでも存在するかを取得します。
     * 
     * @return Aspectのアノテーションが一つでも存在するか
     */
    public boolean isAspectAnnotationExists() {
        return aspectAnnotationExists;
    }

}
