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
import org.json.JSONObject;

import com.jspmyadmin.app.server.common.beans.CommonListBean;
import com.jspmyadmin.framework.connection.AbstractLogic;
import com.jspmyadmin.framework.connection.ApiConnection;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.logic.EncDecLogic;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/10
 *
 */
public class EngineLogic extends AbstractLogic {

	/**
	 * 
	 * @param bean
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws JSONException
	 * @throws Exception
	 */
	public void fillBean(Bean bean) throws ClassNotFoundException, SQLException, JSONException, Exception {

		CommonListBean engineBean = null;
		List<String[]> engineInfoList = null;
		String[] engineInfo = null;
		int length = 0;
		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		EncDecLogic encDecLogic = null;
		JSONObject jsonObject = null;
		String orderBy = "ENGINE";
		String sort = " ASC";
		boolean type = false;
		try {
			engineBean = (CommonListBean) bean;
			apiConnection = getConnection(false);
			encDecLogic = new EncDecLogic();
			if (!super.isEmpty(engineBean.getToken())) {
				jsonObject = new JSONObject(encDecLogic.decode(engineBean.getToken()));
				if (jsonObject.has(FrameworkConstants.NAME)) {
					orderBy = jsonObject.getString(FrameworkConstants.NAME);
				}
				if (jsonObject.has(FrameworkConstants.TYPE)) {
					type = jsonObject.getBoolean(FrameworkConstants.TYPE);
				}
			}
			if (type) {
				sort = " DESC";
			}
			statement = apiConnection
					.getStmtSelect("SELECT * FROM information_schema.ENGINES ORDER BY " + orderBy + sort);
			resultSet = statement.executeQuery();
			resultSetMetaData = resultSet.getMetaData();
			length = resultSetMetaData.getColumnCount();

			engineInfo = new String[length];
			for (int i = 0; i < length; i++) {
				engineInfo[i] = resultSetMetaData.getColumnName(i + 1);
			}
			engineBean.setColumnInfo(engineInfo);

			engineInfo = new String[length];
			for (int i = 0; i < length; i++) {
				jsonObject = new JSONObject();
				jsonObject.put(FrameworkConstants.NAME, resultSetMetaData.getColumnName(i + 1));
				if (orderBy.equalsIgnoreCase(resultSetMetaData.getColumnName(i + 1))) {
					jsonObject.put(FrameworkConstants.TYPE, !type);
				} else {
					jsonObject.put(FrameworkConstants.TYPE, false);
				}
				engineInfo[i] = encDecLogic.encode(jsonObject.toString());
			}
			engineBean.setSortInfo(engineInfo);

			engineInfoList = new ArrayList<String[]>();
			while (resultSet.next()) {
				engineInfo = new String[length];
				for (int i = 0; i < length; i++) {
					engineInfo[i] = resultSet.getString(i + 1);
				}
				engineInfoList.add(engineInfo);
			}
			engineBean.setType(Boolean.toString(type));
			engineBean.setField(orderBy);
			engineBean.setData_list(engineInfoList);
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
	}

	/**
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws JSONException
	 * @throws Exception
	 */
	public List<String> getEngineList() throws ClassNotFoundException, SQLException, JSONException, Exception {

		List<String> engineList = null;
		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			apiConnection = getConnection(false);
			statement = apiConnection.getStmtSelect(
					"SELECT engine FROM information_schema.engines WHERE support <> ? ORDER BY support,engine ASC");
			statement.setString(1, "No");
			resultSet = statement.executeQuery();
			engineList = new ArrayList<String>();
			while (resultSet.next()) {
				engineList.add(resultSet.getString(1));
			}
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
		return engineList;
	}
}
