package com.tracknix.jspmyadmin.framework.web.utils;

/**
 * Query interface is to maintain query objects
 */
public interface QueryHelper {

    /**
     * To check query or parts
     *
     * @param queryIndex query index
     * @return boolean
     */
    boolean isQuery(int queryIndex);

    /**
     * To get query.
     *
     * @param queryIndex query index
     * @param args       string arguments
     * @return query string
     */
    String getQuery(int queryIndex, Object... args);

    /**
     * to get part
     *
     * @param queryIndex int
     * @param partIndex  int
     * @param args       string arguments
     * @return String
     */
    String getPart(int queryIndex, int partIndex, Object... args);

    /**
     * To get columns
     *
     * @param queryIndex int
     * @return String
     */
    String[] getColumns(int queryIndex);
}
