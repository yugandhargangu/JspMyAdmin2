package com.tracknix.jspmyadmin.application.server.beans.database;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Yugandhar Gangu
 */
@Data
public class DatabaseInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String database;
    private String collation;
    private int tables;
    private int rows;
    private long data;
    private long indexes;
    private long total;
}
