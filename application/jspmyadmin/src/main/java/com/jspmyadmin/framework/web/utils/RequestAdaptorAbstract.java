/**
 * 
 */
package com.jspmyadmin.framework.web.utils;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.jspmyadmin.framework.exception.EncodingException;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/08/30
 *
 */
abstract class RequestAdaptorAbstract implements RequestAdaptor {

	/**
	 * @param session
	 *            the session to set
	 */
	abstract void setSession(HttpSession session);

	/**
	 * @param redirectParams
	 *            the redirectParams to set
	 */
	abstract void setRedirectParams(RedirectParams redirectParams);

	/**
	 * @param jsonToken
	 *            the jsonToken to set
	 */
	abstract void setJsonToken(JSONObject jsonToken);

	/**
	 * 
	 * @param bean
	 * @param request
	 * @param encDecLogic
	 */
	abstract void fillRequestBean(Bean bean, RequestLevel requestLevel);

	/**
	 * 
	 * @param bean
	 * @param encDecLogic
	 */
	abstract void fillRequestToken(Bean bean);

	/**
	 * 
	 * @param bean
	 * @param encDecLogic
	 * @return
	 */
	abstract String createRequestToken(Bean bean);

	/**
	 * 
	 * @param bean
	 * @param token
	 * @param encDecLogic
	 */
	abstract String createRequestToken(Bean bean, String token);

	/**
	 * 
	 * @param token
	 * @return
	 */
	abstract boolean isValidToken(String token);

	/**
	 * 
	 * @param bean
	 */
	abstract void fillBasics(Bean bean);

	/**
	 * 
	 * @param bean
	 * @param requestLevel
	 */
	abstract boolean canProceed(Bean bean, RequestLevel requestLevel);

	/**
	 * 
	 * @return
	 * @throws EncodingException
	 */
	public abstract String generateToken() throws EncodingException;

}
