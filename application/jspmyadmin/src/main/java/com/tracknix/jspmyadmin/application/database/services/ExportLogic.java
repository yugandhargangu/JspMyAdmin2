package com.tracknix.jspmyadmin.application.database.services;

import com.tracknix.jspmyadmin.application.database.beans.export.ExportBean;
import com.tracknix.jspmyadmin.framework.connection.ApiConnection;
import com.tracknix.jspmyadmin.framework.connection.ConnectionHelper;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.annotations.Detect;
import com.tracknix.jspmyadmin.framework.web.annotations.LogicService;
import com.tracknix.jspmyadmin.framework.web.utils.Bean;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author Yugandhar Gangu
 */
@LogicService
public class ExportLogic {

    @Detect
    private ConnectionHelper connectionHelper;

    /**
     * @param bean
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void fillBean(Bean bean) throws SQLException {
        ExportBean exportBean = (ExportBean) bean;

        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            apiConnection = connectionHelper.getConnection(bean.getRequest_db());
            exportBean.setFilename(bean.getRequest_db());
            statement = apiConnection.getStmtSelect("SHOW FULL TABLES WHERE table_type = ?");
            statement.setString(1, Constants.BASE_TABLE);
            resultSet = statement.executeQuery();
            List<String> tableList = new ArrayList<String>();
            while (resultSet.next()) {
                tableList.add(resultSet.getString(1));
            }
            exportBean.setTable_list(tableList);
        } finally {
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
    }

    /**
     * @param bean
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    public File exportFile(Bean bean) throws SQLException, IOException, ClassNotFoundException {
        ExportBean exportBean = (ExportBean) bean;

        File file = null;
        FileOutputStream fileOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;

        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        PreparedStatement hexStatement = null;
        ResultSet hexResultSet = null;
        try {
            apiConnection = connectionHelper.getConnection(bean.getRequest_db());
            statement = apiConnection.getStmt("SET foreign_key_checks = ?");
            statement.setInt(1, 0);
            statement.execute();
            connectionHelper.close(statement);

            if (connectionHelper.isEmpty(exportBean.getFilename())) {
                exportBean.setFilename(bean.getRequest_db());
            }

            file = new File(connectionHelper.getTempFilePath());
            file.setExecutable(true, false);
            file.setReadable(true, false);
            file.setWritable(true, false);
            fileOutputStream = new FileOutputStream(file);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            bufferedWriter = new BufferedWriter(outputStreamWriter);

            if (Constants.ONE.equals(exportBean.getDisable_fks())) {
                bufferedWriter.write(Constants.ONE_LINE_COMMENT);
                bufferedWriter.write(Constants.NEW_LINE);
                bufferedWriter.write(Constants.NEW_LINE);
                bufferedWriter.write("SET foreign_key_checks = 0");
                bufferedWriter.write(Constants.ONE_LINE_COMMENT);
                bufferedWriter.write(Constants.NEW_LINE);
                bufferedWriter.write(Constants.ONE_LINE_SEPARATOR);
                bufferedWriter.write(Constants.NEW_LINE);
                bufferedWriter.write(Constants.NEW_LINE);
            }

            StringBuilder builder = new StringBuilder();
            String database = bean.getRequest_db();
            bufferedWriter.write(Constants.ONE_LINE_SEPARATOR);
            bufferedWriter.write(Constants.NEW_LINE);
            bufferedWriter.write(Constants.ONE_LINE_COMMENT);
            bufferedWriter.write(Constants.NEW_LINE);
            bufferedWriter.write(Constants.ONE_LINE_COMMENT);
            bufferedWriter.write(" DATABASE: `");
            bufferedWriter.write(database);
            bufferedWriter.write(Constants.SYMBOL_TEN);
            bufferedWriter.write(Constants.NEW_LINE);
            bufferedWriter.write(Constants.ONE_LINE_COMMENT);
            bufferedWriter.write(Constants.NEW_LINE);
            bufferedWriter.write(Constants.NEW_LINE);
            if (Constants.ONE.equals(exportBean.getAdd_drop_db())) {
                bufferedWriter.write(Constants.NEW_LINE);
                bufferedWriter.write("DROP DATABASE IF EXISTS `");
                bufferedWriter.write(database);
                bufferedWriter.write(Constants.SYMBOL_TEN);
                bufferedWriter.write(Constants.SYMBOL_SEMI_COLON);
                bufferedWriter.write(Constants.NEW_LINE);
            }

            builder.delete(0, builder.length());
            builder.append("SELECT default_character_set_name, default_collation_name ");
            builder.append("FROM information_schema.schemata WHERE schema_name = ?");
            statement = apiConnection.getStmtSelect(builder.toString());
            statement.setString(1, database);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                bufferedWriter.write("CREATE DATABASE IF NOT EXISTS `");
                bufferedWriter.write(database);
                bufferedWriter.write("` CHARACTER SET ");
                bufferedWriter.write(resultSet.getString(1));
                bufferedWriter.write(" COLLATE ");
                bufferedWriter.write(resultSet.getString(2));
                bufferedWriter.write(Constants.SYMBOL_SEMI_COLON);
                bufferedWriter.write(Constants.NEW_LINE);
                bufferedWriter.write("USE `");
                bufferedWriter.write(database);
                bufferedWriter.write(Constants.SYMBOL_TEN);
                bufferedWriter.write(Constants.SYMBOL_SEMI_COLON);
                bufferedWriter.write(Constants.NEW_LINE);
                bufferedWriter.write(Constants.NEW_LINE);
            }
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);

            // dump tables
            List<String> tableList = Arrays.asList(exportBean.getTables());
            Map<String, String> constraintList = new HashMap<String, String>();
            Map<String, List<String>> constraintMap = new HashMap<String, List<String>>();

            builder.delete(0, builder.length());
            builder.append("SELECT table_name,constraint_name FROM ");
            builder.append("information_schema.key_column_usage WHERE ");
            builder.append("referenced_table_name IS NOT NULL AND constraint_schema = ? ");
            builder.append("AND table_name IN (");
            for (int i = 0; i < tableList.size(); i++) {
                if (i > 0) {
                    builder.append(Constants.SYMBOL_COMMA);
                }
                builder.append("?");
            }
            builder.append(")");

            statement = apiConnection.getStmtSelect(builder.toString());
            statement.setString(1, database);
            for (int i = 0; i < tableList.size(); i++) {
                statement.setString(i + 2, tableList.get(i));
            }
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String table = resultSet.getString(1);
                if (constraintMap.containsKey(table)) {
                    List<String> keyList = constraintMap.get(table);
                    keyList.add(resultSet.getString(2));
                } else {
                    List<String> keyList = new ArrayList<String>();
                    keyList.add(resultSet.getString(2));
                    constraintMap.put(table, keyList);
                }
            }
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);

            for (int j = 0; j < tableList.size(); j++) {
                String table = tableList.get(j);
                bufferedWriter.write(Constants.NEW_LINE);
                bufferedWriter.write(Constants.ONE_LINE_COMMENT);
                bufferedWriter.write(Constants.NEW_LINE);
                bufferedWriter.write(Constants.ONE_LINE_COMMENT);
                bufferedWriter.write(" TABLE: `");
                bufferedWriter.write(table);
                bufferedWriter.write(Constants.SYMBOL_TEN);
                bufferedWriter.write(Constants.NEW_LINE);
                bufferedWriter.write(Constants.ONE_LINE_COMMENT);
                bufferedWriter.write(Constants.NEW_LINE);

                if (Constants.ONE.equals(exportBean.getAdd_drop_table())) {
                    bufferedWriter.write("DROP TABLE IF EXISTS `");
                    bufferedWriter.write(table);
                    bufferedWriter.write(Constants.SYMBOL_TEN);
                    bufferedWriter.write(Constants.SYMBOL_SEMI_COLON);
                    bufferedWriter.write(Constants.NEW_LINE);
                }

                statement = apiConnection.getStmtSelect("SHOW CREATE TABLE `" + table + Constants.SYMBOL_TEN);
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    String createStmt = resultSet.getString(2);
                    if (constraintMap.containsKey(table)) {
                        builder.delete(0, builder.length());
                        List<String> keyList = constraintMap.get(table);
                        Iterator<String> keyIterator = keyList.iterator();
                        int index = -1;
                        while (keyIterator.hasNext()) {
                            String key = keyIterator.next();
                            String check = "CONSTRAINT `" + key + Constants.SYMBOL_TEN;
                            int k = createStmt.indexOf(check);
                            if (index == -1 || (index > k)) {
                                index = k;
                            }
                        }
                        char ch = createStmt.charAt(--index);
                        while (',' != ch) {
                            builder.append(ch);
                            ch = createStmt.charAt(--index);
                        }
                        builder.reverse();
                        String constraintStmt = createStmt.substring(index, createStmt.indexOf(") ENGINE="));
                        createStmt = createStmt.replace(constraintStmt, builder.toString());
                        constraintList.put(table, constraintStmt.substring(1));
                    }
                    bufferedWriter.write(createStmt);
                }
                bufferedWriter.write(Constants.SYMBOL_SEMI_COLON);
                connectionHelper.close(resultSet);
                connectionHelper.close(statement);

                // export data
                boolean exportData = false;
                if (exportBean.getTables_data() != null) {
                    for (int k = 0; k < exportBean.getTables_data().length; k++) {
                        if (table.equalsIgnoreCase(exportBean.getTables_data()[k])) {
                            exportData = true;
                            break;
                        }
                    }
                }

                if (exportData) {
                    bufferedWriter.write(Constants.NEW_LINE);
                    bufferedWriter.write(Constants.ONE_LINE_COMMENT);
                    bufferedWriter.write(Constants.NEW_LINE);
                    bufferedWriter.write(Constants.ONE_LINE_COMMENT);
                    bufferedWriter.write(" TABLE DATA: `");
                    bufferedWriter.write(table);
                    bufferedWriter.write(Constants.SYMBOL_TEN);
                    bufferedWriter.write(Constants.NEW_LINE);
                    bufferedWriter.write(Constants.ONE_LINE_COMMENT);
                    bufferedWriter.write(Constants.NEW_LINE);

                    statement = apiConnection.getStmtSelect("SELECT * FROM `" + table + Constants.SYMBOL_TEN);
                    resultSet = statement.executeQuery();
                    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

                    // 0 - string, 1 - number, 2 - binary
                    Map<Integer, Integer> columnMap = new HashMap<Integer, Integer>();
                    int index = 0;
                    while (resultSet.next()) {
                        if (index == 0) {
                            bufferedWriter.write("INSERT INTO `");
                            bufferedWriter.write(table);
                            bufferedWriter.write(Constants.SYMBOL_TEN);
                            bufferedWriter.write(Constants.SYMBOL_BRACKET_OPEN);
                            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                                if (i > 1) {
                                    bufferedWriter.write(Constants.SYMBOL_COMMA);
                                }
                                bufferedWriter.write(Constants.SYMBOL_TEN);
                                bufferedWriter.write(resultSetMetaData.getColumnName(i));
                                bufferedWriter.write(Constants.SYMBOL_TEN);

                                String className = resultSetMetaData.getColumnClassName(i);
                                if (Constants.BYTE_TYPE.equals(className)) {
                                    columnMap.put(i, 2);
                                } else {
                                    Class<?> klass = Class.forName(className);
                                    if (klass == Short.class || klass == Integer.class || klass == Long.class
                                            || klass == Boolean.class || klass == Float.class || klass == Double.class
                                            || klass == BigDecimal.class || klass == BigInteger.class) {
                                        columnMap.put(i, 1);
                                    } else {
                                        columnMap.put(i, 0);
                                    }
                                }
                            }
                            bufferedWriter.write(Constants.SYMBOL_BRACKET_CLOSE);
                            bufferedWriter.write(" VALUES ");
                            bufferedWriter.write(Constants.NEW_LINE);
                        } else {
                            bufferedWriter.write(Constants.SYMBOL_COMMA);
                            bufferedWriter.write(Constants.NEW_LINE);
                        }

                        bufferedWriter.write(Constants.SYMBOL_BRACKET_OPEN);
                        for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                            if (i > 1) {
                                bufferedWriter.write(Constants.SYMBOL_COMMA);
                            }
                            switch (columnMap.get(i)) {
                                case 1:
                                    String value = resultSet.getString(i);
                                    if (value != null) {
                                        bufferedWriter.write(value);
                                    } else {
                                        bufferedWriter.write(Constants.NULL);
                                    }
                                    break;
                                case 2:
                                    byte[] byteValue = resultSet.getBytes(i);
                                    if (byteValue != null) {
                                        hexStatement = apiConnection.getStmtSelect("SELECT HEX(?) FROM DUAL");
                                        hexStatement.setBytes(1, byteValue);
                                        hexResultSet = hexStatement.executeQuery();
                                        value = Constants.NULL;
                                        if (hexResultSet.next()) {
                                            value = hexResultSet.getString(1);
                                        }
                                        connectionHelper.close(hexResultSet);
                                        connectionHelper.close(hexStatement);
                                        bufferedWriter.write("UNHEX(");
                                        bufferedWriter.write(Constants.SYMBOL_QUOTE);
                                        bufferedWriter.write(value);
                                        bufferedWriter.write(Constants.SYMBOL_QUOTE);
                                        bufferedWriter.write(Constants.SYMBOL_BRACKET_CLOSE);
                                    } else {
                                        bufferedWriter.write(Constants.NULL);
                                    }
                                    break;
                                default:
                                    value = resultSet.getString(i);
                                    if (value != null) {
                                        value = value.replaceAll(Constants.SYMBOL_QUOTE, Constants.SYMBOL_QUOTE_ESCAPE);
                                        bufferedWriter.write(Constants.SYMBOL_QUOTE);
                                        bufferedWriter.write(value);
                                        bufferedWriter.write(Constants.SYMBOL_QUOTE);
                                    } else {
                                        bufferedWriter.write(Constants.NULL);
                                    }
                                    break;
                            }
                        }
                        bufferedWriter.write(Constants.SYMBOL_BRACKET_CLOSE);
                        index++;
                    }
                    if (index > 0) {
                        bufferedWriter.write(Constants.SYMBOL_SEMI_COLON);
                    }
                    connectionHelper.close(resultSet);
                    connectionHelper.close(statement);
                }
                bufferedWriter.write(Constants.NEW_LINE);
                bufferedWriter.write(Constants.ONE_LINE_SEPARATOR);
                bufferedWriter.write(Constants.NEW_LINE);
            }

            if (constraintList.size() > 0) {
                bufferedWriter.write(Constants.NEW_LINE);
                bufferedWriter.write(Constants.NEW_LINE);
                bufferedWriter.write(Constants.ONE_LINE_COMMENT);
                bufferedWriter.write(" CONSTRAINT STATEMENTS START");
                bufferedWriter.write(Constants.NEW_LINE);
                for (Entry<String, String> entry : constraintList.entrySet()) {
                    bufferedWriter.write(Constants.NEW_LINE);
                    builder.delete(0, builder.length());
                    builder.append("ALTER TABLE `");
                    builder.append(entry.getKey());
                    builder.append(Constants.SYMBOL_TEN);
                    bufferedWriter.write(builder.toString());
                    String value = entry.getValue().replaceAll("CONSTRAINT", "ADD CONSTRAINT");
                    int index = value.length();
                    char ch = value.charAt(--index);
                    while ('\n' == ch || '\r' == ch) {
                        ch = value.charAt(--index);
                    }
                    bufferedWriter.write(value.substring(0, index + 1));
                    bufferedWriter.write(Constants.SYMBOL_SEMI_COLON);
                    bufferedWriter.write(Constants.NEW_LINE);
                }
                bufferedWriter.write(Constants.ONE_LINE_COMMENT);
                bufferedWriter.write(" CONSTRAINT STATEMENTS END");
                bufferedWriter.write(Constants.NEW_LINE);
                bufferedWriter.write(Constants.NEW_LINE);
            }

            if (Constants.ONE.equals(exportBean.getInclude_views())) {
                // dump views
                List<String> viewList = new ArrayList<String>();
                statement = apiConnection.getStmtSelect("SHOW FULL TABLES WHERE table_type = ?");
                statement.setString(1, Constants.VIEW);
                resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    viewList.add(resultSet.getString(1));
                }
                connectionHelper.close(resultSet);
                connectionHelper.close(statement);

                if (viewList.size() > 0) {
                    Iterator<String> iterator = viewList.iterator();
                    while (iterator.hasNext()) {
                        String view = iterator.next();
                        bufferedWriter.write(Constants.NEW_LINE);
                        bufferedWriter.write(Constants.NEW_LINE);
                        bufferedWriter.write(Constants.ONE_LINE_COMMENT);
                        bufferedWriter.write(Constants.NEW_LINE);
                        bufferedWriter.write(Constants.ONE_LINE_COMMENT);
                        bufferedWriter.write(" VIEW: `");
                        bufferedWriter.write(view);
                        bufferedWriter.write(Constants.SYMBOL_TEN);
                        bufferedWriter.write(Constants.NEW_LINE);
                        bufferedWriter.write(Constants.ONE_LINE_COMMENT);
                        bufferedWriter.write(Constants.NEW_LINE);

                        if (Constants.ONE.equals(exportBean.getAdd_drop_table())) {
                            bufferedWriter.write("DROP VIEW IF EXISTS `");
                            bufferedWriter.write(view);
                            bufferedWriter.write(Constants.SYMBOL_TEN);
                            bufferedWriter.write(Constants.SYMBOL_SEMI_COLON);
                            bufferedWriter.write(Constants.NEW_LINE);
                        }
                        statement = apiConnection.getStmtSelect("SHOW CREATE VIEW `" + view + Constants.SYMBOL_TEN);
                        resultSet = statement.executeQuery();
                        if (resultSet.next()) {
                            bufferedWriter.write(resultSet.getString(2));
                        }
                        bufferedWriter.write(Constants.SYMBOL_SEMI_COLON);
                        connectionHelper.close(resultSet);
                        connectionHelper.close(statement);
                        bufferedWriter.write(Constants.NEW_LINE);
                        bufferedWriter.write(Constants.ONE_LINE_SEPARATOR);
                        bufferedWriter.write(Constants.NEW_LINE);
                    }
                }
            }

            boolean delimeter = false;

            if (Constants.ONE.equals(exportBean.getInclude_procedures())) {
                // dump procedures
                List<String> procedureList = new ArrayList<String>();
                statement = apiConnection.getStmtSelect("SHOW PROCEDURE STATUS WHERE db = ?");
                statement.setString(1, database);
                resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    procedureList.add(resultSet.getString(2));
                }
                connectionHelper.close(resultSet);
                connectionHelper.close(statement);

                if (procedureList.size() > 0) {
                    if (!delimeter) {
                        bufferedWriter.write(Constants.NEW_LINE);
                        bufferedWriter.write(Constants.DELIMITER_$$);
                        bufferedWriter.write(Constants.NEW_LINE);
                        delimeter = true;
                    }

                    Iterator<String> iterator = procedureList.iterator();
                    while (iterator.hasNext()) {
                        String procedure = iterator.next();
                        bufferedWriter.write(Constants.NEW_LINE);
                        bufferedWriter.write(Constants.ONE_LINE_COMMENT);
                        bufferedWriter.write(Constants.NEW_LINE);
                        bufferedWriter.write(Constants.ONE_LINE_COMMENT);
                        bufferedWriter.write(" PROCEDURE: `");
                        bufferedWriter.write(procedure);
                        bufferedWriter.write(Constants.SYMBOL_TEN);
                        bufferedWriter.write(Constants.NEW_LINE);
                        bufferedWriter.write(Constants.ONE_LINE_COMMENT);
                        bufferedWriter.write(Constants.NEW_LINE);

                        if (Constants.ONE.equals(exportBean.getAdd_drop_table())) {
                            bufferedWriter.write("DROP PROCEDURE IF EXISTS `");
                            bufferedWriter.write(procedure);
                            bufferedWriter.write(Constants.SYMBOL_TEN);
                            bufferedWriter.write("$$");
                            bufferedWriter.write(Constants.NEW_LINE);
                        }
                        statement = apiConnection
                                .getStmtSelect("SHOW CREATE PROCEDURE `" + procedure + Constants.SYMBOL_TEN);
                        resultSet = statement.executeQuery();
                        if (resultSet.next()) {
                            bufferedWriter.write(resultSet.getString(3));
                        }
                        bufferedWriter.write(Constants.SYMBOL_$$);
                        connectionHelper.close(resultSet);
                        connectionHelper.close(statement);
                        bufferedWriter.write(Constants.NEW_LINE);
                        bufferedWriter.write(Constants.ONE_LINE_SEPARATOR);
                        bufferedWriter.write(Constants.NEW_LINE);
                        bufferedWriter.write(Constants.NEW_LINE);
                    }
                }
            }

            if (Constants.ONE.equals(exportBean.getInclude_functions())) {
                // dump functions
                List<String> functionList = new ArrayList<String>();
                statement = apiConnection.getStmtSelect("SHOW FUNCTION STATUS WHERE db = ?");
                statement.setString(1, database);
                resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    functionList.add(resultSet.getString(2));
                }
                connectionHelper.close(resultSet);
                connectionHelper.close(statement);

                if (functionList.size() > 0) {
                    if (!delimeter) {
                        bufferedWriter.write(Constants.NEW_LINE);
                        bufferedWriter.write(Constants.DELIMITER_$$);
                        bufferedWriter.write(Constants.NEW_LINE);
                        delimeter = true;
                    }
                    Iterator<String> iterator = functionList.iterator();
                    while (iterator.hasNext()) {
                        String function = iterator.next();
                        bufferedWriter.write(Constants.NEW_LINE);
                        bufferedWriter.write(Constants.ONE_LINE_COMMENT);
                        bufferedWriter.write(Constants.NEW_LINE);
                        bufferedWriter.write(Constants.ONE_LINE_COMMENT);
                        bufferedWriter.write(" FUNCTION: `");
                        bufferedWriter.write(function);
                        bufferedWriter.write(Constants.SYMBOL_TEN);
                        bufferedWriter.write(Constants.NEW_LINE);
                        bufferedWriter.write(Constants.ONE_LINE_COMMENT);
                        bufferedWriter.write(Constants.NEW_LINE);

                        if (Constants.ONE.equals(exportBean.getAdd_drop_table())) {
                            bufferedWriter.write("DROP FUNCTION IF EXISTS `");
                            bufferedWriter.write(function);
                            bufferedWriter.write(Constants.SYMBOL_TEN);
                            bufferedWriter.write(Constants.SYMBOL_$$);
                            bufferedWriter.write(Constants.NEW_LINE);
                        }
                        statement = apiConnection
                                .getStmtSelect("SHOW CREATE FUNCTION `" + function + Constants.SYMBOL_TEN);
                        resultSet = statement.executeQuery();
                        if (resultSet.next()) {
                            bufferedWriter.write(resultSet.getString(3));
                        }
                        bufferedWriter.write(Constants.SYMBOL_$$);
                        connectionHelper.close(resultSet);
                        connectionHelper.close(statement);
                        bufferedWriter.write(Constants.NEW_LINE);
                        bufferedWriter.write(Constants.ONE_LINE_SEPARATOR);
                        bufferedWriter.write(Constants.NEW_LINE);
                        bufferedWriter.write(Constants.NEW_LINE);
                    }
                }
            }

            if (Constants.ONE.equals(exportBean.getInclude_events())) {
                // dump events
                List<String> eventList = new ArrayList<String>();
                statement = apiConnection.getStmtSelect("SHOW EVENTS WHERE db = ?");
                statement.setString(1, database);
                resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    eventList.add(resultSet.getString(2));
                }
                connectionHelper.close(resultSet);
                connectionHelper.close(statement);

                if (eventList.size() > 0) {
                    if (!delimeter) {
                        bufferedWriter.write(Constants.NEW_LINE);
                        bufferedWriter.write(Constants.DELIMITER_$$);
                        bufferedWriter.write(Constants.NEW_LINE);
                        delimeter = true;
                    }

                    Iterator<String> iterator = eventList.iterator();
                    while (iterator.hasNext()) {
                        String event = iterator.next();
                        bufferedWriter.write(Constants.NEW_LINE);
                        bufferedWriter.write(Constants.ONE_LINE_COMMENT);
                        bufferedWriter.write(Constants.NEW_LINE);
                        bufferedWriter.write(Constants.ONE_LINE_COMMENT);
                        bufferedWriter.write(" EVENT: `");
                        bufferedWriter.write(event);
                        bufferedWriter.write(Constants.SYMBOL_TEN);
                        bufferedWriter.write(Constants.NEW_LINE);
                        bufferedWriter.write(Constants.ONE_LINE_COMMENT);
                        bufferedWriter.write(Constants.NEW_LINE);

                        if (Constants.ONE.equals(exportBean.getAdd_drop_table())) {
                            bufferedWriter.write("DROP EVENT IF EXISTS `");
                            bufferedWriter.write(event);
                            bufferedWriter.write(Constants.SYMBOL_TEN);
                            bufferedWriter.write(Constants.SYMBOL_$$);
                            bufferedWriter.write(Constants.NEW_LINE);
                        }
                        statement = apiConnection.getStmtSelect("SHOW CREATE EVENT `" + event + Constants.SYMBOL_TEN);
                        resultSet = statement.executeQuery();
                        if (resultSet.next()) {
                            bufferedWriter.write(resultSet.getString(4));
                        }
                        bufferedWriter.write(Constants.SYMBOL_$$);
                        connectionHelper.close(resultSet);
                        connectionHelper.close(statement);
                        bufferedWriter.write(Constants.NEW_LINE);
                        bufferedWriter.write(Constants.ONE_LINE_SEPARATOR);
                        bufferedWriter.write(Constants.NEW_LINE);
                        bufferedWriter.write(Constants.NEW_LINE);
                    }
                }
            }

            if (Constants.ONE.equals(exportBean.getInclude_triggers())) {
                // dump triggers
                List<String> triggerList = new ArrayList<String>();
                statement = apiConnection.getStmtSelect("SHOW TRIGGERS");
                resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    triggerList.add(resultSet.getString(1));
                }
                connectionHelper.close(resultSet);
                connectionHelper.close(statement);

                if (triggerList.size() > 0) {
                    if (!delimeter) {
                        bufferedWriter.write(Constants.NEW_LINE);
                        bufferedWriter.write(Constants.DELIMITER_$$);
                        bufferedWriter.write(Constants.NEW_LINE);
                        delimeter = true;
                    }

                    Iterator<String> iterator = triggerList.iterator();
                    while (iterator.hasNext()) {
                        String trigger = iterator.next();
                        bufferedWriter.write(Constants.NEW_LINE);
                        bufferedWriter.write(Constants.ONE_LINE_COMMENT);
                        bufferedWriter.write(Constants.NEW_LINE);
                        bufferedWriter.write(Constants.ONE_LINE_COMMENT);
                        bufferedWriter.write(" TRIGGER: `");
                        bufferedWriter.write(trigger);
                        bufferedWriter.write(Constants.SYMBOL_TEN);
                        bufferedWriter.write(Constants.NEW_LINE);
                        bufferedWriter.write(Constants.ONE_LINE_COMMENT);
                        bufferedWriter.write(Constants.NEW_LINE);

                        if (Constants.ONE.equals(exportBean.getAdd_drop_table())) {
                            bufferedWriter.write("DROP TRIGGER IF EXISTS `");
                            bufferedWriter.write(trigger);
                            bufferedWriter.write(Constants.SYMBOL_TEN);
                            bufferedWriter.write(Constants.SYMBOL_$$);
                            bufferedWriter.write(Constants.NEW_LINE);
                        }
                        statement = apiConnection
                                .getStmtSelect("SHOW CREATE TRIGGER `" + trigger + Constants.SYMBOL_TEN);
                        resultSet = statement.executeQuery();
                        if (resultSet.next()) {
                            bufferedWriter.write(resultSet.getString(3));
                        }
                        bufferedWriter.write(Constants.SYMBOL_$$);
                        connectionHelper.close(resultSet);
                        connectionHelper.close(statement);
                        bufferedWriter.write(Constants.NEW_LINE);
                        bufferedWriter.write(Constants.ONE_LINE_SEPARATOR);
                        bufferedWriter.write(Constants.NEW_LINE);
                        bufferedWriter.write(Constants.NEW_LINE);
                    }
                }
            }

            if (delimeter) {
                bufferedWriter.write(Constants.NEW_LINE);
                bufferedWriter.write(Constants.DELIMITER_COMMA);
                bufferedWriter.write(Constants.NEW_LINE);
            }
        } finally {
            connectionHelper.close(hexResultSet);
            connectionHelper.close(hexStatement);
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
            connectionHelper.close(bufferedWriter);
            connectionHelper.close(outputStreamWriter);
            connectionHelper.close(fileOutputStream);
            connectionHelper.close(fileOutputStream);
        }
        return file;
    }

}
