/**
 * 
 */
package com.jspmyadmin.app.database.routine.controllers;

import java.io.PrintWriter;

import org.json.JSONObject;

import com.jspmyadmin.app.database.routine.beans.RoutineListBean;
import com.jspmyadmin.app.database.routine.logic.RoutineLogic;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.ResponseBody;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.View;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/16
 *
 */
@WebController(authentication = true, path = "/database_procedure_show_create.text")
public class ProcedureShowCreateController extends Controller<RoutineListBean> {

	private static final long serialVersionUID = 1L;

	@Override
	@ResponseBody
	protected void handleGet(RoutineListBean bean, View view) throws Exception {
		JSONObject jsonObject = new JSONObject();
		PrintWriter writer = response.getWriter();
		try {
			writer.print(super.encrypt(jsonObject));
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	@Override
	@ValidateToken
	@ResponseBody
	protected void handlePost(RoutineListBean bean, View view) throws Exception {

		RoutineLogic routineLogic = null;
		JSONObject jsonObject = new JSONObject();
		try {
			routineLogic = new RoutineLogic();
			String result = routineLogic.showCreate(bean, true);
			jsonObject.put(FrameworkConstants.ERR, FrameworkConstants.BLANK);
			jsonObject.put(FrameworkConstants.DATA, result);
		} catch (Exception e) {
			jsonObject.put(FrameworkConstants.ERR, e.getMessage());
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
