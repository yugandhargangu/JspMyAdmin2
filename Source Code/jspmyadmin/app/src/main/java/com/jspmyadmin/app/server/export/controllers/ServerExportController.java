/**
 * 
 */
package com.jspmyadmin.app.server.export.controllers;

import java.io.File;

import com.jspmyadmin.app.server.export.beans.ExportBean;
import com.jspmyadmin.app.server.export.logic.ExportLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.Download;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/22
 *
 */
@WebController(authentication = true, path = "/server_export.html", requestLevel = RequestLevel.SERVER)
public class ServerExportController extends Controller<ExportBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(ExportBean bean, View view) throws Exception {

		super.fillBasics(bean);
		bean.setToken(super.generateToken());
		try {
			bean.setFilename(request.getServerName());
			ExportLogic exportLogic = new ExportLogic();
			exportLogic.fillBean(bean);
		} catch (Exception e) {

		}
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_SERVER_EXPORT_EXPORT);
	}

	@Override
	@ValidateToken
	@Download
	protected void handlePost(ExportBean bean, View view) throws Exception {
		try {
			ExportLogic exportLogic = new ExportLogic();
			File file = exportLogic.exportFile(bean);
			if (bean.getFilename() == null || FrameworkConstants.BLANK.equals(bean.getFilename().trim())) {
				bean.setFilename(request.getServerName());
			}
			handleDownload(file, true, bean.getFilename() + ".sql");
		} catch (Exception e) {
		}
	}
}
