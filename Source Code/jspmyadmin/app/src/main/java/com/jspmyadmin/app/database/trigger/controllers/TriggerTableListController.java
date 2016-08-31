/**
 * 
 */
package com.jspmyadmin.app.database.trigger.controllers;

import java.io.PrintWriter;
import java.util.List;

import org.json.JSONObject;

import com.jspmyadmin.app.common.logic.DataLogic;
import com.jspmyadmin.app.database.trigger.beans.TriggerBean;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.ResponseBody;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/04/11
 *
 */
@WebController(authentication = true, path = "/database_trigger_table_list.text", requestLevel = RequestLevel.DATABASE)
public class TriggerTableListController extends Controller<TriggerBean> {

	private static final long serialVersionUID = 1L;

	@Override
	@ResponseBody
	protected void handleGet(TriggerBean bean, View view) throws Exception {

	}

	@Override
	@ResponseBody
	protected void handlePost(TriggerBean bean, View view) throws Exception {

		JSONObject jsonObject = new JSONObject();
		DataLogic dataLogic = null;
		try {
			dataLogic = new DataLogic();
			List<String> tableList = dataLogic.getTableList(bean.getDatabase_name(), false);
			jsonObject.put(FrameworkConstants.ERR, FrameworkConstants.BLANK);
			jsonObject.put(FrameworkConstants.DATA, tableList);
		} catch (Exception e) {
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
