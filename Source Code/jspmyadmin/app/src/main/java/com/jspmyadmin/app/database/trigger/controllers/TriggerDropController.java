/**
 * 
 */
package com.jspmyadmin.app.database.trigger.controllers;

import com.jspmyadmin.app.database.trigger.beans.TriggerListBean;
import com.jspmyadmin.app.database.trigger.logic.TriggerLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/30
 *
 */
@WebController(authentication = true, path = "/database_trigger_drop.html", requestLevel = RequestLevel.DATABASE)
public class TriggerDropController extends Controller<TriggerListBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(TriggerListBean bean, View view) throws Exception {
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_HOME);
	}

	@Override
	@ValidateToken
	protected void handlePost(TriggerListBean bean, View view) throws Exception {
		try {
			TriggerLogic triggerLogic = new TriggerLogic();
			triggerLogic.drop(bean);
			redirectParams.put(FrameworkConstants.MSG_KEY, AppConstants.MSG_TRIGGER_DROP_SUCCESS);
		} catch (Exception e) {
			redirectParams.put(FrameworkConstants.ERR, e.getMessage());
		}
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_DATABASE_TRIGGERS);
	}

}
