/**
 * 
 */
package com.jspmyadmin.app.common.logic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.jspmyadmin.framework.connection.AbstractLogic;
import com.jspmyadmin.framework.connection.ApiConnection;
import com.jspmyadmin.framework.constants.Constants;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/05
 *
 */
public class SideBarLogic extends AbstractLogic {

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String menubarMain() throws Exception {
		String result = null;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		JSONArray jsonArray = null;
		JSONObject jsonObject = null;
		JSONObject tempJsonObject = null;
		try {
			apiConnection = getConnection();
			statement = apiConnection.getStmtSelect("SHOW DATABASES");
			resultSet = statement.executeQuery();
			jsonArray = new JSONArray();
			while (resultSet.next()) {
				result = resultSet.getString(1);
				jsonObject = new JSONObject();
				jsonObject.put(Constants.NAME, result);
				tempJsonObject = new JSONObject();
				tempJsonObject.put(Constants.REQUEST_DB, result);
				jsonObject.put(Constants.TOKEN, encodeObj.encode(tempJsonObject.toString()));
				jsonArray.put(jsonObject);
			}
			jsonObject = new JSONObject();
			jsonObject.put(Constants.DATA, jsonArray);
			result = jsonObject.toString();
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
		return result;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<String> getDatabaseList() throws Exception {
		List<String> databaseList = null;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			apiConnection = getConnection();
			statement = apiConnection.getStmtSelect("SHOW DATABASES");
			resultSet = statement.executeQuery();
			databaseList = new ArrayList<String>();
			while (resultSet.next()) {
				databaseList.add(resultSet.getString(1));
			}
			Collections.sort(databaseList);
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
		return databaseList;
	}

	/**
	 * 
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public String callColumn(String token) throws Exception {
		String result = null;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		JSONObject jsonObject = null;
		JSONObject tempJsonObject = null;
		JSONArray jsonArray = null;
		try {
			token = encodeObj.decode(token);
			jsonObject = new JSONObject(token);
			String table = jsonObject.getString(Constants.REQUEST_TABLE);
			apiConnection = getConnection(jsonObject.getString(Constants.REQUEST_DB));
			statement = apiConnection.getStmtSelect("SHOW COLUMNS FROM " + table);
			resultSet = statement.executeQuery();
			jsonArray = new JSONArray();
			while (resultSet.next()) {
				result = resultSet.getString(Constants.FIELD);
				jsonObject = new JSONObject();
				jsonObject.put(Constants.NAME, result);
				tempJsonObject = new JSONObject(token);
				jsonObject.put(Constants.TOKEN, encodeObj.encode(tempJsonObject.toString()));
				jsonArray.put(jsonObject);
			}
			jsonObject = new JSONObject();
			jsonObject.put(Constants.DATA, jsonArray);
			result = jsonObject.toString();
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
		return result;
	}

	/**
	 * 
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public String callEvent(String token) throws Exception {
		String result = null;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		JSONObject jsonObject = null;
		JSONObject tempJsonObject = null;
		JSONArray jsonArray = null;
		try {
			token = encodeObj.decode(token);
			jsonObject = new JSONObject(token);
			apiConnection = getConnection(jsonObject.getString(Constants.REQUEST_DB));
			statement = apiConnection.getStmtSelect("SHOW EVENTS");
			resultSet = statement.executeQuery();
			jsonArray = new JSONArray();
			while (resultSet.next()) {
				result = resultSet.getString(Constants.NAME);
				jsonObject = new JSONObject();
				jsonObject.put(Constants.NAME, result);
				tempJsonObject = new JSONObject(token);
				tempJsonObject.put(Constants.EVENT, result);
				jsonObject.put(Constants.TOKEN, encodeObj.encode(tempJsonObject.toString()));
				jsonArray.put(jsonObject);
			}
			jsonObject = new JSONObject();
			jsonObject.put(Constants.DATA, jsonArray);
			result = jsonObject.toString();
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
		return result;
	}

	/**
	 * 
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public String callRoutine(String token) throws Exception {
		String result = null;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		JSONObject jsonObject = null;
		JSONObject tempJsonObject = null;
		JSONArray jsonArray = null;
		try {
			token = encodeObj.decode(token);
			jsonObject = new JSONObject(token);
			String database = jsonObject.getString(Constants.REQUEST_DB);
			apiConnection = getConnection(database);
			statement = apiConnection.getStmtSelect("SHOW PROCEDURE STATUS WHERE DB = ?");
			statement.setString(1, database);
			resultSet = statement.executeQuery();
			jsonArray = new JSONArray();
			while (resultSet.next()) {
				result = resultSet.getString(Constants.NAME);
				jsonObject = new JSONObject();
				jsonObject.put(Constants.NAME, result);
				tempJsonObject = new JSONObject(token);
				tempJsonObject.put(Constants.NAME, result);
				jsonObject.put(Constants.TOKEN, encodeObj.encode(tempJsonObject.toString()));
				jsonArray.put(jsonObject);
			}
			jsonObject = new JSONObject();
			jsonObject.put(Constants.DATA, jsonArray);
			result = jsonObject.toString();
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
		return result;
	}

	/**
	 * 
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public String callFunction(String token) throws Exception {
		String result = null;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		JSONObject jsonObject = null;
		JSONObject tempJsonObject = null;
		JSONArray jsonArray = null;
		try {
			token = encodeObj.decode(token);
			jsonObject = new JSONObject(token);
			String database = jsonObject.getString(Constants.REQUEST_DB);
			apiConnection = getConnection(database);
			statement = apiConnection.getStmtSelect("SHOW FUNCTION STATUS WHERE DB = ?");
			statement.setString(1, database);
			resultSet = statement.executeQuery();
			jsonArray = new JSONArray();
			while (resultSet.next()) {
				result = resultSet.getString(Constants.NAME);
				jsonObject = new JSONObject();
				jsonObject.put(Constants.NAME, result);
				tempJsonObject = new JSONObject(token);
				tempJsonObject.put(Constants.NAME, result);
				jsonObject.put(Constants.TOKEN, encodeObj.encode(tempJsonObject.toString()));
				jsonArray.put(jsonObject);
			}
			jsonObject = new JSONObject();
			jsonObject.put(Constants.DATA, jsonArray);
			result = jsonObject.toString();
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
		return result;
	}

	/**
	 * 
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public String callView(String token) throws Exception {
		String result = null;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		JSONObject jsonObject = null;
		JSONObject tempJsonObject = null;
		JSONArray jsonArray = null;
		try {
			token = encodeObj.decode(token);
			jsonObject = new JSONObject(token);
			apiConnection = getConnection(jsonObject.getString(Constants.REQUEST_DB));
			statement = apiConnection.getStmtSelect("SHOW FULL TABLES WHERE TABLE_TYPE = ?");
			statement.setString(1, Constants.VIEW_UPPER_CASE);
			resultSet = statement.executeQuery();
			jsonArray = new JSONArray();
			while (resultSet.next()) {
				result = resultSet.getString(1);
				jsonObject = new JSONObject();
				jsonObject.put(Constants.NAME, result);
				tempJsonObject = new JSONObject(token);
				tempJsonObject.put(Constants.REQUEST_VIEW, result);
				jsonObject.put(Constants.TOKEN, encodeObj.encode(tempJsonObject.toString()));
				jsonArray.put(jsonObject);
			}
			jsonObject = new JSONObject();
			jsonObject.put(Constants.DATA, jsonArray);
			result = jsonObject.toString();
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
		return result;
	}

	/**
	 * 
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public String callTable(String token) throws Exception {
		String result = null;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		JSONObject jsonObject = null;
		JSONObject oldJsonObject = null;
		JSONObject tempJsonObject = null;
		JSONArray jsonArray = null;
		try {
			token = encodeObj.decode(token);
			oldJsonObject = new JSONObject(token);
			apiConnection = getConnection(oldJsonObject.getString(Constants.REQUEST_DB));
			statement = apiConnection.getStmtSelect("SHOW FULL TABLES WHERE TABLE_TYPE = ?");
			statement.setString(1, Constants.BASE_TABLE);
			resultSet = statement.executeQuery();
			jsonArray = new JSONArray();
			while (resultSet.next()) {
				result = resultSet.getString(1);
				jsonObject = new JSONObject();
				jsonObject.put(Constants.NAME, result);
				tempJsonObject = new JSONObject(token);
				tempJsonObject.put(Constants.REQUEST_TABLE, result);
				jsonObject.put(Constants.TOKEN, encodeObj.encode(tempJsonObject.toString()));
				jsonArray.put(jsonObject);
			}
			jsonObject = new JSONObject();
			jsonObject.put(Constants.DATA, jsonArray);
			result = jsonObject.toString();
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
		return result;
	}

	/**
	 * 
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public String callTrigger(String token) throws Exception {
		String result = null;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		JSONObject jsonObject = null;
		JSONObject tempJsonObject = null;
		JSONArray jsonArray = null;
		try {
			token = encodeObj.decode(token);
			jsonObject = new JSONObject(token);
			apiConnection = getConnection(jsonObject.getString(Constants.REQUEST_DB));
			statement = apiConnection.getStmtSelect("SHOW TRIGGERS");
			resultSet = statement.executeQuery();
			jsonArray = new JSONArray();
			while (resultSet.next()) {
				result = resultSet.getString(Constants.TRIGGER);
				jsonObject = new JSONObject();
				jsonObject.put(Constants.NAME, result);
				tempJsonObject = new JSONObject(token);
				tempJsonObject.put(Constants.NAME, result);
				jsonObject.put(Constants.TOKEN, encodeObj.encode(tempJsonObject.toString()));
				jsonArray.put(jsonObject);
			}
			jsonObject = new JSONObject();
			jsonObject.put(Constants.DATA, jsonArray);
			result = jsonObject.toString();
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
		return result;
	}
}
