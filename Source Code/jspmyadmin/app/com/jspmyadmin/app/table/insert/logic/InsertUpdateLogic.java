/**
 * 
 */
package com.jspmyadmin.app.table.insert.logic;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.jspmyadmin.app.table.insert.beans.InsertInfo;
import com.jspmyadmin.app.table.insert.beans.InsertUpdateBean;
import com.jspmyadmin.framework.connection.AbstractLogic;
import com.jspmyadmin.framework.connection.ApiConnection;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.logic.EncDecLogic;
import com.jspmyadmin.framework.web.utils.Bean;
import com.jspmyadmin.framework.web.utils.FileInput;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/11
 *
 */
public class InsertUpdateLogic extends AbstractLogic {

	private final String _table;

	/**
	 * 
	 * @param table
	 */
	public InsertUpdateLogic(String table) {
		_table = table;
	}

	/**
	 * 
	 * @param bean
	 * @throws JSONException
	 * @throws Exception
	 */
	public void fillBean(Bean bean) throws Exception {
		InsertUpdateBean insertUpdateBean = (InsertUpdateBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		EncDecLogic encDecLogic = null;
		try {
			apiConnection = getConnection(true);
			DatabaseMetaData databaseMetaData = apiConnection.getDatabaseMetaData();
			resultSet = databaseMetaData.getPrimaryKeys(null, null, _table);
			if (resultSet.next()) {
				insertUpdateBean.setPk_column(resultSet.getString(FrameworkConstants.COLUMN_NAME));
				insertUpdateBean.setPk_status(FrameworkConstants.ONE);
			}
			close(resultSet);

			if (!isEmpty(bean.getToken())) {
				try {
					encDecLogic = new EncDecLogic();
					JSONObject jsonObject = new JSONObject(encDecLogic.decode(bean.getToken()));
					if (jsonObject.has(FrameworkConstants.PK_VAL)) {
						insertUpdateBean.setPk_value(jsonObject.getString(FrameworkConstants.PK_VAL));
						insertUpdateBean.setUpdate(FrameworkConstants.ONE);
					}
				} catch (JSONException e) {
				}
			}

			StringBuilder builder = new StringBuilder();
			builder.append("SHOW FULL COLUMNS FROM `");
			builder.append(_table);
			builder.append(FrameworkConstants.SYMBOL_TEN);
			statement = apiConnection.getStmtSelect(builder.toString());
			resultSet = statement.executeQuery();
			List<InsertInfo> insertInfoList = new ArrayList<InsertInfo>();
			while (resultSet.next()) {
				InsertInfo insertInfo = new InsertInfo();
				insertInfo.setColumn(resultSet.getString(1));
				insertInfo.setDataType(resultSet.getString(2));
				insertInfo.setCanBeNull(resultSet.getString(4));
				insertInfo.setExtra(resultSet.getString(7));
				if (FrameworkConstants.AUTO_INCREMENT.equalsIgnoreCase(insertInfo.getExtra())) {
					insertInfo.setValue(FrameworkConstants.ZERO);
				} else {
					insertInfo.setValue(resultSet.getString(6));
				}
				insertInfoList.add(insertInfo);
			}
			close(resultSet);
			close(statement);

			ResultSetMetaData metaData = null;
			if (!isEmpty(insertUpdateBean.getPk_value())) {
				builder.delete(0, builder.length());
				builder.append("SELECT * FROM `");
				builder.append(_table);
				builder.append("` WHERE `");
				builder.append(insertUpdateBean.getPk_column());
				builder.append("` = ?");
				statement = apiConnection.getStmtSelect(builder.toString());
				statement.setString(1, insertUpdateBean.getPk_value());
				resultSet = statement.executeQuery();
				metaData = resultSet.getMetaData();
				for (int i = 0; i < metaData.getColumnCount(); i++) {
					InsertInfo insertInfo = insertInfoList.get(i);
					String className = metaData.getColumnClassName(i + 1);
					if (FrameworkConstants.BYTE_TYPE.equals(className)) {
						String typeName = metaData.getColumnTypeName(i + 1);
						if (FrameworkConstants.Utils.BLOB_LIST.contains(typeName)) {
							insertInfo.setFile_type(FrameworkConstants.ONE);
						}
					}
				}
				if (resultSet.next()) {
					for (int i = 0; i < insertInfoList.size(); i++) {
						InsertInfo insertInfo = insertInfoList.get(i);
						if (FrameworkConstants.ONE.equals(insertInfo.getFile_type())) {
							// do something
						} else {
							insertInfo.setValue(resultSet.getString(i + 1));
						}
					}
				}
			} else {
				builder.delete(0, builder.length());
				builder.append("SELECT * FROM `");
				builder.append(_table);
				builder.append("` WHERE TRUE IS FALSE");
				statement = apiConnection.getStmtSelect(builder.toString());
				resultSet = statement.executeQuery();
				metaData = resultSet.getMetaData();
				for (int i = 0; i < metaData.getColumnCount(); i++) {
					InsertInfo insertInfo = insertInfoList.get(i);
					String className = metaData.getColumnClassName(i + 1);
					if (FrameworkConstants.BYTE_TYPE.equals(className)) {
						String typeName = metaData.getColumnTypeName(i + 1);
						if (FrameworkConstants.Utils.BLOB_LIST.contains(typeName)) {
							insertInfo.setFile_type(FrameworkConstants.ONE);
						}
					}
				}
			}

			insertUpdateBean.setInfo_list(insertInfoList);
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
	}

	/**
	 * 
	 * @param bean
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void insertUpdate(Bean bean) throws IOException, ClassNotFoundException, SQLException {
		InsertUpdateBean insertUpdateBean = (InsertUpdateBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;

		List<Object> paramList = new ArrayList<Object>();
		Map<Integer, Object> paramMap = new LinkedHashMap<Integer, Object>();
		try {
			apiConnection = getConnection(true);
			if (FrameworkConstants.ONE.equals(insertUpdateBean.getUpdate())) {

				// update
				StringBuilder builder = new StringBuilder();
				builder.append("UPDATE `");
				builder.append(_table);
				builder.append("` SET ");
				for (int i = 0; i < insertUpdateBean.getColumns().length; i++) {
					if (!insertUpdateBean.getColumns()[i].equalsIgnoreCase(insertUpdateBean.getPk_column())) {
						Object value = insertUpdateBean.getValues()[i];
						if (value != null) {
							if (value instanceof FileInput) {
								if (paramList.size() > 0) {
									builder.append(FrameworkConstants.SYMBOL_COMMA);
								}
								builder.append(FrameworkConstants.SYMBOL_TEN);
								builder.append(insertUpdateBean.getColumns()[i]);
								builder.append(FrameworkConstants.SYMBOL_TEN);
								builder.append(" = ?");
								FileInput fileInput = (FileInput) value;
								paramList.add(fileInput.getInputStream());
							} else if (!isEmpty((String) value)) {
								if (paramList.size() > 0) {
									builder.append(FrameworkConstants.SYMBOL_COMMA);
								}
								builder.append(FrameworkConstants.SYMBOL_TEN);
								builder.append(insertUpdateBean.getColumns()[i]);
								builder.append(FrameworkConstants.SYMBOL_TEN);
								if (insertUpdateBean.getFunctions()[i] != null
										&& FrameworkConstants.ONE.equals(insertUpdateBean.getFunctions()[i])) {
									builder.append(" = ");
									builder.append(value);
								} else {
									builder.append(" = ?");
									paramList.add(value);
								}
							}
						}
					}
				}
				builder.append(" WHERE `");
				builder.append(insertUpdateBean.getPk_column());
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(" = ?");
				paramList.add(insertUpdateBean.getPk_value());
				statement = apiConnection.getStmt(builder.toString());
				for (int i = 0; i < paramList.size(); i++) {
					Object object = paramList.get(i);
					if (object instanceof InputStream) {
						statement.setObject(i + 1, object);
					} else {
						statement.setObject(i + 1, object);
					}
				}
				statement.executeUpdate();
				apiConnection.commit();
			} else {

				// insert
				StringBuilder builder = new StringBuilder();
				builder.append("INSERT INTO `");
				builder.append(_table);
				builder.append("` (");
				for (int i = 0; i < insertUpdateBean.getColumns().length; i++) {
					Object value = insertUpdateBean.getValues()[i];
					if (value != null) {
						if (value instanceof FileInput) {
							if (paramMap.size() > 0) {
								builder.append(FrameworkConstants.SYMBOL_COMMA);
							}
							builder.append(FrameworkConstants.SYMBOL_TEN);
							builder.append(insertUpdateBean.getColumns()[i]);
							builder.append(FrameworkConstants.SYMBOL_TEN);
							FileInput fileInput = (FileInput) value;
							paramMap.put(i, fileInput.getInputStream());
						} else if (!isEmpty((String) value)) {
							if (!FrameworkConstants.CURRENT_TIMESTAMP.equalsIgnoreCase((String) value)) {
								if (paramMap.size() > 0) {
									builder.append(FrameworkConstants.SYMBOL_COMMA);
								}
								builder.append(FrameworkConstants.SYMBOL_TEN);
								builder.append(insertUpdateBean.getColumns()[i]);
								builder.append(FrameworkConstants.SYMBOL_TEN);
								paramMap.put(i, value);
							}
						}
					}
				}
				builder.append(") VALUES (");
				boolean alreadyEntered = false;
				for (Integer key : paramMap.keySet()) {
					if (alreadyEntered) {
						builder.append(FrameworkConstants.SYMBOL_COMMA);
					} else {
						alreadyEntered = true;
					}
					Object value = paramMap.get(key);
					if (!(value instanceof FileInput) && insertUpdateBean.getFunctions()[key] != null
							&& FrameworkConstants.ONE.equals(insertUpdateBean.getFunctions()[key])) {
						builder.append(value);
						paramMap.remove(key);
					} else {
						builder.append("?");
					}
				}
				builder.append(")");
				statement = apiConnection.getStmt(builder.toString());
				int i = 1;
				for (Integer key : paramMap.keySet()) {
					statement.setObject(i++, paramMap.get(key));
				}
				statement.executeUpdate();
				apiConnection.commit();
			}

		} finally {
			if (paramList.size() > 0) {
				for (int i = 0; i < paramList.size(); i++) {
					Object object = paramList.get(i);
					if (object instanceof InputStream) {
						InputStream stream = (InputStream) object;
						close(stream);
					}
				}
			}
			if (paramMap.size() > 0) {
				for (Integer key : paramMap.keySet()) {
					Object object = paramMap.get(key);
					if (object instanceof InputStream) {
						InputStream stream = (InputStream) object;
						close(stream);
					}
				}
			}
			close(statement);
			close(apiConnection);
		}
	}

	/**
	 * 
	 * @param bean
	 */
	public void fillValues(Bean bean) {
		InsertUpdateBean insertUpdateBean = (InsertUpdateBean) bean;
		if (insertUpdateBean.getValues() != null) {
			for (int i = 0; i < insertUpdateBean.getValues().length; i++) {
				Object object = insertUpdateBean.getValues()[i];
				if (object != null && !(object instanceof FileInput)) {
					insertUpdateBean.getInfo_list().get(i).setValue(object.toString());
				}
			}
		}
	}
}
