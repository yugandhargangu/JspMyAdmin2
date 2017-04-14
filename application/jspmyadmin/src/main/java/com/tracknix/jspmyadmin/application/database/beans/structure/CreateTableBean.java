package com.tracknix.jspmyadmin.application.database.beans.structure;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tracknix.jspmyadmin.framework.connection.util.DataTypes;
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
public class CreateTableBean extends Bean {

    private static final long serialVersionUID = 1L;

    private String table_name;
    private String collation;
    private String engine;
    private String partition;
    private String partition_val;
    private String partitions;
    private String comment;
    private String[] columns;
    private String[] datatypes;
    private String[] lengths;
    private String[] defaults;
    private String[] collations;
    private String[] pks;
    private String[] nns;
    private String[] uqs;
    private String[] bins;
    private String[] uns;
    private String[] zfs;
    private String[] ais;
    private String[] comments;
    private String action;

    private DataTypes.DataType[] datatype_list;
    private Map<String, List<String>> collation_map;
    private List<String> engine_list;
    private String[] partition_list;
    private boolean partition_support;
    private String[] msgs;
}
