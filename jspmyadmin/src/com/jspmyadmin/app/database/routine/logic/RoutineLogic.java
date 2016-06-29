/**
 * 
 */
package com.jspmyadmin.app.database.routine.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.jspmyadmin.app.database.routine.beans.RoutineBean;
import com.jspmyadmin.app.database.routine.beans.RoutineInfo;
import com.jspmyadmin.app.database.routine.beans.RoutineListBean;
import com.jspmyadmin.framework.db.AbstractLogic;
import com.jspmyadmin.framework.db.ApiConnection;
import com.jspmyadmin.framework.util.FrameworkConstants;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/03
 *
 */
public class RoutineLogic extends AbstractLogic {

	/**
	 * 
	 * @param bean
	 * @param routine_type
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void fillListBean(Bean bean, String routine_type) throws ClassNotFoundException, SQLException {

		RoutineListBean routineListBean = null;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		StringBuilder builder = null;
		List<RoutineInfo> routineInfoList = null;
		RoutineInfo routineInfo = null;
		int count = 0;
		try {
			routineListBean = (RoutineListBean) bean;
			apiConnection = super.getConnection(true);
			builder = new StringBuilder();
			builder.append("SELECT specific_name,data_type,routine_body,is_deterministic,");
			builder.append("sql_data_access,security_type,definer,routine_comment FROM ");
			builder.append("information_schema.routines WHERE routine_type = ? and routine_schema = ?");
			statement = apiConnection.preparedStatementSelect(builder.toString());
			statement.setString(1, routine_type);
			statement.setString(2, apiConnection.getDatabase());
			resultSet = statement.executeQuery();
			routineInfoList = new ArrayList<RoutineInfo>();
			while (resultSet.next()) {
				routineInfo = new RoutineInfo();
				routineInfo.setName(resultSet.getString(1));
				routineInfo.setReturns(resultSet.getString(2));
				routineInfo.setRoutine_body(resultSet.getString(3));
				routineInfo.setDeterministic(resultSet.getString(4));
				routineInfo.setData_access(resultSet.getString(5));
				routineInfo.setSecurity_type(resultSet.getString(6));
				routineInfo.setDefiner(resultSet.getString(7));
				routineInfo.setComments(resultSet.getString(8));
				routineInfoList.add(routineInfo);
				count++;
			}
			routineListBean.setRoutine_info_list(routineInfoList);
			routineListBean.setTotal(Integer.toString(count));
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
	}

	/**
	 * 
	 * @param name
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean isExisted(String name, String type) throws ClassNotFoundException, SQLException {
		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			apiConnection = super.getConnection(true);
			statement = apiConnection
					.preparedStatementSelect("SHOW " + type + " STATUS WHERE Name LIKE ? AND Db LIKE ?");
			statement.setString(1, name);
			statement.setString(2, apiConnection.getDatabase());
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
	public String saveProcedure(Bean bean) throws ClassNotFoundException, SQLException {

		String result = null;
		RoutineBean routineBean = null;
		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		StringBuilder builder = null;
		String[] temp = null;
		boolean isEntered = false;
		try {

			routineBean = (RoutineBean) bean;
			apiConnection = super.getConnection(true);
			builder = new StringBuilder();
			builder.append("CREATE ");
			if (!isEmpty(routineBean.getDefiner())) {
				if (FrameworkConstants.CURRENT_USER.equalsIgnoreCase(routineBean.getDefiner())) {
					builder.append("DEFINER = ");
					builder.append(routineBean.getDefiner());
					builder.append(FrameworkConstants.SPACE);
				} else if (!isEmpty(routineBean.getDefiner_name())) {
					temp = routineBean.getDefiner_name().split(FrameworkConstants.SYMBOL_AT);
					builder.append("DEFINER = ");
					if (temp.length < 2) {
						builder.append(FrameworkConstants.SYMBOL_TEN);
						builder.append(temp[0]);
						builder.append(FrameworkConstants.SYMBOL_TEN);
						builder.append(FrameworkConstants.SPACE);
					} else {
						builder.append(FrameworkConstants.SYMBOL_TEN);
						builder.append(temp[0]);
						builder.append(FrameworkConstants.SYMBOL_TEN);
						builder.append(FrameworkConstants.SYMBOL_AT);
						builder.append(FrameworkConstants.SYMBOL_TEN);
						builder.append(temp[1]);
						if (!temp[1].endsWith(FrameworkConstants.SYMBOL_TEN)) {
							builder.append(FrameworkConstants.SYMBOL_TEN);
						}
						builder.append(FrameworkConstants.SPACE);
					}
				}
			}
			builder.append("PROCEDURE ");
			builder.append(FrameworkConstants.SYMBOL_TEN);
			builder.append(routineBean.getName());
			builder.append(FrameworkConstants.SYMBOL_TEN);
			builder.append(FrameworkConstants.SPACE);
			builder.append(FrameworkConstants.SYMBOL_BRACKET_OPEN);
			if (routineBean.getParam_types() != null) {
				for (int i = 0; i < routineBean.getParam_types().length; i++) {
					if (!isEmpty(routineBean.getParams()[i])) {
						if (isEntered) {
							builder.append(FrameworkConstants.SYMBOL_COMMA);
							builder.append(FrameworkConstants.SPACE);
						}
						isEntered = true;
						builder.append(routineBean.getParam_types()[i]);
						builder.append(FrameworkConstants.SPACE);
						builder.append(routineBean.getParams()[i]);
						builder.append(FrameworkConstants.SPACE);
						builder.append(routineBean.getParam_data_types()[i]);
						if (!isEmpty(routineBean.getLengths()[i])) {
							builder.append(FrameworkConstants.SYMBOL_BRACKET_OPEN);
							builder.append(routineBean.getLengths()[i]);
							builder.append(FrameworkConstants.SYMBOL_BRACKET_CLOSE);
						}
					}
				}
			}
			builder.append(FrameworkConstants.SYMBOL_BRACKET_CLOSE);
			builder.append(FrameworkConstants.SPACE);
			if (!isEmpty(routineBean.getComment())) {
				builder.append("COMMENT ");
				builder.append(routineBean.getComment());
				builder.append(FrameworkConstants.SPACE);
			}
			if (!isEmpty(routineBean.getLang_sql())) {
				builder.append(routineBean.getLang_sql());
				builder.append(FrameworkConstants.SPACE);
			}
			if (!isEmpty(routineBean.getDeterministic())) {
				builder.append(routineBean.getDeterministic());
				builder.append(FrameworkConstants.SPACE);
			}
			if (!isEmpty(routineBean.getSql_type())) {
				builder.append(routineBean.getSql_type());
				builder.append(FrameworkConstants.SPACE);
			}
			if (!isEmpty(routineBean.getSql_security())) {
				builder.append("SQL SECURITY ");
				builder.append(routineBean.getSql_security());
				builder.append(FrameworkConstants.SPACE);
			}
			builder.append("BEGIN ");
			builder.append(routineBean.getBody());
			builder.append(" END");

			if (FrameworkConstants.YES.equalsIgnoreCase(routineBean.getAction())) {
				apiConnection = super.getConnection(true);
				statement = apiConnection.preparedStatement(builder.toString());
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
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String saveFunction(Bean bean) throws ClassNotFoundException, SQLException {
		String result = null;
		RoutineBean routineBean = null;
		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		StringBuilder builder = null;
		String[] temp = null;
		boolean isEntered = false;
		try {

			routineBean = (RoutineBean) bean;
			apiConnection = super.getConnection(true);
			builder = new StringBuilder();
			builder.append("CREATE ");
			if (!isEmpty(routineBean.getDefiner())) {
				if (FrameworkConstants.CURRENT_USER.equalsIgnoreCase(routineBean.getDefiner())) {
					builder.append("DEFINER = ");
					builder.append(routineBean.getDefiner());
					builder.append(FrameworkConstants.SPACE);
				} else if (!isEmpty(routineBean.getDefiner_name())) {
					temp = routineBean.getDefiner_name().split(FrameworkConstants.SYMBOL_AT);
					builder.append("DEFINER = ");
					if (temp.length < 2) {
						builder.append(FrameworkConstants.SYMBOL_TEN);
						builder.append(temp[0]);
						builder.append(FrameworkConstants.SYMBOL_TEN);
						builder.append(FrameworkConstants.SPACE);
					} else {
						builder.append(FrameworkConstants.SYMBOL_TEN);
						builder.append(temp[0]);
						builder.append(FrameworkConstants.SYMBOL_TEN);
						builder.append(FrameworkConstants.SYMBOL_AT);
						builder.append(FrameworkConstants.SYMBOL_TEN);
						builder.append(temp[1]);
						if (!temp[1].endsWith(FrameworkConstants.SYMBOL_TEN)) {
							builder.append(FrameworkConstants.SYMBOL_TEN);
						}
						builder.append(FrameworkConstants.SPACE);
					}
				}
			}
			builder.append("FUNCTION ");
			builder.append(FrameworkConstants.SYMBOL_TEN);
			builder.append(routineBean.getName());
			builder.append(FrameworkConstants.SYMBOL_TEN);
			builder.append(FrameworkConstants.SPACE);
			builder.append(FrameworkConstants.SYMBOL_BRACKET_OPEN);
			if (routineBean.getParam_types() != null) {
				for (int i = 0; i < routineBean.getParam_types().length; i++) {
					if (!isEmpty(routineBean.getParams()[i])) {
						if (isEntered) {
							builder.append(FrameworkConstants.SYMBOL_COMMA);
							builder.append(FrameworkConstants.SPACE);
						}
						isEntered = true;

						// builder.append(routineBean.getParam_types()[i]);
						// builder.append(FrameworkConstants.SPACE);
						builder.append(routineBean.getParams()[i]);
						builder.append(FrameworkConstants.SPACE);
						builder.append(routineBean.getParam_data_types()[i]);
						if (!isEmpty(routineBean.getLengths()[i])) {
							builder.append(FrameworkConstants.SYMBOL_BRACKET_OPEN);
							builder.append(routineBean.getLengths()[i]);
							builder.append(FrameworkConstants.SYMBOL_BRACKET_CLOSE);
						}
					}
				}
			}
			builder.append(FrameworkConstants.SYMBOL_BRACKET_CLOSE);
			builder.append(FrameworkConstants.SPACE);
			builder.append("RETURNS ");
			builder.append(routineBean.getReturn_type());
			if (!isEmpty(routineBean.getReturn_length())) {
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(routineBean.getReturn_length());
				builder.append(FrameworkConstants.SYMBOL_TEN);
			}
			builder.append(FrameworkConstants.SPACE);
			if (!isEmpty(routineBean.getComment())) {
				builder.append("COMMENT ");
				builder.append(routineBean.getComment());
				builder.append(FrameworkConstants.SPACE);
			}
			if (!isEmpty(routineBean.getLang_sql())) {
				builder.append(routineBean.getLang_sql());
				builder.append(FrameworkConstants.SPACE);
			}
			if (!isEmpty(routineBean.getDeterministic())) {
				builder.append(routineBean.getDeterministic());
				builder.append(FrameworkConstants.SPACE);
			}
			if (!isEmpty(routineBean.getSql_type())) {
				builder.append(routineBean.getSql_type());
				builder.append(FrameworkConstants.SPACE);
			}
			if (!isEmpty(routineBean.getSql_security())) {
				builder.append("SQL SECURITY ");
				builder.append(routineBean.getSql_security());
				builder.append(FrameworkConstants.SPACE);
			}
			builder.append("BEGIN ");
			builder.append(routineBean.getBody());
			builder.append(" END");

			if (FrameworkConstants.YES.equalsIgnoreCase(routineBean.getAction())) {
				apiConnection = super.getConnection(true);
				statement = apiConnection.preparedStatement(builder.toString());
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
	 * @param isProcedure
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws JSONException
	 */
	public String showCreate(Bean bean, boolean isProcedure)
			throws ClassNotFoundException, SQLException, JSONException {

		String result = null;

		RoutineListBean routineListBean = null;
		JSONObject jsonObject = null;
		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String procedure = "SHOW CREATE PROCEDURE `";
		String function = "SHOW CREATE FUNCTION `";
		try {
			routineListBean = (RoutineListBean) bean;
			apiConnection = super.getConnection(true);
			jsonObject = new JSONObject();
			for (int i = 0; i < routineListBean.getRoutines().length; i++) {
				if (isProcedure) {
					statement = apiConnection.preparedStatementSelect(
							procedure + routineListBean.getRoutines()[i] + FrameworkConstants.SYMBOL_TEN);
				} else {
					statement = apiConnection.preparedStatementSelect(
							function + routineListBean.getRoutines()[i] + FrameworkConstants.SYMBOL_TEN);
				}
				resultSet = statement.executeQuery();
				if (resultSet.next()) {
					jsonObject.put(resultSet.getString(1), resultSet.getString(3));
				}
			}
			result = jsonObject.toString();
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
	 * @param isProcedure
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void dropRoutines(Bean bean, boolean isProcedure) throws ClassNotFoundException, SQLException {

		RoutineListBean routineListBean = null;
		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		String procedure = "DROP PROCEDURE IF EXISTS `";
		String function = "DROP FUNCTION IF EXISTS `";
		try {
			routineListBean = (RoutineListBean) bean;
			apiConnection = super.getConnection(true);
			for (int i = 0; i < routineListBean.getRoutines().length; i++) {
				if (isProcedure) {
					statement = apiConnection.preparedStatement(
							procedure + routineListBean.getRoutines()[i] + FrameworkConstants.SYMBOL_TEN);
				} else {
					statement = apiConnection.preparedStatement(
							function + routineListBean.getRoutines()[i] + FrameworkConstants.SYMBOL_TEN);
				}
				statement.execute();
			}
			apiConnection.commit();
		} finally {
			close(statement);
			close(apiConnection);
		}
	}

	/**
	 * 
	 * @param bean
	 * @param routineType
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public String getParamList(Bean bean, String routineType) throws SQLException, ClassNotFoundException, IOException {
		String result = null;

		RoutineListBean routineListBean = null;
		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		Blob blob = null;
		StringBuilder blodDataBuilder = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		try {
			routineListBean = (RoutineListBean) bean;
			apiConnection = super.getConnection(true);
			statement = apiConnection.preparedStatementSelect(
					"SELECT param_list FROM mysql.proc WHERE type = ? AND db = ? AND name = ?");
			statement.setString(1, routineType);
			statement.setString(2, apiConnection.getDatabase());
			statement.setString(3, routineListBean.getRoutines()[0]);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				blob = resultSet.getBlob(1);
				inputStream = blob.getBinaryStream();
				inputStreamReader = new InputStreamReader(inputStream);
				bufferedReader = new BufferedReader(inputStreamReader);
				blodDataBuilder = new StringBuilder();
				while ((result = bufferedReader.readLine()) != null) {
					blodDataBuilder.append(result);
				}
				result = blodDataBuilder.toString();
				System.out.println(result);
			}
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
			close(bufferedReader);
			close(inputStreamReader);
			close(inputStream);
		}
		return result;
	}

}
