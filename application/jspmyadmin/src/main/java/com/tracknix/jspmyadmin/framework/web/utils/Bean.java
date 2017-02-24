package com.tracknix.jspmyadmin.framework.web.utils;

import com.tracknix.jspmyadmin.framework.constants.Constants;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Yugandhar Gangu
 */
@Data
public abstract class Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String token;
    private String err_key;
    private String msg_key;
    private String err;
    private String msg;

    private String request_db;
    private String request_table;
    private String request_view;
    private String request_token;

    /**
     * @param val value
     * @return result
     */
    protected boolean isEmpty(String val) {
        return val == null || Constants.BLANK.equals(val.trim());
    }
}
