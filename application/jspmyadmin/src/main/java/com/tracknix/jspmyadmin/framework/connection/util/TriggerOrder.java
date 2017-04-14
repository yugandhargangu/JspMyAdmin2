package com.tracknix.jspmyadmin.framework.connection.util;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Yugandhar Gangu
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class TriggerOrder {
    private static final TriggerOrder TRIGGER_ORDER = new TriggerOrder();

    /**
     * @return TriggerOrder
     */
    public static TriggerOrder getInstance() {
        return TRIGGER_ORDER;
    }

    private final String[] trigger_order = new String[]{"FOLLOWS", "PRECEDES"};

    /**
     *
     */
    private TriggerOrder() {

    }

    /**
     * @return String[]
     */
    public String[] getTriggerOrders() {
        return this.trigger_order;
    }
}
