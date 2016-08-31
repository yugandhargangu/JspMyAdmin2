/**
 * 
 */
package com.jspmyadmin.app.table.sql.logic;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;

import com.jspmyadmin.app.table.sql.beans.SqlBean;
import com.jspmyadmin.framework.connection.AbstractLogic;
import com.jspmyadmin.framework.connection.ApiConnection;
import com.jspmyadmin.framework.connection.QuerySeparator;
import com.jspmyadmin.framework.constants.BeanConstants;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/13
 *
 */
public class SqlLogic extends AbstractLogic {

	/**
	 * 
	 * @param bean
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws JSONException
	 */
	public void fillBean(Bean bean) throws ClassNotFoundException, SQLException, JSONException {

		SqlBean sqlBean = (SqlBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			apiConnection = getConnection(bean.getRequest_db());
			statement = apiConnection.getStmtSelect("SHOW TABLES");
			resultSet = statement.executeQuery();
			List<String> tableList = new ArrayList<String>();
			while (resultSet.next()) {
				tableList.add(resultSet.getString(1));
			}
			close(resultSet);
			close(statement);
			Iterator<String> iterator = tableList.iterator();
			StringBuilder mainBuilder = new StringBuilder();
			mainBuilder.append("{");
			mainBuilder.append("tables: {");
			boolean mainEnter = false;
			while (iterator.hasNext()) {
				if (mainEnter) {
					mainBuilder.append(FrameworkConstants.SYMBOL_COMMA);
				} else {
					mainEnter = true;
				}
				String table = iterator.next();
				mainBuilder.append("\"");
				mainBuilder.append(table.replaceAll("\"", "\\\\\""));
				mainBuilder.append("\"");
				mainBuilder.append(": {");
				statement = apiConnection.getStmtSelect("SHOW COLUMNS FROM `" + table + FrameworkConstants.SYMBOL_TEN);
				resultSet = statement.executeQuery();
				boolean subEnter = false;
				while (resultSet.next()) {
					if (subEnter) {
						mainBuilder.append(FrameworkConstants.SYMBOL_COMMA);
					} else {
						subEnter = true;
					}
					mainBuilder.append("\"");
					mainBuilder.append(resultSet.getString(1).replaceAll("\"", "\\\\\""));
					mainBuilder.append("\"");
					mainBuilder.append(": null");
				}
				mainBuilder.append("}");
				close(resultSet);
				close(statement);
			}
			mainBuilder.append("}}");
			String hintOptions = mainBuilder.toString();
			sqlBean.setHint_options(hintOptions);

			if (!isEmpty(sqlBean.getQuery())) {
				if (FrameworkConstants.ONE.equals(sqlBean.getDisable_fks())) {
					statement = apiConnection.getStmt("SET foreign_key_checks = ?");
					statement.setInt(1, 0);
					statement.execute();
					close(statement);
				}
				QuerySeparator querySeparator = new QuerySeparator(sqlBean.getQuery());
				List<String> queries = querySeparator.getQueries();
				iterator = queries.iterator();
				while (iterator.hasNext()) {
					String query = iterator.next();
					if (!isEmpty(query)) {
						statement = apiConnection.getStmt(query);
						long start_time = System.nanoTime();
						boolean result = statement.execute();
						long end_time = System.nanoTime();
						long exec_time = end_time - start_time;
						double final_exec_time = ((double) exec_time) / 1000000000.0;
						DecimalFormat decimalFormat = new DecimalFormat(FrameworkConstants.ZERO);
						decimalFormat.setMaximumFractionDigits(6);
						sqlBean.setExec_time(decimalFormat.format(final_exec_time));
						if (result) {
							resultSet = statement.getResultSet();
							ResultSetMetaData metaData = resultSet.getMetaData();
							List<String> columnList = new ArrayList<String>(metaData.getColumnCount());
							List<Integer> blobList = new ArrayList<Integer>(0);
							List<Integer> byteList = new ArrayList<Integer>(0);
							for (int i = 0; i < metaData.getColumnCount(); i++) {
								String className = metaData.getColumnClassName(i + 1);
								if (FrameworkConstants.BYTE_TYPE.equals(className)) {
									String typeName = metaData.getColumnTypeName(i + 1);
									if (FrameworkConstants.Utils.BLOB_LIST.contains(typeName)) {
										blobList.add(i);
									} else {
										byteList.add(i);
									}
								}
								columnList.add(metaData.getColumnName(i + 1));

							}
							sqlBean.setColumn_list(columnList);
							List<List<String>> fetchList = new ArrayList<List<String>>();
							int count = 0;
							while (resultSet.next() && count <= 1000) {
								if (count < 1000) {
									List<String> rowList = new ArrayList<String>(columnList.size());
									for (int i = 0; i < columnList.size(); i++) {
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
									fetchList.add(rowList);
								}
								count++;
							}
							sqlBean.setFetch_list(fetchList);
							if (count > 1000) {
								sqlBean.setMax_rows(FrameworkConstants.ONE);
							}
							break;
						} else {
							int count = statement.getUpdateCount();
							sqlBean.setResult(String.valueOf(count));
						}
					}
				}
			}
		} finally {
			if (apiConnection != null) {
				apiConnection.commit();
			}
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
	}
}
