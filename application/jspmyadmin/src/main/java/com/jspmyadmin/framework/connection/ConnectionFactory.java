/**
 * 
 */
package com.jspmyadmin.framework.connection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;

import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.utils.DefaultServlet;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/09/06
 *
 */
public class ConnectionFactory {

	public static final String ADMIN_USERNAME = "admin_username";
	public static final String ADMIN_PASSWORD = "admin_password";
	public static final String CONFIG_TYPE = "config_type";
	public static final String CONFIG_HOST = "config_host";
	public static final String CONFIG_PORT = "config_port";
	public static final String CONFIG_USERNAME = "config_username";
	public static final String CONFIG_PASSWORD = "config_password";
	public static final String CONFIG_TYPE_CONFIG = "config";
	public static final String CONFIG_TYPE_HALF_CONFIG = "half_config";
	public static final String CONFIG_TYPE_LOGIN = "login";

	private static final String CONFIG_PATH = DefaultServlet.getWebInfPath() + "/config.ser";

	static Config config = null;
	static ConnectionType connectionType;
	private static boolean isConfigured = false;

	/**
	 * 
	 */
	public static synchronized void init() {
		File file = new File(CONFIG_PATH);
		if (file.exists()) {
			isConfigured = true;
			FileInputStream inputStream = null;
			ObjectInputStream objectInputStream = null;
			try {
				inputStream = new FileInputStream(file);
				objectInputStream = new ObjectInputStream(inputStream);
				ConnectionConfiguration configuration = (ConnectionConfiguration) objectInputStream.readObject();
				if (configuration.getConfig_type() != null) {
					String configType = DefaultServlet.decodeInstall(configuration.getConfig_type());
					if (CONFIG_TYPE_CONFIG.equalsIgnoreCase(configType)) {
						connectionType = ConnectionType.CONFIG;
					} else if (CONFIG_TYPE_HALF_CONFIG.equalsIgnoreCase(configType)) {
						connectionType = ConnectionType.HALF_CONFIG;
					} else {
						connectionType = ConnectionType.LOGIN;
					}
				} else {
					connectionType = ConnectionType.LOGIN;
				}
				if (Constants.BLANK.equals(configuration.getConfig_password())) {
					configuration.setConfig_password(null);
				}
				if (configuration.getConfig_host() != null) {
					configuration.setConfig_host(DefaultServlet.decodeInstall(configuration.getConfig_host()));
				}
				if (configuration.getConfig_port() != null) {
					configuration.setConfig_port(DefaultServlet.decodeInstall(configuration.getConfig_port()));
				}
				if (configuration.getConfig_username() != null) {
					configuration.setConfig_username(DefaultServlet.decodeInstall(configuration.getConfig_username()));
				}
				if (configuration.getConfig_password() != null) {
					configuration.setConfig_password(DefaultServlet.decodeInstall(configuration.getConfig_password()));
				}
				config = new Config(configuration.getConfig_host(), configuration.getConfig_port(),
						configuration.getConfig_username(), configuration.getConfig_password());

			} catch (IOException e) {
			} catch (ClassNotFoundException e) {
			} catch (EncodingException e) {
			} finally {
				if (objectInputStream != null) {
					try {
						objectInputStream.close();
					} catch (IOException e) {
					}
				}
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
					}
				}
			}
		} else {
			isConfigured = false;
			connectionType = null;
			config = null;
		}
	}

	/**
	 * 
	 * @return
	 */
	public static boolean isConfigured() {
		return isConfigured;
	}

	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static synchronized ApiConnection getConnection() throws SQLException {
		return new ApiConnectionImpl();
	}

	/**
	 * 
	 * @param database
	 * @return
	 * @throws SQLException
	 */
	public static synchronized ApiConnection getConnection(String database) throws SQLException {
		return new ApiConnectionImpl(database);
	}

	/**
	 * 
	 * @param host
	 * @param port
	 * @param user
	 * @param pass
	 * @return
	 * @throws SQLException
	 */
	public static synchronized ApiConnection getConnection(String host, String port, String user, String pass)
			throws SQLException {
		return new ApiConnectionImpl(host, port, user, pass);
	}

	/**
	 * 
	 * @author Yugandhar Gangu
	 * @created_at 2016/09/06
	 *
	 */
	static class Config {
		private final String host;
		private final String port;
		private final String user;
		private final String pass;

		/**
		 * 
		 * @param host
		 * @param port
		 * @param user
		 * @param pass
		 */
		private Config(String host, String port, String user, String pass) {
			this.host = host;
			this.port = port;
			this.user = user;
			this.pass = pass;
		}

		/**
		 * @return the host
		 */
		public String getHost() {
			return host;
		}

		/**
		 * @return the port
		 */
		public String getPort() {
			return port;
		}

		/**
		 * @return the user
		 */
		public String getUser() {
			return user;
		}

		/**
		 * @return the pass
		 */
		public String getPass() {
			return pass;
		}

	}

}
