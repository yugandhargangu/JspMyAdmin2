/**
 * 
 */
package com.jspmyadmin.framework.web.utils;

import java.io.Serializable;

import com.jspmyadmin.framework.constants.Constants;

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

	private String request_db = null;
	private String request_table = null;
	private String request_view = null;
	private String request_token = null;

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
	 * @return the request_db
	 */
	public String getRequest_db() {
		return request_db;
	}

	/**
	 * @param request_db
	 *            the request_db to set
	 */
	public void setRequest_db(String request_db) {
		this.request_db = request_db;
	}

	/**
	 * @return the request_table
	 */
	public String getRequest_table() {
		return request_table;
	}

	/**
	 * @param request_table
	 *            the request_table to set
	 */
	public void setRequest_table(String request_table) {
		this.request_table = request_table;
	}

	/**
	 * @return the request_view
	 */
	public String getRequest_view() {
		return request_view;
	}

	/**
	 * @param request_view
	 *            the request_view to set
	 */
	public void setRequest_view(String request_view) {
		this.request_view = request_view;
	}

	/**
	 * @return the request_token
	 */
	public String getRequest_token() {
		return request_token;
	}

	/**
	 * @param request_token
	 *            the request_token to set
	 */
	public void setRequest_token(String request_token) {
		this.request_token = request_token;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 
	 * @param val
	 * @return
	 */
	protected boolean isEmpty(String val) {
		if (val == null || Constants.BLANK.equals(val.trim())) {
			return true;
		}
		return false;
	}

}
