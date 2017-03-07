package com.tracknix.jspmyadmin.application.common.logic;

import com.tracknix.jspmyadmin.framework.connection.ApiConnection;
import com.tracknix.jspmyadmin.framework.connection.ConnectionHelper;
import com.tracknix.jspmyadmin.framework.web.annotations.Detect;
import com.tracknix.jspmyadmin.framework.web.annotations.LogicService;
import com.tracknix.jspmyadmin.framework.web.logic.EncodeHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Yugandhar Gangu
 */
@LogicService
public class SideBarLogic {

    @Detect
    private ConnectionHelper connectionHelper;
    @Detect
    private EncodeHelper encodeObj;

    /**
     * @return List
     * @throws SQLException e
     */
    public List<String> getDatabaseList() throws SQLException {
        List<String> databaseList;
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            apiConnection = connectionHelper.getConnection();
            statement = apiConnection.getStmtSelect("SHOW DATABASES");
            resultSet = statement.executeQuery();
            databaseList = new ArrayList<String>();
            while (resultSet.next()) {
                databaseList.add(resultSet.getString(1));
            }
            Collections.sort(databaseList);
        } finally {
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
        return databaseList;
    }
}
