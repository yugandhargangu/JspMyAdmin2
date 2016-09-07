/**
 * 
 */
package com.jspmyadmin.app.database.structure.controllers;

import java.sql.SQLException;

import com.jspmyadmin.app.database.structure.beans.StructureBean;
import com.jspmyadmin.app.database.structure.logic.StructureLogic;
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
 * @author Yugandhar Gangu
 * @created_at 2016/02/16
 *
 */
@WebController(authentication = true, path = "/database_structure_prefix.html", requestLevel = RequestLevel.DATABASE)
public class PrefixController {

	@Detect
	private RedirectParams redirectParams;
	@Detect
	private View view;
	@Model
	private StructureBean bean;

	@HandlePost
	@ValidateToken
	protected void prefixTables() {

		StructureLogic structureLogic = null;
		try {
			structureLogic = new StructureLogic();
			if (bean.getType() != null) {
				if (Constants.ADD.equalsIgnoreCase(bean.getType())) {
					structureLogic.addPrefix(bean);
					redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_EXECUTED_SUCCESSFULLY);
				} else if (Constants.REPLACE.equalsIgnoreCase(bean.getType())) {
					structureLogic.replacePrefix(bean);
					redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_EXECUTED_SUCCESSFULLY);
				} else if (Constants.REMOVE.equalsIgnoreCase(bean.getType())) {
					structureLogic.removePrefix(bean);
					redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_EXECUTED_SUCCESSFULLY);
				} else if (Constants.COPY.equalsIgnoreCase(bean.getType())) {
					redirectParams.put(Constants.MSG_KEY, Constants.BLANK);
				}
			}
		} catch (SQLException e) {
			redirectParams.put(Constants.ERR, e.getMessage());
		}
		view.setType(ViewType.REDIRECT);
		view.setPath(bean.getAction());
	}

}
