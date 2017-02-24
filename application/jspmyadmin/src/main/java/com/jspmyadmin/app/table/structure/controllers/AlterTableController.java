/**
 * 
 */
package com.jspmyadmin.app.table.structure.controllers;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jspmyadmin.app.table.structure.beans.AlterColumnBean;
import com.jspmyadmin.app.table.structure.logic.StructureLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandlePost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Messages;
import com.jspmyadmin.framework.web.utils.RequestAdaptor;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/22
 *
 */
@WebController(authentication = true, path = "/table_alter.html", requestLevel = RequestLevel.TABLE)
public class AlterTableController {

	@Detect
	private Messages messages;
	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private View view;
	@Model
	private AlterColumnBean bean;

	@HandlePost
	@ValidateToken
	private void alterTable() throws SQLException, EncodingException {

		StructureLogic structureLogic = new StructureLogic(bean.getRequest_table(), messages);
		structureLogic.fillAlterBean(bean);

		Map<String, List<String>> data_types_map = new LinkedHashMap<String, List<String>>();
		data_types_map.putAll(Constants.Utils.DATA_TYPES_MAP);
		bean.setData_types_map(data_types_map);
		bean.setToken(requestAdaptor.generateToken());
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_TABLE_STRUCTURE_ALTER_TABLE);
	}
}
