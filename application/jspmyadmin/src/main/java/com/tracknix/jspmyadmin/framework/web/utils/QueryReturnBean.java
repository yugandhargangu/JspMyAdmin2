package com.tracknix.jspmyadmin.framework.web.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Yugandhar Gangu
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QueryReturnBean extends Bean {
    private static final long serialVersionUID = 1L;

    private String query;
}
