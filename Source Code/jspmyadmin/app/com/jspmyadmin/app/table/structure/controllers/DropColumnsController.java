/**
 * 
 */
package com.jspmyadmin.app.table.structure.controllers;

import org.json.JSONObject;

import com.jspmyadmin.app.table.structure.beans.ColumnListBean;
import com.jspmyadmin.app.table.structure.logic.StructureLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * 
 * @author Yugandhar Gangu
 * @created_at 2016/07/05
 *
 */
@WebController(authentication = true, path = "/table_column_drop.html")
public class DropColumnsController extends Controller<ColumnListBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(ColumnListBean bean, View view) throws Exception {
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_DATABASE_STRUCTURE);
	}

	@Override
	@ValidateToken
	protected void handlePost(ColumnListBean bean, View view) throws Exception {

		StructureLogic structureLogic = null;
		JSONObject jsonObject = new JSONObject();
		try {
			structureLogic = new StructureLogic((String) session.getAttribute(FrameworkConstants.SESSION_TABLE),
					messages);
			structureLogic.dropColums(bean);
			jsonObject.put(FrameworkConstants.MSG_KEY, "msg.column_dropped_successfully");
		} catch (Exception e) {
			jsonObject.put(FrameworkConstants.ERR, e.getMessage());
		} finally {
			structureLogic = null;
		}
		view.setToken(super.encode(jsonObject));
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_TABLE_STRUCTURE);
	}

}
