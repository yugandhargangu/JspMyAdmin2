/**
 * 
 */
package com.jspmyadmin.app.table.data.controllers;

import com.jspmyadmin.app.table.data.beans.DataSelectBean;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.Download;
import com.jspmyadmin.framework.web.annotations.HandleGetOrPost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.logic.EncodeHelper;
import com.jspmyadmin.framework.web.utils.RequestAdaptor;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/22
 *
 */
@WebController(authentication = true, path = "/table_blob_download.html", requestLevel = RequestLevel.TABLE)
public class TableBlobDownloadController {

	@Detect
	private EncodeHelper encodeObj;
	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private View view;
	@Model
	private DataSelectBean bean;

	@HandleGetOrPost
	@Download
	private void download() {

	}
}
