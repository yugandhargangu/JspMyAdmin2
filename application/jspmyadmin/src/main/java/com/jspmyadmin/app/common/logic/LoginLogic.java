/**
 *
 */
package com.jspmyadmin.app.common.logic;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import com.jspmyadmin.app.common.beans.LoginBean;
import com.jspmyadmin.framework.connection.AbstractLogic;
import com.jspmyadmin.framework.connection.ApiConnection;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.web.utils.RequestAdaptor;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/05
 */
public class LoginLogic extends AbstractLogic {

    /**
     * @param bean
     * @return
     */
    public boolean isValidConnection(LoginBean bean) {
        ApiConnection apiConnection = null;
        HttpSession httpSession = RequestAdaptor.REQUEST_MAP.get(Thread.currentThread().getId()).getSession();
        try {
            httpSession.setAttribute(Constants.SESSION_HOST, bean.getHostname());
            httpSession.setAttribute(Constants.SESSION_PORT, bean.getPortnumber());
            httpSession.setAttribute(Constants.SESSION_USER, bean.getUsername());
            httpSession.setAttribute(Constants.SESSION_PASS, bean.getPassword());
            apiConnection = super.getConnection();
            if (apiConnection != null) {
                httpSession.setAttribute(Constants.SESSION, true);
                return true;
            }

        } catch (SQLException e) {
            RequestAdaptor.REQUEST_MAP.get(Thread.currentThread().getId()).setAttribute(Constants.MYSQL_ERROR, e.getMessage());
        } finally {
            close(apiConnection);
        }
        return false;
    }

}
