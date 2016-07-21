/**
 * 
 */
package com.jspmyadmin.app.table.insert.controllers;

import com.jspmyadmin.app.table.insert.beans.InsertUpdateBean;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/11
 *
 */
@WebController(authentication = false, path = "/table_upload.html")
public class UploadController extends Controller<InsertUpdateBean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(InsertUpdateBean bean, View view) throws Exception {
		view.setType(ViewType.FORWARD);
		view.setPath("Upload.jsp");
	}

	@Override
	protected void handlePost(InsertUpdateBean bean, View view) throws Exception {
		view.setType(ViewType.REDIRECT);
		view.setPath("/table_upload.html");
	}

}
