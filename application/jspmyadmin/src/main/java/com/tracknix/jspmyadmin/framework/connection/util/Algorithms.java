package com.tracknix.jspmyadmin.framework.connection.util;

/**
 * @author Yugandhar Gangu
 */
public class Algorithms {
    private static final Algorithms ALGORITHMS = new Algorithms();

    /**
     * @return Partitions
     */
    public static Algorithms getInstance() {
        return ALGORITHMS;
    }

    private final String[] algorithms;

    /**
     * Constructor
     */
    private Algorithms() {
        algorithms = new String[]{"UNDEFINED", "MERGE", "TEMPTABLE"};
    }

    /**
     * @return String[]
     */
    public String[] getAlgorithms() {
        return algorithms;
    }
}
