/**
 * 
 */
package com.jspmyadmin.app.server.database.beans;

import java.util.List;
import java.util.Map;

import com.jspmyadmin.app.common.logic.HomeLogic;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/09
 *
 */
public class DatabaseListBean extends Bean {

	private static final long serialVersionUID = 1L;

	private String database = null;
	private String collation = null;
	private DatabaseInfo sortInfo = null;
	private DatabaseInfo footerInfo = null;
	private String sort = null;
	private String type = null;
	private String[] databases = null;

	private Map<String, List<String>> collation_map = null;

	private List<DatabaseInfo> database_list = null;

	public DatabaseListBean() {
		HomeLogic homeLogic = null;
		try {
			homeLogic = new HomeLogic();
			collation_map = homeLogic.getCollationMap();
		} catch (Exception e) {

		} finally {
			homeLogic = null;
		}
	}

	/**
	 * @return the database
	 */
	public String getDatabase() {
		return database;
	}

	/**
	 * @param database
	 *            the database to set
	 */
	public void setDatabase(String database) {
		this.database = database;
	}

	/**
	 * @return the collation
	 */
	public String getCollation() {
		return collation;
	}

	/**
	 * @param collation
	 *            the collation to set
	 */
	public void setCollation(String collation) {
		this.collation = collation;
	}

	/**
	 * @return the collation_map
	 */
	public Map<String, List<String>> getCollation_map() {
		return collation_map;
	}

	/**
	 * @param collation_map
	 *            the collation_map to set
	 */
	public void setCollation_map(Map<String, List<String>> collation_map) {
		this.collation_map = collation_map;
	}

	/**
	 * @return the database_list
	 */
	public List<DatabaseInfo> getDatabase_list() {
		return database_list;
	}

	/**
	 * @param database_list
	 *            the database_list to set
	 */
	public void setDatabase_list(List<DatabaseInfo> database_list) {
		this.database_list = database_list;
	}

	/**
	 * @return the sortInfo
	 */
	public DatabaseInfo getSortInfo() {
		return sortInfo;
	}

	/**
	 * @param sortInfo
	 *            the sortInfo to set
	 */
	public void setSortInfo(DatabaseInfo sortInfo) {
		this.sortInfo = sortInfo;
	}

	/**
	 * @return the footerInfo
	 */
	public DatabaseInfo getFooterInfo() {
		return footerInfo;
	}

	/**
	 * @param footerInfo
	 *            the footerInfo to set
	 */
	public void setFooterInfo(DatabaseInfo footerInfo) {
		this.footerInfo = footerInfo;
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

}
