package org.support.project.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface Auth {
    /**
     * アクセス可能なロール
     * 
     * @return roles
     */
    String[] roles() default {};

}
