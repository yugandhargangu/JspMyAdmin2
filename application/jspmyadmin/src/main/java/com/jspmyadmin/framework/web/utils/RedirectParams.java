/**
 * 
 */
package com.jspmyadmin.framework.web.utils;

import java.util.Map;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/08/29
 *
 */
public interface RedirectParams {
	/**
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, Object value);

	/**
	 * 
	 * @param key
	 * @return
	 */
	public Object get(String key);

	/**
	 * 
	 * @param key
	 * @return
	 */
	public boolean has(String key);
	
	/**
	 * 
	 * @return
	 */
	public Map<String, Object> getAsMap();
	
	/**
	 * 
	 * @return
	 */
	boolean isEmpty() ;
}
