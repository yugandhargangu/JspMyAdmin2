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
import com.jspmyadmin.framework.constants.FrameworkConstants;
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
	public Map<String, List<String>> getCollationMap() throws ClassNotFoundException, SQLException {
		Map<String, List<String>> collationMap = new TreeMap<String, List<String>>();

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		PreparedStatement innerStatement = null;
		ResultSet innerResultSet = null;
		List<String> collationList = null;
		String charset = null;
		try {
			apiConnection = getConnection(false);
			statement = apiConnection.preparedStatementSelect("SHOW CHARACTER SET");
			resultSet = statement.executeQuery();
			innerStatement = apiConnection.preparedStatementSelect("SHOW COLLATION WHERE CHARSET = ?");
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
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void fillBean(Bean bean) throws ClassNotFoundException, SQLException {
		HomeBean homeBean = null;
		ApiConnection apiConnection = null;
		DatabaseMetaData databaseMetaData = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ServletContext context = null;
		try {
			homeBean = (HomeBean) bean;
			apiConnection = getConnection(false);
			databaseMetaData = apiConnection.getDatabaseMetaData();
			homeBean.setDb_server_user(databaseMetaData.getUserName());

			statement = apiConnection.preparedStatementSelect("SHOW VARIABLES WHERE VARIABLE_NAME = ?");
			statement.setString(1, "collation_server");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				homeBean.setCollation(resultSet.getString(2));
			}
			resultSet.close();
			resultSet = null;
			statement.close();
			statement = null;

			statement = apiConnection.preparedStatementSelect("SHOW VARIABLES WHERE VARIABLE_NAME = ?");
			statement.setString(1, "hostname");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				homeBean.setDb_server_name(resultSet.getString(2));
			}
			resultSet.close();
			resultSet = null;
			statement.close();
			statement = null;

			statement = apiConnection.preparedStatementSelect("SHOW VARIABLES WHERE VARIABLE_NAME = ?");
			statement.setString(1, "version_comment");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				homeBean.setDb_server_type(resultSet.getString(2));
			}
			resultSet.close();
			resultSet = null;
			statement.close();
			statement = null;

			statement = apiConnection.preparedStatementSelect("SHOW VARIABLES WHERE VARIABLE_NAME = ?");
			statement.setString(1, "version");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				homeBean.setDb_server_version(resultSet.getString(2));
			}
			resultSet.close();
			resultSet = null;
			statement.close();
			statement = null;

			statement = apiConnection.preparedStatementSelect("SHOW VARIABLES WHERE VARIABLE_NAME = ?");
			statement.setString(1, "protocol_version");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				homeBean.setDb_server_protocol(resultSet.getString(2));
			}
			resultSet.close();
			resultSet = null;
			statement.close();
			statement = null;

			statement = apiConnection.preparedStatementSelect("SHOW VARIABLES WHERE VARIABLE_NAME = ?");
			statement.setString(1, "character_set_server");
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
			homeBean.setJava_version(Runtime.class.getPackage().getImplementationVersion());
			homeBean.setServelt_version(
					context.getMajorVersion() + FrameworkConstants.SYMBOL_DOT + context.getMinorVersion());
			homeBean.setJsp_version(JspFactory.getDefaultFactory().getEngineInfo().getSpecificationVersion());
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
	}
}
