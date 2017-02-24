package com.tracknix.jspmyadmin.application.database.export.logic;

import com.tracknix.jspmyadmin.application.database.export.beans.ImportBean;
import com.tracknix.jspmyadmin.framework.connection.ApiConnection;
import com.tracknix.jspmyadmin.framework.connection.ConnectionHelper;
import com.tracknix.jspmyadmin.framework.connection.QuerySeparator;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.annotations.Detect;
import com.tracknix.jspmyadmin.framework.web.annotations.LogicService;
import com.tracknix.jspmyadmin.framework.web.utils.Bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

/**
 * @author Yugandhar Gangu
 */
@LogicService
public class ImportLogic {

    @Detect
    private ConnectionHelper connectionHelper;

    /**
     * @param bean
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void importFile(Bean bean) throws SQLException, IOException, ClassNotFoundException {
        ImportBean importBean = (ImportBean) bean;

        ApiConnection apiConnection = null;
        PreparedStatement statement = null;

        File scriptFile = null;
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        long start_time = System.nanoTime();
        int error_count = 0;
        int ignore_count = 0;
        int success_count = 0;
        boolean status = false;
        boolean continue_errors = false;
        StringBuilder errorBuilder = new StringBuilder();
        try {

            if (importBean.getImport_file() != null) {
                String path = connectionHelper.getTempFilePath();
                importBean.getImport_file().copyTo(path);
                scriptFile = new File(path);
                fileReader = new FileReader(scriptFile);
                bufferedReader = new BufferedReader(fileReader);
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line);
                    builder.append(Constants.NEW_LINE);
                }
                if (builder.length() > 0) {

                    importBean.setQuery(builder.toString());

                    if (Constants.ONE.equals(importBean.getContinue_errors())) {
                        continue_errors = true;
                    }
                    if (Constants.ONE.equals(importBean.getImport_to_db())) {
                        apiConnection = connectionHelper.getConnection(bean.getRequest_db());
                    } else {
                        apiConnection = connectionHelper.getConnection();
                    }
                    if (Constants.ONE.equals(importBean.getDisable_fks())) {
                        statement = apiConnection.getStmt("SET foreign_key_checks = ?");
                        statement.setInt(1, 0);
                        statement.execute();
                        connectionHelper.close(statement);
                    }
                    QuerySeparator querySeparator = new QuerySeparator(scriptFile);
                    List<String> queries = querySeparator.getQueries();
                    Iterator<String> iterator = queries.iterator();
                    while (iterator.hasNext()) {
                        status = true;
                        String query = iterator.next();
                        if (!connectionHelper.isEmpty(query)) {
                            statement = apiConnection.getStmt(query);
                            try {
                                boolean result = statement.execute();
                                if (result) {
                                    ignore_count++;
                                } else {
                                    success_count++;
                                }
                            } catch (SQLException e) {
                                if (continue_errors) {
                                    errorBuilder.append(Constants.SYMBOL_SQL_MULTI_START);
                                    errorBuilder.append(e.getMessage());
                                    errorBuilder.append(Constants.SYMBOL_SQL_MULTI_END);
                                    errorBuilder.append(Constants.NEW_LINE);
                                    errorBuilder.append(query);
                                    errorBuilder.append(Constants.SYMBOL_SEMI_COLON);
                                    errorBuilder.append(Constants.NEW_LINE);
                                    errorBuilder.append(Constants.NEW_LINE);
                                    errorBuilder.append(Constants.NEW_LINE);
                                    error_count++;
                                } else {
                                    throw e;
                                }
                            }
                        }
                    }

                }
            }
        } finally {
            if (status) {
                DecimalFormat decimalFormat = new DecimalFormat(Constants.ZERO);
                long end_time = System.nanoTime();
                long exec_time = end_time - start_time;
                double final_exec_time = ((double) exec_time) / 1000000000.0;
                decimalFormat.setMaximumFractionDigits(6);
                importBean.setExec_time(decimalFormat.format(final_exec_time));
                if (ignore_count > 0) {
                    importBean.setIgnore_count(String.valueOf(ignore_count));
                }
                importBean.setSuccess_count(String.valueOf(success_count));
                if (continue_errors) {
                    importBean.setError_count(String.valueOf(error_count));
                    if (errorBuilder.length() > 0) {
                        importBean.setError_queries(errorBuilder.toString());
                    }
                }
            }
            connectionHelper.close(bufferedReader);
            connectionHelper.close(fileReader);
            connectionHelper.deleteFile(scriptFile);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
    }

}
