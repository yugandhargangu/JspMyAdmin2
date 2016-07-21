/**
 * 
 */
package com.jspmyadmin.app.database.routine.controllers;

import org.json.JSONObject;

import com.jspmyadmin.app.database.routine.beans.RoutineListBean;
import com.jspmyadmin.app.database.routine.logic.RoutineLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/16
 *
 */
@WebController(authentication = true, path = "/database_function_drop.html")
public class FunctionDropController extends Controller<RoutineListBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(RoutineListBean bean, View view) throws Exception {
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_DATABASE_STRUCTURE);
	}

	@Override
	@ValidateToken
	protected void handlePost(RoutineListBean bean, View view) throws Exception {

		RoutineLogic routineLogic = null;
		JSONObject jsonObject = new JSONObject();
		try {
			routineLogic = new RoutineLogic();
			routineLogic.dropRoutines(bean, false);
			jsonObject.put(FrameworkConstants.MSG_KEY, "msg.function_drop_success");
		} catch (Exception e) {
			jsonObject.put(FrameworkConstants.ERR, e.getMessage());
		} finally {
			routineLogic = null;
		}
		view.setToken(super.encode(jsonObject));
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_DATABASE_FUNCTIONS);
	}

}
