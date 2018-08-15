package com.tracknix.jspmyadmin.application.database.controllers;

import com.tracknix.jspmyadmin.application.database.beans.export.ImportBean;
import com.tracknix.jspmyadmin.application.database.services.ImportLogic;
import com.tracknix.jspmyadmin.framework.constants.AppConstants;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.annotations.*;
import com.tracknix.jspmyadmin.framework.web.utils.*;

/**
 * @author Yugandhar Gangu
 */
@WebController(requestLevel = RequestLevel.DATABASE)
public class DatabaseImportController {

    @Handle(path = "/database_import.html", methodType = MethodType.GET)
    @GenerateToken
    private void preImport(View view) {
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_DATABASE_EXPORT_IMPORT);
    }

    @Handle(path = "/database_import.html", methodType = MethodType.POST)
    @ValidateToken
    private void postImport(View view, RedirectParams redirectParams, RequestAdaptor requestAdaptor, @Model ImportBean bean, @LogicParam ImportLogic importLogic) {
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
