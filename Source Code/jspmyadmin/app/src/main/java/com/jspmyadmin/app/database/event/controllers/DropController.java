/**
 * 
 */
package com.jspmyadmin.app.database.event.controllers;

import com.jspmyadmin.app.database.event.beans.EventListBean;
import com.jspmyadmin.app.database.event.logic.EventLogic;
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
 * @created_at 2016/03/17
 *
 */
@WebController(authentication = true, path = "/database_event_drop.html", requestLevel = RequestLevel.DATABASE)
public class DropController extends Controller<EventListBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(EventListBean bean, View view) throws Exception {
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_HOME);
	}

	@Override
	@ValidateToken
	protected void handlePost(EventListBean bean, View view) throws Exception {
		EventLogic eventLogic = null;
		try {
			eventLogic = new EventLogic();
			eventLogic.dropEvent(bean);
			redirectParams.put(FrameworkConstants.MSG_KEY, AppConstants.MSG_EVENT_DROP_SUCCESS);
		} catch (Exception e) {
			redirectParams.put(FrameworkConstants.ERR, e.getMessage());
		}
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_DATABASE_EVENTS);
	}

}
