/**
 * 
 */
package com.jspmyadmin.app.database.routine.controllers;

import org.json.JSONObject;

import com.jspmyadmin.app.database.routine.beans.RoutineBean;
import com.jspmyadmin.app.database.routine.logic.RoutineLogic;
import com.jspmyadmin.framework.util.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/04
 *
 */
@WebController(authentication = true, path = "/database_function_create")
public class FunctionCreateController extends Controller<RoutineBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(RoutineBean bean, View view) throws Exception {

	}

	@Override
	@ValidateToken
	protected void handlePost(RoutineBean bean, View view) throws Exception {
		RoutineLogic routineLogic = new RoutineLogic();
		if (routineLogic.isExisted(bean.getName(), "FUNCTION")) {
			view.setType(ViewType.REDIRECT);
			view.setPath("/database_functions");
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(FrameworkConstants.ERR_KEY, "msg.function_already_existed");
			view.setToken(encode(jsonObject));
			return;
		}
		bean.init();
		bean.setData_types_map(FrameworkConstants.Utils.DATA_TYPES_MAP);
		super.generateToken(bean);
		view.setType(ViewType.FORWARD);
		view.setPath("database/routine/CreateFunction.jsp");
	}
}
