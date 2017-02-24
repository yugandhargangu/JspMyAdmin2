/**
 * 
 */
package com.jspmyadmin.app.server.common.controllers;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.jspmyadmin.app.server.common.beans.VariableBean;
import com.jspmyadmin.app.server.common.logic.VariableLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandlePost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.Rest;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Messages;
import com.jspmyadmin.framework.web.utils.RequestAdaptor;
import com.jspmyadmin.framework.web.utils.RequestLevel;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/12
 *
 */
@WebController(authentication = true, path = "/server_variable.text", requestLevel = RequestLevel.SERVER)
@Rest
public class VariableSaveController {

	@Detect
	private Messages messages;
	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private HttpServletResponse response;
	@Model
	private VariableBean bean;

	@HandlePost
	private JSONObject variableSave() throws JSONException, EncodingException {
		JSONObject jsonObject = new JSONObject();
		VariableLogic variableLogic = null;
		try {
			variableLogic = new VariableLogic();
			String result = variableLogic.save(bean);
			jsonObject.put(Constants.COLUMN, result);
			jsonObject.put(Constants.TYPE, Constants.MSG);
			jsonObject.put(Constants.MSG, messages.getMessage(AppConstants.MSG_SAVE_SUCCESS));
		} catch (SQLException e) {
			jsonObject.put(Constants.TYPE, Constants.ERR);
			jsonObject.put(Constants.MSG, e.getMessage());
		} 
		jsonObject.put(Constants.TOKEN, requestAdaptor.generateToken());
		return jsonObject;
	}

}
