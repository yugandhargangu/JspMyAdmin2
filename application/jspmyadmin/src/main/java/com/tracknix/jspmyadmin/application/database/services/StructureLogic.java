package com.tracknix.jspmyadmin.application.database.services;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tracknix.jspmyadmin.application.database.beans.structure.*;
import com.tracknix.jspmyadmin.framework.connection.ApiConnection;
import com.tracknix.jspmyadmin.framework.connection.ConnectionHelper;
import com.tracknix.jspmyadmin.framework.connection.util.DataTypes;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.annotations.Detect;
import com.tracknix.jspmyadmin.framework.web.annotations.LogicService;
import com.tracknix.jspmyadmin.framework.web.utils.QueryHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Yugandhar Gangu
 */
@LogicService
public class StructureLogic extends AbstractBaseLogic {

    @Detect
    private ConnectionHelper connectionHelper;
    @Detect
    private QueryHelper queryHelper;

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
            String query;
            if (!connectionHelper.isEmpty(tableListBean.getName()) && !connectionHelper.isEmpty(tableListBean.getType())) {
                query = queryHelper.getPart(1, 3, tableListBean.getName(), tableListBean.getType());
            } else if (!connectionHelper.isEmpty(tableListBean.getName())) {
                query = queryHelper.getPart(1, 2, tableListBean.getName());
            } else {
                query = queryHelper.getPart(1, 1);
            }
            statement = apiConnection.getStmtSelect(query);
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
                for (String table : structureBean.getTables()) {
                    String query;
                    if (onlyTables) {
                        query = queryHelper.getQuery(4, table);
                    } else {
                        query = queryHelper.getQuery(5, table);
                    }
                    statement = apiConnection.getStmt(query);
                    statement.execute();
                    connectionHelper.close(statement);
                }
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
                for (String table : structureBean.getTables()) {
                    statement = apiConnection.getStmt(queryHelper.getQuery(6, table));
                    statement.execute();
                    connectionHelper.close(statement);
                }
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
                for (String table : structureBean.getTables()) {
                    String query;
                    if (onlyTables) {
                        query = queryHelper.getQuery(7, table);
                    } else {
                        query = queryHelper.getQuery(8, table);
                    }
                    statement = apiConnection.getStmtSelect(query);
                    resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        objectNode.put(resultSet.getString(1), resultSet.getString(2) + Constants.SYMBOL_SEMI_COLON);
                    }
                    connectionHelper.close(resultSet);
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
                if (Constants.YES.equalsIgnoreCase(structureBean.getDrop_checks())) {
                    // drop tables
                    statement = apiConnection.getStmt(queryHelper.getQuery(9, structureBean.getDatabase_name()));
                    statement.execute();
                    connectionHelper.close(statement);

                    for (String table : structureBean.getTables()) {
                        statement = apiConnection.getStmt(queryHelper.getQuery(4, table));
                        statement.execute();
                        connectionHelper.close(statement);
                    }
                }
                for (int i = 0; i < structureBean.getTables().length; i++) {
                    // get table create statement
                    String query = queryHelper.getQuery(10, structureBean.getDatabase_name(),
                            structureBean.getTables()[i], structureBean.getRequest_db(), structureBean.getTables()[i]);
                    statement = apiConnection.getStmt(query);
                    statement.execute();
                    connectionHelper.close(statement);
                    // insert data
                    if (Constants.DATA.equalsIgnoreCase(structureBean.getType())) {
                        query = queryHelper.getQuery(11, structureBean.getDatabase_name(),
                                structureBean.getTables()[i], structureBean.getRequest_db(), structureBean.getTables()[i]);
                        statement = apiConnection.getStmt(query);
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

                List<String> newTableList = new ArrayList<String>();
                List<String> tableList = new ArrayList<String>();
                for (int i = 0; i < structureBean.getTables().length; i++) {
                    tableList.add(structureBean.getTables()[i]);
                    newTableList.add(structureBean.getPrefix() + structureBean.getTables()[i]);
                    this._runPrefixSuffix(apiConnection, tableList.get(i), newTableList.get(i));
                }
                this._checkForeignKeys(apiConnection, tableList, newTableList, null, structureBean.getRequest_db());
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
                List<String> newTableList = new ArrayList<String>();
                List<String> tableList = new ArrayList<String>();
                for (int i = 0; i < structureBean.getTables().length; i++) {
                    tableList.add(structureBean.getTables()[i]);
                    this._runPrefixSuffix(apiConnection, tableList.get(i), newTableList.get(i));
                }
                this._checkForeignKeys(apiConnection, tableList, newTableList, null, structureBean.getRequest_db());
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
                List<String> newTableList = new ArrayList<String>();
                List<String> tableList = new ArrayList<String>();
                for (int i = 0; i < structureBean.getTables().length; i++) {
                    if (structureBean.getTables()[i].indexOf(structureBean.getPrefix()) == 0) {
                        tableList.add(structureBean.getTables()[i]);
                        newTableList.add(structureBean.getNew_prefix() + structureBean.getTables()[i].substring(length));
                        this._runPrefixSuffix(apiConnection, tableList.get(i), newTableList.get(i));
                    }
                }
                this._checkForeignKeys(apiConnection, tableList, newTableList, null, structureBean.getRequest_db());
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
                for (int i = 0; i < structureBean.getTables().length; i++) {
                    if (structureBean.getTables()[i].endsWith(structureBean.getPrefix())) {
                        tableList.add(structureBean.getTables()[i]);
                        newTableList.add(structureBean.getTables()[i].substring(0, structureBean.getTables()[i].length() - length) + structureBean.getNew_prefix());
                        this._runPrefixSuffix(apiConnection, tableList.get(i), newTableList.get(i));
                    }
                }
                this._checkForeignKeys(apiConnection, tableList, newTableList, null, structureBean.getRequest_db());
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
                for (int i = 0; i < structureBean.getTables().length; i++) {
                    if (structureBean.getTables()[i].indexOf(structureBean.getPrefix()) == 0) {
                        tableList.add(structureBean.getTables()[i]);
                        newTableList.add(structureBean.getTables()[i].substring(length));
                        this._runPrefixSuffix(apiConnection, tableList.get(i), newTableList.get(i));
                    }
                }
                this._checkForeignKeys(apiConnection, tableList, newTableList, null, structureBean.getRequest_db());
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
                for (int i = 0; i < structureBean.getTables().length; i++) {
                    if (structureBean.getTables()[i].endsWith(structureBean.getPrefix())) {
                        tableList.add(structureBean.getTables()[i]);
                        newTableList.add(structureBean.getTables()[i].substring(0, structureBean.getTables()[i].length() - length));
                        this._runPrefixSuffix(apiConnection, tableList.get(i), newTableList.get(i));
                    }
                }
                this._checkForeignKeys(apiConnection, tableList, newTableList, null, structureBean.getRequest_db());
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
            if (structureBean.getTables() == null) {
                return;
            }
            apiConnection = connectionHelper.getConnection(structureBean.getRequest_db());
            connectionHelper.setForeignKeyChecks(apiConnection, false);

            for (String table : structureBean.getTables()) {
                if (this._getTableType(apiConnection, table) > 0) {
                    String newTable = table + "-duplicate";
                    boolean isExisted = false;
                    statement = apiConnection.getStmtSelect(queryHelper.getQuery(13));
                    statement.setString(1, newTable);
                    resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        isExisted = true;
                    }
                    connectionHelper.close(resultSet);
                    connectionHelper.close(statement);
                    if (!isExisted) {
                        statement = apiConnection.getStmt(queryHelper.getQuery(14, newTable, table));
                        statement.execute();
                        connectionHelper.close(statement);
                        statement = apiConnection.getStmt(queryHelper.getQuery(6, newTable));
                        statement.execute();
                        connectionHelper.close(statement);

                        if (Constants.YES.equalsIgnoreCase(structureBean.getEnable_checks())) {
                            statement = apiConnection.getStmtSelect(queryHelper.getQuery(15));
                            statement.setString(1, structureBean.getRequest_db());
                            statement.setString(2, table);
                            resultSet = statement.executeQuery();
                            while (resultSet.next()) {
                                String query = queryHelper.getQuery(16, structureBean.getRequest_db(),
                                        newTable, resultSet.getString(1), structureBean.getRequest_db(),
                                        resultSet.getString(2), resultSet.getString(3));
                                innerStatement = apiConnection.getStmt(query);
                                innerStatement.execute();
                                connectionHelper.close(innerStatement);
                            }
                            connectionHelper.close(resultSet);
                            connectionHelper.close(statement);
                        }
                        if (Constants.YES.equalsIgnoreCase(structureBean.getDrop_checks())) {
                            statement = apiConnection.getStmt(queryHelper.getQuery(17, newTable, table));
                            statement.execute();
                            connectionHelper.close(statement);
                        }
                    }
                }
            }
            connectionHelper.setForeignKeyChecks(apiConnection, true);
            apiConnection.commit();
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
            statement = apiConnection.getStmtSelect(queryHelper.getQuery(18));
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
     * @param apiConnection {@link ApiConnection}
     * @param originalName  {@link String}
     * @param newName       {@link String}
     * @throws SQLException e
     */
    private void _runPrefixSuffix(ApiConnection apiConnection, String originalName, String newName) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = apiConnection.getStmt(queryHelper.getQuery(12, originalName, newName));
            statement.execute();
        } finally {
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
            if (newTableList == null || newTableList.isEmpty()) {
                return;
            }
            for (String current : newTableList) {
                statement = apiConnection.getStmtSelect(queryHelper.getQuery(19));
                if (oldDatabase != null) {
                    statement.setString(1, oldDatabase);
                } else {
                    statement.setString(1, newDatabase);
                }
                statement.setString(2, current);
                resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String table = resultSet.getString(2);
                    if (tableList.contains(table)) {
                        if (oldDatabase == null) {
                            innerStatement = apiConnection.getStmt(queryHelper.getQuery(20, current,
                                    resultSet.getString(1)));
                            connectionHelper.close(innerStatement);
                        }
                        innerStatement = apiConnection.getStmt(queryHelper.getQuery(16, newDatabase, current,
                                resultSet.getString(4), newDatabase, resultSet.getString(2),
                                resultSet.getString(3)));
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
            statement = apiConnection.getStmtSelect(queryHelper.getQuery(21));
            String[] params = queryHelper.getColumns(21);
            statement.setString(1, params[0]);
            statement.setString(2, params[1]);
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
        // create columns
        StringBuilder builder = new StringBuilder();
        boolean alreadyEntered = false;
        for (int i = 0; i < createTableBean.getColumns().length; i++) {
            if (!connectionHelper.isEmpty(createTableBean.getColumns()[i])) {
                if (alreadyEntered) {
                    builder.append(Constants.SYMBOL_COMMA);
                }
                if (!alreadyEntered) {
                    alreadyEntered = true;
                }

                DataTypes.DataTypeInfo dataTypeInfo = DataTypes.getInstance().findDataTypeInfo(createTableBean.getDatatypes()[i]);
                if (dataTypeInfo != null) {
                    builder.append(queryHelper.getPart(22, 10, createTableBean.getColumns()[i], dataTypeInfo.getDatatype()));
                }
                if (!connectionHelper.isEmpty(createTableBean.getLengths()[i])) {
                    builder.append(queryHelper.getPart(22, 11, createTableBean.getLengths()[i]));
                }
                if (Constants.ONE.equals(createTableBean.getZfs()[i])) {
                    builder.append(queryHelper.getPart(22, 12));
                }
                if (Constants.ONE.equals(createTableBean.getUns()[i])) {
                    builder.append(queryHelper.getPart(22, 13));
                }
                if (Constants.ONE.equals(createTableBean.getBins()[i])) {
                    builder.append(queryHelper.getPart(22, 14));
                }
                if (!connectionHelper.isEmpty(createTableBean.getCollations()[i])) {
                    builder.append(queryHelper.getPart(22, 3, createTableBean.getCollations()[i]));
                }
                if (Constants.ONE.equals(createTableBean.getNns()[i])) {
                    builder.append(queryHelper.getPart(22, 15));
                } else {
                    builder.append(queryHelper.getPart(22, 16));
                }
                if (!connectionHelper.isEmpty(createTableBean.getDefaults()[i])) {
                    if (Constants.CURRENT_TIMESTAMP.equals(createTableBean.getDefaults()[i])) {
                        builder.append(queryHelper.getPart(22, 4));
                    } else {
                        builder.append(queryHelper.getPart(22, 5, createTableBean.getDefaults()[i]));
                    }
                }
                if (Constants.ONE.equals(createTableBean.getAis()[i])) {
                    builder.append(queryHelper.getPart(22, 17));
                }
                String comment;
                if (!connectionHelper.isEmpty(createTableBean.getComments()[i])) {
                    comment = createTableBean.getComments()[i];
                } else {
                    comment = Constants.BLANK;
                }
                builder.append(queryHelper.getPart(22, 18, comment));
                if (Constants.ONE.equals(createTableBean.getPks()[i])) {
                    primary_key = createTableBean.getColumns()[i];
                }
                if (Constants.ONE.equals(createTableBean.getUqs()[i])) {
                    uniqueList.add(createTableBean.getColumns()[i]);
                }
            }
        }
        String columns = builder.toString();

        String primaryKeyPart;
        if (primary_key != null) {
            primaryKeyPart = queryHelper.getPart(22, 6, primary_key);
        } else {
            primaryKeyPart = Constants.BLANK;
        }

        builder = new StringBuilder();
        for (String anUniqueList : uniqueList) {
            builder.append(queryHelper.getPart(22, 7, anUniqueList));
        }
        String uniquePart = builder.toString();

        String enginePart;
        if (!connectionHelper.isEmpty(createTableBean.getEngine())) {
            enginePart = queryHelper.getPart(22, 8, createTableBean.getEngine());
        } else {
            enginePart = Constants.BLANK;
        }

        String partitionPart;
        if (!connectionHelper.isEmpty(createTableBean.getPartition())) {
            partitionPart = queryHelper.getPart(22, 9, createTableBean.getPartition(),
                    createTableBean.getPartition_val(), createTableBean.getPartitions());
        } else {
            partitionPart = Constants.BLANK;
        }
        String query = queryHelper.getPart(22, 1, createTableBean.getTable_name(), columns,
                primaryKeyPart, uniquePart, enginePart, createTableBean.getComment(), partitionPart);
        return super._createExecuteAOrReturn(connectionHelper, createTableBean.getAction(), createTableBean.getRequest_db(), query);
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
            statement = apiConnection.getStmtSelect(queryHelper.getQuery(13));
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

        String algorithm;
        if (!connectionHelper.isEmpty(createViewBean.getAlgorithm())) {
            algorithm = queryHelper.getPart(23, 3, createViewBean.getAlgorithm());
        } else {
            algorithm = Constants.BLANK;
        }
        String definer;
        if (!connectionHelper.isEmpty(createViewBean.getDefiner())) {
            if (Constants.CURRENT_USER.equals(createViewBean.getDefiner())) {
                definer = queryHelper.getPart(23, 4);
            } else if (!connectionHelper.isEmpty(createViewBean.getDefiner_name())) {
                String[] temp = createViewBean.getDefiner_name().split(Constants.SYMBOL_AT);
                String user;
                if (temp.length < 2) {
                    user = queryHelper.getPart(23, 6, temp[0]);
                } else {
                    user = queryHelper.getPart(23, 7, temp[0], temp[1]);
                }
                definer = queryHelper.getPart(23, 5, user);
            } else {
                definer = Constants.BLANK;
            }
        } else {
            definer = Constants.BLANK;
        }
        String sql_security;
        if (!connectionHelper.isEmpty(createViewBean.getSql_security())) {
            sql_security = queryHelper.getPart(23, 8, createViewBean.getSql_security());
        } else {
            sql_security = Constants.BLANK;
        }

        String columnPart = null;
        if (createViewBean.getColumn_list() != null) {
            StringBuilder columns = new StringBuilder();
            boolean entered = false;
            for (int i = 0; i < createViewBean.getColumn_list().length; i++) {
                if (!connectionHelper.isEmpty(createViewBean.getColumn_list()[i])) {
                    if (entered) {
                        columns.append(Constants.SYMBOL_COMMA);
                    }
                    entered = true;
                    columns.append(queryHelper.getPart(23, 6, createViewBean.getColumn_list()[i]));
                }
            }
            if (entered) {
                columnPart = columns.toString();
            }
        }
        String check_option;
        if (!connectionHelper.isEmpty(createViewBean.getCheck())) {
            check_option = queryHelper.getPart(23, 9, createViewBean.getCheck());
        } else {
            check_option = Constants.BLANK;
        }
        String query;
        if (columnPart != null) {
            query = queryHelper.getPart(23, 2, createViewBean.getCreate_type(), algorithm,
                    definer, sql_security, createViewBean.getView_name(), columnPart, createViewBean.getDefinition(), check_option);
        } else {
            query = queryHelper.getPart(23, 1, createViewBean.getCreate_type(), algorithm,
                    definer, sql_security, createViewBean.getView_name(), createViewBean.getDefinition(), check_option);
        }
        return this._createExecuteAOrReturn(connectionHelper, createViewBean.getAction(), createViewBean.getRequest_db(), query);
    }


    /**
     * @param value {@link String}
     * @return boolean
     */
    public boolean isEmpty(String value) {
        return connectionHelper.isEmpty(value);
    }
}