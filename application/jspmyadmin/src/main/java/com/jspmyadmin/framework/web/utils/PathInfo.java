/**
 * 
 */
package com.jspmyadmin.framework.web.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/01/29
 *
 */
class PathInfo {

	private Class<?> controller = null;
	private RequestLevel requestLevel = RequestLevel.DEFAULT;
	private Method anyMethod = null;
	private Method getMethod = null;
	private Method postMethod = null;
	private boolean isAuthRequired = false;
	private boolean isResponseBody = false;
	private boolean isDownload = false;
	private boolean isValidateToken = false;
	private boolean isPostDownload = false;
	private boolean isPostValidateToken = false;
	private Field model = null;
	private Map<Field, DetectType> detectMap = null;

	/**
	 * @return the controller
	 */
	public Class<?> getController() {
		return controller;
	}

	/**
	 * @param controller
	 *            the controller to set
	 */
	public void setController(Class<?> controller) {
		this.controller = controller;
	}

	/**
	 * @return the requestLevel
	 */
	public RequestLevel getRequestLevel() {
		return requestLevel;
	}

	/**
	 * @param requestLevel
	 *            the requestLevel to set
	 */
	public void setRequestLevel(RequestLevel requestLevel) {
		this.requestLevel = requestLevel;
	}

	/**
	 * @return the anyMethod
	 */
	public Method getAnyMethod() {
		return anyMethod;
	}

	/**
	 * @param anyMethod
	 *            the anyMethod to set
	 */
	public void setAnyMethod(Method anyMethod) {
		this.anyMethod = anyMethod;
	}

	/**
	 * @return the getMethod
	 */
	public Method getGetMethod() {
		return getMethod;
	}

	/**
	 * @param getMethod
	 *            the getMethod to set
	 */
	public void setGetMethod(Method getMethod) {
		this.getMethod = getMethod;
	}

	/**
	 * @return the postMethod
	 */
	public Method getPostMethod() {
		return postMethod;
	}

	/**
	 * @param postMethod
	 *            the postMethod to set
	 */
	public void setPostMethod(Method postMethod) {
		this.postMethod = postMethod;
	}

	/**
	 * @return the isAuthRequired
	 */
	public boolean isAuthRequired() {
		return isAuthRequired;
	}

	/**
	 * @param isAuthRequired
	 *            the isAuthRequired to set
	 */
	public void setAuthRequired(boolean isAuthRequired) {
		this.isAuthRequired = isAuthRequired;
	}

	/**
	 * @return the isResponseBody
	 */
	public boolean isResponseBody() {
		return isResponseBody;
	}

	/**
	 * @param isResponseBody
	 *            the isResponseBody to set
	 */
	public void setResponseBody(boolean isResponseBody) {
		this.isResponseBody = isResponseBody;
	}

	/**
	 * @return the isDownload
	 */
	public boolean isDownload() {
		return isDownload;
	}

	/**
	 * @param isDownload
	 *            the isDownload to set
	 */
	public void setDownload(boolean isDownload) {
		this.isDownload = isDownload;
	}

	/**
	 * @return the isValidateToken
	 */
	public boolean isValidateToken() {
		return isValidateToken;
	}

	/**
	 * @param isValidateToken
	 *            the isValidateToken to set
	 */
	public void setValidateToken(boolean isValidateToken) {
		this.isValidateToken = isValidateToken;
	}

	/**
	 * @return the isPostDownload
	 */
	public boolean isPostDownload() {
		return isPostDownload;
	}

	/**
	 * @param isPostDownload
	 *            the isPostDownload to set
	 */
	public void setPostDownload(boolean isPostDownload) {
		this.isPostDownload = isPostDownload;
	}

	/**
	 * @return the isPostValidateToken
	 */
	public boolean isPostValidateToken() {
		return isPostValidateToken;
	}

	/**
	 * @param isPostValidateToken
	 *            the isPostValidateToken to set
	 */
	public void setPostValidateToken(boolean isPostValidateToken) {
		this.isPostValidateToken = isPostValidateToken;
	}

	/**
	 * @return the model
	 */
	public Field getModel() {
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(Field model) {
		this.model = model;
	}

	/**
	 * @return the detectMap
	 */
	public Map<Field, DetectType> getDetectMap() {
		return detectMap;
	}

	/**
	 * @param detectMap
	 *            the detectMap to set
	 */
	public void setDetectMap(Map<Field, DetectType> detectMap) {
		this.detectMap = detectMap;
	}

}
