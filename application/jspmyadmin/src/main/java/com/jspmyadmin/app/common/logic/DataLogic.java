/**
 * 
 */
package com.jspmyadmin.app.common.logic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jspmyadmin.framework.connection.AbstractLogic;
import com.jspmyadmin.framework.connection.ApiConnection;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;

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
	public List<String> getDatabaseList() throws SQLException {
		List<String> databaseList = new ArrayList<String>();

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			apiConnection = getConnection();
			statement = apiConnection.getStmtSelect("SHOW DATABASES");
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
	 * @param database
	 * @param isEncoded
	 * @return
	 * @throws EncodingException
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<String> getTableList(String database, boolean isEncoded) throws EncodingException, SQLException {

		List<String> tableList = new ArrayList<String>();

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			if (isEncoded) {
				database = encodeObj.decode(database);
			}
			apiConnection = getConnection(database);
			statement = apiConnection.getStmtSelect("SHOW FULL TABLES WHERE TABLE_TYPE LIKE ?");
			statement.setString(1, Constants.BASE_TABLE);
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

}
