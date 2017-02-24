package com.tracknix.jspmyadmin.framework.web.annotations;

import com.tracknix.jspmyadmin.framework.web.utils.ContentType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Yugandhar Gangu
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface Rest {

    /**
     * @return ContentType
     */
    ContentType contentType() default ContentType.TEXT_PLAIN;
}
