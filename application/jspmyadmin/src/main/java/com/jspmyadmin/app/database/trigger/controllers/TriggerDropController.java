/**
 * 
 */
package com.jspmyadmin.app.database.trigger.controllers;

import java.sql.SQLException;

import com.jspmyadmin.app.database.trigger.beans.TriggerListBean;
import com.jspmyadmin.app.database.trigger.logic.TriggerLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandlePost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.RedirectParams;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/30
 *
 */
@WebController(authentication = true, path = "/database_trigger_drop.html", requestLevel = RequestLevel.DATABASE)
public class TriggerDropController {

	@Detect
	private RedirectParams redirectParams;
	@Detect
	private View view;
	@Model
	private TriggerListBean bean;

	@HandlePost
	@ValidateToken
	private void dropTrigger() {
		try {
			TriggerLogic triggerLogic = new TriggerLogic();
			triggerLogic.drop(bean);
			redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_TRIGGER_DROP_SUCCESS);
		} catch (SQLException e) {
			redirectParams.put(Constants.ERR, e.getMessage());
		}
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_DATABASE_TRIGGERS);
	}

}
