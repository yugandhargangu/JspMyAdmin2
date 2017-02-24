/**
 * 
 */
package com.jspmyadmin.app.server.common.controllers;

import java.sql.SQLException;

import com.jspmyadmin.app.server.common.beans.StatusBean;
import com.jspmyadmin.app.server.common.logic.StatusLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandleGetOrPost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Messages;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/12
 *
 */
@WebController(authentication = true, path = "/server_status.html", requestLevel = RequestLevel.SERVER)
public class StatusController {

	@Detect
	private Messages messages;
	@Detect
	private View view;
	@Model
	private StatusBean bean;

	@HandleGetOrPost
	private void status() throws SQLException {
		StatusLogic statusLogic = new StatusLogic();
		statusLogic.setMessages(messages);
		statusLogic.fillBean(bean);
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_SERVER_COMMON_STATUSLIST);
	}

}
