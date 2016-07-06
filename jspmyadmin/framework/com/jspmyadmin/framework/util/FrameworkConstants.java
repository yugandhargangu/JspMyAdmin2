/**
 * 
 */
package com.jspmyadmin.framework.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/01/27
 *
 */
public final class FrameworkConstants {

	private FrameworkConstants() {
		// prevent from instantiation
	}

	public static final String ADD = "add";
	public static final String APP_DATA_TYPES_INFO = "data_types_info";
	public static final String BLANK = "";
	public static final String BASE_TABLE = "BASE TABLE";
	public static final String BYTE_TYPE = byte[].class.getName();
	public static final String COLUMN = "column";
	public static final String COMMAND = "command";
	public static final String CONTENT_TYPE_TEXT_PLAIN = "text/plain";
	public static final String COPY = "copy";
	public static final String CURRENT_TIMESTAMP = "CURRENT_TIMESTAMP";
	public static final String CURRENT_TIMESTAMP1 = "CURRENT_TIMESTA1MP";
	public static final String CURRENT_USER = "CURRENT_USER";
	public static final String DATA = "data";
	public static final String DATABASE = "database";
	public static final String DATABASE_NULL = "(null)";
	public static final String DATABASE_BLOB = "[BLOB] - ";
	public static final String DATE_FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
	public static final String ENCODE_UTF8 = "UTF-8";
	public static final String ERR = "err";
	public static final String ERR_KEY = "err_key";
	public static final String EVENT = "event";
	public static final String FK = "fk_";
	public static final String GET = "get";
	public static final String HOSTNAME = "hostname";
	public static final String JSP_ROOT = "/WEB-INF/views/";
	public static final String LIMIT = "limit";
	public static final String MSG = "msg";
	public static final String MSG_KEY = "msg_key";
	public static final String NAME = "name";
	public static final String NEW_LINE = "\\n";
	public static final String NO = "No";
	public static final String NON_ALPHA_NUM = "[^A-Za-z0-9 ]";
	public static final String ONE = "1";
	public static final String PAGE = "page";
	public static final String PAGE_CONTEXT_MESSAGES = "messages";
	public static final String REMOVE = "remove";
	public static final String REPLACE = "replace";
	public static final String REQUEST = "request";
	public static final String SEARCH = "search";
	public static final String SESSION = "session";
	public static final String SESSION_DB = "session_db";
	public static final String SESSION_FLASH_MAP = "flash_map";
	public static final String SESSION_FONTSIZE = "fontsize";
	public static final String SESSION_HOST = "session_host";
	public static final String SESSION_LOCALE = "session_locale";
	public static final String SESSION_KEY = "session_key";
	public static final String SESSION_PASS = "session_pass";
	public static final String SESSION_PORT = "session_port";
	public static final String SESSION_TABLE = "session_table";
	public static final String SESSION_TOKEN = "session_token";
	public static final String SESSION_USER = "session_user";
	public static final String SET = "set";
	public static final String SPACE = " ";
	public static final String SORT_BY = "sort_by";
	public static final String SYMBOL_AT = "@";
	public static final String SYMBOL_BACK_SLASH = "/";
	public static final String SYMBOL_BRACKET_CLOSE = ")";
	public static final String SYMBOL_BRACKET_OPEN = "(";
	public static final String SYMBOL_COLON = ":";
	public static final String SYMBOL_COMMA = ",";
	public static final String SYMBOL_DELIMETER = "//";
	public static final String SYMBOL_DOT = ".";
	public static final String SYMBOL_HASH = "#";
	public static final String SYMBOL_HIFEN = "-";
	public static final String SYMBOL_QUOTE = "'";
	public static final String SYMBOL_QUOTE_ESCAPE = "\'";
	public static final String SYMBOL_SEMI_COLON = ";";
	public static final String SYMBOL_TEN = "`";
	public static final String SYMBOL_TOKEN = "?token=";
	public static final String SYMBOL_UNDERSCORE = "_";
	public static final String TABLE = "table";
	public static final String TEMP_DIR = System.getProperty("java.io.tmpdir") + "/jspmyadmin/temp/";
	public static final String TYPE = "type";
	public static final String TOKEN = "token";
	public static final String VIEW = "view";
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
	}

}
