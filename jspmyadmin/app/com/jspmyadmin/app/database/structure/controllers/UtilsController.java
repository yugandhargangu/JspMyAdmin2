/**
 * 
 */
package com.jspmyadmin.app.database.structure.controllers;

import java.io.PrintWriter;

import org.json.JSONObject;

import com.jspmyadmin.app.database.structure.beans.StructureBean;
import com.jspmyadmin.app.database.structure.logic.StructureLogic;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.ResponseBody;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.View;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/23
 *
 */
@WebController(authentication = true, path = "/database_structure_utils.text")
public class UtilsController extends Controller<StructureBean> {

	private static final long serialVersionUID = 1L;

	@Override
	@ResponseBody
	protected void handleGet(StructureBean bean, View view) throws Exception {
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
	@ResponseBody
	protected void handlePost(StructureBean bean, View view) throws Exception {
		StructureLogic structureLogic = null;
		JSONObject jsonObject = new JSONObject();
		try {
			structureLogic = new StructureLogic();
			if (FrameworkConstants.COLUMN.equalsIgnoreCase(bean.getType())) {
				String result = structureLogic.getNewColumn(bean);
				jsonObject.put(FrameworkConstants.ERR, FrameworkConstants.BLANK);
				jsonObject.put(FrameworkConstants.DATA, result);
			}
		} catch (Exception e) {
			jsonObject.put(FrameworkConstants.ERR, e.getMessage());
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
