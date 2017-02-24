/**
 * 
 */
package com.jspmyadmin.framework.web.utils;

import java.io.File;
import java.io.IOException;

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
	public void addAttribute(String key, Object value);

	/**
	 * 
	 * @param file
	 * @param deleteAfterDownload
	 * @param filename
	 * @throws Exception
	 */
	public void handleDownload(File file, boolean deleteAfterDownload, String filename) throws IOException;

	/**
	 * 
	 * @param view
	 */
	public void handleDefault();
}
