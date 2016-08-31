/**
 * 
 */
package com.jspmyadmin.app.server.database.controllers;

import java.sql.SQLException;

import com.jspmyadmin.app.server.database.beans.DatabaseListBean;
import com.jspmyadmin.app.server.database.logic.DatabaseLogic;
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
 * @created_at 2016/02/10
 *
 */
@WebController(authentication = true, path = "/server_database_drop.html", requestLevel = RequestLevel.SERVER)
public class DatabaseDropController extends Controller<DatabaseListBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(DatabaseListBean bean, View view) throws Exception {
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_SERVER_DATABASES);
		redirectParams.put(FrameworkConstants.ERR_KEY, AppConstants.ERR_INVALID_ACCESS);
	}

	@Override
	@ValidateToken
	protected void handlePost(DatabaseListBean bean, View view) throws Exception {

		DatabaseLogic databaseLogic = null;
		try {
			databaseLogic = new DatabaseLogic();
			databaseLogic.dropDatabase(bean);
			redirectParams.put(FrameworkConstants.MSG_KEY, AppConstants.MSG_DROP_DB_SUCCESS);
		} catch (SQLException e) {
			redirectParams.put(FrameworkConstants.ERR, e.getMessage());
		}

		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_SERVER_DATABASES);
	}

}
