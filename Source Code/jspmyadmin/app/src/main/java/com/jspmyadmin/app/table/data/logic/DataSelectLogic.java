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

import org.json.JSONException;
import org.json.JSONObject;

import com.jspmyadmin.app.table.data.beans.DataSelectBean;
import com.jspmyadmin.app.table.data.beans.DataUpdateBean;
import com.jspmyadmin.framework.connection.AbstractLogic;
import com.jspmyadmin.framework.connection.ApiConnection;
import com.jspmyadmin.framework.constants.BeanConstants;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;
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

	/**
	 * 
	 * @param bean
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws JSONException
	 * @throws EncodingException
	 */
	public void fillBean(Bean bean) throws SQLException, JSONException, EncodingException {

		DataSelectBean dataSelectBean = (DataSelectBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		StringBuilder builder = null;
		JSONObject jsonObject = null;
		JSONObject tempJsonObject = null;
		JSONObject maintainJsonObject = null;

		String sortType = "ASC";
		int page = 1;
		try {
			apiConnection = getConnection(bean.getRequest_db());
			DatabaseMetaData databaseMetaData = apiConnection.getDatabaseMetaData();
			resultSet = databaseMetaData.getPrimaryKeys(null, null, _table);
			if (resultSet.next()) {
				dataSelectBean.setPrimary_key(resultSet.getString(Constants.COLUMN_NAME));
			}
			close(resultSet);

			if (dataSelectBean.getToken() != null) {
				try {
					jsonObject = new JSONObject(encodeObj.decode(dataSelectBean.getToken()));
					if (jsonObject.has(Constants.SORT_BY)) {
						dataSelectBean.setSort_by(jsonObject.getString(Constants.SORT_BY));
						if (maintainJsonObject == null) {
							maintainJsonObject = new JSONObject();
						}
						maintainJsonObject.put(Constants.SORT_BY, dataSelectBean.getSort_by());
					}
					if (jsonObject.has(Constants.LIMIT)) {
						dataSelectBean.setLimit(jsonObject.getString(Constants.LIMIT));
					}
					if (jsonObject.has(Constants.TYPE)) {
						sortType = jsonObject.getString(Constants.TYPE);
						dataSelectBean.setSort_type(sortType);
						if (maintainJsonObject == null) {
							maintainJsonObject = new JSONObject();
						}
						maintainJsonObject.put(Constants.TYPE, dataSelectBean.getSort_type());
					}
					if (jsonObject.has(Constants.PAGE)) {
						String temp = jsonObject.getString(Constants.PAGE);
						if (isInteger(temp)) {
							page = Integer.parseInt(temp);
						}
					}

					if (jsonObject.has(Constants.COLUMN)) {
						String column = jsonObject.getString(Constants.COLUMN);
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
						} catch (JSONException e) {
						}
						if (maintainJsonObject == null) {
							maintainJsonObject = new JSONObject();
						}
						maintainJsonObject.put(Constants.COLUMN, column);
					}
					if (jsonObject.has(Constants.SEARCH)) {
						String search = jsonObject.getString(Constants.SEARCH);
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
						} catch (JSONException e) {
						}
						if (maintainJsonObject == null) {
							maintainJsonObject = new JSONObject();
						}
						maintainJsonObject.put(Constants.SEARCH, search);
					}
					if (jsonObject.has(Constants.VIEW)) {
						dataSelectBean.setShow_search(jsonObject.getString(Constants.VIEW));
					}
				} catch (EncodingException e) {
				} catch (JSONException e) {
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
							searchBuilder.append(Constants.SYMBOL_TEN);
							searchBuilder.append(" LIKE '");
							searchBuilder.append("%");
							searchBuilder.append(dataSelectBean.getSearch_list()[i]);
							searchBuilder.append("%");
							searchBuilder.append(Constants.SYMBOL_QUOTE);
						} else {
							searchBuilder.append(" AND `");
							searchBuilder.append(dataSelectBean.getSearch_columns()[i]);
							searchBuilder.append(Constants.SYMBOL_TEN);
							searchBuilder.append(" LIKE '");
							searchBuilder.append("%");
							searchBuilder.append(dataSelectBean.getSearch_list()[i]);
							searchBuilder.append("%");
							searchBuilder.append(Constants.SYMBOL_QUOTE);
						}
					}

				}
				if (searchBuilder != null) {
					strSearch = searchBuilder.toString();
				}
				if (isNull) {
					maintainJsonObject.put(Constants.COLUMN, columnJsonObject.toString());
					maintainJsonObject.put(Constants.SEARCH, searchJsonObject.toString());
				}
			}

			if (maintainJsonObject == null) {
				maintainJsonObject = new JSONObject();
			}

			dataSelectBean.setCurrent_page(String.valueOf(page));
			maintainJsonObject.put(Constants.LIMIT, dataSelectBean.getLimit());
			maintainJsonObject.put(Constants.VIEW, dataSelectBean.getShow_search());
			maintainJsonObject.put(Constants.PAGE, dataSelectBean.getCurrent_page());
			maintainJsonObject.put(Constants.REQUEST_DB, bean.getRequest_db());
			maintainJsonObject.put(Constants.REQUEST_TABLE, bean.getRequest_table());
			String strMaintain = maintainJsonObject.toString();

			jsonObject = new JSONObject(strMaintain);
			jsonObject.put(Constants.PAGE, dataSelectBean.getCurrent_page());
			dataSelectBean.setReload_page(encodeObj.encode(jsonObject.toString()));

			jsonObject = new JSONObject(strMaintain);
			jsonObject.put(Constants.PAGE, String.valueOf(page + 1));
			dataSelectBean.setNext_page(encodeObj.encode(jsonObject.toString()));

			if (page > 1) {
				jsonObject = new JSONObject(strMaintain);
				jsonObject.put(Constants.PAGE, String.valueOf(page - 1));
				dataSelectBean.setPrevious_page(encodeObj.encode(jsonObject.toString()));
			}

			builder = new StringBuilder();
			builder.append("SELECT * FROM `");
			builder.append(_table);
			builder.append(Constants.SYMBOL_TEN);

			if (!isEmpty(strSearch)) {
				builder.append(strSearch);
			}

			if (dataSelectBean.getSort_by() != null && !isEmpty(dataSelectBean.getSort_by())) {
				builder.append(" ORDER BY `");
				builder.append(dataSelectBean.getSort_by());
				builder.append(Constants.SYMBOL_TEN);
				builder.append(Constants.SPACE);
				builder.append(sortType);
			}
			if (dataSelectBean.getLimit() != null && !isEmpty(dataSelectBean.getLimit())
					&& !Constants.ZERO.equals(dataSelectBean.getLimit())) {
				builder.append(" LIMIT ");
				int limit = Integer.parseInt(dataSelectBean.getLimit());
				builder.append((page - 1) * limit);
				builder.append(Constants.SYMBOL_COMMA);
				builder.append(limit);
			}
			statement = apiConnection.getStmtSelect(builder.toString());
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
				if (Constants.BYTE_TYPE.equals(className)) {
					String typeName = resultSetMetaData.getColumnTypeName(i + 1);
					if (Constants.Utils.BLOB_LIST.contains(typeName)) {
						blobList.add(i);
					} else {
						byteList.add(i);
					}
				}
				String columnName = resultSetMetaData.getColumnName(i + 1);
				tempJsonObject = new JSONObject(strMaintain);
				tempJsonObject.put(Constants.SORT_BY, columnName);
				if (columnName.equalsIgnoreCase(dataSelectBean.getSort_by()) && "ASC".equalsIgnoreCase(sortType)) {
					tempJsonObject.put(Constants.TYPE, "DESC");
				} else {
					tempJsonObject.put(Constants.TYPE, "ASC");
				}
				column_name_map.put(columnName, encodeObj.encode(tempJsonObject.toString()));
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
							StringBuilder blobVal = new StringBuilder();
							blobVal.append("<b class=\"blob-download\">");
							blobVal.append(Constants.DATABASE_BLOB);
							blobVal.append(final_length);
							blobVal.append(BeanConstants._KIB);
							blobVal.append("</b>");
							rowList.add(blobVal.toString());
						} else {
							rowList.add(Constants.DATABASE_NULL);
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
							rowList.add(Constants.DATABASE_NULL);
						}
					} else {
						String value = resultSet.getString(i + 1);
						if (value == null) {
							rowList.add(Constants.DATABASE_NULL);
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
	 * @throws SQLException
	 * @throws Exception
	 */
	public int delete(Bean bean) throws SQLException {

		DataSelectBean dataSelectBean = (DataSelectBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		StringBuilder builder = null;
		try {
			apiConnection = getConnection(bean.getRequest_db());
			DatabaseMetaData databaseMetaData = apiConnection.getDatabaseMetaData();
			resultSet = databaseMetaData.getPrimaryKeys(null, null, _table);
			if (resultSet.next()) {
				dataSelectBean.setPrimary_key(resultSet.getString(Constants.COLUMN_NAME));
			}
			close(resultSet);

			if (dataSelectBean.getIds() != null && dataSelectBean.getIds().length > 0) {
				builder = new StringBuilder();
				builder.append("DELETE FROM `");
				builder.append(_table);
				builder.append(Constants.SYMBOL_TEN);
				builder.append(" WHERE `");
				builder.append(dataSelectBean.getPrimary_key());
				builder.append(Constants.SYMBOL_TEN);
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
					builder.append(Constants.SYMBOL_BRACKET_CLOSE);
				}
				statement = apiConnection.getStmt(builder.toString());
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
	public String update(Bean bean) throws SQLException {
		DataUpdateBean dataUpdateBean = (DataUpdateBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		StringBuilder builder = null;
		String primaryKey = null;
		try {
			apiConnection = getConnection(bean.getRequest_db());
			DatabaseMetaData databaseMetaData = apiConnection.getDatabaseMetaData();
			resultSet = databaseMetaData.getPrimaryKeys(null, null, _table);
			if (resultSet.next()) {
				primaryKey = resultSet.getString(Constants.COLUMN_NAME);
			}
			close(resultSet);

			if (dataUpdateBean.getColumn_name() != null && dataUpdateBean.getColumn_value() != null) {
				builder = new StringBuilder();
				builder.append("UPDATE `");
				builder.append(_table);
				builder.append(Constants.SYMBOL_TEN);
				builder.append(" SET `");
				builder.append(dataUpdateBean.getColumn_name().trim());
				builder.append(Constants.SYMBOL_TEN);
				builder.append(" = ? WHERE `");
				builder.append(primaryKey);
				builder.append(Constants.SYMBOL_TEN);
				builder.append(" = ?");
				statement = apiConnection.getStmt(builder.toString());
				if (Constants.DATABASE_NULL.equals(dataUpdateBean.getColumn_value())) {
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
