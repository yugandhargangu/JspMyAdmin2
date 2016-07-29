/**
 * 
 */
package com.jspmyadmin.app.table.insert.controllers;

import com.jspmyadmin.app.table.insert.beans.InsertUpdateBean;
import com.jspmyadmin.app.table.insert.logic.InsertUpdateLogic;
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
@WebController(authentication = true, path = "/table_data_insert_update.html")
public class InsertUpdatePostController extends Controller<InsertUpdateBean> {

	private static final long serialVersionUID = 1L;

	@Override
	@ValidateToken
	protected void handleGet(InsertUpdateBean bean, View view) throws Exception {
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_HOME);
	}

	@Override
	@ValidateToken
	protected void handlePost(InsertUpdateBean bean, View view) throws Exception {

		InsertUpdateLogic insertUpdateLogic = null;
		try {
			insertUpdateLogic = new InsertUpdateLogic(
					session.getAttribute(FrameworkConstants.SESSION_TABLE).toString());
			insertUpdateLogic.insertUpdate(bean);
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_TABLE_DATA);
		} catch (Exception e) {
			e.printStackTrace();
			insertUpdateLogic.fillBean(bean);
			insertUpdateLogic.fillValues(bean);
			super.generateToken(bean);
			bean.setErr(e.getMessage());
			view.setType(ViewType.FORWARD);
			view.setPath(AppConstants.JSP_TABLE_INSERT_INSERTUPDATE);
		} finally {
			insertUpdateLogic = null;
		}
	}
}
