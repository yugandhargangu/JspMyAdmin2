package com.tracknix.jspmyadmin.application.database.routine.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tracknix.jspmyadmin.framework.web.utils.Bean;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Yugandhar Gangu
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoutinesBean extends Bean {

    private static final long serialVersionUID = 1L;

    private String name;
    private String type;
    private List<RoutineInfo> routine_info_list;
    private String[] msgs;
    private String[] routines;
}
