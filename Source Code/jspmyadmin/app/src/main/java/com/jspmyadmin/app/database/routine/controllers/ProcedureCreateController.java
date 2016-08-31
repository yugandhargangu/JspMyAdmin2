/**
 * 
 */
package com.jspmyadmin.app.database.routine.controllers;

import com.jspmyadmin.app.database.routine.beans.RoutineBean;
import com.jspmyadmin.app.database.routine.logic.RoutineLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/04
 *
 */
@WebController(authentication = true, path = "/database_procedure_create.html", requestLevel = RequestLevel.DATABASE)
public class ProcedureCreateController extends Controller<RoutineBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(RoutineBean bean, View view) throws Exception {

	}

	@Override
	@ValidateToken
	protected void handlePost(RoutineBean bean, View view) throws Exception {
		RoutineLogic routineLogic = new RoutineLogic();
		if (routineLogic.isExisted(bean.getName(), FrameworkConstants.PROCEDURE, bean.getRequest_db())) {
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_DATABASE_PROCEDURES);
			redirectParams.put(FrameworkConstants.ERR_KEY, AppConstants.MSG_PROCEDURE_ALREADY_EXISTED);
			return;
		}
		bean.init();
		bean.setToken(super.generateToken());
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_DATABASE_ROUTINE_CREATEPROCEDURE);
	}
}
