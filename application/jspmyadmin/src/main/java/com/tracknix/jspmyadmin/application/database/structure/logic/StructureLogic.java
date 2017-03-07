package com.tracknix.jspmyadmin.application.database.structure.logic;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tracknix.jspmyadmin.application.database.structure.beans.*;
import com.tracknix.jspmyadmin.framework.connection.ApiConnection;
import com.tracknix.jspmyadmin.framework.connection.ConnectionHelper;
import com.tracknix.jspmyadmin.framework.connection.util.DataTypes;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.annotations.Detect;
import com.tracknix.jspmyadmin.framework.web.annotations.LogicService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Yugandhar Gangu
 */
@LogicService
public class StructureLogic {

    @Detect
    private ConnectionHelper connectionHelper;

    /**
     * @param tableListBean {@link StructureListBean}
     * @throws SQLException e
     */
    public void fillBean(StructureListBean tableListBean, final boolean onlyTables) throws SQLException {
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            apiConnection = connectionHelper.getConnection(tableListBean.getRequest_db());
            StringBuilder builder = new StringBuilder();
            builder.append("SELECT table_name, table_type, engine, table_rows, table_collation, ");
            builder.append("(data_length + index_length) AS data_size, auto_increment, create_time, ");
            builder.append("update_time, table_comment FROM information_schema.tables WHERE ");
            builder.append("table_schema = ? AND table_type = ? ORDER BY ");
            if (!connectionHelper.isEmpty(tableListBean.getName())) {
                builder.append(tableListBean.getName());
            } else {
                builder.append("table_name");
            }
            if (!connectionHelper.isEmpty(tableListBean.getType())) {
                builder.append(tableListBean.getType());
            } else {
                builder.append(" ASC");
            }
            statement = apiConnection.getStmtSelect(builder.toString());
            statement.setString(1, tableListBean.getRequest_db());
            if (onlyTables) {
                statement.setString(2, Constants.BASE_TABLE);
            } else {
                statement.setString(2, Constants.VIEW_UPPER_CASE);
            }
            resultSet = statement.executeQuery();
            int count = 0;
            long total = 0;
            int rows = 0;
            List<TableInfo> tableInfoList = new ArrayList<TableInfo>();
            while (resultSet.next()) {
                TableInfo tableInfo = new TableInfo();
                tableInfo.setName(resultSet.getString(1));
                tableInfo.setType(resultSet.getString(2));
                tableInfo.setEngine(resultSet.getString(3));
                tableInfo.setRows(resultSet.getInt(4));
                tableInfo.setCollation(resultSet.getString(5));
                tableInfo.setSize(resultSet.getLong(6));
                tableInfo.setAuto_inr(resultSet.getString(7));
                tableInfo.setCreate_date(resultSet.getString(8));
                tableInfo.setUpdate_date(resultSet.getString(9));
                tableInfo.setComment(resultSet.getString(10));
                tableInfoList.add(tableInfo);
                rows += tableInfo.getRows();
                total += tableInfo.getSize();
                count++;
            }
            tableListBean.setTable_list(tableInfoList);

            TableInfo tableInfo = new TableInfo();
            tableInfo.setName(Integer.toString(count));
            tableInfo.setRows(rows);
            tableInfo.setSize(total);
            tableListBean.setFooter_info(tableInfo);
        } finally {
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
    }

    /**
     * @param structureBean {@link StructureBean}
     * @throws SQLException e
     */
    public void dropTables(StructureBean structureBean, final boolean onlyTables) throws SQLException {
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        try {
            if (structureBean.getTables() != null) {
                boolean withChecks = false;
                apiConnection = connectionHelper.getConnection(structureBean.getRequest_db());
                if (Constants.YES.equalsIgnoreCase(structureBean.getEnable_checks())) {
                    withChecks = true;
                }
                if (!withChecks) {
                    connectionHelper.setForeignKeyChecks(apiConnection, false);
                }
                StringBuilder builder = onlyTables ? new StringBuilder("DROP TABLE IF EXISTS ") : new StringBuilder("DROP VIEW IF EXISTS ");
                this._appendList(structureBean.getTables(), builder);
                statement = apiConnection.getStmt(builder.toString());
                statement.execute();
                if (!withChecks) {
                    connectionHelper.setForeignKeyChecks(apiConnection, true);
                }
                apiConnection.commit();
            }
        } finally {
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
    }

    /**
     * @param structureBean {@link StructureBean}
     * @throws SQLException e
     */
    public void truncateTables(StructureBean structureBean) throws SQLException {
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        try {
            if (structureBean.getTables() != null) {
                boolean withChecks = false;
                apiConnection = connectionHelper.getConnection(structureBean.getRequest_db());
                if (Constants.YES.equalsIgnoreCase(structureBean.getEnable_checks())) {
                    withChecks = true;
                } else {
                    connectionHelper.setForeignKeyChecks(apiConnection, false);
                }
                StringBuilder builder = new StringBuilder("TRUNCATE TABLE ");
                this._appendList(structureBean.getTables(), builder);
                statement = apiConnection.getStmt(builder.toString());
                statement.execute();
                if (!withChecks) {
                    connectionHelper.setForeignKeyChecks(apiConnection, true);
                }
                apiConnection.commit();
            }
        } finally {
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
    }

    /**
     * @param structureBean {@link StructureBean}
     * @return query
     * @throws SQLException e
     */
    public String showCreate(StructureBean structureBean, final boolean onlyTables) throws SQLException {
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            if (structureBean.getTables() != null) {
                apiConnection = connectionHelper.getConnection(structureBean.getRequest_db());
                ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < structureBean.getTables().length; i++) {
                    builder.delete(0, builder.length());
                    if (onlyTables) {
                        builder.append("SHOW CREATE TABLE ");
                    } else {
                        builder.append("SHOW CREATE VIEW ");
                    }
                    builder.append(Constants.SYMBOL_TEN);
                    builder.append(structureBean.getTables()[i]);
                    builder.append(Constants.SYMBOL_TEN);
                    statement = apiConnection.getStmtSelect(builder.toString());
                    resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        objectNode.put(resultSet.getString(1), resultSet.getString(2) + Constants.SYMBOL_SEMI_COLON);
                    }
                    connectionHelper.close(statement);
                }
                return objectNode.toString();
            }
        } finally {
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
        return null;
    }

    /**
     * @param structureBean {@link StructureBean}
     * @throws SQLException e
     */
    public void copyTables(StructureBean structureBean) throws SQLException {
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        try {
            if (structureBean.getTables() != null) {
                apiConnection = connectionHelper.getConnection();
                connectionHelper.setForeignKeyChecks(apiConnection, false);
                StringBuilder builder = new StringBuilder();
                if (Constants.YES.equalsIgnoreCase(structureBean.getDrop_checks())) {
                    // drop tables
                    builder.delete(0, builder.length());
                    builder.append("USE ");
                    builder.append(Constants.SYMBOL_TEN);
                    builder.append(structureBean.getDatabase_name());
                    builder.append(Constants.SYMBOL_TEN);
                    statement = apiConnection.getStmt(builder.toString());
                    statement.execute();
                    connectionHelper.close(statement);
                    builder.delete(0, builder.length());
                    builder.append("DROP TABLE IF EXISTS ");
                    this._appendList(structureBean.getTables(), builder);
                    statement = apiConnection.getStmt(builder.toString());
                    statement.execute();
                    connectionHelper.close(statement);
                }
                for (int i = 0; i < structureBean.getTables().length; i++) {
                    // get table create statement
                    builder.delete(0, builder.length());
                    builder.append("CREATE TABLE IF NOT EXISTS ");
                    builder.append(Constants.SYMBOL_TEN);
                    builder.append(structureBean.getDatabase_name());
                    builder.append(Constants.SYMBOL_TEN);
                    builder.append(Constants.SYMBOL_DOT);
                    builder.append(Constants.SYMBOL_TEN);
                    builder.append(structureBean.getTables()[i]);
                    builder.append(Constants.SYMBOL_TEN);
                    builder.append(" LIKE ");
                    builder.append(Constants.SYMBOL_TEN);
                    builder.append(structureBean.getRequest_db());
                    builder.append(Constants.SYMBOL_TEN);
                    builder.append(Constants.SYMBOL_DOT);
                    builder.append(Constants.SYMBOL_TEN);
                    builder.append(structureBean.getTables()[i]);
                    builder.append(Constants.SYMBOL_TEN);
                    statement = apiConnection.getStmt(builder.toString());
                    statement.execute();
                    connectionHelper.close(statement);
                    // insert data
                    if (Constants.DATA.equalsIgnoreCase(structureBean.getType())) {
                        builder.delete(0, builder.length());
                        builder.append("INSERT INTO ");
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(structureBean.getDatabase_name());
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(Constants.SYMBOL_DOT);
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(structureBean.getTables()[i]);
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append("SELECT * FROM ");
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(structureBean.getRequest_db());
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(Constants.SYMBOL_DOT);
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(structureBean.getTables()[i]);
                        builder.append(Constants.SYMBOL_TEN);
                        statement = apiConnection.getStmt(builder.toString());
                        statement.execute();
                        connectionHelper.close(statement);
                    }
                }
                List<String> tableList = Arrays.asList(structureBean.getTables());
                this._checkForeignKeys(apiConnection, tableList, tableList, structureBean.getRequest_db(), structureBean.getDatabase_name());
                connectionHelper.setForeignKeyChecks(apiConnection, true);
                apiConnection.commit();
            }
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
     * @param structureBean {@link StructureBean}
     * @throws SQLException e
     */
    public void addPrefix(StructureBean structureBean) throws SQLException {
        ApiConnection apiConnection = null;
        try {
            if (structureBean.getTables() != null) {
                apiConnection = connectionHelper.getConnection(structureBean.getRequest_db());
                connectionHelper.setForeignKeyChecks(apiConnection, false);

                StringBuilder builder = new StringBuilder();
                List<String> newTableList = new ArrayList<String>();
                List<String> tableList = new ArrayList<String>();
                builder.append("RENAME TABLE ");
                for (int i = 0; i < structureBean.getTables().length; i++) {
                    tableList.add(structureBean.getTables()[i]);
                    newTableList.add(structureBean.getPrefix() + structureBean.getTables()[i]);
                    builder.append(Constants.SYMBOL_TEN);
                    builder.append(structureBean.getTables()[i]);
                    builder.append(Constants.SYMBOL_TEN);
                    builder.append(" TO ");
                    builder.append(Constants.SYMBOL_TEN);
                    builder.append(structureBean.getPrefix());
                    builder.append(structureBean.getTables()[i]);
                    builder.append(Constants.SYMBOL_TEN);
                    builder.append(Constants.SYMBOL_COMMA);
                }
                this._runPrefixSuffix(structureBean.getRequest_db(), apiConnection, tableList, newTableList, builder);
                connectionHelper.setForeignKeyChecks(apiConnection, true);
                apiConnection.commit();
            }
        } finally {
            connectionHelper.close(apiConnection);
        }
    }

    /**
     * @param structureBean {@link StructureBean}
     * @throws SQLException e
     */
    public void addSuffix(StructureBean structureBean) throws SQLException {
        ApiConnection apiConnection = null;
        try {
            if (structureBean.getTables() != null) {
                apiConnection = connectionHelper.getConnection(structureBean.getRequest_db());
                connectionHelper.setForeignKeyChecks(apiConnection, false);
                StringBuilder builder = new StringBuilder();
                List<String> newTableList = new ArrayList<String>();
                List<String> tableList = new ArrayList<String>();
                builder.append("RENAME TABLE ");
                for (int i = 0; i < structureBean.getTables().length; i++) {
                    tableList.add(structureBean.getTables()[i]);
                    newTableList.add(structureBean.getTables()[i] + structureBean.getPrefix());
                    builder.append(Constants.SYMBOL_TEN);
                    builder.append(structureBean.getTables()[i]);
                    builder.append(Constants.SYMBOL_TEN);
                    builder.append(" TO ");
                    builder.append(Constants.SYMBOL_TEN);
                    builder.append(structureBean.getTables()[i]);
                    builder.append(structureBean.getPrefix());
                    builder.append(Constants.SYMBOL_TEN);
                    builder.append(Constants.SYMBOL_COMMA);
                }
                this._runPrefixSuffix(structureBean.getRequest_db(), apiConnection, tableList, newTableList, builder);
                connectionHelper.setForeignKeyChecks(apiConnection, true);
                apiConnection.commit();
            }
        } finally {
            connectionHelper.close(apiConnection);
        }
    }

    /**
     * @param structureBean {@link StructureBean}
     * @throws SQLException e
     */
    public void replacePrefix(StructureBean structureBean) throws SQLException {
        ApiConnection apiConnection = null;
        try {
            if (structureBean.getTables() != null) {
                apiConnection = connectionHelper.getConnection(structureBean.getRequest_db());
                connectionHelper.setForeignKeyChecks(apiConnection, false);

                int length = structureBean.getPrefix().length();
                StringBuilder builder = new StringBuilder();
                List<String> newTableList = new ArrayList<String>();
                List<String> tableList = new ArrayList<String>();
                builder.append("RENAME TABLE ");
                for (int i = 0; i < structureBean.getTables().length; i++) {
                    if (structureBean.getTables()[i].indexOf(structureBean.getPrefix()) == 0) {
                        tableList.add(structureBean.getTables()[i]);
                        newTableList.add(structureBean.getNew_prefix() + structureBean.getTables()[i].substring(length));
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(structureBean.getTables()[i]);
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(" TO ");
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(structureBean.getNew_prefix());
                        builder.append(structureBean.getTables()[i].substring(length));
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(Constants.SYMBOL_COMMA);
                    }
                }
                this._runPrefixSuffix(structureBean.getRequest_db(), apiConnection, tableList, newTableList, builder);
                connectionHelper.setForeignKeyChecks(apiConnection, true);
                apiConnection.commit();
            }
        } finally {
            connectionHelper.close(apiConnection);
        }
    }

    /**
     * @param structureBean {@link StructureBean}
     * @throws SQLException e
     */
    public void replaceSuffix(StructureBean structureBean) throws SQLException {
        ApiConnection apiConnection = null;
        try {
            if (structureBean.getTables() != null) {
                apiConnection = connectionHelper.getConnection(structureBean.getRequest_db());
                connectionHelper.setForeignKeyChecks(apiConnection, false);

                int length = structureBean.getPrefix().length();
                List<String> newTableList = new ArrayList<String>();
                List<String> tableList = new ArrayList<String>();
                StringBuilder builder = new StringBuilder("RENAME TABLE ");
                for (int i = 0; i < structureBean.getTables().length; i++) {
                    if (structureBean.getTables()[i].endsWith(structureBean.getPrefix())) {
                        tableList.add(structureBean.getTables()[i]);
                        newTableList.add(structureBean.getTables()[i].substring(0, structureBean.getTables()[i].length() - length) + structureBean.getNew_prefix());
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(structureBean.getTables()[i]);
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(" TO ");
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(structureBean.getTables()[i].substring(0,
                                structureBean.getTables()[i].length() - length));
                        builder.append(structureBean.getNew_prefix());
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(Constants.SYMBOL_COMMA);
                    }
                }
                this._runPrefixSuffix(structureBean.getRequest_db(), apiConnection, tableList, newTableList, builder);
                connectionHelper.setForeignKeyChecks(apiConnection, true);
                apiConnection.commit();
            }
        } finally {
            connectionHelper.close(apiConnection);
        }
    }

    /**
     * @param structureBean {@link StructureBean}
     * @throws SQLException e
     */
    public void removePrefix(StructureBean structureBean) throws SQLException {
        ApiConnection apiConnection = null;
        try {
            if (structureBean.getTables() != null) {
                apiConnection = connectionHelper.getConnection(structureBean.getRequest_db());
                connectionHelper.setForeignKeyChecks(apiConnection, false);

                int length = structureBean.getPrefix().length();
                List<String> newTableList = new ArrayList<String>();
                List<String> tableList = new ArrayList<String>();
                StringBuilder builder = new StringBuilder("RENAME TABLE ");
                for (int i = 0; i < structureBean.getTables().length; i++) {
                    if (structureBean.getTables()[i].indexOf(structureBean.getPrefix()) == 0) {
                        tableList.add(structureBean.getTables()[i]);
                        newTableList.add(structureBean.getTables()[i].substring(length));
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(structureBean.getTables()[i]);
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(" TO ");
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(structureBean.getTables()[i].substring(length));
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(Constants.SYMBOL_COMMA);
                    }
                }
                this._runPrefixSuffix(structureBean.getRequest_db(), apiConnection, tableList, newTableList, builder);
                connectionHelper.setForeignKeyChecks(apiConnection, true);
                apiConnection.commit();
            }
        } finally {
            connectionHelper.close(apiConnection);
        }
    }

    /**
     * @param structureBean {@link StructureBean}
     * @throws SQLException e
     */
    public void removeSuffix(StructureBean structureBean) throws SQLException {
        ApiConnection apiConnection = null;
        try {
            if (structureBean.getTables() != null) {
                apiConnection = connectionHelper.getConnection(structureBean.getRequest_db());
                connectionHelper.setForeignKeyChecks(apiConnection, false);

                int length = structureBean.getPrefix().length();
                List<String> newTableList = new ArrayList<String>();
                List<String> tableList = new ArrayList<String>();
                StringBuilder builder = new StringBuilder();
                builder.append("RENAME TABLE ");
                for (int i = 0; i < structureBean.getTables().length; i++) {
                    if (structureBean.getTables()[i].endsWith(structureBean.getPrefix())) {
                        tableList.add(structureBean.getTables()[i]);
                        newTableList.add(structureBean.getTables()[i].substring(0, structureBean.getTables()[i].length() - length));
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(structureBean.getTables()[i]);
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(" TO ");
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(structureBean.getTables()[i].substring(0, structureBean.getTables()[i].length() - length));
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(Constants.SYMBOL_COMMA);
                    }
                }
                this._runPrefixSuffix(structureBean.getRequest_db(), apiConnection, tableList, newTableList, builder);
                connectionHelper.setForeignKeyChecks(apiConnection, true);
                apiConnection.commit();
            }
        } finally {
            connectionHelper.close(apiConnection);
        }
    }

    /**
     * @param structureBean {@link StructureBean}
     * @throws SQLException e
     */
    public void duplicateTable(StructureBean structureBean) throws SQLException {
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        PreparedStatement innerStatement = null;
        ResultSet resultSet = null;
        try {
            if (structureBean.getTables() != null) {
                apiConnection = connectionHelper.getConnection(structureBean.getRequest_db());
                connectionHelper.setForeignKeyChecks(apiConnection, false);

                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < structureBean.getTables().length; i++) {
                    if (_getTableType(apiConnection, structureBean.getTables()[i]) > 0) {
                        boolean isExisted = false;
                        statement = apiConnection.getStmtSelect("SHOW TABLES LIKE ?");
                        statement.setString(1, structureBean.getTables()[i] + "-duplicate");
                        resultSet = statement.executeQuery();
                        while (resultSet.next()) {
                            isExisted = true;
                        }
                        connectionHelper.close(resultSet);
                        connectionHelper.close(statement);
                        if (!isExisted) {
                            builder.delete(0, builder.length());
                            builder.append("CREATE TABLE ");
                            builder.append(Constants.SYMBOL_TEN);
                            builder.append(structureBean.getTables()[i]);
                            builder.append("-duplicate");
                            builder.append(Constants.SYMBOL_TEN);
                            builder.append(" LIKE ");
                            builder.append(Constants.SYMBOL_TEN);
                            builder.append(structureBean.getTables()[i]);
                            builder.append(Constants.SYMBOL_TEN);
                            statement = apiConnection.getStmt(builder.toString());
                            statement.execute();
                            connectionHelper.close(statement);

                            builder.delete(0, builder.length());
                            builder.append("TRUNCATE TABLE ");
                            builder.append(Constants.SYMBOL_TEN);
                            builder.append(structureBean.getTables()[i]);
                            builder.append("-duplicate");
                            builder.append(Constants.SYMBOL_TEN);
                            statement = apiConnection.getStmt(builder.toString());
                            statement.execute();
                            connectionHelper.close(statement);

                            if (Constants.YES.equalsIgnoreCase(structureBean.getEnable_checks())) {
                                builder.delete(0, builder.length());
                                builder.append("SELECT column_name, referenced_table_name, referenced_column_name ");
                                builder.append("FROM information_schema.key_column_usage WHERE referenced_table_schema ");
                                builder.append("IS NOT NULL AND constraint_schema = ? AND table_name = ?");
                                statement = apiConnection.getStmtSelect(builder.toString());
                                statement.setString(1, structureBean.getRequest_db());
                                statement.setString(2, structureBean.getTables()[i]);
                                resultSet = statement.executeQuery();
                                while (resultSet.next()) {
                                    builder.delete(0, builder.length());
                                    builder.append("ALTER TABLE ");
                                    builder.append(Constants.SYMBOL_TEN);
                                    builder.append(structureBean.getRequest_db());
                                    builder.append(Constants.SYMBOL_TEN);
                                    builder.append(Constants.SYMBOL_DOT);
                                    builder.append(Constants.SYMBOL_TEN);
                                    builder.append(structureBean.getTables()[i]);
                                    builder.append("-duplicate");
                                    builder.append(Constants.SYMBOL_TEN);
                                    builder.append(" ADD FOREIGN KEY (");
                                    builder.append(Constants.SYMBOL_TEN);
                                    builder.append(resultSet.getString(1));
                                    builder.append(Constants.SYMBOL_TEN);
                                    builder.append(") REFERENCES ");
                                    builder.append(Constants.SYMBOL_TEN);
                                    builder.append(structureBean.getRequest_db());
                                    builder.append(Constants.SYMBOL_TEN);
                                    builder.append(Constants.SYMBOL_DOT);
                                    builder.append(Constants.SYMBOL_TEN);
                                    builder.append(resultSet.getString(2));
                                    builder.append(Constants.SYMBOL_TEN);
                                    builder.append(Constants.SYMBOL_BRACKET_OPEN);
                                    builder.append(Constants.SYMBOL_TEN);
                                    builder.append(resultSet.getString(3));
                                    builder.append(Constants.SYMBOL_TEN);
                                    builder.append(Constants.SYMBOL_BRACKET_CLOSE);
                                    builder.append(" ON DELETE NO ACTION ON UPDATE NO ACTION");
                                    innerStatement = apiConnection.getStmt(builder.toString());
                                    innerStatement.execute();
                                    connectionHelper.close(innerStatement);
                                }
                                connectionHelper.close(resultSet);
                                connectionHelper.close(statement);
                            }
                            if (Constants.YES.equalsIgnoreCase(structureBean.getDrop_checks())) {
                                builder.delete(0, builder.length());
                                builder.append("INSERT ");
                                builder.append(Constants.SYMBOL_TEN);
                                builder.append(structureBean.getTables()[i]);
                                builder.append("-duplicate");
                                builder.append(Constants.SYMBOL_TEN);
                                builder.append(" SELECT * FROM ");
                                builder.append(Constants.SYMBOL_TEN);
                                builder.append(structureBean.getTables()[i]);
                                builder.append(Constants.SYMBOL_TEN);
                                statement = apiConnection.getStmt(builder.toString());
                                statement.execute();
                                connectionHelper.close(statement);
                            }
                        }
                    }
                }
                connectionHelper.setForeignKeyChecks(apiConnection, true);
                apiConnection.commit();
            }
        } finally {
            connectionHelper.close(innerStatement);
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
    }

    /**
     * @param apiConnection {@link ApiConnection}
     * @param tableName     {@link String}
     * @return int
     * @throws SQLException e
     */
    private int _getTableType(final ApiConnection apiConnection, CharSequence tableName) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = apiConnection.getStmtSelect("SHOW FULL TABLES LIKE ?");
            statement.setString(1, tableName.toString());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                if (Constants.VIEW.equalsIgnoreCase(resultSet.getString(2))) {
                    return -1;
                }
                return 1;
            }
            return 0;
        } finally {
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
        }
    }

    /**
     * @param database      {@link String}
     * @param apiConnection {@link ApiConnection}
     * @param tableList     {@link List}
     * @param newTableList  {@link List}
     * @param builder       {@link StringBuilder}
     * @throws SQLException e
     */
    private void _runPrefixSuffix(String database, ApiConnection apiConnection, List<String> tableList, List<String> newTableList, StringBuilder builder) throws SQLException {
        PreparedStatement statement = null;
        try {
            if (tableList.size() > 0) {
                builder = builder.deleteCharAt(builder.lastIndexOf(Constants.SYMBOL_COMMA));
                statement = apiConnection.getStmt(builder.toString());
                statement.execute();
                this._checkForeignKeys(database, apiConnection, tableList, newTableList);
            }
        } finally {
            connectionHelper.close(statement);
        }
    }

    /**
     * @param apiConnection {@link ApiConnection}
     * @param tableList     {@link List}
     * @param newTableList  {@link List}
     * @throws SQLException e
     */
    private void _checkForeignKeys(String database, ApiConnection apiConnection, List<String> tableList, List<String> newTableList) throws SQLException {

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        PreparedStatement innerStatement = null;
        try {
            Iterator<String> tableIterator = newTableList.iterator();
            StringBuilder builder = new StringBuilder();
            while (tableIterator.hasNext()) {
                String current = tableIterator.next();
                switch (_getTableType(apiConnection, current)) {
                    case 1:
                        // in case of base table
                        builder.delete(0, builder.length());
                        builder.append("SELECT CONSTRAINT_NAME,REFERENCED_TABLE_NAME,REFERENCED_COLUMN_NAME,");
                        builder.append("COLUMN_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE ");
                        builder.append("REFERENCED_TABLE_NAME IS NOT null AND CONSTRAINT_SCHEMA = ? AND TABLE_NAME = ?");
                        statement = apiConnection.getStmtSelect(builder.toString());
                        statement.setString(1, database);
                        statement.setString(2, current);
                        resultSet = statement.executeQuery();
                        while (resultSet.next()) {
                            String table = resultSet.getString(2);
                            if (tableList.contains(table)) {
                                builder.delete(0, builder.length());
                                builder.append("ALTER TABLE ");
                                builder.append(Constants.SYMBOL_TEN);
                                builder.append(current);
                                builder.append(Constants.SYMBOL_TEN);
                                builder.append(" DROP FOREIGN KEY ");
                                builder.append(Constants.SYMBOL_TEN);
                                builder.append(resultSet.getString(1));
                                builder.append(Constants.SYMBOL_TEN);
                                innerStatement = apiConnection.getStmt(builder.toString());
                                innerStatement.execute();
                                innerStatement.close();
                                innerStatement = null;

                                builder.delete(0, builder.length());
                                builder.append("ALTER TABLE ");
                                builder.append(Constants.SYMBOL_TEN);
                                builder.append(current);
                                builder.append(Constants.SYMBOL_TEN);
                                builder.append(" ADD FOREIGN KEY (");
                                builder.append(Constants.SYMBOL_TEN);
                                builder.append(resultSet.getString(4));
                                builder.append(Constants.SYMBOL_TEN);
                                builder.append(") REFERENCES ");
                                builder.append(Constants.SYMBOL_TEN);
                                builder.append(resultSet.getString(2));
                                builder.append(Constants.SYMBOL_TEN);
                                builder.append(Constants.SYMBOL_BRACKET_OPEN);
                                builder.append(Constants.SYMBOL_TEN);
                                builder.append(resultSet.getString(3));
                                builder.append(Constants.SYMBOL_TEN);
                                builder.append(Constants.SYMBOL_BRACKET_CLOSE);
                                builder.append(" ON DELETE NO ACTION ON UPDATE NO ACTION");
                                innerStatement = apiConnection.getStmt(builder.toString());
                                innerStatement.execute();
                                innerStatement.close();
                                innerStatement = null;
                            }
                        }
                        resultSet.close();
                        resultSet = null;
                        statement.close();
                        statement = null;
                        break;
                    case -1:
                        break;
                    default:
                        break;
                }
            }
        } finally {
            connectionHelper.close(innerStatement);
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
        }
    }

    /**
     * @param apiConnection {@link ApiConnection}
     * @param tableList     {@link List}
     * @param newTableList  {@link List}
     * @param oldDatabase   {@link String}
     * @param newDatabase   {@link String}
     * @throws SQLException e
     */
    private void _checkForeignKeys(ApiConnection apiConnection, List<String> tableList, List<String> newTableList, String oldDatabase, String newDatabase) throws SQLException {
        PreparedStatement statement = null;
        PreparedStatement innerStatement = null;
        ResultSet resultSet = null;
        try {
            Iterator<String> tableIterator = newTableList.iterator();
            StringBuilder builder = new StringBuilder();
            while (tableIterator.hasNext()) {
                String current = tableIterator.next();
                builder.delete(0, builder.length());
                builder.append("SELECT CONSTRAINT_NAME,REFERENCED_TABLE_NAME,REFERENCED_COLUMN_NAME,");
                builder.append("COLUMN_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE ");
                builder.append("REFERENCED_TABLE_NAME IS NOT null AND CONSTRAINT_SCHEMA = ? AND TABLE_NAME = ?");
                statement = apiConnection.getStmtSelect(builder.toString());
                statement.setString(1, oldDatabase);
                statement.setString(2, current);
                resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String table = resultSet.getString(2);
                    if (tableList.contains(table)) {
                        builder.delete(0, builder.length());
                        builder.append("ALTER TABLE ");
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(newDatabase);
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(Constants.SYMBOL_DOT);
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(current);
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(" ADD FOREIGN KEY (");
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(resultSet.getString(4));
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(") REFERENCES ");
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(newDatabase);
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(Constants.SYMBOL_DOT);
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(resultSet.getString(2));
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(Constants.SYMBOL_BRACKET_OPEN);
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(resultSet.getString(3));
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(Constants.SYMBOL_BRACKET_CLOSE);
                        builder.append(" ON DELETE NO ACTION ON UPDATE NO ACTION");
                        innerStatement = apiConnection.getStmt(builder.toString());
                        innerStatement.execute();
                        connectionHelper.close(innerStatement);
                    }
                }
            }
        } finally {
            connectionHelper.close(innerStatement);
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
        }
    }

    /**
     * @return boolean
     * @throws SQLException e
     */
    public boolean isSupportsPartition() throws SQLException {
        boolean result = false;
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            apiConnection = connectionHelper.getConnection();
            statement = apiConnection.getStmtSelect(
                    "SELECT plugin_name FROM information_schema.PLUGINS WHERE plugin_status = ? and plugin_name = ?");
            statement.setString(1, "Active");
            statement.setString(2, "partition");
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = true;
            }
        } finally {
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
        return result;
    }

    /**
     * @param createTableBean {@link CreateTableBean}
     * @return query
     * @throws SQLException e
     */
    public String createTable(CreateTableBean createTableBean) throws SQLException {
        String primary_key = null;
        List<String> uniqueList = new ArrayList<String>();
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE ");
        builder.append(Constants.SYMBOL_TEN);
        builder.append(createTableBean.getTable_name());
        builder.append(Constants.SYMBOL_TEN);
        builder.append(Constants.SYMBOL_BRACKET_OPEN);
        boolean alreadyEntered = false;
        for (int i = 0; i < createTableBean.getColumns().length; i++) {
            if (!connectionHelper.isEmpty(createTableBean.getColumns()[i])) {
                if (alreadyEntered) {
                    builder.append(Constants.SYMBOL_COMMA);
                }
                if (!alreadyEntered) {
                    alreadyEntered = true;
                }
                builder.append(Constants.SYMBOL_TEN);
                builder.append(createTableBean.getColumns()[i]);
                builder.append(Constants.SYMBOL_TEN);
                builder.append(Constants.SPACE);
                DataTypes.DataTypeInfo dataTypeInfo = DataTypes.getInstance().findDataTypeInfo(createTableBean.getDatatypes()[i]);
                builder.append(dataTypeInfo.getDatatype());
                if (!connectionHelper.isEmpty(createTableBean.getLengths()[i])) {
                    builder.append(Constants.SYMBOL_BRACKET_OPEN);
                    builder.append(createTableBean.getLengths()[i]);
                    builder.append(Constants.SYMBOL_BRACKET_CLOSE);
                }
                builder.append(Constants.SPACE);
                if (Constants.ONE.equals(createTableBean.getZfs()[i])) {
                    builder.append("ZEROFILL");
                    builder.append(Constants.SPACE);
                }
                if (Constants.ONE.equals(createTableBean.getUns()[i])) {
                    builder.append("UNSIGNED");
                    builder.append(Constants.SPACE);
                }
                if (Constants.ONE.equals(createTableBean.getBins()[i])) {
                    builder.append("BINARY");
                    builder.append(Constants.SPACE);
                }
                if (!connectionHelper.isEmpty(createTableBean.getCollations()[i])) {
                    builder.append("COLLATE");
                    builder.append(Constants.SPACE);
                    builder.append(Constants.SYMBOL_QUOTE);
                    builder.append(createTableBean.getCollations()[i]);
                    builder.append(Constants.SYMBOL_QUOTE);
                    builder.append(Constants.SPACE);
                }
                if (Constants.ONE.equals(createTableBean.getNns()[i])) {
                    builder.append("NOT NULL");
                    builder.append(Constants.SPACE);
                } else {
                    builder.append("NULL");
                    builder.append(Constants.SPACE);
                }
                if (!connectionHelper.isEmpty(createTableBean.getDefaults()[i])) {
                    builder.append("DEFAULT");
                    builder.append(Constants.SPACE);
                    if (Constants.CURRENT_TIMESTAMP.equals(createTableBean.getDefaults()[i])) {
                        builder.append(createTableBean.getDefaults()[i]);
                    } else {
                        builder.append(Constants.SYMBOL_QUOTE);
                        builder.append(createTableBean.getDefaults()[i]);
                        builder.append(Constants.SYMBOL_QUOTE);
                    }
                    builder.append(Constants.SPACE);
                }
                if (Constants.ONE.equals(createTableBean.getAis()[i])) {
                    builder.append("AUTO_INCREMENT");
                    builder.append(Constants.SPACE);
                }

                builder.append(Constants.SPACE);
                builder.append("COMMENT");
                builder.append(Constants.SPACE);
                builder.append(Constants.SYMBOL_QUOTE);
                if (!connectionHelper.isEmpty(createTableBean.getComments()[i])) {
                    builder.append(createTableBean.getComments()[i]);
                }
                builder.append(Constants.SYMBOL_QUOTE);

                if (Constants.ONE.equals(createTableBean.getPks()[i])) {
                    primary_key = createTableBean.getColumns()[i];
                }
                if (Constants.ONE.equals(createTableBean.getUqs()[i])) {
                    uniqueList.add(createTableBean.getColumns()[i]);
                }
            }
        }
        if (primary_key != null) {
            builder.append(Constants.SYMBOL_COMMA);
            builder.append("PRIMARY KEY");
            builder.append(Constants.SYMBOL_BRACKET_OPEN);
            builder.append(Constants.SYMBOL_TEN);
            builder.append(primary_key);
            builder.append(Constants.SYMBOL_TEN);
            builder.append(Constants.SYMBOL_BRACKET_CLOSE);
        }

        for (String anUniqueList : uniqueList) {
            builder.append(Constants.SYMBOL_COMMA);
            builder.append("UNIQUE");
            builder.append(Constants.SYMBOL_BRACKET_OPEN);
            builder.append(Constants.SYMBOL_TEN);
            builder.append(anUniqueList);
            builder.append(Constants.SYMBOL_TEN);
            builder.append(Constants.SYMBOL_BRACKET_CLOSE);
        }
        builder.append(Constants.SYMBOL_BRACKET_CLOSE);

        if (!connectionHelper.isEmpty(createTableBean.getEngine())) {
            builder.append(Constants.SPACE);
            builder.append("ENGINE =");
            builder.append(Constants.SPACE);
            builder.append(createTableBean.getEngine());
        }
        builder.append(Constants.SPACE);
        builder.append("COMMENT");
        builder.append(Constants.SPACE);
        builder.append(Constants.SYMBOL_QUOTE);
        if (!connectionHelper.isEmpty(createTableBean.getComment())) {
            builder.append(createTableBean.getComment());
        }
        builder.append(Constants.SYMBOL_QUOTE);

        if (!connectionHelper.isEmpty(createTableBean.getPartition())) {
            builder.append(Constants.SPACE);
            builder.append("PARTITION BY");
            builder.append(Constants.SPACE);
            builder.append(createTableBean.getPartition());
            builder.append(Constants.SYMBOL_BRACKET_OPEN);
            builder.append(createTableBean.getPartition_val());
            builder.append(Constants.SYMBOL_BRACKET_CLOSE);
            builder.append(Constants.SPACE);
            builder.append("PARTITIONS ");
            builder.append(createTableBean.getPartitions());
        }
        return this._createExecuteAndReturn(createTableBean.getAction(), createTableBean.getRequest_db(), builder.toString());
    }

    /**
     * @param name     table
     * @param database database
     * @return boolean
     * @throws SQLException e
     */
    public boolean isTableExisted(String name, String database) throws SQLException {
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            apiConnection = connectionHelper.getConnection(database);
            statement = apiConnection.getStmtSelect("SHOW TABLES LIKE ?");
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } finally {
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
        return false;
    }

    /**
     * @param createViewBean {@link CreateViewBean}
     * @return query
     * @throws SQLException e
     */
    public String createView(CreateViewBean createViewBean) throws SQLException {
        System.out.println(createViewBean.getCreate_type());
        StringBuilder builder = new StringBuilder();
        builder.append(createViewBean.getCreate_type());
        builder.append(Constants.SPACE);
        if (!connectionHelper.isEmpty(createViewBean.getAlgorithm())) {
            builder.append("ALGORITHM = ");
            builder.append(createViewBean.getAlgorithm());
            builder.append(Constants.SPACE);
        }
        if (!connectionHelper.isEmpty(createViewBean.getDefiner())) {
            if (Constants.CURRENT_USER.equals(createViewBean.getDefiner())) {
                builder.append("DEFINER = ");
                builder.append(createViewBean.getDefiner());
                builder.append(Constants.SPACE);
            } else if (!connectionHelper.isEmpty(createViewBean.getDefiner_name())) {
                String[] temp = createViewBean.getDefiner_name().split(Constants.SYMBOL_AT);
                builder.append("DEFINER = ");
                if (temp.length < 2) {
                    builder.append(Constants.SYMBOL_TEN);
                    builder.append(temp[0]);
                    builder.append(Constants.SYMBOL_TEN);
                    builder.append(Constants.SPACE);
                } else {
                    builder.append(Constants.SYMBOL_TEN);
                    builder.append(temp[0]);
                    builder.append(Constants.SYMBOL_TEN);
                    builder.append(Constants.SYMBOL_AT);
                    builder.append(Constants.SYMBOL_TEN);
                    builder.append(temp[1]);
                    if (!temp[1].endsWith(Constants.SYMBOL_TEN)) {
                        builder.append(Constants.SYMBOL_TEN);
                    }
                    builder.append(Constants.SPACE);
                }
            }
        }
        if (!connectionHelper.isEmpty(createViewBean.getSql_security())) {
            builder.append("SQL SECURITY ");
            builder.append(createViewBean.getSql_security());
            builder.append(Constants.SPACE);
        }
        builder.append("VIEW ");
        builder.append(Constants.SYMBOL_TEN);
        builder.append(createViewBean.getView_name());
        builder.append(Constants.SYMBOL_TEN);
        builder.append(Constants.SPACE);
        if (createViewBean.getColumn_list() != null) {
            StringBuilder columns = new StringBuilder(Constants.SYMBOL_BRACKET_OPEN);
            boolean entered = false;
            for (int i = 0; i < createViewBean.getColumn_list().length; i++) {
                if (!connectionHelper.isEmpty(createViewBean.getColumn_list()[i])) {
                    if (entered) {
                        columns.append(Constants.SYMBOL_COMMA);
                    }
                    entered = true;
                    columns.append(Constants.SYMBOL_TEN);
                    columns.append(createViewBean.getColumn_list()[i]);
                    columns.append(Constants.SYMBOL_TEN);
                }
            }
            columns.append(Constants.SYMBOL_BRACKET_CLOSE);
            if (entered) {
                builder.append(columns);
                builder.append(Constants.SPACE);
            }
        }
        builder.append("AS ");
        builder.append(createViewBean.getDefinition());
        if (!connectionHelper.isEmpty(createViewBean.getCheck())) {
            builder.append(Constants.SPACE);
            builder.append("WITH ");
            builder.append(createViewBean.getCheck());
            builder.append(" CHECK OPTION");
        }
        return this._createExecuteAndReturn(createViewBean.getAction(), createViewBean.getRequest_db(), builder.toString());
    }

    /**
     * @param action   {@link String}
     * @param database {@link String}
     * @param query    {@link String}
     * @return String or null
     */
    private String _createExecuteAndReturn(String action, String database, String query) throws SQLException {
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        try {
            if (Constants.YES.equalsIgnoreCase(action)) {
                apiConnection = connectionHelper.getConnection(database);
                statement = apiConnection.getStmt(query);
                statement.execute();
                apiConnection.commit();
            } else {
                return query;
            }
        } finally {
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
        return null;
    }

    /**
     * @param items   String[]
     * @param builder {@link StringBuilder}
     */
    private void _appendList(String[] items, StringBuilder builder) {
        for (int i = 0; i < items.length; i++) {
            builder.append(Constants.SYMBOL_TEN);
            builder.append(items[i]);
            builder.append(Constants.SYMBOL_TEN);
            if (items.length != i + 1) {
                builder.append(Constants.SYMBOL_COMMA);
            }
        }
    }

    /**
     * @param value {@link String}
     * @return boolean
     */
    public boolean isEmpty(String value) {
        return connectionHelper.isEmpty(value);
    }
}