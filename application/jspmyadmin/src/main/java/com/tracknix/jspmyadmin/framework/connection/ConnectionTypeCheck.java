package com.tracknix.jspmyadmin.framework.connection;

/**
 * @author Yugandhar Gangu
 */
public final class ConnectionTypeCheck {

    /**
     *
     */
    private ConnectionTypeCheck() {
        // nothing
    }

    /**
     * @return ConnectionType
     */
    public static ConnectionType check() {
        return ConnectionFactory.connectionType;
    }

}
