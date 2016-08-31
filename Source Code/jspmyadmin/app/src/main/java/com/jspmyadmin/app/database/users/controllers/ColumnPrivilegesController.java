/**
 * 
 */
package com.jspmyadmin.app.database.users.controllers;

import org.json.JSONObject;

import com.jspmyadmin.app.database.users.beans.ColumnPrivilegeBean;
import com.jspmyadmin.app.database.users.logic.UserLogic;
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
 * @created_at 2016/07/15
 *
 */
@WebController(authentication = true, path = "/database_column_privileges.html", requestLevel = RequestLevel.DATABASE)
public class ColumnPrivilegesController extends Controller<ColumnPrivilegeBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(ColumnPrivilegeBean bean, View view) throws Exception {
		try {
			super.fillBasics(bean);
			UserLogic userLogic = new UserLogic();
			userLogic.fillColumnPrivileges(bean);
			bean.setToken(super.generateToken());
			view.setType(ViewType.FORWARD);
			view.setPath(AppConstants.JSP_DATABASE_USERS_COLUMNPRIVILEGES);
		} catch (Exception e) {
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_DATABASE_PRIVILEGES);
		}
	}

	@Override
	@ValidateToken
	protected void handlePost(ColumnPrivilegeBean bean, View view) throws Exception {
		try {
			UserLogic userLogic = new UserLogic();
			userLogic.saveColumnPrivileges(bean);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(FrameworkConstants.USER, bean.getUser());
			if (bean.getTable() != null) {
				jsonObject.put(FrameworkConstants.TABLE, bean.getTable());
			}
			view.setToken(jsonObject.toString());
			redirectParams.put(FrameworkConstants.ERR_KEY, AppConstants.MSG_SAVE_SUCCESS);
		} catch (Exception e) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(FrameworkConstants.USER, bean.getUser());
			view.setToken(jsonObject.toString());
			redirectParams.put(FrameworkConstants.ERR, e.getMessage());
		}
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_DATABASE_COLUMN_PRIVILEGES);
	}

}
