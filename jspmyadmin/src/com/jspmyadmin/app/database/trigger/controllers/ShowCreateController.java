/**
 * 
 */
package com.jspmyadmin.app.database.trigger.controllers;

import java.io.PrintWriter;

import org.json.JSONObject;

import com.jspmyadmin.app.database.trigger.beans.TriggerListBean;
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
@WebController(authentication = true, path = "/database_trigger_show_create.text")
public class ShowCreateController extends Controller<TriggerListBean> {

	private static final long serialVersionUID = 1L;

	@Override
	@ResponseBody
	protected void handleGet(TriggerListBean bean, View view) throws Exception {

	}

	@Override
	@ValidateToken
	@ResponseBody
	protected void handlePost(TriggerListBean bean, View view) throws Exception {
		JSONObject jsonObject = new JSONObject();

		TriggerLogic triggerLogic = null;
		try {
			triggerLogic = new TriggerLogic();
			String result = triggerLogic.showCreate(bean);
			jsonObject.put(FrameworkConstants.DATA, result);
			jsonObject.put(FrameworkConstants.ERR, FrameworkConstants.BLANK);
		} catch (Exception e) {
			jsonObject.put(FrameworkConstants.ERR, e.getMessage());
		}
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.println(super.encrypt(jsonObject));
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

}
