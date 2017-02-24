/**
 * 
 */
package com.jspmyadmin.app.server.users.beans;

import java.util.ArrayList;
import java.util.List;

import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/15
 *
 */
public class SchemaPrivilegeBean extends Bean {

	private static final long serialVersionUID = 1L;
	private final List<String> privilege_obj_list = new ArrayList<String>(Constants.Utils.PRIVILEGE_OBJ_LIST);
	private final List<String> privilege_ddl_list = new ArrayList<String>(Constants.Utils.PRIVILEGE_DDL_LIST);
	private final List<String> privilege_admn_list = new ArrayList<String>(
			Constants.Utils.PRIVILEGE_ADMN_LIST);

	private String user = null;
	private String database = null;
	private String[] privileges = null;
	private List<String> schema_list = null;
	private List<SchemaPrivilegeInfo> privilege_list = null;

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
	 * @return the database
	 */
	public String getDatabase() {
		return database;
	}

	/**
	 * @param database
	 *            the database to set
	 */
	public void setDatabase(String database) {
		this.database = database;
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
	 * @return the schema_list
	 */
	public List<String> getSchema_list() {
		return schema_list;
	}

	/**
	 * @param schema_list
	 *            the schema_list to set
	 */
	public void setSchema_list(List<String> schema_list) {
		this.schema_list = schema_list;
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
	 * @return the privilege_list
	 */
	public List<SchemaPrivilegeInfo> getPrivilege_list() {
		return privilege_list;
	}

	/**
	 * @param privilege_list
	 *            the privilege_list to set
	 */
	public void setPrivilege_list(List<SchemaPrivilegeInfo> privilege_list) {
		this.privilege_list = privilege_list;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
