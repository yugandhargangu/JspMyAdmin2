package com.tracknix.jspmyadmin.application.database.beans.structure;

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
public class StructureListBean extends Bean {
    private String name;
    private String type;
    private TableInfo footer_info;
    private List<TableInfo> table_list;
    private List<String> database_list;
    private String[] msgs;
}
