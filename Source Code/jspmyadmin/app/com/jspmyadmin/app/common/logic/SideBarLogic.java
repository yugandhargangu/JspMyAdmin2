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
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.logic.EncDecLogic;

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

		EncDecLogic encDecLogic = null;
		JSONArray jsonArray = null;
		JSONObject jsonObject = null;
		JSONObject tempJsonObject = null;
		try {
			apiConnection = getConnection(false);
			statement = apiConnection.getStmtSelect("SHOW DATABASES");
			resultSet = statement.executeQuery();
			encDecLogic = new EncDecLogic();
			jsonArray = new JSONArray();
			while (resultSet.next()) {
				result = resultSet.getString(1);
				jsonObject = new JSONObject();
				jsonObject.put(FrameworkConstants.NAME, result);
				tempJsonObject = new JSONObject();
				tempJsonObject.put(FrameworkConstants.DATABASE, result);
				jsonObject.put(FrameworkConstants.TOKEN, encDecLogic.encode(tempJsonObject.toString()));
				jsonArray.put(jsonObject);
			}
			jsonObject = new JSONObject();
			jsonObject.put(FrameworkConstants.DATA, jsonArray);
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
			apiConnection = getConnection(false);
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

		EncDecLogic encDecLogic = null;
		JSONObject jsonObject = null;
		JSONObject tempJsonObject = null;
		JSONArray jsonArray = null;
		try {
			encDecLogic = new EncDecLogic();
			token = encDecLogic.decode(token);
			jsonObject = new JSONObject(token);
			String table = jsonObject.getString(FrameworkConstants.TABLE);
			apiConnection = getConnection(jsonObject.getString(FrameworkConstants.DATABASE));
			statement = apiConnection.getStmtSelect("SHOW COLUMNS FROM " + table);
			resultSet = statement.executeQuery();
			jsonArray = new JSONArray();
			while (resultSet.next()) {
				result = resultSet.getString(1);
				jsonObject = new JSONObject();
				jsonObject.put(FrameworkConstants.NAME, result);
				tempJsonObject = new JSONObject(token);
				tempJsonObject.put(FrameworkConstants.COLUMN, result);
				jsonObject.put(FrameworkConstants.TOKEN, encDecLogic.encode(tempJsonObject.toString()));
				jsonArray.put(jsonObject);
			}
			jsonObject = new JSONObject();
			jsonObject.put(FrameworkConstants.DATA, jsonArray);
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

		EncDecLogic encDecLogic = null;
		JSONObject jsonObject = null;
		JSONObject tempJsonObject = null;
		JSONArray jsonArray = null;
		try {
			encDecLogic = new EncDecLogic();
			token = encDecLogic.decode(token);
			jsonObject = new JSONObject(token);
			apiConnection = getConnection(jsonObject.getString(FrameworkConstants.DATABASE));
			statement = apiConnection.getStmtSelect("SHOW EVENTS");
			resultSet = statement.executeQuery();
			jsonArray = new JSONArray();
			while (resultSet.next()) {
				result = resultSet.getString(2);
				jsonObject = new JSONObject();
				jsonObject.put(FrameworkConstants.NAME, result);
				tempJsonObject = new JSONObject();
				tempJsonObject.put(FrameworkConstants.DATABASE, token);
				tempJsonObject.put(FrameworkConstants.EVENT, result);
				jsonObject.put(FrameworkConstants.TOKEN, encDecLogic.encode(tempJsonObject.toString()));
				jsonArray.put(jsonObject);
			}
			jsonObject = new JSONObject();
			jsonObject.put(FrameworkConstants.DATA, jsonArray);
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

		EncDecLogic encDecLogic = null;
		JSONObject jsonObject = null;
		JSONObject tempJsonObject = null;
		JSONArray jsonArray = null;
		try {
			encDecLogic = new EncDecLogic();
			token = encDecLogic.decode(token);
			jsonObject = new JSONObject(token);
			apiConnection = getConnection(jsonObject.getString(FrameworkConstants.DATABASE));
			statement = apiConnection.getStmtSelect("SHOW PROCEDURE STATUS WHERE DB LIKE ?");
			statement.setString(1, token);
			resultSet = statement.executeQuery();
			jsonArray = new JSONArray();
			while (resultSet.next()) {
				result = resultSet.getString(1);
				jsonObject = new JSONObject();
				jsonObject.put(FrameworkConstants.NAME, result);
				tempJsonObject = new JSONObject();
				tempJsonObject.put(FrameworkConstants.DATABASE, token);
				tempJsonObject.put(FrameworkConstants.NAME, result);
				jsonObject.put(FrameworkConstants.TOKEN, encDecLogic.encode(tempJsonObject.toString()));
				jsonArray.put(jsonObject);
			}
			jsonObject = new JSONObject();
			jsonObject.put(FrameworkConstants.DATA, jsonArray);
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

		EncDecLogic encDecLogic = null;
		JSONObject jsonObject = null;
		JSONObject tempJsonObject = null;
		JSONArray jsonArray = null;
		try {
			encDecLogic = new EncDecLogic();
			token = encDecLogic.decode(token);
			jsonObject = new JSONObject(token);
			apiConnection = getConnection(jsonObject.getString(FrameworkConstants.DATABASE));
			statement = apiConnection.getStmtSelect("SHOW FUNCTION STATUS WHERE DB LIKE ?");
			statement.setString(1, token);
			resultSet = statement.executeQuery();
			jsonArray = new JSONArray();
			while (resultSet.next()) {
				result = resultSet.getString(1);
				jsonObject = new JSONObject();
				jsonObject.put(FrameworkConstants.NAME, result);
				tempJsonObject = new JSONObject();
				tempJsonObject.put(FrameworkConstants.DATABASE, token);
				tempJsonObject.put(FrameworkConstants.NAME, result);
				jsonObject.put(FrameworkConstants.TOKEN, encDecLogic.encode(tempJsonObject.toString()));
				jsonArray.put(jsonObject);
			}
			jsonObject = new JSONObject();
			jsonObject.put(FrameworkConstants.DATA, jsonArray);
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

		EncDecLogic encDecLogic = null;
		JSONObject jsonObject = null;
		JSONObject tempJsonObject = null;
		JSONArray jsonArray = null;
		try {
			encDecLogic = new EncDecLogic();
			token = encDecLogic.decode(token);
			jsonObject = new JSONObject(token);
			apiConnection = getConnection(jsonObject.getString(FrameworkConstants.DATABASE));
			statement = apiConnection.getStmtSelect("SHOW FULL TABLES WHERE TABLE_TYPE LIKE ?");
			statement.setString(1, "VIEW");
			resultSet = statement.executeQuery();
			jsonArray = new JSONArray();
			while (resultSet.next()) {
				result = resultSet.getString(1);
				jsonObject = new JSONObject();
				jsonObject.put(FrameworkConstants.NAME, result);
				tempJsonObject = new JSONObject();
				tempJsonObject.put(FrameworkConstants.DATABASE, token);
				tempJsonObject.put(FrameworkConstants.VIEW, result);
				jsonObject.put(FrameworkConstants.TOKEN, encDecLogic.encode(tempJsonObject.toString()));
				jsonArray.put(jsonObject);
			}
			jsonObject = new JSONObject();
			jsonObject.put(FrameworkConstants.DATA, jsonArray);
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

		EncDecLogic encDecLogic = null;
		JSONObject jsonObject = null;
		JSONObject oldJsonObject = null;
		JSONObject tempJsonObject = null;
		JSONArray jsonArray = null;
		try {
			encDecLogic = new EncDecLogic();
			token = encDecLogic.decode(token);
			oldJsonObject = new JSONObject(token);
			apiConnection = getConnection(oldJsonObject.getString(FrameworkConstants.DATABASE));
			statement = apiConnection.getStmtSelect("SHOW FULL TABLES WHERE TABLE_TYPE LIKE ?");
			statement.setString(1, "BASE TABLE");
			resultSet = statement.executeQuery();
			jsonArray = new JSONArray();
			while (resultSet.next()) {
				result = resultSet.getString(1);
				jsonObject = new JSONObject();
				jsonObject.put(FrameworkConstants.NAME, result);
				tempJsonObject = new JSONObject(token);
				tempJsonObject.put(FrameworkConstants.TABLE, result);
				jsonObject.put(FrameworkConstants.TOKEN, encDecLogic.encode(tempJsonObject.toString()));
				jsonArray.put(jsonObject);
			}
			jsonObject = new JSONObject();
			jsonObject.put(FrameworkConstants.DATA, jsonArray);
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

		EncDecLogic encDecLogic = null;
		JSONObject jsonObject = null;
		JSONObject tempJsonObject = null;
		JSONArray jsonArray = null;
		try {
			encDecLogic = new EncDecLogic();
			token = encDecLogic.decode(token);
			jsonObject = new JSONObject(token);
			apiConnection = getConnection(jsonObject.getString(FrameworkConstants.DATABASE));
			statement = apiConnection.getStmtSelect("SHOW TRIGGERS");
			resultSet = statement.executeQuery();
			jsonArray = new JSONArray();
			while (resultSet.next()) {
				result = resultSet.getString(1);
				jsonObject = new JSONObject();
				jsonObject.put(FrameworkConstants.NAME, result);
				tempJsonObject = new JSONObject();
				tempJsonObject.put(FrameworkConstants.DATABASE, token);
				tempJsonObject.put(FrameworkConstants.NAME, result);
				jsonObject.put(FrameworkConstants.TOKEN, encDecLogic.encode(tempJsonObject.toString()));
				jsonArray.put(jsonObject);
			}
			jsonObject = new JSONObject();
			jsonObject.put(FrameworkConstants.DATA, jsonArray);
			result = jsonObject.toString();
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
		return result;
	}
}
