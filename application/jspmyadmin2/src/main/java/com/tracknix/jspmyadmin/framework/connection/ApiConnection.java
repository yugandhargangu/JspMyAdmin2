package com.tracknix.jspmyadmin.framework.connection;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * ApiConnection interface is to provide database connection utils.
 *
 * @author Yugandhar Gangu
 */
public interface ApiConnection {

    /**
     * Returns the Read Only PreparedStatement object.
     *
     * @param query query string to create prepared statement.
     * @return PreparedStatement object
     * @throws SQLException e
     */
    PreparedStatement getStmtSelect(String query) throws SQLException;

    /**
     * @param query String
     * @return PreparedStatement
     * @throws SQLException e
     */
    PreparedStatement getStmt(String query) throws SQLException;

    /**
     * @return DatabaseMetaData
     * @throws SQLException e
     */
    DatabaseMetaData getDatabaseMetaData() throws SQLException;

    /**
     *
     */
    void commit();

    /**
     *
     */
    void rollback();

    /**
     *
     */
    void close();
}
