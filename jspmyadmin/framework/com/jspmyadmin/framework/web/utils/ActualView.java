/**
 * 
 */
package com.jspmyadmin.framework.web.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jspmyadmin.framework.constants.FrameworkConstants;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/01/29
 *
 */
public class ActualView implements View {

	private ViewType type = ViewType.DEFAULT;
	private String path = null;
	private String token = null;
	private final HttpServletRequest _request;

	public ActualView(HttpServletRequest request) {
		_request = request;
	}

	/**
	 * @return the type
	 */
	public ViewType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(ViewType type) {
		this.type = type;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	@SuppressWarnings("unchecked")
	public void addAttribute(String key, Object value) {
		HttpSession httpSession = _request.getSession();
		if (httpSession != null) {
			Object temp = httpSession.getAttribute(FrameworkConstants.SESSION_FLASH_MAP);
			Map<String, Object> flashMap = null;
			if (temp == null) {
				flashMap = new HashMap<String, Object>();
			} else {
				flashMap = (Map<String, Object>) temp;
			}
			flashMap.put(key, value);
		}
	}

}
