package com.tracknix.jspmyadmin.application.database.routine.logic;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tracknix.jspmyadmin.application.database.routine.beans.RoutineBean;
import com.tracknix.jspmyadmin.application.database.routine.beans.RoutineInfo;
import com.tracknix.jspmyadmin.application.database.routine.beans.RoutinesBean;
import com.tracknix.jspmyadmin.framework.connection.ApiConnection;
import com.tracknix.jspmyadmin.framework.connection.ConnectionHelper;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.annotations.Detect;
import com.tracknix.jspmyadmin.framework.web.annotations.LogicService;

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

/**
 * @author Yugandhar Gangu
 */
@LogicService
public class RoutineLogic {

    @Detect
    private ConnectionHelper connectionHelper;

    /**
     * @param routinesBean {@link RoutinesBean}
     * @throws SQLException e
     */
    public void fillListBean(RoutinesBean routinesBean) throws SQLException {

        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            apiConnection = connectionHelper.getConnection(routinesBean.getRequest_db());
            StringBuilder builder = new StringBuilder();
            builder.append("SELECT specific_name,data_type,routine_body,is_deterministic,");
            builder.append("sql_data_access,security_type,definer,routine_comment FROM ");
            builder.append("information_schema.routines WHERE routine_type = ? and routine_schema = ?");
            statement = apiConnection.getStmtSelect(builder.toString());
            statement.setString(1, routinesBean.getType());
            statement.setString(2, routinesBean.getRequest_db());
            resultSet = statement.executeQuery();
            List<RoutineInfo> routineInfoList = new ArrayList<RoutineInfo>();
            while (resultSet.next()) {
                RoutineInfo routineInfo = new RoutineInfo();
                routineInfo.setName(resultSet.getString(1));
                routineInfo.setReturns(resultSet.getString(2));
                routineInfo.setRoutine_body(resultSet.getString(3));
                routineInfo.setDeterministic(resultSet.getString(4));
                routineInfo.setData_access(resultSet.getString(5));
                routineInfo.setSecurity_type(resultSet.getString(6));
                routineInfo.setDefiner(resultSet.getString(7));
                routineInfo.setComments(resultSet.getString(8));
                routineInfoList.add(routineInfo);
            }
            routinesBean.setRoutine_info_list(routineInfoList);
        } finally {
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
    }

    /**
     * @param name     {@link String}
     * @param type     {@link String}
     * @param database {@link String}
     * @return boolean
     * @throws SQLException e
     */
    public boolean isExisted(String name, String type, String database) throws SQLException {
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            apiConnection = connectionHelper.getConnection(database);
            statement = apiConnection.getStmtSelect("SHOW " + type + " STATUS WHERE name LIKE ? AND db LIKE ?");
            statement.setString(1, name);
            statement.setString(2, database);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } finally {
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
        return false;
    }

    /**
     * @param routineBean {@link RoutineBean}
     * @return String
     * @throws SQLException e
     */
    public String saveProcedure(RoutineBean routineBean) throws SQLException {
        StringBuilder builder = this._createQuery(routineBean);
        builder.append("PROCEDURE ");
        builder.append(Constants.SYMBOL_TEN);
        builder.append(routineBean.getName());
        builder.append(Constants.SYMBOL_TEN);
        builder.append(Constants.SPACE);
        builder.append(Constants.SYMBOL_BRACKET_OPEN);
        boolean isEntered = false;
        if (routineBean.getParam_types() != null) {
            for (int i = 0; i < routineBean.getParam_types().length; i++) {
                if (!connectionHelper.isEmpty(routineBean.getParams()[i])) {
                    if (isEntered) {
                        builder.append(Constants.SYMBOL_COMMA);
                        builder.append(Constants.SPACE);
                    }
                    isEntered = true;
                    builder.append(routineBean.getParam_types()[i]);
                    builder.append(Constants.SPACE);
                    builder.append(routineBean.getParams()[i]);
                    builder.append(Constants.SPACE);
                    builder.append(routineBean.getParam_data_types()[i]);
                    if (!connectionHelper.isEmpty(routineBean.getLengths()[i])) {
                        builder.append(Constants.SYMBOL_BRACKET_OPEN);
                        builder.append(routineBean.getLengths()[i]);
                        builder.append(Constants.SYMBOL_BRACKET_CLOSE);
                    }
                }
            }
        }
        builder.append(Constants.SYMBOL_BRACKET_CLOSE);
        builder.append(Constants.SPACE);
        builder = this._appendComment(builder, routineBean.getComment());
        if (!connectionHelper.isEmpty(routineBean.getLang_sql())) {
            builder.append(routineBean.getLang_sql());
            builder.append(Constants.SPACE);
        }
        if (!connectionHelper.isEmpty(routineBean.getDeterministic())) {
            builder.append(routineBean.getDeterministic());
            builder.append(Constants.SPACE);
        }
        if (!connectionHelper.isEmpty(routineBean.getSql_type())) {
            builder.append(routineBean.getSql_type());
            builder.append(Constants.SPACE);
        }
        if (!connectionHelper.isEmpty(routineBean.getSql_security())) {
            builder.append("SQL SECURITY ");
            builder.append(routineBean.getSql_security());
            builder.append(Constants.SPACE);
        }
        builder.append("BEGIN ");
        builder.append(routineBean.getBody());
        builder.append(" END");

        return this._createExecuteOrReturn(routineBean.getAction(), routineBean.getRequest_db(), builder.toString());
    }

    /**
     * @param routineBean {@link RoutineBean}
     * @return String
     * @throws SQLException e
     */
    public String saveFunction(RoutineBean routineBean) throws SQLException {
        StringBuilder builder = this._createQuery(routineBean);
        builder.append("FUNCTION ");
        builder.append(Constants.SYMBOL_TEN);
        builder.append(routineBean.getName());
        builder.append(Constants.SYMBOL_TEN);
        builder.append(Constants.SPACE);
        builder.append(Constants.SYMBOL_BRACKET_OPEN);
        boolean isEntered = false;
        if (routineBean.getParam_types() != null) {
            for (int i = 0; i < routineBean.getParam_types().length; i++) {
                if (!connectionHelper.isEmpty(routineBean.getParams()[i])) {
                    if (isEntered) {
                        builder.append(Constants.SYMBOL_COMMA);
                        builder.append(Constants.SPACE);
                    }
                    isEntered = true;

                    // builder.append(routineBean.getParam_types()[i]);
                    // builder.append(FrameworkConstants.SPACE);
                    builder.append(routineBean.getParams()[i]);
                    builder.append(Constants.SPACE);
                    builder.append(routineBean.getParam_data_types()[i]);
                    if (!connectionHelper.isEmpty(routineBean.getLengths()[i])) {
                        builder.append(Constants.SYMBOL_BRACKET_OPEN);
                        builder.append(routineBean.getLengths()[i]);
                        builder.append(Constants.SYMBOL_BRACKET_CLOSE);
                    }
                }
            }
        }
        builder.append(Constants.SYMBOL_BRACKET_CLOSE);
        builder.append(Constants.SPACE);
        builder.append("RETURNS ");
        builder.append(routineBean.getReturn_type());
        if (!connectionHelper.isEmpty(routineBean.getReturn_length())) {
            builder.append(Constants.SYMBOL_TEN);
            builder.append(routineBean.getReturn_length());
            builder.append(Constants.SYMBOL_TEN);
        }
        builder.append(Constants.SPACE);
        builder = this._appendComment(builder, routineBean.getComment());
        if (!connectionHelper.isEmpty(routineBean.getLang_sql())) {
            builder.append(routineBean.getLang_sql());
            builder.append(Constants.SPACE);
        }
        if (!connectionHelper.isEmpty(routineBean.getDeterministic())) {
            builder.append(routineBean.getDeterministic());
            builder.append(Constants.SPACE);
        }
        if (!connectionHelper.isEmpty(routineBean.getSql_type())) {
            builder.append(routineBean.getSql_type());
            builder.append(Constants.SPACE);
        }
        if (!connectionHelper.isEmpty(routineBean.getSql_security())) {
            builder.append("SQL SECURITY ");
            builder.append(routineBean.getSql_security());
            builder.append(Constants.SPACE);
        }
        builder.append("BEGIN ");
        builder.append(routineBean.getBody());
        builder.append(" END");

        return this._createExecuteOrReturn(routineBean.getAction(), routineBean.getRequest_db(), builder.toString());
    }

    /**
     * @param routineBean {@link RoutineBean}
     * @return StringBuilder
     */
    private StringBuilder _createQuery(RoutineBean routineBean) {
        StringBuilder builder = new StringBuilder("CREATE ");
        if (!connectionHelper.isEmpty(routineBean.getDefiner())) {
            if (Constants.CURRENT_USER.equalsIgnoreCase(routineBean.getDefiner())) {
                builder.append("DEFINER = ");
                builder.append(routineBean.getDefiner());
                builder.append(Constants.SPACE);
            } else if (!connectionHelper.isEmpty(routineBean.getDefiner_name())) {
                String[] temp = routineBean.getDefiner_name().split(Constants.SYMBOL_AT);
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
        return builder;
    }

    /**
     * @param builder {@link StringBuilder}
     * @param comment {@link String}
     * @return StringBuilder
     */
    private StringBuilder _appendComment(StringBuilder builder, String comment) {
        if (!connectionHelper.isEmpty(comment)) {
            builder.append("COMMENT ");
            builder.append(Constants.SYMBOL_QUOTE);
            builder.append(comment);
            builder.append(Constants.SYMBOL_QUOTE);
            builder.append(Constants.SPACE);
        }
        return builder;
    }

    /**
     * @param action   {@link String}
     * @param database {@link String}
     * @param query    {@link String}
     * @return String
     * @throws SQLException e
     */
    private String _createExecuteOrReturn(String action, String database, String query) throws SQLException {
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        try {
            if (Constants.YES.equalsIgnoreCase(action)) {
                apiConnection = connectionHelper.getConnection(database);
                statement = apiConnection.getStmt(query);
                statement.execute();
                apiConnection.commit();
            } else {
                return query;
            }
        } finally {
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
        return null;
    }

    /**
     * @param routinesBean {@link RoutinesBean}
     * @return String
     * @throws SQLException e
     */
    public String showCreate(RoutinesBean routinesBean) throws SQLException {
        String result;
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            apiConnection = connectionHelper.getConnection(routinesBean.getRequest_db());
            ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < routinesBean.getRoutines().length; i++) {
                builder.append("SHOW CREATE ");
                builder.append(routinesBean.getType());
                builder.append(" `");
                builder.append(routinesBean.getRoutines()[i]);
                builder.append(Constants.SYMBOL_TEN);
                statement = apiConnection.getStmtSelect(builder.toString());
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    objectNode.put(resultSet.getString(1), resultSet.getString(3));
                }
                connectionHelper.close(resultSet);
                connectionHelper.close(statement);
                builder.delete(0, builder.length());
            }
            result = objectNode.toString();
        } finally {
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
        return result;
    }

    /**
     * @param routinesBean {@link RoutinesBean}
     * @param isProcedure  boolean
     * @throws SQLException e
     */
    public void dropRoutines(RoutinesBean routinesBean, boolean isProcedure) throws SQLException {
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        try {
            apiConnection = connectionHelper.getConnection(routinesBean.getRequest_db());
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < routinesBean.getRoutines().length; i++) {
                builder.append("DROP ");
                builder.append(routinesBean.getType());
                builder.append(" IF EXISTS `");
                builder.append(routinesBean.getRoutines()[i]);
                builder.append(Constants.SYMBOL_TEN);
                statement = apiConnection.getStmt(builder.toString());
                statement.execute();
                builder.delete(0, builder.length());
                connectionHelper.close(statement);
            }
            apiConnection.commit();
        } finally {
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
    }

    /**
     * @param routinesBean {@link RoutinesBean}
     * @param routineType  boolean
     * @return String
     * @throws SQLException e
     * @throws IOException  e
     */
    public String getParamList(RoutinesBean routinesBean, String routineType) throws SQLException, IOException {
        String result = null;
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            apiConnection = connectionHelper.getConnection(routinesBean.getRequest_db());
            statement = apiConnection
                    .getStmtSelect("SELECT param_list FROM mysql.proc WHERE type = ? AND db = ? AND name = ?");
            statement.setString(1, routineType);
            statement.setString(2, routinesBean.getRequest_db());
            statement.setString(3, routinesBean.getRoutines()[0]);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Blob blob = resultSet.getBlob(1);
                inputStream = blob.getBinaryStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder blodDataBuilder = new StringBuilder();
                while ((result = bufferedReader.readLine()) != null) {
                    blodDataBuilder.append(result);
                }
                result = blodDataBuilder.toString();
            }
        } finally {
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
            connectionHelper.close(bufferedReader);
            connectionHelper.close(inputStreamReader);
            connectionHelper.close(inputStream);
        }
        return result;
    }

    /**
     * @param value {@link String}
     * @return boolean
     */
    public boolean isEmpty(String value) {
        return connectionHelper.isEmpty(value);
    }
}