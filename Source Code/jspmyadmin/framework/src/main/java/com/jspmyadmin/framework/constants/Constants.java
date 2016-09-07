/**
 * 
 */
package com.jspmyadmin.framework.constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/01/27
 *
 */
public final class Constants {

	private Constants() {
		// prevent from instantiation
	}

	public static final String ADD = "add";
	public static final String APP_DATA_TYPES_INFO = "data_types_info";
	public static final String AT = "AT";
	public static final String AUTO_INCREMENT = "auto_increment";
	public static final String BLANK = "";
	public static final String BASE_TABLE = "BASE TABLE";
	public static final String BINARY = "BINARY";
	public static final String BYTE_TYPE = byte[].class.getName();
	public static final String CHARSET = "charset";
	public static final String CHARACTER_SET_SERVER = "character_set_server";
	public static final String COLLATION_SERVER = "collation_server";
	public static final String COLUMN = "column";
	public static final String COLUMN_NAME = "COLUMN_NAME";
	public static final String COMMAND = "command";
	public static final String CONTENT_TYPE_TEXT_PLAIN = "text/plain";
	public static final String COPY = "copy";
	public static final String COUNT = "count";
	public static final String CSV = "CSV";
	public static final String CURRENT_TIMESTAMP = "CURRENT_TIMESTAMP";
	public static final String CURRENT_TIMESTAMP1 = "CURRENT_TIMESTA1MP";
	public static final String CURRENT_USER = "CURRENT_USER";
	public static final String DATA = "data";
	public static final String DATABASE = "database";
	public static final String DATABASE_NULL = "(null)";
	public static final String DATABASE_BLOB = "[BLOB] - ";
	public static final String DATE_FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
	public static final String DELIMITER_$$ = "DELIMITER $$";
	public static final String DELIMITER_COMMA = "DELIMITER ;";
	public static final String DEFAULT_LOCALE = "en";
	public static final String ENCODE_UTF8 = "UTF-8";
	public static final String ERR = "err";
	public static final String ERR_KEY = "err_key";
	public static final String EVENT = "event";
	public static final String EVERY = "EVERY";
	public static final String FIELD = "field";
	public static final String FILE_EXT_SQL = ".sql";
	public static final String FK = "fk_";
	public static final String FUNCTION = "FUNCTION";
	public static final String GET = "get";
	public static final String GRANT_OPTION = "GRANT OPTION";
	public static final String HOSTNAME = "hostname";
	public static final String JSON = "JSON";
	public static final String JSPMYADMIN_EXPORT = "jspmyadmin_export";
	public static final String JSP_ROOT = "/WEB-INF/views/";
	public static final String LIMIT = "25";
	public static final String METADATA = "metadata";
	public static final String MSG = "msg";
	public static final String MSG_KEY = "msg_key";
	public static final String MULTIPART_FORM_DATA = "multipart/form-data";
	public static final String NAME = "name";
	public static final String NEW_ADD = "new_add";
	public static final String NEW_LINE = "\r\n";
	public static final String NO = "No";
	public static final String NON_ALPHA_NUM = "[^A-Za-z0-9 ]";
	public static final String NULL = "NULL";
	public static final String NUMBER = "NUMBER";
	public static final String ONE = "1";
	public static final String ONE_LINE_COMMENT = "--";
	public static final String ONE_LINE_SEPARATOR = "-- ------------------------------------------------------------------";
	public static final String PAGE = "page";
	public static final String PAGE_CONTEXT_MESSAGES = "messages";
	public static final String PK_VAL = "pk_val";
	public static final String PROCEDURE = "PROCEDURE";
	public static final String PROTOCOL_VERSION = "protocol_version";
	public static final String PROXY = "PROXY";
	public static final String QUERY = "query";
	public static final String REGEX_$$ = "\\$\\$";
	public static final String REGEX_NEW_LINE = "\\r\\n";
	public static final String REMOVE = "remove";
	public static final String REPLACE = "replace";
	public static final String REQUEST = "request";
	public static final String REQUEST_DB = "request_db";
	public static final String REQUEST_VIEW = "request_view";
	public static final String REQUEST_TABLE = "request_table";
	public static final String ROW = "row";
	public static final String SEARCH = "search";
	public static final String SESSION = "session";
	public static final String SESSION_CONNECT = "session_connect";
	public static final String SESSION_FLASH_MAP = "flash_map";
	public static final String SESSION_FONTSIZE = "fontsize";
	public static final String SESSION_HOST = "session_host";
	public static final String SESSION_LOCALE = "session_locale";
	public static final String SESSION_KEY = "session_key";
	public static final String SESSION_PASS = "session_pass";
	public static final String SESSION_PORT = "session_port";
	public static final String SESSION_REDIRECT_PARAM = "redirect_param";
	public static final String SESSION_TOKEN = "session_token";
	public static final String SESSION_USER = "session_user";
	public static final String SET = "set";
	public static final String FETCH_LIMIT = "25";
	public static final String SHOW_SEARCH = "show_search";
	public static final String SPACE = " ";
	public static final String SORT_BY = "sort_by";
	public static final String STRING = "STRING";
	public static final String SYMBOL_$$ = "$$";
	public static final String SYMBOL_AT = "@";
	public static final String SYMBOL_BACK_SLASH = "/";
	public static final String SYMBOL_BRACKET_CLOSE = ")";
	public static final String SYMBOL_BRACKET_OPEN = "(";
	public static final String SYMBOL_COLON = ":";
	public static final String SYMBOL_COMMA = ",";
	public static final String SYMBOL_DELIMETER = "//";
	public static final String SYMBOL_DOT = ".";
	public static final String SYMBOL_DOT_EXPR = "\\.";
	public static final String SYMBOL_DOUBLE_QUOTE = "\"";
	public static final String SYMBOL_HASH = "#";
	public static final String SYMBOL_HIFEN = "-";
	public static final String SYMBOL_HIFEN_HIFEN = "--";
	public static final String SYMBOL_QUOTE = "'";
	public static final String SYMBOL_QUOTE_ESCAPE = "\'";
	public static final String SYMBOL_SEMI_COLON = ";";
	public static final String SYMBOL_SQL_MULTI_START = "/*";
	public static final String SYMBOL_SQL_MULTI_END = "*/";
	public static final String SYMBOL_TEN = "`";
	public static final String SYMBOL_TOKEN = "?token=";
	public static final String SYMBOL_UNDERSCORE = "_";
	public static final String SYMBOL_HEX = "0x";
	public static final String TABLE = "table";
	public static final String TEMP_DIR = System.getProperty("java.io.tmpdir") + "/jspmyadmin/temp/";
	public static final String THREE = "3";
	public static final String TRIGGER = "trigger";
	public static final String TWO = "2";
	public static final String TYPE = "type";
	public static final String TOKEN = "token";
	public static final String USER = "user";
	public static final String VERSION = "version";
	public static final String VERSION_COMMENT = "version_comment";
	public static final String VIEW = "view";
	public static final String VIEW_UPPER_CASE = "VIEW";
	public static final String XML = "XML";
	public static final String YES = "Yes";
	public static final String ZERO = "0";

	public static class Utils {

		private Utils() {
			// prevent from instantiation
		}

		public static final Map<String, List<String>> DATA_TYPES_MAP = Collections
				.synchronizedMap(new LinkedHashMap<String, List<String>>());

		static {
			List<String> dataTypeList = new ArrayList<String>();
			dataTypeList.add("INT");
			dataTypeList.add("VARCHAR");
			dataTypeList.add("DECIMAL");
			dataTypeList.add("TIMESTAMP");
			DATA_TYPES_MAP.put("", dataTypeList);
			dataTypeList = new ArrayList<String>();
			dataTypeList.add("TINYINT");
			dataTypeList.add("SMALLINT");
			dataTypeList.add("MEDIUMINT");
			dataTypeList.add("INT");
			dataTypeList.add("BIGINT");
			dataTypeList.add("FLOAT");
			dataTypeList.add("DOUBLE");
			dataTypeList.add("DECIMAL");
			dataTypeList.add("FLOAT");
			DATA_TYPES_MAP.put("Numeric", dataTypeList);
			dataTypeList = new ArrayList<String>();
			dataTypeList.add("BIT");
			DATA_TYPES_MAP.put("Bit", dataTypeList);
			dataTypeList = new ArrayList<String>();
			dataTypeList.add("CHAR");
			dataTypeList.add("VARCHAR");
			dataTypeList.add("TINYTEXT");
			dataTypeList.add("TEXT");
			dataTypeList.add("MEDIUMTEXT");
			dataTypeList.add("LONGTEXT");
			dataTypeList.add("BINARY");
			dataTypeList.add("VARBINARY");
			dataTypeList.add("TINYBLOB");
			dataTypeList.add("BLOB");
			dataTypeList.add("MEDIUMBLOB");
			dataTypeList.add("LONGBLOB");
			dataTypeList.add("ENUM");
			dataTypeList.add("SET");
			DATA_TYPES_MAP.put("String", dataTypeList);
			dataTypeList.add("DATE");
			dataTypeList.add("DATETIME");
			dataTypeList.add("TIME");
			dataTypeList.add("TIMESTAMP");
			dataTypeList.add("YEAR");
			DATA_TYPES_MAP.put("Date & Time", dataTypeList);
		}

		public static final String DATA_TYPES_INFO = _getDataTypesInfo();

		/**
		 * 
		 * @return
		 */
		private static String _getDataTypesInfo() {
			String result = null;
			JSONObject jsonObjectMain = new JSONObject();
			JSONObject jsonObject = null;
			String a = "a"; // has list
			String b = "b"; // has length -1:no length, 0:length1 and mandatory,
							// 1:lenght2 and mandatory,2:length1 and not
							// mandatory,3:length2 and not mandatory
			String c = "c"; // auto increment
			String d = "d"; // unsigned
			String e = "e"; // zero fill
			String f = "f"; // binary
			String g = "g"; // default value -1:list
			String h = "h"; // character set
			try {
				jsonObject = new JSONObject();
				jsonObject.put(a, 0);
				jsonObject.put(b, 2);
				jsonObject.put(c, 1);
				jsonObject.put(d, 1);
				jsonObject.put(e, 1);
				jsonObject.put(f, 0);
				jsonObject.put(h, 0);
				jsonObject.put(g, ZERO);
				jsonObject.put(h, 0);
				jsonObjectMain.put("TINYINT", jsonObject);

				jsonObject = new JSONObject();
				jsonObject.put(a, 0);
				jsonObject.put(b, 2);
				jsonObject.put(c, 1);
				jsonObject.put(d, 1);
				jsonObject.put(e, 1);
				jsonObject.put(f, 0);
				jsonObject.put(g, ZERO);
				jsonObject.put(h, 0);
				jsonObjectMain.put("SMALLINT", jsonObject);

				jsonObject = new JSONObject();
				jsonObject.put(a, 0);
				jsonObject.put(b, 2);
				jsonObject.put(c, 1);
				jsonObject.put(d, 1);
				jsonObject.put(e, 1);
				jsonObject.put(f, 0);
				jsonObject.put(g, ZERO);
				jsonObject.put(h, 0);
				jsonObjectMain.put("MEDIUMINT", jsonObject);

				jsonObject = new JSONObject();
				jsonObject.put(a, 0);
				jsonObject.put(b, 2);
				jsonObject.put(c, 1);
				jsonObject.put(d, 1);
				jsonObject.put(e, 1);
				jsonObject.put(f, 0);
				jsonObject.put(g, ZERO);
				jsonObject.put(h, 0);
				jsonObjectMain.put("INT", jsonObject);

				jsonObject = new JSONObject();
				jsonObject.put(a, 0);
				jsonObject.put(b, 2);
				jsonObject.put(c, 1);
				jsonObject.put(d, 1);
				jsonObject.put(e, 1);
				jsonObject.put(f, 0);
				jsonObject.put(g, ZERO);
				jsonObject.put(h, 0);
				jsonObjectMain.put("BIGINT", jsonObject);

				jsonObject = new JSONObject();
				jsonObject.put(a, 0);
				jsonObject.put(b, 1);
				jsonObject.put(c, 0);
				jsonObject.put(d, 1);
				jsonObject.put(e, 1);
				jsonObject.put(f, 0);
				jsonObject.put(g, ZERO);
				jsonObject.put(h, 0);
				jsonObjectMain.put("FLOAT", jsonObject);

				jsonObject = new JSONObject();
				jsonObject.put(a, 0);
				jsonObject.put(b, 1);
				jsonObject.put(c, 0);
				jsonObject.put(d, 1);
				jsonObject.put(e, 1);
				jsonObject.put(f, 0);
				jsonObject.put(g, ZERO);
				jsonObject.put(h, 0);
				jsonObjectMain.put("DOUBLE", jsonObject);

				jsonObject = new JSONObject();
				jsonObject.put(a, 0);
				jsonObject.put(b, 3);
				jsonObject.put(c, 0);
				jsonObject.put(d, 0);
				jsonObject.put(e, 0);
				jsonObject.put(f, 0);
				jsonObject.put(g, ZERO);
				jsonObject.put(h, 0);
				jsonObjectMain.put("DECIMAL", jsonObject);

				jsonObject = new JSONObject();
				jsonObject.put(a, 0);
				jsonObject.put(b, 0);
				jsonObject.put(c, 0);
				jsonObject.put(d, 0);
				jsonObject.put(e, 0);
				jsonObject.put(f, 0);
				jsonObject.put(g, ZERO);
				jsonObject.put(h, 0);
				jsonObjectMain.put("BIT", jsonObject);

				jsonObject = new JSONObject();
				jsonObject.put(a, 0);
				jsonObject.put(b, 0);
				jsonObject.put(c, 0);
				jsonObject.put(d, 0);
				jsonObject.put(e, 0);
				jsonObject.put(f, 1);
				jsonObject.put(g, BLANK);
				jsonObject.put(h, 1);
				jsonObjectMain.put("CHAR", jsonObject);

				jsonObject = new JSONObject();
				jsonObject.put(a, 0);
				jsonObject.put(b, 0);
				jsonObject.put(c, 0);
				jsonObject.put(d, 0);
				jsonObject.put(e, 0);
				jsonObject.put(f, 1);
				jsonObject.put(g, BLANK);
				jsonObject.put(h, 1);
				jsonObjectMain.put("VARCHAR", jsonObject);

				jsonObject = new JSONObject();
				jsonObject.put(a, 0);
				jsonObject.put(b, 2);
				jsonObject.put(c, 0);
				jsonObject.put(d, 0);
				jsonObject.put(e, 0);
				jsonObject.put(f, 1);
				jsonObject.put(g, BLANK);
				jsonObject.put(h, 1);
				jsonObjectMain.put("TINYTEXT", jsonObject);

				jsonObject = new JSONObject();
				jsonObject.put(a, 0);
				jsonObject.put(b, 2);
				jsonObject.put(c, 0);
				jsonObject.put(d, 0);
				jsonObject.put(e, 0);
				jsonObject.put(f, 1);
				jsonObject.put(g, BLANK);
				jsonObject.put(h, 1);
				jsonObjectMain.put("TEXT", jsonObject);

				jsonObject = new JSONObject();
				jsonObject.put(a, 0);
				jsonObject.put(b, 2);
				jsonObject.put(c, 0);
				jsonObject.put(d, 0);
				jsonObject.put(e, 0);
				jsonObject.put(f, 1);
				jsonObject.put(g, BLANK);
				jsonObject.put(h, 1);
				jsonObjectMain.put("MEDIUMTEXT", jsonObject);

				jsonObject = new JSONObject();
				jsonObject.put(a, 0);
				jsonObject.put(b, 2);
				jsonObject.put(c, 0);
				jsonObject.put(d, 0);
				jsonObject.put(e, 0);
				jsonObject.put(f, 1);
				jsonObject.put(g, BLANK);
				jsonObject.put(h, 1);
				jsonObjectMain.put("LONGTEXT", jsonObject);

				jsonObject = new JSONObject();
				jsonObject.put(a, 0);
				jsonObject.put(b, 0);
				jsonObject.put(c, 0);
				jsonObject.put(d, 0);
				jsonObject.put(e, 0);
				jsonObject.put(f, 0);
				jsonObject.put(g, BLANK);
				jsonObject.put(h, 0);
				jsonObjectMain.put("BINARY", jsonObject);

				jsonObject = new JSONObject();
				jsonObject.put(a, 0);
				jsonObject.put(b, 0);
				jsonObject.put(c, 0);
				jsonObject.put(d, 0);
				jsonObject.put(e, 0);
				jsonObject.put(f, 0);
				jsonObject.put(g, BLANK);
				jsonObject.put(h, 0);
				jsonObjectMain.put("VARBINARY", jsonObject);

				jsonObject = new JSONObject();
				jsonObject.put(a, 0);
				jsonObject.put(b, 2);
				jsonObject.put(c, 0);
				jsonObject.put(d, 0);
				jsonObject.put(e, 0);
				jsonObject.put(f, 0);
				jsonObject.put(g, BLANK);
				jsonObject.put(h, 0);
				jsonObjectMain.put("TINYBLOB", jsonObject);

				jsonObject = new JSONObject();
				jsonObject.put(a, 0);
				jsonObject.put(b, 2);
				jsonObject.put(c, 0);
				jsonObject.put(d, 0);
				jsonObject.put(e, 0);
				jsonObject.put(f, 0);
				jsonObject.put(g, BLANK);
				jsonObject.put(h, 0);
				jsonObjectMain.put("BLOB", jsonObject);

				jsonObject = new JSONObject();
				jsonObject.put(a, 0);
				jsonObject.put(b, 2);
				jsonObject.put(c, 0);
				jsonObject.put(d, 0);
				jsonObject.put(e, 0);
				jsonObject.put(f, 0);
				jsonObject.put(g, BLANK);
				jsonObject.put(h, 0);
				jsonObjectMain.put("MEDIUMBLOB", jsonObject);

				jsonObject = new JSONObject();
				jsonObject.put(a, 0);
				jsonObject.put(b, 2);
				jsonObject.put(c, 0);
				jsonObject.put(d, 0);
				jsonObject.put(e, 0);
				jsonObject.put(f, 0);
				jsonObject.put(g, BLANK);
				jsonObject.put(h, 0);
				jsonObjectMain.put("LONGBLOB", jsonObject);

				jsonObject = new JSONObject();
				jsonObject.put(a, 1);
				jsonObject.put(b, -1);
				jsonObject.put(c, 0);
				jsonObject.put(d, 0);
				jsonObject.put(e, 0);
				jsonObject.put(f, 0);
				jsonObject.put(g, -1);
				jsonObject.put(h, 1);
				jsonObjectMain.put("ENUM", jsonObject);

				jsonObject = new JSONObject();
				jsonObject.put(a, 1);
				jsonObject.put(b, -1);
				jsonObject.put(c, 0);
				jsonObject.put(d, 0);
				jsonObject.put(e, 0);
				jsonObject.put(f, 0);
				jsonObject.put(g, -1);
				jsonObject.put(h, 1);
				jsonObjectMain.put("SET", jsonObject);

				jsonObject = new JSONObject();
				jsonObject.put(a, 0);
				jsonObject.put(b, -1);
				jsonObject.put(c, 0);
				jsonObject.put(d, 0);
				jsonObject.put(e, 0);
				jsonObject.put(f, 0);
				jsonObject.put(g, "0000-00-00");
				jsonObject.put(h, 1);
				jsonObjectMain.put("DATE", jsonObject);

				jsonObject = new JSONObject();
				jsonObject.put(a, 0);
				jsonObject.put(b, -1);
				jsonObject.put(c, 0);
				jsonObject.put(d, 0);
				jsonObject.put(e, 0);
				jsonObject.put(f, 0);
				jsonObject.put(g, "0000-00-00 00:00:00");
				jsonObject.put(h, 1);
				jsonObjectMain.put("DATETIME", jsonObject);

				jsonObject = new JSONObject();
				jsonObject.put(a, 0);
				jsonObject.put(b, -1);
				jsonObject.put(c, 0);
				jsonObject.put(d, 0);
				jsonObject.put(e, 0);
				jsonObject.put(f, 0);
				jsonObject.put(g, "00:00:00");
				jsonObject.put(h, 1);
				jsonObjectMain.put("TIME", jsonObject);

				jsonObject = new JSONObject();
				jsonObject.put(a, 0);
				jsonObject.put(b, -1);
				jsonObject.put(c, 0);
				jsonObject.put(d, 0);
				jsonObject.put(e, 0);
				jsonObject.put(f, 0);
				jsonObject.put(g, CURRENT_TIMESTAMP);
				jsonObject.put(h, 1);
				jsonObjectMain.put("TIMESTAMP", jsonObject);

				jsonObject = new JSONObject();
				jsonObject.put(a, 0);
				jsonObject.put(b, -1);
				jsonObject.put(c, 0);
				jsonObject.put(d, 0);
				jsonObject.put(e, 0);
				jsonObject.put(f, 0);
				jsonObject.put(g, "0000");
				jsonObject.put(h, 1);
				jsonObjectMain.put("YEAR", jsonObject);

				result = jsonObjectMain.toString();
			} catch (JSONException exception) {
			} finally {
				jsonObjectMain = null;
			}
			return result;
		}

		public static final List<String> PARTITION_LIST = Collections.synchronizedList(new ArrayList<String>());

		static {
			PARTITION_LIST.add("RANGE");
			PARTITION_LIST.add("LIST");
			PARTITION_LIST.add("RANGE COLUMNS");
			PARTITION_LIST.add("LIST COLUMNS");
			PARTITION_LIST.add("HASH");
			PARTITION_LIST.add("LINEAR HASH");
			PARTITION_LIST.add("KEY");
		}

		public static final List<String> BLOB_LIST = Collections.synchronizedList(new ArrayList<String>());

		static {
			BLOB_LIST.add("TINYBLOB");
			BLOB_LIST.add("BLOB");
			BLOB_LIST.add("MEDIUMBLOB");
			BLOB_LIST.add("LONGBLOB");
		}

		public static final List<String> ACTION_LIST = new ArrayList<String>(4);

		static {
			ACTION_LIST.add("RESTRICT");
			ACTION_LIST.add("CASCADE");
			ACTION_LIST.add("SET NULL");
			ACTION_LIST.add("NO ACTION");
		}

		public static final List<String> PRIVILEGE_OBJ_LIST = new ArrayList<String>(6);

		static {
			PRIVILEGE_OBJ_LIST.add("SELECT");
			PRIVILEGE_OBJ_LIST.add("INSERT");
			PRIVILEGE_OBJ_LIST.add("UPDATE");
			PRIVILEGE_OBJ_LIST.add("DELETE");
			PRIVILEGE_OBJ_LIST.add("EXECUTE");
			PRIVILEGE_OBJ_LIST.add("SHOW VIEW");
		}

		public static final List<String> PRIVILEGE_DDL_LIST = new ArrayList<String>(10);

		static {
			PRIVILEGE_DDL_LIST.add("CREATE");
			PRIVILEGE_DDL_LIST.add("ALTER");
			PRIVILEGE_DDL_LIST.add("REFERENCES");
			PRIVILEGE_DDL_LIST.add("INDEX");
			PRIVILEGE_DDL_LIST.add("CREATE VIEW");
			PRIVILEGE_DDL_LIST.add("CREATE ROUTINE");
			PRIVILEGE_DDL_LIST.add("ALTER ROUTINE");
			PRIVILEGE_DDL_LIST.add("EVENT");
			PRIVILEGE_DDL_LIST.add("DROP");
			PRIVILEGE_DDL_LIST.add("TRIGGER");
		}

		public static final List<String> PRIVILEGE_ADMN_LIST = new ArrayList<String>(3);

		static {
			PRIVILEGE_ADMN_LIST.add("GRANT OPTION");
			PRIVILEGE_ADMN_LIST.add("CREATE TEMPORARY TABLES");
			PRIVILEGE_ADMN_LIST.add("LOCK TABLES");
		}

		public static final List<String> PRIVILEGE_TABLE_LIST = new ArrayList<String>(13);

		static {
			PRIVILEGE_TABLE_LIST.add("CREATE");
			PRIVILEGE_TABLE_LIST.add("ALTER");
			PRIVILEGE_TABLE_LIST.add("DROP");
			PRIVILEGE_TABLE_LIST.add("CREATE VIEW");
			PRIVILEGE_TABLE_LIST.add("INDEX");
			PRIVILEGE_TABLE_LIST.add("TRIGGER");
			PRIVILEGE_TABLE_LIST.add("REFERENCES");
			PRIVILEGE_TABLE_LIST.add("SELECT");
			PRIVILEGE_TABLE_LIST.add("INSERT");
			PRIVILEGE_TABLE_LIST.add("UPDATE");
			PRIVILEGE_TABLE_LIST.add("DELETE");
			PRIVILEGE_TABLE_LIST.add("SHOW VIEW");
			PRIVILEGE_TABLE_LIST.add("GRANT OPTION");
		}

		public static final List<String> PRIVILEGE_COLUMN_LIST = new ArrayList<String>(4);

		static {
			PRIVILEGE_COLUMN_LIST.add("SELECT");
			PRIVILEGE_COLUMN_LIST.add("INSERT");
			PRIVILEGE_COLUMN_LIST.add("UPDATE");
			PRIVILEGE_COLUMN_LIST.add("REFERENCES");
		}

		public static final List<String> PRIVILEGE_ROUTINE_LIST = new ArrayList<String>(3);

		static {
			PRIVILEGE_ROUTINE_LIST.add("ALTER ROUTINE");
			PRIVILEGE_ROUTINE_LIST.add("EXECUTE");
			PRIVILEGE_ROUTINE_LIST.add("GRANT OPTION");
		}

		public static final List<String> CHECK_OP_LIST = new ArrayList<String>(6);

		static {
			CHECK_OP_LIST.add("FOR UPGRADE");
			CHECK_OP_LIST.add("QUICK");
			CHECK_OP_LIST.add("FAST");
			CHECK_OP_LIST.add("MEDIUM");
			CHECK_OP_LIST.add("EXTENDED");
			CHECK_OP_LIST.add("EXTENDED");
		}

		public static final List<String> ANALIZE_OP_LIST = new ArrayList<String>(2);

		static {
			ANALIZE_OP_LIST.add("NO_WRITE_TO_BINLOG");
			ANALIZE_OP_LIST.add("LOCAL");
		}

		public static final List<String> CHECKSUM_OP_LIST = new ArrayList<String>(2);

		static {
			CHECKSUM_OP_LIST.add("QUICK");
			CHECKSUM_OP_LIST.add("EXTENDED");
		}

		public static final List<String> REPAIR_OP_LIST = new ArrayList<String>(3);

		static {
			REPAIR_OP_LIST.add("QUICK");
			REPAIR_OP_LIST.add("EXTENDED");
			REPAIR_OP_LIST.add("USE_FRM");
		}

		public static final Map<String, String> LANGUAGE_MAP = Collections
				.synchronizedMap(new LinkedHashMap<String, String>(1));

		static {
			LANGUAGE_MAP.put("en", "English");
		}

		public static final List<String> EVENT_INTERVAL_LIST = new ArrayList<String>(15);

		static {
			EVENT_INTERVAL_LIST.add("DAY");
			EVENT_INTERVAL_LIST.add("DAY_HOUR");
			EVENT_INTERVAL_LIST.add("DAY_MINUTE");
			EVENT_INTERVAL_LIST.add("DAY_SECOND");
			EVENT_INTERVAL_LIST.add("HOUR");
			EVENT_INTERVAL_LIST.add("HOUR_MINUTE");
			EVENT_INTERVAL_LIST.add("HOUR_SECOND");
			EVENT_INTERVAL_LIST.add("MINUTE");
			EVENT_INTERVAL_LIST.add("MINUTE_SECOND");
			EVENT_INTERVAL_LIST.add("MONTH");
			EVENT_INTERVAL_LIST.add("QUARTER");
			EVENT_INTERVAL_LIST.add("SECOND");
			EVENT_INTERVAL_LIST.add("WEEK");
			EVENT_INTERVAL_LIST.add("YEAR ");
			EVENT_INTERVAL_LIST.add("YEAR_MONTH");
		}

		public static final List<String> DEFINER_LIST = new ArrayList<String>(2);

		static {
			DEFINER_LIST.add(Constants.CURRENT_USER);
			DEFINER_LIST.add("OTHER");
		}

		public static final List<String> SQL_TYPE_LIST = new ArrayList<String>(4);

		static {
			SQL_TYPE_LIST.add("CONTAINS SQL");
			SQL_TYPE_LIST.add("NO SQL");
			SQL_TYPE_LIST.add("READS SQL DATA");
			SQL_TYPE_LIST.add("MODIFIES SQL DATA");
		}

		public static final List<String> SECURITY_TYPE_LIST = new ArrayList<String>(2);

		static {
			SECURITY_TYPE_LIST.add("DEFINER");
			SECURITY_TYPE_LIST.add("INVOKER");
		}

		public static final List<String> ALGORITHM_LIST = new ArrayList<String>(3);

		static {
			ALGORITHM_LIST.add("UNDEFINED");
			ALGORITHM_LIST.add("MERGE");
			ALGORITHM_LIST.add("TEMPTABLE");
		}

		public static final List<String> VIEW_CHECK_LIST = new ArrayList<String>(2);

		static {
			VIEW_CHECK_LIST.add("CASCADED");
			VIEW_CHECK_LIST.add("LOCAL");
		}

		public static final List<String> TRIGGER_TIME_LIST = new ArrayList<String>(2);

		static {
			TRIGGER_TIME_LIST.add("BEFORE");
			TRIGGER_TIME_LIST.add("AFTER");
		}

		public static final List<String> TRIGGER_EVENT_LIST = new ArrayList<String>(3);

		static {
			TRIGGER_EVENT_LIST.add("INSERT");
			TRIGGER_EVENT_LIST.add("UPDATE");
			TRIGGER_EVENT_LIST.add("DELETE");
		}

		public static final List<String> TRIGGER_ORDER_LIST = new ArrayList<String>(2);

		static {
			TRIGGER_ORDER_LIST.add("FOLLOWS");
			TRIGGER_ORDER_LIST.add("PRECEDES");
		}

		public static final List<String> LIMIT_LIST = new ArrayList<String>(5);

		static {
			LIMIT_LIST.add("25");
			LIMIT_LIST.add("50");
			LIMIT_LIST.add("100");
			LIMIT_LIST.add("500");
			LIMIT_LIST.add("1000");
		}

		public static final List<String> EXPORT_TYPE_LIST = new ArrayList<String>(5);

		static {
			EXPORT_TYPE_LIST.add("CSV");
			EXPORT_TYPE_LIST.add("XML");
			EXPORT_TYPE_LIST.add("JSON");
		}

		public static final List<String> IGNORE_DATABASE_LIST = new CopyOnWriteArrayList<String>();

		static {
			IGNORE_DATABASE_LIST.add("information_schema");
			IGNORE_DATABASE_LIST.add("mysql");
			IGNORE_DATABASE_LIST.add("performance_schema");
		}
	}

}
