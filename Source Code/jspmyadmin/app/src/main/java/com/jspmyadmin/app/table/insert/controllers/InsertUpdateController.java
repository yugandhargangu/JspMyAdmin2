/**
 * 
 */
package com.jspmyadmin.app.table.insert.controllers;

import java.sql.SQLException;

import com.jspmyadmin.app.table.insert.beans.InsertUpdateBean;
import com.jspmyadmin.app.table.insert.logic.InsertUpdateLogic;
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
 * @created_at 2016/07/11
 *
 */
@WebController(authentication = true, path = "/table_insert_update.html", requestLevel = RequestLevel.TABLE)
public class InsertUpdateController {

	@Detect
	private EncodeHelper encodeObj;
	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private View view;
	@Model
	private InsertUpdateBean bean;

	@HandleGetOrPost
	private void insertUpdate() throws EncodingException {
		InsertUpdateLogic insertUpdateLogic = null;
		try {
			insertUpdateLogic = new InsertUpdateLogic(bean.getRequest_table());
			insertUpdateLogic.setEncodeObj(encodeObj);
			insertUpdateLogic.fillBean(bean);
			bean.setToken(requestAdaptor.generateToken());
			view.setType(ViewType.FORWARD);
			view.setPath(AppConstants.JSP_TABLE_INSERT_INSERTUPDATE);
		} catch (SQLException e) {
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_HOME);
		}
	}

}
