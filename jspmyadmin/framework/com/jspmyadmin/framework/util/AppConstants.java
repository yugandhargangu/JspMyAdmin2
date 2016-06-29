/**
 * 
 */
package com.jspmyadmin.framework.util;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/10
 *
 */
public final class AppConstants {

	private AppConstants() {
		// prevent from instantiation
	}

	public static final String ERR_INVALID_ACCESS = "err.invalid_access";
	public static final String ERR_INVALID_SETTINGS = "err.invalid_settings";

	public static final String MSG_CREATE_DB_SUCCESS = "msg.create_db_success";
	public static final String MSG_DROP_DB_SUCCESS = "msg.drop_db_success";
	public static final String MSG_SAVE_SUCCESS = "msg.save_success";

	public static final String VAL_DAYS = "val.days";
	public static final String VAL_HOURS = "val.hours";
	public static final String VAL_MINS = "val.mins";
	public static final String VAL_SECS = "val.secs";

	public static final String JSP_COMMON_HOME = "common/Home.jsp";
	public static final String JSP_COMMON_LOGIN = "common/Login.jsp";
	public static final String JSP_DATABASE_STRUCTURE_CREATE_TABLE = "database/structure/CreateTable.jsp";
	public static final String JSP_DATABASE_STRUCTURE_CREATE_VIEW = "database/structure/CreateView.jsp";
	public static final String JSP_DATABASE_STRUCTURE_TABLES = "database/structure/Tables.jsp";
	public static final String JSP_DATABASE_STRUCTURE_VIEWS = "database/structure/Views.jsp";
	public static final String JSP_SERVER_COMMON_CHARSETLIST = "server/common/CharsetList.jsp";
	public static final String JSP_SERVER_COMMON_ENGINELIST = "server/common/EngineList.jsp";
	public static final String JSP_SERVER_COMMON_PLUGINLIST = "server/common/PluginList.jsp";
	public static final String JSP_SERVER_COMMON_STATUSLIST = "server/common/StatusList.jsp";
	public static final String JSP_SERVER_COMMON_VARIABLELIST = "server/common/VariableList.jsp";
	public static final String JSP_SERVER_DATABASE_DATABASELIST = "server/database/DatabaseList.jsp";
	public static final String JSP_TABLE_STRUCTURE_ALTER_TABLE = "table/structure/AlterTable.jsp";
	public static final String JSP_TABLE_STRUCTURE_STRUCTURE = "table/structure/Structure.jsp";
	public static final String JSP_TABLE_DATA_DATA = "table/data/Data.jsp";

	public static final String PATH_HOME = "/home";
	public static final String PATH_SERVER_DATABASES = "/server_databases";
	public static final String PATH_DATABASE_STRUCTURE = "/database_structure";
	public static final String PATH_DATABASE_VIEW_LIST = "/database_view_list";

}
