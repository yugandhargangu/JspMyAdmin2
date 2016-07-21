/**
 * 
 */
package com.jspmyadmin.app.table.common.controllers;

import com.jspmyadmin.app.table.common.beans.MaintenanceBean;
import com.jspmyadmin.app.table.common.logic.MaintenanceLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/06
 *
 */
@WebController(authentication = true, path = "/table_maintenance.html")
public class MaintenanceController extends Controller<MaintenanceBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(MaintenanceBean bean, View view) throws Exception {

		super.generateToken(bean);
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_TABLE_COMMON_MAINTENANCE);
	}

	@Override
	protected void handlePost(MaintenanceBean bean, View view) throws Exception {
		MaintenanceLogic maintenanceLogic = null;
		try {
			super.fillBasics(bean);
			String table = super.checkForTable(bean);
			if (table == null) {
				table = (String) session.getAttribute(FrameworkConstants.SESSION_TABLE);
			}
			maintenanceLogic = new MaintenanceLogic(table);
			maintenanceLogic.fillBean(bean);
			view.setType(ViewType.FORWARD);
			view.setPath(AppConstants.JSP_TABLE_COMMON_MAINTENANCE);
		} catch (Exception e) {
			e.printStackTrace();
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_HOME);
		}
	}

}
