/**
 * 
 */
package com.jspmyadmin.app.server.users.controllers;

import org.json.JSONObject;

import com.jspmyadmin.app.server.users.beans.SchemaPrivilegeBean;
import com.jspmyadmin.app.server.users.logic.UserLogic;
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
@WebController(path = "/server_schema_privileges.html", requestLevel = RequestLevel.SERVER)
public class SchemaPrivilegesController extends Controller<SchemaPrivilegeBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(SchemaPrivilegeBean bean, View view) throws Exception {
		UserLogic userLogic = null;
		try {
			super.fillBasics(bean);
			userLogic = new UserLogic();
			userLogic.fillSchemaPrivileges(bean);
			bean.setToken(super.generateToken());
			view.setType(ViewType.FORWARD);
			view.setPath(AppConstants.JSP_SERVER_USERS_SCHEMA_PRIVILEGES);
		} catch (Exception e) {
			redirectParams.put(FrameworkConstants.ERR, e.getMessage());
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_SERVER_USERS);
		}
	}

	@Override
	@ValidateToken
	protected void handlePost(SchemaPrivilegeBean bean, View view) throws Exception {
		UserLogic userLogic = null;
		try {
			userLogic = new UserLogic();
			userLogic.saveSchemaPrivileges(bean);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(FrameworkConstants.USER, bean.getUser());
			view.setToken(jsonObject.toString());
			redirectParams.put(FrameworkConstants.ERR_KEY, AppConstants.MSG_SAVE_SUCCESS);
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_SERVER_SCHEMA_PRIVILEGES);
		} catch (Exception e) {
			redirectParams.put(FrameworkConstants.ERR, e.getMessage());
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_SERVER_USERS);
		}
	}

}
