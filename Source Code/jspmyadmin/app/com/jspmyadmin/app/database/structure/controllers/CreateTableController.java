/**
 * 
 */
package com.jspmyadmin.app.database.structure.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.jspmyadmin.app.database.structure.beans.CreateTableBean;
import com.jspmyadmin.app.database.structure.logic.StructureLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.FrameworkConstants;
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
@WebController(authentication = true, path = "/database_create_table.html")
public class CreateTableController extends Controller<CreateTableBean> {

	private static final long serialVersionUID = 1L;

	@Override
	@ValidateToken
	protected void handleGet(CreateTableBean bean, View view) throws Exception {
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.JSP_COMMON_HOME);
	}

	@Override
	@ValidateToken
	protected void handlePost(CreateTableBean bean, View view) throws Exception {
		StructureLogic structureLogic = new StructureLogic();
		if (structureLogic.isTableExisted(bean.getTable_name())) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(FrameworkConstants.ERR_KEY, "msg.table_already_existed");
			view.setToken(super.encode(jsonObject));
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_DATABASE_STRUCTURE);
			return;
		}
		Map<String, List<String>> data_types_map = new LinkedHashMap<String, List<String>>();
		data_types_map.putAll(FrameworkConstants.Utils.DATA_TYPES_MAP);
		bean.setData_types_map(data_types_map);
		super.generateToken(bean);
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_DATABASE_STRUCTURE_CREATE_TABLE);
	}
}
