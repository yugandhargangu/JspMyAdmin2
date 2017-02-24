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
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import com.jspmyadmin.app.table.insert.beans.InsertInfo;
import com.jspmyadmin.app.table.insert.beans.InsertUpdateBean;
import com.jspmyadmin.framework.connection.AbstractLogic;
import com.jspmyadmin.framework.connection.ApiConnection;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;
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
	 * @throws SQLException
	 * @throws EncodingException
	 * @throws JSONException
	 * @throws Exception
	 */
	public void fillBean(Bean bean) throws SQLException, EncodingException {
		InsertUpdateBean insertUpdateBean = (InsertUpdateBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			apiConnection = getConnection(bean.getRequest_db());
			DatabaseMetaData databaseMetaData = apiConnection.getDatabaseMetaData();
			resultSet = databaseMetaData.getPrimaryKeys(null, null, _table);
			if (resultSet.next()) {
				insertUpdateBean.setPk_column(resultSet.getString(Constants.COLUMN_NAME));
				insertUpdateBean.setPk_status(Constants.ONE);
			}
			close(resultSet);

			if (!isEmpty(bean.getToken())) {
				try {
					JSONObject jsonObject = new JSONObject(encodeObj.decode(bean.getToken()));
					if (jsonObject.has(Constants.PK_VAL)) {
						insertUpdateBean.setPk_value(jsonObject.getString(Constants.PK_VAL));
						insertUpdateBean.setUpdate(Constants.ONE);
					}
				} catch (JSONException e) {
				}
			}

			StringBuilder builder = new StringBuilder();
			builder.append("SHOW FULL COLUMNS FROM `");
			builder.append(_table);
			builder.append(Constants.SYMBOL_TEN);
			statement = apiConnection.getStmtSelect(builder.toString());
			resultSet = statement.executeQuery();
			List<InsertInfo> insertInfoList = new ArrayList<InsertInfo>();
			while (resultSet.next()) {
				InsertInfo insertInfo = new InsertInfo();
				insertInfo.setColumn(resultSet.getString(1));
				insertInfo.setDataType(resultSet.getString(2));
				insertInfo.setCanBeNull(resultSet.getString(4));
				insertInfo.setExtra(resultSet.getString(7));
				if (Constants.AUTO_INCREMENT.equalsIgnoreCase(insertInfo.getExtra())) {
					insertInfo.setValue(Constants.ZERO);
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
					if (Constants.BYTE_TYPE.equals(className)) {
						String typeName = metaData.getColumnTypeName(i + 1);
						if (Constants.Utils.BLOB_LIST.contains(typeName)) {
							insertInfo.setFile_type(Constants.ONE);
						}
					}
				}
				if (resultSet.next()) {
					for (int i = 0; i < insertInfoList.size(); i++) {
						InsertInfo insertInfo = insertInfoList.get(i);
						if (Constants.ONE.equals(insertInfo.getFile_type())) {
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
					if (Constants.BYTE_TYPE.equals(className)) {
						String typeName = metaData.getColumnTypeName(i + 1);
						if (Constants.Utils.BLOB_LIST.contains(typeName)) {
							insertInfo.setFile_type(Constants.ONE);
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
	public void insertUpdate(Bean bean) throws IOException, SQLException {
		InsertUpdateBean insertUpdateBean = (InsertUpdateBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;

		List<Object> paramList = null;
		Map<Integer, Object> paramMap = null;
		try {
			apiConnection = getConnection(bean.getRequest_db());
			StringBuilder builder = new StringBuilder();
			if (Constants.ONE.equals(insertUpdateBean.getUpdate())) {

				// update
				paramList = new ArrayList<Object>();
				builder.append("UPDATE `");
				builder.append(_table);
				builder.append("` SET ");
				for (int i = 0; i < insertUpdateBean.getColumns().length; i++) {
					if (!insertUpdateBean.getColumns()[i].equalsIgnoreCase(insertUpdateBean.getPk_column())) {
						Object value = insertUpdateBean.getValues()[i];
						if (value != null) {
							if (value instanceof FileInput) {
								if (paramList.size() > 0) {
									builder.append(Constants.SYMBOL_COMMA);
								}
								builder.append(Constants.SYMBOL_TEN);
								builder.append(insertUpdateBean.getColumns()[i]);
								builder.append(Constants.SYMBOL_TEN);
								builder.append(" = ?");
								FileInput fileInput = (FileInput) value;
								paramList.add(fileInput.getInputStream());
							} else if (!isEmpty((String) value)) {
								if (paramList.size() > 0) {
									builder.append(Constants.SYMBOL_COMMA);
								}
								builder.append(Constants.SYMBOL_TEN);
								builder.append(insertUpdateBean.getColumns()[i]);
								builder.append(Constants.SYMBOL_TEN);
								if (insertUpdateBean.getFunctions()[i] != null
										&& Constants.ONE.equals(insertUpdateBean.getFunctions()[i])) {
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
				builder.append(Constants.SYMBOL_TEN);
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

			} else {

				// insert
				paramMap = new LinkedHashMap<Integer, Object>();
				builder.append("INSERT INTO `");
				builder.append(_table);
				builder.append("` (");
				for (int i = 0; i < insertUpdateBean.getColumns().length; i++) {
					Object value = insertUpdateBean.getValues()[i];
					if (value != null) {
						if (value instanceof FileInput) {
							if (paramMap.size() > 0) {
								builder.append(Constants.SYMBOL_COMMA);
							}
							builder.append(Constants.SYMBOL_TEN);
							builder.append(insertUpdateBean.getColumns()[i]);
							builder.append(Constants.SYMBOL_TEN);
							FileInput fileInput = (FileInput) value;
							paramMap.put(i, fileInput.getInputStream());
						} else if (!isEmpty((String) value)) {
							if (!Constants.CURRENT_TIMESTAMP.equalsIgnoreCase((String) value)) {
								if (paramMap.size() > 0) {
									builder.append(Constants.SYMBOL_COMMA);
								}
								builder.append(Constants.SYMBOL_TEN);
								builder.append(insertUpdateBean.getColumns()[i]);
								builder.append(Constants.SYMBOL_TEN);
								paramMap.put(i, value);
							}
						}
					}
				}
				builder.append(") VALUES (");
				boolean alreadyEntered = false;
				for (Entry<Integer, Object> entry : paramMap.entrySet()) {
					if (alreadyEntered) {
						builder.append(Constants.SYMBOL_COMMA);
					} else {
						alreadyEntered = true;
					}
					Object value = entry.getValue();
					if (!(value instanceof FileInput) && insertUpdateBean.getFunctions()[entry.getKey()] != null
							&& Constants.ONE.equals(insertUpdateBean.getFunctions()[entry.getKey()])) {
						builder.append(value);
						paramMap.remove(entry.getKey());
					} else {
						builder.append("?");
					}
				}
				builder.append(")");
				statement = apiConnection.getStmt(builder.toString());
				int i = 1;
				for (Entry<Integer, Object> entry : paramMap.entrySet()) {
					statement.setObject(i++, entry.getValue());
				}
			}
			statement.executeUpdate();
			apiConnection.commit();

		} finally {
			if (paramList != null && paramList.size() > 0) {
				for (int i = 0; i < paramList.size(); i++) {
					Object object = paramList.get(i);
					if (object instanceof InputStream) {
						close((InputStream) object);
					}
				}
			}
			if (paramMap != null && paramMap.size() > 0) {
				for (Entry<Integer, Object> entry : paramMap.entrySet()) {
					Object object = entry.getValue();
					if (object instanceof InputStream) {
						close((InputStream) object);
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
