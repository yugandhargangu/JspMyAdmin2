/**
 * 
 */
package com.jspmyadmin.app.server.users.beans;

import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/19
 *
 */
public class UserInfoBean extends Bean {

	private static final long serialVersionUID = 1L;

	private String user = null;
	private String old_user = null;
	private String old_host = null;
	private String login_name = "new_user";
	private String host_name = "localhost";
	private String password = null;
	private String password_confirm = null;

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the old_user
	 */
	public String getOld_user() {
		return old_user;
	}

	/**
	 * @param old_user
	 *            the old_user to set
	 */
	public void setOld_user(String old_user) {
		this.old_user = old_user;
	}

	/**
	 * @return the old_host
	 */
	public String getOld_host() {
		return old_host;
	}

	/**
	 * @param old_host
	 *            the old_host to set
	 */
	public void setOld_host(String old_host) {
		this.old_host = old_host;
	}

	/**
	 * @return the login_name
	 */
	public String getLogin_name() {
		return login_name;
	}

	/**
	 * @param login_name
	 *            the login_name to set
	 */
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	/**
	 * @return the host_name
	 */
	public String getHost_name() {
		return host_name;
	}

	/**
	 * @param host_name
	 *            the host_name to set
	 */
	public void setHost_name(String host_name) {
		this.host_name = host_name;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the password_confirm
	 */
	public String getPassword_confirm() {
		return password_confirm;
	}

	/**
	 * @param password_confirm
	 *            the password_confirm to set
	 */
	public void setPassword_confirm(String password_confirm) {
		this.password_confirm = password_confirm;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
