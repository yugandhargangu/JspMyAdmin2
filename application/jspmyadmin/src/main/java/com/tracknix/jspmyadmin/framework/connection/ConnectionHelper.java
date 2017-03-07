package com.tracknix.jspmyadmin.framework.connection;

import java.io.Closeable;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Yugandhar Gangu
 */
public interface ConnectionHelper {

    /**
     * @return ApiConnection
     * @throws SQLException e
     */
    ApiConnection getConnection() throws SQLException;

    /**
     * @param dbName database name
     * @return ApiConnection
     * @throws SQLException e
     */
    ApiConnection getConnection(String dbName) throws SQLException;

    /**
     * @param val value
     * @return boolean
     */
    boolean isEmpty(String val);

    /**
     * @param val value
     * @return boolean
     */
    boolean isDouble(String val);

    /**
     * @param val value
     * @return boolean
     */
    boolean isInteger(String val);

    /**
     * @return string
     */
    String getTempFilePath();

    /**
     * @param file File
     * @return boolean
     */
    boolean deleteFile(File file);

    /**
     * @param str sql string
     * @return boolean
     */
    boolean isValidSqlString(String str, boolean withQuotes);

    /**
     * @param statement PreparedStatement
     * @return string
     */
    String extractQuery(PreparedStatement statement);

    /**
     * @param resultSet ResultSet
     */
    void close(ResultSet resultSet);

    /**
     * @param statement PreparedStatement
     */
    void close(PreparedStatement statement);

    /**
     * @param apiConnection ApiConnection
     */
    void close(ApiConnection apiConnection);

    /**
     * @param closeable Closeable
     */
    void close(Closeable closeable);

    /**
     * @param apiConnection {@link ApiConnection}
     * @param enable        boolean
     * @throws SQLException e
     */
    void setForeignKeyChecks(ApiConnection apiConnection, boolean enable) throws SQLException;
}
