package org.support.project.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * AOP(メソッドへ)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Aspect {

    /**
     * 実行するアドバイス
     * 
     * @return Advice
     */
    Class<? extends Advice> advice();

}
