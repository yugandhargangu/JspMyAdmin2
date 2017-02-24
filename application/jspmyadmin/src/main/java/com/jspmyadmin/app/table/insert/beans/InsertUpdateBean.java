/**
 * 
 */
package com.jspmyadmin.app.table.insert.beans;

import java.util.List;

import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/11
 *
 */
public class InsertUpdateBean extends Bean {

	private static final long serialVersionUID = 1L;

	private String update = null;
	private String pk_status = null;

	private String pk_column = null;
	private String pk_value = null;

	private String[] changes = null;
	private String[] columns = null;
	private String[] nulls = null;
	private String[] functions = null;
	private Object[] values = null;

	private List<InsertInfo> info_list = null;

	/**
	 * @return the update
	 */
	public String getUpdate() {
		return update;
	}

	/**
	 * @param update
	 *            the update to set
	 */
	public void setUpdate(String update) {
		this.update = update;
	}

	/**
	 * @return the pk_status
	 */
	public String getPk_status() {
		return pk_status;
	}

	/**
	 * @param pk_status
	 *            the pk_status to set
	 */
	public void setPk_status(String pk_status) {
		this.pk_status = pk_status;
	}

	/**
	 * @return the pk_column
	 */
	public String getPk_column() {
		return pk_column;
	}

	/**
	 * @param pk_column
	 *            the pk_column to set
	 */
	public void setPk_column(String pk_column) {
		this.pk_column = pk_column;
	}

	/**
	 * @return the pk_value
	 */
	public String getPk_value() {
		return pk_value;
	}

	/**
	 * @param pk_value
	 *            the pk_value to set
	 */
	public void setPk_value(String pk_value) {
		this.pk_value = pk_value;
	}

	/**
	 * @return the changes
	 */
	public String[] getChanges() {
		return changes;
	}

	/**
	 * @param changes
	 *            the changes to set
	 */
	public void setChanges(String[] changes) {
		this.changes = changes;
	}

	/**
	 * @return the columns
	 */
	public String[] getColumns() {
		return columns;
	}

	/**
	 * @param columns
	 *            the columns to set
	 */
	public void setColumns(String[] columns) {
		this.columns = columns;
	}

	/**
	 * @return the nulls
	 */
	public String[] getNulls() {
		return nulls;
	}

	/**
	 * @param nulls
	 *            the nulls to set
	 */
	public void setNulls(String[] nulls) {
		this.nulls = nulls;
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
	 * @return the values
	 */
	public Object[] getValues() {
		return values;
	}

	/**
	 * @param values
	 *            the values to set
	 */
	public void setValues(Object[] values) {
		this.values = values;
	}

	/**
	 * @return the info_list
	 */
	public List<InsertInfo> getInfo_list() {
		return info_list;
	}

	/**
	 * @param info_list
	 *            the info_list to set
	 */
	public void setInfo_list(List<InsertInfo> info_list) {
		this.info_list = info_list;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
