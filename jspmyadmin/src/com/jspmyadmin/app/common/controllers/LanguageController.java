/**
 * 
 */
package com.jspmyadmin.app.common.controllers;

import javax.servlet.http.HttpSession;

import com.jspmyadmin.app.common.beans.LanguageBean;
import com.jspmyadmin.framework.util.FrameworkConstants;
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
@WebController(authentication = false, path = "/language")
public class LanguageController extends Controller<LanguageBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(LanguageBean bean, View view) throws Exception {
		view.setType(ViewType.REDIRECT);
		view.setPath("/login");
	}

	@Override
	@ValidateToken
	protected void handlePost(LanguageBean bean, View view) throws Exception {
		view.setType(ViewType.REDIRECT);
		if (bean.getUrl() != null) {
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute(FrameworkConstants.SESSION_LOCALE, bean.getLanguage());
			view.setPath(bean.getUrl().substring(
					bean.getUrl().lastIndexOf(request.getContextPath()) + request.getContextPath().length(),
					bean.getUrl().length()));
		} else {
			view.setPath("/login");
		}

	}

}
