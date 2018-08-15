/**
 *
 */
package com.tracknix.jspmyadmin.application.common.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.utils.Bean;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;


/**
 * @author Yugandhar Gangu
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HomeBean extends Bean {

    private static final long serialVersionUID = 1L;

    private String collation;
    private String language = Constants.DEFAULT_LOCALE;
    private String fontsize;

    private String db_server_name;
    private String db_server_type;
    private String db_server_version;
    private String db_server_protocol;
    private String db_server_user;
    private String db_server_charset;

    private String web_server_name;
    private String jdbc_version;
    private String java_version;
    private String servelt_version;
    private String jsp_version;

    private String jma_version = "2.1";

    private Map<String, List<String>> collation_map;
    private Map<String, String> language_map;
    private int[] fontsizes;

    private String action;
}