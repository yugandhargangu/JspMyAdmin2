/**
 * 
 */
package com.jspmyadmin.app.common.controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import com.jspmyadmin.app.common.beans.InstallBean;
import com.jspmyadmin.app.common.logic.InstallLogic;
import com.jspmyadmin.framework.connection.ConnectionFactory;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandleGet;
import com.jspmyadmin.framework.web.annotations.HandlePost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.logic.EncodeHelper;
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
@WebController(authentication = false, path = "/uninstall.html", requestLevel = RequestLevel.DEFAULT)
public class UninstallController {

	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private RedirectParams redirectParams;
	@Detect
	private HttpSession session;
	@Detect
	private EncodeHelper encodeObj;
	@Detect
	private View view;
	@Model
	private InstallBean bean;

	@HandleGet
	private void load() throws EncodingException, SQLException {

		bean.setToken(requestAdaptor.generateToken());
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_COMMON_UNINSTALL);
	}

	@HandlePost
	@ValidateToken
	private void check() {
		InstallLogic installLogic = new InstallLogic(encodeObj);
		try {
			if (!installLogic.uninstallConfig(bean)) {
				redirectParams.put(Constants.ERR_KEY, AppConstants.ERR_INVALID_SETTINGS);
				view.setType(ViewType.REDIRECT);
				view.setPath(AppConstants.PATH_UNINSTALL);
				return;
			} else {
				session.invalidate();
				ConnectionFactory.init();

			}
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		} catch (EncodingException e) {
		}
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_HOME);
	}
}
