package com.tracknix.jspmyadmin.application.database.sql.beans;

import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.utils.Bean;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Yugandhar Gangu
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SqlBean extends Bean {

    private static final long serialVersionUID = 1L;

    private String query = null;
    private String disable_fks = null;
    private String result = null;
    private String error = null;
    private List<String> column_list = null;
    private List<List<String>> fetch_list = null;

    private String max_rows = null;
    private String exec_time = null;
    private String hint_options = null;

    // extra setters

    /**
     * @return string
     */
    public String getTotal_data_count() {
        if (fetch_list == null) {
            return Constants.ZERO;
        }
        return String.valueOf(fetch_list.size());
    }

    /**
     * @return string
     */
    public String getColumn_count() {
        if (column_list == null) {
            return Constants.ZERO;
        }
        return String.valueOf(column_list.size());
    }
}
