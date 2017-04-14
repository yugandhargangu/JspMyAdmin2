package com.tracknix.jspmyadmin.application.server.services;

import com.tracknix.jspmyadmin.application.server.beans.common.StatusBean;
import com.tracknix.jspmyadmin.framework.connection.ApiConnection;
import com.tracknix.jspmyadmin.framework.connection.ConnectionHelper;
import com.tracknix.jspmyadmin.framework.constants.AppConstants;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.annotations.Detect;
import com.tracknix.jspmyadmin.framework.web.annotations.LogicService;
import com.tracknix.jspmyadmin.framework.web.utils.Messages;
import com.tracknix.jspmyadmin.framework.web.utils.QueryHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Yugandhar Gangu
 */
@LogicService
public class StatusLogic {
    @Detect
    private ConnectionHelper connectionHelper;
    @Detect
    private Messages messages;
    @Detect
    private QueryHelper queryHelper;

    /**
     * @param statusBean {@link StatusBean}
     * @throws SQLException e
     */
    public void fillBean(StatusBean statusBean) throws SQLException {
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData;
        try {
            apiConnection = connectionHelper.getConnection();
            statement = apiConnection.getStmtSelect(queryHelper.getQuery(1));
            resultSet = statement.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            String[] columns = new String[2];
            columns[0] = resultSetMetaData.getColumnName(1);
            columns[1] = resultSetMetaData.getColumnName(2);
            statusBean.setColumn_info(columns);

            List<String[]> data_list = new ArrayList<String[]>();
            while (resultSet.next()) {
                String[] status = new String[2];
                status[0] = resultSet.getString(1);
                status[1] = resultSet.getString(2);
                data_list.add(status);
            }
            statusBean.setData_list(data_list);
            String[] msgs = new String[4];
            msgs[0] = messages.getMessage(AppConstants.VAL_DAYS);
            msgs[1] = messages.getMessage(AppConstants.VAL_HOURS);
            msgs[2] = messages.getMessage(AppConstants.VAL_MINS);
            msgs[3] = messages.getMessage(AppConstants.VAL_SECS);
            statusBean.setMsgs(msgs);
        } finally {
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
    }
}
