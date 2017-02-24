/**
 * 
 */
package com.jspmyadmin.app.server.export.beans;

import com.jspmyadmin.framework.web.utils.Bean;
import com.jspmyadmin.framework.web.utils.FileInput;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/27
 *
 */
public class ImportBean extends Bean {

	private static final long serialVersionUID = 1L;

	private FileInput import_file = null;
	private String query = null;
	private String error = null;
	private String disable_fks = null;
	private String continue_errors = null;
	private String error_queries = null;
	private String exec_time = null;
	private String error_count = null;
	private String ignore_count = null;
	private String success_count = null;

	/**
	 * @return the import_file
	 */
	public FileInput getImport_file() {
		return import_file;
	}

	/**
	 * @param import_file
	 *            the import_file to set
	 */
	public void setImport_file(FileInput import_file) {
		this.import_file = import_file;
	}

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
	 * @return the continue_errors
	 */
	public String getContinue_errors() {
		return continue_errors;
	}

	/**
	 * @param continue_errors
	 *            the continue_errors to set
	 */
	public void setContinue_errors(String continue_errors) {
		this.continue_errors = continue_errors;
	}

	/**
	 * @return the error_queries
	 */
	public String getError_queries() {
		return error_queries;
	}

	/**
	 * @param error_queries
	 *            the error_queries to set
	 */
	public void setError_queries(String error_queries) {
		this.error_queries = error_queries;
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
	 * @return the error_count
	 */
	public String getError_count() {
		return error_count;
	}

	/**
	 * @param error_count
	 *            the error_count to set
	 */
	public void setError_count(String error_count) {
		this.error_count = error_count;
	}

	/**
	 * @return the ignore_count
	 */
	public String getIgnore_count() {
		return ignore_count;
	}

	/**
	 * @param ignore_count
	 *            the ignore_count to set
	 */
	public void setIgnore_count(String ignore_count) {
		this.ignore_count = ignore_count;
	}

	/**
	 * @return the success_count
	 */
	public String getSuccess_count() {
		return success_count;
	}

	/**
	 * @param success_count
	 *            the success_count to set
	 */
	public void setSuccess_count(String success_count) {
		this.success_count = success_count;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
