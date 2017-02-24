/**
 * 
 */
package com.jspmyadmin.app.table.export.controllers;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.json.JSONException;

import com.jspmyadmin.app.table.export.beans.ExportBean;
import com.jspmyadmin.app.table.export.logic.ExportLogic;
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
@WebController(authentication = true, path = "/table_export.html", requestLevel = RequestLevel.TABLE)
public class TableExportController {

	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private View view;
	@Model
	private ExportBean bean;

	@HandleGet
	private void loadExport() throws EncodingException, SQLException {

		ExportLogic exportLogic = new ExportLogic();
		exportLogic.fillBean(bean);
		bean.setFilename(bean.getRequest_table());
		bean.setToken(requestAdaptor.generateToken());
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_TABLE_EXPORT_EXPORT);
	}

	@HandlePost
	@ValidateToken
	@Download
	private void exportFile() throws IOException, EncodingException, SQLException, ClassNotFoundException,
			ParserConfigurationException, TransformerException, JSONException {
		ExportLogic exportLogic = new ExportLogic();
		File file = exportLogic.exportFile(bean);
		if (bean.getFilename() == null || Constants.BLANK.equals(bean.getFilename().trim())) {
			bean.setFilename(bean.getRequest_table());
		}
		view.handleDownload(file, true,
				bean.getFilename() + Constants.SYMBOL_DOT + bean.getExport_type().toLowerCase());
	}
}
