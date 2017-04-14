package com.tracknix.jspmyadmin.application.server.beans.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tracknix.jspmyadmin.framework.web.utils.Bean;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Yugandhar Gangu
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VariableBean extends Bean {

    private static final long serialVersionUID = 1L;

    private String name;
    private String value;
    private String[] scope;
}
