package com.tracknix.jspmyadmin.application.database.beans.routine;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tracknix.jspmyadmin.framework.connection.util.DataTypes;
import com.tracknix.jspmyadmin.framework.connection.util.SecurityTypes;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.utils.Bean;
import com.tracknix.jspmyadmin.framework.web.utils.RequestAdaptor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;

/**
 * @author Yugandhar Gangu
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoutineBean extends Bean {

    private static final long serialVersionUID = 1L;

    private String name = null;
    private String definer = null;
    private String definer_name = null;
    private String[] param_types = null;
    private String[] params = null;
    private String[] param_data_types = null;
    private String[] lengths = null;
    private String return_type = null;
    private String return_length = null;
    private String comment = null;
    private String lang_sql = null;
    private String deterministic = null;
    private String sql_type = null;
    private String sql_security = null;
    private String body = null;
    private String action = null;
    private String alter = null;
    private String new_column = null;

    private String[] definer_list;
    private DataTypes.DataType[] data_types;
    private String[] sql_type_list;
    private String[] security_type_list;
}
