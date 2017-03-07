package com.tracknix.jspmyadmin.framework.web.utils;

import java.util.Map;

/**
 * @author Yugandhar Gangu
 */
public interface RedirectParams {
    /**
     * @param key   string
     * @param value {@link Object}
     */
    public void put(String key, Object value);

    /**
     * @param key {@link String}
     * @return Object
     */
    public Object get(String key);

    /**
     * @param key {@link String}
     * @return Object
     */
    public boolean has(String key);

    /**
     * @return Map
     */
    public Map<String, Object> getAsMap();

    /**
     * @return boolean
     */
    boolean isEmpty();
}
