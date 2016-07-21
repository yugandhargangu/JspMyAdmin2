/**
 * 
 */
package com.jspmyadmin.app.table.data.controllers;

import com.jspmyadmin.app.table.data.beans.DataSelectBean;
import com.jspmyadmin.app.table.data.logic.DataSelectLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/22
 *
 */
@WebController(authentication = true, path = "/table_blob_download.html")
public class TableBlobDownloadController extends Controller<DataSelectBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(DataSelectBean bean, View view) throws Exception {

		DataSelectLogic dataSelectLogic = null;
		try {
			super.fillBasics(bean);
			String table = super.checkForTable(bean);
			if (table == null) {
				table = (String) session.getAttribute(FrameworkConstants.SESSION_TABLE);
			}
			dataSelectLogic = new DataSelectLogic(table);
			dataSelectLogic.fillBean(bean);
			super.generateToken(bean);
			view.setType(ViewType.FORWARD);
			view.setPath(AppConstants.JSP_TABLE_DATA_DATA);
		} catch (Exception e) {
			e.printStackTrace();
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_HOME);
		} finally {
			dataSelectLogic = null;
		}

	}

	@Override
	protected void handlePost(DataSelectBean bean, View view) throws Exception {
		this.handleGet(bean, view);
	}
}
