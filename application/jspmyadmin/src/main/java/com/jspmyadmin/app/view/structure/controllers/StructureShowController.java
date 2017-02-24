/**
 * 
 */
package com.jspmyadmin.app.view.structure.controllers;

import java.sql.SQLException;

import com.jspmyadmin.app.view.structure.beans.ColumnListBean;
import com.jspmyadmin.app.view.structure.logic.StructureLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandleGetOrPost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.RequestAdaptor;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/22
 *
 */
@WebController(authentication = true, path = "/view_structure.html", requestLevel = RequestLevel.VIEW)
public class StructureShowController {

	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private View view;
	@Model
	private ColumnListBean bean;

	@HandleGetOrPost
	private void structure() throws SQLException, EncodingException {
		StructureLogic structureLogic = new StructureLogic(bean.getRequest_view());
		structureLogic.fillStructureBean(bean);
		bean.setToken(requestAdaptor.generateToken());
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_VIEW_STRUCTURE_STRUCTURE);
	}

}
