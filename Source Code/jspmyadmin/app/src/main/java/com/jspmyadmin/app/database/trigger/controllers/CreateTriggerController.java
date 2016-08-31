/**
 * 
 */
package com.jspmyadmin.app.database.trigger.controllers;

import com.jspmyadmin.app.common.logic.DataLogic;
import com.jspmyadmin.app.database.trigger.beans.TriggerBean;
import com.jspmyadmin.app.database.trigger.logic.TriggerLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.FrameworkConstants;
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
@WebController(authentication = true, path = "/database_trigger_create.html", requestLevel = RequestLevel.DATABASE)
public class CreateTriggerController extends Controller<TriggerBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(TriggerBean bean, View view) throws Exception {
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_HOME);
	}

	@Override
	protected void handlePost(TriggerBean bean, View view) throws Exception {
		try {
			TriggerLogic triggerLogic = new TriggerLogic();
			if (triggerLogic.isExisted(bean.getTrigger_name(), bean.getRequest_db())) {
				redirectParams.put(FrameworkConstants.ERR,
						messages.getMessage(AppConstants.MSG_TRIGGER_ALREADY_EXISTED));
				view.setType(ViewType.REDIRECT);
				view.setPath(AppConstants.PATH_DATABASE_TRIGGERS);
				return;
			}
			bean.setOther_trigger_name_list(triggerLogic.getTriggerList(bean.getRequest_db()));
			bean.setDatabase_name(bean.getRequest_db());
			DataLogic dataLogic = new DataLogic();
			bean.setDatabase_name_list(dataLogic.getDatabaseList());
		} catch (Exception e) {
			redirectParams.put(FrameworkConstants.ERR, e.getMessage());
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_DATABASE_TRIGGERS);
			return;
		}

		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_DATABASE_TRIGGER_CREATETRIGGER);
	}

}
