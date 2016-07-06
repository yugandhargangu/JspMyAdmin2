/**
 * 
 */
package com.jspmyadmin.app.table.structure.controllers;

import com.jspmyadmin.app.table.structure.beans.ColumnListBean;
import com.jspmyadmin.app.table.structure.logic.StructureLogic;
import com.jspmyadmin.framework.util.AppConstants;
import com.jspmyadmin.framework.util.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/22
 *
 */
@WebController(authentication = true, path = "/table_structure")
public class StructureShowController extends Controller<ColumnListBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(ColumnListBean bean, View view) throws Exception {
		String table_name = (String) session.getAttribute(FrameworkConstants.SESSION_TABLE);
		super.fillBasics(bean);
		StructureLogic structureLogic = new StructureLogic(table_name, messages);
		structureLogic.fillStructureBean(bean);
		super.generateToken(bean);
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_TABLE_STRUCTURE_STRUCTURE);
	}

	@Override
	protected void handlePost(ColumnListBean bean, View view) throws Exception {
		this.handleGet(bean, view);
	}
}
