/**
 *
 */
package com.tracknix.jspmyadmin.application.common.beans;

import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.utils.Bean;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/09/05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InstallBean extends Bean {

    private static final long serialVersionUID = 3377912732082651254L;

    private String config_type = null;
    private String admin_name = null;
    private String admin_password = null;
    private String config_host = null;
    private String config_port = null;
    private String config_username = null;
    private String config_password = null;

    private Map<String, String> language_map = new LinkedHashMap<String, String>(Constants.Utils.LANGUAGE_MAP);

}
