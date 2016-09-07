/**
 * 
 */
package com.jspmyadmin.app.server.users.controllers;

import java.sql.SQLException;

import org.json.JSONException;

import com.jspmyadmin.app.server.users.beans.UserListBean;
import com.jspmyadmin.app.server.users.logic.UserLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandleGetOrPost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.logic.EncodeHelper;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/15
 *
 */
@WebController(authentication = true, path = "/server_users.html", requestLevel = RequestLevel.SERVER)
public class ServerUserController {

	@Detect
	private EncodeHelper encodeObj;
	@Detect
	private View view;
	@Model
	private UserListBean bean;

	@HandleGetOrPost
	private void users() throws JSONException, EncodingException  {
		try {
			UserLogic userLogic = new UserLogic();
			userLogic.setEncodeObj(encodeObj);
			userLogic.fillBean(bean);
		} catch (SQLException e) {
			bean.setError(Constants.ONE);
		}
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_SERVER_USERS_USERS);
	}

}
