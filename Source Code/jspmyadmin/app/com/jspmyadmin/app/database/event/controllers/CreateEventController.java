/**
 * 
 */
package com.jspmyadmin.app.database.event.controllers;

import com.jspmyadmin.app.database.event.beans.EventBean;
import com.jspmyadmin.app.database.event.logic.EventLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/17
 *
 */
@WebController(authentication = true, path = "/database_event_create.html")
public class CreateEventController extends Controller<EventBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(EventBean bean, View view) throws Exception {

	}

	@Override
	@ValidateToken
	protected void handlePost(EventBean bean, View view) throws Exception {

		bean.init();
		EventLogic eventLogic = new EventLogic();
		eventLogic.setMessages(messages);
		bean.setStart_interval(eventLogic.getStartInterval(bean.getInterval_list()));
		bean.setEnd_interval(eventLogic.getEndInterval(bean.getInterval_list()));
		super.generateToken(bean);
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_DATABASE_EVENT_CREATEEVENT);
	}

}
