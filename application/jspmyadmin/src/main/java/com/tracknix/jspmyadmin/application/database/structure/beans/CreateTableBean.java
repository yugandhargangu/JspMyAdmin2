package com.tracknix.jspmyadmin.application.database.structure.beans;

import com.tracknix.jspmyadmin.application.common.logic.HomeLogic;
import com.tracknix.jspmyadmin.application.database.structure.logic.StructureLogic;
import com.tracknix.jspmyadmin.application.server.common.logic.EngineLogic;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.utils.Bean;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author Yugandhar Gangu
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CreateTableBean extends Bean {

    private static final long serialVersionUID = 1L;

    private String table_name = null;
    private String collation = null;
    private String engine = null;
    private String partition = null;
    private String partition_val = null;
    private String partitions = null;
    private String comment = null;
    private String[] columns = null;
    private String[] datatypes = null;
    private String[] lengths = null;
    private String[] defaults = null;
    private String[] collations = null;
    private String[] pks = null;
    private String[] nns = null;
    private String[] uqs = null;
    private String[] bins = null;
    private String[] uns = null;
    private String[] zfs = null;
    private String[] ais = null;
    private String[] comments = null;
    private String action = null;

    private Map<String, List<String>> data_types_map = null;
    private Map<String, List<String>> collation_map = null;
    private List<String> engine_list = null;
    private List<String> partition_list = Constants.Utils.PARTITION_LIST;
    private String partition_support = null;

    /**
     *
     */
    public CreateTableBean() {
        try {
            HomeLogic homeLogic = new HomeLogic();
            collation_map = homeLogic.getCollationMap();
            EngineLogic engineLogic = new EngineLogic();
            engine_list = engineLogic.getEngineList();
            StructureLogic structureLogic = new StructureLogic();
            partition_support = Boolean.toString(structureLogic.isSupportsPartition());
        } catch (SQLException e) {
        }
    }
}
