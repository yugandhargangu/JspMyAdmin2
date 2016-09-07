/**
 * 
 */
package com.jspmyadmin.app.database.trigger.controllers;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.jspmyadmin.app.database.trigger.beans.TriggerBean;
import com.jspmyadmin.app.database.trigger.logic.TriggerLogic;
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
import com.jspmyadmin.framework.web.utils.Messages;
import com.jspmyadmin.framework.web.utils.RequestAdaptor;
import com.jspmyadmin.framework.web.utils.RequestLevel;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/30
 *
 */
@WebController(authentication = true, path = "/database_trigger_create_post.text", requestLevel = RequestLevel.DATABASE)
@Rest
public class CreateTriggerPostController {

	@Detect
	private Messages messages;
	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private EncodeHelper encodeObj;
	@Detect
	private HttpServletResponse response;
	@Model
	private TriggerBean bean;

	@HandlePost
	@ValidateToken
	private JSONObject createTrigger() throws JSONException, EncodingException {
		JSONObject jsonObject = new JSONObject();
		try {
			TriggerLogic triggerLogic = new TriggerLogic();
			if (triggerLogic.isExisted(bean.getTrigger_name(), bean.getRequest_db())) {
				jsonObject.put(Constants.ERR, messages.getMessage(AppConstants.MSG_TRIGGER_ALREADY_EXISTED));
			} else {
				String result = triggerLogic.save(bean);
				jsonObject.put(Constants.ERR, Constants.BLANK);
				if (result != null) {
					jsonObject.put(Constants.DATA, result);
				} else {
					JSONObject temp = new JSONObject();
					temp.put(Constants.MSG_KEY, AppConstants.MSG_TRIGGER_CREATE_SUCCESS);
					jsonObject.put(Constants.DATA, encodeObj.encode(temp.toString()));
				}
			}
		} catch (SQLException e) {
			jsonObject = new JSONObject();
			jsonObject.put(Constants.ERR, e.getMessage());
		}
		jsonObject.put(Constants.TOKEN, requestAdaptor.generateToken());
		return jsonObject;
	}

}
