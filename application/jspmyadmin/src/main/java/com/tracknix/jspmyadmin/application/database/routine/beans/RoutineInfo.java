package com.tracknix.jspmyadmin.application.database.routine.beans;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Yugandhar Gangu
 */
@Data
public class RoutineInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String returns;
    private String routine_body;
    private String deterministic;
    private String data_access;
    private String security_type;
    private String definer;
    private String comments;
}
