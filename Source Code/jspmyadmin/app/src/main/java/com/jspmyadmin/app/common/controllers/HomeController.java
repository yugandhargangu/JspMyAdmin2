/**
 * 
 */
package com.jspmyadmin.app.common.controllers;

import com.jspmyadmin.app.common.beans.HomeBean;
import com.jspmyadmin.app.common.logic.HomeLogic;
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
 * @created_at 2016/02/03
 *
 */
@WebController(authentication = true, path = "/home.html", requestLevel = RequestLevel.SERVER)
public class HomeController extends Controller<HomeBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(HomeBean bean, View view) throws Exception {
		HomeLogic homeLogic = null;
		try {
			bean.setToken(super.generateToken());
			homeLogic = new HomeLogic();
			homeLogic.fillBean(bean);
		} finally {
			homeLogic = null;
		}
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_COMMON_HOME);
	}

	@Override
	@ValidateToken
	protected void handlePost(HomeBean bean, View view) throws Exception {

		try {
			if (bean.getAction() != null && !FrameworkConstants.BLANK.equals(bean.getAction().trim())) {
				int i = _getInteger(bean.getAction());
				switch (i) {
				case 1:
					if (bean.getCollation() != null && !FrameworkConstants.BLANK.equals(bean.getCollation().trim())) {
						HomeLogic homeLogic = new HomeLogic();
						homeLogic.saveServerCollation(bean.getCollation());
					}
					break;
				case 2:
					if (bean.getLanguage() != null && !FrameworkConstants.BLANK.equals(bean.getLanguage().trim())) {
						session.setAttribute(FrameworkConstants.SESSION_LOCALE, bean.getLanguage());
					}
					break;
				case 3:
					if (bean.getFontsize() != null && !FrameworkConstants.BLANK.equals(bean.getFontsize().trim())) {
						session.setAttribute(FrameworkConstants.SESSION_FONTSIZE, bean.getFontsize());
					}
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			redirectParams.put(FrameworkConstants.ERR, e.getMessage());
		}
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_HOME);
	}

	/**
	 * 
	 * @param val
	 * @return
	 */
	private int _getInteger(String val) {
		try {
			return Integer.parseInt(val);
		} catch (Exception e) {
		}
		return 0;
	}
}
