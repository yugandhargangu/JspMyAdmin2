package com.tracknix.jspmyadmin.application.database.structure.beans;

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
public class CreateViewBean extends Bean {

    private static final long serialVersionUID = 1L;

    private String view_name;
    private String create_type;
    private String algorithm;
    private String definer;
    private String definer_name;
    private String sql_security;
    private String[] column_list;
    private String definition;
    private String check;
    private String action;

    private String[] algorithm_list;
    private String[] definer_list;
    private String[] security_list;
    private String[] check_list;
    private String[] msgs;
}
