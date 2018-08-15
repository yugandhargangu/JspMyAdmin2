package com.tracknix.jspmyadmin.application.database.beans.trigger;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TriggerBean extends Bean {
    private static final long serialVersionUID = 1L;
    private String trigger_name;
    private String definer;
    private String definer_name;
    private String trigger_time;
    private String trigger_event;
    private String database_name;
    private String table_name;
    private String trigger_order;
    private String other_trigger_name;
    private String trigger_body;
    private String action;

    private String[] definer_list;
    private String[] trigger_time_list;
    private String[] trigger_event_list;
    private String[] trigger_order_list;
    private List<String> database_name_list;
    private List<String> other_trigger_name_list;
}
