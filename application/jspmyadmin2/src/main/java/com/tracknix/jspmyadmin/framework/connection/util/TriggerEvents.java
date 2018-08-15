package com.tracknix.jspmyadmin.framework.connection.util;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Yugandhar Gangu
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class TriggerEvents {
    private static final TriggerEvents TRIGGER_EVENTS = new TriggerEvents();

    /**
     * @return TriggerTime
     */
    public static TriggerEvents getInstance() {
        return TRIGGER_EVENTS;
    }

    private final String[] trigger_events = new String[]{"INSERT", "UPDATE", "DELETE"};

    /**
     *
     */
    private TriggerEvents() {

    }

    /**
     * @return String[]
     */
    public String[] getTriggerEvents() {
        return this.trigger_events;
    }
}
