/**
 * 
 */
package com.jspmyadmin.framework.constants;

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
	public static final String ERR_UNABLE_TO_CONNECT_WITH_SERVER = "err.unable_to_connect_with_server";

	public static final String MSG_COLUMN_DROPPED_SUCCESSFULLY = "msg.column_dropped_successfully";
	public static final String MSG_COPY_DONE = "msg.copy_done";
	public static final String MSG_CREATE_DB_SUCCESS = "msg.create_db_success";
	public static final String MSG_DUPLICATE_TABLE_SUCCESSFULLY = "msg.duplicate_table_successfully";
	public static final String MSG_DROP_DB_SUCCESS = "msg.drop_db_success";
	public static final String MSG_DROP_FK_SUCCESS = "msg.drop_fk_success";
	public static final String MSG_EVENT_CREATE_SUCCESS = "msg.event_create_success";
	public static final String MSG_EVENT_DROP_SUCCESS = "msg.event_drop_success";
	public static final String MSG_EXECUTED_SUCCESSFULLY = "msg.executed_successfully";
	public static final String MSG_FK_ADD_SUCCESS = "msg.fk_add_success";
	public static final String MSG_FUNCTION_ALREADY_EXISTED = "msg.function_already_existed";
	public static final String MSG_FUNCTION_DROP_SUCCESS = "msg.function_drop_success";
	public static final String MSG_FUNCTION_SAVE_SUCCESS = "msg.function_save_success";
	public static final String MSG_IMPORT_FILE_BLANK = "msg.import_file_blank";
	public static final String MSG_IMPORT_FILE_EMPTY = "msg.import_file_empty";
	public static final String MSG_IMPORT_INVALID_FILE = "msg.import_invalid_file";
	public static final String MSG_NO_CHANGES_FOUND = "msg.no_changes_found";
	public static final String MSG_PROCEDURE_ALREADY_EXISTED = "msg.procedure_already_existed";
	public static final String MSG_PROCEDURE_DROP_SUCCESS = "msg.procedure_drop_success";
	public static final String MSG_PROCEDURE_SAVE_SUCCESS = "msg.procedure_save_success";
	public static final String MSG_SAVE_SUCCESS = "msg.save_success";
	public static final String MSG_TABLE_ALREADY_EXISTED = "msg.table_already_existed";
	public static final String MSG_TABLE_ALTERED = "msg.table_altered";
	public static final String MSG_TABLE_CREATED = "msg.table_created";
	public static final String MSG_TABLES_DROPPED_SUCCESSFULLY = "msg.tables_dropped_successfully";
	public static final String MSG_TABLES_TRUNCATE_SUCCESSFULLY = "msg.tables_truncate_successfully";
	public static final String MSG_TRIGGER_ALREADY_EXISTED = "msg.trigger_already_existed";
	public static final String MSG_TRIGGER_CREATE_SUCCESS = "msg.trigger_create_success";
	public static final String MSG_TRIGGER_DROP_SUCCESS = "msg.trigger_drop_success";
	public static final String MSG_USER_DROP_SUCCESS = "msg.user_drop_success";
	public static final String MSG_VIEW_ALREADY_EXISTED = "msg.view_already_existed";
	public static final String MSG_VIEW_CREATED = "msg.view_created";
	public static final String MSG_VIEW_DROPPED_SUCCESSFULLY = "msg.view_dropped_successfully";

	public static final String VAL_DAYS = "val.days";
	public static final String VAL_HOURS = "val.hours";
	public static final String VAL_MINS = "val.mins";
	public static final String VAL_SECS = "val.secs";

	public static final String JSP_COMMON_ERROR = "common/Error.jsp";
	public static final String JSP_COMMON_HOME = "common/Home.jsp";
	public static final String JSP_COMMON_INSTALL = "common/Install.jsp";
	public static final String JSP_COMMON_LOGIN = "common/Login.jsp";
	public static final String JSP_COMMON_UNINSTALL = "common/Uninstall.jsp";
	public static final String JSP_DATABASE_EVENT_CREATEEVENT = "database/event/CreateEvent.jsp";
	public static final String JSP_DATABASE_EVENT_EVENTS = "database/event/Events.jsp";
	public static final String JSP_DATABASE_EXPORT_EXPORT = "database/export/Export.jsp";
	public static final String JSP_DATABASE_EXPORT_IMPORT = "database/export/Import.jsp";
	public static final String JSP_DATABASE_EXPORT_IMPORT_RESULT = "database/export/ImportResult.jsp";
	public static final String JSP_DATABASE_ROUTINE_CREATEFUNCTION = "database/routine/CreateFunction.jsp";
	public static final String JSP_DATABASE_ROUTINE_CREATEPROCEDURE = "database/routine/CreateProcedure.jsp";
	public static final String JSP_DATABASE_ROUTINE_FUNCTIONS = "database/routine/Functions.jsp";
	public static final String JSP_DATABASE_ROUTINE_PROCEDURES = "database/routine/Procedures.jsp";
	public static final String JSP_DATABASE_STRUCTURE_CREATE_TABLE = "database/structure/CreateTable.jsp";
	public static final String JSP_DATABASE_STRUCTURE_CREATE_VIEW = "database/structure/CreateView.jsp";
	public static final String JSP_DATABASE_STRUCTURE_TABLES = "database/structure/Tables.jsp";
	public static final String JSP_DATABASE_STRUCTURE_VIEWS = "database/structure/Views.jsp";
	public static final String JSP_DATABASE_SQL_SQL = "database/sql/SQL.jsp";
	public static final String JSP_DATABASE_TRIGGER_CREATETRIGGER = "database/trigger/CreateTrigger.jsp";
	public static final String JSP_DATABASE_TRIGGER_TRIGGERS = "database/trigger/Triggers.jsp";
	public static final String JSP_DATABASE_USERS_COLUMNPRIVILEGES = "database/users/ColumnPrivileges.jsp";
	public static final String JSP_DATABASE_USERS_ROUTINEPRIVILEGES = "database/users/RoutinePrivileges.jsp";
	public static final String JSP_DATABASE_USERS_TABLEPRIVILEGES = "database/users/TablePrivileges.jsp";
	public static final String JSP_DATABASE_USERS_USERS = "database/users/Users.jsp";
	public static final String JSP_SERVER_COMMON_CHARSETLIST = "server/common/CharsetList.jsp";
	public static final String JSP_SERVER_COMMON_ENGINELIST = "server/common/EngineList.jsp";
	public static final String JSP_SERVER_COMMON_PLUGINLIST = "server/common/PluginList.jsp";
	public static final String JSP_SERVER_COMMON_STATUSLIST = "server/common/StatusList.jsp";
	public static final String JSP_SERVER_COMMON_VARIABLELIST = "server/common/VariableList.jsp";
	public static final String JSP_SERVER_DATABASE_DATABASELIST = "server/database/DatabaseList.jsp";
	public static final String JSP_SERVER_EXPORT_EXPORT = "server/export/Export.jsp";
	public static final String JSP_SERVER_EXPORT_IMPORT = "server/export/Import.jsp";
	public static final String JSP_SERVER_EXPORT_IMPORT_RESULT = "server/export/ImportResult.jsp";
	public static final String JSP_SERVER_SQL_SQL = "server/sql/SQL.jsp";
	public static final String JSP_SERVER_USERS_GLOBAL_PRIVILEGES = "server/users/GlobalPrivileges.jsp";
	public static final String JSP_SERVER_USERS_SCHEMA_PRIVILEGES = "server/users/SchemaPrivileges.jsp";
	public static final String JSP_SERVER_USERS_USER = "server/users/User.jsp";
	public static final String JSP_SERVER_USERS_USERS = "server/users/Users.jsp";
	public static final String JSP_TABLE_COMMON_FOREIGNKEY = "table/common/ForeignKey.jsp";
	public static final String JSP_TABLE_COMMON_INFORMATION = "table/common/Information.jsp";
	public static final String JSP_TABLE_COMMON_MAINTENANCE = "table/common/Maintenance.jsp";
	public static final String JSP_TABLE_COMMON_PARTITIONS = "table/common/Partitions.jsp";
	public static final String JSP_TABLE_DATA_DATA = "table/data/Data.jsp";
	public static final String JSP_TABLE_EXPORT_EXPORT = "table/export/Export.jsp";
	public static final String JSP_TABLE_EXPORT_IMPORT = "table/export/Import.jsp";
	public static final String JSP_TABLE_EXPORT_IMPORT_RESULT = "table/export/ImportResult.jsp";
	public static final String JSP_TABLE_INSERT_INSERTUPDATE = "table/insert/InsertUpdate.jsp";
	public static final String JSP_TABLE_STRUCTURE_ALTER_TABLE = "table/structure/AlterTable.jsp";
	public static final String JSP_TABLE_STRUCTURE_STRUCTURE = "table/structure/Structure.jsp";
	public static final String JSP_TABLE_SQL_SQL = "table/sql/SQL.jsp";
	public static final String JSP_VIEW_DATA_DATA = "view/data/Data.jsp";
	public static final String JSP_VIEW_EXPORT_EXPORT = "view/export/Export.jsp";
	public static final String JSP_VIEW_EXPORT_IMPORT = "view/export/Import.jsp";
	public static final String JSP_VIEW_EXPORT_IMPORT_RESULT = "view/export/ImportResult.jsp";
	public static final String JSP_VIEW_STRUCTURE_STRUCTURE = "view/structure/Structure.jsp";
	public static final String JSP_VIEW_SQL_SQL = "view/sql/SQL.jsp";

	public static final String PATH_HOME = "/home.html";
	public static final String PATH_INSTALL = "/install.html";
	public static final String PATH_DATABASE_IMPORT = "/database_import.html";
	public static final String PATH_DATABASE_EVENTS = "/database_events.html";
	public static final String PATH_DATABASE_FUNCTIONS = "/database_functions.html";
	public static final String PATH_DATABASE_PRIVILEGES = "/database_privileges.html";
	public static final String PATH_DATABASE_PROCEDURES = "/database_procedures.html";
	public static final String PATH_DATABASE_COLUMN_PRIVILEGES = "/database_column_privileges.html";
	public static final String PATH_DATABASE_ROUTINE_PRIVILEGES = "/database_routine_privileges.html";
	public static final String PATH_DATABASE_SQL = "/database_sql.html";
	public static final String PATH_DATABASE_TABLE_PRIVILEGES = "/database_table_privileges.html";
	public static final String PATH_DATABASE_TRIGGERS = "/database_triggers.html";
	public static final String PATH_DATABASE_STRUCTURE = "/database_structure.html";
	public static final String PATH_DATABASE_VIEW_LIST = "/database_view_list.html";
	public static final String PATH_LOGIN = "/login.html";
	public static final String PATH_SERVER_DATABASES = "/server_databases.html";
	public static final String PATH_SERVER_GLOBAL_PRIVILEGES = "/server_global_privileges.html";
	public static final String PATH_SERVER_IMPORT = "/server_import.html";
	public static final String PATH_SERVER_SCHEMA_PRIVILEGES = "/server_schema_privileges.html";
	public static final String PATH_SERVER_USER_INFO = "/server_user_info.html";
	public static final String PATH_SERVER_USERS = "/server_users.html";
	public static final String PATH_TABLE_DATA = "/table_data.html";
	public static final String PATH_TABLE_FOREIGN_KEYS = "/table_foreign_keys.html";
	public static final String PATH_TABLE_IMPORT = "/table_import.html";
	public static final String PATH_TABLE_PARTITIONS = "/table_partitions.html";
	public static final String PATH_TABLE_STRUCTURE = "/table_structure.html";
	public static final String PATH_UNINSTALL = "/uninstall.html";
	public static final String PATH_VIEW_IMPORT = "/view_import.html";

}
