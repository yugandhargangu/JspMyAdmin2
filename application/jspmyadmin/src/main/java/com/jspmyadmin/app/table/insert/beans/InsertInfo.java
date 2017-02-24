/**
 * 
 */
package com.jspmyadmin.app.table.insert.beans;

import java.io.Serializable;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/11
 *
 */
public class InsertInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String column = null;
	private String dataType = null;
	private String canBeNull = null;
	private String extra = null;
	private String value = null;
	private String file_type = null;

	/**
	 * @return the column
	 */
	public String getColumn() {
		return column;
	}

	/**
	 * @param column
	 *            the column to set
	 */
	public void setColumn(String column) {
		this.column = column;
	}

	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * @param dataType
	 *            the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * @return the canBeNull
	 */
	public String getCanBeNull() {
		return canBeNull;
	}

	/**
	 * @param canBeNull
	 *            the canBeNull to set
	 */
	public void setCanBeNull(String canBeNull) {
		this.canBeNull = canBeNull;
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
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the file_type
	 */
	public String getFile_type() {
		return file_type;
	}

	/**
	 * @param file_type
	 *            the file_type to set
	 */
	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
