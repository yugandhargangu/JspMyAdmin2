package com.tracknix.jspmyadmin.application.database.controllers;

import com.tracknix.jspmyadmin.application.database.beans.routine.RoutineBean;
import com.tracknix.jspmyadmin.application.database.beans.routine.RoutinesBean;
import com.tracknix.jspmyadmin.application.database.services.RoutineLogic;
import com.tracknix.jspmyadmin.framework.connection.util.DataTypes;
import com.tracknix.jspmyadmin.framework.connection.util.Definers;
import com.tracknix.jspmyadmin.framework.connection.util.SecurityTypes;
import com.tracknix.jspmyadmin.framework.connection.util.SqlTypes;
import com.tracknix.jspmyadmin.framework.constants.AppConstants;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.exception.EncodingException;
import com.tracknix.jspmyadmin.framework.web.annotations.*;
import com.tracknix.jspmyadmin.framework.web.utils.*;

import java.sql.SQLException;

/**
 * @author Yugandhar Gangu
 */
@WebController(requestLevel = RequestLevel.DATABASE)
public class RoutinesController {

    @Handle(path = "/database/routines.jma")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public RoutinesBean routines(@Model RoutinesBean bean, @LogicParam RoutineLogic routineLogic) {
        try {
            if (bean.getType() != null) {
                routineLogic.fillListBean(bean);
            }
        } catch (SQLException e) {
            bean.setErr(e.getMessage());
        }
        return bean;
    }

    @Handle(path = "/database/routine/exists.jma", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public DefaultBean routineExists(@Model RoutinesBean bean, @LogicParam RoutineLogic routineLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            if (routineLogic.isEmpty(bean.getName())) {
                if (Constants.PROCEDURE.equals(bean.getType())) {
                    defaultBean.setErr("msg.procedure_name_blank");
                } else if (Constants.FUNCTION.equals(bean.getType())) {
                    defaultBean.setErr("msg.function_name_blank");
                }
            } else if (routineLogic.isExisted(bean.getName(), bean.getType(), bean.getRequest_db())) {
                if (Constants.PROCEDURE.equals(bean.getType())) {
                    defaultBean.setErr(AppConstants.MSG_PROCEDURE_ALREADY_EXISTED);
                } else if (Constants.FUNCTION.equals(bean.getType())) {
                    defaultBean.setErr(AppConstants.MSG_FUNCTION_ALREADY_EXISTED);
                }
            }
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }

    @Handle(path = "/database/procedure/create.jma", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public QueryReturnBean procedureCreate(@Model RoutineBean bean, @LogicParam RoutineLogic routineLogic) {
        QueryReturnBean queryReturnBean = new QueryReturnBean();
        try {
            if (routineLogic.isEmpty(bean.getName())) {
                queryReturnBean.setErr("msg.procedure_name_blank");
            } else if (routineLogic.isEmpty(bean.getBody())) {
                queryReturnBean.setErr("msg.procedure_body_blank");
            } else if (routineLogic.isExisted(bean.getName(), Constants.PROCEDURE, bean.getRequest_db())) {
                queryReturnBean.setErr(AppConstants.MSG_PROCEDURE_ALREADY_EXISTED);
            } else {
                String result = routineLogic.saveProcedure(bean);
                if (result != null) {
                    queryReturnBean.setQuery(result);
                } else {
                    queryReturnBean.setMsg(AppConstants.MSG_PROCEDURE_SAVE_SUCCESS);
                }
            }
        } catch (SQLException e) {
            queryReturnBean.setErr(e.getMessage());
        }
        return queryReturnBean;
    }

    @Handle(path = "/database/function/create.jma", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public QueryReturnBean functionCreate(@Model RoutineBean bean, @LogicParam RoutineLogic routineLogic) {
        QueryReturnBean queryReturnBean = new QueryReturnBean();
        try {
            if (routineLogic.isEmpty(bean.getName())) {
                queryReturnBean.setErr("msg.function_name_blank");
            } else if (routineLogic.isEmpty(bean.getBody())) {
                queryReturnBean.setErr("msg.function_body_blank");
            } else if (routineLogic.isExisted(bean.getName(), Constants.FUNCTION, bean.getRequest_db())) {
                queryReturnBean.setErr(AppConstants.MSG_FUNCTION_ALREADY_EXISTED);
            } else {
                String result = routineLogic.saveFunction(bean);
                if (result != null) {
                    queryReturnBean.setQuery(result);
                } else {
                    queryReturnBean.setMsg(AppConstants.MSG_FUNCTION_SAVE_SUCCESS);
                }
            }
        } catch (SQLException e) {
            queryReturnBean.setErr(e.getMessage());
        }
        return queryReturnBean;
    }

    @Handle(path = "/database/routine/show_create.jma", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public QueryReturnBean showRoutineCreate(@Model RoutinesBean bean, @LogicParam RoutineLogic routineLogic) {
        QueryReturnBean queryReturnBean = new QueryReturnBean();
        try {
            if (bean.getType() != null) {
                String result = routineLogic.showCreate(bean);
                queryReturnBean.setQuery(result);
            }
        } catch (SQLException e) {
            queryReturnBean.setErr(e.getMessage());
        }
        return queryReturnBean;
    }


    @Handle(path = "/database/routine/drop.jma", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public DefaultBean dropRoutine(@Model RoutinesBean bean, @LogicParam RoutineLogic routineLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            if (bean.getType() != null) {
                routineLogic.dropRoutines(bean);
                if (Constants.PROCEDURE.equals(bean.getType())) {
                    defaultBean.setMsg(AppConstants.MSG_PROCEDURE_DROP_SUCCESS);
                } else if (Constants.FUNCTION.equals(bean.getType())) {
                    defaultBean.setMsg(AppConstants.MSG_FUNCTION_DROP_SUCCESS);
                }
            }
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }
}