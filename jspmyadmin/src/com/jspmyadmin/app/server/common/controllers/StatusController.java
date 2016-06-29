/**
 * 
 */
package com.jspmyadmin.app.server.common.controllers;

import com.jspmyadmin.app.server.common.beans.StatusBean;
import com.jspmyadmin.app.server.common.logic.StatusLogic;
import com.jspmyadmin.framework.util.AppConstants;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/12
 *
 */
@WebController(authentication = true, path = "/server_status")
public class StatusController extends Controller<StatusBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(StatusBean bean, View view) throws Exception {
		StatusLogic statusLogic = null;
		try {
			super.clearForServer();
			statusLogic = new StatusLogic();
			statusLogic.setMessages(messages);
			statusLogic.fillBean(bean);
		} finally {
			statusLogic = null;
		}
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_SERVER_COMMON_STATUSLIST);
	}

	@Override
	protected void handlePost(StatusBean bean, View view) throws Exception {
		this.handleGet(bean, view);
	}

}
