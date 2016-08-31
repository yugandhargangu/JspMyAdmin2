/**
 * 
 */
package com.jspmyadmin.app.database.trigger.controllers;

import java.io.PrintWriter;

import org.json.JSONObject;

import com.jspmyadmin.app.database.trigger.beans.TriggerBean;
import com.jspmyadmin.app.database.trigger.logic.TriggerLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.ResponseBody;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/30
 *
 */
@WebController(authentication = true, path = "/database_trigger_create_post.text", requestLevel = RequestLevel.DATABASE)
public class CreateTriggerPostController extends Controller<TriggerBean> {

	private static final long serialVersionUID = 1L;

	@Override
	@ResponseBody
	protected void handleGet(TriggerBean bean, View view) throws Exception {

	}

	@Override
	@ValidateToken
	@ResponseBody
	protected void handlePost(TriggerBean bean, View view) throws Exception {
		JSONObject jsonObject = new JSONObject();
		try {
			TriggerLogic triggerLogic = new TriggerLogic();
			if (triggerLogic.isExisted(bean.getTrigger_name(), bean.getRequest_db())) {
				jsonObject.put(FrameworkConstants.ERR, messages.getMessage(AppConstants.MSG_TRIGGER_ALREADY_EXISTED));
			} else {
				String result = triggerLogic.save(bean);
				jsonObject.put(FrameworkConstants.ERR, FrameworkConstants.BLANK);
				if (result != null) {
					jsonObject.put(FrameworkConstants.DATA, result);
				} else {
					JSONObject temp = new JSONObject();
					temp.put(FrameworkConstants.MSG_KEY, AppConstants.MSG_TRIGGER_CREATE_SUCCESS);
					jsonObject.put(FrameworkConstants.DATA, super.encode(temp));
				}
			}
		} catch (Exception e) {
			jsonObject = new JSONObject();
			jsonObject.put(FrameworkConstants.ERR, e.getMessage());
		}
		jsonObject.put(FrameworkConstants.TOKEN, super.generateToken());
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
			printWriter.println(super.encrypt(jsonObject));
		} finally {
			if (printWriter != null) {
				printWriter.close();
			}
		}
	}

}
