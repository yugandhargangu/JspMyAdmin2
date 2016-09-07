/**
 * 
 */
package com.jspmyadmin.app.common.controllers;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import com.jspmyadmin.app.common.beans.InstallBean;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandleGetOrPost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/03
 *
 */
@WebController(authentication = false, path = "/connection_error.html", requestLevel = RequestLevel.DEFAULT)
public class ErrorController {

	@Detect
	private HttpSession session;
	@Detect
	private View view;
	@Model
	private InstallBean bean;

	@HandleGetOrPost
	private void load() throws EncodingException, SQLException {

		if (session.getAttribute(Constants.SESSION_CONNECT) != null) {
			session.invalidate();
			view.setType(ViewType.FORWARD);
			view.setPath(AppConstants.JSP_COMMON_ERROR);
		} else {
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_HOME);
		}
	}

}
