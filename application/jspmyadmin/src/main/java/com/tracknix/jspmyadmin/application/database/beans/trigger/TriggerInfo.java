package com.tracknix.jspmyadmin.application.database.beans.trigger;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Yugandhar Gangu
 */
@Data
public class TriggerInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String trigger_name;
    private String event_type;
    private String table_name;
    private String event_time;
    private String definer;
}
