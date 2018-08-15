package com.tracknix.jspmyadmin.application.database.services;

import com.tracknix.jspmyadmin.framework.connection.ApiConnection;
import com.tracknix.jspmyadmin.framework.connection.ConnectionHelper;
import com.tracknix.jspmyadmin.framework.constants.Constants;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 */
abstract class AbstractBaseLogic {
    /**
     * @param connectionHelper {@link ConnectionHelper}
     * @param action           {@link String}
     * @param database         {@link String}
     * @param query            {@link String}
     * @return String or null
     */
    String _createExecuteAOrReturn(ConnectionHelper connectionHelper, String action, String database, String query) throws SQLException {
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        try {
            if (Constants.YES.equalsIgnoreCase(action)) {
                apiConnection = connectionHelper.getConnection(database);
                statement = apiConnection.getStmt(query);
                statement.execute();
                apiConnection.commit();
            } else {
                return query;
            }
        } finally {
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
        return null;
    }
}
