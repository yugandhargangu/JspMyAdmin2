/**
 *
 */
package com.tracknix.jspmyadmin.application.common.logic;

import com.tracknix.jspmyadmin.framework.connection.ApiConnection;
import com.tracknix.jspmyadmin.framework.connection.ConnectionHelper;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.exception.EncodingException;
import com.tracknix.jspmyadmin.framework.web.annotations.Detect;
import com.tracknix.jspmyadmin.framework.web.annotations.LogicService;
import com.tracknix.jspmyadmin.framework.web.logic.EncodeHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/30
 */
@LogicService
public class DataLogic {

    @Detect
    private ConnectionHelper connectionHelper;
    @Detect
    private EncodeHelper encodeHelper;

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
            statement = apiConnection.getStmtSelect("SHOW DATABASES");
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
     * @param database
     * @param isEncoded
     * @return
     * @throws EncodingException
     * @throws SQLException
     * @throws Exception
     */
    public List<String> getTableList(String database, boolean isEncoded) throws EncodingException, SQLException {

        List<String> tableList = new ArrayList<String>();

        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            if (isEncoded) {
                database = encodeHelper.decode(database);
            }
            apiConnection = connectionHelper.getConnection(database);
            statement = apiConnection.getStmtSelect("SHOW FULL TABLES WHERE TABLE_TYPE LIKE ?");
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
