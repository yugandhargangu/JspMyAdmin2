/**
 * 
 */
package com.jspmyadmin.app.database.sql.controllers;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.jspmyadmin.app.database.sql.beans.SqlBean;
import com.jspmyadmin.app.database.sql.logic.SqlLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.Constants;
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
@WebController(authentication = true, path = "/database_sql.html", requestLevel = RequestLevel.DATABASE)
public class DatabaseSqlController {

	@Detect
	private HttpSession session;
	@Detect
	private View view;
	@Model
	private SqlBean bean;

	@HandleGetOrPost
	private void sqlEditor() throws Exception {

		SqlLogic sqlLogic = null;
		try {
			boolean fetch = true;
			Object temp = session.getAttribute(Constants.QUERY);
			if (temp != null) {
				session.removeAttribute(Constants.QUERY);
				try {
					JSONObject jsonObject = new JSONObject(temp.toString());
					if (jsonObject.has(Constants.QUERY)) {
						bean.setQuery(jsonObject.getString(Constants.QUERY));
						fetch = false;
					}
				} catch (JSONException e) {
				}
			}
			sqlLogic = new SqlLogic();
			sqlLogic.fillBean(bean, fetch);
		} catch (SQLException e) {
			bean.setError(e.getMessage());
		}
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_DATABASE_SQL_SQL);
	}

}
