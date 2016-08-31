/**
 * 
 */
package com.jspmyadmin.app.common.beans;

import java.util.LinkedHashMap;
import java.util.Map;

import com.jspmyadmin.framework.connection.ConnectionType;
import com.jspmyadmin.framework.connection.ConnectionTypeCheck;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.utils.Bean;
import com.jspmyadmin.framework.web.utils.DefaultServlet;
import com.jspmyadmin.framework.web.utils.DefaultServlet.Config;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/05
 *
 */
public class LoginBean extends Bean {

	private static final long serialVersionUID = 1L;

	private String halfconfig = null;
	private String hostname = "localhost";
	private String portnumber = "3306";
	private String username = "root";
	private String password = "";

	private Map<String, String> language_map = new LinkedHashMap<String, String>(FrameworkConstants.Utils.LANGUAGE_MAP);

	public LoginBean() {
		if (ConnectionType.HALF_CONFIG.equals(ConnectionTypeCheck.check())) {
			Config config = (Config) DefaultServlet.getContext().getAttribute("config");
			hostname = config.getHost();
			portnumber = config.getPort();
			halfconfig = FrameworkConstants.YES;
		}
	}

	/**
	 * @return the halfconfig
	 */
	public String getHalfconfig() {
		return halfconfig;
	}

	/**
	 * @param halfconfig
	 *            the halfconfig to set
	 */
	public void setHalfconfig(String halfconfig) {
		this.halfconfig = halfconfig;
	}

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

	/**
	 * @return the language_map
	 */
	public Map<String, String> getLanguage_map() {
		return language_map;
	}

	/**
	 * @param language_map
	 *            the language_map to set
	 */
	public void setLanguage_map(Map<String, String> language_map) {
		this.language_map = language_map;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
