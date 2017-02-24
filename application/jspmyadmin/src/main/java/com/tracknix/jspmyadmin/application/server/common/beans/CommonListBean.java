package com.tracknix.jspmyadmin.application.server.common.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
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
public class CommonListBean extends Bean {

    private static final long serialVersionUID = 1L;

    private String name;
    private String type;
    private String[] column_info;
    private List<String[]> data_list;
}