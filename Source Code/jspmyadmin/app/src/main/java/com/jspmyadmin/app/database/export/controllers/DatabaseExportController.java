/**
 * 
 */
package com.jspmyadmin.app.database.export.controllers;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.jspmyadmin.app.database.export.beans.ExportBean;
import com.jspmyadmin.app.database.export.logic.ExportLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.Download;
import com.jspmyadmin.framework.web.annotations.HandleGet;
import com.jspmyadmin.framework.web.annotations.HandlePost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
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
@WebController(authentication = true, path = "/database_export.html", requestLevel = RequestLevel.DATABASE)
public class DatabaseExportController {

	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private HttpServletRequest request;
	@Detect
	private View view;
	@Model
	private ExportBean bean;

	@HandleGet
	private void preExport() throws EncodingException {

		bean.setToken(requestAdaptor.generateToken());
		try {
			bean.setFilename(request.getServerName());
			ExportLogic exportLogic = new ExportLogic();
			exportLogic.fillBean(bean);
		} catch (SQLException e) {
		}
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_DATABASE_EXPORT_EXPORT);
	}

	@HandlePost
	@ValidateToken
	@Download
	private void exportDownload() throws SQLException, IOException, ClassNotFoundException {
		ExportLogic exportLogic = new ExportLogic();
		File file = exportLogic.exportFile(bean);
		view.handleDownload(file, true, bean.getFilename() + Constants.FILE_EXT_SQL);
	}
}
