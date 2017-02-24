package com.tracknix.jspmyadmin.framework.web.utils;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author Yugandhar Gangu
 */
@Data
class PathInfo {

    private Class<?> controller = null;
    private boolean isAuthRequired = false;
    private RequestLevel requestLevel = RequestLevel.DEFAULT;
    private Method method = null;
    private MethodType methodType = MethodType.ANY;
    private boolean isRest = false;
    private ContentType contentType = null;
    private boolean isDownload = false;
    private boolean isValidateToken = false;
    private boolean isGenerateToken = false;
    private Param[] parameters = null;

    /**
     * @author Yugandhar Gangu
     */
    @Data
    static class Param {
        private final Class<?> klass;
        private final DetectType detectType;
        private final String name;

        /**
         * Constructor
         *
         * @param klass      {@link Class}
         * @param detectType {@link DetectType}
         * @param name       {@link String}
         */
        Param(Class<?> klass, DetectType detectType, String name) {
            this.klass = klass;
            this.detectType = detectType;
            this.name = name;
        }
    }
}