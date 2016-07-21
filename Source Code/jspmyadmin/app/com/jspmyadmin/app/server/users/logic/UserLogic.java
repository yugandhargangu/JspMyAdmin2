/**
 * 
 */
package com.jspmyadmin.app.server.users.logic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.jspmyadmin.app.server.users.beans.GlobalPrivilegeBean;
import com.jspmyadmin.app.server.users.beans.PrivilegeInfo;
import com.jspmyadmin.app.server.users.beans.SchemaPrivilegeBean;
import com.jspmyadmin.app.server.users.beans.SchemaPrivilegeInfo;
import com.jspmyadmin.app.server.users.beans.UserInfo;
import com.jspmyadmin.app.server.users.beans.UserInfoBean;
import com.jspmyadmin.app.server.users.beans.UserListBean;
import com.jspmyadmin.framework.connection.AbstractLogic;
import com.jspmyadmin.framework.connection.ApiConnection;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.logic.EncDecLogic;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/15
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
			builder.append("SELECT user,host,max_questions,max_updates,max_connections,");
			builder.append("max_user_connections,plugin FROM mysql.user ORDER BY user");
			statement = apiConnection.getStmtSelect(builder.toString());
			resultSet = statement.executeQuery();
			encDecLogic = new EncDecLogic();
			List<UserInfo> userInfoList = new ArrayList<UserInfo>();
			while (resultSet.next()) {
				builder.delete(0, builder.length());
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(resultSet.getString(1));
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(FrameworkConstants.SYMBOL_AT);
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(resultSet.getString(2));
				builder.append(FrameworkConstants.SYMBOL_TEN);
				UserInfo userInfo = new UserInfo();
				userInfo.setUser(builder.toString());
				JSONObject jsonObject = new JSONObject();
				jsonObject.put(FrameworkConstants.USER, userInfo.getUser());
				userInfo.setToken(encDecLogic.encode(jsonObject.toString()));
				userInfo.setMax_questions(resultSet.getString(3));
				userInfo.setMax_updates(resultSet.getString(4));
				userInfo.setMax_connections(resultSet.getString(5));
				userInfo.setMax_user_connections(resultSet.getString(6));
				userInfo.setPlugin(resultSet.getString(7));
				userInfoList.add(userInfo);
			}
			userListBean.setUser_info_list(userInfoList);
			close(resultSet);
			close(statement);
			Iterator<UserInfo> iterator = userInfoList.iterator();
			while (iterator.hasNext()) {
				UserInfo userInfo = iterator.next();
				statement = apiConnection.getStmtSelect("SHOW GRANTS FOR " + userInfo.getUser());
				resultSet = statement.executeQuery();
				List<String> grantList = new ArrayList<String>();
				while (resultSet.next()) {
					grantList.add(resultSet.getString(1));
				}
				userInfo.setGrant_list(grantList);
				close(resultSet);
				close(statement);
			}
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
	}

	/**
	 * 
	 * @param token
	 * @throws Exception
	 */
	public void dropUser(String token) throws Exception {
		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		try {
			EncDecLogic encDecLogic = new EncDecLogic();
			JSONObject jsonObject = new JSONObject(encDecLogic.decode(token));
			String user = null;
			if (jsonObject.has(FrameworkConstants.USER)) {
				user = jsonObject.getString(FrameworkConstants.USER);
			} else {
				throw new NullPointerException();
			}
			apiConnection = getConnection(false);
			statement = apiConnection.getStmt("DROP USER " + user);
			statement.execute();
			apiConnection.commit();
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
	public void fillGlobalPrivileges(Bean bean) throws Exception {
		GlobalPrivilegeBean globalPrivilegeBean = (GlobalPrivilegeBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		EncDecLogic encDecLogic = null;
		try {
			encDecLogic = new EncDecLogic();
			JSONObject jsonObject = new JSONObject(encDecLogic.decode(globalPrivilegeBean.getToken()));
			String user = null;
			if (jsonObject.has(FrameworkConstants.USER)) {
				user = jsonObject.getString(FrameworkConstants.USER);
			} else {
				throw new NullPointerException();
			}
			globalPrivilegeBean.setUser(user);
			user = user.replaceAll(FrameworkConstants.SYMBOL_TEN, "\'");
			apiConnection = getConnection(false);
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT privilege_type FROM ");
			builder.append("information_schema.user_privileges WHERE grantee = ?");
			statement = apiConnection.getStmtSelect(builder.toString());
			statement.setString(1, user);
			resultSet = statement.executeQuery();
			List<String> privilegeList = new ArrayList<String>();
			while (resultSet.next()) {
				privilegeList.add(resultSet.getString(1));
			}
			close(resultSet);
			close(statement);
			statement = apiConnection.getStmtSelect("SHOW GRANTS FOR " + globalPrivilegeBean.getUser());
			resultSet = statement.executeQuery();
			boolean grant = false;
			if (resultSet.next()) {
				grant = resultSet.getString(1).endsWith(FrameworkConstants.GRANT_OPTION);
			}
			close(resultSet);
			close(statement);
			if (grant) {
				privilegeList.add(FrameworkConstants.GRANT_OPTION);
			}
			statement = apiConnection.getStmtSelect("SHOW PRIVILEGES");
			resultSet = statement.executeQuery();
			List<PrivilegeInfo> privilegeInfoList = new ArrayList<PrivilegeInfo>();
			while (resultSet.next()) {
				PrivilegeInfo privilegeInfo = new PrivilegeInfo();
				privilegeInfo.setPrivilege(resultSet.getString(1).toUpperCase());
				if (!FrameworkConstants.PROXY.equals(privilegeInfo.getPrivilege())) {
					privilegeInfo.setContext(resultSet.getString(2));
					privilegeInfo.setComment(resultSet.getString(3));
					if (privilegeList.contains(privilegeInfo.getPrivilege())) {
						privilegeInfo.setValue(FrameworkConstants.ONE);
					}
					privilegeInfoList.add(privilegeInfo);
				}
			}
			globalPrivilegeBean.setPrivilege_info_list(privilegeInfoList);
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
	public void saveGlobalPrivileges(Bean bean) throws ClassNotFoundException, SQLException {
		GlobalPrivilegeBean globalPrivilegeBean = (GlobalPrivilegeBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			apiConnection = getConnection(false);
			if (FrameworkConstants.ONE.equals(globalPrivilegeBean.getRevoke_all())) {
				statement = apiConnection
						.getStmt("REVOKE ALL PRIVILEGES, GRANT OPTION FROM " + globalPrivilegeBean.getUser());
				statement.executeQuery();
			} else {
				String user = globalPrivilegeBean.getUser().replaceAll(FrameworkConstants.SYMBOL_TEN, "\'");
				StringBuilder builder = new StringBuilder();
				builder.append("SELECT privilege_type FROM ");
				builder.append("information_schema.user_privileges WHERE grantee = ?");
				statement = apiConnection.getStmtSelect(builder.toString());
				statement.setString(1, user);
				resultSet = statement.executeQuery();
				List<String> privilegeList = new ArrayList<String>();
				while (resultSet.next()) {
					privilegeList.add(resultSet.getString(1));
				}
				close(resultSet);
				close(statement);
				statement = apiConnection.getStmtSelect("SHOW GRANTS FOR " + globalPrivilegeBean.getUser());
				resultSet = statement.executeQuery();
				boolean grant = false;
				if (resultSet.next()) {
					grant = resultSet.getString(1).endsWith(FrameworkConstants.GRANT_OPTION);
				}
				close(resultSet);
				close(statement);
				if (grant) {
					privilegeList.add(FrameworkConstants.GRANT_OPTION);
				}
				if (globalPrivilegeBean.getPrivileges() != null && globalPrivilegeBean.getPrivileges().length > 0) {
					builder.delete(0, builder.length());
					builder.append("GRANT ");
					boolean alreadyEntered = false;
					for (int i = 0; i < globalPrivilegeBean.getPrivileges().length; i++) {
						if (!FrameworkConstants.PROXY.equals(globalPrivilegeBean.getPrivileges()[i])) {
							if (privilegeList.contains(globalPrivilegeBean.getPrivileges()[i])) {
								privilegeList.remove(globalPrivilegeBean.getPrivileges()[i]);
							} else {
								if (alreadyEntered) {
									builder.append(FrameworkConstants.SYMBOL_COMMA);
								} else {
									alreadyEntered = true;
								}
								builder.append(globalPrivilegeBean.getPrivileges()[i]);
							}
						} else {
							if (privilegeList.contains(globalPrivilegeBean.getPrivileges()[i])) {
								privilegeList.remove(globalPrivilegeBean.getPrivileges()[i]);
							}
						}
					}
					if (alreadyEntered) {
						builder.append(" ON *.* TO ");
						builder.append(globalPrivilegeBean.getUser());
						statement = apiConnection.getStmt(builder.toString());
						statement.execute();
						close(statement);
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
					builder.append(" ON *.* FROM ");
					builder.append(globalPrivilegeBean.getUser());
					statement = apiConnection.getStmt(builder.toString());
					statement.execute();
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

	/**
	 * 
	 * @param bean
	 * @throws Exception
	 */
	public void fillSchemaPrivileges(Bean bean) throws Exception {
		SchemaPrivilegeBean schemaPrivilegeBean = (SchemaPrivilegeBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		EncDecLogic encDecLogic = null;
		try {
			encDecLogic = new EncDecLogic();
			JSONObject jsonObject = new JSONObject(encDecLogic.decode(schemaPrivilegeBean.getToken()));
			String user = null;
			if (jsonObject.has(FrameworkConstants.USER)) {
				user = jsonObject.getString(FrameworkConstants.USER);
			} else {
				throw new NullPointerException();
			}
			schemaPrivilegeBean.setUser(user);
			user = user.replaceAll(FrameworkConstants.SYMBOL_TEN, "\'");
			apiConnection = getConnection(false);
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT table_schema,privilege_type,is_grantable FROM ");
			builder.append("information_schema.schema_privileges WHERE grantee = ?");
			builder.append(" ORDER BY table_schema");
			statement = apiConnection.getStmtSelect(builder.toString());
			statement.setString(1, user);
			resultSet = statement.executeQuery();
			List<String> schemaList = new ArrayList<String>();
			List<SchemaPrivilegeInfo> schemaPrivilegeInfoList = new ArrayList<SchemaPrivilegeInfo>();
			String lastSchema = null;
			String[] obj_rights = null;
			String[] ddl_rights = null;
			String[] other_rights = null;
			SchemaPrivilegeInfo schemaPrivilegeInfo = null;
			while (resultSet.next()) {
				String currentSchema = resultSet.getString(1);
				if (!schemaList.contains(currentSchema)) {
					schemaList.add(currentSchema);
				}
				if (lastSchema != null && !currentSchema.equalsIgnoreCase(lastSchema)) {
					schemaPrivilegeInfo.setObj_rights(obj_rights);
					schemaPrivilegeInfo.setDdl_rights(ddl_rights);
					schemaPrivilegeInfo.setOther_rights(other_rights);
					schemaPrivilegeInfoList.add(schemaPrivilegeInfo);
					schemaPrivilegeInfo = null;
				}
				if (schemaPrivilegeInfo == null) {
					schemaPrivilegeInfo = new SchemaPrivilegeInfo();
					schemaPrivilegeInfo.setSchema(currentSchema);
					obj_rights = new String[schemaPrivilegeBean.getPrivilege_obj_list().size()];
					ddl_rights = new String[schemaPrivilegeBean.getPrivilege_ddl_list().size()];
					other_rights = new String[schemaPrivilegeBean.getPrivilege_admn_list().size()];

					if (FrameworkConstants.YES.equalsIgnoreCase(resultSet.getString(3))) {
						other_rights[0] = FrameworkConstants.ONE;
					}
				}
				String privilegeType = resultSet.getString(2);
				if (schemaPrivilegeBean.getPrivilege_obj_list().contains(privilegeType)) {
					obj_rights[schemaPrivilegeBean.getPrivilege_obj_list()
							.indexOf(privilegeType)] = FrameworkConstants.ONE;
				} else if (schemaPrivilegeBean.getPrivilege_ddl_list().contains(privilegeType)) {
					ddl_rights[schemaPrivilegeBean.getPrivilege_ddl_list()
							.indexOf(privilegeType)] = FrameworkConstants.ONE;
				} else if (schemaPrivilegeBean.getPrivilege_admn_list().contains(privilegeType)) {
					other_rights[schemaPrivilegeBean.getPrivilege_admn_list()
							.indexOf(privilegeType)] = FrameworkConstants.ONE;
				}
				lastSchema = currentSchema;
			}
			if (schemaPrivilegeInfo != null) {
				schemaPrivilegeInfo.setObj_rights(obj_rights);
				schemaPrivilegeInfo.setDdl_rights(ddl_rights);
				schemaPrivilegeInfo.setOther_rights(other_rights);
				schemaPrivilegeInfoList.add(schemaPrivilegeInfo);
			}
			close(resultSet);
			close(statement);

			schemaPrivilegeBean.setPrivilege_list(schemaPrivilegeInfoList);
			statement = apiConnection.getStmtSelect("SHOW DATABASES");
			resultSet = statement.executeQuery();
			List<String> databaseList = new ArrayList<String>();
			while (resultSet.next()) {
				String database = resultSet.getString(1);
				if (!schemaList.contains(database)) {
					databaseList.add(database);
				}
			}
			schemaPrivilegeBean.setSchema_list(databaseList);
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
		SchemaPrivilegeBean schemaPrivilegeBean = (SchemaPrivilegeBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			apiConnection = getConnection(false);
			String user = schemaPrivilegeBean.getUser().replaceAll(FrameworkConstants.SYMBOL_TEN, "\'");
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT privilege_type,is_grantable FROM ");
			builder.append("information_schema.schema_privileges WHERE grantee = ?");
			builder.append(" AND table_schema = ?");
			statement = apiConnection.getStmtSelect(builder.toString());
			statement.setString(1, user);
			statement.setString(2, schemaPrivilegeBean.getDatabase());
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
			if (schemaPrivilegeBean.getPrivileges() != null && schemaPrivilegeBean.getPrivileges().length > 0) {
				boolean alreadyEntered = false;
				builder.delete(0, builder.length());
				builder.append("GRANT ");
				for (String privilege : schemaPrivilegeBean.getPrivileges()) {
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
					builder.append(schemaPrivilegeBean.getDatabase());
					builder.append(FrameworkConstants.SYMBOL_TEN);
					builder.append(".* TO ");
					builder.append(schemaPrivilegeBean.getUser());
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
				builder.append(schemaPrivilegeBean.getDatabase());
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(".* FROM ");
				builder.append(schemaPrivilegeBean.getUser());
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
	public void fillUserInfo(Bean bean) throws JSONException, Exception {
		UserInfoBean userInfoBean = (UserInfoBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		EncDecLogic encDecLogic = null;
		try {
			if (!isEmpty(userInfoBean.getToken())) {
				encDecLogic = new EncDecLogic();
				JSONObject jsonObject = new JSONObject(encDecLogic.decode(userInfoBean.getToken()));
				if (jsonObject.has(FrameworkConstants.USER)) {
					String user = jsonObject.getString(FrameworkConstants.USER);
					userInfoBean.setUser(user);
					user = user.substring(1, user.length() - 1);
					String[] temp = user.split("`@`");
					if (temp.length == 2) {
						userInfoBean.setLogin_name(temp[0]);
						userInfoBean.setOld_user(temp[0]);
						userInfoBean.setHost_name(temp[1]);
						userInfoBean.setOld_host(temp[1]);
						apiConnection = getConnection(false);
						statement = apiConnection
								.getStmtSelect("SELECT password FROM mysql.user WHERE user = ? and host = ?");
						statement.setString(1, temp[0]);
						statement.setString(2, temp[1]);
						resultSet = statement.executeQuery();
						if (resultSet.next()) {
							String password = resultSet.getString(1);
							if (password != null && !FrameworkConstants.BLANK.equals(password.trim())) {
								userInfoBean.setPassword(password);
								userInfoBean.setPassword_confirm(password);
							}
						}
					} else {
						throw new NullPointerException();
					}
				}
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
	public void saveUserInfo(Bean bean) throws ClassNotFoundException, SQLException {
		UserInfoBean userInfoBean = (UserInfoBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			apiConnection = getConnection(false);
			if (!isEmpty(userInfoBean.getOld_user())) {
				if (!userInfoBean.getOld_user().equalsIgnoreCase(userInfoBean.getLogin_name())
						|| !userInfoBean.getHost_name().equalsIgnoreCase(userInfoBean.getOld_host())) {
					StringBuilder builder = new StringBuilder();
					builder.append("RENAME USER ");
					builder.append(userInfoBean.getUser());
					builder.append(" TO ");
					builder.append(FrameworkConstants.SYMBOL_TEN);
					builder.append(userInfoBean.getLogin_name());
					builder.append(FrameworkConstants.SYMBOL_TEN);
					builder.append(FrameworkConstants.SYMBOL_AT);
					builder.append(FrameworkConstants.SYMBOL_TEN);
					builder.append(userInfoBean.getHost_name());
					builder.append(FrameworkConstants.SYMBOL_TEN);
					statement = apiConnection.getStmt(builder.toString());
					statement.execute();
					close(statement);
					builder.delete(0, builder.length());
					builder.append(FrameworkConstants.SYMBOL_TEN);
					builder.append(userInfoBean.getLogin_name());
					builder.append(FrameworkConstants.SYMBOL_TEN);
					builder.append(FrameworkConstants.SYMBOL_AT);
					builder.append(FrameworkConstants.SYMBOL_TEN);
					builder.append(userInfoBean.getHost_name());
					builder.append(FrameworkConstants.SYMBOL_TEN);
					userInfoBean.setOld_user(userInfoBean.getLogin_name());
					userInfoBean.setOld_host(userInfoBean.getHost_name());
				}
				statement = apiConnection.getStmtSelect("SELECT password FROM mysql.user WHERE user = ? and host = ?");
				statement.setString(1, userInfoBean.getOld_user());
				statement.setString(2, userInfoBean.getOld_host());
				resultSet = statement.executeQuery();
				String password = null;
				if (resultSet.next()) {
					password = resultSet.getString(1);
				}
				close(resultSet);
				close(statement);
				if (!userInfoBean.getPassword().equals(password)) {
					StringBuilder builder = new StringBuilder();
					builder.append("SET PASSWORD FOR ");
					builder.append(userInfoBean.getUser());
					builder.append(" = PASSWORD(?)");
					statement = apiConnection.getStmt(builder.toString());
					statement.setString(1, userInfoBean.getPassword());
					statement.execute();
				}

			} else {
				StringBuilder builder = new StringBuilder();
				builder.append("CREATE USER ");
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(userInfoBean.getLogin_name());
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(FrameworkConstants.SYMBOL_AT);
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(userInfoBean.getHost_name());
				builder.append(FrameworkConstants.SYMBOL_TEN);
				if (!isEmpty(userInfoBean.getPassword())) {
					builder.append(" IDENTIFIED BY ?");
				}
				statement = apiConnection.getStmt(builder.toString());
				if (!isEmpty(userInfoBean.getPassword())) {
					statement.setString(1, userInfoBean.getPassword());
				}
				statement.execute();
				builder.delete(0, builder.length());
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(userInfoBean.getLogin_name());
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(FrameworkConstants.SYMBOL_AT);
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(userInfoBean.getHost_name());
				builder.append(FrameworkConstants.SYMBOL_TEN);
				userInfoBean.setUser(builder.toString());
			}
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
	}
}
