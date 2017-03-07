package com.tracknix.jspmyadmin.application.server.common.logic;

import com.tracknix.jspmyadmin.application.server.common.beans.CommonListBean;
import com.tracknix.jspmyadmin.framework.connection.ApiConnection;
import com.tracknix.jspmyadmin.framework.connection.ConnectionHelper;
import com.tracknix.jspmyadmin.framework.web.annotations.Detect;
import com.tracknix.jspmyadmin.framework.web.annotations.LogicService;
import com.tracknix.jspmyadmin.framework.web.logic.EncodeHelper;

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
public class EngineLogic {

    @Detect
    private ConnectionHelper connectionHelper;
    @Detect
    private EncodeHelper encodeObj;

    /**
     * @param engineBean {@link CommonListBean}
     * @throws SQLException e
     */
    public void fillBean(CommonListBean engineBean) throws SQLException {
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData;
        try {
            apiConnection = connectionHelper.getConnection();
            statement = apiConnection.getStmtSelect("SELECT * FROM information_schema.ENGINES ORDER BY ENGINE ASC");
            resultSet = statement.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            int length = resultSetMetaData.getColumnCount();

            String[] engineInfo = new String[length];
            for (int i = 0; i < length; i++) {
                engineInfo[i] = resultSetMetaData.getColumnName(i + 1);
            }
            engineBean.setColumn_info(engineInfo);

            List<String[]> engineInfoList = new ArrayList<String[]>();
            while (resultSet.next()) {
                engineInfo = new String[length];
                for (int i = 0; i < length; i++) {
                    engineInfo[i] = resultSet.getString(i + 1);
                }
                engineInfoList.add(engineInfo);
            }
            engineBean.setData_list(engineInfoList);
        } finally {
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
    }

    /**
     * @return List
     * @throws SQLException e
     */
    public List<String> getEngineList() throws SQLException {

        List<String> engineList;
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            apiConnection = connectionHelper.getConnection();
            statement = apiConnection.getStmtSelect(
                    "SELECT engine FROM information_schema.engines WHERE support <> ? ORDER BY support,engine ASC");
            statement.setString(1, "No");
            resultSet = statement.executeQuery();
            engineList = new ArrayList<String>();
            while (resultSet.next()) {
                engineList.add(resultSet.getString(1));
            }
        } finally {
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
        return engineList;
    }
}
