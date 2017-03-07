package com.tracknix.jspmyadmin.application.database.export.controllers;

import com.tracknix.jspmyadmin.application.database.export.beans.ExportBean;
import com.tracknix.jspmyadmin.application.database.export.logic.ExportLogic;
import com.tracknix.jspmyadmin.framework.constants.AppConstants;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.exception.EncodingException;
import com.tracknix.jspmyadmin.framework.web.annotations.*;
import com.tracknix.jspmyadmin.framework.web.utils.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Yugandhar Gangu
 */
@WebController(requestLevel = RequestLevel.DATABASE)
public class DatabaseExportController {

    @Handle(path = "/database_export.html", methodType = MethodType.GET)
    private void preExport(View view, RequestAdaptor requestAdaptor, HttpServletRequest request, @Model ExportBean bean) throws EncodingException {

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

    @Handle(path = "/database_export.html", methodType = MethodType.POST)
    @ValidateToken
    @Download
    private void exportDownload(View view, @Model ExportBean bean) throws SQLException, IOException, ClassNotFoundException {
        ExportLogic exportLogic = new ExportLogic();
        File file = exportLogic.exportFile(bean);
        view.handleDownload(file, true, bean.getFilename() + Constants.FILE_EXT_SQL);
    }
}
