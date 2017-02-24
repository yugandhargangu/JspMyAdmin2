/**
 * 
 */
package com.jspmyadmin.app.table.insert.controllers;

import java.io.IOException;
import java.sql.SQLException;

import com.jspmyadmin.app.table.insert.beans.InsertUpdateBean;
import com.jspmyadmin.app.table.insert.logic.InsertUpdateLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandlePost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
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
@WebController(authentication = true, path = "/table_data_insert_update.html", requestLevel = RequestLevel.TABLE)
public class InsertUpdatePostController {

	@Detect
	private EncodeHelper encodeObj;
	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private View view;
	@Model
	private InsertUpdateBean bean;

	@HandlePost
	@ValidateToken
	private void insertUpdate() throws EncodingException, SQLException, IOException {

		InsertUpdateLogic insertUpdateLogic = new InsertUpdateLogic(bean.getRequest_table());
		try {
			insertUpdateLogic.insertUpdate(bean);
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_TABLE_DATA);
		} catch (SQLException e) {
			insertUpdateLogic.setEncodeObj(encodeObj);
			insertUpdateLogic.fillBean(bean);
			insertUpdateLogic.fillValues(bean);
			bean.setToken(requestAdaptor.generateToken());
			bean.setErr(e.getMessage());
			view.setType(ViewType.FORWARD);
			view.setPath(AppConstants.JSP_TABLE_INSERT_INSERTUPDATE);
		}
	}
}
