package com.tracknix.jspmyadmin.application.common.services;

import com.tracknix.jspmyadmin.framework.connection.ApiConnection;
import com.tracknix.jspmyadmin.framework.connection.ConnectionHelper;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.annotations.Detect;
import com.tracknix.jspmyadmin.framework.web.annotations.LogicService;
import com.tracknix.jspmyadmin.framework.web.utils.QueryHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yugandhar Gangu
 */
@LogicService
public class DataLogic {

    @Detect
    private ConnectionHelper connectionHelper;
    @Detect
    private QueryHelper queryHelper;

    /**
     * @return List object
     * @throws SQLException e
     */
    public List<String> getDatabaseList() throws SQLException {
        List<String> databaseList = new ArrayList<String>();

        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            apiConnection = connectionHelper.getConnection();
            statement = apiConnection.getStmtSelect(queryHelper.getQuery(1));
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                databaseList.add(resultSet.getString(1));
            }
        } finally {
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
        return databaseList;
    }

    /**
     * @param database {@link String }
     * @return List
     * @throws SQLException e
     */
    public List<String> getTableList(String database) throws SQLException {
        List<String> tableList = new ArrayList<String>();
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            apiConnection = connectionHelper.getConnection(database);
            statement = apiConnection.getStmtSelect(queryHelper.getQuery(2));
            statement.setString(1, Constants.BASE_TABLE);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tableList.add(resultSet.getString(1));
            }
        } finally {
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
        return tableList;
    }
}