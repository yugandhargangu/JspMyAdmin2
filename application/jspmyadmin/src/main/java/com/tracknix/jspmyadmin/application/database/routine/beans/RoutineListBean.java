package com.tracknix.jspmyadmin.application.database.routine.beans;

import com.tracknix.jspmyadmin.framework.web.utils.Bean;

import java.util.List;

/**
 * @author Yugandhar Gangu
 */
public class RoutineListBean extends Bean {

    private static final long serialVersionUID = 1L;

    private String[] routines = null;
    private String total = null;
    private List<RoutineInfo> routine_info_list = null;

    /**
     * @return the routines
     */
    public String[] getRoutines() {
        return routines;
    }

    /**
     * @param routines the routines to set
     */
    public void setRoutines(String[] routines) {
        this.routines = routines;
    }

    /**
     * @return the routine_info_list
     */
    public List<RoutineInfo> getRoutine_info_list() {
        return routine_info_list;
    }

    /**
     * @param routine_info_list the routine_info_list to set
     */
    public void setRoutine_info_list(List<RoutineInfo> routine_info_list) {
        this.routine_info_list = routine_info_list;
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
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
