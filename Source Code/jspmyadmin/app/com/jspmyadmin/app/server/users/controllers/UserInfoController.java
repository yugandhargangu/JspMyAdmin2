/**
 * 
 */
package com.jspmyadmin.app.server.users.controllers;

import org.json.JSONObject;

import com.jspmyadmin.app.server.users.beans.UserInfoBean;
import com.jspmyadmin.app.server.users.logic.UserLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/15
 *
 */
@WebController(authentication = true, path = "/server_user_info.html")
public class UserInfoController extends Controller<UserInfoBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(UserInfoBean bean, View view) throws Exception {
		try {
			super.fillBasics(bean);
			UserLogic userLogic = new UserLogic();
			userLogic.fillUserInfo(bean);
			super.generateToken(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_SERVER_USERS_USER);
	}

	@Override
	@ValidateToken
	protected void handlePost(UserInfoBean bean, View view) throws Exception {
		try {
			super.fillBasics(bean);
			UserLogic userLogic = new UserLogic();
			userLogic.saveUserInfo(bean);
			if (bean.getOld_user() == null || FrameworkConstants.BLANK.equals(bean.getOld_user())) {
				view.setType(ViewType.REDIRECT);
				view.setPath(AppConstants.PATH_SERVER_GLOBAL_PRIVILEGES);
				JSONObject jsonObject = new JSONObject();
				jsonObject.put(FrameworkConstants.USER, bean.getUser());
				view.setToken(super.encode(jsonObject));
			} else {
				view.setType(ViewType.REDIRECT);
				view.setPath(AppConstants.PATH_SERVER_USERS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(FrameworkConstants.ERR, e.getMessage());
			view.setToken(super.encode(jsonObject));
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_SERVER_USERS);
		}
	}

}
