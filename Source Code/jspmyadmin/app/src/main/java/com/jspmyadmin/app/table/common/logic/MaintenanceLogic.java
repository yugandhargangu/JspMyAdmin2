/**
 * 
 */
package com.jspmyadmin.app.table.common.logic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jspmyadmin.app.table.common.beans.MaintenanceBean;
import com.jspmyadmin.framework.connection.AbstractLogic;
import com.jspmyadmin.framework.connection.ApiConnection;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/21
 *
 */
public class MaintenanceLogic extends AbstractLogic {

	private final String _table;

	/**
	 * 
	 * @param table
	 */
	public MaintenanceLogic(String table) {
		if (isEmpty(table)) {
			throw new NullPointerException();
		}
		_table = table;
	}

	/**
	 * 
	 * @param bean
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void fillBean(Bean bean) throws ClassNotFoundException, SQLException {
		MaintenanceBean maintenanceBean = (MaintenanceBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			int action = (int) Long.parseLong(maintenanceBean.getAction());
			StringBuilder builder = new StringBuilder();
			switch (action) {
			case 1:
				// analyze
				builder.append("ANALYZE ");
				if (!isEmpty(maintenanceBean.getOption())) {
					builder.append(maintenanceBean.getOption());
					builder.append(FrameworkConstants.SPACE);
				}
				builder.append("TABLE `");
				builder.append(_table);
				builder.append(FrameworkConstants.SYMBOL_TEN);
				break;
			case 2:
				// check
				builder.append("CHECK TABLE `");
				builder.append(_table);
				builder.append(FrameworkConstants.SYMBOL_TEN);
				if (!isEmpty(maintenanceBean.getOption())) {
					builder.append(FrameworkConstants.SPACE);
					builder.append(maintenanceBean.getOption());
				}
				break;
			case 3:
				// checksum
				builder.append("CHECKSUM TABLE `");
				builder.append(_table);
				builder.append(FrameworkConstants.SYMBOL_TEN);
				if (!isEmpty(maintenanceBean.getOption())) {
					builder.append(FrameworkConstants.SPACE);
					builder.append(maintenanceBean.getOption());
				}
				break;
			case 4:
				// optimize
				builder.append("OPTIMIZE ");
				if (!isEmpty(maintenanceBean.getOption())) {
					builder.append(maintenanceBean.getOption());
					builder.append(FrameworkConstants.SPACE);
				}
				builder.append("TABLE `");
				builder.append(_table);
				builder.append(FrameworkConstants.SYMBOL_TEN);
				break;
			case 5:
				// repair
				builder.append("REPAIR ");
				if (!isEmpty(maintenanceBean.getOption())) {
					builder.append(maintenanceBean.getOption());
					builder.append(FrameworkConstants.SPACE);
				}
				builder.append("TABLE `");
				builder.append(_table);
				builder.append(FrameworkConstants.SYMBOL_TEN);
				if (maintenanceBean.getRepair_options() != null) {
					for (String opt : maintenanceBean.getRepair_options()) {
						builder.append(FrameworkConstants.SPACE);
						builder.append(opt);
					}
				}
				break;
			default:
				break;
			}
			if (builder.length() > 0) {
				apiConnection = getConnection(bean.getRequest_db());
				statement = apiConnection.getStmt(builder.toString());
				boolean status = statement.execute();
				if (status) {
					resultSet = statement.getResultSet();
					ResultSetMetaData metaData = resultSet.getMetaData();
					List<String> columnList = new ArrayList<String>(metaData.getColumnCount());
					for (int i = 0; i < metaData.getColumnCount(); i++) {
						columnList.add(metaData.getColumnName(i + 1));
					}
					maintenanceBean.setColumn_names(columnList);
					List<List<String>> dataList = new ArrayList<List<String>>();
					while (resultSet.next()) {
						List<String> rowList = new ArrayList<String>();
						for (int i = 0; i < metaData.getColumnCount(); i++) {
							rowList.add(resultSet.getString(i + 1));
						}
						dataList.add(rowList);
					}
					maintenanceBean.setData_list(dataList);
				}
			}
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}

	}
}
