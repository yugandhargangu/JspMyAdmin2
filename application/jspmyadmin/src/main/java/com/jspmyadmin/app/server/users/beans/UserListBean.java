/**
 * 
 */
package com.jspmyadmin.app.server.users.beans;

import java.util.List;

import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/14
 *
 */
public class UserListBean extends Bean {

	private static final long serialVersionUID = 1L;

	private String error = null;
	private List<UserInfo> user_info_list = null;

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error
	 *            the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * @return the user_info_list
	 */
	public List<UserInfo> getUser_info_list() {
		return user_info_list;
	}

	/**
	 * @param user_info_list
	 *            the user_info_list to set
	 */
	public void setUser_info_list(List<UserInfo> user_info_list) {
		this.user_info_list = user_info_list;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
