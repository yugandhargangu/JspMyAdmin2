package com.tracknix.jspmyadmin.application.database.routine.controllers;

import com.tracknix.jspmyadmin.application.database.routine.beans.RoutineBean;
import com.tracknix.jspmyadmin.application.database.routine.beans.RoutineListBean;
import com.tracknix.jspmyadmin.application.database.routine.logic.RoutineLogic;
import com.tracknix.jspmyadmin.framework.constants.AppConstants;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.exception.EncodingException;
import com.tracknix.jspmyadmin.framework.web.annotations.*;
import com.tracknix.jspmyadmin.framework.web.logic.EncodeHelper;
import com.tracknix.jspmyadmin.framework.web.utils.*;

import java.sql.SQLException;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/03
 */
@WebController(requestLevel = RequestLevel.DATABASE)
public class ProceduresController {

    @Handle(path = "/database_procedures.html")
    @GenerateToken
    private void procedures(View view, @Model RoutineListBean bean, @LogicParam RoutineLogic routineLogic)
            throws SQLException {
        routineLogic.fillListBean(bean, Constants.PROCEDURE);
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_DATABASE_ROUTINE_PROCEDURES);
    }

    @Handle(path = "/database_procedure_create.html", methodType = MethodType.POST)
    @ValidateToken
    private void procedureCtreate(View view, RedirectParams redirectParams, @Model RoutineBean bean, @LogicParam RoutineLogic routineLogic) throws SQLException {
        if (routineLogic.isExisted(bean.getName(), Constants.PROCEDURE, bean.getRequest_db())) {
            view.setType(ViewType.REDIRECT);
            view.setPath(AppConstants.PATH_DATABASE_PROCEDURES);
            redirectParams.put(Constants.ERR_KEY, AppConstants.MSG_PROCEDURE_ALREADY_EXISTED);
            return;
        }
        bean.init();
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_DATABASE_ROUTINE_CREATEPROCEDURE);
    }

    @Handle(path = "/database_procedure_show_create.text", methodType = MethodType.POST)
    @ValidateToken
    @Rest
    private QueryReturnBean procedureShowCreate(RequestAdaptor requestAdaptor, @Model RoutineListBean bean, @LogicParam RoutineLogic routineLogic) throws EncodingException {

        QueryReturnBean jsonObject = new QueryReturnBean();
        try {
            String result = routineLogic.showCreate(bean, true);
            jsonObject.setQuery(result);
        } catch (SQLException e) {
            jsonObject.setErr(e.getMessage());
        }
        return jsonObject;
    }

    @Handle(path = "/database_create_procedure_post.text")
    @ValidateToken
    private QueryReturnBean procedureCreate(Messages messages, EncodeHelper encodeObj, RequestAdaptor requestAdaptor, @Model RoutineBean bean, @LogicParam RoutineLogic routineLogic) throws EncodingException {
        QueryReturnBean jsonObject = new QueryReturnBean();
        try {
            if (routineLogic.isExisted(bean.getName(), Constants.PROCEDURE, bean.getRequest_db())) {
                jsonObject.setErr(messages.getMessage(AppConstants.MSG_PROCEDURE_ALREADY_EXISTED));
            } else {
                String result = routineLogic.saveProcedure(bean);
                if (result != null) {
                    jsonObject.setQuery(result);
                } else {
                    jsonObject.setErr(AppConstants.MSG_PROCEDURE_SAVE_SUCCESS);
                }
            }
        } catch (SQLException e) {
            jsonObject.setErr(e.getMessage());
        }
        return jsonObject;
    }

    @Handle(path = "/database_procedure_drop.html", methodType = MethodType.POST)
    @ValidateToken
    private void dropProcedure(View view, RedirectParams redirectParams, @Model RoutineListBean bean, @LogicParam RoutineLogic routineLogic) {
        try {
            routineLogic.dropRoutines(bean, true);
            redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_PROCEDURE_DROP_SUCCESS);
        } catch (SQLException e) {
            redirectParams.put(Constants.ERR, e.getMessage());
        }
        view.setType(ViewType.REDIRECT);
        view.setPath(AppConstants.PATH_DATABASE_PROCEDURES);
    }
}