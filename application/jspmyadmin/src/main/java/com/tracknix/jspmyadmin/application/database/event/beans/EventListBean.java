package com.tracknix.jspmyadmin.application.database.event.beans;

import com.tracknix.jspmyadmin.framework.web.utils.Bean;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Yugandhar Gangu
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EventListBean extends Bean {

    private static final long serialVersionUID = 1L;

    private List<EventInfo> event_list = null;
    private String total = null;
    private String[] events = null;
    private String new_event = null;
}
