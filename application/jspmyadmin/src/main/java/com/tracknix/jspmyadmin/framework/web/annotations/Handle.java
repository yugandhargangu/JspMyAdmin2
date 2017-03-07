package com.tracknix.jspmyadmin.framework.web.annotations;

import com.tracknix.jspmyadmin.framework.web.utils.MethodType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Yugandhar Gangu
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface Handle {
    /**
     * @return string
     */
    String path();

    /**
     * @return MethodType
     */
    MethodType methodType() default MethodType.ANY;
}
