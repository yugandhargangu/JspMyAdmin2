/**
 * 
 */
package com.jspmyadmin.app.database.routine.controllers;

import java.sql.SQLException;

import com.jspmyadmin.app.database.routine.beans.RoutineListBean;
import com.jspmyadmin.app.database.routine.logic.RoutineLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandleGetOrPost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.RequestAdaptor;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/03
 *
 */
@WebController(authentication = true, path = "/database_procedures.html", requestLevel = RequestLevel.DATABASE)
public class ProcedureListController {

	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private View view;
	@Model
	private RoutineListBean bean;

	@HandleGetOrPost
	private void procedures() throws SQLException, EncodingException {
		RoutineLogic routineLogic = new RoutineLogic();
		routineLogic.fillListBean(bean, Constants.PROCEDURE);
		bean.setToken(requestAdaptor.generateToken());
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_DATABASE_ROUTINE_PROCEDURES);
	}
}
