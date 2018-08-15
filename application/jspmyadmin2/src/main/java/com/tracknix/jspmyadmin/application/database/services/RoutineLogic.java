package com.tracknix.jspmyadmin.application.database.services;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tracknix.jspmyadmin.application.database.beans.routine.RoutineBean;
import com.tracknix.jspmyadmin.application.database.beans.routine.RoutineInfo;
import com.tracknix.jspmyadmin.application.database.beans.routine.RoutinesBean;
import com.tracknix.jspmyadmin.framework.connection.ApiConnection;
import com.tracknix.jspmyadmin.framework.connection.ConnectionHelper;
import com.tracknix.jspmyadmin.framework.connection.util.DataTypes;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.annotations.Detect;
import com.tracknix.jspmyadmin.framework.web.annotations.LogicService;
import com.tracknix.jspmyadmin.framework.web.utils.QueryHelper;

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
public class RoutineLogic extends AbstractBaseLogic {

    @Detect
    private ConnectionHelper connectionHelper;
    @Detect
    private QueryHelper queryHelper;

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
            statement = apiConnection.getStmtSelect(queryHelper.getQuery(1));
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
            statement = apiConnection.getStmtSelect(queryHelper.getQuery(2, type));
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

        // create definer part
        String definer = this._getDefiner(routineBean);
        // create parameters part
        String params = this._createParameterPart(routineBean);
        // create comment part
        String comment;
        if (!connectionHelper.isEmpty(routineBean.getComment())) {
            comment = routineBean.getComment();
        } else {
            comment = Constants.BLANK;
        }

        String lang_sql;
        if (!connectionHelper.isEmpty(routineBean.getLang_sql())) {
            lang_sql = routineBean.getLang_sql();
        } else {
            lang_sql = Constants.BLANK;
        }

        String deterministic;
        if (!connectionHelper.isEmpty(routineBean.getDeterministic())) {
            deterministic = routineBean.getDeterministic();
        } else {
            deterministic = Constants.BLANK;
        }
        String sql_type;
        if (!connectionHelper.isEmpty(routineBean.getSql_type())) {
            sql_type = routineBean.getSql_type();
        } else {
            sql_type = Constants.BLANK;
        }

        String sql_security;
        if (!connectionHelper.isEmpty(routineBean.getSql_security())) {
            sql_security = queryHelper.getPart(3, 7, routineBean.getSql_security());
        } else {
            sql_security = Constants.BLANK;
        }

        String query = queryHelper.getPart(3, 1, definer, routineBean.getName(), params, comment, lang_sql,
                deterministic, sql_type, sql_security, routineBean.getBody());
        return super._createExecuteAOrReturn(connectionHelper, routineBean.getAction(), routineBean.getRequest_db(), query);
    }

    /**
     * @param routineBean {@link RoutineBean}
     * @return String
     * @throws SQLException e
     */
    public String saveFunction(RoutineBean routineBean) throws SQLException {
        // create definer part
        String definer = this._getDefiner(routineBean);
        // create parameters part
        String params = this._createParameterPart(routineBean);
        // create return part
        String returnType = DataTypes.getInstance().findDataTypeInfo(routineBean.getReturn_type()).getDatatype();
        String returnLength;
        if (!connectionHelper.isEmpty(routineBean.getReturn_length())) {
            returnLength = queryHelper.getPart(3, 8, routineBean.getReturn_length());
        } else {
            returnLength = Constants.BLANK;
        }
        // create comment part
        String comment;
        if (!connectionHelper.isEmpty(routineBean.getComment())) {
            comment = routineBean.getComment();
        } else {
            comment = Constants.BLANK;
        }

        String lang_sql;
        if (!connectionHelper.isEmpty(routineBean.getLang_sql())) {
            lang_sql = routineBean.getLang_sql();
        } else {
            lang_sql = Constants.BLANK;
        }

        String deterministic;
        if (!connectionHelper.isEmpty(routineBean.getDeterministic())) {
            deterministic = routineBean.getDeterministic();
        } else {
            deterministic = Constants.BLANK;
        }
        String sql_type;
        if (!connectionHelper.isEmpty(routineBean.getSql_type())) {
            sql_type = routineBean.getSql_type();
        } else {
            sql_type = Constants.BLANK;
        }

        String sql_security;
        if (!connectionHelper.isEmpty(routineBean.getSql_security())) {
            sql_security = queryHelper.getPart(3, 7, routineBean.getSql_security());
        } else {
            sql_security = Constants.BLANK;
        }

        String query = queryHelper.getPart(3, 2, definer, routineBean.getName(), params, returnType, returnLength,
                comment, lang_sql, deterministic, sql_type, sql_security, routineBean.getBody());
        return super._createExecuteAOrReturn(connectionHelper, routineBean.getAction(), routineBean.getRequest_db(), query);
    }

    /**
     * @param routineBean {@link RoutineBean}
     * @return String
     */
    private String _getDefiner(RoutineBean routineBean) {
        if (!connectionHelper.isEmpty(routineBean.getDefiner())) {
            if (Constants.CURRENT_USER.equalsIgnoreCase(routineBean.getDefiner())) {
                return queryHelper.getPart(3, 3);
            } else if (!connectionHelper.isEmpty(routineBean.getDefiner_name())) {
                String[] temp = routineBean.getDefiner_name().split(Constants.SYMBOL_AT);
                String user;
                if (temp.length < 2) {
                    user = queryHelper.getPart(3, 8, temp[0]);
                } else {
                    if (temp[1].endsWith(Constants.SYMBOL_TEN)) {
                        temp[1] = temp[1].substring(0, temp[1].length() - 2);
                    }
                    user = queryHelper.getPart(3, 9, temp[0], temp[1]);
                }
                return queryHelper.getPart(3, 4, user);
            }
        }
        return Constants.BLANK;
    }

    /**
     * @param routineBean {@link RoutineBean}
     * @return String
     */
    private String _createParameterPart(RoutineBean routineBean) {
        boolean isEntered = false;
        if (routineBean.getParams() != null) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < routineBean.getParams().length; i++) {
                if (!connectionHelper.isEmpty(routineBean.getParams()[i])) {
                    if (isEntered) {
                        builder.append(Constants.SYMBOL_COMMA);
                        builder.append(Constants.SPACE);
                    }
                    isEntered = true;
                    String datatype = DataTypes.getInstance().findDataTypeInfo(routineBean.getParam_data_types()[i]).getDatatype();
                    String paramtype;
                    if (routineBean.getParam_types() == null) {
                        paramtype = Constants.BLANK;
                    } else {
                        paramtype = routineBean.getParam_types()[i];
                    }
                    if (!connectionHelper.isEmpty(routineBean.getLengths()[i])) {
                        builder.append(queryHelper.getPart(3, 5, paramtype,
                                routineBean.getParams()[i], datatype, routineBean.getLengths()[i]));
                    } else {
                        builder.append(queryHelper.getPart(3, 5,
                                routineBean.getParam_types()[i], routineBean.getParams()[i], datatype));
                    }
                }
            }
            return builder.toString();
        }
        return Constants.BLANK;
    }

    /**
     * @param routinesBean {@link RoutinesBean}
     * @return String
     * @throws SQLException e
     */
    public String showCreate(RoutinesBean routinesBean) throws SQLException {
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            apiConnection = connectionHelper.getConnection(routinesBean.getRequest_db());
            ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
            for (int i = 0; i < routinesBean.getRoutines().length; i++) {
                String query = queryHelper.getQuery(4, routinesBean.getType(), routinesBean.getRoutines()[i]);
                statement = apiConnection.getStmtSelect(query);
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    objectNode.put(resultSet.getString(1), resultSet.getString(3));
                }
                connectionHelper.close(resultSet);
                connectionHelper.close(statement);
            }
            return objectNode.toString();
        } finally {
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
    }

    /**
     * @param routinesBean {@link RoutinesBean}
     * @throws SQLException e
     */
    public void dropRoutines(RoutinesBean routinesBean) throws SQLException {
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        try {
            apiConnection = connectionHelper.getConnection(routinesBean.getRequest_db());
            for (int i = 0; i < routinesBean.getRoutines().length; i++) {
                String query = queryHelper.getQuery(5, routinesBean.getType(), routinesBean.getRoutines()[i]);
                statement = apiConnection.getStmt(query);
                statement.execute();
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
            statement = apiConnection.getStmtSelect(queryHelper.getQuery(6));
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