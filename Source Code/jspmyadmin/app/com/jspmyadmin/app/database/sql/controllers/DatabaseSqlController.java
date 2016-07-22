/**
 * 
 */
package com.jspmyadmin.app.database.sql.controllers;

import org.json.JSONObject;

import com.jspmyadmin.app.database.sql.beans.SqlBean;
import com.jspmyadmin.app.database.sql.logic.SqlLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/22
 *
 */
@WebController(authentication = true, path = "/database_sql.html")
public class DatabaseSqlController extends Controller<SqlBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(SqlBean bean, View view) throws Exception {

		SqlLogic sqlLogic = null;
		try {
			super.fillBasics(bean);
			boolean fetch = true;
			Object temp = session.getAttribute(FrameworkConstants.QUERY);
			if (temp != null) {
				session.removeAttribute(FrameworkConstants.QUERY);
				try {
					JSONObject jsonObject = new JSONObject(temp.toString());
					if (jsonObject.has(FrameworkConstants.QUERY)) {
						bean.setQuery(jsonObject.getString(FrameworkConstants.QUERY));
						fetch = false;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			sqlLogic = new SqlLogic();
			sqlLogic.fillBean(bean, fetch);
		} catch (Exception e) {
			e.printStackTrace();
			bean.setError(e.getMessage());
		} finally {
			sqlLogic = null;
		}
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_DATABASE_SQL_SQL);
	}

	@Override
	protected void handlePost(SqlBean bean, View view) throws Exception {
		this.handleGet(bean, view);
	}
}
