/**
 * 
 */
package com.jspmyadmin.framework.connection;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.web.logic.EncodeHelper;
import com.jspmyadmin.framework.web.utils.DefaultServlet;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/02
 *
 */
public abstract class AbstractLogic {

	protected EncodeHelper encodeObj;

	/**
	 * @param encodeObj
	 *            the encodeObj to set
	 */
	public void setEncodeObj(EncodeHelper encodeObj) {
		this.encodeObj = encodeObj;
	}

	/**
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	protected ApiConnection getConnection() throws SQLException {
		ApiConnection connection = new ApiConnectionImpl();
		return connection;
	}

	/**
	 * 
	 * @param dbName
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	protected ApiConnection getConnection(String dbName) throws SQLException {
		ApiConnection connection = new ApiConnectionImpl(dbName);
		return connection;
	}

	/**
	 * 
	 * @param val
	 * @return
	 */
	protected boolean isEmpty(String val) {
		if (val == null || Constants.BLANK.equals(val.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param val
	 * @return
	 */
	protected boolean isDouble(String val) {
		try {
			Double.parseDouble(val);
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 
	 * @param val
	 * @return
	 */
	protected boolean isInteger(String val) {
		try {
			Integer.parseInt(val);
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 
	 * @param file
	 * @return
	 */
	protected boolean deleteFile(File file) {
		if (file != null) {
			return file.delete();
		}
		return false;
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	protected boolean isValidSqlString(String str, boolean withQuotes) {

		if (withQuotes && (!str.startsWith(Constants.SYMBOL_QUOTE)
				|| !str.endsWith(Constants.SYMBOL_QUOTE))) {
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
		if (count1 == count2) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param resultSet
	 */
	protected void close(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * 
	 * @param statement
	 */
	protected void close(PreparedStatement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * 
	 * @param apiConnection
	 */
	protected void close(ApiConnection apiConnection) {
		if (apiConnection != null) {
			apiConnection.close();
		}
	}

	/**
	 * 
	 * @param closeable
	 */
	protected void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 
	 * @author Yugandhar Gangu
	 * @created_at 2016/06/27
	 *
	 */
	protected static class QueryExtracter {
		private final String _query;

		/**
		 * 
		 * @param statement
		 */
		public QueryExtracter(PreparedStatement statement) {
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

	/**
	 * 
	 * @return
	 */
	protected static synchronized String getTempFilePath() {
		File file = new File(DefaultServlet.getRoot_path(), System.currentTimeMillis() + ".tmp");
		return file.getAbsolutePath();
	}
}
