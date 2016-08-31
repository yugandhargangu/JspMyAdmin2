/**
 * 
 */
package com.jspmyadmin.app.table.insert.controllers;

import com.jspmyadmin.app.table.insert.beans.InsertUpdateBean;
import com.jspmyadmin.app.table.insert.logic.InsertUpdateLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/11
 *
 */
@WebController(authentication = true, path = "/table_insert_update.html", requestLevel = RequestLevel.TABLE)
public class InsertUpdateController extends Controller<InsertUpdateBean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(InsertUpdateBean bean, View view) throws Exception {
		InsertUpdateLogic insertUpdateLogic = null;
		try {
			super.fillBasics(bean);
			super.setTable(bean);
			insertUpdateLogic = new InsertUpdateLogic(bean.getRequest_table());
			insertUpdateLogic.fillBean(bean);
			bean.setToken(super.generateToken());
			view.setType(ViewType.FORWARD);
			view.setPath(AppConstants.JSP_TABLE_INSERT_INSERTUPDATE);
		} catch (Exception e) {
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_HOME);
		}
	}

	@Override
	protected void handlePost(InsertUpdateBean bean, View view) throws Exception {
		this.handleGet(bean, view);
	}

}
