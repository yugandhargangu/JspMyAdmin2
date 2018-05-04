package com.tracknix.jspmyadmin.application.database.services;

import com.tracknix.jspmyadmin.application.database.beans.sql.SqlBean;
import com.tracknix.jspmyadmin.framework.connection.ApiConnection;
import com.tracknix.jspmyadmin.framework.connection.ConnectionHelper;
import com.tracknix.jspmyadmin.framework.connection.QuerySeparator;
import com.tracknix.jspmyadmin.framework.constants.BeanConstants;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.annotations.Detect;
import com.tracknix.jspmyadmin.framework.web.annotations.LogicService;
import com.tracknix.jspmyadmin.framework.web.utils.Bean;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Yugandhar Gangu
 */
@LogicService
public class SqlLogic {

    @Detect
    private ConnectionHelper connectionHelper;

    /**
     * @param bean
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void fillBean(Bean bean, boolean fetch) throws SQLException {

        SqlBean sqlBean = (SqlBean) bean;

        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            apiConnection = connectionHelper.getConnection(bean.getRequest_db());
            statement = apiConnection.getStmtSelect("SHOW TABLES");
            resultSet = statement.executeQuery();
            List<String> tableList = new ArrayList<String>();
            while (resultSet.next()) {
                tableList.add(resultSet.getString(1));
            }
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            Iterator<String> iterator = tableList.iterator();
            StringBuilder mainBuilder = new StringBuilder();
            mainBuilder.append("{");
            mainBuilder.append("tables: {");
            boolean mainEnter = false;
            while (iterator.hasNext()) {
                if (mainEnter) {
                    mainBuilder.append(Constants.SYMBOL_COMMA);
                    mainBuilder.append(Constants.SPACE);
                } else {
                    mainEnter = true;
                }
                String table = iterator.next();
                mainBuilder.append("\"");
                mainBuilder.append(table.replaceAll("\"", "\\\\\""));
                mainBuilder.append("\"");
                mainBuilder.append(": {");
                statement = apiConnection.getStmtSelect("SHOW COLUMNS FROM `" + table + Constants.SYMBOL_TEN);
                resultSet = statement.executeQuery();
                boolean subEnter = false;
                while (resultSet.next()) {
                    if (subEnter) {
                        mainBuilder.append(Constants.SYMBOL_COMMA);
                        mainBuilder.append(Constants.SPACE);
                    } else {
                        subEnter = true;
                    }
                    mainBuilder.append("\"");
                    mainBuilder.append(resultSet.getString(1).replaceAll("\"", "\\\\\""));
                    mainBuilder.append("\"");
                    mainBuilder.append(": null");
                }
                mainBuilder.append("}");
                connectionHelper.close(resultSet);
                connectionHelper.close(statement);
            }
            mainBuilder.append("}}");
            String hintOptions = mainBuilder.toString();
            sqlBean.setHint_options(hintOptions);
            if (fetch) {
                if (!connectionHelper.isEmpty(sqlBean.getQuery())) {
                    if (Constants.ONE.equals(sqlBean.getDisable_fks())) {
                        statement = apiConnection.getStmt("SET foreign_key_checks = ?");
                        statement.setInt(1, 0);
                        statement.execute();
                        connectionHelper.close(statement);
                    }
                    QuerySeparator querySeparator = new QuerySeparator(sqlBean.getQuery());
                    List<String> queries = querySeparator.getQueries();
                    iterator = queries.iterator();
                    boolean alreadyEntered = false;
                    while (iterator.hasNext()) {
                        String query = iterator.next();
                        if (!connectionHelper.isEmpty(query)) {

                            statement = apiConnection.getStmt(query);
                            long start_time = System.nanoTime();
                            boolean result = statement.execute();
                            long end_time = System.nanoTime();
                            long exec_time = end_time - start_time;
                            double final_exec_time = ((double) exec_time) / 1000000000.0;
                            DecimalFormat decimalFormat = new DecimalFormat("0");
                            decimalFormat.setMaximumFractionDigits(6);
                            sqlBean.setExec_time(decimalFormat.format(final_exec_time));
                            if (result) {
                                if (!alreadyEntered) {
                                    resultSet = statement.getResultSet();
                                    ResultSetMetaData metaData = resultSet.getMetaData();
                                    List<String> columnList = new ArrayList<String>(metaData.getColumnCount());
                                    List<Integer> blobList = new ArrayList<Integer>(0);
                                    List<Integer> byteList = new ArrayList<Integer>(0);
                                    for (int i = 0; i < metaData.getColumnCount(); i++) {
                                        String className = metaData.getColumnClassName(i + 1);
                                        if (Constants.BYTE_TYPE.equals(className)) {
                                            String typeName = metaData.getColumnTypeName(i + 1);
                                            if (Constants.Utils.BLOB_LIST.contains(typeName)) {
                                                blobList.add(i);
                                            } else {
                                                byteList.add(i);
                                            }
                                        }
                                        columnList.add(metaData.getColumnName(i + 1));

                                    }
                                    sqlBean.setColumn_list(columnList);
                                    List<List<String>> fetchList = new ArrayList<List<String>>();
                                    int count = 0;
                                    while (resultSet.next() && count <= 1000) {
                                        if (count < 1000) {
                                            List<String> rowList = new ArrayList<String>(columnList.size());
                                            for (int i = 0; i < columnList.size(); i++) {
                                                if (blobList.contains(i)) {
                                                    Blob blob = resultSet.getBlob(i + 1);
                                                    if (blob != null) {
                                                        long length = blob.length();
                                                        double final_length = ((double) length) / 1000.0;
                                                        StringBuilder blobVal = new StringBuilder();
                                                        blobVal.append("<b class=\"blob-download\">");
                                                        blobVal.append(Constants.DATABASE_BLOB);
                                                        blobVal.append(final_length);
                                                        blobVal.append(BeanConstants._KIB);
                                                        blobVal.append("</b>");
                                                        rowList.add(blobVal.toString());
                                                    } else {
                                                        rowList.add(Constants.DATABASE_NULL);
                                                    }
                                                } else if (byteList.contains(i)) {
                                                    byte[] bytes = resultSet.getBytes(i + 1);
                                                    if (bytes != null) {
                                                        StringBuilder byteData = new StringBuilder(bytes.length);
                                                        for (byte aByte : bytes) {
                                                            byteData.append(aByte);
                                                        }
                                                        rowList.add(byteData.toString());
                                                    } else {
                                                        rowList.add(Constants.DATABASE_NULL);
                                                    }
                                                } else {
                                                    String value = resultSet.getString(i + 1);
                                                    if (value == null) {
                                                        rowList.add(Constants.DATABASE_NULL);
                                                    } else {
                                                        rowList.add(value);
                                                    }
                                                }
                                            }
                                            fetchList.add(rowList);
                                        }
                                        count++;
                                    }
                                    sqlBean.setFetch_list(fetchList);
                                    if (count > 1000) {
                                        sqlBean.setMax_rows(Constants.ONE);
                                    }
                                }
                                alreadyEntered = true;
                            } else {
                                int count = statement.getUpdateCount();
                                sqlBean.setResult(String.valueOf(count));
                            }
                        }
                    }
                }
            }
        } finally {
            if (apiConnection != null) {
                apiConnection.commit();
            }
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
    }

}
