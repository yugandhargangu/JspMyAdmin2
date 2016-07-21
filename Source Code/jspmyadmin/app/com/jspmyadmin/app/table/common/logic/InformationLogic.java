/**
 * 
 */
package com.jspmyadmin.app.table.common.logic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jspmyadmin.app.table.common.beans.InformationBean;
import com.jspmyadmin.framework.connection.AbstractLogic;
import com.jspmyadmin.framework.connection.ApiConnection;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/06
 *
 */
public class InformationLogic extends AbstractLogic {

	private final String _table;

	public InformationLogic(String table) {
		_table = table;
	}

	/**
	 * 
	 * @param bean
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void fillBean(Bean bean) throws ClassNotFoundException, SQLException {

		InformationBean informationBean = (InformationBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			apiConnection = getConnection(true);
			statement = apiConnection.getStmtSelect("SHOW TABLE STATUS WHERE NAME = ?");
			statement.setString(1, _table);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				ResultSetMetaData metaData = resultSet.getMetaData();
				Map<String, String> info_map = new LinkedHashMap<String, String>(metaData.getColumnCount());
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					String value = resultSet.getString(i);
					if (value == null) {
						info_map.put(metaData.getColumnLabel(i), FrameworkConstants.DATABASE_NULL);
					} else {
						info_map.put(metaData.getColumnLabel(i), value);
					}
				}
				informationBean.setInfo_map(info_map);
			}
			close(resultSet);
			close(statement);
			statement = apiConnection
					.getStmtSelect("SHOW CREATE TABLE `" + _table + FrameworkConstants.SYMBOL_TEN);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				informationBean.setCreate_syn(resultSet.getString(2) + FrameworkConstants.SYMBOL_SEMI_COLON);
			}
			close(resultSet);
			close(statement);
			statement = apiConnection.getStmtSelect("SELECT * FROM `" + _table + "` WHERE TRUE IS FALSE");
			resultSet = statement.executeQuery();
			ResultSetMetaData metaData = resultSet.getMetaData();
			List<String> columnList = new ArrayList<String>(metaData.getColumnCount());
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				columnList.add(metaData.getColumnName(i));
			}

			String start = "<{";
			String end = ": }>";
			String equals = " = ";
			String new_row = FrameworkConstants.SPACE;

			StringBuilder builder = new StringBuilder("INSERT INTO `");
			builder.append(apiConnection.getDatabase());
			builder.append(FrameworkConstants.SYMBOL_TEN);
			builder.append(FrameworkConstants.SYMBOL_DOT);
			builder.append(FrameworkConstants.SYMBOL_TEN);
			builder.append(_table);
			builder.append(FrameworkConstants.SYMBOL_TEN);
			builder.append(new_row);
			builder.append(FrameworkConstants.SYMBOL_BRACKET_OPEN);
			Iterator<String> iterator = columnList.iterator();
			boolean isEntered = false;
			while (iterator.hasNext()) {
				if (isEntered) {
					builder.append(FrameworkConstants.SYMBOL_COMMA);
					builder.append(new_row);
				} else {
					isEntered = true;
				}
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(iterator.next());
				builder.append(FrameworkConstants.SYMBOL_TEN);
			}
			builder.append(FrameworkConstants.SYMBOL_BRACKET_CLOSE);
			builder.append(new_row);
			builder.append(" VALUES ");
			builder.append(new_row);
			builder.append(FrameworkConstants.SYMBOL_BRACKET_OPEN);
			iterator = columnList.iterator();
			isEntered = false;
			while (iterator.hasNext()) {
				if (isEntered) {
					builder.append(FrameworkConstants.SYMBOL_COMMA);
					builder.append(new_row);
				} else {
					isEntered = true;
				}
				builder.append(start);
				builder.append(iterator.next());
				builder.append(end);
			}
			builder.append(FrameworkConstants.SYMBOL_BRACKET_CLOSE);
			builder.append(FrameworkConstants.SYMBOL_SEMI_COLON);
			informationBean.setInsert_syn(builder.toString());

			builder.delete(0, builder.length());
			builder.append("UPDATE `");
			builder.append(apiConnection.getDatabase());
			builder.append(FrameworkConstants.SYMBOL_TEN);
			builder.append(FrameworkConstants.SYMBOL_DOT);
			builder.append(FrameworkConstants.SYMBOL_TEN);
			builder.append(_table);
			builder.append(FrameworkConstants.SYMBOL_TEN);
			builder.append(" SET ");
			builder.append(new_row);
			iterator = columnList.iterator();
			isEntered = false;
			while (iterator.hasNext()) {
				if (isEntered) {
					builder.append(FrameworkConstants.SYMBOL_COMMA);
					builder.append(new_row);
				} else {
					isEntered = true;
				}
				String column = iterator.next();
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(column);
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(equals);
				builder.append(start);
				builder.append(column);
				builder.append(end);
			}
			builder.append(new_row);
			builder.append(" WHERE <{where_expression}>;");
			informationBean.setUpdate_syn(builder.toString());

			builder.delete(0, builder.length());
			builder.append("DELETE FROM `");
			builder.append(apiConnection.getDatabase());
			builder.append(FrameworkConstants.SYMBOL_TEN);
			builder.append(FrameworkConstants.SYMBOL_DOT);
			builder.append(FrameworkConstants.SYMBOL_TEN);
			builder.append(_table);
			builder.append(FrameworkConstants.SYMBOL_TEN);
			builder.append(" WHERE <{where_expression}>;");
			informationBean.setDelete_syn(builder.toString());
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
	}
}
