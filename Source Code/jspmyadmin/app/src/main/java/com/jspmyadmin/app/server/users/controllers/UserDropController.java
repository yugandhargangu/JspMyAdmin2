/**
 * 
 */
package com.jspmyadmin.app.server.users.controllers;

import java.sql.SQLException;

import com.jspmyadmin.app.server.users.beans.UserListBean;
import com.jspmyadmin.app.server.users.logic.UserLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/15
 *
 */
@WebController(authentication = true, path = "/server_user_drop.html", requestLevel = RequestLevel.SERVER)
public class UserDropController extends Controller<UserListBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(UserListBean bean, View view) throws Exception {
		try {
			UserLogic userLogic = new UserLogic();
			userLogic.dropUser(bean.getToken());
			redirectParams.put(FrameworkConstants.MSG_KEY, AppConstants.MSG_USER_DROP_SUCCESS);
		} catch (SQLException e) {
			redirectParams.put(FrameworkConstants.ERR, e.getMessage());
		} catch (Exception e) {
			redirectParams.put(FrameworkConstants.ERR_KEY, AppConstants.ERR_INVALID_ACCESS);
		}
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_SERVER_USERS);
	}

	@Override
	protected void handlePost(UserListBean bean, View view) throws Exception {
		this.handleGet(bean, view);
	}
}
