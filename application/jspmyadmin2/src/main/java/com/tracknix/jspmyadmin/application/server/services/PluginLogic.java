package com.tracknix.jspmyadmin.application.server.services;

import com.tracknix.jspmyadmin.application.server.beans.common.CommonListBean;
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
public class PluginLogic {

    @Detect
    private ConnectionHelper connectionHelper;
    @Detect
    private QueryHelper queryHelper;

    /**
     * @param pluginBean {@link CommonListBean}
     * @throws SQLException e
     */
    public void fillBean(CommonListBean pluginBean) throws SQLException {
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

            String[] pluginInfo = new String[length];
            for (int i = 0; i < length; i++) {
                pluginInfo[i] = resultSetMetaData.getColumnName(i + 1);
            }
            pluginBean.setColumn_info(pluginInfo);

            List<String[]> pluginInfoList = new ArrayList<String[]>();
            while (resultSet.next()) {
                pluginInfo = new String[length];
                for (int i = 0; i < length; i++) {
                    pluginInfo[i] = resultSet.getString(i + 1);
                }
                pluginInfoList.add(pluginInfo);
            }
            pluginBean.setData_list(pluginInfoList);
        } finally {
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
    }
}
