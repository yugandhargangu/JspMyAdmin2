/**
 * 
 */
package com.jspmyadmin.app.database.structure.controllers;

import java.io.PrintWriter;

import org.json.JSONObject;

import com.jspmyadmin.app.database.structure.beans.CreateTableBean;
import com.jspmyadmin.app.database.structure.logic.StructureLogic;
import com.jspmyadmin.framework.util.AppConstants;
import com.jspmyadmin.framework.util.FrameworkConstants;
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
@WebController(authentication = true, path = "/database_create_table_post.text")
public class CreateTablePostController extends Controller<CreateTableBean> {

	private static final long serialVersionUID = 1L;

	@Override
	@ValidateToken
	protected void handleGet(CreateTableBean bean, View view) throws Exception {
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.JSP_COMMON_HOME);
	}

	@Override
	@ValidateToken
	@ResponseBody
	protected void handlePost(CreateTableBean bean, View view) throws Exception {

		StructureLogic structureLogic = null;
		JSONObject jsonObject = new JSONObject();
		try {
			structureLogic = new StructureLogic();
			structureLogic.setMessages(messages);
			String result = structureLogic.validate(bean);
			if (result != null) {
				jsonObject.append(FrameworkConstants.ERR, result);
			} else {
				result = structureLogic.createTable(bean);
				jsonObject.append(FrameworkConstants.ERR, FrameworkConstants.BLANK);
				if (result != null) {
					jsonObject.append(FrameworkConstants.DATA, result);
				} else {
					JSONObject msg = new JSONObject();
					msg.put(FrameworkConstants.MSG_KEY, "msg.table_created");
					jsonObject.append(FrameworkConstants.MSG, super.encode(msg.toString()));
				}
			}
		} catch (Exception e) {
			jsonObject.append(FrameworkConstants.ERR, e.getMessage());
		} finally {
			structureLogic = null;
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
