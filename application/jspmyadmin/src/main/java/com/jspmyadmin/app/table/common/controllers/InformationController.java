/**
 * 
 */
package com.jspmyadmin.app.table.common.controllers;

import java.sql.SQLException;

import com.jspmyadmin.app.table.common.beans.InformationBean;
import com.jspmyadmin.app.table.common.logic.InformationLogic;
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
 * @created_at 2016/07/06
 *
 */
@WebController(authentication = true, path = "/table_info.html", requestLevel = RequestLevel.TABLE)
public class InformationController {

	@Detect
	private View view;
	@Model
	private InformationBean bean;

	@HandleGetOrPost
	private void tableInfo() {

		InformationLogic informationLogic = null;
		try {
			informationLogic = new InformationLogic(bean.getRequest_table());
			informationLogic.fillBean(bean);
			view.setType(ViewType.FORWARD);
			view.setPath(AppConstants.JSP_TABLE_COMMON_INFORMATION);
		} catch (SQLException e) {
			view.handleDefault();
		}
	}

}
