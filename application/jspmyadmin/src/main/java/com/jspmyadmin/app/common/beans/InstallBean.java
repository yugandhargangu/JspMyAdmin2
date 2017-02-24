/**
 * 
 */
package com.jspmyadmin.app.common.beans;

import java.util.LinkedHashMap;
import java.util.Map;

import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/09/05
 *
 */
public class InstallBean extends Bean {

	private static final long serialVersionUID = 3377912732082651254L;

	private String config_type = null;
	private String admin_name = null;
	private String admin_password = null;
	private String config_host = null;
	private String config_port = null;
	private String config_username = null;
	private String config_password = null;

	private Map<String, String> language_map = new LinkedHashMap<String, String>(Constants.Utils.LANGUAGE_MAP);

	/**
	 * @return the config_type
	 */
	public String getConfig_type() {
		return config_type;
	}

	/**
	 * @param config_type
	 *            the config_type to set
	 */
	public void setConfig_type(String config_type) {
		this.config_type = config_type;
	}

	/**
	 * @return the admin_name
	 */
	public String getAdmin_name() {
		return admin_name;
	}

	/**
	 * @param admin_name
	 *            the admin_name to set
	 */
	public void setAdmin_name(String admin_name) {
		this.admin_name = admin_name;
	}

	/**
	 * @return the admin_password
	 */
	public String getAdmin_password() {
		return admin_password;
	}

	/**
	 * @param admin_password
	 *            the admin_password to set
	 */
	public void setAdmin_password(String admin_password) {
		this.admin_password = admin_password;
	}

	/**
	 * @return the config_host
	 */
	public String getConfig_host() {
		return config_host;
	}

	/**
	 * @param config_host
	 *            the config_host to set
	 */
	public void setConfig_host(String config_host) {
		this.config_host = config_host;
	}

	/**
	 * @return the config_port
	 */
	public String getConfig_port() {
		return config_port;
	}

	/**
	 * @param config_port
	 *            the config_port to set
	 */
	public void setConfig_port(String config_port) {
		this.config_port = config_port;
	}

	/**
	 * @return the config_username
	 */
	public String getConfig_username() {
		return config_username;
	}

	/**
	 * @param config_username
	 *            the config_username to set
	 */
	public void setConfig_username(String config_username) {
		this.config_username = config_username;
	}

	/**
	 * @return the config_password
	 */
	public String getConfig_password() {
		return config_password;
	}

	/**
	 * @param config_password
	 *            the config_password to set
	 */
	public void setConfig_password(String config_password) {
		this.config_password = config_password;
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
