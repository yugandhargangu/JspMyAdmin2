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
import com.jspmyadmin.framework.db.AbstractLogic;
import com.jspmyadmin.framework.db.ApiConnection;
import com.jspmyadmin.framework.util.FrameworkConstants;
import com.jspmyadmin.framework.web.logic.EncDecLogic;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/10
 *
 */
public class PluginLogic extends AbstractLogic {

	/**
	 * 
	 * @param bean
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws JSONException
	 * @throws Exception
	 */
	public void fillBean(Bean bean) throws ClassNotFoundException, SQLException, JSONException, Exception {

		CommonListBean pluginBean = null;
		List<String[]> pluginInfoList = null;
		String[] pluginInfo = null;
		int length = 0;
		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		EncDecLogic encDecLogic = null;
		JSONObject jsonObject = null;
		String orderBy = "PLUGIN_NAME";
		String sort = " ASC";
		boolean type = false;
		try {
			pluginBean = (CommonListBean) bean;
			apiConnection = getConnection(false);
			encDecLogic = new EncDecLogic();
			if (!super.isEmpty(pluginBean.getToken())) {
				jsonObject = new JSONObject(encDecLogic.decode(pluginBean.getToken()));
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
					.preparedStatementSelect("SELECT * FROM information_schema.PLUGINS ORDER BY " + orderBy + sort);
			resultSet = statement.executeQuery();
			resultSetMetaData = resultSet.getMetaData();
			length = resultSetMetaData.getColumnCount();

			pluginInfo = new String[length];
			for (int i = 0; i < length; i++) {
				pluginInfo[i] = resultSetMetaData.getColumnName(i + 1);
			}
			pluginBean.setColumnInfo(pluginInfo);

			pluginInfo = new String[length];
			for (int i = 0; i < length; i++) {
				jsonObject = new JSONObject();
				jsonObject.put(FrameworkConstants.NAME, resultSetMetaData.getColumnName(i + 1));
				if (orderBy.equalsIgnoreCase(resultSetMetaData.getColumnName(i + 1))) {
					jsonObject.put(FrameworkConstants.TYPE, !type);
				} else {
					jsonObject.put(FrameworkConstants.TYPE, false);
				}
				pluginInfo[i] = encDecLogic.encode(jsonObject.toString());
			}
			pluginBean.setSortInfo(pluginInfo);

			pluginInfoList = new ArrayList<String[]>();
			while (resultSet.next()) {
				pluginInfo = new String[length];
				for (int i = 0; i < length; i++) {
					pluginInfo[i] = resultSet.getString(i + 1);
				}
				pluginInfoList.add(pluginInfo);
			}
			pluginBean.setType(Boolean.toString(type));
			pluginBean.setField(orderBy);
			pluginBean.setData_list(pluginInfoList);
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
	}
}
