/**
 * 
 */
package com.jspmyadmin.app.database.users.beans;

import java.util.ArrayList;
import java.util.List;

import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/19
 *
 */
public class UserListBean extends Bean {

	private static final long serialVersionUID = 1L;

	private final List<String> privilege_obj_list = new ArrayList<String>(Constants.Utils.PRIVILEGE_OBJ_LIST);
	private final List<String> privilege_ddl_list = new ArrayList<String>(Constants.Utils.PRIVILEGE_DDL_LIST);
	private final List<String> privilege_admn_list = new ArrayList<String>(
			Constants.Utils.PRIVILEGE_ADMN_LIST);

	private String error = null;
	private List<String> user_list = null;
	private List<UserInfo> user_info_list = null;

	private String user = null;
	private String[] privileges = null;

	/**
	 * @return the user_list
	 */
	public List<String> getUser_list() {
		return user_list;
	}

	/**
	 * @param user_list
	 *            the user_list to set
	 */
	public void setUser_list(List<String> user_list) {
		this.user_list = user_list;
	}

	/**
	 * @return the privilege_obj_list
	 */
	public List<String> getPrivilege_obj_list() {
		return privilege_obj_list;
	}

	/**
	 * @return the privilege_ddl_list
	 */
	public List<String> getPrivilege_ddl_list() {
		return privilege_ddl_list;
	}

	/**
	 * @return the privilege_admn_list
	 */
	public List<String> getPrivilege_admn_list() {
		return privilege_admn_list;
	}

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
	 * @return the privileges
	 */
	public String[] getPrivileges() {
		return privileges;
	}

	/**
	 * @param privileges
	 *            the privileges to set
	 */
	public void setPrivileges(String[] privileges) {
		this.privileges = privileges;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
