/**
 * 
 */
package com.jspmyadmin.app.common.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jspmyadmin.app.common.beans.LanguageBean;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandlePost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/05
 *
 */
@WebController(authentication = false, path = "/language.html", requestLevel = RequestLevel.DEFAULT)
public class LanguageController {

	@Detect
	private HttpServletRequest request;
	@Detect
	private View view;
	@Model
	private LanguageBean bean;

	@HandlePost
	@ValidateToken
	private void change() {
		if (bean.getLanguage() != null && !Constants.BLANK.equals(bean.getLanguage().trim())) {
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute(Constants.SESSION_LOCALE, bean.getLanguage());
		}
		view.setType(ViewType.REDIRECT);
		if (bean.getRedirect() != null && !Constants.BLANK.equals(bean.getRedirect())) {
			view.setPath(bean.getRedirect());
		} else {
			view.setPath(AppConstants.PATH_LOGIN);
		}
	}

}
