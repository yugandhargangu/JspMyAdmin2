package com.tracknix.jspmyadmin.application.database.trigger.beans;

import com.tracknix.jspmyadmin.framework.web.utils.Bean;

import java.util.List;

/**
 * @author Yugandhar Gangu
 */
public class TriggerListBean extends Bean {

    private static final long serialVersionUID = 1L;

    private List<TriggerInfo> trigger_list = null;
    private String total = null;
    private String[] triggers = null;

    /**
     * @return the trigger_list
     */
    public List<TriggerInfo> getTrigger_list() {
        return trigger_list;
    }

    /**
     * @param trigger_list the trigger_list to set
     */
    public void setTrigger_list(List<TriggerInfo> trigger_list) {
        this.trigger_list = trigger_list;
    }

    /**
     * @return the total
     */
    public String getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(String total) {
        this.total = total;
    }

    /**
     * @return the triggers
     */
    public String[] getTriggers() {
        return triggers;
    }

    /**
     * @param triggers the triggers to set
     */
    public void setTriggers(String[] triggers) {
        this.triggers = triggers;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
