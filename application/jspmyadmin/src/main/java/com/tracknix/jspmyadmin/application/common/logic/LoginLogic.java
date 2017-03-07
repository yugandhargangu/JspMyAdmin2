/**
 *
 */
package com.tracknix.jspmyadmin.application.common.logic;

import com.tracknix.jspmyadmin.application.common.beans.LoginBean;
import com.tracknix.jspmyadmin.framework.connection.ApiConnection;
import com.tracknix.jspmyadmin.framework.connection.ConnectionHelper;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.annotations.Detect;
import com.tracknix.jspmyadmin.framework.web.annotations.LogicService;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/05
 * @modified_at 2017/01/17
 */
@LogicService
public class LoginLogic {

    @Detect
    private ConnectionHelper connectionHelper = null;
    @Detect
    private HttpSession httpSession;

    /**
     * @param bean
     * @return
     */
    public boolean isValidConnection(LoginBean bean) {
        ApiConnection apiConnection = null;
        try {
            httpSession.setAttribute(Constants.SESSION_HOST, bean.getHostname());
            httpSession.setAttribute(Constants.SESSION_PORT, bean.getPortnumber());
            httpSession.setAttribute(Constants.SESSION_USER, bean.getUsername());
            httpSession.setAttribute(Constants.SESSION_PASS, bean.getPassword());
            apiConnection = connectionHelper.getConnection();
            if (apiConnection != null) {
                httpSession.setAttribute(Constants.SESSION, true);
                return true;
            }

        } catch (SQLException e) {
        } finally {
            connectionHelper.close(apiConnection);
        }
        return false;
    }

}
