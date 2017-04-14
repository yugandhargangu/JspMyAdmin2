package com.tracknix.jspmyadmin.application.server.services;

import com.tracknix.jspmyadmin.application.server.beans.common.CommonListBean;
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
import java.util.ArrayList;
import java.util.List;

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
     * @param variableBean {@link CommonListBean}
     * @throws SQLException e
     */
    public void fillBean(CommonListBean variableBean) throws SQLException {
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

            String[] columns = new String[length];
            for (int i = 0; i < length; i++) {
                columns[i] = resultSetMetaData.getColumnName(i + 1);
            }
            variableBean.setColumn_info(columns);

            List<String[]> variableInfoList = new ArrayList<String[]>();
            while (resultSet.next()) {
                String[] data = new String[length + 1];
                for (int i = 0; i < length; i++) {
                    data[i] = resultSet.getString(i + 1);
                }
                data[2] = "SESSION";
                variableInfoList.add(data);
            }
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            statement = apiConnection.getStmtSelect(queryHelper.getQuery(2));
            resultSet = statement.executeQuery();
            int i = 0;
            while (resultSet.next()) {
                String name = resultSet.getString(1);
                while (!name.equals(variableInfoList.get(i)[0])) {
                    i++;
                }
                variableInfoList.get(i)[2] = "SESSION, GLOBAL";
                i++;
            }
            variableBean.setData_list(variableInfoList);
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