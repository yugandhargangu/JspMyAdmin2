/**
 * 
 */
package com.jspmyadmin.app.table.partition.controllers;

import com.jspmyadmin.app.table.partition.beans.PartinitionBean;
import com.jspmyadmin.app.table.partition.logic.PartitionLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.FrameworkConstants;
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
@WebController(authentication = true, path = "/table_partitions.html")
public class PartitionController extends Controller<PartinitionBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(PartinitionBean bean, View view) throws Exception {

		PartitionLogic partitionLogic = null;
		try {
			super.fillBasics(bean);
			String table = super.checkForTable(bean);
			if (table == null) {
				table = (String) session.getAttribute(FrameworkConstants.SESSION_TABLE);
			}
			partitionLogic = new PartitionLogic(table);
			partitionLogic.fillBean(bean);
			super.generateToken(bean);
			view.setType(ViewType.FORWARD);
			view.setPath(AppConstants.JSP_TABLE_COMMON_PARTITIONS);
		} catch (Exception e) {
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_HOME);
		}
	}

	@Override
	protected void handlePost(PartinitionBean bean, View view) throws Exception {
		this.handleGet(bean, view);
	}

}
