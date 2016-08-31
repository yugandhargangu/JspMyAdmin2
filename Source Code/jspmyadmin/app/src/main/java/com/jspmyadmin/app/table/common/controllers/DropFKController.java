/**
 * 
 */
package com.jspmyadmin.app.table.common.controllers;

import com.jspmyadmin.app.table.common.beans.ForeignKeyBean;
import com.jspmyadmin.app.table.common.logic.ForeignKeyLogic;
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
@WebController(authentication = true, path = "/table_fk_drop.html", requestLevel = RequestLevel.TABLE)
public class DropFKController extends Controller<ForeignKeyBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(ForeignKeyBean bean, View view) throws Exception {
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_DATABASE_STRUCTURE);
	}

	@Override
	@ValidateToken
	protected void handlePost(ForeignKeyBean bean, View view) throws Exception {

		ForeignKeyLogic foreignKeyLogic = null;
		try {
			foreignKeyLogic = new ForeignKeyLogic(bean.getRequest_table());
			foreignKeyLogic.dropForeignKeys(bean);
			redirectParams.put(FrameworkConstants.MSG_KEY, AppConstants.MSG_DROP_FK_SUCCESS);
		} catch (Exception e) {
			redirectParams.put(FrameworkConstants.ERR, e.getMessage());
		}
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_TABLE_FOREIGN_KEYS);
	}

}
