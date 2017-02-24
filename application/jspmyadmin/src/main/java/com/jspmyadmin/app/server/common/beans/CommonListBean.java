/**
 * 
 */
package com.jspmyadmin.app.server.common.beans;

import java.util.List;

import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/10
 *
 */
public class CommonListBean extends Bean {

	private static final long serialVersionUID = 1L;

	private String field = null;
	private String type = null;
	private String[] columnInfo = null;
	private String[] sortInfo = null;
	private List<String[]> data_list = null;

	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * @param field
	 *            the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the columnInfo
	 */
	public String[] getColumnInfo() {
		return columnInfo;
	}

	/**
	 * @param columnInfo
	 *            the columnInfo to set
	 */
	public void setColumnInfo(String[] columnInfo) {
		this.columnInfo = columnInfo;
	}

	/**
	 * @return the sortInfo
	 */
	public String[] getSortInfo() {
		return sortInfo;
	}

	/**
	 * @param sortInfo
	 *            the sortInfo to set
	 */
	public void setSortInfo(String[] sortInfo) {
		this.sortInfo = sortInfo;
	}

	/**
	 * @return the data_list
	 */
	public List<String[]> getData_list() {
		return data_list;
	}

	/**
	 * @param data_list
	 *            the data_list to set
	 */
	public void setData_list(List<String[]> data_list) {
		this.data_list = data_list;
	}

}
