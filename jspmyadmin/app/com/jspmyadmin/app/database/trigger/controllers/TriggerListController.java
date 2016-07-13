/**
 * 
 */
package com.jspmyadmin.app.database.trigger.controllers;

import com.jspmyadmin.app.database.trigger.beans.TriggerListBean;
import com.jspmyadmin.app.database.trigger.logic.TriggerLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/30
 *
 */
@WebController(authentication = true, path = "/database_triggers.html")
public class TriggerListController extends Controller<TriggerListBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(TriggerListBean bean, View view) throws Exception {

		TriggerLogic triggerLogic = null;
		try {
			super.fillBasics(bean);
			triggerLogic = new TriggerLogic();
			triggerLogic.fillListBean(bean);
			super.generateToken(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_DATABASE_TRIGGER_TRIGGERS);
	}

	@Override
	protected void handlePost(TriggerListBean bean, View view) throws Exception {
		this.handleGet(bean, view);
	}

}
