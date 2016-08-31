/**
 * 
 */
package com.jspmyadmin.app.database.routine.beans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.utils.Bean;
import com.jspmyadmin.framework.web.utils.DefaultServlet;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/02
 *
 */
public class RoutineBean extends Bean {

	private static final long serialVersionUID = 1L;

	private String name = null;
	private String definer = null;
	private String definer_name = null;
	private String[] param_types = null;
	private String[] params = null;
	private String[] param_data_types = null;
	private String[] lengths = null;
	private String return_type = null;
	private String return_length = null;
	private String comment = null;
	private String lang_sql = null;
	private String deterministic = null;
	private String sql_type = null;
	private String sql_security = null;
	private String body = null;
	private String action = null;
	private String alter = null;
	private String new_column = null;

	private List<String> definer_list = new ArrayList<String>(FrameworkConstants.Utils.DEFINER_LIST);
	private Map<String, List<String>> data_types_map = new LinkedHashMap<String, List<String>>(
			FrameworkConstants.Utils.DATA_TYPES_MAP);
	private List<String> sql_type_list = new ArrayList<String>(FrameworkConstants.Utils.SQL_TYPE_LIST);
	private List<String> security_type_list = new ArrayList<String>(FrameworkConstants.Utils.SECURITY_TYPE_LIST);

	@Override
	public void init() {

		StringBuilder builder = new StringBuilder();
		builder.append("<tr><td class=\"no-display\">");
		builder.append("<select name=\"param_types\">");
		builder.append("<option value=\"IN\">IN</option>");
		builder.append("<option value=\"OUT\">OUT</option>");
		builder.append("<option value=\"INOUT\">INOUT</option>");
		builder.append("</select></td>");
		builder.append("<td><input type=\"text\" name=\"params\"></td>");
		builder.append("<td><select name=\"param_data_types\">");
		for (String key : FrameworkConstants.Utils.DATA_TYPES_MAP.keySet()) {
			builder.append("<optgroup value=\"");
			builder.append(key);
			builder.append("\">");
			Iterator<String> iterator = FrameworkConstants.Utils.DATA_TYPES_MAP.get(key).iterator();
			while (iterator.hasNext()) {
				String val = iterator.next();
				builder.append("<option value=\"");
				builder.append(val);
				builder.append("\">");
				builder.append(val);
				builder.append("</option>");
			}
			builder.append("</optgroup>");
		}
		builder.append("</select></td>");
		builder.append("<td><input type=\"text\" name=\"lengths\"></td>");
		builder.append("<td style=\"text-align: center;\">");
		builder.append("<img alt=\"\" class=\"icon\" ");
		builder.append("onclick=\"removeThisTr(this);\" src=\"");
		builder.append(DefaultServlet.REQUEST_MAP.get(Thread.currentThread().getId()).getContextPath());
		builder.append("/components/icons/minus-r.png\">");
		builder.append("</td></tr>");
		new_column = builder.toString();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the definer
	 */
	public String getDefiner() {
		return definer;
	}

	/**
	 * @param definer
	 *            the definer to set
	 */
	public void setDefiner(String definer) {
		this.definer = definer;
	}

	/**
	 * @return the definer_name
	 */
	public String getDefiner_name() {
		return definer_name;
	}

	/**
	 * @param definer_name
	 *            the definer_name to set
	 */
	public void setDefiner_name(String definer_name) {
		this.definer_name = definer_name;
	}

	/**
	 * @return the param_types
	 */
	public String[] getParam_types() {
		return param_types;
	}

	/**
	 * @param param_types
	 *            the param_types to set
	 */
	public void setParam_types(String[] param_types) {
		this.param_types = param_types;
	}

	/**
	 * @return the params
	 */
	public String[] getParams() {
		return params;
	}

	/**
	 * @param params
	 *            the params to set
	 */
	public void setParams(String[] params) {
		this.params = params;
	}

	/**
	 * @return the param_data_types
	 */
	public String[] getParam_data_types() {
		return param_data_types;
	}

	/**
	 * @param param_data_types
	 *            the param_data_types to set
	 */
	public void setParam_data_types(String[] param_data_types) {
		this.param_data_types = param_data_types;
	}

	/**
	 * @return the return_type
	 */
	public String getReturn_type() {
		return return_type;
	}

	/**
	 * @param return_type
	 *            the return_type to set
	 */
	public void setReturn_type(String return_type) {
		this.return_type = return_type;
	}

	/**
	 * @return the return_length
	 */
	public String getReturn_length() {
		return return_length;
	}

	/**
	 * @param return_length
	 *            the return_length to set
	 */
	public void setReturn_length(String return_length) {
		this.return_length = return_length;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the lang_sql
	 */
	public String getLang_sql() {
		return lang_sql;
	}

	/**
	 * @param lang_sql
	 *            the lang_sql to set
	 */
	public void setLang_sql(String lang_sql) {
		this.lang_sql = lang_sql;
	}

	/**
	 * @return the deterministic
	 */
	public String getDeterministic() {
		return deterministic;
	}

	/**
	 * @param deterministic
	 *            the deterministic to set
	 */
	public void setDeterministic(String deterministic) {
		this.deterministic = deterministic;
	}

	/**
	 * @return the sql_type
	 */
	public String getSql_type() {
		return sql_type;
	}

	/**
	 * @param sql_type
	 *            the sql_type to set
	 */
	public void setSql_type(String sql_type) {
		this.sql_type = sql_type;
	}

	/**
	 * @return the sql_security
	 */
	public String getSql_security() {
		return sql_security;
	}

	/**
	 * @param sql_security
	 *            the sql_security to set
	 */
	public void setSql_security(String sql_security) {
		this.sql_security = sql_security;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body
	 *            the body to set
	 */
	public void setBody(String body) {
		this.body = body;
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

	/**
	 * @return the alter
	 */
	public String getAlter() {
		return alter;
	}

	/**
	 * @param alter
	 *            the alter to set
	 */
	public void setAlter(String alter) {
		this.alter = alter;
	}

	/**
	 * @return the new_column
	 */
	public String getNew_column() {
		return new_column;
	}

	/**
	 * @param new_column
	 *            the new_column to set
	 */
	public void setNew_column(String new_column) {
		this.new_column = new_column;
	}

	/**
	 * @return the lengths
	 */
	public String[] getLengths() {
		return lengths;
	}

	/**
	 * @param lengths
	 *            the lengths to set
	 */
	public void setLengths(String[] lengths) {
		this.lengths = lengths;
	}

	/**
	 * @return the definer_list
	 */
	public List<String> getDefiner_list() {
		return definer_list;
	}

	/**
	 * @param definer_list
	 *            the definer_list to set
	 */
	public void setDefiner_list(List<String> definer_list) {
		this.definer_list = definer_list;
	}

	/**
	 * @return the data_types_map
	 */
	public Map<String, List<String>> getData_types_map() {
		return data_types_map;
	}

	/**
	 * @param data_types_map
	 *            the data_types_map to set
	 */
	public void setData_types_map(Map<String, List<String>> data_types_map) {
		this.data_types_map = data_types_map;
	}

	/**
	 * @return the sql_type_list
	 */
	public List<String> getSql_type_list() {
		return sql_type_list;
	}

	/**
	 * @param sql_type_list
	 *            the sql_type_list to set
	 */
	public void setSql_type_list(List<String> sql_type_list) {
		this.sql_type_list = sql_type_list;
	}

	/**
	 * @return the security_type_list
	 */
	public List<String> getSecurity_type_list() {
		return security_type_list;
	}

	/**
	 * @param security_type_list
	 *            the security_type_list to set
	 */
	public void setSecurity_type_list(List<String> security_type_list) {
		this.security_type_list = security_type_list;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
