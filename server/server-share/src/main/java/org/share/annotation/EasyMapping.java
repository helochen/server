package org.share.annotation;


import org.share.command.FlagType;

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

    /**
     * 对应的命令，没得说
     * @return
     */
    String command();

    /**
     * 是否放弃
     * */
    boolean deprecated() default false;

    /**
     * 是否检测是否包含了必要信息
     * @return
     */
    byte check() default FlagType.LOGIN_SUCCESS;
}

