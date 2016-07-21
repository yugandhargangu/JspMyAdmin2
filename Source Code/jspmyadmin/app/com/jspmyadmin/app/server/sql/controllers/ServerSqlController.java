/**
 * 
 */
package com.jspmyadmin.app.server.sql.controllers;

import com.jspmyadmin.app.server.sql.beans.SqlBean;
import com.jspmyadmin.app.server.sql.logic.SqlLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/22
 *
 */
@WebController(authentication = true, path = "/server_sql.html")
public class ServerSqlController extends Controller<SqlBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(SqlBean bean, View view) throws Exception {

		SqlLogic sqlLogic = null;
		try {
			super.fillBasics(bean);
			sqlLogic = new SqlLogic();
			sqlLogic.fillBean(bean);
		} catch (Exception e) {
			bean.setError(e.getMessage());
		} finally {
			sqlLogic = null;
		}
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_SERVER_SQL_SQL);
	}

	@Override
	protected void handlePost(SqlBean bean, View view) throws Exception {
		this.handleGet(bean, view);
	}
}
