package com.tracknix.jspmyadmin.application.database.controllers;

import com.tracknix.jspmyadmin.application.common.services.DataLogic;
import com.tracknix.jspmyadmin.application.database.beans.trigger.TablesBean;
import com.tracknix.jspmyadmin.application.database.beans.trigger.TriggerBean;
import com.tracknix.jspmyadmin.application.database.beans.trigger.TriggersBean;
import com.tracknix.jspmyadmin.application.database.services.TriggerLogic;
import com.tracknix.jspmyadmin.framework.connection.util.Definers;
import com.tracknix.jspmyadmin.framework.connection.util.TriggerEvents;
import com.tracknix.jspmyadmin.framework.connection.util.TriggerOrder;
import com.tracknix.jspmyadmin.framework.connection.util.TriggerTime;
import com.tracknix.jspmyadmin.framework.constants.AppConstants;
import com.tracknix.jspmyadmin.framework.web.annotations.*;
import com.tracknix.jspmyadmin.framework.web.utils.*;

import java.sql.SQLException;

/**
 * @author Yugandhar Gangu
 */
@WebController(requestLevel = RequestLevel.DATABASE)
public class TriggersController {

    @Handle(path = "/database/structure/triggers.jma")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public TriggersBean triggers(@Model TriggersBean bean, @LogicParam TriggerLogic triggerLogic) {
        try {
            triggerLogic.fillListBean(bean);
        } catch (SQLException e) {
            bean.setErr(e.getMessage());
        }
        return bean;
    }

    @Handle(path = "/database/trigger/exists.jma", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public DefaultBean triggerExists(@Model TriggerBean bean, @LogicParam TriggerLogic triggerLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            if (triggerLogic.isExisted(bean.getTrigger_name(), bean.getRequest_db())) {
                defaultBean.setErr(AppConstants.MSG_TRIGGER_ALREADY_EXISTED);
            }
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }

    @Handle(path = "/database/trigger/create.jma", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public QueryReturnBean createTrigger(@Model TriggerBean bean, @LogicParam TriggerLogic triggerLogic) {
        QueryReturnBean queryReturnBean = new QueryReturnBean();
        try {
            if (triggerLogic.isExisted(bean.getTrigger_name(), bean.getRequest_db())) {
                queryReturnBean.setErr(AppConstants.MSG_TRIGGER_ALREADY_EXISTED);
            } else {
                String result = triggerLogic.save(bean);
                if (result != null) {
                    queryReturnBean.setQuery(result);
                } else {
                    queryReturnBean.setMsg(AppConstants.MSG_TRIGGER_CREATE_SUCCESS);
                }
            }
        } catch (SQLException e) {
            queryReturnBean.setErr(e.getMessage());
        }
        return queryReturnBean;
    }

    @Handle(path = "/database/trigger/show_create.jma", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public QueryReturnBean showCreateTrigger(@Model TriggersBean bean, @LogicParam TriggerLogic triggerLogic) {
        QueryReturnBean queryReturnBean = new QueryReturnBean();
        try {
            queryReturnBean.setQuery(triggerLogic.showCreate(bean));
        } catch (SQLException e) {
            queryReturnBean.setErr(e.getMessage());
        }
        return queryReturnBean;
    }

    @Handle(path = "/database/trigger/drop.jma", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public DefaultBean dropTrigger(Messages messages, @Model TriggersBean bean, @LogicParam TriggerLogic triggerLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            triggerLogic.drop(bean);
            defaultBean.setMsg(AppConstants.MSG_TRIGGER_DROP_SUCCESS);
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }
}