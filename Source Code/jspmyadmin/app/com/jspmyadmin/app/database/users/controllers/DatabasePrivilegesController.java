/**
 * 
 */
package com.jspmyadmin.app.database.users.controllers;

import org.json.JSONObject;

import com.jspmyadmin.app.database.users.beans.UserListBean;
import com.jspmyadmin.app.database.users.logic.UserLogic;
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
@WebController(authentication = true, path = "/database_privileges.html")
public class DatabasePrivilegesController extends Controller<UserListBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(UserListBean bean, View view) throws Exception {
		try {
			super.fillBasics(bean);
			UserLogic userLogic = new UserLogic();
			userLogic.fillBean(bean);
			super.generateToken(bean);
		} catch (Exception e) {
			e.printStackTrace();
			bean.setError(FrameworkConstants.ONE);
		}
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_DATABASE_USERS_USERS);
	}

	@Override
	@ValidateToken
	protected void handlePost(UserListBean bean, View view) throws Exception {
		try {
			UserLogic userLogic = new UserLogic();
			userLogic.saveSchemaPrivileges(bean);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(FrameworkConstants.ERR_KEY, AppConstants.MSG_SAVE_SUCCESS);
			view.setToken(super.encode(jsonObject));
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(FrameworkConstants.ERR, e.getMessage());
			view.setToken(super.encode(jsonObject));
		}
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_DATABASE_PRIVILEGES);
	}

}
