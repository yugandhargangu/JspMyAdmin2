package com.tracknix.jspmyadmin.application.server.controllers;

import com.tracknix.jspmyadmin.application.server.beans.common.CommonListBean;
import com.tracknix.jspmyadmin.application.server.beans.common.VariableBean;
import com.tracknix.jspmyadmin.application.server.services.VariableLogic;
import com.tracknix.jspmyadmin.framework.constants.AppConstants;
import com.tracknix.jspmyadmin.framework.web.annotations.*;
import com.tracknix.jspmyadmin.framework.web.utils.*;

import java.sql.SQLException;
import java.util.Collection;

/**
 * @author Yugandhar Gangu
 */
@WebController(requestLevel = RequestLevel.SERVER)
public class VariableController {

    @Handle(path = "/server/variables.jma", methodType = MethodType.GET)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public ResponseBody<Collection<String[]>> variables(@Model CommonListBean bean, @LogicParam VariableLogic variableLogic) {
        try {
            return new ResponseBody<Collection<String[]>>().body(variableLogic.getVariables());
        } catch (SQLException e) {
            return new ResponseBody<Collection<String[]>>().error(ResponseBody.MYSQL_ERROR).message(e.getMessage());
        }
    }

    @Handle(path = "/server/variable.jma", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public ResponseBody<Void> variableSave(@Model VariableBean bean, @LogicParam VariableLogic variableLogic) {
        try {
            if (variableLogic.isEmpty(bean.getName()) || bean.getScope() == null || bean.getScope().length == 0) {
                return new ResponseBody<Void>().error(ResponseBody.PARAMS_ERROR).message(AppConstants.ERR_INVALID_PARAMS);
            } else {
                variableLogic.save(bean);
                return new ResponseBody<Void>().message(AppConstants.MSG_SAVE_SUCCESS);
            }
        } catch (SQLException e) {
            return new ResponseBody<Void>().error(ResponseBody.MYSQL_ERROR).message(e.getMessage());
        }
    }
}