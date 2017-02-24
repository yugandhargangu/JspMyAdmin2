/**
 * 
 */
package com.jspmyadmin.app.table.common.controllers;

import java.sql.SQLException;

import com.jspmyadmin.app.table.common.beans.ForeignKeyBean;
import com.jspmyadmin.app.table.common.logic.ForeignKeyLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandlePost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.RedirectParams;
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
public class DropFKController {

	@Detect
	private RedirectParams redirectParams;
	@Detect
	private View view;
	@Model
	private ForeignKeyBean bean;

	@HandlePost
	@ValidateToken
	private void dropFK() {

		ForeignKeyLogic foreignKeyLogic = null;
		try {
			foreignKeyLogic = new ForeignKeyLogic(bean.getRequest_table());
			foreignKeyLogic.dropForeignKeys(bean);
			redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_DROP_FK_SUCCESS);
		} catch (SQLException e) {
			redirectParams.put(Constants.ERR, e.getMessage());
		}
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_TABLE_FOREIGN_KEYS);
	}

}
