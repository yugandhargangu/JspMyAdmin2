/**
 * 
 */
package com.jspmyadmin.app.table.partition.controllers;

import com.jspmyadmin.app.table.partition.beans.PartinitionBean;
import com.jspmyadmin.app.table.partition.logic.PartitionLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
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
public class PartitionAddController extends Controller<PartinitionBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(PartinitionBean bean, View view) throws Exception {
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_HOME);
	}

	@Override
	@ValidateToken
	protected void handlePost(PartinitionBean bean, View view) throws Exception {
		PartitionLogic partitionLogic = null;
		try {
			super.setTable(bean);
			partitionLogic = new PartitionLogic(bean.getRequest_table());
			partitionLogic.addPartition(bean);
		} catch (Exception e) {
			redirectParams.put(FrameworkConstants.ERR, e.getMessage());
		}
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_TABLE_PARTITIONS);
	}

}
