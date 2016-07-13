/**
 * 
 */
package com.jspmyadmin.app.table.common.controllers;

import java.io.PrintWriter;

import com.jspmyadmin.app.table.common.beans.ForeignKeyBean;
import com.jspmyadmin.app.table.common.logic.ForeignKeyLogic;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.ResponseBody;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.View;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/05
 *
 */
@WebController(authentication = true, path = "/table_fetch_columns.text")
public class FetchColumnsController extends Controller<ForeignKeyBean> {

	private static final long serialVersionUID = 1L;

	@Override
	@ResponseBody
	protected void handleGet(ForeignKeyBean bean, View view) throws Exception {
		this.handlePost(bean, view);
	}

	@Override
	@ResponseBody
	protected void handlePost(ForeignKeyBean bean, View view) throws Exception {
		String result = null;
		ForeignKeyLogic foreignKeyLogic = null;
		try {
			if (bean.getRef_table_name() != null) {
				foreignKeyLogic = new ForeignKeyLogic(bean.getRef_table_name());
				result = foreignKeyLogic.fetchColumns();
			} else {
				result = FrameworkConstants.BLANK;
			}
		} catch (Exception e) {
			result = FrameworkConstants.BLANK;
		} finally {
			foreignKeyLogic = null;
		}
		PrintWriter writer = response.getWriter();
		try {
			writer.print(super.encrypt(result));
		} finally {
			writer.close();
		}
	}
}
