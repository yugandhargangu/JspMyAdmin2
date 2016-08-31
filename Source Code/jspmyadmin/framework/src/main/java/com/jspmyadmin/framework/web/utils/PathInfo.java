/**
 * 
 */
package com.jspmyadmin.framework.web.utils;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/01/29
 *
 */
class PathInfo {

	private Class<?> controller = null;
	private Class<?> bean = null;
	private RequestLevel requestLevel = RequestLevel.DEFAULT;
	private boolean isAuthRequired = false;
	private boolean isGetMultiPart = false;
	private boolean isGetDownload = false;
	private boolean isPostMultiPart = false;
	private boolean isPostDownload = false;
	private boolean isGetValidateToken = false;
	private boolean isPostValidateToken = false;
	private boolean isGetResponseBody = false;
	private boolean isPostResponseBody = false;

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
	 * @return the bean
	 */
	public Class<?> getBean() {
		return bean;
	}

	/**
	 * @param bean
	 *            the bean to set
	 */
	public void setBean(Class<?> bean) {
		this.bean = bean;
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
	 * @return the isGetMultiPart
	 */
	public boolean isGetMultiPart() {
		return isGetMultiPart;
	}

	/**
	 * @param isGetMultiPart
	 *            the isGetMultiPart to set
	 */
	public void setGetMultiPart(boolean isGetMultiPart) {
		this.isGetMultiPart = isGetMultiPart;
	}

	/**
	 * @return the isGetDownload
	 */
	public boolean isGetDownload() {
		return isGetDownload;
	}

	/**
	 * @param isGetDownload
	 *            the isGetDownload to set
	 */
	public void setGetDownload(boolean isGetDownload) {
		this.isGetDownload = isGetDownload;
	}

	/**
	 * @return the isPostMultiPart
	 */
	public boolean isPostMultiPart() {
		return isPostMultiPart;
	}

	/**
	 * @param isPostMultiPart
	 *            the isPostMultiPart to set
	 */
	public void setPostMultiPart(boolean isPostMultiPart) {
		this.isPostMultiPart = isPostMultiPart;
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
	 * @return the isGetValidateToken
	 */
	public boolean isGetValidateToken() {
		return isGetValidateToken;
	}

	/**
	 * @param isGetValidateToken
	 *            the isGetValidateToken to set
	 */
	public void setGetValidateToken(boolean isGetValidateToken) {
		this.isGetValidateToken = isGetValidateToken;
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
	 * @return the isGetResponseBody
	 */
	public boolean isGetResponseBody() {
		return isGetResponseBody;
	}

	/**
	 * @param isGetResponseBody
	 *            the isGetResponseBody to set
	 */
	public void setGetResponseBody(boolean isGetResponseBody) {
		this.isGetResponseBody = isGetResponseBody;
	}

	/**
	 * @return the isPostResponseBody
	 */
	public boolean isPostResponseBody() {
		return isPostResponseBody;
	}

	/**
	 * @param isPostResponseBody
	 *            the isPostResponseBody to set
	 */
	public void setPostResponseBody(boolean isPostResponseBody) {
		this.isPostResponseBody = isPostResponseBody;
	}

}
