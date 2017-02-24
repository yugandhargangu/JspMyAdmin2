/**
 * 
 */
package com.jspmyadmin.app.table.data.beans;

import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/06/28
 *
 */
public class DataUpdateBean extends Bean {

	private static final long serialVersionUID = 1L;

	private String column_name = null;
	private String column_value = null;
	private String primary_key = null;

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
	 * @return the column_value
	 */
	public String getColumn_value() {
		return column_value;
	}

	/**
	 * @param column_value
	 *            the column_value to set
	 */
	public void setColumn_value(String column_value) {
		this.column_value = column_value;
	}

	/**
	 * @return the primary_key
	 */
	public String getPrimary_key() {
		return primary_key;
	}

	/**
	 * @param primary_key
	 *            the primary_key to set
	 */
	public void setPrimary_key(String primary_key) {
		this.primary_key = primary_key;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
