package com.tracknix.jspmyadmin.application.server.database.logic;

import com.tracknix.jspmyadmin.application.server.database.beans.DatabaseCreateBean;
import com.tracknix.jspmyadmin.application.server.database.beans.DatabaseDropBean;
import com.tracknix.jspmyadmin.application.server.database.beans.DatabaseInfo;
import com.tracknix.jspmyadmin.application.server.database.beans.DatabaseListBean;
import com.tracknix.jspmyadmin.framework.connection.ApiConnection;
import com.tracknix.jspmyadmin.framework.connection.ConnectionHelper;
import com.tracknix.jspmyadmin.framework.constants.Constants;
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
 */
@LogicService
public class DatabaseLogic {

    @Detect
    private ConnectionHelper connectionHelper;
    @Detect
    private EncodeHelper encodeObj;

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
            StringBuilder builder = new StringBuilder();
            builder.append("SELECT a.db_name, a.db_collation, CASE WHEN b.db_table_count > 0 ");
            builder.append("THEN b.db_table_count ELSE 0 END, CASE WHEN b.db_rows_count > 0 ");
            builder.append("THEN b.db_rows_count ELSE 0 END, CASE WHEN b.db_data > 0 THEN ");
            builder.append("b.db_data ELSE 0 END, CASE WHEN b.db_index > 0 THEN b.db_index ");
            builder.append("ELSE 0 END, CASE WHEN b.db_total THEN b.db_total ELSE 0 END ");
            builder.append("FROM ( SELECT SCHEMA_NAME AS db_name, DEFAULT_COLLATION_NAME ");
            builder.append("AS db_collation FROM information_schema.SCHEMATA) AS a ");
            builder.append("LEFT JOIN (SELECT COUNT(TABLE_NAME) AS db_table_count, ");
            builder.append("SUM(TABLE_ROWS) AS db_rows_count, SUM(DATA_LENGTH) AS db_data, ");
            builder.append("SUM(INDEX_LENGTH) AS db_index, SUM(DATA_LENGTH + INDEX_LENGTH) ");
            builder.append("AS db_total ,TABLE_SCHEMA AS db_name FROM information_schema.TABLES ");
            builder.append("GROUP BY TABLE_SCHEMA) AS b ON a.db_name = b.db_name GROUP BY a.db_name,a.db_collation");
            if (!connectionHelper.isEmpty(databaseListBean.getName())) {
                builder.append(" ORDER BY ");
                builder.append(databaseListBean.getName());
            }
            if (!connectionHelper.isEmpty(databaseListBean.getType())) {
                builder.append(databaseListBean.getType());
            }
            statement = apiConnection.getStmtSelect(builder.toString());
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
            StringBuilder query = new StringBuilder("CREATE DATABASE ");
            query.append(Constants.SYMBOL_TEN);
            query.append(bean.getDatabase());
            query.append(Constants.SYMBOL_TEN);
            if (!connectionHelper.isEmpty(bean.getCollation())) {
                query.append(" COLLATE ");
                query.append(bean.getCollation());
            }
            statement = apiConnection.getStmt(query.toString());
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
            StringBuilder query = new StringBuilder();
            if (bean.getDatabases() != null) {
                for (int i = 0; i < bean.getDatabases().length; i++) {
                    query.delete(0, query.length());
                    query.append("DROP DATABASE IF EXISTS ");
                    query.append(Constants.SYMBOL_TEN);
                    query.append(bean.getDatabases()[i]);
                    query.append(Constants.SYMBOL_TEN);
                    statement = apiConnection.getStmt(query.toString());
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
