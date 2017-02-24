/**
 * 
 */
package com.jspmyadmin.app.database.event.controllers;

import com.jspmyadmin.app.database.event.beans.EventBean;
import com.jspmyadmin.app.database.event.logic.EventLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandlePost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Messages;
import com.jspmyadmin.framework.web.utils.RequestAdaptor;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/17
 *
 */
@WebController(authentication = true, path = "/database_event_create.html", requestLevel = RequestLevel.DATABASE)
public class CreateEventController {

	@Detect
	private Messages messages;
	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private View view;
	@Model
	private EventBean bean;

	@HandlePost
	@ValidateToken
	private void createEvent() throws EncodingException {

		EventLogic eventLogic = new EventLogic();
		eventLogic.setMessages(messages);
		bean.setStart_interval(eventLogic.getStartInterval(bean.getInterval_list()));
		bean.setEnd_interval(eventLogic.getEndInterval(bean.getInterval_list()));
		bean.setToken(requestAdaptor.generateToken());
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_DATABASE_EVENT_CREATEEVENT);
	}

}
