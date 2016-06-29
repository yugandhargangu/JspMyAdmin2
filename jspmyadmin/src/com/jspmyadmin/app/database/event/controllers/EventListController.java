/**
 * 
 */
package com.jspmyadmin.app.database.event.controllers;

import com.jspmyadmin.app.database.event.beans.EventListBean;
import com.jspmyadmin.app.database.event.logic.EventLogic;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/17
 *
 */
@WebController(authentication = true, path = "/database_events")
public class EventListController extends Controller<EventListBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(EventListBean bean, View view) throws Exception {
		this.handlePost(bean, view);
	}

	@Override
	protected void handlePost(EventListBean bean, View view) throws Exception {
		super.fillBasics(bean);
		EventLogic eventLogic = new EventLogic();
		eventLogic.fillListBean(bean);
		super.generateToken(bean);
		view.setType(ViewType.FORWARD);
		view.setPath("database/event/Events.jsp");
	}

}
