/**
 * 
 */
package com.jspmyadmin.app.server.users.beans;

import java.util.List;

import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/15
 *
 */
public class GlobalPrivilegeBean extends Bean {

	private static final long serialVersionUID = 1L;

	private String revoke_all = null;
	private String[] privileges = null;

	private String user = null;
	private List<PrivilegeInfo> privilege_info_list = null;

	/**
	 * @return the revoke_all
	 */
	public String getRevoke_all() {
		return revoke_all;
	}

	/**
	 * @param revoke_all
	 *            the revoke_all to set
	 */
	public void setRevoke_all(String revoke_all) {
		this.revoke_all = revoke_all;
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
	 * @return the privilege_info_list
	 */
	public List<PrivilegeInfo> getPrivilege_info_list() {
		return privilege_info_list;
	}

	/**
	 * @param privilege_info_list
	 *            the privilege_info_list to set
	 */
	public void setPrivilege_info_list(List<PrivilegeInfo> privilege_info_list) {
		this.privilege_info_list = privilege_info_list;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
