package com.tracknix.jspmyadmin.application.common.logic;

import com.tracknix.jspmyadmin.application.common.beans.HomeBean;
import com.tracknix.jspmyadmin.framework.connection.ApiConnection;
import com.tracknix.jspmyadmin.framework.connection.ConnectionHelper;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.annotations.Detect;
import com.tracknix.jspmyadmin.framework.web.annotations.LogicService;
import com.tracknix.jspmyadmin.framework.web.utils.DefaultServlet;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspFactory;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Yugandhar Gangu
 */
@LogicService
public class HomeLogic {

    @Detect
    private ConnectionHelper connectionHelper;

    /**
     * @return Map
     * @throws SQLException e
     */
    public Map<String, List<String>> getCollationMap() throws SQLException {
        Map<String, List<String>> collationMap = new TreeMap<String, List<String>>();

        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        PreparedStatement innerStatement = null;
        ResultSet innerResultSet = null;
        try {
            apiConnection = connectionHelper.getConnection();
            statement = apiConnection.getStmtSelect("SHOW CHARACTER SET");
            resultSet = statement.executeQuery();
            innerStatement = apiConnection.getStmtSelect("SHOW COLLATION WHERE CHARSET = ?");
            while (resultSet.next()) {
                String charset = resultSet.getString(1);
                innerStatement.clearParameters();
                innerStatement.setString(1, charset);
                innerResultSet = innerStatement.executeQuery();
                List<String> collationList = new ArrayList<String>();
                while (innerResultSet.next()) {
                    collationList.add(innerResultSet.getString(1));
                }
                innerResultSet.close();
                innerResultSet = null;
                Collections.sort(collationList);
                collationMap.put(charset, collationList);
            }
        } finally {
            connectionHelper.close(innerResultSet);
            connectionHelper.close(innerStatement);
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
        return collationMap;
    }

    /**
     * @param homeBean HomeBean
     * @throws SQLException e
     */
    public void fillBean(HomeBean homeBean) throws SQLException {
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            apiConnection = connectionHelper.getConnection();
            DatabaseMetaData databaseMetaData = apiConnection.getDatabaseMetaData();
            homeBean.setDb_server_user(databaseMetaData.getUserName());

            statement = apiConnection.getStmtSelect("SHOW VARIABLES WHERE VARIABLE_NAME = ?");
            statement.setString(1, Constants.COLLATION_SERVER);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                homeBean.setCollation(resultSet.getString(2));
            }
            resultSet.close();
            resultSet = null;
            statement.close();
            statement = null;

            statement = apiConnection.getStmtSelect("SHOW VARIABLES WHERE VARIABLE_NAME = ?");
            statement.setString(1, Constants.HOSTNAME);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                homeBean.setDb_server_name(resultSet.getString(2));
            }
            resultSet.close();
            resultSet = null;
            statement.close();
            statement = null;

            statement = apiConnection.getStmtSelect("SHOW VARIABLES WHERE VARIABLE_NAME = ?");
            statement.setString(1, Constants.VERSION_COMMENT);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                homeBean.setDb_server_type(resultSet.getString(2));
            }
            resultSet.close();
            resultSet = null;
            statement.close();
            statement = null;

            statement = apiConnection.getStmtSelect("SHOW VARIABLES WHERE VARIABLE_NAME = ?");
            statement.setString(1, Constants.VERSION);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                homeBean.setDb_server_version(resultSet.getString(2));
            }
            resultSet.close();
            resultSet = null;
            statement.close();
            statement = null;

            statement = apiConnection.getStmtSelect("SHOW VARIABLES WHERE VARIABLE_NAME = ?");
            statement.setString(1, Constants.PROTOCOL_VERSION);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                homeBean.setDb_server_protocol(resultSet.getString(2));
            }
            resultSet.close();
            resultSet = null;
            statement.close();
            statement = null;

            statement = apiConnection.getStmtSelect("SHOW VARIABLES WHERE VARIABLE_NAME = ?");
            statement.setString(1, Constants.CHARACTER_SET_SERVER);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                homeBean.setDb_server_charset(resultSet.getString(2));
            }
            resultSet.close();
            resultSet = null;
            statement.close();
            statement = null;

            ServletContext context = DefaultServlet.getContext();
            homeBean.setWeb_server_name(context.getServerInfo());
            homeBean.setJdbc_version(databaseMetaData.getDriverVersion());
            homeBean.setJava_version(Runtime.class.getPackage().getImplementationVersion());
            homeBean.setServelt_version(context.getMajorVersion() + Constants.SYMBOL_DOT + context.getMinorVersion());
            homeBean.setJsp_version(JspFactory.getDefaultFactory().getEngineInfo().getSpecificationVersion());
        } finally {
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
    }

    /**
     * @param collation String
     * @throws SQLException e
     */
    public void saveServerCollation(String collation) throws SQLException {
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String charset = null;
        try {
            apiConnection = connectionHelper.getConnection();
            statement = apiConnection.getStmtSelect("SHOW COLLATION WHERE collation = ?");
            statement.setString(1, collation);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                charset = resultSet.getString(Constants.CHARSET);
            }
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            if (charset != null) {
                statement = apiConnection.getStmt("SET character_set_server = ?");
                statement.setString(1, charset);
                statement.execute();
                connectionHelper.close(statement);
            }
            statement = apiConnection.getStmt("SET collation_server = ?");
            statement.setString(1, collation);
            statement.execute();
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
