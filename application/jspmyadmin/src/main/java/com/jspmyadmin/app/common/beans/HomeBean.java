/**
 * 
 */
package com.jspmyadmin.app.common.beans;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.jspmyadmin.app.common.logic.HomeLogic;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.web.utils.Bean;
import com.jspmyadmin.framework.web.utils.RequestAdaptor;

/**
 * @author Yugandhar Gangu
 * @version 1.0
 * @created_at 2016/02/03
 *
 */
public class HomeBean extends Bean {

	private static final long serialVersionUID = 1L;
	private static final String _ADDON = " %";

	private String collation = null;
	private String language = Constants.DEFAULT_LOCALE;
	private String fontsize = "100";

	private String db_server_name = null;
	private String db_server_type = null;
	private String db_server_version = null;
	private String db_server_protocol = null;
	private String db_server_user = null;
	private String db_server_charset = null;

	private String web_server_name = null;
	private String jdbc_version = null;
	private String java_version = null;
	private String servelt_version = null;
	private String jsp_version = null;

	private String jma_version = "1.0.4";

	private String action = null;

	private Map<String, List<String>> collation_map = null;
	private Map<String, String> language_map = new LinkedHashMap<String, String>(Constants.Utils.LANGUAGE_MAP);
	private Map<String, String> fontsize_map = null;

	public HomeBean() {
		HomeLogic homeLogic = null;
		try {
			homeLogic = new HomeLogic();
			collation_map = homeLogic.getCollationMap();
		} catch (Exception e) {

		} finally {
			homeLogic = null;
		}
		fontsize_map = new LinkedHashMap<String, String>();
		for (int i = 30; i <= 200; i++) {
			fontsize_map.put(Integer.toString(i), i + _ADDON);
		}

		HttpSession httpSession = RequestAdaptor.REQUEST_MAP.get(Thread.currentThread().getId()).getSession();
		Object temp = httpSession.getAttribute(Constants.SESSION_FONTSIZE);
		if (temp != null) {
			fontsize = temp.toString();
		}
	}

	/**
	 * @return the collation
	 */
	public String getCollation() {
		return collation;
	}

	/**
	 * @param collation
	 *            the collation to set
	 */
	public void setCollation(String collation) {
		this.collation = collation;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the fontsize
	 */
	public String getFontsize() {
		return fontsize;
	}

	/**
	 * @param fontsize
	 *            the fontsize to set
	 */
	public void setFontsize(String fontsize) {
		this.fontsize = fontsize;
	}

	/**
	 * @return the db_server_name
	 */
	public String getDb_server_name() {
		return db_server_name;
	}

	/**
	 * @param db_server_name
	 *            the db_server_name to set
	 */
	public void setDb_server_name(String db_server_name) {
		this.db_server_name = db_server_name;
	}

	/**
	 * @return the db_server_type
	 */
	public String getDb_server_type() {
		return db_server_type;
	}

	/**
	 * @param db_server_type
	 *            the db_server_type to set
	 */
	public void setDb_server_type(String db_server_type) {
		this.db_server_type = db_server_type;
	}

	/**
	 * @return the db_server_version
	 */
	public String getDb_server_version() {
		return db_server_version;
	}

	/**
	 * @param db_server_version
	 *            the db_server_version to set
	 */
	public void setDb_server_version(String db_server_version) {
		this.db_server_version = db_server_version;
	}

	/**
	 * @return the db_server_protocol
	 */
	public String getDb_server_protocol() {
		return db_server_protocol;
	}

	/**
	 * @param db_server_protocol
	 *            the db_server_protocol to set
	 */
	public void setDb_server_protocol(String db_server_protocol) {
		this.db_server_protocol = db_server_protocol;
	}

	/**
	 * @return the db_server_user
	 */
	public String getDb_server_user() {
		return db_server_user;
	}

	/**
	 * @param db_server_user
	 *            the db_server_user to set
	 */
	public void setDb_server_user(String db_server_user) {
		this.db_server_user = db_server_user;
	}

	/**
	 * @return the db_server_charset
	 */
	public String getDb_server_charset() {
		return db_server_charset;
	}

	/**
	 * @param db_server_charset
	 *            the db_server_charset to set
	 */
	public void setDb_server_charset(String db_server_charset) {
		this.db_server_charset = db_server_charset;
	}

	/**
	 * @return the web_server_name
	 */
	public String getWeb_server_name() {
		return web_server_name;
	}

	/**
	 * @param web_server_name
	 *            the web_server_name to set
	 */
	public void setWeb_server_name(String web_server_name) {
		this.web_server_name = web_server_name;
	}

	/**
	 * @return the jdbc_version
	 */
	public String getJdbc_version() {
		return jdbc_version;
	}

	/**
	 * @param jdbc_version
	 *            the jdbc_version to set
	 */
	public void setJdbc_version(String jdbc_version) {
		this.jdbc_version = jdbc_version;
	}

	/**
	 * @return the java_version
	 */
	public String getJava_version() {
		return java_version;
	}

	/**
	 * @param java_version
	 *            the java_version to set
	 */
	public void setJava_version(String java_version) {
		this.java_version = java_version;
	}

	/**
	 * @return the servelt_version
	 */
	public String getServelt_version() {
		return servelt_version;
	}

	/**
	 * @param servelt_version
	 *            the servelt_version to set
	 */
	public void setServelt_version(String servelt_version) {
		this.servelt_version = servelt_version;
	}

	/**
	 * @return the jsp_version
	 */
	public String getJsp_version() {
		return jsp_version;
	}

	/**
	 * @param jsp_version
	 *            the jsp_version to set
	 */
	public void setJsp_version(String jsp_version) {
		this.jsp_version = jsp_version;
	}

	/**
	 * @return the jma_version
	 */
	public String getJma_version() {
		return jma_version;
	}

	/**
	 * @param jma_version
	 *            the jma_version to set
	 */
	public void setJma_version(String jma_version) {
		this.jma_version = jma_version;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the collation_map
	 */
	public Map<String, List<String>> getCollation_map() {
		return collation_map;
	}

	/**
	 * @param collation_map
	 *            the collation_map to set
	 */
	public void setCollation_map(Map<String, List<String>> collation_map) {
		this.collation_map = collation_map;
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
	 * @return the fontsize_map
	 */
	public Map<String, String> getFontsize_map() {
		return fontsize_map;
	}

	/**
	 * @param fontsize_map
	 *            the fontsize_map to set
	 */
	public void setFontsize_map(Map<String, String> fontsize_map) {
		this.fontsize_map = fontsize_map;
	}

}
