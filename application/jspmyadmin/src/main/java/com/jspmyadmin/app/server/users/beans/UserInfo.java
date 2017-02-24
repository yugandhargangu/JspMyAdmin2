/**
 * 
 */
package com.jspmyadmin.app.server.users.beans;

import java.io.Serializable;
import java.util.List;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/14
 *
 */
public class UserInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String user = null;
	private String token = null;
	private String max_questions = null;
	private String max_updates = null;
	private String max_connections = null;
	private String max_user_connections = null;
	private String plugin = null;
	private List<String> grant_list = null;

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
	 * @return the max_questions
	 */
	public String getMax_questions() {
		return max_questions;
	}

	/**
	 * @param max_questions
	 *            the max_questions to set
	 */
	public void setMax_questions(String max_questions) {
		this.max_questions = max_questions;
	}

	/**
	 * @return the max_updates
	 */
	public String getMax_updates() {
		return max_updates;
	}

	/**
	 * @param max_updates
	 *            the max_updates to set
	 */
	public void setMax_updates(String max_updates) {
		this.max_updates = max_updates;
	}

	/**
	 * @return the max_connections
	 */
	public String getMax_connections() {
		return max_connections;
	}

	/**
	 * @param max_connections
	 *            the max_connections to set
	 */
	public void setMax_connections(String max_connections) {
		this.max_connections = max_connections;
	}

	/**
	 * @return the max_user_connections
	 */
	public String getMax_user_connections() {
		return max_user_connections;
	}

	/**
	 * @param max_user_connections
	 *            the max_user_connections to set
	 */
	public void setMax_user_connections(String max_user_connections) {
		this.max_user_connections = max_user_connections;
	}

	/**
	 * @return the plugin
	 */
	public String getPlugin() {
		return plugin;
	}

	/**
	 * @param plugin
	 *            the plugin to set
	 */
	public void setPlugin(String plugin) {
		this.plugin = plugin;
	}

	/**
	 * @return the grant_list
	 */
	public List<String> getGrant_list() {
		return grant_list;
	}

	/**
	 * @param grant_list
	 *            the grant_list to set
	 */
	public void setGrant_list(List<String> grant_list) {
		this.grant_list = grant_list;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
