/**
 * 
 */
package com.jspmyadmin.app.common.controllers;

import com.jspmyadmin.app.common.beans.LoginBean;
import com.jspmyadmin.app.common.logic.LoginLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandleGet;
import com.jspmyadmin.framework.web.annotations.HandlePost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.RequestAdaptor;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/05
 *
 */
@WebController(authentication = false, path = "/login.html", requestLevel = RequestLevel.DEFAULT)
public class LoginController {

	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private View view;
	@Model
	private LoginBean bean;

	@HandleGet
	private void load() throws EncodingException {
		bean.setToken(requestAdaptor.generateToken());
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_COMMON_LOGIN);
	}

	@HandlePost
	@ValidateToken
	private void check() {
		LoginLogic loginLogic = new LoginLogic();
		if (loginLogic.isValidConnection(bean)) {
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_HOME);
		} else {
			view.setType(ViewType.FORWARD);
			bean.setErr_key(AppConstants.ERR_INVALID_SETTINGS);
			view.setPath(AppConstants.JSP_COMMON_LOGIN);
		}
	}

}
