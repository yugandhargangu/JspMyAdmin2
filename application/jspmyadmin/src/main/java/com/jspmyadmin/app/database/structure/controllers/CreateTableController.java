/**
 * 
 */
package com.jspmyadmin.app.database.structure.controllers;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jspmyadmin.app.database.structure.beans.CreateTableBean;
import com.jspmyadmin.app.database.structure.logic.StructureLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandlePost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.RedirectParams;
import com.jspmyadmin.framework.web.utils.RequestAdaptor;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/22
 *
 */
@WebController(authentication = true, path = "/database_create_table.html", requestLevel = RequestLevel.DATABASE)
public class CreateTableController {

	@Detect
	private RedirectParams redirectParams;
	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private View view;
	@Model
	private CreateTableBean bean;

	@HandlePost
	@ValidateToken
	private void createTable() throws SQLException, EncodingException {
		StructureLogic structureLogic = new StructureLogic();
		if (structureLogic.isTableExisted(bean.getTable_name(), bean.getRequest_db())) {
			redirectParams.put(Constants.ERR_KEY, AppConstants.MSG_TABLE_ALREADY_EXISTED);
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_DATABASE_STRUCTURE);
			return;
		}
		Map<String, List<String>> data_types_map = new LinkedHashMap<String, List<String>>();
		data_types_map.putAll(Constants.Utils.DATA_TYPES_MAP);
		bean.setData_types_map(data_types_map);
		bean.setToken(requestAdaptor.generateToken());
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_DATABASE_STRUCTURE_CREATE_TABLE);
	}
}
