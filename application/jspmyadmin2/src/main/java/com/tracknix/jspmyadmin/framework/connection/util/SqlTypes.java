package com.tracknix.jspmyadmin.framework.connection.util;

/**
 * @author Yugandhar Gangu
 */
public class SqlTypes {
    private static final SqlTypes SQL_TYPES = new SqlTypes();

    /**
     * @return Partitions
     */
    public static SqlTypes getInstance() {
        return SQL_TYPES;
    }

    private final String[] sql_types;

    /**
     * Constructor
     */
    private SqlTypes() {
        sql_types = new String[]{"CONTAINS SQL", "NO SQL","READS SQL DATA","MODIFIES SQL DATA"};
    }

    /**
     * @return String[]
     */
    public String[] getSqlTypes() {
        return sql_types;
    }
}
