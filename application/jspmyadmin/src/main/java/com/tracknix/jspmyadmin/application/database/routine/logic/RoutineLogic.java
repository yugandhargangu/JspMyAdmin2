package com.tracknix.jspmyadmin.application.database.routine.logic;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tracknix.jspmyadmin.application.database.routine.beans.RoutineBean;
import com.tracknix.jspmyadmin.application.database.routine.beans.RoutineInfo;
import com.tracknix.jspmyadmin.application.database.routine.beans.RoutineListBean;
import com.tracknix.jspmyadmin.framework.connection.ApiConnection;
import com.tracknix.jspmyadmin.framework.connection.ConnectionHelper;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.annotations.Detect;
import com.tracknix.jspmyadmin.framework.web.annotations.LogicService;
import com.tracknix.jspmyadmin.framework.web.utils.Bean;

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
     * @param bean
     * @param routine_type
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void fillListBean(Bean bean, String routine_type) throws SQLException {

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
            apiConnection = connectionHelper.getConnection(bean.getRequest_db());
            builder = new StringBuilder();
            builder.append("SELECT specific_name,data_type,routine_body,is_deterministic,");
            builder.append("sql_data_access,security_type,definer,routine_comment FROM ");
            builder.append("information_schema.routines WHERE routine_type = ? and routine_schema = ?");
            statement = apiConnection.getStmtSelect(builder.toString());
            statement.setString(1, routine_type);
            statement.setString(2, bean.getRequest_db());
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
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
    }

    /**
     * @param name
     * @param type
     * @param database
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
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
     * @param bean
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public String saveProcedure(Bean bean) throws SQLException {

        String result = null;
        RoutineBean routineBean = null;
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        StringBuilder builder = null;
        String[] temp = null;
        boolean isEntered = false;
        try {

            routineBean = (RoutineBean) bean;
            apiConnection = connectionHelper.getConnection(bean.getRequest_db());
            builder = new StringBuilder();
            builder.append("CREATE ");
            if (!connectionHelper.isEmpty(routineBean.getDefiner())) {
                if (Constants.CURRENT_USER.equalsIgnoreCase(routineBean.getDefiner())) {
                    builder.append("DEFINER = ");
                    builder.append(routineBean.getDefiner());
                    builder.append(Constants.SPACE);
                } else if (!connectionHelper.isEmpty(routineBean.getDefiner_name())) {
                    temp = routineBean.getDefiner_name().split(Constants.SYMBOL_AT);
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
            builder.append("PROCEDURE ");
            builder.append(Constants.SYMBOL_TEN);
            builder.append(routineBean.getName());
            builder.append(Constants.SYMBOL_TEN);
            builder.append(Constants.SPACE);
            builder.append(Constants.SYMBOL_BRACKET_OPEN);
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
            if (!connectionHelper.isEmpty(routineBean.getComment())) {
                builder.append("COMMENT ");
                builder.append(Constants.SYMBOL_QUOTE);
                builder.append(routineBean.getComment());
                builder.append(Constants.SYMBOL_QUOTE);
                builder.append(Constants.SPACE);
            }
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

            if (Constants.YES.equalsIgnoreCase(routineBean.getAction())) {
                apiConnection = connectionHelper.getConnection(bean.getRequest_db());
                statement = apiConnection.getStmt(builder.toString());
                statement.execute();
                apiConnection.commit();
            } else {
                result = builder.toString();
            }
        } finally {
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
        return result;
    }

    /**
     * @param bean
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public String saveFunction(Bean bean) throws SQLException {
        String result = null;
        RoutineBean routineBean = null;
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        StringBuilder builder = null;
        String[] temp = null;
        boolean isEntered = false;
        try {

            routineBean = (RoutineBean) bean;
            apiConnection = connectionHelper.getConnection(bean.getRequest_db());
            builder = new StringBuilder();
            builder.append("CREATE ");
            if (!connectionHelper.isEmpty(routineBean.getDefiner())) {
                if (Constants.CURRENT_USER.equalsIgnoreCase(routineBean.getDefiner())) {
                    builder.append("DEFINER = ");
                    builder.append(routineBean.getDefiner());
                    builder.append(Constants.SPACE);
                } else if (!connectionHelper.isEmpty(routineBean.getDefiner_name())) {
                    temp = routineBean.getDefiner_name().split(Constants.SYMBOL_AT);
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
            builder.append("FUNCTION ");
            builder.append(Constants.SYMBOL_TEN);
            builder.append(routineBean.getName());
            builder.append(Constants.SYMBOL_TEN);
            builder.append(Constants.SPACE);
            builder.append(Constants.SYMBOL_BRACKET_OPEN);
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
            if (!connectionHelper.isEmpty(routineBean.getComment())) {
                builder.append("COMMENT ");
                builder.append(Constants.SYMBOL_QUOTE);
                builder.append(routineBean.getComment());
                builder.append(Constants.SYMBOL_QUOTE);
                builder.append(Constants.SPACE);
            }
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

            if (Constants.YES.equalsIgnoreCase(routineBean.getAction())) {
                apiConnection = connectionHelper.getConnection(bean.getRequest_db());
                statement = apiConnection.getStmt(builder.toString());
                statement.execute();
                apiConnection.commit();
            } else {
                result = builder.toString();
            }
        } finally {
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
        return result;
    }

    /**
     * @param bean
     * @param isProcedure
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public String showCreate(Bean bean, boolean isProcedure) throws SQLException {

        String result = null;

        RoutineListBean routineListBean = null;
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String procedure = "SHOW CREATE PROCEDURE `";
        String function = "SHOW CREATE FUNCTION `";
        try {
            routineListBean = (RoutineListBean) bean;
            apiConnection = connectionHelper.getConnection(bean.getRequest_db());
            ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
            for (int i = 0; i < routineListBean.getRoutines().length; i++) {
                if (isProcedure) {
                    statement = apiConnection.getStmtSelect(
                            procedure + routineListBean.getRoutines()[i] + Constants.SYMBOL_TEN);
                } else {
                    statement = apiConnection
                            .getStmtSelect(function + routineListBean.getRoutines()[i] + Constants.SYMBOL_TEN);
                }
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    objectNode.put(resultSet.getString(1), resultSet.getString(3));
                }
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
     * @param bean
     * @param isProcedure
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void dropRoutines(Bean bean, boolean isProcedure) throws SQLException {

        RoutineListBean routineListBean = null;
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        String procedure = "DROP PROCEDURE IF EXISTS `";
        String function = "DROP FUNCTION IF EXISTS `";
        try {
            routineListBean = (RoutineListBean) bean;
            apiConnection = connectionHelper.getConnection(bean.getRequest_db());
            for (int i = 0; i < routineListBean.getRoutines().length; i++) {
                if (isProcedure) {
                    statement = apiConnection
                            .getStmt(procedure + routineListBean.getRoutines()[i] + Constants.SYMBOL_TEN);
                } else {
                    statement = apiConnection
                            .getStmt(function + routineListBean.getRoutines()[i] + Constants.SYMBOL_TEN);
                }
                statement.execute();
            }
            apiConnection.commit();
        } finally {
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
    }

    /**
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
            apiConnection = connectionHelper.getConnection(bean.getRequest_db());
            statement = apiConnection
                    .getStmtSelect("SELECT param_list FROM mysql.proc WHERE type = ? AND db = ? AND name = ?");
            statement.setString(1, routineType);
            statement.setString(2, bean.getRequest_db());
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

}
