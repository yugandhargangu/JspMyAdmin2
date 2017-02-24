package com.tracknix.jspmyadmin.application.server.database.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tracknix.jspmyadmin.framework.web.utils.Bean;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

/**
 * @author Yugandhar Gangu
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DatabaseListBean extends Bean {

    private static final long serialVersionUID = 1L;

    private String name;
    private String type;
    private DatabaseInfo footer_info;
    private Map<String, List<String>> collation_map;
    private List<DatabaseInfo> database_list;
    private int count = 0;
}