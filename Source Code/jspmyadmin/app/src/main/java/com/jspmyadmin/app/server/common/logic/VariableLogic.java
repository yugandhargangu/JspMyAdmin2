/**
 * 
 */
package com.jspmyadmin.app.server.common.logic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.jspmyadmin.app.server.common.beans.CommonListBean;
import com.jspmyadmin.app.server.common.beans.VariableBean;
import com.jspmyadmin.framework.connection.AbstractLogic;
import com.jspmyadmin.framework.connection.ApiConnection;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/10
 *
 */
public class VariableLogic extends AbstractLogic {

	/**
	 * 
	 * @param bean
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws JSONException
	 * @throws Exception
	 */
	public void fillBean(Bean bean) throws SQLException, JSONException {

		CommonListBean variableBean = null;
		List<String[]> variableInfoList = null;
		String[] variableInfo = null;
		int length = 0;
		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		try {
			variableBean = (CommonListBean) bean;
			apiConnection = getConnection();
			statement = apiConnection.getStmtSelect("SHOW VARIABLES");
			resultSet = statement.executeQuery();
			resultSetMetaData = resultSet.getMetaData();
			length = resultSetMetaData.getColumnCount();

			variableInfo = new String[length];
			for (int i = 0; i < length; i++) {
				variableInfo[i] = resultSetMetaData.getColumnName(i + 1);
			}
			variableBean.setColumnInfo(variableInfo);

			variableInfoList = new ArrayList<String[]>();
			while (resultSet.next()) {
				variableInfo = new String[length];
				for (int i = 0; i < length; i++) {
					variableInfo[i] = resultSet.getString(i + 1);
				}
				variableInfoList.add(variableInfo);
			}
			variableBean.setData_list(variableInfoList);
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
	}

	/**
	 * 
	 * @param bean
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws Exception
	 */
	public String save(VariableBean bean) throws SQLException {

		String result = null;
		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			apiConnection = getConnection();
			statement = apiConnection.getStmt("SET GLOBAL " + bean.getName() + " = ?");
			if (super.isInteger(bean.getValue())) {
				statement.setInt(1, Integer.parseInt(bean.getValue()));
			} else {
				statement.setString(1, bean.getValue());
			}
			statement.executeUpdate();
			statement.close();
			statement = null;
			apiConnection.commit();
			statement = apiConnection.getStmtSelect("SHOW VARIABLES like ?");
			statement.setString(1, bean.getName());
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				result = resultSet.getString(2);
			}
		} catch (SQLException e) {
			if (statement != null) {
				apiConnection.rollback();
			}
			throw e;
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
		return result;
	}
}
