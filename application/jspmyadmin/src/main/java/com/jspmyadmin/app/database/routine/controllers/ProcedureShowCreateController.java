/**
 * 
 */
package com.jspmyadmin.app.database.routine.controllers;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.jspmyadmin.app.database.routine.beans.RoutineListBean;
import com.jspmyadmin.app.database.routine.logic.RoutineLogic;
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
 * @created_at 2016/02/16
 *
 */
@WebController(authentication = true, path = "/database_procedure_show_create.text", requestLevel = RequestLevel.DATABASE)
@Rest
public class ProcedureShowCreateController {

	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private HttpServletResponse response;
	@Model
	private RoutineListBean bean;

	@HandlePost
	@ValidateToken
	private JSONObject procedureShowCreate() throws JSONException, EncodingException {

		RoutineLogic routineLogic = null;
		JSONObject jsonObject = new JSONObject();
		try {
			routineLogic = new RoutineLogic();
			String result = routineLogic.showCreate(bean, true);
			jsonObject.put(Constants.ERR, Constants.BLANK);
			jsonObject.put(Constants.DATA, result);
		} catch (SQLException e) {
			jsonObject.put(Constants.ERR, e.getMessage());
		}
		jsonObject.put(Constants.TOKEN, requestAdaptor.generateToken());
		return jsonObject;
	}

}
