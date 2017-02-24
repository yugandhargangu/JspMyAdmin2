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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jspmyadmin.app.table.common.beans.ForeignKeyBean;
import com.jspmyadmin.app.table.common.beans.ForeignKeyInfo;
import com.jspmyadmin.framework.connection.AbstractLogic;
import com.jspmyadmin.framework.connection.ApiConnection;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/06
 *
 */
public class ForeignKeyLogic extends AbstractLogic {

	private final String _table;

	public ForeignKeyLogic(String table) {
		_table = table;
	}

	/**
	 * 
	 * @param bean
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void fillBean(Bean bean) throws SQLException {

		ForeignKeyBean foreignKeyBean = (ForeignKeyBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {

			apiConnection = getConnection(bean.getRequest_db());
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT a.constraint_name, a.column_name, a.referenced_table_name,");
			builder.append("a.referenced_column_name,b.update_rule,b.delete_rule ");
			builder.append("FROM information_schema.key_column_usage AS a JOIN ");
			builder.append("information_schema.referential_constraints AS b ");
			builder.append("ON a.table_schema = b.constraint_schema AND a.table_name = b.table_name ");
			builder.append("WHERE a.referenced_table_name IS NOT NUll AND a.table_schema = ? ");
			builder.append("AND a.table_name = ? GROUP BY a.constraint_name");
			statement = apiConnection.getStmtSelect(builder.toString());
			statement.setString(1, bean.getRequest_db());
			statement.setString(2, _table);
			resultSet = statement.executeQuery();
			List<ForeignKeyInfo> foreignKeyInfoList = new ArrayList<ForeignKeyInfo>();
			ForeignKeyInfo foreignKeyInfo = null;
			while (resultSet.next()) {
				foreignKeyInfo = new ForeignKeyInfo();
				foreignKeyInfo.setKey_name(resultSet.getString(1));
				foreignKeyInfo.setColumn_name(resultSet.getString(2));
				foreignKeyInfo.setRef_table_name(resultSet.getString(3));
				foreignKeyInfo.setRef_column_name(resultSet.getString(4));
				foreignKeyInfo.setUpdate_rule(resultSet.getString(5));
				foreignKeyInfo.setDelete_rule(resultSet.getString(6));
				foreignKeyInfoList.add(foreignKeyInfo);
			}
			foreignKeyBean.setForeign_key_info_list(foreignKeyInfoList);
			close(resultSet);
			close(statement);

			builder.delete(0, builder.length());
			builder.append("SELECT a.constraint_name, a.referenced_column_name, a.table_name,");
			builder.append("a.column_name, b.update_rule,b.delete_rule ");
			builder.append("FROM information_schema.key_column_usage AS a JOIN ");
			builder.append("information_schema.referential_constraints AS b ");
			builder.append("ON a.table_schema = b.constraint_schema AND a.table_name = b.table_name ");
			builder.append("WHERE a.referenced_table_name IS NOT NUll AND a.table_schema = ? ");
			builder.append("AND a.referenced_table_name = ? GROUP BY a.constraint_name");
			statement = apiConnection.getStmtSelect(builder.toString());
			statement.setString(1, bean.getRequest_db());
			statement.setString(2, _table);
			resultSet = statement.executeQuery();
			foreignKeyInfoList = new ArrayList<ForeignKeyInfo>();
			foreignKeyInfo = null;
			while (resultSet.next()) {
				foreignKeyInfo = new ForeignKeyInfo();
				foreignKeyInfo.setKey_name(resultSet.getString(1));
				foreignKeyInfo.setColumn_name(resultSet.getString(2));
				foreignKeyInfo.setRef_table_name(resultSet.getString(3));
				foreignKeyInfo.setRef_column_name(resultSet.getString(4));
				foreignKeyInfo.setUpdate_rule(resultSet.getString(5));
				foreignKeyInfo.setDelete_rule(resultSet.getString(6));
				foreignKeyInfoList.add(foreignKeyInfo);
			}
			foreignKeyBean.setReference_key_info_list(foreignKeyInfoList);
			close(resultSet);
			close(statement);

			builder.delete(0, builder.length());
			builder.append("SELECT * FROM `");
			builder.append(_table);
			builder.append("` WHERE TRUE IS FALSE");
			statement = apiConnection.getStmtSelect(builder.toString());
			resultSet = statement.executeQuery();
			ResultSetMetaData metaData = resultSet.getMetaData();
			List<String> columnList = new ArrayList<String>(metaData.getColumnCount());
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				columnList.add(metaData.getColumnName(i));
			}
			foreignKeyBean.setColumn_list(columnList);
			close(resultSet);
			close(statement);

			builder.delete(0, builder.length());
			builder.append("SHOW FULL TABLES FROM `");
			builder.append(bean.getRequest_db());
			builder.append("` WHERE table_type = ?");
			statement = apiConnection.getStmtSelect(builder.toString());
			statement.setString(1, Constants.BASE_TABLE);
			resultSet = statement.executeQuery();
			List<String> tableList = new ArrayList<String>();
			while (resultSet.next()) {
				tableList.add(resultSet.getString(1));
			}
			foreignKeyBean.setRef_table_list(tableList);
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
	}

	/**
	 * 
	 * @param database
	 * @return
	 * @throws SQLException
	 * @throws JSONException
	 */
	public JSONObject fetchColumns(String database) throws SQLException, JSONException {

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		JSONObject jsonObject = null;
		try {
			apiConnection = getConnection(database);
			statement = apiConnection.getStmtSelect("SHOW COLUMNS FROM `" + _table + "`");
			resultSet = statement.executeQuery();
			JSONArray jsonArray = new JSONArray();
			while (resultSet.next()) {
				jsonArray.put(resultSet.getString(1));
			}
			jsonObject = new JSONObject();
			jsonObject.put(Constants.DATA, jsonArray);
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
		return jsonObject;
	}

	/**
	 * 
	 * @param bean
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void dropForeignKeys(Bean bean) throws SQLException {
		ForeignKeyBean foreignKeyBean = (ForeignKeyBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		try {
			if (foreignKeyBean.getKeys() != null && foreignKeyBean.getKeys().length > 0) {
				apiConnection = getConnection(bean.getRequest_db());
				StringBuilder builder = new StringBuilder("ALTER TABLE `");
				builder.append(_table);
				builder.append(Constants.SYMBOL_TEN);
				boolean alreadyEntered = false;
				for (String key : foreignKeyBean.getKeys()) {
					if (alreadyEntered) {
						builder.append(Constants.SYMBOL_COMMA);
					} else {
						alreadyEntered = true;
					}
					builder.append(" DROP FOREIGN KEY `");

					builder.append(key);
					builder.append(Constants.SYMBOL_TEN);
				}
				statement = apiConnection.getStmt(builder.toString());
				statement.execute();
				apiConnection.commit();
			}
		} finally {
			close(statement);
			close(apiConnection);
		}
	}

	/**
	 * 
	 * @param bean
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void addForeignKey(Bean bean) throws SQLException {
		ForeignKeyBean foreignKeyBean = (ForeignKeyBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		try {
			apiConnection = getConnection(bean.getRequest_db());
			StringBuilder builder = new StringBuilder("ALTER TABLE `");
			builder.append(_table);
			builder.append(Constants.SYMBOL_TEN);
			builder.append(" ADD FOREIGN KEY (`");
			builder.append(foreignKeyBean.getColumn_name());
			builder.append("`)");
			builder.append(" REFERENCES `");
			builder.append(foreignKeyBean.getRef_table_name());
			builder.append("`(`");
			builder.append(foreignKeyBean.getRef_column_name());
			builder.append("`)");
			if (!isEmpty(foreignKeyBean.getDelete_action())) {
				builder.append(" ON DELETE ");
				builder.append(foreignKeyBean.getDelete_action());
			}
			if (!isEmpty(foreignKeyBean.getUpdate_action())) {
				builder.append(" ON UPDATE ");
				builder.append(foreignKeyBean.getUpdate_action());
			}
			statement = apiConnection.getStmt(builder.toString());
			statement.execute();
		} finally {
			close(statement);
			close(apiConnection);
		}
	}
}
