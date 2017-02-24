/**
 * 
 */
package com.jspmyadmin.app.database.routine.controllers;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.jspmyadmin.app.database.routine.beans.RoutineBean;
import com.jspmyadmin.app.database.routine.logic.RoutineLogic;
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
 * @created_at 2016/02/22
 *
 */
@WebController(authentication = true, path = "/database_create_function_post.text", requestLevel = RequestLevel.DATABASE)
@Rest
public class FunctionCreatePostController {

	@Detect
	private Messages messages;
	@Detect
	private EncodeHelper encodeObj;
	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private HttpServletResponse response;
	@Model
	private RoutineBean bean;

	@HandlePost
	@ValidateToken
	private JSONObject functionCreate() throws JSONException, EncodingException {

		RoutineLogic routineLogic = null;
		JSONObject jsonObject = new JSONObject();
		try {
			routineLogic = new RoutineLogic();
			if (routineLogic.isExisted(bean.getName(), Constants.FUNCTION, bean.getRequest_db())) {
				jsonObject.put(Constants.ERR, messages.getMessage(AppConstants.MSG_FUNCTION_ALREADY_EXISTED));
			} else {
				String result = routineLogic.saveFunction(bean);
				jsonObject.append(Constants.ERR, Constants.BLANK);
				if (result != null) {
					jsonObject.append(Constants.DATA, result);
				} else {
					JSONObject msg = new JSONObject();
					msg.put(Constants.MSG_KEY, AppConstants.MSG_FUNCTION_SAVE_SUCCESS);
					jsonObject.append(Constants.MSG, encodeObj.encode(msg.toString()));
				}
			}
		} catch (SQLException e) {
			jsonObject.append(Constants.ERR, e.getMessage());
		} finally {
			routineLogic = null;
		}
		jsonObject.put(Constants.TOKEN, requestAdaptor.generateToken());
		return jsonObject;
	}
}
