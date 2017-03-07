package com.tracknix.jspmyadmin.framework.connection.util;

/**
 * @author Yugandhar Gangu
 */
public class Partitions {
    private static final Partitions PARTITIONS = new Partitions();

    /**
     * @return Partitions
     */
    public static Partitions getInstance() {
        return PARTITIONS;
    }

    private final String[] partitions;

    /**
     * Constructor
     */
    private Partitions() {
        partitions = new String[]{"RANGE", "LIST", "RANGE COLUMNS", "LIST COLUMNS", "HASH", "LINEAR HASH", "KEY"};
    }

    /**
     * @return String[]
     */
    public String[] getPartitions() {
        return partitions;
    }
}
