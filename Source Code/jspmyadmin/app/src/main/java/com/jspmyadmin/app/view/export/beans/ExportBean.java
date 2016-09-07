/**
 * 
 */
package com.jspmyadmin.app.view.export.beans;

import java.util.ArrayList;
import java.util.List;

import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/09/05
 *
 */
public class ExportBean extends Bean {

	private static final long serialVersionUID = 1339002439220829682L;

	private String[] column_list = null;
	private String export_type = null;
	private String filename = null;

	private List<String> type_list = new ArrayList<String>(Constants.Utils.EXPORT_TYPE_LIST);

	/**
	 * @return the column_list
	 */
	public String[] getColumn_list() {
		return column_list;
	}

	/**
	 * @param column_list
	 *            the column_list to set
	 */
	public void setColumn_list(String[] column_list) {
		this.column_list = column_list;
	}

	/**
	 * @return the export_type
	 */
	public String getExport_type() {
		return export_type;
	}

	/**
	 * @param export_type
	 *            the export_type to set
	 */
	public void setExport_type(String export_type) {
		this.export_type = export_type;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename
	 *            the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the type_list
	 */
	public List<String> getType_list() {
		return type_list;
	}

	/**
	 * @param type_list
	 *            the type_list to set
	 */
	public void setType_list(List<String> type_list) {
		this.type_list = type_list;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
