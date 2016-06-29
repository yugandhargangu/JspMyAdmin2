/**
 * 
 */
package com.jspmyadmin.app.common.logic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jspmyadmin.framework.db.AbstractLogic;
import com.jspmyadmin.framework.db.ApiConnection;
import com.jspmyadmin.framework.web.logic.EncDecLogic;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/30
 *
 */
public class DataLogic extends AbstractLogic {

	/**
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<String> getDatabaseList() throws ClassNotFoundException, SQLException {
		List<String> databaseList = new ArrayList<String>();

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			apiConnection = getConnection(false);
			statement = apiConnection.preparedStatementSelect("SHOW DATABASES");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				databaseList.add(resultSet.getString(1));
			}
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
		return databaseList;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getDatabaseMap() throws Exception {
		Map<String, String> databaseMap = new LinkedHashMap<String, String>();

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		String temp = null;
		EncDecLogic encDecLogic = null;
		try {
			apiConnection = getConnection(false);
			statement = apiConnection.preparedStatementSelect("SHOW DATABASES");
			resultSet = statement.executeQuery();
			encDecLogic = new EncDecLogic();
			while (resultSet.next()) {
				temp = resultSet.getString(1);
				databaseMap.put(encDecLogic.encode(temp), temp);
			}
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
		return databaseMap;
	}

	/**
	 * 
	 * @param database
	 * @param isEncoded
	 * @return
	 * @throws Exception
	 */
	public List<String> getTableList(String database, boolean isEncoded) throws Exception {

		List<String> tableList = new ArrayList<String>();

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		EncDecLogic encDecLogic = null;
		try {
			encDecLogic = new EncDecLogic();
			if (isEncoded) {
				database = encDecLogic.decode(database);
			}
			apiConnection = getConnection(database);
			statement = apiConnection.preparedStatementSelect("SHOW FULL TABLES WHERE TABLE_TYPE LIKE ?");
			statement.setString(1, "BASE TABLE");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				tableList.add(resultSet.getString(1));
			}
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
		return tableList;
	}

	/**
	 * 
	 * @param database
	 * @param isEncoded
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getTableMap(String database, boolean isEncoded) throws Exception {

		Map<String, String> tableMap = new LinkedHashMap<String, String>();

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		EncDecLogic encDecLogic = null;
		String temp = null;
		try {
			encDecLogic = new EncDecLogic();
			if (isEncoded) {
				database = encDecLogic.decode(database);
			}
			apiConnection = getConnection(database);
			statement = apiConnection.preparedStatementSelect("SHOW FULL TABLES WHERE TABLE_TYPE LIKE ?");
			statement.setString(1, "BASE TABLE");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				temp = resultSet.getString(1);
				tableMap.put(encDecLogic.encode(temp), temp);
			}
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
		return tableMap;
	}

}
