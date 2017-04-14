package com.tracknix.jspmyadmin.framework.connection.util;

/**
 * @author Yugandhar Gangu
 */
public class EventIntervals {
    private static final EventIntervals EVENT_INTERVALS = new EventIntervals();

    /**
     * @return EventIntervals
     */
    public static EventIntervals getInstance() {
        return EVENT_INTERVALS;
    }

    private final String[] event_intervals;

    /**
     * Constructor
     */
    private EventIntervals() {
        event_intervals = new String[]{"DAY", "DAY_HOUR", "DAY_MINUTE", "DAY_SECOND", "HOUR",
                "HOUR_MINUTE", "HOUR_SECOND", "MINUTE", "MINUTE_SECOND", "MONTH", "QUARTER",
                "SECOND", "WEEK", "YEAR", "YEAR_MONTH"};
    }

    /**
     * @return String[]
     */
    public String[] getEventIntervals() {
        return event_intervals;
    }
}
