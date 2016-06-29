/**
 * 
 */
package com.jspmyadmin.app.database.routine.beans;

import java.io.Serializable;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/03
 *
 */
public class RoutineInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name = null;
	private String returns = null;
	private String routine_body = null;
	private String deterministic = null;
	private String data_access = null;
	private String security_type = null;
	private String definer = null;
	private String comments = null;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the returns
	 */
	public String getReturns() {
		return returns;
	}

	/**
	 * @param returns
	 *            the returns to set
	 */
	public void setReturns(String returns) {
		this.returns = returns;
	}

	/**
	 * @return the routine_body
	 */
	public String getRoutine_body() {
		return routine_body;
	}

	/**
	 * @param routine_body
	 *            the routine_body to set
	 */
	public void setRoutine_body(String routine_body) {
		this.routine_body = routine_body;
	}

	/**
	 * @return the deterministic
	 */
	public String getDeterministic() {
		return deterministic;
	}

	/**
	 * @param deterministic
	 *            the deterministic to set
	 */
	public void setDeterministic(String deterministic) {
		this.deterministic = deterministic;
	}

	/**
	 * @return the data_access
	 */
	public String getData_access() {
		return data_access;
	}

	/**
	 * @param data_access
	 *            the data_access to set
	 */
	public void setData_access(String data_access) {
		this.data_access = data_access;
	}

	/**
	 * @return the security_type
	 */
	public String getSecurity_type() {
		return security_type;
	}

	/**
	 * @param security_type
	 *            the security_type to set
	 */
	public void setSecurity_type(String security_type) {
		this.security_type = security_type;
	}

	/**
	 * @return the definer
	 */
	public String getDefiner() {
		return definer;
	}

	/**
	 * @param definer
	 *            the definer to set
	 */
	public void setDefiner(String definer) {
		this.definer = definer;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
