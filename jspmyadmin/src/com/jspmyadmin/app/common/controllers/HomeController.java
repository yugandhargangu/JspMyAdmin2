/**
 * 
 */
package com.jspmyadmin.app.common.controllers;

import com.jspmyadmin.app.common.beans.HomeBean;
import com.jspmyadmin.app.common.logic.HomeLogic;
import com.jspmyadmin.framework.util.AppConstants;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/03
 *
 */
@WebController(authentication = true, path = "/home")
public class HomeController extends Controller<HomeBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(HomeBean bean, View view) throws Exception {
		HomeLogic homeLogic = null;
		try {
			super.clearForServer();
			super.generateToken(bean);
			homeLogic = new HomeLogic();
			homeLogic.fillBean(bean);
		} finally {
			homeLogic = null;
		}
		view.setPath(AppConstants.JSP_COMMON_HOME);
		view.setType(ViewType.FORWARD);
	}

	@Override
	protected void handlePost(HomeBean bean, View view) throws Exception {
		this.handleGet(bean, view);
	}

}
