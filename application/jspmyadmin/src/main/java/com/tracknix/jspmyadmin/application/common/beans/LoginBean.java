/**
 *
 */
package com.tracknix.jspmyadmin.application.common.beans;


import com.tracknix.jspmyadmin.framework.web.utils.Bean;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginBean extends Bean {
    private boolean half_config = false;
    private String hostname = "localhost";
    private String portnumber = "3306";
    private String username = "root";
    private String password = "";
}
