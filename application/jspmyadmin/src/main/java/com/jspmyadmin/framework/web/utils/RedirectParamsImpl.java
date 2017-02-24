/**
 * 
 */
package com.jspmyadmin.framework.web.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/08/29
 *
 */
class RedirectParamsImpl implements RedirectParams {

	private Map<String, Object> _paramsMap;

	/**
	 * 
	 */
	public void put(String key, Object value) {
		if (_paramsMap == null) {
			_paramsMap = new HashMap<String, Object>();
		}
		_paramsMap.put(key, value);
	}

	/**
	 * 
	 */
	public Object get(String key) {
		if (_paramsMap != null && _paramsMap.containsKey(key)) {
			return _paramsMap.get(key);
		}
		return null;
	}

	/**
	 * 
	 */
	public Map<String, Object> getAsMap() {
		return _paramsMap;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		if (_paramsMap == null || _paramsMap.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 */
	public boolean has(String key) {
		if (_paramsMap != null && _paramsMap.containsKey(key)) {
			return true;
		}
		return false;
	}
}
