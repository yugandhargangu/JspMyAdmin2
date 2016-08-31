/**
 * 
 */
package com.jspmyadmin.app.server.common.controllers;

import java.io.PrintWriter;

import org.json.JSONObject;

import com.jspmyadmin.app.server.common.beans.VariableBean;
import com.jspmyadmin.app.server.common.logic.VariableLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.ResponseBody;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/12
 *
 */
@WebController(authentication = true, path = "/server_variable.text", requestLevel = RequestLevel.SERVER)
public class VariableSaveController extends Controller<VariableBean> {

	private static final long serialVersionUID = 1L;

	@Override
	@ResponseBody
	protected void handleGet(VariableBean bean, View view) throws Exception {
	}

	@Override
	@ResponseBody
	protected void handlePost(VariableBean bean, View view) throws Exception {
		JSONObject jsonObject = new JSONObject();
		VariableLogic variableLogic = null;
		try {
			variableLogic = new VariableLogic();
			String result = variableLogic.save(bean);
			jsonObject.put(FrameworkConstants.COLUMN, result);
			jsonObject.put(FrameworkConstants.TYPE, FrameworkConstants.MSG);
			jsonObject.put(FrameworkConstants.MSG, messages.getMessage(AppConstants.MSG_SAVE_SUCCESS));
		} catch (Exception e) {
			jsonObject.put(FrameworkConstants.TYPE, FrameworkConstants.ERR);
			jsonObject.put(FrameworkConstants.MSG, e.getMessage());
		} finally {
			variableLogic = null;
		}
		jsonObject.put(FrameworkConstants.TOKEN, super.generateToken());
		PrintWriter printWriter = response.getWriter();
		try {
			printWriter.print(super.encrypt(jsonObject));
		} finally {
			if (printWriter != null) {
				printWriter.close();
			}
			jsonObject = null;
		}
	}

}
