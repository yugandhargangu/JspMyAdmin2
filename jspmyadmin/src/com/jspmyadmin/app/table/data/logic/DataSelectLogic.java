/**
 * 
 */
package com.jspmyadmin.app.table.data.logic;

import java.sql.Blob;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.jspmyadmin.app.table.data.beans.DataSelectBean;
import com.jspmyadmin.app.table.data.beans.DataUpdateBean;
import com.jspmyadmin.framework.db.AbstractLogic;
import com.jspmyadmin.framework.db.ApiConnection;
import com.jspmyadmin.framework.util.FrameworkConstants;
import com.jspmyadmin.framework.web.logic.EncDecLogic;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/06/27
 *
 */
public class DataSelectLogic extends AbstractLogic {

	private final String _table;

	/**
	 * 
	 * @param table
	 * @throws IllegalArgumentException
	 */
	public DataSelectLogic(String table) throws IllegalArgumentException {
		if (table == null) {
			throw new IllegalArgumentException();
		}
		_table = table;
	}

	public void fillBean(Bean bean) throws ClassNotFoundException, SQLException, Exception {

		DataSelectBean dataSelectBean = (DataSelectBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		StringBuilder builder = null;
		JSONObject jsonObject = null;
		JSONObject tempJsonObject = null;
		JSONObject maintainJsonObject = null;
		EncDecLogic encDecLogic = null;

		String sortType = "ASC";
		int page = 1;
		try {
			apiConnection = getConnection(true);
			DatabaseMetaData databaseMetaData = apiConnection.getDatabaseMetaData();
			resultSet = databaseMetaData.getPrimaryKeys(null, null, _table);
			if (resultSet.next()) {
				dataSelectBean.setPrimary_key(resultSet.getString("COLUMN_NAME"));
			}
			close(resultSet);

			encDecLogic = new EncDecLogic();
			if (dataSelectBean.getToken() != null) {
				try {
					jsonObject = new JSONObject(encDecLogic.decode(dataSelectBean.getToken()));
					if (jsonObject.has(FrameworkConstants.SORT_BY)) {
						dataSelectBean.setSort_by(jsonObject.getString(FrameworkConstants.SORT_BY));
						if (maintainJsonObject == null) {
							maintainJsonObject = new JSONObject();
						}
						maintainJsonObject.put(FrameworkConstants.SORT_BY, dataSelectBean.getSort_by());
					}
					if (jsonObject.has(FrameworkConstants.LIMIT)) {
						dataSelectBean.setLimit(jsonObject.getString(FrameworkConstants.LIMIT));
					}
					if (jsonObject.has(FrameworkConstants.TYPE)) {
						sortType = jsonObject.getString(FrameworkConstants.TYPE);
						dataSelectBean.setSort_type(sortType);
						if (maintainJsonObject == null) {
							maintainJsonObject = new JSONObject();
						}
						maintainJsonObject.put(FrameworkConstants.TYPE, dataSelectBean.getSort_type());
					}
					if (jsonObject.has(FrameworkConstants.PAGE)) {
						String temp = jsonObject.getString(FrameworkConstants.PAGE);
						if (isInteger(temp)) {
							page = Integer.parseInt(temp);
						}
					}

					if (jsonObject.has(FrameworkConstants.COLUMN)) {
						String column = jsonObject.getString(FrameworkConstants.COLUMN);
						try {
							tempJsonObject = new JSONObject(column);
							Iterator<?> keyIterator = tempJsonObject.keys();
							Map<Integer, String> searchMap = new HashMap<Integer, String>();
							while (keyIterator.hasNext()) {
								Object key = keyIterator.next();
								searchMap.put(Integer.parseInt(key.toString()),
										tempJsonObject.getString(key.toString()));
							}
							String[] search_list = new String[searchMap.size()];
							for (int key : searchMap.keySet()) {
								search_list[key] = searchMap.get(key);
							}
							dataSelectBean.setSearch_columns(search_list);
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (maintainJsonObject == null) {
							maintainJsonObject = new JSONObject();
						}
						maintainJsonObject.put(FrameworkConstants.COLUMN, column);
					}
					if (jsonObject.has(FrameworkConstants.SEARCH)) {
						String search = jsonObject.getString(FrameworkConstants.SEARCH);
						try {
							tempJsonObject = new JSONObject(search);
							Iterator<?> keyIterator = tempJsonObject.keys();
							Map<Integer, String> searchMap = new HashMap<Integer, String>();
							while (keyIterator.hasNext()) {
								Object key = keyIterator.next();
								searchMap.put(Integer.parseInt(key.toString()),
										tempJsonObject.getString(key.toString()));
							}
							String[] search_list = new String[searchMap.size()];
							for (int key : searchMap.keySet()) {
								search_list[key] = searchMap.get(key);
							}
							dataSelectBean.setSearch_list(search_list);
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (maintainJsonObject == null) {
							maintainJsonObject = new JSONObject();
						}
						maintainJsonObject.put(FrameworkConstants.SEARCH, search);
					}
					if (jsonObject.has(FrameworkConstants.VIEW)) {
						dataSelectBean.setShow_search(jsonObject.getString(FrameworkConstants.VIEW));
					}
				} catch (Exception e) {
				}
			}

			String strSearch = null;
			if (dataSelectBean.getSearch_columns() != null && dataSelectBean.getSearch_list() != null) {
				StringBuilder searchBuilder = null;
				boolean isNull = false;

				JSONObject columnJsonObject = null;
				JSONObject searchJsonObject = null;
				if (maintainJsonObject == null) {
					maintainJsonObject = new JSONObject();
					columnJsonObject = new JSONObject();
					searchJsonObject = new JSONObject();
					isNull = true;
				}
				for (int i = 0; i < dataSelectBean.getSearch_columns().length; i++) {
					if (isNull) {
						String index = String.valueOf(i);
						columnJsonObject.put(index, dataSelectBean.getSearch_columns()[i]);
						searchJsonObject.put(index, dataSelectBean.getSearch_list()[i]);
					}
					if (!isEmpty(dataSelectBean.getSearch_list()[i])) {
						if (searchBuilder == null) {
							searchBuilder = new StringBuilder(" WHERE `");
							searchBuilder.append(dataSelectBean.getSearch_columns()[i]);
							searchBuilder.append(FrameworkConstants.SYMBOL_TEN);
							searchBuilder.append(" LIKE '");
							searchBuilder.append(dataSelectBean.getSearch_list()[i]);
							searchBuilder.append(FrameworkConstants.SYMBOL_QUOTE);
						} else {
							searchBuilder.append(" AND `");
							searchBuilder.append(dataSelectBean.getSearch_columns()[i]);
							searchBuilder.append(FrameworkConstants.SYMBOL_TEN);
							searchBuilder.append(" LIKE '");
							searchBuilder.append(dataSelectBean.getSearch_list()[i]);
							searchBuilder.append(FrameworkConstants.SYMBOL_QUOTE);
						}
					}

				}
				if (searchBuilder != null) {
					strSearch = searchBuilder.toString();
				}
				if (isNull) {
					maintainJsonObject.put(FrameworkConstants.COLUMN, columnJsonObject.toString());
					maintainJsonObject.put(FrameworkConstants.SEARCH, searchJsonObject.toString());
				}
			}

			if (maintainJsonObject == null) {
				maintainJsonObject = new JSONObject();
			}

			dataSelectBean.setCurrent_page(String.valueOf(page));
			maintainJsonObject.put(FrameworkConstants.LIMIT, dataSelectBean.getLimit());
			maintainJsonObject.put(FrameworkConstants.VIEW, dataSelectBean.getShow_search());
			maintainJsonObject.put(FrameworkConstants.PAGE, dataSelectBean.getCurrent_page());
			String strMaintain = maintainJsonObject.toString();

			jsonObject = new JSONObject(strMaintain);
			jsonObject.put(FrameworkConstants.PAGE, dataSelectBean.getCurrent_page());
			dataSelectBean.setReload_page(encDecLogic.encode(jsonObject.toString()));

			jsonObject = new JSONObject(strMaintain);
			jsonObject.put(FrameworkConstants.PAGE, String.valueOf(page + 1));
			dataSelectBean.setNext_page(encDecLogic.encode(jsonObject.toString()));

			if (page > 1) {
				jsonObject = new JSONObject(strMaintain);
				jsonObject.put(FrameworkConstants.PAGE, String.valueOf(page - 1));
				dataSelectBean.setPrevious_page(encDecLogic.encode(jsonObject.toString()));
			}

			builder = new StringBuilder();
			builder.append("SELECT * FROM `");
			builder.append(_table);
			builder.append(FrameworkConstants.SYMBOL_TEN);

			if (!isEmpty(strSearch)) {
				builder.append(strSearch);
			}

			if (dataSelectBean.getSort_by() != null && !isEmpty(dataSelectBean.getSort_by())) {
				builder.append(" ORDER BY `");
				builder.append(dataSelectBean.getSort_by());
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(FrameworkConstants.SPACE);
				builder.append(sortType);
			}
			if (dataSelectBean.getLimit() != null && !isEmpty(dataSelectBean.getLimit())
					&& !FrameworkConstants.ZERO.equals(dataSelectBean.getLimit())) {
				builder.append(" LIMIT ");
				int limit = Integer.parseInt(dataSelectBean.getLimit());
				builder.append((page - 1) * limit);
				builder.append(FrameworkConstants.SYMBOL_COMMA);
				builder.append(limit);
			}
			statement = apiConnection.preparedStatementSelect(builder.toString());
			dataSelectBean.setQuery(new QueryExtracter(statement).toString());
			long start_time = System.nanoTime();
			resultSet = statement.executeQuery();
			long end_time = System.nanoTime();
			long exec_time = end_time - start_time;
			double final_exec_time = ((double) exec_time) / 1000000000.0;
			DecimalFormat decimalFormat = new DecimalFormat("0");
			decimalFormat.setMaximumFractionDigits(6);
			dataSelectBean.setExec_time(decimalFormat.format(final_exec_time));
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			Map<String, String> column_name_map = new LinkedHashMap<String, String>(resultSetMetaData.getColumnCount());
			List<Integer> byteList = new ArrayList<Integer>(0);
			List<Integer> blobList = new ArrayList<Integer>(0);
			int pkIndex = -1;
			int columnCount = resultSetMetaData.getColumnCount();
			for (int i = 0; i < columnCount; i++) {
				String className = resultSetMetaData.getColumnClassName(i + 1);
				if (FrameworkConstants.BYTE_TYPE.equals(className)) {
					String typeName = resultSetMetaData.getColumnTypeName(i + 1);
					if (FrameworkConstants.Utils.BLOB_LIST.contains(typeName)) {
						blobList.add(i);
					} else {
						byteList.add(i);
					}
				}
				String columnName = resultSetMetaData.getColumnName(i + 1);
				tempJsonObject = new JSONObject(strMaintain);
				tempJsonObject.put(FrameworkConstants.SORT_BY, columnName);
				if (columnName.equalsIgnoreCase(dataSelectBean.getSort_by()) && "ASC".equalsIgnoreCase(sortType)) {
					tempJsonObject.put(FrameworkConstants.TYPE, "DESC");
				} else {
					tempJsonObject.put(FrameworkConstants.TYPE, "ASC");
				}
				column_name_map.put(columnName, encDecLogic.encode(tempJsonObject.toString()));
				if (columnName.equalsIgnoreCase(dataSelectBean.getPrimary_key())) {
					pkIndex = i;
				}

			}
			dataSelectBean.setColumn_name_map(column_name_map);

			List<List<String>> selectList = new ArrayList<List<String>>();
			List<String> pkList = null;
			if (pkIndex > -1) {
				pkList = new ArrayList<String>();
			}
			while (resultSet.next()) {
				List<String> rowList = new ArrayList<String>(columnCount);
				for (int i = 0; i < columnCount; i++) {
					if (blobList.contains(i)) {
						Blob blob = resultSet.getBlob(i + 1);
						if (blob != null) {
							long length = blob.length();
							double final_length = ((double) length) / 1000.0;
							// TODO blob value pending
							rowList.add(FrameworkConstants.DATABASE_BLOB + final_length + "KB");
						} else {
							rowList.add(FrameworkConstants.DATABASE_NULL);
						}
					} else if (byteList.contains(i)) {
						byte[] bytes = resultSet.getBytes(i + 1);
						if (bytes != null) {
							StringBuilder byteData = new StringBuilder(bytes.length);
							for (int j = 0; j < bytes.length; j++) {
								byteData.append(bytes[j]);
							}
							rowList.add(byteData.toString());
						} else {
							rowList.add(FrameworkConstants.DATABASE_NULL);
						}
					} else {
						String value = resultSet.getString(i + 1);
						if (value == null) {
							rowList.add(FrameworkConstants.DATABASE_NULL);
						} else {
							rowList.add(value);
						}
					}
				}
				if (pkIndex > -1) {
					pkList.add(rowList.get(pkIndex));
				}
				selectList.add(rowList);
			}
			dataSelectBean.setSelect_list(selectList);
			if (pkList != null) {
				dataSelectBean.setPrimary_key_list(pkList);
			}

		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
	}

	/**
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int delete(Bean bean) throws Exception {

		DataSelectBean dataSelectBean = (DataSelectBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		StringBuilder builder = null;
		try {
			apiConnection = getConnection(true);
			DatabaseMetaData databaseMetaData = apiConnection.getDatabaseMetaData();
			resultSet = databaseMetaData.getPrimaryKeys(null, null, _table);
			if (resultSet.next()) {
				dataSelectBean.setPrimary_key(resultSet.getString("COLUMN_NAME"));
			}
			close(resultSet);

			if (dataSelectBean.getIds() != null && dataSelectBean.getIds().length > 0) {
				builder = new StringBuilder();
				builder.append("DELETE FROM `");
				builder.append(_table);
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(" WHERE `");
				builder.append(dataSelectBean.getPrimary_key());
				builder.append(FrameworkConstants.SYMBOL_TEN);
				if (dataSelectBean.getIds().length == 1) {
					builder.append(" = ?");
				} else {
					builder.append(" IN (");
					for (int i = 0; i < dataSelectBean.getIds().length; i++) {
						if (i == 0) {
							builder.append("?");
						} else {
							builder.append(",?");
						}
					}
					builder.append(FrameworkConstants.SYMBOL_BRACKET_CLOSE);
				}
				statement = apiConnection.preparedStatement(builder.toString());
				for (int i = 0; i < dataSelectBean.getIds().length; i++) {
					statement.setString(i + 1, dataSelectBean.getIds()[i]);
				}
				int count = statement.executeUpdate();
				apiConnection.commit();
				return count;
			}
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
		return 0;
	}

	/**
	 * 
	 * @param bean
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws Exception
	 */
	public String update(Bean bean) throws ClassNotFoundException, SQLException, Exception {
		DataUpdateBean dataUpdateBean = (DataUpdateBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		StringBuilder builder = null;
		String primaryKey = null;
		try {
			apiConnection = getConnection(true);
			DatabaseMetaData databaseMetaData = apiConnection.getDatabaseMetaData();
			resultSet = databaseMetaData.getPrimaryKeys(null, null, _table);
			if (resultSet.next()) {
				primaryKey = resultSet.getString("COLUMN_NAME");
			}
			close(resultSet);

			if (dataUpdateBean.getColumn_name() != null && dataUpdateBean.getColumn_value() != null) {
				builder = new StringBuilder();
				builder.append("UPDATE `");
				builder.append(_table);
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(" SET `");
				builder.append(dataUpdateBean.getColumn_name().trim());
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(" = ? WHERE `");
				builder.append(primaryKey);
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(" = ?");
				statement = apiConnection.preparedStatement(builder.toString());
				if (FrameworkConstants.DATABASE_NULL.equals(dataUpdateBean.getColumn_value())) {
					statement.setObject(1, null);
				} else {
					statement.setString(1, dataUpdateBean.getColumn_value());
				}
				statement.setString(2, dataUpdateBean.getPrimary_key());
				int count = statement.executeUpdate();
				String query = new QueryExtracter(statement).toString();
				apiConnection.commit();
				return query + "; // " + count;
			}
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
		return null;
	}

}
