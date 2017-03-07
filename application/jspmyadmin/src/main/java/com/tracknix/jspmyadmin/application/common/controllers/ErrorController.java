/**
 * 
 */
package com.tracknix.jspmyadmin.application.common.controllers;

import javax.servlet.http.HttpSession;

import com.tracknix.jspmyadmin.framework.constants.AppConstants;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.annotations.Handle;
import com.tracknix.jspmyadmin.framework.web.annotations.WebController;
import com.tracknix.jspmyadmin.framework.web.utils.MethodType;
import com.tracknix.jspmyadmin.framework.web.utils.RequestLevel;
import com.tracknix.jspmyadmin.framework.web.utils.View;
import com.tracknix.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/03
 * @modified_at 2017/01/17
 */
@WebController(authentication = false, requestLevel = RequestLevel.DEFAULT)
public class ErrorController {

	@Handle(path = "/connection_error.html", methodType = MethodType.ANY)
	public void load(HttpSession session, View view) {

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
