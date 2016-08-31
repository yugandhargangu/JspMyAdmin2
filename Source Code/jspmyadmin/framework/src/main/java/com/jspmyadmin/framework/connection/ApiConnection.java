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

	/**
	 * 
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	PreparedStatement getStmtSelect(String query) throws SQLException;

	/**
	 * 
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	PreparedStatement getStmt(String query) throws SQLException;

	/**
	 * 
	 * @return
	 * @throws SQLException
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
