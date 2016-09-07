/**
 * 
 */
package com.jspmyadmin.framework.web.utils;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import javax.servlet.http.HttpServletRequest;

import com.jspmyadmin.framework.exception.EncodingException;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/08/30
 *
 */
public interface RequestAdaptor {

	public static final Map<Long, HttpServletRequest> REQUEST_MAP = Collections
			.synchronizedMap(new WeakHashMap<Long, HttpServletRequest>());

	/**
	 * 
	 * @return
	 * @throws EncodingException
	 */
	public abstract String generateToken() throws EncodingException;

}
