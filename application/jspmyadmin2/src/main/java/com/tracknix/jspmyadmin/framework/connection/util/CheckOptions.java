package com.tracknix.jspmyadmin.framework.connection.util;

/**
 * @author Yugandhar Gangu
 */
public class CheckOptions {
    private static final CheckOptions CHECK_OPTIONS = new CheckOptions();

    /**
     * @return Partitions
     */
    public static CheckOptions getInstance() {
        return CHECK_OPTIONS;
    }

    private final String[] check_options;

    /**
     * Constructor
     */
    private CheckOptions() {
        check_options = new String[]{"CASCADED", "LOCAL"};
    }

    /**
     * @return String[]
     */
    public String[] getCheckOptions() {
        return check_options;
    }
}
