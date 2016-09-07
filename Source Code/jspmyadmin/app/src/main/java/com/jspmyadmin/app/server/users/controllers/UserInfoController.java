/**
 * 
 */
package com.jspmyadmin.app.server.users.controllers;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.jspmyadmin.app.server.users.beans.UserInfoBean;
import com.jspmyadmin.app.server.users.logic.UserLogic;
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
 * @created_at 2016/07/15
 *
 */
@WebController(authentication = true, path = "/server_user_info.html", requestLevel = RequestLevel.SERVER)
public class UserInfoController {

	@Detect
	private EncodeHelper encodeObj;
	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private RedirectParams redirectParams;
	@Detect
	private View view;
	@Model
	private UserInfoBean bean;

	@HandleGet
	private void loadUserInfo() throws JSONException, EncodingException, SQLException {
		UserLogic userLogic = new UserLogic();
		userLogic.setEncodeObj(encodeObj);
		userLogic.fillUserInfo(bean);
		bean.setToken(requestAdaptor.generateToken());
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_SERVER_USERS_USER);
	}

	@HandlePost
	@ValidateToken
	private void saveUserInfo() throws JSONException {
		try {
			UserLogic userLogic = new UserLogic();
			userLogic.saveUserInfo(bean);
			if (bean.getOld_user() == null || Constants.BLANK.equals(bean.getOld_user())) {
				view.setType(ViewType.REDIRECT);
				view.setPath(AppConstants.PATH_SERVER_GLOBAL_PRIVILEGES);
				JSONObject jsonObject = new JSONObject();
				jsonObject.put(Constants.USER, bean.getUser());
				view.setToken(jsonObject.toString());
			} else {
				view.setType(ViewType.REDIRECT);
				view.setPath(AppConstants.PATH_SERVER_USERS);
			}
		} catch (SQLException e) {
			redirectParams.put(Constants.ERR, e.getMessage());
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_SERVER_USERS);
		}
	}

}
