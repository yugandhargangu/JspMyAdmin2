/**
 * 
 */
package com.jspmyadmin.app.database.sql.beans;

import java.util.List;

import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/13
 *
 */
public class SqlBean extends Bean {

	private static final long serialVersionUID = 1L;

	private String query = null;
	private String disable_fks = null;
	private String result = null;
	private String error = null;
	private List<String> column_list = null;
	private List<List<String>> fetch_list = null;

	private String max_rows = null;
	private String exec_time = null;
	private String hint_options = null;

	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @param query
	 *            the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * @return the disable_fks
	 */
	public String getDisable_fks() {
		return disable_fks;
	}

	/**
	 * @param disable_fks
	 *            the disable_fks to set
	 */
	public void setDisable_fks(String disable_fks) {
		this.disable_fks = disable_fks;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error
	 *            the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * @return the column_list
	 */
	public List<String> getColumn_list() {
		return column_list;
	}

	/**
	 * @param column_list
	 *            the column_list to set
	 */
	public void setColumn_list(List<String> column_list) {
		this.column_list = column_list;
	}

	/**
	 * @return the fetch_list
	 */
	public List<List<String>> getFetch_list() {
		return fetch_list;
	}

	/**
	 * @param fetch_list
	 *            the fetch_list to set
	 */
	public void setFetch_list(List<List<String>> fetch_list) {
		this.fetch_list = fetch_list;
	}

	/**
	 * @return the max_rows
	 */
	public String getMax_rows() {
		return max_rows;
	}

	/**
	 * @param max_rows
	 *            the max_rows to set
	 */
	public void setMax_rows(String max_rows) {
		this.max_rows = max_rows;
	}

	/**
	 * @return the exec_time
	 */
	public String getExec_time() {
		return exec_time;
	}

	/**
	 * @param exec_time
	 *            the exec_time to set
	 */
	public void setExec_time(String exec_time) {
		this.exec_time = exec_time;
	}

	/**
	 * @return the hint_options
	 */
	public String getHint_options() {
		return hint_options;
	}

	/**
	 * @param hint_options
	 *            the hint_options to set
	 */
	public void setHint_options(String hint_options) {
		this.hint_options = hint_options;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// extra setters
	/**
	 * 
	 * @return
	 */
	public String getTotal_data_count() {
		if (fetch_list == null) {
			return Constants.ZERO;
		}
		return String.valueOf(fetch_list.size());
	}

	/**
	 * 
	 * @return
	 */
	public String getColumn_count() {
		if (column_list == null) {
			return Constants.ZERO;
		}
		return String.valueOf(column_list.size());
	}
}
