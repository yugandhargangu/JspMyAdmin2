/**
 * 
 */
package com.jspmyadmin.app.table.partition.controllers;

import java.sql.SQLException;

import com.jspmyadmin.app.table.partition.beans.PartinitionBean;
import com.jspmyadmin.app.table.partition.logic.PartitionLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandleGetOrPost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.RequestAdaptor;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * 
 * @author Yugandhar Gangu
 * @created_at 2016/07/11
 *
 */
@WebController(authentication = true, path = "/table_partitions.html", requestLevel = RequestLevel.TABLE)
public class PartitionController {

	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private View view;
	@Model
	private PartinitionBean bean;

	@HandleGetOrPost
	private void partitions() throws EncodingException {

		PartitionLogic partitionLogic = null;
		try {
			partitionLogic = new PartitionLogic(bean.getRequest_table());
			partitionLogic.fillBean(bean);
			bean.setToken(requestAdaptor.generateToken());
			view.setType(ViewType.FORWARD);
			view.setPath(AppConstants.JSP_TABLE_COMMON_PARTITIONS);
		} catch (SQLException e) {
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_HOME);
		}
	}

}
