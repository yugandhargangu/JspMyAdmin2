package com.tracknix.jspmyadmin.application.database.export.beans;

import com.tracknix.jspmyadmin.framework.web.utils.Bean;

import java.util.List;

/**
 * @author Yugandhar Gangu
 */
public class ExportBean extends Bean {

    private static final long serialVersionUID = 1L;

    private String[] tables = null;
    private String[] tables_data = null;
    private String filename = null;
    private String disable_fks = null;

    private String add_drop_db = null;
    private String add_drop_table = null;
    private String include_views = null;
    private String include_procedures = null;
    private String include_functions = null;
    private String include_events = null;
    private String include_triggers = null;

    private List<String> table_list = null;

    /**
     * @return the tables
     */
    public String[] getTables() {
        return tables;
    }

    /**
     * @param tables the tables to set
     */
    public void setTables(String[] tables) {
        this.tables = tables;
    }

    /**
     * @return the tables_data
     */
    public String[] getTables_data() {
        return tables_data;
    }

    /**
     * @param tables_data the tables_data to set
     */
    public void setTables_data(String[] tables_data) {
        this.tables_data = tables_data;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @return the disable_fks
     */
    public String getDisable_fks() {
        return disable_fks;
    }

    /**
     * @param disable_fks the disable_fks to set
     */
    public void setDisable_fks(String disable_fks) {
        this.disable_fks = disable_fks;
    }

    /**
     * @return the add_drop_db
     */
    public String getAdd_drop_db() {
        return add_drop_db;
    }

    /**
     * @param add_drop_db the add_drop_db to set
     */
    public void setAdd_drop_db(String add_drop_db) {
        this.add_drop_db = add_drop_db;
    }

    /**
     * @return the add_drop_table
     */
    public String getAdd_drop_table() {
        return add_drop_table;
    }

    /**
     * @param add_drop_table the add_drop_table to set
     */
    public void setAdd_drop_table(String add_drop_table) {
        this.add_drop_table = add_drop_table;
    }

    /**
     * @return the include_views
     */
    public String getInclude_views() {
        return include_views;
    }

    /**
     * @param include_views the include_views to set
     */
    public void setInclude_views(String include_views) {
        this.include_views = include_views;
    }

    /**
     * @return the include_procedures
     */
    public String getInclude_procedures() {
        return include_procedures;
    }

    /**
     * @param include_procedures the include_procedures to set
     */
    public void setInclude_procedures(String include_procedures) {
        this.include_procedures = include_procedures;
    }

    /**
     * @return the include_functions
     */
    public String getInclude_functions() {
        return include_functions;
    }

    /**
     * @param include_functions the include_functions to set
     */
    public void setInclude_functions(String include_functions) {
        this.include_functions = include_functions;
    }

    /**
     * @return the include_events
     */
    public String getInclude_events() {
        return include_events;
    }

    /**
     * @param include_events the include_events to set
     */
    public void setInclude_events(String include_events) {
        this.include_events = include_events;
    }

    /**
     * @return the include_triggers
     */
    public String getInclude_triggers() {
        return include_triggers;
    }

    /**
     * @param include_triggers the include_triggers to set
     */
    public void setInclude_triggers(String include_triggers) {
        this.include_triggers = include_triggers;
    }

    /**
     * @return the table_list
     */
    public List<String> getTable_list() {
        return table_list;
    }

    /**
     * @param table_list the table_list to set
     */
    public void setTable_list(List<String> table_list) {
        this.table_list = table_list;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
