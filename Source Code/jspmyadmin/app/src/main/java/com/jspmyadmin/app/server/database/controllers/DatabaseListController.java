/**
 * 
 */
package com.jspmyadmin.app.server.database.controllers;

import java.sql.SQLException;

import org.json.JSONException;

import com.jspmyadmin.app.server.database.beans.DatabaseListBean;
import com.jspmyadmin.app.server.database.logic.DatabaseLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandleGetOrPost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.logic.EncodeHelper;
import com.jspmyadmin.framework.web.utils.RequestAdaptor;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/09
 *
 */
@WebController(authentication = true, path = "/server_databases.html", requestLevel = RequestLevel.SERVER)
public class DatabaseListController {

	@Detect
	private EncodeHelper encodeObj;
	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private View view;
	@Model
	private DatabaseListBean bean;

	@HandleGetOrPost
	private void databases() throws SQLException, JSONException, EncodingException {
		DatabaseLogic databaseLogic = new DatabaseLogic();
		databaseLogic.setEncodeObj(encodeObj);
		databaseLogic.fillBean(bean);
		bean.setToken(requestAdaptor.generateToken());
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_SERVER_DATABASE_DATABASELIST);
	}

}
