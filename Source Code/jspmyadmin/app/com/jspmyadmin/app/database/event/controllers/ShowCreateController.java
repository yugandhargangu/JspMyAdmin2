/**
 * 
 */
package com.jspmyadmin.app.database.event.controllers;

import java.io.PrintWriter;

import org.json.JSONObject;

import com.jspmyadmin.app.database.event.beans.EventListBean;
import com.jspmyadmin.app.database.event.logic.EventLogic;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.ResponseBody;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.View;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/18
 *
 */
@WebController(authentication = true, path = "/database_event_show_create.text")
public class ShowCreateController extends Controller<EventListBean> {

	private static final long serialVersionUID = 1L;

	@Override
	@ResponseBody
	protected void handleGet(EventListBean bean, View view) throws Exception {
	}

	@Override
	@ResponseBody
	@ValidateToken
	protected void handlePost(EventListBean bean, View view) throws Exception {
		JSONObject jsonObject = new JSONObject();
		EventLogic eventLogic = null;
		try {
			eventLogic = new EventLogic();
			String result = eventLogic.getShowCreate(bean);
			jsonObject.put(FrameworkConstants.ERR, FrameworkConstants.BLANK);
			jsonObject.put(FrameworkConstants.DATA, result);
		} catch (Exception e) {
			jsonObject.put(FrameworkConstants.ERR, e.getMessage());
		}

		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.println(encrypt(jsonObject));
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

}
