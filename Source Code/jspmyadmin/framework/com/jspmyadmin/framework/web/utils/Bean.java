/**
 * 
 */
package com.jspmyadmin.framework.web.utils;

import java.io.Serializable;

import com.jspmyadmin.framework.constants.FrameworkConstants;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/01/29
 *
 */
public abstract class Bean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String token = null;
	private String err_key = null;
	private String msg_key = null;
	private String err = null;
	private String msg = null;

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

	/**
	 * @return the err_key
	 */
	public String getErr_key() {
		return err_key;
	}

	/**
	 * @param err_key
	 *            the err_key to set
	 */
	public void setErr_key(String err_key) {
		this.err_key = err_key;
	}

	/**
	 * @return the msg_key
	 */
	public String getMsg_key() {
		return msg_key;
	}

	/**
	 * @param msg_key
	 *            the msg_key to set
	 */
	public void setMsg_key(String msg_key) {
		this.msg_key = msg_key;
	}

	/**
	 * @return the err
	 */
	public String getErr() {
		return err;
	}

	/**
	 * @param err
	 *            the err to set
	 */
	public void setErr(String err) {
		this.err = err;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 
	 * @param val
	 * @return
	 */
	protected boolean isEmpty(String val) {
		if (val == null || FrameworkConstants.BLANK.equals(val.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 */
	protected void init() {
		// do nothing
	}
}
