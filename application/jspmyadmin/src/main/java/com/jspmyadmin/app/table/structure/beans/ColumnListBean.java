/**
 * 
 */
package com.jspmyadmin.app.table.structure.beans;

import java.util.List;

import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/04/13
 *
 */
public class ColumnListBean extends Bean {

	private static final long serialVersionUID = 1L;

	private String[] columns = null;
	private String[] keys = null;
	private List<ColumnInfo> column_list = null;
	private List<IndexInfo> index_list = null;

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
	 * @return the column_list
	 */
	public List<ColumnInfo> getColumn_list() {
		return column_list;
	}

	/**
	 * @param column_list
	 *            the column_list to set
	 */
	public void setColumn_list(List<ColumnInfo> column_list) {
		this.column_list = column_list;
	}

	/**
	 * @return the index_list
	 */
	public List<IndexInfo> getIndex_list() {
		return index_list;
	}

	/**
	 * @param index_list
	 *            the index_list to set
	 */
	public void setIndex_list(List<IndexInfo> index_list) {
		this.index_list = index_list;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// extra get methods
	/**
	 * 
	 * @return
	 */
	public int getColumns_size() {
		if (column_list != null) {
			return column_list.size();
		}
		return 0;
	}

	/**
	 * 
	 * @return
	 */
	public int getIndex_size() {
		if (index_list != null) {
			return index_list.size();
		}
		return 0;
	}
}
