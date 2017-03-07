package com.tracknix.jspmyadmin.application.database.users.beans;

import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.utils.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yugandhar Gangu
 */
public class TablePrivilegeBean extends Bean {

    private static final long serialVersionUID = 1L;

    private final List<String> privilege_table_list = new ArrayList<String>(
            Constants.Utils.PRIVILEGE_TABLE_LIST);

    private String user = null;
    private List<String> table_list = null;

    private String[] tables = null;
    private String[] privileges = null;
    private String fetch = null;
    private String table = null;
    private String column_token = null;

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
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
     * @return the privileges
     */
    public String[] getPrivileges() {
        return privileges;
    }

    /**
     * @param privileges the privileges to set
     */
    public void setPrivileges(String[] privileges) {
        this.privileges = privileges;
    }

    /**
     * @return the fetch
     */
    public String getFetch() {
        return fetch;
    }

    /**
     * @param fetch the fetch to set
     */
    public void setFetch(String fetch) {
        this.fetch = fetch;
    }

    /**
     * @return the privilege_table_list
     */
    public List<String> getPrivilege_table_list() {
        return privilege_table_list;
    }

    /**
     * @return the table
     */
    public String getTable() {
        return table;
    }

    /**
     * @param table the table to set
     */
    public void setTable(String table) {
        this.table = table;
    }

    /**
     * @return the column_token
     */
    public String getColumn_token() {
        return column_token;
    }

    /**
     * @param column_token the column_token to set
     */
    public void setColumn_token(String column_token) {
        this.column_token = column_token;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    // extra getters

    /**
     * @param index
     * @return
     */
    public String obj_rights_val(int index) {
        if (privileges != null && privileges.length > index) {
            return privileges[index];
        }
        return null;
    }
}
