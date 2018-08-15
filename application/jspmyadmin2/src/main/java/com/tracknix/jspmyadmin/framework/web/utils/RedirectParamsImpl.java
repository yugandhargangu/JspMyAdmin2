package com.tracknix.jspmyadmin.framework.web.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yugandhar Gangu
 */
class RedirectParamsImpl implements RedirectParams {

    private Map<String, Object> _paramsMap;

    public void put(String key, Object value) {
        if (_paramsMap == null) {
            _paramsMap = new HashMap<String, Object>();
        }
        _paramsMap.put(key, value);
    }

    public Object get(String key) {
        if (_paramsMap != null && _paramsMap.containsKey(key)) {
            return _paramsMap.get(key);
        }
        return null;
    }

    public Map<String, Object> getAsMap() {
        return _paramsMap;
    }

    public boolean isEmpty() {
        return _paramsMap == null || _paramsMap.isEmpty();
    }

    public boolean has(String key) {
        return _paramsMap != null && _paramsMap.containsKey(key);
    }
}
