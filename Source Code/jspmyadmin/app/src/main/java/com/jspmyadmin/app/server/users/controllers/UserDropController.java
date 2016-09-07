/**
 * 
 */
package com.jspmyadmin.app.server.users.controllers;

import java.sql.SQLException;

import org.json.JSONException;

import com.jspmyadmin.app.server.users.beans.UserListBean;
import com.jspmyadmin.app.server.users.logic.UserLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandleGetOrPost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.logic.EncodeHelper;
import com.jspmyadmin.framework.web.utils.RedirectParams;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/15
 *
 */
@WebController(authentication = true, path = "/server_user_drop.html", requestLevel = RequestLevel.SERVER)
public class UserDropController {

	@Detect
	private EncodeHelper encodeObj;
	@Detect
	private RedirectParams redirectParams;
	@Detect
	private View view;
	@Model
	private UserListBean bean;

	@HandleGetOrPost
	private void dropUser() {
		try {
			UserLogic userLogic = new UserLogic();
			userLogic.setEncodeObj(encodeObj);
			userLogic.dropUser(bean.getToken());
			redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_USER_DROP_SUCCESS);
		} catch (SQLException e) {
			redirectParams.put(Constants.ERR, e.getMessage());
		} catch (JSONException e) {
			redirectParams.put(Constants.ERR_KEY, AppConstants.ERR_INVALID_ACCESS);
		} catch (EncodingException e) {
			redirectParams.put(Constants.ERR_KEY, AppConstants.ERR_INVALID_ACCESS);
		}
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_SERVER_USERS);
	}
}
