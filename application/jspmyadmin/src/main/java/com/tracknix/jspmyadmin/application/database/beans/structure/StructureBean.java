package com.tracknix.jspmyadmin.application.database.beans.structure;

import com.tracknix.jspmyadmin.framework.web.utils.Bean;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Yugandhar Gangu
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StructureBean extends Bean {

    private static final long serialVersionUID = 1L;

    private String database_name;
    private String table_name;
    private String column_length;
    private String[] tables;
    private String sort;
    private String type;
    private String enable_checks;
    private String drop_checks;
    private String prefix;
    private String new_prefix;
    private String action;
}