/**
 * 
 */
package com.jspmyadmin.app.database.event.controllers;

import java.io.PrintWriter;

import org.json.JSONObject;

import com.jspmyadmin.app.database.event.beans.EventBean;
import com.jspmyadmin.app.database.event.logic.EventLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.ResponseBody;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/17
 *
 */
@WebController(authentication = true, path = "/database_event_create_post.text", requestLevel = RequestLevel.DATABASE)
public class CreateEventPostController extends Controller<EventBean> {

	private static final long serialVersionUID = 1L;

	@Override
	@ResponseBody
	protected void handleGet(EventBean bean, View view) throws Exception {

	}

	@Override
	@ValidateToken
	@ResponseBody
	protected void handlePost(EventBean bean, View view) throws Exception {
		JSONObject jsonObject = new JSONObject();
		try {
			EventLogic eventLogic = new EventLogic();
			String result = eventLogic.saveEvent(bean);
			jsonObject.put(FrameworkConstants.ERR, FrameworkConstants.BLANK);
			if (result != null) {
				jsonObject.put(FrameworkConstants.DATA, result.trim());
			} else {
				JSONObject msg = new JSONObject();
				msg.put(FrameworkConstants.MSG_KEY, AppConstants.MSG_EVENT_CREATE_SUCCESS);
				jsonObject.put(FrameworkConstants.MSG, super.encode(msg.toString()));
			}
		} catch (Exception e) {
			jsonObject.put(FrameworkConstants.ERR, e.getMessage());
		}
		jsonObject.put(FrameworkConstants.TOKEN, super.generateToken());
		PrintWriter writer = response.getWriter();
		try {
			writer.println(super.encrypt(jsonObject));
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

}
