package com.tracknix.jspmyadmin.application.database.structure.beans;

import com.tracknix.jspmyadmin.application.common.logic.SideBarLogic;
import com.tracknix.jspmyadmin.framework.web.utils.Bean;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Yugandhar Gangu
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StructureBean extends Bean {

    private static final long serialVersionUID = 1L;

    private String database_name = null;
    private String table_name = null;
    private String column_length = null;
    private String[] tables = null;
    private String sort = null;
    private String type = null;

    private String enable_checks = null;
    private String drop_checks = null;
    private String prefix = null;
    private String new_prefix = null;

    private TableInfo sortInfo = null;
    private TableInfo footerInfo = null;
    private List<TableInfo> table_list = null;
    private List<String> database_list = null;
    private String action = null;

    public StructureBean() {
        SideBarLogic sideBarLogic = new SideBarLogic();
        try {
            this.database_list = sideBarLogic.getDatabaseList();
        } catch (SQLException e) {
        }
    }
}