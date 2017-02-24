/**
 * 
 */
package com.jspmyadmin.app.table.common.beans;

import java.io.Serializable;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/06
 *
 */
public class ForeignKeyInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String key_name = null;
	private String column_name = null;
	private String ref_table_name = null;
	private String ref_column_name = null;
	private String update_rule = null;
	private String delete_rule = null;

	/**
	 * @return the key_name
	 */
	public String getKey_name() {
		return key_name;
	}

	/**
	 * @param key_name
	 *            the key_name to set
	 */
	public void setKey_name(String key_name) {
		this.key_name = key_name;
	}

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
	 * @return the update_rule
	 */
	public String getUpdate_rule() {
		return update_rule;
	}

	/**
	 * @param update_rule
	 *            the update_rule to set
	 */
	public void setUpdate_rule(String update_rule) {
		this.update_rule = update_rule;
	}

	/**
	 * @return the delete_rule
	 */
	public String getDelete_rule() {
		return delete_rule;
	}

	/**
	 * @param delete_rule
	 *            the delete_rule to set
	 */
	public void setDelete_rule(String delete_rule) {
		this.delete_rule = delete_rule;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
