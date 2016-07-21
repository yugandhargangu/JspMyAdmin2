/**
 * 
 */
package com.jspmyadmin.app.database.routine.controllers;

import com.jspmyadmin.app.database.routine.beans.RoutineListBean;
import com.jspmyadmin.app.database.routine.logic.RoutineLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/03
 *
 */
@WebController(authentication = true, path = "/database_functions.html")
public class FunctionListController extends Controller<RoutineListBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(RoutineListBean bean, View view) throws Exception {
		this.handlePost(bean, view);
	}

	@Override
	protected void handlePost(RoutineListBean bean, View view) throws Exception {
		super.fillBasics(bean);
		RoutineLogic routineLogic = new RoutineLogic();
		routineLogic.fillListBean(bean, "FUNCTION");
		super.generateToken(bean);
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_DATABASE_ROUTINE_FUNCTIONS);
	}

}
