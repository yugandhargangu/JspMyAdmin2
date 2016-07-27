/**
 * 
 */
package com.jspmyadmin.app.table.common.controllers;

import org.json.JSONObject;

import com.jspmyadmin.app.table.common.beans.ForeignKeyBean;
import com.jspmyadmin.app.table.common.logic.ForeignKeyLogic;
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
@WebController(authentication = true, path = "/table_fk_add.html")
public class AddFKController extends Controller<ForeignKeyBean> {

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
		JSONObject jsonObject = new JSONObject();
		try {
			foreignKeyLogic = new ForeignKeyLogic((String) session.getAttribute(FrameworkConstants.SESSION_TABLE));
			foreignKeyLogic.addForeignKey(bean);
			jsonObject.put(FrameworkConstants.MSG_KEY, AppConstants.MSG_FK_ADD_SUCCESS);
		} catch (Exception e) {
			jsonObject.put(FrameworkConstants.ERR, e.getMessage());
		} finally {
			foreignKeyLogic = null;
		}
		view.setToken(super.encode(jsonObject));
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_TABLE_FOREIGN_KEYS);
	}

}
