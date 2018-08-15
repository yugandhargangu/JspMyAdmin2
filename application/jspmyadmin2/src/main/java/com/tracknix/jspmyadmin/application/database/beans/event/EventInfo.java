package com.tracknix.jspmyadmin.application.database.beans.event;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Yugandhar Gangu
 */
@Data
public class EventInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String definer;
    private String type;
    private String status;
    private String create_date;
    private String alter_date;
    private String comments;
}
