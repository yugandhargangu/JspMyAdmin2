package com.tracknix.jspmyadmin.application.database.structure.beans;

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
public class TableListBean extends Bean {
    private String name = null;
    private String type = null;
    private TableInfo footerInfo = null;
    private List<TableInfo> table_list = null;
}
