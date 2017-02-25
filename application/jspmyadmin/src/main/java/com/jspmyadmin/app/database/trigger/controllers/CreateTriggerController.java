/**
 * 
 */
package com.jspmyadmin.app.database.trigger.controllers;

import java.sql.SQLException;

import com.jspmyadmin.app.common.logic.DataLogic;
import com.jspmyadmin.app.database.trigger.beans.TriggerBean;
import com.jspmyadmin.app.database.trigger.logic.TriggerLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandlePost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Messages;
import com.jspmyadmin.framework.web.utils.RedirectParams;
import com.jspmyadmin.framework.web.utils.RequestAdaptor;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/30
 *
 */
@WebController(authentication = true, path = "/database_trigger_create.html", requestLevel = RequestLevel.DATABASE)
public class CreateTriggerController {

	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private RedirectParams redirectParams;
	@Detect
	private Messages messages;
	@Detect
	private View view;
	@Model
	private TriggerBean bean;

	@HandlePost
	@ValidateToken
	private void createTrigger() throws EncodingException {
		try {
			TriggerLogic triggerLogic = new TriggerLogic();
			if (triggerLogic.isExisted(bean.getTrigger_name(), bean.getRequest_db())) {
				redirectParams.put(Constants.ERR,
						messages.getMessage(AppConstants.MSG_TRIGGER_ALREADY_EXISTED));
				view.setType(ViewType.REDIRECT);
				view.setPath(AppConstants.PATH_DATABASE_TRIGGERS);
				return;
			}
			bean.setOther_trigger_name_list(triggerLogic.getTriggerList(bean.getRequest_db()));
			bean.setDatabase_name(bean.getRequest_db());
			DataLogic dataLogic = new DataLogic();
			bean.setDatabase_name_list(dataLogic.getDatabaseList());
			bean.setToken(requestAdaptor.generateToken());
		} catch (SQLException e) {
			redirectParams.put(Constants.ERR, e.getMessage());
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_DATABASE_TRIGGERS);
			return;
		}

		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_DATABASE_TRIGGER_CREATETRIGGER);
	}

}
