/**
 * 
 */
package com.jspmyadmin.framework.web.utils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/12
 *
 */
public interface FileInput {

	/**
	 * 
	 * @return
	 */
	String getFileName();

	/**
	 * 
	 * @return
	 */
	long getFileSize();

	/**
	 * 
	 * @param path
	 * @throws IOException
	 */
	void copyTo(String path) throws IOException;
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	InputStream getInputStream() throws IOException;
}
