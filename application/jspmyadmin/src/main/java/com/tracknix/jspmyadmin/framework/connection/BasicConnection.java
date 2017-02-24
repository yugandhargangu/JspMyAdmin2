package com.tracknix.jspmyadmin.framework.connection;

import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.utils.DefaultServlet;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @author Yugandhar Gangu
 */
public class BasicConnection implements ConnectionHelper {
    /**
     * @return ApiConnection
     * @throws SQLException e
     */
    public ApiConnection getConnection() throws SQLException {
        return new ApiConnectionImpl();
}

    /**
     * @param dbName database name
     * @return ApiConnection
     * @throws SQLException e
     */
    public ApiConnection getConnection(String dbName) throws SQLException {
        return new ApiConnectionImpl(dbName);
    }

    /**
     * @param val value
     * @return true or false
     */
    public boolean isEmpty(String val) {
        return val == null || Constants.BLANK.equals(val.trim());
    }

    /**
     * @param val value
     * @return true or false
     */
    public boolean isDouble(String val) {
        try {
            Double.parseDouble(val);
            return true;
        } catch (NumberFormatException ignored) {
        }
        return false;
    }

    /**
     * @param val value
     * @return true or false
     */
    public boolean isInteger(String val) {
        try {
            Integer.parseInt(val);
            return true;
        } catch (NumberFormatException ignored) {
        }
        return false;
    }

    /**
     * @param file File
     * @return true or false
     */
    public boolean deleteFile(File file) {
        return file != null && file.delete();
    }

    /**
     * @param str sql
     * @return true or false
     */
    public boolean isValidSqlString(String str, boolean withQuotes) {

        if (withQuotes && (!str.startsWith(Constants.SYMBOL_QUOTE) || !str.endsWith(Constants.SYMBOL_QUOTE))) {
            return false;
        }

        int count1 = 0;
        int count2 = 0;
        int lastIndex = 0;
        while (lastIndex != -1) {
            lastIndex = str.indexOf(Constants.SYMBOL_QUOTE, lastIndex);

            if (lastIndex != -1) {
                count1++;
                lastIndex += 1;
            }
        }
        lastIndex = 0;
        while (lastIndex != -1) {
            lastIndex = str.indexOf(Constants.SYMBOL_QUOTE_ESCAPE, lastIndex);

            if (lastIndex != -1) {
                count2++;
                lastIndex += 2;
            }
        }
        return count1 == count2;
    }

    /**
     * @param resultSet ResultSet
     */
    public void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException ignored) {
            }
        }
    }

    /**
     * @param statement PreparedStatement
     */
    public void close(PreparedStatement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException ignored) {
            }
        }
    }

    /**
     * @param apiConnection ApiConnection
     */
    public void close(ApiConnection apiConnection) {
        if (apiConnection != null) {
            apiConnection.close();
        }
    }

    /**
     * @param closeable Closeable
     */
    public void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ignored) {
            }
        }
    }

    /**
     *
     */
    public String getTempFilePath() {
        return createTempFilePath();
    }

    /**
     *
     */
    public String extractQuery(PreparedStatement statement) {
        QueryExtractor extractor = new QueryExtractor(statement);
        return extractor.toString();
    }

    /**
     * @return file path
     */
    private static synchronized String createTempFilePath() {
        File file = new File(DefaultServlet.getRoot_path(), System.currentTimeMillis() + ".tmp");
        return file.getAbsolutePath();
    }

    /**
     * @author Yugandhar Gangu
     */
    private static class QueryExtractor {
        private final String _query;

        /**
         * @param statement PreparedStatement
         */
        private QueryExtractor(PreparedStatement statement) {
            String query = statement.toString();
            int index = query.indexOf(Constants.SYMBOL_COLON) + 1;
            if (index != -1) {
                _query = query.substring(index);
            } else {
                _query = null;
            }
        }

        @Override
        public String toString() {
            return _query;
        }
    }
}
