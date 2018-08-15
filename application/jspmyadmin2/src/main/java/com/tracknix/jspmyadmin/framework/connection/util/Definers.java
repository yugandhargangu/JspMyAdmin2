package com.tracknix.jspmyadmin.framework.connection.util;

/**
 * @author Yugandhar Gangu
 */
public class Definers {
    private static final Definers DEFINERS = new Definers();

    /**
     * @return Partitions
     */
    public static Definers getInstance() {
        return DEFINERS;
    }

    private final String[] definers;

    /**
     * Constructor
     */
    private Definers() {
        definers = new String[]{"CURRENT_USER", "OTHER"};
    }

    /**
     * @return String[]
     */
    public String[] getDefiners() {
        return definers;
    }
}
