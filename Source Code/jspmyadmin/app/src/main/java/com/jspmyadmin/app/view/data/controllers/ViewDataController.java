/**
 * 
 */
package com.jspmyadmin.app.view.data.controllers;

import com.jspmyadmin.app.view.data.beans.DataSelectBean;
import com.jspmyadmin.app.view.data.logic.DataSelectLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/22
 *
 */
@WebController(authentication = true, path = "/view_data.html", requestLevel = RequestLevel.VIEW)
public class ViewDataController extends Controller<DataSelectBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(DataSelectBean bean, View view) throws Exception {

		DataSelectLogic dataSelectLogic = null;
		try {
			super.fillBasics(bean);
			super.setView(bean);
			dataSelectLogic = new DataSelectLogic(bean.getRequest_view());
			dataSelectLogic.fillBean(bean);
			bean.setToken(super.generateToken());
			view.setType(ViewType.FORWARD);
			view.setPath(AppConstants.JSP_VIEW_DATA_DATA);
		} catch (Exception e) {
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_HOME);
		}

	}

	@Override
	protected void handlePost(DataSelectBean bean, View view) throws Exception {
		this.handleGet(bean, view);
	}
}
