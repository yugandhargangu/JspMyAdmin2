/**
 * 
 */
package com.jspmyadmin.app.table.structure.controllers;

import java.sql.SQLException;

import com.jspmyadmin.app.table.structure.beans.ColumnListBean;
import com.jspmyadmin.app.table.structure.logic.StructureLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandlePost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Messages;
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
@WebController(authentication = true, path = "/table_column_drop.html", requestLevel = RequestLevel.TABLE)
public class DropColumnsController {

	@Detect
	private Messages messages;
	@Detect
	private RedirectParams redirectParams;
	@Detect
	private View view;
	@Model
	private ColumnListBean bean;

	@HandlePost
	@ValidateToken
	private void dropColumns() {

		StructureLogic structureLogic = null;
		try {
			structureLogic = new StructureLogic(bean.getRequest_table(), messages);
			structureLogic.dropColums(bean);
			redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_COLUMN_DROPPED_SUCCESSFULLY);
		} catch (SQLException e) {
			redirectParams.put(Constants.ERR, e.getMessage());
		}
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_TABLE_STRUCTURE);
	}

}
