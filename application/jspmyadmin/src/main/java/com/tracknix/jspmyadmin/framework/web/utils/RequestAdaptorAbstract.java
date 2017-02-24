package com.tracknix.jspmyadmin.framework.web.utils;

import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.JsonNode;
import com.tracknix.jspmyadmin.framework.exception.EncodingException;

/**
 * @author Yugandhar Gangu
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
	abstract void setJsonToken(JsonNode jsonToken);

	/**
	 * @param bean
	 *            Bean instance
	 * @param requestLevel
	 *            requestLevel
	 */
	abstract void fillRequestBean(Bean bean, RequestLevel requestLevel);

	/**
	 * @param bean
	 *            Bean instance
	 */
	abstract void fillRequestToken(Bean bean);

	/**
	 * @param bean
	 *            Bean instance
	 * @return token
	 */
	abstract String createRequestToken(Bean bean);

	/**
	 * @param bean
	 *            Bean instance
	 * @param token
	 *            token
	 * @return token
	 */
	abstract String createRequestToken(Bean bean, String token);

	/**
	 * @param token
	 *            token
	 * @return boolean
	 */
	abstract boolean isValidToken(String token);

	/**
	 * @param bean
	 *            Bean instance
	 */
	abstract void fillBasics(Bean bean);

	/**
	 * @param bean
	 *            Bean instance
	 * @param requestLevel
	 *            requestLevel
	 */
	abstract boolean canProceed(Bean bean, RequestLevel requestLevel);

	/**
	 * @return token
	 * @throws EncodingException
	 *             e
	 */
	public abstract String generateToken() throws EncodingException;

}
