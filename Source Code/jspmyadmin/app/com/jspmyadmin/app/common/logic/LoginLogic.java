/**
 * 
 */
package com.jspmyadmin.app.common.logic;

import javax.servlet.http.HttpSession;

import com.jspmyadmin.app.common.beans.LoginBean;
import com.jspmyadmin.framework.connection.AbstractLogic;
import com.jspmyadmin.framework.connection.ApiConnection;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.utils.DefaultServlet;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/05
 *
 */
public class LoginLogic extends AbstractLogic {

	/**
	 * 
	 * @param bean
	 * @return
	 */
	public boolean isValidConnection(LoginBean bean) {
		ApiConnection apiConnection = null;
		HttpSession httpSession = DefaultServlet.REQUEST_MAP.get(Thread.currentThread().getId()).getSession();
		try {
			httpSession.setAttribute(FrameworkConstants.SESSION_HOST, bean.getHostname());
			httpSession.setAttribute(FrameworkConstants.SESSION_PORT, bean.getPortnumber());
			httpSession.setAttribute(FrameworkConstants.SESSION_USER, bean.getUsername());
			httpSession.setAttribute(FrameworkConstants.SESSION_PASS, bean.getPassword());
			apiConnection = super.getConnection(false);
			if (apiConnection != null) {
				httpSession.setAttribute(FrameworkConstants.SESSION, true);
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(apiConnection);
		}
		return false;
	}

}
