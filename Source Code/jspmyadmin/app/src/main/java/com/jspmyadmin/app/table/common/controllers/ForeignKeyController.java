/**
 * 
 */
package com.jspmyadmin.app.table.common.controllers;

import com.jspmyadmin.app.table.common.beans.ForeignKeyBean;
import com.jspmyadmin.app.table.common.logic.ForeignKeyLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/06
 *
 */
@WebController(authentication = true, path = "/table_foreign_keys.html", requestLevel = RequestLevel.TABLE)
public class ForeignKeyController extends Controller<ForeignKeyBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(ForeignKeyBean bean, View view) throws Exception {

		ForeignKeyLogic foreignKeyLogic = null;
		try {
			super.fillBasics(bean);
			super.setTable(bean);
			foreignKeyLogic = new ForeignKeyLogic(bean.getRequest_table());
			foreignKeyLogic.fillBean(bean);
			bean.setToken(super.generateToken());
			view.setType(ViewType.FORWARD);
			view.setPath(AppConstants.JSP_TABLE_COMMON_FOREIGNKEY);
		} catch (Exception e) {
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_HOME);
		}
	}

	@Override
	protected void handlePost(ForeignKeyBean bean, View view) throws Exception {
		this.handleGet(bean, view);
	}

}
