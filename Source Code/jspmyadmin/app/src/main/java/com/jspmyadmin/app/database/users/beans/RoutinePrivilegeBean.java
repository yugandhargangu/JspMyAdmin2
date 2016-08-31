/**
 * 
 */
package com.jspmyadmin.app.database.users.beans;

import java.util.ArrayList;
import java.util.List;

import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/21
 *
 */
public class RoutinePrivilegeBean extends Bean {

	private static final long serialVersionUID = 1L;

	private final List<String> privilege_routine_list = new ArrayList<String>(
			FrameworkConstants.Utils.PRIVILEGE_ROUTINE_LIST);

	private String fetch = null;
	private String user = null;
	private List<String> procedure_list = null;
	private List<String> function_list = null;
	private String[] procedures = null;
	private String[] procedure_privileges = null;
	private String[] functions = null;
	private String[] function_privileges = null;

	/**
	 * @return the fetch
	 */
	public String getFetch() {
		return fetch;
	}

	/**
	 * @param fetch
	 *            the fetch to set
	 */
	public void setFetch(String fetch) {
		this.fetch = fetch;
	}

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
	 * @return the procedure_list
	 */
	public List<String> getProcedure_list() {
		return procedure_list;
	}

	/**
	 * @param procedure_list
	 *            the procedure_list to set
	 */
	public void setProcedure_list(List<String> procedure_list) {
		this.procedure_list = procedure_list;
	}

	/**
	 * @return the function_list
	 */
	public List<String> getFunction_list() {
		return function_list;
	}

	/**
	 * @param function_list
	 *            the function_list to set
	 */
	public void setFunction_list(List<String> function_list) {
		this.function_list = function_list;
	}

	/**
	 * @return the procedures
	 */
	public String[] getProcedures() {
		return procedures;
	}

	/**
	 * @param procedures
	 *            the procedures to set
	 */
	public void setProcedures(String[] procedures) {
		this.procedures = procedures;
	}

	/**
	 * @return the procedure_privileges
	 */
	public String[] getProcedure_privileges() {
		return procedure_privileges;
	}

	/**
	 * @param procedure_privileges
	 *            the procedure_privileges to set
	 */
	public void setProcedure_privileges(String[] procedure_privileges) {
		this.procedure_privileges = procedure_privileges;
	}

	/**
	 * @return the functions
	 */
	public String[] getFunctions() {
		return functions;
	}

	/**
	 * @param functions
	 *            the functions to set
	 */
	public void setFunctions(String[] functions) {
		this.functions = functions;
	}

	/**
	 * @return the function_privileges
	 */
	public String[] getFunction_privileges() {
		return function_privileges;
	}

	/**
	 * @param function_privileges
	 *            the function_privileges to set
	 */
	public void setFunction_privileges(String[] function_privileges) {
		this.function_privileges = function_privileges;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the privilege_routine_list
	 */
	public List<String> getPrivilege_routine_list() {
		return privilege_routine_list;
	}

}
