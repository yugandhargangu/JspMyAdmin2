/**
 * 
 */
package com.jspmyadmin.app.table.structure.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jspmyadmin.app.table.structure.beans.AlterColumnBean;
import com.jspmyadmin.app.table.structure.logic.StructureLogic;
import com.jspmyadmin.framework.util.AppConstants;
import com.jspmyadmin.framework.util.FrameworkConstants;
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
@WebController(authentication = true, path = "/table_alter")
public class AlterTableController extends Controller<AlterColumnBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(AlterColumnBean bean, View view) throws Exception {
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.JSP_COMMON_HOME);
	}

	@Override
	@ValidateToken
	protected void handlePost(AlterColumnBean bean, View view) throws Exception {

		String table_name = (String) session.getAttribute(FrameworkConstants.SESSION_TABLE);
		StructureLogic structureLogic = new StructureLogic(table_name, messages);
		structureLogic.fillAlterBean(bean);

		Map<String, List<String>> data_types_map = new LinkedHashMap<String, List<String>>();
		data_types_map.putAll(FrameworkConstants.Utils.DATA_TYPES_MAP);
		bean.setData_types_map(data_types_map);
		super.generateToken(bean);
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_TABLE_STRUCTURE_ALTER_TABLE);
	}
}
