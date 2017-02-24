/**
 * 
 */
package com.jspmyadmin.app.database.structure.beans;

import java.util.List;

import com.jspmyadmin.app.common.logic.SideBarLogic;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/15
 *
 */
public class StructureBean extends Bean {

	private static final long serialVersionUID = 1L;

	private String database_name = null;
	private String table_name = null;
	private String column_length = null;
	private String[] tables = null;
	private String sort = null;
	private String type = null;

	private String enable_checks = null;
	private String drop_checks = null;
	private String prefix = null;
	private String new_prefix = null;

	private TableInfo sortInfo = null;
	private TableInfo footerInfo = null;
	private List<TableInfo> table_list = null;
	private List<String> database_list = null;
	private String action = null;

	public StructureBean() {
		SideBarLogic sideBarLogic = new SideBarLogic();
		try {
			this.database_list = sideBarLogic.getDatabaseList();
		} catch (Exception e) {
		} finally {
			sideBarLogic = null;
		}
	}

	/**
	 * @return the database_name
	 */
	public String getDatabase_name() {
		return database_name;
	}

	/**
	 * @param database_name
	 *            the database_name to set
	 */
	public void setDatabase_name(String database_name) {
		this.database_name = database_name;
	}

	/**
	 * @return the table_name
	 */
	public String getTable_name() {
		return table_name;
	}

	/**
	 * @param table_name
	 *            the table_name to set
	 */
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	/**
	 * @return the column_length
	 */
	public String getColumn_length() {
		return column_length;
	}

	/**
	 * @param column_length
	 *            the column_length to set
	 */
	public void setColumn_length(String column_length) {
		this.column_length = column_length;
	}

	/**
	 * @return the tables
	 */
	public String[] getTables() {
		return tables;
	}

	/**
	 * @param tables
	 *            the tables to set
	 */
	public void setTables(String[] tables) {
		this.tables = tables;
	}

	/**
	 * @return the enable_checks
	 */
	public String getEnable_checks() {
		return enable_checks;
	}

	/**
	 * @param enable_checks
	 *            the enable_checks to set
	 */
	public void setEnable_checks(String enable_checks) {
		this.enable_checks = enable_checks;
	}

	/**
	 * @return the sort
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * @param sort
	 *            the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
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
	 * @return the drop_checks
	 */
	public String getDrop_checks() {
		return drop_checks;
	}

	/**
	 * @param drop_checks
	 *            the drop_checks to set
	 */
	public void setDrop_checks(String drop_checks) {
		this.drop_checks = drop_checks;
	}

	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @param prefix
	 *            the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * @return the new_prefix
	 */
	public String getNew_prefix() {
		return new_prefix;
	}

	/**
	 * @param new_prefix
	 *            the new_prefix to set
	 */
	public void setNew_prefix(String new_prefix) {
		this.new_prefix = new_prefix;
	}

	/**
	 * @return the sortInfo
	 */
	public TableInfo getSortInfo() {
		return sortInfo;
	}

	/**
	 * @param sortInfo
	 *            the sortInfo to set
	 */
	public void setSortInfo(TableInfo sortInfo) {
		this.sortInfo = sortInfo;
	}

	/**
	 * @return the footerInfo
	 */
	public TableInfo getFooterInfo() {
		return footerInfo;
	}

	/**
	 * @param footerInfo
	 *            the footerInfo to set
	 */
	public void setFooterInfo(TableInfo footerInfo) {
		this.footerInfo = footerInfo;
	}

	/**
	 * @return the table_list
	 */
	public List<TableInfo> getTable_list() {
		return table_list;
	}

	/**
	 * @param table_list
	 *            the table_list to set
	 */
	public void setTable_list(List<TableInfo> table_list) {
		this.table_list = table_list;
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

}
