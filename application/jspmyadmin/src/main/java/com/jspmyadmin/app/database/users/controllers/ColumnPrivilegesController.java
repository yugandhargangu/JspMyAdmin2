/**
 * 
 */
package com.jspmyadmin.app.database.users.controllers;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.jspmyadmin.app.database.users.beans.ColumnPrivilegeBean;
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
@WebController(authentication = true, path = "/database_column_privileges.html", requestLevel = RequestLevel.DATABASE)
public class ColumnPrivilegesController {

	@Detect
	private EncodeHelper encodeObj;
	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private RedirectParams redirectParams;
	@Detect
	private View view;
	@Model
	private ColumnPrivilegeBean bean;

	@HandleGet
	private void loadPrivileges() throws JSONException, EncodingException {
		try {
			UserLogic userLogic = new UserLogic();
			userLogic.setEncodeObj(encodeObj);
			userLogic.fillColumnPrivileges(bean);
			bean.setToken(requestAdaptor.generateToken());
			view.setType(ViewType.FORWARD);
			view.setPath(AppConstants.JSP_DATABASE_USERS_COLUMNPRIVILEGES);
		} catch (SQLException e) {
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_DATABASE_PRIVILEGES);
		}
	}

	@HandlePost
	@ValidateToken
	private void savePrivileges() throws JSONException {
		try {
			UserLogic userLogic = new UserLogic();
			userLogic.saveColumnPrivileges(bean);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(Constants.USER, bean.getUser());
			if (bean.getTable() != null) {
				jsonObject.put(Constants.TABLE, bean.getTable());
			}
			view.setToken(jsonObject.toString());
			redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_SAVE_SUCCESS);
		} catch (SQLException e) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(Constants.USER, bean.getUser());
			view.setToken(jsonObject.toString());
			redirectParams.put(Constants.ERR, e.getMessage());
		}
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_DATABASE_COLUMN_PRIVILEGES);
	}

}
