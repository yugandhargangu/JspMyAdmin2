/**
 * 
 */
package com.jspmyadmin.app.database.routine.controllers;

import java.sql.SQLException;

import com.jspmyadmin.app.database.routine.beans.RoutineBean;
import com.jspmyadmin.app.database.routine.logic.RoutineLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandlePost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.RedirectParams;
import com.jspmyadmin.framework.web.utils.RequestAdaptor;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/04
 *
 */
@WebController(authentication = true, path = "/database_procedure_create.html", requestLevel = RequestLevel.DATABASE)
public class ProcedureCreateController {

	@Detect
	private RedirectParams redirectParams;
	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private View view;
	@Model
	private RoutineBean bean;

	@HandlePost
	@ValidateToken
	private void procedureCtreate() throws SQLException, EncodingException {
		RoutineLogic routineLogic = new RoutineLogic();
		if (routineLogic.isExisted(bean.getName(), Constants.PROCEDURE, bean.getRequest_db())) {
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_DATABASE_PROCEDURES);
			redirectParams.put(Constants.ERR_KEY, AppConstants.MSG_PROCEDURE_ALREADY_EXISTED);
			return;
		}
		bean.init();
		bean.setToken(requestAdaptor.generateToken());
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_DATABASE_ROUTINE_CREATEPROCEDURE);
	}
}
