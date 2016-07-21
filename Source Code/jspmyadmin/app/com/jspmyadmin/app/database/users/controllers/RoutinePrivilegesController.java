/**
 * 
 */
package com.jspmyadmin.app.database.users.controllers;

import org.json.JSONObject;

import com.jspmyadmin.app.database.users.beans.RoutinePrivilegeBean;
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
@WebController(authentication = true, path = "/database_routine_privileges.html")
public class RoutinePrivilegesController extends Controller<RoutinePrivilegeBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(RoutinePrivilegeBean bean, View view) throws Exception {
		try {
			super.fillBasics(bean);
			UserLogic userLogic = new UserLogic();
			userLogic.fillRoutinePrivileges(bean);
			super.generateToken(bean);
			view.setType(ViewType.FORWARD);
			view.setPath(AppConstants.JSP_DATABASE_USERS_ROUTINEPRIVILEGES);
		} catch (Exception e) {
			e.printStackTrace();
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_DATABASE_PRIVILEGES);
		}
	}

	@Override
	@ValidateToken
	protected void handlePost(RoutinePrivilegeBean bean, View view) throws Exception {
		if (!FrameworkConstants.ONE.equals(bean.getFetch()) && !FrameworkConstants.TWO.equals(bean.getFetch())) {
			this.handleGet(bean, view);
			return;
		}
		try {
			UserLogic userLogic = new UserLogic();
			userLogic.saveRoutinePrivileges(bean);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(FrameworkConstants.USER, bean.getUser());
			view.setToken(super.encode(jsonObject));
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(FrameworkConstants.USER, bean.getUser());
			jsonObject.put(FrameworkConstants.ERR, e.getMessage());
			view.setToken(super.encode(jsonObject));
		}
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_DATABASE_ROUTINE_PRIVILEGES);
	}

}
