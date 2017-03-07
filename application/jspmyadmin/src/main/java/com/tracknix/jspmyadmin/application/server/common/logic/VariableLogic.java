package com.tracknix.jspmyadmin.application.server.common.logic;

import com.tracknix.jspmyadmin.application.server.common.beans.CommonListBean;
import com.tracknix.jspmyadmin.application.server.common.beans.VariableBean;
import com.tracknix.jspmyadmin.framework.connection.ApiConnection;
import com.tracknix.jspmyadmin.framework.connection.ConnectionHelper;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.annotations.Detect;
import com.tracknix.jspmyadmin.framework.web.annotations.LogicService;

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

    /**
     * @param variableBean {@link CommonListBean}
     * @throws SQLException e
     */
    public void fillBean(CommonListBean variableBean) throws SQLException {
        List<String[]> variableInfoList = null;
        int length = 0;
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        try {
            apiConnection = connectionHelper.getConnection();
            statement = apiConnection.getStmtSelect("SHOW VARIABLES");
            resultSet = statement.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            length = resultSetMetaData.getColumnCount();

            String[] columns = new String[length];
            for (int i = 0; i < length; i++) {
                columns[i] = resultSetMetaData.getColumnName(i + 1);
            }
            variableBean.setColumn_info(columns);

            variableInfoList = new ArrayList<String[]>();
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
            statement = apiConnection.getStmtSelect("SHOW GLOBAL VARIABLES");
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
            StringBuilder builder = new StringBuilder();
            for (String scope : bean.getScope()) {
                builder.delete(0, builder.length());
                builder.append("SET ");
                builder.append(scope);
                builder.append(Constants.SPACE);
                builder.append(bean.getName());
                builder.append(" = ?");
                statement = apiConnection.getStmt(builder.toString());
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