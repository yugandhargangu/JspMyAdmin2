/**
 * 
 */
package com.jspmyadmin.app.server.users.controllers;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.jspmyadmin.app.server.users.beans.SchemaPrivilegeBean;
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
@WebController(path = "/server_schema_privileges.html", requestLevel = RequestLevel.SERVER)
public class SchemaPrivilegesController {

	@Detect
	private EncodeHelper encodeObj;
	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private RedirectParams redirectParams;
	@Detect
	private View view;
	@Model
	private SchemaPrivilegeBean bean;

	@HandleGet
	private void loadPrivileges() throws JSONException, EncodingException {
		UserLogic userLogic = null;
		try {
			userLogic = new UserLogic();
			userLogic.setEncodeObj(encodeObj);
			userLogic.fillSchemaPrivileges(bean);
			bean.setToken(requestAdaptor.generateToken());
			view.setType(ViewType.FORWARD);
			view.setPath(AppConstants.JSP_SERVER_USERS_SCHEMA_PRIVILEGES);
		} catch (SQLException e) {
			redirectParams.put(Constants.ERR, e.getMessage());
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_SERVER_USERS);
		}
	}

	@HandlePost
	@ValidateToken
	private void savePrivileges() throws JSONException {
		UserLogic userLogic = null;
		try {
			userLogic = new UserLogic();
			userLogic.saveSchemaPrivileges(bean);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(Constants.USER, bean.getUser());
			view.setToken(jsonObject.toString());
			redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_SAVE_SUCCESS);
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_SERVER_SCHEMA_PRIVILEGES);
		} catch (SQLException e) {
			redirectParams.put(Constants.ERR, e.getMessage());
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_SERVER_USERS);
		}
	}

}
