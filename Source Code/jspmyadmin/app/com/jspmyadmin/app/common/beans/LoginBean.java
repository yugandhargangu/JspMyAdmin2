/**
 * 
 */
package com.jspmyadmin.app.common.beans;

import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/05
 *
 */
public class LoginBean extends Bean {

	private static final long serialVersionUID = 1L;

	private String hostname = "localhost";
	private String portnumber = "3306";
	private String username = "root";
	private String password = "";

	/**
	 * @return the hostname
	 */
	public String getHostname() {
		return hostname;
	}

	/**
	 * @param hostname
	 *            the hostname to set
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	/**
	 * @return the portnumber
	 */
	public String getPortnumber() {
		return portnumber;
	}

	/**
	 * @param portnumber
	 *            the portnumber to set
	 */
	public void setPortnumber(String portnumber) {
		this.portnumber = portnumber;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
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

}
