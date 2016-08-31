/**
 * 
 */
package com.jspmyadmin.framework.connection;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/08/29
 *
 */
public final class ConnectionTypeCheck {

	/**
	 * 
	 */
	private ConnectionTypeCheck() {
		// nothing
	}

	/**
	 * 
	 * @return
	 */
	public static ConnectionType check() {
		return ApiConnectionImpl.connectionType;
	}

}
