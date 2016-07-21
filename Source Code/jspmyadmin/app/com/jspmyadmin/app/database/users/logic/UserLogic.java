/**
 * 
 */
package com.jspmyadmin.app.database.users.logic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.jspmyadmin.app.database.users.beans.ColumnPrivilegeBean;
import com.jspmyadmin.app.database.users.beans.RoutinePrivilegeBean;
import com.jspmyadmin.app.database.users.beans.TablePrivilegeBean;
import com.jspmyadmin.app.database.users.beans.UserInfo;
import com.jspmyadmin.app.database.users.beans.UserListBean;
import com.jspmyadmin.framework.connection.AbstractLogic;
import com.jspmyadmin.framework.connection.ApiConnection;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.logic.EncDecLogic;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/19
 *
 */
public class UserLogic extends AbstractLogic {

	/**
	 * 
	 * @param bean
	 * @throws Exception
	 */
	public void fillBean(Bean bean) throws Exception {
		UserListBean userListBean = (UserListBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		EncDecLogic encDecLogic = null;
		try {
			apiConnection = getConnection(false);
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT user,host ");
			builder.append("FROM mysql.user ORDER BY user");
			statement = apiConnection.getStmtSelect(builder.toString());
			resultSet = statement.executeQuery();
			List<String> userList = new ArrayList<String>();
			while (resultSet.next()) {
				builder.delete(0, builder.length());
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(resultSet.getString(1));
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(FrameworkConstants.SYMBOL_AT);
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(resultSet.getString(2));
				builder.append(FrameworkConstants.SYMBOL_TEN);
				userList.add(builder.toString());
			}
			close(resultSet);
			close(statement);
			userListBean.setUser_list(userList);

			encDecLogic = new EncDecLogic();
			List<UserInfo> userInfoList = new ArrayList<UserInfo>();
			Iterator<String> iterator = userList.iterator();
			while (iterator.hasNext()) {
				String userName = iterator.next();
				statement = apiConnection.getStmtSelect("SHOW GRANTS FOR " + userName);
				resultSet = statement.executeQuery();
				boolean isGlobal = false;
				while (resultSet.next()) {
					String str = resultSet.getString(1);
					if (str.startsWith("GRANT ALL PRIVILEGES ON *.*")) {
						isGlobal = true;
					}
				}
				close(resultSet);
				close(statement);

				if (isGlobal) {
					UserInfo userInfo = new UserInfo();
					userInfo.setUser(userName);
					JSONObject jsonObject = new JSONObject();
					jsonObject.put(FrameworkConstants.USER, userName);
					userInfo.setToken(encDecLogic.encode(jsonObject.toString()));
					userInfo.setType(FrameworkConstants.ONE);
					userInfoList.add(userInfo);
					iterator.remove();
				} else {
					builder.delete(0, builder.length());
					builder.append("SELECT privilege_type,is_grantable FROM ");
					builder.append("information_schema.schema_privileges ");
					builder.append("WHERE grantee = ? AND table_schema = ?");
					statement = apiConnection.getStmtSelect(builder.toString());
					String user = userName.replaceAll(FrameworkConstants.SYMBOL_TEN, "\'").replaceAll("'", "\'");
					statement.setString(1, user);
					statement.setString(2, apiConnection.getDatabase());
					resultSet = statement.executeQuery();
					String[] obj_rights = null;
					String[] ddl_rights = null;
					String[] other_rights = null;
					UserInfo userInfo = null;
					String grant = null;
					while (resultSet.next()) {
						if (userInfo == null) {
							userInfo = new UserInfo();
							obj_rights = new String[userListBean.getPrivilege_obj_list().size()];
							ddl_rights = new String[userListBean.getPrivilege_ddl_list().size()];
							other_rights = new String[userListBean.getPrivilege_admn_list().size()];
						}
						if (grant == null) {
							grant = resultSet.getString(2);
						}
						String privilegeType = resultSet.getString(1);
						if (userListBean.getPrivilege_obj_list().contains(privilegeType)) {
							obj_rights[userListBean.getPrivilege_obj_list()
									.indexOf(privilegeType)] = FrameworkConstants.ONE;
						} else if (userListBean.getPrivilege_ddl_list().contains(privilegeType)) {
							ddl_rights[userListBean.getPrivilege_ddl_list()
									.indexOf(privilegeType)] = FrameworkConstants.ONE;
						} else if (userListBean.getPrivilege_admn_list().contains(privilegeType)) {
							other_rights[userListBean.getPrivilege_admn_list()
									.indexOf(privilegeType)] = FrameworkConstants.ONE;
						}
					}
					if (userInfo != null) {
						userInfo.setUser(userName);
						JSONObject jsonObject = new JSONObject();
						jsonObject.put(FrameworkConstants.USER, userName);
						userInfo.setToken(encDecLogic.encode(jsonObject.toString()));
						if (FrameworkConstants.YES.equalsIgnoreCase(grant)) {
							other_rights[0] = FrameworkConstants.ONE;
						}
						userInfo.setObj_rights(obj_rights);
						userInfo.setDdl_rights(ddl_rights);
						userInfo.setOther_rights(other_rights);
						userInfoList.add(userInfo);
						iterator.remove();
					}
				}
				close(resultSet);
				close(statement);
			}

			iterator = userList.iterator();
			while (iterator.hasNext()) {
				String userName = iterator.next();
				builder.delete(0, builder.length());
				builder.append("SELECT privilege_type,is_grantable FROM ");
				builder.append("information_schema.table_privileges ");
				builder.append("WHERE grantee = ? AND table_schema = ?");
				statement = apiConnection.getStmtSelect(builder.toString());
				String user = userName.replaceAll(FrameworkConstants.SYMBOL_TEN, "\'").replaceAll("'", "\'");
				statement.setString(1, user);
				statement.setString(2, apiConnection.getDatabase());
				resultSet = statement.executeQuery();
				if (resultSet.next()) {
					UserInfo userInfo = new UserInfo();
					userInfo.setUser(userName);
					userInfo.setType(FrameworkConstants.TWO);
					JSONObject jsonObject = new JSONObject();
					jsonObject.put(FrameworkConstants.USER, userName);
					userInfo.setToken(encDecLogic.encode(jsonObject.toString()));
					userInfoList.add(userInfo);
					iterator.remove();
				}
				close(resultSet);
				close(statement);
			}

			iterator = userList.iterator();
			while (iterator.hasNext()) {
				String userName = iterator.next();
				builder.delete(0, builder.length());
				builder.append("SELECT privilege_type,is_grantable FROM ");
				builder.append("information_schema.column_privileges ");
				builder.append("WHERE grantee = ? AND table_schema = ?");
				statement = apiConnection.getStmtSelect(builder.toString());
				String user = userName.replaceAll(FrameworkConstants.SYMBOL_TEN, "\'").replaceAll("'", "\'");
				statement.setString(1, user);
				statement.setString(2, apiConnection.getDatabase());
				resultSet = statement.executeQuery();
				if (resultSet.next()) {
					UserInfo userInfo = new UserInfo();
					userInfo.setUser(userName);
					userInfo.setType(FrameworkConstants.THREE);
					JSONObject jsonObject = new JSONObject();
					jsonObject.put(FrameworkConstants.USER, userName);
					userInfo.setToken(encDecLogic.encode(jsonObject.toString()));
					userInfoList.add(userInfo);
					iterator.remove();
				}
				close(resultSet);
				close(statement);
			}
			userListBean.setUser_info_list(userInfoList);
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
	public void saveSchemaPrivileges(Bean bean) throws ClassNotFoundException, SQLException {
		UserListBean userListBean = (UserListBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			apiConnection = getConnection(false);
			String user = userListBean.getUser().replaceAll(FrameworkConstants.SYMBOL_TEN, "\'");
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT privilege_type,is_grantable FROM ");
			builder.append("information_schema.schema_privileges WHERE grantee = ?");
			builder.append(" AND table_schema = ?");
			statement = apiConnection.getStmtSelect(builder.toString());
			statement.setString(1, user);
			statement.setString(2, apiConnection.getDatabase());
			resultSet = statement.executeQuery();
			List<String> privilegeList = new ArrayList<String>();
			String grant = null;
			while (resultSet.next()) {
				if (grant == null) {
					grant = resultSet.getString(2);
				}
				privilegeList.add(resultSet.getString(1));
			}
			close(resultSet);
			close(statement);
			if (FrameworkConstants.YES.equalsIgnoreCase(grant)) {
				privilegeList.add(FrameworkConstants.GRANT_OPTION);
			}
			if (userListBean.getPrivileges() != null && userListBean.getPrivileges().length > 0) {
				boolean alreadyEntered = false;
				builder.delete(0, builder.length());
				builder.append("GRANT ");
				for (String privilege : userListBean.getPrivileges()) {
					if (privilegeList.contains(privilege)) {
						privilegeList.remove(privilege);
					} else {
						if (alreadyEntered) {
							builder.append(FrameworkConstants.SYMBOL_COMMA);
						} else {
							alreadyEntered = true;
						}
						builder.append(privilege);
					}
				}
				if (alreadyEntered) {
					builder.append(" ON ");
					builder.append(FrameworkConstants.SYMBOL_TEN);
					builder.append(apiConnection.getDatabase());
					builder.append(FrameworkConstants.SYMBOL_TEN);
					builder.append(".* TO ");
					builder.append(userListBean.getUser());
					statement = apiConnection.getStmt(builder.toString());
					statement.execute();
				}
			}
			if (privilegeList.size() > 0) {
				builder.delete(0, builder.length());
				builder.append("REVOKE ");
				Iterator<String> iterator = privilegeList.iterator();
				boolean alreadyEntered = false;
				while (iterator.hasNext()) {
					if (alreadyEntered) {
						builder.append(FrameworkConstants.SYMBOL_COMMA);
					} else {
						alreadyEntered = true;
					}
					builder.append(iterator.next());
				}
				builder.append(" ON ");
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(apiConnection.getDatabase());
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(".* FROM ");
				builder.append(userListBean.getUser());
				statement = apiConnection.getStmt(builder.toString());
				statement.execute();
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

	/**
	 * 
	 * @param bean
	 * @throws JSONException
	 * @throws Exception
	 */
	public void fillTablePrivileges(Bean bean) throws Exception {
		TablePrivilegeBean tablePrivilegeBean = (TablePrivilegeBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		EncDecLogic encDecLogic = null;
		try {
			encDecLogic = new EncDecLogic();
			if (!isEmpty(tablePrivilegeBean.getToken())) {
				try {
					JSONObject jsonObject = new JSONObject(encDecLogic.decode(tablePrivilegeBean.getToken()));
					if (jsonObject.has(FrameworkConstants.USER)) {
						tablePrivilegeBean.setUser(jsonObject.getString(FrameworkConstants.USER));
					}
					if (jsonObject.has(FrameworkConstants.TABLE)) {
						tablePrivilegeBean.setTable(jsonObject.getString(FrameworkConstants.TABLE));
					}
				} catch (Exception e) {
				}
			}
			apiConnection = getConnection(true);
			statement = apiConnection.getStmtSelect("SHOW TABLES");
			resultSet = statement.executeQuery();
			List<String> tableList = new ArrayList<String>();
			while (resultSet.next()) {
				tableList.add(resultSet.getString(1));
			}
			close(resultSet);
			close(statement);

			tablePrivilegeBean.setTable_list(tableList);

			if (isEmpty(tablePrivilegeBean.getTable()) && tableList.size() > 0) {
				tablePrivilegeBean.setTable(tableList.get(0));
			}

			if (!isEmpty(tablePrivilegeBean.getTable())) {
				String[] privileges = new String[tablePrivilegeBean.getPrivilege_table_list().size()];
				StringBuilder builder = new StringBuilder();
				builder.append("SELECT privilege_type, is_grantable FROM ");
				builder.append("information_schema.table_privileges ");
				builder.append("WHERE grantee = ? AND table_schema = ? AND table_name = ?");
				statement = apiConnection.getStmtSelect(builder.toString());
				String user = tablePrivilegeBean.getUser().replaceAll(FrameworkConstants.SYMBOL_TEN, "\'");
				statement.setString(1, user);
				statement.setString(2, apiConnection.getDatabase());
				statement.setString(3, tablePrivilegeBean.getTable());
				resultSet = statement.executeQuery();
				String grant = null;
				while (resultSet.next()) {
					if (grant == null) {
						grant = resultSet.getString(2);
					}
					String privilege = resultSet.getString(1);
					if (tablePrivilegeBean.getPrivilege_table_list().contains(privilege)) {
						privileges[tablePrivilegeBean.getPrivilege_table_list()
								.indexOf(privilege)] = FrameworkConstants.ONE;
					}
				}
				if (grant != null && FrameworkConstants.YES.equalsIgnoreCase(grant)) {
					privileges[privileges.length - 1] = FrameworkConstants.ONE;
				}
				tablePrivilegeBean.setPrivileges(privileges);

				JSONObject jsonObject = new JSONObject();
				jsonObject.put(FrameworkConstants.USER, tablePrivilegeBean.getUser());
				jsonObject.put(FrameworkConstants.TABLE, tablePrivilegeBean.getTable());
				tablePrivilegeBean.setColumn_token(encDecLogic.encode(jsonObject.toString()));
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
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void saveTablePrivileges(Bean bean) throws ClassNotFoundException, SQLException {
		TablePrivilegeBean tablePrivilegeBean = (TablePrivilegeBean) bean;

		ApiConnection apiConnection = null;
		try {
			if (!isEmpty(tablePrivilegeBean.getTable())) {
				apiConnection = getConnection(true);
				_saveTablePrivileges(apiConnection, tablePrivilegeBean.getUser(), tablePrivilegeBean.getTable(),
						tablePrivilegeBean.getPrivileges());
			} else if (tablePrivilegeBean.getTables() != null && tablePrivilegeBean.getTables().length > 0) {
				apiConnection = getConnection(true);
				for (String table : tablePrivilegeBean.getTables()) {
					_saveTablePrivileges(apiConnection, tablePrivilegeBean.getUser(), table,
							tablePrivilegeBean.getPrivileges());
				}
			}

		} finally {
			if (apiConnection != null) {
				apiConnection.commit();
			}
			close(apiConnection);
		}
	}

	/**
	 * 
	 * @param apiConnection
	 * @param userName
	 * @param table
	 * @param privileges
	 * @throws SQLException
	 */
	private void _saveTablePrivileges(final ApiConnection apiConnection, final String userName, final String table,
			final String[] privileges) throws SQLException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT privilege_type, is_grantable FROM ");
			builder.append("information_schema.table_privileges ");
			builder.append("WHERE grantee = ? AND table_schema = ? AND table_name = ?");
			statement = apiConnection.getStmtSelect(builder.toString());
			String user = userName.replaceAll(FrameworkConstants.SYMBOL_TEN, "\'");
			statement.setString(1, user);
			statement.setString(2, apiConnection.getDatabase());
			statement.setString(3, table);
			resultSet = statement.executeQuery();
			String grant = null;
			List<String> privilegeList = new ArrayList<String>();
			while (resultSet.next()) {
				if (grant == null) {
					grant = resultSet.getString(2);
				}
				privilegeList.add(resultSet.getString(1));
			}
			close(resultSet);
			close(statement);
			if (FrameworkConstants.YES.equalsIgnoreCase(grant)) {
				privilegeList.add(FrameworkConstants.GRANT_OPTION);
			}
			if (privileges != null && privileges.length > 0) {
				builder.delete(0, builder.length());
				builder.append("GRANT ");
				boolean alreadyEntered = false;
				for (String privilege : privileges) {
					if (alreadyEntered) {
						builder.append(FrameworkConstants.SYMBOL_COMMA);
					} else {
						alreadyEntered = true;
					}
					builder.append(privilege);
					privilegeList.remove(privilege);
				}
				builder.append(" ON ");
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(apiConnection.getDatabase());
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(FrameworkConstants.SYMBOL_DOT);
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(table);
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(" TO ");
				builder.append(userName);
				statement = apiConnection.getStmt(builder.toString());
				statement.execute();
				close(statement);
			}
			if (privilegeList.size() > 0) {
				Iterator<String> iterator = privilegeList.iterator();
				builder.delete(0, builder.length());
				builder.append("REVOKE ");
				boolean alreadyEntered = false;
				while (iterator.hasNext()) {
					if (alreadyEntered) {
						builder.append(FrameworkConstants.SYMBOL_COMMA);
					} else {
						alreadyEntered = true;
					}
					String privilege = iterator.next();
					builder.append(privilege);
				}
				builder.append(" ON ");
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(apiConnection.getDatabase());
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(FrameworkConstants.SYMBOL_DOT);
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(table);
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(" FROM ");
				builder.append(userName);
				statement = apiConnection.getStmt(builder.toString());
				statement.execute();
			}
		} finally {
			close(resultSet);
			close(statement);
		}
	}

	/**
	 * 
	 * @param bean
	 * @throws Exception
	 */
	public void fillColumnPrivileges(Bean bean) throws Exception {
		ColumnPrivilegeBean columnPrivilegeBean = (ColumnPrivilegeBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		EncDecLogic encDecLogic = null;
		try {
			encDecLogic = new EncDecLogic();
			if (!isEmpty(columnPrivilegeBean.getToken())) {
				try {
					JSONObject jsonObject = new JSONObject(encDecLogic.decode(columnPrivilegeBean.getToken()));
					if (jsonObject.has(FrameworkConstants.USER)) {
						columnPrivilegeBean.setUser(jsonObject.getString(FrameworkConstants.USER));
					}
					if (jsonObject.has(FrameworkConstants.TABLE)) {
						columnPrivilegeBean.setTable(jsonObject.getString(FrameworkConstants.TABLE));
					}
				} catch (Exception e) {
				}
			}
			apiConnection = getConnection(true);
			statement = apiConnection.getStmtSelect(
					"SHOW COLUMNS FROM `" + columnPrivilegeBean.getTable() + FrameworkConstants.SYMBOL_TEN);
			resultSet = statement.executeQuery();
			List<String> columnList = new ArrayList<String>();
			while (resultSet.next()) {
				columnList.add(resultSet.getString(1));
			}
			close(resultSet);
			close(statement);

			columnPrivilegeBean.setColumn_list(columnList);

			StringBuilder builder = new StringBuilder();
			builder.append("SELECT privilege_type,column_name FROM ");
			builder.append("information_schema.column_privileges ");
			builder.append("WHERE grantee = ? AND table_schema = ? AND table_name = ?");
			statement = apiConnection.getStmtSelect(builder.toString());
			String user = columnPrivilegeBean.getUser().replaceAll(FrameworkConstants.SYMBOL_TEN, "\'");
			statement.setString(1, user);
			statement.setString(2, apiConnection.getDatabase());
			statement.setString(3, columnPrivilegeBean.getTable());
			resultSet = statement.executeQuery();
			List<String> selectList = new ArrayList<String>();
			List<String> insertList = new ArrayList<String>();
			List<String> updateList = new ArrayList<String>();
			List<String> referenceList = new ArrayList<String>();
			while (resultSet.next()) {
				String privilege = resultSet.getString(1);
				if (columnPrivilegeBean.getPrivilege_column_list().contains(privilege)) {
					int index = columnPrivilegeBean.getPrivilege_column_list().indexOf(privilege);
					switch (index) {
					case 0:
						selectList.add(resultSet.getString(2));
						break;
					case 1:
						insertList.add(resultSet.getString(2));
						break;
					case 2:
						updateList.add(resultSet.getString(2));
						break;
					case 3:
						referenceList.add(resultSet.getString(2));
						break;
					default:
						break;
					}
				}
			}
			close(resultSet);
			close(statement);
			if (selectList.size() > 0) {
				columnPrivilegeBean.setSelect_column_list(selectList);
			}
			if (insertList.size() > 0) {
				columnPrivilegeBean.setInsert_column_list(insertList);
			}
			if (updateList.size() > 0) {
				columnPrivilegeBean.setUpdate_column_list(updateList);
			}
			if (referenceList.size() > 0) {
				columnPrivilegeBean.setReference_column_list(referenceList);
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(FrameworkConstants.USER, columnPrivilegeBean.getUser());
			jsonObject.put(FrameworkConstants.TABLE, columnPrivilegeBean.getTable());
			columnPrivilegeBean.setTable_token(encDecLogic.encode(jsonObject.toString()));
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}

	}

	/**
	 * 
	 * @param bean
	 * @throws Exception
	 */
	public void saveColumnPrivileges(Bean bean) throws Exception {
		ColumnPrivilegeBean columnPrivilegeBean = (ColumnPrivilegeBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		try {
			if (FrameworkConstants.ONE.equals(columnPrivilegeBean.getFetch())) {

				boolean alreadyEntered = false;
				StringBuilder builder = new StringBuilder();
				builder.append("GRANT ");
				if (columnPrivilegeBean.getSelect_columns() != null
						&& columnPrivilegeBean.getSelect_columns().length > 0) {
					builder.append(" SELECT(");
					for (String column : columnPrivilegeBean.getSelect_columns()) {
						if (alreadyEntered) {
							builder.append(FrameworkConstants.SYMBOL_COMMA);
						} else {
							alreadyEntered = true;
						}
						builder.append(FrameworkConstants.SYMBOL_TEN);
						builder.append(column);
						builder.append(FrameworkConstants.SYMBOL_TEN);
					}
					builder.append(")");
				}
				if (columnPrivilegeBean.getInsert_columns() != null
						&& columnPrivilegeBean.getInsert_columns().length > 0) {
					if (alreadyEntered) {
						builder.append(FrameworkConstants.SYMBOL_COMMA);
						alreadyEntered = false;
					}
					builder.append(" INSERT(");
					for (String column : columnPrivilegeBean.getInsert_columns()) {
						if (alreadyEntered) {
							builder.append(FrameworkConstants.SYMBOL_COMMA);
						} else {
							alreadyEntered = true;
						}
						builder.append(FrameworkConstants.SYMBOL_TEN);
						builder.append(column);
						builder.append(FrameworkConstants.SYMBOL_TEN);
					}
					builder.append(")");
				}
				if (columnPrivilegeBean.getUpdate_columns() != null
						&& columnPrivilegeBean.getUpdate_columns().length > 0) {
					if (alreadyEntered) {
						builder.append(FrameworkConstants.SYMBOL_COMMA);
						alreadyEntered = false;
					}
					builder.append(" UPDATE(");
					for (String column : columnPrivilegeBean.getUpdate_columns()) {
						if (alreadyEntered) {
							builder.append(FrameworkConstants.SYMBOL_COMMA);
						} else {
							alreadyEntered = true;
						}
						builder.append(FrameworkConstants.SYMBOL_TEN);
						builder.append(column);
						builder.append(FrameworkConstants.SYMBOL_TEN);
					}
					builder.append(")");
				}
				if (columnPrivilegeBean.getReference_columns() != null
						&& columnPrivilegeBean.getReference_columns().length > 0) {
					if (alreadyEntered) {
						builder.append(FrameworkConstants.SYMBOL_COMMA);
						alreadyEntered = false;
					}
					builder.append(" REFERENCES(");
					for (String column : columnPrivilegeBean.getReference_columns()) {
						if (alreadyEntered) {
							builder.append(FrameworkConstants.SYMBOL_COMMA);
						} else {
							alreadyEntered = true;
						}
						builder.append(FrameworkConstants.SYMBOL_TEN);
						builder.append(column);
						builder.append(FrameworkConstants.SYMBOL_TEN);
					}
					builder.append(")");
				}
				if (alreadyEntered) {
					apiConnection = getConnection(true);
					builder.append(" ON ");
					builder.append(FrameworkConstants.SYMBOL_TEN);
					builder.append(apiConnection.getDatabase());
					builder.append(FrameworkConstants.SYMBOL_TEN);
					builder.append(FrameworkConstants.SYMBOL_DOT);
					builder.append(FrameworkConstants.SYMBOL_TEN);
					builder.append(columnPrivilegeBean.getTable());
					builder.append(FrameworkConstants.SYMBOL_TEN);
					builder.append(" TO ");
					builder.append(columnPrivilegeBean.getUser());
					statement = apiConnection.getStmt(builder.toString());
					statement.execute();
				}
			} else if (FrameworkConstants.TWO.equals(columnPrivilegeBean.getFetch())) {
				boolean alreadyEntered = false;
				StringBuilder builder = new StringBuilder();
				builder.append("REVOKE ");
				if (columnPrivilegeBean.getSelect_columns() != null
						&& columnPrivilegeBean.getSelect_columns().length > 0) {
					builder.append(" SELECT(");
					for (String column : columnPrivilegeBean.getSelect_columns()) {
						if (alreadyEntered) {
							builder.append(FrameworkConstants.SYMBOL_COMMA);
						} else {
							alreadyEntered = true;
						}
						builder.append(FrameworkConstants.SYMBOL_TEN);
						builder.append(column);
						builder.append(FrameworkConstants.SYMBOL_TEN);
					}
					builder.append(")");
				}
				if (columnPrivilegeBean.getInsert_columns() != null
						&& columnPrivilegeBean.getInsert_columns().length > 0) {
					if (alreadyEntered) {
						builder.append(FrameworkConstants.SYMBOL_COMMA);
						alreadyEntered = false;
					}
					builder.append(" INSERT(");
					for (String column : columnPrivilegeBean.getInsert_columns()) {
						if (alreadyEntered) {
							builder.append(FrameworkConstants.SYMBOL_COMMA);
						} else {
							alreadyEntered = true;
						}
						builder.append(FrameworkConstants.SYMBOL_TEN);
						builder.append(column);
						builder.append(FrameworkConstants.SYMBOL_TEN);
					}
					builder.append(")");
				}
				if (columnPrivilegeBean.getUpdate_columns() != null
						&& columnPrivilegeBean.getUpdate_columns().length > 0) {
					if (alreadyEntered) {
						builder.append(FrameworkConstants.SYMBOL_COMMA);
						alreadyEntered = false;
					}
					builder.append(" UPDATE(");
					for (String column : columnPrivilegeBean.getUpdate_columns()) {
						if (alreadyEntered) {
							builder.append(FrameworkConstants.SYMBOL_COMMA);
						} else {
							alreadyEntered = true;
						}
						builder.append(FrameworkConstants.SYMBOL_TEN);
						builder.append(column);
						builder.append(FrameworkConstants.SYMBOL_TEN);
					}
					builder.append(")");
				}
				if (columnPrivilegeBean.getReference_columns() != null
						&& columnPrivilegeBean.getReference_columns().length > 0) {
					if (alreadyEntered) {
						builder.append(FrameworkConstants.SYMBOL_COMMA);
						alreadyEntered = false;
					}
					builder.append(" REFERENCES(");
					for (String column : columnPrivilegeBean.getReference_columns()) {
						if (alreadyEntered) {
							builder.append(FrameworkConstants.SYMBOL_COMMA);
						} else {
							alreadyEntered = true;
						}
						builder.append(FrameworkConstants.SYMBOL_TEN);
						builder.append(column);
						builder.append(FrameworkConstants.SYMBOL_TEN);
					}
					builder.append(")");
				}
				if (alreadyEntered) {
					apiConnection = getConnection(true);
					builder.append(" ON ");
					builder.append(FrameworkConstants.SYMBOL_TEN);
					builder.append(apiConnection.getDatabase());
					builder.append(FrameworkConstants.SYMBOL_TEN);
					builder.append(FrameworkConstants.SYMBOL_DOT);
					builder.append(FrameworkConstants.SYMBOL_TEN);
					builder.append(columnPrivilegeBean.getTable());
					builder.append(FrameworkConstants.SYMBOL_TEN);
					builder.append(" FROM ");
					builder.append(columnPrivilegeBean.getUser());
					statement = apiConnection.getStmt(builder.toString());
					statement.execute();
				}
			}
		} finally {
			close(statement);
			close(apiConnection);
		}
	}

	/**
	 * 
	 * @param bean
	 * @throws Exception
	 */
	public void fillRoutinePrivileges(Bean bean) throws Exception {
		RoutinePrivilegeBean routinePrivilegeBean = (RoutinePrivilegeBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		EncDecLogic encDecLogic = null;
		try {
			encDecLogic = new EncDecLogic();
			if (!isEmpty(routinePrivilegeBean.getToken())) {
				try {
					JSONObject jsonObject = new JSONObject(encDecLogic.decode(routinePrivilegeBean.getToken()));
					if (jsonObject.has(FrameworkConstants.USER)) {
						routinePrivilegeBean.setUser(jsonObject.getString(FrameworkConstants.USER));
					}
				} catch (Exception e) {
				}
			}
			apiConnection = getConnection(true);
			statement = apiConnection.getStmtSelect("SHOW PROCEDURE STATUS WHERE db = ?");
			statement.setString(1, apiConnection.getDatabase());
			resultSet = statement.executeQuery();
			List<String> procedureList = new ArrayList<String>();
			while (resultSet.next()) {
				procedureList.add(resultSet.getString("name"));
			}
			close(resultSet);
			close(statement);
			routinePrivilegeBean.setProcedure_list(procedureList);

			statement = apiConnection.getStmtSelect("SHOW FUNCTION STATUS WHERE db = ?");
			statement.setString(1, apiConnection.getDatabase());
			resultSet = statement.executeQuery();
			List<String> functionList = new ArrayList<String>();
			while (resultSet.next()) {
				functionList.add(resultSet.getString("name"));
			}
			close(resultSet);
			close(statement);
			routinePrivilegeBean.setFunction_list(functionList);
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}

	}

	/**
	 * 
	 * @param bean
	 * @throws Exception
	 */
	public void saveRoutinePrivileges(Bean bean) throws Exception {
		RoutinePrivilegeBean routinePrivilegeBean = (RoutinePrivilegeBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			String type = null;
			String[] routines = null;
			String[] privileges = null;
			if (routinePrivilegeBean.getProcedures() != null && routinePrivilegeBean.getProcedures().length > 0) {
				routines = routinePrivilegeBean.getProcedures();
				type = "PROCEDURE ";
			} else if (routinePrivilegeBean.getFunctions() != null && routinePrivilegeBean.getFunctions().length > 0) {
				routines = routinePrivilegeBean.getFunctions();
				type = "FUNCTION ";
			}
			if (routinePrivilegeBean.getProcedure_privileges() != null
					&& routinePrivilegeBean.getProcedure_privileges().length > 0) {
				privileges = routinePrivilegeBean.getProcedure_privileges();
			} else if (routinePrivilegeBean.getFunction_privileges() != null
					&& routinePrivilegeBean.getFunction_privileges().length > 0) {
				privileges = routinePrivilegeBean.getFunction_privileges();
			}
			if (routines != null && privileges != null) {
				apiConnection = getConnection(true);
				if (FrameworkConstants.ONE.equals(routinePrivilegeBean.getFetch())) {
					for (String routine : routines) {
						if (privileges != null && privileges.length > 0) {
							StringBuilder builder = new StringBuilder();
							builder.append("GRANT ");
							boolean alreadyEntered = false;
							for (String privilege : privileges) {
								if (alreadyEntered) {
									builder.append(FrameworkConstants.SYMBOL_COMMA);
								} else {
									alreadyEntered = true;
								}
								builder.append(privilege);
							}
							builder.append(" ON ");
							builder.append(type);
							builder.append(FrameworkConstants.SYMBOL_TEN);
							builder.append(apiConnection.getDatabase());
							builder.append(FrameworkConstants.SYMBOL_TEN);
							builder.append(FrameworkConstants.SYMBOL_DOT);
							builder.append(FrameworkConstants.SYMBOL_TEN);
							builder.append(routine);
							builder.append(FrameworkConstants.SYMBOL_TEN);
							builder.append(" TO ");
							builder.append(routinePrivilegeBean.getUser());
							System.out.println(builder.toString());
							statement = apiConnection.getStmt(builder.toString());
							statement.execute();
							close(statement);
						}
					}
				} else if (FrameworkConstants.TWO.equals(routinePrivilegeBean.getFetch())) {
					for (String routine : routines) {
						if (privileges != null && privileges.length > 0) {
							StringBuilder builder = new StringBuilder();
							builder.append("REVOKE ");
							boolean alreadyEntered = false;
							for (String privilege : privileges) {
								if (alreadyEntered) {
									builder.append(FrameworkConstants.SYMBOL_COMMA);
								} else {
									alreadyEntered = true;
								}
								builder.append(privilege);
							}
							builder.append(" ON ");
							builder.append(type);
							builder.append(FrameworkConstants.SYMBOL_TEN);
							builder.append(apiConnection.getDatabase());
							builder.append(FrameworkConstants.SYMBOL_TEN);
							builder.append(FrameworkConstants.SYMBOL_DOT);
							builder.append(FrameworkConstants.SYMBOL_TEN);
							builder.append(routine);
							builder.append(FrameworkConstants.SYMBOL_TEN);
							builder.append(" FROM ");
							builder.append(routinePrivilegeBean.getUser());
							statement = apiConnection.getStmt(builder.toString());
							statement.execute();
							close(statement);
						}
					}
				}
			}

		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}

	}
}
