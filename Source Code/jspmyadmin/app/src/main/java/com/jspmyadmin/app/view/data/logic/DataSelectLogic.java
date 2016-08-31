/**
 * 
 */
package com.jspmyadmin.app.view.data.logic;

import java.sql.Blob;
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

import com.jspmyadmin.app.view.data.beans.DataSelectBean;
import com.jspmyadmin.framework.connection.AbstractLogic;
import com.jspmyadmin.framework.connection.ApiConnection;
import com.jspmyadmin.framework.constants.BeanConstants;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.logic.EncDecLogic;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/06/27
 *
 */
public class DataSelectLogic extends AbstractLogic {

	private final String _view;

	/**
	 * 
	 * @param view
	 * @throws IllegalArgumentException
	 */
	public DataSelectLogic(String view) throws IllegalArgumentException {
		if (view == null) {
			throw new IllegalArgumentException();
		}
		_view = view;
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
			apiConnection = getConnection(bean.getRequest_db());

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
						}
						if (maintainJsonObject == null) {
							maintainJsonObject = new JSONObject();
						}
						maintainJsonObject.put(FrameworkConstants.SEARCH, search);
					}
					if (jsonObject.has(FrameworkConstants.SHOW_SEARCH)) {
						dataSelectBean.setShow_search(jsonObject.getString(FrameworkConstants.SHOW_SEARCH));
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
							searchBuilder.append("%");
							searchBuilder.append(dataSelectBean.getSearch_list()[i]);
							searchBuilder.append("%");
							searchBuilder.append(FrameworkConstants.SYMBOL_QUOTE);
						} else {
							searchBuilder.append(" AND `");
							searchBuilder.append(dataSelectBean.getSearch_columns()[i]);
							searchBuilder.append(FrameworkConstants.SYMBOL_TEN);
							searchBuilder.append(" LIKE '");
							searchBuilder.append("%");
							searchBuilder.append(dataSelectBean.getSearch_list()[i]);
							searchBuilder.append("%");
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
			maintainJsonObject.put(FrameworkConstants.SHOW_SEARCH, dataSelectBean.getShow_search());
			maintainJsonObject.put(FrameworkConstants.PAGE, dataSelectBean.getCurrent_page());
			maintainJsonObject.put(FrameworkConstants.REQUEST_DB, bean.getRequest_db());
			maintainJsonObject.put(FrameworkConstants.REQUEST_VIEW, bean.getRequest_view());
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
			builder.append(_view);
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
			}
			dataSelectBean.setColumn_name_map(column_name_map);

			List<List<String>> selectList = new ArrayList<List<String>>();
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
							blobVal.append(FrameworkConstants.DATABASE_BLOB);
							blobVal.append(final_length);
							blobVal.append(BeanConstants._KIB);
							blobVal.append("</b>");
							rowList.add(blobVal.toString());
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
				selectList.add(rowList);
			}
			dataSelectBean.setSelect_list(selectList);
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
	}
}
