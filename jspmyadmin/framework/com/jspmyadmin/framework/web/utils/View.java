/**
 * 
 */
package com.jspmyadmin.framework.web.utils;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/01/29
 *
 */
public interface View {

	/**
	 * 
	 * @param type
	 */
	public void setType(ViewType type);

	/**
	 * 
	 * @param path
	 */
	public void setPath(String path);

	/**
	 * 
	 * @param token
	 */
	public void setToken(String token);
	
	/**
	 * 
	 * @param key
	 * @param value
	 */
	public void addAttribute(String key,Object value);
}
