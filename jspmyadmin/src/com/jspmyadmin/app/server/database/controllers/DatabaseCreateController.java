/**
 * 
 */
package com.jspmyadmin.app.server.database.controllers;

import java.sql.SQLException;

import org.json.JSONObject;

import com.jspmyadmin.app.server.database.beans.DatabaseListBean;
import com.jspmyadmin.app.server.database.logic.DatabaseLogic;
import com.jspmyadmin.framework.util.AppConstants;
import com.jspmyadmin.framework.util.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/10
 *
 */
@WebController(authentication = true, path = "/server_database_create")
public class DatabaseCreateController extends Controller<DatabaseListBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(DatabaseListBean bean, View view) throws Exception {
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_SERVER_DATABASES);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(FrameworkConstants.ERR_KEY, AppConstants.ERR_INVALID_ACCESS);
		view.setToken(jsonObject.toString());
		jsonObject = null;
	}

	@Override
	@ValidateToken
	protected void handlePost(DatabaseListBean bean, View view) throws Exception {

		DatabaseLogic databaseLogic = null;
		JSONObject jsonObject = new JSONObject();
		try {
			databaseLogic = new DatabaseLogic();
			databaseLogic.createDatabase(bean);
			jsonObject.put(FrameworkConstants.MSG_KEY, AppConstants.MSG_CREATE_DB_SUCCESS);
		} catch (SQLException e) {
			jsonObject.put(FrameworkConstants.ERR, e.getMessage());
		} finally {
			databaseLogic = null;
		}
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_SERVER_DATABASES);
		view.setToken(super.encode(jsonObject.toString()));
		jsonObject = null;
	}

}
