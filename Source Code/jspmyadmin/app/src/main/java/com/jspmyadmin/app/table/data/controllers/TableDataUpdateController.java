/**
 * 
 */
package com.jspmyadmin.app.table.data.controllers;

import java.io.PrintWriter;

import org.json.JSONObject;

import com.jspmyadmin.app.table.data.beans.DataUpdateBean;
import com.jspmyadmin.app.table.data.logic.DataSelectLogic;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.ResponseBody;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/22
 *
 */
@WebController(authentication = true, path = "/table_data_update.text", requestLevel = RequestLevel.TABLE)
public class TableDataUpdateController extends Controller<DataUpdateBean> {

	private static final long serialVersionUID = 1L;

	@Override
	@ResponseBody
	protected void handleGet(DataUpdateBean bean, View view) throws Exception {
	}

	@Override
	@ValidateToken
	@ResponseBody
	protected void handlePost(DataUpdateBean bean, View view) throws Exception {

		DataSelectLogic dataSelectLogic = null;
		JSONObject jsonObject = new JSONObject();
		try {
			dataSelectLogic = new DataSelectLogic(bean.getRequest_table());
			String result = dataSelectLogic.update(bean);
			jsonObject.append(FrameworkConstants.ERR, FrameworkConstants.BLANK);
			jsonObject.append(FrameworkConstants.DATA, result);
		} catch (Exception e) {
			jsonObject.append(FrameworkConstants.ERR, e.getMessage());
		} finally {
			dataSelectLogic = null;
		}
		jsonObject.put(FrameworkConstants.TOKEN, super.generateToken());
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
