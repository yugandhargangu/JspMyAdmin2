/**
 * 
 */
package com.jspmyadmin.app.table.common.controllers;

import java.sql.SQLException;

import com.jspmyadmin.app.table.common.beans.MaintenanceBean;
import com.jspmyadmin.app.table.common.logic.MaintenanceLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandleGet;
import com.jspmyadmin.framework.web.annotations.HandlePost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.RequestAdaptor;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/06
 *
 */
@WebController(authentication = true, path = "/table_maintenance.html", requestLevel = RequestLevel.TABLE)
public class MaintenanceController {

	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private View view;
	@Model
	private MaintenanceBean bean;

	@HandleGet
	private void loadMaintenance() throws EncodingException {

		bean.setToken(requestAdaptor.generateToken());
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_TABLE_COMMON_MAINTENANCE);
	}

	@HandlePost
	private void saveMaintenance() {
		MaintenanceLogic maintenanceLogic = null;
		try {
			maintenanceLogic = new MaintenanceLogic(bean.getRequest_table());
			maintenanceLogic.fillBean(bean);
			view.setType(ViewType.FORWARD);
			view.setPath(AppConstants.JSP_TABLE_COMMON_MAINTENANCE);
		} catch (SQLException e) {
			view.handleDefault();
		}
	}

}
