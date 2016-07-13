/**
 * 
 */
package com.jspmyadmin.framework.connection;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/02
 *
 */
public interface ApiConnection {

	void close();

	PreparedStatement preparedStatementSelect(String query) throws SQLException;

	PreparedStatement preparedStatement(String query) throws SQLException;

	DatabaseMetaData getDatabaseMetaData() throws SQLException;

	String getDatabase();

	void commit();

	void rollback();
}
