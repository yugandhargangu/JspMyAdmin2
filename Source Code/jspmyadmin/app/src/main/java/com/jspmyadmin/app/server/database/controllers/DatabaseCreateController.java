/**
 * 
 */
package com.jspmyadmin.app.server.database.controllers;

import java.sql.SQLException;

import com.jspmyadmin.app.server.database.beans.DatabaseListBean;
import com.jspmyadmin.app.server.database.logic.DatabaseLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandlePost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.RedirectParams;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/10
 *
 */
@WebController(authentication = true, path = "/server_database_create.html", requestLevel = RequestLevel.SERVER)
public class DatabaseCreateController {

	@Detect
	private RedirectParams redirectParams;
	@Detect
	private View view;
	@Model
	private DatabaseListBean bean;

	@HandlePost
	@ValidateToken
	private void createDatabase() {

		DatabaseLogic databaseLogic = null;
		try {
			databaseLogic = new DatabaseLogic();
			databaseLogic.createDatabase(bean);
			redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_CREATE_DB_SUCCESS);
		} catch (SQLException e) {
			redirectParams.put(Constants.ERR, e.getMessage());
		}
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_SERVER_DATABASES);
	}

}
