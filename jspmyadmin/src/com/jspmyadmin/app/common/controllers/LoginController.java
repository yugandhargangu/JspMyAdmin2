/**
 * 
 */
package com.jspmyadmin.app.common.controllers;

import com.jspmyadmin.app.common.beans.LoginBean;
import com.jspmyadmin.app.common.logic.LoginLogic;
import com.jspmyadmin.framework.util.AppConstants;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/05
 *
 */
@WebController(authentication = false, path = "/login")
public class LoginController extends Controller<LoginBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(LoginBean bean, View view) throws Exception {
		super.generateToken(bean);
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_COMMON_LOGIN);
	}

	@Override
	@ValidateToken
	protected void handlePost(LoginBean bean, View view) throws Exception {
		LoginLogic loginLogic = null;
		try {
			loginLogic = new LoginLogic();
			if (loginLogic.isValidConnection(bean)) {
				view.setType(ViewType.REDIRECT);
				view.setPath(AppConstants.PATH_HOME);
			} else {
				view.setType(ViewType.FORWARD);
				bean.setErr_key(AppConstants.ERR_INVALID_SETTINGS);
				view.setPath(AppConstants.JSP_COMMON_LOGIN);
			}
		} finally {
			loginLogic = null;
		}
	}

}
