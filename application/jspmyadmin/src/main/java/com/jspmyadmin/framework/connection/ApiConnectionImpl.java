package com.jspmyadmin.framework.connection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.web.utils.RequestAdaptor;
import com.mysql.cj.jdbc.MysqlDataSource;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/02
 */
class ApiConnectionImpl implements ApiConnection {

    private static final String _URL = "jdbc:mysql://";
    private static final String _YEARISDATETYPE = "?yearIsDateType=false";

    private final Connection _connection;

    /**
     * @throws SQLException e
     */
    ApiConnectionImpl() throws SQLException {
        try {
            _connection = _openConnection(null);
            if (_connection != null) {
                _connection.setAutoCommit(false);
            }
        } catch (SQLException e) {
            HttpSession session = RequestAdaptor.REQUEST_MAP.get(Thread.currentThread().getId()).getSession();
            session.setAttribute(Constants.SESSION_CONNECT, true);
            session.setAttribute(Constants.MYSQL_ERROR, e.getMessage());
            throw e;
        }
    }

    /**
     * @param dbName String
     * @throws SQLException e
     */
    ApiConnectionImpl(String dbName) throws SQLException {
        try {
            _connection = _openConnection(dbName);
            if (_connection != null) {
                _connection.setAutoCommit(false);
            }
        } catch (SQLException e) {
            HttpSession session = RequestAdaptor.REQUEST_MAP.get(Thread.currentThread().getId()).getSession();
            session.setAttribute(Constants.SESSION_CONNECT, true);
            throw e;
        }
    }

    /**
     * @param host String
     * @param port String
     * @param user String
     * @param pass String
     * @throws SQLException e
     */
    ApiConnectionImpl(String host, String port, String user, String pass) throws SQLException {
        try {
            _connection = _openConnection(host, port, user, pass);
        } catch (SQLException e) {
            HttpSession session = RequestAdaptor.REQUEST_MAP.get(Thread.currentThread().getId()).getSession();
            session.setAttribute(Constants.SESSION_CONNECT, true);
            throw e;
        }
    }

    public void close() {
        try {
            if (_connection != null && !_connection.isClosed()) {
                _connection.close();
            }
        } catch (SQLException ignored) {
        }
    }

    public PreparedStatement getStmtSelect(final String query) throws SQLException {
        return _connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
    }

    public PreparedStatement getStmt(final String query) throws SQLException {
        return _connection.prepareStatement(query);
    }

    public DatabaseMetaData getDatabaseMetaData() throws SQLException {
        return _connection.getMetaData();
    }

    public void commit() {
        if (_connection != null) {
            try {
                _connection.commit();
            } catch (SQLException ignored) {
            }
        }
    }

    public void rollback() {
        if (_connection != null) {
            try {
                _connection.rollback();
            } catch (SQLException ignored) {
            }
        }
    }

    /**
     * To open connection by providing all of the properties.
     *
     * @param host String
     * @param port String
     * @param user String
     * @param pass String
     * @return Connection
     * @throws SQLException e
     */
    private Connection _openConnection(String host, String port, String user, String pass) throws SQLException {

        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUseSSL(false);
        dataSource.setURL(_URL + host + Constants.SYMBOL_COLON + port + Constants.SYMBOL_BACK_SLASH);
        dataSource.setUser(user);
        dataSource.setPassword(pass);
        return dataSource.getConnection();
    }

    /**
     * To open the connection with database name or without database name.
     *
     * @param dbName String
     * @return Connection
     * @throws SQLException e
     */
    private Connection _openConnection(String dbName) throws SQLException {

        StringBuilder builder = new StringBuilder(_URL);
        switch (ConnectionFactory.connectionType) {
            case LOGIN:
                HttpSession httpSession = RequestAdaptor.REQUEST_MAP.get(Thread.currentThread().getId()).getSession();
                if (httpSession.getAttribute(Constants.SESSION_HOST) != null) {
                    builder.append(httpSession.getAttribute(Constants.SESSION_HOST).toString());
                    builder.append(Constants.SYMBOL_COLON);
                    builder.append(httpSession.getAttribute(Constants.SESSION_PORT).toString());
                    builder.append(Constants.SYMBOL_BACK_SLASH);
                }
                if (dbName != null) {
                    builder.append(dbName);
                }
                builder.append(_YEARISDATETYPE);

                MysqlDataSource dataSource = new MysqlDataSource();
                dataSource.setUseSSL(false);
                dataSource.setURL(builder.toString());
                dataSource.setUser(httpSession.getAttribute(Constants.SESSION_USER).toString());
                String pass = httpSession.getAttribute(Constants.SESSION_PASS).toString();
                if (!Constants.BLANK.equals(pass)) {
                    dataSource.setPassword(pass);
                }
                return dataSource.getConnection();

            case HALF_CONFIG:
                if (ConnectionFactory.config.getHost() != null) {
                    builder.append(ConnectionFactory.config.getHost());
                    builder.append(Constants.SYMBOL_COLON);
                    builder.append(ConnectionFactory.config.getPort());
                    builder.append(Constants.SYMBOL_BACK_SLASH);
                }
                httpSession = RequestAdaptor.REQUEST_MAP.get(Thread.currentThread().getId()).getSession();
                if (dbName != null) {
                    builder.append(dbName);
                }
                builder.append(_YEARISDATETYPE);
                dataSource = new MysqlDataSource();
                dataSource.setUseSSL(false);
                dataSource.setURL(builder.toString());
                dataSource.setUser(httpSession.getAttribute(Constants.SESSION_USER).toString());
                pass = httpSession.getAttribute(Constants.SESSION_PASS).toString();
                if (!Constants.BLANK.equals(pass)) {
                    dataSource.setPassword(pass);
                }
                return dataSource.getConnection();

            case CONFIG:
                if (ConnectionFactory.config.getHost() != null) {
                    builder.append(ConnectionFactory.config.getHost());
                    builder.append(Constants.SYMBOL_COLON);
                    builder.append(ConnectionFactory.config.getPort());
                    builder.append(Constants.SYMBOL_BACK_SLASH);
                }
                if (dbName != null) {
                    builder.append(dbName);
                }
                builder.append(_YEARISDATETYPE);
                dataSource = new MysqlDataSource();
                dataSource.setUseSSL(false);
                dataSource.setURL(builder.toString());
                dataSource.setUser(ConnectionFactory.config.getUser());
                dataSource.setPassword(ConnectionFactory.config.getPass());
                return dataSource.getConnection();
            default:
                return null;
        }
    }
}
