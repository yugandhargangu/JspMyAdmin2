/**
 * 
 */
package com.jspmyadmin.app.server.database.logic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import com.jspmyadmin.app.server.database.beans.DatabaseInfo;
import com.jspmyadmin.app.server.database.beans.DatabaseListBean;
import com.jspmyadmin.app.server.database.beans.DatabaseInfo.DatabaseInfoComparator;
import com.jspmyadmin.framework.connection.AbstractLogic;
import com.jspmyadmin.framework.connection.ApiConnection;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.logic.EncDecLogic;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/09
 *
 */
public class DatabaseLogic extends AbstractLogic {

	/**
	 * 
	 * @param bean
	 * @throws Exception
	 */
	public void fillBean(Bean bean) throws Exception {
		DatabaseListBean databaseListBean = null;
		List<DatabaseInfo> databaseInfoList = null;
		DatabaseInfo databaseInfo = null;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		StringBuilder builder = null;
		JSONObject jsonObject = null;
		EncDecLogic encDecLogic = null;
		int count = 0;
		int tables = 0;
		int rows = 0;
		double data = 0;
		double index = 0;
		String strData = null;
		String strIndex = null;
		try {
			databaseListBean = (DatabaseListBean) bean;
			apiConnection = getConnection(false);
			builder = new StringBuilder();
			builder.append("SELECT a.db_name, a.db_collation, CASE WHEN b.db_table_count > 0 ");
			builder.append("THEN b.db_table_count ELSE 0 END, CASE WHEN b.db_rows_count > 0 ");
			builder.append("THEN b.db_rows_count ELSE 0 END, CASE WHEN b.db_data > 0 THEN ");
			builder.append("b.db_data ELSE 0 END, CASE WHEN b.db_index > 0 THEN b.db_index ");
			builder.append("ELSE 0 END, CASE WHEN b.db_total THEN b.db_total ELSE 0 END ");
			builder.append("FROM ( SELECT SCHEMA_NAME AS db_name, DEFAULT_COLLATION_NAME ");
			builder.append("AS db_collation FROM information_schema.SCHEMATA) AS a ");
			builder.append("LEFT JOIN (SELECT COUNT(TABLE_NAME) AS db_table_count, ");
			builder.append("SUM(TABLE_ROWS) AS db_rows_count, SUM(DATA_LENGTH) AS db_data, ");
			builder.append("SUM(INDEX_LENGTH) AS db_index, SUM(DATA_LENGTH + INDEX_LENGTH) ");
			builder.append("AS db_total ,TABLE_SCHEMA AS db_name FROM information_schema.TABLES ");
			builder.append("GROUP BY db_name) AS b ON a.db_name = b.db_name GROUP BY a.db_name");
			statement = apiConnection.preparedStatementSelect(builder.toString());
			resultSet = statement.executeQuery();
			databaseInfoList = new ArrayList<DatabaseInfo>();
			encDecLogic = new EncDecLogic();
			while (resultSet.next()) {
				databaseInfo = new DatabaseInfo();
				databaseInfo.setDatabase(resultSet.getString(1));
				databaseInfo.setCollation(resultSet.getString(2));
				databaseInfo.setTables(resultSet.getString(3));
				databaseInfo.setRows(resultSet.getString(4));
				strData = resultSet.getString(5);
				databaseInfo.setData(strData);
				strIndex = resultSet.getString(6);
				databaseInfo.setIndexes(strIndex);
				databaseInfo.setTotal(resultSet.getString(7));
				jsonObject = new JSONObject();
				jsonObject.put(FrameworkConstants.DATABASE, databaseInfo.getDatabase());
				databaseInfo.setAction(encDecLogic.encode(jsonObject.toString()));
				count++;
				tables += Integer.parseInt(databaseInfo.getTables());
				rows += Integer.parseInt(databaseInfo.getRows());
				data += Double.parseDouble(strData);
				index += Double.parseDouble(strIndex);
				databaseInfoList.add(databaseInfo);

			}
			DatabaseInfoComparator comparator = new DatabaseInfoComparator(databaseListBean.getToken());
			Collections.sort(databaseInfoList, comparator);
			databaseListBean.setDatabase_list(databaseInfoList);
			databaseListBean.setSortInfo(comparator.getSortInfo());
			databaseListBean.setSort(Integer.toString(comparator.getField()));
			databaseListBean.setType(Boolean.toString(!comparator.getType()));
			databaseInfo = new DatabaseInfo();
			databaseInfo.setDatabase(Integer.toString(count));
			databaseInfo.setTables(Integer.toString(tables));
			databaseInfo.setRows(Integer.toString(rows));
			databaseInfo.setData(Double.toString(data));
			databaseInfo.setIndexes(Double.toString(index));
			databaseInfo.setTotal(Double.toString(data + index));
			databaseListBean.setFooterInfo(databaseInfo);
		} finally {
			close(resultSet);
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
	public void createDatabase(Bean bean) throws ClassNotFoundException, SQLException {

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		StringBuilder query = null;
		DatabaseListBean databaseListBean = null;
		try {
			databaseListBean = (DatabaseListBean) bean;
			apiConnection = getConnection(false);
			query = new StringBuilder("CREATE DATABASE ");
			query.append(FrameworkConstants.SYMBOL_TEN);
			query.append(databaseListBean.getDatabase());
			query.append(FrameworkConstants.SYMBOL_TEN);
			if (!super.isEmpty(databaseListBean.getCollation())) {
				query.append(" COLLATE ");
				query.append(databaseListBean.getCollation());
			}
			statement = apiConnection.preparedStatement(query.toString());
			statement.executeUpdate();
			apiConnection.commit();
		} catch (SQLException e) {
			apiConnection.rollback();
			throw e;
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
	public void dropDatabase(Bean bean) throws ClassNotFoundException, SQLException {

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		StringBuilder query = null;
		DatabaseListBean databaseListBean = null;
		try {
			databaseListBean = (DatabaseListBean) bean;
			apiConnection = getConnection(false);
			query = new StringBuilder();
			if (databaseListBean.getDatabases() != null) {
				for (int i = 0; i < databaseListBean.getDatabases().length; i++) {
					query.append("DROP DATABASE IF EXISTS ");
					query.append(FrameworkConstants.SYMBOL_TEN);
					query.append(databaseListBean.getDatabases()[i]);
					query.append(FrameworkConstants.SYMBOL_TEN);
					statement = apiConnection.preparedStatement(query.toString());
					statement.executeUpdate();
				}
			}
			apiConnection.commit();
		} catch (SQLException e) {
			apiConnection.rollback();
			throw e;
		} finally {
			close(statement);
			close(apiConnection);
		}
	}
}
