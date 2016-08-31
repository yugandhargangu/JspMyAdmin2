/**
 * 
 */
package com.jspmyadmin.app.server.export.controllers;

import com.jspmyadmin.app.server.export.beans.ImportBean;
import com.jspmyadmin.app.server.export.logic.ImportLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.FrameworkConstants;
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
@WebController(authentication = true, path = "/server_import.html", requestLevel = RequestLevel.SERVER)
public class ServerImportController extends Controller<ImportBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(ImportBean bean, View view) throws Exception {

		super.fillBasics(bean);
		bean.setToken(super.generateToken());
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_SERVER_EXPORT_IMPORT);
	}

	@Override
	@ValidateToken
	protected void handlePost(ImportBean bean, View view) throws Exception {
		try {
			if (bean.getImport_file() == null) {
				redirectParams.put(FrameworkConstants.ERR_KEY, "msg.import_file_blank");
				view.setType(ViewType.REDIRECT);
				view.setPath(AppConstants.PATH_SERVER_IMPORT);
			} else if (!bean.getImport_file().getFileName().toLowerCase().endsWith(".sql")) {
				redirectParams.put(FrameworkConstants.ERR_KEY, "msg.import_invalid_file");
				view.setType(ViewType.REDIRECT);
				view.setPath(AppConstants.PATH_SERVER_IMPORT);
			} else if (bean.getImport_file().getFileSize() == 0) {
				redirectParams.put(FrameworkConstants.ERR_KEY, "msg.import_file_empty");
				view.setType(ViewType.REDIRECT);
				view.setPath(AppConstants.PATH_SERVER_IMPORT);
			} else {
				bean.setToken(super.generateToken());
				ImportLogic importLogic = new ImportLogic();
				importLogic.importFile(bean);
				view.setType(ViewType.FORWARD);
				view.setPath(AppConstants.JSP_SERVER_EXPORT_IMPORT_RESULT);
			}
		} catch (Exception e) {
			bean.setError(e.getMessage());
			view.setType(ViewType.FORWARD);
			view.setPath(AppConstants.JSP_SERVER_EXPORT_IMPORT_RESULT);
		}
	}
}
