/**
 * 
 */
package com.jspmyadmin.app.database.routine.controllers;

import java.io.PrintWriter;

import org.json.JSONObject;

import com.jspmyadmin.app.database.routine.beans.RoutineBean;
import com.jspmyadmin.app.database.routine.logic.RoutineLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.ResponseBody;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/22
 *
 */
@WebController(authentication = true, path = "/database_create_procedure_post.text")
public class ProcedureCreatePostController extends Controller<RoutineBean> {

	private static final long serialVersionUID = 1L;

	@Override
	@ValidateToken
	protected void handleGet(RoutineBean bean, View view) throws Exception {
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.JSP_COMMON_HOME);
	}

	@Override
	@ValidateToken
	@ResponseBody
	protected void handlePost(RoutineBean bean, View view) throws Exception {

		RoutineLogic routineLogic = null;
		JSONObject jsonObject = new JSONObject();
		try {
			routineLogic = new RoutineLogic();
			if (routineLogic.isExisted(bean.getName(), "PROCEDURE")) {
				jsonObject.put(FrameworkConstants.ERR, messages.getMessage("msg.procedure_already_existed"));
			} else {
				String result = routineLogic.saveProcedure(bean);
				jsonObject.append(FrameworkConstants.ERR, FrameworkConstants.BLANK);
				if (result != null) {
					jsonObject.append(FrameworkConstants.DATA, result);
				} else {
					JSONObject msg = new JSONObject();
					msg.put(FrameworkConstants.MSG_KEY, "msg.procedure_save_success");
					jsonObject.append(FrameworkConstants.MSG, super.encode(msg));
				}
			}
		} catch (Exception e) {
			jsonObject.append(FrameworkConstants.ERR, e.getMessage());
		} finally {
			routineLogic = null;
		}

		PrintWriter writer = response.getWriter();
		try {
			writer.print(super.encrypt(jsonObject));
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
}
