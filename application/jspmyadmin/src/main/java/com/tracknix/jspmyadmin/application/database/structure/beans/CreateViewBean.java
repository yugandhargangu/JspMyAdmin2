package com.tracknix.jspmyadmin.application.database.structure.beans;

import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.utils.Bean;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yugandhar Gangu
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CreateViewBean extends Bean {

    private static final long serialVersionUID = 1L;

    private String view_name = null;
    private String create_type = null;
    private String algorithm = null;
    private String definer = null;
    private String definer_name = null;
    private String sql_security = null;
    private String[] column_list = null;
    private String definition = null;
    private String check = null;
    private String action = null;

    private List<String> algorithm_list = new ArrayList<String>(Constants.Utils.ALGORITHM_LIST);
    private List<String> definer_list = new ArrayList<String>(Constants.Utils.DEFINER_LIST);
    private List<String> security_list = new ArrayList<String>(Constants.Utils.SECURITY_TYPE_LIST);
    private List<String> check_list = new ArrayList<String>(Constants.Utils.VIEW_CHECK_LIST);
}
