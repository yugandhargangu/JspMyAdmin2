package com.tracknix.jspmyadmin.application.database.beans.event;

import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.utils.Bean;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yugandhar Gangu
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EventBean extends Bean {

    private static final long serialVersionUID = 1L;

    private String definer = null;
    private String definer_name = null;
    private String not_exists = null;
    private String event_name = null;
    private String schedule_type = null;
    private String interval_quantity = null;
    private String interval = null;
    private String start_date_type = null;
    private String start_date = null;
    private String[] start_date_interval_quantity = null;
    private String[] start_date_interval = null;
    private String end_date_type = null;
    private String end_date = null;
    private String[] end_date_interval_quantity = null;
    private String[] end_date_interval = null;
    private String preserve = null;
    private String status = null;
    private String comment = null;
    private String body = null;

    private String start_interval = null;
    private String end_interval = null;
    private String action = null;

    private String[] interval_list;
    private String[] definer_list;
}
