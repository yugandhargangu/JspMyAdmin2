/**
 * 
 */
package com.jspmyadmin.app.common.logic;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspFactory;

import com.jspmyadmin.app.common.beans.HomeBean;
import com.jspmyadmin.framework.connection.AbstractLogic;
import com.jspmyadmin.framework.connection.ApiConnection;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.web.utils.Bean;
import com.jspmyadmin.framework.web.utils.DefaultServlet;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/08
 *
 */
public class HomeLogic extends AbstractLogic {

	/**
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Map<String, List<String>> getCollationMap() throws SQLException {
		Map<String, List<String>> collationMap = new TreeMap<String, List<String>>();

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		PreparedStatement innerStatement = null;
		ResultSet innerResultSet = null;
		List<String> collationList = null;
		String charset = null;
		try {
			apiConnection = getConnection();
			statement = apiConnection.getStmtSelect("SHOW CHARACTER SET");
			resultSet = statement.executeQuery();
			innerStatement = apiConnection.getStmtSelect("SHOW COLLATION WHERE CHARSET = ?");
			while (resultSet.next()) {
				charset = resultSet.getString(1);
				innerStatement.clearParameters();
				innerStatement.setString(1, charset);
				innerResultSet = innerStatement.executeQuery();
				collationList = new ArrayList<String>();
				while (innerResultSet.next()) {
					collationList.add(innerResultSet.getString(1));
				}
				innerResultSet.close();
				innerResultSet = null;
				Collections.sort(collationList);
				collationMap.put(charset, collationList);
			}
		} finally {
			close(innerResultSet);
			close(innerStatement);
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
		return collationMap;
	}

	/**
	 * 
	 * @param bean
	 * @throws SQLException
	 */
	public void fillBean(Bean bean) throws SQLException {
		HomeBean homeBean = null;
		ApiConnection apiConnection = null;
		DatabaseMetaData databaseMetaData = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ServletContext context = null;
		try {
			homeBean = (HomeBean) bean;
			apiConnection = getConnection();
			databaseMetaData = apiConnection.getDatabaseMetaData();
			homeBean.setDb_server_user(databaseMetaData.getUserName());

			statement = apiConnection.getStmtSelect("SHOW VARIABLES WHERE VARIABLE_NAME = ?");
			statement.setString(1, Constants.COLLATION_SERVER);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				homeBean.setCollation(resultSet.getString(2));
			}
			resultSet.close();
			resultSet = null;
			statement.close();
			statement = null;

			statement = apiConnection.getStmtSelect("SHOW VARIABLES WHERE VARIABLE_NAME = ?");
			statement.setString(1, Constants.HOSTNAME);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				homeBean.setDb_server_name(resultSet.getString(2));
			}
			resultSet.close();
			resultSet = null;
			statement.close();
			statement = null;

			statement = apiConnection.getStmtSelect("SHOW VARIABLES WHERE VARIABLE_NAME = ?");
			statement.setString(1, Constants.VERSION_COMMENT);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				homeBean.setDb_server_type(resultSet.getString(2));
			}
			resultSet.close();
			resultSet = null;
			statement.close();
			statement = null;

			statement = apiConnection.getStmtSelect("SHOW VARIABLES WHERE VARIABLE_NAME = ?");
			statement.setString(1, Constants.VERSION);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				homeBean.setDb_server_version(resultSet.getString(2));
			}
			resultSet.close();
			resultSet = null;
			statement.close();
			statement = null;

			statement = apiConnection.getStmtSelect("SHOW VARIABLES WHERE VARIABLE_NAME = ?");
			statement.setString(1, Constants.PROTOCOL_VERSION);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				homeBean.setDb_server_protocol(resultSet.getString(2));
			}
			resultSet.close();
			resultSet = null;
			statement.close();
			statement = null;

			statement = apiConnection.getStmtSelect("SHOW VARIABLES WHERE VARIABLE_NAME = ?");
			statement.setString(1, Constants.CHARACTER_SET_SERVER);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				homeBean.setDb_server_charset(resultSet.getString(2));
			}
			resultSet.close();
			resultSet = null;
			statement.close();
			statement = null;

			context = DefaultServlet.getContext();
			homeBean.setWeb_server_name(context.getServerInfo());
			homeBean.setJdbc_version(databaseMetaData.getDriverVersion());
			homeBean.setJava_version(System.getProperty("java.version"));
			homeBean.setServelt_version(
					context.getMajorVersion() + Constants.SYMBOL_DOT + context.getMinorVersion());
			homeBean.setJsp_version(JspFactory.getDefaultFactory().getEngineInfo().getSpecificationVersion());
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
	}

	/**
	 * 
	 * @param collation
	 * @throws SQLException
	 */
	public void saveServerCollation(String collation) throws SQLException {
		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String charset = null;
		try {
			apiConnection = getConnection();
			statement = apiConnection.getStmtSelect("SHOW COLLATION WHERE collation = ?");
			statement.setString(1, collation);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				charset = resultSet.getString(Constants.CHARSET);
			}
			close(resultSet);
			close(statement);
			if (charset != null) {
				statement = apiConnection.getStmt("SET character_set_server = ?");
				statement.setString(1, charset);
				statement.execute();
				close(statement);
			}
			statement = apiConnection.getStmt("SET collation_server = ?");
			statement.setString(1, collation);
			statement.execute();
		} finally {
			if (apiConnection != null) {
				apiConnection.commit();
			}
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
	}
}
