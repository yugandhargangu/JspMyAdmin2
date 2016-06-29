/**
 * 
 */
package com.jspmyadmin.app.database.trigger.controllers;

import java.io.PrintWriter;

import org.json.JSONObject;

import com.jspmyadmin.app.database.trigger.beans.TriggerBean;
import com.jspmyadmin.app.database.trigger.logic.TriggerLogic;
import com.jspmyadmin.framework.util.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.ResponseBody;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.View;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/30
 *
 */
@WebController(authentication = true, path = "/database_trigger_create_post.text")
public class CreateTriggerPostController extends Controller<TriggerBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(TriggerBean bean, View view) throws Exception {

	}

	@Override
	@ValidateToken
	@ResponseBody
	protected void handlePost(TriggerBean bean, View view) throws Exception {
		JSONObject jsonObject = new JSONObject();
		try {
			TriggerLogic triggerLogic = new TriggerLogic();
			if (triggerLogic.isExisted(bean.getTrigger_name())) {
				jsonObject.put(FrameworkConstants.ERR, "Trigger Name is Already Existed.");
			} else {
				String result = triggerLogic.save(bean);
				jsonObject.put(FrameworkConstants.ERR, FrameworkConstants.BLANK);
				if (result != null) {
					jsonObject.put(FrameworkConstants.DATA, result);
				} else {
					JSONObject temp = new JSONObject();
					temp.put(FrameworkConstants.MSG, "Operation Successful.");
					jsonObject.put(FrameworkConstants.DATA, super.encode(temp));
				}
			}
		} catch (Exception e) {
			jsonObject = new JSONObject();
			jsonObject.put(FrameworkConstants.ERR, e.getMessage());
		}
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
