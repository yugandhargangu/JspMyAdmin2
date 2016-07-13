/**
 * 
 */
package com.jspmyadmin.framework.connection;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.utils.DefaultServlet;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/02
 *
 */
class ApiConnectionImpl implements ApiConnection {

	public static ConnectionType connectionType = ConnectionType.SESSION;

	private static final String _DRIVER = "com.mysql.jdbc.Driver";
	private static final String _URL = "jdbc:mysql://";

	private final Connection _connection;

	/**
	 * 
	 * @param withDb
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ApiConnectionImpl(boolean withDb) throws ClassNotFoundException, SQLException {
		_connection = _openConnection(withDb);
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

	public PreparedStatement preparedStatementSelect(String query) throws SQLException {
		PreparedStatement statement = _connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		return statement;
	}

	public PreparedStatement preparedStatement(String query) throws SQLException {
		PreparedStatement statement = _connection.prepareStatement(query);
		return statement;
	}

	public DatabaseMetaData getDatabaseMetaData() throws SQLException {
		return _connection.getMetaData();
	}

	public String getDatabase() {
		HttpSession httpSession = DefaultServlet.REQUEST_MAP.get(Thread.currentThread().getId()).getSession();
		if (httpSession.getAttribute(FrameworkConstants.SESSION_DB) != null) {
			return httpSession.getAttribute(FrameworkConstants.SESSION_DB).toString();
		}
		return null;
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
	 * @param withDb
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private Connection _openConnection(boolean withDb) throws ClassNotFoundException, SQLException {
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
			if (withDb && httpSession.getAttribute(FrameworkConstants.SESSION_DB) != null) {
				builder.append(httpSession.getAttribute(FrameworkConstants.SESSION_DB).toString());
			}
			connection = DriverManager.getConnection(builder.toString(),
					httpSession.getAttribute(FrameworkConstants.SESSION_USER).toString(),
					httpSession.getAttribute(FrameworkConstants.SESSION_PASS).toString());
			break;

		case CONFIG:
			if (Config.HOST != null) {
				builder.append(Config.HOST);
				builder.append(FrameworkConstants.SYMBOL_COLON);
				builder.append(Config.PORT);
				builder.append(FrameworkConstants.SYMBOL_BACK_SLASH);
			}
			HttpSession httpSession2 = DefaultServlet.REQUEST_MAP.get(Thread.currentThread().getId()).getSession();
			if (withDb && httpSession2.getAttribute(FrameworkConstants.SESSION_DB) != null) {
				builder.append(httpSession2.getAttribute(FrameworkConstants.SESSION_DB).toString());
			}
			connection = DriverManager.getConnection(builder.toString(), Config.USER, Config.PASS);
			connection = DriverManager.getConnection("", "", "");
			break;
		}
		return connection;
	}

	private Connection _openConnection(String dbName) throws ClassNotFoundException, SQLException {
		Class.forName(_DRIVER);
		Connection connection = null;
		StringBuilder builder = new StringBuilder(_URL);
		HttpSession httpSession = DefaultServlet.REQUEST_MAP.get(Thread.currentThread().getId()).getSession();
		if (httpSession.getAttribute(FrameworkConstants.SESSION_HOST) != null) {
			builder.append(httpSession.getAttribute(FrameworkConstants.SESSION_HOST).toString());
			builder.append(FrameworkConstants.SYMBOL_COLON);
			builder.append(httpSession.getAttribute(FrameworkConstants.SESSION_PORT).toString());
			builder.append(FrameworkConstants.SYMBOL_BACK_SLASH);
		}
		builder.append(dbName);
		connection = DriverManager.getConnection(builder.toString(),
				httpSession.getAttribute(FrameworkConstants.SESSION_USER).toString(),
				httpSession.getAttribute(FrameworkConstants.SESSION_PASS).toString());
		return connection;
	}

	private static class Config {
		static final String HOST;
		static final String PORT;
		static final String USER;
		static final String PASS;

		static {
			try {
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				URL packageURL = classLoader.getResource("config.xml");
				URI uri = new URI(packageURL.toString());
				File configFile = new File(uri.getPath());
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(configFile);
				doc.appendChild(null);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			HOST = null;
			PORT = null;
			USER = null;
			PASS = null;
		}
	}

}
