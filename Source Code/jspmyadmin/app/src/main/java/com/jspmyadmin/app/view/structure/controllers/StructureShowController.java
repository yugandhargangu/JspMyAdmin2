/**
 * 
 */
package com.jspmyadmin.app.view.structure.controllers;

import com.jspmyadmin.app.view.structure.beans.ColumnListBean;
import com.jspmyadmin.app.view.structure.logic.StructureLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/22
 *
 */
@WebController(authentication = true, path = "/view_structure.html", requestLevel = RequestLevel.VIEW)
public class StructureShowController extends Controller<ColumnListBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(ColumnListBean bean, View view) throws Exception {
		super.fillBasics(bean);
		StructureLogic structureLogic = new StructureLogic(bean.getRequest_view());
		structureLogic.fillStructureBean(bean);
		bean.setToken(super.generateToken());
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_VIEW_STRUCTURE_STRUCTURE);
	}

	@Override
	protected void handlePost(ColumnListBean bean, View view) throws Exception {
		this.handleGet(bean, view);
	}
}
