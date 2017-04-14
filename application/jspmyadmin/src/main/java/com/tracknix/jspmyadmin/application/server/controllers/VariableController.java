package com.tracknix.jspmyadmin.application.server.controllers;

import com.tracknix.jspmyadmin.application.server.beans.common.CommonListBean;
import com.tracknix.jspmyadmin.application.server.beans.common.VariableBean;
import com.tracknix.jspmyadmin.application.server.services.VariableLogic;
import com.tracknix.jspmyadmin.framework.constants.AppConstants;
import com.tracknix.jspmyadmin.framework.exception.EncodingException;
import com.tracknix.jspmyadmin.framework.web.annotations.*;
import com.tracknix.jspmyadmin.framework.web.utils.*;

import java.sql.SQLException;

/**
 * @author Yugandhar Gangu
 */
@WebController(requestLevel = RequestLevel.SERVER)
public class VariableController {

    @Handle(path = "/server/variables.text", methodType = MethodType.GET)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @GenerateToken
    public CommonListBean variables(@Model CommonListBean bean, @LogicParam VariableLogic variableLogic) {
        try {
            variableLogic.fillBean(bean);
        } catch (SQLException e) {
            bean.setErr(e.getMessage());
        }
        return bean;
    }

    @Handle(path = "/server/variable.text", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @ValidateToken
    @GenerateToken
    public DefaultBean variableSave(Messages messages, @Model VariableBean bean, @LogicParam VariableLogic variableLogic) throws EncodingException {
        DefaultBean defaultBean = new DefaultBean();
        try {
            if (variableLogic.isEmpty(bean.getName()) || bean.getScope() == null || bean.getScope().length == 0) {
                defaultBean.setErr(messages.getMessage(AppConstants.ERR_INVALID_PARAMS));
            } else {
                variableLogic.save(bean);
                defaultBean.setMsg(messages.getMessage(AppConstants.MSG_SAVE_SUCCESS));
            }
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }
}