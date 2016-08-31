/**
 * 
 */
package com.jspmyadmin.app.common.controllers;

import javax.servlet.http.HttpSession;

import com.jspmyadmin.app.common.beans.LanguageBean;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/05
 *
 */
@WebController(authentication = false, path = "/language.html", requestLevel = RequestLevel.DEFAULT)
public class LanguageController extends Controller<LanguageBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(LanguageBean bean, View view) throws Exception {
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_LOGIN);
	}

	@Override
	@ValidateToken
	protected void handlePost(LanguageBean bean, View view) throws Exception {
		if (bean.getLanguage() != null && !FrameworkConstants.BLANK.equals(bean.getLanguage().trim())) {
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute(FrameworkConstants.SESSION_LOCALE, bean.getLanguage());
		}
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_LOGIN);
	}

}
