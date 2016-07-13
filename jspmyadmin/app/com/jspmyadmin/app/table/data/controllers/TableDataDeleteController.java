/**
 * 
 */
package com.jspmyadmin.app.table.data.controllers;

import java.io.PrintWriter;

import org.json.JSONObject;

import com.jspmyadmin.app.table.data.beans.DataSelectBean;
import com.jspmyadmin.app.table.data.logic.DataSelectLogic;
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
@WebController(authentication = true, path = "/table_data_delete.text")
public class TableDataDeleteController extends Controller<DataSelectBean> {

	private static final long serialVersionUID = 1L;

	@Override
	@ValidateToken
	protected void handleGet(DataSelectBean bean, View view) throws Exception {
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.JSP_COMMON_HOME);
	}

	@Override
	@ValidateToken
	@ResponseBody
	protected void handlePost(DataSelectBean bean, View view) throws Exception {

		DataSelectLogic dataSelectLogic = null;
		JSONObject jsonObject = new JSONObject();
		try {
			dataSelectLogic = new DataSelectLogic(session.getAttribute(FrameworkConstants.SESSION_TABLE).toString());
			int result = dataSelectLogic.delete(bean);
			jsonObject.append(FrameworkConstants.ERR, FrameworkConstants.BLANK);
			jsonObject.append(FrameworkConstants.DATA, result);
		} catch (Exception e) {
			jsonObject.append(FrameworkConstants.ERR, e.getMessage());
		} finally {
			dataSelectLogic = null;
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
