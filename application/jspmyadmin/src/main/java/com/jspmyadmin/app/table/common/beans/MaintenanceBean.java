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
 * @created_at 2016/07/21
 *
 */
public class MaintenanceBean extends Bean {

	private static final long serialVersionUID = 1L;

	private final List<String> check_op_list = new ArrayList<String>(Constants.Utils.CHECK_OP_LIST);
	private final List<String> analize_op_list = new ArrayList<String>(Constants.Utils.ANALIZE_OP_LIST);
	private final List<String> checksum_op_list = new ArrayList<String>(Constants.Utils.CHECKSUM_OP_LIST);
	private final List<String> repair_op_list = new ArrayList<String>(Constants.Utils.REPAIR_OP_LIST);

	private String option = null;
	private String[] repair_options = null;

	private String action = null;

	private List<String> column_names = null;
	private List<List<String>> data_list = null;

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
	 * @return the option
	 */
	public String getOption() {
		return option;
	}

	/**
	 * @param option
	 *            the option to set
	 */
	public void setOption(String option) {
		this.option = option;
	}

	/**
	 * @return the repair_options
	 */
	public String[] getRepair_options() {
		return repair_options;
	}

	/**
	 * @param repair_options
	 *            the repair_options to set
	 */
	public void setRepair_options(String[] repair_options) {
		this.repair_options = repair_options;
	}

	/**
	 * @return the column_names
	 */
	public List<String> getColumn_names() {
		return column_names;
	}

	/**
	 * @param column_names
	 *            the column_names to set
	 */
	public void setColumn_names(List<String> column_names) {
		this.column_names = column_names;
	}

	/**
	 * @return the data_list
	 */
	public List<List<String>> getData_list() {
		return data_list;
	}

	/**
	 * @param data_list
	 *            the data_list to set
	 */
	public void setData_list(List<List<String>> data_list) {
		this.data_list = data_list;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the check_op_list
	 */
	public List<String> getCheck_op_list() {
		return check_op_list;
	}

	/**
	 * @return the analize_op_list
	 */
	public List<String> getAnalize_op_list() {
		return analize_op_list;
	}

	/**
	 * @return the checksum_op_list
	 */
	public List<String> getChecksum_op_list() {
		return checksum_op_list;
	}

	/**
	 * @return the repair_op_list
	 */
	public List<String> getRepair_op_list() {
		return repair_op_list;
	}

	// extra getters

	/**
	 * 
	 * @return
	 */
	public String getColumn_count() {
		if (column_names != null) {
			return String.valueOf(column_names.size());
		}
		return Constants.ZERO;
	}
}
