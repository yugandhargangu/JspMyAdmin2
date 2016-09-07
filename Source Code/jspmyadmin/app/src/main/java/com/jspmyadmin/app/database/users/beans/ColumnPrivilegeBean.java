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
 * @created_at 2016/07/21
 *
 */
public class ColumnPrivilegeBean extends Bean {

	private static final long serialVersionUID = 1L;

	private final List<String> privilege_column_list = new ArrayList<String>(
			Constants.Utils.PRIVILEGE_COLUMN_LIST);

	private String user = null;
	private String table = null;
	private String fetch = null;

	private List<String> column_list = null;
	private List<String> select_column_list = null;
	private List<String> insert_column_list = null;
	private List<String> update_column_list = null;
	private List<String> reference_column_list = null;
	private String[] select_columns = null;
	private String[] insert_columns = null;
	private String[] update_columns = null;
	private String[] reference_columns = null;
	private String table_token = null;

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
	 * @return the table
	 */
	public String getTable() {
		return table;
	}

	/**
	 * @param table
	 *            the table to set
	 */
	public void setTable(String table) {
		this.table = table;
	}

	/**
	 * @return the column_list
	 */
	public List<String> getColumn_list() {
		return column_list;
	}

	/**
	 * @param column_list
	 *            the column_list to set
	 */
	public void setColumn_list(List<String> column_list) {
		this.column_list = column_list;
	}

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
	 * @return the select_column_list
	 */
	public List<String> getSelect_column_list() {
		return select_column_list;
	}

	/**
	 * @param select_column_list
	 *            the select_column_list to set
	 */
	public void setSelect_column_list(List<String> select_column_list) {
		this.select_column_list = select_column_list;
	}

	/**
	 * @return the insert_column_list
	 */
	public List<String> getInsert_column_list() {
		return insert_column_list;
	}

	/**
	 * @param insert_column_list
	 *            the insert_column_list to set
	 */
	public void setInsert_column_list(List<String> insert_column_list) {
		this.insert_column_list = insert_column_list;
	}

	/**
	 * @return the update_column_list
	 */
	public List<String> getUpdate_column_list() {
		return update_column_list;
	}

	/**
	 * @param update_column_list
	 *            the update_column_list to set
	 */
	public void setUpdate_column_list(List<String> update_column_list) {
		this.update_column_list = update_column_list;
	}

	/**
	 * @return the reference_column_list
	 */
	public List<String> getReference_column_list() {
		return reference_column_list;
	}

	/**
	 * @param reference_column_list
	 *            the reference_column_list to set
	 */
	public void setReference_column_list(List<String> reference_column_list) {
		this.reference_column_list = reference_column_list;
	}

	/**
	 * @return the select_columns
	 */
	public String[] getSelect_columns() {
		return select_columns;
	}

	/**
	 * @param select_columns
	 *            the select_columns to set
	 */
	public void setSelect_columns(String[] select_columns) {
		this.select_columns = select_columns;
	}

	/**
	 * @return the insert_columns
	 */
	public String[] getInsert_columns() {
		return insert_columns;
	}

	/**
	 * @param insert_columns
	 *            the insert_columns to set
	 */
	public void setInsert_columns(String[] insert_columns) {
		this.insert_columns = insert_columns;
	}

	/**
	 * @return the update_columns
	 */
	public String[] getUpdate_columns() {
		return update_columns;
	}

	/**
	 * @param update_columns
	 *            the update_columns to set
	 */
	public void setUpdate_columns(String[] update_columns) {
		this.update_columns = update_columns;
	}

	/**
	 * @return the reference_columns
	 */
	public String[] getReference_columns() {
		return reference_columns;
	}

	/**
	 * @param reference_columns
	 *            the reference_columns to set
	 */
	public void setReference_columns(String[] reference_columns) {
		this.reference_columns = reference_columns;
	}

	/**
	 * @return the table_token
	 */
	public String getTable_token() {
		return table_token;
	}

	/**
	 * @param table_token
	 *            the table_token to set
	 */
	public void setTable_token(String table_token) {
		this.table_token = table_token;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the privilege_column_list
	 */
	public List<String> getPrivilege_column_list() {
		return privilege_column_list;
	}

}
