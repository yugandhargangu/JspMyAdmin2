/**
 * 
 */
package com.jspmyadmin.app.database.structure.controllers;

import com.jspmyadmin.app.database.structure.beans.StructureBean;
import com.jspmyadmin.app.database.structure.logic.StructureLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/16
 *
 */
@WebController(authentication = true, path = "/database_structure_suffix.html", requestLevel = RequestLevel.DATABASE)
public class SuffixController extends Controller<StructureBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(StructureBean bean, View view) throws Exception {
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_DATABASE_STRUCTURE);
	}

	@Override
	@ValidateToken
	protected void handlePost(StructureBean bean, View view) throws Exception {

		StructureLogic structureLogic = null;
		try {
			structureLogic = new StructureLogic();
			if (bean.getType() != null) {
				if (FrameworkConstants.ADD.equalsIgnoreCase(bean.getType())) {
					structureLogic.addSuffix(bean);
					redirectParams.put(FrameworkConstants.MSG_KEY, AppConstants.MSG_EXECUTED_SUCCESSFULLY);
				} else if (FrameworkConstants.REPLACE.equalsIgnoreCase(bean.getType())) {
					structureLogic.replaceSuffix(bean);
					redirectParams.put(FrameworkConstants.MSG_KEY, AppConstants.MSG_EXECUTED_SUCCESSFULLY);
				} else if (FrameworkConstants.REMOVE.equalsIgnoreCase(bean.getType())) {
					structureLogic.removeSuffix(bean);
					redirectParams.put(FrameworkConstants.MSG_KEY, AppConstants.MSG_EXECUTED_SUCCESSFULLY);
				} else if (FrameworkConstants.COPY.equalsIgnoreCase(bean.getType())) {
					redirectParams.put(FrameworkConstants.MSG_KEY, FrameworkConstants.BLANK);
				}

			}
		} catch (Exception e) {
			redirectParams.put(FrameworkConstants.ERR, e.getMessage());
		}
		view.setType(ViewType.REDIRECT);
		view.setPath(bean.getAction());
	}

}
