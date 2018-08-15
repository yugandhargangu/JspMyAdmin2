package com.tracknix.jspmyadmin.framework.web.annotations;

import com.tracknix.jspmyadmin.framework.web.utils.RequestLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Yugandhar Gangu
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface WebController {

    /**
     * @return boolean
     */
    boolean authentication() default true;

    /**
     * @return RequestLevel
     */
    RequestLevel requestLevel();
}
