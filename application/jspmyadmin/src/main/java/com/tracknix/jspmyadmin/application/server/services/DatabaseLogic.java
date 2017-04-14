package com.tracknix.jspmyadmin.application.server.services;

import com.tracknix.jspmyadmin.application.server.beans.database.DatabaseCreateBean;
import com.tracknix.jspmyadmin.application.server.beans.database.DatabaseDropBean;
import com.tracknix.jspmyadmin.application.server.beans.database.DatabaseInfo;
import com.tracknix.jspmyadmin.application.server.beans.database.DatabaseListBean;
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
public class DatabaseLogic {

    @Detect
    private ConnectionHelper connectionHelper;
    @Detect
    private QueryHelper queryHelper;

    /**
     * @param databaseListBean DatabaseListBean
     * @throws SQLException e
     */
    public void fillBean(DatabaseListBean databaseListBean) throws SQLException {
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            apiConnection = connectionHelper.getConnection();
            String query;
            if (!connectionHelper.isEmpty(databaseListBean.getName()) && !connectionHelper.isEmpty(databaseListBean.getType())) {
                query = queryHelper.getQuery(2, databaseListBean.getName(), databaseListBean.getType());
            } else if (!connectionHelper.isEmpty(databaseListBean.getName())) {
                query = queryHelper.getQuery(2, databaseListBean.getName(), Constants.BLANK);
            } else {
                query = queryHelper.getQuery(1);
            }
            statement = apiConnection.getStmtSelect(query);
            resultSet = statement.executeQuery();
            int count = 0;
            int tables = 0;
            int rows = 0;
            long data = 0;
            long index = 0;
            List<DatabaseInfo> databaseInfoList = new ArrayList<DatabaseInfo>();
            while (resultSet.next()) {
                DatabaseInfo databaseInfo = new DatabaseInfo();
                databaseInfo.setDatabase(resultSet.getString(1));
                databaseInfo.setCollation(resultSet.getString(2));
                databaseInfo.setTables(resultSet.getInt(3));
                databaseInfo.setRows(resultSet.getInt(4));
                databaseInfo.setData(resultSet.getLong(5));
                databaseInfo.setIndexes(resultSet.getLong(6));
                databaseInfo.setTotal(resultSet.getLong(7));
                count++;
                tables += databaseInfo.getTables();
                rows += databaseInfo.getRows();
                data += databaseInfo.getData();
                index += databaseInfo.getIndexes();
                databaseInfoList.add(databaseInfo);
            }
            databaseListBean.setDatabase_list(databaseInfoList);
            databaseListBean.setCount(databaseInfoList.size());
            // footer info
            DatabaseInfo databaseInfo = new DatabaseInfo();
            databaseInfo.setDatabase(Integer.toString(count));
            databaseInfo.setTables(tables);
            databaseInfo.setRows(rows);
            databaseInfo.setData(data);
            databaseInfo.setIndexes(index);
            databaseInfo.setTotal(data + index);
            databaseListBean.setFooter_info(databaseInfo);
        } finally {
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
    }

    /**
     * @param bean DatabaseCreateBean
     * @throws SQLException e
     */
    public void createDatabase(DatabaseCreateBean bean) throws SQLException {
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        try {
            apiConnection = connectionHelper.getConnection();
            String collate;
            if (!connectionHelper.isEmpty(bean.getCollation())) {
                collate = queryHelper.getPart(3, 2, bean.getCollation());
            } else {
                collate = Constants.BLANK;
            }
            statement = apiConnection.getStmt(queryHelper.getPart(3, 1, bean.getDatabase(), collate));
            statement.executeUpdate();
            apiConnection.commit();
        } catch (SQLException e) {
            if (apiConnection != null) {
                apiConnection.rollback();
            }
            throw e;
        } finally {
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
    }

    /**
     * @param bean DatabaseDropBean
     * @throws SQLException e
     */
    public void dropDatabase(DatabaseDropBean bean) throws SQLException {

        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        try {
            apiConnection = connectionHelper.getConnection();
            if (bean.getDatabases() != null) {
                for (int i = 0; i < bean.getDatabases().length; i++) {
                    statement = apiConnection.getStmt(queryHelper.getQuery(4, bean.getDatabases()[i]));
                    statement.execute();
                }
            }
            apiConnection.commit();
        } catch (SQLException e) {
            if (apiConnection != null) {
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
     * @return true or false
     */
    public boolean isEmpty(String value) {
        return connectionHelper.isEmpty(value);
    }
}
