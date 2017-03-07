package com.tracknix.jspmyadmin.framework.connection;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Yugandhar Gangu
 */
@Data
public class ConnectionConfiguration implements Serializable {

    private static final long serialVersionUID = 4437096797557089362L;

    private String config_type = null;
    private String admin_name = null;
    private String admin_password = null;
    private String config_host = null;
    private String config_port = null;
    private String config_username = null;
    private String config_password = null;
}
