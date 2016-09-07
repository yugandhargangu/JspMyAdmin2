/**
 * 
 */
package com.jspmyadmin.app.database.users.controllers;

import java.sql.SQLException;

import org.json.JSONException;

import com.jspmyadmin.app.database.users.beans.UserListBean;
import com.jspmyadmin.app.database.users.logic.UserLogic;
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
@WebController(authentication = true, path = "/database_privileges.html", requestLevel = RequestLevel.DATABASE)
public class DatabasePrivilegesController {

	@Detect
	private EncodeHelper encodeObj;
	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private RedirectParams redirectParams;
	@Detect
	private View view;
	@Model
	private UserListBean bean;

	@HandleGet
	private void loadPrivileges() throws JSONException, EncodingException {
		try {
			UserLogic userLogic = new UserLogic();
			userLogic.setEncodeObj(encodeObj);
			userLogic.fillBean(bean);
			bean.setToken(requestAdaptor.generateToken());
		} catch (SQLException e) {
			bean.setError(Constants.ONE);
		}
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_DATABASE_USERS_USERS);
	}

	@HandlePost
	@ValidateToken
	private void savePrivileges() {
		try {
			UserLogic userLogic = new UserLogic();
			userLogic.saveSchemaPrivileges(bean);
			redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_SAVE_SUCCESS);
		} catch (SQLException e) {
			redirectParams.put(Constants.ERR, e.getMessage());
		}
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_DATABASE_PRIVILEGES);
	}

}
