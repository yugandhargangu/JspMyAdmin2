/**
 * 
 */
package com.jspmyadmin.app.table.partition.controllers;

import org.json.JSONObject;

import com.jspmyadmin.app.table.partition.beans.PartinitionBean;
import com.jspmyadmin.app.table.partition.logic.PartitionLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * 
 * @author Yugandhar Gangu
 * @created_at 2016/07/11
 *
 */
@WebController(authentication = true, path = "/table_partition_add.html")
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
			String table = super.checkForTable(bean);
			if (table == null) {
				table = (String) session.getAttribute(FrameworkConstants.SESSION_TABLE);
			}
			partitionLogic = new PartitionLogic(table);
			partitionLogic.addPartition(bean);
		} catch (Exception e) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(FrameworkConstants.ERR, e.getMessage());
			view.setToken(super.encode(jsonObject));
		}
		view.setPath(AppConstants.PATH_TABLE_PARTITIONS);
		view.setType(ViewType.REDIRECT);
	}

}
