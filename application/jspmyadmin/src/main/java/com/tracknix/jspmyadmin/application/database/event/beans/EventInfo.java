package com.tracknix.jspmyadmin.application.database.event.beans;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Yugandhar Gangu
 */
@Data
public class EventInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name = null;
    private String definer = null;
    private String type = null;
    private String status = null;
    private String create_date = null;
    private String alter_date = null;
    private String comments = null;
}
