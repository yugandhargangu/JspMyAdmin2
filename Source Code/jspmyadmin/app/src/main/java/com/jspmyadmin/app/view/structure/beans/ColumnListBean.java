/**
 * 
 */
package com.jspmyadmin.app.view.structure.beans;

import java.util.List;

import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/04/13
 *
 */
public class ColumnListBean extends Bean {

	private static final long serialVersionUID = 1L;

	private List<ColumnInfo> column_list = null;

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

}
