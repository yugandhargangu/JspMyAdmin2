/**
 * 
 */
package com.jspmyadmin.app.database.event.controllers;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.jspmyadmin.app.database.event.beans.EventListBean;
import com.jspmyadmin.app.database.event.logic.EventLogic;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandlePost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.Rest;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.RequestAdaptor;
import com.jspmyadmin.framework.web.utils.RequestLevel;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/18
 *
 */
@WebController(authentication = true, path = "/database_event_show_create.text", requestLevel = RequestLevel.DATABASE)
@Rest
public class ShowCreateController {

	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private HttpServletResponse response;
	@Model
	private EventListBean bean;

	@HandlePost
	@ValidateToken
	private JSONObject showCreateEvent() throws JSONException, EncodingException {
		JSONObject jsonObject = new JSONObject();
		EventLogic eventLogic = null;
		try {
			eventLogic = new EventLogic();
			String result = eventLogic.getShowCreate(bean);
			jsonObject.put(Constants.ERR, Constants.BLANK);
			jsonObject.put(Constants.DATA, result);
		} catch (SQLException e) {
			jsonObject.put(Constants.ERR, e.getMessage());
		}
		jsonObject.put(Constants.TOKEN, requestAdaptor.generateToken());
		return jsonObject;
	}

}
