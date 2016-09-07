/**
 * 
 */
package com.jspmyadmin.app.database.export.controllers;

import com.jspmyadmin.app.database.export.beans.ImportBean;
import com.jspmyadmin.app.database.export.logic.ImportLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandleGet;
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
@WebController(authentication = true, path = "/database_import.html", requestLevel = RequestLevel.DATABASE)
public class DatabaseImportController {

	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private RedirectParams redirectParams;
	@Detect
	private View view;
	@Model
	private ImportBean bean;

	@HandleGet
	private void preImport() throws EncodingException {

		bean.setToken(requestAdaptor.generateToken());
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_DATABASE_EXPORT_IMPORT);
	}

	@HandlePost
	@ValidateToken
	private void postImport() {
		try {
			if (bean.getImport_file() == null) {
				redirectParams.put(Constants.ERR_KEY, AppConstants.MSG_IMPORT_FILE_BLANK);
				view.setType(ViewType.REDIRECT);
				view.setPath(AppConstants.PATH_DATABASE_IMPORT);
			} else if (!bean.getImport_file().getFileName().toLowerCase().endsWith(Constants.FILE_EXT_SQL)) {
				redirectParams.put(Constants.ERR_KEY, AppConstants.MSG_IMPORT_INVALID_FILE);
				view.setType(ViewType.REDIRECT);
				view.setPath(AppConstants.PATH_DATABASE_IMPORT);
			} else if (bean.getImport_file().getFileSize() == 0) {
				redirectParams.put(Constants.ERR_KEY, AppConstants.MSG_IMPORT_FILE_EMPTY);
				view.setType(ViewType.REDIRECT);
				view.setPath(AppConstants.PATH_DATABASE_IMPORT);
			} else {
				bean.setToken(requestAdaptor.generateToken());
				ImportLogic importLogic = new ImportLogic();
				importLogic.importFile(bean);
				view.setType(ViewType.FORWARD);
				view.setPath(AppConstants.JSP_DATABASE_EXPORT_IMPORT_RESULT);
			}
		} catch (Exception e) {
			bean.setError(e.getMessage());
			view.setType(ViewType.FORWARD);
			view.setPath(AppConstants.JSP_DATABASE_EXPORT_IMPORT_RESULT);
		}
	}
}
