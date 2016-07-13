/**
 * 
 */
package com.jspmyadmin.app.table.common.controllers;

import com.jspmyadmin.app.table.common.beans.InformationBean;
import com.jspmyadmin.app.table.common.logic.InformationLogic;
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
@WebController(authentication = true, path = "/table_info.html")
public class InformationController extends Controller<InformationBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(InformationBean bean, View view) throws Exception {

		InformationLogic informationLogic = null;
		try {
			super.fillBasics(bean);
			String table = super.checkForTable(bean);
			if (table == null) {
				table = (String) session.getAttribute(FrameworkConstants.SESSION_TABLE);
			}
			informationLogic = new InformationLogic(table);
			informationLogic.fillBean(bean);
			view.setType(ViewType.FORWARD);
			view.setPath(AppConstants.JSP_TABLE_COMMON_INFORMATION);
		} catch (Exception e) {
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_HOME);
		}
	}

	@Override
	protected void handlePost(InformationBean bean, View view) throws Exception {
		this.handleGet(bean, view);
	}

}
