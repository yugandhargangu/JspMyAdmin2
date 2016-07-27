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
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/15
 *
 */
@WebController(path = "/server_schema_privileges.html")
public class SchemaPrivilegesController extends Controller<SchemaPrivilegeBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(SchemaPrivilegeBean bean, View view) throws Exception {
		UserLogic userLogic = null;
		try {
			super.fillBasics(bean);
			userLogic = new UserLogic();
			userLogic.fillSchemaPrivileges(bean);
			super.generateToken(bean);
			view.setType(ViewType.FORWARD);
			view.setPath(AppConstants.JSP_SERVER_USERS_SCHEMA_PRIVILEGES);
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(FrameworkConstants.ERR, e.getMessage());
			view.setToken(super.encode(jsonObject));
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
			jsonObject.put(FrameworkConstants.ERR_KEY, AppConstants.MSG_SAVE_SUCCESS);
			view.setToken(super.encode(jsonObject));
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_SERVER_SCHEMA_PRIVILEGES);
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
