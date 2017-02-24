/**
 * 
 */
package com.jspmyadmin.app.table.common.controllers;

import java.sql.SQLException;

import com.jspmyadmin.app.table.common.beans.ForeignKeyBean;
import com.jspmyadmin.app.table.common.logic.ForeignKeyLogic;
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
 * @created_at 2016/07/06
 *
 */
@WebController(authentication = true, path = "/table_foreign_keys.html", requestLevel = RequestLevel.TABLE)
public class ForeignKeyController {

	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private View view;
	@Model
	private ForeignKeyBean bean;

	@HandleGetOrPost
	private void foreignKeys() throws EncodingException {

		ForeignKeyLogic foreignKeyLogic = null;
		try {
			foreignKeyLogic = new ForeignKeyLogic(bean.getRequest_table());
			foreignKeyLogic.fillBean(bean);
			bean.setToken(requestAdaptor.generateToken());
			view.setType(ViewType.FORWARD);
			view.setPath(AppConstants.JSP_TABLE_COMMON_FOREIGNKEY);
		} catch (SQLException e) {
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_HOME);
		}
	}

}
