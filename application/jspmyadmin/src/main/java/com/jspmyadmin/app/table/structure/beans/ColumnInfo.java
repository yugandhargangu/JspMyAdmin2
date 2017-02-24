/**
 * 
 */
package com.jspmyadmin.app.table.structure.beans;

import java.io.Serializable;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/04/13
 *
 */
public class ColumnInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String field_name = null;
	private String field_type = null;
	private String collation = null;
	private String null_yes = null;
	private String key = null;
	private String def_val = null;
	private String extra = null;
	private String privileges = null;
	private String comments = null;
	private String action = null;

	/**
	 * @return the field_name
	 */
	public String getField_name() {
		return field_name;
	}

	/**
	 * @param field_name
	 *            the field_name to set
	 */
	public void setField_name(String field_name) {
		this.field_name = field_name;
	}

	/**
	 * @return the field_type
	 */
	public String getField_type() {
		return field_type;
	}

	/**
	 * @param field_type
	 *            the field_type to set
	 */
	public void setField_type(String field_type) {
		this.field_type = field_type;
	}

	/**
	 * @return the null_yes
	 */
	public String getNull_yes() {
		return null_yes;
	}

	/**
	 * @param null_yes
	 *            the null_yes to set
	 */
	public void setNull_yes(String null_yes) {
		this.null_yes = null_yes;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the def_val
	 */
	public String getDef_val() {
		return def_val;
	}

	/**
	 * @param def_val
	 *            the def_val to set
	 */
	public void setDef_val(String def_val) {
		this.def_val = def_val;
	}

	/**
	 * @return the extra
	 */
	public String getExtra() {
		return extra;
	}

	/**
	 * @param extra
	 *            the extra to set
	 */
	public void setExtra(String extra) {
		this.extra = extra;
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
	 * @return the privileges
	 */
	public String getPrivileges() {
		return privileges;
	}

	/**
	 * @param privileges
	 *            the privileges to set
	 */
	public void setPrivileges(String privileges) {
		this.privileges = privileges;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
