package com.tracknix.jspmyadmin.framework.connection.util;

/**
 * @author Yugandhar Gangu
 */
public class SecurityTypes {
    private static final SecurityTypes SECURITY_TYPES = new SecurityTypes();

    /**
     * @return Partitions
     */
    public static SecurityTypes getInstance() {
        return SECURITY_TYPES;
    }

    private final String[] security_types;

    /**
     * Constructor
     */
    private SecurityTypes() {
        security_types = new String[]{"DEFINER", "INVOKER"};
    }

    /**
     * @return String[]
     */
    public String[] getSecurityTypes() {
        return security_types;
    }
}
