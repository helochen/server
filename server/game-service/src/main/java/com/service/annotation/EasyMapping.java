package com.service.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 方法的返回值一定要是org.share.msg.Message对象或者void
 *
 * @return Message ||
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EasyMapping {

    String command();

    boolean deprecated() default false;

}

