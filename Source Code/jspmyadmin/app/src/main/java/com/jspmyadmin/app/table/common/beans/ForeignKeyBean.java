/**
 * 
 */
package com.jspmyadmin.app.table.common.beans;

import java.util.ArrayList;
import java.util.List;

import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/06
 *
 */
public class ForeignKeyBean extends Bean {

	private static final long serialVersionUID = 1L;

	private String column_name = null;
	private String ref_table_name = null;
	private String ref_column_name = null;
	private String update_action = null;
	private String delete_action = null;

	private String[] keys = null;

	private List<ForeignKeyInfo> foreign_key_info_list = null;
	private List<ForeignKeyInfo> reference_key_info_list = null;

	private List<String> column_list = null;
	private List<String> ref_table_list = null;
	private List<String> action_list = new ArrayList<String>(Constants.Utils.ACTION_LIST);

	/**
	 * @return the column_name
	 */
	public String getColumn_name() {
		return column_name;
	}

	/**
	 * @param column_name
	 *            the column_name to set
	 */
	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}

	/**
	 * @return the ref_table_name
	 */
	public String getRef_table_name() {
		return ref_table_name;
	}

	/**
	 * @param ref_table_name
	 *            the ref_table_name to set
	 */
	public void setRef_table_name(String ref_table_name) {
		this.ref_table_name = ref_table_name;
	}

	/**
	 * @return the ref_column_name
	 */
	public String getRef_column_name() {
		return ref_column_name;
	}

	/**
	 * @param ref_column_name
	 *            the ref_column_name to set
	 */
	public void setRef_column_name(String ref_column_name) {
		this.ref_column_name = ref_column_name;
	}

	/**
	 * @return the update_action
	 */
	public String getUpdate_action() {
		return update_action;
	}

	/**
	 * @param update_action
	 *            the update_action to set
	 */
	public void setUpdate_action(String update_action) {
		this.update_action = update_action;
	}

	/**
	 * @return the delete_action
	 */
	public String getDelete_action() {
		return delete_action;
	}

	/**
	 * @param delete_action
	 *            the delete_action to set
	 */
	public void setDelete_action(String delete_action) {
		this.delete_action = delete_action;
	}

	/**
	 * @return the keys
	 */
	public String[] getKeys() {
		return keys;
	}

	/**
	 * @param keys
	 *            the keys to set
	 */
	public void setKeys(String[] keys) {
		this.keys = keys;
	}

	/**
	 * @return the foreign_key_info_list
	 */
	public List<ForeignKeyInfo> getForeign_key_info_list() {
		return foreign_key_info_list;
	}

	/**
	 * @param foreign_key_info_list
	 *            the foreign_key_info_list to set
	 */
	public void setForeign_key_info_list(List<ForeignKeyInfo> foreign_key_info_list) {
		this.foreign_key_info_list = foreign_key_info_list;
	}

	/**
	 * @return the reference_key_info_list
	 */
	public List<ForeignKeyInfo> getReference_key_info_list() {
		return reference_key_info_list;
	}

	/**
	 * @param reference_key_info_list
	 *            the reference_key_info_list to set
	 */
	public void setReference_key_info_list(List<ForeignKeyInfo> reference_key_info_list) {
		this.reference_key_info_list = reference_key_info_list;
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
	 * @return the ref_table_list
	 */
	public List<String> getRef_table_list() {
		return ref_table_list;
	}

	/**
	 * @param ref_table_list
	 *            the ref_table_list to set
	 */
	public void setRef_table_list(List<String> ref_table_list) {
		this.ref_table_list = ref_table_list;
	}

	/**
	 * @return the action_list
	 */
	public List<String> getAction_list() {
		return action_list;
	}

	/**
	 * @param action_list
	 *            the action_list to set
	 */
	public void setAction_list(List<String> action_list) {
		this.action_list = action_list;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// extra methods

	public String getKey_count() {
		if (foreign_key_info_list != null) {
			return String.valueOf(foreign_key_info_list.size());
		}
		return Constants.ZERO;
	}

	public String getRef_count() {
		if (reference_key_info_list != null) {
			return String.valueOf(reference_key_info_list.size());
		}
		return Constants.ZERO;
	}
}
