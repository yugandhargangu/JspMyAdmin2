/**
 * 
 */
package com.jspmyadmin.framework.connection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.utils.DefaultServlet;
import com.jspmyadmin.framework.web.utils.DefaultServlet.Config;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/02
 *
 */
class ApiConnectionImpl implements ApiConnection {

	public static ConnectionType connectionType = DefaultServlet.geConnectionType();;

	private static final String _DRIVER = "com.mysql.jdbc.Driver";
	private static final String _URL = "jdbc:mysql://";
	private static final String _YEARISDATETYPE = "?yearIsDateType=false";

	private final Connection _connection;

	/**
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ApiConnectionImpl() throws ClassNotFoundException, SQLException {
		_connection = _openConnection(null);
		_connection.setAutoCommit(false);
	}

	/**
	 * 
	 * @param dbName
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ApiConnectionImpl(String dbName) throws ClassNotFoundException, SQLException {
		_connection = _openConnection(dbName);
		_connection.setAutoCommit(false);
	}

	public void close() {
		try {
			if (_connection != null && !_connection.isClosed()) {
				_connection.close();
			}
		} catch (SQLException e) {
		}
	}

	public PreparedStatement getStmtSelect(String query) throws SQLException {
		PreparedStatement statement = _connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		return statement;
	}

	public PreparedStatement getStmt(String query) throws SQLException {
		PreparedStatement statement = _connection.prepareStatement(query);
		return statement;
	}

	public DatabaseMetaData getDatabaseMetaData() throws SQLException {
		return _connection.getMetaData();
	}

	public void commit() {
		if (_connection != null) {
			try {
				_connection.commit();
			} catch (SQLException e) {
			}
		}
	}

	public void rollback() {
		if (_connection != null) {
			try {
				_connection.rollback();
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * 
	 * @param dbName
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private Connection _openConnection(String dbName) throws ClassNotFoundException, SQLException {

		Class.forName(_DRIVER);
		Connection connection = null;
		StringBuilder builder = new StringBuilder(_URL);
		switch (connectionType) {
		case SESSION:
			HttpSession httpSession = DefaultServlet.REQUEST_MAP.get(Thread.currentThread().getId()).getSession();
			if (httpSession.getAttribute(FrameworkConstants.SESSION_HOST) != null) {
				builder.append(httpSession.getAttribute(FrameworkConstants.SESSION_HOST).toString());
				builder.append(FrameworkConstants.SYMBOL_COLON);
				builder.append(httpSession.getAttribute(FrameworkConstants.SESSION_PORT).toString());
				builder.append(FrameworkConstants.SYMBOL_BACK_SLASH);
			}
			if (dbName != null) {
				builder.append(dbName);
			}
			builder.append(_YEARISDATETYPE);
			connection = DriverManager.getConnection(builder.toString(),
					httpSession.getAttribute(FrameworkConstants.SESSION_USER).toString(),
					httpSession.getAttribute(FrameworkConstants.SESSION_PASS).toString());
			break;

		case HALF_CONFIG:
			Config config = (Config) DefaultServlet.getContext().getAttribute("config");
			if (config.getHost() != null) {
				builder.append(config.getHost());
				builder.append(FrameworkConstants.SYMBOL_COLON);
				builder.append(config.getPort());
				builder.append(FrameworkConstants.SYMBOL_BACK_SLASH);
			}
			httpSession = DefaultServlet.REQUEST_MAP.get(Thread.currentThread().getId()).getSession();
			if (dbName != null) {
				builder.append(dbName);
			}
			builder.append(_YEARISDATETYPE);
			connection = DriverManager.getConnection(builder.toString(),
					httpSession.getAttribute(FrameworkConstants.SESSION_USER).toString(),
					httpSession.getAttribute(FrameworkConstants.SESSION_PASS).toString());
			break;

		case CONFIG:
			config = (Config) DefaultServlet.getContext().getAttribute("config");
			if (config.getHost() != null) {
				builder.append(config.getHost());
				builder.append(FrameworkConstants.SYMBOL_COLON);
				builder.append(config.getPort());
				builder.append(FrameworkConstants.SYMBOL_BACK_SLASH);
			}
			if (dbName != null) {
				builder.append(dbName);
			}
			builder.append(_YEARISDATETYPE);
			connection = DriverManager.getConnection(builder.toString(), config.getUser(), config.getPass());
			break;
		}

		return connection;
	}

}
