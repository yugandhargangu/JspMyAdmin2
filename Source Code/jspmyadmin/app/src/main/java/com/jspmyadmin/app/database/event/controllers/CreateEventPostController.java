/**
 * 
 */
package com.jspmyadmin.app.database.event.controllers;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.jspmyadmin.app.database.event.beans.EventBean;
import com.jspmyadmin.app.database.event.logic.EventLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandlePost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.Rest;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.logic.EncodeHelper;
import com.jspmyadmin.framework.web.utils.RequestAdaptor;
import com.jspmyadmin.framework.web.utils.RequestLevel;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/17
 *
 */
@WebController(authentication = true, path = "/database_event_create_post.text", requestLevel = RequestLevel.DATABASE)
@Rest
public class CreateEventPostController {

	@Detect
	private EncodeHelper encodeObj;
	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private HttpServletResponse response;
	@Model
	private EventBean bean;

	@HandlePost
	@ValidateToken
	private JSONObject createEvent() throws JSONException, EncodingException {
		JSONObject jsonObject = new JSONObject();
		try {
			EventLogic eventLogic = new EventLogic();
			String result = eventLogic.saveEvent(bean);
			jsonObject.put(Constants.ERR, Constants.BLANK);
			if (result != null) {
				jsonObject.put(Constants.DATA, result.trim());
			} else {
				JSONObject msg = new JSONObject();
				msg.put(Constants.MSG_KEY, AppConstants.MSG_EVENT_CREATE_SUCCESS);
				jsonObject.put(Constants.MSG, encodeObj.encode(msg.toString()));
			}
		} catch (JSONException e) {
			jsonObject.put(Constants.ERR, e.getMessage());
		} catch (SQLException e) {
			jsonObject.put(Constants.ERR, e.getMessage());
		}
		jsonObject.put(Constants.TOKEN, requestAdaptor.generateToken());
		return jsonObject;
	}

}
