/**
 * 
 */
package com.jspmyadmin.app.database.structure.controllers;

import org.json.JSONObject;

import com.jspmyadmin.app.database.structure.beans.StructureBean;
import com.jspmyadmin.app.database.structure.logic.StructureLogic;
import com.jspmyadmin.framework.util.AppConstants;
import com.jspmyadmin.framework.util.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/16
 *
 */
@WebController(authentication = true, path = "/database_structure_prefix")
public class PrefixController extends Controller<StructureBean> {

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
		JSONObject jsonObject = new JSONObject();
		try {
			structureLogic = new StructureLogic();
			if (bean.getType() != null) {
				if (FrameworkConstants.ADD.equalsIgnoreCase(bean.getType())) {
					structureLogic.addPrefix(bean);
					jsonObject.put(FrameworkConstants.MSG_KEY, "msg.executed_successfully");
				} else if (FrameworkConstants.REPLACE.equalsIgnoreCase(bean.getType())) {
					structureLogic.replacePrefix(bean);
					jsonObject.put(FrameworkConstants.MSG_KEY, "msg.executed_successfully");
				} else if (FrameworkConstants.REMOVE.equalsIgnoreCase(bean.getType())) {
					structureLogic.removePrefix(bean);
					jsonObject.put(FrameworkConstants.MSG_KEY, "msg.executed_successfully");
				} else if (FrameworkConstants.COPY.equalsIgnoreCase(bean.getType())) {
					jsonObject.put(FrameworkConstants.MSG_KEY, "");
				}

			}
		} catch (Exception e) {
			jsonObject.put(FrameworkConstants.ERR, e.getMessage());
		} finally {
			structureLogic = null;
		}
		view.setToken(super.encode(jsonObject));
		view.setType(ViewType.REDIRECT);
		view.setPath(bean.getAction());
	}

}
