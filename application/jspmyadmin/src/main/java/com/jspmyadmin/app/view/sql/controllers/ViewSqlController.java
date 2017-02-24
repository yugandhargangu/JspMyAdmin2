/**
 * 
 */
package com.jspmyadmin.app.view.sql.controllers;

import java.sql.SQLException;

import org.json.JSONException;

import com.jspmyadmin.app.view.sql.beans.SqlBean;
import com.jspmyadmin.app.view.sql.logic.SqlLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandleGetOrPost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/22
 *
 */
@WebController(authentication = true, path = "/view_sql.html", requestLevel = RequestLevel.VIEW)
public class ViewSqlController {

	@Detect
	private View view;
	@Model
	private SqlBean bean;

	@HandleGetOrPost
	private void sqlEditor() throws JSONException {

		SqlLogic sqlLogic = null;
		try {
			sqlLogic = new SqlLogic();
			sqlLogic.fillBean(bean);
		} catch (SQLException e) {
			bean.setError(e.getMessage());
		} 
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_VIEW_SQL_SQL);
	}

}
