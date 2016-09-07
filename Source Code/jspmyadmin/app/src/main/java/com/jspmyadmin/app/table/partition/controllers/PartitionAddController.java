/**
 * 
 */
package com.jspmyadmin.app.table.partition.controllers;

import java.sql.SQLException;

import com.jspmyadmin.app.table.partition.beans.PartinitionBean;
import com.jspmyadmin.app.table.partition.logic.PartitionLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandlePost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.RedirectParams;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * 
 * @author Yugandhar Gangu
 * @created_at 2016/07/11
 *
 */
@WebController(authentication = true, path = "/table_partition_add.html", requestLevel = RequestLevel.TABLE)
public class PartitionAddController {

	@Detect
	private RedirectParams redirectParams;
	@Detect
	private View view;
	@Model
	private PartinitionBean bean;

	@HandlePost
	@ValidateToken
	private void addPrtition() {
		PartitionLogic partitionLogic = null;
		try {
			partitionLogic = new PartitionLogic(bean.getRequest_table());
			partitionLogic.addPartition(bean);
		} catch (SQLException e) {
			redirectParams.put(Constants.ERR, e.getMessage());
		}
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_TABLE_PARTITIONS);
	}

}
