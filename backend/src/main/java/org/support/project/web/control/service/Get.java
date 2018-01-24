package org.support.project.web.control.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Get {
    String path() default "";
    String subscribeToken() default "";
    String publishToken() default "csrf";
    boolean checkReferer() default false;
    boolean checkCookieToken() default false;
    boolean checkReqToken() default false;
    boolean checkHeaderToken() default false;
}
