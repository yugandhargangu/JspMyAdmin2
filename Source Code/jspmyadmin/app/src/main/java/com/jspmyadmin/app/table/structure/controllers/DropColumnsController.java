/**
 * 
 */
package com.jspmyadmin.app.table.structure.controllers;

import com.jspmyadmin.app.table.structure.beans.ColumnListBean;
import com.jspmyadmin.app.table.structure.logic.StructureLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * 
 * @author Yugandhar Gangu
 * @created_at 2016/07/05
 *
 */
@WebController(authentication = true, path = "/table_column_drop.html", requestLevel = RequestLevel.TABLE)
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
		try {
			structureLogic = new StructureLogic(bean.getRequest_table(), messages);
			structureLogic.dropColums(bean);
			redirectParams.put(FrameworkConstants.MSG_KEY, AppConstants.MSG_COLUMN_DROPPED_SUCCESSFULLY);
		} catch (Exception e) {
			redirectParams.put(FrameworkConstants.ERR, e.getMessage());
		}
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_TABLE_STRUCTURE);
	}

}
