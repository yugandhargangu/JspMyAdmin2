package com.tracknix.jspmyadmin.application.server.services;

import com.tracknix.jspmyadmin.application.server.beans.common.VariableBean;
import com.tracknix.jspmyadmin.framework.connection.ApiConnection;
import com.tracknix.jspmyadmin.framework.connection.ConnectionHelper;
import com.tracknix.jspmyadmin.framework.web.annotations.Detect;
import com.tracknix.jspmyadmin.framework.web.annotations.LogicService;
import com.tracknix.jspmyadmin.framework.web.utils.QueryHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Yugandhar Gangu
 */
@LogicService
public class VariableLogic {

    @Detect
    private ConnectionHelper connectionHelper;
    @Detect
    private QueryHelper queryHelper;

    /**
     * @return List
     * @throws SQLException e
     */
    public Collection<String[]> getVariables() throws SQLException {
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData;
        try {
            apiConnection = connectionHelper.getConnection();
            statement = apiConnection.getStmtSelect(queryHelper.getQuery(1));
            resultSet = statement.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            int length = resultSetMetaData.getColumnCount();

            Map<String, String[]> variablesMap = new TreeMap<String, String[]>();
            while (resultSet.next()) {
                String[] data = new String[length + 2];
                for (int i = 0; i < length; i++) {
                    data[i] = resultSet.getString(i + 1);
                }
                data[3] = "SESSION";
                variablesMap.put(data[0], data);
            }
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            statement = apiConnection.getStmtSelect(queryHelper.getQuery(2));
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String[] data = new String[length + 2];
                for (int i = 0; i < length; i++) {
                    data[i] = resultSet.getString(i + 1);
                }
                if (variablesMap.containsKey(data[0])) {
                    variablesMap.get(data[0])[2]=data[1];
                    variablesMap.get(data[0])[3]="SESSION,GLOBAL";
                } else {
                    data[2] =  data[1];
                    data[3] = "GLOBAL";
                    variablesMap.put(data[0], data);
                }

            }
            return variablesMap.values();
        } finally {
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
    }

    /**
     * @param bean {@link VariableBean}
     * @throws SQLException e
     */
    public void save(VariableBean bean) throws SQLException {

        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        try {
            apiConnection = connectionHelper.getConnection();
            for (String scope : bean.getScope()) {
                statement = apiConnection.getStmt(queryHelper.getQuery(3, scope, bean.getName()));
                if (connectionHelper.isInteger(bean.getValue())) {
                    statement.setInt(1, Integer.parseInt(bean.getValue()));
                } else {
                    statement.setString(1, bean.getValue());
                }
                statement.executeUpdate();
            }
            apiConnection.commit();
        } catch (SQLException e) {
            if (statement != null) {
                apiConnection.rollback();
            }
            throw e;
        } finally {
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
    }

    /**
     * @param value string
     * @return ConnectionHelper.isEmpty
     */
    public boolean isEmpty(String value) {
        return connectionHelper.isEmpty(value);
    }
}