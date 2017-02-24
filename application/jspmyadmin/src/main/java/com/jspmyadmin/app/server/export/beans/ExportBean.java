/**
 * 
 */
package com.jspmyadmin.app.server.export.beans;

import java.util.List;

import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/08/01
 *
 */
public class ExportBean extends Bean {

	private static final long serialVersionUID = 1L;

	private String[] databases = null;
	private String filename = null;
	private String disable_fks = null;
	private String export_type = null;

	private String add_drop_db = null;
	private String add_drop_table = null;
	private String include_views = null;
	private String include_procedures = null;
	private String include_functions = null;
	private String include_events = null;
	private String include_triggers = null;

	private List<String> database_list = null;

	/**
	 * @return the databases
	 */
	public String[] getDatabases() {
		return databases;
	}

	/**
	 * @param databases
	 *            the databases to set
	 */
	public void setDatabases(String[] databases) {
		this.databases = databases;
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
	 * @return the add_drop_db
	 */
	public String getAdd_drop_db() {
		return add_drop_db;
	}

	/**
	 * @param add_drop_db
	 *            the add_drop_db to set
	 */
	public void setAdd_drop_db(String add_drop_db) {
		this.add_drop_db = add_drop_db;
	}

	/**
	 * @return the add_drop_table
	 */
	public String getAdd_drop_table() {
		return add_drop_table;
	}

	/**
	 * @param add_drop_table
	 *            the add_drop_table to set
	 */
	public void setAdd_drop_table(String add_drop_table) {
		this.add_drop_table = add_drop_table;
	}

	/**
	 * @return the include_views
	 */
	public String getInclude_views() {
		return include_views;
	}

	/**
	 * @param include_views
	 *            the include_views to set
	 */
	public void setInclude_views(String include_views) {
		this.include_views = include_views;
	}

	/**
	 * @return the include_procedures
	 */
	public String getInclude_procedures() {
		return include_procedures;
	}

	/**
	 * @param include_procedures
	 *            the include_procedures to set
	 */
	public void setInclude_procedures(String include_procedures) {
		this.include_procedures = include_procedures;
	}

	/**
	 * @return the include_functions
	 */
	public String getInclude_functions() {
		return include_functions;
	}

	/**
	 * @param include_functions
	 *            the include_functions to set
	 */
	public void setInclude_functions(String include_functions) {
		this.include_functions = include_functions;
	}

	/**
	 * @return the include_events
	 */
	public String getInclude_events() {
		return include_events;
	}

	/**
	 * @param include_events
	 *            the include_events to set
	 */
	public void setInclude_events(String include_events) {
		this.include_events = include_events;
	}

	/**
	 * @return the include_triggers
	 */
	public String getInclude_triggers() {
		return include_triggers;
	}

	/**
	 * @param include_triggers
	 *            the include_triggers to set
	 */
	public void setInclude_triggers(String include_triggers) {
		this.include_triggers = include_triggers;
	}

	/**
	 * @return the database_list
	 */
	public List<String> getDatabase_list() {
		return database_list;
	}

	/**
	 * @param database_list
	 *            the database_list to set
	 */
	public void setDatabase_list(List<String> database_list) {
		this.database_list = database_list;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
