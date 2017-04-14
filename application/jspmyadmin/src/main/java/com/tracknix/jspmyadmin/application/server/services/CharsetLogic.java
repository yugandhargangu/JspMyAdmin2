package com.tracknix.jspmyadmin.application.server.services;

import com.tracknix.jspmyadmin.application.server.beans.common.CharsetBean;
import com.tracknix.jspmyadmin.framework.connection.ApiConnection;
import com.tracknix.jspmyadmin.framework.connection.ConnectionHelper;
import com.tracknix.jspmyadmin.framework.web.annotations.Detect;
import com.tracknix.jspmyadmin.framework.web.annotations.LogicService;
import com.tracknix.jspmyadmin.framework.web.logic.EncodeHelper;
import com.tracknix.jspmyadmin.framework.web.utils.QueryHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yugandhar Gangu
 */
@LogicService
public class CharsetLogic {

    @Detect
    private ConnectionHelper connectionHelper;
    @Detect
    private QueryHelper queryHelper;

    /**
     * @param charsetBean {@link CharsetBean}
     * @throws SQLException e
     */
    public void fillBean(CharsetBean charsetBean) throws SQLException {

        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData;
        try {
            apiConnection = connectionHelper.getConnection();
            statement = apiConnection.getStmtSelect(queryHelper.getQuery(1));
            resultSet = statement.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            int length = resultSetMetaData.getColumnCount() - 1;

            String[] columns = new String[length];
            for (int i = 0; i < length; i++) {
                columns[i] = resultSetMetaData.getColumnName(i + 2);
            }
            charsetBean.setColumns(columns);

            Map<String, List<String[]>> charsets = new LinkedHashMap<String, List<String[]>>();
            List<String[]> collationList = null;
            String last = null;
            while (resultSet.next()) {
                String charset = resultSet.getString(1);
                if (!charset.equals(last)) {
                    collationList = new ArrayList<String[]>();
                    charsets.put(charset, collationList);
                    last = charset;
                }
                String[] charsetInfo = new String[length];
                for (int i = 0; i < length; i++) {
                    charsetInfo[i] = resultSet.getString(i + 2);
                }
                collationList.add(charsetInfo);
            }
            charsetBean.setCharsets(charsets);
        } finally {
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
    }
}
