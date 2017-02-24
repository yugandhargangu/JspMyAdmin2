/**
 * 
 */
package com.jspmyadmin.app.database.structure.beans;

import java.util.ArrayList;
import java.util.List;

import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/01
 *
 */
public class CreateViewBean extends Bean {

	private static final long serialVersionUID = 1L;

	private String view_name = null;
	private String create_type = null;
	private String algorithm = null;
	private String definer = null;
	private String definer_name = null;
	private String sql_security = null;
	private String[] column_list = null;
	private String definition = null;
	private String check = null;
	private String action = null;

	private List<String> algorithm_list = new ArrayList<String>(Constants.Utils.ALGORITHM_LIST);
	private List<String> definer_list = new ArrayList<String>(Constants.Utils.DEFINER_LIST);
	private List<String> security_list = new ArrayList<String>(Constants.Utils.SECURITY_TYPE_LIST);
	private List<String> check_list = new ArrayList<String>(Constants.Utils.VIEW_CHECK_LIST);

	/**
	 * @return the view_name
	 */
	public String getView_name() {
		return view_name;
	}

	/**
	 * @param view_name
	 *            the view_name to set
	 */
	public void setView_name(String view_name) {
		this.view_name = view_name;
	}

	/**
	 * @return the create_type
	 */
	public String getCreate_type() {
		return create_type;
	}

	/**
	 * @param create_type
	 *            the create_type to set
	 */
	public void setCreate_type(String create_type) {
		this.create_type = create_type;
	}

	/**
	 * @return the algorithm
	 */
	public String getAlgorithm() {
		return algorithm;
	}

	/**
	 * @param algorithm
	 *            the algorithm to set
	 */
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
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
	 * @return the column_list
	 */
	public String[] getColumn_list() {
		return column_list;
	}

	/**
	 * @param column_list
	 *            the column_list to set
	 */
	public void setColumn_list(String[] column_list) {
		this.column_list = column_list;
	}

	/**
	 * @return the definition
	 */
	public String getDefinition() {
		return definition;
	}

	/**
	 * @param definition
	 *            the definition to set
	 */
	public void setDefinition(String definition) {
		this.definition = definition;
	}

	/**
	 * @return the check
	 */
	public String getCheck() {
		return check;
	}

	/**
	 * @param check
	 *            the check to set
	 */
	public void setCheck(String check) {
		this.check = check;
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
	 * @return the algorithm_list
	 */
	public List<String> getAlgorithm_list() {
		return algorithm_list;
	}

	/**
	 * @param algorithm_list
	 *            the algorithm_list to set
	 */
	public void setAlgorithm_list(List<String> algorithm_list) {
		this.algorithm_list = algorithm_list;
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
	 * @return the security_list
	 */
	public List<String> getSecurity_list() {
		return security_list;
	}

	/**
	 * @param security_list
	 *            the security_list to set
	 */
	public void setSecurity_list(List<String> security_list) {
		this.security_list = security_list;
	}

	/**
	 * @return the check_list
	 */
	public List<String> getCheck_list() {
		return check_list;
	}

	/**
	 * @param check_list
	 *            the check_list to set
	 */
	public void setCheck_list(List<String> check_list) {
		this.check_list = check_list;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
