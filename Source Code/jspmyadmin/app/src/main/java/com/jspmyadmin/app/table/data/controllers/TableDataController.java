/**
 * 
 */
package com.jspmyadmin.app.table.data.controllers;

import java.sql.SQLException;

import org.json.JSONException;

import com.jspmyadmin.app.table.data.beans.DataSelectBean;
import com.jspmyadmin.app.table.data.logic.DataSelectLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandleGetOrPost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.logic.EncodeHelper;
import com.jspmyadmin.framework.web.utils.RequestAdaptor;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/22
 *
 */
@WebController(authentication = true, path = "/table_data.html", requestLevel = RequestLevel.TABLE)
public class TableDataController {

	@Detect
	private EncodeHelper encodeObj;
	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private View view;
	@Model
	private DataSelectBean bean;

	@HandleGetOrPost
	private void tableData() throws JSONException, EncodingException {

		DataSelectLogic dataSelectLogic = null;
		try {
			bean.setEncodeObj(encodeObj);
			dataSelectLogic = new DataSelectLogic(bean.getRequest_table());
			dataSelectLogic.setEncodeObj(encodeObj);
			dataSelectLogic.fillBean(bean);
			bean.setToken(requestAdaptor.generateToken());
			view.setType(ViewType.FORWARD);
			view.setPath(AppConstants.JSP_TABLE_DATA_DATA);
		} catch (SQLException e) {
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_HOME);
		}

	}
}
