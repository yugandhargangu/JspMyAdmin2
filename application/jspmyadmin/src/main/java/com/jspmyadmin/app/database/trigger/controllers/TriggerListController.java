/**
 * 
 */
package com.jspmyadmin.app.database.trigger.controllers;

import java.sql.SQLException;

import com.jspmyadmin.app.database.trigger.beans.TriggerListBean;
import com.jspmyadmin.app.database.trigger.logic.TriggerLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandleGetOrPost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.RequestAdaptor;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/30
 *
 */
@WebController(authentication = true, path = "/database_triggers.html", requestLevel = RequestLevel.DATABASE)
public class TriggerListController {

	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private View view;
	@Model
	private TriggerListBean bean;

	@HandleGetOrPost
	private void triggers() throws EncodingException, SQLException {

		TriggerLogic triggerLogic = new TriggerLogic();
		triggerLogic.fillListBean(bean);
		bean.setToken(requestAdaptor.generateToken());
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_DATABASE_TRIGGER_TRIGGERS);
	}

}
