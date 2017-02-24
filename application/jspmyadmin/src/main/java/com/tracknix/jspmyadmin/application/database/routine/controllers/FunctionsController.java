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
 */
@WebController(requestLevel = RequestLevel.DATABASE)
public class FunctionsController {

    @Handle(path = "/database_functions.html")
    @GenerateToken
    private void functions(View view, @Model RoutineListBean bean, @LogicParam RoutineLogic routineLogic) throws SQLException {
        routineLogic.fillListBean(bean, Constants.FUNCTION);
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_DATABASE_ROUTINE_FUNCTIONS);
    }

    @Handle(path = "/database_function_create.html", methodType = MethodType.POST)
    @ValidateToken
    @GenerateToken
    private void functionCreate(View view, RedirectParams redirectParams, @Model RoutineBean bean, @LogicParam RoutineLogic routineLogic) throws SQLException {
        if (routineLogic.isExisted(bean.getName(), Constants.FUNCTION, bean.getRequest_db())) {
            view.setType(ViewType.REDIRECT);
            view.setPath(AppConstants.PATH_DATABASE_FUNCTIONS);
            redirectParams.put(Constants.ERR_KEY, AppConstants.MSG_FUNCTION_ALREADY_EXISTED);
            return;
        }
        bean.init();
        bean.setData_types_map(Constants.Utils.DATA_TYPES_MAP);
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_DATABASE_ROUTINE_CREATEFUNCTION);
    }

    @Handle(path = "/database_function_show_create.text")
    @ValidateToken
    @Rest
    private QueryReturnBean showFunctionCreate(RequestAdaptor requestAdaptor, @Model RoutineListBean bean, @LogicParam RoutineLogic routineLogic) throws EncodingException {

        QueryReturnBean queryReturnBean = new QueryReturnBean();
        try {
            String result = routineLogic.showCreate(bean, false);
            queryReturnBean.setQuery(result);
        } catch (SQLException e) {
            queryReturnBean.setErr(e.getMessage());
        }
        return queryReturnBean;
    }

    @Handle(path = "/database_create_function_post.text")
    @ValidateToken
    @Rest
    private QueryReturnBean functionCreate(Messages messages, RequestAdaptor requestAdaptor, EncodeHelper encodeObj, @Model RoutineBean bean, @LogicParam RoutineLogic routineLogic) throws EncodingException {

        QueryReturnBean jsonObject = new QueryReturnBean();
        try {
            if (routineLogic.isExisted(bean.getName(), Constants.FUNCTION, bean.getRequest_db())) {
                jsonObject.setErr(messages.getMessage(AppConstants.MSG_FUNCTION_ALREADY_EXISTED));
            } else {
                String result = routineLogic.saveFunction(bean);
                if (result != null) {
                    jsonObject.setQuery(result);
                } else {
                    jsonObject.setMsg(AppConstants.MSG_FUNCTION_SAVE_SUCCESS);
                }
            }
        } catch (SQLException e) {
            jsonObject.setErr(e.getMessage());
        }
        return jsonObject;
    }

    @Handle(path = "/database_function_drop.html")
    @ValidateToken
    private void functionDrop(View view, RedirectParams redirectParams, @Model RoutineListBean bean) {

        RoutineLogic routineLogic = null;
        try {
            routineLogic = new RoutineLogic();
            routineLogic.dropRoutines(bean, false);
            redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_FUNCTION_DROP_SUCCESS);
        } catch (SQLException e) {
            redirectParams.put(Constants.ERR, e.getMessage());
        }
        view.setType(ViewType.REDIRECT);
        view.setPath(AppConstants.PATH_DATABASE_FUNCTIONS);
    }
}
