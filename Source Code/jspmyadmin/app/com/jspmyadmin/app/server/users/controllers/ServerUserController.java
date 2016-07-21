/**
 * 
 */
package com.jspmyadmin.app.server.users.controllers;

import com.jspmyadmin.app.server.users.beans.UserListBean;
import com.jspmyadmin.app.server.users.logic.UserLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/15
 *
 */
@WebController(authentication = true, path = "/server_users.html")
public class ServerUserController extends Controller<UserListBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(UserListBean bean, View view) throws Exception {
		try {
			super.fillBasics(bean);
			UserLogic userLogic = new UserLogic();
			userLogic.fillBean(bean);
		} catch (Exception e) {
			e.printStackTrace();
			bean.setError(FrameworkConstants.ONE);
		}
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_SERVER_USERS_USERS);
	}

	@Override
	protected void handlePost(UserListBean bean, View view) throws Exception {
		this.handleGet(bean, view);
	}

}
