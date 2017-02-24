package com.tracknix.jspmyadmin.application.database.structure.beans;

import lombok.Data;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * @author Yugandhar Gangu
 */
@Data
public class TableInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private final DecimalFormat _format = new DecimalFormat("0.00");

    private String name = null;
    private String type = null;
    private String engine = null;
    private String rows = null;
    private String collation = null;
    private String size = null;
    private String auto_inr = null;
    private String create_date = null;
    private String update_date = null;
    private String comment = null;
    private String action = null;
}
