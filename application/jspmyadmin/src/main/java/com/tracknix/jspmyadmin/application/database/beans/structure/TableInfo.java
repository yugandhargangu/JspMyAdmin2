package com.tracknix.jspmyadmin.application.database.beans.structure;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Yugandhar Gangu
 */
@Data
public class TableInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String type;
    private String engine;
    private int rows;
    private String collation;
    private long size;
    private String auto_inr;
    private String create_date;
    private String update_date;
    private String comment;
}
