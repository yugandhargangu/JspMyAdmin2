/**
 * 
 */
package com.jspmyadmin.app.common.controllers;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import com.jspmyadmin.app.common.beans.HomeBean;
import com.jspmyadmin.app.common.logic.HomeLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandleGet;
import com.jspmyadmin.framework.web.annotations.HandlePost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.RedirectParams;
import com.jspmyadmin.framework.web.utils.RequestAdaptor;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/03
 *
 */
@WebController(authentication = true, path = "/home.html", requestLevel = RequestLevel.SERVER)
public class HomeController {

	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private HttpSession session;
	@Detect
	private RedirectParams redirectParams;
	@Detect
	private View view;
	@Model
	private HomeBean bean;

	@HandleGet
	private void home() throws EncodingException, SQLException {
		HomeLogic homeLogic = null;
		try {
			bean.setToken(requestAdaptor.generateToken());
			homeLogic = new HomeLogic();
			homeLogic.fillBean(bean);
		} finally {
			homeLogic = null;
		}
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_COMMON_HOME);
	}

	@HandlePost
	@ValidateToken
	private void save() {

		try {
			if (bean.getAction() != null && !Constants.BLANK.equals(bean.getAction().trim())) {
				int i = _getInteger(bean.getAction());
				switch (i) {
				case 1:
					if (bean.getCollation() != null && !Constants.BLANK.equals(bean.getCollation().trim())) {
						HomeLogic homeLogic = new HomeLogic();
						homeLogic.saveServerCollation(bean.getCollation());
					}
					break;
				case 2:
					if (bean.getLanguage() != null && !Constants.BLANK.equals(bean.getLanguage().trim())) {
						session.setAttribute(Constants.SESSION_LOCALE, bean.getLanguage());
					}
					break;
				case 3:
					if (bean.getFontsize() != null && !Constants.BLANK.equals(bean.getFontsize().trim())) {
						session.setAttribute(Constants.SESSION_FONTSIZE, bean.getFontsize());
					}
					break;
				default:
					break;
				}
			}
		} catch (SQLException e) {
			redirectParams.put(Constants.ERR, e.getMessage());
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
