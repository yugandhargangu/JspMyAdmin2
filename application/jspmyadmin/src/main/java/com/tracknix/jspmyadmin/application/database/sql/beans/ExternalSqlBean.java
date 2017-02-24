package com.tracknix.jspmyadmin.application.database.sql.beans;

import com.tracknix.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 */
public class ExternalSqlBean extends Bean {

    private static final long serialVersionUID = 1L;

    private String edit_type = null;
    private String edit_name = null;

    /**
     * @return the edit_type
     */
    public String getEdit_type() {
        return edit_type;
    }

    /**
     * @param edit_type the edit_type to set
     */
    public void setEdit_type(String edit_type) {
        this.edit_type = edit_type;
    }

    /**
     * @return the edit_name
     */
    public String getEdit_name() {
        return edit_name;
    }

    /**
     * @param edit_name the edit_name to set
     */
    public void setEdit_name(String edit_name) {
        this.edit_name = edit_name;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
