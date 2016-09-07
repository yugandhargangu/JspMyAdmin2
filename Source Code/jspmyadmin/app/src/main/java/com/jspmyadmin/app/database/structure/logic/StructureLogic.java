/**
 * 
 */
package com.jspmyadmin.app.database.structure.logic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import com.jspmyadmin.app.common.logic.HomeLogic;
import com.jspmyadmin.app.database.structure.beans.CreateTableBean;
import com.jspmyadmin.app.database.structure.beans.CreateViewBean;
import com.jspmyadmin.app.database.structure.beans.StructureBean;
import com.jspmyadmin.app.database.structure.beans.TableInfo;
import com.jspmyadmin.framework.connection.AbstractLogic;
import com.jspmyadmin.framework.connection.ApiConnection;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.utils.Bean;
import com.jspmyadmin.framework.web.utils.Messages;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/15
 *
 */
public class StructureLogic extends AbstractLogic {

	private Messages messages = null;

	/**
	 * @param messages
	 *            the messages to set
	 */
	public void setMessages(Messages messages) {
		this.messages = messages;
	}

	/**
	 * 
	 * @param bean
	 * @throws SQLException
	 * @throws EncodingException
	 * @throws JSONException
	 * @throws Exception
	 */
	public void fillBean(Bean bean, final boolean onlyTables) throws SQLException, JSONException, EncodingException {
		StructureBean structureBean = null;
		List<TableInfo> tableInfoList = null;
		TableInfo tableInfo = null;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		ResultSetMetaData resultSetMetaData = null;
		StringBuilder builder = null;
		JSONObject jsonObject = null;
		String orderBy = "TABLE_NAME";
		boolean sort = false;
		int count = 0;
		double total = 0;
		long rows = 0L;
		String temp = null;
		try {
			structureBean = (StructureBean) bean;
			apiConnection = getConnection(bean.getRequest_db());
			if (structureBean.getToken() != null) {
				jsonObject = new JSONObject(encodeObj.decode(structureBean.getToken()));
				if (jsonObject != null) {
					if (jsonObject.has(Constants.COLUMN)) {
						orderBy = jsonObject.getString(Constants.COLUMN);
					}
					if (jsonObject.has(Constants.TYPE)) {
						sort = jsonObject.getBoolean(Constants.TYPE);
					}
				}
			}
			builder = new StringBuilder();
			builder.append("SELECT TABLE_NAME, TABLE_TYPE, ENGINE, TABLE_ROWS, ");
			builder.append("TABLE_COLLATION, (DATA_LENGTH + INDEX_LENGTH) AS ");
			builder.append("DATA_SIZE, AUTO_INCREMENT, CREATE_TIME, UPDATE_TIME, ");
			builder.append("TABLE_COMMENT FROM information_schema.TABLES WHERE ");
			builder.append("TABLE_SCHEMA = ? AND TABLE_TYPE = ? ORDER BY ");
			builder.append(orderBy);
			if (sort) {
				builder.append(" DESC");
			} else {
				builder.append(" ASC");
			}
			statement = apiConnection.getStmtSelect(builder.toString());
			statement.setString(1, bean.getRequest_db());
			if (onlyTables) {
				statement.setString(2, Constants.BASE_TABLE);
			} else {
				statement.setString(2, Constants.VIEW_UPPER_CASE);
			}
			resultSet = statement.executeQuery();

			tableInfoList = new ArrayList<TableInfo>();
			while (resultSet.next()) {
				tableInfo = new TableInfo();
				tableInfo.setName(resultSet.getString(1));
				tableInfo.setType(resultSet.getString(2));
				tableInfo.setEngine(resultSet.getString(3));
				temp = resultSet.getString(4);
				if (temp != null) {
					rows += Long.parseLong(temp);
				}
				tableInfo.setRows(temp);
				tableInfo.setCollation(resultSet.getString(5));
				temp = resultSet.getString(6);
				if (temp != null) {
					total += Double.parseDouble(temp);
				}
				tableInfo.setSize(temp);
				tableInfo.setAuto_inr(resultSet.getString(7));
				tableInfo.setCreate_date(resultSet.getString(8));
				tableInfo.setUpdate_date(resultSet.getString(9));
				tableInfo.setComment(resultSet.getString(10));
				jsonObject = new JSONObject();
				jsonObject.put(Constants.REQUEST_DB, bean.getRequest_db());
				if (onlyTables) {
					jsonObject.put(Constants.REQUEST_TABLE, tableInfo.getName());
				} else {
					jsonObject.put(Constants.REQUEST_VIEW, tableInfo.getName());
				}
				tableInfo.setAction(encodeObj.encode(jsonObject.toString()));
				tableInfoList.add(tableInfo);
				count++;
			}
			structureBean.setTable_list(tableInfoList);

			tableInfo = new TableInfo();
			tableInfo.setName(Integer.toString(count));
			tableInfo.setRows(Long.toString(rows));
			tableInfo.setSize(Double.toString(total));
			structureBean.setFooterInfo(tableInfo);

			tableInfo = new TableInfo();
			resultSetMetaData = resultSet.getMetaData();
			for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
				temp = resultSetMetaData.getColumnName(i + 1);
				jsonObject = new JSONObject();
				jsonObject.put(Constants.REQUEST_DB, bean.getRequest_db());
				jsonObject.put(Constants.COLUMN, temp);
				if (temp.equalsIgnoreCase(orderBy)) {
					jsonObject.put(Constants.TYPE, !sort);
					structureBean.setSort(Integer.toString(i + 1));
					structureBean.setType(Boolean.toString(sort));
				} else {
					jsonObject.put(Constants.TYPE, false);
				}
				temp = encodeObj.encode(jsonObject.toString());
				switch (i) {
				case 0:
					tableInfo.setName(temp);
					break;
				case 1:
					tableInfo.setType(temp);
					break;
				case 2:
					tableInfo.setEngine(temp);
					break;
				case 3:
					tableInfo.setRows(temp);
					break;
				case 4:
					tableInfo.setCollation(temp);
					break;
				case 5:
					tableInfo.setSize(temp);
					break;
				case 6:
					tableInfo.setAuto_inr(temp);
					break;
				case 7:
					tableInfo.setCreate_date(temp);
					break;
				case 8:
					tableInfo.setUpdate_date(temp);
					break;
				case 9:
					tableInfo.setComment(temp);
					break;
				default:
					break;
				}
			}
			structureBean.setSortInfo(tableInfo);
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
	 * @throws Exception
	 */
	public void dropTables(Bean bean, final boolean onlyTables) throws SQLException {

		StructureBean structureBean = null;
		ApiConnection apiConnection = null;
		PreparedStatement statement = null;

		StringBuilder builder = null;
		boolean withChecks = false;
		try {
			structureBean = (StructureBean) bean;
			if (structureBean.getTables() != null) {
				apiConnection = getConnection(bean.getRequest_db());
				if (Constants.YES.equalsIgnoreCase(structureBean.getEnable_checks())) {
					withChecks = true;
				}
				if (!withChecks) {
					statement = apiConnection.getStmt("SET foreign_key_checks = ?");
					statement.setInt(1, 0);
					statement.execute();
					statement.close();
					statement = null;
				}
				if (onlyTables) {
					builder = new StringBuilder("DROP TABLE IF EXISTS ");
					for (int i = 0; i < structureBean.getTables().length; i++) {
						builder.append(Constants.SYMBOL_TEN);
						builder.append(structureBean.getTables()[i]);
						builder.append(Constants.SYMBOL_TEN);
						if (structureBean.getTables().length != i + 1) {
							builder.append(Constants.SYMBOL_COMMA);
						}
					}
					statement = apiConnection.getStmt(builder.toString());
					statement.execute();
					statement.close();
					statement = null;
				} else {
					builder = new StringBuilder("DROP VIEW IF EXISTS ");
					for (int i = 0; i < structureBean.getTables().length; i++) {
						builder.append(Constants.SYMBOL_TEN);
						builder.append(structureBean.getTables()[i]);
						builder.append(Constants.SYMBOL_TEN);
						if (structureBean.getTables().length != i + 1) {
							builder.append(Constants.SYMBOL_COMMA);
						}
					}
					statement = apiConnection.getStmt(builder.toString());
					statement.execute();
					statement.close();
					statement = null;
				}
				if (!withChecks) {
					statement = apiConnection.getStmt("SET foreign_key_checks = ?");
					statement.setInt(1, 1);
					statement.execute();
					statement.close();
					statement = null;
				}
				apiConnection.commit();
			}
		} catch (SQLException e) {
			if (apiConnection != null) {
				apiConnection.rollback();
			}
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
	 * @throws Exception
	 */
	public void truncateTables(Bean bean) throws SQLException {

		StructureBean structureBean = null;
		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		String query = "TRUNCATE TABLE ";
		boolean withChecks = false;
		try {
			structureBean = (StructureBean) bean;
			if (structureBean.getTables() != null) {
				apiConnection = getConnection(bean.getRequest_db());
				if (Constants.YES.equalsIgnoreCase(structureBean.getEnable_checks())) {
					withChecks = true;
				}
				if (!withChecks) {
					statement = apiConnection.getStmt("SET foreign_key_checks = ?");
					statement.setInt(1, 0);
					statement.execute();
					statement.close();
					statement = null;
				}
				for (int i = 0; i < structureBean.getTables().length; i++) {
					statement = apiConnection.getStmt(query + Constants.SYMBOL_TEN
							+ structureBean.getTables()[i] + Constants.SYMBOL_TEN);
					statement.execute();
					statement.close();
					statement = null;
				}

				if (!withChecks) {
					statement = apiConnection.getStmt("SET foreign_key_checks = ?");
					statement.setInt(1, 1);
					statement.execute();
					statement.close();
					statement = null;
				}
				apiConnection.commit();
			}
		} catch (SQLException e) {
			if (apiConnection != null) {
				apiConnection.rollback();
			}
			throw e;
		} finally {
			close(statement);
			close(apiConnection);
		}
	}

	/**
	 * 
	 * @param bean
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws JSONException
	 * @throws Exception
	 */
	public String showCreate(Bean bean, final boolean onlyTables) throws SQLException, JSONException {
		StructureBean structureBean = null;

		String result = null;
		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		JSONObject jsonObject = null;
		StringBuilder builder = null;
		String one = "SHOW CREATE TABLE ";
		String two = "SHOW CREATE VIEW ";
		try {
			structureBean = (StructureBean) bean;
			if (structureBean.getTables() != null) {
				apiConnection = getConnection(bean.getRequest_db());
				jsonObject = new JSONObject();
				builder = new StringBuilder();
				for (int i = 0; i < structureBean.getTables().length; i++) {
					builder.delete(0, builder.length());
					if (onlyTables) {
						builder.append(one);
					} else {
						builder.append(two);
					}
					builder.append(Constants.SYMBOL_TEN);
					builder.append(structureBean.getTables()[i]);
					builder.append(Constants.SYMBOL_TEN);
					statement = apiConnection.getStmtSelect(builder.toString());
					resultSet = statement.executeQuery();
					if (resultSet.next()) {
						jsonObject.put(resultSet.getString(1),
								resultSet.getString(2) + Constants.SYMBOL_SEMI_COLON);
					}
				}
				result = jsonObject.toString();
			}
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
		return result;
	}

	/**
	 * 
	 * @param bean
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws Exception
	 */
	public void copyTables(Bean bean) throws SQLException {

		StructureBean structureBean = null;
		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		StringBuilder builder = null;
		List<String> tableList = null;
		boolean withDrops = false;
		boolean withData = false;

		String temp1 = "USE ";
		String temp2 = "DROP TABLE IF EXISTS ";
		String temp3 = "CREATE TABLE IF NOT EXISTS ";
		String temp4 = " LIKE ";
		String temp5 = "INSERT INTO ";
		String temp6 = "SELECT * FROM ";
		try {
			structureBean = (StructureBean) bean;
			if (structureBean.getTables() != null) {
				apiConnection = getConnection();
				statement = apiConnection.getStmt("SET foreign_key_checks = ?");
				statement.setInt(1, 0);
				statement.execute();
				statement.close();
				statement = null;
				if (Constants.DATA.equalsIgnoreCase(structureBean.getType())) {
					withData = true;
				}

				if (Constants.YES.equalsIgnoreCase(structureBean.getDrop_checks())) {
					withDrops = true;
				}

				builder = new StringBuilder();
				if (withDrops) {
					// drop tables
					builder.delete(0, builder.length());
					builder.append(temp1);
					builder.append(Constants.SYMBOL_TEN);
					builder.append(structureBean.getDatabase_name());
					builder.append(Constants.SYMBOL_TEN);
					statement = apiConnection.getStmt(builder.toString());
					statement.execute();
					statement.close();
					statement = null;

					builder.delete(0, builder.length());
					builder.append(temp2);
					for (int i = 0; i < structureBean.getTables().length; i++) {
						builder.append(structureBean.getTables()[i]);
						if (structureBean.getTables().length != i + 1) {
							builder.append(Constants.SYMBOL_COMMA);
						}
					}
					statement = apiConnection.getStmt(builder.toString());
					statement.execute();
					statement.close();
					statement = null;
				}

				for (int i = 0; i < structureBean.getTables().length; i++) {

					// get table create statement
					builder.delete(0, builder.length());
					builder.append(temp3);
					builder.append(Constants.SYMBOL_TEN);
					builder.append(structureBean.getDatabase_name());
					builder.append(Constants.SYMBOL_TEN);
					builder.append(Constants.SYMBOL_DOT);
					builder.append(Constants.SYMBOL_TEN);
					builder.append(structureBean.getTables()[i]);
					builder.append(Constants.SYMBOL_TEN);
					builder.append(temp4);
					builder.append(Constants.SYMBOL_TEN);
					builder.append(bean.getRequest_db());
					builder.append(Constants.SYMBOL_TEN);
					builder.append(Constants.SYMBOL_DOT);
					builder.append(Constants.SYMBOL_TEN);
					builder.append(structureBean.getTables()[i]);
					builder.append(Constants.SYMBOL_TEN);
					statement = apiConnection.getStmt(builder.toString());
					statement.execute();
					statement.close();
					statement = null;

					// insert data
					if (withData) {
						builder.delete(0, builder.length());
						builder.append(temp5);
						builder.append(Constants.SYMBOL_TEN);
						builder.append(structureBean.getDatabase_name());
						builder.append(Constants.SYMBOL_TEN);
						builder.append(Constants.SYMBOL_DOT);
						builder.append(Constants.SYMBOL_TEN);
						builder.append(structureBean.getTables()[i]);
						builder.append(Constants.SYMBOL_TEN);
						builder.append(temp6);
						builder.append(Constants.SYMBOL_TEN);
						builder.append(bean.getRequest_db());
						builder.append(Constants.SYMBOL_TEN);
						builder.append(Constants.SYMBOL_DOT);
						builder.append(Constants.SYMBOL_TEN);
						builder.append(structureBean.getTables()[i]);
						builder.append(Constants.SYMBOL_TEN);
						statement = apiConnection.getStmt(builder.toString());
						statement.execute();
						statement.close();
						statement = null;
					}
				}

				tableList = Arrays.asList(structureBean.getTables());
				_checkForeignKeys(apiConnection, statement, resultSet, tableList, tableList, bean.getRequest_db(),
						structureBean.getDatabase_name());

				statement = apiConnection.getStmt("SET foreign_key_checks = ?");
				statement.setInt(1, 1);
				statement.execute();
				statement.close();
				statement = null;
				apiConnection.commit();
			}
		} catch (SQLException e) {
			if (apiConnection != null) {
				apiConnection.rollback();
			}
			throw e;
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
	 * @throws Exception
	 */
	public void addPrefix(Bean bean) throws SQLException {
		StructureBean structureBean = null;

		List<String> newTableList = new ArrayList<String>();
		List<String> tableList = new ArrayList<String>();
		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		StringBuilder builder = null;
		String to = " TO ";
		try {
			structureBean = (StructureBean) bean;
			if (structureBean.getTables() != null) {
				apiConnection = getConnection(bean.getRequest_db());
				statement = apiConnection.getStmt("SET foreign_key_checks = ?");
				statement.setInt(1, 0);
				statement.execute();
				statement.close();
				statement = null;

				builder = new StringBuilder();
				builder.append("RENAME TABLE ");
				for (int i = 0; i < structureBean.getTables().length; i++) {
					tableList.add(structureBean.getTables()[i]);
					newTableList.add(structureBean.getPrefix() + structureBean.getTables()[i]);
					builder.append(Constants.SYMBOL_TEN);
					builder.append(structureBean.getTables()[i]);
					builder.append(Constants.SYMBOL_TEN);
					builder.append(to);
					builder.append(Constants.SYMBOL_TEN);
					builder.append(structureBean.getPrefix());
					builder.append(structureBean.getTables()[i]);
					builder.append(Constants.SYMBOL_TEN);
					builder.append(Constants.SYMBOL_COMMA);
				}
				if (tableList.size() > 0) {
					builder = builder.deleteCharAt(builder.lastIndexOf(Constants.SYMBOL_COMMA));
					statement = apiConnection.getStmt(builder.toString());
					statement.execute();
					statement.close();
					statement = null;

					_checkForeignKeys(bean.getRequest_db(), apiConnection, statement, resultSet, tableList,
							newTableList);
				}
				statement = apiConnection.getStmt("SET foreign_key_checks = ?");
				statement.setInt(1, 1);
				statement.execute();
				statement.close();
				statement = null;
				apiConnection.commit();
			}
		} catch (SQLException e) {
			if (apiConnection != null) {
				apiConnection.rollback();
			}
			throw e;
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
	 * @throws Exception
	 */
	public void addSuffix(Bean bean) throws SQLException {

		List<String> newTableList = new ArrayList<String>();
		List<String> tableList = new ArrayList<String>();
		StructureBean structureBean = null;
		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		StringBuilder builder = null;
		String to = " TO ";
		try {
			structureBean = (StructureBean) bean;
			if (structureBean.getTables() != null) {
				apiConnection = getConnection(bean.getRequest_db());
				statement = apiConnection.getStmt("SET foreign_key_checks = ?");
				statement.setInt(1, 0);
				statement.execute();
				statement.close();
				statement = null;

				builder = new StringBuilder();
				builder.append("RENAME TABLE ");
				for (int i = 0; i < structureBean.getTables().length; i++) {
					tableList.add(structureBean.getTables()[i]);
					newTableList.add(structureBean.getTables()[i] + structureBean.getPrefix());
					builder.append(Constants.SYMBOL_TEN);
					builder.append(structureBean.getTables()[i]);
					builder.append(Constants.SYMBOL_TEN);
					builder.append(to);
					builder.append(Constants.SYMBOL_TEN);
					builder.append(structureBean.getTables()[i]);
					builder.append(structureBean.getPrefix());
					builder.append(Constants.SYMBOL_TEN);
					builder.append(Constants.SYMBOL_COMMA);
				}
				if (tableList.size() > 0) {
					builder = builder.deleteCharAt(builder.lastIndexOf(Constants.SYMBOL_COMMA));
					statement = apiConnection.getStmt(builder.toString());
					statement.execute();
					statement.close();
					statement = null;

					_checkForeignKeys(bean.getRequest_db(), apiConnection, statement, resultSet, tableList,
							newTableList);
				}

				statement = apiConnection.getStmt("SET foreign_key_checks = ?");
				statement.setInt(1, 1);
				statement.execute();
				statement.close();
				statement = null;
				apiConnection.commit();
			}
		} catch (SQLException e) {
			if (apiConnection != null) {
				apiConnection.rollback();
			}
			throw e;
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
	 * @throws Exception
	 */
	public void replacePrefix(Bean bean) throws SQLException {

		List<String> newTableList = new ArrayList<String>();
		List<String> tableList = new ArrayList<String>();
		StructureBean structureBean = null;
		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		StringBuilder builder = null;
		String to = " TO ";
		int length = 0;
		try {
			structureBean = (StructureBean) bean;
			if (structureBean.getTables() != null) {
				apiConnection = getConnection(bean.getRequest_db());
				statement = apiConnection.getStmt("SET foreign_key_checks = ?");
				statement.setInt(1, 0);
				statement.execute();
				statement.close();
				statement = null;

				length = structureBean.getPrefix().length();
				builder = new StringBuilder();
				builder.append("RENAME TABLE ");
				for (int i = 0; i < structureBean.getTables().length; i++) {
					if (structureBean.getTables()[i].indexOf(structureBean.getPrefix()) == 0) {
						tableList.add(structureBean.getTables()[i]);
						newTableList
								.add(structureBean.getNew_prefix() + structureBean.getTables()[i].substring(length));
						builder.append(Constants.SYMBOL_TEN);
						builder.append(structureBean.getTables()[i]);
						builder.append(Constants.SYMBOL_TEN);
						builder.append(to);
						builder.append(Constants.SYMBOL_TEN);
						builder.append(structureBean.getNew_prefix());
						builder.append(structureBean.getTables()[i].substring(length));
						builder.append(Constants.SYMBOL_TEN);
						builder.append(Constants.SYMBOL_COMMA);
					}
				}
				if (tableList.size() > 0) {
					builder = builder.deleteCharAt(builder.lastIndexOf(Constants.SYMBOL_COMMA));
					statement = apiConnection.getStmt(builder.toString());
					statement.execute();
					statement.close();
					statement = null;

					_checkForeignKeys(bean.getRequest_db(), apiConnection, statement, resultSet, tableList,
							newTableList);
				}

				statement = apiConnection.getStmt("SET foreign_key_checks = ?");
				statement.setInt(1, 1);
				statement.execute();
				statement.close();
				statement = null;
				apiConnection.commit();
			}
		} catch (SQLException e) {
			if (apiConnection != null) {
				apiConnection.rollback();
			}
			throw e;
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
	 * @throws Exception
	 */
	public void replaceSuffix(Bean bean) throws SQLException {

		List<String> newTableList = new ArrayList<String>();
		List<String> tableList = new ArrayList<String>();
		StructureBean structureBean = null;
		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		StringBuilder builder = null;
		String to = " TO ";
		int length = 0;
		try {
			structureBean = (StructureBean) bean;
			if (structureBean.getTables() != null) {
				apiConnection = getConnection(bean.getRequest_db());
				statement = apiConnection.getStmt("SET foreign_key_checks = ?");
				statement.setInt(1, 0);
				statement.execute();
				statement.close();
				statement = null;

				length = structureBean.getPrefix().length();
				builder = new StringBuilder();
				builder.append("RENAME TABLE ");
				for (int i = 0; i < structureBean.getTables().length; i++) {
					if (structureBean.getTables()[i].endsWith(structureBean.getPrefix())) {
						tableList.add(structureBean.getTables()[i]);
						newTableList.add(structureBean.getTables()[i].substring(0,
								structureBean.getTables()[i].length() - length) + structureBean.getNew_prefix());
						builder.append(Constants.SYMBOL_TEN);
						builder.append(structureBean.getTables()[i]);
						builder.append(Constants.SYMBOL_TEN);
						builder.append(to);
						builder.append(Constants.SYMBOL_TEN);
						builder.append(structureBean.getTables()[i].substring(0,
								structureBean.getTables()[i].length() - length));
						builder.append(structureBean.getNew_prefix());
						builder.append(Constants.SYMBOL_TEN);
						builder.append(Constants.SYMBOL_COMMA);
					}
				}
				if (tableList.size() > 0) {
					builder = builder.deleteCharAt(builder.lastIndexOf(Constants.SYMBOL_COMMA));
					statement = apiConnection.getStmt(builder.toString());
					statement.execute();
					statement.close();
					statement = null;

					_checkForeignKeys(bean.getRequest_db(), apiConnection, statement, resultSet, tableList,
							newTableList);
				}
				statement = apiConnection.getStmt("SET foreign_key_checks = ?");
				statement.setInt(1, 1);
				statement.execute();
				statement.close();
				statement = null;
				apiConnection.commit();
			}
		} catch (SQLException e) {
			if (apiConnection != null) {
				apiConnection.rollback();
			}
			throw e;
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
	 * @throws Exception
	 */
	public void removePrefix(Bean bean) throws SQLException {

		List<String> newTableList = new ArrayList<String>();
		List<String> tableList = new ArrayList<String>();
		StructureBean structureBean = null;
		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		StringBuilder builder = null;
		String to = " TO ";
		int length = 0;
		try {
			structureBean = (StructureBean) bean;
			if (structureBean.getTables() != null) {
				apiConnection = getConnection(bean.getRequest_db());
				statement = apiConnection.getStmt("SET foreign_key_checks = ?");
				statement.setInt(1, 0);
				statement.execute();
				statement.close();
				statement = null;

				length = structureBean.getPrefix().length();
				builder = new StringBuilder();
				builder.append("RENAME TABLE ");
				for (int i = 0; i < structureBean.getTables().length; i++) {
					if (structureBean.getTables()[i].indexOf(structureBean.getPrefix()) == 0) {
						tableList.add(structureBean.getTables()[i]);
						newTableList.add(structureBean.getTables()[i].substring(length));
						builder.append(Constants.SYMBOL_TEN);
						builder.append(structureBean.getTables()[i]);
						builder.append(Constants.SYMBOL_TEN);
						builder.append(to);
						builder.append(Constants.SYMBOL_TEN);
						builder.append(structureBean.getTables()[i].substring(length));
						builder.append(Constants.SYMBOL_TEN);
						builder.append(Constants.SYMBOL_COMMA);
					}
				}
				if (tableList.size() > 0) {
					builder = builder.deleteCharAt(builder.lastIndexOf(Constants.SYMBOL_COMMA));
					statement = apiConnection.getStmt(builder.toString());
					statement.execute();
					statement.close();
					statement = null;

					_checkForeignKeys(bean.getRequest_db(), apiConnection, statement, resultSet, tableList,
							newTableList);
				}

				statement = apiConnection.getStmt("SET foreign_key_checks = ?");
				statement.setInt(1, 1);
				statement.execute();
				statement.close();
				statement = null;
				apiConnection.commit();
			}
		} catch (SQLException e) {
			if (apiConnection != null) {
				apiConnection.rollback();
			}
			throw e;
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
	 * @throws Exception
	 */
	public void removeSuffix(Bean bean) throws SQLException {

		List<String> newTableList = new ArrayList<String>();
		List<String> tableList = new ArrayList<String>();
		StructureBean structureBean = null;
		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		StringBuilder builder = null;
		String to = " TO ";
		int length = 0;
		try {
			structureBean = (StructureBean) bean;
			if (structureBean.getTables() != null) {
				apiConnection = getConnection(bean.getRequest_db());
				statement = apiConnection.getStmt("SET foreign_key_checks = ?");
				statement.setInt(1, 0);
				statement.execute();
				statement.close();
				statement = null;

				length = structureBean.getPrefix().length();
				builder = new StringBuilder();
				builder.append("RENAME TABLE ");
				for (int i = 0; i < structureBean.getTables().length; i++) {
					if (structureBean.getTables()[i].endsWith(structureBean.getPrefix())) {
						tableList.add(structureBean.getTables()[i]);
						newTableList.add(structureBean.getTables()[i].substring(0,
								structureBean.getTables()[i].length() - length));
						builder.append(Constants.SYMBOL_TEN);
						builder.append(structureBean.getTables()[i]);
						builder.append(Constants.SYMBOL_TEN);
						builder.append(to);
						builder.append(Constants.SYMBOL_TEN);
						builder.append(structureBean.getTables()[i].substring(0,
								structureBean.getTables()[i].length() - length));
						builder.append(Constants.SYMBOL_TEN);
						builder.append(Constants.SYMBOL_COMMA);
					}
				}
				if (tableList.size() > 0) {
					builder = builder.deleteCharAt(builder.lastIndexOf(Constants.SYMBOL_COMMA));
					statement = apiConnection.getStmt(builder.toString());
					statement.execute();
					statement.close();
					statement = null;

					_checkForeignKeys(bean.getRequest_db(), apiConnection, statement, resultSet, tableList,
							newTableList);
				}

				statement = apiConnection.getStmt("SET foreign_key_checks = ?");
				statement.setInt(1, 1);
				statement.execute();
				statement.close();
				statement = null;
				apiConnection.commit();
			}
		} catch (SQLException e) {
			if (apiConnection != null) {
				apiConnection.rollback();
			}
			throw e;
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
	 * @throws Exception
	 */
	public void duplicateTable(Bean bean) throws SQLException {

		StructureBean structureBean = null;
		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		PreparedStatement innerStatement = null;
		ResultSet resultSet = null;
		StringBuilder builder = null;
		String one = "CREATE TABLE ";
		String two = " LIKE ";
		String three = "INSERT ";
		String four = " SELECT * FROM ";
		String five = "ALTER TABLE ";
		String six = " ADD FOREIGN KEY (";
		String seven = ") REFERENCES ";
		String eight = "TRUNCATE TABLE ";
		String nine = " ON DELETE NO ACTION ON UPDATE NO ACTION";
		String ten = "SHOW TABLES LIKE ?";
		String duplicate = "-duplicate";
		boolean enableChecks = false;
		boolean withData = false;
		boolean isExisted = false;
		try {
			structureBean = (StructureBean) bean;
			if (structureBean.getTables() != null) {
				if (Constants.YES.equalsIgnoreCase(structureBean.getEnable_checks())) {
					enableChecks = true;
				}
				if (Constants.YES.equalsIgnoreCase(structureBean.getDrop_checks())) {
					withData = true;
				}
				apiConnection = getConnection(bean.getRequest_db());

				statement = apiConnection.getStmt("SET foreign_key_checks = ?");
				statement.setInt(1, 0);
				statement.execute();
				statement.close();
				statement = null;

				builder = new StringBuilder();
				for (int i = 0; i < structureBean.getTables().length; i++) {
					if (_getTableType(apiConnection, statement, resultSet, structureBean.getTables()[i]) > 0) {
						isExisted = false;
						statement = apiConnection.getStmtSelect(ten);
						statement.setString(1, structureBean.getTables()[i] + duplicate);
						resultSet = statement.executeQuery();
						while (resultSet.next()) {
							isExisted = true;
						}
						resultSet.close();
						resultSet = null;
						statement.close();
						statement = null;
						if (!isExisted) {
							builder.delete(0, builder.length());
							builder.append(one);
							builder.append(Constants.SYMBOL_TEN);
							builder.append(structureBean.getTables()[i]);
							builder.append(duplicate);
							builder.append(Constants.SYMBOL_TEN);
							builder.append(two);
							builder.append(Constants.SYMBOL_TEN);
							builder.append(structureBean.getTables()[i]);
							builder.append(Constants.SYMBOL_TEN);
							statement = apiConnection.getStmt(builder.toString());
							statement.execute();
							statement.close();
							statement = null;

							builder.delete(0, builder.length());
							builder.append(eight);
							builder.append(Constants.SYMBOL_TEN);
							builder.append(structureBean.getTables()[i]);
							builder.append(duplicate);
							builder.append(Constants.SYMBOL_TEN);
							statement = apiConnection.getStmt(builder.toString());
							statement.execute();
							statement.close();
							statement = null;

							if (enableChecks) {

								statement = apiConnection.getStmtSelect(
										"SELECT COLUMN_NAME,REFERENCED_TABLE_NAME,REFERENCED_COLUMN_NAME FROM information_schema."
												+ "KEY_COLUMN_USAGE WHERE REFERENCED_TABLE_SCHEMA IS NOT null AND CONSTRAINT_SCHEMA = ? AND TABLE_NAME = ?");
								statement.setString(1, bean.getRequest_db());
								statement.setString(2, structureBean.getTables()[i]);
								resultSet = statement.executeQuery();
								while (resultSet.next()) {
									builder.delete(0, builder.length());
									builder.append(five);
									builder.append(Constants.SYMBOL_TEN);
									builder.append(bean.getRequest_db());
									builder.append(Constants.SYMBOL_TEN);
									builder.append(Constants.SYMBOL_DOT);
									builder.append(Constants.SYMBOL_TEN);
									builder.append(structureBean.getTables()[i]);
									builder.append(duplicate);
									builder.append(Constants.SYMBOL_TEN);
									builder.append(six);
									builder.append(Constants.SYMBOL_TEN);
									builder.append(resultSet.getString(1));
									builder.append(Constants.SYMBOL_TEN);
									builder.append(seven);
									builder.append(Constants.SYMBOL_TEN);
									builder.append(bean.getRequest_db());
									builder.append(Constants.SYMBOL_TEN);
									builder.append(Constants.SYMBOL_DOT);
									builder.append(Constants.SYMBOL_TEN);
									builder.append(resultSet.getString(2));
									builder.append(Constants.SYMBOL_TEN);
									builder.append(Constants.SYMBOL_BRACKET_OPEN);
									builder.append(Constants.SYMBOL_TEN);
									builder.append(resultSet.getString(3));
									builder.append(Constants.SYMBOL_TEN);
									builder.append(Constants.SYMBOL_BRACKET_CLOSE);
									builder.append(nine);
									innerStatement = apiConnection.getStmt(builder.toString());
									innerStatement.execute();
									innerStatement.close();
									innerStatement = null;
								}
								resultSet.close();
								resultSet = null;
								statement.close();
								statement = null;
							}
							if (withData) {
								builder.delete(0, builder.length());
								builder.append(three);
								builder.append(Constants.SYMBOL_TEN);
								builder.append(structureBean.getTables()[i]);
								builder.append(duplicate);
								builder.append(Constants.SYMBOL_TEN);
								builder.append(four);
								builder.append(Constants.SYMBOL_TEN);
								builder.append(structureBean.getTables()[i]);
								builder.append(Constants.SYMBOL_TEN);
								statement = apiConnection.getStmt(builder.toString());
								statement.execute();
								statement.close();
								statement = null;
							}
						}
					}
				}

				statement = apiConnection.getStmt("SET foreign_key_checks = ?");
				statement.setInt(1, 1);
				statement.execute();
				statement.close();
				statement = null;

				apiConnection.commit();
			}
		} catch (SQLException e) {
			if (apiConnection != null) {
				apiConnection.rollback();
			}
			throw e;
		} finally {
			close(innerStatement);
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
	}

	/**
	 * 
	 * @param apiConnection
	 * @param statement
	 * @param resultSet
	 * @param tableName
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	private int _getTableType(final ApiConnection apiConnection, PreparedStatement statement, ResultSet resultSet,
			CharSequence tableName) throws SQLException {
		try {
			statement = apiConnection.getStmtSelect("SHOW FULL TABLES LIKE ?");
			statement.setString(1, tableName.toString());
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				if (Constants.VIEW.equalsIgnoreCase(resultSet.getString(2))) {
					return -1;
				}
				return 1;
			}
			return 0;
		} finally {
			close(resultSet);
			resultSet = null;
			close(statement);
			statement = null;
		}
	}

	/**
	 * 
	 * @param apiConnection
	 * @param statement
	 * @param resultSet
	 * @param tableList
	 * @param newTableList
	 * @throws SQLException
	 */
	private void _checkForeignKeys(String database, ApiConnection apiConnection, PreparedStatement statement,
			ResultSet resultSet, List<String> tableList, List<String> newTableList) throws SQLException {

		PreparedStatement innerStatement = null;
		Iterator<String> tableIterator = null;
		String current = null;
		String table = null;
		StringBuilder builder = null;
		String one = "ALTER TABLE ";
		String two = " DROP FOREIGN KEY ";
		String three = " ADD FOREIGN KEY (";
		String four = ") REFERENCES ";
		String five = " ON DELETE NO ACTION ON UPDATE NO ACTION";
		String query = "SELECT CONSTRAINT_NAME,REFERENCED_TABLE_NAME,REFERENCED_COLUMN_NAME,"
				+ "COLUMN_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE "
				+ "REFERENCED_TABLE_NAME IS NOT null AND CONSTRAINT_SCHEMA = ? AND TABLE_NAME = ?";
		try {
			tableIterator = newTableList.iterator();
			builder = new StringBuilder();
			while (tableIterator.hasNext()) {
				current = tableIterator.next();
				switch (_getTableType(apiConnection, statement, resultSet, current)) {
				case 1:
					// in case of base table
					statement = apiConnection.getStmtSelect(query);
					statement.setString(1, database);
					statement.setString(2, current);
					resultSet = statement.executeQuery();
					while (resultSet.next()) {
						table = resultSet.getString(2);
						if (tableList.contains(table)) {
							builder.delete(0, builder.length());
							builder.append(one);
							builder.append(Constants.SYMBOL_TEN);
							builder.append(current);
							builder.append(Constants.SYMBOL_TEN);
							builder.append(two);
							builder.append(Constants.SYMBOL_TEN);
							builder.append(resultSet.getString(1));
							builder.append(Constants.SYMBOL_TEN);
							innerStatement = apiConnection.getStmt(builder.toString());
							innerStatement.execute();
							innerStatement.close();
							innerStatement = null;

							builder.delete(0, builder.length());
							builder.append(one);
							builder.append(Constants.SYMBOL_TEN);
							builder.append(current);
							builder.append(Constants.SYMBOL_TEN);
							builder.append(three);
							builder.append(Constants.SYMBOL_TEN);
							builder.append(resultSet.getString(4));
							builder.append(Constants.SYMBOL_TEN);
							builder.append(four);
							builder.append(Constants.SYMBOL_TEN);
							builder.append(resultSet.getString(2));
							builder.append(Constants.SYMBOL_TEN);
							builder.append(Constants.SYMBOL_BRACKET_OPEN);
							builder.append(Constants.SYMBOL_TEN);
							builder.append(resultSet.getString(3));
							builder.append(Constants.SYMBOL_TEN);
							builder.append(Constants.SYMBOL_BRACKET_CLOSE);
							builder.append(five);
							innerStatement = apiConnection.getStmt(builder.toString());
							innerStatement.execute();
							innerStatement.close();
							innerStatement = null;
						}
					}
					resultSet.close();
					resultSet = null;
					statement.close();
					statement = null;
					break;
				case -1:
					break;
				default:
					break;
				}
			}
		} finally {
			close(innerStatement);
			close(resultSet);
			resultSet = null;
			close(statement);
			statement = null;
		}
	}

	/**
	 * 
	 * @param apiConnection
	 * @param statement
	 * @param resultSet
	 * @param tableList
	 * @param newTableList
	 * @param oldDatabase
	 * @param newDatabase
	 * @throws SQLException
	 * @throws Exception
	 */
	private void _checkForeignKeys(ApiConnection apiConnection, PreparedStatement statement, ResultSet resultSet,
			List<String> tableList, List<String> newTableList, String oldDatabase, String newDatabase)
					throws SQLException {

		PreparedStatement innerStatement = null;
		Iterator<String> tableIterator = null;
		String current = null;
		String table = null;
		StringBuilder builder = null;
		String temp1 = "SELECT CONSTRAINT_NAME,REFERENCED_TABLE_NAME,REFERENCED_COLUMN_NAME,"
				+ "COLUMN_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE "
				+ "REFERENCED_TABLE_NAME IS NOT null AND CONSTRAINT_SCHEMA = ? AND TABLE_NAME = ?";
		String temp2 = "ALTER TABLE ";
		String temp3 = " ADD FOREIGN KEY (";
		String four = ") REFERENCES ";
		String five = " ON DELETE NO ACTION ON UPDATE NO ACTION";

		try {
			tableIterator = newTableList.iterator();
			builder = new StringBuilder();
			while (tableIterator.hasNext()) {
				current = tableIterator.next();
				statement = apiConnection.getStmtSelect(temp1);
				statement.setString(1, oldDatabase);
				statement.setString(2, current);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					table = resultSet.getString(2);
					if (tableList.contains(table)) {

						builder.delete(0, builder.length());
						builder.append(temp2);
						builder.append(Constants.SYMBOL_TEN);
						builder.append(newDatabase);
						builder.append(Constants.SYMBOL_TEN);
						builder.append(Constants.SYMBOL_DOT);
						builder.append(Constants.SYMBOL_TEN);
						builder.append(current);
						builder.append(Constants.SYMBOL_TEN);
						builder.append(temp3);
						builder.append(Constants.SYMBOL_TEN);
						builder.append(resultSet.getString(4));
						builder.append(Constants.SYMBOL_TEN);
						builder.append(four);
						builder.append(Constants.SYMBOL_TEN);
						builder.append(newDatabase);
						builder.append(Constants.SYMBOL_TEN);
						builder.append(Constants.SYMBOL_DOT);
						builder.append(Constants.SYMBOL_TEN);
						builder.append(resultSet.getString(2));
						builder.append(Constants.SYMBOL_TEN);
						builder.append(Constants.SYMBOL_BRACKET_OPEN);
						builder.append(Constants.SYMBOL_TEN);
						builder.append(resultSet.getString(3));
						builder.append(Constants.SYMBOL_TEN);
						builder.append(Constants.SYMBOL_BRACKET_CLOSE);
						builder.append(five);
						innerStatement = apiConnection.getStmt(builder.toString());
						innerStatement.execute();
						innerStatement.close();
						innerStatement = null;
					}
				}
			}
		} finally {
			close(innerStatement);
			close(resultSet);
			resultSet = null;
			close(statement);
			statement = null;
		}
	}

	/**
	 * 
	 * @param bean
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public String getNewColumn(Bean bean) throws SQLException {
		StructureBean structureBean = (StructureBean) bean;
		String temp = null;
		String temp1 = "<td>";
		String temp2 = "</td>";
		String temp3 = "\">";
		String temp4 = "<optgroup label=\"";
		String temp5 = "</optgroup>";
		String temp6 = "<option value=\"";
		String temp7 = "</option>";
		String temp8 = "<input type=\"checkbox\" onchange=\"callSetValue(this);\">";
		String temp9 = "\" disabled=\"disabled\"";
		StringBuilder result = new StringBuilder();
		result.append("<tr>");

		result.append(temp1);
		result.append(structureBean.getSort());
		result.append(temp2);

		result.append(temp1);
		result.append("<input type=\"text\" name=\"columns\" class=\"form-control\" style=\"width: 150px;\">");
		result.append(temp2);

		result.append(temp1);
		result.append("<select name=\"datatypes\" class=\"form-control\" onchange=\"callApplyDatatype(this);\">");
		Map<String, List<String>> data_types_map = new LinkedHashMap<String, List<String>>();
		data_types_map.putAll(Constants.Utils.DATA_TYPES_MAP);
		for (Entry<String, List<String>> entry : data_types_map.entrySet()) {
			result.append(temp4);
			result.append(entry.getKey());
			result.append(temp3);
			List<String> typeList = entry.getValue();
			Iterator<String> typeIterator = typeList.iterator();
			while (typeIterator.hasNext()) {
				temp = typeIterator.next();
				result.append(temp6);
				result.append(temp);
				result.append(temp3);
				result.append(temp);
				result.append(temp7);
			}
			result.append(temp5);
		}
		result.append("</select>");
		result.append(temp2);

		result.append(temp1);
		result.append("<input type=\"text\" name=\"lengths\" class=\"form-control\" style=\"width: 80px;\">");
		result.append(temp2);

		result.append(temp1);
		result.append("<input type=\"text\" name=\"defaults\" class=\"form-control\">");
		result.append(temp2);

		HomeLogic homeLogic = new HomeLogic();
		Map<String, List<String>> collationMap = homeLogic.getCollationMap();
		result.append(temp1);
		result.append("<select name=\"collations\" class=\"form-control\">");
		result.append(temp6);
		result.append(temp3);
		result.append(structureBean.getPrefix());
		result.append(temp7);
		for (Entry<String, List<String>> entry : collationMap.entrySet()) {
			result.append(temp4);
			result.append(entry.getKey());
			result.append(temp3);
			List<String> typeList = entry.getValue();
			Iterator<String> typeIterator = typeList.iterator();
			while (typeIterator.hasNext()) {
				temp = typeIterator.next();
				result.append(temp6);
				result.append(temp);
				result.append(temp9);
				result.append(temp3);
				result.append(temp);
				result.append(temp7);
			}
			result.append(temp5);
		}
		result.append("</select>");
		result.append(temp2);

		result.append(temp1);
		result.append("<input type=\"hidden\" name=\"pks\" value=\"0\">");
		result.append("<input type=\"checkbox\" onchange=\"callSetValue(this);callPrimaryKey(this);\">");
		result.append(temp2);

		result.append(temp1);
		result.append("<input type=\"hidden\" name=\"nns\" value=\"0\">");
		result.append(temp8);
		result.append(temp2);

		result.append(temp1);
		result.append("<input type=\"hidden\" name=\"uqs\" value=\"0\">");
		result.append(temp8);
		result.append(temp2);

		result.append(temp1);
		result.append("<input type=\"hidden\" name=\"bins\" value=\"0\">");
		result.append("<input type=\"checkbox\" onchange=\"callSetValue(this);\" disabled=\"disabled\">");
		result.append(temp2);

		result.append(temp1);
		result.append("<input type=\"hidden\" name=\"uns\" value=\"0\">");
		result.append(temp8);
		result.append(temp2);

		result.append(temp1);
		result.append("<input type=\"hidden\" name=\"zfs\" value=\"0\">");
		result.append(temp8);
		result.append(temp2);

		result.append(temp1);
		result.append("<input type=\"hidden\" name=\"ais\" value=\"0\">");
		result.append("<input type=\"checkbox\" onchange=\"callSetValue(this);applyAutoIncrement(this);\">");
		result.append(temp2);

		result.append(temp1);
		result.append("<input type=\"text\" name=\"comments\" class=\"form-control\">");
		result.append(temp2);

		result.append("</tr>");
		return result.toString();
	}

	/**
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean isSupportsPartition() throws SQLException {
		boolean result = false;
		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			apiConnection = getConnection();
			statement = apiConnection.getStmtSelect(
					"SELECT plugin_name FROM information_schema.PLUGINS WHERE plugin_status = ? and plugin_name = ?");
			statement.setString(1, "Active");
			statement.setString(2, "partition");
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				result = true;
			}
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
		return result;
	}

	/**
	 * 
	 * @param bean
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws Exception
	 */
	public String createTable(Bean bean) throws SQLException {

		String result = null;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		StringBuilder builder = null;
		CreateTableBean createTableBean = (CreateTableBean) bean;
		List<String> uniqueList = null;
		Iterator<String> uniqueIterator = null;
		String zerofill = "ZEROFILL";
		String unsigned = "UNSIGNED";
		String binary = "BINARY";
		String null_ = "NULL";
		String not_null = "NOT NULL";
		String collate = "COLLATE";
		String default_ = "DEFAULT";
		String auto_increment = "AUTO_INCREMENT";
		String comment = "COMMENT";
		String unique_index = "UNIQUE";
		String primary_key = null;
		boolean alreadyEntered = false;
		try {

			uniqueList = new ArrayList<String>();
			builder = new StringBuilder();
			builder.append("CREATE TABLE ");
			builder.append(Constants.SYMBOL_TEN);
			builder.append(createTableBean.getTable_name());
			builder.append(Constants.SYMBOL_TEN);
			builder.append(Constants.SYMBOL_BRACKET_OPEN);
			for (int i = 0; i < createTableBean.getColumns().length; i++) {
				if (!isEmpty(createTableBean.getColumns()[i])) {
					if (alreadyEntered) {
						builder.append(Constants.SYMBOL_COMMA);
					}
					if (!alreadyEntered) {
						alreadyEntered = true;
					}
					builder.append(Constants.SYMBOL_TEN);
					builder.append(createTableBean.getColumns()[i]);
					builder.append(Constants.SYMBOL_TEN);
					builder.append(Constants.SPACE);
					builder.append(createTableBean.getDatatypes()[i]);
					if (!isEmpty(createTableBean.getLengths()[i])) {
						builder.append(Constants.SYMBOL_BRACKET_OPEN);
						builder.append(createTableBean.getLengths()[i]);
						builder.append(Constants.SYMBOL_BRACKET_CLOSE);
					}
					builder.append(Constants.SPACE);
					if (Constants.ONE.equals(createTableBean.getZfs()[i])) {
						builder.append(zerofill);
						builder.append(Constants.SPACE);
					}
					if (Constants.ONE.equals(createTableBean.getUns()[i])) {
						builder.append(unsigned);
						builder.append(Constants.SPACE);
					}
					if (Constants.ONE.equals(createTableBean.getBins()[i])) {
						builder.append(binary);
						builder.append(Constants.SPACE);
					}
					if (!isEmpty(createTableBean.getCollations()[i])) {
						builder.append(collate);
						builder.append(Constants.SPACE);
						builder.append(Constants.SYMBOL_QUOTE);
						builder.append(createTableBean.getCollations()[i]);
						builder.append(Constants.SYMBOL_QUOTE);
						builder.append(Constants.SPACE);
					}
					if (Constants.ONE.equals(createTableBean.getNns()[i])) {
						builder.append(not_null);
						builder.append(Constants.SPACE);
					} else {
						builder.append(null_);
						builder.append(Constants.SPACE);
					}
					if (!isEmpty(createTableBean.getDefaults()[i])) {
						builder.append(default_);
						builder.append(Constants.SPACE);
						if (Constants.CURRENT_TIMESTAMP.equals(createTableBean.getDefaults()[i])) {
							builder.append(createTableBean.getDefaults()[i]);
						} else {
							builder.append(Constants.SYMBOL_QUOTE);
							builder.append(createTableBean.getDefaults()[i]);
							builder.append(Constants.SYMBOL_QUOTE);
						}
						builder.append(Constants.SPACE);
					}
					if (Constants.ONE.equals(createTableBean.getAis()[i])) {
						builder.append(auto_increment);
						builder.append(Constants.SPACE);
					}

					builder.append(Constants.SPACE);
					builder.append(comment);
					builder.append(Constants.SPACE);
					builder.append(Constants.SYMBOL_QUOTE);
					if (!isEmpty(createTableBean.getComments()[i])) {
						builder.append(createTableBean.getComments()[i]);
					}
					builder.append(Constants.SYMBOL_QUOTE);

					if (Constants.ONE.equals(createTableBean.getPks()[i])) {
						primary_key = createTableBean.getColumns()[i];
					}
					if (Constants.ONE.equals(createTableBean.getUqs()[i])) {
						uniqueList.add(createTableBean.getColumns()[i]);
					}
				}
			}
			if (primary_key != null) {
				builder.append(Constants.SYMBOL_COMMA);
				builder.append("PRIMARY KEY");
				builder.append(Constants.SYMBOL_BRACKET_OPEN);
				builder.append(Constants.SYMBOL_TEN);
				builder.append(primary_key);
				builder.append(Constants.SYMBOL_TEN);
				builder.append(Constants.SYMBOL_BRACKET_CLOSE);
			}

			uniqueIterator = uniqueList.iterator();
			while (uniqueIterator.hasNext()) {
				builder.append(Constants.SYMBOL_COMMA);
				builder.append(unique_index);
				builder.append(Constants.SYMBOL_BRACKET_OPEN);
				builder.append(Constants.SYMBOL_TEN);
				builder.append(uniqueIterator.next());
				builder.append(Constants.SYMBOL_TEN);
				builder.append(Constants.SYMBOL_BRACKET_CLOSE);
			}
			builder.append(Constants.SYMBOL_BRACKET_CLOSE);

			if (!isEmpty(createTableBean.getEngine())) {
				builder.append(Constants.SPACE);
				builder.append("ENGINE =");
				builder.append(Constants.SPACE);
				builder.append(createTableBean.getEngine());
			}
			builder.append(Constants.SPACE);
			builder.append(comment);
			builder.append(Constants.SPACE);
			builder.append(Constants.SYMBOL_QUOTE);
			if (!isEmpty(createTableBean.getComment())) {
				builder.append(createTableBean.getComment());
			}
			builder.append(Constants.SYMBOL_QUOTE);

			if (!isEmpty(createTableBean.getPartition())) {
				builder.append(Constants.SPACE);
				builder.append("PARTITION BY");
				builder.append(Constants.SPACE);
				builder.append(createTableBean.getPartition());
				builder.append(Constants.SYMBOL_BRACKET_OPEN);
				builder.append(createTableBean.getPartition_val());
				builder.append(Constants.SYMBOL_BRACKET_CLOSE);
				builder.append(Constants.SPACE);
				builder.append("PARTITIONS ");
				builder.append(createTableBean.getPartitions());
			}

			if (Constants.YES.equalsIgnoreCase(createTableBean.getAction())) {
				apiConnection = getConnection(bean.getRequest_db());
				statement = apiConnection.getStmt(builder.toString());
				statement.execute();
				apiConnection.commit();
			} else {
				result = builder.toString();
			}
		} finally {
			close(statement);
			close(apiConnection);
		}
		return result;
	}

	/**
	 * 
	 * @param bean
	 * @return
	 * @throws SQLException
	 * @throws JSONException
	 * @throws Exception
	 */
	public String validate(Bean bean) throws SQLException, JSONException {

		String result = null;
		CreateTableBean createTableBean = null;
		int count = 0;
		JSONObject jsonObject = null;
		JSONObject tempJsonObject = null;
		String a = "a";
		String b = "b";
		String[] tempArr = null;
		try {
			createTableBean = (CreateTableBean) bean;
			if (isTableExisted(createTableBean.getTable_name(), bean.getRequest_db())) {
				result = messages.getMessage("msg.table_already_existed");
				return result;
			}
			jsonObject = new JSONObject(Constants.Utils.DATA_TYPES_INFO);
			for (int i = 0; i < createTableBean.getColumns().length; i++) {
				if (!isEmpty(createTableBean.getColumns()[i])) {
					count++;
					// check column is duplicate or not
					if (!isColumnNameValid(createTableBean.getColumns(), createTableBean.getColumns()[i])) {
						result = messages.getMessage("msg.duplicate_columna_name") + createTableBean.getColumns()[i];
						break;
					}

					// get validation data
					tempJsonObject = jsonObject.getJSONObject(createTableBean.getDatatypes()[i]);
					int aVal = tempJsonObject.getInt(a);
					if (aVal > 0) {
						// has list
						if (isEmpty(createTableBean.getLengths()[i])) {
							result = messages.getMessage("msg.length_value_blank_column")
									+ createTableBean.getColumns()[i];
							break;
						}

						// validate list values
						tempArr = createTableBean.getLengths()[i].split(Constants.SYMBOL_COMMA);
						boolean isInvalid = false;
						for (int j = 0; j < tempArr.length; j++) {
							if (!isValidSqlString(tempArr[j], true)) {
								isInvalid = true;
								break;
							}
						}
						if (isInvalid) {
							result = messages.getMessage("msg.length_value_blank_column")
									+ createTableBean.getColumns()[i];
							break;
						}
					} else {
						// no list
						int bVal = tempJsonObject.getInt(b);
						boolean isInvalid = false;
						switch (bVal) {
						case 0:
							// length = 1 and mandatory
							if (isEmpty(createTableBean.getLengths()[i])) {
								isInvalid = true;
								break;
							}
							if (!isInteger(createTableBean.getLengths()[i])) {
								isInvalid = true;
								break;
							}
							break;
						case 1:
							// length = 2 and mandatory
							if (isEmpty(createTableBean.getLengths()[i])) {
								isInvalid = true;
								break;
							}
							tempArr = createTableBean.getLengths()[i].split(Constants.SYMBOL_COMMA);
							if (tempArr == null || tempArr.length != 2) {
								isInvalid = true;
								break;
							} else if (!isInteger(tempArr[0]) || !isInteger(tempArr[1])) {
								isInvalid = true;
								break;
							}
							break;
						case 2:
							// length = 1 and not mandatory
							if (!isEmpty(createTableBean.getLengths()[i])
									&& !isInteger(createTableBean.getLengths()[i])) {
								isInvalid = true;
								break;
							}
							break;
						case 3:
							// length = 2 and not mandatory
							if (!isEmpty(createTableBean.getLengths()[i])) {
								tempArr = createTableBean.getLengths()[i].split(Constants.SYMBOL_COMMA);
								if (tempArr == null || tempArr.length != 2) {
									isInvalid = true;
									break;
								} else if (!isInteger(tempArr[0]) || !isInteger(tempArr[1])) {
									isInvalid = true;
									break;
								}
							}
							break;
						default:
							break;
						}

						if (isInvalid) {
							result = messages.getMessage("msg.length_value_blank_column")
									+ createTableBean.getColumns()[i];
							break;
						}
					}

					// validate default value
					if (!isEmpty(createTableBean.getDefaults()[i])
							&& !isValidSqlString(createTableBean.getDefaults()[i], false)) {
						result = messages.getMessage("msg.default_value_invalid_column")
								+ createTableBean.getColumns()[i];
						break;
					}

					// validate comment value
					if (!isEmpty(createTableBean.getComments()[i])
							&& !isValidSqlString(createTableBean.getComments()[i], false)) {
						result = messages.getMessage("msg.comment_invalid_column") + createTableBean.getColumns()[i];
						break;
					}
				}
			}
			if (count == 0) {
				result = messages.getMessage("msg.all_columns_blank");
			}
		} finally {

		}
		return result;
	}

	/**
	 * 
	 * @param columns
	 * @param column
	 * @return
	 * @throws Exception
	 */
	public boolean isColumnNameValid(String[] columns, String column) {
		int count = 0;
		try {
			for (int i = 0; i < columns.length; i++) {
				if (!isEmpty(columns[i]) && columns[i].trim().equalsIgnoreCase(column.trim())) {
					count++;
					if (count > 1) {
						return false;
					}
				}
			}
		} finally {
			columns = null;
			column = null;
		}
		return true;
	}

	/**
	 * 
	 * @param name
	 * @param database
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean isTableExisted(String name, String database) throws SQLException {

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			apiConnection = super.getConnection(database);
			statement = apiConnection.getStmtSelect("SHOW TABLES LIKE ?");
			statement.setString(1, name);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return true;
			}
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
		return false;
	}

	/**
	 * 
	 * @param bean
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String createView(Bean bean) throws SQLException {
		String result = null;

		CreateViewBean createViewBean = null;
		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		StringBuilder builder = null;
		String[] temp = null;
		try {
			createViewBean = (CreateViewBean) bean;
			builder = new StringBuilder();
			builder.append(createViewBean.getCreate_type());
			builder.append(Constants.SPACE);
			if (!isEmpty(createViewBean.getAlgorithm())) {
				builder.append("ALGORITHM = ");
				builder.append(createViewBean.getAlgorithm());
				builder.append(Constants.SPACE);
			}
			if (!isEmpty(createViewBean.getDefiner())) {
				if (Constants.CURRENT_USER.equals(createViewBean.getDefiner())) {
					builder.append("DEFINER = ");
					builder.append(createViewBean.getDefiner());
					builder.append(Constants.SPACE);
				} else if (!isEmpty(createViewBean.getDefiner_name())) {
					temp = createViewBean.getDefiner_name().split(Constants.SYMBOL_AT);
					builder.append("DEFINER = ");
					if (temp.length < 2) {
						builder.append(Constants.SYMBOL_TEN);
						builder.append(temp[0]);
						builder.append(Constants.SYMBOL_TEN);
						builder.append(Constants.SPACE);
					} else {
						builder.append(Constants.SYMBOL_TEN);
						builder.append(temp[0]);
						builder.append(Constants.SYMBOL_TEN);
						builder.append(Constants.SYMBOL_AT);
						builder.append(Constants.SYMBOL_TEN);
						builder.append(temp[1]);
						if (!temp[1].endsWith(Constants.SYMBOL_TEN)) {
							builder.append(Constants.SYMBOL_TEN);
						}
						builder.append(Constants.SPACE);
					}
				}
			}
			if (!isEmpty(createViewBean.getSql_security())) {
				builder.append("SQL SECURITY ");
				builder.append(createViewBean.getSql_security());
				builder.append(Constants.SPACE);
			}
			builder.append("VIEW ");
			builder.append(Constants.SYMBOL_TEN);
			builder.append(createViewBean.getView_name());
			builder.append(Constants.SYMBOL_TEN);
			builder.append(Constants.SPACE);
			if (createViewBean.getColumn_list() != null) {

				StringBuilder columns = new StringBuilder(Constants.SYMBOL_BRACKET_OPEN);
				boolean entered = false;
				for (int i = 0; i < createViewBean.getColumn_list().length; i++) {
					if (!isEmpty(createViewBean.getColumn_list()[i])) {
						if (entered) {
							columns.append(Constants.SYMBOL_COMMA);
						}
						entered = true;
						columns.append(Constants.SYMBOL_TEN);
						columns.append(createViewBean.getColumn_list()[i]);
						columns.append(Constants.SYMBOL_TEN);
					}
				}
				columns.append(Constants.SYMBOL_BRACKET_CLOSE);
				if (entered) {
					builder.append(columns);
					builder.append(Constants.SPACE);
				}
			}
			builder.append("AS ");
			builder.append(createViewBean.getDefinition());
			if (!isEmpty(createViewBean.getCheck())) {
				builder.append(Constants.SPACE);
				builder.append("WITH ");
				builder.append(createViewBean.getCheck());
				builder.append(" CHECK OPTION");
			}
			if (Constants.YES.equalsIgnoreCase(createViewBean.getAction())) {
				apiConnection = super.getConnection(bean.getRequest_db());
				statement = apiConnection.getStmt(builder.toString());
				statement.execute();
				apiConnection.commit();
			} else {
				result = builder.toString();
			}
		} finally {
			close(statement);
			close(apiConnection);
		}
		return result;
	}
}
